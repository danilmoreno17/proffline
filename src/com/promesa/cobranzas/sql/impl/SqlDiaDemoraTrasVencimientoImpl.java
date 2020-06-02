package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.DiaDemoraTrasVencimiento;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlDiaDemoraTrasVencimientoImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaDiaDemoraTrasVencimiento(List<DiaDemoraTrasVencimiento> lstDiaDemoraTrasVencimiento){
		List<String> listaDiaDemoraTrasVencimiento = new ArrayList<String>();
		for(DiaDemoraTrasVencimiento diaDemoraTrasVencimiento : lstDiaDemoraTrasVencimiento){
			String sql = "INSERT INTO PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO (codigoCliente, codigoVendedor, sociedad, moneda,"
					+ "cuadro, partidasVencidas, noVencidas) VALUES ('" 	+ diaDemoraTrasVencimiento.getCodigoCliente()
					+ "','" + diaDemoraTrasVencimiento.getCodigoVendedor() 	+ "','" + diaDemoraTrasVencimiento.getSociedad()
					+ "','" + diaDemoraTrasVencimiento.getMoneda() 			+ "','" + diaDemoraTrasVencimiento.getCuadro()
					+ "','" + diaDemoraTrasVencimiento.getPartidasVencidas()+ "','" + diaDemoraTrasVencimiento.getNoVencidas() + "');";
			listaDiaDemoraTrasVencimiento.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaDiaDemoraTrasVencimiento, "DIA DEMORA TRAS VENCIMIENTO", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaDiaDemoraTrasVencimiento(){
		String sql = "DELETE FROM PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO;";
		resultExecute = new ResultExecute(sql, "DIA DEMORA TRAS VENCIMIENTO", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DiaDemoraTrasVencimiento> obtenerListaDiaDemoraTrasVencimiento(String codigoCliente){
		List<DiaDemoraTrasVencimiento> listaDiaDemoraTrasVencimiento = new ArrayList<DiaDemoraTrasVencimiento>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "sociedad");
		column.put("String:4", "moneda");
		column.put("String:5", "cuadro");
		column.put("String:6", "partidasVencidas");
		column.put("String:7", "noVencidas");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					DiaDemoraTrasVencimiento diaDemoraTrasVencimiento = new DiaDemoraTrasVencimiento();
					diaDemoraTrasVencimiento.setId(Integer.parseInt(res.get("id").toString()));
					diaDemoraTrasVencimiento.setCodigoCliente(res.get("codigoCliente").toString());
					diaDemoraTrasVencimiento.setCodigoVendedor(res.get("codigoVendedor").toString());
					diaDemoraTrasVencimiento.setSociedad(res.get("sociedad").toString());
					diaDemoraTrasVencimiento.setMoneda(res.get("moneda").toString());
					diaDemoraTrasVencimiento.setCuadro(res.get("cuadro").toString());
					diaDemoraTrasVencimiento.setPartidasVencidas(res.get("partidasVencidas").toString());
					diaDemoraTrasVencimiento.setNoVencidas(res.get("noVencidas").toString());
					listaDiaDemoraTrasVencimiento.add(diaDemoraTrasVencimiento);
				}
			}
		}
		return listaDiaDemoraTrasVencimiento;
	}
}