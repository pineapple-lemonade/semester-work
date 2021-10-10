package ru.itis.ruzavin.repositories;

import ru.itis.ruzavin.models.User;

import java.util.Optional;

public interface UserRepository {
	void save(User user);
	Optional<User> findUserByLogin(String login);
}
