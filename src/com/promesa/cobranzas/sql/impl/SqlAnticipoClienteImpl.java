package com.promesa.cobranzas.sql.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 

import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.util.Constante;

public class SqlAnticipoClienteImpl {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private ResultExecute resultExecute = null;
	List<BancoPromesa> listaBancoPromesa = null;
	List<FormaPago> listaFormaPagoAnticipo = null;
	List<BancoCliente> listaBancoCliente = null;
	
	public void insertarAnticipoCliente(AnticipoCliente anticipoCliente) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		/*
		 * @author 	MARCELO MOYANO
		 */
		String sql = "INSERT INTO PROFFLINE_TB_ANTICIPO_CLIENTE (codigoCliente, codigoVendedor, nombreCompletoCliente, idFormaPago, referencia, importe,"
			+ "nroCtaBcoCliente, idBancoCliente, idBancoPromesa, observaciones, fechaCreacion, sincronizado, anticipoSecuencia) VALUES ('"
			+ anticipoCliente.getCodigoCliente()
			+ "','" + anticipoCliente.getCodigoVendedor()
			+ "','" + anticipoCliente.getNombreCompletoCliente()
			+ "','" + anticipoCliente.getIdFormaPago()
			+ "','" + anticipoCliente.getReferencia()
			+ "','" + anticipoCliente.getImporte()
			+ "','" + anticipoCliente.getNroCtaBcoCliente()
			+ "','" + anticipoCliente.getIdBancoCliente()
			+ "','" + anticipoCliente.getIdBancoPromesa()
			+ "','" + anticipoCliente.getObservaciones()
			+ "','" + anticipoCliente.getFechaCreacion()
			+ "','" + anticipoCliente.getSincronizado()
			+ "','" + anticipoCliente.getAnticipoSecuencia() + "');";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void actualizarAnticipoCliente(AnticipoCliente anticipoCliente) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_TX);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_ANTICIPO_CLIENTE SET " + "sincronizado = '" + anticipoCliente.getSincronizado() 
				+ "' WHERE id = " + anticipoCliente.getId() + ";";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public boolean eliminarAnticipoCliente(AnticipoCliente anticipoCliente) {
		String sql = "DELETE FROM PROFFLINE_TB_ANTICIPO_CLIENTE " + "WHERE id = " + anticipoCliente.getId() + " and sincronizado = '1';";
		resultExecute = new ResultExecute(sql, Constante.BD_TX);
		return resultExecute.isFlag();
	}
	
	public boolean cambiarEstadoEliminadoAniticpoCliente(AnticipoCliente anticipoCliente){
		String sql = "UPDATE PROFFLINE_TB_ANTICIPO_CLIENTE SET eliminado='true' " + "WHERE id = " + anticipoCliente.getId() + ";";
		resultExecute = new ResultExecute(sql, Constante.BD_TX);
		return resultExecute.isFlag();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AnticipoCliente> obtenerListaAnticipoCliente(String codigoVendedor){
		List<AnticipoCliente> listaAnticipoCliente = new ArrayList<AnticipoCliente>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "nombreCompletoCliente");
		column.put("String:4", "idFormaPago");
		column.put("String:5", "referencia");
		column.put("String:6", "importe");
		column.put("String:7", "nroCtaBcoCliente");
		column.put("String:8", "idBancoCliente");
		column.put("String:9", "idBancoPromesa");
		column.put("String:10", "observaciones");
		column.put("String:11", "fechaCreacion");
		column.put("String:12", "sincronizado");
		column.put("String:13", "anticipoSecuencia");
//		column.put("String:14", "importeIva");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_ANTICIPO_CLIENTE " + "WHERE codigoVendedor = '" + codigoVendedor + "' " + "AND sincronizado = 0 AND eliminado = 'false' ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
				SqlBancoPromesaImpl sqlBancoPromesaImpl = new SqlBancoPromesaImpl();
				listaBancoPromesa = sqlBancoPromesaImpl.obtenerListaBancoPromesa();
				SqlFormaPagoAnticipoImpl sqlFormaPagoAnticipoImpl = new SqlFormaPagoAnticipoImpl();
				listaFormaPagoAnticipo = sqlFormaPagoAnticipoImpl.obtenerListaFormaPagoAnticipo();
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					AnticipoCliente anticipoCliente = new AnticipoCliente();
					anticipoCliente.setId(Integer.parseInt(res.get("id").toString()));
					anticipoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					anticipoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					anticipoCliente.setNombreCompletoCliente(res.get("nombreCompletoCliente").toString());
					anticipoCliente.setIdFormaPago(res.get("idFormaPago").toString());
					anticipoCliente.setReferencia(res.get("referencia").toString());
					anticipoCliente.setImporte(res.get("importe").toString());
//					anticipoCliente.setImporteIva(res.get("importeIva").toString());
					anticipoCliente.setNroCtaBcoCliente(res.get("nroCtaBcoCliente").toString());
					anticipoCliente.setIdBancoCliente(res.get("idBancoCliente").toString());
					anticipoCliente.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					anticipoCliente.setObservaciones(res.get("observaciones").toString());
					anticipoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					anticipoCliente.setSincronizado(res.get("sincronizado").toString());
					anticipoCliente.setAnticipoSecuencia(res.get("anticipoSecuencia").toString());
					anticipoCliente.setDescripcionBancoCliente("");
					anticipoCliente.setDescripcionBancoPromesa("");
					anticipoCliente.setDescripcionFormaPago("");
					listaBancoCliente = sqlBancoClienteImpl.obtenerListaBancoCliente(res.get("codigoCliente").toString());
					for(BancoCliente bancoCliente : listaBancoCliente){
						if(bancoCliente.getNroCtaBcoCliente().endsWith(anticipoCliente.getNroCtaBcoCliente()) && bancoCliente.getIdBancoCliente().equals(anticipoCliente.getIdBancoCliente())){
							anticipoCliente.setDescripcionBancoCliente(bancoCliente.getDescripcionBancoCliente());
							break;
						}
					}
					for(BancoPromesa bancoPromesa : listaBancoPromesa){
						if(bancoPromesa.getIdBancoPromesa().equals(anticipoCliente.getIdBancoPromesa())){
							anticipoCliente.setDescripcionBancoPromesa(bancoPromesa.getDescripcionBancoPromesa());
							break;
						}
					}
					for(FormaPago formaPagoAnticipo : listaFormaPagoAnticipo){
						if(formaPagoAnticipo.getIdFormaPago().equals(anticipoCliente.getIdFormaPago())){
							anticipoCliente.setDescripcionFormaPago(formaPagoAnticipo.getDescripcionFormaPago());
							break;
						}
					}
					listaAnticipoCliente.add(anticipoCliente);
				}
			}
		}
		return listaAnticipoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerUltimoIdAnticipoCliente(){
		String id = "";
		column = new HashMap();
		column.put("String:0", "id");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT MAX(id) as id FROM PROFFLINE_TB_ANTICIPO_CLIENTE LIMIT 1;";
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
	public List<AnticipoCliente> obtenerAnticiposClientesSinSincronizar(){
		List<AnticipoCliente> listaAnticipoCliente = new ArrayList<AnticipoCliente>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "nombreCompletoCliente");
		column.put("String:4", "idFormaPago");
		column.put("String:5", "referencia");
		column.put("String:6", "importe");
		column.put("String:7", "nroCtaBcoCliente");
		column.put("String:8", "idBancoCliente");
		column.put("String:9", "idBancoPromesa");
		column.put("String:10", "observaciones");
		column.put("String:11", "fechaCreacion");
		column.put("String:12", "sincronizado");
		column.put("String:13", "anticipoSecuencia");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_ANTICIPO_CLIENTE WHERE sincronizado = 0 " + "ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				@SuppressWarnings("unused")
				SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
				SqlBancoPromesaImpl sqlBancoPromesaImpl = new SqlBancoPromesaImpl();
				listaBancoPromesa = sqlBancoPromesaImpl.obtenerListaBancoPromesa();
				SqlFormaPagoAnticipoImpl sqlFormaPagoAnticipoImpl = new SqlFormaPagoAnticipoImpl();
				listaFormaPagoAnticipo = sqlFormaPagoAnticipoImpl.obtenerListaFormaPagoAnticipo();
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					AnticipoCliente anticipoCliente = new AnticipoCliente();
					anticipoCliente.setId(Integer.parseInt(res.get("id").toString()));
					anticipoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					anticipoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					anticipoCliente.setNombreCompletoCliente(res.get("nombreCompletoCliente").toString());
					anticipoCliente.setIdFormaPago(res.get("idFormaPago").toString());
					anticipoCliente.setReferencia(res.get("referencia").toString());
					anticipoCliente.setImporte(res.get("importe").toString());
					anticipoCliente.setNroCtaBcoCliente(res.get("nroCtaBcoCliente").toString());
					anticipoCliente.setIdBancoCliente(res.get("idBancoCliente").toString());
					anticipoCliente.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					anticipoCliente.setObservaciones(res.get("observaciones").toString());
					anticipoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					anticipoCliente.setSincronizado(res.get("sincronizado").toString());
					anticipoCliente.setAnticipoSecuencia(res.get("anticipoSecuencia").toString());
					anticipoCliente.setDescripcionBancoCliente("");
					anticipoCliente.setDescripcionBancoPromesa("");
					anticipoCliente.setDescripcionFormaPago("");
					listaAnticipoCliente.add(anticipoCliente);
				}
			}
		}
		return listaAnticipoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AnticipoCliente> getAnticiposElimindosNoSincronizados(){
		List<AnticipoCliente> listaAnticipoCliente = new ArrayList<AnticipoCliente>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
//		column.put("String:3", "nombreCompletoCliente");
		column.put("String:4", "idFormaPago");
		column.put("String:5", "referencia");
		column.put("String:6", "importe");
		column.put("String:7", "nroCtaBcoCliente");
		column.put("String:8", "idBancoCliente");
		column.put("String:9", "idBancoPromesa");
		column.put("String:10", "observaciones");
		column.put("String:11", "fechaCreacion");
		column.put("String:12", "sincronizado");
		column.put("String:13", "anticipoSecuencia");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_ANTICIPO_CLIENTE WHERE sincronizado = 0 AND eliminado='true' ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				
				SqlCliente sqlCliente =  new SqlClienteImpl();
				SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
				SqlBancoPromesaImpl sqlBancoPromesaImpl = new SqlBancoPromesaImpl();
				listaBancoPromesa = sqlBancoPromesaImpl.obtenerListaBancoPromesa();
				
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					AnticipoCliente anticipoCliente = new AnticipoCliente();
					anticipoCliente.setId(Integer.parseInt(res.get("id").toString()));
					anticipoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					anticipoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
//					anticipoCliente.setNombreCompletoCliente(res.get("nombreCompletoCliente").toString());
					anticipoCliente.setIdFormaPago(res.get("idFormaPago").toString());
					anticipoCliente.setReferencia(res.get("referencia").toString());
					anticipoCliente.setImporte(res.get("importe").toString());
					anticipoCliente.setNroCtaBcoCliente(res.get("nroCtaBcoCliente").toString());
					anticipoCliente.setIdBancoCliente(res.get("idBancoCliente").toString());
					anticipoCliente.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					anticipoCliente.setObservaciones(res.get("observaciones").toString());
					anticipoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					anticipoCliente.setSincronizado(res.get("sincronizado").toString());
					anticipoCliente.setAnticipoSecuencia(res.get("anticipoSecuencia").toString());
					anticipoCliente.setDescripcionBancoCliente("");
					anticipoCliente.setDescripcionBancoPromesa("");
					anticipoCliente.setDescripcionFormaPago("");
					
					String codCliente = res.get("codigoCliente").toString();
					anticipoCliente.setNombreCompletoCliente(sqlCliente.obtenerCliente(codCliente).getStrNombreCliente());
					
					
					listaBancoCliente = sqlBancoClienteImpl.obtenerListaBancoCliente(res.get("codigoCliente").toString());
					for(BancoCliente bancoCliente : listaBancoCliente){
						if(bancoCliente.getNroCtaBcoCliente().endsWith(anticipoCliente.getNroCtaBcoCliente()) && bancoCliente.getIdBancoCliente().equals(anticipoCliente.getIdBancoCliente())){
							anticipoCliente.setDescripcionBancoCliente(bancoCliente.getDescripcionBancoCliente());
							break;
						}
					}
					for(BancoPromesa bancoPromesa : listaBancoPromesa){
						if(bancoPromesa.getIdBancoPromesa().equals(anticipoCliente.getIdBancoPromesa())){
							anticipoCliente.setDescripcionBancoPromesa(bancoPromesa.getDescripcionBancoPromesa());
							break;
						}
					}
					listaAnticipoCliente.add(anticipoCliente);
				}
			}
		}
		return listaAnticipoCliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AnticipoCliente> getAnticiposElimindosSincronizados(){
		List<AnticipoCliente> listaAnticipoCliente = new ArrayList<AnticipoCliente>();
		column = new HashMap();
		column.put("String:0", "id");
		column.put("String:1", "codigoCliente");
		column.put("String:2", "codigoVendedor");
		column.put("String:3", "nombreCompletoCliente");
		column.put("String:4", "idFormaPago");
		column.put("String:5", "referencia");
		column.put("String:6", "importe");
		column.put("String:7", "nroCtaBcoCliente");
		column.put("String:8", "idBancoCliente");
		column.put("String:9", "idBancoPromesa");
		column.put("String:10", "observaciones");
		column.put("String:11", "fechaCreacion");
		column.put("String:12", "sincronizado");
		column.put("String:13", "anticipoSecuencia");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_ANTICIPO_CLIENTE WHERE sincronizado = 1 AND eliminado='true' ORDER BY codigoCliente;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_TX);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				
				for(int i = 0; i < mapResultado.size(); i++){
					HashMap res = (HashMap) mapResultado.get(i);
					AnticipoCliente anticipoCliente = new AnticipoCliente();
					anticipoCliente.setId(Integer.parseInt(res.get("id").toString()));
					anticipoCliente.setCodigoCliente(res.get("codigoCliente").toString());
					anticipoCliente.setCodigoVendedor(res.get("codigoVendedor").toString());
					anticipoCliente.setNombreCompletoCliente(res.get("nombreCompletoCliente").toString());
					anticipoCliente.setIdFormaPago(res.get("idFormaPago").toString());
					anticipoCliente.setReferencia(res.get("referencia").toString());
					anticipoCliente.setImporte(res.get("importe").toString());
					anticipoCliente.setNroCtaBcoCliente(res.get("nroCtaBcoCliente").toString());
					anticipoCliente.setIdBancoCliente(res.get("idBancoCliente").toString());
					anticipoCliente.setIdBancoPromesa(res.get("idBancoPromesa").toString());
					anticipoCliente.setObservaciones(res.get("observaciones").toString());
					anticipoCliente.setFechaCreacion(res.get("fechaCreacion").toString());
					anticipoCliente.setSincronizado(res.get("sincronizado").toString());
					anticipoCliente.setAnticipoSecuencia(res.get("anticipoSecuencia").toString());
					anticipoCliente.setDescripcionBancoCliente("");
					anticipoCliente.setDescripcionBancoPromesa("");
					anticipoCliente.setDescripcionFormaPago("");
					
					listaAnticipoCliente.add(anticipoCliente);
				}
			}
		}
		return listaAnticipoCliente;
	}
	
	
	
}