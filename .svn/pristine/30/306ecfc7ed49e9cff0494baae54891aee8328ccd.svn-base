package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.DatoCredito;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlProtestoImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaProtesto(List<DatoCredito> lstProtesto){
		List<String> listaProtesto = new ArrayList<String>();
		for(DatoCredito protesto : lstProtesto){
			String sql = "INSERT INTO PROFFLINE_TB_PROTESTO (codigoCliente, codigoVendedor, numeroDocumento, fechaContable,"
					+ "fechaDocumento, registradoEl, moneda, importe, unOrgRefer) VALUES ('" + Long.parseLong(protesto.getCodigoCliente())
					+ "','" + protesto.getCodigoVendedor()
					+ "','" + protesto.getNumeroDocumento()
					+ "','" + protesto.getFechaContable()
					+ "','" + protesto.getFechaDocumento()
					+ "','" + protesto.getRegistradoEl()
					+ "','" + protesto.getMoneda()
					+ "','" + protesto.getImporte()
					+ "','" + protesto.getUnOrgRefer() + "');";
			listaProtesto.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaProtesto, "PROTESTO", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaProtesto(){
		String sql = "DELETE FROM PROFFLINE_TB_PROTESTO;";
		resultExecute = new ResultExecute(sql, "PROTESTO", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DatoCredito> obtenerListaProtesto(String codigoCliente){
		List<DatoCredito> listaProtesto = new ArrayList<DatoCredito>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "numeroDocumento");
		column.put("String:4", "fechaContable");
		column.put("String:5", "fechaDocumento");
		column.put("String:6", "registradoEl");
		column.put("String:7", "moneda");
		column.put("String:8", "importe");
		column.put("String:9", "unOrgRefer");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PROTESTO " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					DatoCredito protesto = new DatoCredito();
					protesto.setId(Integer.parseInt(res.get("id").toString()));
					protesto.setCodigoCliente(res.get("codigoCliente").toString());
					protesto.setCodigoVendedor(res.get("codigoVendedor").toString());
					protesto.setNumeroDocumento(res.get("numeroDocumento").toString());
					protesto.setFechaContable(res.get("fechaContable").toString());
					protesto.setFechaDocumento(res.get("fechaDocumento").toString());
					protesto.setRegistradoEl(res.get("registradoEl").toString());
					protesto.setMoneda(res.get("moneda").toString());
					protesto.setImporte(res.get("importe").toString());
					protesto.setUnOrgRefer(res.get("unOrgRefer").toString());
					listaProtesto.add(protesto);
				}
			}
		}
		return listaProtesto;
	}
}