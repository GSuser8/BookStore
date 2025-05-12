package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteBook extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "jdbc_user", "mysql")){
				String query = "DELETE FROM bookdata WHERE book_id = "+req.getParameter("bookId")+"";
				Statement stmt = conn.createStatement();
				if(stmt.executeUpdate(query)==1) {
					pw.println("<h1>Deleted!</h1>");
				} else {
					pw.println("Can't delete");
				}
			} catch(Exception e) {
				pw.println(e);
			}
		} catch(Exception e) {
			pw.println(e);
		}
	}
}
