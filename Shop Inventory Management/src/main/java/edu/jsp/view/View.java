package edu.jsp.view;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.jsp.controller.Controller;
import edu.jsp.model.Product;
import edu.jsp.model.Shop;

public class View {
	//for loading purpose scanner is make static
	static Scanner  myInput  = new Scanner(System. in );
	static Controller controller = new Controller();
	static Shop shop=new Shop();
	static {
		//Ask shop details for 1st run of application
		//from 2nd run onward check if shop exists,if yes then use existing
		Shop shopExist = controller.isShopExist();
		if(shopExist.getId()!=0) {
			//Shop Exist
			//maintaining only one reference for further operation
			shop=shopExist;
			System.out.println("Shop details : ");
			System.out.println("Id:" +shop.getId());
			System.out.println("Name : "+shop.getShopName());
			System.out.println("Address : "+shop.getAddress());
			System.out.println("GST : " +shop.getGst());
			System.out.println("Contact : "+shop.getContact());
			System.out.println("Owner Name : "+shop.getOwnerName());
		}
		else {
		System.out.println("-----Welcom to shop-----");
		System.out.print("Enter id:");
		shop.setId(myInput.nextInt());
		myInput.nextLine();
		System.out.print("Enter name:");
		shop.setShopName(myInput.nextLine());
		System.out.print("Enter address:");
		shop.setAddress(myInput.nextLine());
		System.out.print("Enter gst no:");
		shop.setGst(myInput.nextLine());
		System.out.print("Enter owner name:");
		shop.setOwnerName(myInput.nextLine());
		System.out.print("Enter contact:");
		shop.setContact(myInput.nextLong());
		myInput.nextLine();
		if(controller.addShop(shop)!=0) {
			System.out.println("shop added\n");
		}
	}}
		
	public static void main(String[] args) {
		
		do {
			System.out.println("Select operation to perform:");
			System.out.println("1.Add product/s \n2.Remove Product \n3.Update Product \n0.Exit");
			System.out.print("Enter digit respective to desired option:");
			byte userChoice = myInput.nextByte();
			myInput.nextLine();
			switch (userChoice) {
			case 1://Add product/s
				List<Product> products=new ArrayList<Product>();
				boolean continueToAdd=true;
				do {
					Product product = new Product();
					System.out.print("Enter product id : ");
					product.setId(myInput.nextInt());
					myInput.nextLine();
					System.out.print("Enter product name : ");
					product.setProductName(myInput.nextLine());
					System.out.print("Enter product price : ");
					product.setPrice(myInput.nextDouble());
					myInput.nextLine();
					System.out.print("Enter product quantity : ");
					int quantity = myInput.nextInt();
					myInput.nextLine();
					product.setQuantity(quantity);
					if(quantity>0) {
						//set availability true
						product.setAvailability(true);
					}
					else {
						//set availability false
						product.setAvailability(false);
					}
					products.add(product);
					System.out.print("Continue to add product ? y/n : ");
					String continueAdd = myInput.nextLine();
					if(continueAdd.equalsIgnoreCase("n")) {
						continueToAdd=false;
					}
				} while (continueToAdd);
				controller.addProduct(shop, products);
				break;
			case 2: 
				ResultSet productResultSet = controller.fetchAllProducts();
				if(productResultSet==null) {
					System.out.println("No product exist,No remove operation can be perform.");
				}else {
					System.out.println("\nAvailable products in shop : ");
					System.out.println("id  |  Product name ");
					try {
						while(productResultSet.next()) {
							System.out.print(productResultSet.getInt(1) + "  | ");
							System.out.println(" " +productResultSet.getString(2));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					myInput.nextLine();		
					System.out.println("Enter product id to remove : ");
					int removeProductId = myInput.nextInt();
					if(controller.removeProduct(removeProductId)==2) {
						System.out.println("Product removed.");
					}else {
						System.out.println("Unable remove product.");}
				}
				break;
			case 3:
					System.out.println("List of all products to update:");
					ResultSet fetchAllProducts = controller.fetchAllProducts();
					if(fetchAllProducts==null) {
						System.out.println("No product exist,No Update operation can be perform.");
					}else {
						
						System.out.printf("| %-5s | %-12s | %-8s | %-10s | %-12s |%n","ID","Name","Price","Quantity","Availability");
						try {
							while(fetchAllProducts.next()) {
								System.out.printf("| %-5s |",fetchAllProducts.getInt(1));
								System.out.printf(" %-12s |",fetchAllProducts.getString(2));
								System.out.printf(" %-8s |",fetchAllProducts.getDouble(3));
								System.out.printf(" %-10s |",fetchAllProducts.getInt(4));
								System.out.printf(" %-12s |",fetchAllProducts.getBoolean(5));
								System.out.println("");
//								System.out.print(fetchAllProducts.getInt(1));
//								System.out.print(fetchAllProducts.getString(2));
//								System.out.print(fetchAllProducts.getDouble(3));
//								System.out.println(fetchAllProducts.getInt(4));
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					System.out.println("Enetr id to update:");
					int pid = myInput.nextInt();
					myInput.nextLine();
					Product product = controller.fetchParicularProduct(pid);
					boolean continueUpdate=true;
					do {
					System.out.println("What to update?\n1.Product Name\n2.Product Price\n3.Product Quantity\n4.Product Availability\n5.Press to Update");
					System.out.print("Enter digit respective to desire option : ");
					byte userchoice = myInput.nextByte();
					myInput.nextLine();
					switch (userchoice) {
					case 1:
						System.out.print("Enter product name to update : ");
						product.setProductName(myInput.nextLine());
						break;
					case 2:
						System.out.println("Enter price to update : ");
						product.setPrice(myInput.nextDouble());
						break;
					case 3:
						System.out.print("Enter Quantity to update : ");
						int updateQuantity = myInput.nextInt();
						product.setQuantity(updateQuantity);
						break;
					case 4:
						System.out.println("Enter Availability to Update : ");
						product.setAvailability(myInput.nextBoolean());
						break;
					case 5:
						controller.updateProduct(product);
						continueUpdate=false;
						break;
					default:
						System.out.println("Invalid choice.");
						break;
					}}while(continueUpdate);}
				break;
			case 0://0.Exit
				System.out.println("-------EXITED---------");
				System.exit(0);
				break;
			default:
				System.out.println("-------INVALID SELECTION---------");
				break;
			}
			
		} while (true);

	}

}
