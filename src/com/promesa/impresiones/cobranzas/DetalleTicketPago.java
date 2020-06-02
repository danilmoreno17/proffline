package com.promesa.impresiones.cobranzas;

public class DetalleTicketPago {
	
	private String nroFactura;
	private String fechaVencimiento;
	private double valor;
	private double abono;
	
	private String iva;
	
	public String getNroFactura() {
		return nroFactura;
	}
	
	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}
	
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getAbono() {
		return abono;
	}
	
	public void setAbono(double abono) {
		this.abono = abono;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}
}
