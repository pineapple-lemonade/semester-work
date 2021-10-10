package ru.itis.ruzavin.repositories;

import lombok.AllArgsConstructor;
import ru.itis.ruzavin.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

	//language=SQL
	private final static String SQL_SAVE_USER = "insert into users (nickname, email, login, password) values (?, ?, ?, ?)";

	//language=SQL
	private final static String SQL_FIND_USER_BY_LOGIN = "select * from users where login = ?";

	private final DataSource dataSource;

	private final Function<ResultSet, User> rowMapper = row -> {
		try {
			Integer id = row.getInt("id");
			String nick = row.getString("nickname");
			String email = row.getString("email");
			String login = row.getString("login");
			String password = row.getString("password");
			return new User(id, nick, email, login, password);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	};

	@Override
	public void save(User user) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
			preparedStatement.setString(1, user.getNick());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public Optional<User> findUserByLogin(String login) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
			preparedStatement.setString(1,login);
			ResultSet row = preparedStatement.executeQuery();
			if(row.next()){
				User user = rowMapper.apply(row);
				return Optional.of(user);
			} else {
				return Optional.empty();
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}
}
