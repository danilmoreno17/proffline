/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promesa.cobranzas.bean;

/**
 *
 * @author Administrador
 */
public class PedidoPendienteDevolucion {
    private long id;
    private String codigoCliente;
    private String codigoVendedor;
    private String numeroDocumento;
    private String fechaDocumento;
    private String creadoEl;
    private String valorNeto;
    private String nombreVendedor;
    
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
	public String getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	public String getCreadoEl() {
		return creadoEl;
	}
	public void setCreadoEl(String creadoEl) {
		this.creadoEl = creadoEl;
	}
	public String getValorNeto() {
		return valorNeto;
	}
	public void setValorNeto(String valorNeto) {
		this.valorNeto = valorNeto;
	}
	public String getNombreVendedor() {
		return nombreVendedor;
	}
	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}
}
