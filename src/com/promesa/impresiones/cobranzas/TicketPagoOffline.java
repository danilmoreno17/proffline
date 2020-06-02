package com.promesa.impresiones.cobranzas;

import java.util.List;

import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;

public class TicketPagoOffline {

	private List<PagoRecibido> listaPagosRecibidos;
	private List<PagoParcial> listaPagosParciales;
	private String direccion = "";
	private String telefono = "";
	private String numeroTicket = "";
	private String cliente = "";
	private String vendedor = "";
	private String fechadoc ;

	/*
	 * Requerimiento: PRO-2014-0004
	 * MARCELO MOYANO
	 * 07/07/2014
	 */
	public String getFechadoc() {
		return fechadoc;
	}

	public void setFechadoc(String fechadoc) {
		this.fechadoc = fechadoc;
	}

	public List<PagoRecibido> getListaPagosRecibidos() {
		return listaPagosRecibidos;
	}

	public void setListaPagosRecibidos(List<PagoRecibido> listaPagosRecibidos) {
		this.listaPagosRecibidos = listaPagosRecibidos;
	}

	public List<PagoParcial> getListaPagosParciales() {
		return listaPagosParciales;
	}

	public void setListaPagosParciales(List<PagoParcial> listaPagosParciales) {
		this.listaPagosParciales = listaPagosParciales;
	}

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
}