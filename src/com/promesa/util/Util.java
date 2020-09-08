package com.promesa.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.promesa.administracion.sql.impl.SqlDispositivoImpl;
import com.promesa.bean.BeanDato;
import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;
import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.cobranzas.bean.RegistroPagoCliente;
import com.promesa.cobranzas.sql.impl.SqlAnticipoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlCabeceraRegistroPagoClienteImpl;
import com.promesa.cobranzas.sql.impl.SqlPagoParcialImpl;
import com.promesa.cobranzas.sql.impl.SqlPagoRecibidoImpl;
import com.promesa.conexiondb.ConexionJDBC;
import com.promesa.dao.ResultExecuteQuery;
import com.promesa.dialogo.ExceptionViewer;
import com.promesa.dialogo.pedidos.DialogoConfirmacionSincronizacion;
import com.promesa.factory.Factory;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanBuscarPedido;
import com.promesa.pedidos.bean.BeanSecuencia;
import com.promesa.pedidos.bean.MarcaEstrategica;
import com.promesa.pedidos.busqueda.Indexador;
import com.promesa.pedidos.sql.SqlMaterial;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.pedidos.sql.impl.SqlSincronizacionImpl;
import com.promesa.sap.SCobranzas;
import com.promesa.sap.SPedidos;
import com.promesa.sincronizacion.bean.BeanSincronizacion;
import com.promesa.sincronizacion.impl.SincronizacionPedidos;

public class Util {

	private static boolean flag;

	public static boolean isFlag() {
		return flag;
	}

	public static void setFlag(boolean fLag) {
		flag = fLag;
	}

	public static String formatearNumero(Object value) {
		double valor = 0d;
		try {
			valor = Double.parseDouble(value.toString());
			valor = Math.round(valor * 100) / 100.0d;
			
		} catch (NumberFormatException e) {
			valor = 0d;
		}
		return String.format("%.2f", valor).replaceAll(",", ".");
	}

	public static String formatearNumeroTresDigitos(Object value) {
		double valor = 0d;
		try {
			valor = Double.parseDouble(value.toString());
			valor = Math.round(valor * 1000) / 1000.0d;
		} catch (NumberFormatException e) {
			valor = 0d;
		}
		return String.format("%.3f", valor).replaceAll(",", ".");
	}
	
	public static String formatoDigitoDecimal(Object value){
		DecimalFormat formateador = new DecimalFormat("###,###.##");
		String valor = formateador.format(Double.parseDouble(value.toString()));
		valor = valor.replace(".", "~");
		valor = valor.replace(",", ".");
		valor = valor.replace("~", ",");
		return Util.completarCerosEnDEcimales(valor);
	}
	
	public static String completarCerosEnDEcimales(String valor){
		int longitud = valor.length();
		int posPto= valor.indexOf(".");
		String justificar = "";
		if(posPto>0 && longitud - posPto - 1 < 2){
			for (int i = posPto + 1; i < longitud; i++) {
				justificar += "0";
			}
		} else if(posPto<=0){
			justificar = ".00";
		}
		return valor + justificar ;
	}

