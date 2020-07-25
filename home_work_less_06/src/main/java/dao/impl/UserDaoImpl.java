package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.UserDao;
import domain.User;
import utils.ConnectionUtils;

public class UserDaoImpl implements UserDao {

	private static String CREATE = "insert into user(email, first_name, last_name, role, password) values (?,?,?,?,?)";
	private static String READ = "select * from user where id=?";
	private static String READ_BY_EMAIL = "select * from user where email=?";
	private static String UPDATE = "update user set email=?, first_name=?, last_name=?, role=?, password=? where id=?";
	private static String DELETE = "delete from user where id=?";
	private static String READ_ALL = "select * from user";

	private static Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
	
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
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt(1));
		} catch (SQLException e) {
			LOGGER.error(e);
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
			String password = rs.getString("password");
			user = new User(userId, email, firstName, lastName, role, password);
		} catch (SQLException e) {
			LOGGER.error(e);
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
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setInt(6, user.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			LOGGER.error(e);
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
			LOGGER.error(e);
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
				String password = rs.getString("password");
				userRecords.add(new User(userId, email, firstName, lastName, role, password));
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}

		return userRecords;
	}

	@Override
	public User getUserByEmail(String email) {

		User user = null;

		try {
			preparedStatement = connection.prepareStatement(READ_BY_EMAIL);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Integer userId = rs.getInt("id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String role = rs.getString("role");
			String password = rs.getString("password");
			user = new User(userId, email, firstName, lastName, role, password);
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return user;
	}

}
