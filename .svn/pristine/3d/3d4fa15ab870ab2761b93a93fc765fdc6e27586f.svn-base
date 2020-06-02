package com.promesa.impresiones.cobranzas;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class TicketPago implements Comparable{
	
	private String direccion = "";
	private String telefono = "";
	private String numeroTicket = "";
	private String cliente = "";
	private String vendedor = "";
	private String referencia = "";
	private String cuentaBanco = "";
	private String formaPago = "";
	private String fechaDocumento = "";
	private String fechaVencimiento = "";
	private List<DetalleTicketPago> listaDetalleTicketPago = new ArrayList<DetalleTicketPago>();
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getNumeroTicket() {
		return numeroTicket;
	}
	
	public void setNumeroTicket(String numeroTicket) {
		this.numeroTicket = numeroTicket;
	}
	
	public String getCliente() {
		return cliente;
	}
	
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	public String getVendedor() {
		return vendedor;
	}
	
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	
	public String getReferencia() {
		return referencia;
	}
	
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public String getCuentaBanco() {
		return cuentaBanco;
	}
	
	public void setCuentaBanco(String cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
	}
	
	public String getFormaPago() {
		return formaPago;
	}
	
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	public String getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public List<DetalleTicketPago> getListaDetalleTicketPago() {
		return listaDetalleTicketPago;
	}
	
	public void setListaDetalleTicketPago(List<DetalleTicketPago> listaDetalleTicketPago) {
		this.listaDetalleTicketPago = listaDetalleTicketPago;
	}
	
	public void agregarDetalleALaLista(DetalleTicketPago detalleTicketPago){
		listaDetalleTicketPago.add(detalleTicketPago);
	}

	@Override
	public int compareTo(Object o) {
		TicketPago ticketPago2 = (TicketPago)o;
		return this.numeroTicket.compareTo(ticketPago2.numeroTicket);
	}
}