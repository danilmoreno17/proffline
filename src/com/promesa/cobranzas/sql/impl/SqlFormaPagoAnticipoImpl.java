package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlFormaPagoAnticipoImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaFormaPagoAnticipo(List<FormaPago> lstFormaPagoAnticipo){
		List<String> listaFormaPagoAnticipo = new ArrayList<String>();
		for(FormaPago formaPagoAnticipo : lstFormaPagoAnticipo){
			String sql = "INSERT INTO PROFFLINE_TB_FORMA_PAGO_ANTICIPO (idFormaPago, descripcionFormaPago) VALUES ('"
					+ formaPagoAnticipo.getIdFormaPago() + "','" + formaPagoAnticipo.getDescripcionFormaPago() + "');";
			listaFormaPagoAnticipo.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaFormaPagoAnticipo, "FORMA PAGO ANTICIPO", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaFormaPagoAnticipo(){
		String sql = "DELETE FROM PROFFLINE_TB_FORMA_PAGO_ANTICIPO;";
		resultExecute = new ResultExecute(sql, "FORMA PAGO ANTICIPO", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<FormaPago> obtenerListaFormaPagoAnticipo(){
		List<FormaPago> listaFormaPagoAnticipo = new ArrayList<FormaPago>();
		column = new HashMap();
		column.put("String:0", "idFormaPago");
		column.put("String:1", "descripcionFormaPago");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_FORMA_PAGO_ANTICIPO;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					FormaPago formaPagoAnticipo = new FormaPago();
					formaPagoAnticipo.setIdFormaPago(res.get("idFormaPago").toString());
					formaPagoAnticipo.setDescripcionFormaPago(res.get("descripcionFormaPago").toString());
					listaFormaPagoAnticipo.add(formaPagoAnticipo);
				}
			}
		}
		return listaFormaPagoAnticipo;
	}
}