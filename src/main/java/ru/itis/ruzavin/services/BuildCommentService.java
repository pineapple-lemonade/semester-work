package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.BuildCommentDTO;
import ru.itis.ruzavin.models.BuildComment;

import java.util.List;

public interface BuildCommentService {
	BuildCommentDTO findById(int id);
	List<BuildCommentDTO> findAll();
	void save(BuildComment buildComment);
	List<BuildCommentDTO> findAllByBuildId(int id);
}
