package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanFrecuencia;
import com.promesa.planificacion.sql.SqlFrecuencia;
import com.promesa.util.Constante;

public class SqlFrecuenciaImpl implements SqlFrecuencia {

	private String sqlFrecuencia = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanFrecuencia frecuencia = null;
	private List<BeanFrecuencia> listaFrecuencia = null;
	@SuppressWarnings("unused")
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;
	@SuppressWarnings("unused")
	private ResultExecuteQuery resultExecuteQuery = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaFrecuencia(BeanFrecuencia frecuencia) {
		column = new HashMap();
		listaFrecuencia = new ArrayList<BeanFrecuencia>();
		column.put("String:1", "numIdFrecuencia");
		column.put("String:2", "numFrecuencia");
		column.put("String:3", "txtFrecuencia");
		sqlFrecuencia = "SELECT numIdFrecuencia,numFrecuencia,txtFrecuencia FROM PROFFLINE_TB_FRECUENCIA";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlFrecuencia, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.frecuencia = new BeanFrecuencia();
			res = (HashMap) mapResultado.get(i);
			this.frecuencia.setStrIdFrecuencia(getData(res.get("numIdFrecuencia")));
			this.frecuencia.setStrNumFrecuencia(getData(res.get("numFrecuencia")));
			this.frecuencia.setStrFrecuencia(getData(res.get("txtFrecuencia")));
			listaFrecuencia.add(this.frecuencia);
		}
	}

	public String getData(Object obj) {
		String result = "";
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	public List<BeanFrecuencia> getListaFrecuencia() {
		return listaFrecuencia;
	}
}