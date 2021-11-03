package ru.itis.ruzavin.repositories;

import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.models.Guide;

import java.util.List;
import java.util.Optional;

public interface GuideRepository {
	void save(Guide guide);
	List<Guide> findAll();
	Optional<ru.itis.ruzavin.models.Guide> findById(int id);
	List<Guide> findAllByUserId(int userId);
	List<Guide> findAllByTitle(String title);
	void delete(int id);
}
