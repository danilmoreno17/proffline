package com.promesa.pedidos.bean;

public class MarcaVendedor {
	private String codigoCliente;
	private String marca;
	private String presupuestoMes;
	private String ventaMes;
	private String presupuestoAcumulado;
	private String ventaAcumulado;
	private String fecha;
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoVendedor) {
		this.codigoCliente = codigoVendedor;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPresupuestoMes() {
		return presupuestoMes;
	}
	public void setPresupuestoMes(String presupuestoMes) {
		this.presupuestoMes = presupuestoMes;
	}
	public String getVentaMes() {
		return ventaMes;
	}
	public void setVentaMes(String ventaMes) {
		this.ventaMes = ventaMes;
	}
	public String getPresupuestoAcumulado() {
		return presupuestoAcumulado;
	}
	public void setPresupuestoAcumulado(String presupuestoAcumulado) {
		this.presupuestoAcumulado = presupuestoAcumulado;
	}
	public String getVentaAcumulado() {
		return ventaAcumulado;
	}
	public void setVentaAcumulado(String ventaAcumulado) {
		this.ventaAcumulado = ventaAcumulado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
