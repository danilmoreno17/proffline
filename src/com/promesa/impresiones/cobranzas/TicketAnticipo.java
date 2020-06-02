package com.promesa.impresiones.cobranzas;

public class TicketAnticipo {

	private String direccion = "";
	private String telefono = "";
	private String numeroTicket = "";
	private String cliente = "";
	private String vendedor = "";
	private String fecha = "";
	private String referencia = "";
	private String numeroCuentaBanco = "";
	private String formaPago = "";
	private String observacion = "";
	private String totalCobranza = "";
	
	private String importe;
	private String iva;
	private String idFormaPago;

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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNumeroCuentaBanco() {
		return numeroCuentaBanco;
	}

	public void setNumeroCuentaBanco(String numeroCuentaBanco) {
		this.numeroCuentaBanco = numeroCuentaBanco;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTotalCobranza() {
		return totalCobranza;
	}

	public void setTotalCobranza(String totalCobranza) {
		this.totalCobranza = totalCobranza;
	}

//	Marcelo Moyano Reteinción Iva
	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(String idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
}
