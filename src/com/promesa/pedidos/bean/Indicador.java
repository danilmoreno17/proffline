package com.promesa.pedidos.bean;

public class Indicador {
	
	private String codigoCliente;
	private String monto;
	private String acumulado;
	private String estatus;
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getAcumulado() {
		return acumulado;
	}
	public void setAcumulado(String acumulado) {
		this.acumulado = acumulado;
	}
	
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}
