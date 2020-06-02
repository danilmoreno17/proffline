package com.promesa.sap;

import com.promesa.administracion.bean.*;
import com.promesa.bean.*;
import com.promesa.util.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import util.ConexionSAP;

public class SAdministracion {

	/* MÉTODO QUE LISTA USUARIOS ROLES */
	public List<BeanUsuarioRol> usuariosRoles() {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_LIST_UROLE");
				con.EjecutarRFC();
				con.CreaTabla("T_LIST_USER_ROLE");
				@SuppressWarnings("rawtypes")
				List ur = con.ObtenerDatosTabla();
				List<BeanUsuarioRol> ure = new ArrayList<BeanUsuarioRol>();
				BeanUsuarioRol bur = null;
				for (int i = 0; i < ur.size(); i++) {
					String cadena = String.valueOf(ur.get(i));
					String[] temporal = cadena.split("¬");
					bur = new BeanUsuarioRol();
					bur.setStrMandante(temporal[1]);
					bur.setStrIdUsu(temporal[2]);
					bur.setStrIdRol(temporal[3]);
					ure.add(bur);
				}
				con.DesconectarSAP();
				if (ure.size() > 0) {
					return ure;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE LISTA ROLES VISTAS */
	public List<BeanRolVista> rolesVistas() {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VIEW_LIST");
				con.EjecutarRFC();
				con.CreaTabla("T_LIST_VIEW_LIST");
				@SuppressWarnings("rawtypes")
				List rv = con.ObtenerDatosTabla();
				List<BeanRolVista> rve = new ArrayList<BeanRolVista>();
				BeanRolVista brv = null;
				for (int i = 0; i < rv.size(); i++) {
					String cadena = String.valueOf(rv.get(i));
					String[] temporal = cadena.split("¬");
					brv = new BeanRolVista();
					brv.setStrMandante(temporal[1]);
					brv.setStrIdRol(temporal[2]);
					brv.setStrNomVista(temporal[3]);
					rve.add(brv);
				}
				con.DesconectarSAP();
				if (rve.size() > 0) {
					return rve;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE INGRESA MODIFICA DISPOSITIVO */
	public List<String> ingresaModificaDispositivo(String idDis, String tipDis,
			String serie, String codAct, String simm, String imei,
			String nroTelf, String numSeg, String idUsu, String usu,
			String disRel, String obs, String usuRegUpd) {
		ConexionSAP con;
		String idDispositivo;
		List<String> datos;
		try {
			con = Conexiones.getConexionSAP();
			con.RegistrarRFC("ZSD_RFC_DISPOSIT_SET_DIS_V3");
			con.CreaTabla("T_ENTRADA");
			con.IngresarDatoTabla(idDis, "IDDIS", 1);
			con.IngresarDatoTabla(tipDis, "TIPDIS", 1);
			con.IngresarDatoTabla(serie, "SERIE", 1);
			con.IngresarDatoTabla(codAct, "CODACT", 1);
			con.IngresarDatoTabla(simm, "SIMM", 1);
			con.IngresarDatoTabla(imei, "IMEI", 1);
			con.IngresarDatoTabla(nroTelf, "NROTELF", 1);
			con.IngresarDatoTabla(numSeg, "NUMSEG", 1);
			con.IngresarDatoTabla(idUsu, "IDUSU", 1);
			con.IngresarDatoTabla(usu, "USU", 1);
			con.IngresarDatoTabla(disRel, "DISREL", 1);
			con.IngresarDatoTabla(obs, "OBS", 1);
			if (idDis.equals(""))
				con.IngresarDatoTabla(usuRegUpd, "USUREG", 1);
			else
				con.IngresarDatoTabla(usuRegUpd, "USUUPD", 1);
			con.EjecutarRFC();
			datos = new ArrayList<String>();
			idDispositivo = con.ObtenerDato("E_DISPOSITIVO");
			datos.add(idDispositivo);
			con.CreaTabla("T_MSG");
			@SuppressWarnings({ "rawtypes" })
			List msg = con.ObtenerDatosTabla();
			for (int i = 0; i < msg.size(); i++) {
				String cadena = String.valueOf(msg.get(i));
				String[] temporal = cadena.split("¬");
				datos.add(temporal[2]);
			}
			con.DesconectarSAP();
			return datos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			datos = null;
			return datos;
		}
	}

	/* MÉTODO QUE BUSCA DISPOSITIVOS */
	public List<BeanDispositivo> buscaDispositivo(String usuario) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_DISPOSIT_LIST_DIS_USU");
				con.IngresarDatosInput(usuario, "V_USU");
				con.EjecutarRFC();
				con.CreaTabla("T_LIST_DISPO_USU");
				@SuppressWarnings("rawtypes")
				List dispositivos = con.ObtenerDatosTabla();
				List<BeanDispositivo> dispositivose = new ArrayList<BeanDispositivo>();
				BeanDispositivo bd = null;
				for (int i = 0; i < dispositivos.size(); i++) {
					String cadena = String.valueOf(dispositivos.get(i));
					String[] temporal = cadena.split("¬");
					bd = new BeanDispositivo();
					bd.setStrIdDispositivo(temporal[2]);
					bd.setStrTipoDispositivo(temporal[3]);
					bd.setStrNumeroSerieDispositivo(temporal[4]);
					bd.setStrCodigoActivo(temporal[5]);
					bd.setStrSimm(temporal[6]);
					bd.setStrImei(temporal[7]);
					bd.setStrNumeroTelefono(temporal[8]);
					bd.setStrNumeroSeguro(temporal[9]);
					bd.setStrIdUsuario(temporal[10]);
					bd.setStrNomUsuario(temporal[11]);
					bd.setStrDispositivoRelacionado(temporal[12]);
					bd.setStrEstado(temporal[13]);
					bd.setStrObservacion(temporal[14]);
					bd.setStrUsuReg(temporal[15]);
					bd.setStrFecReg(convierteFecha(temporal[16]));
					bd.setStrHorReg(temporal[17].substring(11, 19));
					if (temporal[18].length() > 0) {
						bd.setStrUsuUpd(temporal[18]);
					} else {
						bd.setStrUsuUpd(Constante.VACIO);
					}
					if (temporal[19].equals("null")) {
						bd.setStrFecUpd(Constante.VACIO);
					} else {
						bd.setStrFecUpd(convierteFecha(temporal[19]));
					}
					if (temporal[20].equals("Thu Jan 01 00:00:00 COT 1970")) {
						bd.setStrHorUpd("00:00:00");
					} else {
						bd.setStrHorUpd(temporal[20].substring(11, 19));
					}
					dispositivose.add(bd);
				}
				con.DesconectarSAP();
				if (dispositivose.size() > 0) {
					return dispositivose;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE BLOQUEA DISPOSITIVO */
	public boolean bloqueaDispositivo(String idDis) {
		boolean resultado;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_DISPOSIT_LOCK_DIS");
				con.IngresarDatosInput(idDis, "S_IDDISPOSITIVO");
				con.EjecutarRFC();
				con.DesconectarSAP();
				resultado = true;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				resultado = false;
			}
			return resultado;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}

	/* MÉTODO QUE ELIMINA DISPOSITIVO */
	public boolean eliminaDispositivo(String idDis) {
		boolean resultado;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_DISPOSIT_DEL_DIS");
				con.IngresarDatosInput(idDis, "S_IDDIS");
				con.EjecutarRFC();
				con.DesconectarSAP();
				resultado = true;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				resultado = false;
			}
			return resultado;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}

	/* MÉTODO QUE ELIMINA ROL */
	public boolean eliminaRol(String idRol) {
		boolean resultado;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_PROFILE_DEL_PRO");
				con.IngresarDatosInput(idRol, "IV_PRO_ID");
				con.EjecutarRFC();
				con.DesconectarSAP();
				resultado = true;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				resultado = false;
			}
			return resultado;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}

	/* MÉTODO QUE LISTA ROLES */
	public List<BeanRol> listaRoles() {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_PROFILE_LIST_PRO");
				con.EjecutarRFC();
				con.CreaTabla("T_PRO_LIST");
				@SuppressWarnings("rawtypes")
				List roles = con.ObtenerDatosTabla();
				List<BeanRol> rolese = new ArrayList<BeanRol>();
				BeanRol br = null;
				for (int i = 0; i < roles.size(); i++) {
					String cadena = String.valueOf(roles.get(i));
					String[] cadena2 = cadena.split("¬");
					br = new BeanRol();
					br.setStrMandante(cadena2[1]);
					br.setStrIdRol(cadena2[2]);
					br.setStrNomRol(cadena2[3]);
					rolese.add(br);
				}
				con.DesconectarSAP();
				if (rolese.size() > 0) {
					return rolese;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE INGRESA MODIFICA ROL */
	public List<String> ingresaModifcaRol(String idRol, String nomRol) {
		ConexionSAP con;
		String ID;
		List<String> datos;
		try {
			con = Conexiones.getConexionSAP();
			con.RegistrarRFC("ZSD_RFC_PROFILE_SET_PRO");
			con.CreaTabla("T_PROFILE");
			con.IngresarDatoTabla(idRol, "PROFILE_ID", 1);
			con.IngresarDatoTabla(nomRol, "PROFILE_NAM", 1);
			con.EjecutarRFC();
			datos = new ArrayList<String>();
			ID = con.ObtenerDato("E_PROFILE");
			datos.add(ID);
			con.CreaTabla("T_MSG");
			@SuppressWarnings({ "rawtypes" })
			List msg = con.ObtenerDatosTabla();
			for (int i = 0; i < msg.size(); i++) {
				String cadena = String.valueOf(msg.get(i));
				String[] temporal = cadena.split("¬");
				datos.add(temporal[2]);
			}
			con.DesconectarSAP();
			return datos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			datos = null;
			return datos;
		}
	}

	/* MÉTODO QUE LISTA VISTAS */
	public List<BeanVista> listaVistas(String idRol) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VIEWS_LIST_VIEW");
				if (!(idRol.equals("")))
					con.IngresarDatosInput(idRol, "IV_PRO_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_VIEW_LIST");
				@SuppressWarnings("rawtypes")
				List roles = con.ObtenerDatosTabla();
				List<BeanVista> vistase = new ArrayList<BeanVista>();
				BeanVista bv = null;
				for (int i = 0; i < roles.size(); i++) {
					String cadena = String.valueOf(roles.get(i));
					String[] temporal = cadena.split("¬");
					bv = new BeanVista();
					bv.setStrNomVis(temporal[1]);
					bv.setStrDesVis(temporal[2]);
					if (temporal.length > 3)
						bv.setStrC(temporal[3]);
					vistase.add(bv);
				}
				con.DesconectarSAP();
				if (vistase.size() > 0) {
					return vistase;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE ASIGNA VISTA A ROL */
	public boolean asignaVistaRol(String idRol, List<String> vistas) {
		try {
			boolean resultado;
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VIEWS_ASSIGN_TO_PROFILE");
				con.CreaTabla("T_VIEW_LIST");
				int contador = 1;
				for (String string : vistas) {
					con.IngresarDatoTabla(string, "VIEW_NAME", contador);
					con.IngresarDatoTabla("X", "CHECK_V", contador);
					contador++;
				}
				con.IngresarDatosInput(idRol, "IV_PROFILE_ID");
				con.EjecutarRFC();
				con.DesconectarSAP();
				resultado = true;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				resultado = false;
			}
			return resultado;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}

	/* MÉTODO QUE PERMITE EL ACCESO DE UN USUARIO */
	public List<BeanLogin> accesoUsuario(String usuario, String clave, String antiguaClave, String nuevaClave, String confirmacionNuevaClave, String numeroSerie) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_LOGIN3");
				con.CreaTabla("TS_PARAMENTERS");
				con.IngresarDatoTabla(usuario, "BNAME", 1);
				con.IngresarDatoTabla(clave, "BCODE", 1);
				con.IngresarDatosInput(numeroSerie, "ET_SERIE");
				if (!(antiguaClave.equals("")) && !(nuevaClave.equals("")) && !(confirmacionNuevaClave.equals(""))) {
					con.IngresarDatoTabla(antiguaClave, "OLD_BCODE", 1);
					con.IngresarDatoTabla(nuevaClave, "NEW_BCODE", 1);
					con.IngresarDatoTabla(confirmacionNuevaClave, "CONF_BCODE", 1);
				}
				con.EjecutarRFC();
				List<String> tmsg = new ArrayList<String>();
				con.CreaTabla("T_MSG");
				@SuppressWarnings({ "rawtypes" })
				List msg = con.ObtenerDatosTabla();
				for (int i = 0; i < msg.size(); i++) {
					String cadena = String.valueOf(msg.get(i));
					String[] temporal = cadena.split("¬");
					tmsg.add(temporal[2]);
				}
				List<String> tmenu = new ArrayList<String>();
				con.CreaTabla("T_MENU");
				@SuppressWarnings({ "rawtypes" })
				List menu = con.ObtenerDatosTabla();
				for (int i = 0; i < menu.size(); i++) {
					tmenu.add(menu.get(i).toString());
				}
				List<BeanLogin> accesoe = new ArrayList<BeanLogin>();
				BeanLogin bl = new BeanLogin();
				if (con.ObtenerDato("EV_ACCESS").length() == 0) {
					bl.setStrAcceso(Constante.VACIO);
				} else {
					bl.setStrAcceso(con.ObtenerDato("EV_ACCESS"));
				}
				if (con.ObtenerDato("EV_NEED_CHANGE").length() == 0) {
					bl.setStrCambioClave(Constante.VACIO);
				} else {
					bl.setStrCambioClave(con.ObtenerDato("EV_NEED_CHANGE"));
				}
				if (con.ObtenerDato("V_NOM").length() == 0) {
					bl.setStrUsuario(Constante.VACIO);
				} else {
					bl.setStrUsuario(con.ObtenerDato("V_NOM"));
				}
				if (con.ObtenerDato("V_KUNNR").length() == 0) {
					bl.setStrCodigo(Constante.VACIO);
				} else {
					bl.setStrCodigo(con.ObtenerDato("V_KUNNR"));
				}
				if (con.ObtenerDato("ET_ESTADO").length() == 0) {
					bl.setStrEstado(Constante.VACIO);
				} else {
					bl.setStrEstado(con.ObtenerDato("ET_ESTADO"));
				}
				if (con.ObtenerDato("V_ROL").length() == 0) {
					bl.setStrRol(Constante.VACIO);
				} else {
					bl.setStrRol(con.ObtenerDato("V_ROL"));
				}
				bl.setMenu(tmenu);
				bl.setMsg(tmsg);
				accesoe.add(bl);
				con.DesconectarSAP();
				if (accesoe.size() > 0) {
					return accesoe;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE LISTA USUARIOS SAP */
	public List<BeanIdentificacion> identificacion() {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_GET_VENDOR");
				con.EjecutarRFC();
				con.CreaTabla("TI_OUTPUT");
				@SuppressWarnings("rawtypes")
				List identificaciones = con.ObtenerDatosTabla();
				List<BeanIdentificacion> identificacionese = new ArrayList<BeanIdentificacion>();
				BeanIdentificacion bi = null;
				for (int i = 0; i < identificaciones.size(); i++) {
					String cadena = String.valueOf(identificaciones.get(i));
					String[] temporal = cadena.split("¬");
					bi = new BeanIdentificacion();
					bi.setStrId(temporal[1]);
					bi.setStrNombre(temporal[2]);
					if (temporal.length == 3) {
						bi.setStrC1("");
						bi.setStrC2("");
					}
					if (temporal.length == 4) {
						bi.setStrC1(temporal[3]);
						bi.setStrC2("");
					}
					if (temporal.length == 5) {
						bi.setStrC1(temporal[3]);
						bi.setStrC2(temporal[4]);
					}
					identificacionese.add(bi);
				}
				con.DesconectarSAP();
				if (identificacionese.size() > 0) {
					return identificacionese;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE LISTA USUARIOS DE LA APLICACIÓN */
	public List<BeanUsuario> buscaUsuario(String nomUsu) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_GET_VENDOR");
				con.EjecutarRFC();
				con.CreaTabla("TI_DIVISION");
				@SuppressWarnings("rawtypes")
				List lstDivision = con.ObtenerDatosTabla();
				HashMap<String, String> mapResultadoUsuarioDivision = new HashMap<String, String>();
				for (int i = 0; i < lstDivision.size(); i++) {
					String cadena = String.valueOf(lstDivision.get(i));
					String[] temporal = cadena.split("¬");
					mapResultadoUsuarioDivision.put(Long.parseLong(temporal[1]) + "", temporal[2]);
				}
				con.RegistrarRFC("ZSD_RFC_USER_FIND_USER_V1");
				con.IngresarDatosInput(nomUsu, "IV_USER_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_USER");
				@SuppressWarnings("rawtypes")
				List usuarios = con.ObtenerDatosTabla();
				List<BeanUsuario> usuariose = new ArrayList<BeanUsuario>();
				BeanUsuario bu = null;
				for (int i = 0; i < usuarios.size(); i++) {
					String cadena = String.valueOf(usuarios.get(i));
					String[] temporal = cadena.split("¬");
					bu = new BeanUsuario();
					bu.setStrMandante(temporal[1]);
					bu.setStrIdUsuario(temporal[2]);
					bu.setStrNomUsuario(temporal[3]);
					bu.setStrClaUsuario(temporal[5]);
					bu.setStrFecCre(temporal[6]);
					bu.setStrFecUltAccSis(temporal[7]);
					bu.setStrHorUltAccSis(temporal[8]);
					bu.setStrIntento(temporal[15]);
					bu.setStrBloqueado(temporal[16]);
					bu.setStrDivision(mapResultadoUsuarioDivision.get(Long.parseLong(temporal[21]) + ""));
					if (temporal[9].trim().equals("X")) {
						bu.setStrCambioClave("0");
					} else {
						bu.setStrCambioClave("1");
					}
					bu.setStrIdentificacion(temporal[21]);
					bu.setStrNomApe(temporal[23]);
					usuariose.add(bu);
				}
				con.DesconectarSAP();
				if (usuariose.size() > 0) {
					return usuariose;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE LISTA ROLES POR USUARIO */
	public List<BeanRolUsuario> listaRolesUsuario(String idUsu) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_LIST_ROLES");
				con.IngresarDatosInput(idUsu, "IV_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_ROLE_LIST");
				@SuppressWarnings("rawtypes")
				List rolesUsuario = con.ObtenerDatosTabla();
				List<BeanRolUsuario> rolesusuariose = new ArrayList<BeanRolUsuario>();
				BeanRolUsuario bru = null;
				String cadena;
				for (int i = 0; i < rolesUsuario.size(); i++) {
					cadena = "";
					cadena = String.valueOf(rolesUsuario.get(i));
					if (cadena.charAt(cadena.length() - 1) == 'X')
						cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bru = new BeanRolUsuario();
					bru.setStrIdRol(temporal[2]);
					bru.setStrNomRol(temporal[3]);
					if (temporal.length > 4)
						bru.setStrC(temporal[4]);
					rolesusuariose.add(bru);
				}
				con.DesconectarSAP();
				if (rolesusuariose.size() > 0) {
					return rolesusuariose;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE DESBLOQUEA UN USUARIO */
	public boolean desbloqueaUsuario(String idUsu) {
		boolean resultado;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_UNLOCK_USER");
				con.IngresarDatosInput(idUsu, "IV_ID");
				con.EjecutarRFC();
				con.DesconectarSAP();
				resultado = true;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				resultado = false;
			}
			return resultado;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}

	/* MÉTODO QUE ELIMINA USUARIO */
	public boolean eliminaUsuario(String idUsu) {
		boolean resultado;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_DEL_USER");
				con.IngresarDatosInput(idUsu, "IV_ID");
				con.EjecutarRFC();
				con.DesconectarSAP();
				resultado = true;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				resultado = false;
			}
			return resultado;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}

	/* MÉTODO QUE INGRESA MODIFICA USUARIO */
	public List<String> ingresaModificaUsuario(String idUsu, String nomUsu, String claUsu, String conClaUsu, String identificacion) {
		ConexionSAP con;
		String idUsuario;
		List<String> datos;
		try {
			con = Conexiones.getConexionSAP();
			con.RegistrarRFC("ZSD_RFC_USER_SET_USER_V1");
			con.CreaTabla("TS_USER");
			con.IngresarDatoTabla(idUsu, "ID", 1);
			con.IngresarDatoTabla(nomUsu, "BNAME", 1);
			con.IngresarDatoTabla(claUsu, "BCODE", 1);
			con.IngresarDatoTabla(identificacion, "VENDOR_ID", 1);
			con.IngresarDatosInput(conClaUsu, "IV_CONFIRMATION");
			con.EjecutarRFC();
			datos = new ArrayList<String>();
			idUsuario = con.ObtenerDato("E_ID");
			datos.add(idUsuario);
			con.CreaTabla("T_MSG");
			@SuppressWarnings({ "rawtypes" })
			List msg = con.ObtenerDatosTabla();
			for (int i = 0; i < msg.size(); i++) {
				String cadena = String.valueOf(msg.get(i));
				String[] temporal = cadena.split("¬");
				datos.add(temporal[2]);
			}
			con.DesconectarSAP();
			return datos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			datos = null;
			return datos;
		}
	}

	/* MÉTODO QUE ASIGNA ROL A UN USUARIO */
	public void asignaRolUsuario(List<String> roles, String idUsu) {
		int x;
		x = 1;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_ASSIGN_ROLE");
				con.CreaTabla("T_ROLE_LIST");
				for (String r : roles) {
					con.IngresarDatoTabla(r, "PROFILE_ID", x);
					con.IngresarDatoTabla("X", "CHECK_1", x);
					x++;
				}
				con.IngresarDatosInput(idUsu, "IV_ID");
				con.EjecutarRFC();
				con.DesconectarSAP();
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	/* CONVERSIÓN A DD.MM.YYYY */
	public String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return e.getMessage();
		}
	}
}