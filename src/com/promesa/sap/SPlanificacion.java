package com.promesa.sap;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import util.ConexionSAP;

import com.promesa.main.Promesa;
import com.promesa.planificacion.bean.*;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.util.*;

public class SPlanificacion {

	/* MÉTODO QUE OBTIENE PLANIFICACIONES POR SUPERVISOR */
	public List<BeanPlanificacion> planificaciones(String idSup) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_FIND_IDSUP");
				con.IngresarDatosInput(idSup, "ID_SUPERVISOR");
				con.EjecutarRFC();
				con.CreaTabla("ETT_VISIT_PLAN");
				@SuppressWarnings("rawtypes")
				List p = con.ObtenerDatosTabla();
				List<BeanPlanificacion> pe = new ArrayList<BeanPlanificacion>();
				FechaHora objFH = new FechaHora();
				BeanPlanificacion bp = null;
				final Properties props = new Properties();
				InputStreamReader in = new InputStreamReader(new FileInputStream("conexion.properties"), "UTF-8");
				props.load(in);
				for (int i = 0; i < p.size(); i++) {
					String cadena = String.valueOf(p.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bp = new BeanPlanificacion();
					bp.setStrIdPlan(temporal[1]);
					bp.setStrIdVendedor(temporal[2]);
					bp.setStrIdJefe(temporal[3]);
					bp.setStrFechaInicio(objFH.convierteFecha(temporal[4]));
					bp.setStrFechaFin(objFH.convierteFecha(temporal[5]));
					bp.setStrHora(temporal[6]);
					bp.setStrIdCliente(temporal[7]);
					bp.setStrIdFrecuencia(temporal[10]);
					bp.setStrNombreCliente(temporal[13]);
					bp.setStrMandante(props.getProperty("sap.mandante").trim());
					pe.add(bp);
				}
				in.close();
				con.DesconectarSAP();
				if (pe.size() > 0) {
					return pe;
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

	/* MÉTODO QUE OBTIENE RELACIÓN DE FERIADOS */
	public List<BeanFeriado> feriados() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Feriado");
		ConexionSAP con = null;
		try {
			con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_FERIADOS");
				con.EjecutarRFC();
				con.CreaTabla("T_FERIADO");
				@SuppressWarnings("rawtypes")
				List f = con.ObtenerDatosTabla();
				List<BeanFeriado> fe = new ArrayList<BeanFeriado>();
				BeanFeriado bf = null;
				for (int i = 0; i < f.size(); i++) {
					String cadena = String.valueOf(f.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bf = new BeanFeriado();
					bf.setStrSigFeriado(temporal[2]);
					bf.setStrMesFeriado(temporal[3]);
					bf.setStrDiaFeriado(temporal[4]);
					bf.setStrOriFeriado(temporal[10]);
					fe.add(bf);
				}
				con.DesconectarSAP();
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				if (fe.size() > 0) {
					return fe;
				} else {
					return null;
				}
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				Promesa.getInstance().mostrarAvisoSincronizacion("");
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* EMPLEADOS - EMPLEADOS CLIENTES - CLIENTES */
	@SuppressWarnings("rawtypes")
	public List<BeanEncapsulado> empleadosClientes(String idSup) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_OLD_CUSTOMER_FIND2");
				con.IngresarDatosInput(idSup, "ID_SUPERVISOR");
				con.EjecutarRFC();
				con.CreaTabla("ET_KNVP");
				List ec = con.ObtenerDatosTabla();
				List<BeanEncapsulado> e = new ArrayList<BeanEncapsulado>();
				BeanEncapsulado be = null;
				for (int i = 0; i < ec.size(); i++) {
					String cadena = String.valueOf(ec.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					be = new BeanEncapsulado();
					be.setStrCodCli(temporal[1]);
					be.setStrNomCli(temporal[2]);
					be.setStrCodVen(temporal[3]);
					be.setStrNomVen(temporal[4]);
					be.setStrCodSup(temporal[5]);
					be.setStrNomSup(temporal[6]);
					e.add(be);
				}
				con.DesconectarSAP();
				if (e.size() > 0) {
					return e;
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

	/* MÉTODO QUE OBTIENE RELACIÓN DE VENDEDORES */
	public List<BeanEmpleado> listaVendedor(String idSup) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_USER_GET_TEAM");
				con.CreaTabla("IS_USER_INFO");
				con.IngresarDatoTabla(idSup, "VENDOR_ID", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_TEAM");
				@SuppressWarnings("rawtypes")
				List vendedores = con.ObtenerDatosTabla();
				List<BeanEmpleado> vendedorese = new ArrayList<BeanEmpleado>();
				BeanEmpleado be = null;
				for (int i = 0; i < vendedores.size(); i++) {
					String cadena = String.valueOf(vendedores.get(i));
					String[] temporal = cadena.split("¬");
					be = new BeanEmpleado();
					be.setStrNombreEmpleado(temporal[4]);
					be.setStrIdEmpleado(temporal[21]);
					vendedorese.add(be);
				}
				con.DesconectarSAP();
				if (vendedorese.size() > 0) {
					return vendedorese;
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

	/* MÉTODO QUE OBTIENE RELACIÓN DE CLIENTES */
	public List<BeanCliente> listaClientePlanificacion(String idVen) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND3");
				con.CreaTabla("IS_VENDOR");
				con.IngresarDatoTabla(idVen, "VENDOR_ID", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_CUSTOMER");
				@SuppressWarnings("rawtypes")
				List clientes = con.ObtenerDatosTabla();
				List<BeanCliente> clientese = new ArrayList<BeanCliente>();
				BeanCliente bc = null;
				for (int i = 0; i < clientes.size(); i++) {
					String cadena = String.valueOf(clientes.get(i));
					String[] temporal = cadena.split("¬");
					bc = new BeanCliente();
					bc.setStrIdCliente(temporal[1]);
					bc.setStrCompaniaCliente(temporal[2]);
					clientese.add(bc);
				}
				con.DesconectarSAP();
				if (clientese.size() > 0) {
					return clientese;
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

	/* MÉTODO QUE OBTIENE RELACIÓN DE CLIENTES */
	public List<BeanCliente> listaClienteVisita(String idVen) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND2");
				con.CreaTabla("IS_VENDOR");
				con.IngresarDatoTabla(idVen, "VENDOR_ID", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_CUSTOMER");
				@SuppressWarnings("rawtypes")
				List clientes = con.ObtenerDatosTabla();
				List<BeanCliente> clientese = new ArrayList<BeanCliente>();
				SqlClienteImpl sql = new SqlClienteImpl();
				for (int i = 0; i < clientes.size(); i++) {
					String cadena = String.valueOf(clientes.get(i));
					String[] temporal = cadena.split("¬");
					String idCliente = "";
					try {
						idCliente = "" + Long.parseLong(temporal[1]);
					} catch (Exception e) {
						idCliente = temporal[1];
					}
					BeanCliente bc = sql.buscarCliente(idCliente);
					bc.setStrIdCliente(temporal[1]);
					bc.setStrCompaniaCliente(temporal[2]);
					clientese.add(bc);
				}
				con.DesconectarSAP();
				if (clientese.size() > 0) {
					return clientese;
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

	/* MÉTODO QUE REGISTRA UNA PLANIFICACION */
	public String ingresaPlanificacion(String idVen, String idSup, String fecIni, String fecFin, String hora, String idCli, String idFre) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_SAVE4");
				con.CreaTabla("IS_VISIT_PLAN");
				con.IngresarDatoTabla(idVen, "VENDOR_ID", 1);
				con.IngresarDatoTabla(idSup, "CHIEF_ID", 1);
				con.IngresarDatoTabla(fecIni, "BEGDA", 1);
				con.IngresarDatoTabla(fecFin, "ENDDA", 1);
				con.IngresarDatoTabla(hora, "TIME", 1);
				con.IngresarDatoTabla(idCli, "CUST_ID", 1);
				con.IngresarDatoTabla(idFre, "FRECUENCY", 1);
				con.EjecutarRFC();
				String idPla = con.ObtenerDato("EV_VISIT_PLAN_ID");
				con.DesconectarSAP();
				if (idPla != null) {
					return idPla;
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

	/* MÉTODO QUE LISTA PLANIFICACIONES */
	public List<BeanPlanificacion> listaPlanificacion(String idVen, String idCli) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_FIND_V1");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(idVen, "VENDOR_ID", 1);
				con.IngresarDatoTabla(idCli, "CUST_ID", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_VISIT_PLAN");
				@SuppressWarnings("rawtypes")
				List planificaciones = con.ObtenerDatosTabla();
				List<BeanPlanificacion> planificacionese = new ArrayList<BeanPlanificacion>();
				BeanPlanificacion bp = null;
				for (int i = 0; i < planificaciones.size(); i++) {
					String cadena = String.valueOf(planificaciones.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bp = new BeanPlanificacion();
					bp.setStrIdPlan(temporal[1]);
					bp.setStrIdVendedor(temporal[2]);
					bp.setStrIdJefe(temporal[3]);
					bp.setStrFechaInicio(temporal[4]);
					bp.setStrFechaFin(temporal[5]);
					bp.setStrHora(temporal[6]);
					bp.setStrIdCliente(temporal[7]);
					bp.setStrIdFrecuencia(temporal[10]);
					bp.setStrFrecuencia(temporal[11]);
					bp.setStrDia(temporal[12]);
					bp.setStrNombreCliente(temporal[13]);
					planificacionese.add(bp);
				}
				con.DesconectarSAP();
				if (planificacionese.size() > 0) {
					return planificacionese;
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

	/* MÉTODO QUE ACTUALIZA UNA PLANIFICACIÓN */
	public String actualizaPlanificacion(String idPla, String idCli, String fecIni, String hora, String idFre) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_UPDATE3");
				con.CreaTabla("IS_VISIT_PLAN");
				con.IngresarDatoTabla(idPla, "PLAN_ID", 1);
				con.IngresarDatoTabla(idCli, "CUST_ID", 1);
				con.IngresarDatoTabla(fecIni, "BEGDA", 1);
				con.IngresarDatoTabla(hora, "TIME", 1);
				con.IngresarDatoTabla(idFre, "FRECUENCY", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				@SuppressWarnings("rawtypes")
				List mensaje = con.ObtenerDatosTabla();
				String cadena = String.valueOf(mensaje.get(0));
				String[] temporal = cadena.split("¬");
				String msg = temporal[3];
				con.DesconectarSAP();
				return msg;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE ELIMINA UNA PLANIFICACION */
	public boolean eliminaPlanificacion(String idPla, String idVen, String idCli) {
		boolean resultado;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_DELETE3");
				con.CreaTabla("IS_VISIT_PLAN");
				con.IngresarDatoTabla(idPla, "PLAN_ID", 1);
				con.IngresarDatoTabla(idVen, "VENDOR_ID", 1);
				con.IngresarDatoTabla(idCli, "CUST_ID", 1);
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

	/* MÉTODO QUE VERIFICA SI EXISTE O NO VISITAS */
	public List<BeanPlanificacion> actualizaVisitas(String idVen) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_VERIFICA3");
				con.IngresarDatosInput(idVen, "IV_VENDOR_ID");
				con.EjecutarRFC();
				con.CreaTabla("ET_VIST_PLAN");
				@SuppressWarnings("rawtypes")
				List planificaciones = con.ObtenerDatosTabla();
				List<BeanPlanificacion> planificacionese = new ArrayList<BeanPlanificacion>();
				BeanPlanificacion bp = null;
				for (int i = 0; i < planificaciones.size(); i++) {
					String cadena = String.valueOf(planificaciones.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bp = new BeanPlanificacion();
					bp.setStrIdPlan(temporal[2]);
					bp.setStrIdCliente(temporal[3]);
					bp.setStrIdJefe(temporal[4]);
					bp.setStrFechaInicio(temporal[5]);
					bp.setStrFechaFin(temporal[6]);
					bp.setStrHora(temporal[7]);
					bp.setStrIdVendedor(temporal[8]);
					bp.setStrIdFrecuencia(temporal[10]);
					bp.setStrEstado(temporal[11]);
					planificacionese.add(bp);
				}
				con.DesconectarSAP();
				if (planificacionese.size() > 0) {
					return planificacionese;
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

	/* MÉTODO QUE REGISTRA UNA VISITA */
	public String ingresaVisitaFueraDeRuta(String idVen, String idCli, List<String> lstFechas, String hora, String tipo, String idPla) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_SAVE1");
				con.CreaTabla("IS_VISIT");
				if (idPla.equals("")) {
					con.IngresarDatoTabla(Util.completarCeros(10, idVen), "VENDOR_ID", 1);
					con.IngresarDatoTabla(Util.completarCeros(10, idCli), "CUST_ID", 1);
					con.IngresarDatoTabla(lstFechas.get(0), "V_DATE", 1);
					con.IngresarDatoTabla(hora, "TIME_PLANNED", 1);
					con.IngresarDatoTabla(tipo, "TYPE", 1);
				} else {
					for (int i = 0; i < lstFechas.size(); i++) {
						con.IngresarDatoTabla(Util.completarCeros(10, idVen), "VENDOR_ID", i + 1);
						con.IngresarDatoTabla(Util.completarCeros(10, idCli), "CUST_ID", i + 1);
						con.IngresarDatoTabla("" + lstFechas.get(i).charAt(6) + lstFechas.get(i).charAt(7)
								+ lstFechas.get(i).charAt(8) + lstFechas.get(i).charAt(9)
								+ lstFechas.get(i).charAt(3) + lstFechas.get(i).charAt(4)
								+ lstFechas.get(i).charAt(0) + lstFechas.get(i).charAt(1), "V_DATE", i + 1);
						con.IngresarDatoTabla(hora, "TIME_PLANNED", i + 1);
						con.IngresarDatoTabla(tipo, "TYPE", i + 1);
						con.IngresarDatoTabla(idPla, "PLAN_ID", i + 1);
					}
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				@SuppressWarnings("rawtypes")
				List mensaje = con.ObtenerDatosTabla();
				String cadena = String.valueOf(mensaje.get(0));
				String[] temporal = cadena.split("¬");
				String msg = temporal[3];
				con.DesconectarSAP();
				return msg;
			} else {
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/*
	 * MÉTODO QUE LISTA VISITAS (REPORTES) DE TODOS LOS VENDEDORES POR
	 * SUPERVISOR
	 */
	public List<BeanVisita> reporteVisita02(List<String> lstVen, List<String> lstFecD, List<String> lstFecH) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_AGENDA_DATE_NEXT_M_V1");
				con.CreaTabla("IS_AGENDA_SEARCH_OPTIONS");
				for (int i = 0; i < lstVen.size(); i++) {
					con.IngresarDatoTabla(lstVen.get(i), "IV_VENDOR_ID", i + 1);
					con.IngresarDatoTabla(lstFecD.get(i), "IV_DATE", i + 1);
					con.IngresarDatoTabla(lstFecH.get(i), "IV_DATE_HIGH", i + 1);
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_AGENDA");
				BeanVisita bv;
				List<BeanVisita> listaVisita = new ArrayList<BeanVisita>();
				@SuppressWarnings("rawtypes")
				List visita = con.ObtenerDatosTabla();
				for (int i = 0; i < visita.size(); i++) {
					String cadena = String.valueOf(visita.get(i));
					String[] values = cadena.split("¬");
					bv = new BeanVisita();
					bv.setStrFechaVisita(convierteFecha(values[4]));
					bv.setStrHora(values[6]);
					bv.setStrIdCliente(values[7]);
					bv.setStrNomCliente(values[8]);
					bv.setStrDirCliente(values[9]);
					bv.setStrTelCliente(values[10]);
					bv.setStrTipo(values[24]);
					listaVisita.add(bv);
				}
				con.DesconectarSAP();
				if (listaVisita.size() > 0) {
					return listaVisita;
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

	/* MÉTODO QUE LISTA VISITAS (REPORTES) */
	public List<BeanVisita> reporteVisita(String codVen, String fecDes, String fecHas) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_AGENDA_DATE_NEXT_1");
				con.IngresarDatosInput(codVen, "IV_VENDOR_ID");
				con.IngresarDatosInput(fecDes, "IV_DATE");
				con.IngresarDatosInput(fecHas, "IV_DATE_HIGH");
				con.EjecutarRFC();
				con.CreaTabla("ET_AGENDA");
				BeanVisita bv;
				List<BeanVisita> listaVisita = new ArrayList<BeanVisita>();
				@SuppressWarnings("rawtypes")
				List visita = con.ObtenerDatosTabla();
				for (int i = 0; i < visita.size(); i++) {
					String cadena = String.valueOf(visita.get(i));
					String[] values = cadena.split("¬");
					bv = new BeanVisita();
					bv.setStrFechaVisita(convierteFecha(values[4]));
					bv.setStrHora(values[6]);
					bv.setStrIdCliente(values[7]);
					bv.setStrNomCliente(values[8]);
					bv.setStrDirCliente(values[9]);
					bv.setStrTelCliente(values[10]);
					bv.setStrTipo(values[24]);
					listaVisita.add(bv);
				}
				con.DesconectarSAP();
				if (listaVisita.size() > 0) {
					return listaVisita;
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

	/*
	 * MÉTODO QUE LISTA PLANIFICACIONES (REPORTES) DE TODOS LOS VENDEDORES POR
	 * SUPERVISOR
	 */
	public List<BeanPlanificacion> reportePlanificacion02(List<String> lstVen, List<String> lstFecD, List<String> lstFecH) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_FIND_MUL_V1");
				con.CreaTabla("IS_SEARCH_OPTIONS_MUL");
				for (int i = 0; i < lstVen.size(); i++) {
					con.IngresarDatoTabla(lstVen.get(i), "VENDOR_ID", i + 1);
					con.IngresarDatoTabla(lstFecD.get(i), "FROM", i + 1);
					con.IngresarDatoTabla(lstFecH.get(i), "TO", i + 1);
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_VISIT_PLAN");
				@SuppressWarnings("rawtypes")
				List planificaciones = con.ObtenerDatosTabla();
				List<BeanPlanificacion> planificacionese = new ArrayList<BeanPlanificacion>();
				BeanPlanificacion bp = null;
				for (int i = 0; i < planificaciones.size(); i++) {
					String cadena = String.valueOf(planificaciones.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bp = new BeanPlanificacion();
					bp.setStrIdPlan(temporal[1]);
					bp.setStrIdVendedor(temporal[2]);
					bp.setStrIdJefe(temporal[3]);
					bp.setStrFechaInicio(temporal[4]);
					bp.setStrFechaFin(temporal[5]);
					bp.setStrHora(temporal[6]);
					bp.setStrIdCliente(temporal[7]);
					bp.setStrIdFrecuencia(temporal[10]);
					bp.setStrFrecuencia(temporal[11]);
					bp.setStrDia(temporal[12]);
					bp.setStrNombreCliente(temporal[13]);
					planificacionese.add(bp);
				}
				con.DesconectarSAP();
				if (planificacionese.size() > 0) {
					return planificacionese;
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

	/* MÉTODO QUE LISTA PLANIFICACIONES (REPORTES) */
	public List<BeanPlanificacion> reportePlanificacion(String codVen, String fecDes, String fecHas) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VISIT_PLAN_FIND_V2");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(codVen, "VENDOR_ID", 1);
				con.IngresarDatoTabla(fecDes, "FROM", 1);
				con.IngresarDatoTabla(fecHas, "TO", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_VISIT_PLAN");
				@SuppressWarnings("rawtypes")
				List planificaciones = con.ObtenerDatosTabla();
				List<BeanPlanificacion> planificacionese = new ArrayList<BeanPlanificacion>();
				BeanPlanificacion bp = null;
				for (int i = 0; i < planificaciones.size(); i++) {
					String cadena = String.valueOf(planificaciones.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bp = new BeanPlanificacion();
					bp.setStrIdPlan(temporal[1]);
					bp.setStrIdVendedor(temporal[2]);
					bp.setStrIdJefe(temporal[3]);
					bp.setStrFechaInicio(temporal[4]);
					bp.setStrFechaFin(temporal[5]);
					bp.setStrHora(temporal[6]);
					bp.setStrIdCliente(temporal[7]);
					bp.setStrIdFrecuencia(temporal[10]);
					bp.setStrFrecuencia(temporal[11]);
					bp.setStrDia(temporal[12]);
					bp.setStrNombreCliente(temporal[13]);
					planificacionese.add(bp);
				}
				con.DesconectarSAP();
				if (planificacionese.size() > 0) {
					return planificacionese;
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

	/* 
	 * CONVERSIÓN A DD.MM.YYYY
	 */
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