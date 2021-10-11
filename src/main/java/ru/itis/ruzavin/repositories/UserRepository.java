package ru.itis.ruzavin.repositories;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.models.User;

import java.util.Optional;

public interface UserRepository {
	void save(UserDTO user);
	Optional<UserDTO> findUserByLogin(String login);
}
