package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.BuildDTO;
import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.Build;
import ru.itis.ruzavin.models.Guide;
import ru.itis.ruzavin.repositories.BuildRepository;
import ru.itis.ruzavin.repositories.BuildRepositoryJdbcImpl;
import ru.itis.ruzavin.repositories.GuideRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class BuildServiceImpl implements BuildService {

	private final BuildRepository buildRepository;

	private final UserService userService;

	public BuildServiceImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		this.buildRepository = new BuildRepositoryJdbcImpl(new SimpleDataSource(properties));
		this.userService = new UserServiceImpl();
	}

	@Override
	public void save(Build build) {
		buildRepository.save(build);

	}

	@Override
	public List<BuildDTO> findAll() {
		List<Build> all = buildRepository.findAll();

		return all.stream().map(build -> new BuildDTO(build.getId(),
				userService.findUserById(build.getUserId()).getNick(), build.getTitle(), build.getText(),
				build.getPhotoUrl(), build.getData())).collect(Collectors.toList());
	}

	@Override
	public List<BuildDTO> findAllByTitle(String title) {
		List<Build> allByTitle = buildRepository.findAllByTitle(title);

		return allByTitle.stream().map(build -> new BuildDTO(build.getId(),
						userService.findUserById(build.getId()).getNick(),
						build.getTitle(), build.getText(), build.getPhotoUrl(), build.getData()))
				.collect(Collectors.toList());
	}

	@Override
	public BuildDTO findById(int id) {
		Build guideById = buildRepository.findById(id).get();
		return BuildDTO.builder()
				.data(guideById.getData())
				.id(guideById.getId())
				.photoUrl(guideById.getPhotoUrl())
				.text(guideById.getText())
				.title(guideById.getTitle())
				.userNick(userService.findUserById(guideById.getUserId()).getNick())
				.build();
	}

	@Override
	public List<BuildDTO> findAllByUserId(int id) {
		List<Build> allByUserId = buildRepository.findAllByUserId(id);

		return allByUserId.stream()
				.map(build -> new BuildDTO(build.getId(), userService.findUserById(build.getUserId()).getNick(),
						build.getTitle(), build.getText(), build.getPhotoUrl(), build.getData()))
				.collect(Collectors.toList());
	}
}
