package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.bean.BeanFeriado;
import com.promesa.planificacion.sql.SqlSincronizacion;
import com.promesa.sap.SPedidos;
import com.promesa.sap.SPlanificacion;
import com.promesa.sincronizacion.bean.BeanSincronizacion;
import com.promesa.util.Cmd;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlSincronizacionImpl implements SqlSincronizacion {

	private String sqlSinc = null;
	@SuppressWarnings("unused")
	private String sqlFeriado = null;
	@SuppressWarnings("unused")
	private String sqlCliente = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	private ResultExecuteQuery resultExecuteQuery = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private List<BeanSincronizacion> since = null;
	BeanSincronizacion objS = new BeanSincronizacion();
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;

	/*
	 * M�TODO QUE LISTA SINCRONIZACIONES DE PLANIFICACIONES, EMPLEADOS CLIENTES
	 * Y FERIADOS
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanSincronizacion> listaSincronizacion() {
		column = new HashMap();
		since = new ArrayList<BeanSincronizacion>();
		column.put("String:0", NUM_ID_SINC);
		column.put("String:1", INFO_SINC);
		column.put("String:2", CANT_REG);
		column.put("String:3", FECH_HOR);
		column.put("String:4", TIEMPO);
		column.put("String:5", HORA_INI);
		column.put("String:6", FRECUENCIA);
		sqlSinc = " SELECT PS." + NUM_ID_SINC + ", " + INFO_SINC + ", " + CANT_REG + ", " + FECH_HOR + ", " + TIEMPO
				+ ", " + HORA_INI + "," + FRECUENCIA + " " + " FROM " + TABLA_SINCRONIZACION + " PS"
				+ " INNER JOIN PROFFLINE_TB_DETALLE_SINCRONIZACION PDS ON PS.numIdeSinc=PDS.numIdeSinc"
				+ " WHERE PS." + NUM_ID_SINC + " IN(7,8,9)";
		resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			objS = new BeanSincronizacion();
			res = (HashMap) mapResultado.get(i);
			objS.setStrIdeSinc(res.get(NUM_ID_SINC).toString());
			objS.setStrInfSinc(res.get(INFO_SINC).toString());
			objS.setStrNumCantReg(res.get(CANT_REG).toString());
			objS.setStrFecHor(res.get(FECH_HOR).toString());
			objS.setStrNumTie(res.get(TIEMPO).toString());
			objS.setStrHoraIni(res.get(HORA_INI).toString());
			objS.setStrFrecuencia(res.get(FRECUENCIA).toString());
			since.add(objS);
		}
		return since;
	}

	/* M�TODO QUE ACTUALIZA REGISTRO FERIADOS */
	@SuppressWarnings("static-access")
	public void sF(String fechaInicio) {
		Cmd objC;
		String fechaFinal;
		fechaFinal = "";
		BeanSincronizar sincronizar = new BeanSincronizar();
		try {
			objC = new Cmd();
			sincronizar.setStrIdeSinc("9");
			setEliminarSincronizacion(sincronizar);
			fechaFinal = objC.fechaHora();
			sincronizar.setStrInfSinc("Feriados");
			sincronizar.setStrCantReg(filasTabla("PROFFLINE_TB_FERIADO") + "");
			sincronizar.setStrFecHor(fechaFinal);
			sincronizar.setStrTie(objC.diferencia(fechaInicio, fechaFinal) + "");
			setInsertarSincronizar(sincronizar);
		} catch (Exception ex) {
			Util.mostrarExcepcion(ex);
		}
	}

	/* M�TODO QUE ACTUALIZA REGISTRO EMPLEADOS CLIENTES */
	@SuppressWarnings("static-access")
	public void sEC(String fechaInicio) {
		Cmd objC;
		String fechaFinal;
		int i;
		fechaFinal = "";
		BeanSincronizar sincronizar = new BeanSincronizar();
		try {
			objC = new Cmd();
			i = 0;
			i += filasTabla("PROFFLINE_TB_EMPLEADO");
			i += filasTabla("PROFFLINE_TB_CLIENTE");
			i += filasTabla("PROFFLINE_TB_EMPLEADO_CLIENTE");
			sincronizar.setStrIdeSinc("8");
			setEliminarSincronizacion(sincronizar);
			fechaFinal = objC.fechaHora();
			sincronizar.setStrInfSinc("Emp. Cli.");
			sincronizar.setStrCantReg(i + "");
			sincronizar.setStrFecHor(fechaFinal);
			sincronizar.setStrTie(objC.diferencia(fechaInicio, fechaFinal) + "");
			setInsertarSincronizar(sincronizar);
		} catch (Exception ex) {
			Util.mostrarExcepcion(ex);
		}
	}

	/* M�TODO QUE SINCRONIZA FERIADOS */
	@SuppressWarnings({})
	public boolean sincronizaFeriado() {
		SqlFeriadoImpl objSFI = new SqlFeriadoImpl();
		objSFI.setEliminarFeriado();
		if (objSFI.getEliminarFeriado() == true) {
			resultExecute = null;
			sqlFeriado = "";
			resultado = true;
			SPlanificacion objSAP;
			List<BeanFeriado> fe;
			try {
				fe = new ArrayList<BeanFeriado>();
				objSAP = new SPlanificacion();
				fe = objSAP.feriados();
				if (fe != null) {
					for (int i = 0; i < fe.size(); i++) {
						objSFI.setInsertarFeriado((BeanFeriado) fe.get(i));
						if (objSFI.getInsertarFeriado() == false) {
							resultado = false;
							break;
						}
					}
				}
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		} else {
			resultado = false;
		}
		return resultado;
	}

	/* M�TODO QUE SINCRONIZA EMPLEADOS CLIENTES */
	@SuppressWarnings({})
	public boolean sincronizaEmpleadoCliente(String idSup) {
		SqlClienteImpl objSCI = new SqlClienteImpl();
		SqlClienteEmpleadoImpl objSCEI = new SqlClienteEmpleadoImpl();
		SqlEmpleadoImpl objSEI = new SqlEmpleadoImpl();
		objSCI.setEliminarCliente();
		objSCEI.setEliminarClienteEmpleado();
		objSEI.setEliminarEmpleado();
		if (objSCI.getEliminarCliente() == true && objSCEI.getEliminarClienteEmpleado() == true && objSEI.getEliminarEmpleado() == true) {
			try {
				SPedidos rfcP = new SPedidos();
				List<BeanCliente> clientes = rfcP.vendedorClientes2(idSup, "", "");
				if (clientes != null) {
					SqlClienteImpl sql = new SqlClienteImpl();
					sql.setInsertarCliente(clientes);
					sql.setInsertarEmpleadoClientes(idSup, clientes);
					 resultado = true;
				}
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				resultado = false;
			}
		} else {
			resultado = false;
		}
		return resultado;
	}

	public void setInsertarSincronizar(BeanSincronizar Sincronizar) {
		sqlSinc = " INSERT INTO PROFFLINE_TB_SINCRONIZACION (numIdeSinc,txtInfSinc,numCantReg,txtFecHor,numTie) "
				+ " VALUES (" + Sincronizar.getStrIdeSinc() + ",'" + Sincronizar.getStrInfSinc()
				+ "'," + Sincronizar.getStrCantReg() + ",'" + Sincronizar.getStrFecHor() + "'," + Sincronizar.getStrTie() + ") ";
		ResultExecute resultExecute = new ResultExecute(sqlSinc, "Sincronizaci�n", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarSincronizacion(BeanSincronizar Sincronizar) {
		sqlSinc = "DELETE FROM PROFFLINE_TB_SINCRONIZACION " + "WHERE numIdeSinc = " + Sincronizar.getStrIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSinc, "Sincronizaci�n", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setActualizarSincronizacionDetalle(DetalleSincronizacion ds) {
		sqlSinc = " UPDATE PROFFLINE_TB_DETALLE_SINCRONIZACION " + " SET txtHorIni='" + ds.getTxtHorIni() + "', " + " numFec="
				+ ds.getNumFec() + " " + " WHERE numIdeSinc = " + ds.getNumIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSinc, "Sincronizaci�n", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}
	//Nelson Villegas
	//Metodo para actualizar el numero de registros
	public void setNumeroRegistro(BeanSincronizacion bs) {
		sqlSinc = " UPDATE PROFFLINE_TB_SINCRONIZACION " + " SET numCantReg='" + bs.getStrNumCantReg() + "' "
				+ " WHERE numIdeSinc = " + bs.getStrIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSinc, "Sincronizaci�n", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int filasTabla(String tabla) {
		column = new HashMap();
		int filas = 0;
		column.put("String:0", "filas");
		sqlSinc = "SELECT COUNT(*) as filas FROM " + tabla;
		if(tabla.equals("PROFFLINE_TB_AGENDA"))
			resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column, Constante.BD_TX);
		else
			resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = (HashMap) mapResultado.get(0);
		filas = Integer.parseInt(res.get("filas").toString());
		return filas;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int anticiposEliminados(){
		String sql = "SELECT count(*) as filas FROM PROFFLINE_TB_ANTICIPO_CLIENTE where eliminado = 'true' and sincronizado=0";
		int filas = 0;
		column = new HashMap();
		column.put("String:0", "filas");
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = (HashMap) mapResultado.get(0);
		filas = Integer.parseInt(res.get("filas").toString());
		return filas;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int cobranzaEliminados(){
		String sql = "SELECT count(*) as filas FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE where eliminado = 'true' and sincronizado=0";
		int filas = 0;
		column = new HashMap();
		column.put("String:0", "filas");
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = (HashMap) mapResultado.get(0);
		filas = Integer.parseInt(res.get("filas").toString());
		return filas;
		
	}

	public boolean getEliminarSincronizacion() {
		return resultado;
	}

	public boolean getInsertarSincronizar() {
		return resultado;
	}
	private static String NUM_ID_SINC = "numIdeSinc";
	private static String INFO_SINC = "txtInfSinc";
	private static String CANT_REG = "numCantReg";
	private static String FECH_HOR = "txtFecHor";
	private static String TIEMPO = "numTie";
	private static String HORA_INI = "txtHorIni";
	private static String FRECUENCIA = "numFec";
	private static String TABLA_SINCRONIZACION = "PROFFLINE_TB_SINCRONIZACION";
}