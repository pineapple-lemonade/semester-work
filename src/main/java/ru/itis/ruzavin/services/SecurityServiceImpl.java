package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.repositories.UserRepository;
import ru.itis.ruzavin.repositories.UserRepositoryJdbcImpl;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Properties;

public class SecurityServiceImpl implements SecurityService{

	private final UserRepository userRepository;

	private static String SALT = "sdw132lsap23sd";

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
	public boolean signIn(HttpServletRequest req) {
		String password = req.getParameter("userPass");
		String login = req.getParameter("userLogin");
		Optional<UserDTO> userByLogin = userRepository.findUserByLogin(login);
		if(userByLogin.isPresent() && userByLogin.get().getPassword().equals(password)){
			req.setAttribute("user",userByLogin.get());
			return true;
		}
		return false;
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
		return req.getSession().getAttribute("email") != null;
	}

	private String encrypt(String password){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			password += SALT;
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
