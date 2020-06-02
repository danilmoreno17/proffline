package com.promesa.cobranzas.bean;

public class BeanFacturaDetalle {
	
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public String getCodigoMaterial() {
		return codigoMaterial;
	}
	public void setCodigoMaterial(String material) {
		this.codigoMaterial = material;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCantidadFactura() {
		return cantidadFactura;
	}
	public void setCantidadFactura(String cantidadFactura) {
		this.cantidadFactura = cantidadFactura;
	}
	public String getCantidadPedido() {
		return cantidadPedido;
	}
	public void setCantidadPedido(String cantidadPedido) {
		this.cantidadPedido = cantidadPedido;
	}
	public String getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(String precioNeto) {
		this.precioNeto = precioNeto;
	}
	public String getValorNeto() {
		return valorNeto;
	}
	public void setValorNeto(String valorNeto) {
		this.valorNeto = valorNeto;
	}
	public String getDescuentos3X() {
		return descuentos3X;
	}
	public void setDescuentos3X(String descuentos3x) {
		descuentos3X = descuentos3x;
	}
	public String getDescuentos4X() {
		return descuentos4X;
	}
	public void setDescuentos4X(String descuentos4x) {
		descuentos4X = descuentos4x;
	}
	public String getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public String getDescuentoVolumen() {
		return descuentoVolumen;
	}
	public void setDescuentoVolumen(String descuentoVolumen) {
		this.descuentoVolumen = descuentoVolumen;
	}
	public String getDescuentoCodigo() {
		return descuentoCodigo;
	}
	public void setDescuentoCodigo(String descuentoCodigo) {
		this.descuentoCodigo = descuentoCodigo;
	}
	public String getDescuentoManual() {
		return descuentoManual;
	}
	public void setDescuentoManual(String descuentoManual) {
		this.descuentoManual = descuentoManual;
	}
	
	public void setDescuentoCanal(String descuentoCanal) {
		this.descuentoCanal = descuentoCanal;
	}
	public String getDescuentoCanal() {
		return descuentoCanal;
	}

	private String posicion;
	private String codigoMaterial;
	private String descripcion;
	private String cantidadPedido;
	private String cantidadFactura;
	private String precioUnitario;
	private String descuentoCanal;
	private String descuentos3X;
	private String descuentos4X;
	private String descuentoVolumen;
	private String descuentoCodigo;
	private String descuentoManual;
	private String valorNeto;
	private String precioNeto;
}
