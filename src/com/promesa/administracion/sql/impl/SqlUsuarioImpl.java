package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;

import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.bean.*;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.*;

public class  SqlUsuarioImpl implements SqlUsuario {

	private String sqlUsuario = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	@SuppressWarnings({ "unused", "rawtypes" })
	private HashMap getMap = new HashMap();
	@SuppressWarnings("unused")
	private String tipo[] = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private BeanUsuario usuario = new BeanUsuario();
	private List<BeanUsuario> listaUsuario = null;
	private boolean resultado = false;
	private ResultExecuteQuery resultExecuteQuery = null;
	private ResultExecute resultExecute = null;

	/* MÉTODO QUE PERMITE EL ACCESO DE UN USUARIO */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanUsuarioRol> accesoUsuario(String nomUsu, String claUsu) {
		List<BeanUsuarioRol> usuariorol = new ArrayList<BeanUsuarioRol>();
		BeanUsuarioRol ur = new BeanUsuarioRol();
		BeanUsuario usuario = new BeanUsuario();
		BeanRol rol = new BeanRol();
		column = new HashMap();
		column.put("String:0", IDROL);
		column.put("String:1", NOMBREROL);
		column.put("String:2", NOMBREUSUARIO);
		column.put("String:3", CLAVEUSUARIO);
		column.put("String:4", USUARIOBLOQUEADO);
		column.put("String:5", IDENTIFICACION);
		column.put("String:6", USUARIO);
		sqlUsuario = "SELECT " + "ROL." + IDROL + "," + "ROL." + NOMBREROL
				+ "," + "USU." + NOMBREUSUARIO + "," + "USU." + CLAVEUSUARIO
				+ "," + "USU." + USUARIOBLOQUEADO + "," + "USU."
				+ IDENTIFICACION + "," + "USU." + USUARIO + " FROM "
				+ TABLA_USUARIO + " USU, " + TABLA_ROL + " ROL, "
				+ TABLA_USUARIO_ROL + " USUROL " + "WHERE " + "USU."
				+ IDUSUARIO + " = USUROL." + IDUSUARIO + " AND " + "USUROL."
				+ IDROL + " = ROL." + IDROL + " AND " + NOMBREUSUARIO + " =  '"
				+ nomUsu + "' AND " + CLAVEUSUARIO + " = '" + claUsu + "'";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (mapResultado != null && !mapResultado.isEmpty()) {
				res = (HashMap) mapResultado.get(0);
				rol.setStrIdRol(res.get(IDROL).toString());
				rol.setStrNomRol(res.get(NOMBREROL).toString());
				usuario.setStrNomUsuario(res.get(NOMBREUSUARIO).toString());
				usuario.setStrClaUsuario(res.get(CLAVEUSUARIO).toString());
				usuario.setStrUsuarioBloqueado(res.get(USUARIOBLOQUEADO).toString());
				usuario.setStrIdentificacion(res.get(IDENTIFICACION).toString());
				usuario.setStrNomApe(res.get(USUARIO).toString());
				ur.setRol(rol);
				ur.setUsuario(usuario);
				usuariorol.add(ur);
				return usuariorol;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE LISTA LAS OPCIONES DE MENU DE UN USUARIO */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getVistasPorUsuarioRol(String nomUsu, String claUsu) {
		List descVista = new ArrayList();
		column = new HashMap();
		column.put("String:0", "txtDescVista");
		sqlUsuario = "SELECT V.txtDescVista FROM PROFFLINE_TB_USUARIO U INNER JOIN PROFFLINE_TB_USUARIO_ROL UR ON U.txtIdUsuario = UR.txtIdUsuario "
				+ "INNER JOIN PROFFLINE_TB_ROL R ON UR.txtIdRol = R.txtIdRol INNER JOIN PROFFLINE_TB_VISTA_ROL VR ON R.txtIdRol = VR.txtIdRol INNER JOIN PROFFLINE_TB_VISTA V ON (VR.txtNombreVista = V.txtNombreVista) "
				+ "WHERE U.txtNombreUsuario = '" + nomUsu + "' AND U.txtClaveUsuario = '" + claUsu + "'" + " order by V.rowid";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				descVista.add("&¬&¬" + res.get("txtDescVista"));
			}
			descVista.add("&¬&¬" + Constante.OPCION_SALIR);
			return descVista;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE LISTA LOS DATOS DE UN USUARIO */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaUsuario() {
		column = new HashMap();
		listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", MANDANTE);
		column.put("String:1", IDUSUARIO);
		column.put("String:2", NOMBREUSUARIO);
		column.put("String:3", CLAVEUSUARIO);
		column.put("String:4", FECHAREGISTROUSUARIO);
		column.put("String:5", FECHAULTIMOACCESOUSUARIO);
		column.put("String:6", HORAULTIMOACCESOUSUARIO);
		column.put("String:7", CAMBIOCLAVE);
		column.put("String:8", INTENTO);
		column.put("String:9", USUARIOBLOQUEADO);
		column.put("String:10", IDENTIFICACION);
		column.put("String:11", USUARIO);
		sqlUsuario = "SELECT " + MANDANTE + "," + IDUSUARIO + ","
				+ NOMBREUSUARIO + "," + CLAVEUSUARIO + ","
				+ FECHAREGISTROUSUARIO + "," + FECHAULTIMOACCESOUSUARIO + ","
				+ HORAULTIMOACCESOUSUARIO + "," + CAMBIOCLAVE + "," + INTENTO
				+ "," + USUARIOBLOQUEADO + "," + IDENTIFICACION + "," + USUARIO
				+ " " + "FROM  " + TABLA_USUARIO;
		
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			usuario = new BeanUsuario();
			res = (HashMap) mapResultado.get(i);
			usuario.setStrMandante(getData(res.get(MANDANTE)));
			usuario.setStrIdUsuario(getData(res.get(IDUSUARIO)));
			usuario.setStrNomUsuario(getData(res.get(NOMBREUSUARIO)));
			usuario.setStrClaUsuario(getData(res.get(CLAVEUSUARIO)));
			usuario.setStrFecCre(getData(res.get(FECHAREGISTROUSUARIO)));
			usuario.setStrFecUltAccSis(getData(res.get(FECHAULTIMOACCESOUSUARIO)));
			usuario.setStrHorUltAccSis(getData(res.get(HORAULTIMOACCESOUSUARIO)));
			usuario.setStrCambioClave(getData(res.get(CAMBIOCLAVE)));
			usuario.setStrIntento(getData(res.get(INTENTO)));
			usuario.setStrUsuarioBloqueado(getData(res.get(USUARIOBLOQUEADO)));
			usuario.setStrIdentificacion(getData(res.get(IDENTIFICACION)));
			usuario.setStrNomApe(getData(res.get(USUARIO)));
			listaUsuario.add(usuario);
		}
	}

	public List<BeanUsuario> getListaUsuario() {
		return listaUsuario;
	}

	/* MÉTODO QUE LISTA LOS DATOS DE UN USUARIO */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setListaUsuario(BeanUsuario usr) {
		column = new HashMap();
		listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", MANDANTE);
		column.put("String:1", IDUSUARIO);
		column.put("String:2", NOMBREUSUARIO);
		column.put("String:3", CLAVEUSUARIO);
		column.put("String:4", FECHAREGISTROUSUARIO);
		column.put("String:5", FECHAULTIMOACCESOUSUARIO);
		column.put("String:6", HORAULTIMOACCESOUSUARIO);
		column.put("String:7", CAMBIOCLAVE);
		column.put("String:8", INTENTO);
		column.put("String:9", USUARIOBLOQUEADO);
		column.put("String:10", IDENTIFICACION);
		column.put("String:11", USUARIO);
		sqlUsuario = "SELECT " + MANDANTE + "," + IDUSUARIO + ","
				+ NOMBREUSUARIO + "," + CLAVEUSUARIO + ","
				+ FECHAREGISTROUSUARIO + "," + FECHAULTIMOACCESOUSUARIO + ","
				+ HORAULTIMOACCESOUSUARIO + "," + CAMBIOCLAVE + "," + INTENTO
				+ "," + USUARIOBLOQUEADO + "," + IDENTIFICACION + "," + USUARIO
				+ " " + "FROM " + TABLA_USUARIO;
		if (usr.getStrNomUsuario() != null && usr.getStrNomUsuario().trim().length() > 0) {
			sqlUsuario += " WHERE " + NOMBREUSUARIO + " = '" + usr.getStrNomUsuario().trim() + "'";
		}
		
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			usuario = new BeanUsuario();
			res = (HashMap) mapResultado.get(i);
			usuario.setStrMandante(getData(res.get(MANDANTE)));
			usuario.setStrIdUsuario(getData(res.get(IDUSUARIO)));
			usuario.setStrNomUsuario(getData(res.get(NOMBREUSUARIO)));
			usuario.setStrClaUsuario(getData(res.get(CLAVEUSUARIO)));
			usuario.setStrFecCre(getData(res.get(FECHAREGISTROUSUARIO)));
			usuario.setStrFecUltAccSis(getData(res.get(FECHAULTIMOACCESOUSUARIO)));
			usuario.setStrHorUltAccSis(getData(res.get(HORAULTIMOACCESOUSUARIO)));
			usuario.setStrCambioClave(getData(res.get(CAMBIOCLAVE)));
			usuario.setStrIntento(getData(res.get(INTENTO)));
			usuario.setStrUsuarioBloqueado(getData(res.get(USUARIOBLOQUEADO)));
			usuario.setStrIdentificacion(getData(res.get(IDENTIFICACION)));
			usuario.setStrNomApe(getData(res.get(USUARIO)));
			listaUsuario.add(usuario);
		}
	}

