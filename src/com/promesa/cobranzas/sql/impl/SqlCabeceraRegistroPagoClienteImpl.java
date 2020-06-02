package com.promesa.cobranzas.sql.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;
import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.Constante;

public class SqlCabeceraRegistroPagoClienteImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecute resultExecute = null;
	
	public void insertarCabeceraRegistroPagoCliente(CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		/*
		 * Marcelo Moyano
		 */
		String sql = "INSERT INTO PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE (codigoCliente, codigoVendedor, importeEntrado, importeAsignado,"
			+ "importeSinAsignar, partidasSeleccionadas, fechaCreacion, sincronizado, cabeceraSecuencia) VALUES ('"
			+ cabeceraRegistroPagoCliente.getCodigoCliente()
			+ "','" + cabeceraRegistroPagoCliente.getCodigoVendedor()
			+ "','" + cabeceraRegistroPagoCliente.getImporteEntrado()
			+ "','" + cabeceraRegistroPagoCliente.getImporteAsignado()
			+ "','" + cabeceraRegistroPagoCliente.getImporteSinAsignar()
			+ "','" + cabeceraRegistroPagoCliente.getPartidasSeleccionadas()
			+ "','" + cabeceraRegistroPagoCliente.getFechaCreacion()
			+ "','" + cabeceraRegistroPagoCliente.getSincronizado()
			+ "','" + cabeceraRegistroPagoCliente.getCabeceraSecuencia() + "');";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void actualizarCabeceraRegistroPagoCliente(CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE SET "
				+ "importeEntrado = '" 			+ cabeceraRegistroPagoCliente.getImporteEntrado() 		+ "', "
				+ "importeAsignado = '" 		+ cabeceraRegistroPagoCliente.getImporteAsignado() 		+ "', "
				+ "importeSinAsignar = '" 		+ cabeceraRegistroPagoCliente.getImporteSinAsignar() 	+ "', "
				+ "partidasSeleccionadas = '" 	+ cabeceraRegistroPagoCliente.getPartidasSeleccionadas()+ "' "
				+ "WHERE idCabecera = " 		+ cabeceraRegistroPagoCliente.getIdCabecera() 			+ ";";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void actualizarCabeceraRegistroPagoClienteSincronizado(CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE SET "
				+ "sincronizado = '1' "
				+ "WHERE idCabecera = " + cabeceraRegistroPagoCliente.getIdCabecera() + ";";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public boolean eliminarCabeceraRegistroPagoCliente(CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente) {
		String sql = "DELETE FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE " + "WHERE idCabecera = " + cabeceraRegistroPagoCliente.getIdCabecera() + ";";
		resultExecute = new ResultExecute(sql, Constante.BD_TX);
		return resultExecute.isFlag();
	}
	
	public boolean cambiarEstadoCabeceraRegistroPagoCliente(CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente){
		String sql = "UPDATE PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE SET eliminado='true' " + "WHERE idCabecera = " + cabeceraRegistroPagoCliente.getIdCabecera() + ";";
		resultExecute = new ResultExecute(sql, Constante.BD_TX);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CabeceraRegistroPagoCliente> obtenerListaCabeceraRegistroPagoCliente(String codigoVendedor) {
		List<CabeceraRegistroPagoCliente> listaCabeceraRegistroPagoCliente = new ArrayList<CabeceraRegistroPagoCliente>();
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		column.put("String:9", "cabeceraSecuencia");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE "
				+ "WHERE codigoVendedor = '" + codigoVendedor + "' "
				+ "AND sincronizado = 0 AND eliminado='false' " + "ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
					cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
					cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
					cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
					cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
					cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
					cabeceraRegistroPagoCliente.setCabeceraSecuencia(res.get("cabeceraSecuencia").toString());
					listaCabeceraRegistroPagoCliente.add(cabeceraRegistroPagoCliente);
				}
			}
		}
		return listaCabeceraRegistroPagoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CabeceraRegistroPagoCliente obtenerCabeceraRegistroPagoCliente(long idCabecera) {
		CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = null;
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE " + "WHERE idCabecera = '" + idCabecera + "' " + "AND sincronizado = 0 AND eliminado='false';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
				cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
				cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
				cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
				cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
				cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
				cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
				cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
				cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
				cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
			}
		}
		return cabeceraRegistroPagoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerUltimoIdCabeceraRegistroPagoCliente(){
		String id = "";
		column = new HashMap();
		column.put("String:0", "id");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT MAX(idCabecera) as id FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE LIMIT 1;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				id = res.get("id").toString();
			}
		}
		return id;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long obtenerCantidadPagoRecibido(long idCabecera){
		long cantidadPagoRecibido = 0L;
		column = new HashMap();
		column.put("String:0", "cantidadPagoRecibido");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT COUNT(*) AS cantidadPagoRecibido FROM PROFFLINE_TB_PAGO_RECIBIDO " + "WHERE idCabecera = " + idCabecera + ";";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				cantidadPagoRecibido = Long.parseLong(res.get("cantidadPagoRecibido").toString());
			}
		}
		return cantidadPagoRecibido;
	}
	
	//	Marcelo Moyano 16/04/2013
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CabeceraRegistroPagoCliente> obtenerListaCabeceraRegistroPagoClientePorCliente(String codigoCliente) {
		List<CabeceraRegistroPagoCliente> listaCabeceraRegistroPagoClientePorCliente = new ArrayList<CabeceraRegistroPagoCliente>();
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE "
				+ "WHERE codigoCliente = '" + codigoCliente + "' " + "AND sincronizado = 0 " + "ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
					cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
					cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
					cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
					cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
					cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
					listaCabeceraRegistroPagoClientePorCliente.add(cabeceraRegistroPagoCliente);
				}
			}
		}
		return listaCabeceraRegistroPagoClientePorCliente;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CabeceraRegistroPagoCliente> getPagoClienteSinSincronizar(){
		List<CabeceraRegistroPagoCliente> listaPagoCliente = new ArrayList<CabeceraRegistroPagoCliente>();
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		
		String sql = "select * from PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE where sincronizado = 0 and eliminado = 'false'";
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
					cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
					cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
					cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
					cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
					cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
					listaPagoCliente.add(cabeceraRegistroPagoCliente);
				}
			}
		}
		return listaPagoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CabeceraRegistroPagoCliente> obtenerListaCabeceraSinSincronizar() {
		List<CabeceraRegistroPagoCliente> listaCabeceraRegistroPagoCliente = new ArrayList<CabeceraRegistroPagoCliente>();
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE "
				+ "WHERE sincronizado = 0 " + "ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
					cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
					cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
					cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
					cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
					cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
					listaCabeceraRegistroPagoCliente.add(cabeceraRegistroPagoCliente);
				}
			}
		}
		return listaCabeceraRegistroPagoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CabeceraRegistroPagoCliente> obtenerCabezaraOffLineEliminada() {
		List<CabeceraRegistroPagoCliente> listaCabeceraRegistroPagoCliente = new ArrayList<CabeceraRegistroPagoCliente>();
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		column.put("String:9", "cabeceraSecuencia");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE "
				+ "WHERE sincronizado = 0 AND eliminado='true' ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
					cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
					cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
					cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
					cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
					cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
					cabeceraRegistroPagoCliente.setCabeceraSecuencia(res.get("cabeceraSecuencia").toString());
					listaCabeceraRegistroPagoCliente.add(cabeceraRegistroPagoCliente);
				}
			}
		}
		return listaCabeceraRegistroPagoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CabeceraRegistroPagoCliente> obtenerCabezaraOffLineEliminadaSinc() {
		List<CabeceraRegistroPagoCliente> listaCabeceraRegistroPagoCliente = new ArrayList<CabeceraRegistroPagoCliente>();
		column = new HashMap();
		column.put("String:0", "idCabecera");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "importeEntrado");
		column.put("String:4", "importeAsignado");
		column.put("String:5", "importeSinAsignar");
		column.put("String:6", "partidasSeleccionadas");
		column.put("String:7", "fechaCreacion");
		column.put("String:8", "sincronizado");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE "
				+ "WHERE sincronizado = 1 AND eliminado='true' ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = new CabeceraRegistroPagoCliente();
					cabeceraRegistroPagoCliente.setIdCabecera(Integer.parseInt(res.get("idCabecera").toString()));
					cabeceraRegistroPagoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					cabeceraRegistroPagoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					cabeceraRegistroPagoCliente.setImporteEntrado(Double.parseDouble(res.get("importeEntrado").toString()));
					cabeceraRegistroPagoCliente.setImporteAsignado(Double.parseDouble(res.get("importeAsignado").toString()));
					cabeceraRegistroPagoCliente.setImporteSinAsignar(Double.parseDouble(res.get("importeSinAsignar").toString()));
					cabeceraRegistroPagoCliente.setPartidasSeleccionadas(Integer.parseInt(res.get("partidasSeleccionadas").toString()));
					cabeceraRegistroPagoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					cabeceraRegistroPagoCliente.setSincronizado(res.get("sincronizado").toString());
					listaCabeceraRegistroPagoCliente.add(cabeceraRegistroPagoCliente);
				}
			}
		}
		return listaCabeceraRegistroPagoCliente;
	}
}