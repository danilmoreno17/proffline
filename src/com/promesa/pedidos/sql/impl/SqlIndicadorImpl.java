package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.Indicador;
import com.promesa.pedidos.sql.SqlIndicador;
import com.promesa.util.Constante;

public class SqlIndicadorImpl implements SqlIndicador {
	
	private static SqlIndicador instance = null;
	
	public static SqlIndicador create(){
		synchronized (SqlIndicadorImpl.class) {
			if(instance == null){
				instance = new SqlIndicadorImpl();
			}
			return instance;
		}
	}

	@Override
	public void insertIndicador(List<Indicador> indicadores) {
		if(indicadores != null && indicadores.size() > 0){
			final List<String> listaSQL = new ArrayList<String>();
			listaSQL.add("DELETE FROM PROFFLINE_TB_INDICADORES");
			for (Indicador i : indicadores) {
				listaSQL.add("INSERT INTO PROFFLINE_TB_INDICADORES ( CLIENTE,MONTO,ACUMULADO,ESTATUS) VALUES ('"
						+ i.getCodigoCliente() + "','" + i.getMonto() +"','"+i.getAcumulado()+ "','" + i.getEstatus()+ "')");
			}
			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "Indicadores", Constante.BD_SYNC);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Indicador getIndicacorByCliente(String codigoCliente) {
		HashMap column = new HashMap();
		String sql = "SELECT * FROM PROFFLINE_TB_INDICADORES WHERE CLIENTE= '"+codigoCliente+"';";
		column.put("String:0", "cliente");
		column.put("String:1", "monto");
		column.put("String:2", "acumulado");
		column.put("String:3", "estatus");
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		HashMap<Integer, HashMap> mapResultado = resultExecuteQuery.getMap();
		if(mapResultado != null && mapResultado.size() > 0){
			HashMap res = (HashMap) mapResultado.get(0);
			Indicador i =  new Indicador();
			i.setCodigoCliente(res.get("cliente").toString().trim());
			i.setMonto(res.get("monto").toString().trim());
			i.setAcumulado(res.get("acumulado").toString());
			i.setEstatus(res.get("estatus").toString().trim());
			return i;
		}
		return null;
	}

}
