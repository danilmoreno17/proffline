package com.promesa.bean;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.promesa.util.Mensaje;

public class BeanConexion {
	private String strPuerto;
	private String strBaseDatos;
	private String strServidor;
	private String strUsuario;
	private String strPassword;
	private String strMandanteSAP;
	private String strUsuarioSAP;
	private String strPasswordSAP;
	private String strIdiomaSAP;
	private String strHostSAP;
	private String strSistemaSAP;

	public BeanConexion() {
		Properties props = new Properties();
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
			props.load(in);
			strMandanteSAP = props.getProperty("sap.mandante").trim();
			strUsuarioSAP = props.getProperty("sap.usuario").trim();
			strPasswordSAP = props.getProperty("sap.password").trim();
			strIdiomaSAP = props.getProperty("sap.idioma").trim();
			strHostSAP = props.getProperty("sap.host").trim();
			strSistemaSAP = props.getProperty("sap.sistema").trim();
			in.close();
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			System.exit(1);
		}
	}
	
	public String getStrPuerto() {
		return strPuerto;
	}
	public void setStrPuerto(String strPuerto) {
		this.strPuerto = strPuerto;
	}
	public String getStrBaseDatos() {
		return strBaseDatos;
	}
	public void setStrBaseDatos(String strBaseDatos) {
		this.strBaseDatos = strBaseDatos;
	}
	public String getStrServidor() {
		return strServidor;
	}
	public void setStrServidor(String strServidor) {
		this.strServidor = strServidor;
	}
	public String getStrUsuario() {
		return strUsuario;
	}
	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getStrMandanteSAP() {
		return strMandanteSAP;
	}
	public void setStrMandanteSAP(String strMandanteSAP) {
		this.strMandanteSAP = strMandanteSAP;
	}
	public String getStrUsuarioSAP() {
		return strUsuarioSAP;
	}
	public void setStrUsuarioSAP(String strUsuarioSAP) {
		this.strUsuarioSAP = strUsuarioSAP;
	}
	public String getStrPasswordSAP() {
		return strPasswordSAP;
	}
	public void setStrPasswordSAP(String strPasswordSAP) {
		this.strPasswordSAP = strPasswordSAP;
	}
	public String getStrIdiomaSAP() {
		return strIdiomaSAP;
	}
	public void setStrIdiomaSAP(String strIdiomaSAP) {
		this.strIdiomaSAP = strIdiomaSAP;
	}
	public String getStrHostSAP() {
		return strHostSAP;
	}
	public void setStrHostSAP(String strHostSAP) {
		this.strHostSAP = strHostSAP;
	}
	public String getStrSistemaSAP() {
		return strSistemaSAP;
	}
	public void setStrSistemaSAP(String strSistemaSAP) {
		this.strSistemaSAP = strSistemaSAP;
	}
}