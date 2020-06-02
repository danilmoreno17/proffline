package com.promesa.pedidos.sql.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import util.ConexionSAP;

import com.promesa.administracion.bean.BeanParametro;
import com.promesa.administracion.bean.BeanSincronizar;
import com.promesa.administracion.bean.DetalleSincronizacion;
import com.promesa.administracion.sql.impl.SqlHistorialSincronizacionImpl;
import com.promesa.bean.BeanDato;
import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecute;
import com.promesa.dao.ResultExecuteList;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanAgenda;
import com.promesa.pedidos.bean.BeanBloqueoEntrega;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanClienteMaterial;
import com.promesa.pedidos.bean.BeanCondicionPago;
import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.bean.BeanSede;
import com.promesa.pedidos.bean.BeanTipologia;
import com.promesa.pedidos.sql.SqlAgenda;
import com.promesa.pedidos.sql.SqlBloqueoEntrega;
import com.promesa.pedidos.sql.SqlCondicionPago;
import com.promesa.pedidos.sql.SqlJerarquia;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.pedidos.sql.SqlSede;
import com.promesa.pedidos.sql.SqlSincronizacion;
import com.promesa.pedidos.sql.SqlTipologia;
import com.promesa.planificacion.bean.BeanCliente;
import com.promesa.planificacion.sql.SqlCliente;
import com.promesa.planificacion.sql.impl.SqlClienteImpl;
import com.promesa.sap.SPedidos;
import com.promesa.sap.SPlanificacion;
import com.promesa.sincronizacion.bean.BeanSincronizacion;
import com.promesa.util.Conexiones;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class SqlSincronizacionImpl implements SqlSincronizacion {

	private String sqlSinc = null;
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
	SPedidos objSAP;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanSincronizacion> listaSincronizacion() {
		column = new HashMap();
		since = new ArrayList<BeanSincronizacion>();
		column.put("String:0", NUM_ID_SINC);
		column.put("String:1", INFO_SINC);
		column.put("String:2", CANT_REG);
		column.put("String:3", FECH_HOR);
		column.put("String:4", TIEMPO);
		sqlSinc = "SELECT * FROM PROFFLINE_TB_SINCRONIZACION";
		resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column,Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			objS = new BeanSincronizacion();
			res = (HashMap) mapResultado.get(i);
			objS.setStrIdeSinc(res.get(NUM_ID_SINC).toString());
			objS.setStrInfSinc(res.get(INFO_SINC).toString());
			objS.setStrNumCantReg((res.get(CANT_REG)== null ? "0" : res.get(CANT_REG)).toString());
			objS.setStrFecHor(res.get(FECH_HOR).toString());
			objS.setStrNumTie(res.get(TIEMPO).toString());
			since.add(objS);
		}
		return since;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanSincronizacion> listaSincronizacionPendientes() {
		//TODO
		column = new HashMap();
		since = new ArrayList<BeanSincronizacion>();
		column.put("String:0", NUM_ID_SINC);
		column.put("String:1", INFO_SINC);
		column.put("String:2", CANT_REG);
		column.put("String:3", FECH_HOR);
		column.put("String:4", TIEMPO);
//		sqlSinc = "SELECT * FROM PROFFLINE_TB_SINCRONIZACION WHERE numCantReg=0";
		sqlSinc = "SELECT * FROM PROFFLINE_TB_SINCRONIZACION WHERE numCantReg=0 or (numCantReg<>0  and ( txtInfSinc = 'Cobranzas Off Line' or  txtInfSinc = 'Anticipos Off Line')) or txtInfSinc = 'Secuencial Vendedor'";
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
			since.add(objS);
		}
		return since;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BeanSincronizacion> listaHistorialSincronizacion(BeanSincronizacion b) {
		column = new HashMap();
		since = new ArrayList<BeanSincronizacion>();
		column.put("String:0", NUM_ID_SINC);
		column.put(""
				+ "String:1", FECH_HOR);
		column.put("String:2", INFO_SINC);
		sqlSinc = " select hs.numIdeSinc as NUMIDESINC ,s.txtInfSinc as TXTINFSINC,substr(hs.txtFecHor,1,10) as TXTFECHOR "
				+ " from proffline_tb_historial_sincronizacion hs "
				+ " inner join proffline_tb_sincronizacion s on (hs.numIdeSinc=s.numIdeSinc) "
				+ " where s.txtInfSinc='" + b.getStrInfSinc() + "' ";
		resultExecuteQuery = new ResultExecuteQuery(sqlSinc, column, Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		HashMap res = null;
		for (int i = 0; i < mapResultado.size(); i++) {
			objS = new BeanSincronizacion();
			res = (HashMap) mapResultado.get(i);
			objS.setStrIdeSinc(res.get(NUM_ID_SINC).toString());
			objS.setStrFecHor(res.get(FECH_HOR).toString());
			objS.setStrInfSinc(res.get(INFO_SINC).toString());
			since.add(objS);
		}
		return since;
	}

	// SYNCHRONIZED CONDICION_EXPEDICION
	@SuppressWarnings({})
	public void sincronizaCondicionExpedicion() {
	}

	/* MÉTODO QUE CONVIERTE FECHA */
	public String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	/* MÉTODO QUE COMPLETA CEROS */
	public String completaCeros(String codigo) {
		int i = codigo.length();
		while (i < 10) {
			codigo = "0" + codigo;
			i++;
		}
		return codigo;
	}

	/* MÉTODO QUE SINCRONIZA AGENDA */
	public boolean sincronizaAgenda(BeanBuscarPedido param) {
		resultado = false;
		List<BeanAgenda> listaAgenda = null;
		SqlAgenda getAgenda = new SqlAgendaImpl();
		try {
			BeanAgenda ag = new BeanAgenda();
			ag.setVENDOR_ID(param.getStrVendorId());
			getAgenda.listaAgendaSAP(ag);
			List<BeanAgenda> temporal = new ArrayList<BeanAgenda>();
			temporal = getAgenda.getListaAgenda();
			if (temporal.size() > 0) {
				List<String> lstFechas;
				SPlanificacion visita = new SPlanificacion();
				for (int i = 0; i < temporal.size(); i++) {
					lstFechas = new ArrayList<String>();
					lstFechas.add(convierteFecha(((BeanAgenda) temporal.get(i)).getBEGDA()));
					visita.ingresaVisitaFueraDeRuta(((BeanAgenda) temporal.get(i)).getVENDOR_ID(), completaCeros(((BeanAgenda) temporal.get(i)).getCUST_ID()), lstFechas, ((BeanAgenda) temporal.get(i)).getTIME(), temporal.get(i).getTY(), Constante.VACIO);
				}
			}
			objSAP = new SPedidos();
			listaAgenda = objSAP.obtenerAgenda(param);
			if (listaAgenda != null) {
				getAgenda.setEliminarAgenda(ag);
				getAgenda.setInsertaAgenda(listaAgenda);
				resultado = true;
			}
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED JERARQUIA
	@SuppressWarnings({})
	public boolean sincronizaJerarquia() {
		resultado = false;
		List<BeanJerarquia> listaJerarquia;
		SqlJerarquia sqlJerarquia = new SqlJerarquiaImpl();
		try {
			objSAP = new SPedidos();
			listaJerarquia = objSAP.listaJerarquia();
			if (listaJerarquia != null) {
				sqlJerarquia.setEliminarJerarquia();
				sqlJerarquia.setInsertarJerarquia(listaJerarquia);
			}
			resultado = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED MATERIAL - VER SOBRE LA TABLA PROFFLINE_TB_MATERIAL_STOCK
	@SuppressWarnings({})
	public boolean sincronizaMaterial() {
		resultado = false;
		List<BeanMaterial> listaMaterial;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			objSAP = new SPedidos();
			listaMaterial = objSAP.listaMateriales("");
			if (listaMaterial != null) {
				sqlMaterial.setInsertarActualizarMaterial(listaMaterial);
			}
			resultado = true;
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED MATERIAL NUEVO - VER SOBRE LA TABLA PROFFLINE_TB_MATERIAL_STOCK
	@SuppressWarnings({})
	public boolean sincronizaMaterialNuevo() {
		resultado = false;
		List<BeanMaterial> listaMaterial;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			objSAP = new SPedidos();
			listaMaterial = objSAP.obtenerListaMaterialNuevo();
			if (listaMaterial != null) {
				sqlMaterial.setInsertarActualizarMaterialNuevo(listaMaterial);
			}
			resultado = true;
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
			resultado = false;
		}
		return resultado;
	}
	
	// SYNCHRONIZED MATERIAL - VER SOBRE LA TABLA PROFFLINE_TB_MATERIAL_STOCK
	@SuppressWarnings({})
	public boolean sincronizaMaterialStock() {
		resultado = false;
		List<BeanMaterial> listaMaterial;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			objSAP = new SPedidos();
			listaMaterial = objSAP.listaMaterialesStock("");
			if (listaMaterial != null) {
				sqlMaterial.setEliminarMaterialStock();//	Marcelo Moyano	06/03/2013 - 09:44
				sqlMaterial.insertarMaterialesStock(listaMaterial);
			}
			resultado = true;
		} catch (Exception e) {
			resultado = false;
			Mensaje.mostrarError(e.getMessage());
		}
		return resultado;
	}

	@SuppressWarnings("static-access")
	public void sincronizaMaterialConsultaDinamicaTopXClienteYTipologia() {
		resultado = false;
		List<List<BeanMaterial>> listaTopCli = null;
		List<List<BeanMaterial>> listaTopTipo = null;
		List<BeanMaterial> listaMaterial;
		List<BeanMaterial> listaMaterialtipologia;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			System.out.println("sincronizaMaterialConsultaDinamicaTopXClienteYTipologia");
			objSAP = new SPedidos();
			listaTopCli = objSAP.listaMaterialesTop(Promesa.getInstance().datose.get(0).getStrCodigo());
			if (listaTopCli != null && listaTopCli.size() > 0 && listaTopCli.get(0) != null) {
				listaMaterial = sqlMaterial.obtenerTodosMateriales(listaTopCli.get(0), "Consultando Materiales por cliente.");
				sqlMaterial.migrarMaterialesTopCliente2(listaTopCli, listaMaterial);
			}
			listaTopTipo = objSAP.listaMaterialesTopTipol(Promesa.getInstance().datose.get(0).getStrCodigo());
			if (listaTopTipo != null && listaTopTipo.size() > 0 && listaTopTipo.get(0) != null) {
				listaMaterialtipologia = sqlMaterial.obtenerTodosMateriales(listaTopTipo.get(0), "Consultando Materiales por tipologia.");// TOP TIPOLOGIA
				sqlMaterial.migrarMaterialesTopTipologia2(listaTopTipo, listaMaterialtipologia);
			}
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
		}
	}
	
	// SYNCHRONIZED MATERIAL - VER SOBRE LA TABLA PROFFLINE_TB_MATERIAL_STOCK
	@SuppressWarnings({ "unused", "static-access" })
	public void sincronizaMaterialConsultaDinamica() {
		resultado = false;
		List<List<BeanMaterial>> lista = null;
		List<List<BeanMaterial>> listaTopCli = null;
		List<List<BeanMaterial>> listaTopTipo = null;
		List<BeanMaterial> listaMaterial;
		List<BeanMaterial> listaTodosMaterial;
		List<BeanMaterial> listaMaterialtipologia;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			objSAP = new SPedidos();
			lista = objSAP.listaMaterialesConsultaDinamica();
			listaTodosMaterial = sqlMaterial.obtenerTodosMateriales2();
			if (lista != null && lista.get(0) != null) {
				sqlMaterial.eliminarMaterialConsultaDinamica1();
				listaMaterial = buscarJerarquiaMateriales(listaTodosMaterial, lista.get(0), "Consultando promooferte.");
				sqlMaterial.migrarMaterialConsultaDinamica1(listaMaterial);
				listaMaterial.clear();
			}
			if (lista != null && lista.get(1) != null) {
				sqlMaterial.eliminarMaterialConsultaDinamica2();
				listaMaterial = buscarJerarquiaMateriales(listaTodosMaterial, lista.get(1), "Consultando dscto por política.");
				sqlMaterial.migrarMaterialConsultaDinamica2(listaMaterial);
				listaMaterial.clear();
			}
			if (lista != null && lista.get(2) != null) {
				sqlMaterial.eliminarMaterialConsultaDinamica3();
				listaMaterial = buscarJerarquiaMateriales(listaTodosMaterial, lista.get(2), "Consultando dscto manuales.");
				sqlMaterial.migrarMaterialConsultaDinamica3(listaMaterial);
				listaMaterial.clear();
			}
			// ///Rangos de Consulta Dinàmica

			// ///Rangos de Consulta Dinàmica
			// SINCRONIZA TABLAS TOP_CLIENTE / TOP TIPOLOGIA
			BeanDato b = Promesa.datose.get(0);
			SqlClienteImpl sqlClientes = new SqlClienteImpl();
			listaTopCli = objSAP.listaMaterialesTop(Promesa.getInstance().datose.get(0).getStrCodigo());
			
			if (listaTopCli != null && listaTopCli.size() > 0 && listaTopCli.get(0) != null) {
				listaMaterial = buscarJerarquiaMateriales(listaTodosMaterial, listaTopCli.get(0), "Consultando por cliente.");
				sqlMaterial.migrarMaterialesTopCliente2(listaTopCli, listaMaterial);
				listaMaterial.clear();
			}
			listaTopTipo = objSAP.listaMaterialesTopTipol(Promesa.getInstance().datose.get(0).getStrCodigo());
			if (listaTopTipo != null && listaTopTipo.size() > 0&& listaTopTipo.get(0) != null) {
				listaMaterialtipologia = buscarJerarquiaMateriales(listaTodosMaterial, listaTopTipo.get(0), "Consultando por tipologia.");
				sqlMaterial.migrarMaterialesTopTipologia2(listaTopTipo, listaMaterialtipologia);
				listaMaterialtipologia.clear();
			}
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
		}
	}
	
	public List<BeanMaterial> buscarJerarquiaMateriales(List<BeanMaterial> listaTodosMateriales, List<BeanMaterial> listaMateriales, String mensaje){
		List<BeanMaterial> listaMaterial = new ArrayList<BeanMaterial>();
		double total = listaMateriales.size();
		double i = 1;
		double avance = 0;
		for(BeanMaterial material : listaMateriales){
			avance = Math.round(i * 100 / total * 100) / 100.0d;
			Promesa.getInstance().mostrarAvisoSincronizacion(mensaje +" "+ avance + " %");
			i++;
			for(BeanMaterial m : listaTodosMateriales){
				if(material.getIdMaterial().equals(m.getIdMaterial())){
					material.setIdMaterial(m.getIdMaterial());
					material.setStock(m.getStock());
					material.setUn(m.getUn());
					material.setDescripcion(m.getDescripcion().replaceAll("'", ""));
					material.setText_line(m.getText_line().replaceAll("'", ""));
					material.setTarget_qty(m.getTarget_qty());
					material.setPrice_1(m.getPrice_1());
					material.setPrice_2(m.getPrice_2());
					material.setPrice_3(m.getPrice_3());
					material.setPrice_4(m.getPrice_4());
					material.setPrdha(m.getPrdha());
					material.setTipoMaterial(m.getTipoMaterial());
					material.setNormt(m.getNormt());
					material.setZzordco(m.getZzordco());
					material.setCell_design(m.getCell_design());
					material.setMtart(m.getMtart());
					material.setTypeMat(m.getTypeMat());
					material.setGrupo_compra(m.getGrupo_compra());
					material.setSt_1(m.getSt_1());
					listaMaterial.add(material);
					break;
				}
			}
		}
		return listaMaterial;
	}
	
	// SYNCHRONIZED COMBOS
	@SuppressWarnings({ "static-access" })
	public boolean sincronizaCombos() {
		resultado = false;
		List<Item> listaCombos;
		List<Item> listaCombosHijos;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			objSAP = new SPedidos();
			List<List<Item>> result = objSAP.listaCombos();
			listaCombos = result.get(0);
			listaCombosHijos = result.get(1);
			if (listaCombos != null) {
				sqlMaterial.setEliminarCombo();
				sqlMaterial.setInsertarCombo(listaCombos);
			}
			if (listaCombosHijos != null) {
				sqlMaterial.setEliminarComboHijos();
				sqlMaterial.setInsertarComboHijos(listaCombosHijos);
			}
			resultado = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED CONDICIONES COMERCIALES
//	@SuppressWarnings({ "static-access", "unused" })
	public boolean sincronizaCondicionesComerciales() {
		resultado = true;
		sincronizaCC("ZTSD_ZD01");
		sincronizaCC("ZTSD_ZD02");
		sincronizaCC("ZTSD_ZD3X");
		sincronizaCC("ZTSD_ZD4X");
		sincronizaCC("ZTSD_ZD05");
		sincronizaCC("ZTSD_SCALES");
		/*
		 * PRECIO CLIENTE MATERIAL
		 */
//		sincronicacionPrecioClienteMaterial();
		return resultado;
	}

	// SYNCHRONIZED CONDICIONES COMERCIALES
	@SuppressWarnings({})
	public boolean sincronizaClaseMaterial() {
		resultado = false;
		SqlMaterial sqlMaterial = new SqlMaterialImpl();
		try {
			objSAP = new SPedidos();
			@SuppressWarnings("static-access")
			List<BeanClaseMaterial> result = objSAP.listaClaseMaterial();
			if (result != null) {
				sqlMaterial.setInsertarClaseMaterial(result);
				resultado = true;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED TIPOLOGIA
	@SuppressWarnings({})
	public boolean sincronizaTipologia() {
		resultado = false;
		List<BeanTipologia> listaTipologia;
		SqlTipologia sqlTipologia = new SqlTipologiaImpl();
		try {
			objSAP = new SPedidos();
			listaTipologia = objSAP.listarTipologias();
			if (listaTipologia != null) {
				sqlTipologia.setEliminarTipologia();
				sqlTipologia.setInsertarTipologia(listaTipologia);
			}
			resultado = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED BLOQUEO ENTREGA
	public boolean sincronizaBloqueoEntrega() {
		resultado = false;
		List<BeanBloqueoEntrega> listaBloqueoEntrega;
		SqlBloqueoEntrega sqlBloqueoEntrega = new SqlBloqueoEntregaImpl();
		try {
			objSAP = new SPedidos();
			listaBloqueoEntrega = objSAP.guardarBloqueosEntrega();
			if (listaBloqueoEntrega != null) {
				sqlBloqueoEntrega.setEliminarBloqueoEntrega();
				sqlBloqueoEntrega.setInsertarBloqueoEntrega(listaBloqueoEntrega);
				resultado = true;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED TABLA_CONDICION_PAGO
	public boolean sincronizaCondicionPago() {
		resultado = false;
		List<BeanCondicionPago> listaCondicionesPago;
		List<BeanCondicionPago> listaCondicionesPagoDetalle;
		SqlCondicionPago sqlCondicionPago = new SqlCondicionPagoImpl();
		try {
			objSAP = new SPedidos();
			listaCondicionesPago = objSAP.listaCondicionesPago();
			if (listaCondicionesPago != null) {
				sqlCondicionPago.setEliminarCondicionesPago();
				sqlCondicionPago.setInsertarCondicionesPago(listaCondicionesPago);
			}
			listaCondicionesPagoDetalle = objSAP.listaCondicionesPagoDetalle();
			if (listaCondicionesPagoDetalle != null) {
				sqlCondicionPago.eliminarCondicionesPagoDetalle();
				sqlCondicionPago.insertarCondicionesPagoDetalle(listaCondicionesPagoDetalle);
			}
			resultado = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}

	// SYNCHRONIZED TABLA_CONDICION_PAGO
	public void sincronizaCliente(String idVendedor) throws Exception {
		resultado = false;
		List<BeanCliente> listaCliente;
		SqlCliente sqlCliente = new SqlClienteImpl();
		objSAP = new SPedidos();
		listaCliente = objSAP.vendedorClientes2(idVendedor, "", "");
		if (listaCliente != null) {
			sqlCliente.setEliminarCliente(idVendedor);
			sqlCliente.setInsertarCliente2(idVendedor, listaCliente);
		}
	}

	public static void main(String[] args) {
		SqlSincronizacionImpl sql = new SqlSincronizacionImpl();
		sql.sincronizaSede();
	}

	// SYNCHRONIZED TABLA_SEDE
	@SuppressWarnings("static-access")
	public boolean sincronizaSede() {
		resultado = false;
		List<BeanSede> listaSede = new ArrayList<BeanSede>();
		SqlSede sqlSede = new SqlSedeImpl();
		try {
			objSAP = new SPedidos();
			listaSede = objSAP.listarSucursales(Promesa.getInstance().datose.get(0).getStrCodigo());
			if (listaSede != null) {
				sqlSede.setEliminarSede();
				sqlSede.setInsertarSede(listaSede);
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
				+ " VALUES (" + Sincronizar.getStrIdeSinc() + ",'" + Sincronizar.getStrInfSinc()
				+ "'," + Sincronizar.getStrCantReg() + ",'" + Sincronizar.getStrFecHor() + "'," + Sincronizar.getStrTie() + ") ";
		histSinc.setInsertarHistorialSincronizacion(Sincronizar);
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar, "Sincronización", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void actualizarSincronizar(BeanSincronizar sincronizar) {
		sqlSincronizar = "UPDATE PROFFLINE_TB_SINCRONIZACION SET " + " numCantReg=" + sincronizar.getStrCantReg()
				+ " ,txtFecHor='" + sincronizar.getStrFecHor() + "' " + " ,numTie='" + sincronizar.getStrTie() + "'"
				+ " WHERE numIdeSinc=" + sincronizar.getStrIdeSinc() + ";";
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar, "Sincronización", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setEliminarSincronizacion(BeanSincronizar Sincronizar) {
		sqlSincronizar = "DELETE FROM PROFFLINE_TB_SINCRONIZACION " + "WHERE numIdeSinc = " + Sincronizar.getStrIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar, "Sincronización", Constante.BD_SYNC);
		resultado = resultExecute.isFlag();
	}

	public void setActualizarSincronizacionDetalle(DetalleSincronizacion ds) {
		sqlSincronizar = " UPDATE PROFFLINE_TB_DETALLE_SINCRONIZACION " + " SET txtHorIni='" + ds.getTxtHorIni() + "', " + " numFec="
				+ ds.getNumFec() + " " + " WHERE numIdeSinc = " + ds.getNumIdeSinc();
		ResultExecute resultExecute = new ResultExecute(sqlSincronizar, "Sincronización", Constante.BD_SYNC);
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

	
	@SuppressWarnings({ "rawtypes", "unused" })
	private boolean sincronizaCC(String tabla){
		try{
		ConexionSAP con = Conexiones.getConexionSAP();
		if (con != null) {
			con.RegistrarRFC("ZSD_RFC_MIGRA_CONDICIONES");
			con.IngresarDatosInput(tabla, "TABLE_NAME");
			con.EjecutarRFC();
			if (tabla.compareTo("ZTSD_ZD01") == 0) {
				con.CreaTabla("T_ZD01");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_1X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String nivel = v[2].isEmpty() ? "*" : v[2];
					String claseCond = v[3].isEmpty() ? "*" : v[3];
					String acceso = v[4].isEmpty() ? "*" : v[4];
					String tablaCond = v[5].isEmpty() ? "*" : v[5];
					String prioridad = v[6].isEmpty() ? "*" : v[6];
					String claseMaterial = v[7].isEmpty() ? "*" : v[7];
					String condicionPago = v[9].isEmpty() ? "*" : v[9];
					String cliente = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
					String unidad = v[10].isEmpty() ? "*" : v[10];
					String importe = v[11].isEmpty() ? "*" : v[11];
					String escala = v[12];
					String nroRegCond = v[13].isEmpty() ? "*" :  "" + Long.parseLong(v[13]);
					String num = v[14].isEmpty() ? "*" : v[14];
					sql = "insert into PROFFLINE_TB_CONDICION_1X values('" + nivel + "','" + claseCond + "','" + acceso
							+ "','" + tablaCond + "','" + prioridad + "','" + claseMaterial + "','" + condicionPago + "','"
							+ cliente + "','" + unidad + "','" + importe + "','" + escala + "','" + nroRegCond + "','" + num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			} else if (tabla.compareTo("ZTSD_ZD02") == 0) {
				con.CreaTabla("T_ZD02");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_2X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String nivel = v[2].isEmpty() ? "*" : v[2];
					String claseCond = v[3].isEmpty() ? "*" : v[3];
					String acceso = v[4].isEmpty() ? "*" : v[4];
					String tablaCond = v[5].isEmpty() ? "*" : v[5];
					String prioridad = v[6].isEmpty() ? "*" : v[6];
					String cliente = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
					String grupoCliente = v[8].isEmpty() ? "*" : v[8];
					String canal = v[9].isEmpty() ? "*" : v[9];
					String unidad = v[10].isEmpty() ? "*" : v[10];
					String importe = v[11].isEmpty() ? "*" : v[11];
					String escala = v[12];
					String nroRegCond = v[13].isEmpty() ? "*" : "" + Long.parseLong(v[13]);
					String num = v[14].isEmpty() ? "*" : v[14];
					sql = "insert into PROFFLINE_TB_CONDICION_2X values('" + nivel + "','" + claseCond + "','" + acceso + "','" 
							+ tablaCond + "','" + prioridad + "','" + cliente + "','"  + grupoCliente + "','" + canal 
							+ "','" + unidad + "','" + importe + "','" + escala + "','" + nroRegCond + "','"  + num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			} else if (tabla.compareTo("ZTSD_ZD3X") == 0) {
				con.CreaTabla("T_ZD3X");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_3X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				/*
				 *  @author	Marcelo Moyano
				 *  @date	01/12/2014
				 *  @Description	SEPARAR LAS CONDICIONES 3X CON RESPECTO A LA CLASE DE CONDICIÓN
				 *  				ZPR1 EN UNA TABLA 3X_ZPR1.
				 * 
				 */
				String sqlZPR1 = "DELETE FROM PROFFLINE_TB_CONDICION_ZPR1;";
				rs = new ResultExecute(sqlZPR1, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String nivel = v[2].isEmpty() ? "*" : v[2];
					String ClaseCond = v[3].isEmpty() ? "*" : v[3];
					String Acceso = v[4].isEmpty() ? "*" : v[4];
					String TablaCond = v[5].isEmpty() ? "*" : v[5];
					String Prioridad = v[6].isEmpty() ? "*" : v[6];
					String Material = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
					String DivisionC = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
					String DivisionCom = v[9].isEmpty() ? "*" : "" + Long.parseLong(v[9]);
					String CatProd = v[10].isEmpty() ? "*" : "" + Long.parseLong(v[10]);
					String Familia = v[11].isEmpty() ? "*" :"" + Long.parseLong(v[11]);
					String Linea = v[12].isEmpty() ? "*" : "" + Long.parseLong(v[12]);
					String Grupo = v[13].isEmpty() ? "*" : "" + Long.parseLong(v[13]);
					String Cliente = v[14].isEmpty() ? "*" : "" + Long.parseLong(v[14]);
					String ZonaPromesa = v[15].isEmpty() ? "*" : v[15];
					String Clase = v[16].isEmpty() ? "*" : v[16];
					String GrupoCliente = v[17].isEmpty() ? "*" : v[17];
					String Unidad = v[18].isEmpty() ? "*" : v[18];
					String Importe = v[19].isEmpty() ? "*" : v[19];
					String Escala = v[20];
					String NroRegCond = v[21].isEmpty() ? "*" : "" + Long.parseLong(v[21]);
					String Num = v[22].isEmpty() ? "*" : v[22];
					/*
					 * 	@AUTHOR	MARCELO MOYANO
					 *  01/12/2014
					 */
					String tipoTabla = " PROFFLINE_TB_CONDICION_3X ";
					if(ClaseCond.equalsIgnoreCase("ZPR1")){
						tipoTabla = " PROFFLINE_TB_CONDICION_ZPR1 ";
					}
					sql = "insert into" + tipoTabla +"values('" + nivel + "','" + ClaseCond + "','" + Acceso + "','" 
							+ TablaCond + "','" + Prioridad + "','" + Material + "','" + DivisionC + "','" 
							+ DivisionCom + "','" + CatProd + "','" + Familia + "','" + Linea + "','" + Grupo + "','" + Cliente
							+ "','" + ZonaPromesa + "','" + Clase + "','" + GrupoCliente + "','" + Unidad + "','" + Importe 
							+ "','" + Escala + "','" + NroRegCond + "','" + Num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			} else if (tabla.compareTo("ZTSD_ZD4X") == 0) {
				con.CreaTabla("T_ZD4X");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_4X;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String Nivel = v[2].isEmpty() ? "*" : v[2];
					String ClaseCond = v[3].isEmpty() ? "*" : v[3];
					String Acceso = v[4].isEmpty() ? "*" : v[4];
					String TablaCond = v[5].isEmpty() ? "*" : v[5];
					String Prioridad = v[6].isEmpty() ? "*" : v[6];
					String Material = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
					String DivisionC = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
					String DivisionCom = v[9].isEmpty() ? "*" : "" + Long.parseLong(v[9]);
					String CatProd = v[10].isEmpty() ? "*" : "" + Long.parseLong(v[10]);
					String Familia = v[11].isEmpty() ? "*" : "" + Long.parseLong(v[11]);
					String Linea = v[12].isEmpty() ? "*" : "" + Long.parseLong(v[12]);
					String Grupo = v[13].isEmpty() ? "*" : "" + Long.parseLong(v[13]);
					String Cliente = v[14].isEmpty() ? "*" : "" + Long.parseLong(v[14]);
					String ZonaPromesa = v[15].isEmpty() ? "*" : v[15];
					String Clase = v[16].isEmpty() ? "*" : v[16];
					String GrupoCliente = v[17].isEmpty() ? "*" : v[17];
					String Unidad = v[18].isEmpty() ? "*" : v[18];
					String Importe = v[19].isEmpty() ? "*" : v[19];
					String Escala = v[20];
					String NroRegCond = v[21].isEmpty() ? "*" : "" + Long.parseLong(v[21]);
					String Num = v[22].isEmpty() ? "*" : v[22];
					sql = "insert into PROFFLINE_TB_CONDICION_4X values('" + Nivel + "','" + ClaseCond + "','" + Acceso + "','" 
							+ TablaCond + "','" + Prioridad + "','" + Material + "','" + DivisionC + "','" + DivisionCom 
							+ "','" + CatProd + "','" + Familia + "','" + Linea + "','" + Grupo + "','" + Cliente
							+ "','" + ZonaPromesa + "','" + Clase + "','" + GrupoCliente + "','" + Unidad + "','" + Importe 
							+ "','" + Escala + "','" + NroRegCond + "','" + Num + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			}
			 else if (tabla.compareTo("ZTSD_ZD05") == 0) {
					con.CreaTabla("T_ZD05");
					List resultado = con.ObtenerDatosTabla();
					String sql = "DELETE FROM PROFFLINE_TB_CONDICION_5X;";
					ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
					for (int i = 0; i < resultado.size(); i++) {
						String[] v = String.valueOf(resultado.get(i)).split("[¬]");
						String Nivel = v[2].isEmpty() ? "*" : v[2];
						String ClaseCond = v[3].isEmpty() ? "*" : v[3];
						String Acceso = v[4].isEmpty() ? "*" : v[4];
						String TablaCond = v[5].isEmpty() ? "*" : v[5];
						String Prioridad = v[6].isEmpty() ? "*" : v[6];
						String DivisionCom = v[7].isEmpty() ? "*" : "" + Long.parseLong(v[7]);
						String CatProd = v[8].isEmpty() ? "*" : "" + Long.parseLong(v[8]);
						String Familia = v[9].isEmpty() ? "*" : "" + Long.parseLong(v[9]);
						String Cliente = v[10].isEmpty() ? "*" : "" + Long.parseLong(v[10]);
						String Unidad = v[11].isEmpty() ? "*" : v[11];
						String Importe = v[12].isEmpty() ? "*" : v[12];
						String Escala = v[13];
						String NroRegCond = v[14].isEmpty() ? "*" : "" + Long.parseLong(v[14]);
						String Num = v[15].isEmpty() ? "*" : v[15];
						sql = "insert into PROFFLINE_TB_CONDICION_5X values('" + Nivel + "','" + ClaseCond + "','" + Acceso + "','" 
								+ TablaCond + "','" + Prioridad + "','" + DivisionCom + "','" + CatProd + "','" + Familia
								+ "','" + Cliente + "','" + Unidad + "','" + Importe + "','" + Escala + "','" + NroRegCond + "','" + Num + "');";
						rs = new ResultExecute(sql, Constante.BD_SYNC);
					}
				}
			else if (tabla.compareTo("ZTSD_SCALES") == 0) {
				con.CreaTabla("T_SCALES");
				List resultado = con.ObtenerDatosTabla();
				String sql = "DELETE FROM PROFFLINE_TB_CONDICION_ESCALAS;";
				ResultExecute rs = new ResultExecute(sql, Constante.BD_SYNC);
				for (int i = 0; i < resultado.size(); i++) {
					String[] v = String.valueOf(resultado.get(i)).split("[¬]");
					String ClaseCond = v[2].isEmpty() ? "*" : v[2];
					String TipoEscala = v[3].isEmpty() ? "*" : v[3];
					String NroRegCond = v[4].isEmpty() ? "*" : "" + Long.parseLong(v[4]);
					String NumActCond = v[5].isEmpty() ? "*" : v[5];
					String NumLinea = v[6].isEmpty() ? "*" : v[6];
					String CantEscala = v[7];
					String ValorEscala = v[8];
					String Importe = v[9].isEmpty() ? "*" : v[9];
					sql = "insert into PROFFLINE_TB_CONDICION_ESCALAS values('"
							+ ClaseCond + "','" +TipoEscala + "','" + NroRegCond + "','" + NumActCond
							+ "','" + NumLinea + "','" + CantEscala + "','" + ValorEscala + "','" + Importe + "');";
					rs = new ResultExecute(sql, Constante.BD_SYNC);
				}
			}
			con.DesconectarSAP();
		}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/*
	 * PRECIO X CLIENTE MATERIAL
	 */
	public boolean sincronicacionPrecioClienteMaterial(){
		boolean resultado = false;
		List<BeanClienteMaterial> listaClienteMaterial = sincronizarClienteMaterialDesdeSap();
		if(listaClienteMaterial != null){
			try {
				eliminarPrecioClienteMaterial();
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				resultado = false;
			}
			guardarPrecioClienteMaterial(listaClienteMaterial);
			resultado = true;
		}
		return resultado;
	}
	
	private void eliminarPrecioClienteMaterial() throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "DELETE FROM PROFFLINE_TB_CONDICION_PRECIO_CLIENTEXMATERIAL";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}

	private void guardarPrecioClienteMaterial(List<BeanClienteMaterial> listaClienteMaterial) {
		String sqlClienteMaterial = null;
		List<String> sql = new ArrayList<String>();
		for (BeanClienteMaterial bcm : listaClienteMaterial) {
			sqlClienteMaterial = "INSERT INTO PROFFLINE_TB_CONDICION_PRECIO_CLIENTEXMATERIAL" 
								+ "(CONDICIONZPR1, CODIGOCLIENTE, CODIGOMATERIAL, PRECIOESPECIAL) VALUES ('"
								+ bcm.getCondicionZPR1()  + "', '" + bcm.getCodigoCliente()
								+ bcm.getCodigoMaterial() + "', '" + bcm.getPrecioClienteMaterial() + "');";
			sql.add(sqlClienteMaterial);
		}
		ResultExecuteList resultExecute = new ResultExecuteList();
		resultExecute.insertarListaConsultas(sql, "Precio Cliente Material", Constante.BD_SYNC);
	}

	@SuppressWarnings("rawtypes")
	private List<BeanClienteMaterial> sincronizarClienteMaterialDesdeSap() {
		List<BeanClienteMaterial> listaClienteMaterial = null;
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null) {
				listaClienteMaterial = new ArrayList<BeanClienteMaterial>();
				Promesa.getInstance().mostrarAvisoSincronizacion("SAP: Se Conecto a SAP, Clientes Material.");
				con.RegistrarRFC("");
				con.EjecutarRFC();
				con.CreaTabla("");
				List ClientesMateriales = con.ObtenerDatosTabla();
				if(ClientesMateriales.size() > 0) {
					for(int i = 0; i < ClientesMateriales.size(); i++) {
						String cadena = String.valueOf(ClientesMateriales.get(i));
						String[] temporal = cadena.split("¬");
						BeanClienteMaterial bcm = new BeanClienteMaterial();
						bcm.setCondicionZPR1("" + temporal[1]);
						bcm.setCodigoCliente("" + temporal[2]);
						bcm.setCodigoMaterial("" + temporal[3]);
						bcm.setPrecioClienteMaterial(Double.parseDouble("" + temporal[4]));
						listaClienteMaterial.add(bcm);
					}
					con.DesconectarSAP();
					Promesa.getInstance().mostrarAvisoSincronizacion("");
				}
			}
		}catch (Exception ex) {
			Util.mostrarExcepcion(ex);
		}
		return listaClienteMaterial;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public double precioClienteXMaterial(String strCondicionZPR1, String strCodigoCliente, String strCodigoMaterial){
		double precio = -1d;
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		column = new HashMap();
		column.put("String:0", "precioEspecial");
		String sql = "SELECT PRECIOESPECIAL FROM PROFFLINE_TB_CONDICION_PRECIO_CLIENTEXMATERIAL WHERE CONDICIONZPR1 = '"
					+ strCondicionZPR1 + "' AND CODIGOCLIENTE = '" + strCodigoCliente + "' AND CODIGOMATERIAL = '" + strCodigoMaterial + "';";
		
		ResultExecuteQuery resultExecuteQuery = new ResultExecuteQuery(sql, column, Constante.BD_SYNC);
		if(resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if(mapResultado.size() > 0) {
				HashMap res = (HashMap) mapResultado.get(0);
				precio = Double.parseDouble(res.get("precioEspecial").toString());
			}
		}
		return precio;
	}
	
	/*
	 * MARCELO MOYANO
	 */
	public boolean sincronizaZtConstante() {
		resultado = false;
		List<BeanParametro> listaParametro;
		SqlPedidoImpl sqlPedido = new SqlPedidoImpl();
		try {
			objSAP = new SPedidos();
			listaParametro = objSAP.obtenerParametrosConstantes();
			if (listaParametro != null) {
				sqlPedido.eliminarTablaBloquePedido("BLOCK_PED");
				sqlPedido.insertarBloquePedido(listaParametro);
			}
			resultado = true;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			resultado = false;
		}
		return resultado;
	}
	// --------------------------------------------------- //

	private static String NUM_ID_SINC = "numIdeSinc";
	private static String INFO_SINC = "txtInfSinc";
	private static String CANT_REG = "numCantReg";
	private static String FECH_HOR = "txtFecHor";
	private static String TIEMPO = "numTie";
	@SuppressWarnings("unused")
	private static String HORA_INI = "txtHorIni";
	@SuppressWarnings("unused")
	private static String FRECUENCIA = "numFec";
	@SuppressWarnings("unused")
	private static String TABLA_SINCRONIZACION = "PROFFLINE_TB_SINCRONIZACION";
}