package ru.itis.ruzavin.repositories;

import lombok.AllArgsConstructor;
import ru.itis.ruzavin.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository{

	//language=SQL
	private final static String SQL_SAVE_USER = "insert into users (nickname, email, login, password) values (?, ?, ?, ?)";

	private final DataSource dataSource;

	@Override
	public void save(User user) {
		try(Connection connection = dataSource.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
			preparedStatement.setString(1,user.getNick());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setString(3,user.getLogin());
			preparedStatement.setString(4,user.getPassword());
			preparedStatement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}
}
