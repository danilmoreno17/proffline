package com.promesa.administracion.sql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import util.ConexionSAP;
import com.promesa.administracion.bean.*;
import com.promesa.administracion.sql.*;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.util.*;

public class SqlMenuUsuarioImpl implements SqlMenuUsuario {

	@SuppressWarnings({ "rawtypes", "unused", "unchecked", "static-access" })
	public void filtro(String strUsuario) {
		final List<String> listaSQL = new ArrayList<String>();
		List temp = null;
		BeanMenuUsuario menuUsuario = new BeanMenuUsuario();
		String sql = "";
		String idUsuario = "";
		String idMandante = "";
		boolean resultado = false;
		String sqlTemp = "";
		FILTRO_USUARIO = FILTRO_USUARIO + strUsuario.toUpperCase().trim() + "' ";
		FILTRO_USUARIO_ROL = FILTRO_USUARIO_ROL + strUsuario.toUpperCase().trim() + "') ";
		FILTRO_VISTA_ROL = FILTRO_VISTA_ROL + strUsuario.toUpperCase().trim() + "') ";
		FILTRO_ROL = FILTRO_ROL + strUsuario.toUpperCase().trim() + "') ";
		HashMap<Integer, HashMap> mapResultado = null;
		HashMap column = new HashMap();
		column.put("String:0", "txtNombreVista");
		String sqlUs = " SELECT VR.txtNombreVista FROM PROFFLINE_TB_VISTA_ROL VR " + " INNER JOIN PROFFLINE_TB_USUARIO_ROL UR ON "
				+ " VR.txtIdRoL = UR.txtIdRoL " + " INNER JOIN PROFFLINE_TB_USUARIO U ON "
				+ " U.txtIdUsuario=UR.txtIdUsuario " + " WHERE txtNombreUsuario='" + strUsuario.toUpperCase().trim() + "' ";
		try {
			ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sqlUs, column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			for (int i = 0; i < mapResultado.size(); i++) {
				res = (HashMap) mapResultado.get(i);
				sqlTemp = FILTRO_VISTA + " WHERE txtNombreVista='" + res.get("txtNombreVista").toString() + "' ";
				listaSQL.add(sqlTemp);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		listaSQL.add(FILTRO_ROL);
		listaSQL.add(FILTRO_VISTA_ROL);
		listaSQL.add(FILTRO_USUARIO_ROL);
		listaSQL.add(FILTRO_USUARIO);
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			con.RegistrarRFC("ZSD_RFC_ADMIN_CONGLOMERADO");
			con.IngresarDatosInput(strUsuario, "IV_USER_ID");
			con.EjecutarRFC();
			BeanUsuario usuario = null;
			con.CreaTabla("T_USER");
			temp = con.ObtenerDatosTabla();
			for (int i = 0; i < temp.size(); i++) {
				String cadena = String.valueOf(temp.get(i));
				String[] temporal = cadena.split("¬");
				usuario = new BeanUsuario();
				usuario.setStrMandante(temporal[1]);
				usuario.setStrIdUsuario(temporal[2]);
				usuario.setStrNomUsuario(temporal[3]);
				usuario.setStrClaUsuario(temporal[5]);
				usuario.setStrFecCre(temporal[6]);
				usuario.setStrFecUltAccSis(temporal[7]);
				usuario.setStrHorUltAccSis(temporal[8]);
				if (temporal[9].trim().equals("X")) {
					usuario.setStrCambioClave("0");
				} else {
					usuario.setStrCambioClave("1");
				}
				usuario.setStrIntento(temporal[15]);
				if (temporal[16].trim().equals("")) {
					usuario.setStrBloqueado("0");
				} else {
					usuario.setStrBloqueado("1");
				}
				usuario.setStrIdentificacion(temporal[21]);
				usuario.setStrNomApe(temporal[23]);
				sql = "INSERT INTO " + TABLA_USUARIO + " (txtMandante, "
						+ " txtIdUsuario, " + " txtNombreUsuario, " + " txtClaveUsuario, " + " txtFechaRegistroUsuario, "
						+ " txtFechaUltimoAccesoUsuario, " + " txtHoraUltimoAccesoUsuario, " + " numCambioClave, "
						+ " numIntento, " + " numUsuarioBloqueado," + " txtIdentificacion," + " txtUsuario) "
						+ " VALUES ('" + usuario.getStrMandante() + "','" + usuario.getStrIdUsuario() + "','"
						+ usuario.getStrNomUsuario() + "','" + usuario.getStrClaUsuario() + "','"
						+ usuario.getStrFecCre() + "','" + usuario.getStrFecUltAccSis() + "','"
						+ usuario.getStrHorUltAccSis() + "'," + usuario.getStrCambioClave() + ","
						+ Integer.parseInt(usuario.getStrIntento()) + "," + usuario.getStrBloqueado() + ",'"
						+ usuario.getStrIdentificacion() + "','" + usuario.getStrNomApe() + "') ";
				idUsuario = usuario.getStrIdUsuario();
				listaSQL.add(sql);
			}
			BeanRol br = null;
			con.CreaTabla("T_PRO_LIST");
			temp = con.ObtenerDatosTabla();
			for (int i = 0; i < temp.size(); i++) {
				String cadena = String.valueOf(temp.get(i));
				String[] cadena2 = cadena.split("¬");
				br = new BeanRol();
				br.setStrMandante(cadena2[1]);
				br.setStrIdRol(cadena2[2]);
				br.setStrNomRol(cadena2[3]);
				idMandante = br.getStrMandante();
				SqlRolImpll objRol = new SqlRolImpll();
				if (!objRol.verficaRol(cadena2[3])) {
					sql = "INSERT INTO  " + TABLA_ROL + " (txtMandante, txtIdRol, txtNombreRol) "
							+ "VALUES ('" + br.getStrMandante() + "','" + br.getStrIdRol() + "','" + br.getStrNomRol() + "')";
					listaSQL.add(sql);
				}
				objRol = null;
			}
			BeanRolUsuario bru = null;
			con.CreaTabla("T_ROLE_LIST");
			temp = con.ObtenerDatosTabla();
			for (int i = 0; i < temp.size(); i++) {
				String cadena = String.valueOf(temp.get(i));
				String[] temporal = cadena.split("¬");
				bru = new BeanRolUsuario();
				bru.setStrIdRol(temporal[2]);
				bru.setStrNomRol(temporal[3]);
				if (temporal.length > 4) {
					bru.setStrC(temporal[4]);
				}
				sql = "INSERT INTO " + TABLA_USUARIO_ROL + " (txtMandante,txtIdUsuario,txtIdRoL) " + "VALUES ('"
						+ idMandante + "','" + idUsuario + "','" + bru.getStrIdRol() + "') ";
				listaSQL.add(sql);
			}
			BeanRolVista brv = null;
			con.CreaTabla("T_LIST_VIEW_LIST");
			temp = con.ObtenerDatosTabla();
			for (int i = 0; i < temp.size(); i++) {
				String cadena = String.valueOf(temp.get(i));
				String[] temporal = cadena.split("¬");
				brv = new BeanRolVista();
				brv.setStrMandante(temporal[1]);
				brv.setStrIdRol(temporal[2]);
				brv.setStrNomVista(temporal[3]);
				SqlVistaRolImpl objVisRol = new SqlVistaRolImpl();
				if (!objVisRol.verificaVistaRol(temporal[2], temporal[3])) {
					sql = "INSERT INTO " + TABALA_VISTA_ROL + " (txtMandante,txtIdRol,txtNombreVista) VALUES ('"
							+ brv.getStrMandante() + "'," + "'" + brv.getStrIdRol() + "'," + "'" + brv.getStrNomVista() + "'); ";
					listaSQL.add(sql);
				}
				objVisRol = null;
			}
			BeanVista bv = null;
			con.CreaTabla("T_VIEW_LIST");
			temp = con.ObtenerDatosTabla();
			for (int i = 0; i < temp.size(); i++) {
				String cadena = String.valueOf(temp.get(i));
				String[] temporal = cadena.split("¬");
				bv = new BeanVista();
				bv.setStrNomVis(temporal[1]);
				bv.setStrDesVis(temporal[2]);
				if (temporal.length > 3) {
					bv.setStrC(temporal[3]);
				}
				SqlVistaImpl objVis = new SqlVistaImpl();
				if (!objVis.verificaVista(temporal[1])) {
					sql = "INSERT INTO  " + TABLA_VISTA + "(txtMandante,txtNombreVista,txtDescVista) "
							+ "VALUES ('" + idMandante + "','" + bv.getStrNomVis() + "','" + bv.getStrDesVis() + "') ";
					listaSQL.add(sql);
				}
				objVis = null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		if (listaSQL.size() > 0) {
			SqlRol sqlRol = new SqlRolImpll();
			SqlUsuarioRol sqlUsuarioRol = new SqlUsuarioRolImpl();
			SqlUsuario sqlUsuario = new SqlUsuarioImpl();
			SqlVista sqlVista = new SqlVistaImpl();
			SqlVistaRol sqlVistaRol = new SqlVistaRolImpl();
			BeanUsuario usuario = new BeanUsuario();
			usuario.setStrIdUsuario(idUsuario);
			Cmd objC = new Cmd();
			SqlSincronizacionImpl objSI = new SqlSincronizacionImpl();
			BeanSincronizar bs = new BeanSincronizar();
			bs.setStrIdeSinc("1");
			String fechaInicio = objC.fechaHora();
			objSI.setEliminarSincronizacion(bs);
			sqlUsuario.setListaUsuario();
			bs.setStrInfSinc(USUARIOS);
			bs.setStrCantReg(sqlUsuario.getListaUsuario().size() + "");
			String fechaFin = objC.fechaHora();
			bs.setStrFecHor(fechaFin);
			bs.setStrTie(objC.diferencia(fechaInicio, fechaFin) + "");
			objSI.setInsertarSincronizar(bs);

			ResultExecuteList resultExecute = new ResultExecuteList();
			resultExecute.insertarListaConsultas(listaSQL, "Menú de usuario", Constante.BD_SYNC);
			resultado = resultExecute.isFlag();
		}
	}

	private String FILTRO_USUARIO = " DELETE FROM PROFFLINE_TB_USUARIO " + " WHERE txtNombreUsuario='";

	private String FILTRO_USUARIO_ROL = " DELETE FROM PROFFLINE_TB_USUARIO_ROL "
			+ " WHERE txtIdUsuario=(SELECT txtIdUsuario FROM PROFFLINE_TB_USUARIO "
			+ " WHERE txtNombreUsuario='";

	private String FILTRO_VISTA_ROL = " DELETE FROM PROFFLINE_TB_VISTA_ROL "
			+ " WHERE txtIdRoL = (SELECT VR.TxtIdRoL FROM PROFFLINE_TB_VISTA_ROL VR "
			+ " INNER JOIN PROFFLINE_TB_USUARIO_ROL UR ON "
			+ " VR.txtIdRoL = UR.txtIdRoL "
			+ " INNER JOIN PROFFLINE_TB_USUARIO U ON "
			+ " U.txtIdUsuario=UR.txtIdUsuario " + " WHERE txtNombreUsuario='";

	private String FILTRO_VISTA = " DELETE FROM PROFFLINE_TB_VISTA  ";

	private String FILTRO_ROL = " DELETE FROM " + TABLA_ROL
			+ " WHERE txtIdRoL =(SELECT UR.TxtIdRoL FROM PROFFLINE_TB_USUARIO_ROL UR "
			+ " INNER JOIN PROFFLINE_TB_USUARIO U "
			+ " ON U.txtIdUsuario=UR.txtIdUsuario " + " WHERE txtNombreUsuario='";

	private static String TABLA_USUARIO = "PROFFLINE_TB_USUARIO";
	private static String TABALA_VISTA_ROL = "PROFFLINE_TB_VISTA_ROL";
	private static String TABLA_USUARIO_ROL = "PROFFLINE_TB_USUARIO_ROL";
	private static String TABLA_VISTA = "PROFFLINE_TB_VISTA";
	private static String TABLA_ROL = "PROFFLINE_TB_ROL";
	private static String USUARIOS = "Usuarios";
}