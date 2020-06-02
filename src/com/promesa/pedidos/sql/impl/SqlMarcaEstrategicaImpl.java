package com.promesa.pedidos.sql.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.bean.NombreMarcaEstrategica;
import com.promesa.pedidos.sql.SqlMarcaEstrategica;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlMarcaEstrategicaImpl implements SqlMarcaEstrategica {
	
	private static SqlMarcaEstrategica instance = null;
	
	public static SqlMarcaEstrategica create(){
		synchronized (SqlMarcaEstrategicaImpl.class) {
			if(instance == null){
				instance = new SqlMarcaEstrategicaImpl();
			}
			return instance;
		}
	}

	@Override
	public void insertMarcaEstrategica(List<MarcaEstrategica> marcas) {
		if(marcas != null && marcas.size() >0){
			final List<String> listaSQL = new ArrayList<String>();
			listaSQL.add("DELETE FROM PROFFLINE_TB_MARCA_ESTRATEGICA");
			for (MarcaEstrategica ms : marcas) {
				listaSQL.add("INSERT INTO PROFFLINE_TB_MARCA_ESTRATEGICA ( CLIENTE,MARCA,PRESUPUESTO,ACUMULADO, FECHA) VALUES ('"
						+ ms.getCodigoCliente() + "','" + ms.getMarca() + "','" + ms.getPresupuesto()+"','"+ms.getAcumulado() +"','"+ms.getFecha()+ "')");
			}
			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "MarcaEstrategica", Constante.BD_SYNC);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MarcaEstrategica> getMarcaEstrategicaByCliente(String cliente) {
		// TODO Auto-generated method stub
		HashMap column = new HashMap();
		String sql = "SELECT * FROM PROFFLINE_TB_MARCA_ESTRATEGICA WHERE CLIENTE= '"+cliente+"';";
		column.put("String:0", "CLIENTE");
		column.put("String:1", "MARCA");
		column.put("String:2", "PRESUPUESTO");
		column.put("String:3", "ACUMULADO");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		List<MarcaEstrategica> marcas = null;
		if(mapResultado != null){
			marcas = new ArrayList();
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				MarcaEstrategica ms =  new MarcaEstrategica();
				ms.setCodigoCliente(res.get("CLIENTE").toString().trim());
				ms.setAcumulado(res.get("ACUMULADO").toString().trim());
				ms.setMarca(res.get("MARCA").toString().trim());
				ms.setPresupuesto(res.get("PRESUPUESTO").toString().trim());
				marcas.add(ms);
			}
			
			return marcas;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean perteneceAMarcaEstrategica(String cliente, String marca) {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) AS CANTIDAD FROM PROFFLINE_TB_MARCA_ESTRATEGICA WHERE CLIENTE='"+cliente+"' AND MARCA='"+marca+"';";
		HashMap column = new HashMap();
		column.put("String:0", "CANTIDAD");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		if(mapResultado != null && mapResultado.size() > 0){
			HashMap res = (HashMap) mapResultado.get(0);
			int c = Integer.parseInt(res.get("CANTIDAD").toString().trim());
			if(c > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public void inserNombreMarcaEstrategica(List<NombreMarcaEstrategica> nombresMs) {
		if(nombresMs != null && nombresMs.size() > 0){
			final List<String> listaSQL = new ArrayList<String>();
			listaSQL.add("DELETE FROM PROFFLINE_TB_NOMBRE_MARCA_ESTRATEGICA");
			for (NombreMarcaEstrategica ms : nombresMs) {
				listaSQL.add("INSERT INTO PROFFLINE_TB_NOMBRE_MARCA_ESTRATEGICA ( MARCA,NOMBRE) VALUES ('"
						+ ms.getMarca() + "','" + ms.getNombre() + "')");
			}
			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "NombreMarcaEstrategica", Constante.BD_SYNC);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getNombreMarcaEstrategicaByMarca(String marca) {
		String sql = "SELECT NOMBRE AS NOMBRE FROM PROFFLINE_TB_NOMBRE_MARCA_ESTRATEGICA WHERE MARCA='"+marca+"';";
		HashMap column = new HashMap();
		column.put("String:0", "NOMBRE");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		if(mapResultado != null && mapResultado.size() > 0){
			HashMap res = (HashMap) mapResultado.get(0);
			return res.get("NOMBRE").toString().trim();
			
		}
		return null;
	}

	@Override
	public void actualizarMarcaEstrategica(List<MarcaEstrategica> marcas) {
		// TODO Auto-generated method stub
		final List<String> listaSQL = new ArrayList<String>();
		if(marcas != null && marcas.size() > 0){
			for (MarcaEstrategica ms : marcas) {
				Double dblAcum;
				try {
					dblAcum = Double.parseDouble(ms.getAcumulado());
				}catch(Exception e) {
					dblAcum=0.00;
				}
				Double dblValor;
				try {
					dblValor = Double.parseDouble(ms.getValor());
				}catch(Exception e) {
					dblValor=0.00;
				}
				Double dblNew = dblAcum+dblValor;
				listaSQL.add("UPDATE PROFFLINE_TB_MARCA_ESTRATEGICA SET ACUMULADO='"+dblNew+"' WHERE CLIENTE='"
						+ ms.getCodigoCliente() + "' AND MARCA ='" + ms.getMarca() + "';");
			}
			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "Marca Estrategica", Constante.BD_SYNC);
			Promesa.getInstance().mostrarAvisoSincronizacion("");
		}
	}
	
	
}
