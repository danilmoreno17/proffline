package com.promesa.cobranzas.bean;

public class AnticipoCliente {
	private long id;
	private String codigoCliente = "";
	private String nombreCompletoCliente = "";
	private String idFormaPago = "";
	private String referencia = "";
	private String importe = "";
	private String nroCtaBcoCliente = "";
	private String idBancoCliente = "";
	private String idBancoPromesa = "";
	private String codigoVendedor = "";
	private String observaciones = "";
	private String fechaCreacion = "";
	private String sincronizado = "";
	private String anticipoSecuencia;//	Marcelo Moyano Secuencia
	// ATRIBUTOS PARA LA VISTA
	private String descripcionBancoCliente = "";
	private String descripcionBancoPromesa = "";
	private String descripcionFormaPago = "";
	
	private String importeIva ;

	public String getImporteIva() {
		return importeIva;
	}
	public void setImporteIva(String importeIva) {
		this.importeIva = importeIva;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getNombreCompletoCliente() {
		return nombreCompletoCliente;
	}
	public void setNombreCompletoCliente(String nombreCompletoCliente) {
		this.nombreCompletoCliente = nombreCompletoCliente;
	}
	public String getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(String idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getNroCtaBcoCliente() {
		return nroCtaBcoCliente;
	}
	public void setNroCtaBcoCliente(String nroCtaBcoCliente) {
		this.nroCtaBcoCliente = nroCtaBcoCliente;
	}
	public String getIdBancoCliente() {
		return idBancoCliente;
	}
	public void setIdBancoCliente(String idBancoCliente) {
		this.idBancoCliente = idBancoCliente;
	}
	public String getIdBancoPromesa() {
		return idBancoPromesa;
	}
	public void setIdBancoPromesa(String idBancoPromesa) {
		this.idBancoPromesa = idBancoPromesa;
	}
	public String getCodigoVendedor() {
		return codigoVendedor;
	}
	public void setCodigoVendedor(String codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getSincronizado() {
		return sincronizado;
	}
	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}
	public String getDescripcionBancoCliente() {
		return descripcionBancoCliente;
	}
	public void setDescripcionBancoCliente(String descripcionBancoCliente) {
		this.descripcionBancoCliente = descripcionBancoCliente;
	}
	public String getDescripcionBancoPromesa() {
		return descripcionBancoPromesa;
	}
	public void setDescripcionBancoPromesa(String descripcionBancoPromesa) {
		this.descripcionBancoPromesa = descripcionBancoPromesa;
	}
	public String getDescripcionFormaPago() {
		return descripcionFormaPago;
	}
	public void setDescripcionFormaPago(String descripcionFormaPago) {
		this.descripcionFormaPago = descripcionFormaPago;
	}
	//	Marcelo Moyano Secuencia de Anticipos
	public String getAnticipoSecuencia() {
		return anticipoSecuencia;
	}
	public void setAnticipoSecuencia(String anticipoSecuencia) {
		this.anticipoSecuencia = anticipoSecuencia;
	}
}
