package com.promesa.pedidos.bean;

import java.util.List;

import com.promesa.internalFrame.pedidos.custom.Item;

public class BeanMensaje {

	private Long identificador;
	private String descripcion;
	private String tipo;
	private BeanPedido pedido;
	private String tipoMaterial = "";
	private List<Item> its;

	public BeanMensaje(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BeanPedido getPedido() {
		return pedido;
	}

	public void setPedido(BeanPedido pedido) {
		this.pedido = pedido;
	}

	public List<Item> getIts() {
		return its;
	}

	public void setIts(List<Item> its) {
		this.its = its;
	}

}
