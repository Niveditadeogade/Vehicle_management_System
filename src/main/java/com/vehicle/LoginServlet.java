package com.vehicle;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DbConnection db = new DbConnection();
		PrintWriter out = response.getWriter();
		
		String email =  request.getParameter("email");
		String password =  request.getParameter("password");
		
		System.out.println("User Email:\t" + request.getParameter("email"));
		System.out.println("User Password:\t" + request.getParameter("password"));

		try {
			Connection con = db.makeConnection();
			if(con != null) {
				System.out.print("Connection Successfull");
				
				// Prepare SQL query
				String sql = "SELECT * FROM users WHERE email=?";
				PreparedStatement pstmt = con.prepareStatement(sql);

				// Set parameter value
				pstmt.setString(1, email);

				// Execute SQL query
				ResultSet rs = pstmt.executeQuery();


		         // Extract data from result set
		         if(!rs.isBeforeFirst()) {
		        	 out.println("Email ID Not found: \t" + email);
		         } else {
		        	 while(rs.next()){
		        		int user_id = rs.getInt("id");
		        		String name = rs.getString("name");
		        		String remail = rs.getString("email");
				        String rpassword = rs.getString("password");
				        
				        // Check for password 
				        if(password.equals(rpassword)) {
				        	session.setAttribute("user_id", user_id);
				        	session.setAttribute("name", name);
				        	session.setAttribute("email", remail);
				        	response.sendRedirect("dashboard");
				        } else {
				        	out.println("Invalid Password");
				        	response.sendRedirect("login");
				        }
				        
		        	 }
		         }
			}
		} catch(Exception e){};
		
	}
}
