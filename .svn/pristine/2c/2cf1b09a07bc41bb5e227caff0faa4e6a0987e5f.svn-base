package com.promesa.sincronizacion.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.promesa.administracion.bean.BeanDispositivo;
import com.promesa.administracion.bean.BeanRol;
import com.promesa.administracion.bean.BeanRolVista;
import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.administracion.bean.BeanUsuario;
import com.promesa.administracion.bean.BeanUsuarioRol;
import com.promesa.administracion.bean.BeanVista;
import com.promesa.administracion.sql.SqlDispositivo;
import com.promesa.administracion.sql.SqlRol;
import com.promesa.administracion.sql.SqlUsuario;
import com.promesa.administracion.sql.SqlUsuarioRol;
import com.promesa.administracion.sql.SqlVista;
import com.promesa.administracion.sql.SqlVistaRol;
import com.promesa.administracion.sql.impl.SqlDispositivoImpl;
import com.promesa.administracion.sql.impl.SqlRolImpll;
import com.promesa.administracion.sql.impl.SqlSincronizacionImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioImpl;
import com.promesa.administracion.sql.impl.SqlUsuarioRolImpl;
import com.promesa.administracion.sql.impl.SqlVistaImpl;
import com.promesa.administracion.sql.impl.SqlVistaRolImpl;
import com.promesa.sap.SAdministracion;
import com.promesa.util.Cmd;
import com.promesa.util.Util;

public class SincronizacionAdministracion {
	SqlSincronizacionImpl objSI;
	String usuRegUpd;

	public SincronizacionAdministracion(String usuRegUpd) {
		this.usuRegUpd = usuRegUpd;
	}

