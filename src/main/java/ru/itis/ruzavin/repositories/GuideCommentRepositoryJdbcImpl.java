package ru.itis.ruzavin.repositories;

import lombok.AllArgsConstructor;
import ru.itis.ruzavin.models.GuideComment;

import javax.sql.DataSource;
import javax.swing.tree.RowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
public class GuideCommentRepositoryJdbcImpl implements GuideCommentRepository {

	private final DataSource dataSource;

	private final static Function<ResultSet, GuideComment> rowMapper = (rowMapper1 -> {
		try {
			int id = rowMapper1.getInt("id");
			int userId = rowMapper1.getInt("user_id");
			int guideId = rowMapper1.getInt("guide_id");
			String text = rowMapper1.getString("text");
			return new GuideComment(id, userId, guideId, text);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	});

	//language=SQL
	private static final String SQL_FIND_BY_ID = "SELECT * FROM comment_guide WHERE id = ?";

	//language=SQL
	private static final String SQL_FIND_ALL = "SELECT * FROM comment_guide";

	//language=SQL
	private static final String SQL_SAVE = "INSERT INTO comment_guide (user_id, guide_id, text) VALUES (?, ?, ?)";

	//language=SQL
	private static final String SQL_FIND_ALL_BY_GUIDE_ID = "SELECT * FROM comment_guide WHERE guide_id = ?";

	@Override
	public GuideComment findById(int id) {
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
	public List<GuideComment> findAll() {
		try (Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
			ResultSet resultSet = statement.executeQuery();
			List<GuideComment> list = new ArrayList<>();
			while (resultSet.next()){
				list.add(rowMapper.apply(resultSet));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public void save(GuideComment guideComment) {
		try (Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
			statement.setInt(1, guideComment.getUserId());
			statement.setInt(2, guideComment.getGuideId());
			statement.setString(3, guideComment.getText());
			statement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<GuideComment> findAllByGuideId(int id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_BY_GUIDE_ID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			List<GuideComment> list = new ArrayList<>();
			while (resultSet.next()){
				list.add(rowMapper.apply(resultSet));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}
}
