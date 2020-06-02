package com.promesa.pedidos.bean;

public class BeanFactura {

	private String strNFactura; // NUMERO FACTURA
	private String strFacturaSRI;// FACTURA DEL SRI MARCELO MOYANO 12/05/2013
	private BeanMaterial material; // CODIGO MATERIAL Y DESCRIPCION MATERIAL
	private String strCFactura; // CANTIDAD FACTURADA
	private String strVNeto; // VALOR NETO
	private String strFFactura; // FECHA FACTURA
	private String strPUnitario; // PRECIO UNITARIO
	private String strDCanal; // DESCUENTO CANAL
	private String strD3x; // DESCUENTO 3X
	private String strD4x; // DESCUENTO 4X
	private String strDVolumen; // DESCUENTO VOLUMEN
	private String strD5x; // DESCUENTO CANTIDAD
	private String strDManual; // DESCUENTO MANUAL
	private String strUVentas; // UNIDAD VENTAS
	private String strMarca; // MARCA
	private String strCPedida; // CANTIDAD PEDIDA
	private String strPNeto;

	public String getStrNFactura() {
		return strNFactura;
	}

	public void setStrNFactura(String strNFactura) {
		this.strNFactura = strNFactura;
	}

	public BeanMaterial getMaterial() {
		return material;
	}

	public void setMaterial(BeanMaterial material) {
		this.material = material;
	}

	public String getStrCFactura() {
		return strCFactura;
	}

	public void setStrCFactura(String strCFactura) {
		this.strCFactura = strCFactura;
	}

	public String getStrVNeto() {
		return strVNeto;
	}

	public void setStrVNeto(String strVNeto) {
		this.strVNeto = strVNeto;
	}

	public String getStrFFactura() {
		return strFFactura;
	}

	public void setStrFFactura(String strFFactura) {
		this.strFFactura = strFFactura;
	}

	public String getStrPUnitario() {
		return strPUnitario;
	}

	public void setStrPUnitario(String strPUnitario) {
		this.strPUnitario = strPUnitario;
	}

	public String getStrDCanal() {
		return strDCanal;
	}

	public void setStrDCanal(String strDCanal) {
		this.strDCanal = strDCanal;
	}

	public String getStrD3x() {
		return strD3x;
	}

	public void setStrD3x(String strD3x) {
		this.strD3x = strD3x;
	}

	public String getStrD4x() {
		return strD4x;
	}

	public void setStrD4x(String strD4x) {
		this.strD4x = strD4x;
	}

	public String getStrDVolumen() {
		return strDVolumen;
	}

	public void setStrDVolumen(String strDVolumen) {
		this.strDVolumen = strDVolumen;
	}

	public String getStrD5x() {
		return strD5x;
	}

	public void setStrD5x(String strD5x) {
		this.strD5x = strD5x;
	}

	public String getStrDManual() {
		return strDManual;
	}

	public void setStrDManual(String strDManual) {
		this.strDManual = strDManual;
	}

	public String getStrUVentas() {
		return strUVentas;
	}

	public void setStrUVentas(String strUVentas) {
		this.strUVentas = strUVentas;
	}

	public String getStrMarca() {
		return strMarca;
	}

	public void setStrMarca(String strMarca) {
		this.strMarca = strMarca;
	}

	public String getStrCPedida() {
		return strCPedida;
	}

	public void setStrCPedida(String strCPedida) {
		this.strCPedida = strCPedida;
	}
	//	Marcelo Moyano 12/05/2013
	public String getStrFacturaSRI() {
		return strFacturaSRI;
	}

	public void setStrFacturaSRI(String strFacturaSRI) {
		this.strFacturaSRI = strFacturaSRI;
	}
	//	Marcelo Moyano
	
	@Override
	public String toString() {
		return "BeanFactura [strNFactura=" + strNFactura + ", strFacturaSRI ="+ strFacturaSRI + ", material="
				+ material + ", strCFactura=" + strCFactura + ", strVNeto="
				+ strVNeto + ", strFFactura=" + strFFactura + ", strPUnitario="
				+ strPUnitario + ", strDCanal=" + strDCanal + ", strD3x="
				+ strD3x + ", strD4x=" + strD4x + ", strDVolumen="
				+ strDVolumen + ", strD5x=" + strD5x + ", strDManual="
				+ strDManual + ", strUVentas=" + strUVentas + ", strMarca="
				+ strMarca + ", strCPedida=" + strCPedida + "]";
	}

	public void setStrPNeto(String strPNeto) {
		this.strPNeto = strPNeto;
	}

	public String getStrPNeto() {
		return strPNeto;
	}

}