	@SuppressWarnings("static-access")
	public void sincronizar_DOWN_Roles() {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		BeanRol rol = new BeanRol();
		SqlRol sqlRol = new SqlRolImpll();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("2");
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaRol();
		objSI.setEliminarSincronizacion(bs);
		sqlRol.setListaRol(rol);
		bs.setStrInfSinc(ROLES);
		bs.setStrCantReg(sqlRol.getListaRol().size() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.setInsertarSincronizar(bs);
	}

	public void sincronizar_UP_Roles() {
		SqlRol sqlRol = new SqlRolImpll();
		BeanRol rol = new BeanRol();
		List<BeanRol> listaRol = null;
		SAdministracion sAdministracion = new SAdministracion();
		sqlRol.setListaRol(rol);
		listaRol = sqlRol.getListaRol();
		for (BeanRol rl : listaRol) {
			try {
				sAdministracion.ingresaModifcaRol(rl.getStrIdRol(), rl.getStrNomRol());
			} catch (Exception exec) {
				Util.mostrarExcepcion(exec);
			}
		}
	}

	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizar_DOWN_Usuarios_Roles() {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		BeanRol rol = new BeanRol();
		SqlUsuarioRol usuarioRol = new SqlUsuarioRolImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("3");
		String fechaInicio = objC.fechaHora();
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaUsuarioRol();
		objSI.setEliminarSincronizacion(bs);
		usuarioRol.setListaUsuarioRol();
		bs.setStrInfSinc(USUARIO_ROLES);
		bs.setStrCantReg(usuarioRol.getListaUsuarioRol().size() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.setInsertarSincronizar(bs);
	}

	@SuppressWarnings("unused")
	public void sincronizar_UP_Usuarios_Roles() {
		SqlUsuarioRol usuarioRol = new SqlUsuarioRolImpl();
		SqlUsuario sqlU = new SqlUsuarioImpl();
		BeanUsuario usu = new BeanUsuario();
		BeanUsuarioRol bur = new BeanUsuarioRol();
		BeanRol rol = new BeanRol();
		List<BeanUsuarioRol> listaUsuarioRol = null;
		List<BeanUsuario> listaUsu = null;
		List<String> datos = new ArrayList<String>();
		String idUsuario = "";
		SAdministracion sAdministracion = new SAdministracion();
		bur.setUsuario(usu);
		usuarioRol.setListaUsuarioRol(bur);
		listaUsuarioRol = usuarioRol.getListaUsuarioRol();
		datos.clear();
		for (BeanUsuarioRol usr : listaUsuarioRol) {
			usu = usr.getUsuario();
			rol = usr.getRol();
			idUsuario = usu.getStrIdUsuario();
			datos.add(rol.getStrIdRol());
		}
		try {
			sAdministracion.asignaRolUsuario(datos, idUsuario);
		} catch (Exception ex) {
			Util.mostrarExcepcion(ex);
		}
	}

	@SuppressWarnings("static-access")
	public void sincronizar_DOWN_Vistas() {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		BeanVista vista = new BeanVista();
		SqlVista sqlVista = new SqlVistaImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("4");
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaVista();
		objSI.setEliminarSincronizacion(bs);
		sqlVista.setListaVista(vista);
		bs.setStrInfSinc(VISTAS);
		bs.setStrCantReg(sqlVista.getListaVista().size() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.setInsertarSincronizar(bs);
	}

	public void sincronizar_UP_Vistas() {

	}

	@SuppressWarnings("static-access")
	public void sincronizar_DOWN_Roles_Vistas() {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlVistaRol vistaRol = new SqlVistaRolImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("5");
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaRolVista();
		objSI.setEliminarSincronizacion(bs);
		vistaRol.setListaVistaRol();
		bs.setStrInfSinc(ROLES_VISTAS);
		bs.setStrCantReg(vistaRol.getListaVistaRol().size() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;

		bs.setStrTie((dif / 1000) + "");
		objSI.setInsertarSincronizar(bs);
	}

	@SuppressWarnings("unused")
	public void sincronizar_UP_Roles_Vistas() {
		SqlVistaRol sqlVistR = new SqlVistaRolImpl();
		BeanRolVista vistaRol = new BeanRolVista();
		BeanRol rol = new BeanRol();
		BeanVista vista = new BeanVista();
		List<BeanRolVista> listaBeanRolVista = null;
		SAdministracion sAdministracion = new SAdministracion();
		vistaRol.setRol(rol);
		sqlVistR.setListaVistaRol(vistaRol);
		listaBeanRolVista = sqlVistR.getListaVistaRol();
		for (BeanRolVista brv : listaBeanRolVista) {
			rol = brv.getRol();
			vista = brv.getVista();
			try {
//				sAdministracion.asignaVistaRol(rol.getStrIdRol(), vista.getStrNomVis());
			} catch (Exception ex) {
				Util.mostrarExcepcion(ex);
			}
		}
	}

	@SuppressWarnings("static-access")
	public void sincronizar_DOWN_Dispositivos() {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		BeanDispositivo dispositivo = new BeanDispositivo();
		SqlDispositivo sqlDispositivo = new SqlDispositivoImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("6");
		objSI = new SqlSincronizacionImpl();
		objSI.sincronizaDispositivo();
		objSI.setEliminarSincronizacion(bs);
		sqlDispositivo.setListaDispositivo(dispositivo);
		bs.setStrInfSinc(DISPOSITIVOS);
		bs.setStrCantReg(sqlDispositivo.getListaDispositivo().size() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.setInsertarSincronizar(bs);
	}

	public void sincronizar_UP_Dispositivos() {
		SqlDispositivo sqlD = new SqlDispositivoImpl();
		BeanDispositivo disp = new BeanDispositivo();
		List<BeanDispositivo> listaDisp = null;
		SAdministracion sAdministracion = new SAdministracion();
		sqlD.setListaDispositivo(disp);
		listaDisp = sqlD.getListaDispositivo();
		for (BeanDispositivo bd : listaDisp) {
			try {
				sAdministracion.ingresaModificaDispositivo(bd.getStrIdDispositivo(), bd.getStrTipoDispositivo(),
						bd.getStrNumeroSerieDispositivo(), bd.getStrCodigoActivo(), bd.getStrSimm(),
						bd.getStrImei(), bd.getStrNumeroTelefono(), bd.getStrNumeroSeguro(), bd.getStrIdUsuario(),
						bd.getStrNomUsuario(), bd.getStrDispositivoRelacionado(), bd.getStrObservacion(), this.usuRegUpd);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
			}
		}
	}

	@SuppressWarnings("unused")
	private static String USUARIOS = "Usuarios";
	private static String ROLES = "Roles";
	private static String USUARIO_ROLES = "Usuarios Roles";
	private static String VISTAS = "Vistas";
	private static String ROLES_VISTAS = "Roles Vistas";
	private static String DISPOSITIVOS = "Dispositivos";
}