package com.github.jpedidos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
	Esta classe ainda nao faz a conexao com banco 100%.
	Use ela como ponto de partida. 
 */
public class ConexaoBD {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Connection conn = null;

		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost/mysql","root", "password");

		    // Do something with the Connection
		} catch (SQLException ex) {
		    // handle any errors
			ex.printStackTrace();
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}	
	}
}
