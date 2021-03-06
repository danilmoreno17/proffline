package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
//import com.promesa.pedidos.bean.BeanBloqueoEntrega;
//import com.promesa.pedidos.bean.BeanCondicionPago;
import com.promesa.pedidos.bean.BeanJerarquia;
//import com.promesa.pedidos.sql.SqlCondicionPago;
import com.promesa.pedidos.sql.SqlJerarquia;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlJerarquiaImpl implements SqlJerarquia {

	private List<BeanJerarquia> listaJerarquia = null;
	@SuppressWarnings({ "rawtypes" })
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes" })
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	ResultExecute resultExecute = null;
	private String sqlJerarquia = null;
	private boolean resultado = false;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setListaJerarquia() {
		column = new HashMap();
		listaJerarquia = new ArrayList<BeanJerarquia>();
		BeanJerarquia bean = null;
		column.put("String:0", IdPadre);
		column.put("String:1", PRODH);
		column.put("String:2", S);
		column.put("String:3", VTEXT);
		column.put("String:4", ZZSEQ);
		column.put("String:5", ICON);
		column.put("String:6", I);
		column.put("String:7", CELL_DESIGN);

		sqlJerarquia = "SELECT " + IdPadre + "," + PRODH + "," + S + ","
				+ VTEXT + "," + ZZSEQ + "," + ICON + "," + I + ","
				+ CELL_DESIGN + " " + TABLA_JERARQUIA;

		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlJerarquia, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			bean = new BeanJerarquia();

			bean.setStrIdPadre(res.get(IdPadre).toString());
			bean.setStrPRODH(res.get(PRODH).toString());
			bean.setStrVTEXT(res.get(S).toString());
			bean.setStrZZSEQ(res.get(VTEXT).toString());
			bean.setStrICON(res.get(ZZSEQ).toString());
			bean.setStrI(res.get(ICON).toString());
			bean.setStrCellDesign(res.get(I).toString());
			bean.setStrCellDesign(res.get(CELL_DESIGN).toString());
			listaJerarquia.add(bean);
		}
	}

	public void setInsertarJerarquia(List<BeanJerarquia> listaBeanJerarquia) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanJerarquia bean : listaBeanJerarquia) {
			listaSQL.add("INSERT INTO " + TABLA_JERARQUIA + " (" + IdPadre
					+ "," + PRODH + "," + S + "," + VTEXT + "," + ZZSEQ + ","
					+ ICON + "," + I + "," + CELL_DESIGN + ")" + "VALUES ('"
					+ bean.getStrIdPadre() + "','" + bean.getStrPRODH() + "','"
					+ bean.getStrS() + "','" + bean.getStrVTEXT() + "','"
					+ bean.getStrZZSEQ() + "','" + bean.getStrICON() + "','"
					+ bean.getStrI() + "','" + bean.getStrCellDesign() + "') ");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Jerarqu�a", Constante.BD_SYNC);
	}

	public void setEliminarJerarquia() {
		sqlJerarquia = " DELETE FROM " + TABLA_JERARQUIA;
		resultExecute = new ResultExecute(sqlJerarquia, "Jerarqu�a", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarJerarquia() {
		return resultado;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlJerarquia = " SELECT COUNT(*) AS filas FROM " + TABLA_JERARQUIA;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlJerarquia, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	public boolean getInsertarJerarquia() {
		return resultado;
	}

	@Override
	public List<BeanJerarquia> getListaJerarquia() {
		return listaJerarquia;
	}

	private static String TABLA_JERARQUIA = "PROFFLINE_TB_JERARQUIA";
	private String IdPadre = "txtIdPadre";
	private String PRODH = "txtPRODH";
	private String S = "txtS";
	private String VTEXT = "txtVTEXT";
	private String ZZSEQ = "txtZZSEQ";
	private String ICON = "txtICON";
	private String I = "txtI";
	private String CELL_DESIGN = "txtCELLDESIGN";

	@Override
	public String getMarcaVendedor(String marca) {
		String nombre = "";
		column = new HashMap();
		column.put("String:0", VTEXT);
		sqlJerarquia = "SELECT " + VTEXT + " FROM " +TABLA_JERARQUIA+ " WHERE "+S+" = '3' AND "+PRODH+" = '"+marca+"';";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlJerarquia, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			nombre = res.get(VTEXT).toString();
		}
		return nombre;

	}

}
