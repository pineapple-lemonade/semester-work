package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.BuildCommentDTO;
import ru.itis.ruzavin.dto.GuideCommentDTO;
import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.BuildComment;
import ru.itis.ruzavin.models.GuideComment;
import ru.itis.ruzavin.repositories.BuildCommentRepository;
import ru.itis.ruzavin.repositories.BuildCommentRepositoryJdbcImpl;
import ru.itis.ruzavin.repositories.GuideCommentRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class BuildCommentServiceImpl implements BuildCommentService{

	private final BuildCommentRepository buildCommentRepository;

	private final UserService userService;

	public BuildCommentServiceImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		this.buildCommentRepository = new BuildCommentRepositoryJdbcImpl(new SimpleDataSource(properties));
		this.userService = new UserServiceImpl();
	}

	@Override
	public BuildCommentDTO findById(int id) {
		BuildComment comment = buildCommentRepository.findById(id);
		UserDTO userDTO = userService.findUserById(comment.getUserId());
		return new BuildCommentDTO(comment.getId(), userDTO, comment.getBuildId(), comment.getText());
	}

	@Override
	public List<BuildCommentDTO> findAll() {
		List<BuildComment> all = buildCommentRepository.findAll();

		return all.stream().map(buildComment -> new BuildCommentDTO(buildComment.getId(),
						userService.findUserById(buildComment.getUserId()), buildComment.getBuildId(), buildComment.getText()))
				.collect(Collectors.toList());
	}

	@Override
	public void save(BuildComment buildComment) {
		buildCommentRepository.save(buildComment);

	}

	@Override
	public List<BuildCommentDTO> findAllByBuildId(int id) {
		List<BuildComment> allByGuideId = buildCommentRepository.findAllByGuideId(id);

		return allByGuideId.stream().map(buildComment -> new BuildCommentDTO(buildComment.getId(),
						userService.findUserById(buildComment.getUserId()), buildComment.getBuildId(), buildComment.getText()))
				.collect(Collectors.toList());
	}
}
