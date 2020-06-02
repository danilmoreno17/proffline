package com.promesa.pedidos.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.Map;

import com.promesa.dao.ResultExecuteQuery;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.pedidos.sql.SqlCombo;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlComboImpl implements SqlCombo {

	@SuppressWarnings({ "rawtypes" })
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Item getCombo(String codigo) {
		Item item = new Item();
		column = new HashMap();
		column.put("String:0", "c1");
		column.put("String:1", "c2");
		column.put("String:2", "c3");
		column.put("String:3", "c4");
		column.put("String:4", "c5");
		column.put("String:5", "c6");
		column.put("String:6", "c7");
		column.put("String:7", "unidad");
		column.put("String:8", "SHORT_TEXT");
		String sql = "select proffline_tb_combo.codigo as c1, "
				+ "proffline_tb_combo.nombre as c2, proffline_tb_combo.precio "
				+ "as c3, proffline_tb_detalle_combo.codigo as c4, "
				+ "proffline_tb_detalle_combo.nombre as c5, "
				+ "proffline_tb_detalle_combo.precio as c6, proffline_tb_detalle_combo.cantidad as c7,  "
				+ "unidad, SHORT_TEXT from proffline_tb_combo inner join proffline_tb_detalle_combo on "
				+ "proffline_tb_combo.codigo=proffline_tb_detalle_combo.idCombo inner join proffline_tb_material on proffline_tb_material.matnr=proffline_tb_detalle_combo.codigo "
				+ "where c1='" + codigo
				+ "' and proffline_tb_detalle_combo.idCombo='" + codigo + "';";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql,column,Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		List<Item> detalles = new ArrayList<Item>();
		Double precio_neto = 0d;
		for (int i = 0; i < mapResultado.size(); i++) {
			Item detalle = new Item();
			res = (HashMap) mapResultado.get(i);
			Iterator it = res.entrySet().iterator();
			// -- CABECERA ------------------------------
			item.setCodigo(res.get("c1").toString());
			item.setDenominacion(res.get("c2").toString());
			item.setUnidad(res.get("unidad").toString());
			item.setTipoMaterial("N");
			try {
				double price = Double.parseDouble(res.get("c3").toString());
				precio_neto += price;
				item.setPrecioNeto(price);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				item.setPrecioNeto(0d);
			}
			item.setPrecioNeto(precio_neto);
			// -- DETALLE ------------------------------
			detalle.setUnidad(res.get("unidad").toString());
			detalle.setCodigo(res.get("c4").toString());
			detalle.setDenominacion(res.get("SHORT_TEXT").toString());
			try {
				// double price = Double.parseDouble(res.get("c6").toString());
				// detalle.setPrecioNeto(price);
				detalle.setPrecioNeto(0d);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				detalle.setPrecioNeto(0d);
			}
			detalle.setCantidad(res.get("c7").toString());
			try {
				detalle.setCantidadAMostrar(Integer.parseInt(res.get("c7").toString()));
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				detalle.setCantidadAMostrar(0);
			}
			// detalle.setCantidad("0");
			detalle.setCantidadConfirmada("0");
			detalle.setPorcentajeDescuento("0.0");
			detalle.setTipoMaterial("N");
			detalle.setTipo(2);
			detalle.setEditable(false);
			detalles.add(detalle);
		}
		item.setCantidad("1");
		item.setCombos(detalles);
		item.setEditable(true);
		item.setTipoSAP("ZPP1");
		item.setTipo(1);// <- Determinas que es un combo
		if (detalles.size() > 0) {
			return item;
		} else {
			return null;
		}
	}
}
