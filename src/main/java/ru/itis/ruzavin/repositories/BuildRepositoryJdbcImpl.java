package ru.itis.ruzavin.repositories;

import lombok.AllArgsConstructor;
import ru.itis.ruzavin.models.Build;
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
public class BuildRepositoryJdbcImpl implements BuildRepository{

	private final DataSource dataSource;

	//language=SQL
	private static final String SQL_SAVE_BUILD = "INSERT INTO builds(user_id, title, text, photo_url, data) VALUES (?, ?, ?, ?, ?)";

	//language=SQL
	private static final String SQL_FIND_ALL = "SELECT * FROM builds";

	//language=SQL
	private static final String SQL_FIND_BY_ID = "SELECT * FROM builds WHERE id = ?";

	//language=SQL
	private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM builds WHERE user_id = ?";

	//language=SQL
	private static final String SQL_FIND_BY_TITLE = "SELECT * FROM builds WHERE title ILIKE ?";

	private final Function<ResultSet, Build> buildRowMapper = (row) -> {
		try {
			int id = row.getInt("id");
			int userId = row.getInt("user_id");
			String title = row.getString("title");
			String text = row.getString("text");
			String photoUrl = row.getString("photo_url");
			String data = row.getString("data");
			return new Build(id, userId, title, text, photoUrl, data);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	};


	@Override
	public void save(Build build) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_SAVE_BUILD)) {
			statement.setInt(1, build.getUserId());
			statement.setString(2, build.getTitle());
			statement.setString(3, build.getText());
			statement.setString(4, build.getPhotoUrl());
			statement.setString(5, build.getData());
			statement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<Build> findAll() {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
			ResultSet resultSet = statement.executeQuery();
			List<Build> list = new ArrayList<>();
			while (resultSet.next()) {
				list.add(buildRowMapper.apply(resultSet));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public Optional<Build> findById(int id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
			statement.setInt(1, id);
			ResultSet row = statement.executeQuery();
			if (row.next()){
				Build build = buildRowMapper.apply(row);
				return Optional.of(build);
			} else {
				return Optional.empty();
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<Build> findAllByUserId(int userId) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID)) {
			statement.setInt(1, userId);
			List<Build> build = new ArrayList<>();
			ResultSet row = statement.executeQuery();
			while (row.next()){
				build.add(buildRowMapper.apply(row));
			}
			return build;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<Build> findAllByTitle(String title) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_TITLE)) {
			statement.setString(1, "%" + title + "%");
			List<Build> builds = new ArrayList<>();
			ResultSet row = statement.executeQuery();
			while (row.next()){
				builds.add(buildRowMapper.apply(row));
			}
			return builds;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public void delete(int id) {

	}
}
