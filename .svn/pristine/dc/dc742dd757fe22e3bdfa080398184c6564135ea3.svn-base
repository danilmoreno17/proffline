package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanEmpleado;
import com.promesa.planificacion.sql.SqlEmpleado;
import com.promesa.util.Constante;

public class SqlEmpleadoImpl implements SqlEmpleado {

	private String sqlEmpleado = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes", "unused" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanEmpleado empleado = null;
	private List<BeanEmpleado> listaEmpleado = null;
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;

	/* VENDEDORES POR CLIENTE */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaVendedor(String idCliente) {
		column = new HashMap();
		listaEmpleado = new ArrayList<BeanEmpleado>();
		column.put("String:0", ID_EMPLEADO);
		column.put("String:1", NOMBRE_EMPLEADO);
		sqlEmpleado = "SELECT " + "a." + ID_EMPLEADO + "," + "a." + NOMBRE_EMPLEADO + " FROM " + TABLA_EMPLEADO + " a,"
				+ TABLA_EMPLEADO_CLIENTE + " b " + "WHERE " + "a." + ID_EMPLEADO + " = b." + ID_EMPLEADO + " AND " + "b." + ID_CLIENTE + " = '" + idCliente + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlEmpleado, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.empleado = new BeanEmpleado();
			res = (HashMap) mapResultado.get(i);
			this.empleado.setStrIdEmpleado(res.get(ID_EMPLEADO).toString());
			this.empleado.setStrNombreEmpleado(res.get(NOMBRE_EMPLEADO).toString());
			listaEmpleado.add(this.empleado);
		}
	}

	public List<BeanEmpleado> getListaVendedor() {
		return listaEmpleado;
	}

	/* VENDEDORES POR SUPERVISOR */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaEmpleado(BeanEmpleado empleado) {
		column = new HashMap();
		listaEmpleado = new ArrayList<BeanEmpleado>();
		column.put("String:0", ID_EMPLEADO);
		column.put("String:1", NOMBRE_EMPLEADO);
		column.put("String:2", ID_SUPERVISOR);
		sqlEmpleado = "SELECT DISTINCT " + ID_EMPLEADO + "," + NOMBRE_EMPLEADO + "," + ID_SUPERVISOR + " FROM " + TABLA_EMPLEADO + " WHERE "
				+ ID_SUPERVISOR + "='" + empleado.getStrIdSupervisor() + "' ";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlEmpleado, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.empleado = new BeanEmpleado();
			res = (HashMap) mapResultado.get(i);
			this.empleado.setStrIdEmpleado(res.get(ID_EMPLEADO).toString());
			this.empleado.setStrNombreEmpleado(res.get(NOMBRE_EMPLEADO).toString());
			this.empleado.setStrIdSupervisor(res.get(ID_SUPERVISOR).toString());
			listaEmpleado.add(this.empleado);
		}
	}

	public List<BeanEmpleado> getListaEmpleado() {
		return listaEmpleado;
	}

	/*
	 * MÉTODO QUE REGISTRA EMPLEADOS COMO PARTE DE LA SINCRONIZACIÓN DEL MÓDULO
	 * DE PLANIFICACIÓN
	 */
	public void setInsertarEmpleado(String idEmp, String nomEmp, String ID) {
		sqlEmpleado = "INSERT INTO " + TABLA_EMPLEADO + " (" + ID_EMPLEADO + "," + NOMBRE_EMPLEADO + "," + ID_SUPERVISOR + ") "
				+ "   VALUES (" + "'" + idEmp + "','" + nomEmp + "','" + ID + "');";
		ResultExecute resultExecute = new ResultExecute(sqlEmpleado, "Empleados", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	/* MÉTODO QUE VERIFICA SI EXISTE O NO REGISTRO DE EMPLEADOS */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificaEmpleado(String codEmp) {
		boolean resultado = false;
		column = new HashMap();
		String codigo = Constante.VACIO;
		column.put("String:0", ID_EMPLEADO);
		sqlEmpleado = "SELECT " + ID_EMPLEADO + " FROM " + TABLA_EMPLEADO + " WHERE " + ID_EMPLEADO + " = '" + codEmp + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlEmpleado, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			codigo = res.get(ID_EMPLEADO).toString();
		}
		if (!codigo.equals(Constante.VACIO)) {
			resultado = true;
		}
		return resultado;
	}

	public void setInsertarEmpleadoM2(List<String> listaCliente) {
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaCliente, "Empleados", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getInsertarEmpleado() {
		return resultado;
	}

	public void setEliminarEmpleado() {
		sqlEmpleado = "DELETE FROM " + TABLA_EMPLEADO;
		ResultExecute resultExecute = new ResultExecute(sqlEmpleado, "Empleados", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarEmpleado() {
		return resultado;
	}

	private static String TABLA_EMPLEADO = "PROFFLINE_TB_EMPLEADO";
	private static String ID_EMPLEADO = "txtIdEmpleado";
	private static String NOMBRE_EMPLEADO = "txtNombreEmpleado";
	private static String ID_SUPERVISOR = "txtIdSupervisor";
	private static String TABLA_EMPLEADO_CLIENTE = "PROFFLINE_TB_EMPLEADO_CLIENTE";
	private static String ID_CLIENTE = "txtIdCliente";

}