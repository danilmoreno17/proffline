package com.promesa.planificacion.bean;

import java.util.List;

public class BeanCliente {

	private String strIdCliente = "";
	private String strCompaniaCliente = "";
	private String strNombreCliente = "";
	private String strApellidosCliente = "";
	private String strEmailCliente = "";
	private String strTelefonoPrivadoCliente = "";
	private String strTelefonoTrabajoCliente = "";
	private String strTelefonoMovilCliente = "";
	private String strNumeroFaxCliente = "";
	private String strDireccionCliente = "";
	private String strCiudadCliente = "";
	private String strEstadoProvinciaCliente = "";
	private String strCodigoPostalCliente = "";
	private String strCodigoTipologia;
	private String strOficinaVentas = "";
	private String strGrupoVentas = "";
	private String strDescripcionTipologia;
	private BeanClienteEmpleado ClienteEmpleado;
	private List<BeanPlanificacion> listaPlanificacion;
	private String strMarcaBloqueoAlmacen;
	private String indicadorIva;
	private String strCodOrgVentas;
	private String strCodCanalDist;
	private String strCodSector;
	private String strClase;
	public String getStrClase() {
		return strClase;
	}

	public void setStrClase(String strClase) {
		this.strClase = strClase;
	}

	private String strCondicionPagoDefecto;

	public String getStrCondicionPagoDefecto() {
		return strCondicionPagoDefecto;
	}

	public void setStrCondicionPagoDefecto(String strCondicionPagoDefecto) {
		this.strCondicionPagoDefecto = strCondicionPagoDefecto;
	}

	public String getStrCodOrgVentas() {
		return strCodOrgVentas;
	}

	public void setStrCodOrgVentas(String strCodOrgVentas) {
		this.strCodOrgVentas = strCodOrgVentas;
	}

	public String getStrCodCanalDist() {
		return strCodCanalDist;
	}

	public void setStrCodCanalDist(String strCodCanalDist) {
		this.strCodCanalDist = strCodCanalDist;
	}

	public String getStrCodSector() {
		return strCodSector;
	}

	public void setStrCodSector(String strCodSector) {
		this.strCodSector = strCodSector;
	}

	public String getStrMarcaBloqueoAlmacen() {
		return strMarcaBloqueoAlmacen;
	}

	public void setStrMarcaBloqueoAlmacen(String strMarcaBloqueoAlmacen) {
		this.strMarcaBloqueoAlmacen = strMarcaBloqueoAlmacen;
	}

	public List<BeanPlanificacion> getListaPlanificacion() {
		return listaPlanificacion;
	}

	public void setListaPlanificacion(List<BeanPlanificacion> listaPlanificacion) {
		this.listaPlanificacion = listaPlanificacion;
	}

	public String getStrIdCliente() {
		return strIdCliente;
	}

	public void setStrIdCliente(String strIdCliente) {
		this.strIdCliente = strIdCliente;
	}

	public String getStrCompaniaCliente() {
		return strCompaniaCliente;
	}

	public void setStrCompaniaCliente(String strCompaniaCliente) {
		this.strCompaniaCliente = strCompaniaCliente;
	}

	public String getStrNombreCliente() {
		return strNombreCliente;
	}

	public void setStrNombreCliente(String strNombreCliente) {
		this.strNombreCliente = strNombreCliente;
	}

	public String getStrApellidosCliente() {
		return strApellidosCliente;
	}

	public void setStrApellidosCliente(String strApellidosCliente) {
		this.strApellidosCliente = strApellidosCliente;
	}

	public String getStrEmailCliente() {
		return strEmailCliente;
	}

	public void setStrEmailCliente(String strEmailCliente) {
		this.strEmailCliente = strEmailCliente;
	}

	public String getStrTelefonoPrivadoCliente() {
		return strTelefonoPrivadoCliente;
	}

	public void setStrTelefonoPrivadoCliente(String strTelefonoPrivadoCliente) {
		this.strTelefonoPrivadoCliente = strTelefonoPrivadoCliente;
	}

	public String getStrTelefonoTrabajoCliente() {
		return strTelefonoTrabajoCliente;
	}

	public void setStrTelefonoTrabajoCliente(String strTelefonoTrabajoCliente) {
		this.strTelefonoTrabajoCliente = strTelefonoTrabajoCliente;
	}

	public String getStrTelefonoMovilCliente() {
		return strTelefonoMovilCliente;
	}

	public void setStrTelefonoMovilCliente(String strTelefonoMovilCliente) {
		this.strTelefonoMovilCliente = strTelefonoMovilCliente;
	}

	public String getStrNumeroFaxCliente() {
		return strNumeroFaxCliente;
	}

	public void setStrNumeroFaxCliente(String strNumeroFaxCliente) {
		this.strNumeroFaxCliente = strNumeroFaxCliente;
	}

	public String getStrDireccionCliente() {
		return strDireccionCliente;
	}

	public void setStrDireccionCliente(String strDireccionCliente) {
		this.strDireccionCliente = strDireccionCliente;
	}

	public String getStrCiudadCliente() {
		return strCiudadCliente;
	}

	public void setStrCiudadCliente(String strCiudadCliente) {
		this.strCiudadCliente = strCiudadCliente;
	}

	public String getStrEstadoProvinciaCliente() {
		return strEstadoProvinciaCliente;
	}

	public void setStrEstadoProvinciaCliente(String strEstadoProvinciaCliente) {
		this.strEstadoProvinciaCliente = strEstadoProvinciaCliente;
	}

	public String getStrCodigoPostalCliente() {
		return strCodigoPostalCliente;
	}

	public void setStrCodigoPostalCliente(String strCodigoPostalCliente) {
		this.strCodigoPostalCliente = strCodigoPostalCliente;
	}

	public BeanClienteEmpleado getClienteEmpleado() {
		return ClienteEmpleado;
	}

	public void setClienteEmpleado(BeanClienteEmpleado clienteEmpleado) {
		ClienteEmpleado = clienteEmpleado;
	}

	public String getStrCodigoTipologia() {
		return strCodigoTipologia;
	}

	public void setStrCodigoTipologia(String strCodigoTipologia) {
		this.strCodigoTipologia = strCodigoTipologia;
	}

	public String getStrDescripcionTipologia() {
		return strDescripcionTipologia;
	}

	public void setStrDescripcionTipologia(String strDescripcionTipologia) {
		this.strDescripcionTipologia = strDescripcionTipologia;
	}

	public String getIndicadorIva() {
		return indicadorIva;
	}

	public void setIndicadorIva(String indicadorIva) {
		this.indicadorIva = indicadorIva;
	}

	public String getStrOficinaVentas() {
		return strOficinaVentas;
	}

	public void setStrOficinaVentas(String strOficinaVentas) {
		this.strOficinaVentas = strOficinaVentas;
	}

	public String getStrGrupoVentas() {
		return strGrupoVentas;
	}

	public void setStrGrupoVentas(String strGrupoVentas) {
		this.strGrupoVentas = strGrupoVentas;
	}

	@Override
	public String toString() {
		return strCompaniaCliente;
	}
}