package com.promesa.sap;

import java.util.ArrayList;
import java.util.List;
import util.ConexionSAP;

import com.promesa.ferias.Asistencia;
import com.promesa.ferias.BeanGremioEInstitutoEducativo;
import com.promesa.ferias.BeanProfesion;
import com.promesa.ferias.Cliente;
import com.promesa.ferias.LugarFeria;
import com.promesa.ferias.Relacion;
import com.promesa.internalFrame.ferias.IFerias;
import com.promesa.main.Promesa;
import com.promesa.util.Conexiones;
import com.promesa.util.Constante;
import com.promesa.util.Util;

/*
 * @author	MARCELO MOYANO
 * 
 * 			CLASE QUE REALIZA LAS CONECCIÓN
 * 			A SAP PARA ZINCRONIZAR TODA LA
 * 			INFORMACIÓN NECESARIA PARA EL
 * 			MODULO DE FERIAS
 */
public class SFerias {
	/*
	 * 	CARGA LOS LUGARES DE FERIA DESDE SAP
	 */
	@SuppressWarnings("rawtypes")
	public List<LugarFeria> consultaLugarFerias(IFerias instancia, String claveIdioma, String codigoPais){
		try{
			List<LugarFeria> listFerias = new ArrayList<LugarFeria>();
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				instancia.registrarMensaje("Se conecto a SAP.\n");
				con.RegistrarRFC("ZSD_RFC_CONSLUGAR_FERIA");
				con.IngresarDatosInput(claveIdioma, "IS_SPRAS");
				con.IngresarDatosInput(codigoPais, "IS_LAND1");
				con.IngresarDatosInput("", "IS_REGIO");
				con.IngresarDatosInput("", "IS_CITYC");
				con.EjecutarRFC();
				con.CreaTabla("TI_LUGARES");
				List lugares = con.ObtenerDatosTabla();
				LugarFeria lferia = null;
				if(lugares.size() > 0){
					for(int i = 0; i < lugares.size(); i++){
						String cadena = String.valueOf(lugares.get(i));
						String[] temporal = cadena.split("¬");
						lferia = new LugarFeria();
						lferia.setCodigoCiudad(temporal[1]);
						lferia.setNombreCiudad(temporal[2]);
						listFerias.add(lferia);
					}
					con.DesconectarSAP();
					instancia.registrarMensaje("");
					return listFerias;
				}else
					return null;
			}else
				return null;
		}catch(Exception ex){
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	/*
	 * 	CONSULTA LAS ASISTENCIA EN SAP
	 */
	@SuppressWarnings("rawtypes")
	public List<Asistencia> consultaFeriasAsistencia(String codigoUsuario, String strFechaDesde, String strFechaHasta){
		try {
			List<Asistencia> listaAsistencia = null;
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				con.RegistrarRFC("ZSD_RFC_CONSULTA_FERIA");
				con.IngresarDatosInput(codigoUsuario, "IS_UNAME");
				con.IngresarDatosInput(strFechaDesde, "IS_FEC_INI");
				con.IngresarDatosInput(strFechaHasta, "IS_FEC_FIN");
				con.EjecutarRFC();
				con.CreaTabla("IT_INFO_FERIAS");
				List registro = con.ObtenerDatosTabla();
				Asistencia asiste = null;
				if(registro.size() > 0) {
					listaAsistencia = new ArrayList<Asistencia>();
					for(int i = 0; i < registro.size(); i++){
						String cadena = String.valueOf(registro.get(i));
						String[] temporal = cadena.split("¬");
						asiste = new Asistencia();
						asiste.setEjercicio(temporal[2]);
						asiste.setSecuencia(Util.eliminarCerosInicios(temporal[3]).toString().trim());
						asiste.setCiudadFeria(temporal[4].toString().trim());
						asiste.setRelacion(temporal[5].toString().trim());
						asiste.setCodigoCliente(Util.eliminarCerosInicios(temporal[6]).toString().trim());
						asiste.setCiudadCliente(temporal[7].toString().trim());
						asiste.setInvitado(temporal[8].toString().trim());
						asiste.setCorreo(temporal[9].toString().trim());
						asiste.setFacebook(temporal[10].toString().trim());
						asiste.setTwitter(temporal[11].toString().trim());
						asiste.setTelefono(temporal[12].toString().trim());
						asiste.setCelular(temporal[13].toString().trim());
						asiste.setOtroTelefono(temporal[14].toString().trim());
						asiste.setInteresEspeciales(temporal[16].toString().trim());
						asiste.setGremio(temporal[18].toString().trim());
						asiste.setProfesion(temporal[17].toString().trim());
						asiste.setObservacion(temporal[15].toString().trim());
						asiste.setFechaAsistencia(temporal[20].toString().trim());
						asiste.setHoraAsistencia(temporal[21].toString().trim());
						asiste.setIdUsuario(Promesa.datose.get(0).getStrUsuario());
						asiste.setSource("" + Constante.FROM_SAP);
						listaAsistencia.add(asiste);
					}
					con.DesconectarSAP();
					return listaAsistencia;
				}else 
					return null;
			}else
				return null;
		}catch (Exception ex) {
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	/*
	 * 	REGISTRA TODAS LAS ASISTENCIA EN SAP
	 */
	@SuppressWarnings({ "unchecked" })
	public String[] registrarAsistencia(List<Asistencia> listaAsistencia) {
		try {
			String cadena = "";
			String[] valores = new String[3];
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null) {
				con.RegistrarRFC("ZSD_RFC_GRABAR_FERIA");
				con.CreaTabla("IT_INFO_FERIAS");
				int i  = 1;
				for(Asistencia asiste : listaAsistencia){
					con.IngresarDatoTabla(asiste.getEjercicio(), "GJAHR", i);
					con.IngresarDatoTabla(asiste.getSecuencia(), "NRSECU", i);
					con.IngresarDatoTabla(asiste.getCiudadFeria(), "CITYC", i);
					con.IngresarDatoTabla(asiste.getRelacion(), "RELATIONSHIP", i);
					if(asiste.getCodigoCliente().equals("")){
						con.IngresarDatoTabla(asiste.getCodigoCliente(), "KUNNR", i);
					}else {
						String strCodigoCliente = Util.completarCeros(10, asiste.getCodigoCliente());
						con.IngresarDatoTabla(strCodigoCliente, "KUNNR", i);
					}
					con.IngresarDatoTabla(asiste.getCiudadCliente(), "STREET", i);
					con.IngresarDatoTabla(asiste.getInvitado(), "GUEST", i);
					con.IngresarDatoTabla(asiste.getCorreo(), "SMTP_ADDR", i);
					con.IngresarDatoTabla(asiste.getFacebook(), "SOCIAL_ADDR", i);
					con.IngresarDatoTabla(asiste.getTwitter(), "SOCIAL_ADDR1", i);
					con.IngresarDatoTabla(asiste.getTelefono(), "TEL_NUMBER", i);
					con.IngresarDatoTabla(asiste.getCelular(), "MOB_NUMBER", i);
					con.IngresarDatoTabla(asiste.getOtroTelefono(), "TEL_OTHER_NUM", i);
					con.IngresarDatoTabla(asiste.getObservacion(), "OBSERVATION", i);
					con.IngresarDatoTabla(asiste.getInteresEspeciales(), "INTERES", i);
					con.IngresarDatoTabla(asiste.getGremio(), "GREMIOS_INSTIT", i);
					con.IngresarDatoTabla(asiste.getProfesion(), "PROFESION", i);
					con.IngresarDatoTabla(asiste.getIdUsuario(), "UNAME", i);
					con.IngresarDatoTabla(asiste.getFechaAsistencia(), "DATUM", i);
					con.IngresarDatoTabla(asiste.getHoraAsistencia(), "UZEIT", i);
					i++;
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				List<String> ur = con.ObtenerDatosTabla();
				if(ur != null && !ur.isEmpty()) {
					cadena =  ur.get(0);
					valores = String.valueOf(cadena).split("[¬]");
				}
				return valores;
			} else {
				return null;
			}
		}catch (Exception ex) {
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	/*
	 * 	EDITA UNAS ASISTENCIA EN SAP
	 */
	@SuppressWarnings("unchecked")
	public String[] editarAsistencia(List<Asistencia> ListaAsistencia) {
		try {
			String cadena = "";
			String[] valores = new String[3];
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null) {
				con.RegistrarRFC("ZSD_RFC_MODIF_FERIA");
				con.CreaTabla("TI_REGISTRO");
				int i = 1;
				for(Asistencia asiste : ListaAsistencia){
					con.IngresarDatoTabla(asiste.getEjercicio(), "GJAHR", i);
					con.IngresarDatoTabla(asiste.getSecuencia(), "NRSECU", i);
					con.IngresarDatoTabla(asiste.getCiudadFeria(), "CITYC", i);
					con.IngresarDatoTabla(asiste.getRelacion(), "RELATIONSHIP", i);
					con.IngresarDatoTabla(Util.completarCeros(10, asiste.getCodigoCliente()), "KUNNR", i);
					con.IngresarDatoTabla(asiste.getCiudadCliente(), "STREET", i);
					con.IngresarDatoTabla(asiste.getInvitado(), "GUEST", i);
					con.IngresarDatoTabla(asiste.getCorreo(), "SMTP_ADDR", i);
					con.IngresarDatoTabla(asiste.getFacebook(), "SOCIAL_ADDR", i);
					con.IngresarDatoTabla(asiste.getTwitter(), "SOCIAL_ADDR1", i);
					con.IngresarDatoTabla(asiste.getTelefono(), "TEL_NUMBER", i);
					con.IngresarDatoTabla(asiste.getCelular(), "MOB_NUMBER", i);
					con.IngresarDatoTabla(asiste.getOtroTelefono(), "TEL_OTHER_NUM", i);
					con.IngresarDatoTabla(asiste.getObservacion(), "OBSERVATION", i);
					con.IngresarDatoTabla(asiste.getInteresEspeciales(), "INTERES", i);
					con.IngresarDatoTabla(asiste.getGremio(), "GREMIOS_INSTIT", i);
					con.IngresarDatoTabla(asiste.getProfesion(), "PROFESION", i);
					con.IngresarDatoTabla(asiste.getIdUsuario(), "UNAME", i);
					con.IngresarDatoTabla(asiste.getFechaAsistencia(), "DATUM", i);
					con.IngresarDatoTabla(asiste.getHoraAsistencia(), "UZEIT", i);
					i++;
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				List<String> ur = con.ObtenerDatosTabla();
				if(ur != null && !ur.isEmpty()) {
					cadena =  ur.get(0);
					valores = String.valueOf(cadena).split("[¬]");
				}
				return valores;
			} else {
				return null;
			}
		}catch (Exception ex) {
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	/*
	 * 	CARGA TODOS LOS CLIENTES DESDE SAP
	 */
	@SuppressWarnings("rawtypes")
	public List<Cliente> cargarClienteFeriasSap(IFerias instancia){
		List<Cliente> clientes = new ArrayList<Cliente>();
		Cliente c = null;
		ConexionSAP con;
		try {
			con = Conexiones.getConexionSAP();
			if(con != null){
				instancia.registrarMensaje("Se conecto a SAP.\n");
				con.RegistrarRFC("ZSD_RFC_INFOCLI_FERIA");
				con.EjecutarRFC();
				con.CreaTabla("TI_CLIENTES");
				List listaClientes = con.ObtenerDatosTabla();
				if(listaClientes.size() > 0){
					for(int i = 0; i < listaClientes.size(); i++){
						String cadena = String.valueOf(listaClientes.get(i));
						String[] temporal = cadena.split("¬");
						c = new Cliente();
						if(temporal.length > 8){
							c.setCodigoCliente(Util.eliminarCerosInicios(temporal[1]).trim());
							c.setNombreCliente(temporal[3].replaceAll("'", "''"));
							c.setRazonSocial(temporal[2].replaceAll("'", "''"));
							c.setRUC(temporal[4]);
							c.setTelefono(temporal[5]);
							c.setCelular(temporal[6]);
							c.setOtroTelefono("");
							c.setCiudad(temporal[7]);
							c.setCorreo(temporal[8]);
						}else if(temporal.length > 5){
							c.setCodigoCliente(Util.eliminarCerosInicios(temporal[1]).trim());
							c.setNombreCliente(temporal[3].replaceAll("'", "''"));
							c.setRazonSocial(temporal[2].replaceAll("'", "''"));
							c.setRUC(temporal[4]);
							c.setTelefono(temporal[5]);
							c.setCelular(temporal[6]);
							c.setOtroTelefono("");
							c.setCiudad(temporal[7]);
							c.setCorreo("");
						}
						clientes.add(c);
					}
					con.DesconectarSAP();
					instancia.registrarMensaje("");
					return clientes;
				}else 
					return null;
			}else
				return null;
		} catch (Exception ex) {
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	/*
	 * 	CARGA TODOS LOS TIPOS DE RELACIONES DESDE SAP
	 */
	@SuppressWarnings("rawtypes")
	public List<Relacion> consultaRelacion(IFerias instancia){
		try {
			List<Relacion> relaciones = null;
			Relacion r = null;
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				instancia.registrarMensaje("Se conecto a SAP.\n");
				con.RegistrarRFC("ZSD_RFC_RELACLI_FERIA");
				con.EjecutarRFC();
				con.CreaTabla("T_REL");
				List listaRelaciones = con.ObtenerDatosTabla();
				if(listaRelaciones.size() > 0){
					relaciones = new ArrayList<Relacion>();
					for(int i = 0; i < listaRelaciones.size(); i++){
						String cadena = String.valueOf(listaRelaciones.get(i));
						String[] temporal = cadena.split("¬");
						r = new Relacion();
						r.setCodigoRelacion(temporal[1]);
						r.setTipoRelacion(temporal[2]);
						relaciones.add(r);
					}
					con.DesconectarSAP();
					instancia.registrarMensaje("");
				}
			}
			return relaciones;
		}catch (Exception ex){
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	/*
	 * 	CARGA TODAS LAS PROFECIONES DESDE SAP
	 */
	@SuppressWarnings("rawtypes")
	public List<BeanProfesion> cargarProfesion(IFerias instancia){
		List<BeanProfesion> listaProfesion = null;
		try{
			BeanProfesion bp = null;
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				instancia.registrarMensaje("Se conecto a SAP.\n");
				con.RegistrarRFC("ZSD_RFC_PROF_FERIA");
				con.EjecutarRFC();
				con.CreaTabla("T_CONS7T");
				List profesionales = con.ObtenerDatosTabla();
				if(profesionales.size() > 0){
					listaProfesion = new ArrayList<BeanProfesion>();
					for(int i = 0; i < profesionales.size(); i++){
						String cadena = String.valueOf(profesionales.get(i));
						String[] temporal = cadena.split("¬");
						bp = new BeanProfesion();
						bp.setCodigoProfesion(temporal[3].trim());
						bp.setNombreProfesion(temporal[4].trim());
						listaProfesion.add(bp);
					}
					con.DesconectarSAP();
					instancia.registrarMensaje("");
				}
			}
		}catch (Exception ex){
			Util.mostrarExcepcion(ex);
		}
		return listaProfesion;
	}
	
	/*
	 * 	CARGA TODOS LOS GREMIOS E INSTITUCIONES EDUCATIVAS DESDE SAP
	 */
	@SuppressWarnings("rawtypes")
	public List<BeanGremioEInstitutoEducativo> cargarGremioEInstitucionEducativa(IFerias instancia){
		List<BeanGremioEInstitutoEducativo> listaGremio = null;
		try{
			BeanGremioEInstitutoEducativo bp = null;
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				instancia.registrarMensaje("Se conecto a SAP.\n");
				con.RegistrarRFC("ZSD_RFC_GREMIO_INST_FERIA");
				con.EjecutarRFC();
				con.CreaTabla("T_CONS8T");
				List lista = con.ObtenerDatosTabla();
				if(lista.size() > 0){
					listaGremio = new ArrayList<BeanGremioEInstitutoEducativo>();
					for(int i = 0; i < lista.size(); i++){
						String cadena = String.valueOf(lista.get(i));
						String[] temporal = cadena.split("¬");
						bp = new BeanGremioEInstitutoEducativo();
						bp.setCodigoGremioInst(temporal[3].trim());
						bp.setNombreGremioInst(temporal[4].trim());
						listaGremio.add(bp);
					}
					con.DesconectarSAP();
					instancia.registrarMensaje("");
				}
			}
			
		}catch (Exception ex){
			Util.mostrarExcepcion(ex);
		}
		return listaGremio;
	}
}