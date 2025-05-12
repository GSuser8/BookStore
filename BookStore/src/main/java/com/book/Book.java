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

public class Book extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		String id = req.getParameter("book_id");
		String name = req.getParameter("book_name");
		String edition = req.getParameter("book_edition");
		String price = req.getParameter("book_price");
		PrintWriter pw = res.getWriter();
		pw.println(name + " " + edition + " " + price);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String query = "INSERT INTO bookdata (book_id, book_name, book_edition, book_price) VALUES (?,?,?,?)";
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "jdbc_user",
					"mysql")) {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, Integer.parseInt(id));
				ps.setString(2, name);
				ps.setInt(3, Integer.parseInt(edition));
				ps.setInt(4, Integer.parseInt(price));
				if (ps.executeUpdate() == 1) {
					pw.println("Registered successfully!");
				} else {
					pw.println("Could not register");
				}
			} catch (Exception e) {
				pw.println("Could not connect to database" + e);
			}
		} catch (Exception e) {
			pw.println(e);
		}
	}

}
