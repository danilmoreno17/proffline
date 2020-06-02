package com.promesa.cobranzas.bean;

import java.util.List;

public class RegistroPagoCliente {
	private CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente;
	private List<PagoRecibido> listaPagoRecibido;
	private List<PagoParcial> listaPagoParcial;

	public CabeceraRegistroPagoCliente getCabeceraRegistroPagoCliente() {
		return cabeceraRegistroPagoCliente;
	}
	public void setCabeceraRegistroPagoCliente(CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente) {
		this.cabeceraRegistroPagoCliente = cabeceraRegistroPagoCliente;
	}
	public List<PagoRecibido> getListaPagoRecibido() {
		return listaPagoRecibido;
	}
	public void setListaPagoRecibido(List<PagoRecibido> listaPagoRecibido) {
		this.listaPagoRecibido = listaPagoRecibido;
	}
	public List<PagoParcial> getListaPagoParcial() {
		return listaPagoParcial;
	}
	public void setListaPagoParcial(List<PagoParcial> listaPagoParcial) {
		this.listaPagoParcial = listaPagoParcial;
	}
	@Override
	public String toString() {
		return "RegistroPagoCliente [cabeceraRegistroPagoCliente=" + cabeceraRegistroPagoCliente + ", listaPagoRecibido=" + listaPagoRecibido + ", listaPagoParcial=" + listaPagoParcial + "]";
	}
}
