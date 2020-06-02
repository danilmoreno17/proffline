package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanPlanificacion;
import com.promesa.planificacion.sql.SqlPlanificacion;
import com.promesa.util.Constante;
import com.promesa.util.FechaHora;
import com.promesa.util.GenerarId;
import com.promesa.util.Util;

@SuppressWarnings("unused")
public class SqlPlanificacionImpl implements SqlPlanificacion {

	private String sqlPlanificacion = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes" })
	private HashMap getMap = new HashMap();
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanPlanificacion planificacion = null;
	private List<BeanPlanificacion> listaPlanificacion = null;
	private boolean resultado = false;
	private ResultExecute resultExecute = null;
	private ResultExecuteQuery resultExecuteQuery = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaPlanificacion(BeanPlanificacion planificacion) {
		column = new HashMap();
		listaPlanificacion = new ArrayList<BeanPlanificacion>();
		column.put("String:0", ID_PLAN);
		column.put("String:1", ID_VENDEDOR);
		column.put("String:2", ID_JEFE);
		column.put("String:3", ID_CLIENTE);
		column.put("String:4", FECHA_INICIO);
		column.put("String:5", FECHA_FIN);
		column.put("String:6", HORA);
		column.put("String:7", NOMBRE_CLIENTE);
		column.put("String:8", ID_FRECUENCIA);
		column.put("String:9", MANDANTE);
		column.put("String:10", FRECUENCIA);
		sqlPlanificacion = " SELECT " + "TP." + ID_PLAN + "," + "TP." + ID_VENDEDOR + "," + "TP." + ID_JEFE + "," + "TP."
				+ ID_CLIENTE + "," + "TP." + FECHA_INICIO + "," + "TP." + FECHA_FIN + "," + "TP." + HORA + "," + "TP." + NOMBRE_CLIENTE
				+ "," + "TP." + ID_FRECUENCIA + "," + "TP." + MANDANTE + "," + "TF." + FRECUENCIA + " FROM " + TABLA_PLANIFICACION + " TP "
				+ " INNER JOIN " + TABLA_FRECUENCIA + " TF " + " ON TP." + ID_FRECUENCIA + " = " + "TF." + ID_FRECUENCIA;
		if (!planificacion.getStrIdVendedor().equalsIgnoreCase("")) {
			sqlPlanificacion += " AND TP." + ID_VENDEDOR + " = '" + planificacion.getStrIdVendedor() + "'";
		}
		if (!planificacion.getStrIdCliente().equalsIgnoreCase("")) {
			sqlPlanificacion += " AND TP." + ID_CLIENTE + " = '" + planificacion.getStrIdCliente() + "'";
		}
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlPlanificacion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.planificacion = new BeanPlanificacion();
			res = (HashMap) mapResultado.get(i);
			this.planificacion.setStrIdPlan(getData(res.get(ID_PLAN).toString()));
			this.planificacion.setStrIdVendedor(getData(res.get(ID_VENDEDOR).toString()));
			this.planificacion.setStrIdJefe(getData(res.get(ID_JEFE).toString()));
			this.planificacion.setStrIdCliente(getData(res.get(ID_CLIENTE).toString()));
			this.planificacion.setStrFechaInicio(getData(res.get(FECHA_INICIO).toString()));
			this.planificacion.setStrFechaFin(getData(res.get(FECHA_FIN).toString()));
			this.planificacion.setStrHora(getData(res.get(HORA).toString()));
			this.planificacion.setStrNombreCliente(getData(res.get(NOMBRE_CLIENTE).toString()));
			this.planificacion.setStrIdFrecuencia(getData(res.get(ID_FRECUENCIA).toString()));
			this.planificacion.setStrMandante(getData(res.get(MANDANTE).toString()));
			this.planificacion.setStrFrecuencia(getData(res.get(FRECUENCIA).toString()));
			listaPlanificacion.add(this.planificacion);
		}
	}

	public List<BeanPlanificacion> getListaPlanificacion() {
		return listaPlanificacion;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getIdPlan(String idCli) {
		column = new HashMap();
		column.put("String:0", "ID");
		sqlPlanificacion = "SELECT MAX(" + ID_PLAN + ") AS ID FROM " + TABLA_PLANIFICACION + " WHERE " + ID_CLIENTE + " = '" + idCli + "'";
		try {
			ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlPlanificacion, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			if (res.get("ID") == null) {
				return "1";
			} else {
				return String.valueOf(Integer.parseInt(res.get("ID").toString()) + 1);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public String setInsertarPlanificacion(BeanPlanificacion planificacion) {
		FechaHora objFH = new FechaHora();
		sqlPlanificacion = "INSERT INTO " + TABLA_PLANIFICACION + " VALUES " + "(" + "'" + planificacion.getStrIdPlan() + "','"
				+ planificacion.getStrIdVendedor() + "','" + planificacion.getStrIdJefe() + "','"
				+ planificacion.getStrIdCliente() + "','" + planificacion.getStrFechaInicio() + "','"
				+ planificacion.getStrFechaFin() + "','"
				+ planificacion.getStrHora() + "','" + planificacion.getStrNombreCliente() + "',"
				+ planificacion.getStrIdFrecuencia() + ",'" + planificacion.getStrMandante() + "');";
		try {
			ResultExecute resultExecute = new ResultExecute(sqlPlanificacion,"Planificaciones", Constante.BD_SYNC);
			resultado = resultExecute.isFlag();
			return planificacion.getStrIdPlan();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
			return null;
		}
	}

	public boolean getInsertarPlanificacion() {
		return resultado;
	}

	public void setActualizarPlanificacion(BeanPlanificacion planificacion) {
		sqlPlanificacion = "UPDATE " + TABLA_PLANIFICACION + " SET " + FECHA_INICIO + " = '" + planificacion.getStrFechaInicio()
				+ "', " + HORA + " = '" + planificacion.getStrHora() + "', " + ID_FRECUENCIA + " = " + planificacion.getStrIdFrecuencia()
				+ " " + "WHERE " + ID_PLAN + " = '" + planificacion.getStrIdPlan() + "' " + "AND " + ID_CLIENTE
				+ " = '" + planificacion.getStrIdCliente() + "'";
		ResultExecute resultExecute = new ResultExecute(sqlPlanificacion, "Planificaciones", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getActualizarPlanificacion() {
		return resultado;
	}

	public void setEliminarPlanificacion(BeanPlanificacion planificacion) {
		sqlPlanificacion = "DELETE FROM " + TABLA_PLANIFICACION + " WHERE " + ID_PLAN + " = '" + planificacion.getStrIdPlan() + "' AND "
				+ ID_CLIENTE + " = '" + planificacion.getStrIdCliente() + "'";
		ResultExecute resultExecute = new ResultExecute(sqlPlanificacion, "Planificaciones", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarPlanificacion() {
		sqlPlanificacion = "DELETE FROM " + TABLA_PLANIFICACION + "  ";
		ResultExecute resultExecute = new ResultExecute(sqlPlanificacion, "Planificaciones", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarPlanificacion() {
		return resultado;
	}

	public String getData(Object obj) {
		String result = "";
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	private static String TABLA_PLANIFICACION = "PROFFLINE_TB_PLANIFICACION";
	private static String ID_PLAN = "txtIdPlan";
	private static String ID_VENDEDOR = "txtIdVendedor";
	private static String ID_JEFE = "txtIdJefe";
	private static String ID_CLIENTE = "txtIdCliente";
	private static String FECHA_INICIO = "txtFechaInicio";
	private static String FECHA_FIN = "txtFechaFin";
	private static String HORA = "txtHora";
	private static String NOMBRE_CLIENTE = "txtNombreCliente";
	private static String ID_FRECUENCIA = "numIdFrecuencia";
	private static String MANDANTE = "txtMandante";
	private static String TABLA_FRECUENCIA = "PROFFLINE_TB_FRECUENCIA";
	private static String FRECUENCIA = "txtFrecuencia";
}