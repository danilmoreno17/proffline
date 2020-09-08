package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlBancoPromesaImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaBancoPromesa(List<BancoPromesa> lstBancoPromesa){
		List<String> listaBancoPromesa = new ArrayList<String>();
		String sql = "";
		for(BancoPromesa bancoPromesa : lstBancoPromesa){
			if(bancoPromesa.getTipoRecaudo().equals("")){
				sql = "INSERT INTO PROFFLINE_TB_BANCO_PROMESA (idBancoPromesa, descripcionBancoPromesa) VALUES ('"
					+ bancoPromesa.getIdBancoPromesa()
					+ "','" + bancoPromesa.getDescripcionBancoPromesa() + "');";
			}else{
				sql = "INSERT INTO PROFFLINE_TB_BANCO_PROMESA (idBancoPromesa, descripcionBancoPromesa, tipoRecaudo, depApps) VALUES ('"
					+ bancoPromesa.getIdBancoPromesa()
					+ "','" + bancoPromesa.getDescripcionBancoPromesa() 
					+ "','" + bancoPromesa.getTipoRecaudo()
					+ "','" + bancoPromesa.getDepApps() + "');";
			}
			listaBancoPromesa.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaBancoPromesa, "BANCO PROMESA", Constante.BD_SYNC);
	}
	//	Marcelo Moyano Insertar Banco Recaudo
	//	S o N 
//	public void insertarListaBancoPromesaRecaudo(List<BancoPromesa> lstBancoPromesa){
//	public void insertarListaBancoPromesa(List<BancoPromesa> lstBancoPromesa){
//		List<String> listaBancoPromesa = new ArrayList<String>();
//		for(BancoPromesa bancoPromesa : lstBancoPromesa){
//			String sql = "INSERT INTO PROFFLINE_TB_BANCO_PROMESA (idBancoPromesa, descripcionBancoPromesa, tipoRecaudo) VALUES ('"
//					+ bancoPromesa.getIdBancoPromesa()
//					+ "','"
//					+ bancoPromesa.getDescripcionBancoPromesa() 
//					+ "','"
//					+ bancoPromesa.getTipoRecaudo() + "');";
//			listaBancoPromesa.add(sql);
//		}
//		resultExecuteList = new ResultExecuteList();
//		resultExecuteList.insertarListaConsultas(listaBancoPromesa, "BANCO PROMESA", Constante.BD_SYNC);
//	}
	// Marcelo Moyano 02/04/2013 - 03:00
	
	public boolean eliminarListaBancoPromesa(){
		String sql = "DELETE FROM PROFFLINE_TB_BANCO_PROMESA;";
		resultExecute = new ResultExecute(sql, "BANCO PROMESA", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BancoPromesa> obtenerListaBancoPromesa(){
		List<BancoPromesa> listaBancoPromesa = new ArrayList<BancoPromesa>();
		column = new HashMap();
		column.put("String:0", "idBancoPromesa");
		column.put("String:1", "descripcionBancoPromesa");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_BANCO_PROMESA;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					BancoPromesa bancoPromesa = new BancoPromesa();
					bancoPromesa.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					bancoPromesa.setDescripcionBancoPromesa(res.get("descripcionBancoPromesa").toString());
					listaBancoPromesa.add(bancoPromesa);
				}
			}
		}
		return listaBancoPromesa;
	}
	/*
	 * @author	Marcelo Moyano
	 * 			Obtener Bancos Recaudo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BancoPromesa> obtenerListaBancoPromesaRecaudo(){
		List<BancoPromesa> listaBancoPromesa = new ArrayList<BancoPromesa>();
		column = new HashMap();
		column.put("String:0", "idBancoPromesa");
		column.put("String:1", "descripcionBancoPromesa");
		column.put("String:2", "tipoRecaudo");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_BANCO_PROMESA WHERE TIPORECAUDO = 'S' OR IDBANCOPROMESA = '';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					BancoPromesa bancoPromesa = new BancoPromesa();
					bancoPromesa.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					bancoPromesa.setDescripcionBancoPromesa(res.get("descripcionBancoPromesa").toString());
					bancoPromesa.setTipoRecaudo(res.get("tipoRecaudo").toString());
					listaBancoPromesa.add(bancoPromesa);
				}
			}
		}
		return listaBancoPromesa;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BancoPromesa> obtenerListaBancoPromesaDepApps(){
		List<BancoPromesa> listaBancoPromesa = new ArrayList<BancoPromesa>();
		column = new HashMap();
		column.put("String:0", "idBancoPromesa");
		column.put("String:1", "descripcionBancoPromesa");
		column.put("String:2", "depApps");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_BANCO_PROMESA WHERE depApps = 'S' OR IDBANCOPROMESA = '';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					BancoPromesa bancoPromesa = new BancoPromesa();
					bancoPromesa.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					bancoPromesa.setDescripcionBancoPromesa(res.get("descripcionBancoPromesa").toString());
					bancoPromesa.setDepApps(res.get("depApps").toString());
					listaBancoPromesa.add(bancoPromesa);
				}
			}
		}
		return listaBancoPromesa;
	}
}