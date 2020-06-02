package com.promesa.impresiones.devoluciones;

public class DetalleTicketDevolucion {

	private String posicion = "";
	private String material = "";
	private String cantidad = "";
	private String um = "";
	private String denominacion = "";
	private String precioNeto = "";
	private String valorNeto = "";
	
	public String getPosicion() {
		return posicion;
	}
	
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	
	public String getMaterial() {
		return material;
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getUm() {
		return um;
	}
	
	public void setUm(String um) {
		this.um = um;
	}
	
	public String getDenominacion() {
		return denominacion;
	}
	
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	public String getPrecioNeto() {
		return precioNeto;
	}
	
	public void setPrecioNeto(String precioNeto) {
		this.precioNeto = precioNeto;
	}
	
	public String getValorNeto() {
		return valorNeto;
	}
	
	public void setValorNeto(String valorNeto) {
		this.valorNeto = valorNeto;
	}	
}
