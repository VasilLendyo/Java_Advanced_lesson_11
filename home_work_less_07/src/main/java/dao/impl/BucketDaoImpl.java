package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.BucketDao;
import domain.Bucket;
import utils.ConnectionUtils;

public class BucketDaoImpl implements BucketDao {

	private static String CREATE = "insert into bucket(user_id, product_id, purchase_date) values (?,?,?)";
	private static String READ = "select * from bucket where id=?";
	private static String UPDATE = "update bucket set user_id=?, product_id=?, purchase_date=? where id=?";
	private static String DELETE = "delete from bucket where id=?";
	private static String READ_ALL = "select * from bucket";

	private Connection connection;
	private PreparedStatement preparedStatement;

	public BucketDaoImpl() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public Bucket create(Bucket bucket) {
		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, bucket.getUserId());
			preparedStatement.setInt(2, bucket.getProductId());
			preparedStatement.setDate(3, bucket.getPurchaseDate());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			bucket.setId(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bucket;
	}

	@Override
	public Bucket read(Integer id) {

		Bucket bucket = null;

		try {
			preparedStatement = connection.prepareStatement(READ);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Integer bucketId = rs.getInt("id");
			Integer userId = rs.getInt("user_id");
			Integer productId = rs.getInt("product_id");
			Date purchaseDate = rs.getDate("purchase_date");
			bucket = new Bucket(bucketId, userId, productId, purchaseDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bucket;
	}

	@Override
	public Bucket update(Bucket bucket) {

		try {
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setInt(1, bucket.getUserId());
			preparedStatement.setInt(2, bucket.getProductId());
			preparedStatement.setDate(3, bucket.getPurchaseDate());
			preparedStatement.setInt(5, bucket.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bucket;
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
	public List<Bucket> readAll() {
		List<Bucket> bucketRecords = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer bucketId = rs.getInt("id");
				Integer userId = rs.getInt("user_id");
				Integer productId = rs.getInt("product_id");
				Date purchaseDate = rs.getDate("purchase_date");
				new Bucket(bucketId, userId, productId, purchaseDate);
				bucketRecords.add(new Bucket(bucketId, userId, productId, purchaseDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bucketRecords;
	}

}
