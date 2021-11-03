package ru.itis.ruzavin.services;

import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.Guide;
import ru.itis.ruzavin.repositories.GuideRepository;
import ru.itis.ruzavin.repositories.GuideRepositoryJdbcImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class GuideServiceImpl implements GuideService{

	private final GuideRepository guideRepository;

	private final UserService userService;

	public GuideServiceImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		this.guideRepository = new GuideRepositoryJdbcImpl(new SimpleDataSource(properties));
		this.userService = new UserServiceImpl();
	}

	@Override
	public void save(Guide guide) {
		guideRepository.save(guide);
	}

	@Override
	public List<GuideDTO> findAll() {
		List<Guide> all = guideRepository.findAll();

		return all.stream().map(guide -> new GuideDTO(guide.getId(),
				userService.findUserById(guide.getUserId()).getNick(), guide.getTitle(), guide.getText(),
				guide.getPhotoUrl(), guide.getData())).collect(Collectors.toList());
	}

	@Override
	public List<GuideDTO> findAllByTitle(String title) {
		List<Guide> allByTitle = guideRepository.findAllByTitle(title);

		return allByTitle.stream().map(guide -> new GuideDTO(guide.getId(),
						userService.findUserById(guide.getId()).getNick(),
						guide.getTitle(), guide.getText(), guide.getPhotoUrl(), guide.getData()))
				.collect(Collectors.toList());
	}

	@Override
	public GuideDTO findById(int id) {
		Guide guideById = guideRepository.findById(id).get();
		return GuideDTO.builder()
				.data(guideById.getData())
				.id(guideById.getId())
				.photoUrl(guideById.getPhotoUrl())
				.text(guideById.getText())
				.title(guideById.getTitle())
				.userNick(userService.findUserById(guideById.getUserId()).getNick())
				.build();
	}

	@Override
	public List<GuideDTO> findAllByUserId(int id) {
		List<Guide> allByUserId = guideRepository.findAllByUserId(id);

		return allByUserId.stream()
				.map(guide -> new GuideDTO(guide.getId(), userService.findUserById(guide.getUserId()).getNick(),
						guide.getTitle(), guide.getText(), guide.getPhotoUrl(), guide.getData()))
				.collect(Collectors.toList());
	}

}
