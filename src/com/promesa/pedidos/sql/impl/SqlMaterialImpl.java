package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanCondicionComercial1;
import com.promesa.pedidos.bean.BeanCondicionComercial2;
import com.promesa.pedidos.bean.BeanCondicionComercial3x;
import com.promesa.pedidos.bean.BeanCondicionComercial4x;
import com.promesa.pedidos.bean.BeanCondicionComercial5x;
import com.promesa.pedidos.bean.BeanCondicionComercialEscala;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.util.Constante;
import com.promesa.util.Util;

//import java.io.Console;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
@SuppressWarnings("rawtypes")
public class SqlMaterialImpl implements SqlMaterial {
	
	private static SqlMaterial instance = null;

	private boolean resultado = false;
	private String sqlMaterial = "";
	private String sqlMaterial2 = "";
	private String sqlMaterialNuevo = "";
	private HashMap column = null;
	private HashMap<Integer, HashMap> mapResultado = null;
	private HashMap<Integer, HashMap> mapResultado2 = null;
	private List<BeanMaterial> listaMaterial = null;
	private static String TABLA_MATERIAL = "PROFFLINE_TB_MATERIAL";
	private static String TABLA_MATERIAL_NUEVO = "PROFFLINE_TB_MATERIAL_NUEVO";
	private static String TABLA_MATERIAL_STOCK = "PROFFLINE_TB_MATERIAL_STOCK";
	private static String TABLA_MATERIAL_TOP_CLIENTE = "PROFFLINE_TB_MATERIAL_TOP_CLIENTE";
	private static String TABLA_MATERIAL_TOP_TIPOLOGIA = "PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA";
	private static String TABLA_MATERIAL_PROMO_OFERTA = "PROFFLINE_TB_MATERIAL_PROMO_OFERTA";
	private static String TABLA_MATERIAL_DSCTO_POLITICA = "PROFFLINE_TB_MATERIAL_DSCTO_POLITICA";
	private static String TABLA_MATERIAL_DSCTO_MANUAL = "PROFFLINE_TB_MATERIAL_DSCTO_MANUAL";
	private static String TABLA_CLASE_MATERIAL = "PROFFLINE_TB_CLASE_MATERIAL";
	private static String TABLA_COMBO = "PROFFLINE_TB_COMBO";
	private static String TABLA_CONDICION1 = "PROFFLINE_TB_CONDICION_1";
	private static String TABLA_CONDICION2 = "PROFFLINE_TB_CONDICION_2";
	private static String TABLA_DETALLE_COMBO = "PROFFLINE_TB_DETALLE_COMBO";
	private static String MATNR = "MATNR";
	private static String STOCK = "STOCK";
	private static String S_U = "S_U";
	private static String SHORT_TEXT = "SHORT_TEXT";
	private static String TEXT_LINE = "TEXT_LINE";
	private static String TARGET_QTY = "TARGET_QTY";
	private static String PRICE_1 = "PRICE_1";
	private static String PRICE_2 = "PRICE_2";
	private static String PRICE_3 = "PRICE_3";
	private static String PRICE_4 = "PRICE_4";
	private static String PRDHA = "PRDHA";
	private static String HER = "HER";
	private static String NORMT = "NORMT";
	private static String ZZORDCO = "ZZORDCO";
	private static String CELL_DESIGN = "CELL_DESIGN";
	private static String MTART = "MTART";
	private static String TYPEMAT = "TYPEMAT";
	private static String GRUPO_COMPRA = "GRUPO_COMPRA";
	private static String ST_1 = "ST_1";
	private static String FEC_ING = "FEC_ING";
	private static String COSTO = "COSTO";
	private static String MARGEN_OBJ = "MARGEN_OBJ";
	@SuppressWarnings("unused")
	private static String PRIORIDADGRUPO = "PRIORIDADGRUPO";
	@SuppressWarnings("unused")
	private static String PRIORIDADCONDICION = "PRIORIDADCONDICION";
	@SuppressWarnings("unused")
	private static String CLASEMATERIAL = "CLASEMATERIAL";
	@SuppressWarnings("unused")
	private static String CONDICIONPAGO = "CONDICIONPAGO";
	@SuppressWarnings("unused")
	private static String CLIENTE = "CLIENTE";
	@SuppressWarnings("unused")
	private static String TIPO = "TIPO";
	@SuppressWarnings("unused")
	private static String PORCENTAJE = "PORCENTAJE";
	@SuppressWarnings("unused")
	private static String GRUPOCLIENTE = "GRUPOCLIENTE";
	@SuppressWarnings("unused")
	private static String CANAL = "CANAL";
	public static SqlMaterial create(){
		synchronized (SqlMaterialImpl.class) {
			if(instance == null){
				instance = new SqlMaterialImpl();
			}
			return instance;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setListarMaterial() {
		listaMaterial = new ArrayList<BeanMaterial>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanMaterial material = null;
		column = new HashMap();
		column.put("String:0", MATNR);
		column.put("String:1", STOCK);
		column.put("String:2", S_U);
		column.put("String:3", SHORT_TEXT);
		column.put("String:4", TEXT_LINE);
		column.put("String:5", TARGET_QTY);
		column.put("String:6", PRICE_1);
		column.put("String:7", PRICE_2);
		column.put("String:8", PRICE_3);
		column.put("String:9", PRICE_4);
		column.put("String:10", PRDHA);
		column.put("String:11", HER);
		column.put("String:12", NORMT);
		column.put("String:13", ZZORDCO);
		column.put("String:14", CELL_DESIGN);
		column.put("String:15", MTART);
		column.put("String:16", TYPEMAT);
		column.put("String:17", GRUPO_COMPRA);
		column.put("String:18", ST_1);
		column.put("String:19", FEC_ING);
		column.put("String:20", COSTO);
		column.put("String:21", MARGEN_OBJ);
		sqlMaterial = " SELECT " + MATNR + "," + STOCK + "," + S_U + ","
				+ SHORT_TEXT + "," + TEXT_LINE + "," + TARGET_QTY + ","
				+ PRICE_1 + "," + PRICE_2 + "," + PRICE_3 + "," + PRICE_4 + ","
				+ PRDHA + "," + HER + "," + NORMT + "," + ZZORDCO + ","
				+ CELL_DESIGN + "," + MTART + "," + TYPEMAT + ","
				+ GRUPO_COMPRA + "," + ST_1 + ", "+FEC_ING+", "+COSTO+",  "+MARGEN_OBJ+" FROM " + TABLA_MATERIAL;

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				material = new BeanMaterial();
				material.setIdMaterial(res.get(MATNR).toString());
				material.setStock(res.get(STOCK).toString());
				material.setUn(res.get(S_U).toString());
				material.setDescripcion(res.get(SHORT_TEXT).toString());
				material.setText_line(res.get(TEXT_LINE).toString());
				material.setTarget_qty(res.get(TARGET_QTY).toString());
				material.setPrice_1(res.get(PRICE_1).toString());
				material.setPrice_2(res.get(PRICE_2).toString());
				material.setPrice_3(res.get(PRICE_3).toString());
				material.setPrice_4(res.get(PRICE_4).toString());
				material.setPrdha(res.get(PRDHA).toString());
				material.setTipoMaterial(res.get(HER).toString());
				material.setNormt(res.get(NORMT).toString());
				material.setZzordco(res.get(ZZORDCO).toString());
				material.setCell_design(res.get(CELL_DESIGN).toString());
				material.setMtart(res.get(MTART).toString());
				material.setTypeMat(res.get(TYPEMAT).toString());
				material.setGrupo_compra(res.get(GRUPO_COMPRA).toString());
				material.setTipologia(res.get(TYPEMAT).toString());
				material.setSt_1(res.get(ST_1).toString());
				material.setStrFec_Ing(res.get(FEC_ING).toString());
				material.setStrCosto(res.get(COSTO).toString());
				material.setStrMargen_Obj(res.get(MARGEN_OBJ).toString());
				listaMaterial.add(material);
			}

		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Double> obtenerrango() {
		List<Double> colRango = new ArrayList();
		column = new HashMap();
		column.put("String:0", "VALOR_1");
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlRango = "SELECT * FROM PROFFLINE_TB_ZTCONSTANTE WHERE TXTNOMBRECAMPO = 'CTL_MARGEN';";
		resultExecuteQuery = new ResultExecuteQuery(sqlRango, column, Constante.BD_SYNC);
		if (sqlRango != null) {
			mapResultado = resultExecuteQuery.getMap();
			for(int i = 0; i<mapResultado.size();i++) {
				HashMap res = (HashMap) mapResultado.get(i);
				try{
					colRango.add(Double.parseDouble(res.get("VALOR_1").toString()));
				}catch(Exception e){
					colRango.add(0.00);
				}
			}
		}
		return colRango;
	}
	@SuppressWarnings("unused")
	public void setInsertarActualizarMaterial(List<BeanMaterial> listm) {
		List<String> listaSQL = new ArrayList<String>();
		ResultExecuteList resultExecute = null;
		for (BeanMaterial m : listm) {
			if ((m.getText_line() == null) || (m.getText_line().trim().equals(""))) {
		        m.setText_line(m.getDescripcion());
		      }
			int tipo = m.getTipoAccion();
			switch (tipo) {
			case Constante.AGREGAR_MATERIAL:
				listaSQL.add("INSERT INTO " + TABLA_MATERIAL + " (" + MATNR
						+ "," + STOCK + "," + S_U + "," + SHORT_TEXT + ","
						+ TEXT_LINE + "," + TARGET_QTY + "," + PRICE_1 + ","
						+ PRICE_2 + "," + PRICE_3 + "," + PRICE_4 + "," + PRDHA
						+ "," + HER + "," + NORMT + "," + ZZORDCO + ","
						+ CELL_DESIGN + "," + MTART + "," + TYPEMAT + ","
						+ GRUPO_COMPRA + "," + ST_1 + ", "+ FEC_ING +", "+ COSTO +", "+MARGEN_OBJ+")" + " VALUES ('"
						+ m.getIdMaterial() + "','" + m.getStock() + "','"
						+ m.getUn() + "','" + m.getDescripcion().replaceAll("'", "") + "','"
						+ m.getText_line().replaceAll("'", "") + "','" + m.getTarget_qty() + "','"
						+ m.getPrice_1() + "','" + m.getPrice_2() + "','"
						+ m.getPrice_3() + "','" + m.getPrice_4() + "','"
						+ m.getPrdha() + "','" + m.getTipoMaterial() + "','"
						+ m.getNormt() + "','" + m.getZzordco() + "','"
						+ m.getCell_design() + "','" + m.getMtart() + "','"
						+ m.getTypeMat() + "','" + m.getGrupo_compra() + "','"
						+ m.getSt_1() + "', '"+ m.getStrFec_Ing() +"', '"+m.getStrCosto()+"', '"+m.getStrMargen_Obj()+"')");
				break;
			case Constante.ACTUALIZAR_MATERIAL:
				listaSQL.add("UPDATE " + TABLA_MATERIAL + " SET STOCK='"
						+ m.getStock() + "', S_U='" + m.getUn()
						+ "', SHORT_TEXT='" + m.getDescripcion()
						+ "', TEXT_LINE='" + m.getText_line() + "', "
						+ "TARGET_QTY='" + m.getTarget_qty() + "', PRICE_1='"
						+ m.getPrice_1() + "', PRICE_2='" + m.getPrice_2()
						+ "', PRICE_3='" + m.getPrice_3() + "', PRICE_4='"
						+ m.getPrice_4() + "', " + "PRDHA='" + m.getPrdha()
						+ "', HER='" + m.getTipoMaterial() + "', NORMT='"
						+ m.getNormt() + "', ZZORDCO='" + m.getZzordco()
						+ "', CELL_DESIGN='" + m.getCell_design()
						+ "', MTART='" + m.getMtart() + "', " + "TYPEMAT='"
						+ m.getTypeMat() + "', GRUPO_COMPRA='"
						+ m.getGrupo_compra() + "', ST_1='" + m.getSt_1()
						+ "', FEC_ING='"+m.getStrFec_Ing()+"', COSTO ='"+m.getStrCosto()
						+"', MARGEN_OBJ='"+m.getStrMargen_Obj()+"' WHERE MATNR='" + m.getIdMaterial() + "'");
				break;
			}
		}
		ResultExecuteList rs = new ResultExecuteList();
		rs.insertarListaConsultas(listaSQL, "materiales", Constante.BD_SYNC);
	}
	
	@Override
	public void setInsertarActualizarMaterialNuevo(List<BeanMaterial> listm) {
		List<String> listaSQL = new ArrayList<String>();
		ResultExecuteList resultExecute = null;
		for (BeanMaterial m : listm) {
			BeanMaterial mat = getMaterial(m.getIdMaterial());
			int tipo = getCountRowNuevo(m.getIdMaterial());
			switch (tipo) {
			case 0:
				listaSQL.add("INSERT INTO " + TABLA_MATERIAL_NUEVO + " (" 
						+ MATNR + "," 
						+ SHORT_TEXT + ","
						+ TEXT_LINE + ","
						+ PRICE_1 + ","
						+ NORMT + "," 
						+ ZZORDCO + ")" + " VALUES ('"
						+ m.getIdMaterial() + "','"
						+ mat.getDescripcion().replaceAll("'", "") + "','"
						+ mat.getText_line().replaceAll("'", "") + "','"
						+ mat.getPrice_1() + "','"
						+ m.getNormt() + "','" 
						+ m.getZzordco() + "')");
				break;
			case 1:
				listaSQL.add("UPDATE " + TABLA_MATERIAL_NUEVO 
						+ " SET SHORT_TEXT='" + mat.getDescripcion()
						+ "', TEXT_LINE='" + mat.getText_line() 
						+ "', PRICE_1='"+ m.getPrice_1()
						+ "', NORMT='" + m.getNormt() 
						+ "', ZZORDCO='" + m.getZzordco()
						+ "' WHERE MATNR='" + m.getIdMaterial() + "'");
				break;
			}
		}
		ResultExecuteList rs = new ResultExecuteList();
		rs.insertarListaConsultas(listaSQL, "materiales nuevos", Constante.BD_SYNC);
		
	}	

	public void setInsertarMaterial(List<BeanMaterial> listm) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanMaterial m : listm) {
			if (m.getText_line() == null || m.getText_line().trim().equals("")) {
				m.setText_line(m.getDescripcion());
			}
			listaSQL.add("INSERT INTO " + TABLA_MATERIAL + " (" + MATNR + ","
					+ STOCK + "," + S_U + "," + SHORT_TEXT + "," + TEXT_LINE
					+ "," + TARGET_QTY + "," + PRICE_1 + "," + PRICE_2 + ","
					+ PRICE_3 + "," + PRICE_4 + "," + PRDHA + "," + HER + ","
					+ NORMT + "," + ZZORDCO + "," + CELL_DESIGN + "," + MTART
					+ "," + TYPEMAT + "," + GRUPO_COMPRA + "," + ST_1 + ", "+FEC_ING+", "+COSTO+", "+MARGEN_OBJ+")"
					+ " VALUES ('" + m.getIdMaterial() + "','" + m.getStock()
					+ "','" + m.getUn() + "','" + m.getDescripcion() + "','"
					+ m.getText_line() + "','" + m.getTarget_qty() + "','"
					+ m.getPrice_1() + "','" + m.getPrice_2() + "','"
					+ m.getPrice_3() + "','" + m.getPrice_4() + "','"
					+ m.getPrdha() + "','" + m.getTipoMaterial() + "','"
					+ m.getNormt() + "','" + m.getZzordco() + "','"
					+ m.getCell_design() + "','" + m.getMtart() + "','"
					+ m.getTypeMat() + "','" + m.getGrupo_compra() + "','"
					+ m.getSt_1() + "', '"+m.getStrFec_Ing()+"', '"
					+m.getStrCosto()+"', '"+m.getStrMargen_Obj()+"')");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Materiales", Constante.BD_SYNC);
	}

	public void insertarMaterialesStock(List<BeanMaterial> listm) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanMaterial m : listm) {
			listaSQL.add(" INSERT INTO " + TABLA_MATERIAL_STOCK + " (MATNR,STOCK) VALUES ('" + m.getIdMaterial() + "','"
						+ m.getStock() + "') ");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Stock de materiales", Constante.BD_SYNC);
	}
	
