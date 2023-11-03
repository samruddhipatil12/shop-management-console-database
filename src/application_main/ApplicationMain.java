package application_main;

import java.io.IOException;
import java.util.Scanner;

import product_managment.ProductManagment;
import user_managment.UserManagment;

public class ApplicationMain {
	
		public static void main(String[] args) throws IOException {
			Scanner scanner = new Scanner(System.in); // Creating scanner object to get option from user

			boolean canIKeepRunningTheProgram = true;

			
			System.out.println("**** Welcome to Shop Management *****");
			
			System.out.println("\n");
			System.out.println("Enter login name : ");
			String loginName = scanner.nextLine();
			System.out.println(" Enter the password :");
			String password = scanner.nextLine();

			if(!UserManagment.validateUserAndPassword(loginName, password)) // FALSE => TRUE
			{
				System.out.println("!!!!!!!! Login failed. Closing the application");
				return;
			}

			while (canIKeepRunningTheProgram == true) {

				System.out.println("**** Welcome to Shop Management *****");
				System.out.println("\n");
				
				System.out.println("What would you like to do ?");
				System.out.println("1. User Management");
				System.out.println("2. Product Management");
				System.out.println("5. Quit");

				int optionSelectedByUser = scanner.nextInt();

				if (optionSelectedByUser == 1) {				
					UserManagment.userManagement();
				} else if (optionSelectedByUser == 2) {
					ProductManagment.productmanagement();
				} else if (optionSelectedByUser == 5) {
					break;
				}

			}
		
	}

}
