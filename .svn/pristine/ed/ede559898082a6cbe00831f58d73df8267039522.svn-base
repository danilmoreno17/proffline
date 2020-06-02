package com.promesa.planificacion.sql.impl;

import java.util.HashMap;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlClienteEmpleadoImpl {

	private String sqlClienteEmpleado = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private boolean resultado = false;

	/*
	 * MÉTODO QUE REGISTRA CÓDIGOS DE CLIENTES Y EMPLEADOS COMO PARTE DE LA
	 * SINCRONIZACIÓN DEL MÓDULO DE PLANIFICACIÓN
	 */
	public void setInsertarClienteEmpleado(String idCli, String idEmp) {
		sqlClienteEmpleado = "INSERT INTO " + TABLA_EMPLEADO_CLIENTE + " VALUES ('" + idEmp + "','" + idCli + "');";
		ResultExecute resultExecute = new ResultExecute(sqlClienteEmpleado, "Empleados-Clientes",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	/*
	 * MÉTODO QUE VERIFICA SI EXISTE O NO REGISTRO DE CÓDIGOS DE CLIENTES Y
	 * EMPLEADOS
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificaClienteEmpleado(String codEmp, String codCli) {
		boolean resultado = false;
		column = new HashMap();
		String IE = Constante.VACIO;
		String IC = Constante.VACIO;
		column.put("String:0", CADENA);
		sqlClienteEmpleado = "SELECT " + ID_EMPLEADO + "||','||" + ID_CLIENTE + " AS " + CADENA + " FROM " + TABLA_EMPLEADO_CLIENTE
				+ " WHERE " + ID_EMPLEADO + " = '" + codEmp + "' AND " + ID_CLIENTE + " = '" + codCli + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlClienteEmpleado, column,Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			String temp = res.get(CADENA).toString();
			String[] temporal = temp.split(",");
			IE = temporal[0];
			IC = temporal[1];
		}
		if (!IE.equals(Constante.VACIO) && !IC.equals(Constante.VACIO)) {
			resultado = true;
		}
		return resultado;
	}

	public boolean getInsertarClienteEmpleado() {
		return resultado;
	}

	public void setEliminarClienteEmpleado() {
		sqlClienteEmpleado = "DELETE FROM " + TABLA_EMPLEADO_CLIENTE;
		ResultExecute resultExecute = new ResultExecute(sqlClienteEmpleado, "Empleados-Clientes",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarClienteEmpleado() {
		return resultado;
	}

	private static String TABLA_EMPLEADO_CLIENTE = "PROFFLINE_TB_EMPLEADO_CLIENTE";
	private static String CADENA = "CADENA";
	private static String ID_EMPLEADO = "txtIdEmpleado";
	private static String ID_CLIENTE = "txtIdCliente";
}