	public void consultarStock() {
		
	}
	
	public void migrarMaterialConsultaDinamica1(List<BeanMaterial> listm) {
		final List<String> listaSQL = new ArrayList<String>();
		String cadenaSQL = "";
		for (BeanMaterial m : listm) {
//			cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_PROMO_OFERTA "
//					+ " SELECT * FROM PROFFLINE_TB_MATERIAL " + " WHERE MATNR ='" + m.getIdMaterial() + "' ";
			
			cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_PROMO_OFERTA(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, " +
			"PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, " +
			"GRUPO_COMPRA, ST_1, VENTAS_ACUMULADO, VENTAS_PROMEDIO, CLIENTE) VALUES ('" + m.getIdMaterial() + "', '" +
			m.getStock() + "','" + m.getUn() + "','" + m.getDescripcion() + "','" + m.getText_line() + "','" +
			m.getTarget_qty() + "','" + m.getPrice_1() + "','" + m.getPrice_2() + "','" + m.getPrice_3() + "','" +
			m.getPrice_4() + "','" + m.getPrdha() + "','" + m.getTipoMaterial() + "','" + m.getNormt() + "','" +
			m.getZzordco() + "','" + m.getCell_design() + "','" + m.getMtart() + "','" + m.getTypeMat() + "','" +
			m.getGrupo_compra() + "','" + m.getSt_1() + "','" + "" + "','" + "" + "','" +
			"" + "');";
			listaSQL.add(cadenaSQL);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Materiales Promoferta", Constante.BD_SYNC);
	}

	public void migrarMaterialConsultaDinamica2(List<BeanMaterial> listm) {
		final List<String> listaSQL = new ArrayList<String>();
		String cadenaSQL = "";
		for (BeanMaterial m : listm) {
//			cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_DSCTO_POLITICA "
//					+ " SELECT * FROM PROFFLINE_TB_MATERIAL " + " WHERE MATNR='" + m.getIdMaterial() + "' ";
			
			cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_DSCTO_POLITICA(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, " +
			"PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, " +
			"GRUPO_COMPRA, ST_1, VENTAS_ACUMULADO, VENTAS_PROMEDIO, CLIENTE) VALUES ('" + m.getIdMaterial() + "', '" +
			m.getStock() + "','" + m.getUn() + "','" + m.getDescripcion() + "','" + m.getText_line() + "','" +
			m.getTarget_qty() + "','" + m.getPrice_1() + "','" + m.getPrice_2() + "','" + m.getPrice_3() + "','" +
			m.getPrice_4() + "','" + m.getPrdha() + "','" + m.getTipoMaterial() + "','" + m.getNormt() + "','" +
			m.getZzordco() + "','" + m.getCell_design() + "','" + m.getMtart() + "','" + m.getTypeMat() + "','" +
			m.getGrupo_compra() + "','" + m.getSt_1() + "','" + "" + "','" + "" + "','" +
			"" + "');";
			
			listaSQL.add(cadenaSQL);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Descuentos por política", Constante.BD_SYNC);
	}

	public void migrarMaterialConsultaDinamica3(List<BeanMaterial> listm) {
		final List<String> listaSQL = new ArrayList<String>();
		String cadenaSQL = "";
		for (BeanMaterial m : listm) {
//			cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_DSCTO_MANUAL "
//					+ " SELECT * FROM PROFFLINE_TB_MATERIAL " + " WHERE MATNR ='" + m.getIdMaterial() + "' ";
			
			cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_DSCTO_MANUAL(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, " +
			"PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, " +
			"GRUPO_COMPRA, ST_1, VENTAS_ACUMULADO, VENTAS_PROMEDIO, CLIENTE) VALUES ('" + m.getIdMaterial() + "', '" +
			m.getStock() + "','" + m.getUn() + "','" + m.getDescripcion() + "','" + m.getText_line() + "','" +
			m.getTarget_qty() + "','" + m.getPrice_1() + "','" + m.getPrice_2() + "','" + m.getPrice_3() + "','" +
			m.getPrice_4() + "','" + m.getPrdha() + "','" + m.getTipoMaterial() + "','" + m.getNormt() + "','" +
			m.getZzordco() + "','" + m.getCell_design() + "','" + m.getMtart() + "','" + m.getTypeMat() + "','" +
			m.getGrupo_compra() + "','" + m.getSt_1() + "','" + "" + "','" + "" + "','" +
			"" + "');";
			
			listaSQL.add(cadenaSQL);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Descuentos manuales",Constante.BD_SYNC);
	}

	public void setInsertarCombo(List<Item> listCombos) {
		final List<String> listaSQL = new ArrayList<String>();

		for (Item m : listCombos) {
			listaSQL.add("INSERT INTO " + TABLA_COMBO + " (" + "UNIDAD" + ","
					+ "CODIGO" + "," + "NOMBRE" + "," + "PRECIO" + ")"
					+ " VALUES ('" + m.getUnidad() + "','" + m.getCodigo()
					+ "','" + m.getDenominacion() + "','" + m.getPrecioNeto() + "')");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Combos", Constante.BD_SYNC);
	}

	public void setInsertarCondicion1(List<BeanCondicionComercial1> listCondicion) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanCondicionComercial1 m : listCondicion) {
			listaSQL.add("INSERT INTO " + TABLA_CONDICION1 + " ("
					+ "PRIORIDADGRUPO" + "," + "PRIORIDADCONDICION" + ","
					+ "CLASEMATERIAL" + "," + "CONDICIONPAGO" + "," + "CLIENTE"
					+ "," + "TIPO" + "," + "PORCENTAJE" + ")" + " VALUES ("
					+ m.getIntPrioridadGrupo() + ","
					+ m.getIntPrioridadInterna() + ",'"
					+ m.getStrClaseMaterial() + "','" + m.getStrCondicionPago()
					+ "','" + m.getStrCliente() + "','" + m.getStrTipo()
					+ "','" + m.getDblDscto() + "'" + ")");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Condiciones", Constante.BD_SYNC);
	}

	public void setInsertarCondicion2(List<BeanCondicionComercial2> listCondicion) {
		final List<String> listaSQL = new ArrayList<String>();

		for (BeanCondicionComercial2 m : listCondicion) {
			listaSQL.add("INSERT INTO " + TABLA_CONDICION2 + " ("
					+ "PRIORIDADGRUPO" + "," + "PRIORIDADCONDICION" + ","
					+ "CLIENTE" + "," + "GRUPOCLIENTE" + "," + "CANAL" + ","
					+ "TIPO" + "," + "PORCENTAJE" + ")" + " VALUES ("
					+ m.getIntPrioridadGrupo() + ","
					+ m.getIntPrioridadInterna() + ",'" + m.getStrCliente()
					+ "','" + m.getStrGrupoCliente() + "','" + m.getStrCanal()
					+ "','" + m.getStrTipo() + "','" + m.getDblDscto() + "'" + ")");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Condiciones", Constante.BD_SYNC);
	}

	public void setInsertarComboHijos(List<Item> listCombosHijos) {
		final List<String> listaSQL = new ArrayList<String>();
		for (Item m : listCombosHijos) {
			listaSQL.add("INSERT INTO " + TABLA_DETALLE_COMBO + " ("
					+ "CANTIDAD" + "," + "CODIGO" + "," + "NOMBRE" + ","
					+ "PRECIO" + "," + "IDCOMBO" + ")" + " VALUES ('"
					+ m.getCantidad() + "','" + m.getCodigo() + "','"
					+ m.getDenominacion() + "','" + m.getPrecioNeto() + "','"
					+ m.getPadre().getCodigo() + "')");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "Detalles de combos",Constante.BD_SYNC);
	}

	@SuppressWarnings("unchecked")
	public String getUnidadMaterial(String codigo) {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "S_U");
		String tipo = "";

		String sql = " SELECT S_U FROM PROFFLINE_TB_MATERIAL WHERE MATNR='" + codigo + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (mapResultado != null && mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				tipo = ("" + res.get("S_U")).toString().trim();
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return tipo;

	}

	@SuppressWarnings("unchecked")
	public String getTipoMaterial(String codigo) {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "TYPEMAT");
		String tipo = "";

		String sql = " SELECT TYPEMAT FROM PROFFLINE_TB_MATERIAL WHERE MATNR='" + codigo + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				tipo = res.get("TYPEMAT").toString().trim();
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return tipo;
	}

	@SuppressWarnings("unchecked")
	public int getCountRow() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public int getCountRowStock() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_STOCK;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	@SuppressWarnings("unchecked")
	public int getCountRowPromoOferta() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_PROMO_OFERTA;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	public int getCountRowDsctoPolitica() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_DSCTO_POLITICA;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	public int getCountRowDsctoManual() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_DSCTO_MANUAL;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	public int getCountRowTopCliente() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;

		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_TOP_CLIENTE;

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;

	}

	@SuppressWarnings("unchecked")
	public int getCountRowTopTipologia() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;

		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_TOP_TIPOLOGIA;

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	public int getCountRowCombos() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;

		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_COMBO;

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public String obtenerTipoMaterialCombo(String codigoCombo) {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "typemat");
		sqlMaterial = "select typemat from proffline_tb_detalle_combo inner join "
				+ "proffline_tb_material on proffline_tb_detalle_combo.codigo = "
				+ "proffline_tb_material.matnr where idCombo='"
				+ codigoCombo + "' LIMIT 0,1;";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (mapResultado != null && mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				return res.get("typemat").toString().trim();
			} else {
				return "";
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return "";
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, String> existeMaterial() {
		ResultExecuteQuery resultExecuteQuery = null;
//		BeanMaterial material = new BeanMaterial();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "ZZORDCO");
		sqlMaterial = "select ZZORDCO, MATNR from proffline_tb_material";
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado != null) {
				for (HashMap res : mapResultado.values()) {
					String key = "" + res.get("MATNR");
					String value = "" + res.get("ZZORDCO");
					hm.put(key, value);
				}
			} else {
				return hm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, String> existeMaterialPrice1() {
		ResultExecuteQuery resultExecuteQuery = null;
//		BeanMaterial material = new BeanMaterial();
		column = new HashMap();
		column.put("String:0", "MATNR");
		column.put("String:1", "PRICE_1");
		sqlMaterial = "select PRICE_1, MATNR from proffline_tb_material";
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado != null) {
				for (HashMap res : mapResultado.values()) {
					String key = "" + res.get("MATNR");
					String value = "" + res.get("PRICE_1");
					hm.put(key, value);
				}
			} else {
				return hm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}	
	@SuppressWarnings("unchecked")
	public int getCountRowCondicion1() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_CONDICION1;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	@SuppressWarnings("unchecked")
	public int getCountRowCondicion2() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_CONDICION2;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	@SuppressWarnings("unchecked")
	public int getCountRowClaseMaterial() {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterial = " SELECT COUNT(*) AS filas FROM " + TABLA_CLASE_MATERIAL;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getClaseMaterial() {
		ResultExecuteQuery resultExecuteQuery = null;
		HashMap<String, String> hm = new HashMap<String, String>();
		column = new HashMap();
		column.put("String:0", "txtCodigoClase");
		column.put("String:1", "txtCodigoMaterial");
		sqlMaterial = "SELECT txtCodigoClase, txtCodigoMaterial FROM " + TABLA_CLASE_MATERIAL;
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			for (HashMap res : mapResultado.values()) {
				String key = "" + res.get("txtCodigoClase");
				String value = "" + res.get("txtCodigoMaterial");
				hm.put(key + "," + value, value);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return hm;
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public String obtenerNombreMaterial(String codigo) {
		ResultExecuteQuery resultExecuteQuery = null;
		String nombre = "";
		column = new HashMap();
		column.put("String:0", "SHORT_TEXT");
		column.put("String:1", "TEXT_LINE");
		sqlMaterial = "SELECT SHORT_TEXT, TEXT_LINE from proffline_tb_material where MATNR='" + codigo + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			String shortText = "";
			String textLine = "";
			if (!mapResultado.isEmpty()) {
				res = (HashMap) mapResultado.get(0);
				shortText = ("" + res.get("SHORT_TEXT")).toString();
				textLine = ("" + res.get("TEXT_LINE")).toString().trim();
			}
			if (textLine.isEmpty()) {
				nombre = shortText;
			}else {
				nombre = textLine;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
			return nombre;
		}
	}
	
	//	Marcelo Moyano 14/03/2013 - 14:03
	@SuppressWarnings({ "unchecked", "finally" })
	public String obtenerStockMaterial(String codigoMaterial) {
		ResultExecuteQuery resultExecuteQuery = null;
		String stockMaterial = "";
		column = new HashMap();
		column.put("String:0", "STOCK");
		sqlMaterial = "SELECT STOCK from PROFFLINE_TB_MATERIAL_STOCK where MATNR='" + codigoMaterial + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (!mapResultado.isEmpty()) {
				res = (HashMap) mapResultado.get(0);
				stockMaterial = "" + res.get("STOCK").toString();
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
			return stockMaterial;
		}
	}
	//	Marcelo Moyano
	
	public void setEliminarMaterialStock() {
		sqlMaterial = " DELETE FROM " + TABLA_MATERIAL_STOCK;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Materiales", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void eliminarMaterialConsultaDinamica1() {
		sqlMaterial = " DELETE FROM " + TABLA_MATERIAL_PROMO_OFERTA;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Promofertas", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void eliminarMaterialConsultaDinamica2() {
		sqlMaterial = " DELETE FROM " + TABLA_MATERIAL_DSCTO_POLITICA;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Descuentos por política", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void eliminarMaterialConsultaDinamica3() {
		sqlMaterial = " DELETE FROM " + TABLA_MATERIAL_DSCTO_MANUAL;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Descuentos manuales", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarCombo() {
		sqlMaterial = " DELETE FROM " + TABLA_COMBO;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Combos", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarCondicion1() {
		sqlMaterial = " DELETE FROM " + TABLA_CONDICION1;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Condiciones", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarClaseMaterial() {
		sqlMaterial = " DELETE FROM " + TABLA_CLASE_MATERIAL;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Clases de material", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarCondicion2() {
		sqlMaterial = " DELETE FROM " + TABLA_CONDICION2;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Condiciones", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarComboHijos() {
		sqlMaterial = " DELETE FROM " + TABLA_DETALLE_COMBO;
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Detalles de combos", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean isEliminarMaterial() {
		return resultado;
	}

	public List<BeanMaterial> getListaMaterial() {
		return listaMaterial;
	}

	public boolean getInsertarUsuario() {
		return resultado;
	}

	

	@SuppressWarnings({ "unchecked" })
	@Override
	public BeanMaterial getMaterial(String codigo) {
		ResultExecuteQuery resultExecuteQuery = null;
		BeanMaterial material = null;
		column = new HashMap();
		column.put("String:0", MATNR);
		column.put("String:1", STOCK);
		column.put("String:2", S_U);
		column.put("String:3", SHORT_TEXT);
		column.put("String:4", TEXT_LINE);
		column.put("String:5", TARGET_QTY);
		column.put("String:6", PRICE_1);
		column.put("String:7", PRICE_2);
		column.put("String:8", PRICE_3);
		column.put("String:9", PRICE_4);
		column.put("String:10", PRDHA);
		column.put("String:11", HER);
		column.put("String:12", NORMT);
		column.put("String:13", ZZORDCO);
		column.put("String:14", CELL_DESIGN);
		column.put("String:15", MTART);
		column.put("String:16", TYPEMAT);
		column.put("String:17", GRUPO_COMPRA);
		column.put("String:18", ST_1);
		column.put("String:19",FEC_ING);
		column.put("String:20", COSTO);
		column.put("String:21",MARGEN_OBJ);
		sqlMaterial = " SELECT " + MATNR + "," + STOCK + "," + S_U + ","
				+ SHORT_TEXT + "," + TEXT_LINE + "," + TARGET_QTY + ","
				+ PRICE_1 + "," + PRICE_2 + "," + PRICE_3 + "," + PRICE_4 + ","
				+ PRDHA + "," + HER + "," + NORMT + "," + ZZORDCO + ","
				+ CELL_DESIGN + "," + MTART + "," + TYPEMAT + ","
				+ GRUPO_COMPRA + "," + ST_1 + ", "+FEC_ING+", "
				+COSTO  + ", " + MARGEN_OBJ
				+" FROM " + TABLA_MATERIAL
				+ " WHERE " + MATNR + "='" + codigo + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			if (mapResultado.size() > 0) {
				res = (HashMap) mapResultado.get(0);
				material = new BeanMaterial();
				material.setIdMaterial(res.get(MATNR).toString());
				material.setStock(res.get(STOCK).toString());
				material.setUn(res.get(S_U).toString());
				material.setDescripcion(res.get(SHORT_TEXT).toString());
				material.setText_line(res.get(TEXT_LINE).toString());
				material.setTarget_qty(res.get(TARGET_QTY).toString());
				material.setPrice_1(res.get(PRICE_1).toString());
				material.setPrice_2(res.get(PRICE_2).toString());
				material.setPrice_3(res.get(PRICE_3).toString());
				material.setPrice_4(res.get(PRICE_4).toString());
				material.setPrdha(res.get(PRDHA).toString());
				material.setTipoMaterial(res.get(HER).toString());
				material.setNormt(res.get(NORMT).toString());
				material.setZzordco(res.get(ZZORDCO).toString());
				material.setCell_design(res.get(CELL_DESIGN).toString());
				material.setMtart(res.get(MTART).toString());
				material.setTypeMat(res.get(TYPEMAT).toString());
				material.setGrupo_compra(res.get(GRUPO_COMPRA).toString());
				material.setSt_1(res.get(ST_1).toString());
				material.setStrFec_Ing(res.get(FEC_ING).toString());
				material.setStrCosto(res.get(COSTO).toString());
				material.setStrMargen_Obj(res.get(MARGEN_OBJ).toString());
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return material;
	}

	public void migrarMaterialesTopCliente(List<List<BeanMaterial>> listt) {
		List<String> listaSQL = new ArrayList<String>();
		listaSQL.add("DELETE FROM PROFFLINE_TB_MATERIAL_TOP_CLIENTE ");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "", Constante.BD_SYNC);
		listaSQL = new ArrayList<String>();
		for (BeanMaterial listm : listt.get(0)) {
			String cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_TOP_CLIENTE "
					+ " SELECT * FROM PROFFLINE_TB_MATERIAL "
					+ " WHERE MATNR ='" + listm.getIdMaterial() + "' ";
			listaSQL.add(cadenaSQL);
			String strUdt = " UPDATE PROFFLINE_TB_MATERIAL_TOP_CLIENTE SET "
					+ " VENTAS_ACUMULADO=" + listm.getDblAcumulado()
					+ " ,VENTAS_PROMEDIO=" + listm.getDblPromedio()
					+ " ,CLIENTE='" + listm.getStrCodCliente() + "' "
					+ " WHERE MATNR='" + listm.getIdMaterial()
					+ "'  AND CLIENTE is null ";
			listaSQL.add(strUdt);
		}
		ResultExecuteList resultExecute2 = new ResultExecuteList();
		resultExecute2.insertarListaConsultas(listaSQL, "Top por cliente", Constante.BD_SYNC);
	}
	
	public void migrarMaterialesTopCliente2(List<List<BeanMaterial>> listt, List<BeanMaterial> listaMateriales) {
		List<String> listaSQL = new ArrayList<String>();
		listaSQL.add("DELETE FROM PROFFLINE_TB_MATERIAL_TOP_CLIENTE ");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "", Constante.BD_SYNC);
		
//		List<BeanMaterial> listaMateriales = obtenerTodosMateriales(listt.get(0));
		
		listaSQL = new ArrayList<String>();
		for (BeanMaterial m : listaMateriales) {
			String cadenaSQL = 	" INSERT INTO PROFFLINE_TB_MATERIAL_TOP_CLIENTE(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, " +
								"PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, " +
								"GRUPO_COMPRA, ST_1, VENTAS_ACUMULADO, VENTAS_PROMEDIO, CLIENTE) VALUES ('" + m.getIdMaterial() + "', '" +
								m.getStock() + "','" + m.getUn() + "','" + m.getDescripcion() + "','" + m.getText_line() + "','" +
								m.getTarget_qty() + "','" + m.getPrice_1() + "','" + m.getPrice_2() + "','" + m.getPrice_3() + "','" +
								m.getPrice_4() + "','" + m.getPrdha() + "','" + m.getTipoMaterial() + "','" + m.getNormt() + "','" +
								m.getZzordco() + "','" + m.getCell_design() + "','" + m.getMtart() + "','" + m.getTypeMat() + "','" +
								m.getGrupo_compra() + "','" + m.getSt_1() + "','" + m.getDblAcumulado() + "','" + m.getDblPromedio() + "','" +
								m.getStrCodCliente() + "');";
			listaSQL.add(cadenaSQL);
			
		}
		ResultExecuteList resultExecute2 = new ResultExecuteList();
		resultExecute2.insertarListaConsultas(listaSQL, "Top por cliente 2", Constante.BD_SYNC);
	}
	
	public void migrarMaterialesTopTipologia2(List<List<BeanMaterial>> listt, List<BeanMaterial> listaMateriales) {
		List<String> listaSQL = new ArrayList<String>();
		listaSQL.add("DELETE FROM PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA ");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "", Constante.BD_SYNC);
		
//		List<BeanMaterial> listaMateriales = obtenerTodosMateriales(listt.get(0));
		
		listaSQL = new ArrayList<String>();
//		for (BeanMaterial m : listt.get(0)) {
		int i = 0;
		for (BeanMaterial m : listaMateriales) {
			String tipologia = listt.get(0).get(i).getStrCodCliente();
			String cadenaSQL = 	" INSERT INTO PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA(MATNR, STOCK, S_U, SHORT_TEXT, TEXT_LINE, TARGET_QTY, " +
								"PRICE_1, PRICE_2, PRICE_3, PRICE_4, PRDHA, HER, NORMT, ZZORDCO, CELL_DESIGN, MTART, TYPEMAT, " +
								"GRUPO_COMPRA, ST_1, VENTAS_ACUMULADO, VENTAS_PROMEDIO, CLIENTE) VALUES ('" + m.getIdMaterial() + "', '" +
								m.getStock() + "','" + m.getUn() + "','" + m.getDescripcion() + "','" + m.getText_line() + "','" +
								m.getTarget_qty() + "','" + m.getPrice_1() + "','" + m.getPrice_2() + "','" + m.getPrice_3() + "','" +
								m.getPrice_4() + "','" + m.getPrdha() + "','" + m.getTipoMaterial() + "','" + m.getNormt() + "','" +
								m.getZzordco() + "','" + m.getCell_design() + "','" + m.getMtart() + "','" + m.getTypeMat() + "','" +
								m.getGrupo_compra() + "','" + m.getSt_1() + "','" + m.getDblAcumulado() + "','" + m.getDblPromedio() + "','" +
								tipologia + "');";
			i++;
			listaSQL.add(cadenaSQL);
		}
		ResultExecuteList resultExecute2 = new ResultExecuteList();
		resultExecute2.insertarListaConsultas(listaSQL, "Top por tipología 2", Constante.BD_SYNC);
	}
	
	@SuppressWarnings("unchecked")
	public List<BeanMaterial> obtenerTodosMateriales2(){
		List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
		
		column = new HashMap();
		column.put("String:0", MATNR);
		column.put("String:1", STOCK);
		column.put("String:2", S_U);
		column.put("String:3", SHORT_TEXT);
		column.put("String:4", TEXT_LINE);
		column.put("String:5", TARGET_QTY);
		column.put("String:6", PRICE_1);
		column.put("String:7", PRICE_2);
		column.put("String:8", PRICE_3);
		column.put("String:9", PRICE_4);
		column.put("String:10", PRDHA);
		column.put("String:11", HER);
		column.put("String:12", NORMT);
		column.put("String:13", ZZORDCO);
		column.put("String:14", CELL_DESIGN);
		column.put("String:15", MTART);
		column.put("String:16", TYPEMAT);
		column.put("String:17", GRUPO_COMPRA);
		column.put("String:18", ST_1);
		column.put("String:19", FEC_ING);
		column.put("String:20", COSTO);
		column.put("String:21", MARGEN_OBJ);
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_MATERIAL";
		try {
			double avance = 0;
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
			if(resultExecuteQuery != null){
				mapResultado = resultExecuteQuery.getMap();
				if(mapResultado.size() > 0){
					int total = mapResultado.size();
					for(int i = 0; i < mapResultado.size(); i++){
						avance = Math.round(i * 100 / total * 100) / 100.0d;
						Promesa.getInstance().mostrarAvisoSincronizacion("Consultando todos los materiales." +" "+ avance + " %");
						HashMap res = (HashMap) mapResultado.get(i);
						BeanMaterial material = new BeanMaterial();
						material.setIdMaterial(res.get(MATNR).toString());
						material.setStock(res.get(STOCK).toString());
						material.setUn(res.get(S_U).toString());
						material.setDescripcion(res.get(SHORT_TEXT).toString());
						material.setText_line(res.get(TEXT_LINE).toString());
						material.setTarget_qty(res.get(TARGET_QTY).toString());
						material.setPrice_1(res.get(PRICE_1).toString());
						material.setPrice_2(res.get(PRICE_2).toString());
						material.setPrice_3(res.get(PRICE_3).toString());
						material.setPrice_4(res.get(PRICE_4).toString());
						material.setPrdha(res.get(PRDHA).toString());
						material.setTipoMaterial(res.get(HER).toString());
						material.setNormt(res.get(NORMT).toString());
						material.setZzordco(res.get(ZZORDCO).toString());
						material.setCell_design(res.get(CELL_DESIGN).toString());
						material.setMtart(res.get(MTART).toString());
						material.setTypeMat(res.get(TYPEMAT).toString());
						material.setGrupo_compra(res.get(GRUPO_COMPRA).toString());
						material.setSt_1(res.get(ST_1).toString());
						material.setStrFec_Ing(res.get(FEC_ING).toString());
						material.setStrCosto(res.get(COSTO).toString());
						material.setStrMargen_Obj(res.get(MARGEN_OBJ).toString());
						materiales.add(material);
					}			
				}
			}
		}catch(Exception ex) {
			return null;
		}
		return materiales;
	}
	
	
	public List<BeanMaterial> obtenerTodosMateriales(List<BeanMaterial> listaMateriales , String mensaje) {
		try {
			List<BeanMaterial> materiales = new ArrayList<BeanMaterial>();
			ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
			Connection connection = (Connection) conn.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet res = null;
			double total = listaMateriales.size();
			double i = 1;
			double avance = 0;
			
			for(BeanMaterial material : listaMateriales){
				avance = Math.round(i * 100 / total * 100) / 100.0d;
				Promesa.getInstance().mostrarAvisoSincronizacion(mensaje +" "+ avance + " %");
				String sql = "SELECT * FROM PROFFLINE_TB_MATERIAL WHERE MATNR = '" + material.getIdMaterial() + "';";
				i++;
				res = stmt.executeQuery(sql);
				while(res.next()){
					BeanMaterial m = new BeanMaterial();
					m.setIdMaterial(res.getString("MATNR").toString());
					m.setStock(res.getString("STOCK").toString());
					m.setUn(res.getString("S_U").toString());
					m.setDescripcion(res.getString("SHORT_TEXT").toString().replaceAll("'", ""));
					m.setText_line(res.getString("TEXT_LINE").toString().replaceAll("'", ""));
					m.setTarget_qty(res.getString("TARGET_QTY").toString());
					m.setPrice_1(res.getString("PRICE_1").toString());
					m.setPrice_2(res.getString("PRICE_2").toString());
					m.setPrice_3(res.getString("PRICE_3").toString());
					m.setPrice_4(res.getString("PRICE_4").toString());
					m.setPrdha(res.getString("PRDHA").toString());
					m.setTipoMaterial(res.getString("HER").toString());
					m.setNormt(res.getString("NORMT").toString());
					m.setZzordco(res.getString("ZZORDCO").toString());
					m.setCell_design(res.getString("CELL_DESIGN").toString());
					m.setMtart(res.getString("MTART").toString());
					m.setTypeMat(res.getString("TYPEMAT").toString());
					m.setGrupo_compra(res.getString("GRUPO_COMPRA").toString());
					m.setSt_1(res.getString("ST_1").toString());
					m.setDblAcumulado(material.getDblAcumulado());
					m.setDblPromedio(material.getDblPromedio());
					m.setStrCodCliente(material.getStrCodCliente());
					m.setStrFec_Ing(res.getString("FEC_ING").toString());
					m.setStrCosto(res.getString("COSTO").toString());
					m.setStrMargen_Obj(res.getString(MARGEN_OBJ).toString());
					materiales.add(m);
				}
			}
			if(res != null){
				res.close();
			}
			stmt.close();
			connection.close();
			conn.close();
			return materiales;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void migrarMaterialesTopTipologia(List<List<BeanMaterial>> listt) {
		List<String> listaSQL = new ArrayList<String>();
		listaSQL.add("DELETE FROM PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA ");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "", Constante.BD_SYNC);
		
		
		
		listaSQL = new ArrayList<String>();
		for (BeanMaterial listm : listt.get(0)) {
			String cadenaSQL = " INSERT INTO PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA "
					+ " SELECT * FROM PROFFLINE_TB_MATERIAL "
					+ " WHERE MATNR ='" + listm.getIdMaterial() + "' ";
			listaSQL.add(cadenaSQL);
			String strUdt = " UPDATE PROFFLINE_TB_MATERIAL_TOP_TIPOLOGIA SET "
					+ " VENTAS_ACUMULADO=" + listm.getDblAcumulado()
					+ " ,VENTAS_PROMEDIO=" + listm.getDblPromedio()
					+ " ,CLIENTE='" + listm.getStrCodCliente() + "' "
					+ " WHERE MATNR='" + listm.getIdMaterial()
					+ "'  AND CLIENTE is null ";
			listaSQL.add(strUdt);
		}
		ResultExecuteList resultExecute2 = new ResultExecuteList();
		resultExecute2.insertarListaConsultas(listaSQL, "Top por tipología", Constante.BD_SYNC);
	}
	@SuppressWarnings("unchecked")
	public List<BeanCondicionComercial1> listarCondicion1( BeanCondicionComercial1 b) {
		List<BeanCondicionComercial1> listaCondicion = new ArrayList<BeanCondicionComercial1>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCondicionComercial1 bean = null;
		column = new HashMap();
		column.put("String:0", "NIVEL");
		column.put("String:1", "CLASECOND");
		column.put("String:2", "ACCESO");
		column.put("String:3", "TABLACOND");
		column.put("String:4", "PRIORIDAD");
		column.put("String:5", "CLASEMATERIAL");
		column.put("String:6", "CONDICIONPAGO");
		column.put("String:7", "CLIENTE");
		column.put("String:8", "UNIDAD");
		column.put("String:9", "IMPORTE");
		column.put("String:10", "ESCALA");
		column.put("String:11", "NROREGCOND");
		column.put("String:12", "NUM");
		
		String strCadClasesMaterial = "and clasematerial";
		int ii = 0;
		if (b.getListaStrClaseMaterial() != null && b.getListaStrClaseMaterial().size() > 0) {
			strCadClasesMaterial += " in (";
			for (BeanClaseMaterial bcm : b.getListaStrClaseMaterial()) {
				if (ii < (b.getListaStrClaseMaterial().size() - 1)) {
					strCadClasesMaterial += "'" + bcm.getStrDescripcionClaseMaterial() + "'" + ",";
				} else {
					strCadClasesMaterial += "'" + bcm.getStrDescripcionClaseMaterial() + "'";
				}
				ii++;
			}
			strCadClasesMaterial += " ) ";
		} else {
			strCadClasesMaterial += " = '*' ";
		}

		sqlMaterial = " select * from proffline_tb_condicion_1x where " + " (condicionpago='"
				+ b.getStrCondicionPago() + "' " + " and claseMaterial='*' " + " and cliente='*') or "
				+ " (condicionpago='" + b.getStrCondicionPago() + "' " + strCadClasesMaterial + " and cliente='*') or "
				+ " (condicionpago='" + b.getStrCondicionPago() + "' " + strCadClasesMaterial + " and cliente='"
				+ b.getStrCliente() + "') or "

				+ " (condicionpago='*' " + strCadClasesMaterial + " and cliente='*') or " + " (condicionpago='"
				+ b.getStrCondicionPago() + "' " + strCadClasesMaterial + " and cliente='*') or "

				+ " (condicionpago='*' " + " and claseMaterial='*' " + " and cliente='" + b.getStrCliente()
				+ "') or " + " (condicionpago='" + b.getStrCondicionPago() + "' " + " and claseMaterial='*' "
				+ " and cliente='" + b.getStrCliente() + "') " + " order by nivel,acceso,prioridad limit 1; ";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanCondicionComercial1();
				bean.setIntNivel(Integer.parseInt(res.get("NIVEL").toString()));
				bean.setStrClaseCond(res.get("CLASECOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("ACCESO").toString()));
				bean.setStrTablaCond(res.get("TABLACOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("PRIORIDAD").toString()));
				bean.setStrClaseMaterial(res.get("CLASEMATERIAL").toString());
				bean.setStrCondicionPago(res.get("CONDICIONPAGO").toString());
				bean.setStrCliente(res.get("CLIENTE").toString());
				bean.setStrUnidad(res.get("UNIDAD").toString());
				bean.setDblImporte(Double.parseDouble(res.get("IMPORTE").toString()));				
				bean.setStrEscala(res.get("ESCALA").toString());
				bean.setStrNroRegCond(res.get("NROREGCOND").toString());
				bean.setStrNum(res.get("NUM").toString());
				listaCondicion.add(bean);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaCondicion;
	}
	@SuppressWarnings("unchecked")
	public List<BeanCondicionComercial2> listarCondicion2(BeanCondicionComercial2 b) {
		List<BeanCondicionComercial2> listaCondicion = new ArrayList<BeanCondicionComercial2>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCondicionComercial2 bean = null;
		column = new HashMap();

		column.put("String:0", "NIVEL");
		column.put("String:1", "CLASECOND");
		column.put("String:2", "ACCESO");
		column.put("String:3", "TABLACOND");
		column.put("String:4", "PRIORIDAD");		
		column.put("String:5", "CLIENTE");
		column.put("String:6", "GRUPOCLIENTE");
		column.put("String:7", "CANAL");
		column.put("String:8", "UNIDAD");
		column.put("String:9", "IMPORTE");
		column.put("String:10", "ESCALA");
		column.put("String:11", "NROREGCOND");
		column.put("String:12", "NUM");

		sqlMaterial = " select * from proffline_tb_condicion_2x where " + " (cliente='" + b.getStrCliente()
				+ "' and grupocliente='*' and canal='*') or " + " (cliente='" + b.getStrCliente()
				+ "' and grupocliente='" + b.getStrGrupoCliente() + "' and canal='*') or " + " (cliente='"
				+ b.getStrCliente() + "' and grupocliente='" + b.getStrGrupoCliente() + "' and canal='"
				+ b.getStrCanal() + "') or " + " (cliente='*' and grupocliente='" + b.getStrGrupoCliente()
				+ "' and canal='*') or " + " (cliente='" + b.getStrCliente() + "' and grupocliente='"
				+ b.getStrGrupoCliente() + "' and canal='*') or " + " (cliente='*' and grupocliente='*' and canal='"
				+ b.getStrCanal() + "') or " + " (cliente='" + b.getStrCliente() + "' and grupocliente='*' and canal='"
				+ b.getStrCanal() + "') " + " order by nivel,acceso,prioridad limit 1; ";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanCondicionComercial2();
				bean.setIntNivel(Integer.parseInt(res.get("NIVEL").toString()));
				bean.setStrClaseCond(res.get("CLASECOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("ACCESO").toString()));
				bean.setStrTablaCond(res.get("TABLACOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("PRIORIDAD").toString()));
				bean.setStrCliente(res.get("CLIENTE").toString());
				bean.setStrGrupoCliente(res.get("GRUPOCLIENTE").toString());
				bean.setStrCanal(res.get("CANAL").toString());
				bean.setStrUnidad(res.get("UNIDAD").toString());
				bean.setDblImporte(Double.parseDouble(res.get("IMPORTE").toString()));				
				bean.setStrEscala(res.get("ESCALA").toString());
				bean.setStrNroRegCond(res.get("NROREGCOND").toString());
				bean.setStrNum(res.get("NUM").toString());
				listaCondicion.add(bean);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaCondicion;
	}

	/*
	 * 	@auto	MARCELO MOYANO
	 * 			11/09/2013
	 * 
	 * 			METODO QUE OBTIENE EL PRECIO DE UN MATERIAL
	 * 			QUE PUEDE SER UN COMBO O UN MATERIAL ESPECIAL
	 * 			PARA UN CLIENTE DADO.
	 * 
	 * 	@param	strCodigoCliente EL CODIGO DEL CLIENTE AL QUE SE DESEA
	 * 			SABER EL PRECIO ESPECIAL QUE TIENE MATA UNA MATERIAL
	 * 			ESPECIFICO, SI strCodigoCliente ES UNA CADENA VACIA,
	 * 			EL PRECIO A OBTENER ES DE UN MATERIAL COMBO.
	 * 	@param	strCodigoMaterial	ES EL CODIGO DEL MATERIAL QUE SE DESEA
	 * 			OBTENER EL PRECIO.
	 */
	// ----------------------------- 11/09/2013 ------------------------------------------------- //
	@SuppressWarnings("unchecked")
	public double getPrecioXCliente(String codCliente, String codMaterial){
		double importe = 0d;
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "IMPORTE");
		String sqlPrecioMaterial = "";
		/*
		 * @author	MARCELO MOYANO
		 * @date	01/12/2014
		 */
		if(codCliente.equals("")){
			sqlPrecioMaterial = "SELECT IMPORTE FROM PROFFLINE_TB_CONDICION_ZPR1 WHERE CLIENTE = '*' AND" 
				+ " MATERIAL = '" + codMaterial + "' ";
//			+ " MATERIAL = '" + strCodigoMaterial + "' AND CLASECOND = 'ZPR1'";
		} else {
			sqlPrecioMaterial = "SELECT IMPORTE FROM PROFFLINE_TB_CONDICION_ZPR1 WHERE CLIENTE = '" + codCliente 
			+ "' AND MATERIAL = '" + codMaterial + "' ";
//			+ "' AND MATERIAL = '" + strCodigoMaterial + "' AND CLASECOND = 'ZPR1'";
		}
		resultExecuteQuery = new ResultExecuteQuery(sqlPrecioMaterial, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = null;
				res = (HashMap) mapResultado.get(0);
				String strImporte = res.get("IMPORTE").toString();
				importe = Double.parseDouble(strImporte);
			}
		}
		return importe;
	}
	// --------------------------- PRECIO CLIENTE MATERIAL ---------------------------- //

	@SuppressWarnings("unchecked")
	public List<BeanCondicionComercial3x> listarCondicion3(BeanCondicionComercial3x b) {
		List<BeanCondicionComercial3x> listaCondicion = new ArrayList<BeanCondicionComercial3x>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCondicionComercial3x bean = null;
		column = new HashMap();
		column.put("String:0", "NIVEL");
		column.put("String:1", "CLASECOND");
		column.put("String:2", "ACCESO");
		column.put("String:3", "TABLACOND");
		column.put("String:4", "PRIORIDAD");
		column.put("String:5", "MATERIAL");
		column.put("String:6", "DIVISIONC");
		column.put("String:7", "DIVISIONCOM");
		column.put("String:8", "CATPROD");
		column.put("String:9", "FAMILIA");
		column.put("String:10", "LINEA");
		column.put("String:11", "GRUPO");
		column.put("String:12", "CLIENTE");
		column.put("String:13", "ZONAPROMESA");
		column.put("String:14", "CLASE");
		column.put("String:15", "GRUPOCLIENTE");
		column.put("String:16", "UNIDAD");
		column.put("String:17", "IMPORTE");
		column.put("String:18", "ESCALA");
		column.put("String:19", "NROREGCOND");
		column.put("String:20", "NUM");
		
		if(b.getStrClase()!=null && !b.getStrClase().equals("") && b.getStrClase().indexOf("-")>=0){
			String strClaseMaterialTemp="";
			String [] arrClaMat=b.getStrClase().split("-");
			for(int i=0;i<arrClaMat.length;i++){
				if(arrClaMat[i]!=null && !arrClaMat[i].equals("")){
					if(strClaseMaterialTemp.equals("")){
						strClaseMaterialTemp+="'"+arrClaMat[i]+"'";
					}
					else{
						strClaseMaterialTemp+=","+"'"+arrClaMat[i]+"'";
					}
				}
			}
		sqlMaterial = 	" select * from proffline_tb_condicion_3X where " +
		" (Material='*' or Material='"+b.getStrMaterial()+"') and (DivisionC='*' or DivisionC='"+b.getStrDivisionC()+"') and (DivisionCom='*' or DivisionCom='"+ String.valueOf( Integer.parseInt(b.getStrDivisionCom()))  +"') AND" +
			" (CatProd='*' or CatProd='"+String.valueOf( Integer.parseInt(b.getStrCatProd()))+"') and (Familia='*' or Familia='"+String.valueOf( Integer.parseInt(b.getStrFamilia()))+"') and (Linea='*' or Linea='"+String.valueOf( Integer.parseInt(b.getStrLinea()))+"') AND " +
			" (Grupo='*' or Grupo='"+String.valueOf( Integer.parseInt(b.getStrGrupo()))+"') and (Cliente='*' or Cliente='"+b.getStrCliente()+"') and (ZonaPromesa='*' or ZonaPromesa='"+b.getStrZonaPromesa()+"') and " +
			" (Clase='*' or Clase in ("+strClaseMaterialTemp+")) and (GrupoCliente='*' or GrupoCliente='"+b.getStrGrupoCliente()+"')" + // --- Marcelo Moyano --- add this -- and (ClaseCond like 'ZD%') //
			" order by Nivel,Acceso,Prioridad limit 1; ";
		}
		else{
		sqlMaterial = 	" select * from proffline_tb_condicion_3X where " +
					" (Material='*' or Material='"+b.getStrMaterial()+"') and (DivisionC='*' or DivisionC='"+b.getStrDivisionC()+"') and (DivisionCom='*' or DivisionCom='"+ String.valueOf( Integer.parseInt(b.getStrDivisionCom()))  +"') AND" +
						" (CatProd='*' or CatProd='"+String.valueOf( Integer.parseInt(b.getStrCatProd()))+"') and (Familia='*' or Familia='"+String.valueOf( Integer.parseInt(b.getStrFamilia()))+"') and (Linea='*' or Linea='"+String.valueOf( Integer.parseInt(b.getStrLinea()))+"') AND " +
						" (Grupo='*' or Grupo='"+String.valueOf( Integer.parseInt(b.getStrGrupo()))+"') and (Cliente='*' or Cliente in ('"+b.getStrCliente()+"')) and (ZonaPromesa='*' or ZonaPromesa='"+b.getStrZonaPromesa()+"') and " +
						" (Clase='*' or Clase in ('"+b.getStrClase()+"')) and (GrupoCliente='*' or GrupoCliente='"+b.getStrGrupoCliente()+"')" + // --- Marcelo Moyano --- add this -- and (ClaseCond like 'ZD%') //
						" order by Nivel,Acceso,Prioridad limit 1; ";
		}
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanCondicionComercial3x();
				bean.setIntNivel(Integer.parseInt(res.get("NIVEL").toString()));
				bean.setStrClaseCond(res.get("CLASECOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("ACCESO").toString()));
				bean.setStrTablaCond(res.get("TABLACOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("PRIORIDAD").toString()));
				bean.setStrMaterial(res.get("MATERIAL").toString());
				bean.setStrDivisionC(res.get("DIVISIONC").toString());
				bean.setStrDivisionCom(res.get("DIVISIONCOM").toString());
				bean.setStrCatProd(res.get("CATPROD").toString());
				bean.setStrFamilia(res.get("FAMILIA").toString());
				bean.setStrLinea(res.get("LINEA").toString());
				bean.setStrCliente(res.get("CLIENTE").toString());
				bean.setStrZonaPromesa(res.get("ZONAPROMESA").toString());
				bean.setStrClase(res.get("CLASE").toString());
				bean.setStrGrupoCliente(res.get("GRUPOCLIENTE").toString());
				bean.setStrUnidad(res.get("UNIDAD").toString());
				bean.setDblImporte(Double.parseDouble(res.get("IMPORTE").toString()));				
				bean.setStrEscala(res.get("ESCALA").toString());
				bean.setStrNroRegCond(res.get("NROREGCOND").toString());
				bean.setStrNum(res.get("NUM").toString());
				
				if(bean.getStrEscala()!=null && bean.getStrEscala().equals("X")){
					BeanCondicionComercialEscala bcce = new BeanCondicionComercialEscala();
					bcce.setStrNroRegCond(bean.getStrNroRegCond());
					bean.setListEscalas(listarCondicionEscalas(bcce));
				}
				listaCondicion.add(bean);	
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaCondicion;
	}
	@SuppressWarnings("unchecked")
	public List<BeanCondicionComercial4x> listarCondicion4(BeanCondicionComercial3x b) {
		List<BeanCondicionComercial4x> listaCondicion = new ArrayList<BeanCondicionComercial4x>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCondicionComercial4x bean = null;
		column = new HashMap();
		column.put("String:0", "NIVEL");
		column.put("String:1", "CLASECOND");
		column.put("String:2", "ACCESO");
		column.put("String:3", "TABLACOND");
		column.put("String:4", "PRIORIDAD");
		column.put("String:5", "MATERIAL");
		column.put("String:6", "DIVISIONC");
		column.put("String:7", "DIVISIONCOM");
		column.put("String:8", "CATPROD");
		column.put("String:9", "FAMILIA");
		column.put("String:10", "LINEA");
		column.put("String:11", "GRUPO");
		column.put("String:12", "CLIENTE");
		column.put("String:13", "ZONAPROMESA");
		column.put("String:14", "CLASE");
		column.put("String:15", "GRUPOCLIENTE");
		column.put("String:16", "UNIDAD");
		column.put("String:17", "IMPORTE");
		column.put("String:18", "ESCALA");
		column.put("String:19", "NROREGCOND");
		column.put("String:20", "NUM");
		sqlMaterial = 	" select * from proffline_tb_condicion_4X where " +
					" (Material='*' or Material='"+b.getStrMaterial()+"') and (DivisionC='*' or DivisionC='"+b.getStrDivisionC()+"') and (DivisionCom='*' or DivisionCom='"+ String.valueOf( Integer.parseInt(b.getStrDivisionCom()))  +"') AND" +
						" (CatProd='*' or CatProd='"+String.valueOf( Integer.parseInt(b.getStrCatProd()))+"') and (Familia='*' or Familia='"+String.valueOf( Integer.parseInt(b.getStrFamilia()))+"') and (Linea='*' or Linea='"+String.valueOf( Integer.parseInt(b.getStrLinea()))+"') AND " +
						" (Grupo='*' or Grupo='"+String.valueOf( Integer.parseInt(b.getStrGrupo()))+"') and (Cliente='*' or Cliente='"+b.getStrCliente()+"') and (ZonaPromesa='*' or ZonaPromesa='"+b.getStrZonaPromesa()+"') and " +
						" (Clase='*' or Clase in('"+b.getStrClase()+"')) and (GrupoCliente='*' or GrupoCliente='"+b.getStrGrupoCliente()+"') " +
						" order by Nivel,Acceso,Prioridad limit 1; ";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanCondicionComercial4x();
				bean.setIntNivel(Integer.parseInt(res.get("NIVEL").toString()));
				bean.setStrClaseCond(res.get("CLASECOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("ACCESO").toString()));
				bean.setStrTablaCond(res.get("TABLACOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("PRIORIDAD").toString().trim()));
				bean.setStrMaterial(res.get("MATERIAL").toString());
				bean.setStrDivisionC(res.get("DIVISIONC").toString());
				bean.setStrDivisionCom(res.get("DIVISIONCOM").toString());
				bean.setStrCatProd(res.get("CATPROD").toString());
				bean.setStrFamilia(res.get("FAMILIA").toString());
				bean.setStrLinea(res.get("LINEA").toString());
				bean.setStrCliente(res.get("CLIENTE").toString());
				bean.setStrZonaPromesa(res.get("ZONAPROMESA").toString());
				bean.setStrClase(res.get("CLASE").toString());
				bean.setStrGrupoCliente(res.get("GRUPOCLIENTE").toString());
				bean.setStrUnidad(res.get("UNIDAD").toString());
				bean.setDblImporte(Double.parseDouble(res.get("IMPORTE").toString()));				
				bean.setStrEscala(res.get("ESCALA").toString());
				bean.setStrNroRegCond(res.get("NROREGCOND").toString());
				bean.setStrNum(res.get("NUM").toString());
				
				if(bean.getStrEscala()!=null && bean.getStrEscala().equals("X")){
					BeanCondicionComercialEscala bcce = new BeanCondicionComercialEscala();
					bcce.setStrNroRegCond(bean.getStrNroRegCond());
					bean.setListEscalas(listarCondicionEscalas(bcce));
				}
				listaCondicion.add(bean);	
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaCondicion;
	}
	@SuppressWarnings("unchecked")
	public List<BeanCondicionComercial5x> listarCondicion5( BeanCondicionComercial5x b) {
		List<BeanCondicionComercial5x> listaCondicion = new ArrayList<BeanCondicionComercial5x>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCondicionComercial5x bean = null;
		column = new HashMap();
		column.put("String:0", "NIVEL");
		column.put("String:1", "CLASECOND");
		column.put("String:2", "ACCESO");
		column.put("String:3", "TABLACOND");
		column.put("String:4", "PRIORIDAD");
		column.put("String:5", "DIVISIONCOM");
		column.put("String:6", "CATPROD");
		column.put("String:7", "FAMILIA");
		column.put("String:8", "CLIENTE");
		column.put("String:9", "UNIDAD");
		column.put("String:10", "IMPORTE");
		column.put("String:11", "ESCALA");
		column.put("String:12", "NROREGCOND");
		column.put("String:13", "NUM");
		
		sqlMaterial = 	" select * from proffline_tb_condicion_5X where " +
					" (DivisionCom='*' or DivisionCom='"+ String.valueOf( Integer.parseInt(b.getStrDivisionCom()))  +"') AND" +
						" (CatProd='*' or CatProd='"+String.valueOf( Integer.parseInt(b.getStrCatProd()))+"') and (Familia='*' or Familia='"+String.valueOf( Integer.parseInt(b.getStrFamilia()))+"') AND " +
						" (Cliente='*' or Cliente='"+b.getStrCliente()+"') " +
						" order by Nivel,Acceso,Prioridad limit 1; ";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;

			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanCondicionComercial5x();
				bean.setIntNivel(Integer.parseInt(res.get("NIVEL").toString()));
				bean.setStrClaseCond(res.get("CLASECOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("ACCESO").toString()));
				bean.setStrTablaCond(res.get("TABLACOND").toString());
				bean.setIntAcceso(Integer.parseInt(res.get("PRIORIDAD").toString()));
				bean.setStrDivisionCom(res.get("DIVISIONCOM").toString());
				bean.setStrCatProd(res.get("CATPROD").toString());
				bean.setStrFamilia(res.get("FAMILIA").toString());
				bean.setStrCliente(res.get("CLIENTE").toString());
				bean.setStrUnidad(res.get("UNIDAD").toString());
				bean.setDblImporte(Double.parseDouble(res.get("IMPORTE").toString()));				
				bean.setStrEscala(res.get("ESCALA").toString());
				bean.setStrNroRegCond(res.get("NROREGCOND").toString());
				bean.setStrNum(res.get("NUM").toString());
				
				if(bean.getStrEscala()!=null && bean.getStrEscala().equals("X")){
					BeanCondicionComercialEscala bcce = new BeanCondicionComercialEscala();
					bcce.setStrNroRegCond(bean.getStrNroRegCond());
					bean.setListEscalas(listarCondicionEscalas(bcce));
				}
				
				listaCondicion.add(bean);
			}

		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaCondicion;
	}	
	@SuppressWarnings("unchecked")
	public List<BeanCondicionComercialEscala> listarCondicionEscalas(BeanCondicionComercialEscala b) {
		List<BeanCondicionComercialEscala> listaCondicionEscala = new ArrayList<BeanCondicionComercialEscala>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanCondicionComercialEscala bean = null;
		column = new HashMap();
		column.put("String:0", "CLASECOND");
		column.put("String:1", "TIPOESCALA");
		column.put("String:2", "NROREGCOND");
		column.put("String:3", "NUMACTCOND");
		column.put("String:4", "NUMLINEA");
		column.put("String:5", "CANTESCALA");
		column.put("String:6", "VALORESCALA");
		column.put("String:7", "IMPORTE");

		sqlMaterial2 = 	" select * from proffline_tb_condicion_escalas where NroRegCond='"+b.getStrNroRegCond()+"' ";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial2, column, Constante.BD_SYNC);
			mapResultado2 = resultExecuteQuery.getMap();
			HashMap res = null;

			for (int i = 0; i < mapResultado2.size(); i++) {
				res = (HashMap) mapResultado2.get(i);
				bean = new BeanCondicionComercialEscala();
				bean.setStrClaseCond(res.get("CLASECOND").toString());
				bean.setStrTipoEscala(res.get("TIPOESCALA").toString());
				bean.setStrNroRegCond(res.get("NROREGCOND").toString());
				bean.setIntNumActCond(Integer.parseInt(res.get("NUMACTCOND").toString()));
				bean.setIntNumLinea(Integer.parseInt(res.get("NUMLINEA").toString()));
				bean.setStrCantEscala(res.get("CANTESCALA").toString());
				bean.setStrValorEscala(res.get("VALORESCALA").toString());
				bean.setStrImporte(res.get("IMPORTE").toString());
				listaCondicionEscala.add(bean);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaCondicionEscala;
	}

	public void actualizarStock(String strCodMaterial, int intStock) {
		sqlMaterial = "UPDATE  " + " PROFFLINE_TB_MATERIAL  " + " SET ST_1 = '"
				+ intStock + "'" + " WHERE MATNR = '" + strCodMaterial + "'";
		ResultExecute resultExecute = new ResultExecute(sqlMaterial, "Materiales", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}
	@SuppressWarnings("unchecked")
	public List<BeanClaseMaterial> obtenerClaseMaterial(String strCodigoMaterial) {
		List<BeanClaseMaterial> listaClases = new ArrayList<BeanClaseMaterial>();
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "TXTCODIGOCLASE");
		column.put("String:1", "TXTDESCRIPCIONCLASE");

		sqlMaterial = " select TXTCODIGOCLASE,TXTDESCRIPCIONCLASE "
				+ " from proffline_tb_clase_material "
				+ " where trim(txtcodigomaterial)='" + strCodigoMaterial + "' ";

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			BeanClaseMaterial bean = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanClaseMaterial();
				bean.setStrCodigoClaseMaterial(res.get("TXTCODIGOCLASE").toString());
				bean.setStrDescripcionClaseMaterial(res.get("TXTDESCRIPCIONCLASE").toString());
				listaClases.add(bean);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaClases;
	}
	@SuppressWarnings("unchecked")
	public List<BeanMaterial> obtenerMaterialesPorClaseMaterial(String strCodClaseMaterial) {
		List<BeanMaterial> listaMateriales = new ArrayList<BeanMaterial>();
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "TXTCODIGOMATERIAL");

		sqlMaterial = " select TXTCODIGOMATERIAL "
				+ " from proffline_tb_clase_material "
				+ " where txtDescripcionClase='" + strCodClaseMaterial + "' ";

		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			BeanMaterial bean = null;
			int ii = 0;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				bean = new BeanMaterial();
				ii = Integer.parseInt(res.get("TXTCODIGOMATERIAL").toString());
				bean.setIdMaterial("" + ii);
				listaMateriales.add(bean);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaMateriales;
	}

	public void setInsertarClaseMaterial(List<BeanClaseMaterial> listClaseMaterial) {
		final List<String> listaSQL = new ArrayList<String>();
		for (BeanClaseMaterial m : listClaseMaterial) {
			listaSQL.add("INSERT INTO " + TABLA_CLASE_MATERIAL + " ("
					+ "TXTCODIGOCLASE" + "," + "TXTDESCRIPCIONCLASE" + ","
					+ "TXTCODIGOMATERIAL" + ")" + " VALUES (" + "'"
					+ m.getStrCodigoClaseMaterial() + "'" + "," + "'"
					+ m.getStrDescripcionClaseMaterial().replaceAll("''", "") + "'" + ",'"
					+ m.getStrCodigoMaterial() + "'" + ")");
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(listaSQL, "clase de materiales", Constante.BD_SYNC);
	}
	@SuppressWarnings("unchecked")
	public List<BeanMaterial> buscarMaterialPorMarca(String strCodigoMarca) {
		List<BeanMaterial> listaMaterial = new ArrayList<BeanMaterial>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanMaterial material = null;
		column = new HashMap();
		column.put("String:0", MATNR);
		sqlMaterial = " SELECT " + MATNR + " FROM " + TABLA_MATERIAL
				+ " where NORMT='" + strCodigoMarca + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterial, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				material = new BeanMaterial();
				material.setIdMaterial(res.get(MATNR).toString());
				listaMaterial.add(material);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaMaterial;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public HashMap<String, String> getTipoMaterial(){
		HashMap<String, String> codigos = new HashMap<String, String>();
		List<String> listaSql = new ArrayList<String>();
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "TYPEMAT");
		column.put("String:1", "MATNR");
		String sql = " SELECT MATNR, TYPEMAT FROM PROFFLINE_TB_MATERIAL";
	
		try {
			resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				codigos.put(res.get("MATNR").toString(), res.get(TYPEMAT).toString());
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return codigos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaterialMarcaEstrategica(String matnr) {
		String sql = "SELECT PRDHA AS PRDHA FROM PROFFLINE_TB_MATERIAL WHERE MATNR='"+matnr+"';";
		column = new HashMap();
		column.put("String:0", "PRDHA");
		String prdha = "";
		try {
			ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				prdha = (res.get("PRDHA").toString());
				prdha = prdha.substring(0, 9);
			}
			
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return prdha;
	}

	
	@Override
	public int getCountRowNuevo(String strCodMaterial) {
		ResultExecuteQuery resultExecuteQuery = null;
		column = new HashMap();
		column.put("String:0", "filas");
		int f = 0;
		sqlMaterialNuevo = " SELECT COUNT(*) AS filas FROM " + TABLA_MATERIAL_NUEVO + " WHERE "+MATNR+" = '"+strCodMaterial+"';";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterialNuevo, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			res = (HashMap) mapResultado.get(0);
			f = Integer.parseInt(res.get("filas").toString().trim());
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return f;
	}

	@Override
	public List<BeanMaterial> getMaterialNuevo(String codigo, String IdMaterial) {
		List<BeanMaterial> listaMaterial = new ArrayList<BeanMaterial>();
		ResultExecuteQuery resultExecuteQuery = null;
		BeanMaterial material = null;
		column = new HashMap();
		column.put("String:0", MATNR);
		column.put("String:1", PRICE_1);
		column.put("String:2", ZZORDCO);
		sqlMaterialNuevo = " SELECT " + MATNR + " , PRICE_1, ZZORDCO FROM " + TABLA_MATERIAL_NUEVO
				+ " where NORMT='" + codigo + "' ORDER BY ZZORDCO DESC";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlMaterialNuevo, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			//HashMap res = null;
			//for (int i = 0; i < mapResultado.size(); i++) {
			for(HashMap res:mapResultado.values()){	
				if(!res.get(MATNR).toString().equals(IdMaterial)){
				material = new BeanMaterial();
				material = getMaterial(res.get(MATNR).toString());
				material.setPrice_1(res.get(PRICE_1).toString());
				material.setZzordco(res.get(ZZORDCO).toString());
				listaMaterial.add(material);
				}
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return listaMaterial;
	}



	
	
}