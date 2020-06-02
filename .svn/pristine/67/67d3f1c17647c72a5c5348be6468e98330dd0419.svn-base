package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.ValorPorVencer;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlValorPorVencerImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaValorPorVencer(List<ValorPorVencer> lstValorPorVencer){
		List<String> listaValorPorVencer = new ArrayList<String>();
		for(ValorPorVencer valorPorVencer : lstValorPorVencer){
			String sql = "INSERT INTO PROFFLINE_TB_VALOR_POR_VENCER (codigoCliente, codigoVendedor, mesAnio, cantidad) VALUES ('"
					+ Long.parseLong(valorPorVencer.getCodigoCliente())
					+ "','" + valorPorVencer.getCodigoVendedor()
					+ "','" + valorPorVencer.getMesAnio()
					+ "','" + valorPorVencer.getCantidad() + "');";
			listaValorPorVencer.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaValorPorVencer, "VALOR POR VENCER", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaValorPorVencer(){
		String sql = "DELETE FROM PROFFLINE_TB_VALOR_POR_VENCER;";
		resultExecute = new ResultExecute(sql, "VALOR POR VENCER", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ValorPorVencer> obtenerListaValorPorVencer(String codigoCliente){
		List<ValorPorVencer> listaValorPorVencer = new ArrayList<ValorPorVencer>();
		column = new HashMap();
		column.put("String:0", "codigoCliente");
		column.put("String:1", "codigoVendedor");
		column.put("String:2", "mesAnio");
		column.put("String:3", "cantidad");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_VALOR_POR_VENCER " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					ValorPorVencer valorPorVencer = new ValorPorVencer();
					valorPorVencer.setCodigoCliente(res.get("codigoCliente").toString());
					valorPorVencer.setCodigoVendedor(res.get("codigoVendedor").toString());
					valorPorVencer.setMesAnio(res.get("mesAnio").toString());
					valorPorVencer.setCantidad(res.get("cantidad").toString());
					listaValorPorVencer.add(valorPorVencer);
				}
			}
		}
		return listaValorPorVencer;
	}
}
