package com.promesa.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.main.Promesa;
import com.promesa.util.Util;

public class ResultExecute {

	String clase = "ResultExecute";
	private boolean flag = false; 

	public ResultExecute(String sqlQuery, String tabla, int origenDatos) {
		ConexionJDBC conn = new ConexionJDBC(origenDatos);
		Connection connection = conn.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			flag = false;
			try{
				if (Promesa.getInstance() != null) {
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando " + tabla);
				}
			}catch(Exception e1){}
			try {
				stmt.execute(sqlQuery);
				flag = true;
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			}
			flag = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
			try{
				if (Promesa.getInstance() != null) {
					Promesa.getInstance().mostrarAvisoSincronizacion("");
				}
			}catch(Exception e){}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
		}
	}
	
	public ResultExecute(String sqlQuery, int origenDatos) {
		ConexionJDBC conn = new ConexionJDBC(origenDatos);
		Connection connection = conn.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			flag = false;
			try {
				stmt.execute(sqlQuery);
				flag = true;
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			}
			flag = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
				}
		}
	}

	public boolean isFlag() {
		return flag;
	}
}