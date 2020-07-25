package service;

import java.util.Map;

import domain.Product;
import shared.AbstractCRUD;

public interface ProductService extends AbstractCRUD<Product>{
	public Map<Integer, Product> readAllMap(); 
}
