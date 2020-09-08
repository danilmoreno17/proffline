package com.promesa.pedidos.bean;

public class BeanMercadeo implements Comparable {

	private String strFechaCarga;
	private String strDivisionCliente;
	private String strCanalCliente;
	private String strCodigoMaterial;
	private String strDescripcion;
	private String strFechaVigenciaDesde;
	private String strFechaVigenciaHasta;
	
	public BeanMercadeo(){
		this.strFechaCarga = "";
		this.strDivisionCliente = "";
		this.strCanalCliente = "";
		this.strCodigoMaterial = "";
		this.strDescripcion = "";
		this.strFechaVigenciaDesde = "";
		this.strFechaVigenciaHasta = "";
	}
	
	public String getStrCanalCliente() {
		return strCanalCliente;
	}

	public void setStrCanalCliente(String strCanalCliente) {
		this.strCanalCliente = strCanalCliente;
	}

	public String getStrFechaCarga() {
		return strFechaCarga;
	}

	public void setStrFechaCarga(String strFechaCarga) {
		this.strFechaCarga = strFechaCarga;
	}

	public String getStrDivisionCliente() {
		return strDivisionCliente;
	}

	public void setStrDivisionCliente(String strDivisionCliente) {
		this.strDivisionCliente = strDivisionCliente;
	}

	public String getStrCodigoMaterial() {
		return strCodigoMaterial;
	}

	public void setStrCodigoMaterial(String strCodigoMaterial) {
		this.strCodigoMaterial = strCodigoMaterial;
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

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
