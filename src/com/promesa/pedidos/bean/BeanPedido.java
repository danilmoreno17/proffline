package com.promesa.pedidos.bean;

import java.util.List;

public class BeanPedido {

	private String codigoPedido;
	private BeanPedidoHeader header;
	private BeanPedidoPartners partners;
	private List<BeanPedidoDetalle> detalles;
	private String tipo = "";

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BeanPedidoHeader getHeader() {
		return header;
	}
	public void setHeader(BeanPedidoHeader header) {
		this.header = header;
	}

	public BeanPedidoPartners getPartners() {
		return partners;
	}
	public void setPartners(BeanPedidoPartners partners) {
		this.partners = partners;
	}

	public List<BeanPedidoDetalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<BeanPedidoDetalle> detalles) {
		this.detalles = detalles;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}
	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	@Override
	public String toString() {
		return "BeanPedido [codigoPedido=" + codigoPedido + ", header="
				+ header + ", partners=" + partners + ",\ndetalles=" + detalles
				+ "]";
	}
}