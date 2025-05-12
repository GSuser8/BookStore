package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetBook extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "jdbc_user", "mysql")){
				Statement query = conn.createStatement();
				ResultSet rs = query.executeQuery("SELECT * FROM bookdata");
				pw.println("<table>");
				pw.println("<tr>");
				pw.println("<th>ID</th>");
				pw.println("<th>Name</th>");
				pw.println("<th>Edition</th>");
				pw.println("<th>Price</th>");
				pw.println("<th>Edit</th>");
				pw.println("<th>Delete</th>");
				pw.println("</tr>");
				while(rs.next()) {
					pw.println("<tr>");
					pw.println("<td>"+rs.getString(1)+"</td>");
					pw.println("<td>"+rs.getString(2)+"</td>");
					pw.println("<td>"+rs.getString(3)+"</td>");
					pw.println("<td>"+rs.getString(4)+"</td>");
					pw.println("<td>"+"<a href='edit?bookId="+rs.getString(1)+"'><button>Edit</button></a>"+"</td>");
					pw.println("<td>"+"<a href='delete?bookId="+rs.getString(1)+"'><button>Delete</button></a>"+"</td>");
					pw.println("</tr>");
				}
			pw.println("</table>");
			} catch(Exception e) {
				pw.println(e);
			}
		} catch(Exception e) {
			pw.println(e);
		}
	}
}