	/* MÉTODO QUE INSERTA UN USUARIO */
	public void setInsertarUsuario(BeanUsuario usuario) {
		sqlUsuario = "INSERT INTO " + TABLA_USUARIO + " VALUES " + "('"
				+ usuario.getStrMandante() + "','" + usuario.getStrIdUsuario()
				+ "','" + usuario.getStrNomUsuario() + "','"
				+ usuario.getStrClaUsuario() + "','" + usuario.getStrFecCre()
				+ "','" + usuario.getStrFecUltAccSis() + "','"
				+ usuario.getStrHorUltAccSis() + "',"
				+ usuario.getStrCambioClave() + "," + usuario.getStrIntento()
				+ "," + usuario.getStrBloqueado() + ",'"
				+ usuario.getStrIdentificacion() + "','"
				+ usuario.getStrNomApe() + "','"
				+ usuario.getStrDivision() + "') ";
		ResultExecute resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getInsertarUsuario() {
		return resultado;
	}

	/* MÉTODO QUE ACTUALIZA UN USUARIO */
	public void setActualizarUsuario(BeanUsuario usuario) {
		sqlUsuario = " UPDATE  " + " PROFFLINE_TB_USUARIO  "
				+ " SET txtNombreUsuario = '" + usuario.getStrNomUsuario()
				+ "', " + " txtClaveUsuario = '" + usuario.getStrClaUsuario()
				+ "', " + " txtIdentificacion = '"
				+ usuario.getStrIdentificacion() + "' "
				+ " WHERE txtIdUsuario = '" + usuario.getStrIdUsuario() + "' ";
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getActualizarUsuario() {
		return resultado;
	}

	/* MÉTODO QUE ELIMINA UN USUARIO */
	public void setEliminarUsuario(BeanUsuario usuario) {
		sqlUsuario = " DELETE  " + " FROM " + TABLA_USUARIO + " WHERE " + IDUSUARIO + " = '" + usuario.getStrIdUsuario() + "'";
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	/* MÉTODO QUE ELIMINA UN USUARIO */
	public void setEliminarUsuario() {
		sqlUsuario = " DELETE FROM " + TABLA_USUARIO;
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarUsuario() {
		return resultado;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BeanUsuario getIdUsuario() {
		@SuppressWarnings("unused")
		int id;
		BeanUsuario usuario = new BeanUsuario();
		GenerarId generarId = new GenerarId();
		HashMap res = null;
		column = new HashMap();
		column.put("String:0", "ID");
		sqlUsuario = " SELECT SUBSTR(txtIdUsuario,5,LENGTH(txtIdUsuario)) AS ID " + " FROM  PROFFLINE_TB_USUARIO ORDER BY txtIdUsuario DESC LIMIT 1 ";
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		res = (HashMap) mapResultado.get(0);
		generarId.setIdUsuario(res.get("ID").toString());
		usuario.setStrIdUsuario(generarId.getIdUsuario().getStrIdUsuario());
		return usuario;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getIdUsuarioPorNombreUsuario(String nomUsu) {
		column = new HashMap();
		listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", "txtIdUsuario");
		sqlUsuario = "SELECT txtIdUsuario FROM  PROFFLINE_TB_USUARIO WHERE txtNombreUsuario = '" + nomUsu + "'";
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			return res.get("txtIdUsuario").toString();
		} else {
			return "";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getCodigoVendedor(String strUsuario, String strClave) {
		column = new HashMap();
		listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", "txtIdentificacion");
		sqlUsuario = "SELECT txtIdentificacion FROM  PROFFLINE_TB_USUARIO WHERE txtNombreUsuario = '" + strUsuario + "' and txtClaveUsuario = '" + strClave + "'";
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			return res.get("txtIdentificacion").toString();
		} else {
			return "";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getIdUsuariosPorNombreUsuario(String nomUsu) {
		column = new HashMap();
		listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", "txtIdUsuario");
		sqlUsuario = "SELECT txtIdUsuario FROM  PROFFLINE_TB_USUARIO WHERE txtNombreUsuario = '" + nomUsu + "'";
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			return res.get("txtIdUsuario").toString();
		} else {
			return "";
		}
	}

	public void desbloquearUsuario(String IdUsu) {
		sqlUsuario = "UPDATE  " + "PROFFLINE_TB_USUARIO " + "SET numUsuarioBloqueado = 0," + "numIntento = 0 " + "WHERE txtIdUsuario = '" + IdUsu + "'";
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setActualizarClaveUsuario(String claUsu, String fecUlAcUsu, String horUlAcUsu, String nomUsu) {
		sqlUsuario = "UPDATE " + "PROFFLINE_TB_USUARIO "
				+ "SET txtClaveUsuario = '" + claUsu + "',"
				+ "txtFechaUltimoAccesoUsuario = '" + fecUlAcUsu + "',"
				+ "txtHoraUltimoAccesoUsuario = '" + horUlAcUsu + "',"
				+ "numCambioClave = 0 " + "WHERE txtNombreUsuario = '" + nomUsu
				+ "'";
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setSincronizadoDispositivo(String IdUsu) {
		sqlUsuario = " UPDATE  " + " PROFFLINE_TB_USUARIO  " + " SET txtSincronizado = 1 " + " WHERE txtIdUsuario = '" + IdUsu + "'";
		ResultExecute resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getActualizarClaveUsuario() {
		return resultado;
	}

	public void setActualizarNumIntentoUsuario(int numIntento, String nomUsu) {
		sqlUsuario = "UPDATE " + "PROFFLINE_TB_USUARIO " + "SET numIntento = " + numIntento + " " + "WHERE txtNombreUsuario = '" + nomUsu + "'";
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getNumIntentoPorNombreUsuario(String nomUsu) {
		column = new HashMap();
		listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", "numIntento");
		sqlUsuario = "SELECT numIntento FROM PROFFLINE_TB_USUARIO WHERE txtNombreUsuario = '" + nomUsu + "'";
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			return Integer.parseInt(res.get("numIntento").toString());
		} else {
			return 0;
		}
	}

	public void bloquearUsuario(String nomUsu) {
		sqlUsuario = "UPDATE  " + "PROFFLINE_TB_USUARIO " + "SET numUsuarioBloqueado = 1 " + "WHERE txtNombreUsuario = '" + nomUsu + "'";
		resultExecute = new ResultExecute(sqlUsuario, "Usuarios", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public String getData(Object obj) {
		String result = "";
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	/* MÉTODO QUE LISTA LOS DATOS DE UN USUARIO */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BeanUsuario obtenerDatosUsuario(String strCodigoUsuario) {
		column = new HashMap();
		List<BeanUsuario> listaUsuario = new ArrayList<BeanUsuario>();
		column.put("String:0", DIVISION);
		column.put("String:1", MANDANTE);
		column.put("String:2", IDUSUARIO);
		column.put("String:3", NOMBREUSUARIO);
		column.put("String:4", CLAVEUSUARIO);
		column.put("String:5", FECHAREGISTROUSUARIO);
		column.put("String:6", FECHAULTIMOACCESOUSUARIO);
		column.put("String:7", HORAULTIMOACCESOUSUARIO);
		column.put("String:8", CAMBIOCLAVE);
		column.put("String:9", INTENTO);
		column.put("String:10", USUARIOBLOQUEADO);
		column.put("String:11", IDENTIFICACION);
		column.put("String:12", USUARIO);
		sqlUsuario = "SELECT "+ DIVISION + "," + MANDANTE + "," + IDUSUARIO + ","
				+ NOMBREUSUARIO + "," + CLAVEUSUARIO + ","
				+ FECHAREGISTROUSUARIO + "," + FECHAULTIMOACCESOUSUARIO + ","
				+ HORAULTIMOACCESOUSUARIO + "," + CAMBIOCLAVE + "," + INTENTO
				+ "," + USUARIOBLOQUEADO + "," + IDENTIFICACION + "," + USUARIO
				+ " " + "FROM " + TABLA_USUARIO;
		if (strCodigoUsuario != null && !strCodigoUsuario.equals("")) {
			sqlUsuario += " WHERE " + IDENTIFICACION + " = '" + strCodigoUsuario.trim() + "'";
		}
		resultExecuteQuery = new ResultExecuteQuery(sqlUsuario, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			usuario = new BeanUsuario();
			res = (HashMap) mapResultado.get(i);
			usuario.setStrDivision(getData(res.get(DIVISION)));
			usuario.setStrMandante(getData(res.get(MANDANTE)));
			usuario.setStrIdUsuario(getData(res.get(IDUSUARIO)));
			usuario.setStrNomUsuario(getData(res.get(NOMBREUSUARIO)));
			usuario.setStrClaUsuario(getData(res.get(CLAVEUSUARIO)));
			usuario.setStrFecCre(getData(res.get(FECHAREGISTROUSUARIO)));
			usuario.setStrFecUltAccSis(getData(res.get(FECHAULTIMOACCESOUSUARIO)));
			usuario.setStrHorUltAccSis(getData(res.get(HORAULTIMOACCESOUSUARIO)));
			usuario.setStrCambioClave(getData(res.get(CAMBIOCLAVE)));
			usuario.setStrIntento(getData(res.get(INTENTO)));
			usuario.setStrUsuarioBloqueado(getData(res.get(USUARIOBLOQUEADO)));
			usuario.setStrIdentificacion(getData(res.get(IDENTIFICACION)));
			usuario.setStrNomApe(getData(res.get(USUARIO)));
			listaUsuario.add(usuario);
		}
		return listaUsuario.get(0);
	}

	private static String TABLA_USUARIO = "PROFFLINE_TB_USUARIO";
	private static String IDUSUARIO = "txtIdUsuario";
	private static String NOMBREUSUARIO = "txtNombreUsuario";
	private static String CLAVEUSUARIO = "txtClaveUsuario";
	private static String FECHAREGISTROUSUARIO = "txtFechaRegistroUsuario";
	private static String FECHAULTIMOACCESOUSUARIO = "txtFechaUltimoAccesoUsuario";
	private static String HORAULTIMOACCESOUSUARIO = "txtHoraUltimoAccesoUsuario";
	private static String CAMBIOCLAVE = "numCambioClave";
	private static String INTENTO = "numIntento";
	private static String USUARIOBLOQUEADO = "numUsuarioBloqueado";
	private static String IDENTIFICACION = "txtIdentificacion";
	private static String USUARIO = "txtUsuario";
	private static String TABLA_USUARIO_ROL = "PROFFLINE_TB_USUARIO_ROL";
	private static String TABLA_ROL = "PROFFLINE_TB_ROL";
	private static String IDROL = "txtIdRol";
	private static String NOMBREROL = "txtNombreRol";
	private static String MANDANTE = "txtMandante";
	private static String DIVISION = "txtDivision";
}