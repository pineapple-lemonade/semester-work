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
import java.util.Optional;
import java.util.Properties;

public class SecurityServiceImpl implements SecurityService{

	private final UserRepository userRepository;

	//private static String SALT = "sdw132lsap23sd";

	private final static String USER_AUTH_COOKIE_NAME = "userAuth";

	private final static int AUTH_COOKIE_MAX_AGE = 60 * 60 * 12;

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
		Optional<UserDTO> userByLogin = userRepository.findUserByLogin(login);
		if(userByLogin.isPresent() && userByLogin.get().getPassword().equals(encrypt(password))){
			setSessionAndCookie(req, response, userByLogin.get());
			return true;
		}
		return false;
	}

	private void setSessionAndCookie(HttpServletRequest req, HttpServletResponse response, UserDTO userByLogin) {
		HttpSession session = req.getSession(true);
		session.setAttribute(SESSION_AUTH_ATTRIBUTE_NAME, true);
		session.setAttribute("user", userByLogin);
		createAndSendAuthCookie(response, req);
	}

	@Override
	public boolean signUp(HttpServletRequest req) {
		String nick = req.getParameter("userNick");
		String email = req.getParameter("userEmail");
		String password = encrypt(req.getParameter("userPass"));
		String login = req.getParameter("userLogin");
		if(!userRepository.findUserByLogin(login).isPresent()) {
			userRepository.save(new UserDTO(nick, email, login, password));
			return true;
		}
		return false;
	}

	@Override
	public boolean isSigned(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session != null && session.getAttribute(SESSION_AUTH_ATTRIBUTE_NAME).equals(true)){
			return true;
		}
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(USER_AUTH_COOKIE_NAME)) {
				return true;
			}
		}
		return false;
	}

	private String encrypt(String password){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			//password += SALT;
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void createAndSendAuthCookie(HttpServletResponse response, HttpServletRequest request) {
		Cookie userAuthCookie = new Cookie(USER_AUTH_COOKIE_NAME, (String) request.getAttribute("userLogin"));
		userAuthCookie.setMaxAge(AUTH_COOKIE_MAX_AGE);
		response.addCookie(userAuthCookie);
	}

}
