package ru.itis.ruzavin.repositories;

import lombok.AllArgsConstructor;
import ru.itis.ruzavin.models.BuildComment;
import ru.itis.ruzavin.models.GuideComment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
public class BuildCommentRepositoryJdbcImpl implements BuildCommentRepository{

	private final DataSource dataSource;

	private final static Function<ResultSet, BuildComment> rowMapper = (rowMapper1 -> {
		try {
			int id = rowMapper1.getInt("id");
			int userId = rowMapper1.getInt("user_id");
			int guideId = rowMapper1.getInt("build_id");
			String text = rowMapper1.getString("text");
			return new BuildComment(id, userId, guideId, text);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	});

	//language=SQL
	private static final String SQL_FIND_BY_ID = "SELECT * FROM comment_build WHERE id = ?";

	//language=SQL
	private static final String SQL_FIND_ALL = "SELECT * FROM comment_build";

	//language=SQL
	private static final String SQL_SAVE = "INSERT INTO comment_build (user_id, build_id, text) VALUES (?, ?, ?)";

	//language=SQL
	private static final String SQL_FIND_ALL_BY_GUIDE_ID = "SELECT * FROM comment_build WHERE build_id = ?";

	@Override
	public BuildComment findById(int id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				return rowMapper.apply(resultSet);
			} else {
				return null;
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<BuildComment> findAll() {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
			ResultSet resultSet = statement.executeQuery();
			List<BuildComment> list = new ArrayList<>();
			while (resultSet.next()){
				list.add(rowMapper.apply(resultSet));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public void save(BuildComment buildComment) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
			statement.setInt(1, buildComment.getUserId());
			statement.setInt(2, buildComment.getBuildId());
			statement.setString(3, buildComment.getText());
			statement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<BuildComment> findAllByGuideId(int id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_BY_GUIDE_ID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			List<BuildComment> list = new ArrayList<>();
			while (resultSet.next()){
				list.add(rowMapper.apply(resultSet));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}
}
