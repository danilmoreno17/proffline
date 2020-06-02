package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.bean.MarcaVendedor;
import com.promesa.pedidos.sql.SqlMarcaVendedor;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlMarcaVendedorImpl implements SqlMarcaVendedor{

	private static SqlMarcaVendedor instance = null;
	
	public static SqlMarcaVendedor create() {
		synchronized (SqlMarcaVendedor.class) {
			if(instance == null) {
				instance = new SqlMarcaVendedorImpl();
			}
		}
		return instance;
	}
	
	@Override
	public void insertMarcaVendedor(List<MarcaVendedor> marcas) {
		if(marcas != null && marcas.size()>0) {
			final List<String> listaSQL = new ArrayList<String>();
			listaSQL.add("DELETE FROM PROFFLINE_TB_MARCA_VENDEDOR");
			for (MarcaVendedor ms : marcas) {
				listaSQL.add("INSERT INTO PROFFLINE_TB_MARCA_VENDEDOR ( CLIENTE ,MARCA,PRESUPUESTO_MES,VENTA_MES,PRESUPUESTO_ACUMULADO, VENTA_ACUMULADA, FECHA) VALUES ('"
						+ ms.getCodigoCliente() + "','" + ms.getMarca() + "','" + ms.getPresupuestoMes()+"','"+ms.getVentaMes() +"','"+ ms.getPresupuestoAcumulado()+"','"+ms.getVentaAcumulado() +"','"+ms.getFecha()+ "')");
			}
			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "MarcaVendedor", Constante.BD_SYNC);
		}
	}

	@Override
	public List<MarcaVendedor> getMarcaVendedor() {
		HashMap column = new HashMap();
		String sql = "SELECT * FROM PROFFLINE_TB_MARCA_VENDEDOR;";
		column.put("String:0", "CLIENTE");
		column.put("String:1", "MARCA");
		column.put("String:2", "PRESUPUESTO_MES");
		column.put("String:3", "VENTA_MES");
		column.put("String:4", "PRESUPUESTO_ACUMULADO");
		column.put("String:5", "VENTA_ACUMULADA");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		List<MarcaVendedor> marcas = null;
		if(mapResultado != null){
			marcas = new ArrayList();
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				MarcaVendedor ms =  new MarcaVendedor();
				ms.setCodigoCliente(res.get("CLIENTE").toString().trim());
				ms.setMarca(res.get("MARCA").toString().trim());
				ms.setPresupuestoMes(res.get("PRESUPUESTO_MES").toString().trim());
				ms.setVentaMes(res.get("VENTA_MES").toString().trim());
				ms.setPresupuestoAcumulado(res.get("PRESUPUESTO_ACUMULADO").toString().trim());
				ms.setVentaAcumulado(res.get("VENTA_ACUMULADA").toString().trim());
				marcas.add(ms);
			}
			
			return marcas;
		}
		return null;
	}

	@Override
	public void actualizarMarcaVendedor(List<MarcaVendedor> marcas) {
		final List<String> listaSQL = new ArrayList<String>();
		if(marcas != null && marcas.size() > 0){
			for (MarcaVendedor ms : marcas) {
				listaSQL.add("UPDATE PROFFLINE_TB_MARCA_VENDEDOR SET VENTA_ACUMULADA='"+ms.getVentaAcumulado()+"', VENTA_MES='"+ms.getVentaMes()+"' WHERE CLIENTE='"
						+ ms.getCodigoCliente() + "' AND MARCA ='" + ms.getMarca() + "';");
			}
			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "Marca Estrategica", Constante.BD_SYNC);
			Promesa.getInstance().mostrarAvisoSincronizacion("");
		}
	}

	@Override
	public List<MarcaVendedor> getMarcaVendedorByCliente(String cliente) {
		HashMap column = new HashMap();
		String sql = "SELECT * FROM PROFFLINE_TB_MARCA_VENDEDOR WHERE CLIENTE= '"+Util.completarCeros(10, cliente)+"';";
		column.put("String:0", "CLIENTE");
		column.put("String:1", "MARCA");
		column.put("String:2", "PRESUPUESTO_MES");
		column.put("String:3", "VENTA_MES");
		column.put("String:4", "PRESUPUESTO_ACUMULADO");
		column.put("String:5", "VENTA_ACUMULADA");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		List<MarcaVendedor> marcas = null;
		if(mapResultado != null){
			marcas = new ArrayList();
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				if(res.get("MARCA").toString().trim().length()>0) {
				MarcaVendedor ms =  new MarcaVendedor();
				ms.setCodigoCliente(res.get("CLIENTE").toString().trim());
				ms.setMarca(res.get("MARCA").toString().trim());
				ms.setPresupuestoMes(res.get("PRESUPUESTO_MES").toString().trim());
				ms.setVentaMes(res.get("VENTA_MES").toString().trim());
				ms.setPresupuestoAcumulado(res.get("PRESUPUESTO_ACUMULADO").toString().trim());
				ms.setVentaAcumulado(res.get("VENTA_ACUMULADA").toString().trim());
				marcas.add(ms);
				}
			}
			
			return marcas;
		}
		return null;
	}

	@Override
	public boolean perteneceAMarcaVendedor(String cliente, String marca) {
		String sql = "SELECT COUNT(*) AS CANTIDAD FROM PROFFLINE_TB_MARCA_VENDEDOR WHERE CLIENTE='"+cliente+"' AND MARCA='"+marca+"';";
		HashMap column = new HashMap();
		column.put("String:0", "CANTIDAD");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		if(mapResultado != null && mapResultado.size() > 0){
			HashMap res = (HashMap) mapResultado.get(0);
			int c = Integer.parseInt(res.get("CANTIDAD").toString().trim());
			if(c > 0){
				return true;
			}
		}
		return false;
	}

}
