package ru.itis.ruzavin.repositories;

import ru.itis.ruzavin.models.Build;

import java.util.List;
import java.util.Optional;

public interface BuildRepository {
	void save(Build build);
	List<Build> findAll();
	Optional<Build> findById(int id);
	List<Build> findAllByUserId(int userId);
	List<Build> findAllByTitle(String title);
	void delete(int id);
}
