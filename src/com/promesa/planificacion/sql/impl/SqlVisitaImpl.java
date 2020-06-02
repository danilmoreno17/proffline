package com.promesa.planificacion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.bean.BeanVisita;
import com.promesa.planificacion.sql.SqlVisita;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlVisitaImpl implements SqlVisita {

	private String sqlVisita = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "unused", "rawtypes" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanVisita visita = null;
	private List<BeanVisita> listaVisita = null;
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;
	@SuppressWarnings("unused")
	private ResultExecuteQuery resultExecuteQuery = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaVisita(BeanVisita visita) {
		column = new HashMap();
		listaVisita = new ArrayList<BeanVisita>();
		column.put("String:0", ID_VENDEDOR);
		column.put("String:1", ID_CLIENTE);
		column.put("String:2", ID_JEFE);
		column.put("String:3", FECHA_VISITA);
		column.put("String:4", HORA_PLANEADA);
		column.put("String:5", HORA);
		column.put("String:6", STATUS);
		column.put("String:7", TIPO);
		column.put("String:8", COMENTARIO1);
		column.put("String:9", COMENTARIO2);
		column.put("String:10", DOCUMENTO_REFERENCIA);
		column.put("String:11", VISITA_EFECTUADA);
		column.put("String:12", ID_PLANIFICACION);
		column.put("String:13", MANDANTE);
		sqlVisita = " SELECT  " + ID_VENDEDOR + "," + ID_CLIENTE + "," + ID_JEFE + "," + FECHA_VISITA + "," + HORA_PLANEADA + ","
				+ HORA + "," + STATUS + "," + TIPO + "," + COMENTARIO1 + "," + COMENTARIO2 + "," + DOCUMENTO_REFERENCIA + ","
				+ VISITA_EFECTUADA + "," + ID_PLANIFICACION + "," + MANDANTE + " FROM " + TABLA + " WHERE Eliminado = 0";
		if (visita.getStrSincronizado() != null) {
			sqlVisita += " AND txtSincronizado IN (0,2) ";
		}
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlVisita, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.visita = new BeanVisita();
			res = (HashMap) mapResultado.get(i);
			this.visita.setStrIdVendedor(res.get(ID_VENDEDOR).toString());
			this.visita.setStrIdCliente(res.get(ID_CLIENTE).toString());
			this.visita.setStrIdJefe(res.get(ID_JEFE).toString());
			this.visita.setStrFechaVisita(res.get(FECHA_VISITA).toString());
			this.visita.setStrHoraPlaneada(res.get(HORA_PLANEADA).toString());
			this.visita.setStrHora(res.get(HORA).toString());
			this.visita.setStrStatus(res.get(STATUS).toString());
			this.visita.setStrTipo(res.get(TIPO).toString());
			this.visita.setStrComentario1(res.get(COMENTARIO1).toString());
			this.visita.setStrComentario2(res.get(COMENTARIO2).toString());
			this.visita.setStrDocumentoReferencia(res.get(DOCUMENTO_REFERENCIA).toString());
			this.visita.setStrVisitaEfectuada(res.get(VISITA_EFECTUADA).toString());
			this.visita.setStrIdPlanificacion(res.get(ID_PLANIFICACION).toString());
			this.visita.setStrMandante(res.get(MANDANTE).toString());
			listaVisita.add(this.visita);
		}
	}

	public List<BeanVisita> getListaVisita() {
		return listaVisita;
	}

	public void setInsertarVisita(BeanVisita visita) {
		sqlVisita = "INSERT INTO " + TABLA + " VALUES " + "(" + "'"
				+ visita.getStrIdVendedor() + "','" + visita.getStrIdCliente()
				+ "','" + visita.getStrIdJefe() + "','" + visita.getStrFechaVisita() + "','"
				+ visita.getStrHoraPlaneada() + "','" + visita.getStrHora()
				+ "','" + visita.getStrStatus() + "','" + visita.getStrTipo()
				+ "','" + visita.getStrComentario1() + "','" + visita.getStrComentario2() + "','"
				+ visita.getStrDocumentoReferencia() + "','" + visita.getStrVisitaEfectuada() + "','"
				+ visita.getStrIdPlanificacion() + "','" + visita.getStrMandante() + "',0,0);";
		try {
			ResultExecute resultExecute = new ResultExecute(sqlVisita, "Vistas", Constante.BD_SYNC);
			resultado = resultExecute.isFlag();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
	}

	public boolean getInsertarVisita() {
		return resultado;
	}

	/* COMPARA FECHAS */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void comparaFecha(BeanVisita visita) {
		column = new HashMap();
		listaVisita = new ArrayList<BeanVisita>();
		column.put("String:0", FECHA_VISITA);
		sqlVisita = "SELECT " + FECHA_VISITA + " FROM " + TABLA + " WHERE " + ID_CLIENTE + " = '" + visita.getStrIdCliente() + "'"
				+ " AND " + ID_PLANIFICACION + " = '" + visita.getStrIdPlanificacion() + "'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlVisita, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			this.visita = new BeanVisita();
			res = (HashMap) mapResultado.get(i);
			this.visita.setStrFechaVisita(res.get(FECHA_VISITA).toString());
			listaVisita.add(this.visita);
		}
		for (int j = 0; j < listaVisita.size(); j++) {
			if (Integer.parseInt(visita.getStrFechaVisita().charAt(3) + ""
					+ visita.getStrFechaVisita().charAt(4)) <= Integer.parseInt(((BeanVisita) listaVisita.get(j)).getStrFechaVisita().charAt(3)
							+ ""+ ((BeanVisita) listaVisita.get(j)).getStrFechaVisita().charAt(4))) {
				if (Integer.parseInt(visita.getStrFechaVisita().charAt(3) + ""+ visita.getStrFechaVisita().charAt(4)) == Integer.parseInt(((BeanVisita) listaVisita.get(j)).getStrFechaVisita().charAt(3)
								+ "" + ((BeanVisita) listaVisita.get(j)).getStrFechaVisita().charAt(4))) {
					if (Integer.parseInt(visita.getStrFechaVisita().charAt(0) + "" + visita.getStrFechaVisita().charAt(1)) < Integer.parseInt(((BeanVisita) listaVisita.get(j))
									.getStrFechaVisita().charAt(0) + "" + ((BeanVisita) listaVisita.get(j)).getStrFechaVisita().charAt(1))) {
						actualizaStatus(visita, ((BeanVisita) listaVisita.get(j)).getStrFechaVisita());
					}
				} else {
					actualizaStatus(visita, ((BeanVisita) listaVisita.get(j)).getStrFechaVisita());
				}
			}
		}
		eliminarXStatus(visita);
	}

	/* ACTUALIZA STATUS */
	public void actualizaStatus(BeanVisita visita, String fecha) {
		sqlVisita = "UPDATE " + TABLA + " SET " + STATUS + " = '" + CAMBIA_STATUS + "' " + "WHERE " + FECHA_VISITA + " = '"
				+ fecha + "'" + " AND " + ID_CLIENTE + " = '" + visita.getStrIdCliente() + "'" + " AND " + ID_PLANIFICACION
				+ " = '" + visita.getStrIdPlanificacion() + "'";
		ResultExecute resultExecute = new ResultExecute(sqlVisita, "Vistas",  Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	/* ELIMINA SEGÚN STATUS */
	public void eliminarXStatus(BeanVisita visita) {
		sqlVisita = "DELETE FROM " + TABLA + " WHERE " + STATUS + " = '" + CAMBIA_STATUS + "' " + " AND " + ID_CLIENTE + " = '"
				+ visita.getStrIdCliente() + "'" + " AND " + ID_PLANIFICACION + " = '" + visita.getStrIdPlanificacion() + "'";
		ResultExecute resultExecute = new ResultExecute(sqlVisita, "Vistas", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminaXStatus() {
		return resultado;
	}

	public void setEliminarVisita(BeanVisita visita) {
		sqlVisita = "DELETE FROM " + TABLA + " WHERE " + ID_CLIENTE + " = '"
				+ visita.getStrIdCliente() + "'" + " AND " + ID_PLANIFICACION + " = '" + visita.getStrIdPlanificacion() + "'";
		ResultExecute resultExecute = new ResultExecute(sqlVisita, "Vistas", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarVisita() {
		return resultado;
	}

	private static String TABLA = "PROFFLINE_TB_VISITA";
	private static String ID_VENDEDOR = "txtIdVendedor";
	private static String ID_CLIENTE = "txtIdCliente";
	private static String ID_JEFE = "txtIdJefe";
	private static String FECHA_VISITA = "txtFechaVisita";
	private static String HORA_PLANEADA = "txtHoraPlaneada";
	private static String HORA = "txtHora";
	private static String STATUS = "txtStatus";
	private static String TIPO = "txtTipo";
	private static String COMENTARIO1 = "txtComentario1";
	private static String COMENTARIO2 = "txtComentario2";
	private static String DOCUMENTO_REFERENCIA = "txtDocumentoReferencia";
	private static String VISITA_EFECTUADA = "txtVisitaEfectuada";
	private static String ID_PLANIFICACION = "txtIdPlanificacion";
	private static String MANDANTE = "txtMandante";
	@SuppressWarnings("unused")
	private static String ELIMINADO = "Eliminado";
	private static String CAMBIA_STATUS = "X";
}