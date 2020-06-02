package com.promesa.sap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import util.ConexionSAP;

import com.promesa.devoluciones.bean.BeanDevolucion;
import com.promesa.devoluciones.bean.BeanMotivoDevolucion;
import com.promesa.pedidos.bean.BeanPedido;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.planificacion.bean.BeanVisita;
import com.promesa.util.Conexiones;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class SDevoluciones {

	/* CONVERSIÓN A DD.MM.YYYY */
	public String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/* MÉTODO QUE INGRESA SOLICITUD DE DEVOLUCIÓN */
	public String ingresaSolicitud(BeanVisita visita) {
		ConexionSAP con;
		String mensaje = Constante.VACIO;
		try {
			con = Conexiones.getConexionSAP();
			con.RegistrarRFC("ZSD_RFC_ORDER_SAVE_VISIT_RETUR");
			con.CreaTabla("IS_VISIT");
			con.IngresarDatoTabla(visita.getStrIdVendedor(), "VENDOR_ID", 1);
			con.IngresarDatoTabla(visita.getStrIdCliente(), "CUST_ID", 1);
			con.IngresarDatoTabla(Util.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(visita.getStrFechaVisita()), "V_DATE", 1);
			con.IngresarDatoTabla(visita.getStrHoraPlaneada(), "TIME_PLANNED", 1);
			con.IngresarDatoTabla(visita.getStrTipo(), "TYPE", 1);
			con.EjecutarRFC();
			con.CreaTabla("ET_MSG");
			@SuppressWarnings({ "rawtypes" })
			List msg = con.ObtenerDatosTabla();
			for (int i = 0; i < msg.size(); i++) {
				String cadena = String.valueOf(msg.get(i));
				String[] temporal = cadena.split("¬");
				mensaje = temporal[3].trim();
			}
			con.DesconectarSAP();
			return mensaje;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE OBTIENE LISTADO DE SOLICITUDES DE DEVOLUCIONES */
	@SuppressWarnings("rawtypes")
	public List<BeanDevolucion> obtenerSolicitudes(String codVen, String codCli) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_GET_OPEN_RETURNS");
				con.IngresarDatosInput(codVen, "IV_VENDOR_ID");
				con.IngresarDatosInput(codCli, "IV_CUST_ID");
				con.EjecutarRFC();
				con.CreaTabla("ET_OPEN_RETURN");
				List devoluciones = con.ObtenerDatosTabla();
				List<BeanDevolucion> d = new ArrayList<BeanDevolucion>();
				BeanDevolucion bd = null;
				for (int i = 0; i < devoluciones.size(); i++) {
					String cadena = String.valueOf(devoluciones.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bd = new BeanDevolucion();
					String tFR = Constante.VACIO;
					if (!temporal[2].equalsIgnoreCase("null")) {
						tFR = convierteFecha(temporal[2]);
					}
					String tFV = Constante.VACIO;
					if (!temporal[7].equalsIgnoreCase("null")) {
						tFV = convierteFecha(temporal[7]);
					}
					String tHV = Constante.VACIO;
					if (temporal[8].length() > 0) {
						tHV = temporal[8];
					}
					bd.setStrDocVta(temporal[1]);
					bd.setStrFecReg(tFR);
					bd.setStrCodCli(temporal[3]);
					bd.setStrNomCli(temporal[4]);
					bd.setStrDirCli(temporal[5]);
					bd.setStrTelCli(temporal[6]);
					bd.setStrFecVis(tFV);
					bd.setStrHorVis(tHV);
					bd.setStrTipVis(temporal[9]);
					bd.setStrCodVis(temporal[10]);
					d.add(bd);
				}
				con.DesconectarSAP();
				if (d.size() > 0) {
					return d;
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

	/* MÉTODO QUE OBTIENE LISTADO DE PEDIDOS (POR FACTURA) */
	@SuppressWarnings("rawtypes")
	public List<String[]> pedidosPorFactura(String codCli, String numFac) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				String codigoPedido = "0000000000";
				con.RegistrarRFC("ZSD_RFC_ORDER_GET_FROM_BILL");
				con.IngresarDatosInput(codCli, "IV_CUST_ID");
				con.IngresarDatosInput(numFac, "IV_BILL_ID");
				con.EjecutarRFC();
				List<String[]> p = new ArrayList<String[]>();
				p.add(new String[] { "FACTURA", "0000000000" });
				con.CreaTabla("ET_PARTNERS");
				List l1 = con.ObtenerDatosTabla();
				for (int i = 0; i < l1.size(); i++) {
					String cadena = String.valueOf(l1.get(i));
					String[] temporal = cadena.split("¬");
					String[] d1 = new String[temporal.length];
					for (int j = 1; j < temporal.length; j++) {
						d1[j - 1] = temporal[j].trim();
					}
					p.add(d1);
				}
				con.CreaTabla("ET_ORDER_ITEMS");
				List l2 = con.ObtenerDatosTabla();
				for (int i = 0; i < l2.size(); i++) {
					String cadena = String.valueOf(l2.get(i));
					String[] temporal = cadena.split("¬");
					String[] d2 = new String[temporal.length];
					codigoPedido = temporal[2];
					for (int j = 1; j < temporal.length; j++) {
						d2[j - 1] = temporal[j].trim();
					}
					p.add(d2);
				}
				p.add(new String[] { String.valueOf(l1.size()), String.valueOf(l2.size()) });
				con.CreaTabla("ET_MSG");
				List l3 = con.ObtenerDatosTabla();
				if (l3.size() > 0) {
					for (int i = 0; i < l3.size(); i++) {
						String cadena = String.valueOf(l3.get(i));
						String[] temporal = cadena.split("¬");
						String[] d3 = new String[1];
						d3[0] = temporal[3];
						p.add(d3);
					}
				}
				p.get(0)[1] = codigoPedido;
				con.DesconectarSAP();
				if (p.size() > 0) {
					return p;
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

	/* MÉTODO QUE OBTIENE LISTADO DE PEDIDOS (POR # CASO) */
	@SuppressWarnings("rawtypes")
	public List<String[]> pedidosPorCaso(String caso) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				try {
					con.RegistrarRFC("ZSD_RFC_ORDER_GET2");
				} catch (Exception e) {
					return null;
				}
				con.IngresarDatosInput(caso, "IV_SALES_DOC_ID");
				con.EjecutarRFC();
				List<String[]> p = new ArrayList<String[]>();
//				p.add(new String[] { "CASO" });
				con.CreaTabla("ES_ORDER_HEADERS");
				List l1 = con.ObtenerDatosTabla();
				if(l1.size() > 0){
					p.add(new String[] { "CASO" });
					for (int i = 0; i < l1.size(); i++) {
						String cadena = String.valueOf(l1.get(i));
						String[] temporal = cadena.split("¬");
						String[] d1 = new String[temporal.length];
						for (int j = 1; j < temporal.length; j++) {
							d1[j - 1] = temporal[j].trim();
						}
						p.add(d1);
					}
				}
				
				con.CreaTabla("ET_PARTNERS");
				List l2 = con.ObtenerDatosTabla();
				if(l2.size() > 0){
					for (int i = 0; i < l2.size(); i++) {
						String cadena = String.valueOf(l2.get(i));
						String[] temporal = cadena.split("¬");
						String[] d2 = new String[temporal.length];
						for (int j = 1; j < temporal.length; j++) {
							d2[j - 1] = temporal[j].trim();
						}
						p.add(d2);
					}
				}
				con.CreaTabla("ES_ORDER_BUSINESS");
				List l3 = con.ObtenerDatosTabla();
				if(l3.size() > 0){
					for (int i = 0; i < l3.size(); i++) {
						String cadena = String.valueOf(l3.get(i));
						String[] temporal = cadena.split("¬");
						String[] d3 = new String[temporal.length];
						for (int j = 1; j < temporal.length; j++) {
							d3[j - 1] = temporal[j].trim();
						}
						p.add(d3);
					}
				}
				
				con.CreaTabla("ET_ORDER_ITEMS");
				List l4 = con.ObtenerDatosTabla();
				if(l4.size() > 0){
					for (int i = 0; i < l4.size(); i++) {
						String cadena = String.valueOf(l4.get(i));
						String[] temporal = cadena.split("¬");
						String[] d4 = new String[temporal.length];
						for (int j = 1; j < temporal.length; j++) {
							d4[j - 1] = temporal[j].trim();
						}
						p.add(d4);
					}
				}
				if(l1.size() > 0 && l2.size() > 0 && l3.size() > 0 && l4.size() > 0 ){
					p.add(new String[] { String.valueOf(l1.size()), String.valueOf(l4.size()), String.valueOf(l3.size()), String.valueOf(l2.size()) });
				}
				
				con.DesconectarSAP();
				if (p.size() > 0) {
					return p;
				} else {
					return null;
				}
			} else {
				System.out.println("278");
				Mensaje.mostrarError(Constante.MENSAJE_PERDIDA_CONEXION);
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE OBTIENE RELACIÓN DE MOTIVOS DE DEVOLUCIONES */
	public List<BeanMotivoDevolucion> motivosDevoluciones() {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_MOTIVO_DEVOLUCION");
				con.EjecutarRFC();
				con.CreaTabla("ET_TVAUT");
				@SuppressWarnings("rawtypes")
				List md = con.ObtenerDatosTabla();
				List<BeanMotivoDevolucion> mde = new ArrayList<BeanMotivoDevolucion>();
				BeanMotivoDevolucion bmd = null;
				for (int i = 0; i < md.size(); i++) {
					String cadena = String.valueOf(md.get(i));
					cadena = cadena + "¬";
					String[] temporal = cadena.split("¬");
					bmd = new BeanMotivoDevolucion();
					bmd.setStrMandante(temporal[1]);
					bmd.setStrIdioma(temporal[2]);
					bmd.setStrCodMotDev(temporal[3]);
					bmd.setStrMotDev(temporal[4]);
					mde.add(bmd);
				}
				con.DesconectarSAP();
				if (mde.size() > 0) {
					return mde;
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

	@SuppressWarnings("rawtypes")
	public String[] guardarDevolucion(BeanPedido pedido) {
		try {
			BeanPedidoHeader header = pedido.getHeader();
			List<BeanPedidoDetalle> materiales = pedido.getDetalles();
			ConexionSAP con = Conexiones.getConexionSAP();
			con.RegistrarRFC("ZSD_RFC_ORDER_UPDATE_OFF");
			con.IngresarDatosInput(pedido.getCodigoPedido(), "IV_DOC_ID");
//			System.out.println("IV_DOC_ID: " + pedido.getCodigoPedido());
			con.CreaTabla("ORDER_HEADER_IN");
//			System.out.println("ORDER_HEADER_IN");
			con.IngresarDatoTabla("ZD01", "DOC_TYPE", 1);
//			System.out.println("doc type: zd01");
			con.IngresarDatoTabla(header.getSALES_ORG(), "SALES_ORG", 1);
//			System.out.println("SALES_ORG: " + header.getSALES_ORG());
			con.IngresarDatoTabla(header.getDISTR_CHAN(), "DISTR_CHAN", 1);
//			System.out.println("DISTR_CHAN: " + header.getDISTR_CHAN());
			con.IngresarDatoTabla(header.getDIVISION(), "DIVISION", 1);
//			System.out.println("DIVISION: " + header.getDIVISION());
			con.IngresarDatoTabla(header.getSALES_GRP(), "SALES_GRP", 1);
//			System.out.println("SALES_GRP: " + header.getSALES_GRP());
			con.IngresarDatoTabla(header.getSALES_OFF(), "SALES_OFF", 1);
//			System.out.println("SALES_OFF: " + header.getSALES_OFF());
			con.IngresarDatoTabla(header.getREQ_DATE_H(), "REQ_DATE_H", 1);
//			System.out.println("REQ_DATE_H: " + header.getREQ_DATE_H());
			con.IngresarDatoTabla(header.getPURCH_DATE(), "PURCH_DATE", 1);
//			System.out.println("PURCH_DATE: " + header.getPURCH_DATE());
			con.IngresarDatoTabla(header.getPMNTTRMS(), "PMNTTRMS", 1);
//			System.out.println("PMNTTRMS: " + header.getPMNTTRMS());
			con.IngresarDatoTabla(header.getDLV_BLOCK(), "DLV_BLOCK", 1);
//			System.out.println("DLV_BLOCK: " + header.getDLV_BLOCK());
			con.IngresarDatoTabla(header.getPURCH_NO_C(), "PURCH_NO_C", 1);
//			System.out.println("PURCH_NO_C: " + header.getPURCH_NO_C());
			con.IngresarDatoTabla(header.getCREATED_BY(), "CREATED_BY", 1);
//			System.out.println("CREATED_BY: " + header.getCREATED_BY());
			con.IngresarDatoTabla(header.getStrSello(), "REF_1_S", 1);
//			System.out.println("REF_1_S: " + header.getStrSello());
			con.IngresarDatoTabla(header.getORD_REASON(), "ORD_REASON", 1);
//			System.out.println("ORD_REASON: " + header.getORD_REASON());
			con.IngresarDatoTabla(header.getREF_1(), "REF_1", 1);
//			System.out.println("REF_1: " + header.getREF_1());
			con.IngresarDatoTabla(header.getDUN_COUNT(), "DUN_COUNT", 1);
//			System.out.println("DUN_COUNT: " + header.getDUN_COUNT());
			con.IngresarDatoTabla(header.getPURCH_NO_S(), "PURCH_NO_S", 1);
//			System.out.println("PURCH_NO_S: " + header.getPURCH_NO_S());
			con.IngresarDatoTabla(header.getPO_SUPPLEM(), "PO_SUPPLEM", 1);
//			System.out.println("PO_SUPPLEM: " + header.getPO_SUPPLEM());
			con.IngresarDatoTabla(header.getCOLLECT_NO(), "COLLECT_NO", 1);
//			System.out.println("COLLECT_NO: " + header.getCOLLECT_NO());
			con.IngresarDatoTabla(header.getNAME(), "NAME", 1);
//			System.out.println("NAME: " + header.getNAME());
			
			con.CreaTabla("ORDER_PARTNERS");
//			System.out.println("ORDER_PARTNERS");
			Set set = pedido.getPartners().getHm().entrySet();
			Iterator it = set.iterator();
			int i = 1;
			while (it.hasNext()) {
				Map.Entry me = (Map.Entry) it.next();
				con.IngresarDatoTabla(me.getKey().toString(), "PARTN_ROLE", i);
//				System.out.println("PARTN_ROLE: " + me.getKey().toString());
				con.IngresarDatoTabla(me.getValue().toString(), "PARTN_NUMB", i);
//				System.out.println("PARTN_NUMB: " + me.getValue().toString());
				con.IngresarDatoTabla("EC", "COUNTRY", i);
//				System.out.println("country: ec");
				i++;
			}
			con.CreaTabla("ORDER_ITEMS_IN");
			System.out.println("ORDER_ITEMS_IN");
			if (materiales.size() > 0) {
				i = 1;
				for (BeanPedidoDetalle material : materiales) {
					con.IngresarDatoTabla(material.getPosicion(), "ITM_NUMBER", i);
//					System.out.println("ITM_NUMBER: " + material.getPosicion());
					con.IngresarDatoTabla(material.getMaterial(), "MATERIAL", i);
//					System.out.println("MATERIAL: " + material.getMaterial());
					con.IngresarDatoTabla(material.getCantidad(), "TARGET_QTY", i);
//					System.out.println("TARGET_QTY: " + material.getCantidad());
					con.IngresarDatoTabla("1030", "PLANT", i);
//					System.out.println("PLANT: 1030");
					i++;
				}
			}
			con.EjecutarRFC();
			String codigo = "";
			con.CreaTabla("ET_MSG");
			List ur = con.ObtenerDatosTabla();
			String mensaje[] = new String[3];
			String error = "";
			boolean huboError = false;
			for (i = 0; i < ur.size(); i++) {
				String cadena = String.valueOf(ur.get(i));
				String values[] = cadena.split("¬");
				String cod = values[1];
				codigo = values[3];
				if (cod.compareTo("S") != 0) {
					huboError = true;
				}
			}
			if (huboError) {
				mensaje[0] = "E";
			} else {
				mensaje[0] = "N";
			}
			mensaje[1] = error;
			mensaje[2] = codigo;
			return mensaje;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public String[] guardarComentarioPedidoDevolucion(String vtaMercaderia, List<String> lstLineas, String codigoTdid) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			String[] valores = new String[20];
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_OBJECT_SAVE_TEXT");
				con.IngresarDatosInput("VBBK", "IV_TDOBJECT");
				con.IngresarDatosInput(vtaMercaderia, "IV_TDNAME"); // Venta de  mercadería
				con.IngresarDatosInput(codigoTdid, "IV_TDID");
				con.IngresarDatosInput("S", "IV_TDSPRAS");
				con.CreaTabla("IT_LINES");
				for (int i = 0; i < lstLineas.size(); i++) {
					String comentario = lstLineas.get(i);
					con.IngresarDatoTabla(comentario, "TDLINE", i);
				}
				//con.IngresarDatoTabla(comentario, "TDLINE", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				@SuppressWarnings("unchecked")
				List<String> mensajes = con.ObtenerDatosTabla();
				if (!mensajes.isEmpty()) {
					String mensaje = mensajes.get(0);
					valores = String.valueOf(mensaje).split("[¬]");
				}
				con.DesconectarSAP();
				return valores;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public String leerComentarioPedidoDevolucion(String vtaMercaderia, String codigoTdid) {
		String comentario = "";
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_OBJECT_READ_TEXT");
				con.IngresarDatosInput("VBBK", "IV_TDOBJECT");
				con.IngresarDatosInput(vtaMercaderia, "IV_TDNAME"); // Venta de mercadería
				con.IngresarDatosInput(codigoTdid, "IV_TDID");
				con.IngresarDatosInput("S", "IV_TDSPRAS");
				con.EjecutarRFC();
				con.CreaTabla("IT_LINES");
				List<String> mensajes = con.ObtenerDatosTabla();
				if (!mensajes.isEmpty()) {
					for (String string : mensajes) {
						String observaciones[] = string.split("[¬]");
						if (observaciones.length == 3) {
							//comentario = observaciones[2];
							comentario += observaciones[2] + "\n";
						}
					}
				}
				con.CreaTabla("ET_MSG");
				mensajes = con.ObtenerDatosTabla();
			}
			con.DesconectarSAP();
			return comentario;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}
}