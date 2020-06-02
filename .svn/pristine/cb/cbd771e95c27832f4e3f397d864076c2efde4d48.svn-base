package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.BeanCondicionExpedicion;
import com.promesa.pedidos.sql.SqlCondicionExpedicion;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlCondicionExpedicionImpl implements SqlCondicionExpedicion {

	private String sqlCondicionExpedicion = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	private List<BeanCondicionExpedicion> listaCondicionExpedicion = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaCondicionExpedicion() {
		column = new HashMap();
		listaCondicionExpedicion = new ArrayList<BeanCondicionExpedicion>();
		column.put("String:0", ID_CONDICION);
		column.put("String:1", DESCRIPCION);
		sqlCondicionExpedicion = "SELECT * FROM PROFFLINE_TB_CONDICION_EXPEDICION WHERE txtIdCondicion='01' OR txtIdCondicion='04'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCondicionExpedicion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			BeanCondicionExpedicion bean = new BeanCondicionExpedicion();
			res = (HashMap) mapResultado.get(i);
			bean.setStrIdCondicionExpedicion(res.get(ID_CONDICION).toString());
			bean.setStrDescripcion(res.get(DESCRIPCION).toString());
			listaCondicionExpedicion.add(bean);
		}
	}

	@Override
	public List<BeanCondicionExpedicion> getListaCondicionExpedicion() {
		return listaCondicionExpedicion;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlCondicionExpedicion = " SELECT COUNT(*) AS filas FROM " + TABLA_CONDICION_EXPEDICION;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlCondicionExpedicion,column,Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	private static String ID_CONDICION = "txtIdCondicion";
	private static String DESCRIPCION = "txtDescripcion";
	private static String TABLA_CONDICION_EXPEDICION = "PROFFLINE_TB_CONDICION_EXPEDICION";
}
