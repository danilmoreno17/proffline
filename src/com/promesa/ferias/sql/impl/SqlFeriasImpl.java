package com.promesa.ferias.sql.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.ferias.Asistencia;
import com.promesa.ferias.BeanGremioEInstitutoEducativo;
import com.promesa.ferias.BeanProfesion;
import com.promesa.ferias.Cliente;
import com.promesa.ferias.LugarFeria;
import com.promesa.ferias.Relacion;
import com.promesa.ferias.sql.SqlFerias;
import com.promesa.internalFrame.ferias.IFerias;
import com.promesa.main.Promesa;
import com.promesa.util.Constante;
import com.promesa.util.Util;


public class SqlFeriasImpl implements SqlFerias {
	
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;

	public void registrarCliente(Cliente cliente) throws Exception {
		
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlCliente = "INSERT INTO PROFFLINE_TB_FERIAS_CLIENTE" +
				"(CODIGOCLIENTE, NOMBRECLIENTE, RAZONSOCIAL, RUC, TELEFONO, CELULAR," +
				"OTROTELEFONO, CIUDAD, CORREO) VALUES('" + cliente.getCodigoCliente()
				+ "', '" + cliente.getNombreCliente() 	+ "', '" + cliente.getRazonSocial()
				+ "', '" + cliente.getRUC() 			+ "', '" + cliente.getTelefono()
				+ "', '" + cliente.getCelular() 		+ "', '" + cliente.getOtroTelefono()
				+ "', '" + cliente.getCiudad() 			+ "', '" + cliente.getCorreo() + "');";
		stmt = connection.createStatement();
		stmt.execute(sqlCliente);
		
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void guardarClientes(IFerias instancia, List<Cliente> listaClientes) throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sqlCliente = null;
		double total = (double)listaClientes.size();
		int i = 1;
		stmt = connection.createStatement();
		for(Cliente cliente : listaClientes){
			String porcentaje = Util.formatearNumero((i * 100) / total);
			instancia.registrarMensaje("Clientes: " + porcentaje + "%.");
			sqlCliente = "INSERT INTO PROFFLINE_TB_FERIAS_CLIENTE " +
						"(CODIGOCLIENTE, NOMBRECLIENTE, RAZONSOCIAL, RUC, TELEFONO, CELULAR," +
						"OTROTELEFONO, CIUDAD, CORREO) VALUES('" + cliente.getCodigoCliente()
						+ "', '" + cliente.getNombreCliente() 	+ "', '" + cliente.getRazonSocial().replace("'", " ")
						+ "', '" + cliente.getRUC() 			+ "', '" + cliente.getTelefono()
						+ "', '" + cliente.getCelular() 		+ "', '" + cliente.getOtroTelefono()
						+ "', '" + cliente.getCiudad() 			+ "', '" + cliente.getCorreo() + "');";
			stmt.execute(sqlCliente);
			i++;
		}
		instancia.registrarMensaje("");
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void guardarClientes2(IFerias instancia, List<Cliente> listaClientes) throws Exception {
		String sqlRelacion = null;
		List<String> sql = new ArrayList<String>();
		for(Cliente cliente : listaClientes){
			sqlRelacion = "INSERT INTO PROFFLINE_TB_FERIAS_CLIENTE " +
					"(CODIGOCLIENTE, NOMBRECLIENTE, RAZONSOCIAL, RUC, TELEFONO, CELULAR," +
					"OTROTELEFONO, CIUDAD, CORREO) VALUES('" + cliente.getCodigoCliente()
					+ "', '" + cliente.getNombreCliente()
					+ "', '" + cliente.getRazonSocial()
					+ "', '" + cliente.getRUC()
					+ "', '" + cliente.getTelefono()
					+ "', '" + cliente.getCelular()
					+ "', '" + cliente.getOtroTelefono()
					+ "', '" + cliente.getCiudad()
					+ "', '" + cliente.getCorreo() + "');";
			sql.add(sqlRelacion);
		}
		instancia.registrarMensaje("");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaFerias(instancia, sql, "Clientes Ferias",Constante.BD_FR, "Clientes: ");
		Promesa.getInstance().mostrarAvisoSincronizacion("");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cliente buscarCliente(String codigoCliente) {
		Cliente cliente = null;
		column = new HashMap();
		column.put("String:0", "nombreCliente");
		column.put("String:1", "razonSocial");
		column.put("String:2", "RUC");
		column.put("String:3", "telefono");
		column.put("String:4", "celular");
		column.put("String:5", "otroTelefono");
		column.put("String:6", "ciudad");
		column.put("String:7", "ciudad");
		column.put("String:8", "correo");
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlCliente = "SELECT * FROM PROFFLINE_TB_FERIAS_CLIENTE WHERE CODIGOCLIENTE = '" + codigoCliente +"';";
		resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				cliente = new Cliente();
				HashMap res = (HashMap) mapResultado.get(0);
				cliente.setNombreCliente(res.get("nombreCliente").toString());
				cliente.setRazonSocial(res.get("razonSocial").toString().trim().replace("'", ""));
				cliente.setRUC(res.get("RUC").toString());
				cliente.setTelefono(res.get("telefono").toString());
				cliente.setCelular(res.get("celular").toString());
				cliente.setOtroTelefono(res.get("otroTelefono").toString());
				cliente.setCiudad(res.get("ciudad").toString());
				cliente.setCorreo(res.get("correo").toString());
			}
		}
		return cliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String buscarGremioInstitucionEducativa(String strCodigoGremioInst){
		String strNombreGremioinst = null;
		column = new HashMap();
		column.put("String:0", "nombreGremio");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlCliente = "SELECT NOMBREGREMIO FROM PROFFLINE_TB_FERIAS_GREMIO_INST WHERE CODIGOGREMIO = '" + strCodigoGremioInst +"';";
		resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				try{
					strNombreGremioinst = res.get("nombreGremio").toString();
				}catch (Exception ex) {
					strNombreGremioinst = "";
					Util.mostrarExcepcion(ex);
				}
			}
		}
		return strNombreGremioinst;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerUltimoRegistroAsistencia(){
		String id = "";
		column = new HashMap();
		column.put("String:0", "id");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT MAX(id) as id FROM PROFFLINE_TB_FERIAS_ASISTENCIA LIMIT 1;";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				try{
					id = res.get("id").toString();
				}catch(Exception ex){
					id = "";
				}
			}
		}
		return id;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerCodigoGremioInstitucionEducativa(String strNombreGremio){
		String id = "";
		column = new HashMap();
		column.put("String:0", "codigoGremio");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_FERIAS_GREMIO_INST WHERE NOMBREGREMIO = '" + strNombreGremio +"';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				try{
					id = res.get("codigoGremio").toString();
				}catch(Exception ex){
					id = "";
				}
			}
		}
		return id;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerNombreProfesion(String strCodigoProfesion){
		String strNombreGremioinst = null;
		column = new HashMap();
		column.put("String:0", "nombreProfesion");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlCliente = "SELECT * FROM PROFFLINE_TB_FERIAS_PROFESION WHERE CODIGOPROFESION = '" + strCodigoProfesion +"';";
		resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				try{
					strNombreGremioinst = res.get("nombreProfesion").toString();
				}catch (Exception ex) {
					strNombreGremioinst = "";
					Util.mostrarExcepcion(ex);
				}
			}
		}
		return strNombreGremioinst;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerCodigoProfesion(String strNombreProfesion){
		String id = "";
		column = new HashMap();
		column.put("String:0", "codigoProfesion");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT * FROM PROFFLINE_TB_FERIAS_PROFESION WHERE NOMBREPROFESION = '" + strNombreProfesion +"';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				HashMap res = (HashMap) mapResultado.get(0);
				try{
					id = res.get("codigoProfesion").toString();
				}catch(Exception ex){
					id = "";
				}
			}
		}
		return id;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Cliente> obtenerClientes() {
		List<Cliente> listaClientes = null;
		Cliente cliente = null;
		column = new HashMap();
		column.put("String:0", "codigoCliente");
		column.put("String:1", "nombreCliente");
		column.put("String:2", "razonSocial");
		column.put("String:3", "RUC");
		column.put("String:4", "telefono");
		column.put("String:5", "celular");
		column.put("String:6", "otroTelefono");
		column.put("String:7", "ciudad");
		column.put("String:8", "ciudad");
		column.put("String:9", "correo");
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlCliente = "SELECT * FROM PROFFLINE_TB_FERIAS_CLIENTE";
		
		resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				listaClientes = new ArrayList<Cliente>();
				for(int i = 0; i < mapResultado.size(); i++){
					cliente = new Cliente();
					HashMap res = (HashMap) mapResultado.get(i);
					cliente.setCodigoCliente(res.get("codigoCliente").toString());
					cliente.setNombreCliente(res.get("nombreCliente").toString());
					cliente.setRazonSocial(res.get("razonSocial").toString());
					cliente.setRUC(res.get("RUC").toString());
					cliente.setTelefono(res.get("telefono").toString());
					cliente.setCelular(res.get("celular").toString());
					cliente.setOtroTelefono(res.get("otroTelefono").toString());
					cliente.setCiudad(res.get("ciudad").toString());
					cliente.setCorreo(res.get("correo").toString());
					
					listaClientes.add(cliente);
				}
			}
		}
		return listaClientes;
	}
	
	public void registrarAsistencia(Asistencia a) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlAsistencia = "INSERT INTO PROFFLINE_TB_FERIAS_ASISTENCIA"
								+ "(CODIGOCLIENTE, razonSocial, RELACION, CIUDADCLIENTE, INVITADO, CORREO,"
								+ " FACEBOOK, TWITTER, TELEFONO, CELULAR, OTROTELEFONO, INTERECESESPECIALES,"
								+ "OBSERVACION, EJERCICIO, SECUENCIA, CIUDADFERIA, IDUSUARIO, HORAASISTENCIA,"
								+ "FECHAASISTENCIA, source, SINCRONIZACION, GREMIOINST, PROFESION) VALUES ('"
								+ a.getCodigoCliente() 	 + "', '" + a.getRazonSocial()		+ "', '" 
								+ a.getRelacion() 		 + "', '" + a.getCiudadCliente()		+ "', '" 
								+ a.getInvitado()	 	 + "', '" + a.getCorreo() 			+ "', '" 
								+ a.getFacebook() 		 + "', '" + a.getTwitter()			+ "', '" 
								+ a.getTelefono() 		 + "', '" + a.getCelular()		 	+ "', '" 
								+ a.getOtroTelefono()	 + "', '" + a.getInteresEspeciales()+ "', '"
								+ a.getObservacion()	 + "', '" + a.getEjercicio()		+ "', '"
								+ a.getSecuencia()		 + "', '" + a.getCiudadFeria()		+ "', '"
								+ a.getIdUsuario()		 + "', '" + a.getHoraAsistencia()	+ "', '"
								+ a.getFechaAsistencia() + "', '" + Constante.FROM_DB 		+ "', '0', '"
								+ a.getGremio() 		 + "', '" + a.getProfesion()        + "' );";
						
		stmt = connection.createStatement();
		stmt.execute(sqlAsistencia);
		
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	// METODO QUE ACTUALIZA UNA ASISTENCIA
	public void actualizarAsistencia(Asistencia a) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sqlAsistencia  = "UPDATE PROFFLINE_TB_FERIAS_ASISTENCIA SET "
							+ "EJERCICIO = '" 			+ a.getEjercicio() 			+ "', " 
							+ "SECUENCIA = '" 			+ a.getSecuencia() 			+ "', "
							+ "CIUDADFERIA = '"	 		+ a.getCiudadFeria() 		+ "', "
							+ "RELACION = '" 			+ a.getRelacion() 			+ "', "
							+ "CODIGOCLIENTE = '" 		+ a.getCodigoCliente() 		+ "', "
							+ "RAZONSOCIAL = '" 		+ a.getRazonSocial() 		+ "', "
							+ "CIUDADCLIENTE = '" 		+ a.getCiudadCliente() 		+ "', "
							+ "INVITADO = '" 			+ a.getInvitado() 			+ "', "
							+ "CORREO = '" 				+ a.getCorreo() 			+ "', "
							+ "FACEBOOK = '" 			+ a.getFacebook() 			+ "', "
							+ "TWITTER = '" 			+ a.getTwitter() 			+ "', "
							+ "TELEFONO = '" 			+ a.getTelefono() 			+ "', "
							+ "CELULAR = '" 			+ a.getCelular() 			+ "', "
							+ "OTROTELEFONO = '" 		+ a.getOtroTelefono() 		+ "', "
							+ "INTERECESESPECIALES = '" + a.getInteresEspeciales() 	+ "', "
							+ "OBSERVACION = '" 		+ a.getObservacion() 		+ "', "
							+ "FECHAASISTENCIA = '" 	+ a.getFechaAsistencia() 	+ "', "
							+ "HORAASISTENCIA = '" 		+ a.getHoraAsistencia() 	+ "', "
							+ "IDUSUARIO = '" 			+ a.getIdUsuario() 			+ "', "
							+ "SOURCE = '" 				+ a.getSource() 			+ "', "
							+ "GREMIOINST = '" 			+ a.getGremio() 			+ "', "
							+ "PROFESION = '" 			+ a.getProfesion() 			+ "' "
							+ "WHERE id = "  			+ a.getId()					+ ";";
		
		stmt = connection.createStatement();
		stmt.execute(sqlAsistencia);
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	
	public void asistenciasSincronizadas(List<Asistencia> listaAsistencia) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		for(Asistencia asiste : listaAsistencia){
			String sqlAsistencia  = "UPDATE PROFFLINE_TB_FERIAS_ASISTENCIA SET SINCRONIZACION = '1' " + "WHERE id = '"  + asiste.getId()	+ "';";
			stmt = connection.createStatement();
			stmt.execute(sqlAsistencia);
		}
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Asistencia> obtenerAsistencias(String strFechaDesde, String strFechaHasta) {
		List<Asistencia> listaAsistencia = new ArrayList<Asistencia>();
		Asistencia asistente = null;
		column = new HashMap();
		column.put("String:0", "ejercicio");
		column.put("String:1", "secuencia");
		column.put("String:2", "ciudadFeria");
		column.put("String:3", "relacion");
		column.put("String:4", "codigoCliente");
		column.put("String:5", "razonSocial");
		column.put("String:6", "ciudadCliente");
		column.put("String:7", "invitado");
		column.put("String:8", "correo");
		column.put("String:9", "facebook");
		column.put("String:10", "twitter");
		column.put("String:11", "telefono");
		column.put("String:12", "celular");
		column.put("String:13", "otroTelefono");
		column.put("String:14", "interecesespeciales");
		column.put("String:15", "observacion");
		column.put("String:16", "fechaAsistencia");
		column.put("String:17", "horaAsistencia");
		column.put("String:18", "idusuario");
		column.put("String:19", "source");
		column.put("String:20", "id");
		column.put("String:21", "sincronizacion");
		column.put("String:22", "gremioInst");
		column.put("String:23", "profesion");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlCliente = "SELECT * FROM PROFFLINE_TB_FERIAS_ASISTENCIA WHERE FECHAASISTENCIA BETWEEN '"+strFechaDesde+"' AND '"+strFechaHasta+"' AND SINCRONIZACION = '0'";
		try{
			resultExecuteQuery = new ResultExecuteQuery(sqlCliente, column, Constante.BD_FR);
			if(resultExecuteQuery != null){
				mapResultado = resultExecuteQuery.getMap();
				if(mapResultado.size() > 0){
					for(int i = 0; i < mapResultado.size(); i++){
						asistente = new Asistencia();
						HashMap res = (HashMap) mapResultado.get(i);			
						asistente.setEjercicio(res.get("ejercicio").toString());
						asistente.setSecuencia(res.get("secuencia").toString());
						asistente.setCiudadFeria(res.get("ciudadFeria").toString());
						asistente.setRelacion(res.get("relacion").toString());
						asistente.setCodigoCliente(res.get("codigoCliente").toString());
						asistente.setRazonSocial(res.get("razonSocial").toString());
						asistente.setCiudadCliente(res.get("ciudadCliente").toString());
						asistente.setInvitado(res.get("invitado").toString());
						asistente.setCorreo(res.get("correo").toString());
						asistente.setFacebook(res.get("facebook").toString());
						asistente.setTwitter(res.get("twitter").toString());
						asistente.setTelefono(res.get("telefono").toString());
						asistente.setCelular(res.get("celular").toString());
						asistente.setOtroTelefono(res.get("otroTelefono").toString());
						asistente.setInteresEspeciales(res.get("interecesespeciales").toString());
						asistente.setObservacion(res.get("observacion").toString());
						asistente.setFechaAsistencia(res.get("fechaAsistencia").toString());
						asistente.setHoraAsistencia(res.get("horaAsistencia").toString());
						asistente.setIdUsuario(res.get("idusuario").toString());
						asistente.setSource(res.get("source").toString());
						asistente.setId(res.get("id").toString());
						asistente.setSincronizacion(res.get("sincronizacion").toString());
						asistente.setGremio(res.get("gremioInst").toString());
						asistente.setProfesion(res.get("profesion").toString());
						
						listaAsistencia.add(asistente);
					}
				}
			}
		}catch(Exception ex){
			return listaAsistencia;
		}
		return listaAsistencia;
	}
	
	public void guardarLugarFeria(IFerias instancia, List<LugarFeria> listaFerias) throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		double total =  (double)listaFerias.size();
		int i = 1;
		stmt = connection.createStatement();
		for(LugarFeria lf : listaFerias){
			String porcentaje = Util.formatearNumero((i * 100) / total);
			instancia.registrarMensaje("Lugares de ferias: " + porcentaje + "%.");
			String sqlFeria = "INSERT INTO PROFFLINE_TB_FERIAS_LUGAR" + "(codigoCiudad, nombreCiudad) VALUES('" + lf.getCodigoCiudad()
								+ "', '" + lf.getNombreCiudad() + "');";
			stmt.execute(sqlFeria);
			i++;
		}
		instancia.registrarMensaje("");
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void guardarLugarFeria2(IFerias instancia, List<LugarFeria> listaFerias) throws Exception {
		List<String> sql = new ArrayList<String>();
		for(LugarFeria lf : listaFerias){
			
			String sqlFeria = "INSERT INTO PROFFLINE_TB_FERIAS_LUGAR" +
					"(codigoCiudad, nombreCiudad) VALUES('" + lf.getCodigoCiudad() + "', '" + lf.getNombreCiudad() + "');";
			sql.add(sqlFeria);
		}
		instancia.registrarMensaje("");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaFerias(instancia, sql, "Lugar Ferias",Constante.BD_FR, "Lugares Ferias: ");
		Promesa.getInstance().mostrarAvisoSincronizacion("");
	}
	
	public void guardarRelacion(IFerias instancia, List<Relacion> listaRelaciones) throws Exception{
		String sqlRelacion = null;
		List<String> sql = new ArrayList<String>();
		for(Relacion r : listaRelaciones){
			sqlRelacion = "INSERT INTO PROFFLINE_TB_FERIAS_RELACION" + "(codigoRelacion, tipoRelacion) VALUES('" + r.getCodigoRelacion() + "', '" + r.getTipoRelacion() + "');";
			sql.add(sqlRelacion);
		}
		instancia.registrarMensaje("");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaFerias(instancia, sql, "Relación Ferias",Constante.BD_FR, "Relación: ");
		Promesa.getInstance().mostrarAvisoSincronizacion("");
	}
	
	public void guardarProfesion(IFerias instancia, List<BeanProfesion> listaProfesion) throws Exception {
		List<String> listaSql = new ArrayList<String>();
		double total = (double)listaProfesion.size();
		int i = 1;
		for(BeanProfesion bp : listaProfesion){
			String porcentaje = Util.formatearNumero((i * 100) / total);
			instancia.registrarMensaje("Profesión: " + porcentaje + "%.");
			String sql = "INSERT INTO PROFFLINE_TB_FERIAS_PROFESION (CODIGOPROFESION, NOMBREPROFESION) VALUES('"
						+ bp.getCodigoProfesion() + "', '" + bp.getNombreProfesion() + "');";
			listaSql.add(sql);
		}
		instancia.registrarMensaje("");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaFerias(instancia, listaSql, "Profesión Ferias",Constante.BD_FR, "Profesión: ");
		Promesa.getInstance().mostrarAvisoSincronizacion("");
	}
	
	public void guardarGremioEInstitucionEducativa(IFerias instancia, List<BeanGremioEInstitutoEducativo> listaGremioInst) throws Exception {
		List<String> listaSql = new ArrayList<String>();
		double total = (double)listaGremioInst.size();
		int i = 1;
		for(BeanGremioEInstitutoEducativo bGrie : listaGremioInst){
			String porcentaje = Util.formatearNumero((i * 100) / total);
			instancia.registrarMensaje("Gremio e Intitución Educativa: " + porcentaje + "%.");
			String Sql = "INSERT INTO PROFFLINE_TB_FERIAS_GREMIO_INST (CODIGOGREMIO, NOMBREGREMIO) VALUES ('" 
						+ bGrie.getCodigoGremioInst() + "', '" + bGrie.getNombreGremioInst() + "');";
			listaSql.add(Sql);
		}
		instancia.registrarMensaje("");
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaFerias(instancia, listaSql, "Gremio e Institución Educativa Ferias",Constante.BD_FR, "Gremio e Intitución Educativa: ");
		Promesa.getInstance().mostrarAvisoSincronizacion("");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LugarFeria> obtenerLugares() {
		List<LugarFeria> lugar = null;
		LugarFeria lf = null;
		column = new HashMap();
		column.put("String:0", "codigoCiudad");
		column.put("String:1", "nombreCiudad");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlFerias = "SELECT * FROM PROFFLINE_TB_FERIAS_LUGAR ORDER BY NOMBRECIUDAD;" ;
		
		resultExecuteQuery = new ResultExecuteQuery(sqlFerias, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				lugar = new ArrayList<LugarFeria>();
				for(int i = 0; i < mapResultado.size(); i++){
					lf = new LugarFeria();
					HashMap res = (HashMap) mapResultado.get(i);
					lf.setCodigoCiudad(res.get("codigoCiudad").toString());
					lf.setNombreCiudad(res.get("nombreCiudad").toString());
					lugar.add(lf);
				}
			}
		}
		return lugar;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerCodigoCiudad(String nombreCiudad) {
		String codigoCiudad = null;
		column = new HashMap();
		column.put("String:0", "codigoCiudad");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlFerias = "SELECT * FROM PROFFLINE_TB_FERIAS_LUGAR WHERE NOMBRECIUDAD = '" + nombreCiudad + "';" ;
		
		resultExecuteQuery = new ResultExecuteQuery(sqlFerias, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
					HashMap res = (HashMap) mapResultado.get(0);
					codigoCiudad = res.get("codigoCiudad").toString();
			}
		}
		return codigoCiudad;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerCodigoRelacion(String nombreRelacion){
		String codigoRelacion = null;
		column = new HashMap();
		column.put("String:0", "codigoRelacion");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlFerias = "SELECT * FROM PROFFLINE_TB_FERIAS_RELACION WHERE TIPORELACION = '" + nombreRelacion + "';" ;
		resultExecuteQuery = new ResultExecuteQuery(sqlFerias, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
					HashMap res = (HashMap) mapResultado.get(0);
					codigoRelacion = res.get("codigoRelacion").toString();
			}
		}
		return codigoRelacion;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerTipoRelacion(String codigoRelacion){
		String strTipoRelacion = null;
		column = new HashMap();
		column.put("String:0", "tipoRelacion");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlFerias = "SELECT * FROM PROFFLINE_TB_FERIAS_RELACION WHERE CODIGORELACION = '" + codigoRelacion + "';" ;
		
		resultExecuteQuery = new ResultExecuteQuery(sqlFerias, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
					HashMap res = (HashMap) mapResultado.get(0);
					strTipoRelacion = res.get("tipoRelacion").toString();
			}
		}
		return strTipoRelacion;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String obtenerNombreCiudad(String codigoCiudad) {
		String nombreCiudad = null;
		column = new HashMap();
		column.put("String:0", "nombreCiudad");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlFerias = "SELECT * FROM PROFFLINE_TB_FERIAS_LUGAR WHERE CODIGOCIUDAD = '" + codigoCiudad + "';" ;
		
		resultExecuteQuery = new ResultExecuteQuery(sqlFerias, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
					HashMap res = (HashMap) mapResultado.get(0);
					nombreCiudad = res.get("nombreCiudad").toString();
			}
		}
		return nombreCiudad;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Relacion> obtenerRelacion() {
		List<Relacion> relacion = null;
		Relacion r = null;
		column = new HashMap();
		column.put("String:0", "codigoRelacion");
		column.put("String:1", "tipoRelacion");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlRelacion = "SELECT * FROM PROFFLINE_TB_FERIAS_RELACION ;" ;
		
		resultExecuteQuery = new ResultExecuteQuery(sqlRelacion, column, Constante.BD_FR);
		if(resultExecuteQuery != null){
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0){
				relacion = new ArrayList<Relacion>();
				for(int i = 0; i < mapResultado.size(); i++){
					r = new Relacion();
					HashMap res = (HashMap) mapResultado.get(i);
					r.setCodigoRelacion(res.get("codigoRelacion").toString());
					r.setTipoRelacion(res.get("tipoRelacion").toString());
					relacion.add(r);
				}
			}
		}
		return relacion;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanProfesion> obtenerProfesion(){
		List<BeanProfesion> listaProfesiones = null;
		BeanProfesion bp = null;
		column = new HashMap();
		column.put("String:0", "nombreProfesion");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlProfesion = "SELECT NOMBREPROFESION FROM PROFFLINE_TB_FERIAS_PROFESION ORDER BY NOMBREPROFESION;";
		
		resultExecuteQuery = new ResultExecuteQuery(sqlProfesion, column, Constante.BD_FR);
		if(resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0) {
				listaProfesiones = new ArrayList<BeanProfesion>();
				for(int i = 0; i < mapResultado.size(); i++) {
					bp = new BeanProfesion();
					HashMap res = (HashMap) mapResultado.get(i);
					bp.setNombreProfesion(res.get("nombreProfesion").toString());
					listaProfesiones.add(bp);
				}
			}
		}
		return listaProfesiones;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BeanGremioEInstitutoEducativo> obtenerGremioEInstitucionEducativa(){
		List<BeanGremioEInstitutoEducativo> listaProfesiones = null;
		BeanGremioEInstitutoEducativo bGremio = null;
		column = new HashMap();
		column.put("String:0", "nombreGremio");
		
		ResultExecuteQuery resultExecuteQuery = null;
		String sqlProfesion = "SELECT NOMBREGREMIO FROM PROFFLINE_TB_FERIAS_GREMIO_INST ORDER BY NOMBREGREMIO;";
		
		resultExecuteQuery = new ResultExecuteQuery(sqlProfesion, column, Constante.BD_FR);
		if(resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0) {
				listaProfesiones = new ArrayList<BeanGremioEInstitutoEducativo>();
				for(int i = 0; i < mapResultado.size(); i++) {
					bGremio = new BeanGremioEInstitutoEducativo();
					HashMap res = (HashMap) mapResultado.get(i);
					bGremio.setNombreGremioInst(res.get("nombreGremio").toString());
					listaProfesiones.add(bGremio);
				}
			}
		}
		return listaProfesiones;
	}
	
	public void eliminarTablaClienteFerias() throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlCliente = "DELETE FROM PROFFLINE_TB_FERIAS_CLIENTE;";
		stmt = connection.createStatement();
		stmt.execute(sqlCliente);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
		
	}
	
	public void eliminarTablaRelacionFerias() throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlCliente = "DELETE FROM PROFFLINE_TB_FERIAS_RELACION;";
		stmt = connection.createStatement();
		stmt.execute(sqlCliente);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void eliminarTablaLugarFerias() throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlCliente = "DELETE FROM PROFFLINE_TB_FERIAS_LUGAR ;";
		stmt = connection.createStatement();
		stmt.execute(sqlCliente);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void eliminarTablaProfesion() throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlCliente = "DELETE FROM PROFFLINE_TB_FERIAS_PROFESION ;";
		stmt = connection.createStatement();
		stmt.execute(sqlCliente);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void eliminarTablaGremioEInstitucionEducativa() throws Exception{
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_FR);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		
		String sqlCliente = "DELETE FROM PROFFLINE_TB_FERIAS_GREMIO_INST ;";
		stmt = connection.createStatement();
		stmt.execute(sqlCliente);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
}