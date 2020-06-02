package com.promesa.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.internalFrame.ferias.IFerias;
import com.promesa.main.Promesa;

public class ResultExecuteList {

	String clase = "ResultExecuteList";
	private boolean flag = false;
	int[] nb = null;
	int numReg = 0;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> map = new HashMap<Integer, HashMap>();

	public ResultExecuteList() {

	}
	
	public void insertarListaFerias(IFerias instancia, List<String> insertSQL, String tabla, int origenDatos, String strMensaje) {
		ConexionJDBC conn = null;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = new ConexionJDBC(origenDatos);
			connection = conn.getConnection();
			stmt = connection.createStatement();
			connection.setAutoCommit(false);
			double total = insertSQL.size();
			double i = 1;
			double avance = 0;
			for (String query : insertSQL) {
				stmt.execute(query);
				avance = Math.round(i * 100 / total * 100) / 100.0d;
				instancia.registrarMensaje(strMensaje + avance + "%.");
				Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando " + tabla + " al " + avance + "%");
				i++;
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					connection.commit();
					connection.setAutoCommit(true);
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void insertarListaConsultas(List<String> insertSQL, String tabla, int origenDatos) {
		ConexionJDBC conn = null;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = new ConexionJDBC(origenDatos);
			connection = conn.getConnection();
			stmt = connection.createStatement();
			connection.setAutoCommit(false);
			double total = insertSQL.size();
			double i = 1;
			double avance = 0;
			for (String query : insertSQL) {
				stmt.execute(query);
				avance = Math.round(i * 100 / total * 100) / 100.0d;
				try{
				Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando " + tabla + " al " + avance + "%");
				}catch(Exception e){}
				i++;
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					connection.commit();
					connection.setAutoCommit(true);
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void insertarListaConsultas(List<String> insertSQL, int origenDatos) {
		ConexionJDBC conn = null; 
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = new ConexionJDBC(origenDatos);
			connection = conn.getConnection();
			stmt = connection.createStatement();
			connection.setAutoCommit(false);
			double i = 1;
			for (String query : insertSQL) {
				stmt.execute(query);
				i++;
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					connection.commit();
					connection.setAutoCommit(true);
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public int getNumReg() {
		return numReg;

	}
	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}
}