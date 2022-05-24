package org.anemoi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Book {
	
	public static final String driver="com.mysql.cj.jdbc.Driver";
	
	public static final String Url="jdbc:mysql://localhost:3306/anemoi_library";
	
	public static final String uname="root";
	
	public static final String password="alka@123";
	
	
	// Add Book
	public void addBook(String name, String author, int quantity) throws ClassNotFoundException, SQLException {
		final String sql = "INSERT INTO library (name, author, quantity) VALUES(?,?,?)";
		
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		PreparedStatement stmt=con.prepareStatement(sql); 
		
		stmt.setString(1,name); 
		stmt.setString(2,author); 
		stmt.setInt(3, quantity);
		
		int status = stmt.executeUpdate();
        if(status > 0) {
           System.out.println("Record is inserted successfully !!!");
        }
		
		con.close();
	}

	// Display Catalog
	public void displayCatalog() throws SQLException, ClassNotFoundException{
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		Statement stmt=con.createStatement();  
		ResultSet rs1=stmt.executeQuery("SELECT * FROM library");
		
		System.out.println();
		System.out.println("Id\t\tName\t\tAuthor\t\t\tQuantity\tBorrwed");
		while(rs1.next()) {  
		System.out.println(rs1.getInt(1)+"\t"+rs1.getString(2)+"\t"+rs1.getString(3)+"\t"+rs1.getInt(4)+"\t"+rs1.getInt(5));
		}
		
		totalBooks();
		
		con.close();
	}
	
	// Display Total Books count
	public void totalBooks() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		Statement stmt=con.createStatement();  
		
		ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM library");
		
		int count = 0;
		while(rs2.next()) {
			count = rs2.getInt(1);
		}
		
		System.out.println("Total Books in library: "+count);
		
		con.close();
	}
	
	
	//Borrow Book 
	public void borrowBook(String name) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		Statement stmt=con.createStatement();  
		ResultSet rs1=stmt.executeQuery("SELECT * FROM library");

		System.out.println();
		
		boolean is_present = false;
		int book_id = 0;
		while(rs1.next()) {  
		if(name.compareToIgnoreCase(rs1.getString(2)) == 0) {
			is_present = true;
			System.out.println("Present");
			book_id = rs1.getInt(1);
		}
			
		}
		
		if(is_present) {
			PreparedStatement pre_stmt=con.prepareStatement("UPDATE library SET quantity = quantity - 1 "+
															"WHERE id = ?"); 
			pre_stmt.setInt(1, book_id);
			
			int status = pre_stmt.executeUpdate();
	        if(status > 0) {
	           System.out.println("Record is updated successfully !!!");
	        }
	        
	        borrowAdd(book_id);
			
		} else {
			System.out.println("Book Not Found!");
		}
			
		con.close();
	}
	
	// linked to borrowBook, update borrowed columnn status
	public void borrowAdd(int book_id) throws ClassNotFoundException, SQLException {
		
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		PreparedStatement pre_stmt_borrow=con.prepareStatement("UPDATE library SET borrowed = borrowed + 1 "+
										"WHERE id = ?"); 
		pre_stmt_borrow.setInt(1, book_id);

		int status_borrow = pre_stmt_borrow.executeUpdate();
		if(status_borrow > 0) {
			System.out.println("Borrow Record is updated successfully !!!");
		}
	}
	
	
	//Return Book
	public void returnBook(String name) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		Statement stmt=con.createStatement();  
		ResultSet rs1=stmt.executeQuery("SELECT * FROM library");

		System.out.println();
		
		boolean is_present = false;
		int book_id = 0;
		while(rs1.next()) {  
		if(name.compareToIgnoreCase(rs1.getString(2)) == 0) {
			is_present = true;
			book_id = rs1.getInt(1);
		}
			
		}
		
		if(is_present) {
			PreparedStatement pre_stmt_quantity=con.prepareStatement("UPDATE library SET quantity = quantity + 1 "+
															"WHERE id = ?"); 
			pre_stmt_quantity.setInt(1, book_id);
			int status = pre_stmt_quantity.executeUpdate();
	        if(status > 0) {
	           System.out.println("Record is updated successfully !!!");
	        }
		
			returnSub(book_id);
			
		} else {
			System.out.println("Book Not Found!");
		}
			
		con.close();
	}
	// linked to returnBook statment, update borrow status
	public void returnSub(int book_id) throws ClassNotFoundException, SQLException {
		
		Class.forName(driver);
		Connection con=DriverManager.getConnection(Url,uname,password); 
		
		PreparedStatement pre_stmt_borrow=con.prepareStatement("UPDATE library SET borrowed = borrowed - 1 "+
										"WHERE id = ?"); 
		pre_stmt_borrow.setInt(1, book_id);

		int status_borrow = pre_stmt_borrow.executeUpdate();
		if(status_borrow > 0) {
			System.out.println("Borrow Record is updated successfully !!!");
		}
	}
	
	
}
