package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.repositories.UserRepository;
import ru.itis.ruzavin.repositories.UserRepositoryJdbcImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class SecurityServiceImpl implements SecurityService{

	private final UserRepository userRepository;

	public final static String USER_AUTH_COOKIE_NAME = "userAuth";

	private final static int AUTH_COOKIE_MAX_AGE = 60 * 60 * 12;
	private final static int AUTH_COOKIE_MIN_AGE = 0;

	public final static String SESSION_AUTH_ATTRIBUTE_NAME = "isAuth";

	public SecurityServiceImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		this.userRepository = new UserRepositoryJdbcImpl(new SimpleDataSource(properties));
	}

	@Override
	public boolean signIn(HttpServletRequest req, HttpServletResponse response) {
		String password = req.getParameter("userPass");
		String login = req.getParameter("userLogin");
		String checkbox = req.getParameter("isRemember");
		System.out.println(checkbox);
		Optional<UserDTO> userByLogin = userRepository.findUserByLogin(login);
		if(userByLogin.isPresent() && userByLogin.get().getPassword().equals(encrypt(password))){
			setSessionAndCookie(req, response, userByLogin.get(), checkbox);
			return true;
		}
		return false;
	}

	private void setSessionAndCookie(HttpServletRequest req, HttpServletResponse response, UserDTO userByLogin, String checkbox) {
		HttpSession session = req.getSession(true);
		session.setAttribute(SESSION_AUTH_ATTRIBUTE_NAME, true);
		session.setAttribute("user", userByLogin);
		if(checkbox != null){
			createAndSendAuthCookie(response, req);
		}
	}

	@Override
	public boolean signUp(HttpServletRequest req) {
		String nick = req.getParameter("userNick");
		String email = req.getParameter("userEmail");
		String password = encrypt(req.getParameter("userPass"));
		String login = req.getParameter("userLogin");
		if (nick.equals("") || email.equals("") || login.equals("")){
			return false;
		}
		List<UserDTO> all = userRepository.findAll();
		for (UserDTO userDTO:all){
			if(userDTO.getNick().equals(nick) || userDTO.getEmail().equals(email)){
				return false;
			}
		}
		if(!userRepository.findUserByLogin(login).isPresent()) {
			userRepository.save(new UserDTO(nick, email, login, password, "/files/img.png"));
			return true;
		}
		return false;
	}

	@Override
	public boolean isSigned(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(USER_AUTH_COOKIE_NAME)) {
				session.setAttribute("user", userRepository.findUserByLogin(cookie.getValue()).get());
				return true;
			}
		}
		return session.getAttribute(SESSION_AUTH_ATTRIBUTE_NAME) != null && session.getAttribute(SESSION_AUTH_ATTRIBUTE_NAME).equals(true);
	}

	@Override
	public void signOut(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		session.removeAttribute(SESSION_AUTH_ATTRIBUTE_NAME);
		session.removeAttribute("user");
		deleteAuthCookie(response);
	}

	private String encrypt(String password){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void createAndSendAuthCookie(HttpServletResponse response, HttpServletRequest request) {
		Cookie userAuthCookie = new Cookie(USER_AUTH_COOKIE_NAME, request.getParameter("userLogin"));
		userAuthCookie.setMaxAge(AUTH_COOKIE_MAX_AGE);
		response.addCookie(userAuthCookie);
	}

	private void deleteAuthCookie(HttpServletResponse response){
		Cookie userAuthCookie = new Cookie(USER_AUTH_COOKIE_NAME, "false");
		userAuthCookie.setMaxAge(AUTH_COOKIE_MIN_AGE);
		response.addCookie(userAuthCookie);
	}

}