	public static String getFechaActual() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(new Date(System.currentTimeMillis()));
	}
	
	public static String getMesActual() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format(new Date(System.currentTimeMillis()));
	}
	
	public static String getAnioActual(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(new Date(System.currentTimeMillis()));
	}

	public static String getFechaActualYYYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date(System.currentTimeMillis()));
	}

	public static String getFechaActualDDMMYYYY(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(fecha);
	}

	public static String getHoraActual() {
		long time = System.currentTimeMillis();
		Date fecha = new Date(time);
		String hora = new SimpleDateFormat("HH").format(fecha);
		String minutos = new SimpleDateFormat("mm").format(fecha);
		String segundos = new SimpleDateFormat("ss").format(fecha);
		return hora + ":" + minutos + ":" + segundos;
	}

	public static String getHoraActualHHMM() {
		long time = System.currentTimeMillis();
		Date fecha = new Date(time);
		String hora = new SimpleDateFormat("HH").format(fecha);
		String minutos = new SimpleDateFormat("mm").format(fecha);
		@SuppressWarnings("unused")
		String segundos = new SimpleDateFormat("ss").format(fecha);

		return hora + ":" + minutos;
	}

	public String getFecha() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getHora() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public boolean isActualizaData(Date fechaSis, String fechaDB) {

		String[] fechaSistema = null;
		String[] fechaActualizada = null;
		boolean comparaDia = false;
		boolean comparaMes = false;
		boolean comparaAnio = false;
		boolean actualizaData = false;
		Date horaProgramada = new Date();
		if (fechaDB != null && fechaDB.indexOf(".") != -1) {
			while (fechaDB.indexOf(".") != -1) {
				fechaDB = fechaDB.substring(0, fechaDB.indexOf("."))
						+ "-"
						+ fechaDB.substring(fechaDB.indexOf(".") + 1,
								fechaDB.length());
			}
			fechaDB = fechaDB + " 01:01:01 AM";
		}
		SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd-MM-yyyy hh:mm:ss a");
		try {
			horaProgramada = new Date(dateFormat.parse(fechaDB).getTime());
			fechaActualizada = (dateFormat3.format(horaProgramada)).split("-");
			fechaSistema = (dateFormat3.format(fechaSis)).split("-");
			if (aEntero(fechaSistema[2]) == aEntero(fechaActualizada[2])) {
				comparaAnio = true;
			} else {
				comparaAnio = false;
			}
			if (aEntero(fechaSistema[1]) == aEntero(fechaActualizada[1])) {
				comparaMes = true;
			} else {
				comparaMes = false;
			}
			if (aEntero(fechaSistema[0]) == aEntero(fechaActualizada[0])) {
				comparaDia = true;
			} else {
				comparaDia = false;
			}
			if (!comparaDia) {
				actualizaData = true;
			}
			if (!comparaMes) {
				actualizaData = true;
			}
			if (!comparaAnio) {
				actualizaData = true;
			}
		} catch (Exception exec) {
			Util.mostrarExcepcion(exec);
		}
		return actualizaData;
	}

	public int aEntero(String num) {
		return Integer.parseInt(num.trim());
	}

	public static String completarCeros(int cantidad, String cadena) {
		int restante = cantidad - cadena.length();
		String justificar = "";
		for (int i = 0; i < restante; i++) {
			justificar += "0";
		}
		return justificar + cadena;
	}

	/*
	 * Marcelo Moyano 29/05/2013
	 */
	public static String eliminarCerosInicios(String cadena) {
		String subCadena = "";
		int pos = 0;
		for (int i = 0; i < cadena.length(); i++) {
			if (cadena.charAt(i) != '0') {
				pos = i;
				break;
			}
		}
		subCadena = cadena.substring(pos, cadena.length());
		return subCadena;
	}

	public static String convierteFecha(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(
				"E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return "";
		}
	}

	public static String convierteFechayyyyMMdd(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(
				"E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return "";
		}
	}
	public static String convierteFechaADDMMMYYYY(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf1.parse(StringRecogido);
			return sdf2.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return "";
		}
	}
    
	public String convierteFecha2(String fsf) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(
				"E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		try {
			String StringRecogido = fsf;
			Date datehora = sdf2.parse(StringRecogido);
			return sdf1.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return "";
		}
	}
	public Date convierteStringaDate(String strFecha){
		SimpleDateFormat sdf1 = new SimpleDateFormat(
				"E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		try {
			String StringRecogido = strFecha;
			Date datehora = sdf1.parse(StringRecogido);
			return datehora;
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return new Date();
		}
	}

	public static String convierteHoraAFormatoHHMM(String strhora) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		SimpleDateFormat sdf2 = new SimpleDateFormat("H:mm");
		try {
			String StringRecogido = strhora;
			Date datehora = sdf2.parse(StringRecogido);
			return sdf1.format(datehora);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			return "";
		}
	}

	public static String generarPosicion(String location, int tipo) {
		if (tipo < 0) {
			return "000010";
		} else {
			int posicion = 0;
			try {
				posicion = Integer.parseInt(location);
				if (tipo == 0) {
					posicion += 10;
				}
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				posicion = 0;
			}
			return Util.completarCeros(6, "" + posicion);
		}
	}

	public static void setAnchoColumnas(JTable tabla) {
		int nroFilas = tabla.getRowCount();
		int nroColumnas = tabla.getColumnCount();
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int columna = 0; columna < nroColumnas; columna++) {
			javax.swing.table.TableCellRenderer renderer = tabla
					.getColumnModel().getColumn(columna).getHeaderRenderer();
			if (renderer == null) {
				renderer = tabla.getTableHeader().getDefaultRenderer();
			}
			Component comp = renderer.getTableCellRendererComponent(tabla,
					tabla.getColumnModel().getColumn(columna).getHeaderValue(),
					false, false, 0, 0);
			int ancho = comp.getPreferredSize().width; // ancho de la cabecera
			for (int fila = 0; fila < nroFilas; fila++) {
				renderer = tabla.getCellRenderer(fila, columna);
				comp = tabla.prepareRenderer(renderer, fila, columna);
				ancho = Math.max(comp.getPreferredSize().width, ancho);
			}
			TableColumnModel modeloColumna = tabla.getColumnModel();
			TableColumn columnaTabla = modeloColumna.getColumn(columna);
			columnaTabla.setPreferredWidth(ancho + 20);
		}
	}

	public static void setAnchoColumnasTablaResultado(JTable tabla) {
		int nroFilas = tabla.getRowCount();
		int nroColumnas = tabla.getColumnCount();
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int columna = 0; columna < nroColumnas; columna++) {
			javax.swing.table.TableCellRenderer renderer = tabla
					.getColumnModel().getColumn(columna).getHeaderRenderer();
			if (renderer == null) {
				renderer = tabla.getTableHeader().getDefaultRenderer();
			}
			Component comp = renderer.getTableCellRendererComponent(tabla,
					tabla.getColumnModel().getColumn(columna).getHeaderValue(),
					false, false, 0, 0);
			int ancho = comp.getPreferredSize().width; // ancho de la cabecera
														// de cada columna
			for (int fila = 0; fila < nroFilas; fila++) {
				renderer = tabla.getCellRenderer(fila, columna);
				comp = tabla.prepareRenderer(renderer, fila, columna);
				ancho = Math.max(comp.getPreferredSize().width, ancho);
			}
			TableColumnModel modeloColumna = tabla.getColumnModel();
			TableColumn columnaTabla = modeloColumna.getColumn(columna);
			columnaTabla.setPreferredWidth(ancho + 80);
		}
	}
	@SuppressWarnings("serial")
	public static void addEscapeKey(final JDialog dialog) {
		KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		};
		dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escape, "ESCAPE");
		dialog.getRootPane().getActionMap().put("ESCAPE", escapeAction);
	}

	public static String obtenerCodigoSupervisor() {
		try {
			BeanDato usuario = Promesa.datose.get(0);
			String codigoVendedor = usuario.getStrCodigo();
			Long codigoNumerico = 0l;
			try {
				codigoNumerico = Long.parseLong(codigoVendedor);
			} catch (Exception e) {
				codigoNumerico = 0l;
			}
			SPedidos s = new SPedidos();
			return Util.completarCeros(10,
					s.obtenerCodigoSupervisor("" + codigoNumerico));
		} catch (Exception e) {
			Mensaje.mostrarError("No se pudo recuperar el supervisor.\n"
					+ e.getMessage());
			return "";
		}
	}

	public static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}

	public static void mostrarExcepcion(Exception ex) {
		String mensaje = "" + ex.getMessage();
		if (Promesa.getInstance() != null) {
			Promesa.getInstance().mostrarAvisoSincronizacion("");
			if (mensaje.compareTo("No se ha establecido la conexión con SAP") != 0) {
				ExceptionViewer.getInstance().setStackTrace(getStackTrace(ex));
				ExceptionViewer.getInstance().hacerVisible();
			}
		}
	}

	public static boolean comparaIgualdadFechaHoyYFechaArchivo(
			String strFechaHoy, String strFechaArchivo) {
		if (strFechaHoy.equals(strFechaArchivo)) {
			return true;
		}
		return false;
	}

	public static String convierteFechaAFormatoYYYYMMdd(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return "" + formatter.format(fecha);
	}
	
	public static String convierteFechaYYYYMMDDaFormatoReporte(String strFecha){
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			if(strFecha != null && !strFecha.equals(""))
				date = formatoDelTexto.parse(strFecha);
			else
				return "";
		} catch (ParseException ex) {
			date = null;
		}
		return "" + formatoDeFecha.format(date);
	}

	public static String convierteFechaAFormatoDDMMYYYY(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return "" + formatter.format(fecha);
	}

	public static Date convierteCadenaYYYYMMDDAFecha(String strFecha) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
		Date fecha;
		try {
			fecha = formatoDelTexto.parse(strFecha);
		} catch (ParseException e) {
			return null;
		}
		return fecha;
	}
    
	public static String convierteCadenaAFormatoYYYYMMDD(String strFecha) {
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		Date date = null;
		try {
			date = formatoDelTexto.parse(strFecha);
		} catch (ParseException ex) {
			date = null;
		}
		return "" + formatoDeFecha.format(date);
	}

	public static Date convierteCadenaDDMMYYYYAFecha(String strFecha) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd.MM.yyyy");
		Date fecha;
		try {
			fecha = formatoDelTexto.parse(strFecha);
		} catch (ParseException e) {
			return null;
		}
		return fecha;
	}

	public static String convierteFechaACadenaDDMMYYYY(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		return "" + formatter.format(fecha);
	}

	public static boolean comparaFecha1MenorOIgualQueFecha2(Date fecha1,
			Date fecha2) {
		if (fecha1.compareTo(fecha2) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean comparaFecha1MenorQueFecha2(Date fecha1, Date fecha2) {
		if (fecha1.compareTo(fecha2) < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(Date fechaHoy) {
		SimpleDateFormat formato = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:ss aa");
		return "" + formato.format(fechaHoy);
	}

	public static String convierteFechaHoyAFormatoDDMMYYYYHHMM(Date fechaHoy) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return "" + formato.format(fechaHoy);
	}
	
	public static String convierteFechaHoyAFormatoDDMMMYYYYHHMM(Date fechaHoy) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		return "" + formato.format(fechaHoy);
	}
	

	public static String convierteFechaAFormatoDDMMYYYYHHMMSS(Date fechaHoy) {
		SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
		return "" + formato.format(fechaHoy);
	}

	public static String convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(
			Date fechaHoy) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyyHHmmss");
		return "" + formato.format(fechaHoy);
	}

	public static String convierteFechaAFormatoDDMMMYYYY(Date fecha) {
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MMM-yyyy");
		return "" + formatoDeFecha.format(fecha);
	}

	public static String convierteFechaAFormatoDDMMMYYYYHHMMSS(Date fecha) {
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("ddMMyyyyHHmmss");
		return "" + formatoDeFecha.format(fecha);
	}

	public static String convierteFechaAFormatoDDMMMYYYY(String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaPuntoDDMMYYYAFormatoDDMMMYYYY(
			String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd.MM.yyyy");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaPuntoDDMMYYYAFormatoBarraDDMMMYYYY(
			String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
					"dd.MM.yyyy");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaPuntoDDMMYYYYAFormatoYYYYMMDD(
			String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd.MM.yyyy");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaDDMMYYYYAFormatoYYYYMMDD(String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
					"dd/MM/yyyy");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaYYYYMMDDAFormatoDDMMYYYY(String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}
	
	public static String convierteFechaPuntoDD_MMM_YYYAFormatoYYYYMMdd(String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaPuntoDDMMYYYAFormatoYYYYMMdd(String fecha) {
		if (fecha != null && !fecha.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
			Date date = null;
			try {
				date = formatoDelTexto.parse(fecha);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteHoraDosPuntosAHoraHHMMSS(String hora) {
		if (hora != null && !hora.isEmpty()) {
			SimpleDateFormat formatoDeFecha = new SimpleDateFormat("HHmmss");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm:ss");
			Date date = null;
			try {
				date = formatoDelTexto.parse(hora);
			} catch (ParseException ex) {
				date = null;
			}
			return "" + formatoDeFecha.format(date);
		} else {
			return "";
		}
	}

	public static String convierteFechaAFormatoYYYY(Date fecha) {
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy");
		return "" + formatoDeFecha.format(fecha);
	}

	public static Date obtenerFechaAyer() {
		int diferenciaEnDias = 1;
		Date fechaActual = Calendar.getInstance().getTime();
		long tiempoActual = fechaActual.getTime();
		long unDia = diferenciaEnDias * 24 * 60 * 60 * 1000;
		Date fechaAyer = new Date(tiempoActual - unDia);
		return fechaAyer;
	}

	@SuppressWarnings("unused")
	public static String leerFechaArchivo() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			final String KEY_FILE = "init.key";
			final String FTP_FILE = "ftp.properties";
			final Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(FTP_FILE), "UTF-8");
			props.load(in);
			String sLocalDirectorio = props.getProperty("local.directorio").trim();
			archivo = new File(sLocalDirectorio + "\\archivo.bat");
			if (!archivo.exists()) {
				escribirFechaAArchivo(Util.convierteFechaAFormatoDDMMYYYY(Util.obtenerFechaAyer()));
				archivo = new File(sLocalDirectorio + "\\archivo.bat");
			}
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			// Lectura del fichero
			String linea;
			while ((linea = br.readLine()) != null) {
				return linea.toString();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	public static String obtenerNombrePromesa() {
		String strDireccion = "";
		try {
			final String EMPRESA_FILE = "empresa.properties";
			Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					EMPRESA_FILE), "UTF-8");
			props.load(in);
			strDireccion = props.getProperty("promesa.nombre").trim();
			in.close();
		} catch (Exception e) {
			strDireccion = "";
		}
		return strDireccion;
	}

	public static String obtenerDireccionPromesa() {
		String strDireccion = "";
		try {
			final String EMPRESA_FILE = "empresa.properties";
			Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					EMPRESA_FILE), "UTF-8");
			props.load(in);
			strDireccion = props.getProperty("promesa.direccion").trim();
			in.close();
		} catch (Exception e) {
			strDireccion = "";
		}
		return strDireccion;
	}

	public static String obtenerTelefonoPromesa() {
		String strTelefono = "";
		try {
			final String EMPRESA_FILE = "empresa.properties";
			Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					EMPRESA_FILE), "UTF-8");
			props.load(in);
			strTelefono = props.getProperty("promesa.telefono").trim();
			in.close();
		} catch (Exception e) {
			strTelefono = "";
		}
		return strTelefono;
	}

	public static double obtenerIva() {
		double iva = 0d;
		try {
			final String EMPRESA_FILE = "empresa.properties";
			Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					EMPRESA_FILE), "UTF-8");
			props.load(in);
			String strIVA = props.getProperty("promesa.iva").trim();
			iva = Double.parseDouble(strIVA);
			in.close();
		} catch (Exception e) {
			Mensaje.mostrarError("No se ha podido determinar el valor del I.V.A");
			iva = 0d;
		}
		return iva;
	}

	public static String obtenerSociedadPromesa() {
		String strSociedad = "";
		try {
			final String EMPRESA_FILE = "empresa.properties";
			Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					EMPRESA_FILE), "UTF-8");
			props.load(in);
			strSociedad = props.getProperty("promesa.sociedad").trim();
			in.close();
		} catch (Exception e) {
			Mensaje.mostrarError("No se ha podido determinar la sociedad.");
			strSociedad = "";
		}
		return strSociedad;
	}

	public static String obtenerCanalPromesa() {
		String strSociedad = "";
		try {
			final String EMPRESA_FILE = "empresa.properties";
			Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					EMPRESA_FILE), "UTF-8");
			props.load(in);
			strSociedad = props.getProperty("promesa.canal").trim();
			in.close();
		} catch (Exception e) {
			Mensaje.mostrarError("No se ha podido determinar el canal de distribución.");
			strSociedad = "";
		}
		return strSociedad;
	}

	@SuppressWarnings("unused")
	public static void escribirFechaAArchivo(String fecha) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			final String KEY_FILE = "init.key";
			final String FTP_FILE = "ftp.properties";
			final Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					FTP_FILE), "UTF-8");
			props.load(in);
			String sLocalDirectorio = props.getProperty("local.directorio")
					.trim();
			fichero = new FileWriter(sLocalDirectorio + "\\archivo.bat");
			in.close();
			pw = new PrintWriter(fichero);
			pw.print(fecha);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * @param strCodigoVendedor
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean validoDescargaCorrecta(String strCodigoVendedor) {
		boolean descargoCorrectamente = false;
		final DLocker bloqueador = new DLocker();
		try {
			final String KEY_FILE = "init.key";
			final String FTP_FILE = "ftp.properties";
			final Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(FTP_FILE), "UTF-8");
			props.load(in);
			FTPClient client = new FTPClient();
			client.setDataTimeout(20000);
			String sFTP = props.getProperty("ftp.server").trim();
			String sUser = CryptoUtils.decrypt(props.getProperty("ftp.user").trim(), new File(KEY_FILE));
			String sPassword = CryptoUtils.decrypt(props.getProperty("ftp.password").trim(),new File(KEY_FILE));
			String sLocalDirectorio = props.getProperty("local.directorio").trim();
			String nombreTemporal = sLocalDirectorio + "\\proffline_" + strCodigoVendedor;
			String sFTPDirectorio = props.getProperty("ftp.directorio").trim();
			String sFtPDirectorioVendedor = sFTPDirectorio + "_" + strCodigoVendedor;
			int reply = 0;
			try {
				client.connect(sFTP);
				boolean login = client.login(sUser, sPassword);
				if (login) {
					reply = client.getReplyCode();
					if (FTPReply.isPositiveCompletion(reply)) {
						try {
							File file = new File(nombreTemporal);
							if (file.exists()) {
								file.delete();
							}
							FileOutputStream dfile = new FileOutputStream(file);
							descargoCorrectamente = client.retrieveFile(sFtPDirectorioVendedor, dfile);
							if (!descargoCorrectamente) {
								descargoCorrectamente = client.retrieveFile(sFTPDirectorio, dfile);
								flag = true;
							} else {
								// System.out.println("SINCRONIZANDO BASE VENDEDOR: "
								// + strCodigoVendedor + ".");
							}
							dfile.close();
							if (descargoCorrectamente) {
								// REEMPLAZAMOS
								File archivoDescargado = new File(nombreTemporal);
								File nuevaBaseDatos = new File(sLocalDirectorio + "\\proffline");
								// ----- Validacion de Tamaño de Archivo Descargado -----------
								if (archivoDescargado.length() > 0) {
									if (nuevaBaseDatos.exists()) {
										nuevaBaseDatos.delete();
									}
									boolean seRenombroArchivoCorrectamente = archivoDescargado.renameTo(nuevaBaseDatos);
									Util.ActualizarNumeroRegistros();
									descargoCorrectamente = true;
								} else {
									descargoCorrectamente = false;
								}
							} else {
								descargoCorrectamente = false;
							}
						} catch (IOException e) {
							e.printStackTrace();
							descargoCorrectamente = false;
							return descargoCorrectamente;
						}
					} else {
						client.disconnect();
						descargoCorrectamente = false;
					}
				} else {
					descargoCorrectamente = false;
				}
				client.logout();
				client.disconnect();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				descargoCorrectamente = false;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			descargoCorrectamente = false;
		}
		return descargoCorrectamente;
	}

	public static String convierteMilisegundosAFormatoMMSS(long milisegundos) {
		int minutos = 0;
		long segundos = milisegundos / 1000l;
		while (segundos >= 60) {
			segundos -= 60;
			minutos++;
		}
		return formateaCeroALaIzquierda(minutos) + ":"
				+ formateaCeroALaIzquierda(segundos);
	}

	public static String formateaCeroALaIzquierda(int numero) {
		if (numero >= 0 && numero < 10) {
			return "0" + numero;
		}
		return numero + "";
	}

	public static String formateaCeroALaIzquierda(long numero) {
		if (numero >= 0 && numero < 10) {
			return "0" + numero;
		}
		return numero + "";
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE TRABAJA EN LA TABLA PROFFLINE_TB_SECUENCIA
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL CODIGO DEL
	 * CLIENTE
	 * 
	 * @return RETORNA UN STRING QUE ES EL CODIGO DEL VENDEDOR
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "finally" })
	public static String obtenerCodigoVendedorPorCliente(String codigoCliente) {
		ResultExecuteQuery resultExecuteQuery = null;
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		String codigoVendedor = "";
		HashMap column = new HashMap();
		column.put("String:0", "TXTIDEMPLEADO");
		String sqlcodigoVendedor = "SELECT TXTIDEMPLEADO FROM PROFFLINE_TB_EMPLEADO_CLIENTE"
				+ " WHERE TXTIDCLIENTE = '" + codigoCliente + "';";
		try {
			resultExecuteQuery = new ResultExecuteQuery(sqlcodigoVendedor,
					column, Constante.BD_SYNC);
			mapResultado = resultExecuteQuery.getMap();
			HashMap res = null;
			if (!mapResultado.isEmpty()) {
				res = (HashMap) mapResultado.get(0);
				codigoVendedor = "" + res.get("TXTIDEMPLEADO").toString();
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		} finally {
			return codigoVendedor;
		}
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE OBTIENE LA SECUENCIA DE UN VENDEDOR
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL CODIGO DEL
	 * VENDEDOR
	 * 
	 * @return RETORNA UN STRING QUE ES EL NUMERO DE SECUENCIA PARA EL VENDEDOR
	 * QUE SE ENVIA COMO PARAMETRO
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String obtenerSecuenciaPorVendedor(String codigoVendedor) {
		String id = "0";
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		HashMap column = new HashMap();
		column.put("String:0", "id");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT TXTSECUENCIA as id FROM PROFFLINE_TB_SECUENCIA"
				+ " WHERE TXTIDEMPLEADO = '" + codigoVendedor + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column,
				Constante.BD_SYNC);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				HashMap res = (HashMap) mapResultado.get(0);
				id = res.get("id").toString().trim();
			}
		}
		return id;
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE OBTIENE LA SECUENCIA POR CABECERA DE REGISTRO
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL ID CABECERA
	 * 
	 * @return RETORNA UN STRING QUE ES EL NUMERO DE SECUENCIA SEGUN EL ID DE
	 * CABECERA
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String obtenerSecuenciaPorCabecera(String idCabecera) {
		String id = "0";
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		HashMap column = new HashMap();
		column.put("String:0", "id");
		ResultExecuteQuery resultExecuteQuery = null;
		String sql = "SELECT CABECERASECUENCIA as id FROM PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE"
				+ " WHERE IDCABECERA = '" + idCabecera + "';";
		resultExecuteQuery = new ResultExecuteQuery(sql, column,
				Constante.BD_TX);
		if (resultExecuteQuery != null) {
			mapResultado = resultExecuteQuery.getMap();
			if (mapResultado.size() > 0) {
				HashMap res = (HashMap) mapResultado.get(0);
				id = res.get("id").toString();
			}
		}
		return id;
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE ACTUALIZA UNA SECUENCIA POR VENDEDOR
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL CODIGO DEL
	 * VENDEDOR
	 * 
	 * @param RECIBE COMO SEGUNDO PARAMETRO UN STRING QUE REPRESENTA LA NUEVA
	 * SECUENCIA A ACTUALIZAR
	 */
	public static void actualizarSecuencia(String codigoVendedor, String secuenciaStock) {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "UPDATE PROFFLINE_TB_SECUENCIA SET " + "TXTSECUENCIA = '"
				+ secuenciaStock + "' " + "WHERE TXTIDEMPLEADO = '"
				+ codigoVendedor + "';";
		try {
			stmt = connection.createStatement();
			stmt.execute(sql);
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE INSERTA UNA LA SECUENCIA POR VENDEDOR
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL CODIGO DEL
	 * VENDEDOR
	 * 
	 * @param RECIBE COMO SEGUNDO PARAMETRO UN STRING QUE REPRESENTA LA NUEVA
	 * SECUENCIA A INSERTAR
	 */
	public static void insertarSecuenciaPorVendedor(String codigoVendedor,
			String secuencia) throws Exception {
		ConexionJDBC conn = new ConexionJDBC(Constante.BD_SYNC);
		Connection connection = conn.getConnection();
		Statement stmt = null;
		String sql = "INSERT INTO PROFFLINE_TB_SECUENCIA (txtIdEmpleado, txtSecuencia) VALUES ('"
				+ codigoVendedor + "','" + secuencia + "');";
		stmt = connection.createStatement();
		stmt.execute(sql);
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE VERIFICA SI UN VENDEDOR TIENE UNA SECUENCIA
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL CODIGO DEL
	 * VENDEDOR
	 * 
	 * @return RETORNO VERDADERO SI EL VENDEDOR TIENE UNA SECUENCIA, Y RETORNA
	 * FALSO SI NO TIENE UNA SECUENCIA
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean verificarVendedorEnTablaSecuencia(String codigoVendedor) {
		boolean resultado = false;
		HashMap<Integer, HashMap> mapResultado = new HashMap<Integer, HashMap>();
		HashMap column = new HashMap();
		HashMap res = null;
		ResultExecuteQuery resultExecuteQuery = null;
		String codigo = Constante.VACIO;

		column.put("String:0", "TXTIDEMPLEADO");
		String sql = "SELECT " + "TXTIDEMPLEADO" + " FROM "
				+ "PROFFLINE_TB_SECUENCIA" + " WHERE " + "TXTIDEMPLEADO"
				+ " = '" + codigoVendedor + "'";
		resultExecuteQuery = new ResultExecuteQuery(sql, column,Constante.BD_SYNC);
		mapResultado = resultExecuteQuery.getMap();
		if (mapResultado.size() > 0) {
			res = (HashMap) mapResultado.get(0);
			codigo = res.get("TXTIDEMPLEADO").toString();
		}
		if (!codigo.equals(Constante.VACIO)) {
			resultado = true;
		}
		return resultado;
	}

	/*
	 * @author MARCELO MOYANO
	 * 
	 * METODO QUE GRABA LA SECUENCIA UN VENDEDOR
	 * 
	 * @param RECIBE COMO PARAMETRO UN STRING QUE REPRESENTA EL CODIGO DEL
	 * VENDEDOR
	 * 
	 * @param RECIBE COMO SEGUNDO PARAMETRO UN STRING QUE REPRESENTA LA NUEVA
	 * SECUENCIA A GRABAR
	 */
	public static void grabarSecuenciaPorVendedor(BeanSecuencia bs) {
		try {
			if (Util.verificarVendedorEnTablaSecuencia(bs.getCodigoVendedor())) {
				 String secuenciaActual = Util.obtenerSecuenciaPorVendedor(bs.getCodigoVendedor());
				 if(Integer.parseInt(bs.getSecuenciaCobranza()) > Integer.parseInt(secuenciaActual)){
					 Util.actualizarSecuencia(bs.getCodigoVendedor(), bs.getSecuenciaCobranza());
				 }
				 int secuenciaPedidoActual = Factory.createSqlSecuenciaPedido().getSecuencialPedido(bs.getCodigoVendedor());
				 int secuenciaNueva = Integer.parseInt(bs.getSecuenciaPedido());
				 if(secuenciaNueva > secuenciaPedidoActual){
					 Factory.createSqlSecuenciaPedido().actualizarSecuencialPedido(bs.getCodigoVendedor(), secuenciaNueva);
				 }
			} else {
				Factory.createSqlSecuenciaPedido().insertarSecuenciaPorVendedor(bs);
//				Util.insertarSecuenciaPorVendedor(bs.getCodigoVendedor(), bs.getSecuenciaCobranza());
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	// Rolando Rodríguez
	public static void limpiarBasesDeDatosMalDescargadas() {
		try {
			final String FTP_FILE = "ftp.properties";
			final Properties props = new Properties();
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					FTP_FILE), "UTF-8");
			props.load(in);
			String sLocalDirectorio = props.getProperty("local.directorio")
					.trim();
			File directorio = new File(sLocalDirectorio);
			File[] ficheros = directorio.listFiles();
			if (ficheros != null) {
				for (int i = 0; i < ficheros.length; i++) {
					if (ficheros[i].getName().startsWith("_")
							&& ficheros[i].getName().endsWith("_")) {
						ficheros[i].delete();
					}
				}
			}
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	public static void sincronizarTablasPendientes() {
		List<String[]> msg = new ArrayList<String[]>();
		List<BeanSincronizacion> sincronizaciones;
		SqlSincronizacionImpl objSI = new SqlSincronizacionImpl();
		sincronizaciones = objSI.listaSincronizacionPendientes();
		for (BeanSincronizacion beanSincronizacion : sincronizaciones) {
			String[] mensaje = new String[2];
			int cantidadRegistros = 0;
			Calendar inicioCalendario = Calendar.getInstance();
			com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
			long inicio = inicioCalendario.getTimeInMillis();
			int opcion = 0;
			try {
				opcion = Integer.parseInt(beanSincronizacion.getStrIdeSinc());
				switch (opcion) {
				case 1:
					mensaje[1] = "Sincronización de usuarios";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaUsuario();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO");
					break;
				case 2:
					mensaje[1] = "Sincronización de roles y usuarios";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaRol();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ROL");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO_ROL");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA_ROL");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA");
					break;
				case 3:
					//TODO
					/*mensaje[1] = "Sincronización de agendas";
					Date fechaInicio = new Date();
					fechaInicio.setMonth(fechaInicio.getMonth() - fechaInicio.getMonth());
					fechaInicio.setDate(1);
					BeanBuscarPedido param = new BeanBuscarPedido();
					String codigoVend = Promesa.datose.get(0).getStrCodigo();
					param.setStrVendorId(codigoVend);
					param.setStrDocType("ZP01");
					param.setStrFechaInicio(fechaInicio);
					param.setStrFechaFin(new Date());
					mensaje[0] = SincronizacionPedidos.sincronizarTablaAgenda(param);
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");
					System.out.println("Cantidad Registro: "+cantidadRegistros+" y "+mensaje[0]);
					*/
					break;
				case 4:
					mensaje[1] = "Sincronización Bloqueos de entrega";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaBloqueoEntrega();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BLOQUEO_ENTREGA");
					break;
				case 5:
					mensaje[1] = "Sincronización de clase de materiales";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaClaseMaterial();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLASE_MATERIAL");
					break;
				case 6:
					String codigoVendedor = Promesa.datose.get(0).getStrCodigo();
					mensaje[1] = "Sincronización de clientes";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaEmpleado(codigoVendedor);
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CLIENTE");
					break;
				case 7:
					mensaje[1] = "Sincronización de combos";
					mensaje[0] = SincronizacionPedidos.sincronizaTablaCombo();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_COMBO");
					break;
				case 8:
					mensaje[1] = "Sincronización de condiciones comerciales";
					mensaje[0] = SincronizacionPedidos
							.sincronizarCondicionesComerciales();
					int cant1 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_1X");
					int cant2 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_2X");
					int cant3 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_3X");
					int cant4 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_4X");
					int cant5 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_5X");
					int cant6 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_ESCALAS");
					int cant7 = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICION_ZPR1");
					cantidadRegistros = cant1 + cant2 + cant3 + cant4 + cant5
							+ cant6 + cant7;
					break;
				case 9:
					mensaje[1] = "Sincronización de condiciones de pago";
					mensaje[0] = SincronizacionPedidos
							.sincronizarCondicionesPago();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CONDICIONES_PAGO");
					break;
				case 10:
					mensaje[1] = "Sincronización de destinatarios";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaDestinatario();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_SEDE");
					break;
				case 11:
					mensaje[1] = "Sincronización de dispositivos";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaDispositivo();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_DISPOSITIVO");
					break;
				case 12:
					break;
				case 13:
					mensaje[1] = "Sincronización de feriados";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaFeriado();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_FERIADO");
					break;
				case 14:
					mensaje[1] = "Sincronización de jerarquías";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaJerarquia();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_JERARQUIA");
					break;
				case 15:
					mensaje[1] = "Sincronización de materiales";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaMaterial();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL");
					break;
				case 16:
					mensaje[1] = "Sincronización de stock materiales";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaMaterialStock();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_STOCK");
					break;
				case 17:
					mensaje[1] = "Sincronización de tipologías";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaTipologia();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_TIPOLOGIA");
					break;
				case 18:
					mensaje[1] = "Sincronización de consulta dinámica";
					mensaje[0] = SincronizacionPedidos.sincronizaMaterialConsultaDinamica();
					SqlMaterial sqlMaterial = new SqlMaterialImpl();
					cantidadRegistros = (sqlMaterial.getCountRowPromoOferta()
							+ sqlMaterial.getCountRowDsctoPolitica() 
							+ sqlMaterial.getCountRowDsctoManual() 
							+ sqlMaterial.getCountRowTopCliente() 
							+ sqlMaterial.getCountRowTopTipologia());
					break;
				case 19:
					mensaje[1] = "Sincronización de banco cliente";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaBancoCliente();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_BANCO_CLIENTE");
					break;
				case 20:
					mensaje[1] = "Sincronización de hoja maestra crédito";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaHojaMaestraCredito();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_CABECERA_HOJA_MAESTRA_CREDITO");
					cantidadRegistros += sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO");
					cantidadRegistros += sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_HISTORIAL_PAGO");
					cantidadRegistros += sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_VALOR_POR_VENCER");
					cantidadRegistros += sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_NOTA_CREDITO");
					cantidadRegistros += sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_PROTESTO");
					break;
				case 21:
					mensaje[1] = "Sincronización de partida individual";
					mensaje[0] = SincronizacionPedidos
							.sincronizarTablaPartidaIndividual();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL");
					break;
				case 23:
					mensaje[1] = "Sincronización de formas de pago de cobranzas";
					mensaje[0] = SincronizacionPedidos
							.sincronizarFormaPagoCobranza();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_FORMA_PAGO_COBRANZA");
					break;
				case 24:
					mensaje[1] = "Sincronización de formas de pago de anticipos";
					mensaje[0] = SincronizacionPedidos
							.sincronizarFormaPagoAnticipo();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_FORMA_PAGO_ANTICIPO");
					break;
				case 25:
					mensaje[1] = "Sincronización de Bancos Promesa";
					mensaje[0] = SincronizacionPedidos
							.sincronizarBancoPromesa();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_BANCO_PROMESA");
					break;
				case 26:
					mensaje[1] = "Cobranzas fuera de linea";
					mensaje[0] = SincronizacionPedidos
							.sincronizaPartidasIndividualesAbiertas();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA");
					cantidadRegistros += sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA");
					break;
				case 27://Secuencia sincronización 03/04/2013 - 14:38
					codigoVendedor = Promesa.datose.get(0).getStrCodigo();
					mensaje[1] = "Secuencial Vendedor";
					mensaje[0] = SincronizacionPedidos.sincronizaSecuenciaPorVendedor();
					cantidadRegistros = sqlSincronizacionImpl
							.filasTabla("PROFFLINE_TB_SECUENCIA");
					break;
				case 28://SINCRONIZACION DE TABLA
					codigoVendedor = Promesa.datose.get(0).getStrCodigo();
					mensaje[1] = "Parametro Constantes";
					mensaje[0] = SincronizacionPedidos.sincronizaParametrosConstante();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ZTCONSTANTE");
					break;
				case 29:
					int cob = sqlSincronizacionImpl.cobranzaEliminados();
					if(cob > 0){
//						mensaje[1] = "Cobranzas OFFLINE Eliminadas: " + cob;
						SincronizacionPedidos.sincronizacionCobranzasOffLineEliminado();
						cantidadRegistros = sqlSincronizacionImpl.cobranzaEliminados();//Poner la cantidad de Cobranzas se ha enviado a SAP(Eliminado en DB)
					}
					break;
				case 30:
					int ant = sqlSincronizacionImpl.anticiposEliminados();
					if(ant >0 ){
//						mensaje[1] = "Anticipo OFFLINE Eliminadas: " + ant;
						SincronizacionPedidos.sincronizacionAnticiposOffLineEliminado();
						cantidadRegistros = sqlSincronizacionImpl.anticiposEliminados();//Poner la cantidad de Anticipo se ha enviado a SAP(Eliminado en DB)
					}
					break;
				case 31:
					mensaje[1] = "Presupuesto Cliente";
					mensaje[0] = SincronizacionPedidos.sincronizacionPresupuesto();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PRESUPUESTO");
					break;
				case 32:
					mensaje[1] = "Marca Estrategicas e Indicadores";
					mensaje[0] = SincronizacionPedidos.sincronizacionMarcaEstrategica();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MARCA_ESTRATEGICA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_INDICADORES") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_NOMBRE_MARCA_ESTRATEGICA")+ sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MARCA_VENDEDOR");;
					break;
				case 33:
					mensaje[1] = "Materiales Nuevos";
					mensaje[0] = SincronizacionPedidos.sincronizarTablaMaterialNuevo();
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_NUEVO");
					break;
				case 34:
					codigoVendedor = Promesa.datose.get(0).getStrCodigo();
					mensaje[1] = "Venta Cruzada";
					mensaje[0] = SincronizacionPedidos.sincronizarVentaCruzada(codigoVendedor);
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_VENTA_CRUZADA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VENTA_CRUZADA");
					break;
				case 35:
					codigoVendedor = Promesa.datose.get(0).getStrCodigo();
					mensaje[1] = "Mercadeo";
					mensaje[0] = SincronizacionPedidos.sincronizarMercadeo(codigoVendedor);
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_MERCADEO") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_PROMOCION");
					break;
				default:
					mensaje[1] = "No se ha seleccionado ninguna tabla";
					mensaje[0] = "E";
					cantidadRegistros = 0;
					break;
				}
				if (mensaje[0] != null && mensaje[1] != null) {
					msg.add(mensaje);
				}

				Calendar finCalendario = Calendar.getInstance();
				long fin = finCalendario.getTimeInMillis();
				String strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
				com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
				beanSincronizar.setStrIdeSinc(beanSincronizacion.getStrIdeSinc());
				beanSincronizar.setStrTie(strTie);
				beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
				beanSincronizar.setStrCantReg(cantidadRegistros + "");
				SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
				sqlSincronizacion.actualizarSincronizar(beanSincronizar);
			} catch (Exception e) {
				opcion = 0;
			}
		}
		if (!msg.isEmpty() && isCobranza(msg)) {
			final List<String[]> mensajes = new ArrayList<String[]>(msg);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					DialogoConfirmacionSincronizacion dlg = new DialogoConfirmacionSincronizacion(
							Promesa.getInstance(), true, mensajes,
							"Confirmación de pendientes");
					dlg.setVisible(true);
				}
			});
		}
	}
	
	public static boolean isCobranza(List<String[]> msg) {
		for (String[] str : msg) {
			if((str[1].equalsIgnoreCase("Cobranzas OFFLINE") || str[1].equalsIgnoreCase("Anticipos OFFLINE")) && str[0].equalsIgnoreCase("E")){
				return false;
			}
				
		}
		return true;
	}

	public static void ActualizarNumeroRegistros() {
		List<BeanSincronizacion> sincronizaciones;
		SqlSincronizacionImpl objSI = new SqlSincronizacionImpl();
		sincronizaciones = objSI.listaSincronizacionPendientes();
		for (BeanSincronizacion beanSincronizacion : sincronizaciones) {
			int cantidadRegistros = 0;
			Calendar inicioCalendario = Calendar.getInstance();
			com.promesa.planificacion.sql.impl.SqlSincronizacionImpl sqlSincronizacionImpl = new com.promesa.planificacion.sql.impl.SqlSincronizacionImpl();
			long inicio = inicioCalendario.getTimeInMillis();
			int opcion = 0;
			try {
				opcion = Integer.parseInt(beanSincronizacion.getStrIdeSinc());
				switch (opcion) {
				case 1:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO");
					break;
				case 2:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ROL");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_USUARIO_ROL");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA_ROL");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VISTA");
					break;
				case 3:
					//cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_AGENDA");
					break;
				case 4:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BLOQUEO_ENTREGA");
					break;
				case 5:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLASE_MATERIAL");
					break;
				case 6:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CLIENTE");
					break;
				case 7:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_COMBO");
					break;
				case 8:
					int cant1 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_1X");
					int cant2 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_2X");
					int cant3 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_3X");
					int cant4 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_4X");
					int cant5 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_5X");
					int cant6 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_ESCALAS");
					int cant7 = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICION_ZPR1");
					cantidadRegistros = cant1 + cant2 + cant3 + cant4 + cant5 + cant6 + cant7;
					break;
				case 9:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CONDICIONES_PAGO");
					break;
				case 10:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SEDE");
					break;
				case 11:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DISPOSITIVO");
					break;
				case 12:
					break;
				case 13:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FERIADO");
					break;
				case 14:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_JERARQUIA");
					break;
				case 15:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL");
					break;
				case 16:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_STOCK");
					break;
				case 17:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_TIPOLOGIA");
					break;
				case 18:
					SqlMaterial sqlMaterial = new SqlMaterialImpl();
					cantidadRegistros = (sqlMaterial.getCountRowPromoOferta()
							+ sqlMaterial.getCountRowDsctoPolitica() 
							+ sqlMaterial.getCountRowDsctoManual() 
							+ sqlMaterial.getCountRowTopCliente() 
							+ sqlMaterial.getCountRowTopTipologia());//Agregar Tabla Top por cliente y top por tipologia
					break;
				case 19:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BANCO_CLIENTE");
					break;
				case 20:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_CABECERA_HOJA_MAESTRA_CREDITO");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DIA_DEMORA_TRAS_VENCIMIENTO");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_HISTORIAL_PAGO");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VALOR_POR_VENCER");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_NOTA_CREDITO");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PROTESTO");
					break;
				case 21:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL");
					break;
				case 23:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FORMA_PAGO_COBRANZA");
					break;
				case 24:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_FORMA_PAGO_ANTICIPO");
					break;
				case 25:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_BANCO_PROMESA");
					break;
				case 26:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PARTIDA_INDIVIDUAL_ABIERTA");
					cantidadRegistros += sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_DETALLE_PAGO_PARTIDA_INDIVIDUAL_ABIERTA");
					break;
				case 27:// mARCELO mOYANO Secuencia sincronización 03/04/2013 -
						// 14:38
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_SECUENCIA");
					break;
				case 28:// mARCELO mOYANO Secuencia sincronización 03/04/2013 -
						// 14:38;
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_ZTCONSTANTE");
					break;
				case 29:// mARCELO mOYANO Secuencia sincronización 03/04/2013 - 14:38;
					cantidadRegistros = sqlSincronizacionImpl.cobranzaEliminados();
					break;
				case 30:
					cantidadRegistros = sqlSincronizacionImpl.anticiposEliminados();//Poner la cantidad de Anticipo se ha enviado a SAP(Eliminado en DB)
					break;
				case 31:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_PRESUPUESTO");
					break;
				case 33:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_NUEVO");
					break;
				case 34:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_VENTA_CRUZADA") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_VENTA_CRUZADA");
					break;
				case 35:
					cantidadRegistros = sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_MERCADEO") + sqlSincronizacionImpl.filasTabla("PROFFLINE_TB_MATERIAL_PROMOCION");
					break;
				default:
					cantidadRegistros = 0;
					break;
				}
				Calendar finCalendario = Calendar.getInstance();
				long fin = finCalendario.getTimeInMillis();
				String strTie = Util.convierteMilisegundosAFormatoMMSS(fin - inicio);
				com.promesa.administracion.bean.BeanSincronizar beanSincronizar = new com.promesa.administracion.bean.BeanSincronizar();
				beanSincronizar.setStrIdeSinc(beanSincronizacion.getStrIdeSinc());
				beanSincronizar.setStrTie(strTie);
				beanSincronizar.setStrFecHor(Util.convierteFechaHoyAFormatoDDMMYYYYHHMMSSAA(new Date()));
				beanSincronizar.setStrCantReg(cantidadRegistros + "");
				SqlSincronizacionImpl sqlSincronizacion = new SqlSincronizacionImpl();
				sqlSincronizacion.actualizarSincronizar(beanSincronizar);
			} catch (Exception e) {
				opcion = 0;
			}
		}
	}

	public static String verificarImpresora() {
		String strDispositivoImpresora;
		SqlDispositivoImpl sqlImpresora = new SqlDispositivoImpl();
		strDispositivoImpresora = sqlImpresora.verificarImpresora(
				Promesa.datose.get(0).getStrNU(), "IMPRESORA");
		return strDispositivoImpresora;
	}

	public static void CargarConsultaDinamica() throws IOException {
		// TODO Auto-generated method stub
		/*
		 * Cargando Jerarquia
		 */
		Indexador indexDivision = new Indexador(Constante.PATH + "Division");
		indexDivision.reconstruirIndexJerarquia("1");
		indexDivision.closeIndexador();

		Indexador indexCategoria = new Indexador(Constante.PATH + "Categoria");
		indexCategoria.reconstruirIndexJerarquia("2");
		indexCategoria.closeIndexador();

		Indexador indexFamilia = new Indexador(Constante.PATH + "Familia");
		indexFamilia.reconstruirIndexJerarquia("3");
		indexFamilia.closeIndexador();

		Indexador indexLinea = new Indexador(Constante.PATH + "Linea");
		indexLinea.reconstruirIndexJerarquia("4");
		indexLinea.closeIndexador();

		Indexador indexGrupo = new Indexador(Constante.PATH + "Grupo");
		indexGrupo.reconstruirIndexJerarquia("5");
		indexGrupo.closeIndexador();

		/*
		 * Cargando Materiales
		 */
		Indexador indexMaterial = new Indexador(Constante.PATH
				+ Constante.MATERIAL);
		indexMaterial.reconstruirIndexMaterial();
		indexMaterial.closeIndexador();

		Indexador indexPromoOferta = new Indexador(Constante.PATH
				+ Constante.PROMOOFERTA);
		indexPromoOferta.reconstruirPromoOferta();
		indexPromoOferta.closeIndexador();

		Indexador indexDescPolitica = new Indexador(Constante.PATH
				+ Constante.DESCUENTOPOLITICA);
		indexDescPolitica.reconstruirDescuentoPolitica();
		indexDescPolitica.closeIndexador();

		Indexador indexDescManual = new Indexador(Constante.PATH
				+ Constante.DESCUENTOMANUAL);
		indexDescManual.reconstruirDescuentoPolitica();
		indexDescManual.closeIndexador();
	}

	public static List<String> generarLineas(String comentario,
			int cantidadCaracteres) {
		String[] arrayPalabras = comentario.trim().split(" ");
		List<String> lstPalabras = new ArrayList<String>();
		for (int i = 0; i < arrayPalabras.length; i++) {
			lstPalabras.add(arrayPalabras[i]);
		}
		List<String> lstLineas = new ArrayList<String>();
		generarListaPalabraPorLinea(lstPalabras, cantidadCaracteres, lstLineas);
		return lstLineas;
	}

	public static void generarListaPalabraPorLinea(List<String> lstPalabras,
			int cantidadCaracteres, List<String> lstLineas) {
		List<String> lstPalabraRestante = new ArrayList<String>();
		int contador = 0;
		String linea = "";
		for (int i = 0; i < lstPalabras.size(); i++) {
			String palabra = lstPalabras.get(i);
			if (palabra.trim().length() + contador + 1 < cantidadCaracteres) {
				linea = linea.concat(palabra.trim()).concat(" ");
				contador = linea.length();
			} else {
				for (int j = i; j < lstPalabras.size(); j++) {
					lstPalabraRestante.add(lstPalabras.get(j));
				}
				break;
			}
		}
		lstLineas.add(linea);
		if (lstPalabraRestante.size() > 0) {
			generarListaPalabraPorLinea(lstPalabraRestante, cantidadCaracteres,
					lstLineas);
		}
	}

	public static int sumarDigitos(String strCadena) {
		int suma = 0;
		for (int i = 0; i < strCadena.length(); i++) {
			suma += Integer.parseInt("" + strCadena.charAt(i));
		}
		return suma;
	}
	
	public static List<String[]> sincronizarAnticiposASAP(String codigoVendedor){
		Promesa.getInstance().mostrarAvisoSincronizacion("SINCRONIZANDO ANTICIPOS.");
		SqlAnticipoClienteImpl sqlAnticipoClienteImpl =  new SqlAnticipoClienteImpl();
		List<AnticipoCliente> listaAnticipoClientes = new ArrayList<AnticipoCliente>();
		SCobranzas sCobranzas = new SCobranzas();
		listaAnticipoClientes = sqlAnticipoClienteImpl.obtenerListaAnticipoCliente(codigoVendedor);
		if(listaAnticipoClientes != null && listaAnticipoClientes.size() > 0){
			List<String[]> responseAnticipoClientes = sCobranzas.sincronizarRegistroAnticipo(listaAnticipoClientes);
			if(responseAnticipoClientes != null && responseAnticipoClientes.size() > 0){
				int i = 0;
				for(String[] str : responseAnticipoClientes){
					if(str[1].equals("S")) {
						listaAnticipoClientes.get(i).setSincronizado("1");
					}
					i++;
				}
				for(AnticipoCliente anticipoCliente : listaAnticipoClientes){
					try {
						sqlAnticipoClienteImpl.actualizarAnticipoCliente(anticipoCliente);
						sqlAnticipoClienteImpl.eliminarAnticipoCliente(anticipoCliente);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			Promesa.getInstance().mostrarAvisoSincronizacion("");
			return responseAnticipoClientes;
		} else 
			return null;
	}
	
	public static List<String[]> sincronizarPagosCliente(){
		Promesa.getInstance().mostrarAvisoSincronizacion("SINCRONIZANDO REGISTRO PAGOS DE CLIENTES.");
		SqlPagoRecibidoImpl sqlPagoRecibidoImpl = new SqlPagoRecibidoImpl();
		SqlPagoParcialImpl sqlPagoParcialImpl = new SqlPagoParcialImpl();
		SqlCabeceraRegistroPagoClienteImpl sqlCabeceraRegistroPago = new SqlCabeceraRegistroPagoClienteImpl();
		List<CabeceraRegistroPagoCliente> listaCabeceraPagos = sqlCabeceraRegistroPago.getPagoClienteSinSincronizar();
		List<RegistroPagoCliente> listaRegistroPago = new ArrayList<RegistroPagoCliente>();
		for(CabeceraRegistroPagoCliente cabecera : listaCabeceraPagos) {
			Long idCabecera = Long.valueOf(cabecera.getIdCabecera());
			List<PagoRecibido> listPagosRecibidos = sqlPagoRecibidoImpl.obtenerListaPagoRecibido( idCabecera);
			List<PagoParcial> listaPagosParciales = sqlPagoParcialImpl.obtenerListaPagoParcial(idCabecera);
			List<PagoParcial> listaPagosParcialesActivos = new ArrayList<PagoParcial>();
			for (PagoParcial pagoParcial : listaPagosParciales) {
				if (!pagoParcial.isHijo() && pagoParcial.getImportePago() > 0d && pagoParcial.isActivo()) {
					listaPagosParcialesActivos.add(pagoParcial);
				}
			}
			RegistroPagoCliente registroPagoCliente = new RegistroPagoCliente();
			registroPagoCliente.setCabeceraRegistroPagoCliente(cabecera);
			registroPagoCliente.setListaPagoRecibido(listPagosRecibidos);
			registroPagoCliente.setListaPagoParcial(listaPagosParcialesActivos);
			listaRegistroPago.add(registroPagoCliente);
		}
		List<String[]> listaResponse = new ArrayList<String[]>();
		SCobranzas sCobranzas = new SCobranzas();
		for(RegistroPagoCliente registroPagoCliente : listaRegistroPago){
			Object arregloRegistroPagoCliente[] = sCobranzas.grabarRegistroPagoCliente(registroPagoCliente.getListaPagoRecibido(), registroPagoCliente.getListaPagoParcial());
			if (arregloRegistroPagoCliente != null) {
				@SuppressWarnings("unchecked")
				List<String> lstMensaje = (List<String>) arregloRegistroPagoCliente[1];
				for (String mensaje : lstMensaje) {
					String[] valores = String.valueOf(mensaje).split("[¬]");
					if (valores[1].trim().equals("S")) {
						registroPagoCliente.getCabeceraRegistroPagoCliente().setSincronizado("1");
					}
					listaResponse.add(valores);
				}
			}
		}
		for(RegistroPagoCliente registroPagoCliente : listaRegistroPago){
			try {
				sqlCabeceraRegistroPago.actualizarCabeceraRegistroPagoClienteSincronizado(registroPagoCliente.getCabeceraRegistroPagoCliente());
				
				sqlPagoRecibidoImpl.eliminarListaPagoRecibido(registroPagoCliente.getCabeceraRegistroPagoCliente().getIdCabecera());
				sqlPagoParcialImpl.eliminarListaPagoParcial(registroPagoCliente.getCabeceraRegistroPagoCliente().getIdCabecera());
				sqlCabeceraRegistroPago.eliminarCabeceraRegistroPagoCliente(registroPagoCliente.getCabeceraRegistroPagoCliente());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Promesa.getInstance().mostrarAvisoSincronizacion("");
		return listaResponse;
	}
	
	public static StringBuilder SincronizarCreditoAnticiposASAP(String codigoVendedor){
		StringBuilder mensajeConfirmacion = new StringBuilder();
		StringBuilder mensajeError = new StringBuilder();
		StringBuilder mensajeResponse = new StringBuilder();
		List<String[]> responsesAnticipos = Util.sincronizarAnticiposASAP(codigoVendedor);
		List<String[]> responsesRegistroPagos =Util.sincronizarPagosCliente();
		
		SqlAnticipoClienteImpl sqlAnticipoClienteImpl =  new SqlAnticipoClienteImpl();
		
		SqlCabeceraRegistroPagoClienteImpl sql = new SqlCabeceraRegistroPagoClienteImpl();
		
		String mensajeHTMLConfirmacion = "";
		String mensajeHTLMLError = "";
		int errorAnticipos = 0;
		int errorCobranza = 0;
		if(responsesAnticipos != null && responsesAnticipos.size() > 0){
			mensajeHTMLConfirmacion = "<html><table>";
			mensajeHTLMLError = "<html><table>";
			for(String respuesta[] : responsesAnticipos){
				if (respuesta[1].trim().equals("S")) {
					mensajeHTMLConfirmacion += "<tr><td>" + respuesta[2].trim() + "</td></tr>";
				} else {
					errorAnticipos++;
				}
			}
			mensajeConfirmacion.append(mensajeHTMLConfirmacion + "\n");
			if(errorAnticipos > 0){
				mensajeHTLMLError += Constante.MENSAJE_ERROR_ANTICIPO;
				List<AnticipoCliente> listaAnticiposError = sqlAnticipoClienteImpl.obtenerAnticiposClientesSinSincronizar();
				if(listaAnticiposError != null && listaAnticiposError.size() > 0){
					for(AnticipoCliente ac : listaAnticiposError){
						mensajeHTLMLError += "<tr><td><font color='red'>Secuencia: " + ac.getAnticipoSecuencia()+ " - Cliente " + ac.getCodigoCliente()+ " - Importe "+ac.getImporte() +"</font></td></tr>";
					}
					mensajeError.append(mensajeHTLMLError + "\n");
				}
			}
		}
		if(responsesRegistroPagos != null && responsesRegistroPagos.size() > 0){
			if(mensajeHTMLConfirmacion.equals("")){
				mensajeHTMLConfirmacion = "<html><table>";
			}
			if(mensajeHTLMLError.equals("")){
				mensajeHTLMLError = "<html><table>";
			}
			for(String respuesta[] : responsesRegistroPagos){
				if (respuesta[1].trim().equals("S")) {
					mensajeHTMLConfirmacion += "<tr><td>" + respuesta[3].trim() + "</td></tr>";
				} else {
					errorCobranza++;
				}
			}
			mensajeConfirmacion.append(mensajeHTMLConfirmacion + "\n");
//			mensajeError.append(mensajeHTLMLError + "\n");
		}
		if(mensajeConfirmacion.length() > 0){
			mensajeResponse.append(mensajeHTMLConfirmacion);
		}
		
		if(errorCobranza> 0){
			mensajeHTLMLError += Constante.MENSAJE_ERROR_COBRANZA;
			List<CabeceraRegistroPagoCliente> ListaErrorCabecera = sql.getPagoClienteSinSincronizar();
			if(ListaErrorCabecera != null && ListaErrorCabecera.size() > 0){
				for(CabeceraRegistroPagoCliente cabecera : ListaErrorCabecera){
					mensajeHTLMLError += "<tr><td><font color='red'>Secuencia: " + cabecera.getCabeceraSecuencia()+ " - Cliente: " + cabecera.getCodigoCliente() +" - Importe "+cabecera.getImporteAsignado()+ "</font></td></tr>";
				}
				mensajeError.append(mensajeHTLMLError + "\n");
			}
		}
		mensajeResponse.append(mensajeError);
		return mensajeResponse;
	}
	
	public static boolean esPedidoZPXX(String clasePedido){
		return Pattern.matches(Constante.EXP_REG_CLASE_PEDIDO_ZPXX,clasePedido);
	}
	
	public static int getMesesFaltante(){
		
		Date fecha = new Date();
		int mesfaltante = 0;
		switch (fecha.getMonth()) {
		case 0://Enero
			mesfaltante = 0;
			break;
		case 1://FEBRERO
			mesfaltante = 11;
			break;
		case 2://MARZO
			mesfaltante = 10;
			break;
		case 3://ABRIL
			mesfaltante = 9;
			break;
		case 4://MAYO
			mesfaltante = 8;
			break;
		case 5://JUNIO
			mesfaltante = 7;
			break;
		case 6://JULIO
			mesfaltante = 6;
			break;
		case 7://AGOSTO
			mesfaltante = 5;
			break;
		case 8://SEPTIEMBRE
			mesfaltante = 4;
			break;
		case 9://OCTUBRE
			mesfaltante = 3;
			break;
		case 10://NOVIEMBRE
			mesfaltante = 2;
			break;
		case 11://DICIEMBRE
			mesfaltante = 1;
			break;
		}
		
		return mesfaltante;
	}
	
	public static String getCodigoVendedor(){
		return Promesa.getInstance().datose.get(0).getStrCodigo();
	}
	
	public static void quickSort(List<MarcaEstrategica> marcas, int low, int high){
		if (marcas == null || marcas.size() == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		double pivot = Double.parseDouble(marcas.get(middle).getPresupuesto());
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (Double.parseDouble(marcas.get(i).getPresupuesto()) > pivot) {
				i++;
			}
			while (Double.parseDouble(marcas.get(j).getPresupuesto()) < pivot) {
				j--;
			}
 
			if (i <= j) {
				MarcaEstrategica tmp = marcas.get(i);
				marcas.set(i, marcas.get(j));
				marcas.set(j, tmp);
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(marcas, low, j);
 
		if (high > i)
			quickSort(marcas, i, high);
		}
	
	public static String getTrimestre(){
		String anioActual = getAnioActual();
		if(esPrimerTrimestre()){
			return "(Ene - Mar/"+anioActual + ")";
		} else if(esSegundoTrimestre()){
			return "(Abr - Jun/"+anioActual + ")";
		} else if(esTercerTrimestre()){
			return "(Jul - Sep/"+anioActual + ")";
		} else {
			return "(Oct - Dic/"+anioActual + ")";
		}
	}
	
	private static boolean esPrimerTrimestre(){
		if(getMesActual().equalsIgnoreCase("01") || getMesActual().equalsIgnoreCase("02") || getMesActual().equalsIgnoreCase("03")){
			return true;
		}
		return false;
	}
	private static boolean esSegundoTrimestre(){
		if(getMesActual().equalsIgnoreCase("04") || getMesActual().equalsIgnoreCase("05") || getMesActual().equalsIgnoreCase("06")){
			return true;
		}
		return false;
	}
	private static boolean esTercerTrimestre(){
		if(getMesActual().equalsIgnoreCase("07") || getMesActual().equalsIgnoreCase("08") || getMesActual().equalsIgnoreCase("09")){
			return true;
		}
		return false;
	}
	
	public static boolean fechaMaximoTresMeses(Date fecha){
		Calendar fechaActual = Calendar.getInstance();
		//fechaActual.add(Calendar.DAY_OF_MONTH, -fechaActual.get(Calendar.DAY_OF_MONTH) +1);
		fechaActual.add(Calendar.MONTH, -3);
		
//		System.out.println("fecha Sistema: " + Util.convierteFechaAFormatoDDMMMYYYY(fechaActual.getTime()));
//		System.out.println("fecha ingreso: " + Util.convierteFechaAFormatoDDMMMYYYY(fecha));
		return comparaFecha1MenorOIgualQueFecha2(fechaActual.getTime(), fecha);
	}
	public static String fechaTresMesesAntes(Date fecha){
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.MONTH, -3);
		
//		System.out.println("fecha Sistema: " + Util.convierteFechaAFormatoDDMMMYYYY(fechaActual.getTime()));
//		System.out.println("fecha ingreso: " + Util.convierteFechaAFormatoDDMMMYYYY(fecha));
		return Util.convierteFechaAFormatoDDMMMYYYY(fechaActual.getTime());
	}
	public static boolean esformatoFechaCorrecto(String textoFecha){
		Pattern pat = Pattern.compile("(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012]).[1-9]0[0-9][0-9]");
	     Matcher mat = pat.matcher(textoFecha);
	     return mat.matches();
	}
	
	
	public static void main(String args[]){
//		System.out.println("MES ACTUAL: " + getMesActual());
//		System.out.println("ANIO ACTUAL: " + getAnioActual());
		
		System.out.println("Fecha: " + fechaMaximoTresMeses(new Date("03/03/2017")));
		System.out.println("Validar: " + comparaFecha1MenorOIgualQueFecha2(new Date("03/01/2017"),new Date("03/03/2017")));
	}

}