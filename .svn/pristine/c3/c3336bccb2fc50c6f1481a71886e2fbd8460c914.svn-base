package com.promesa.sap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import util.ConexionSAP;

import com.promesa.cobranzas.bean.BeanFacturaDetalle;
import com.promesa.cobranzas.bean.BeanFacturaHeader;
import com.promesa.util.Conexiones;
import com.promesa.util.Util;

public class SFacturas {
	
	@SuppressWarnings("rawtypes")
	public List<BeanFacturaHeader> obtenerFacturasSap(String codigoVendedor, String codigoCliente, String fechaInicio, String fechaFin){
		try{
			List<BeanFacturaHeader> listaFacturas = new ArrayList<BeanFacturaHeader>();
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				con.RegistrarRFC("ZSD_RFC_BILL_PROFFLINE_K");
				con.IngresarDatosInput(codigoVendedor, "IV_VENDOR_ID");
				con.IngresarDatosInput(codigoCliente, "IV_CUST_ID");
				con.IngresarDatosInput(fechaInicio, "IV_BEGDATUM");
				con.IngresarDatosInput(fechaFin, "IV_ENDDATUM");
				con.EjecutarRFC();
				con.CreaTabla("ET_BILL_OUT");
				List facturas = con.ObtenerDatosTabla();
				BeanFacturaHeader bfh = null;
				if(facturas.size() > 0){
					for(int i = 0; i < facturas.size(); i++){
						String cadena = String.valueOf(facturas.get(i));
						String[] temporal = cadena.split("¬");
						bfh = new BeanFacturaHeader();
						bfh.setNumeroFacturaSap(temporal[1]);
						bfh.setNumeroFacturaSRI(temporal[2]);
						bfh.setFechaFactura(convierteFecha(temporal[3]));
						bfh.setTipoDocumento(temporal[4]);
						bfh.setCodigocliente(temporal[5]);
						bfh.setNombreCliente(temporal[6]);
						bfh.setNumeroPedido(temporal[7]);
						bfh.setValorNeto(temporal[8]);
						bfh.setTotalImpuesto(temporal[9]);
						bfh.setValorTotal(temporal[11]);
						bfh.setCondicionPago(temporal[10]);
						listaFacturas.add(bfh);
					}
					con.DesconectarSAP();
					return listaFacturas;
				}else
					return null;
			}else
				return null;
			
		}catch(Exception e){
			Util.mostrarExcepcion(e);
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<BeanFacturaDetalle> obtenerDetalleFactura(String numeroFactura){
		try{
			List<BeanFacturaDetalle> detalle = new ArrayList<BeanFacturaDetalle>();
			ConexionSAP con = Conexiones.getConexionSAP();
			if(con != null){
				con.RegistrarRFC("ZSD_RFC_BILL_PROFFLINE_P");
				con.IngresarDatosInput(numeroFactura, "IV_BILL_ID");
				con.EjecutarRFC();
				con.CreaTabla("ET_BILL_P_OUT");
				List detallesFacturas = con.ObtenerDatosTabla();
				BeanFacturaDetalle df = null;
				if(detallesFacturas.size() > 0){
					for(int i = 0; i < detallesFacturas.size(); i++){
						String cadena = String.valueOf(detallesFacturas.get(i));
						String[] temporal = cadena.split("¬");
						df = new BeanFacturaDetalle();
						df.setPosicion(temporal[2]);
						df.setCodigoMaterial(temporal[3]);
						df.setDescripcion((temporal[4]));
						df.setCantidadPedido(temporal[17]);
						df.setCantidadFactura(temporal[5]);
						try{
							Double precioUnitario = Double.parseDouble(temporal[8]) / Double.parseDouble(temporal[5]);
							df.setPrecioUnitario(String.valueOf(precioUnitario));
						}catch(Exception ex){
							Util.mostrarExcepcion(ex);
							df.setPrecioUnitario("0");
						}
						df.setDescuentoCanal(temporal[9]);
						df.setDescuentos3X(temporal[10]);
						df.setDescuentos4X(temporal[11]);
						df.setDescuentoVolumen(temporal[12]);
						df.setDescuentoCodigo(temporal[13]);
						df.setDescuentoManual(temporal[14]);
						df.setValorNeto(temporal[6]);
						Double precioNeto = Double.parseDouble(temporal[6]) / Double.parseDouble(temporal[5]);
						df.setPrecioNeto(String.valueOf(precioNeto));
						detalle.add(df);
					}
					con.DesconectarSAP();
					return detalle;
				}else
					return null;
			}else
				return null;
		}catch (Exception ex){
			Util.mostrarExcepcion(ex);
			return null;
		}
	}
	
	private String convierteFecha(String fsf) {
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
}