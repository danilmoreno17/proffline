package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.BeanSede;
import com.promesa.pedidos.sql.SqlSede;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlSedeImpl implements SqlSede {
	private boolean resultado = false;
	private String sqlSede = "";
	@SuppressWarnings("rawtypes")
	private HashMap column = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = null;
	private List<BeanSede> listaSede = null;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setListarSede() {
		listaSede = new ArrayList<BeanSede>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanSede sede = null;
		column = new HashMap();
		column.put("String:0", CODIGO);
		column.put("String:1", DIRECCION);
		sqlSede = "SELECT " + CODIGO + "," + DIRECCION + " FROM " + TABLA_SEDE;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlSede, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			for (HashMap res : mapResultado.values()) {
				sede = new BeanSede();
				sede.setCodigo(res.get(CODIGO).toString());
				sede.setDireccion(res.get(DIRECCION).toString());
				listaSede.add(sede);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setListarSede(String cliente) {
		listaSede = new ArrayList<BeanSede>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanSede sede = null;
		column = new HashMap();
		column.put("String:0", CODIGO);
		column.put("String:1", DIRECCION);
		sqlSede = "SELECT " + CODIGO + "," + DIRECCION + " FROM " + TABLA_SEDE + " WHERE txtIdCliente='" + cliente + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlSede, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			for (HashMap res : mapResultado.values()) {
				sede = new BeanSede();
				sede.setCodigo(res.get(CODIGO).toString());
				sede.setDireccion(res.get(DIRECCION).toString());
				listaSede.add(sede);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	public void setInsertarSede(List<BeanSede> lists) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanSede s : lists) {
			listaSQL.add("INSERT INTO " + TABLA_SEDE + " (" + IDCLIENTE + "," + CODIGO + "," + DIRECCION + ")" + " VALUES ('"
					+ s.getIdCliente() + "','" + s.getCodigo() + "','" + s.getDireccion() + "')");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Destinatarios", Constante.BD_SYNC);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlSede = " SELECT COUNT(*) AS filas FROM " + TABLA_SEDE;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlSede, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	public void setEliminarSede() {
		sqlSede = " DELETE FROM " + TABLA_SEDE;
		ResultExecute resultExecute = new ResultExecute(sqlSede, "Destinatarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean isEliminarSede() {
		return resultado;
	}

	public List<BeanSede> getListaSede() {
		return listaSede;
	}
	private static String TABLA_SEDE = "PROFFLINE_TB_SEDE";
	private static String IDCLIENTE = "txtIdCliente";
	private static String CODIGO = "txtCodigo";
	private static String DIRECCION = "txtDireccion";
}
