package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.Presupuesto;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlBancoClienteImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaBancoCliente(List<BancoCliente> lstBancoCliente) throws Exception {
		List<String> listaBancoCliente = new ArrayList<String>();
		for(BancoCliente bancoCliente : lstBancoCliente){
			String sql = "INSERT INTO PROFFLINE_TB_BANCO_CLIENTE (codigoCliente, nroCtaBcoCliente, idBancoCliente, descripcionBancoCliente) VALUES ('"
					+ bancoCliente.getCodigoCliente()
					+ "','" + bancoCliente.getNroCtaBcoCliente()
					+ "','" + bancoCliente.getIdBancoCliente()
					+ "','" + bancoCliente.getDescripcionBancoCliente() + "');";
			listaBancoCliente.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaBancoCliente, "BANCO CLIENTE", Constante.BD_SYNC);
	}
	
	public boolean eliminarBancoCliente(){
		String sql = "DELETE FROM PROFFLINE_TB_BANCO_CLIENTE;";
		resultExecute = new ResultExecute(sql, "BANCO CLIENTE", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BancoCliente> obtenerListaBancoCliente(String codigoCliente){
		List<BancoCliente> listaBancoCliente = new ArrayList<BancoCliente>();
		column = new HashMap();
		column.put("String:0", "codigoCliente");
		column.put("String:1", "nroCtaBcoCliente");
		column.put("String:2", "idBancoCliente");
		column.put("String:3", "descripcionBancoCliente");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_BANCO_CLIENTE " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					BancoCliente bancoCliente = new BancoCliente();
					bancoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					bancoCliente.setNroCtaBcoCliente(res.get("nroCtaBcoCliente").toString());
					bancoCliente.setIdBancoCliente(res.get("idBancoCliente").toString());
					bancoCliente.setDescripcionBancoCliente(res.get("descripcionBancoCliente").toString());
					listaBancoCliente.add(bancoCliente);
				}
			}
		}
		return listaBancoCliente;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getDescripcionBancocuenta(String codigoCliente, String numeroCuenta){
		column = new HashMap();
		column.put("String:0", "descripcionBancoCliente");
		String sql = "SELECT descripcionBancoCliente FROM PROFFLINE_TB_BANCO_CLIENTE " + "WHERE codigoCliente = '" + codigoCliente + "' and" +
				" nroCtaBcoCliente = '"+numeroCuenta+"';";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		String descripcion = "";
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			descripcion = res.get("descripcionBancoCliente").toString();
		}
		return descripcion;
	}
	
	
}