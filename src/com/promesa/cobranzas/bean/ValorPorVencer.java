package com.promesa.cobranzas.bean;

public class ValorPorVencer {
	private String codigoCliente;
	private String codigoVendedor;
	private String mesAnio;
	private String cantidad;
	
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getCodigoVendedor() {
		return codigoVendedor;
	}
	public void setCodigoVendedor(String codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
	}
	public String getMesAnio() {
		return mesAnio;
	}
	public void setMesAnio(String mesAnio) {
		this.mesAnio = mesAnio;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
}
