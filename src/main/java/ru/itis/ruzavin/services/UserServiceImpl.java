package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.repositories.UserRepository;
import ru.itis.ruzavin.repositories.UserRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

	public UserServiceImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		this.userRepository = new UserRepositoryJdbcImpl(new SimpleDataSource(properties));
	}

	@Override
	public void updateAvatar(UserDTO userDTO) {
		userRepository.updateAvatar(userDTO);
	}

	@Override
	public UserDTO findUserByLogin(String login){
		return userRepository.findUserByLogin(login).get();
	}

	@Override
	public UserDTO findUserByNick(String  id) {
		return userRepository.findByNick(id);
	}

	@Override
	public UserDTO findUserById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public List<UserDTO> findAll() {
		return userRepository.findAll();
	}
}
