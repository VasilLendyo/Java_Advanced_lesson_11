package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import domain.Product;
import utils.ConnectionUtils;

public class ProductDaoImpl implements ProductDao {

	private static String CREATE = "insert into product(name, descrioption, price) values (?,?,?)";
	private static String READ = "select * from product where id=?";
	private static String UPDATE = "update product set name=?, description=?, price=? where id=?";
	private static String DELETE = "delete from product where id=?";
	private static String READ_ALL = "select * from product";

	private Connection connection;
	private PreparedStatement preparedStatement;

	public ProductDaoImpl()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public Product create(Product product) {
		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			product.setId(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
	}

	@Override
	public Product read(Integer id) {

		Product product = null;

		try {
			preparedStatement = connection.prepareStatement(READ);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Integer productId = rs.getInt("id");
			String name = rs.getString("name");
			String description = rs.getString("descrioption");
			Double price = rs.getDouble("price");
			product = new Product(productId, name, description, price);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product update(Product product) {

		try {
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.setInt(5, product.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
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
	public List<Product> readAll() {
		List<Product> productRecords = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer productId = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("descrioption");
				Double price = rs.getDouble("price");
				productRecords.add(new Product(productId, name, description, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productRecords;
	}

}
