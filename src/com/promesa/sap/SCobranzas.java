package com.promesa.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.cobranzas.bean.CabeceraHojaMaestraCredito;
import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;
import com.promesa.cobranzas.bean.DatoCredito;
import com.promesa.cobranzas.bean.DetallePagoPartidaIndividualAbierta;
import com.promesa.cobranzas.bean.DiaDemoraTrasVencimiento;
import com.promesa.cobranzas.bean.FlujoDocumento;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.cobranzas.bean.HistorialPago;
import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.cobranzas.bean.PartidaIndividual;
import com.promesa.cobranzas.bean.PartidaIndividualAbierta;
import com.promesa.cobranzas.bean.PedidoPendienteDevolucion;
import com.promesa.cobranzas.bean.Presupuesto;
import com.promesa.cobranzas.bean.RegistroPagoCliente;
import com.promesa.cobranzas.bean.ValorPorVencer;
import com.promesa.cobranzas.dao.bean.HojaMaestraCredito;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanSecuencia;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.util.Conexiones;
import com.promesa.util.Util;

//import net.sf.jasperreports.components.list.ListDesignConverter;


import util.ConexionSAP;

public class SCobranzas {

	public static DefaultMutableTreeNode obtenerFlujoDocumento(
			String codigoPedido) {
		try {
			codigoPedido = Util.completarCeros(10, codigoPedido);
			ConexionSAP con = Conexiones.getConexionSAP();
			DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_DOCUMENT_FLOW");
				con.IngresarDatosInput(codigoPedido, "IV_VBELN");
				con.EjecutarRFC();
				con.CreaTabla("ET_DOCFLOW");
				@SuppressWarnings("unchecked")
				List<String> mensaje = con.ObtenerDatosTabla();
				if (!mensaje.isEmpty()) {
					for (String string : mensaje) {
						String[] values = string.split("¬");
						String documento = values[5] + " " + values[1];
						String ei = Util
								.convierteFecha(("" + values[8]).trim());
						String estatus = "";
						if (values.length >= 10) {
							estatus = values[9];
						}
						String nivel = values[6];
						String codigo = values[1];
						String codigoPadre = values[3];
						FlujoDocumento fd = new FlujoDocumento(documento, ei,
								estatus, false, codigo, nivel, codigoPadre);
						defaultMutableTreeNode = insertarNuevoRegistroEnArbol(
								defaultMutableTreeNode, fd);
					}
				}
			} else {
				defaultMutableTreeNode = null;
			}
			return defaultMutableTreeNode;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public static DefaultMutableTreeNode insertarNuevoRegistroEnArbol(
			DefaultMutableTreeNode arbol, FlujoDocumento flujo) {
		// if (flujo.getNivel().compareTo("0") == 0) {
		if (flujo.getCodigoPadre().equals("")) {
			flujo.setRoot(true);
			arbol.add(new DefaultMutableTreeNode(flujo));
		} else {
			@SuppressWarnings("rawtypes")
			Enumeration en = arbol.depthFirstEnumeration();
			while (en.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) en
						.nextElement();
				FlujoDocumento fd = (FlujoDocumento) node.getUserObject();
				if (fd != null) {
					if (flujo.getCodigoPadre().compareTo(fd.getCodigo()) == 0) {
						node.add(new DefaultMutableTreeNode(flujo));
					}
				}
			}
		}
		return arbol;
	}

