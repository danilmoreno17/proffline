package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.DatoCredito;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlNotaCreditoImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaNotaCredito(List<DatoCredito> lstNotaCredito){
		List<String> listaNotaCredito = new ArrayList<String>();
		for(DatoCredito notaCredito : lstNotaCredito){
			String sql = "INSERT INTO PROFFLINE_TB_NOTA_CREDITO (codigoCliente, codigoVendedor, numeroDocumento, fechaContable,"
					+ "fechaDocumento, registradoEl, moneda, importe, unOrgRefer) VALUES ('" + Long.parseLong(notaCredito.getCodigoCliente())
					+ "','" + notaCredito.getCodigoVendedor() + "','" + notaCredito.getNumeroDocumento()
					+ "','" + notaCredito.getFechaContable()  + "','" + notaCredito.getFechaDocumento()
					+ "','" + notaCredito.getRegistradoEl()   + "','" + notaCredito.getMoneda()
					+ "','" + notaCredito.getImporte() 		  + "','" + notaCredito.getUnOrgRefer() + "');";
			listaNotaCredito.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaNotaCredito, "NOTA CREDITO", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaNotaCredito(){
		String sql = "DELETE FROM PROFFLINE_TB_NOTA_CREDITO;";
		resultExecute = new ResultExecute(sql, "NOTA CREDITO", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DatoCredito> obtenerListaNotaCredito(String codigoCliente){
		List<DatoCredito> listaNotaCredito = new ArrayList<DatoCredito>();
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
		String sql = "SELECT * FROM PROFFLINE_TB_NOTA_CREDITO " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					DatoCredito notaCredito = new DatoCredito();
					notaCredito.setId(Integer.parseInt(res.get("id").toString()));
					notaCredito.setCodigoCliente(res.get("codigoCliente").toString());
					notaCredito.setCodigoVendedor(res.get("codigoVendedor").toString());
					notaCredito.setNumeroDocumento(res.get("numeroDocumento").toString());
					notaCredito.setFechaContable(res.get("fechaContable").toString());
					notaCredito.setFechaDocumento(res.get("fechaDocumento").toString());
					notaCredito.setRegistradoEl(res.get("registradoEl").toString());
					notaCredito.setMoneda(res.get("moneda").toString());
					notaCredito.setImporte(res.get("importe").toString());
					notaCredito.setUnOrgRefer(res.get("unOrgRefer").toString());
					listaNotaCredito.add(notaCredito);
				}
			}
		}
		return listaNotaCredito;
	}
}