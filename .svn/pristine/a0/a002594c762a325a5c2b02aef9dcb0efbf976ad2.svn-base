package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.PartidaIndividual;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlPartidaIndividualImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaPartidaIndividual(List<PartidaIndividual> lstPartidaIndividual){
		List<String> listaPartidaIndividual = new ArrayList<String>();
		for(PartidaIndividual partidaIndividual : lstPartidaIndividual){
			String sql = "INSERT INTO PROFFLINE_TB_PARTIDA_INDIVIDUAL (codigoCliente, codigoVendedor, numeroDocumento, claseDocumento,"
					+ "posicion, fechaDocumento, fechaVencimiento, registradoEl, moneda, impteDePos, referencia) VALUES ('"
					+ Long.parseLong(partidaIndividual.getCodigoCliente())
					+ "','" + partidaIndividual.getCodigoVendedor()
					+ "','" + partidaIndividual.getNumeroDocumento()
					+ "','" + partidaIndividual.getClaseDocumento()
					+ "','" + partidaIndividual.getPosicion()
					+ "','" + partidaIndividual.getFechaDocumento()
					+ "','" + partidaIndividual.getFechaVencimiento()
					+ "','" + partidaIndividual.getRegistradoEl()
					+ "','" + partidaIndividual.getMoneda()
					+ "','" + partidaIndividual.getImpteDePos()
					+ "','" + partidaIndividual.getReferencia() + "');";
			listaPartidaIndividual.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaPartidaIndividual, "PARTIDA INDIVIDUAL", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaPartidaIndividual(){
		String sql = "DELETE FROM PROFFLINE_TB_PARTIDA_INDIVIDUAL;";
		resultExecute = new ResultExecute(sql, "PARTIDA INDIVIDUAL", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PartidaIndividual> obtenerListaPartidaIndividual(String codigoCliente){
		List<PartidaIndividual> listaPartidaIndividual = new ArrayList<PartidaIndividual>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "numeroDocumento");
		column.put("String:4", "claseDocumento");
		column.put("String:5", "posicion");
		column.put("String:6", "fechaDocumento");
		column.put("String:7", "fechaVencimiento");
		column.put("String:8", "registradoEl");
		column.put("String:9", "moneda");
		column.put("String:10", "impteDePos");
		column.put("String:11", "referencia");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PARTIDA_INDIVIDUAL " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					PartidaIndividual partidaIndividual = new PartidaIndividual();
					partidaIndividual.setId(Long.parseLong(res.get("id").toString()));
					partidaIndividual.setCodigoCliente(res.get("codigoCliente").toString());
					partidaIndividual.setCodigoVendedor(res.get("codigoVendedor").toString());
					partidaIndividual.setNumeroDocumento(res.get("numeroDocumento").toString());
					partidaIndividual.setClaseDocumento(res.get("claseDocumento").toString());
					partidaIndividual.setPosicion(res.get("posicion").toString());
					partidaIndividual.setFechaDocumento(res.get("fechaDocumento").toString());
					partidaIndividual.setFechaVencimiento(res.get("fechaVencimiento").toString());
					partidaIndividual.setRegistradoEl(res.get("registradoEl").toString());
					partidaIndividual.setMoneda(res.get("moneda").toString());
					partidaIndividual.setImpteDePos(res.get("impteDePos").toString());
					partidaIndividual.setReferencia(res.get("referencia").toString());
					listaPartidaIndividual.add(partidaIndividual);
				}
			}
		}
		return listaPartidaIndividual;
	}
}