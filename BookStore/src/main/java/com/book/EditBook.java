package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditBook extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		String book_id = req.getParameter("bookId");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "jdbc_user", "mysql")){
				Statement query = conn.createStatement();
				ResultSet rs = query.executeQuery("SELECT * FROM bookdata WHERE book_id ="+book_id);
				while(rs.next()) {
					String name = rs.getString(2);
					String edition = rs.getString(3);
					String price = rs.getString(4);
					pw.println("<form method='post' action='edit?bookId="+book_id+"'>");
					pw.println("<label>Book name:</label>");
					pw.println("<input name='book_name' value="+name+">");
					pw.println("<label>Book edition:</label>");
					pw.println("<input name='book_edition' value="+edition+">");
					pw.println("<label>Book price:</label>");
					pw.println("<input name='book_price' value="+price+">");
					pw.println("<button type='submit'>Update</button>");
					pw.println("</form>");
				}
			}
		} catch(Exception e) {
			pw.println(e);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "jdbc_user", "mysql")){
				String bookID = req.getParameter("bookId");
				String query = "UPDATE bookdata SET book_name=?, book_edition=?, book_price=? WHERE book_id = "+bookID+";";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, req.getParameter("book_name"));
				ps.setInt(2, Integer.parseInt(req.getParameter("book_edition")));
				ps.setInt(3, Integer.parseInt(req.getParameter("book_price")));
				if(ps.executeUpdate()==1) {
					pw.println("<h1>Updated!</h1>");
				} else {
					pw.println("Unable to update");
				}
			}
		} catch(Exception e) {
			pw.println(e);
		}
	}
}
