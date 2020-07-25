package service.impl;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import domain.Product;
import service.ProductService;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;

	public ProductServiceImpl() {
		try {
			productDao = new ProductDaoImpl();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Product create(Product t) {
		return productDao.create(t);
	}

	@Override
	public Product read(Integer id) {
		return productDao.read(id);
	}

	@Override
	public Product update(Product t) {
		return productDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		productDao.delete(id);
	}

	@Override
	public List<Product> readAll() {
		return productDao.readAll();
	}

}
