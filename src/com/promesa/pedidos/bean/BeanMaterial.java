/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promesa.pedidos.bean;

/**
 * 
 * @author myguel
 */
@SuppressWarnings("rawtypes")
public class BeanMaterial implements Comparable {

	private String idMaterial;
	private String stock;
	private String un;
	private String descripcion;
	private String text_line;
	private String target_qty;
	private String price_1;
	private String price_2;
	private String price_3;
	private String price_4;
	private String prdha;
	private String tipoMaterial;
	private String normt;
	private String zzordco;
	private String cell_desing;
	private String mtart;
	private String typeMat;
	private String grupo_compra;
	private String st_1;
	private String tipologia;

	private String cell_design ;
	private String tipoConsultaDinamica;
	private Double dblAcumulado;
	private Double dblPromedio;
	private String strCodCliente;

	private int tipoAccion;

	private String strRegCondicion;
	private Double dblValorRango;
	private String strValorCondicion;
	private String strTipoRango;
	private String marcadoParaIva;
	private String busqueda;
	private boolean booleanToString = false;
	private String strFec_Ing;
	private String strMargen_Obj;
	
	private String strVentasAcumulado;
	private String strVentasPromedio;
	private String strCliente;
	private String strVentaReal;
	
	private Double cantSug;
	
	private String divisionCliente;
	private String descMercadeo;
	
	public String getDivisionCliente() {
		return divisionCliente;
	}

	public void setDivisionCliente(String divisionCliente) {
		this.divisionCliente = divisionCliente;
	}

	public String getDescMercadeo() {
		return descMercadeo;
	}

	public void setDescMercadeo(String descMercadeo) {
		this.descMercadeo = descMercadeo;
	}

	public Double getCantSug() {
		return cantSug;
	}

	public void setCantSug(Double cantSug) {
		this.cantSug = cantSug;
	}

	public String getStrVentaReal() {
		return strVentaReal;
	}

	public void setStrVentaReal(String strVentaReal) {
		this.strVentaReal = strVentaReal;
	}

	public String getStrVentasAcumulado() {
		return strVentasAcumulado;
	}

	public void setStrVentasAcumulado(String strVentasAcumulado) {
		this.strVentasAcumulado = strVentasAcumulado;
	}

	public String getStrVentasPromedio() {
		return strVentasPromedio;
	}

	public void setStrVentasPromedio(String strVentasPromedio) {
		this.strVentasPromedio = strVentasPromedio;
	}

	public String getStrCliente() {
		return strCliente;
	}

	public void setStrCliente(String strCliente) {
		this.strCliente = strCliente;
	}
	
	public String getStrMargen_Obj() {
		return strMargen_Obj;
	}

	public void setStrMargen_Obj(String strMargen_Obj) {
		this.strMargen_Obj = strMargen_Obj;
	}

	public String getStrFec_Ing() {
		return strFec_Ing;
	}

	public void setStrFec_Ing(String strFec_Ing) {
		this.strFec_Ing = strFec_Ing;
	}

	public String getStrCosto() {
		return strCosto;
	}

	public void setStrCosto(String strCosto) {
		this.strCosto = strCosto;
	}

	private String strCosto;

	public boolean isBooleanToString() {
		return booleanToString;
	}

