package com.promesa.dao;

import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.util.Util;

import java.sql.Statement;
import java.lang.String;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResultExecuteQuery {

	String clase = "ResultExecuteQuery";
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> map = new HashMap<Integer, HashMap>();

	@SuppressWarnings("rawtypes")
	public ResultExecuteQuery(String sqlQuery, HashMap column, int origenDatos) {
		ConexionJDBC conn = new ConexionJDBC(origenDatos);
		int fila = 0;
		String tipo[] = null;
		HashMap<String, String> setMap = null;
		Connection connection = conn.getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				setMap = new HashMap<String, String>();
				Iterator it2 = column.entrySet().iterator();
				while (it2.hasNext()) {
					Map.Entry e = (Map.Entry) it2.next();
					tipo = e.getKey().toString().trim().split(":");
					if (tipo[0].trim().equals("String")) {
						setMap.put(e.getValue().toString(), rs.getString(e.getValue().toString()));
					} else if (tipo[0].trim().equals("Timestamp")) {
						// setMap.put(e.getValue().toString(),util.getFechaHora(rs.getTimestamp(e.getValue().toString())));
					}
				}
				map.put(fila, setMap);
				fila++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	
	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}
}