package ru.itis.ruzavin.repositories;

import ru.itis.ruzavin.models.BuildComment;

import java.util.List;

public interface BuildCommentRepository {
	BuildComment findById(int id);
	List<BuildComment> findAll();
	void save(BuildComment buildComment);
	List<BuildComment> findAllByGuideId(int id);
}
