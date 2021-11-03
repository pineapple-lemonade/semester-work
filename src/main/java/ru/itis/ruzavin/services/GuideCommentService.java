package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.GuideCommentDTO;
import ru.itis.ruzavin.models.GuideComment;

import java.util.List;

public interface GuideCommentService {
	GuideCommentDTO findById(int id);
	List<GuideCommentDTO> findAll();
	void save(GuideComment guideComment);
	List<GuideCommentDTO> findAllByGuideId(int id);
}
