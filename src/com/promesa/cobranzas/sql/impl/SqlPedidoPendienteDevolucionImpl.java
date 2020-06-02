package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.PedidoPendienteDevolucion;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlPedidoPendienteDevolucionImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecuteList resultExecuteList = null;
	private ResultExecute resultExecute = null;
	
	public void insertarListaPedidoPendienteDevolucion(List<PedidoPendienteDevolucion> lstPedidoPendienteDevolucion){
		List<String> listaPedidoPendienteDevolucion = new ArrayList<String>();
		for(PedidoPendienteDevolucion pedidoPendienteDevolucion : lstPedidoPendienteDevolucion){
			String sql = "INSERT INTO PROFFLINE_TB_PEDIDO_PENDIENTE_DEVOLUCION (codigoCliente, codigoVendedor, numeroDocumento, fechaDocumento,"
					+ "creadoEl, valorNeto, nombreVendedor) VALUES ('" + Long.parseLong(pedidoPendienteDevolucion.getCodigoCliente())
					+ "','" + pedidoPendienteDevolucion.getCodigoVendedor()
					+ "','" + pedidoPendienteDevolucion.getNumeroDocumento()
					+ "','" + pedidoPendienteDevolucion.getFechaDocumento()
					+ "','" + pedidoPendienteDevolucion.getCreadoEl()
					+ "','" + pedidoPendienteDevolucion.getValorNeto()
					+ "','" + pedidoPendienteDevolucion.getNombreVendedor() + "');";
			listaPedidoPendienteDevolucion.add(sql);
		}
		resultExecuteList = new ResultExecuteList();
		resultExecuteList.insertarListaConsultas(listaPedidoPendienteDevolucion, "PEDIDO PENDIENTE DEVOLUCION", Constante.BD_SYNC);
	}
	
	public boolean eliminarListaPedidoPendienteDevolucion(){
		String sql = "DELETE FROM PROFFLINE_TB_PEDIDO_PENDIENTE_DEVOLUCION;";
		resultExecute = new ResultExecute(sql, "PEDIDO PENDIENTE DEVOLUCION", Constante.BD_SYNC);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PedidoPendienteDevolucion> obtenerListaPedidoPendienteDevolucion(String codigoCliente){
		List<PedidoPendienteDevolucion> listaPedidoPendienteDevolucion = new ArrayList<PedidoPendienteDevolucion>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "numeroDocumento");
		column.put("String:4", "fechaDocumento");
		column.put("String:5", "creadoEl");
		column.put("String:6", "valorNeto");
		column.put("String:7", "nombreVendedor");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_PEDIDO_PENDIENTE_DEVOLUCION " + "WHERE codigoCliente = '" + codigoCliente + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					PedidoPendienteDevolucion pedidoPendienteDevolucion = new PedidoPendienteDevolucion();
					pedidoPendienteDevolucion.setId(Integer.parseInt(res.get("id").toString()));
					pedidoPendienteDevolucion.setCodigoCliente(res.get("codigoCliente").toString());
					pedidoPendienteDevolucion.setCodigoVendedor(res.get("codigoVendedor").toString());
					pedidoPendienteDevolucion.setNumeroDocumento(res.get("numeroDocumento").toString());
					pedidoPendienteDevolucion.setFechaDocumento(res.get("fechaDocumento").toString());
					pedidoPendienteDevolucion.setCreadoEl(res.get("creadoEl").toString());
					pedidoPendienteDevolucion.setValorNeto(res.get("valorNeto").toString());
					pedidoPendienteDevolucion.setNombreVendedor(res.get("nombreVendedor").toString());
					listaPedidoPendienteDevolucion.add(pedidoPendienteDevolucion);
				}
			}
		}
		return listaPedidoPendienteDevolucion;
	}
}