package com.promesa.pedidos.bean;

public class BeanCondicionExpedicion {

	private String strIdCondicionExpedicion;
	private String strDescripcion;
	
	public String getStrIdCondicionExpedicion() {
		return strIdCondicionExpedicion;
	}
	public void setStrIdCondicionExpedicion(String strIdCondicionExpedicion) {
		this.strIdCondicionExpedicion = strIdCondicionExpedicion;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	
	@Override
	public String toString() {
		return strDescripcion;
	}
	
}