	public HojaMaestraCredito obtenerHojaMaestraCredito(String codCli,
			String codVend) {
		CabeceraHojaMaestraCredito cbmc = null;
		List<DiaDemoraTrasVencimiento> diaDemoraTrasVencimientos = null;
		List<HistorialPago> historialPagos = null;
		List<ValorPorVencer> valoresVencer = null;
		List<DatoCredito> notasCreditos = null;
		List<DatoCredito> protestos = null;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				HojaMaestraCredito hmc = new HojaMaestraCredito();
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_DETAIL");
				String codigo = Util.completarCeros(10, codCli);
				con.IngresarDatosInput(codigo, "IV_CUST_ID");
				con.EjecutarRFC();
				cbmc = obtenerCabeceraHojaMaestraCredito(con, codVend);
				diaDemoraTrasVencimientos = obtenerListaDiaDemoraTrasVencimiento(
						con, codVend);
				historialPagos = obtenerListaHistorialPago(con, codCli, codVend);
				valoresVencer = obtenerListaValorPorVencer(con, codCli, codVend);
				notasCreditos = obtenerListaNotaCredito(con, codCli, codVend);
				protestos = obtenerListaProtesto(con, codCli, codVend);
				hmc.setCabeceraHojaMaestraCredito(cbmc);
				hmc.setListaDiaDemoraTrasVencimiento(diaDemoraTrasVencimientos);
				hmc.setListaHistorialPago(historialPagos);
				hmc.setListaValorPorVencer(valoresVencer);
				hmc.setListaNotaCredito(notasCreditos);
				hmc.setListaProtesto(protestos);
				con.DesconectarSAP();
				return hmc;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public HojaMaestraCredito obtenerHojaMaestraCreditoXVendedor(String codVend) {
		List<CabeceraHojaMaestraCredito> cbmc = null;
		List<DiaDemoraTrasVencimiento> diaDemoraTrasVencimientos = null;
		List<ValorPorVencer> valoresVencer = null;
		List<HistorialPago> historialPagos = null;
		List<DatoCredito> notasCreditos = null;
		List<DatoCredito> protestos = null;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				HojaMaestraCredito hmc = new HojaMaestraCredito();
				con.RegistrarRFC("ZSD_RFC_VEND_GET_DETAIL");
				String codigo = Util.completarCeros(10, codVend);
				con.IngresarDatosInput(codigo, "IV_CUST_ID");
				con.EjecutarRFC();
				cbmc = obtenerListaCabeceraHojaMaestraCreditoXVendedor(con,
						codVend);
				diaDemoraTrasVencimientos = obtenerListaDiaDemoraTrasVencimiento(
						con, codVend);
				historialPagos = obtenerListaHistorialPagoXVendedor(con,
						codVend);
				valoresVencer = obtenerListaValorPorVencerXVendedor(con,
						codVend);
				notasCreditos = obtenerListaNotaCreditoXVendedor(con, codVend);
				protestos = obtenerListaProtestoXVendedor(con, codVend);
				hmc.setListaCabeceraHojaMaestraCredito(cbmc);
				hmc.setListaDiaDemoraTrasVencimiento(diaDemoraTrasVencimientos);
				hmc.setListaHistorialPago(historialPagos);
				hmc.setListaValorPorVencer(valoresVencer);
				hmc.setListaNotaCredito(notasCreditos);
				hmc.setListaProtesto(protestos);
				con.DesconectarSAP();
				return hmc;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private CabeceraHojaMaestraCredito obtenerCabeceraHojaMaestraCredito(
			ConexionSAP con, String codigoVendedor) {
		try {
			CabeceraHojaMaestraCredito chmc = new CabeceraHojaMaestraCredito();
			con.CreaTabla("ES_CUST_INFO");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				String[] valores = String.valueOf(result.get(0)).split("[¬]");
				chmc.setCodigoCliente(valores[1].trim());
				chmc.setCodigoVendedor(codigoVendedor);
				chmc.setNombreCompletoCliente(valores[2].trim());
				chmc.setLimiteCredito(valores[9].trim());
				chmc.setClaseRiesgo(valores[10].trim());
				chmc.setCupoDisponible(valores[11].trim());
				chmc.setValorVencido(valores[12].trim());
				chmc.setFuds(valores[13].trim());
				chmc.setNotaCredito(valores[14].trim());
				chmc.setProtestante(valores[15].trim());
				if (valores.length > 16) {
					chmc.setBloqueoCredito(valores[17].trim());
				} else {
					chmc.setBloqueoCredito("");
				}
				return chmc;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<CabeceraHojaMaestraCredito> obtenerListaCabeceraHojaMaestraCreditoXVendedor(
			ConexionSAP con, String codVend) {
		try {
			List<CabeceraHojaMaestraCredito> chmcs = new ArrayList<CabeceraHojaMaestraCredito>();
			con.CreaTabla("ES_CUST_INFO");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					CabeceraHojaMaestraCredito chmc = new CabeceraHojaMaestraCredito();
					String[] valores = result.get(i).toString().split("[¬]");
					chmc.setCodigoCliente(valores[1].trim());
					chmc.setCodigoVendedor(codVend);
					chmc.setNombreCompletoCliente(valores[2].trim());
					chmc.setLimiteCredito(valores[9].trim());
					chmc.setClaseRiesgo(valores[10].trim());
					chmc.setCupoDisponible(valores[11].trim());
					chmc.setValorVencido(valores[12].trim());
					chmc.setFuds(valores[13].trim());
					chmc.setNotaCredito(valores[14].trim());
					chmc.setProtestante(valores[15].trim());
					if (valores.length > 16) {
						try{
							chmc.setBloqueoCredito(valores[17].trim());
						} catch (ArrayIndexOutOfBoundsException indexOut) {
							chmc.setBloqueoCredito("");
						}						
					} else {
						chmc.setBloqueoCredito("");
					}
					chmcs.add(chmc);
				}
				return chmcs;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<DiaDemoraTrasVencimiento> obtenerListaDiaDemoraTrasVencimiento(
			ConexionSAP con, String codigoVendedor) {
		try {
			List<DiaDemoraTrasVencimiento> diasDemoras = new ArrayList<DiaDemoraTrasVencimiento>();
			con.CreaTabla("ET_ARREARS");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					DiaDemoraTrasVencimiento ddtv = new DiaDemoraTrasVencimiento();
					String[] valores = result.get(i).toString().split("[¬]");
					ddtv.setId(i);
					ddtv.setCodigoCliente(!("").equals(valores[1].trim()) ? (Integer
							.parseInt(valores[1].trim()) + "") : "");
					ddtv.setCodigoVendedor(codigoVendedor);
					ddtv.setSociedad(valores[2].trim());
					ddtv.setMoneda(valores[3].trim());
					ddtv.setCuadro(valores[4].trim());
					ddtv.setPartidasVencidas(valores[5].trim());
					ddtv.setNoVencidas(valores[6].trim());
					diasDemoras.add(ddtv);
				}
			}
			return diasDemoras;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<HistorialPago> obtenerListaHistorialPago(ConexionSAP con,
			String codigoCliente, String codigoVendedor) {
		try {
			List<HistorialPago> historialPagos = new ArrayList<HistorialPago>();
			con.CreaTabla("ET_PAYMENTS");
			@SuppressWarnings("unchecked")
			List<String> lista = con.ObtenerDatosTabla();
			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					HistorialPago hp = new HistorialPago();
					// String[] valores1 = lista.get(i).split("[¬]");
					String[] valores = lista.get(i).toString().split("[¬]");
					hp.setId(i);
					hp.setCodigoCliente(valores[1].trim());
					hp.setCodigoVendedor(codigoVendedor);
					hp.setMoneda(valores[2].trim());
					hp.setEjercicio(valores[3].trim());
					hp.setPeriodo(valores[4].trim());
					hp.setConDPP(valores[5].trim());
					hp.setDiasDemora1(valores[6].trim());
					hp.setSinDescuento(valores[7].trim());
					hp.setDiasDemora2(valores[8].trim());
					hp.setCtd(valores[9].trim());
					historialPagos.add(hp);
				}
			}
			return historialPagos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<HistorialPago> obtenerListaHistorialPagoXVendedor(
			ConexionSAP con, String codigoVendedor) {
		try {
			List<HistorialPago> historialPagos = new ArrayList<HistorialPago>();
			con.CreaTabla("ET_PAYMENTS");
			@SuppressWarnings("unchecked")
			List<String> lista = con.ObtenerDatosTabla();
			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					HistorialPago hp = new HistorialPago();
					String[] valores = lista.get(i).toString().split("[¬]");
					hp.setId(i);
					hp.setCodigoCliente(valores[1].trim());
					hp.setCodigoVendedor(codigoVendedor);
					hp.setMoneda(valores[2].trim());
					hp.setEjercicio(valores[3].trim());
					hp.setPeriodo(valores[4].trim());
					hp.setConDPP(valores[5].trim());
					hp.setDiasDemora1(valores[6].trim());
					hp.setSinDescuento(valores[7].trim());
					hp.setDiasDemora2(valores[8].trim());
					hp.setCtd(valores[9].trim());
					historialPagos.add(hp);
				}
			}
			return historialPagos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<ValorPorVencer> obtenerListaValorPorVencer(ConexionSAP con,
			String codCli, String codVend) {
		try {
			List<ValorPorVencer> valoresVencer = new ArrayList<ValorPorVencer>();
			con.CreaTabla("ET_OPEN_ITEMS_T");
			@SuppressWarnings("unchecked")
			List<String> lista = con.ObtenerDatosTabla();
			if (lista.size() > 0) {
				for (int i = 0; i < lista.size(); i++) {
					ValorPorVencer vv = new ValorPorVencer();
					String[] valores = lista.get(i).toString().split("[¬]");
					vv.setCodigoCliente(codCli);
					vv.setCodigoVendedor(codVend);
					vv.setMesAnio(valores[1].trim());
					vv.setCantidad(valores[2].trim());
					valoresVencer.add(vv);
				}
			}
			return valoresVencer;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<ValorPorVencer> obtenerListaValorPorVencerXVendedor(
			ConexionSAP con, String codigoVendedor) {
		try {
			List<ValorPorVencer> valoresVencer = new ArrayList<ValorPorVencer>();
			con.CreaTabla("ET_OPEN_ITEMS_T");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					ValorPorVencer vv = new ValorPorVencer();
					String[] valores = result.get(i).toString().split("[¬]");
					vv.setCodigoCliente(valores[1].trim());
					vv.setCodigoVendedor(codigoVendedor);
					vv.setMesAnio(valores[2].trim());
					vv.setCantidad(valores[3].trim());
					valoresVencer.add(vv);
				}
			}
			return valoresVencer;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<DatoCredito> obtenerListaNotaCredito(ConexionSAP con,
			String codigoCliente, String codigoVendedor) {
		try {
			List<DatoCredito> ntasCreditos = new ArrayList<DatoCredito>();
			con.CreaTabla("ET_C_NOTES");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					DatoCredito dc = new DatoCredito();
					String[] valores = result.get(i).toString().split("[¬]");
					dc.setId(i);
					dc.setCodigoCliente(codigoCliente);
					dc.setCodigoVendedor(codigoVendedor);
					dc.setNumeroDocumento(valores[1].trim());
					dc.setFechaContable(("null").equalsIgnoreCase(valores[2]
							.trim()) ? "" : Util.convierteFecha(valores[2]
							.trim()));
					dc.setFechaDocumento(("null").equalsIgnoreCase(valores[3]
							.trim()) ? "" : Util.convierteFecha(valores[3]
							.trim()));
					dc.setRegistradoEl(("null").equalsIgnoreCase(valores[4]
							.trim()) ? "" : Util.convierteFecha(valores[4]
							.trim()));
					dc.setMoneda(valores[6].trim());
					dc.setImporte(valores[7].trim());
					dc.setUnOrgRefer(valores[8].trim());
					ntasCreditos.add(dc);
				}
			}
			return ntasCreditos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<DatoCredito> obtenerListaNotaCreditoXVendedor(ConexionSAP con,
			String codigoVendedor) {
		try {
			List<DatoCredito> ntasCreditos = new ArrayList<DatoCredito>();
			con.CreaTabla("ET_C_NOTES");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					DatoCredito dc = new DatoCredito();
					String[] valores = String.valueOf(result.get(i)).split(
							"[¬]");
					dc.setId(i);
					dc.setCodigoCliente(Util.eliminarCerosInicios(valores[1]
							.trim()));
					dc.setCodigoVendedor(codigoVendedor);
					dc.setNumeroDocumento(valores[2].trim());
					dc.setFechaContable(("null").equalsIgnoreCase(valores[3]
							.trim()) ? "" : Util.convierteFecha(valores[3]
							.trim()));
					dc.setFechaDocumento(("null").equalsIgnoreCase(valores[4]
							.trim()) ? "" : Util.convierteFecha(valores[4]
							.trim()));
					dc.setRegistradoEl(("null").equalsIgnoreCase(valores[5]
							.trim()) ? "" : Util.convierteFecha(valores[5]
							.trim()));
					dc.setMoneda(valores[7].trim());
					dc.setImporte(valores[8].trim());
					dc.setUnOrgRefer(valores[9].trim());
					ntasCreditos.add(dc);
				}
			}
			return ntasCreditos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<DatoCredito> obtenerListaProtesto(ConexionSAP con,
			String codigoCliente, String codigoVendedor) {
		try {
			List<DatoCredito> protestos = new ArrayList<DatoCredito>();
			con.CreaTabla("ET_PROTESTS");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					DatoCredito dc = new DatoCredito();
					String[] valores = result.get(i).toString().split("[¬]");
					dc.setId(i);
					dc.setCodigoCliente(codigoCliente);
					dc.setCodigoVendedor(codigoVendedor);
					dc.setNumeroDocumento(valores[1].trim());
					dc.setFechaContable(("null").equalsIgnoreCase(valores[2]
							.trim()) ? "" : Util.convierteFecha(valores[2]
							.trim()));
					dc.setFechaDocumento(("null").equalsIgnoreCase(valores[3]
							.trim()) ? "" : Util.convierteFecha(valores[3]
							.trim()));
					dc.setRegistradoEl(("null").equalsIgnoreCase(valores[4]
							.trim()) ? "" : Util.convierteFecha(valores[4]
							.trim()));
					dc.setMoneda(valores[6].trim());
					dc.setImporte(valores[7].trim());
					dc.setUnOrgRefer(valores[8].trim());
					protestos.add(dc);
				}
			}
			return protestos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	private List<DatoCredito> obtenerListaProtestoXVendedor(ConexionSAP con,
			String codigoVendedor) {
		try {
			List<DatoCredito> protestos = new ArrayList<DatoCredito>();
			con.CreaTabla("ET_PROTESTS");
			@SuppressWarnings("unchecked")
			List<String> result = con.ObtenerDatosTabla();
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					DatoCredito dc = new DatoCredito();
					String[] valores = result.get(i).toString().split("[¬]");
					dc.setId(i);
					dc.setCodigoCliente(valores[1].trim());
					dc.setCodigoVendedor(codigoVendedor);
					dc.setNumeroDocumento(valores[2].trim());
					dc.setFechaContable(("null").equalsIgnoreCase(valores[3]
							.trim()) ? "" : Util.convierteFecha(valores[3]
							.trim()));
					dc.setFechaDocumento(("null").equalsIgnoreCase(valores[4]
							.trim()) ? "" : Util.convierteFecha(valores[4]
							.trim()));
					dc.setRegistradoEl(("null").equalsIgnoreCase(valores[5]
							.trim()) ? "" : Util.convierteFecha(valores[5]
							.trim()));
					dc.setMoneda(valores[7].trim());
					dc.setImporte(valores[8].trim());
					dc.setUnOrgRefer(valores[8].trim());
					protestos.add(dc);
				}
			}
			return protestos;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	// ENVIAR 01 PARA COBRANZAS
	// ENVIAR 02 PARA ANTICIPOS
	@SuppressWarnings("unchecked")
	public List<FormaPago> obtenerListaFormaPago(String tipoFormasPago) {
		Promesa instance = Promesa.getInstance();
		if (tipoFormasPago.equals("01")) {
			// 01 - Forma Pago Cobranza
			instance.mostrarAvisoSincronizacion("SAP: Forma Pago Cobranza");
		} else {
			// 02 - Forma Pago Anticipo
			instance.mostrarAvisoSincronizacion("SAP: Forma Pago Anticipo");
		}
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			List<FormaPago> formasPagos = new ArrayList<FormaPago>();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_PAYMENT_TYPES_GET_LIST");
				con.IngresarDatosInput(tipoFormasPago, "IV_PAYMENT");
				con.EjecutarRFC();
				con.CreaTabla("T_PAYMENT_TYPES");
				List<String> resultados = con.ObtenerDatosTabla();
				if (resultados.size() > 0) {
					for (int i = 0; i < resultados.size(); i++) {
						String[] valores = resultados.get(i).split("[¬]");
						// String[] valores =
						// String.valueOf(formasPagos.get(i)).split("[¬]");
						FormaPago fp = new FormaPago();
						if (valores.length == 0) {
							fp.setIdFormaPago("");
							fp.setDescripcionFormaPago("");
						} else {
							fp.setIdFormaPago(valores[1].trim());
							fp.setDescripcionFormaPago(valores[2].trim());
						}
						formasPagos.add(fp);
					}
				}
				con.DesconectarSAP();
				return formasPagos;
			} else {
				return formasPagos;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<BancoPromesa> obtenerListaBancoPromesa() {
		Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Banco Promesa");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			List<BancoPromesa> bcoPromesas = new ArrayList<BancoPromesa>();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_COMPANY_GET_BNK_ACCOUN");
				con.EjecutarRFC();
				con.CreaTabla("T_BNK_ACCOUNT");
				@SuppressWarnings("unchecked")
				List<String> resultados = con.ObtenerDatosTabla();
				if (resultados.size() > 0) {
					for (int i = 0; i < resultados.size(); i++) {
						String[] valores = resultados.get(i).toString()
								.split("[¬]");
						BancoPromesa bp = new BancoPromesa();
						if (valores.length == 0) {
							bp.setIdBancoPromesa("");
							bp.setDescripcionBancoPromesa("");
							bp.setTipoRecaudo("N");
							// bancoPromesa.setTipoRecaudo("");// Marcelo Moyano
							// Tipo recaudo Capturado desde SAP 02/04/2013
						} else if (valores.length == 6) {
							// Marcelo Moyano // 02/04/2013
							// Tipo recaudo Capturado desdeSAP
							bp.setIdBancoPromesa(valores[2].trim());
							bp.setDescripcionBancoPromesa(valores[4].trim());
							bp.setTipoRecaudo(valores[5].trim());
						} else if (valores.length == 7) {
							bp.setIdBancoPromesa(valores[2].trim());
							bp.setDescripcionBancoPromesa(valores[4].trim());
							bp.setTipoRecaudo(valores[5].trim());
							bp.setDepApps(valores[6].trim());
						} else {
							bp.setIdBancoPromesa(valores[2].trim());
							bp.setDescripcionBancoPromesa(valores[4].trim());
							bp.setTipoRecaudo("");
						}
						bcoPromesas.add(bp);
					}
				}
				con.DesconectarSAP();
				return bcoPromesas;
			} else {
				return bcoPromesas;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<PartidaIndividual> obtenerListaPartidaIndividual(
			String codigoCliente, String codigoVendedor,
			String partidasAbiertas, String partidasCompensadas,
			String todasPartidas, String fechaDesde, String fechaHasta) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			List<PartidaIndividual> partidas = new ArrayList<PartidaIndividual>();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND_ITEMS");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(codigoCliente, "CUST_ID", 1);
				con.IngresarDatoTabla("", "DOC_TYPE", 1);
				con.IngresarDatoTabla(partidasAbiertas, "OPEN_ITEMS", 1);
				con.IngresarDatoTabla(partidasCompensadas, "CLEARED_ITEMS", 1);
				con.IngresarDatoTabla(todasPartidas, "ALL_ITEMS", 1);
				con.IngresarDatoTabla(fechaDesde, "DATE_FROM", 1);
				con.IngresarDatoTabla(fechaHasta, "DATE_TO", 1);
				con.IngresarDatoTabla("", "WITH_EXPIRATION_DATE", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_OPEN_ITEM");
				@SuppressWarnings("unchecked")
				List<String> resultados = con.ObtenerDatosTabla();
				if (resultados.size() > 0) {
					for (int i = 0; i < resultados.size(); i++) {
						String[] valores = resultados.get(i).toString()
								.split("[¬]");
						PartidaIndividual pi = new PartidaIndividual();
						pi.setId(i);
						pi.setCodigoCliente(codigoCliente);
						pi.setCodigoVendedor(codigoVendedor);
						pi.setNumeroDocumento(valores[1].trim());
						pi.setClaseDocumento(valores[10].trim());
						pi.setPosicion(valores[11].trim());
						pi.setFechaDocumento(("null")
								.equalsIgnoreCase(valores[3].trim()) ? ""
								: Util.convierteFecha(valores[3].trim()));
						pi.setFechaVencimiento(("null")
								.equalsIgnoreCase(valores[5].trim()) ? ""
								: Util.convierteFecha(valores[5].trim()));
						pi.setRegistradoEl(("null").equalsIgnoreCase(valores[4]
								.trim()) ? "" : Util.convierteFecha(valores[4]
								.trim()));
						pi.setMoneda(valores[6].trim());
						pi.setImpteDePos(valores[7].trim());
						pi.setReferencia(valores[9].trim());
						partidas.add(pi);
					}
				}
				con.DesconectarSAP();
				return partidas;
			} else {
				return partidas;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<PartidaIndividual> obtenerListaPartidaIndividualXVendedor(
			String codigoVendedor, String partidasAbiertas,
			String partidasCompensadas, String todasPartidas,
			String fechaDesde, String fechaHasta) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			List<PartidaIndividual> partidas = new ArrayList<PartidaIndividual>();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VEND_FIND_ITEMS");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(codigoVendedor, "CUST_ID", 1);
				con.IngresarDatoTabla("", "DOC_TYPE", 1);
				con.IngresarDatoTabla(partidasAbiertas, "OPEN_ITEMS", 1);
				con.IngresarDatoTabla(partidasCompensadas, "CLEARED_ITEMS", 1);
				con.IngresarDatoTabla(todasPartidas, "ALL_ITEMS", 1);
				con.IngresarDatoTabla(fechaDesde, "DATE_FROM", 1);
				con.IngresarDatoTabla(fechaHasta, "DATE_TO", 1);
				con.IngresarDatoTabla("", "WITH_EXPIRATION_DATE", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_OPEN_ITEM_VND");
				@SuppressWarnings("unchecked")
				List<String> resultado = con.ObtenerDatosTabla();
				if (resultado.size() > 0) {
					for (int i = 0; i < resultado.size(); i++) {
						String[] valores = resultado.get(i).toString()
								.split("[¬]");
						PartidaIndividual pi = new PartidaIndividual();
						pi.setId(i);
						pi.setCodigoCliente(valores[1].trim());
						pi.setCodigoVendedor(codigoVendedor);
						pi.setNumeroDocumento(valores[2].trim());
						pi.setClaseDocumento(valores[11].trim());
						pi.setPosicion(valores[12].trim());
						pi.setFechaDocumento(("null")
								.equalsIgnoreCase(valores[4].trim()) ? ""
								: Util.convierteFecha(valores[4].trim()));
						pi.setFechaVencimiento(("null")
								.equalsIgnoreCase(valores[6].trim()) ? ""
								: Util.convierteFecha(valores[6].trim()));
						pi.setRegistradoEl(("null").equalsIgnoreCase(valores[5]
								.trim()) ? "" : Util.convierteFecha(valores[5]
								.trim()));
						pi.setMoneda(valores[7].trim());
						pi.setImpteDePos(valores[8].trim());
						pi.setReferencia(valores[10].trim());
						partidas.add(pi);
					}
				}
				con.DesconectarSAP();
				return partidas;

			} else {
				return partidas;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<PedidoPendienteDevolucion> obtenerListaPedidoPendienteDevolucion(
			String codigoCliente) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			List<PedidoPendienteDevolucion> pedidosPendiente = new ArrayList<PedidoPendienteDevolucion>();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_DETAIL2");
				con.IngresarDatosInput(codigoCliente, "IV_CUST_ID");
				con.EjecutarRFC();
				con.CreaTabla("ET_ORDER");
				@SuppressWarnings("unchecked")
				List<String> resultado = con.ObtenerDatosTabla();
				if (resultado.size() > 0) {
					for (int i = 0; i < resultado.size(); i++) {
						String[] valores = resultado.get(i).toString()
								.split("[¬]");
						PedidoPendienteDevolucion ppd = new PedidoPendienteDevolucion();
						ppd.setId(i);
						ppd.setCodigoCliente(codigoCliente);
						ppd.setCodigoVendedor(valores[7].trim());
						ppd.setNumeroDocumento(valores[1].trim());
						ppd.setFechaDocumento(("null")
								.equalsIgnoreCase(valores[2].trim()) ? ""
								: Util.convierteFecha(valores[2].trim()));
						ppd.setCreadoEl(("null").equalsIgnoreCase(valores[3]
								.trim()) ? "" : Util.convierteFecha(valores[3]
								.trim()));
						ppd.setValorNeto(valores[5].trim());
						ppd.setNombreVendedor(valores[8].trim());
						pedidosPendiente.add(ppd);
					}
				}
				con.DesconectarSAP();
				return pedidosPendiente;
			} else {
				return pedidosPendiente;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public String[] registroAnticipo(AnticipoCliente ac) {
		String[] arreglo = new String[3];
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			String formaPago = ac.getIdFormaPago();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_ENTER_DOWNPAY");
				BeanDato bd = Promesa.datose.get(0);
				String secVend;
				secVend = Util.obtenerSecuenciaPorVendedor(bd.getStrCodigo());
				con.IngresarDatosInput(ac.getAnticipoSecuencia(), "IV_DOCSECU");
//				System.out.println("iv:docsecu: " + ac.getAnticipoSecuencia());
				con.IngresarDatosInput(secVend, "IV_USERSECU");
//				System.out.println("iv_usersecu: " + secVend);
				// Marcelo Moyano 27/03/2013
				con.CreaTabla("IS_PAYMENT_INFO");
//				System.out.println("is_payment_info");
				con.IngresarDatoTabla(ac.getIdFormaPago(), "BLART", 1);
//				System.out.println("blart: " + ac.getIdFormaPago());
				String referencia;
				referencia = ac.getReferencia();
//				if (ac.getReferencia().length() > 17) {
//					referencia = ac.getReferencia().substring(0, 15);
//				} else {
//					referencia = ac.getReferencia();
//				}
				con.IngresarDatoTabla(referencia, "XBLNR", 1);
//				System.out.println("xblnr: " + referencia);
				con.IngresarDatoTabla(ac.getImporte(), "WRBTR", 1);
//				System.out.println("wrbtr renta: " + ac.getImporte());
				con.IngresarDatoTabla(ac.getImporteIva(), "IVA", 1);
//				System.out.println("iva: " + ac.getImporteIva());
				con.IngresarDatoTabla(ac.getCodigoCliente(), "CUST_ID", 1);
//				System.out.println("cust_id: " + ac.getCodigoCliente());
				con.IngresarDatoTabla(ac.getIdBancoPromesa(), "XREF2_HD", 1);
//				System.out.println("xref2_hd: " + ac.getIdBancoPromesa());
				con.IngresarDatoTabla(ac.getNroCtaBcoCliente(), "BANKN", 1);
//				System.out.println("bankn: " + ac.getNroCtaBcoCliente());
				String descrip;
				if (formaPago.equals("VY")) {
					descrip = ac.getDescripcionBancoPromesa();
					// Marcelo Moyano 06/03/2013 - 14:57
					con.IngresarDatoTabla(descrip, "BANK", 1);
//					System.out.println("bank: " + descrip);
					String fecha;
					fecha = ac.getFechaCreacion();
					fecha = Util.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(fecha);
					con.IngresarDatoTabla(fecha, "DOCUMENT_DATE", 1);
//					System.out.println("document_date: " + fecha);
				} else if (formaPago.equals("VX")) {
					try {
						String fecha;
						fecha = ac.getFechaCreacion();
						con.IngresarDatoTabla(fecha, "DOCUMENT_DATE", 1);
//						System.out.println("document_date: " + fecha);
					} catch (Exception e) {
						// TODO: handle exception
						String str = ac.getFechaCreacion();
						String fecha;
						fecha = Util
								.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(str);
						con.IngresarDatoTabla(fecha, "DOCUMENT_DATE", 1);
//						System.out.println("document_date: " + fecha);
					}
					con.IngresarDatoTabla(ac.getFechaCreacion(),"DOCUMENT_DATE", 1);
					descrip = ac.getDescripcionBancoPromesa();
					con.IngresarDatoTabla(descrip, "BANK", 1);
//					System.out.println("bank: " + descrip);
				} else if (formaPago.equals("VZ")) {
					String str = ac.getFechaCreacion();
					con.IngresarDatoTabla(str, "DOCUMENT_DATE", 1);
//					System.out.println("document_date: " + str);
				} else if (formaPago.equals("VT")){
					String str = ac.getFechaCreacion();
					con.IngresarDatoTabla(str, "DOCUMENT_DATE", 1);
				}
				String cod = ac.getCodigoVendedor();
				con.IngresarDatoTabla(cod, "RESPONSABLE_ID", 1);
//				System.out.println("responsable_id: " + cod);
				String observacion;
				if (ac.getObservaciones().length() > 50) {
					observacion = ac.getObservaciones().substring(0, 49);
				} else {
					observacion = ac.getObservaciones();
				}
				con.IngresarDatoTabla(observacion, "COMMENTS", 1);
//				System.out.println("comments: " + observacion);
				// Marcelo Moyano
				con.EjecutarRFC();
				String cadenaNumTicket = con.ObtenerDato("EV_TICKET_NRO");
				con.CreaTabla("ET_MSG");
//				System.out.println("Numero ticket: " + cadenaNumTicket);
				@SuppressWarnings("unchecked")
				List<String> mensaje = con.ObtenerDatosTabla();
//				System.out.println("mensajes: " + mensaje);
				try {
					String[] valores = mensaje.get(0).toString().split("[¬]");
					arreglo[0] = cadenaNumTicket; // Num. Ticket.
					arreglo[1] = valores[1].trim(); // Indicador.
					arreglo[2] = valores[3].trim(); // Mensaje.
				} catch (Exception e) {
					// TODO: handle exception
					arreglo = null;
				}
				con.DesconectarSAP();
				return arreglo;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	// Marcelo Moyano - metodo para obtener la secuencia por vendedor
	// Desde el RFC de SAP
	public BeanSecuencia obtenerSecuenciaPorVendedor(String codigoVendedor) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			BeanSecuencia beanSecuencia = null;
			if (con != null) {
				beanSecuencia = new BeanSecuencia();
				con.RegistrarRFC("ZSD_RFC_USER_GET_SEC");
				con.IngresarDatosInput(codigoVendedor, "IS_VENDOR_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_MSG");
				beanSecuencia.setCodigoVendedor(codigoVendedor);
				beanSecuencia.setSecuenciaCobranza(con.ObtenerDato("ES_NRSECU"));
				beanSecuencia.setSecuenciaPedido(con.ObtenerDato("ES_NPSECU"));
				con.DesconectarSAP();
			}
			return beanSecuencia;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	// Marcelo Moyano
	public String[] obtenerBancoAsociadoANumeroCuenta(String numeroDeCuenta,
			String codigoCliente) {
		String[] banco = null;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_BNK_ACCT");
				con.IngresarDatosInput(numeroDeCuenta, "IV_BANKN");
				con.IngresarDatosInput(codigoCliente, "IV_CUST_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_BNK_ACCOUNT");
				@SuppressWarnings("unchecked")
				List<String> mensaje = con.ObtenerDatosTabla();
				if (!mensaje.isEmpty()) {
					banco = String.valueOf(mensaje.get(0)).split("[¬]");
				}
				con.DesconectarSAP();
			}
			return banco;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> obtenerBancosAsociadosDelCliente(String codigoCliente) {
		List<String> lstBanco = new ArrayList<String>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_GET_BNK_ACCT");
				con.IngresarDatosInput("", "IV_BANKN");
				con.IngresarDatosInput(codigoCliente, "IV_CUST_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_BNK_ACCOUNT");
				lstBanco = con.ObtenerDatosTabla();
				con.DesconectarSAP();
			}
			return lstBanco;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> obtenerBancosAsociadosXVendedor(String codigoVendedor) {
		List<String> lstBanco = new ArrayList<String>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VEND_GET_BNK_ACCT");
				con.IngresarDatosInput("", "IV_BANKN");
				con.IngresarDatosInput(codigoVendedor, "IV_CUST_ID");
				con.EjecutarRFC();
				con.CreaTabla("T_BNK_ACCOUNT_VND");
				lstBanco = con.ObtenerDatosTabla();
				con.DesconectarSAP();
			}
			return lstBanco;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public Object[] obtenerArregloDeListaPartidaIndividualAbierta(
			String codigoCliente, String fechaHasta, long contador) {
		Object arreglo[] = new Object[3];
		List<PartidaIndividualAbierta> partidas = new ArrayList<PartidaIndividualAbierta>();
		List<DetallePagoPartidaIndividualAbierta> detalles = new ArrayList<DetallePagoPartidaIndividualAbierta>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_FIND_T_ITEMS");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(codigoCliente, "CUST_ID", 1);
				con.IngresarDatoTabla("", "DOC_TYPE", 1);
				con.IngresarDatoTabla("X", "OPEN_ITEMS", 1);
				con.IngresarDatoTabla("", "CLEARED_ITEMS", 1);
				con.IngresarDatoTabla("", "ALL_ITEMS", 1);
				con.IngresarDatoTabla("", "DATE_FROM", 1);
				con.IngresarDatoTabla(fechaHasta, "DATE_TO", 1);
				con.IngresarDatoTabla("", "WITH_EXPIRATION_DATE", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_OPEN_ITEM");
				@SuppressWarnings("unchecked")
				List<String> lista = con.ObtenerDatosTabla();
				HashMap<String, Long> asociacion = new HashMap<String, Long>();
				if (lista.size() > 0) {
					for (int i = 0; i < lista.size(); i++) {
						String[] valores = lista.get(i).toString().split("[¬]");
						PartidaIndividualAbierta pia = new PartidaIndividualAbierta();
						pia.setIdPartidaIndividualAbierta(contador);
						pia.setCodigoCliente(codigoCliente);
						pia.setDocNo(valores[1].trim());
						pia.setPstngDate(("null").equalsIgnoreCase(valores[2]
								.trim()) ? "" : Util.convierteFecha(valores[2]
								.trim()));
						pia.setDocDate(("null").equalsIgnoreCase(valores[3]
								.trim()) ? "" : Util.convierteFecha(valores[3]
								.trim()));
						pia.setEntryDate(("null").equalsIgnoreCase(valores[4]
								.trim()) ? "" : Util.convierteFecha(valores[4]
								.trim()));
						pia.setExpirationDate(("null")
								.equalsIgnoreCase(valores[5].trim()) ? ""
								: Util.convierteFecha(valores[5].trim()));
						pia.setCurrency(valores[6].trim());
						pia.setAmtDoccur(valores[7].trim());
						pia.setRefOrgUn(valores[8].trim());
						pia.setRefDocNo(valores[9].trim());
						pia.setDocType(valores[10].trim());
						pia.setItemNum(valores[11].trim());
						pia.setPostKey(valores[12].trim());
						pia.setPsprt(valores[13].trim());
						pia.setPszah(valores[14].trim());
						pia.setPsskt(valores[15].trim());
						pia.setInvRef(valores[16].trim());
						pia.setInvItem(valores[17].trim());
						pia.setIsLeaf(valores[18].trim());
						pia.setIsExpanded(valores[19].trim());
						pia.setIsReadOnly(valores[20].trim());
						pia.setIndice(valores[21].trim());
						pia.setDisplayColor(valores[22].trim());
						pia.setFiscYear(valores[23].trim());
						pia.setFisPeriod(valores[24].trim());
						pia.setSgtxt(valores[25].trim());
						pia.setIsReadOnly2(valores[26].trim());
						pia.setDbCrInd(valores[27].trim());
						pia.setVerzn(valores[28].trim());
						partidas.add(pia);
						asociacion.put(
								valores[1].trim() + "" + valores[11].trim(),
								contador);
						contador++;
					}
				}
				con.CreaTabla("ET_OPEN_ITEM_LEAD");
				@SuppressWarnings("unchecked")
				List<String> listas = con.ObtenerDatosTabla();
				if (listas.size() > 0) {
					for (int i = 0; i < listas.size(); i++) {
						String[] valores = listas.get(i).toString()
								.split("[¬]");
						DetallePagoPartidaIndividualAbierta dppia = new DetallePagoPartidaIndividualAbierta();
						dppia.setIdDetallePagoPartidaIndividualAbierta(i);
						Long valor = asociacion.get(valores[16].trim() + ""
								+ valores[17].trim());
						dppia.setIdPartidaIndividualAbierta(valor);
						dppia.setDocNo(valores[1].trim());
						dppia.setPstngDate(("null").equalsIgnoreCase(valores[2]
								.trim()) ? "" : Util.convierteFecha(valores[2]
								.trim()));
						dppia.setDocDate(("null").equalsIgnoreCase(valores[3]
								.trim()) ? "" : Util.convierteFecha(valores[3]
								.trim()));
						dppia.setEntryDate(("null").equalsIgnoreCase(valores[4]
								.trim()) ? "" : Util.convierteFecha(valores[4]
								.trim()));
						dppia.setExpirationDate(("null")
								.equalsIgnoreCase(valores[5].trim()) ? ""
								: Util.convierteFecha(valores[5].trim()));
						dppia.setCurrency(valores[6].trim());
						dppia.setAmtDoccur(valores[7].trim());
						dppia.setRefOrgUn(valores[8].trim());
						dppia.setRefDocNo(valores[9].trim());
						dppia.setDocType(valores[10].trim());
						dppia.setItemNum(valores[11].trim());
						dppia.setPostKey(valores[12].trim());
						dppia.setPsprt(valores[13].trim());
						dppia.setPszah(valores[14].trim());
						dppia.setPsskt(valores[15].trim());
						dppia.setInvRef(valores[16].trim());
						dppia.setInvItem(valores[17].trim());
						dppia.setIsLeaf(valores[18].trim());
						dppia.setIsExpanded(valores[19].trim());
						dppia.setIsReadOnly(valores[20].trim());
						dppia.setIndice(valores[21].trim());
						dppia.setDisplayColor(valores[22].trim());
						dppia.setFiscYear(valores[23].trim());
						dppia.setFisPeriod(valores[24].trim());
						dppia.setSgtxt(valores[25].trim());
						dppia.setIsReadOnly2(valores[26].trim());
						dppia.setDbCrInd(valores[27].trim());
						dppia.setVerzn(valores[28].trim());
						detalles.add(dppia);
					}
				}
				con.DesconectarSAP();
				arreglo[0] = partidas;
				arreglo[1] = detalles;
				arreglo[2] = contador;
				con.DesconectarSAP();
				return arreglo;
			} else {
				arreglo[0] = partidas;
				arreglo[1] = detalles;
				arreglo[2] = contador;
				return arreglo;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			arreglo[0] = null;
			arreglo[1] = null;
			arreglo[2] = null;
			return arreglo;
		}
	}

	public Object[] obtenerArregloDeListaPartidaIndividualAbiertaXVendedor(
			String codigoVendedor, String fechaHasta, long contador) {
		Object arreglo[] = new Object[3];
		List<PartidaIndividualAbierta> partidas = new ArrayList<PartidaIndividualAbierta>();
		List<DetallePagoPartidaIndividualAbierta> pagosPartidas = new ArrayList<DetallePagoPartidaIndividualAbierta>();
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_VEND_FIND_T_ITEMS");
				con.CreaTabla("IS_SEARCH_OPTIONS");
				con.IngresarDatoTabla(codigoVendedor, "CUST_ID", 1);
				con.IngresarDatoTabla("", "DOC_TYPE", 1);
				con.IngresarDatoTabla("X", "OPEN_ITEMS", 1);
				con.IngresarDatoTabla("", "CLEARED_ITEMS", 1);
				con.IngresarDatoTabla("", "ALL_ITEMS", 1);
				con.IngresarDatoTabla("", "DATE_FROM", 1);
				con.IngresarDatoTabla(fechaHasta, "DATE_TO", 1);
				con.IngresarDatoTabla("", "WITH_EXPIRATION_DATE", 1);
				con.EjecutarRFC();
				con.CreaTabla("ET_OPEN_ITEM_VND");
				@SuppressWarnings("unchecked")
				List<String> lista = con.ObtenerDatosTabla();
				HashMap<String, Long> asociacion = new HashMap<String, Long>();
				if (lista.size() > 0) {
					for (int i = 0; i < lista.size(); i++) {
						String[] valores = lista.get(i).toString().split("[¬]");
						// String[] valores =
						// String.valueOf(lista.get(i)).split("[¬]");
						PartidaIndividualAbierta partida = new PartidaIndividualAbierta();
						partida.setIdPartidaIndividualAbierta(contador);
						partida.setCodigoCliente(valores[1].trim());
						partida.setDocNo(valores[2].trim());
						partida.setPstngDate(("null")
								.equalsIgnoreCase(valores[3].trim()) ? ""
								: Util.convierteFecha(valores[3].trim()));
						partida.setDocDate(("null").equalsIgnoreCase(valores[4]
								.trim()) ? "" : Util.convierteFecha(valores[4]
								.trim()));
						partida.setEntryDate(("null")
								.equalsIgnoreCase(valores[5].trim()) ? ""
								: Util.convierteFecha(valores[5].trim()));
						partida.setExpirationDate(("null")
								.equalsIgnoreCase(valores[6].trim()) ? ""
								: Util.convierteFecha(valores[6].trim()));
						partida.setCurrency(valores[7].trim());
						partida.setAmtDoccur(valores[8].trim());
						partida.setRefOrgUn(valores[9].trim());
						partida.setRefDocNo(valores[10].trim());
						partida.setDocType(valores[11].trim());
						partida.setItemNum(valores[12].trim());
						partida.setPostKey(valores[13].trim());
						partida.setPsprt(valores[14].trim());
						partida.setPszah(valores[15].trim());
						partida.setPsskt(valores[16].trim());
						partida.setInvRef(valores[17].trim());
						partida.setInvItem(valores[18].trim());
						partida.setIsLeaf(valores[19].trim());
						partida.setIsExpanded(valores[20].trim());
						partida.setIsReadOnly(valores[21].trim());
						partida.setIndice(valores[22].trim());
						partida.setDisplayColor(valores[23].trim());
						partida.setFiscYear(valores[24].trim());
						partida.setFisPeriod(valores[25].trim());
						partida.setSgtxt(valores[26].trim());
						partida.setIsReadOnly2(valores[27].trim());
						partida.setDbCrInd(valores[28].trim());
						partida.setVerzn(valores[29].trim());
						partidas.add(partida);
						asociacion.put(
								valores[2].trim() + "" + valores[12].trim(),
								contador);
						contador++;
					}
				}
				con.CreaTabla("ET_OPEN_ITEM_LEAD_VND");
				@SuppressWarnings("unchecked")
				List<String> lista1 = con.ObtenerDatosTabla();
				if (lista.size() > 0) {
					for (int i = 0; i < lista1.size(); i++) {
						String[] valores = lista1.get(i).toString()
								.split("[¬]");
						// String[] valores =
						// String.valueOf(lista.get(i)).split("[¬]");
						DetallePagoPartidaIndividualAbierta detalle = new DetallePagoPartidaIndividualAbierta();
						detalle.setIdDetallePagoPartidaIndividualAbierta(i);
						Long valor = asociacion.get(valores[17].trim() + ""
								+ valores[18].trim());
						detalle.setIdPartidaIndividualAbierta(valor);
						detalle.setDocNo(valores[2].trim());
						detalle.setPstngDate(("null")
								.equalsIgnoreCase(valores[3].trim()) ? ""
								: Util.convierteFecha(valores[3].trim()));
						detalle.setDocDate(("null").equalsIgnoreCase(valores[4]
								.trim()) ? "" : Util.convierteFecha(valores[4]
								.trim()));
						detalle.setEntryDate(("null")
								.equalsIgnoreCase(valores[5].trim()) ? ""
								: Util.convierteFecha(valores[5].trim()));
						detalle.setExpirationDate(("null")
								.equalsIgnoreCase(valores[6].trim()) ? ""
								: Util.convierteFecha(valores[6].trim()));
						detalle.setCurrency(valores[7].trim());
						detalle.setAmtDoccur(valores[8].trim());
						detalle.setRefOrgUn(valores[9].trim());
						detalle.setRefDocNo(valores[10].trim());
						detalle.setDocType(valores[11].trim());
						detalle.setItemNum(valores[12].trim());
						detalle.setPostKey(valores[13].trim());
						detalle.setPsprt(valores[14].trim());
						detalle.setPszah(valores[15].trim());
						detalle.setPsskt(valores[16].trim());
						detalle.setInvRef(valores[17].trim());
						detalle.setInvItem(valores[18].trim());
						detalle.setIsLeaf(valores[19].trim());
						detalle.setIsExpanded(valores[20].trim());
						detalle.setIsReadOnly(valores[21].trim());
						detalle.setIndice(valores[22].trim());
						detalle.setDisplayColor(valores[23].trim());
						detalle.setFiscYear(valores[24].trim());
						detalle.setFisPeriod(valores[25].trim());
						detalle.setSgtxt(valores[26].trim());
						detalle.setIsReadOnly2(valores[27].trim());
						detalle.setDbCrInd(valores[28].trim());
						detalle.setVerzn(valores[29].trim());
						pagosPartidas.add(detalle);
					}
				}
				con.DesconectarSAP();
				arreglo[0] = partidas;
				arreglo[1] = pagosPartidas;
				arreglo[2] = contador;
				con.DesconectarSAP();
				return arreglo;
			} else {
				arreglo[0] = partidas;
				arreglo[1] = pagosPartidas;
				arreglo[2] = contador;
				return arreglo;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			arreglo[0] = null;
			arreglo[1] = null;
			arreglo[2] = null;
			return arreglo;
		}
	}

	@SuppressWarnings("unchecked")
	public Object[] grabarRegistroPagoCliente(
			List<PagoRecibido> pagosRecibidos, List<PagoParcial> pagosParciales) {
		Object arregloRegistroPagoCliente[] = new Object[2];
		List<String> detalles = new ArrayList<String>();
		List<String> mensajes = new ArrayList<String>();
		SqlPedidoImpl sqlPedidoImpl = new SqlPedidoImpl();
		String strBloque = sqlPedidoImpl.obtenerBloqueoPedido("COBRANZA_AUTO");
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				String codigo = Promesa.datose.get(0).getStrCodigo();
				String secuVend = Util.obtenerSecuenciaPorVendedor(codigo);
				PagoRecibido PrimerPagoRecibido = pagosRecibidos.get(0);
				con.RegistrarRFC("ZSD_RFC_CUSTOMER_ENTER_PAYMENT");
				if (PrimerPagoRecibido.getIdCabecera() == 0) {
					Long lSecu = Long.parseLong(secuVend);
					lSecu++;
					con.IngresarDatosInput(String.valueOf(lSecu), "IV_DOCSECU");
					con.IngresarDatosInput(String.valueOf(lSecu), "IV_USERSECU");
					Util.actualizarSecuencia(codigo, String.valueOf(lSecu));
				} else {
					Long id = PrimerPagoRecibido.getIdCabecera();
					String idCabecera = String.valueOf(id);
					String cabSecu;
					cabSecu = Util.obtenerSecuenciaPorCabecera(idCabecera);
					con.IngresarDatosInput(cabSecu, "IV_DOCSECU");
					con.IngresarDatosInput(secuVend, "IV_USERSECU");
				}
				// Marcelo Moyano 26/03/2013
				con.CreaTabla("IS_PAYMENT_INFO");
				for (int i = 0; i < pagosRecibidos.size(); i++) {
					PagoRecibido pr = pagosRecibidos.get(i);
					String idFormaPago = pr.getFormaPago().getIdFormaPago();
					con.IngresarDatoTabla(idFormaPago, "BLART", i + 1);
					con.IngresarDatoTabla(pr.getReferencia(), "XBLNR", i + 1);
					con.IngresarDatoTabla(pr.getImporte() + "", "WRBTR", i + 1);
					String codCli = pr.getBancoCliente().getCodigoCliente();
					con.IngresarDatoTabla(codCli, "CUST_ID", i + 1);
					String nroFact;
					nroFact = pr.getNumeroFactura();
					con.IngresarDatoTabla(nroFact, "XREF2_HD", i + 1);
					String nroCta = pr.getBancoCliente().getNroCtaBcoCliente();
					con.IngresarDatoTabla(nroCta, "BANKN", i + 1);
					if (("VC").equals(idFormaPago)) {
						BancoCliente bc = pr.getBancoCliente();
						String descBcoCli = bc.getDescripcionBancoCliente();
						con.IngresarDatoTabla(descBcoCli, "BANK", i + 1);
					} else if (("VX").equals(idFormaPago)) {
						BancoPromesa bp = pr.getBancoPromesa();
						String descripBcoPro = bp.getDescripcionBancoPromesa();
						con.IngresarDatoTabla(descripBcoPro, "BANK", i + 1);
					} else if (("VO").equals(idFormaPago)) {
						BancoPromesa bp = pr.getBancoPromesa();
						String descripBcoPro = bp.getDescripcionBancoPromesa();
						con.IngresarDatoTabla(descripBcoPro, "BANK", i + 1);// Recaudo
					}else if (("VY").equals(idFormaPago)) {
						BancoPromesa bp = pr.getBancoPromesa();
						//con.IngresarDatoTabla(bp.getIdBancoPromesa(), "BANKN", i + 1);
						String descripBcoPro = bp.getDescripcionBancoPromesa();
						con.IngresarDatoTabla(descripBcoPro, "BANK", i + 1);// Recaudo
					}else {
						// Otros
						con.IngresarDatoTabla("", "BANK", i + 1);
					}
					BeanDato bd = Promesa.datose.get(0);
					String cod;
					cod = bd.getStrCodigo();
					con.IngresarDatoTabla(cod, "RESPONSABLE_ID", i + 1);
					con.IngresarDatoTabla("", "UMSKZ", i + 1);
					con.IngresarDatoTabla("", "COMMENTS", i + 1);
					String fdoc = pr.getFechaVencimiento();
					con.IngresarDatoTabla(fdoc, "EXPIRATION_DATE", i + 1);
					if (pr.getFechaDocumento() == null
							|| pr.getFechaDocumento().length() == 0) {
						String fDoc;
						fDoc = Util.convierteFechaAFormatoYYYYMMdd(new Date());
						pr.setFechaDocumento(fDoc);
					}
					// FORMATO DE FECHAS ES YYYYMMDD: EJEMPLO 20140723
					try {
						String fDoc;
						fDoc = pr.getFechaDocumento();
						con.IngresarDatoTabla(fDoc, "DOCUMENT_DATE", i + 1);
					} catch (Exception e) {
						// TODO: handle exception
						String fechaDoc = pr.getFechaDocumento();
						String fDoc;
						fDoc = Util.convierteFechaPuntoDD_MMM_YYYAFormatoYYYYMMdd(fechaDoc);
						pr.setFechaDocumento(fDoc);
						con.IngresarDatoTabla(fDoc, "DOCUMENT_DATE", i + 1);
					}
					con.IngresarDatoTabla("", "IS_READ_ONLY", i + 1);
					con.IngresarDatoTabla("", "IS_READ_ONLY2", i + 1);
					con.IngresarDatoTabla("", "IS_READ_ONLY3", i + 1);
					con.IngresarDatoTabla("", "IS_READ_ONLY4", i + 1);
					con.IngresarDatoTabla("", "IS_READ_ONLY5", i + 1);
					con.IngresarDatoTabla("", "IS_READ_ONLY6", i + 1);
					con.IngresarDatoTabla((i + 1) + "", "POSITION", i + 1);
				}
				con.CreaTabla("IS_OPEN_ITEM_D");
				if(!strBloque.equals("X")){
					for (int i = 0; i < pagosParciales.size(); i++) {
						PagoParcial pp = pagosParciales.get(i);
						String nroDoc;
						nroDoc = pp.getNumeroDocumento();
						con.IngresarDatoTabla(nroDoc, "DOC_NO", i + 1);
	
						String pstng;
						pstng = pp.getPstngDate();
						Date date;
						date = Util.convierteCadenaDDMMYYYYAFecha(pstng);
						String pstngDate;
						pstngDate = Util.convierteFechaAFormatoYYYYMMdd(date);
						con.IngresarDatoTabla(pstngDate, "PSTNG_DATE", i + 1);
	
						String doc = pp.getDocDate();
						Date dateDocDate = Util.convierteCadenaDDMMYYYYAFecha(doc);
						String docDate;
						docDate = Util.convierteFechaAFormatoYYYYMMdd(dateDocDate);
						con.IngresarDatoTabla(docDate, "DOC_DATE", i + 1);
	
						String entry = pp.getEntryDate();
						Date dateEntry = Util.convierteCadenaDDMMYYYYAFecha(entry);
						String entryDate;
						entryDate = Util.convierteFechaAFormatoYYYYMMdd(dateEntry);
						con.IngresarDatoTabla(entryDate, "ENTRY_DATE", i + 1);
	
						String Venci = pp.getVencimiento();
						Date expiDate;
						expiDate = Util.convierteCadenaDDMMYYYYAFecha(Venci);
						String expiration;
						expiration = Util.convierteFechaAFormatoYYYYMMdd(expiDate);
						con.IngresarDatoTabla(expiration, "EXPIRATION_DATE", i + 1);
	
						con.IngresarDatoTabla(pp.getCurrency(), "CURRENCY", i + 1);
	
						String importePagoTotal;
						importePagoTotal = "" + pp.getImportePagoTotal();
						con.IngresarDatoTabla(importePagoTotal, "AMT_DOCCUR", i + 1);
						con.IngresarDatoTabla(pp.getRefOrgUn(), "REF_ORG_UN", i + 1);
	
						String ref = pp.getReferencia();
						con.IngresarDatoTabla(ref, "REF_DOC_NO", i + 1);
						String claseDoc;
						claseDoc = pp.getClaseDocumento();
						con.IngresarDatoTabla(claseDoc, "DOC_TYPE", i + 1);
						con.IngresarDatoTabla(pp.getPosicion(), "ITEM_NUM", i + 1);
						con.IngresarDatoTabla(pp.getPostKey(), "POST_KEY", i + 1);
	
						String importePagoParcial = "" + pp.getImportePagoParcial();
						con.IngresarDatoTabla(importePagoParcial, "PSPRT", i + 1);
	
						String importePago = "" + pp.getImportePago();
						con.IngresarDatoTabla(importePago, "PSZAH", i + 1);
						con.IngresarDatoTabla(pp.getPsskt(), "PSSKT", i + 1);
						con.IngresarDatoTabla(pp.getInvRef(), "INV_REF", i + 1);
						con.IngresarDatoTabla(pp.getInvItem(), "INV_ITEM", i + 1);
						con.IngresarDatoTabla(pp.getIsLeaf(), "IS_LEAF", i + 1);
	
						String isExp = pp.getIsExpanded();
						con.IngresarDatoTabla(isExp, "IS_EXPANDED", i + 1);
	
						String isReadOnly = pp.getIsReadOnly();
						con.IngresarDatoTabla(isReadOnly, "IS_READ_ONLY", i + 1);
						con.IngresarDatoTabla(pp.getIndice(), "INDEX", i + 1);
	
						String color = pp.getDisplayColor();
						con.IngresarDatoTabla(color, "DISPLAY_COLOR", i + 1);
						con.IngresarDatoTabla(pp.getFiscYear(), "FISC_YEAR", i + 1);
	
						String fisPeriod = pp.getFisPeriod();
						con.IngresarDatoTabla(fisPeriod, "FIS_PERIOD", i + 1);
						con.IngresarDatoTabla(pp.getSgtxt(), "SGTXT", i + 1);
	
						String isReadOnly2 = pp.getIsReadOnly2();
						con.IngresarDatoTabla(isReadOnly2, "IS_READ_ONLY2", i + 1);
						con.IngresarDatoTabla(pp.getDbCrInd(), "DB_CR_IND", i + 1);
						con.IngresarDatoTabla(pp.getVerzn(), "VERZN", i + 1);
					}
				}
				
				con.EjecutarRFC();
				con.CreaTabla("IS_PAYMENT_DETAIL");
				detalles = con.ObtenerDatosTabla();
				con.CreaTabla("ET_MSG");
				mensajes = con.ObtenerDatosTabla();
				con.DesconectarSAP();
				arregloRegistroPagoCliente[0] = detalles;
				arregloRegistroPagoCliente[1] = mensajes;
				con.DesconectarSAP();
				return arregloRegistroPagoCliente;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			arregloRegistroPagoCliente[0] = detalles;
			arregloRegistroPagoCliente[1] = mensajes;
			return arregloRegistroPagoCliente;
		}
	}

	public String[] guardarComentarioPagoParcial(PagoParcial pp) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			String[] valores = new String[20];
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_SAVE_TEXT");
				String sociedad = Util.obtenerSociedadPromesa();
				con.IngresarDatosInput(sociedad, "IV_BUKRS"); // Sociedad
				String fiscYear = Util.convierteFechaAFormatoYYYY(new Date());
				con.IngresarDatosInput(fiscYear, "IV_FISC_YEAR"); // Ejercicio
				// Número de un documento contable
				String nroDoc = pp.getNumeroDocumento();
				con.IngresarDatosInput(nroDoc.trim(), "IV_DOC_NO");
				String pos = Util.completarCeros(6, pp.getPosicion().trim());
				con.IngresarDatosInput(pos, "IV_POS");
				con.CreaTabla("IT_LINES");
				con.IngresarDatoTabla(pp.getComentario(), "TDLINE", 1);
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
	public String leerComentarioPagoParcial(PagoParcial pp) {
		String comentario = "";
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_ORDER_READ_TEXT");
				String sociedad = Util.obtenerSociedadPromesa();
				con.IngresarDatosInput(sociedad, "IV_BUKRS");
				String ejercicio = Util.convierteFechaAFormatoYYYY(new Date());
				con.IngresarDatosInput(ejercicio, "IV_FISC_YEAR");
				// Número de un documento contable
				String nroDoc = pp.getNumeroDocumento();
				con.IngresarDatosInput(nroDoc.trim(), "IV_DOC_NO");
				// del documento comercial
				String pos = Util.completarCeros(6, pp.getPosicion().trim());
				con.IngresarDatosInput(pos, "IV_POS");
				con.CreaTabla("ET_MSG");
				con.EjecutarRFC();
				con.CreaTabla("IT_LINES");
				List<String> mensajes = con.ObtenerDatosTabla();
				if (!mensajes.isEmpty()) {
					for (String string : mensajes) {
						String observaciones[] = string.split("[¬]");
						if (observaciones.length == 3) {
							comentario = observaciones[2];
						}
					}
				}
				con.CreaTabla("ET_MSG");
				mensajes = con.ObtenerDatosTabla();
			}
			return comentario;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/*
	 * Marcelo Moyano Requerimiento PRO-2014-0004 15/07/2014
	 */
	public List<String[]> sincronizarRegistroAnticipo(
			List<AnticipoCliente> antiClientes) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				List<String[]> arreglos = new ArrayList<String[]>();
				for (AnticipoCliente ac : antiClientes) {
					String[] arreglo = new String[3];
					String formaPago = ac.getIdFormaPago();
					con.RegistrarRFC("ZSD_RFC_CUSTOMER_ENTER_DOWNPAY");
					BeanDato bd = Promesa.datose.get(0);
					String cod = bd.getStrCodigo();
					String secVend;
					secVend = Util.obtenerSecuenciaPorVendedor(cod);
					String antSec = ac.getAnticipoSecuencia();
					con.IngresarDatosInput(antSec, "IV_DOCSECU");
					con.IngresarDatosInput(secVend, "IV_USERSECU");
					// Marcelo Moyano 27/03/2013
					con.CreaTabla("IS_PAYMENT_INFO");
					con.IngresarDatoTabla(ac.getIdFormaPago(), "BLART", 1);
					con.IngresarDatoTabla(ac.getReferencia(), "XBLNR", 1);
					con.IngresarDatoTabla(ac.getImporte(), "WRBTR", 1);
					con.IngresarDatoTabla(ac.getCodigoCliente(), "CUST_ID", 1);
					con.IngresarDatoTabla(ac.getIdBancoPromesa(), "XREF2_HD", 1);
					con.IngresarDatoTabla(ac.getNroCtaBcoCliente(), "BANKN", 1);
					String descrp;
					String fDeposito;
					String creacion;
					if (formaPago.equals("VY")) {
						// Marcelo Moyano 06/03/2013 - 14:57
						descrp = ac.getDescripcionBancoCliente();
						con.IngresarDatoTabla(descrp, "BANK", 1);
					} else if (formaPago.equals("VX")) {

						creacion = ac.getFechaCreacion();
						fDeposito = Util
								.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(creacion);
						con.IngresarDatoTabla(fDeposito, "DOCUMENT_DATE", 1);
						descrp = ac.getDescripcionBancoPromesa();
						con.IngresarDatoTabla(descrp, "BANK", 1);
					} else if (formaPago.equals("VZ")) {
						creacion = ac.getFechaCreacion();
						fDeposito = Util
								.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(creacion);
						con.IngresarDatoTabla(fDeposito, "DOCUMENT_DATE", 1);
					}
					if (ac.getFechaCreacion() == null
							|| ac.getFechaCreacion().length() == 0) {
						creacion = Util
								.convierteFechaAFormatoYYYYMMdd(new Date());
						ac.setFechaCreacion(creacion);
					}
					String codVend = ac.getCodigoVendedor();
					con.IngresarDatoTabla(codVend, "RESPONSABLE_ID", 1);
					con.IngresarDatoTabla(ac.getObservaciones(), "COMMENTS", 1);
					// Marcelo Moyano
					con.EjecutarRFC();
					String cadenaNumTicket = con.ObtenerDato("EV_TICKET_NRO");
					con.CreaTabla("ET_MSG");
					@SuppressWarnings("unchecked")
					List<String> mensaje = con.ObtenerDatosTabla();
					try {
						String[] valores;
						valores = mensaje.get(0).toString().split("[¬]");
						// String[] valores =
						// String.valueOf(mensaje.get(0)).split("[¬]");
						arreglo[0] = cadenaNumTicket; // Num. Ticket.
						arreglo[1] = valores[1].trim(); // Indicador.
						arreglo[2] = valores[3].trim(); // Mensaje.
					} catch (Exception e) {
						// TODO: handle exception
						arreglo[0] = cadenaNumTicket; // Num. Ticket.
						arreglo[1] = "E"; // Indicador.
						arreglo[2] = "Error"; // Mensaje.
					}
					arreglos.add(arreglo);
				}
				con.DesconectarSAP();
				return arreglos;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}
	
	public int registrarCobranzasEliminadas(List<RegistroPagoCliente> cobranzas){
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				con.RegistrarRFC("ZSD_RFC_COB_ELIM");
				int i = 1;
				con.CreaTabla("IT_COD_ELIM");
				for (RegistroPagoCliente registro : cobranzas) {
					this.registrarRegistroPago(con,registro,i);
					i++;
				}
				con.EjecutarRFC();
				con.CreaTabla("TBL_RESPUESTA");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return 0;
	}

	private void registrarRegistroPago(ConexionSAP con, RegistroPagoCliente registro, int i) throws Exception {
		
		CabeceraRegistroPagoCliente cabecera = registro.getCabeceraRegistroPagoCliente();
		List<PagoRecibido> recibidos = registro.getListaPagoRecibido();
		
		String codigo = Promesa.datose.get(0).getStrCodigo();
		String secuVend = Util.obtenerSecuenciaPorVendedor(codigo);
		
		
		
		con.IngresarDatoTabla(cabecera.getCodigoVendedor(), "RESPONSABLE_ID",i);
		con.IngresarDatoTabla(Util.completarCeros(10, cabecera.getCodigoCliente()), "CUST_ID",i);
		con.IngresarDatoTabla(cabecera.getCabeceraSecuencia(), "DOC_SECU",i);
		con.IngresarDatoTabla("COB", "TIPO_COB",i);
		con.IngresarDatoTabla(recibidos.get(0).getFormaPago().getIdFormaPago(), "BLART", i);
		con.IngresarDatoTabla(recibidos.get(0).getReferencia(), "XBLNR", i);
		con.IngresarDatoTabla(recibidos.get(0).getNumeroFactura(), "XREF2_HD", i);
		con.IngresarDatoTabla(recibidos.get(0).getBancoCliente().getNroCtaBcoCliente(), "BANKN", i);
		con.IngresarDatoTabla(recibidos.get(0).getBancoCliente().getDescripcionBancoCliente(), "BANK", i);
		con.IngresarDatoTabla(recibidos.get(0).getFechaVencimiento(), "EXPIRATION_DATE", i);
		con.IngresarDatoTabla(recibidos.get(0).getFechaDocumento(), "DOCUMENT_DATE", i);
		con.IngresarDatoTabla(recibidos.get(0).getBancoPromesa().getDescripcionBancoPromesa(), "BCO_PROMESA", i);
		con.IngresarDatoTabla(cabecera.getImporteEntrado()+"", "IMPORT_PG", i);
	}
	

	public int registrarAnticiposEliminados(List<AnticipoCliente> anticipos){
		int cantidad = 0;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_COB_ELIM");
				int i=1;
				con.CreaTabla("IT_COD_ELIM");
				for(AnticipoCliente ant : anticipos){
					
					BeanDato bd = Promesa.datose.get(0);
					String cod = bd.getStrCodigo();
					
					String fp = ant.getIdFormaPago();
					
					con.IngresarDatoTabla(ant.getCodigoVendedor(), "RESPONSABLE_ID", i);
					con.IngresarDatoTabla(Util.completarCeros(10, ant.getCodigoCliente()), "CUST_ID", i);
					con.IngresarDatoTabla(ant.getAnticipoSecuencia(), "DOC_SECU", i);
					con.IngresarDatoTabla("ANT", "TIPO_COB",i);
					con.IngresarDatoTabla(fp, "BLART", i);
					if(!fp.equalsIgnoreCase("VT")){
						con.IngresarDatoTabla(ant.getReferencia(), "XBLNR", i);
					} else {
						con.IngresarDatoTabla(ant.getReferencia(), "XREF2_HD", i);
					}
					con.IngresarDatoTabla(ant.getNroCtaBcoCliente(), "BANKN", i);
					con.IngresarDatoTabla(ant.getDescripcionBancoCliente(), "BANK", i);
					
					String obs = ant.getObservaciones();
					if(ant.getObservaciones().length() >50 ){
						obs = ant.getObservaciones().substring(0, 49);
					}
					con.IngresarDatoTabla(obs, "COMMENTS", i);
					con.IngresarDatoTabla(Util.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(ant.getFechaCreacion()), "DOCUMENT_DATE", i);
					con.IngresarDatoTabla(ant.getDescripcionBancoPromesa(), "BCO_PROMESA", i);
					con.IngresarDatoTabla(ant.getImporte(), "IMPORT_PG", i);
					i++;
					
				}
				con.EjecutarRFC();
				con.CreaTabla("ET_MSG");
				cantidad= 1;
			} else {cantidad = 0;}
		} catch(Exception e) {
			cantidad= 0;
		} 
		return cantidad;
	}
	
	public List<Presupuesto> getPresupuestoByVendedor(String codigoVendedor){
        try {
        	ConexionSAP con = Conexiones.getConexionSAP();
            List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
            if (con != null) {
                con.RegistrarRFC("ZSD_RFC_PRESUPUESTO_CLIENTES");
                con.IngresarDatosInput(codigoVendedor, "IV_KUNN2");
                String fecha = Util.convierteFechaAFormatoYYYYMMdd(new Date());
                con.IngresarDatosInput(fecha, "IV_DATUM");
                con.EjecutarRFC();
                con.CreaTabla("ET_PRESUP_VTA");
                List<String> respuesta = con.ObtenerDatosTabla();
                con.DesconectarSAP();
                if(respuesta != null && respuesta.size() > 0){
                    for (String str : respuesta) {
                        String [] valor = str.split("¬");
                        Presupuesto pre =  new Presupuesto();
                        pre.setCodCliente(valor[2].trim());
                        pre.setPresupuestoAnual(valor[7].trim());
                        pre.setPresupuestoReal(valor[6].trim());
                        pre.setPresupuestoMensual(valor[8].trim());
                        pre.setPresupuestoFecha(valor[9].trim());
                        pre.setVentaAnual(valor[10].trim());
                        pre.setVentaMensual(valor[11].trim());
                        pre.setVentaAcumAnioAnt(valor[12].toString());
                        pre.setPromoPlus(valor[13].toString());
                        pre.setVta_gracia(valor[14].toString());
                        pre.setFechaRegistro(Util.convierteFecha(valor[15].toString()));
                        
                        presupuestos.add(pre);
                    }
                }
                return presupuestos;
            } 
            return null;
            
        } catch (Exception e) {
        	Util.mostrarExcepcion(e);
            return null;
        }
    }
}