package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.User;
import ru.itis.ruzavin.repositories.UserRepository;
import ru.itis.ruzavin.repositories.UserRepositoryJdbcImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class SecurityServiceImpl implements SecurityService{

	private final UserRepository userRepository;

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
	public void signOut(HttpServletRequest req) {
		req.getSession().removeAttribute("email");
	}

	@Override
	public boolean signIn(HttpServletRequest req) {
		String password = req.getParameter("userPass");
		String login = req.getParameter("userLogin");
		Optional<UserDTO> userByLogin = userRepository.findUserByLogin(login);
		return userByLogin.isPresent() && userByLogin.get().getPassword().equals(password);
	}

	@Override
	public boolean signUp(HttpServletRequest req) {
		String nick = req.getParameter("userNick");
		String email = req.getParameter("userEmail");
		String password = req.getParameter("userPass");
		String login = req.getParameter("userLogin");
		if(!userRepository.findUserByLogin(login).isPresent()) {
			userRepository.save(new UserDTO(nick, email, login, password));
			return true;
		} else {
			return false;
		}
	}
}
