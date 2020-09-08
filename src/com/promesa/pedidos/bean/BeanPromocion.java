package com.promesa.pedidos.bean;

public class BeanPromocion {
	private String strFecha;
	private String strDivisionCliente;
	private String strCanalCliente;
	private String strCodigoCliente;
	private String strTitulo;
	private String strFamilia1;
	private String strFamilia2;
	private String strDescripcion;
	private String strFechaVigenciaDesde;
	private String strFechaVigenciaHasta;
	
	public BeanPromocion(){
		this.strFecha = "";
		this.strDivisionCliente = "";
		this.strCanalCliente = "";
		this.strCodigoCliente = "";
		this.strTitulo = "";
		this.strFamilia1 = "";
		this.strFamilia2 = "";
		this.strDescripcion = "";
		this.strFechaVigenciaDesde = "";
		this.strFechaVigenciaHasta = "";
	}
	
	public String getStrFecha() {
		return strFecha;
	}

	public void setStrFecha(String strFecha) {
		this.strFecha = strFecha;
	}

	public String getStrDivisionCliente() {
		return strDivisionCliente;
	}

	public void setStrDivisionCliente(String strDivisionCliente) {
		this.strDivisionCliente = strDivisionCliente;
	}

	public String getStrCanalCliente() {
		return strCanalCliente;
	}

	public void setStrCanalCliente(String strCanalCliente) {
		this.strCanalCliente = strCanalCliente;
	}

	public String getStrCodigoCliente() {
		return strCodigoCliente;
	}

	public void setStrCodigoCliente(String strCodigoCliente) {
		this.strCodigoCliente = strCodigoCliente;
	}

	public String getStrFamilia1() {
		return strFamilia1;
	}

	public void setStrFamilia1(String strFamilia1) {
		this.strFamilia1 = strFamilia1;
	}

	public String getStrFamilia2() {
		return strFamilia2;
	}

	public void setStrFamilia2(String strFamilia2) {
		this.strFamilia2 = strFamilia2;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public String getStrFechaVigenciaDesde() {
		return strFechaVigenciaDesde;
	}

	public void setStrFechaVigenciaDesde(String strFechaVigenciaDesde) {
		this.strFechaVigenciaDesde = strFechaVigenciaDesde;
	}

	public String getStrFechaVigenciaHasta() {
		return strFechaVigenciaHasta;
	}

	public void setStrFechaVigenciaHasta(String strFechaVigenciaHasta) {
		this.strFechaVigenciaHasta = strFechaVigenciaHasta;
	}

	public String getStrTitulo() {
		return strTitulo;
	}

	public void setStrTitulo(String strTitulo) {
		this.strTitulo = strTitulo;
	}

}
