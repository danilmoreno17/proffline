package com.promesa.conexiondb;

import com.promesa.util.Constante;
import com.promesa.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionJDBC {

	private Connection connection = null;
	private int origenDatos;

	public ConexionJDBC(int origenDatos) {
		this.origenDatos = origenDatos;
		connection_SQL();
	}

	public void connection_SQL() {
		try {
			Class.forName(Constante.DRIVER_CLASSNAME);
			switch(origenDatos) {
			case Constante.BD_SYNC:
				connection = DriverManager.getConnection(Constante.CONNECTION_URL);
				break;
			case Constante.BD_TX:
				connection = DriverManager.getConnection(Constante.CONNECTION_URL_TX);
				break;
			case Constante.BD_FR:
				connection = DriverManager.getConnection(Constante.CONNECTION_URL_FR);
				break;	
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	public void close() {
		if (connection != null)
			try {
				connection.close();
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
	}

	public Connection getConnection() {
		return connection;
	}
}