package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.models.Guide;

import java.util.List;

public interface GuideService {
	void save(Guide guide);
	List<GuideDTO> findAll();
	List<GuideDTO> findAllByTitle(String title);
	GuideDTO findById(int id);
	List<GuideDTO> findAllByUserId(int id);
}
