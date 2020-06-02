package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
//import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanTipologia;
import com.promesa.pedidos.sql.SqlTipologia;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlTipologiaImpl implements SqlTipologia {

	private List<BeanTipologia> listaTipologias = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private String sqlTipologia = null;
	private boolean resultado = false;
	private ResultExecute resultExecute = null;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaTipologia() {
		column = new HashMap();
		listaTipologias = new ArrayList<BeanTipologia>();
		column.put("String:0", MTAR);
		column.put("String:1", MTBEZ);
		sqlTipologia = "SELECT " + MTAR + "," + MTBEZ + " " + "FROM " + TABLA_TIPOLOGIA + " order by MTBEZ";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery( sqlTipologia, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanTipologia beanTipologia = new BeanTipologia(res.get(MTAR).toString(), res.get(MTBEZ).toString());
			listaTipologias.add(beanTipologia);
		}
	}

	public void setInsertarTipologia(List<BeanTipologia> listaBeanTipologia) {
		final List<String> listaSQL = new ArrayList<String>(); 
		for (BeanTipologia bean : listaBeanTipologia) {
			listaSQL.add("INSERT INTO " + TABLA_TIPOLOGIA + " (" + MTAR + ","
					+ MTBEZ + ")" + "VALUES ('" + bean.getTxtMTAR() + "','" + bean.getTxtMTBEZ() + "') ");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Tipologías", Constante.BD_SYNC);
	}

	public void setEliminarTipologia() {
		sqlTipologia = " DELETE FROM " + TABLA_TIPOLOGIA;
		resultExecute = new ResultExecute(sqlTipologia, "Tipologías", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarTipologia() {
		return resultado;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlTipologia = " SELECT COUNT(*) AS filas FROM " + TABLA_TIPOLOGIA;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlTipologia, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	public boolean getInsertarTipologia() {
		return resultado;
	}

	@Override
	public List<BeanTipologia> getListaTipologia() {
		return listaTipologias;
	}

	private static String TABLA_TIPOLOGIA = "PROFFLINE_TB_TIPOLOGIA";
	private String MTAR = "MTAR";
	private String MTBEZ = "MTBEZ";
}
