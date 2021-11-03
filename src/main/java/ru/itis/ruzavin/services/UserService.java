package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.UserDTO;

import java.util.List;

public interface UserService {
	void updateAvatar(UserDTO userDTO);

	UserDTO findUserByLogin(String login);

	UserDTO findUserById(int id);

	UserDTO findUserByNick(String id);

	List<UserDTO> findAll();
}
