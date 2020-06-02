package com.promesa.sap;

import java.util.ArrayList;
import java.util.List;

import util.ConexionSAP;

import com.promesa.util.Conexiones;
import com.promesa.util.Util;

public class SSistemasInformacion {

	public List<String[]> obtenerIndicadoresCambioPrecio(String orgVentas,
			String canalDistribucion, String fechaInicio, String fechaFinal, String fecha) {
//			String canalDistribucion, String fechaInicio, String fechaFinal) {// Original
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				List<String[]> indicadoresCambioPrecio = new ArrayList<String[]>();
				con.RegistrarRFC("ZSD_RFC_MATERIAL_CHANGE_PRICE");
				con.IngresarDatosInput(orgVentas, "IV_VKORG");
				con.IngresarDatosInput(canalDistribucion, "IV_VTWEG");
				con.IngresarDatosInput(fechaInicio, "IV_BEGDATUM");
				con.IngresarDatosInput(fechaFinal, "IV_ENDDATUM");
				con.IngresarDatosInput(fecha, "IV_BEGCPUDT");//Requerimiento: PRO-2013-0015
				con.EjecutarRFC();
				con.CreaTabla("ET_PRICES");
				@SuppressWarnings("unchecked")
				List<String> lstCambioPrecio = con.ObtenerDatosTabla();
				if (lstCambioPrecio != null) {
					for (String strings : lstCambioPrecio) {
						String[] valores = strings.split("¬");
						indicadoresCambioPrecio.add(valores);
					}
				}
				con.DesconectarSAP();
				return indicadoresCambioPrecio;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<String[]> obtenerIndicadoresVendedor(int mes, int anio, String codigo) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_GET_SUMMARY_FOR_VENDOR");
				con.IngresarDatosInput("" + mes, "IS_MONTH");
				con.IngresarDatosInput("" + anio, "IS_YEAR");
				con.IngresarDatosInput(codigo, "IS_KUNNR");
				con.EjecutarRFC();
				con.CreaTabla("ET_SD_KPI");
				@SuppressWarnings("unchecked")
				List<String> lstIndicadoresVendedor = con.ObtenerDatosTabla();
				List<String[]> indicadoresVendedor = new ArrayList<String[]>();
				if (lstIndicadoresVendedor != null) {
					for (String strings : lstIndicadoresVendedor) {
						String[] valores = strings.split("¬");
						indicadoresVendedor.add(valores);
					}
				}
				con.DesconectarSAP();
				return indicadoresVendedor;

			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<String[]> obtenerIndicadoresDetalleVendedor(int mes, int anio, String codigo) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_GET_DETAIL_FOR_VENDOR");
				con.IngresarDatosInput("" + mes, "IS_MONTH");
				con.IngresarDatosInput("" + anio, "IS_YEAR");
				con.IngresarDatosInput(codigo, "IS_KUNNR");
				con.EjecutarRFC();
				con.CreaTabla("ET_SD_KPI");
				@SuppressWarnings("unchecked")
				List<String> lstIndicadoresDetalleVendedor = con.ObtenerDatosTabla();
				List<String[]> indicadoresDetalleVendedor = new ArrayList<String[]>();
				if (lstIndicadoresDetalleVendedor != null) {
					for (String strings : lstIndicadoresDetalleVendedor) {
						String[] valores = strings.split("¬");
						indicadoresDetalleVendedor.add(valores);
					}
				}
				con.DesconectarSAP();
				return indicadoresDetalleVendedor;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<String[]> obtenerIndicadoresSupervisor(int mes, int anio, String codigo) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_GET_SUMMARY_FOR_SUPER");
				con.IngresarDatosInput("" + mes, "IS_MONTH");
				con.IngresarDatosInput("" + anio, "IS_YEAR");
				con.IngresarDatosInput(codigo, "IS_KUNNR");
				con.EjecutarRFC();
				con.CreaTabla("ET_SD_KPI");
				@SuppressWarnings("unchecked")
				List<String> lstIndicadoresSupervisor = con.ObtenerDatosTabla();
				List<String[]> indicadoresSupervisor = new ArrayList<String[]>();
				if (lstIndicadoresSupervisor != null) {
					for (String strings : lstIndicadoresSupervisor) {
						String[] valores = strings.split("¬");
						indicadoresSupervisor.add(valores);
					}
				}
				con.DesconectarSAP();
				return indicadoresSupervisor;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}

	public List<String[]> obtenerIndicadoresDetalleSupervisor(int mes, int anio, String codigo) {
		try {
			ConexionSAP con = Conexiones.getConexionSAP();
			if (con != null) {
				con.RegistrarRFC("ZSD_RFC_GET_DETAIL_FOR_SUPER");
				con.IngresarDatosInput("" + mes, "IS_MONTH");
				con.IngresarDatosInput("" + anio, "IS_YEAR");
				con.IngresarDatosInput(codigo, "IS_KUNNR");
				con.EjecutarRFC();
				con.CreaTabla("ET_SD_KPI");
				@SuppressWarnings("unchecked")
				List<String> lstIndicadoresDetalleSupervisor = con.ObtenerDatosTabla();
				List<String[]> indicadoresDetalleSupervisor = new ArrayList<String[]>();
				if (lstIndicadoresDetalleSupervisor != null) {
					for (String strings : lstIndicadoresDetalleSupervisor) {
						String[] valores = strings.split("¬");
						indicadoresDetalleSupervisor.add(valores);
					}
				}
				con.DesconectarSAP();
				return indicadoresDetalleSupervisor;
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return null;
		}
	}
}