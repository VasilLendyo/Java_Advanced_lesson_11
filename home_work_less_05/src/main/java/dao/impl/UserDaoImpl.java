package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.UserDao;
import domain.User;
import utils.ConnectionUtils;

public class UserDaoImpl implements UserDao {

	private static String CREATE = "insert into user(email, first_name, last_name, role) values (?,?,?,?)";
	private static String READ = "select * from user where id=?";
	private static String UPDATE = "update user set email=?, first_name=?, last_name=?, role=? where id=?";
	private static String DELETE = "delete from user where id=?";
	private static String READ_ALL = "select * from user";

	private Connection connection;
	private PreparedStatement preparedStatement;

	public UserDaoImpl() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public User create(User user) {
		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User read(Integer id) {

		User user = null;

		try {
			preparedStatement = connection.prepareStatement(READ);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Integer userId = rs.getInt("id");
			String email = rs.getString("email");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String role = rs.getString("role");
			user = new User(userId, email, firstName, lastName, role);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User update(User user) {

		try {
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public void delete(Integer id) {
		try {
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> readAll() {
		List<User> userRecords = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer userId = rs.getInt("id");
				String email = rs.getString("email");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String role = rs.getString("role");
				userRecords.add(new User(userId, email, firstName, lastName, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userRecords;
	}

}
