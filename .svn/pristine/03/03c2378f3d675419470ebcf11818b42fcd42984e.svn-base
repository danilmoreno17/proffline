package com.promesa.administracion.sql.impl;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.promesa.administracion.sql.SqlDispositivo;
import com.promesa.administracion.sql.SqlRol;
import com.promesa.administracion.sql.SqlSincronizacion;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.SqlUsuarioRol;
import com.promesa.administracion.sql.SqlVista;
import com.promesa.administracion.sql.SqlVistaRol;
import com.promesa.administracion.bean.*;
import com.promesa.main.Promesa;
import com.promesa.sincronizacion.bean.*;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.sap.*;
import com.promesa.util.Constante;
import com.promesa.util.Util;

public class SqlSincronizacionImpl implements SqlSincronizacion {
	private String sqlSinc = null;
	@SuppressWarnings("unused")
	private String sqlUsuario = null;
	@SuppressWarnings("unused")
	private String sqlRol = null;
	@SuppressWarnings("unused")
	private String sqlUsuarioRol = null;
	@SuppressWarnings("unused")
	private String sqlVista = null;
	@SuppressWarnings("unused")
	private String sqlRolVista = null;
	@SuppressWarnings("unused")
	private String sqlDispositivo = null;
	@SuppressWarnings("rawtypes")
	private HashMap column = new HashMap();
	private ResultExecuteQuery resultExecuteQuery = null;
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
	private List<BeanSincronizacion> since = null;
	BeanSincronizacion objS = new BeanSincronizacion();
	private boolean resultado = false;
	@SuppressWarnings("unused")
	private ResultExecute resultExecute = null;
	private String sqlSincronizar;
	private List<BeanSincronizacion> listaSincronizar = null;

