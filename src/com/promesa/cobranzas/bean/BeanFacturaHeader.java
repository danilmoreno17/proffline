package com.promesa.cobranzas.bean;

public class BeanFacturaHeader {
	private String numeroFacturaSap;
	private String numeroFacturaSRI;
	private String fechaFactura;
	private String tipoDocumento;
	private String codigocliente;
	private String nombreCliente;
	private String numeroPedido;
	private String valorNeto;
	private String totalImpuesto;
	private String valorTotal;
	private String condicionPago;
	
	public String getNumeroFacturaSap() {
		return numeroFacturaSap;
	}
	public void setNumeroFacturaSap(String numeroFacturaSap) {
		this.numeroFacturaSap = numeroFacturaSap;
	}
	public String getNumeroFacturaSRI() {
		return numeroFacturaSRI;
	}
	public void setNumeroFacturaSRI(String numeroFacturaSRI) {
		this.numeroFacturaSRI = numeroFacturaSRI;
	}
	public String getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getCodigocliente() {
		return codigocliente;
	}
	public void setCodigocliente(String codigocliente) {
		this.codigocliente = codigocliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public String getValorNeto() {
		return valorNeto;
	}
	public void setValorNeto(String valorNeto) {
		this.valorNeto = valorNeto;
	}
	public String getTotalImpuesto() {
		return totalImpuesto;
	}
	public void setTotalImpuesto(String totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getCondicionPago() {
		return condicionPago;
	}
	public void setCondicionPago(String condicionPago) {
		this.condicionPago = condicionPago;
	}
}
