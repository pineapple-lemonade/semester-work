package ru.itis.ruzavin.repositories;

import ru.itis.ruzavin.models.GuideComment;

import java.util.List;

public interface GuideCommentRepository {
	GuideComment findById(int id);
	List<GuideComment> findAll();
	void save(GuideComment guideComment);
	List<GuideComment> findAllByGuideId(int id);
}
