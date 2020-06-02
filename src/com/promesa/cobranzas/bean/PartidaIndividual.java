package com.promesa.cobranzas.bean;

public class PartidaIndividual {
    private long id;
    private String codigoCliente;
    private String codigoVendedor;
    private String numeroDocumento;
    private String claseDocumento;
    private String posicion;
    private String fechaDocumento;
    private String fechaVencimiento;
    private String registradoEl;
    private String moneda;
    private String impteDePos;
    private String referencia;
    
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
	public String getCodigoVendedor() {
		return codigoVendedor;
	}
	public void setCodigoVendedor(String codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getClaseDocumento() {
		return claseDocumento;
	}
	public void setClaseDocumento(String claseDocumento) {
		this.claseDocumento = claseDocumento;
	}
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
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
	public String getRegistradoEl() {
		return registradoEl;
	}
	public void setRegistradoEl(String registradoEl) {
		this.registradoEl = registradoEl;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getImpteDePos() {
		return impteDePos;
	}
	public void setImpteDePos(String impteDePos) {
		this.impteDePos = impteDePos;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
