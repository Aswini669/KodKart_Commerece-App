package com.kodewala.kodkart;

import java.sql.Connection;
import java.util.Scanner;

import com.kodewala.kodkart.connections.DBConnection;
import com.kodewala.kodkart.entities.Users;
import com.kodewala.kodkart.serviceimplementations.AdminServiceImplementation;
import com.kodewala.kodkart.serviceimplementations.UserMenuServiceImplementation;
import com.kodewala.kodkart.serviceimplementations.UserServiceImplementation;
import com.kodewala.kodkart.services.AdminService;
import com.kodewala.kodkart.services.UserMenuService;
import com.kodewala.kodkart.services.UserService;

public class App {

    public static void main(String[] args) {

        DBConnection dbc = new DBConnection();
        Connection con = dbc.getConnection();

        UserService userv = new UserServiceImplementation();
        AdminService admin = new AdminServiceImplementation();
        UserMenuService umserv = new UserMenuServiceImplementation();

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome To Kodkart App");

        while (true) {

        	System.out.println("==============================================");
            System.out.println("\n---- KODKART HOME ----");
            System.out.println("==============================================");
            System.out.println("+---------------------------+");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Close KodKart");
            System.out.println("+---------------------------+");
            System.out.print("Choose Option: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

            // ---------------- REGISTER ----------------
            case 1:
                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Email: ");
                String email = sc.nextLine();

                System.out.print("Enter Phone: ");
                String phone = sc.nextLine();

                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                Users user = new Users(name, email, phone, password);

                if (userv.register(con, user)) {
                    System.out.println("Registration Successful.");
                } else {
                    System.out.println("Email already exists.");
                }
                break;

            // ---------------- LOGIN ----------------
            case 2:
                System.out.print("Enter Email: ");
                String loginEmail = sc.nextLine();

                System.out.print("Enter Password: ");
                String loginPassword = sc.nextLine();

                int userId = userv.login(con, loginEmail, loginPassword);

                if (userId <= 0) {
                    System.out.println("Invalid Credentials. Try Again.");
                    break;
                }

                System.out.println("Login Successful!");

                // ------------------------------------------------
                // ADMIN LOGIN SECTION
                // ------------------------------------------------
                if (loginEmail.equals("admin@gmail.com")) {

                    while (true) {
                        System.out.println("\n----- ADMIN HOME -----");
                        int choice = admin.adminHome();

                        if (choice == 1) {
                            admin.addProduct(con);
                        } else if (choice == 2) {
                            admin.updateProduct(con);
                        } else if (choice == 3) {
                            admin.deleteProduct(con);
                        } else if (choice == 4) {
                            admin.viewProduct(con);
                        }else if(choice == 5) {
                        	admin.viewAllOrders(con);
                        }
                        else if (choice == 6) {
                            System.out.println("Admin Logged Out.");
                            break;        // 🔥 Return to main HOME menu
                        } else {
                            System.out.println("Invalid choice!");
                        }
                    }

                } else {

                    // ------------------------------------------------
                    // USER LOGIN SECTION
                    // ------------------------------------------------
                    while (true) {
                        int choice = umserv.userHome();

                        if (choice == 1) {
                            umserv.viewProducts(con);
                        } else if (choice == 2) {
                            umserv.searchProduct(con);
                        } else if (choice == 3) {
                            umserv.addToCart(con, userId);
                        } else if (choice == 4) {
                            int cartChoice = umserv.viewCart(con, userId);

                            if (cartChoice == 1) {
                                umserv.placeOrder(con, userId);
                            } else if (cartChoice == 2) {
                                umserv.removeItemFromCart(con, userId);
                            }
                        } else if (choice == 5) {
                            umserv.fetchMyOrders(con, userId);
                        } else if (choice == 6) {
                        	System.out.println("\nThank you for using KodKart!");
                            System.out.println("You are now logged out.");
                            System.out.println("---------*--------");
                            break;        // 🔥 Return to main HOME menu
                        } else {
                            System.out.println("Invalid choice!");
                        }
                    }
                }
                break;

            // ---------------- CLOSE APP ----------------
            case 3:
                System.out.println("KodKart Closed.");
                System.exit(0);

            default:
                System.out.println("Invalid Choice, Try Again.");
            }
        }
    }
}
