package edu.jsp.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.postgresql.Driver;
import edu.jsp.model.Product;
import edu.jsp.model.Shop;

public class Controller {
	
	static String dbUrl="jdbc:postgresql://localhost:5432/shop";
	static Connection connection=null;
	static {
		try {
			Driver pgDriver = new Driver();
			DriverManager.registerDriver(pgDriver);
			FileInputStream fis = new FileInputStream("dbConfig.properties");
			Properties properties = new Properties();
			properties.load(fis);
			connection = DriverManager.getConnection(dbUrl, properties);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int addShop(Shop shop) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO shop VALUES(?,?,?,?,?,?)");
			prepareStatement.setInt(1,shop.getId());
			prepareStatement.setString(2, shop.getShopName());
			prepareStatement.setString(3, shop.getAddress());
			prepareStatement.setString(4, shop.getGst());
			prepareStatement.setLong(5, shop.getContact());
			prepareStatement.setString(6, shop.getOwnerName());
			return prepareStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public Shop isShopExist() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM shop;"+ "");
			Shop isShopExist = new Shop();
			while(resultSet.next()) {
				isShopExist.setId(resultSet.getInt(1));
				isShopExist.setShopName(resultSet.getString(2));
				isShopExist.setAddress(resultSet.getString(3));
				isShopExist.setGst(resultSet.getString(4));
				isShopExist.setContact(resultSet.getLong(5));
				isShopExist.setOwnerName(resultSet.getString(6));
			}
			return isShopExist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void addProduct(Shop shop,List<Product> products) {
		for (Product product : products) {
			try {
				//insert product into product table
				PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?)");
				prepareStatement.setInt(1,product.getId() );
				prepareStatement.setString(2, product.getProductName());
				prepareStatement.setDouble(3, product.getPrice());
				prepareStatement.setInt(4, product.getQuantity());
				prepareStatement.setBoolean(5, product.isAvailability());
				prepareStatement.executeUpdate();
				//insert into shop-product table
				PreparedStatement prepareStatement2 = connection.prepareStatement("INSERT INTO shop_product VALUES(?,?)");
				prepareStatement2.setInt(1, shop.getId());
				prepareStatement2.setInt(2, product.getId());
				prepareStatement2.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet fetchAllProducts() {
		try {
			Statement statement = connection.createStatement();
			ResultSet products = statement.executeQuery("SELECT * FROM product;");
			return checkProductResultSet(products);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int removeProduct(int productId) {
		

		try {
			PreparedStatement removePFromProduct = connection.prepareStatement("DELETE FROM product WHERE product_id=?;");
			removePFromProduct.setInt(1, productId);
			PreparedStatement removeFromShopProduct = connection.prepareStatement("DELETE FROM shop_product WHERE product_id=?;");
			removeFromShopProduct.setInt(1, productId);
			return removePFromProduct.executeUpdate()+removeFromShopProduct.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return 0;
	}
	public ResultSet checkProductResultSet(ResultSet resultSet) {
		Statement statement;
		try {
			statement = connection.createStatement();
			byte count=0;
			while(resultSet.next()) {
				
				if(++count>0) {
					break;
				}
			}
			if(count==1) {
				return statement.executeQuery("SELECT * FROM product;");
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Product fetchParicularProduct(int id) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM product WHERE product_id = ?;");
			prepareStatement.setInt(1, id);
			ResultSet productResultSet = checkProductResultSet(prepareStatement.executeQuery());
			Product product = new Product();
			while(productResultSet.next()) {
				if(productResultSet.getInt(1)==id){
				product.setId(productResultSet.getInt(1));
				product.setProductName(productResultSet.getString(2));
				product.setPrice(productResultSet.getDouble(3));
				product.setQuantity(productResultSet.getInt(4));
				product.setAvailability(productResultSet.getBoolean(5));
				}}
			return product;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public int updateProduct(Product product) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("UPDATE product SET product_name=?,price=?,quantity=?,availability=? WHERE product_id=?");
			prepareStatement.setString(1,product.getProductName());
			prepareStatement.setDouble(2, product.getPrice());
			prepareStatement.setInt(3, product.getQuantity());
			prepareStatement.setBoolean(4, product.isAvailability());
			prepareStatement.setInt(5, product.getId());
			return prepareStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public void closeConnection() {
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
