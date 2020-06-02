package com.promesa.administracion.sql.impl;

import java.util.HashMap;
import com.promesa.administracion.sql.SqlSerie;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlSerieImpl implements SqlSerie{
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	private String sqlSerie = null;
	private ResultExecuteQuery resultExecuteQuery = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer,HashMap> mapResultado = new HashMap<Integer,HashMap>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String numeroSerie() {
		column = new HashMap();	
		column.put("String:0", NUMERO_SERIE);
		sqlSerie= "SELECT "+NUMERO_SERIE+" FROM "+TABLA;
		String numeroSerie;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlSerie,column,Constante.BD_SYNC);
			mapResultado= resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap)mapResultado.get(0);
			numeroSerie = res.get(NUMERO_SERIE).toString();			 
		} catch (Exception e) {		
			Util.mostrarExcepcion(e);
			numeroSerie = null;			 
		}
		return numeroSerie;
	}
	 
	private static String TABLA = "PROFFLINE_TB_SERIE";	 
	private static String NUMERO_SERIE = "txtNumeroSerie";	 	 
}