package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanFeriado;
import com.promesa.planificacion.sql.SqlFeriado;
import com.promesa.util.Constante;

public class SqlFeriadoImpl implements SqlFeriado {

	private String sqlFeriado = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanFeriado feriado = null;
	private List<BeanFeriado> listaFeriado = null;
	private boolean resultado;
	private ResultExecute resultExecute = null;

	/* VALIDA FERIADO */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean esFeriado(String fecha) {
		resultado = false;
		column = new HashMap();
		listaFeriado = new ArrayList<BeanFeriado>();
		column.put("String:0", DIA_FERIADO);
		column.put("String:1", MES_FERIADO);
		sqlFeriado = "SELECT " + DIA_FERIADO + "," + MES_FERIADO + " FROM " + TABLA;
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlFeriado, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.feriado = new BeanFeriado();
			res = (HashMap) mapResultado.get(i);
			this.feriado.setStrDiaFeriado(res.get(DIA_FERIADO).toString());
			this.feriado.setStrMesFeriado(res.get(MES_FERIADO).toString());
			listaFeriado.add(this.feriado);
		}
		for (int j = 0; j < listaFeriado.size(); j++) {
			if ((fecha.charAt(0) + "" + fecha.charAt(1) + "" + fecha.charAt(3) + "" + fecha.charAt(4)).equals(((BeanFeriado) listaFeriado
					.get(j)).getStrDiaFeriado() + "" + ((BeanFeriado) listaFeriado.get(j)).getStrMesFeriado())) {
				resultado = true;
				break;
			}
		}
		return resultado;
	}

	public void setInsertarFeriado(BeanFeriado feriado) {
		sqlFeriado = "INSERT INTO " + TABLA + " VALUES ('" + feriado.getStrSigFeriado() + "'," + "'" + feriado.getStrMesFeriado() 
				+ "'," + "'" + feriado.getStrDiaFeriado() + "'," + "'" + feriado.getStrOriFeriado() + "')";
		ResultExecute resultExecute = new ResultExecute(sqlFeriado, "", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getInsertarFeriado() {
		return resultado;
	}

	public void setEliminarFeriado() {
		sqlFeriado = "DELETE FROM " + TABLA;
		resultExecute = new ResultExecute(sqlFeriado, "", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarFeriado() {
		return resultado;
	}

	private static String TABLA = "PROFFLINE_TB_FERIADO";
	@SuppressWarnings("unused")
	private static String SIGLA_FERIADO = "txtSigFeriado";
	private static String MES_FERIADO = "txtMesFeriado";
	private static String DIA_FERIADO = "txtDiaFeriado";
	@SuppressWarnings("unused")
	private static String ORIGEN_FERIADO = "txtOriFeriado";

}