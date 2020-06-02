package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
//import com.promesa.devoluciones.sql.impl.SqlMotivoDevolucionImpl;
//import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanCondicionPago;
import com.promesa.pedidos.sql.SqlCondicionPago;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlCondicionPagoImpl implements SqlCondicionPago {

	private List<BeanCondicionPago> listaCondiciones = null;
	@SuppressWarnings({ "rawtypes" })
	private HashMap column = new HashMap();
	@SuppressWarnings({ "rawtypes" })
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	ResultExecute resultExecute = null;
	private String sqlCondicionVenta = null;
	private boolean resultado = false;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaCondicionesPago() {
		column = new HashMap();
		listaCondiciones = new ArrayList<BeanCondicionPago>();
		column.put("String:0", ZTERM);
		column.put("String:1", VTEXT);
		sqlCondicionVenta = "SELECT " + ZTERM + "," + VTEXT + " FROM " + TABLA_CONDICIONES_PAGO;

		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCondicionVenta, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanCondicionPago beanSincronizado = new BeanCondicionPago(res.get(ZTERM).toString(), res.get(VTEXT).toString());
			listaCondiciones.add(beanSincronizado);
		}
	}
	
//	public static void main(String[] args) {
//		SqlCondicionPagoImpl sql = new SqlCondicionPagoImpl();
//		System.out.println(sql.obtenerDescripcionCondicionesPago("N008"));
//	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerDescripcionCondicionesPago(String codigo) {
		column = new HashMap();
		listaCondiciones = new ArrayList<BeanCondicionPago>();
		column.put("String:0", VTEXT);
		
		sqlCondicionVenta = "SELECT " + VTEXT + " FROM " + TABLA_CONDICIONES_PAGO + " WHERE " + ZTERM + "='" +codigo+"'";

		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCondicionVenta, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		String condicionPago = "";
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			condicionPago = res.get(VTEXT).toString();
		}
		return condicionPago;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanCondicionPago> listarCondicionesPagoPorCliente(String strCodCond) {
		column = new HashMap();
		listaCondiciones = new ArrayList<BeanCondicionPago>();
		column.put("String:0", ZTERM);
		column.put("String:1", VTEXT);
		
		sqlCondicionVenta = " select H.TXTZTERM as TXTZTERM,H.TXTVTEXT as TXTVTEXT from "
				+ " PROFFLINE_TB_CONDICIONES_PAGO_DETALLE D "
				+ "" + " inner join PROFFLINE_TB_CONDICIONES_PAGO H on (D.TXTZTERMD=H.TXTZTERM) "
				+ " where D.txtZTERMH='" + strCodCond + "' "
				+ " union select x.TXTZTERM as TXTZTERM,x.TXTVTEXT as TXTVTEXT from proffline_tb_condiciones_pago x where x.txtzterm='"
				+ strCodCond + "' ";

		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCondicionVenta, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanCondicionPago beanSincronizado = new BeanCondicionPago(res.get(ZTERM).toString(), res.get(VTEXT).toString());
			if (!seEncuentraEnLista(beanSincronizado, listaCondiciones)) {
				listaCondiciones.add(beanSincronizado);
			}
		}
		return listaCondiciones;
	}

	private boolean seEncuentraEnLista(BeanCondicionPago beanSincronizado, List<BeanCondicionPago> listaCondiciones) {
		boolean retorno = false;
		for (BeanCondicionPago b : listaCondiciones) {
			if (b.getTxtZTERM().trim().equals(beanSincronizado.getTxtZTERM()))
				retorno = true;
		}
		return retorno;
	}

	public void setInsertarCondicionesPago(List<BeanCondicionPago> listaBeanCondicionPago) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanCondicionPago bean : listaBeanCondicionPago) {
			listaSQL.add("INSERT INTO " + TABLA_CONDICIONES_PAGO + " (" + ZTERM
					+ ", " + VTEXT + " ) " + " VALUES ('" + bean.getTxtZTERM()
					+ "','" + bean.getTxtVTEXT() + "') ");
		}
		// ResultExecuteList resultExecute = new ResultExecuteList(
		// listaSQL, "Condiciones de pago");
		// resultado = resultExecute.isFlag();
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL,"Condiciones de pago",Constante.BD_SYNC);
	}

	public void insertarCondicionesPagoDetalle(List<BeanCondicionPago> listaBeanCondicionPago) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanCondicionPago bean : listaBeanCondicionPago) {
			listaSQL.add("INSERT INTO " + TABLA_CONDICIONES_PAGO_DETALLE + " ("
					+ ZTERMH + ", " + ZTERMD + " ) " + " VALUES ('"
					+ bean.getTxtZTERMH() + "','" + bean.getTxtZTERMD() + "') ");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL,"Condiciones de pago",Constante.BD_SYNC);
		// ResultExecuteList resultExecute = new ResultExecuteList(listaSQL,
		// "Condiciones de pago");
		// resultado = resultExecute.isFlag();
	}

	public void setEliminarCondicionesPago() {
		sqlCondicionVenta = " DELETE FROM " + TABLA_CONDICIONES_PAGO;
		resultExecute = new ResultExecute(sqlCondicionVenta,"Condiciones de pago",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void eliminarCondicionesPagoDetalle() {
		sqlCondicionVenta = " DELETE FROM " + TABLA_CONDICIONES_PAGO_DETALLE;
		resultExecute = new ResultExecute(sqlCondicionVenta,"Condiciones de pago",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlCondicionVenta = " SELECT COUNT(*) AS filas FROM " + TABLA_CONDICIONES_PAGO;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlCondicionVenta,column,Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	public boolean getEliminarBloqueoEntrega() {
		return resultado;
	}

	public boolean getInsertarBloqueoEntrega() {
		return resultado;
	}

	@Override
	public List<BeanCondicionPago> getListaCondiciones() {
		return listaCondiciones;
	}
	private static String TABLA_CONDICIONES_PAGO = "PROFFLINE_TB_CONDICIONES_PAGO";
	private static String TABLA_CONDICIONES_PAGO_DETALLE = "PROFFLINE_TB_CONDICIONES_PAGO_DETALLE";
	private static String ZTERM = "txtZTERM";
	private static String VTEXT = "txtVTEXT";
	private static String ZTERMH = "txtZTERMH";
	private static String ZTERMD = "txtZTERMD";
}
