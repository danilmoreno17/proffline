package com.promesa.sincronizacion.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.factory.Factory;
//import com.promesa.administracion.sql.impl.SqlHistorialSincronizacionImpl;
//import com.promesa.cobranzas.sql.impl.SqlBancoClienteImpl;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.bean.BeanMarcaIndicador;
import com.promesa.pedidos.bean.BeanPedidoDetalle;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.pedidos.bean.BeanPedidosParaSap;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.bean.MarcaVendedor;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.SqlBloqueoEntrega;
import com.promesa.pedidos.sql.SqlCondicionExpedicion;
import com.promesa.pedidos.sql.SqlCondicionPago;
import com.promesa.pedidos.sql.SqlJerarquia;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.pedidos.sql.SqlPedido;
import com.promesa.pedidos.sql.SqlSede;
import com.promesa.pedidos.sql.SqlSincronizacion;
//import com.promesa.pedidos.sql.SqlSupervisor;
import com.promesa.pedidos.sql.SqlTipologia;
import com.promesa.pedidos.sql.impl.SqlAgendaImpl;
import com.promesa.pedidos.sql.impl.SqlBloqueoEntregaImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionExpedicionImpl;
import com.promesa.pedidos.sql.impl.SqlCondicionPagoImpl;
import com.promesa.pedidos.sql.impl.SqlJerarquiaImpl;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.pedidos.sql.impl.SqlPedidoImpl;
import com.promesa.pedidos.sql.impl.SqlSedeImpl;
import com.promesa.pedidos.sql.impl.SqlSincronizacionImpl;
//import com.promesa.pedidos.sql.impl.SqlSupervisorImpl;
import com.promesa.pedidos.sql.impl.SqlTipologiaImpl;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.Cmd;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class SincronizacionPedidos {

	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaCondicionExpedicion() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlCondicionExpedicion sqlCondicionExpedicion = new SqlCondicionExpedicionImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("10");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaCondicionExpedicion();
		bs.setStrInfSinc(Constante.TABLA_CONDICION_EXPEDICION);
		bs.setStrCantReg(sqlCondicionExpedicion.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaAgenda(BeanBuscarPedido param) throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlAgenda sqlAgenda = new SqlAgendaImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("11");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaAgenda(param);
		bs.setStrInfSinc(Constante.TABLA_AGENDA);
		bs.setStrCantReg(sqlAgenda.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaJerarquia() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlJerarquia sqlJerarquia = new SqlJerarquiaImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("12");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaJerarquia();
		bs.setStrInfSinc(Constante.TABLA_JERARQUIA);
		bs.setStrCantReg(sqlJerarquia.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaMaterial() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("13");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaMaterial();
		bs.setStrInfSinc(Constante.TABLA_MATERIAL);
		bs.setStrCantReg(sqlMaterial.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaMaterialStock() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("24");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaMaterialStock();
		bs.setStrInfSinc(Constante.TABLA_MATERIAL_STOCK);
		bs.setStrCantReg(sqlMaterial.getCountRowStock() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public static String sincronizaMaterialConsultaDinamica() {
		try {
			Cmd objC = new Cmd();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(new Date());
			long milis1 = cal1.getTimeInMillis();

			SqlSincronizacion objSI = new SqlSincronizacionImpl();
			BeanSincronizar bs = new BeanSincronizar();
			bs.setStrIdeSinc("23");
			String fechaInicio = objC.fechaHora();
			objSI.sincronizaMaterialConsultaDinamica();
			bs.setStrInfSinc(Constante.TABLA_MATERIAL_CONSULTA_DINAMICA);
			String fechaFin = objC.fechaHora();
			bs.setStrFecHor(fechaFin);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(new Date());
			long milis2 = cal2.getTimeInMillis();
			long dif = milis2 - milis1;
			bs.setStrTie((dif / 1000) + "");
			objSI.actualizarSincronizar(bs);
		} catch (Exception e) {
			e.printStackTrace();
			return "E";
		}
		return "S";
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaCombos() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("20");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaCombos();
		bs.setStrInfSinc(Constante.TABLA_COMBOS);
		bs.setStrCantReg(sqlMaterial.getCountRowCombos() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaCondicionesComerciales() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("21");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaCondicionesComerciales();
		bs.setStrInfSinc(Constante.TABLA_CONDICIONES_COMERCIALES);
		bs.setStrCantReg((sqlMaterial.getCountRowCondicion1() + sqlMaterial.getCountRowCondicion2()) + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaClaseMaterial() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("22");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaClaseMaterial();
		bs.setStrInfSinc(Constante.TABLA_CLASE_MATERIAL);
		bs.setStrCantReg(sqlMaterial.getCountRowClaseMaterial() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaTipologia() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlTipologia sqlTipologia = new SqlTipologiaImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("14");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaTipologia();
		bs.setStrInfSinc(Constante.TABLA_TIPOLOGIA);
		bs.setStrCantReg(sqlTipologia.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaBloqueoEntrega() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlBloqueoEntrega sqlBloqueoEntrega = new SqlBloqueoEntregaImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("15");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaBloqueoEntrega();
		bs.setStrInfSinc(Constante.TABLA_BLOQUEO_ENTREGA);
		bs.setStrCantReg(sqlBloqueoEntrega.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaCondicionPago() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlCondicionPago sqlCondicionPago = new SqlCondicionPagoImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("16");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaCondicionPago();
		bs.setStrInfSinc(Constante.TABLA_CONDICION_PAGO);
		bs.setStrCantReg(sqlCondicionPago.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaClientes(String idVendedor) throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlCliente sqlCliente = new SqlClienteImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("17");
		String fechaInicio = objC.fechaHora();
		objSI.sincronizaCliente(idVendedor);
		bs.setStrInfSinc(Constante.TABLA_CLIENTE);
		bs.setStrCantReg(sqlCliente.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaPedidos(String idPedidoHeader) {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("18");
		String fechaInicio = objC.fechaHora();
		BeanPedidosParaSap bps = new BeanPedidosParaSap();
		HashMap<String, BeanPedidoHeader> mapPedidoHeader = null;
		HashMap<String, HashMap<String, String>> mapPedidoPartners = null;
		HashMap<String, List<BeanPedidoDetalle>> mapPedidoDetalle = null;
		SqlPedido sqlp = new SqlPedidoImpl();
		bps = sqlp.listarPedidosParaSaps(idPedidoHeader);
		mapPedidoHeader = bps.getMapPedidoHeader();
		mapPedidoPartners = bps.getMapPedidoPartners();
		mapPedidoDetalle = bps.getMapPedidoDetalle();
		try {
			String msg = "";
			for (BeanPedidoHeader bph : mapPedidoHeader.values()) {
				List<BeanPedidoDetalle> materialesBodega = new ArrayList<BeanPedidoDetalle>();
				List<BeanPedidoDetalle> materialesNormal = new ArrayList<BeanPedidoDetalle>();
				List<BeanPedidoDetalle> materialesPesado = new ArrayList<BeanPedidoDetalle>();
				List<BeanPedidoDetalle> materialesRojos = new ArrayList<BeanPedidoDetalle>();

				List<BeanPedidoDetalle> materiales = mapPedidoDetalle.get(bph.getIdBD());
				SqlMaterialImpl sqlTipoMaterial = new SqlMaterialImpl();
				SqlClienteImpl sql = new SqlClienteImpl();
				for (BeanPedidoDetalle beanPedidoDetalle : materiales) {
					String codigoMaterial;
					try {
						codigoMaterial = "" + Long.parseLong(beanPedidoDetalle.getMaterial());
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						codigoMaterial = "0";
					}
					String tipoMaterial = sqlTipoMaterial.getTipoMaterial(codigoMaterial);
					String codigoCliente = mapPedidoHeader.get(bph.getIdBD()).getStrCodCliente();
					String check = sql.obtenerMarcaBloqueoAlmacen(codigoCliente);
					int tipo = beanPedidoDetalle.getTipo();

					if (tipo == 0) {
						if (tipoMaterial.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
							materialesBodega.add(beanPedidoDetalle);
						} else if (tipoMaterial.compareTo("N") == 0 || (tipoMaterial.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
							materialesNormal.add(beanPedidoDetalle);
						} else if (tipoMaterial.compareTo("P") == 0) { // PESADO
							materialesPesado.add(beanPedidoDetalle);
						} else if (tipoMaterial.compareTo("R") == 0) { // ROJO
							materialesRojos.add(beanPedidoDetalle);
						}
					} else if (tipo == 1) {
						SqlMaterialImpl sqlMat = new SqlMaterialImpl();
						String tipoCombo = sqlMat.obtenerTipoMaterialCombo(codigoMaterial);
						if (tipoCombo.compareTo("B") == 0 && check.compareTo("1") == 0) { // BODEGA
							materialesBodega.add(beanPedidoDetalle);
						} else if (tipoCombo.compareTo("N") == 0 || (tipoCombo.compareTo("B") == 0 && check.compareTo("0") == 0)) { // NORMAL
							materialesNormal.add(beanPedidoDetalle);
						} else if (tipoCombo.compareTo("P") == 0) { // PESADO
							materialesPesado.add(beanPedidoDetalle);
						} else if (tipoCombo.compareTo("R") == 0) { // ROJO
							materialesRojos.add(beanPedidoDetalle);
						}
					}
				}
				if (!materialesBodega.isEmpty()) {
					guardarOrden(bph, materialesBodega, mapPedidoPartners);
				}
				if (!materialesNormal.isEmpty()) {
					guardarOrden(bph, materialesNormal, mapPedidoPartners);
				}
				if (!materialesRojos.isEmpty()) {
					guardarOrden(bph, materialesRojos, mapPedidoPartners);
				}
				if (!materialesPesado.isEmpty()) {
					guardarOrden(bph, materialesPesado, mapPedidoPartners);
				}
			}
		} catch (Exception exec) {
			Mensaje.mostrarError(exec.getMessage());
		}
		objSI.setEliminarSincronizacion(bs);
		bs.setStrInfSinc(Constante.TABLAS_PEDIDOS);
		bs.setStrCantReg(mapPedidoHeader.size() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.setInsertarSincronizar(bs);
	}

	private void guardarOrden(BeanPedidoHeader bph, List<BeanPedidoDetalle> detalles, HashMap<String, HashMap<String, String>> mapPedidoPartners) throws Exception {
		SPedidos objSAP = new SPedidos();
		
		String secuencial = Util.eliminarCerosInicios(bph.getStrCodVendedor()) + Util.completarCeros(7, ""+ bph.getSecuencialPedido());
		
		String mensaje[] = objSAP.crearOrden(bph.getDOC_TYPE(),
				bph.getSALES_ORG(), bph.getDISTR_CHAN(), bph.getDIVISION(),
				bph.getSALES_GRP(), bph.getSALES_OFF(), bph.getREQ_DATE_H(),
				bph.getPURCH_DATE(), bph.getPMNTTRMS(), bph.getDLV_BLOCK(),
				bph.getPRICE_DATE(), bph.getPURCH_NO_C(), bph.getSD_DOC_CAT(),
				bph.getDOC_DATE(), bph.getBILL_DATE(), bph.getSERV_DATE(),
				bph.getCURRENCY(), bph.getCREATED_BY(), bph.getSHIP_TYPE(),
				bph.getSHIP_COND(), mapPedidoPartners.get(bph.getIdBD()), detalles, secuencial);
		actualizarMarcasEstategicas(bph.getStrCodCliente(), detalles);
		if (mensaje != null) {
			boolean huboError = false;
			Long codigo = 0l;
			try {
				codigo = Long.parseLong(mensaje[2]);
			} catch (NumberFormatException e) {
				codigo = -1l;
				huboError = true;
			}
			if (!huboError) {
				SqlPedido sqlp = new SqlPedidoImpl();
				sqlp.setActualizaPedidoHeaderPartnersDetalle(bph.getIdBD());
			}
			if (codigo > 0) {
				Mensaje.mostrarAviso(mensaje[1]);
			} else {
				Mensaje.mostrarError(mensaje[1]);
			}
		}
	}
	@SuppressWarnings({ "static-access", "unused" })
	public void sincronizaSede() throws Exception {
		Cmd objC = new Cmd();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		long milis1 = cal1.getTimeInMillis();

		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		SqlSede sqlSede = new SqlSedeImpl();
		BeanSincronizar bs = new BeanSincronizar();
		bs.setStrIdeSinc("19");
		String fechaInicio = objC.fechaHora();

		objSI.sincronizaSede();
		
		bs.setStrInfSinc(Constante.TABLA_SEDE);
		bs.setStrCantReg(sqlSede.getCountRow() + "");
		String fechaFin = objC.fechaHora();
		bs.setStrFecHor(fechaFin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		long milis2 = cal2.getTimeInMillis();
		long dif = milis2 - milis1;
		bs.setStrTie((dif / 1000) + "");
		objSI.actualizarSincronizar(bs);
	}

	public static String sincronizarTablaRol() {
		com.promesa.administracion.sql.impl.SqlSincronizacionImpl sqlAdmin = new com.promesa.administracion.sql.impl.SqlSincronizacionImpl();
		boolean s1 = sqlAdmin.sincronizaRol();
		boolean s2 = sqlAdmin.sincronizaUsuarioRol();
		boolean s3 = sqlAdmin.sincronizaRolVista();
		boolean s4 = sqlAdmin.sincronizaVista();
		if (s1 && s2 && s3 && s4) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaUsuario() {
		com.promesa.administracion.sql.impl.SqlSincronizacionImpl sqlAdmin = new com.promesa.administracion.sql.impl.SqlSincronizacionImpl();
		if (sqlAdmin.sincronizaUsuario()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaAgenda(BeanBuscarPedido param) {
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		try {
			if (objSI.sincronizaAgenda(param)) {
				return "S";// TEST
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "E";
		}
		return "E";
	}

	public static String sincronizaTablaCombo() {
		SqlSincronizacion objSI = new SqlSincronizacionImpl();
		try {
			if (objSI.sincronizaCombos()) {
				return "S";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "E";
		}
		return "E";
	}

	public static String sincronizarTablaEmpleado(String idSupervisor) {
		com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlPlan = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
		if (sqlPlan.sincronizaEmpleadoCliente(idSupervisor)) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaFeriado() {
		com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlPlan = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
		if (sqlPlan.sincronizaFeriado()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaJerarquia() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaJerarquia()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaMaterial() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaMaterial()) {
			return "S";
		}
		return "E";
	}
	public static String sincronizarTablaMaterialNuevo() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaMaterialNuevo()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarVentaCruzada(String strCodVendedor) {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaMaterialVentaCruzada() && sqlPedido.sincronizaVentaCruzada()) {
			return "S";
		}
		return "E";
	}
	
	public static String sincronizarMercadeo(String strCodVendedor) {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizarMercadeo()) {
			return "S";
		}
		return "E";
	}
	
	public static String sincronizarPromocion(String strCodVendedor) {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizarPromocion()) {
			return "S";
		}
		return "E";
	}
	
	public static String sincronizarTablaMaterialStock() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaMaterialStock()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaTipologia() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaTipologia()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaDispositivo() {
		com.promesa.administracion.sql.impl.SqlSincronizacionImpl sqlAdmin = new com.promesa.administracion.sql.impl.SqlSincronizacionImpl();
		if (sqlAdmin.sincronizaDispositivo()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaDestinatario() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaSede()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarCondicionesComerciales() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaCondicionesComerciales()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarCondicionesPago() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaCondicionPago()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaBloqueoEntrega() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaBloqueoEntrega()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaClaseMaterial() {
		com.promesa.pedidos.sql.impl.SqlSincronizacionImpl sqlPedido = new com.promesa.pedidos.sql.impl.SqlSincronizacionImpl();
		if (sqlPedido.sincronizaClaseMaterial()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaBancoCliente() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaBancoCliente()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaHojaMaestraCredito() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaHojaMaestraCredito()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaPartidaIndividual() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaPartidaIndividual()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarTablaPedidoPendienteDevolucion() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaPedidoPendienteDevolucion()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarFormaPagoCobranza() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaFormaPagoCobranza()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarFormaPagoAnticipo() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaFormaPagoAnticipo()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizarBancoPromesa() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaBancoPromesa()) {
			return "S";
		}
		return "E";
	}

	public static String sincronizaPartidasIndividualesAbiertas() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizaTablaPartidasIndividualesAbiertas()) {
			return "S";
		}
		return "E";
	}
	
	/*
	 * @author	MARCELO MOYANO
	 * 		
	 * 			METODO QUE SINCRONIZA LA
	 * 			SECUENCIA POR VENDEDOR
	 * 
	 * @return	RETORNA UN STRING DE CONFIRMACION
	 * 			DE QUE LA SECUENCIA SE HA SINCRONIZADO
	 * 			CORRECTAMENTE
	 * 			03/04/2013 - 17:20
	 */
	public static String sincronizaSecuenciaPorVendedor() {
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if (sqlCobranza.sincronizacionSecuenciaPorVendedor()) {
			return "S";
		}
		return "E";
	}
	/*
	 * @author	MARCELO MOYANO
	 * 	
	 * 			METODO QUE SINCRONIZA UN
	 * 			PARAMETRO CONSTANTE
	 * 
	 * @return	RETORNA UN STRING DE CONFIRMACION
	 * 			DE QUE EL PARAMETRO CONSTANTE
	 * 			SE HA SINCRONIZADO CORRECTAMENTE
	 */
	public static String sincronizaParametrosConstante() {
		SqlSincronizacionImpl sqlPedidos = new SqlSincronizacionImpl();
		if (sqlPedidos.sincronizaZtConstante()) {
			return "S";
		}
		return "E";
	}
	
	public static String sincronizacionCobranzasOffLineEliminado(){
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if(sqlCobranza.sincronizarCobranzaEliminado()) {
			sqlCobranza.eliminarCobranzaSinc();
			return "S";
		} 
		return "S";
	}
	
	public static String sincronizacionAnticiposOffLineEliminado(){
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if(sqlCobranza.sincronizarAnticipoEliminado()) {
			sqlCobranza.eliminarAnticipoEliminadoSinc();
			return "S";
		}
		return "S";
	}
	
	public static String sincronizacionPresupuesto(){
		SincronizacionCobranzas sqlCobranza = new SincronizacionCobranzas();
		if(sqlCobranza.sincronizarPresupuesto()) {
			return "S";
		}
		return "E";
	}
	
	public static String sincronizacionMarcaEstrategica(){
		SPedidos sp = new SPedidos();
		BeanMarcaIndicador bean =  sp.getMarcaEstrategica(Util.getCodigoVendedor());
		if(bean != null){
			Factory.createSqlMarcaEstrategica().insertMarcaEstrategica(bean.getMarcas());
			Factory.createSqlMarcaEstrategica().inserNombreMarcaEstrategica(bean.getNombres());
			Factory.createSqlIndicador().insertIndicador(bean.getIndicadores());
			Factory.createSqlMarcaVendedor().insertMarcaVendedor(bean.getMarcavendedor());
			return "S";
		}
		return "E";
	}
	
	private void actualizarMarcasEstategicas(String cliente, List<BeanPedidoDetalle> detalles){
		List<MarcaEstrategica> marcas = Factory.createSqlMarcaEstrategica().getMarcaEstrategicaByCliente(Util.completarCeros(10, cliente));
		List<MarcaVendedor> marcavend = Factory.createSqlMarcaVendedor().getMarcaVendedorByCliente(Util.completarCeros(10, cliente));
		for (BeanPedidoDetalle d : detalles) {
			String prdha = Factory.createSqlMaterial().getMaterialMarcaEstrategica(Util.eliminarCerosInicios(d.getMaterial()));
			if (Factory.createSqlMarcaEstrategica().perteneceAMarcaEstrategica(Util.completarCeros(10, cliente),prdha)) {
				actualizarValores(marcas, prdha, Double.parseDouble(d.getValorNeto()));
			}
			if (Factory.createSqlMarcaVendedor().perteneceAMarcaVendedor(Util.completarCeros(10, cliente),prdha)) {
				actualizarValoresMV(marcavend, prdha, Double.parseDouble(d.getValorNeto()));
			}
		}
		Factory.createSqlMarcaEstrategica().actualizarMarcaEstrategica(marcas);
		Factory.createSqlMarcaVendedor().actualizarMarcaVendedor(marcavend);
	}
	
	private void actualizarValores(List<MarcaEstrategica> marcas,String marca, double valor){
		double totalAcum = 0.0;
		for (MarcaEstrategica item : marcas) {
			if(item.getMarca().equalsIgnoreCase(marca)){
				totalAcum = Double.parseDouble(item.getAcumulado()) + valor;
				item.setAcumulado(totalAcum+"");
			}
		}
	}
	private void actualizarValoresMV(List<MarcaVendedor> marcas,String marca, double valor){
		double totalAcum = 0.0;
		double totalMes = 0.0;
		for (MarcaVendedor item : marcas) {
			if(item.getMarca().equalsIgnoreCase(marca)){
				totalMes = Double.parseDouble(item.getVentaMes()) + valor;
				totalAcum = Double.parseDouble(item.getVentaAcumulado()) + valor;
				item.setVentaMes(totalMes+"");
				item.setVentaAcumulado(totalAcum+"");
			}
		}
	}
	
}