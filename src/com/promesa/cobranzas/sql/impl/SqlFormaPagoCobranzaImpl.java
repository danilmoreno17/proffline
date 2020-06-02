package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlFormaPagoCobranzaImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaFormaPagoCobranza(List<FormaPago> lstFormaPagoCobranza){
		List<String> listaFormaPagoCobranza = new ArrayList<String>();
		for(FormaPago formaPagoCobranza : lstFormaPagoCobranza){
			String sql = "INSERT INTO PROFFLINE_TB_FORMA_PAGO_COBRANZA (idFormaPago, descripcionFormaPago) VALUES ('"
					+ formaPagoCobranza.getIdFormaPago() + "','" + formaPagoCobranza.getDescripcionFormaPago() + "');";
			listaFormaPagoCobranza.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaFormaPagoCobranza, "FORMA PAGO COBRANZA", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaFormaPagoCobranza(){
		String sql = "DELETE FROM PROFFLINE_TB_FORMA_PAGO_COBRANZA;";
		resultExecute = new ResultExecute(sql, "FORMA PAGO COBRANZA", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<FormaPago> obtenerListaFormaPagoCobranza(){
		List<FormaPago> listaFormaPagoCobranza = new ArrayList<FormaPago>();
		column = new HashMap();
		column.put("String:0", "idFormaPago");
		column.put("String:1", "descripcionFormaPago");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_FORMA_PAGO_COBRANZA order by descripcionFormaPago;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					FormaPago formaPagoCobranza = new FormaPago();
					formaPagoCobranza.setIdFormaPago(res.get("idFormaPago").toString());
					formaPagoCobranza.setDescripcionFormaPago(res.get("descripcionFormaPago").toString());
					listaFormaPagoCobranza.add(formaPagoCobranza);
				}
			}
		}
		return listaFormaPagoCobranza;
	}
}