	// ALL
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanSincronizacion> listaSincronizacion() {
		column = new HashMap();
		since = new ArrayList<BeanSincronizacion>();
		column.put("String:0", NUM_ID_SINC);
		column.put("String:1", INFO_SINC);
		column.put("String:2", CANT_REG);
		column.put("String:3", FECH_HOR);
		column.put("String:4", TIEMPO);
		column.put("String:5", HORA_INI);
		column.put("String:6", FRECUENCIA);
		sqlSinc = " SELECT PS." + NUM_ID_SINC + ", " + INFO_SINC + ", " + CANT_REG + ", " + FECH_HOR
					+ ", " + TIEMPO + ", " + HORA_INI + "," + FRECUENCIA + " " + " FROM " + TABLA_SINCRONIZACION
					+ " PS" + " INNER JOIN PROFFLINE_TB_DETALLE_SINCRONIZACION PDS ON PS.numIdeSinc=PDS.numIdeSinc"
					+ " WHERE PS." + NUM_ID_SINC + " IN(1,2,3,4,5,6)";

		resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			objS = new BeanSincronizacion();
			res = (HashMap) mapResultado.get(i);
			objS.setStrIdeSinc(res.get(NUM_ID_SINC).toString());
			objS.setStrInfSinc(res.get(INFO_SINC).toString());
			objS.setStrNumCantReg(res.get(CANT_REG).toString());
			objS.setStrFecHor(res.get(FECH_HOR).toString());
			objS.setStrNumTie(res.get(TIEMPO).toString());
			objS.setStrHoraIni(res.get(HORA_INI).toString());
			objS.setStrFrecuencia(res.get(FRECUENCIA).toString());
			since.add(objS);
		}
		return since;
	}

	// SYNCHRONIZED USER
	@SuppressWarnings({ "unused" })
	public boolean sincronizaUsuario() {
		resultExecute = null;
		sqlUsuario = "";
		resultado = false;
		SAdministracion objSAP;
		List<BeanUsuario> usuariose;
		SqlUsuario sqlUsr = new SqlUsuarioImpl();
		BeanUsuario beanUsuario = null;

		try {
			usuariose = new ArrayList<BeanUsuario>();
			objSAP = new SAdministracion();
			usuariose = objSAP.buscaUsuario("");
			String temp;
			temp = "";
			if (usuariose != null) {
				sqlUsr.setEliminarUsuario();
				double avance = 0;
				double i = 0;
				double total = usuariose.size();
				for (BeanUsuario bu : usuariose) {
					avance = Math.round(i * 100 / total * 100) / 100.0d;
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando usuarios al " + avance + "%");
					if (bu.getStrBloqueado().equals(""))
						bu.setStrBloqueado("0");
					else
						bu.setStrBloqueado("1");
					
					sqlUsr.setInsertarUsuario(bu);
					resultado = sqlUsr.getInsertarUsuario();
					i++;
				}
				resultado = true;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED ROLE
	public boolean sincronizaRol() {
		sqlRol = "";
		resultado = false;
		SAdministracion objSAP;
		List<BeanRol> rolese;
		SqlRol sqlR = new SqlRolImpll();
		try {
			rolese = new ArrayList<BeanRol>();
			objSAP = new SAdministracion();
			rolese = objSAP.listaRoles();
			if (rolese != null) {
				sqlR.setEliminarRol();
				double avance = 0;
				double i = 0;
				double total = rolese.size();
				for (BeanRol br : rolese) {
					avance = Math.round(i * 100 / total * 100) / 100.0d;
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando Roles al " + avance + "%");
					sqlR.setInsertarRol(br);
					resultado = sqlR.getInsertarRol();
					i++;
				}
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
			Util.mostrarExcepcion(e);
		}
		return resultado;
	}

	// SYNCHRONIZED USER ROL
	@SuppressWarnings({ "unused" })
	public boolean sincronizaUsuarioRol() {
		sqlUsuarioRol = "";
		resultado = false;
		SAdministracion objSAP;
		List<BeanUsuarioRol> ure;
		SqlUsuarioRol SqlUsR = new SqlUsuarioRolImpl();

		BeanUsuarioRol usuarioR = null;
		BeanRol rol = null;
		BeanUsuario usuario = null;
		try {
			ure = new ArrayList<BeanUsuarioRol>();
			objSAP = new SAdministracion();
			ure = objSAP.usuariosRoles();
			if (ure != null) {
				SqlUsR.setEliminarUsuarioRol();
				double avance = 0;
				double i = 0;
				double total = ure.size();
				final Properties props = new Properties();
	            InputStreamReader in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
	            props.load(in);
				for (BeanUsuarioRol bur : ure) {
					avance = Math.round(i * 100 / total * 100) / 100.0d;
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando Usuarios y Roles al " + avance + "%");
					rol = new BeanRol();
					usuario = new BeanUsuario();
					rol.setStrIdRol(bur.getStrIdRol());
					usuario.setStrIdUsuario(bur.getStrIdUsu());
					usuario.setStrMandante(props.getProperty("sap.mandante").trim());
					bur.setRol(rol);
					bur.setUsuario(usuario);
					SqlUsR.setInsertarUsuarioRol(bur);
					resultado = SqlUsR.getEliminarUsuarioRol();
					i++;
				}
				in.close();
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
			Util.mostrarExcepcion(e);
		}
		return resultado;
	}

	// SYNCHRONIZED VIEW
	@SuppressWarnings({})
	public boolean sincronizaVista() {
		sqlVista = "";
		resultado = false;
		SAdministracion objSAP;
		List<BeanVista> vistase;
		SqlVista sqlV = new SqlVistaImpl();
		try {
			vistase = new ArrayList<BeanVista>();
			objSAP = new SAdministracion();
			vistase = objSAP.listaVistas("");
			if (vistase != null) {
				sqlV.setEliminarVista();
				double avance = 0;
				double i = 0;
				double total = vistase.size();
				final Properties props = new Properties();
	            InputStreamReader in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
	            props.load(in);
				for (BeanVista bv : vistase) {
					avance = Math.round(i * 100 / total * 100) / 100.0d;
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando Visitas al " + avance + "%");
					bv.setStrMandante(props.getProperty("sap.mandante").trim());
					sqlV.setInsertarVista(bv);
					resultado = sqlV.getInsertarVista();
					i++;
				}
				in.close();
				resultado = true;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED ROL VISTA
	@SuppressWarnings({})
	public boolean sincronizaRolVista() {
		SqlVistaRol SqlV = new SqlVistaRolImpl();
		resultExecute = null;
		sqlRolVista = "";
		resultado = false;
		SAdministracion objSAP;
		List<BeanRolVista> rve;
		try {
			rve = new ArrayList<BeanRolVista>();
			objSAP = new SAdministracion();
			rve = objSAP.rolesVistas();
			if (rve != null) {
				SqlV.setEliminarVistaRol();
				double avance = 0;
				double i = 0;
				double total = rve.size();
				for (BeanRolVista bv : rve) {
					avance = Math.round(i * 100 / total * 100) / 100.0d;
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando Vistas y Roles al " + avance + "%");
					SqlV.setInsertarVistaRol(bv);
					resultado = SqlV.getEliminarVistaRol();
					i++;
				}
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
			Util.mostrarExcepcion(e);
		}
		return resultado;
	}

	// SYNCHRONIZED DEVICE
	public boolean sincronizaDispositivo() {
		sqlDispositivo = "";
		resultado = false;
		SAdministracion objSAP;
		List<BeanDispositivo> de;
		SqlDispositivo SqlD = new SqlDispositivoImpl();
		try {
			de = new ArrayList<BeanDispositivo>();
			objSAP = new SAdministracion();
			de = objSAP.buscaDispositivo("");
			if (de != null) {
				SqlD.setEliminarDispositivo();
				double avance = 0;
				double i = 0;
				double total = de.size();
				for (BeanDispositivo bd : de) {
					avance = Math.round(i * 100 / total * 100) / 100.0d;
					Promesa.getInstance().mostrarAvisoSincronizacion("Actualizando Dispositivos al " + avance + "%");
					SqlD.setInsertarDispositivo(bd);
					resultado = SqlD.getInsertarDispositivo();
					i++;
				}
			}
			resultado = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setListaSincronizar() {
		column = new HashMap();
		listaSincronizar = new ArrayList<BeanSincronizacion>();
		column.put("String:0", "numIdeSinc");
		column.put("String:1", "txtInfSinc");
		column.put("String:2", "numCantReg");
		column.put("String:3", "txtFecHor");
		column.put("String:4", "numTie");
		sqlSincronizar = "SELECT " + "numIdeSinc, " + "txtInfSinc, " + "numCantReg, " + "txtFecHor, " + "numTie "
						+ "FROM PROFFLINE_TB_SINCRONIZACION WHERE numIdeSinc IN(1,2,3,4,5,6)";
		resultExecuteQuery = new ResultExecuteQuery(sqlSincronizar, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			objS = new BeanSincronizacion();
			res = (HashMap) mapResultado.get(i);
			objS.setStrIdeSinc(res.get("numIdeSinc").toString());
			objS.setStrInfSinc(res.get("txtInfSinc").toString());
			objS.setStrNumCantReg(res.get("numCantReg").toString());
			objS.setStrFecHor(res.get("txtFecHor").toString());
			objS.setStrNumTie(res.get("numTie").toString());
			listaSincronizar.add(objS);
		}
	}

	public void setInsertarSincronizar(BeanSincronizar Sincronizar) {
		SqlHistorialSincronizacionImpl histSinc = new SqlHistorialSincronizacionImpl();

		sqlSincronizar = " INSERT INTO PROFFLINE_TB_SINCRONIZACION (numIdeSinc,txtInfSinc,numCantReg,txtFecHor,numTie) "
				+ " VALUES (" + Sincronizar.getStrIdeSinc() + ",'" + Sincronizar.getStrInfSinc() + "'," + Sincronizar.getStrCantReg() + ",'" + Sincronizar.getStrFecHor() + "'," + Sincronizar.getStrTie() + ") ";

		histSinc.setInsertarHistorialSincronizacion(Sincronizar);
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar,"Sincronización",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarSincronizacion(BeanSincronizar Sincronizar) {
		sqlSincronizar = "DELETE FROM PROFFLINE_TB_SINCRONIZACION " + "WHERE numIdeSinc = " + Sincronizar.getStrIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar,"Sincronización",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}


	public void setActualizarSincronizacionDetalle(DetalleSincronizacion ds) {
		sqlSincronizar = " UPDATE PROFFLINE_TB_DETALLE_SINCRONIZACION " + " SET txtHorIni='" + ds.getTxtHorIni() + "', " + " numFec=" + ds.getNumFec() + " " + " WHERE numIdeSinc = " + ds.getNumIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar,"Sincronización",Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public boolean getEliminarSincronizacion() {
		return resultado;
	}

	public boolean getInsertarSincronizar() {
		return resultado;
	}

	public List<BeanSincronizacion> getListaSincronizar() {
		return listaSincronizar;
	}

	private static String NUM_ID_SINC = "numIdeSinc";
	private static String INFO_SINC = "txtInfSinc";
	private static String CANT_REG = "numCantReg";
	private static String FECH_HOR = "txtFecHor";
	private static String TIEMPO = "numTie";
	private static String HORA_INI = "txtHorIni";
	private static String FRECUENCIA = "numFec";
	private static String TABLA_SINCRONIZACION = "PROFFLINE_TB_SINCRONIZACION";
}