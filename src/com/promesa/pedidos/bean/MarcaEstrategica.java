package com.promesa.pedidos.bean;

public class MarcaEstrategica {
	
	private String codigoCliente;
	private String marca;
	private String presupuesto;
	private String acumulado;
	private String fecha;
	private String valor="";
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}
	public String getAcumulado() {
		return acumulado;
	}
	public void setAcumulado(String acumulado) {
		this.acumulado = acumulado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
