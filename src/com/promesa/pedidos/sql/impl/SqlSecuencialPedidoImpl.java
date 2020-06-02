package com.promesa.pedidos.sql.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.pedidos.bean.BeanSecuencia;
import com.promesa.pedidos.sql.SqlSecuencialPedido;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlSecuencialPedidoImpl implements SqlSecuencialPedido {
	
	private static SqlSecuencialPedido instance = null;
	
	public static SqlSecuencialPedido create(){
		synchronized (SqlSecuencialPedidoImpl.class) {
			if(instance == null){
				instance = new SqlSecuencialPedidoImpl();
			}
			return instance;
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSecuencialPedido(String codigoVendedor) {
		int secuenciaPedido = 0;
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		HashMap column = new HashMap();
		column.put("String:0", "SEC_PEDIDO");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT SEC_PEDIDO FROM PROFFLINE_TB_SECUENCIA"
				+ " WHERE TXTIDEMPLEADO = '" + codigoVendedor + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column,Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				HashMap res = (HashMap) mapResultado.get(0);
				secuenciaPedido = Integer.parseInt(res.get("SEC_PEDIDO").toString().trim());
			}
		}
		return secuenciaPedido;
		
	}

	@Override
	public void actualizarSecuencialPedido(String codigoVendedor, int secuencialPedido) {

		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_SECUENCIA SET " + "SEC_PEDIDO = '"
				+ secuencialPedido + "' " + "WHERE TXTIDEMPLEADO = '"
				+ codigoVendedor + "';";
		try {
			stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		
	}
	
	@Override
	public void insertarSecuenciaPorVendedor(BeanSecuencia bs) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "INSERT INTO PROFFLINE_TB_SECUENCIA (txtIdEmpleado, txtSecuencia, sec_pedido) VALUES ('"
				+ bs.getCodigoVendedor() + "','" + bs.getSecuenciaCobranza() +"','"+bs.getSecuenciaPedido() +"');";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}

}