	public void setBooleanToString(boolean booleanToString) {
		this.booleanToString = booleanToString;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setStrBusqueda(String strBusqueda) {
		this.busqueda = strBusqueda;
	}

	public String getStrTipoRango() {
		return strTipoRango;
	}

	public void setStrTipoRango(String strTipoRango) {
		this.strTipoRango = strTipoRango;
	}

	public Double getDblValorRango() {
		return dblValorRango;
	}

	public void setDblValorRango(Double dblValorRango) {
		this.dblValorRango = dblValorRango;
	}

	public String getStrValorCondicion() {
		return strValorCondicion;
	}

	public void setStrValorCondicion(String strValorCondicion) {
		this.strValorCondicion = strValorCondicion;
	}

	public String getStrRegCondicion() {
		return strRegCondicion;
	}

	public void setStrRegCondicion(String strRegCondicion) {
		this.strRegCondicion = strRegCondicion;
	}

	public String getStrCodCliente() {
		return strCodCliente;
	}

	public void setStrCodCliente(String strCodCliente) {
		this.strCodCliente = strCodCliente;
	}

	public Double getDblAcumulado() {
		return dblAcumulado;
	}

	public void setDblAcumulado(Double dblAcumulado) {
		this.dblAcumulado = dblAcumulado;
	}

	public Double getDblPromedio() {
		return dblPromedio;
	}

	public void setDblPromedio(Double dblPromedio) {
		this.dblPromedio = dblPromedio;
	}

	public String getTipoConsultaDinamica() {
		return tipoConsultaDinamica;
	}

	public void setTipoConsultaDinamica(String tipoConsultaDinamica) {
		this.tipoConsultaDinamica = tipoConsultaDinamica;
	}

	public BeanMaterial() {
		this.idMaterial = "";
		this.descripcion = "";
		this.text_line = "";
		this.normt = "";
		this.prdha = "";
		this.tipologia = "";
	}

	public BeanMaterial(String idMaterial, String stock, String un,
			String descripcion, String text_line, String target_qty,
			String price_1, String price_2, String price_3, String price_4,
			String prdha, String tipoMaterial, String normt, String zzordco,
			String cell_design, String mtart, String typeMat,
			String grupo_compra, String st_1, int tipoAccion, String fec_ing, String costo, String margen_obj) {
		this.idMaterial = idMaterial;
		this.stock = stock;
		this.un = un;
		this.descripcion = corregirTexto(descripcion);
		this.text_line = text_line;
		this.target_qty = target_qty;
		this.price_1 = price_1;
		this.price_2 = price_2;
		this.price_3 = price_3;
		this.price_4 = price_4;
		this.prdha = prdha;
		this.tipoMaterial = tipoMaterial;
		this.normt = normt;
		this.zzordco = zzordco;
		this.cell_design = cell_design;
		this.mtart = mtart;
		this.typeMat = typeMat;
		this.grupo_compra = grupo_compra;
		this.st_1 = st_1;
		this.tipologia = typeMat;
		this.tipoAccion = tipoAccion;
		this.strFec_Ing = fec_ing;
		this.strCosto = costo;
		this.strMargen_Obj = margen_obj;
	}

	public String getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getText_line() {
		return text_line;
	}

	public void setText_line(String text_line) {
		this.text_line = text_line;
	}

	public String getTarget_qty() {
		return target_qty;
	}

	public void setTarget_qty(String target_qty) {
		this.target_qty = target_qty;
	}

	public String getPrice_1() {
		return price_1;
	}

	public void setPrice_1(String price_1) {
		this.price_1 = price_1;
	}

	public String getPrice_2() {
		return price_2;
	}

	public void setPrice_2(String price_2) {
		this.price_2 = price_2;
	}

	public String getPrice_3() {
		return price_3;
	}

	public void setPrice_3(String price_3) {
		this.price_3 = price_3;
	}

	public String getPrice_4() {
		return price_4;
	}

	public void setPrice_4(String price_4) {
		this.price_4 = price_4;
	}

	public String getPrdha() {
		return prdha;
	}

	public void setPrdha(String prdha) {
		this.prdha = prdha;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public String getNormt() {
		return normt;
	}

	public void setNormt(String normt) {
		this.normt = normt;
	}

	public String getZzordco() {
		return zzordco;
	}

	public void setZzordco(String zzordco) {
		this.zzordco = zzordco;
	}

	public String getCell_design() {
		return cell_design;
	}

	public void setCell_design(String cell_design) {
		this.cell_design = cell_design;
	}

	public String getTypeMat() {
		return typeMat;
	}

	public void setTypeMat(String typeMat) {
		this.typeMat = typeMat;
	}

	public String getCell_desing() {
		return cell_desing;
	}

	public void setCell_desing(String cell_desing) {
		this.cell_desing = cell_desing;
	}

	public String getMtart() {
		return mtart;
	}

	public void setMtart(String mtart) {
		this.mtart = mtart;
	}

	public String getGrupo_compra() {
		return grupo_compra;
	}

	public void setGrupo_compra(String grupo_compra) {
		this.grupo_compra = grupo_compra;
	}

	public String getSt_1() {
		return st_1;
	}

	public void setSt_1(String st_1) {
		this.st_1 = st_1;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public int getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(int tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public String getMarcadoParaIva() {
		return marcadoParaIva;
	}

	public void setMarcadoParaIva(String marcadoParaIva) {
		this.marcadoParaIva = marcadoParaIva;
	}

	public String corregirTexto(String text) {

		while (text.indexOf("'") != -1) {
			text = text.substring(0, text.indexOf("'")) + "\""
					+ text.substring(text.indexOf("'") + 1, text.length());
		}

		while (text.indexOf("\"") != -1) {
			text = text.substring(0, text.indexOf("\"")) + "''"
					+ text.substring(text.indexOf("\"") + 1, text.length());
		}

		return text;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		BeanMaterial aux = (BeanMaterial) arg0;
		return -aux.getDescripcion().compareTo(descripcion);
	}

	@Override
	public String toString() {
		if (booleanToString) {
			if (busqueda.length() > 90) {
				return busqueda.substring(0, 90) + "...";
			}
			return busqueda;
		}else {
			return descripcion;
		}
	}

}
