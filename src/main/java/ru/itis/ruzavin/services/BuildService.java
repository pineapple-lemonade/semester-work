package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.BuildDTO;
import ru.itis.ruzavin.models.Build;

import java.util.List;

public interface BuildService {
	void save(Build build);
	List<BuildDTO> findAll();
	List<BuildDTO> findAllByTitle(String title);
	BuildDTO findById(int id);
	List<BuildDTO> findAllByUserId(int id);
}
