package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.promesa.administracion.bean.BeanUsuario;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
//import com.promesa.pedidos.bean.BeanCondicionPago;
//import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.SqlBloqueoEntrega;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlBloqueoEntregaImpl implements SqlBloqueoEntrega {

	private String sqlBloqueoEntrega = null;
	@SuppressWarnings({ "rawtypes" })
	private HashMap column = new HashMap();
	private List<BeanBloqueoEntrega> listaBloqueoEntrega = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private boolean resultado = false;
	private ResultExecute resultExecute = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setListaBloqueoEntrega() {
		// TODO Auto-generated method stub
		column = new HashMap();
		listaBloqueoEntrega = new ArrayList<BeanBloqueoEntrega>();
		column.put("String:0", CODIGO);
		column.put("String:1", DESCRIPCION);
		sqlBloqueoEntrega = "SELECT * FROM PROFFLINE_TB_BLOQUEO_ENTREGA";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlBloqueoEntrega, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			BeanBloqueoEntrega bean = new BeanBloqueoEntrega();
			res = (HashMap) mapResultado.get(i);
			bean.setCodigo(res.get(CODIGO).toString());
			bean.setDescripcion(res.get(DESCRIPCION).toString());
			listaBloqueoEntrega.add(bean);
		}
	}

	public void setInsertarBloqueoEntrega(List<BeanBloqueoEntrega> ListaBloqueoEntrega) {
		final List<String> listaSQL = new ArrayList<String>();
		
		for (BeanBloqueoEntrega bean : ListaBloqueoEntrega) {
			listaSQL.add("INSERT INTO " + TABLA_BLOQUEO_ENTREGA + " (" + CODIGO
					+ ", " + DESCRIPCION + " ) " + " VALUES ('" + bean.getCodigo() + "','" + bean.getDescripcion() + "') ");
		}

		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Bloqueos de entrega", Constante.BD_SYNC);
	}
	
//	public static void main(String[] args) {
//		SqlBloqueoEntregaImpl sql = new SqlBloqueoEntregaImpl();
//		System.out.println(sql.obtenerDescripcionBloqueoEntrega("10"));
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String obtenerDescripcionBloqueoEntrega(String codigo) {
		column = new HashMap();
		column.put("String:0", DESCRIPCION);
		sqlBloqueoEntrega = "SELECT " + DESCRIPCION + " FROM " + TABLA_BLOQUEO_ENTREGA + " WHERE " + CODIGO + "='" +codigo+"'";

		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlBloqueoEntrega, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		String bloqueoEntrega = "";
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			bloqueoEntrega = res.get(DESCRIPCION).toString();
		}
		return bloqueoEntrega;
	}

	public void setEliminarBloqueoEntrega() {
		sqlBloqueoEntrega = " DELETE FROM " + TABLA_BLOQUEO_ENTREGA;
		resultExecute = new ResultExecute(sqlBloqueoEntrega,"Bloqueos de entrega",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;

		sqlBloqueoEntrega = " SELECT COUNT(*) AS filas FROM " + TABLA_BLOQUEO_ENTREGA;

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlBloqueoEntrega,column,Constante.BD_SYNC);
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
	public List<BeanBloqueoEntrega> getListaBloqueoEntrega() {
		return listaBloqueoEntrega;
	}

	private static String TABLA_BLOQUEO_ENTREGA = "PROFFLINE_TB_BLOQUEO_ENTREGA";
	private static String CODIGO = "txtCodigo";
	private static String DESCRIPCION = "txtDescripcion";

}
