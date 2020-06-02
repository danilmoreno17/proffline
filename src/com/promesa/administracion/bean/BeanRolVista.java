package com.promesa.administracion.bean;

public class BeanRolVista {
	private String strMandante;
	private String strIdRol;
	private String strNomVista;
	private BeanRol rol;
	private BeanVista vista;	
	
	public String getStrMandante() {
		return strMandante;
	}
	public void setStrMandante(String strMandante) {
		this.strMandante = strMandante;
	}
	public String getStrIdRol() {
		return strIdRol;
	}
	public void setStrIdRol(String strIdRol) {
		this.strIdRol = strIdRol;
	}
	public String getStrNomVista() {
		return strNomVista;
	}
	public void setStrNomVista(String strNomVista) {
		this.strNomVista = strNomVista;
	}
	public BeanRol getRol() {
		return rol;
	}
	public void setRol(BeanRol rol) {
		this.rol = rol;
	}
	public BeanVista getVista() {
		return vista;
	}
	public void setVista(BeanVista vista) {
		this.vista = vista;
	}
}