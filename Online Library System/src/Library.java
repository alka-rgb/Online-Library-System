package org.anemoi;

import java.sql.SQLException;
import java.util.Scanner;

public class Library extends Book{
	
	
	public void displayMenu() {
		System.out.println("Menu: ");
		System.out.println("1. Add Book");
		System.out.println("2. Display Catalog");
		System.out.println("3. Borrow a book");
		System.out.println("4. Return a book");
		System.out.println("0. Enter 0(zero) to Exit");
		System.out.println("Enter option");
	
	}
	
	public void bookInfoAdd(Library lib) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter name: ");
		String name = sc.nextLine();
		System.out.println("Enter author: ");
		String author = sc.nextLine();
		System.out.println("Enter quantity");
		int quantity = sc.nextInt();
		
		lib.addBook(name, author, quantity);
	}
	
	public void borrowBooks(Library lib) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nEnter name of the book: ");
		String borrow_name = sc.nextLine();
		lib.borrowBook(borrow_name);
	}
	
	public void returnBooks(Library lib) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nEnter name of the book: ");
		String return_name = sc.nextLine();
		lib.returnBook(return_name);
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		Library lib = new Library();
		
		int option = -1;
		
		System.out.println("Welcome to Anemoi Library!");
		
		while(option != 0) {
		lib.displayMenu();
		option = sc.nextInt();
		
		switch(option) {
		case 1:
			lib.bookInfoAdd(lib);
			break;
		
		case 2:
			System.out.println("\nCatalog:");
			lib.displayCatalog();
			System.out.println();
			break;
		case 3:
			lib.borrowBooks(lib);
			System.out.println();
			break;
		case 4:
			lib.returnBooks(lib);
			System.out.println();
			break;
		default:
			if(option != 0) {
				System.out.println("Invalid Option!");
				System.out.println();
			}
			System.out.println("Please Visit Again!");
			System.out.println();
			break;
			}
		}
		sc.close();
	}
}
