package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import util.ConexionSAP;

import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.SqlDivision;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlDivisionImpl implements SqlDivision {

	private String sqlDivsion = null;
	@SuppressWarnings({ "rawtypes" })
	private HashMap column = new HashMap();
	private List<BeanJerarquia> listaDivision = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private static String ID_PADRE = "txtIdPadre";
	private static String PRODH = "txtPRODH";
	private static String S = "txtS";
	private static String VTEXT = "txtVTEXT";
	private static String ZZSEQ = "txtZZSEQ";
	private static String ICON = "txtICON";
	private static String I = "txtI";
	private static String CELL_DESIGN = "txtCELLDESIGN";

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaJerarquia() {
		column = new HashMap();
		listaDivision = new ArrayList<BeanJerarquia>();
		column.put("String:0", ID_PADRE);
		column.put("String:1", PRODH);
		column.put("String:2", S);
		column.put("String:3", VTEXT);
		column.put("String:4", ZZSEQ);
		column.put("String:5", ICON);
		column.put("String:6", I);
		column.put("String:7", CELL_DESIGN);
		sqlDivsion = "SELECT * FROM PROFFLINE_TB_JERARQUIA";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDivsion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			BeanJerarquia division = new BeanJerarquia();
			res = (HashMap) mapResultado.get(i);
			division.setStrIdPadre(res.get(ID_PADRE).toString());
			division.setStrPRODH(res.get(PRODH).toString());
			division.setStrS(res.get(S).toString());
			division.setStrVTEXT(res.get(VTEXT).toString());
			division.setStrZZSEQ(res.get(ZZSEQ).toString());
			division.setStrICON(res.get(ICON).toString());
			division.setStrI(res.get(I).toString());
			division.setStrCellDesign(res.get(CELL_DESIGN).toString());
			listaDivision.add(division);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanJerarquia> getListaJerarquiaPorNiveles(String nivel) {
		column = new HashMap();
		List<BeanJerarquia> lista = new ArrayList<BeanJerarquia>();
		column.put("String:0", ID_PADRE);
		column.put("String:1", PRODH);
		column.put("String:2", S);
		column.put("String:3", VTEXT);
		column.put("String:4", ZZSEQ);
		column.put("String:5", ICON);
		column.put("String:6", I);
		column.put("String:7", CELL_DESIGN);
		String sqlJerarquia = "select * from PROFFLINE_TB_JERARQUIA where PROFFLINE_TB_JERARQUIA.txtS='" + nivel + "' ORDER BY txtVTEXT";

		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlJerarquia, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			BeanJerarquia division = new BeanJerarquia();
			res = (HashMap) mapResultado.get(i);
			division.setStrPRODH(res.get(PRODH).toString());
			division.setStrVTEXT(res.get(VTEXT).toString());
			lista.add(division);
		}
		return lista;
	}

	@Override
	public List<BeanJerarquia> getListaJerarquia() {
		return listaDivision;
	}

	@SuppressWarnings("finally")
	@Override
	public List<BeanJerarquia>[] jerarquiaMateriales() {
		@SuppressWarnings("unchecked")
		List<BeanJerarquia>[] jerarquia = new List[5];
		try {
			jerarquia[0] = getListaJerarquiaPorNiveles("1");
			jerarquia[1] = getListaJerarquiaPorNiveles("2");
			jerarquia[2] = getListaJerarquiaPorNiveles("3");
			jerarquia[3] = getListaJerarquiaPorNiveles("4");
			jerarquia[4] = getListaJerarquiaPorNiveles("5");
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			jerarquia = null;
		} finally {
			return jerarquia;
		}
	}
	
	/*
	 * @author		MARCELO MOYANO
	 * 
	 * 				METODO QUE OBTINEN EL TIPO
	 * 				DEL MATERIAL
	 * 
	 * @param		RECIBE COMO PARAMETRO UN VALOR
	 * 				ENTERO QUE REPRESENTA EL TIPO
	 * 				DEL MATERIAL
	 * 
	 * @return		RETORNA UNA CADENA DE CARACTER
	 * 				QUE REPRESENTA EL TIPO DEL MATERIAL
	 * 				Y PARA GERERAR LA SENTENCIA SQL
	 * 				PARA HACER LA CONSULTA EN LA BASE DE DATO
	 */
	private String ObtenerType(int tipo){
		String type ;
		switch (tipo) {
		case 0:
			type = "";
			break;
		case 1:
			type = " AND TYPEMAT='N' ";
			break;
		case 2:
			type = " AND TYPEMAT='P' ";
			break;
		case 3:
			type = " AND TYPEMAT='R' ";
			break;
		case 4:
			type = " AND TYPEMAT='B' ";
			break;
		default:
			type = "";
			break;
		}
		return type;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] buscarCantidadMateriales(int tipo, String codigo,
			String shortText, String longText, String codigoMaterial, String tipologia, int t, String marca) {

		if (codigo == null) {
			codigo = "";
		} else {
			codigo = codigo.replaceAll("'", "");
		}
		shortText = shortText.replaceAll("'", "");
		longText = longText.replaceAll("'", "");
		codigoMaterial = codigoMaterial.replaceAll("'", "");
		marca = marca.replaceAll("'", "");
		column = new HashMap();
		column.put("String:0", "COUNT()");
		
		String type = ObtenerType(t);

		String strCadena1 = "";
		String strCadena2 = "";
		String strCadena3 = "";
		String sqlSelect = "SELECT * FROM PROFFLINE_TB_MATERIAL WHERE";
		String sqlCount = "SELECT COUNT() FROM PROFFLINE_TB_MATERIAL WHERE";
		String sqlRetorno = "SELECT * FROM PROFFLINE_TB_MATERIAL WHERE";
		sqlDivsion = "SELECT COUNT() FROM PROFFLINE_TB_MATERIAL WHERE";
		if (shortText != null && !shortText.isEmpty()) {
			shortText = shortText.trim();
			strCadena1 = "(PRDHA LIKE '" + codigo + "%' AND SHORT_TEXT LIKE '%" + shortText + "%'" + type + ") ";
		}
		if (marca != null && !marca.isEmpty()) {
			marca = marca.trim();
			strCadena2 = "(PRDHA LIKE '" + codigo + "%' AND NORMT LIKE '%" + shortText + "%'" + type + ") ";
		}
		if (longText != null && !longText.isEmpty()) {
			longText = longText.trim();
			strCadena3 = "(PRDHA LIKE '" + codigo + "%' AND TEXT_LINE LIKE '%" + longText + "%' " + type +") ";
		}
		if (!strCadena1.equals("")) {
			sqlCount = sqlCount + strCadena1;
			sqlSelect = sqlSelect + strCadena1;
		}
		if (!strCadena2.equals("")) {
			sqlCount = sqlCount + " OR " + strCadena2;
			sqlSelect = sqlSelect + " OR " + strCadena2;
		}
		if (!strCadena3.equals("")) {
			sqlCount = sqlCount + " OR " + strCadena3;
			sqlSelect = sqlSelect + " OR " + strCadena3;
		}
		if (strCadena1.equals("") && strCadena2.equals("") && strCadena3.equals("")) {
			sqlCount = sqlCount + " PRDHA LIKE '" + codigo + "%' " + type;
			sqlSelect = sqlSelect + " PRDHA LIKE '" + codigo + "%' " + type;
		}
		if (codigoMaterial != null && !codigoMaterial.isEmpty()) {
			codigoMaterial = codigoMaterial.trim();
			String sql_temp = "OR MATNR LIKE '";
			for (int i = 0; i < codigoMaterial.length(); i++) {
				char c = codigoMaterial.charAt(i);
				if (c == '*') {
					sql_temp += "%";
				} else {
					sql_temp += c;
				}
			}
			sqlCount = sqlCount + sql_temp + "%'";
			sqlSelect = sqlSelect + sql_temp + "%'";
			sqlDivsion = sqlDivsion + sql_temp + "%' ";
			sqlRetorno = sqlRetorno + sql_temp + "%' ";
		}
		if (tipo == 2) {
			if (tipologia != null) {
				try {
					tipologia = "" + Integer.parseInt(tipologia);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
				}
				sqlCount = sqlCount + " AND GRUPO_COMPRA='" + Util.completarCeros(3, tipologia) + "'";
				sqlSelect = sqlSelect + " AND GRUPO_COMPRA='" + Util.completarCeros(3, tipologia) + "'";
				sqlDivsion = sqlDivsion + " AND GRUPO_COMPRA='" + Util.completarCeros(3, tipologia) + "'";
				sqlRetorno = sqlRetorno + " AND GRUPO_COMPRA='" + Util.completarCeros(3, tipologia) + "'";
			}
		}
		
		sqlCount = sqlCount + " ORDER BY PRDHA, ZZORDCO;";
		sqlSelect = sqlSelect + " ORDER BY PRDHA, ZZORDCO";
		
		sqlCount = sqlCount.trim() + ";";
		sqlRetorno = sqlRetorno.trim() + ";";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlCount, column, Constante.BD_SYNC);

		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		long cantidadRegistros = 0l;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			try {
				cantidadRegistros = Long.parseLong(res.get("COUNT()").toString());
			} catch (Exception e) {
				cantidadRegistros = 0l;
			}
		}
		Object[] objetos = new Object[2];
		objetos[0] = sqlSelect;
		objetos[1] = cantidadRegistros;
		return objetos;
	}

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> buscarMateriales(String consulta, int pagina) {
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		consulta = consulta + " LIMIT " + ((pagina - 1) * Constante.CANTIDAD_REGISTROS_POR_PAGINA)
				+ "," + Constante.CANTIDAD_REGISTROS_POR_PAGINA + ";";
		System.out.println(consulta);
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(consulta, column, Constante.BD_SYNC);
		
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setTypeMat(res.get("TYPEMAT").toString());
			material.setPrdha(res.get("PRDHA").toString());
			materiales.add(material);
		}
		return materiales;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanMaterial> buscarMaterialesTopCliente(int tipo, String codigo, String shortText, String longText,
			String codigoMaterial, String tipologia, int t, String marca, String strCodCliente) {

		codigo = codigo.replaceAll("'", "");
		shortText = shortText.replaceAll("'", "");
		longText = longText.replaceAll("'", "");
		codigoMaterial = codigoMaterial.replaceAll("'", "");
		tipologia = tipologia.replaceAll("'", "");
		marca = marca.replaceAll("'", "");
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");
		
		String type = ObtenerType(t);
		
		String strCadena1 = "";
		String strCadena2 = "";
		String strCadena3 = "";
		String sqlSelect = "SELECT * FROM PROFFLINE_TB_MATERIAL_TOP_CLIENTE WHERE CLIENTE = '" + strCodCliente + "' and ";
		if (shortText != null && !shortText.isEmpty()) {
			shortText = shortText.trim();
			strCadena1 = "(PRDHA LIKE '" + codigo + "%' AND SHORT_TEXT LIKE '%" + shortText + "%'" + type + ") ";
		}

		if (marca != null && !marca.isEmpty()) {
			marca = marca.trim();
			strCadena2 = "(PRDHA LIKE '" + codigo + "%' AND NORMT LIKE '%" + marca + "%'" + type + ") ";
		}

		if (longText != null && !longText.isEmpty()) {
			longText = longText.trim();
			strCadena3 = "(PRDHA LIKE '" + codigo + "%' AND TEXT_LINE LIKE '%" + longText + "%' " + type +") ";
		}
		if (!strCadena1.equals("")) {
			sqlSelect = sqlSelect + strCadena1;
		}
		if (!strCadena2.equals("")) {
			sqlSelect = sqlSelect + " OR " + strCadena2;
		}
		if (!strCadena3.equals("")) {
			sqlSelect = sqlSelect + " OR " + strCadena3;
		}
		if (strCadena1.equals("") && strCadena2.equals("") && strCadena3.equals("")) {
			sqlSelect = sqlSelect + " PRDHA LIKE '" + codigo + "%' " + type;
		}

		if (codigoMaterial != null && !codigoMaterial.isEmpty()) {
			codigoMaterial = codigoMaterial.trim();
			String sql_temp = "OR MATNR LIKE '";
			for (int i = 0; i < codigoMaterial.length(); i++) {
				char c = codigoMaterial.charAt(i);
				if (c == '*') {
					sql_temp += "%";
				} else {
					sql_temp += c;
				}
			}
			sqlSelect = sqlSelect + sql_temp + "%' ";
		}
		if (tipo == 2) {
			if (tipologia != null) {
				try {
					tipologia = "" + Integer.parseInt(tipologia);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
				}
				sqlSelect = sqlSelect + " AND GRUPO_COMPRA='" + Util.completarCeros(3, tipologia) + "'";
			}
		}
		sqlSelect = sqlSelect.trim() + " ORDER BY VENTAS_ACUMULADO DESC;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlSelect, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setTypeMat(res.get("TYPEMAT").toString());
			material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
			material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
			material.setStrCodCliente(res.get("CLIENTE").toString());
			material.setPrdha(res.get("PRDHA").toString());
			materiales.add(material);
		}
		return materiales;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> buscarMaterialesTopTipologia(int tipo, String codigo, String shortText, String longText,
			String codigoMaterial, String tipologia, int t, String marca, String strCodCliente) {

		codigo = codigo.replaceAll("'", "");
		shortText = shortText.replaceAll("'", "");
		longText = longText.replaceAll("'", "");
		codigoMaterial = codigoMaterial.replaceAll("'", "");
		tipologia = tipologia.replaceAll("'", "");
		marca = marca.replaceAll("'", "");
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");
		
		String type = ObtenerType(t);

		String strCadena1 = "";
		String strCadena2 = "";
		String strCadena3 = "";
		String sqlSelect = "SELECT * FROM PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA WHERE CLIENTE ='" + tipologia + "' AND";
		if (shortText != null && !shortText.isEmpty()) {
			shortText = shortText.trim();
			strCadena1 = "(PRDHA LIKE '" + codigo + "%' AND SHORT_TEXT LIKE '%" + shortText + "%'" + type + ") ";
		}
		if (marca != null && !marca.isEmpty()) {
			marca = marca.trim();
			strCadena2 = "(PRDHA LIKE '" + codigo + "%' AND NORMT LIKE '%" + marca + "%'" + type + ") ";
		}
		if (longText != null && !longText.isEmpty()) {
			longText = longText.trim();
			strCadena3 = "(PRDHA LIKE '" + codigo + "%' AND TEXT_LINE LIKE '%" + longText + "%' " + type +") ";
		}

		if (!strCadena1.equals("")) {
			sqlSelect = sqlSelect + strCadena1;
		}
		if (!strCadena2.equals("")) {
			sqlSelect = sqlSelect + " OR " + strCadena2;
		}
		if (!strCadena3.equals("")) {
			sqlSelect = sqlSelect + " OR " + strCadena3;
		}
		if (strCadena1.equals("") && strCadena2.equals("") && strCadena3.equals("")) {
			sqlSelect = sqlSelect + " PRDHA LIKE '" + codigo + "%' " + type;
		}

		if (codigoMaterial != null && !codigoMaterial.isEmpty()) {
			codigoMaterial = codigoMaterial.trim();
			String sql_temp = "OR MATNR LIKE '";
			for (int i = 0; i < codigoMaterial.length(); i++) {
				char c = codigoMaterial.charAt(i);
				if (c == '*') {
					sql_temp += "%";
				} else {
					sql_temp += c;
				}
			}
			sqlSelect = sqlSelect + sql_temp + "%'";
		}
		if (tipo == 2) {
			if (tipologia != null) {
				try {
					tipologia = "" + Integer.parseInt(tipologia);
				} catch (NumberFormatException e) {
					tipologia = "";
				}
				sqlSelect = sqlSelect + " ";
			}
		}
		sqlSelect = sqlSelect.trim() + " ORDER BY VENTAS_ACUMULADO DESC;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlSelect, column, Constante.BD_SYNC);

		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setTypeMat(res.get("TYPEMAT").toString());
			material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
			material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
			material.setStrCodCliente(res.get("CLIENTE").toString());
			material.setPrdha(res.get("PRDHA").toString());
			materiales.add(material);
		}
		return materiales;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BeanMaterial getMaterial(String codigo) {
		ResultExecuteQuery resultExecuteQuery = null;
		BeanMaterial material = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");

		String sqlMaterial = " SELECT  MATNR , STOCK , S_U , SHORT_TEXT , TEXT_LINE , TARGET_QTY , PRICE_1 , PRICE_2 , PRICE_3 , PRICE_4 , PRDHA , HER , NORMT , ZZORDCO , CELL_DESIGN , TYPEMAT FROM PROFFLINE_TB_MATERIAL WHERE  MATNR ='" + codigo + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			if (mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				material = new BeanMaterial();
				material.setIdMaterial(res.get("MATNR").toString());
				material.setStock(res.get("STOCK").toString());
				material.setUn(res.get("S_U").toString());
				material.setDescripcion(res.get("SHORT_TEXT").toString());
				material.setText_line(res.get("TEXT_LINE").toString());
				material.setTarget_qty(res.get("TARGET_QTY").toString());
				material.setPrice_1(res.get("PRICE_1").toString());
				material.setPrice_2(res.get("PRICE_2").toString());
				material.setPrice_3(res.get("PRICE_3").toString());
				material.setPrice_4(res.get("PRICE_4").toString());
				material.setPrdha(res.get("PRDHA").toString());
				material.setTipoMaterial(res.get("HER").toString());
				material.setNormt(res.get("NORMT").toString());
				material.setZzordco(res.get("ZZORDCO").toString());
				material.setCell_design(res.get("CELL_DESIGN").toString());
				material.setTypeMat(res.get("TYPEMAT").toString());
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return material;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanMaterial> getMaterialNuevo(String mate) {
		List<BeanMaterial> listaMaterial = new ArrayList<BeanMaterial>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanMaterial material = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "ZZORDCO");
		String sqlMaterialNuevo = " SELECT MATNR, ZZORDCO "
				+ "FROM PROFFLINE_TB_MATERIAL_NUEVO "
				+ "WHERE MATNR LIKE '%" + mate + "%' "
				+ "OR SHORT_TEXT LIKE '%" +mate+ "%' "
				+ "OR TEXT_LINE LIKE '%"+ mate +"%' "
				+ "OR NORMT LIKE '%"+mate+"%' "
				+ "ORDER BY NORMT ASC,ZZORDCO DESC";
		try {
			
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterialNuevo, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			//HashMap res = null;
			//for (int i = 0; i < mapResultado.size(); i++) {
			for(HashMap res:mapResultado.values()){	
				
				material = new BeanMaterial();
				material = getMaterial(res.get("MATNR").toString());
				material.setZzordco(res.get("ZZORDCO").toString());
				listaMaterial.add(material);
				
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaMaterial;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanMaterial> buscarMaterialesPromoOferta(int tipo, String codigo, String shortText, String longText,
			String codigoMaterial, String tipologia, int t, String marca) {

		codigo = codigo.replaceAll("'", "");
		shortText = shortText.replaceAll("'", "");
		longText = longText.replaceAll("'", "");
		codigoMaterial = codigoMaterial.replaceAll("'", "");
		tipologia = tipologia.replaceAll("'", "");
		marca = marca.replaceAll("'", "");
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		
		String type = ObtenerType(t);

		String strCadena1 = "";
		String strCadena2 = "";
		String strCadena3 = "";

		String sqlSelect = "SELECT * FROM PROFFLINE_TB_MATERIAL_PROMO_OFERTA WHERE ";
		String sqlCount = "SELECT COUNT() FROM PROFFLINE_TB_MATERIAL WHERE ";

		if (shortText != null && !shortText.isEmpty()) {
			shortText = shortText.trim();
			strCadena1 = "(PRDHA LIKE '" + codigo + "%' AND SHORT_TEXT LIKE '%" + shortText + "%'" + type + ") ";
		}

		if (marca != null && !marca.isEmpty()) {
			marca = marca.trim();

			strCadena2 = "(PRDHA LIKE '" + codigo + "%' AND NORMT LIKE '%" + marca + "%'" + type + ") ";
		}

		if (longText != null && !longText.isEmpty()) {
			longText = longText.trim();
			strCadena3 = "(PRDHA LIKE '" + codigo + "%' AND TEXT_LINE LIKE '%" + longText + "%' " + type +") ";
		}
		if (!strCadena1.equals("")) {
			sqlCount = sqlCount + strCadena1;
			sqlSelect = sqlSelect + strCadena1;
		}
		if (!strCadena2.equals("")) {
			sqlCount = sqlCount + " OR " + strCadena2;
			sqlSelect = sqlSelect + " OR " + strCadena2;
		}
		if (!strCadena3.equals("")) {
			sqlCount = sqlCount + " OR " + strCadena3;
			sqlSelect = sqlSelect + " OR " + strCadena3;
		}
		if (strCadena1.equals("") && strCadena2.equals("") && strCadena3.equals("")) {
			sqlCount = sqlCount + " PRDHA LIKE '" + codigo + "%' " + type;
			sqlSelect = sqlSelect + " PRDHA LIKE '" + codigo + "%' " + type;
		}

		if (codigoMaterial != null && !codigoMaterial.isEmpty()) {
			codigoMaterial = codigoMaterial.trim();
			String sql_temp = "OR MATNR LIKE '";
			for (int i = 0; i < codigoMaterial.length(); i++) {
				char c = codigoMaterial.charAt(i);
				if (c == '*') {
					sql_temp += "%";
				} else {
					sql_temp += c;
				}
			}
			sqlSelect = sqlSelect + sql_temp + "%'";
		}
		if (tipo == 2) {
			if (tipologia != null) {
				try {
					tipologia = "" + Integer.parseInt(tipologia);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
				}
				sqlSelect = sqlSelect + " ";
			}
		}
		sqlSelect = sqlSelect.trim() + ";";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery( sqlSelect, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setTypeMat(res.get("TYPEMAT").toString());
			material.setPrdha(res.get("PRDHA").toString());
			materiales.add(material);
		}
		return materiales;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanMaterial> buscarMaterialesDescuentoPolitica(int tipo, String codigo, String shortText, String longText,
			String codigoMaterial, String tipologia, int t, String marca) {

		codigo = codigo.replaceAll("'", "");
		shortText = shortText.replaceAll("'", "");
		longText = longText.replaceAll("'", "");
		codigoMaterial = codigoMaterial.replaceAll("'", "");
		tipologia = tipologia.replaceAll("'", "");
		marca = marca.replaceAll("'", "");
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		
		String type = ObtenerType(t);

		String strCadena1 = "";
		String strCadena2 = "";
		String strCadena3 = "";

		String sqlSelect = "SELECT * FROM PROFFLINE_TB_MATERIAL_DSCTO_POLITICA WHERE " ;
		if (shortText != null && !shortText.isEmpty()) {
			shortText = shortText.trim();	
			strCadena1 = "(PRDHA LIKE '" + codigo + "%' AND SHORT_TEXT LIKE '%" + shortText + "%'"+ type +") ";
		}
		if (marca != null && !marca.isEmpty()) {
			marca = marca.trim();
			strCadena2 = "(PRDHA LIKE '" + codigo + "%' AND NORMT LIKE '%" + marca + "%'"+ type +") ";
		}
		if (longText != null && !longText.isEmpty()) {
			longText = longText.trim();
			
			strCadena3 = "(PRDHA LIKE '" + codigo + "%' AND TEXT_LINE LIKE '%" + longText + "%' " + type +") ";
		}
		if(!strCadena1.equals("")){
			sqlSelect = sqlSelect + strCadena1 ;
		}
		if(!strCadena2.equals("")){
			sqlSelect = sqlSelect + " OR " + strCadena2 ;
		}
		if(!strCadena3.equals("")){
			sqlSelect = sqlSelect + " OR " + strCadena3 ;
		}
		if(strCadena1.equals("") && strCadena2.equals("") && strCadena3.equals("")){
			sqlSelect = sqlSelect + " PRDHA LIKE '" + codigo + "%' " + type ;
		}
		if (codigoMaterial != null && !codigoMaterial.isEmpty()) {
			codigoMaterial = codigoMaterial.trim();
			String sql_temp = "OR MATNR LIKE '";
			for (int i = 0; i < codigoMaterial.length(); i++) {
				char c = codigoMaterial.charAt(i);
				if (c == '*') {
					sql_temp += "%";
				} else {
					sql_temp += c;
				}
			}
			sqlSelect = sqlSelect + sql_temp + "%'";
		}
		if (tipo == 2) {
			if (tipologia != null) {
				try {
					tipologia = "" + Integer.parseInt(tipologia);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
				}
				sqlSelect = sqlSelect + " ";
			}
		}
		sqlSelect = sqlSelect.trim() + ";";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlSelect, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setTypeMat(res.get("TYPEMAT").toString());
			material.setPrdha(res.get("PRDHA").toString());
			try {
				material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
			} catch (Exception e) {
				material.setDblAcumulado(0d);
			}
			try {
				material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
			} catch (Exception e) {
				material.setDblPromedio(0d);
			}
			material.setStrCodCliente("");
			materiales.add(material);
		}
		return materiales;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanMaterial> buscarMaterialesDescuentoManual(int tipo, String codigo, String shortText, String longText,
			String codigoMaterial, String tipologia, int t, String marca) {

		codigo = codigo.replaceAll("'", "");
		shortText = shortText.replaceAll("'", "");
		longText = longText.replaceAll("'", "");
		codigoMaterial = codigoMaterial.replaceAll("'", "");
		tipologia = tipologia.replaceAll("'", "");
		marca = marca.replaceAll("'", "");
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		
		String type = ObtenerType(t);

		String strCadena1 = "";
		String strCadena2 = "";
		String strCadena3 = "";

		String sqlSelect = "SELECT * FROM PROFFLINE_TB_MATERIAL_DSCTO_MANUAL WHERE " ;
		if (shortText != null && !shortText.isEmpty()) {
			shortText = shortText.trim();
			strCadena1 = "(PRDHA LIKE '" + codigo + "%' AND SHORT_TEXT LIKE '%" + shortText + "%'"+ type +") ";
		}
		if (marca != null && !marca.isEmpty()) {
			marca = marca.trim();
			strCadena2 = "(PRDHA LIKE '" + codigo + "%' AND NORMT LIKE '%" + marca + "%'"+ type +") ";
		}

		if (longText != null && !longText.isEmpty()) {
			longText = longText.trim();
			strCadena3 = "(PRDHA LIKE '" + codigo + "%' AND TEXT_LINE LIKE '%" + longText + "%' " + type +") ";
		}
		if (!strCadena1.equals("")) {
			sqlSelect = sqlSelect + strCadena1;
		}
		if (!strCadena2.equals("")) {
			sqlSelect = sqlSelect + " OR " + strCadena2;
		}
		if (!strCadena3.equals("")) {
			sqlSelect = sqlSelect + " OR " + strCadena3;
		}
		if (strCadena1.equals("") && strCadena2.equals("") && strCadena3.equals("")) {
			sqlSelect = sqlSelect + " PRDHA LIKE '" + codigo + "%' " + type;
		}
		if (codigoMaterial != null && !codigoMaterial.isEmpty()) {
			codigoMaterial = codigoMaterial.trim();
			String sql_temp = "OR MATNR LIKE '";
			for (int i = 0; i < codigoMaterial.length(); i++) {
				char c = codigoMaterial.charAt(i);
				if (c == '*') {
					sql_temp += "%";
				} else {
					sql_temp += c;
				}
			}
			sqlSelect = sqlSelect + sql_temp + "%'";
		}
		if (tipo == 2) {
			if (tipologia != null) {
				try {
					tipologia = "" + Integer.parseInt(tipologia);
				} catch (NumberFormatException e) {
					Util.mostrarExcepcion(e);
				}
				sqlSelect = sqlSelect + "%'";
			}
		}
		sqlSelect = sqlSelect.trim() + ";";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlSelect, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setTypeMat(res.get("TYPEMAT").toString());
			material.setPrdha(res.get("PRDHA").toString());
			try {
				material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
			} catch (Exception e) {
				material.setDblAcumulado(0d);
			}
			try {
				material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
			} catch (Exception e) {
				material.setDblPromedio(0d);
			}
			material.setStrCodCliente("");
			materiales.add(material);
		}
		return materiales;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> getListaPrecios(String hierarchy) {
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		sqlDivsion = "SELECT * FROM PROFFLINE_TB_MATERIAL WHERE PRDHA LIKE '" + hierarchy + "%';";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlDivsion, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			res = (HashMap) mapResultado.get(i);
			BeanMaterial material = new BeanMaterial();
			material.setIdMaterial(res.get("MATNR").toString());
			material.setUn(res.get("S_U").toString());
			material.setDescripcion(res.get("SHORT_TEXT").toString());
			material.setPrice_1(res.get("PRICE_1").toString());
			material.setTipoMaterial(res.get("HER").toString());
			material.setNormt(res.get("NORMT").toString());
			material.setText_line(res.get("TEXT_LINE").toString());
			material.setPrdha(res.get("PRDHA").toString());
			materiales.add(material);

		}
		return materiales;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			OBTIENE TODOS LOS MATERIALES DE LA TABLA
	 * 			PROFFLINE_TB_MATERIALES DE LA BASE LOCAL
	 * 			POR ORDEN DE PRDHA Y ZZORDCO.
	 * 
	 * @return	listaMateriales RETORNA UNA LISTA
	 * 			DE MATERIALES.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> obtenerMateriales() {
		List<BeanMaterial> listaMateriales = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");

		String sql = "select * from proffline_tb_material order by prdha, zzordco;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			listaMateriales = new ArrayList<BeanMaterial>();
			if (mapResultado.size() > 0) {
				HashMap res = null;
				for (int i = 0; i < mapResultado.size(); i++) {
					res = (HashMap) mapResultado.get(i);
					BeanMaterial material = new BeanMaterial();
					material.setIdMaterial(res.get("MATNR").toString());
					material.setUn(res.get("S_U").toString());
					material.setDescripcion(res.get("SHORT_TEXT").toString());
					material.setPrice_1(res.get("PRICE_1").toString());
					material.setTipoMaterial(res.get("HER").toString());
					material.setNormt(res.get("NORMT").toString());
					material.setText_line(res.get("TEXT_LINE").toString());
					material.setPrdha(res.get("PRDHA").toString());
					material.setDblAcumulado((Double) res.get("VENTAS_ACUMULADO"));
					material.setDblPromedio((Double) res.get("VENTAS_PROMEDIO"));
					material.setTypeMat(res.get("TYPEMAT").toString());
					listaMateriales.add(material);
				}
			}
		}
		return listaMateriales;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			OBTIENE LOS MATERIALES TOP POR CLIENTE.
	 * 
	 * @param	RECIBE COMO PARAMETRO EL CODIGO DEL CLIENTE.
	 * 
	 * @return	RETORNA UNA LISTA DE MATERIALES SEGUN EL
	 * 			CODIGO DEL CLIENTE.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> obtenerTopCliente(String strCodigoCliente) {
		List<BeanMaterial> listaMateriales = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");

		String sql = "select * from PROFFLINE_TB_MATERIAL_TOP_CLIENTE WHERE CLIENTE = '" + strCodigoCliente + "' order by prdha, zzordco;";		
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			listaMateriales = new ArrayList<BeanMaterial>();
			if (mapResultado.size() > 0) {
				HashMap res = null;
				for (int i = 0; i < mapResultado.size(); i++) {
					res = (HashMap) mapResultado.get(i);
					BeanMaterial material = new BeanMaterial();
					material.setIdMaterial(res.get("MATNR").toString());
					material.setUn(res.get("S_U").toString());
					material.setDescripcion(res.get("SHORT_TEXT").toString());
					material.setPrice_1(res.get("PRICE_1").toString());
					material.setTipoMaterial(res.get("HER").toString());
					material.setNormt(res.get("NORMT").toString());
					material.setText_line(res.get("TEXT_LINE").toString());
					material.setPrdha(res.get("PRDHA").toString());
					material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
					material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
					material.setTypeMat(res.get("TYPEMAT").toString());
					listaMateriales.add(material);
				}
			}
		}
		return listaMateriales;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			OBTIENE LOS MATERIALES POR TIPOLOGIA.
	 * 
	 * @param	RECIBE COMO PARAMETRO EL CODIGO DE
	 * 			TIPOLOGIA.
	 * 
	 * @return	RETORNA UNA LISTA DE MATERIALES SEGUN
	 * 			SU TIPOLOGIA.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> obtenerTopTipologia(String strCodigoTipologia) {
		List<BeanMaterial> listaMateriales = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");

		String sql = "select * from PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA WHERE CLIENTE = '" + strCodigoTipologia + "' order by prdha, zzordco;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			listaMateriales = new ArrayList<BeanMaterial>();
			if (mapResultado.size() > 0) {
				HashMap res = null;
				for (int i = 0; i < mapResultado.size(); i++) {
					res = (HashMap) mapResultado.get(i);
					BeanMaterial material = new BeanMaterial();
					material.setIdMaterial(res.get("MATNR").toString());
					material.setUn(res.get("S_U").toString());
					material.setDescripcion(res.get("SHORT_TEXT").toString());
					material.setPrice_1(res.get("PRICE_1").toString());
					material.setTipoMaterial(res.get("HER").toString());
					material.setNormt(res.get("NORMT").toString());
					material.setText_line(res.get("TEXT_LINE").toString());
					material.setPrdha(res.get("PRDHA").toString());
					material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
					material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
					material.setTypeMat(res.get("TYPEMAT").toString());
					listaMateriales.add(material);
				}
			}
		}
		return listaMateriales;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE OBTIENE TODOS LOS
	 * 			MATERIALES QUE TIENE PROMOCIONES
	 * 			Y OFERTAS ESPECIALES.
	 * 
	 * @return	RETORNA UNA LISTA DE MATERIALES PROMOOFETA
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> obtenerPromoOferta() {
		List<BeanMaterial> listaMateriales = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");

		String sql = "SELECT * FROM PROFFLINE_TB_MATERIAL_PROMO_OFERTA order by prdha, zzordco;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			listaMateriales = new ArrayList<BeanMaterial>();
			if (mapResultado.size() > 0) {
				HashMap res = null;
				for (int i = 0; i < mapResultado.size(); i++) {
					res = (HashMap) mapResultado.get(i);
					BeanMaterial material = new BeanMaterial();
					material.setIdMaterial(res.get("MATNR").toString());
					material.setUn(res.get("S_U").toString());
					material.setDescripcion(res.get("SHORT_TEXT").toString());
					material.setPrice_1(res.get("PRICE_1").toString());
					material.setTipoMaterial(res.get("HER").toString());
					material.setNormt(res.get("NORMT").toString());
					material.setText_line(res.get("TEXT_LINE").toString());
					material.setPrdha(res.get("PRDHA").toString());
					try {
						material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
					} catch (Exception e) {
						material.setDblAcumulado(0d);
					}
					try {
						material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
					} catch (Exception e) {
						material.setDblPromedio(0d);
					}
					material.setTypeMat(res.get("TYPEMAT").toString());
					listaMateriales.add(material);
				}
			}
		}
		return listaMateriales;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			OBTIENE LOS MATERIALES CON DESCUENTOS
	 * 			POLITICAS.
	 * 
	 * @return	RETORNA UNA LISTA DE MATERIALES.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> obtenerDescuentosPolitica() {
		List<BeanMaterial> listaMateriales = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");

		String sql = "SELECT * FROM PROFFLINE_TB_MATERIAL_DSCTO_POLITICA order by prdha, zzordco;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			listaMateriales = new ArrayList<BeanMaterial>();
			if (mapResultado.size() > 0) {
				HashMap res = null;
				for (int i = 0; i < mapResultado.size(); i++) {
					res = (HashMap) mapResultado.get(i);
					BeanMaterial material = new BeanMaterial();
					material.setIdMaterial(res.get("MATNR").toString());
					material.setUn(res.get("S_U").toString());
					material.setDescripcion(res.get("SHORT_TEXT").toString());
					material.setPrice_1(res.get("PRICE_1").toString());
					material.setTipoMaterial(res.get("HER").toString());
					material.setNormt(res.get("NORMT").toString());
					material.setText_line(res.get("TEXT_LINE").toString());
					material.setPrdha(res.get("PRDHA").toString());
					try {
						material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
					} catch (Exception e) {
						material.setDblAcumulado(0d);
					}
					try {
						material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
					} catch (Exception e) {
						material.setDblPromedio(0d);
					}
					material.setTypeMat(res.get("TYPEMAT").toString());
					listaMateriales.add(material);
				}
			}
		}
		return listaMateriales;
	}

	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			OBTIENE LOS MATERIALES CON DESCUENTOS
	 * 			MANUALES.
	 * 
	 * @return	RETORNA UNA LISTA DE MATERIALES.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanMaterial> obtenerDescuentosManuales() {
		List<BeanMaterial> listaMateriales = null;
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "STOCK");
		column.put("String:2", "S_U");
		column.put("String:3", "SHORT_TEXT");
		column.put("String:4", "TEXT_LINE");
		column.put("String:5", "TARGET_QTY");
		column.put("String:6", "PRICE_1");
		column.put("String:7", "PRICE_2");
		column.put("String:8", "PRICE_3");
		column.put("String:9", "PRICE_4");
		column.put("String:10", "PRDHA");
		column.put("String:11", "HER");
		column.put("String:12", "NORMT");
		column.put("String:13", "ZZORDCO");
		column.put("String:14", "CELL_DESIGN");
		column.put("String:15", "TYPEMAT");
		column.put("String:16", "GRUPO_COMPRA");
		column.put("String:17", "ST_1");
		column.put("String:18", "VENTAS_ACUMULADO");
		column.put("String:19", "VENTAS_PROMEDIO");
		column.put("String:20", "CLIENTE");

		String sql = "SELECT * FROM PROFFLINE_TB_MATERIAL_DSCTO_MANUAL order by prdha, zzordco ;";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			listaMateriales = new ArrayList<BeanMaterial>();
			if (mapResultado.size() > 0) {
				HashMap res = null;
				for (int i = 0; i < mapResultado.size(); i++) {
					res = (HashMap) mapResultado.get(i);
					BeanMaterial material = new BeanMaterial();
					material.setIdMaterial(res.get("MATNR").toString());
					material.setUn(res.get("S_U").toString());
					material.setDescripcion(res.get("SHORT_TEXT").toString());
					material.setPrice_1(res.get("PRICE_1").toString());
					material.setTipoMaterial(res.get("HER").toString());
					material.setNormt(res.get("NORMT").toString());
					material.setText_line(res.get("TEXT_LINE").toString());
					material.setPrdha(res.get("PRDHA").toString());
					try {
						material.setDblAcumulado(Double.parseDouble(res.get("VENTAS_ACUMULADO").toString()));
					} catch (Exception e) {
						material.setDblAcumulado(0d);
					}
					try {
						material.setDblPromedio(Double.parseDouble(res.get("VENTAS_PROMEDIO").toString()));
					} catch (Exception e) {
						material.setDblPromedio(0d);
					}
					material.setTypeMat(res.get("TYPEMAT").toString());
					listaMateriales.add(material);
				}
			}
		}
		return listaMateriales;
	}
}