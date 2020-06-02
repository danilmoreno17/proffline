package com.promesa.sincronizacion.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.bean.BancoCliente;
import com.promesa.cobranzas.bean.BancoPromesa;
import com.promesa.cobranzas.bean.CabeceraHojaMaestraCredito;
import com.promesa.cobranzas.bean.DatoCredito;
import com.promesa.cobranzas.bean.DetallePagoPartidaIndividualAbierta;
import com.promesa.cobranzas.bean.DiaDemoraTrasVencimiento;
import com.promesa.cobranzas.bean.FormaPago;
import com.promesa.cobranzas.bean.HistorialPago;
import com.promesa.cobranzas.bean.PartidaIndividual;
import com.promesa.cobranzas.bean.PartidaIndividualAbierta;
import com.promesa.cobranzas.bean.PedidoPendienteDevolucion;
import com.promesa.cobranzas.bean.Presupuesto;
import com.promesa.cobranzas.bean.RegistroPagoCliente;
import com.promesa.cobranzas.bean.ValorPorVencer;
import com.promesa.cobranzas.dao.bean.HojaMaestraCredito;
import com.promesa.cobranzas.sql.impl.SqlAnticipoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlBancoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlBancoPromesaImpl;
import com.promesa.cobranzas.sql.impl.SqlCabeceraHojaMaestraCreditoImpl;
import com.promesa.cobranzas.sql.impl.SqlDetallePagoPartidaIndividualAbiertaImpl;
import com.promesa.cobranzas.sql.impl.SqlDiaDemoraTrasVencimientoImpl;
import com.promesa.cobranzas.sql.impl.SqlFormaPagoAnticipoImpl;
import com.promesa.cobranzas.sql.impl.SqlFormaPagoCobranzaImpl;
import com.promesa.cobranzas.sql.impl.SqlHistorialPagoImpl;
import com.promesa.cobranzas.sql.impl.SqlNotaCreditoImpl;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualAbiertaImpl;
import com.promesa.cobranzas.sql.impl.SqlPartidaIndividualImpl;
import com.promesa.cobranzas.sql.impl.SqlPedidoPendienteDevolucionImpl;
import com.promesa.cobranzas.sql.impl.SqlProtestoImpl;
import com.promesa.cobranzas.sql.impl.SqlRegistroPagoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlValorPorVencerImpl;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanSecuencia;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SCobranzas;
import com.promesa.util.Util;

public class SincronizacionCobranzas {

	private SCobranzas sCobranzas = null;
	private SqlClienteImpl sqlClienteImpl = null;
	private boolean resultado = false;

	@SuppressWarnings("static-access")
	public boolean sincronizaBancoCliente() {
		resultado = true;
		sqlClienteImpl = new SqlClienteImpl();
		List<BeanCliente> listaCliente = sqlClienteImpl.listarClientes();
		List<BancoCliente> lstBancoCliente = new ArrayList<BancoCliente>();
		if (listaCliente.size() > 0) {
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Banco Cliente Vendedor.");
			List<String> lstBanco;
			try {
				String codigoVendedor = Promesa.getInstance().datose.get(0).getStrCodigo();
				lstBanco = sCobranzas.obtenerBancosAsociadosXVendedor(Util.completarCeros(10, codigoVendedor));
				if (lstBanco.size() > 0) {
					for (String banco : lstBanco) {
						String[] arregloBanco = banco.trim().split("[¬]");
						BancoCliente bancoCliente = new BancoCliente();
						bancoCliente.setCodigoCliente(Util.eliminarCerosInicios(arregloBanco[1].trim()));
						bancoCliente.setNroCtaBcoCliente(arregloBanco[4]);
						bancoCliente.setIdBancoCliente(arregloBanco[3]);
						bancoCliente.setDescripcionBancoCliente(arregloBanco[5]);
						lstBancoCliente.add(bancoCliente);
					}
				}
			} catch (Exception e) {
				resultado = false;
			}
		}
		if (lstBancoCliente.size() > 0) {
			SqlBancoClienteImpl sqlBancoClienteImpl = new SqlBancoClienteImpl();
			sqlBancoClienteImpl.eliminarBancoCliente();
			try {
				sqlBancoClienteImpl.insertarListaBancoCliente(lstBancoCliente);
			} catch (Exception e) {
				resultado = false;
			}
		}
		return resultado;
	}

