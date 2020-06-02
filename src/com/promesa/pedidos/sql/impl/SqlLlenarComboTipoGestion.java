package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.sql.SqlComboTipoGestion;
import com.promesa.pedidos.bean.BeanTipoGestion;
import com.promesa.util.Constante;

public class SqlLlenarComboTipoGestion implements SqlComboTipoGestion {
	
	private String sqlTipoGestion = null;
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	private List<BeanTipoGestion> listaTipoGestion = null;
	
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	
	private static String BSAR = "bsar";
	private static String DESCRIPCION = "vtext";
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaTipoGestion() {
		column = new HashMap();
		listaTipoGestion = new ArrayList<BeanTipoGestion>();
		column.put("String:0", BSAR);
		column.put("String:1", DESCRIPCION);
		sqlTipoGestion = "SELECT * FROM PROFFLINE_TB_TIPO_GESTION";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlTipoGestion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			BeanTipoGestion bean = new BeanTipoGestion();
			res = (HashMap) mapResultado.get(i);
			bean.setBsar(res.get(BSAR).toString());
			bean.setDescripcion(res.get(DESCRIPCION).toString());
			listaTipoGestion.add(bean);
		}
	}
	
	@Override
	public List<BeanTipoGestion> getListaTipoGestion() {
		return listaTipoGestion;
	}
	
}
