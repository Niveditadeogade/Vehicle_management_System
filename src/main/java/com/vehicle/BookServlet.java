package com.vehicle;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		int v_id =  Integer.parseInt(request.getParameter("v_id"));
		int owner_id =  Integer.parseInt(request.getParameter("owner_id"));
		int user_id = (Integer)(session.getAttribute("user_id"));
		
		out.println("Vehicle_id:\t" + v_id);
		out.println("Owner id:\t" + owner_id);
		out.println("User id:\t" + user_id);

		try {
			DbConnection db = new DbConnection();
			Connection con = db.makeConnection();
			if(con != null) {
				System.out.print("Connection Successfull");
				
				// Execute SQL query to update avail value for Vehicle
				String sqlUpdt;
				sqlUpdt = "UPDATE vehicle SET avail='false' WHERE v_id=" + v_id;
				PreparedStatement stUpdt = con.prepareStatement(sqlUpdt);
				stUpdt.executeUpdate();
				
				// Execute SQL query to Book a Vehicle
				String sql = "INSERT INTO booking (user_id, owner_id, vehicle_id, date_book) VALUES (?, ?, ?, ?)";
				PreparedStatement st = con.prepareStatement(sql);
				 st.setInt(1, user_id);      
				 st.setInt(2, owner_id);     
				 st.setInt(3, v_id);         
				 st.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now())); 
				st.executeUpdate();
				System.out.println("Successfully Booked");
				response.sendRedirect("dashboard");
			}
		} catch(Exception e){};
	}

}