	public boolean sincronizaFormaPagoCobranza() {
		try {
			sCobranzas = new SCobranzas();
			List<FormaPago> lstFormaPagoCobranza = sCobranzas.obtenerListaFormaPago("01");
			if (lstFormaPagoCobranza.size() > 0) {
				SqlFormaPagoCobranzaImpl sqlFormaPagoCobranzaImpl = new SqlFormaPagoCobranzaImpl();
				sqlFormaPagoCobranzaImpl.eliminarListaFormaPagoCobranza();
				sqlFormaPagoCobranzaImpl.insertarListaFormaPagoCobranza(lstFormaPagoCobranza);
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
		}
		return resultado;
	}

	public boolean sincronizaFormaPagoAnticipo() {
		try {
			sCobranzas = new SCobranzas();
			List<FormaPago> lstFormaPagoAnticipo = sCobranzas.obtenerListaFormaPago("02");
			if (lstFormaPagoAnticipo.size() > 0) {
				SqlFormaPagoAnticipoImpl sqlFormaPagoAnticipoImpl = new SqlFormaPagoAnticipoImpl();
				sqlFormaPagoAnticipoImpl.eliminarListaFormaPagoAnticipo();
				sqlFormaPagoAnticipoImpl.insertarListaFormaPagoAnticipo(lstFormaPagoAnticipo);
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
		}
		return resultado;
	}

	public boolean sincronizaBancoPromesa() {
		try {
			sCobranzas = new SCobranzas();
			List<BancoPromesa> lstBancoPromesa = sCobranzas.obtenerListaBancoPromesa();
			if (lstBancoPromesa.size() > 0) {
				SqlBancoPromesaImpl sqlBancoPromesaImpl = new SqlBancoPromesaImpl();
				sqlBancoPromesaImpl.eliminarListaBancoPromesa();
				sqlBancoPromesaImpl.insertarListaBancoPromesa(lstBancoPromesa);
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
		}
		return resultado;
	}

	public boolean sincronizaHojaMaestraCredito() {
		resultado = true;
		sqlClienteImpl = new SqlClienteImpl();
		List<BeanCliente> listaCliente = sqlClienteImpl.listarClientes();
		List<CabeceraHojaMaestraCredito> lstCabeceraHojaMaestraCredito = new ArrayList<CabeceraHojaMaestraCredito>();
		List<DiaDemoraTrasVencimiento> lstDiaDemoraTrasVencimiento = new ArrayList<DiaDemoraTrasVencimiento>();
		List<HistorialPago> lstHistorialPago = new ArrayList<HistorialPago>();
		List<ValorPorVencer> lstValorPorVencer = new ArrayList<ValorPorVencer>();
		List<DatoCredito> lstNotaCredito = new ArrayList<DatoCredito>();
		List<DatoCredito> lstProtesto = new ArrayList<DatoCredito>();
		if (listaCliente.size() > 0) {
			@SuppressWarnings("static-access")
			String codigoVendedor = Promesa.getInstance().datose.get(0).getStrCodigo();
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Hoja Maestra Crédito Vendedor");
			HojaMaestraCredito hojaMaestraCredito;
			try {
				hojaMaestraCredito = sCobranzas.obtenerHojaMaestraCreditoXVendedor(codigoVendedor);
				if (hojaMaestraCredito != null) {
					if(hojaMaestraCredito.getListaCabeceraHojaMaestraCredito().size() > 0 ){
						lstCabeceraHojaMaestraCredito.addAll(hojaMaestraCredito.getListaCabeceraHojaMaestraCredito());
					}
					if (hojaMaestraCredito.getListaDiaDemoraTrasVencimiento().size() > 0) {
						lstDiaDemoraTrasVencimiento.addAll(hojaMaestraCredito.getListaDiaDemoraTrasVencimiento());
					}
					if (hojaMaestraCredito.getListaHistorialPago().size() > 0) {
						lstHistorialPago.addAll(hojaMaestraCredito.getListaHistorialPago());
					}
					if (hojaMaestraCredito.getListaValorPorVencer().size() > 0) {
						lstValorPorVencer.addAll(hojaMaestraCredito.getListaValorPorVencer());
					}
					if (hojaMaestraCredito.getListaNotaCredito().size() > 0) {
						lstNotaCredito.addAll(hojaMaestraCredito.getListaNotaCredito());
					}
					if (hojaMaestraCredito.getListaProtesto().size() > 0) {
						lstProtesto.addAll(hojaMaestraCredito.getListaProtesto());
					}
				}
			} catch (Exception e) {
				resultado = false;
			}
		}
		if (lstCabeceraHojaMaestraCredito.size() > 0) {
			SqlCabeceraHojaMaestraCreditoImpl sqlCabeceraHojaMaestraCreditoImpl = new SqlCabeceraHojaMaestraCreditoImpl();
			sqlCabeceraHojaMaestraCreditoImpl.eliminarCabeceraHojaMaestraCredito();
			try {
				sqlCabeceraHojaMaestraCreditoImpl.insertarListaCabeceraHojaMaestraCredito(lstCabeceraHojaMaestraCredito);
			} catch (Exception e) {
				resultado = false;
			}
		}
		if (lstDiaDemoraTrasVencimiento.size() > 0) {
			SqlDiaDemoraTrasVencimientoImpl sqlDiaDemoraTrasVencimientoImpl = new SqlDiaDemoraTrasVencimientoImpl();
			sqlDiaDemoraTrasVencimientoImpl.eliminarListaDiaDemoraTrasVencimiento();
			sqlDiaDemoraTrasVencimientoImpl.insertarListaDiaDemoraTrasVencimiento(lstDiaDemoraTrasVencimiento);
		}
		if (lstHistorialPago.size() > 0) {
			SqlHistorialPagoImpl sqlHistorialPagoImpl = new SqlHistorialPagoImpl();
			sqlHistorialPagoImpl.eliminarListaHistorialPago();
			sqlHistorialPagoImpl.insertarListaHistorialPago(lstHistorialPago);
		}
		if (lstValorPorVencer.size() > 0) {
			SqlValorPorVencerImpl sqlValorPorVencerImpl = new SqlValorPorVencerImpl();
			sqlValorPorVencerImpl.eliminarListaValorPorVencer();
			sqlValorPorVencerImpl.insertarListaValorPorVencer(lstValorPorVencer);
		}
		if (lstNotaCredito.size() > 0) {
			SqlNotaCreditoImpl sqlNotaCreditoImpl = new SqlNotaCreditoImpl();
			sqlNotaCreditoImpl.eliminarListaNotaCredito();
			sqlNotaCreditoImpl.insertarListaNotaCredito(lstNotaCredito);
		}
		if (lstProtesto.size() > 0) {
			SqlProtestoImpl sqlProtestoImpl = new SqlProtestoImpl();
			sqlProtestoImpl.eliminarListaProtesto();
			sqlProtestoImpl.insertarListaProtesto(lstProtesto);
		}
		return resultado;
	}

	public boolean sincronizaPartidaIndividual() {
		resultado = true;
		sqlClienteImpl = new SqlClienteImpl();
		List<BeanCliente> listaCliente = sqlClienteImpl.listarClientes();
		List<PartidaIndividual> lstPartidaIndividual = new ArrayList<PartidaIndividual>();
		if (listaCliente.size() > 0) {
			@SuppressWarnings("static-access")
			String codigoVendedor = Promesa.getInstance().datose.get(0).getStrCodigo();
			sCobranzas = new SCobranzas();
			String fechaHasta = Util.convierteFechaAFormatoYYYYMMdd(new Date());
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Partida Individual Vendedor.");
			List<PartidaIndividual> listaPartidaIndividual = sCobranzas.obtenerListaPartidaIndividualXVendedor(codigoVendedor, "X", "", "", "", fechaHasta);
			if (listaPartidaIndividual.size() > 0) {
				lstPartidaIndividual.addAll(listaPartidaIndividual);
			}
		}
		if (lstPartidaIndividual.size() > 0) {
			SqlPartidaIndividualImpl sqlPartidaIndividualImpl = new SqlPartidaIndividualImpl();
			sqlPartidaIndividualImpl.eliminarListaPartidaIndividual();
			sqlPartidaIndividualImpl.insertarListaPartidaIndividual(lstPartidaIndividual);
		}
		return resultado;
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 
	 * 			METODO QUE SINCRONIZA LA SECUENCIA
	 * 			POR VENDEDOR
	 * 
	 * @return	RETORNA UN VALOR BOOLEANO DE
	 * 			CONFIRMACIÓN SI LA SINCRONIZACIÓN
	 * 			SE REALIZÓ EXITOSAMENTE O NO SE 
	 *			REALIZÓ EXITOSAMENTE
	 */
	public boolean sincronizacionSecuenciaPorVendedor() {
		try {
			resultado = true;
			@SuppressWarnings("static-access")
			String codigoVendedor = Promesa.getInstance().datose.get(0).getStrCodigo();
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Secuencia por Vendedor");
			BeanSecuencia bs =  sCobranzas.obtenerSecuenciaPorVendedor(codigoVendedor);
			if (bs != null) {
				Util.grabarSecuenciaPorVendedor(bs);
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
		return resultado;
	}

	public boolean sincronizaPedidoPendienteDevolucion() {
		resultado = true;
		sqlClienteImpl = new SqlClienteImpl();
		List<BeanCliente> listaCliente = sqlClienteImpl.listarClientes();
		List<PedidoPendienteDevolucion> lstPedidoPendienteDevolucion = new ArrayList<PedidoPendienteDevolucion>();
		if (listaCliente.size() > 0) {
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Pedido Pendiente Devolución");
			for (BeanCliente beanCliente : listaCliente) {
				String codigoCliente = Util.completarCeros(10, beanCliente.getStrIdCliente());
				List<PedidoPendienteDevolucion> listaPedidoPendienteDevolucion = sCobranzas.obtenerListaPedidoPendienteDevolucion(codigoCliente);
				if (listaPedidoPendienteDevolucion.size() > 0) {
					lstPedidoPendienteDevolucion.addAll(listaPedidoPendienteDevolucion);
				}
			}
		}
		if (lstPedidoPendienteDevolucion.size() > 0) {
			SqlPedidoPendienteDevolucionImpl sqlPedidoPendienteDevolucionImpl = new SqlPedidoPendienteDevolucionImpl();
			sqlPedidoPendienteDevolucionImpl.eliminarListaPedidoPendienteDevolucion();
			sqlPedidoPendienteDevolucionImpl.insertarListaPedidoPendienteDevolucion(lstPedidoPendienteDevolucion);
		}
		return resultado;
	}

	public String[] sincronizaAnticipoCliente(AnticipoCliente anticipoCliente) throws Exception {
		String[] arregloRegistroAnticipo = new String[3];
		if (anticipoCliente != null) {
			sCobranzas = new SCobranzas();
			if(anticipoCliente.getIdFormaPago().equals("VZ") || anticipoCliente.getIdFormaPago().equals("VT")){
				anticipoCliente.setFechaCreacion(Util.convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(anticipoCliente.getFechaCreacion()));
			}
			arregloRegistroAnticipo = sCobranzas.registroAnticipo(anticipoCliente);
			if (arregloRegistroAnticipo != null) {
				if(arregloRegistroAnticipo[1].equals("S")){
					SqlAnticipoClienteImpl sqlAnticipoClienteImpl = new SqlAnticipoClienteImpl();
					anticipoCliente.setSincronizado("1");
					sqlAnticipoClienteImpl.actualizarAnticipoCliente(anticipoCliente);
					sqlAnticipoClienteImpl.eliminarAnticipoCliente(anticipoCliente);
				}
				
			}
			return arregloRegistroAnticipo;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public boolean sincronizaTablaPartidasIndividualesAbiertas() {
		resultado = true;
		sqlClienteImpl = new SqlClienteImpl();
		List<BeanCliente> listaCliente = sqlClienteImpl.listarClientes();
		Object[] listas;
		List<PartidaIndividualAbierta> lstPartidaIndividualAbierta = new ArrayList<PartidaIndividualAbierta>();
		List<DetallePagoPartidaIndividualAbierta> lstDetallePagoPartidaIndividualAbierta = new ArrayList<DetallePagoPartidaIndividualAbierta>();
		if (listaCliente.size() > 0) {
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Partidas Ind. Abiertas Vendedor ");
			long contador = 1;
			String codigoVendedor = Promesa.getInstance().datose.get(0).getStrCodigo();
			listas = sCobranzas.obtenerArregloDeListaPartidaIndividualAbiertaXVendedor(codigoVendedor, Util.convierteFechaAFormatoYYYYMMdd(new Date()), contador);
			List<PartidaIndividualAbierta> listaPartidaIndividualAbierta = (List<PartidaIndividualAbierta>) listas[0];
			lstPartidaIndividualAbierta.addAll(listaPartidaIndividualAbierta);
			List<DetallePagoPartidaIndividualAbierta> listaDetallePagoPartidaIndividualAbierta = (List<DetallePagoPartidaIndividualAbierta>) listas[1];
			lstDetallePagoPartidaIndividualAbierta.addAll(listaDetallePagoPartidaIndividualAbierta);
		}
		if (lstPartidaIndividualAbierta.size() > 0) {
			SqlPartidaIndividualAbiertaImpl sqlPartidaIndividualAbiertaImpl = new SqlPartidaIndividualAbiertaImpl();
			sqlPartidaIndividualAbiertaImpl.eliminarListaPartidaIndividualAbierta();
			sqlPartidaIndividualAbiertaImpl.insertarListaPartidaIndividualAbierta(lstPartidaIndividualAbierta);
		}
		if (lstDetallePagoPartidaIndividualAbierta.size() > 0) {
			SqlDetallePagoPartidaIndividualAbiertaImpl sqlDetallePagoPartidaIndividualAbiertaImpl = new SqlDetallePagoPartidaIndividualAbiertaImpl();
			sqlDetallePagoPartidaIndividualAbiertaImpl.eliminarListaDetallePagoPartidaIndividualAbierta();
			sqlDetallePagoPartidaIndividualAbiertaImpl.insertarListaDetallePagoPartidaIndividualAbierta(lstDetallePagoPartidaIndividualAbierta);
		}
		return resultado;
	}
	
	public boolean sincronizarCobranzaEliminado(){
		List<RegistroPagoCliente> cobranzas = SqlRegistroPagoClienteImpl.getCobranzaElimindosNoSincronizados();
		if(cobranzas != null && !cobranzas.isEmpty()) {
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: COBRANZAS OFFLINE ELIMINADOS");
			sCobranzas.registrarCobranzasEliminadas(cobranzas);
			for (RegistroPagoCliente rpc : cobranzas) {
				SqlRegistroPagoClienteImpl.actualizarCabeceraSincronizado(rpc);
			}
			return true;
		}
		return false;
	}
	
	public boolean eliminarCobranzaSinc(){
		List<RegistroPagoCliente> cobranzas = SqlRegistroPagoClienteImpl.getCobranzaElimindosSincronizados();
		if(cobranzas != null && !cobranzas.isEmpty()) {
			for (RegistroPagoCliente rpc : cobranzas) {
				try {
					SqlRegistroPagoClienteImpl.eliminarRegistroPagoCliente(rpc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean sincronizarAnticipoEliminado(){
		SqlAnticipoClienteImpl sqlAnticipo = new SqlAnticipoClienteImpl();
		List<AnticipoCliente> anticipos = sqlAnticipo.getAnticiposElimindosNoSincronizados();
		if(anticipos != null && !anticipos.isEmpty()) {
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: ANTICIPOS OFFLINE ELIMINADOS");
			sCobranzas.registrarAnticiposEliminados(anticipos);
			for (AnticipoCliente ant : anticipos) {
				try {
					ant.setSincronizado("1");
					sqlAnticipo.actualizarAnticipoCliente(ant);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean eliminarAnticipoEliminadoSinc(){
		SqlAnticipoClienteImpl sqlAnticipo = new SqlAnticipoClienteImpl();
		List<AnticipoCliente> anticipos = sqlAnticipo.getAnticiposElimindosSincronizados();
		for (AnticipoCliente ant : anticipos) {
			sqlAnticipo.eliminarAnticipoCliente(ant);
		}
		return true;
	}
	
	public boolean sincronizarPresupuesto() {
		try {
			resultado = true;
			@SuppressWarnings("static-access")
			String codigoVendedor = Promesa.getInstance().datose.get(0).getStrCodigo();
			sCobranzas = new SCobranzas();
			Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Presupuesto");
	        List<Presupuesto> presupuestos = sCobranzas.getPresupuestoByVendedor(codigoVendedor);
	        SqlCliente sqlCliente = new SqlClienteImpl();
	        sqlCliente.insertPresupuesto(presupuestos);
			return true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return false;
		}
	}
	
	
}