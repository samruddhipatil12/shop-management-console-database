package user_managment;

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

public class UserManagment {
	

	public static void userManagement() throws IOException {

		Scanner scanner = new Scanner(System.in); // Creating scanner object to get option from user

		boolean canIKeepRunningTheProgram = true; // created variable to decide should we continue running program or
													// not

		while (canIKeepRunningTheProgram == true) { // Checking if used asked to exiting, if option set as 5

			System.out.println("**** Welcome to User Management *****");
			System.out.println("\n");
			System.out.println("What would you like to do ?");
			System.out.println("1. Add User");
			System.out.println("2. Edit User");
			System.out.println("3. Delete User");
			System.out.println("4. Search User");
			System.out.println("5. Quit");

			int optionSelectedByUser = scanner.nextInt();

			if (optionSelectedByUser == UserOption.QUIT) {
				System.out.println("!!! Program closed");
				canIKeepRunningTheProgram = false;

			} else if (optionSelectedByUser == UserOption.ADD_USER) {
				addUser();
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOption.SEARCH_USER) {
				System.out.print("Enter User Name to search: ");
				scanner.nextLine(); // Consume the newline character left from previous input
				String sn = scanner.nextLine();
				searchUser(sn);
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOption.DELETE_USER) {
				System.out.print("Enter User Name to delete: ");
				scanner.nextLine(); // Consume the newline character left from previous input
				String deleteUserName = scanner.nextLine();
				deleteUser(deleteUserName);
				System.out.println("\n");
			} else if (optionSelectedByUser == UserOption.EDIT_USER) {
				System.out.print("Enter User Name to edit: ");
				scanner.nextLine(); // Consume the newline character left from previous input
				String editUserName = scanner.nextLine();
				editUser(editUserName);
				System.out.println("\n");
			}

		}
		System.out.println("\n");
	}

	// ******* Add User Function *****
	public static void addUser() {
		Scanner scanner = new Scanner(System.in);

		User userObject = new User(); // User object

		System.out.print("User Name: ");
		userObject.userName = scanner.nextLine();

		System.out.print("Login Name: ");
		userObject.loginName = scanner.nextLine();

		System.out.print("Password: ");
		userObject.password = scanner.nextLine();

		System.out.print("confirmPassword : ");
		userObject.confirmPassword = scanner.nextLine();

		System.out.print("User Role: ");
		userObject.userRole = scanner.nextLine();

		System.out.println("User Name: " + userObject.userName);
		System.out.println("Login Name: " + userObject.loginName);
		System.out.println("Password: " + userObject.password);
		System.out.println("confirmPassword : " + userObject.confirmPassword);
		System.out.println("User Role: " + userObject.userRole);

		String query = "INSERT INTO User(UserName, LoginName, Password, confirmPassword, userRole) VALUES ('"
				+ userObject.userName + "', '" + userObject.loginName + "', '" + userObject.password + "','"
				+ userObject.confirmPassword + "','" + userObject.userRole + "')";

		DbUtil.executeQuery(query);

	}

	public static void searchUser(String userName) {

		String query = "select * from User where UserName='" + userName + "' ";

		ResultSet rs = DbUtil.executeQueryGetResult(query);

		try {
			while (rs.next()) { // For-Each
				if (rs.getString("username").equalsIgnoreCase(userName)) {
					System.out.println("User Name: " + rs.getString("username"));
					System.out.println("Login Name: " + rs.getString("loginname"));
					System.out.println("Password: " + rs.getString("password"));
					System.out.println("userRole: " + rs.getString("userRole"));
					// break;
					return;
				}
			}
		} catch (Exception e) {
			System.out.println("User not found.");
		}

	}

	// ******* Delete User Function *****
	public static void deleteUser(String userName) {
		String query = "delete from User where UserName='" + userName + "' ";
		DbUtil.executeQuery(query);
	}

	// ******* Edit User Function *****
	public static void editUser(String userName) {
		
		String query = "select * from User where UserName='" + userName + "' ";
		ResultSet rs = DbUtil.executeQueryGetResult(query);
		
		try {
			while (rs.next()) { // For-Each
				if (rs.getString("UserName").equalsIgnoreCase(userName)) {
					Scanner scanner = new Scanner(System.in);
					User user = new User();
					
					System.out.println("Editing user: " + user.userName);

					System.out.print("New User Name: ");
					user.userName = scanner.nextLine();

					System.out.print("New Login Name: ");
					user.loginName = scanner.nextLine();

					System.out.print("New Password: ");
					user.password = scanner.nextLine();

					System.out.print("New confirmPassword Password: ");
					user.confirmPassword = scanner.nextLine();

					System.out.print("New User Role: ");
					user.userRole = scanner.nextLine();
					
					String updateQuery = "update users set "
							+ "UserName='"+user.userName+"', LoginName = '"+user.loginName+"', "
									+ "Password='"+user.password+"', confirmPassword='"+user.confirmPassword+"', "
											+ "userRole='"+user.userRole+"' where userid='"+rs.getString("userid")+"'";
					
					DbUtil.executeQuery(updateQuery);

					System.out.println("User information updated.");

					return;
				}
			}
		} catch (Exception e) {
			System.out.println("User not found.");
		}

		System.out.println("User not found.");
	}

	public static boolean validateUserAndPassword(String loginName, String password) throws IOException {

		String query = "select * from User where loginName='" + loginName + "' and password='" + password + "' ";

		ResultSet rs = DbUtil.executeQueryGetResult(query);

		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}



