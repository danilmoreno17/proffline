package com.promesa.devoluciones.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.devoluciones.bean.BeanMotivoDevolucion;
import com.promesa.devoluciones.sql.SqlMotivoDevolucion;
import com.promesa.util.Constante;

public class SqlMotivoDevolucionImpl implements SqlMotivoDevolucion {

	private String sqlDevolucion = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	private List<BeanMotivoDevolucion> listaMotivoDevolucion = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sMotivoDevolucion() {
		BeanMotivoDevolucion motivo;
		column = new HashMap();
		listaMotivoDevolucion = new ArrayList<BeanMotivoDevolucion>();
		column.put("String:0", MANDANTE);
		column.put("String:1", IDIOMA);
		column.put("String:2", CODIGO_MOTIVO_DEVOLUCION);
		column.put("String:3", MOTIVO_DEVOLUCION);
		
		sqlDevolucion = " SELECT " + MANDANTE + "," + IDIOMA + ","
				+ CODIGO_MOTIVO_DEVOLUCION + "," + MOTIVO_DEVOLUCION + " FROM " + TABLA_EMPLEADO_MOTIVO_DEVOLUCION;	
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDevolucion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			motivo = new BeanMotivoDevolucion();
			res = (HashMap) mapResultado.get(i);
			motivo.setStrMandante(getData(res.get(MANDANTE)));
			motivo.setStrIdioma(getData(res.get(IDIOMA)));
			motivo.setStrCodMotDev(getData(res.get(CODIGO_MOTIVO_DEVOLUCION)));
			motivo.setStrMotDev(getData(res.get(MOTIVO_DEVOLUCION)));
			listaMotivoDevolucion.add(motivo);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerDescripcionMotivoDevolucion(String codigo) {
		column = new HashMap();
		listaMotivoDevolucion = new ArrayList<BeanMotivoDevolucion>();
		column.put("String:0", MOTIVO_DEVOLUCION);
		sqlDevolucion = "SELECT " + MOTIVO_DEVOLUCION + " FROM "
				+ TABLA_EMPLEADO_MOTIVO_DEVOLUCION + " WHERE " + CODIGO_MOTIVO_DEVOLUCION + "='" +codigo+"'";
		
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDevolucion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		String motivoDevolucion = "";
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			motivoDevolucion = getData(res.get(MOTIVO_DEVOLUCION));
		}
		return motivoDevolucion;
	}
	
	public String getData(Object obj) {
		String result = Constante.VACIO;
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	@Override
	public List<BeanMotivoDevolucion> gMotivoDevolucion() {
		return listaMotivoDevolucion;
	}
	private static String TABLA_EMPLEADO_MOTIVO_DEVOLUCION = "PROFFLINE_TB_MOTIVO_DEVOLUCION";
	private static String MANDANTE = "txtMandante";
	private static String IDIOMA = "txtIdioma";
	private static String CODIGO_MOTIVO_DEVOLUCION = "txtCodMotDev";
	private static String MOTIVO_DEVOLUCION = "txtMotDev";
}