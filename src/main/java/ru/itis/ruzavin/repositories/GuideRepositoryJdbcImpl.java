package ru.itis.ruzavin.repositories;

import lombok.AllArgsConstructor;
import ru.itis.ruzavin.models.Guide;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class GuideRepositoryJdbcImpl implements GuideRepository {

	private final DataSource dataSource;

	//language=SQL
	private static final String SQL_SAVE_GUIDE = "INSERT INTO guides(user_id, title, text, photo_url, data) VALUES (?, ?, ?, ?, ?)";

	//language=SQL
	private static final String SQL_FIND_ALL = "SELECT * FROM guides";

	//language=SQL
	private static final String SQL_FIND_BY_ID = "SELECT * FROM guides WHERE id = ?";

	//language=SQL
	private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM guides WHERE user_id = ?";

	//language=SQL
	private static final String SQL_FIND_BY_TITLE = "SELECT * FROM guides WHERE title ILIKE ?";

	private final Function<ResultSet, Guide> guideRowMapper = (row) -> {
		try {
			int id = row.getInt("id");
			int userId = row.getInt("user_id");
			String title = row.getString("title");
			String text = row.getString("text");
			String photoUrl = row.getString("photo_url");
			String data = row.getString("data");
			return new Guide(id, userId, title, text, photoUrl, data);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	};

	@Override
	public void save(Guide guide) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_SAVE_GUIDE)) {
			statement.setInt(1, guide.getUserId());
			statement.setString(2, guide.getTitle());
			statement.setString(3, guide.getText());
			statement.setString(4, guide.getPhotoUrl());
			statement.setString(5, guide.getData());
			statement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<Guide> findAll() {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
			ResultSet resultSet = statement.executeQuery();
			List<Guide> list = new ArrayList<>();
			while (resultSet.next()) {
				list.add(guideRowMapper.apply(resultSet));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public Optional<Guide> findById(int id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
			statement.setInt(1, id);
			ResultSet row = statement.executeQuery();
			if (row.next()){
				Guide guide = guideRowMapper.apply(row);
				return Optional.of(guide);
			} else {
				return Optional.empty();
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<Guide> findAllByUserId(int userId) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {
			statement.setInt(1, userId);
			List<Guide> guides = new ArrayList<>();
			ResultSet row = statement.executeQuery();
			while (row.next()){
				guides.add(guideRowMapper.apply(row));
			}
			return guides;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<Guide> findAllByTitle(String title) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_TITLE)) {
			statement.setString(1, "%" + title + "%");
			List<Guide> guides = new ArrayList<>();
			ResultSet row = statement.executeQuery();
			while (row.next()){
				guides.add(guideRowMapper.apply(row));
			}
			return guides;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public void delete(int id) {

	}
}
