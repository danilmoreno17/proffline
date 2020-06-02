package com.promesa.cobranzas.sql.impl;

import java.util.ArrayList;
import java.util.List;

import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;
import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.cobranzas.bean.RegistroPagoCliente;

public class SqlRegistroPagoClienteImpl {
	
	private static SqlCabeceraRegistroPagoClienteImpl sqlCabeceraRegistroPagoClienteImpl = null;
	private static SqlPagoRecibidoImpl sqlPagoRecibidoImpl = null;
	private static SqlPagoParcialImpl sqlPagoParcialImpl = null;
	
	public static RegistroPagoCliente obtenerRegistroPagoCliente(long idCabecera) {
		RegistroPagoCliente registroPagoCliente = new RegistroPagoCliente();
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		sqlPagoParcialImpl = new SqlPagoParcialImpl();
		CabeceraRegistroPagoCliente cabeceraRegistroPagoCliente = sqlCabeceraRegistroPagoClienteImpl.obtenerCabeceraRegistroPagoCliente(idCabecera);
		List<PagoRecibido> listaPagoRecibido = sqlPagoRecibidoImpl.obtenerListaPagoRecibido(idCabecera);
		List<PagoParcial> listaPagoParcial = sqlPagoParcialImpl.obtenerListaPagoParcial(idCabecera);
		registroPagoCliente.setCabeceraRegistroPagoCliente(cabeceraRegistroPagoCliente);
		registroPagoCliente.setListaPagoRecibido(listaPagoRecibido);
		registroPagoCliente.setListaPagoParcial(listaPagoParcial);
		return registroPagoCliente;
	}
	
	public static void actualizarRegistroPagoCliente(RegistroPagoCliente registroPagoCliente) throws Exception{
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		sqlPagoParcialImpl = new SqlPagoParcialImpl();
		sqlCabeceraRegistroPagoClienteImpl.actualizarCabeceraRegistroPagoCliente(registroPagoCliente.getCabeceraRegistroPagoCliente());
		sqlPagoRecibidoImpl.eliminarListaPagoRecibido(registroPagoCliente.getCabeceraRegistroPagoCliente().getIdCabecera());
		sqlPagoRecibidoImpl.insertarListaPagoRecibido(registroPagoCliente.getListaPagoRecibido());
		sqlPagoParcialImpl.eliminarListaPagoParcial(registroPagoCliente.getCabeceraRegistroPagoCliente().getIdCabecera());
		sqlPagoParcialImpl.insertarListaPagoParcial(registroPagoCliente.getListaPagoParcial());
	}
	
	public static void eliminarRegistroPagoCliente(RegistroPagoCliente registroPagoCliente) throws Exception{
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		sqlPagoParcialImpl = new SqlPagoParcialImpl();
		sqlCabeceraRegistroPagoClienteImpl.eliminarCabeceraRegistroPagoCliente(registroPagoCliente.getCabeceraRegistroPagoCliente());
		sqlPagoRecibidoImpl.eliminarListaPagoRecibido(registroPagoCliente.getCabeceraRegistroPagoCliente().getIdCabecera());
		sqlPagoParcialImpl.eliminarListaPagoParcial(registroPagoCliente.getCabeceraRegistroPagoCliente().getIdCabecera());
	}
	
	public static void actualizarCabeceraSincronizado(RegistroPagoCliente registro){
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		try {
			sqlCabeceraRegistroPagoClienteImpl.actualizarCabeceraRegistroPagoClienteSincronizado(registro.getCabeceraRegistroPagoCliente());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<RegistroPagoCliente> getCobranzaElimindosNoSincronizados(){
		List<RegistroPagoCliente> regitros = new ArrayList<RegistroPagoCliente>();
		
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		sqlPagoParcialImpl = new SqlPagoParcialImpl();
		
		List<CabeceraRegistroPagoCliente> cabeceras = sqlCabeceraRegistroPagoClienteImpl.obtenerCabezaraOffLineEliminada();
		
		for (CabeceraRegistroPagoCliente cabecera : cabeceras) {
			RegistroPagoCliente registro = new RegistroPagoCliente();
			registro.setCabeceraRegistroPagoCliente(cabecera);
			List<PagoRecibido> listaPagoRecibido = sqlPagoRecibidoImpl.obtenerListaPagoRecibido(cabecera.getIdCabecera());
			List<PagoParcial> listaPagoParcial = sqlPagoParcialImpl.obtenerListaPagoParcial(cabecera.getIdCabecera());
			registro.setListaPagoParcial(listaPagoParcial);
			registro.setListaPagoRecibido(listaPagoRecibido);
			regitros.add(registro);
		}
		return regitros;
	}
	
	public static List<RegistroPagoCliente> getCobranzaElimindosSincronizados(){
		List<RegistroPagoCliente> regitros = new ArrayList<RegistroPagoCliente>();
		
		sqlCabeceraRegistroPagoClienteImpl = new SqlCabeceraRegistroPagoClienteImpl();
		sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		sqlPagoParcialImpl = new SqlPagoParcialImpl();
		
		List<CabeceraRegistroPagoCliente> cabeceras = sqlCabeceraRegistroPagoClienteImpl.obtenerCabezaraOffLineEliminadaSinc();
		
		for (CabeceraRegistroPagoCliente cabecera : cabeceras) {
			RegistroPagoCliente registro = new RegistroPagoCliente();
			registro.setCabeceraRegistroPagoCliente(cabecera);
			List<PagoRecibido> listaPagoRecibido = sqlPagoRecibidoImpl.obtenerListaPagoRecibido(cabecera.getIdCabecera());
			List<PagoParcial> listaPagoParcial = sqlPagoParcialImpl.obtenerListaPagoParcial(cabecera.getIdCabecera());
			registro.setListaPagoParcial(listaPagoParcial);
			registro.setListaPagoRecibido(listaPagoRecibido);
			regitros.add(registro);
		}
		return regitros;
	}
}