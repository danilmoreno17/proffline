package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.HistorialPago;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlHistorialPagoImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaHistorialPago(List<HistorialPago> lstHistorialPago){
		List<String> listaHistorialPago = new ArrayList<String>();
		for(HistorialPago historialPago : lstHistorialPago){
			String sql = "INSERT INTO PROFFLINE_TB_HISTORIAL_PAGO (codigoCliente, codigoVendedor, moneda, ejercicio,"
					+ "periodo, conDPP, diasDemora1, sinDescuento, diasDemora2, ctd) VALUES ('" + Long.parseLong(historialPago.getCodigoCliente())
					+ "','" + historialPago.getCodigoVendedor() + "','" + historialPago.getMoneda()
					+ "','" + historialPago.getEjercicio() 		+ "','" + historialPago.getPeriodo()
					+ "','" + historialPago.getConDPP() 		+ "','" + historialPago.getDiasDemora1()
					+ "','" + historialPago.getSinDescuento() 	+ "','" + historialPago.getDiasDemora2()
					+ "','" + historialPago.getCtd() 			+ "');";
			listaHistorialPago.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaHistorialPago, "HISTORIAL PAGO", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaHistorialPago(){
		String sql = "DELETE FROM PROFFLINE_TB_HISTORIAL_PAGO;";
		resultExecute = new ResultExecute(sql, "HISTORIAL PAGO", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HistorialPago> obtenerListaHistorialPago(String codigoCliente){
		List<HistorialPago> listaHistorialPago = new ArrayList<HistorialPago>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "moneda");
		column.put("String:4", "ejercicio");
		column.put("String:5", "periodo");
		column.put("String:6", "conDPP");
		column.put("String:7", "diasDemora1");
		column.put("String:8", "sinDescuento");
		column.put("String:9", "diasDemora2");
		column.put("String:10", "ctd");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_HISTORIAL_PAGO " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					HistorialPago historialPago = new HistorialPago();
					historialPago.setId(Integer.parseInt(res.get("id").toString()));
					historialPago.setCodigoCliente(res.get("codigoCliente").toString());
					historialPago.setCodigoVendedor(res.get("codigoVendedor").toString());
					historialPago.setMoneda(res.get("moneda").toString());
					historialPago.setEjercicio(res.get("ejercicio").toString());
					historialPago.setPeriodo(res.get("periodo").toString());
					historialPago.setConDPP(res.get("conDPP").toString());
					historialPago.setDiasDemora1(res.get("diasDemora1").toString());
					historialPago.setSinDescuento(res.get("sinDescuento").toString());
					historialPago.setDiasDemora2(res.get("diasDemora2").toString());
					historialPago.setCtd(res.get("ctd").toString());
					listaHistorialPago.add(historialPago);
				}
			}
		}
		return listaHistorialPago;
	}
}