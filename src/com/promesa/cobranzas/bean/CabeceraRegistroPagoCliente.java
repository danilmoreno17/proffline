package com.promesa.cobranzas.bean;

public class CabeceraRegistroPagoCliente {
	private long idCabecera;
	private String codigoCliente;
	private String codigoVendedor;
	private double importeEntrado;
	private double importeAsignado;
	private double importeSinAsignar;
	private long partidasSeleccionadas;
	private String fechaCreacion;
	private String sincronizado;
	private long cantidadPagoRecibido;
	private String cabeceraSecuencia;//	Marcelo Moyano Secuencia

	public long getIdCabecera() {
		return idCabecera;
	}
	public void setIdCabecera(long idCabecera) {
		this.idCabecera = idCabecera;
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
	public double getImporteEntrado() {
		return importeEntrado;
	}
	public void setImporteEntrado(double importeEntrado) {
		this.importeEntrado = importeEntrado;
	}
	public double getImporteAsignado() {
		return importeAsignado;
	}
	public void setImporteAsignado(double importeAsignado) {
		this.importeAsignado = importeAsignado;
	}
	public double getImporteSinAsignar() {
		return importeSinAsignar;
	}
	public void setImporteSinAsignar(double importeSinAsignar) {
		this.importeSinAsignar = importeSinAsignar;
	}
	public long getPartidasSeleccionadas() {
		return partidasSeleccionadas;
	}
	public void setPartidasSeleccionadas(long partidasSeleccionadas) {
		this.partidasSeleccionadas = partidasSeleccionadas;
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
	public long getCantidadPagoRecibido() {
		return cantidadPagoRecibido;
	}
	public void setCantidadPagoRecibido(long cantidadPagoRecibido) {
		this.cantidadPagoRecibido = cantidadPagoRecibido;
	}
	//	Marcelo Moyano
	//	Atributos de la clase CabeceraRegistroPago
	//	Representa la Secuencia del documento
	public String getCabeceraSecuencia() {
		return cabeceraSecuencia;
	}
	public void setCabeceraSecuencia(String cabeceraSecuencia) {
		this.cabeceraSecuencia = cabeceraSecuencia;
	}
	//	Marcelo Moyano
	@Override
	public String toString() {
		return "CabeceraRegistroPagoCliente [idCabecera=" + idCabecera
				+ ", codigoCliente=" + codigoCliente + ", codigoVendedor="
				+ codigoVendedor + ", importeEntrado=" + importeEntrado
				+ ", importeAsignado=" + importeAsignado
				+ ", importeSinAsignar=" + importeSinAsignar
				+ ", partidasSeleccionadas=" + partidasSeleccionadas
				+ ", fechaCreacion=" + fechaCreacion + ", sincronizado="
				+ sincronizado + ", cantidadPagoRecibido="
				+ cantidadPagoRecibido + "]";
	}
}