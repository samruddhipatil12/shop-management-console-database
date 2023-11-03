package product_managment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import db_operation.DbUtil;
import user_managment.User;
import user_managment.UserOption;



public class ProductManagment {
	
	public static void productmanagement () 
	{
	
	Scanner scanner = new Scanner(System.in); // Creating scanner object to get option from user

	boolean canIKeepRunningTheProgram = true; 
	while (canIKeepRunningTheProgram == true) 
	{ // Checking if used asked to exiting, if option set as 5

		System.out.println("**** Welcome to User Management *****");
		System.out.println("\n");
		System.out.println("What would you like to do ?");
		System.out.println("1. Add Produtc");
		System.out.println("2. Edit Product");
		System.out.println("3. Delete Product");
		System.out.println("4. Search Product");
		System.out.println("5. Quit");

		int optionSelectedByUser = scanner.nextInt();

		if (optionSelectedByUser == ProductOption.Quit) {
			System.out.println("!!! Program closed");
			canIKeepRunningTheProgram = false;

		} else if (optionSelectedByUser == ProductOption.Add_Product) {
			addUser();
			System.out.println("\n");
		} else if (optionSelectedByUser == ProductOption.Search_Product) {
			System.out.print("Enter Product Name to search: ");
			scanner.nextLine(); // Consume the newline character left from previous input
			String sn = scanner.nextLine();
			searchUser(sn);
			System.out.println("\n");
		} else if (optionSelectedByUser == ProductOption.Delete_Product) {
			System.out.print("Enter Product Name to delete: ");
			scanner.nextLine(); // Consume the newline character left from previous input
			String deleteUserName = scanner.nextLine();
			deleteUser(deleteUserName);
			System.out.println("\n");
		} else if (optionSelectedByUser == ProductOption.Edit_Product) {
			System.out.print("Enter Product Name to edit: ");
			scanner.nextLine(); // Consume the newline character left from previous input
			String editUserName = scanner.nextLine();
			editUser(editUserName);
			System.out.println("\n");
		}

	}
	System.out.println("\n");
}


public static void addUser() {
	Scanner scanner = new Scanner(System.in);

	Product productObject = new Product(); 

	System.out.print("Product Name: ");
	productObject.name = scanner.nextLine();

	System.out.print("Product ID : ");
	productObject.id = scanner.nextLine();

	System.out.print("Product Category : ");
	productObject.category = scanner.nextLine();

	System.out.print(" Product Quantity : ");
	productObject.quantity = scanner.nextLine();

	System.out.print(" Product Price: ");
	productObject.price = scanner.nextLine();

	System.out.println("Product Name: " + productObject.name);
	System.out.println("Product ID : " + productObject.id);
	System.out.println("Product Category: " + productObject.category);
	System.out.println("Product Quantity  : " +productObject.quantity);
	System.out.println("Product Price: " + productObject.price);

	String query = "INSERT INTO Product(Name,ID,Category,Quantity,Price) VALUES ('"
			+ productObject.name + "', '" + productObject.id + "', '" + productObject.category + "','"
			+ productObject.quantity + "','" + productObject.price + "')";

	DbUtil.executeQuery(query);

}

public static void searchUser(String name) {

	String query = "select * from product where name='" + name + "' ";

	ResultSet rs = DbUtil.executeQueryGetResult(query);

	try {
		while (rs.next()) { // For-Each
			if (rs.getString("name").equalsIgnoreCase(name)) {
				System.out.println("name: " + rs.getString("name"));
				System.out.println("id: " + rs.getString("id"));
				System.out.println("category: " + rs.getString("category"));
				System.out.println("price: " + rs.getString("price"));
				// break;
				return;
			}
		}
	} catch (Exception e) {
		System.out.println("Product not found.");
	}

}

// ******* Delete Function *****
public static void deleteUser(String name) {
	String query = "delete from product where name='" + name + "' ";
	DbUtil.executeQuery(query);
}

// ******* Edit  Function *****
public static void editUser(String name) {
	
	String query = "select * from product where name='" + name + "' ";
	ResultSet rs = DbUtil.executeQueryGetResult(query);
	
	try {
		while (rs.next()) { // For-Each
			if (rs.getString("name").equalsIgnoreCase(name)) {
				Scanner scanner = new Scanner(System.in);
				
				Product productObject = new Product();
				
				System.out.println("Editing product: " + productObject.name);

				System.out.print("New product Name: ");
				 productObject.name = scanner.nextLine();

				System.out.print("New product id: ");
				productObject.id = scanner.nextLine();

				System.out.print("New product category: ");
				productObject.category = scanner.nextLine();

				System.out.print("New product quantity: ");
				productObject.quantity = scanner.nextLine();

				System.out.print("New product price: ");
				productObject.price = scanner.nextLine();
				
				String updateQuery = "update product set "
						+ "name='"+ productObject.name+"',  id = '"+ productObject.id+"', "
								+ "category='"+ productObject.category+"', quantity='"+ productObject.quantity+"', "
										+ "price='"+ productObject.price+"' where productid='"+rs.getString("productid")+"'";
				
				DbUtil.executeQuery(updateQuery);

				System.out.println("product information updated.");

				return;
			}
		}
	} catch (Exception e) {
		System.out.println("User not found.");
	}

	System.out.println("User not found.");
}

}

