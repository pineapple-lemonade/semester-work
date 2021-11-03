package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.GuideCommentDTO;
import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.GuideComment;
import ru.itis.ruzavin.repositories.GuideCommentRepository;
import ru.itis.ruzavin.repositories.GuideCommentRepositoryJdbcImpl;
import ru.itis.ruzavin.repositories.GuideRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class GuideCommentServiceImpl implements GuideCommentService{

	private final GuideCommentRepository guideCommentRepository;

	private final UserService userService;

	public GuideCommentServiceImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		this.guideCommentRepository = new GuideCommentRepositoryJdbcImpl(new SimpleDataSource(properties));
		this.userService = new UserServiceImpl();
	}

	@Override
	public GuideCommentDTO findById(int id) {
		GuideComment comment = guideCommentRepository.findById(id);
		UserDTO userDTO = userService.findUserById(comment.getUserId());
		return new GuideCommentDTO(comment.getId(), userDTO, comment.getGuideId(), comment.getText());
	}

	@Override
	public List<GuideCommentDTO> findAll() {
		List<GuideComment> all = guideCommentRepository.findAll();

		return all.stream().map(guideComment -> new GuideCommentDTO(guideComment.getId(),
						userService.findUserById(guideComment.getUserId()), guideComment.getGuideId(), guideComment.getText()))
				.collect(Collectors.toList());
	}

	@Override
	public void save(GuideComment guideComment) {
		guideCommentRepository.save(guideComment);
	}

	@Override
	public List<GuideCommentDTO> findAllByGuideId(int id) {
		List<GuideComment> allByGuideId = guideCommentRepository.findAllByGuideId(id);

		return allByGuideId.stream().map(guideComment -> new GuideCommentDTO(guideComment.getId(),
				userService.findUserById(guideComment.getUserId()), guideComment.getGuideId(), guideComment.getText()))
				.collect(Collectors.toList());
	}
}
