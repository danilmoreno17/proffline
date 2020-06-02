package com.promesa.impresiones;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.cobranzas.bean.PagoRecibido;
import com.promesa.impresiones.cobranzas.DetalleTicketPago;
import com.promesa.impresiones.cobranzas.TicketAnticipo;
import com.promesa.impresiones.cobranzas.TicketPago;
import com.promesa.impresiones.cobranzas.TicketPagoOffline;
import com.promesa.impresiones.devoluciones.DetalleTicketDevolucion;
import com.promesa.impresiones.devoluciones.TicketDevolucion;
import com.promesa.impresiones.pedidos.DetalleTicketOrden;
import com.promesa.impresiones.pedidos.TicketOrden;
import com.promesa.main.Promesa;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class ReporteTicket {

	private boolean margenes = false;
	private static String[][] FONTS = {
			{ BaseFont.HELVETICA, BaseFont.WINANSI },
			{ "resources/fonts/cmr10.afm", BaseFont.WINANSI },
			{ "resources/fonts/cmr10.pfm", BaseFont.WINANSI },
			{ "c:/windows/fonts/ARBLI__.TTF", BaseFont.WINANSI },
			{ "c:/windows/fonts/arial.ttf", BaseFont.WINANSI },
			{ "c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H },
			{ "resources/fonts/Puritan2.otf", BaseFont.WINANSI },
			{ "c:/windows/fonts/msgothic.ttc,0", BaseFont.IDENTITY_H },
			{ "KozMinPro-Regular", "UniJIS-UCS2-H" } };

	private Font fuenteTitulo;
	private Font fuenteTextos;
	private Font fuenteSubrayada;
	private Font fuenteLabels;
	private Font fuenteCombos;
	private TicketOrden ticket;
	private TicketAnticipo ticketAnticipo;
	private TicketPagoOffline ticketPagoOffline;
	private List<TicketPago> listaTicketPago;
	private TicketDevolucion ticketDevolucion;
	private boolean estaSincronizado;

	public ReporteTicket(TicketOrden ticket, boolean estaSincronizado) {
		this.ticket = ticket;
		this.estaSincronizado = estaSincronizado;
		inicializarFuentes();
	}

	public ReporteTicket(TicketAnticipo ticketAnticipo) {
		this.ticketAnticipo = ticketAnticipo;
		inicializarFuentes();
	}

	public ReporteTicket(TicketPagoOffline ticketPagoOffline) {
		this.ticketPagoOffline = ticketPagoOffline;
		inicializarFuentes();
	}

	public ReporteTicket(List<TicketPago> listaTicketPago) {
		this.listaTicketPago = listaTicketPago;
		inicializarFuentes();
	}

	public ReporteTicket(TicketDevolucion ticketDevolucion) {
		this.ticketDevolucion = ticketDevolucion;
		inicializarFuentes();
	}

	private void inicializarFuentes() {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(FONTS[4][0], FONTS[4][1], BaseFont.EMBEDDED);
			fuenteTitulo = new Font(bf, 7.3f, Font.BOLD);
			fuenteTextos = new Font(bf, 6f, Font.NORMAL);
			fuenteSubrayada = new Font(bf, 6.3f, Font.UNDERLINE);
			fuenteLabels = new Font(bf, 5.0f, Font.BOLD);
			fuenteCombos = new Font(bf, 6f, Font.NORMAL);
		} catch (Exception e) {
			fuenteTitulo = new Font(Font.FontFamily.COURIER, 7, Font.BOLD);
			fuenteTextos = new Font(Font.FontFamily.COURIER, 7f, Font.NORMAL);
			fuenteSubrayada = new Font(Font.FontFamily.COURIER, 6f, Font.UNDERLINE);
			fuenteLabels = new Font(Font.FontFamily.COURIER, 6f, Font.BOLD);
			fuenteCombos = new Font(Font.FontFamily.COURIER, 6f, Font.NORMAL);
		}
	}

	private void agregarTextoNormal(Document document, String string, Font font, int align) {
		try {
			Chunk espacio = new Chunk(string, font);
			Paragraph p = new Paragraph(espacio);
			p.setAlignment(align);
			document.add(p);
		} catch (DocumentException ex) {
			Util.mostrarExcepcion(ex);
		}
	}

	private void agregarSeparacion(Document document) {
		try {
			Font fuente = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.NORMAL);
			Chunk espacio = new Chunk("\n", fuente);
			document.add(new Paragraph(espacio));
		} catch (DocumentException ex) {
			Util.mostrarExcepcion(ex);
		}
	}

	public static void main(String[] args) {
		TicketDevolucion ticketDevolucion = new TicketDevolucion();
		ticketDevolucion.setDireccion("Las Begonias 345 - San Isidro");
		ticketDevolucion.setTelefono("7751284");
		ticketDevolucion.setCodigoCliente("0800071");
		ticketDevolucion.setNombreCliente("Empresa La Industrial S.A.");
		ticketDevolucion.setNumeroPedido("02929201920");
		ticketDevolucion.setDireccionDestinatario("Amazonas 3432");
		ticketDevolucion.setDescripcionBloqueEntrega("10");
		ticketDevolucion.setNumeroPedidoCliente("110");
		ticketDevolucion.setSello("Sello de Oro");
		ticketDevolucion.setClaseRiesgo("B");
		ticketDevolucion.setDescripcionCondicionPago("NB142");
		ticketDevolucion.setDescripcionMotivoDevolucion("Gwtshbajha");
		ticketDevolucion.setValorNeto("120.32");
		ticketDevolucion.setCabeceraFormulario("Comentario de la cabecera del formulario");
		ticketDevolucion.setNotaCabecera("Comentario de la nota de cabecera");
		List<DetalleTicketDevolucion> listaDetalleTicketDevolucion = new ArrayList<DetalleTicketDevolucion>();
		DetalleTicketDevolucion detalleTicketDevolucion1 = new DetalleTicketDevolucion();
		detalleTicketDevolucion1.setPosicion("00010000");
		detalleTicketDevolucion1.setMaterial("HGD2010101");
		detalleTicketDevolucion1.setCantidad("40");
		detalleTicketDevolucion1.setUm("KG");
		detalleTicketDevolucion1.setDenominacion("Astaro Perchaz");
		detalleTicketDevolucion1.setPrecioNeto("10.20");
		detalleTicketDevolucion1.setValorNeto("100.30");
		listaDetalleTicketDevolucion.add(detalleTicketDevolucion1);
		DetalleTicketDevolucion detalleTicketDevolucion2 = new DetalleTicketDevolucion();
		detalleTicketDevolucion2.setPosicion("00020000");
		detalleTicketDevolucion2.setMaterial("HEW2555101");
		detalleTicketDevolucion2.setCantidad("70");
		detalleTicketDevolucion2.setUm("YH");
		detalleTicketDevolucion2.setDenominacion("Astaro Quilca");
		detalleTicketDevolucion2.setPrecioNeto("15.70");
		detalleTicketDevolucion2.setValorNeto("640.90");
		listaDetalleTicketDevolucion.add(detalleTicketDevolucion2);
		ticketDevolucion.setListaDetalleTicketDevolucion(listaDetalleTicketDevolucion);
		ReporteTicket rt = new ReporteTicket(ticketDevolucion);
		rt.generarReporteDevolucion();
	}

	public void generarReporteDevolucion() {
		try {
			String strNombreArchivo = "ticket_registro_devolucion_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
			Rectangle pagesize = new Rectangle(324f, 290f);
			Document document = new Document(pagesize, 15, 15, 10, 10);
			PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
			document.open();
			generarCuerpoRegistroDevolucion(document, "-      REPORTE DEVOLUCIÓN      -");
			document.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarReporteRegistroPagoOnline() {
		try {
			String strNombreArchivo = "ticket_registro_pago_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
			Rectangle pagesize = new Rectangle(335f, 310f);
			Document document = new Document(pagesize, 15, 15, 15, 15);
			PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
			// ORIGINAL
			document.open();
			generarCuerpoRegistroPagoOnline(document, "-      ORIGINAL      -");
			// -- FIN ORIGINAL
			// COPIA
			agregarSeparacion(document);
			generarCuerpoRegistroPagoOnline(document, "-      COPIA      -");
			// -- FIN COPIA
			document.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarReporteRegistroPagoOffline() {
		try {
			String strNombreArchivo = "ticket_registro_pago_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
			Rectangle pagesize = new Rectangle(324f, 290f);
			Document document = new Document(pagesize, 15, 15, 10, 10);
			PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
			// ORIGINAL
			document.open();
			String titulo = "-      ORIGINAL      -";
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoRegistroPagoOffline(document);
			// -- FIN ORIGINAL
			agregarTextoNormal(document, "                                                                                                              ",
					fuenteSubrayada, Element.ALIGN_CENTER);
			agregarSeparacion(document);
			titulo = "-      COPIA      -";
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoRegistroPagoOffline(document);
			document.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarReporteAnticipo() {
		try {
			String strNombreArchivo = "ticket_anticipo_" + ticketAnticipo.getNumeroTicket() + "_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
			Rectangle pagesize = new Rectangle(324f, 290f);
			Document document = new Document(pagesize, 15, 15, 10, 10);
			PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
			// ORIGINAL
			document.open();
			String titulo = "-      ORIGINAL      -";
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoAnticipo(document);
			// -- FIN ORIGINAL
			agregarTextoNormal(document, "                                                                                                                 ",
					fuenteSubrayada, Element.ALIGN_CENTER);
			agregarSeparacion(document);
			titulo = "-      COPIA      -";
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoAnticipo(document);
			document.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}

	// Marcelo Moyano 01/04/2013 Reporte Online
	public void generarReporteAnticipoOnline() {
		try {
			String[] numeroDocumento = ticketAnticipo.getNumeroTicket().split("-");
			String strNombreArchivo = "ticket_anticipo_" + Util.completarCeros(10, numeroDocumento[0]) + "_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
			Rectangle pagesize = new Rectangle(324f, 290f);
			Document document = new Document(pagesize, 15, 15, 10, 10);
			PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
			// ORIGINAL
			document.open();
			String titulo = "-      ORIGINAL      -";
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoAnticipoOnline(document);
			// -- FIN ORIGINAL
			agregarTextoNormal(document, "                                                                                                                 ",
					fuenteSubrayada, Element.ALIGN_CENTER);
			agregarSeparacion(document);
			titulo = "-      COPIA      -";
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoAnticipoOnline(document);
			document.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}

	public void generarReportePedido() {
		try {
			String strNombreArchivo = "ticket_orden_" + ticket.getCodigoCliente() + "_" + ticket.getNumeroPedido() + "_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
			Rectangle pagesize = new Rectangle(324f, 561.42f);
			Document document = new Document(pagesize, 15, 15, 10, 10);
			PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
			document.open();
			// ORIGINAL
			String titulo = ticket.getTitulo();
			if (titulo.isEmpty()) {
				titulo = "ORIGINAL";
			} else {
				titulo += " - ORIGINAL";
			}
			if (!estaSincronizado) {
				titulo += " - TEMPORAL";
			}
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoFactura(document);
			// -- FIN ORIGINAL
			agregarSeparacion(document);
			agregarTextoNormal(document, "                                                                                                                 ",
					fuenteSubrayada, Element.ALIGN_CENTER);
			agregarSeparacion(document);
			// COPIA
			titulo = ticket.getTitulo();
			if (titulo.isEmpty()) {
				titulo = "COPIA";
			} else {
				titulo += " - COPIA";
			}
			if (!estaSincronizado) {
				titulo += " - TEMPORAL";
			}
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			generarCuerpoFactura(document);
			document.close();
			Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
		} catch (FileNotFoundException fnfe) {
			Mensaje.mostrarError("Ya existe un archivo de impresión abierto.");
		} catch (Exception e) {
			Mensaje.mostrarError(e.getMessage());
		}
	}

	private void generarCuerpoRegistroPagoOnline(Document document, String modo) {
		try {
			for (TicketPago ticketPago : listaTicketPago) {
				/*
				 * CABECERA
				 */
				margenes = false;
				String titulo = modo;
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				PdfPTable table0 = new PdfPTable(1);
				table0.setWidthPercentage(100f);
				agregarEtiqueta(table0, Util.obtenerNombrePromesa(), Element.ALIGN_CENTER, margenes, 1);
				PdfPTable table1 = new PdfPTable(6);
				table1.setWidthPercentage(100f);
				table1.setWidths(new float[] { 0.7f, 1.2f, 0.67f, 0.6f, 0.7f, 0.8f });
				agregarEtiqueta(table1, "Dirección:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table1, Util.obtenerDireccionPromesa(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table1, "Teléfono:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table1, Util.obtenerTelefonoPromesa(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table1, "No. Ticket:", Element.ALIGN_RIGHT, margenes, 1);
				String[] numeroTicket = ticketPago.getNumeroTicket().split("-");
				agregarTexto(table1, numeroTicket[0], Element.ALIGN_LEFT, margenes, 1);
				// Marcelo Moyano Reporte Pago Cliente 28/03/2013
				PdfPTable table4 = new PdfPTable(6);
				table4.setWidthPercentage(100f);
				table4.setWidths(new float[] { 0.7f, 1.2f, 0.67f, 0.6f, 0.7f, 0.8f });
				agregarEtiqueta(table4, "No. SAP  :", Element.ALIGN_RIGHT, margenes, 5);
				agregarTexto(table4, numeroTicket[1], Element.ALIGN_LEFT, margenes, 1);
				// Marcelo Moyano Reporte Pago Cliente 28/03/2013
				PdfPTable tablaCabecera = new PdfPTable(1);
				tablaCabecera.setWidthPercentage(100f);
				PdfPCell celda0 = new PdfPCell(table0);
				celda0.setBorder(Rectangle.NO_BORDER);
				PdfPCell celda1 = new PdfPCell(table1);
				celda1.setBorder(Rectangle.NO_BORDER);
				PdfPCell celda4 = new PdfPCell(table4);// Marcelo Moyano Reporte Pago Cliente  28/03/2013
				celda4.setBorder(Rectangle.NO_BORDER);// Marcelo Moyano Reporte Pago Cliente 28/03/2013
				tablaCabecera.addCell(celda0);
				tablaCabecera.addCell(celda1);
				tablaCabecera.addCell(celda4);// Marcelo Moyano Reporte Pago Cliente 28/03/2013
				document.add(tablaCabecera);
				agregarSeparacion(document);

				// -----------------------------------------------------------------------------
				// SEGUNDA FILA
				// -----------------------------------------------------------------------------
				PdfPTable table2 = new PdfPTable(2);
				table2.setWidthPercentage(100f);
				table2.setWidths(new float[] { 0.4f, 2f });
				agregarEtiqueta(table2, "Cliente:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table2, ticketPago.getCliente(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table2, "Vendedor:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table2, ticketPago.getVendedor(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table2, "Referencia:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table2, ticketPago.getReferencia(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table2, "Cuenta Banco:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table2, ticketPago.getCuentaBanco(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table2, "Forma de Pago:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table2, ticketPago.getFormaPago(), Element.ALIGN_LEFT, margenes, 1);
				document.add(table2);

				// -----------------------------------------------------------------------------
				// TERCERA FILA
				// -----------------------------------------------------------------------------
				PdfPTable table3 = new PdfPTable(6);
				table3.setWidthPercentage(100f);
				table3.setWidths(new float[] { 1.5f, 1.0f, 1.5f, 1.0f, 1.5f, 1.5f });
				agregarEtiqueta(table3, "Fecha Registro:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table3, Util.convierteFechaAFormatoDDMMMYYYY(new Date()), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table3, "Fecha Documento:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table3, ticketPago.getFechaDocumento(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table3, "Fecha Venc.:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table3, ticketPago.getFechaVencimiento(), Element.ALIGN_LEFT, margenes, 1);
				document.add(table3);
				agregarSeparacion(document);

				// -----------------------------------------------------------------------------
				// CUARTA FILA
				// -----------------------------------------------------------------------------
				List<DetalleTicketPago> listaDetalleTicketPago = ticketPago.getListaDetalleTicketPago();
				PdfPTable tblDetalleTicketPago = new PdfPTable(4);
				tblDetalleTicketPago.setWidthPercentage(100f);
				agregarEtiqueta(tblDetalleTicketPago, "Nro. Factura", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(tblDetalleTicketPago, "Fec. Venc.", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(tblDetalleTicketPago, "Valor", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(tblDetalleTicketPago, "Abono", Element.ALIGN_LEFT, margenes, 1);
				BigDecimal totalCobranza = new BigDecimal("0.00");
				BigDecimal iva = new BigDecimal("0.00");
				for (DetalleTicketPago detalleTicketPago : listaDetalleTicketPago) {
						agregarTexto(tblDetalleTicketPago, detalleTicketPago.getNroFactura(), Element.ALIGN_LEFT, margenes, 1);
						agregarTexto( tblDetalleTicketPago, Util.convierteFechaPuntoDDMMYYYAFormatoDDMMMYYYY(detalleTicketPago .getFechaVencimiento()), Element.ALIGN_LEFT, margenes, 1);
						agregarTexto(tblDetalleTicketPago, Util.formatearNumero(detalleTicketPago.getValor()), Element.ALIGN_LEFT, margenes, 1);
						agregarTexto(tblDetalleTicketPago, Util.formatearNumero(detalleTicketPago.getAbono()), Element.ALIGN_LEFT, margenes, 1);
						totalCobranza = totalCobranza.add(new BigDecimal(Util.formatearNumero(detalleTicketPago.getAbono())));
//					iva = iva.add(new BigDecimal(Util.formatearNumero(detalleTicketPago.getIva())));
				}
				document.add(tblDetalleTicketPago);
				if(!iva.toString().equals("0.00")){
					double importe = totalCobranza.doubleValue() - iva.doubleValue();
					String strImporte = Util.formatearNumero(importe);
					// -----------------------------------------------------------------------------
					// QUINTA FILA
					// -----------------------------------------------------------------------------
					PdfPTable table5 = new PdfPTable(2);
					table5.setWidthPercentage(100f);
					table5.setWidths(new float[] { 5.0f, 1.0f });
					agregarEtiqueta(table5, "Retencion Renta:", Element.ALIGN_RIGHT, margenes, 1);
					agregarTexto(table5, strImporte, Element.ALIGN_LEFT, margenes, 1);
					document.add(table5);
					
					// -----------------------------------------------------------------------------
					// QUINTA FILA
					// -----------------------------------------------------------------------------
					PdfPTable table6 = new PdfPTable(2);
					table6.setWidthPercentage(100f);
					table6.setWidths(new float[] { 5.0f, 1.0f });
					agregarEtiqueta(table6, "Retencion Iva:", Element.ALIGN_RIGHT, margenes, 1);
					agregarTexto(table6, iva.toString(), Element.ALIGN_LEFT, margenes, 1);
					document.add(table6);
				}
				
				
				// -----------------------------------------------------------------------------
				// QUINTA FILA
				// -----------------------------------------------------------------------------
				PdfPTable table7 = new PdfPTable(2);
				table7.setWidthPercentage(100f);
				table7.setWidths(new float[] { 5.0f, 1.0f });
				agregarEtiqueta(table7, "Total de cobranza:", Element.ALIGN_RIGHT, margenes, 1);
				agregarTexto(table7, totalCobranza.toString(), Element.ALIGN_LEFT, margenes, 1);
				document.add(table7);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generarCuerpoRegistroDevolucion(Document document,
			String titulo) {
		try {
			// -----------------------------------------------------------------------------
			// CABECERA
			// -----------------------------------------------------------------------------
			margenes = false;
			agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
			PdfPTable table0 = new PdfPTable(1);
			table0.setWidthPercentage(100f);
			agregarEtiqueta(table0, "Productos Metalurgicos S.A. Promesa", Element.ALIGN_CENTER, margenes, 1);
			agregarSeparacion(document);

			PdfPTable table1 = new PdfPTable(4);
			table1.setWidthPercentage(100f);
			table1.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table1, "", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, "", Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "Nº Seguimiento:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketDevolucion.getCodigoPedidoDevoluciones(), Element.ALIGN_LEFT, margenes, 1);// Numero de Seguimiento
			PdfPTable tablaCabecera = new PdfPTable(1);
			tablaCabecera.setWidthPercentage(100f);
			PdfPCell celda0 = new PdfPCell(table0);
			celda0.setBorder(Rectangle.NO_BORDER);
			PdfPCell celda1 = new PdfPCell(table1);
			celda1.setBorder(Rectangle.NO_BORDER);
			tablaCabecera.addCell(celda0);
			tablaCabecera.addCell(celda1);
			document.add(tablaCabecera);

			// -----------------------------------------------------------------------------
			// SEGUNDA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100f);
			table2.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table2, "", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, "", Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table2, "Fecha", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, Util.getFechaActualDDMMYYYY(new Date()), Element.ALIGN_LEFT, margenes, 1);
			document.add(table2);

			// -----------------------------------------------------------------------------
			// TERCERA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table3 = new PdfPTable(4);
			table3.setWidthPercentage(100f);
			table3.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table3, "Cliente:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table3, ticketDevolucion.getCodigoCliente() + " - " + ticketDevolucion.getNombreCliente(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table3, "Ciudad:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table3, ticketDevolucion.getDireccion(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table3);

			// -----------------------------------------------------------------------------
			// CUARTA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table4 = new PdfPTable(4);
			table4.setWidthPercentage(100f);
			table4.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table4, "Vendedor:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table4, Promesa.datose.get(0).getStrCodigo() + " - " + Promesa.datose.get(0).getStrUsuario(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table4, "Fec. Fact.", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table4, ticketDevolucion.getFechaFactura(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table4);

			// -----------------------------------------------------------------------------
			// QUINTA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table5 = new PdfPTable(4);
			table5.setWidthPercentage(100f);
			table5.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table5, "Fac. SAP:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table5, ticketDevolucion.getFacturaSAP(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table5, "Fac. SRI:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table5, ticketDevolucion.getFacturaSRI(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table5);

			// -----------------------------------------------------------------------------
			// SEXTA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table6 = new PdfPTable(4);
			table6.setWidthPercentage(100f);
			table6.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table6, "Nro. Sello:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table6, ticketDevolucion.getSello(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table6, "FUD:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table6, ticketDevolucion.getStrFud(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table6);

			// -----------------------------------------------------------------------------
			// SEPTIMA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table7 = new PdfPTable(4);
			table7.setWidthPercentage(100f);
			table7.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table7, "Guia Transp:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table7, ticketDevolucion.getGuiatransp(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table7, "Nro. Bulto:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table7, ticketDevolucion.getNumeroBulto(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table7);

			// -----------------------------------------------------------------------------
			// OCTAVA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table8 = new PdfPTable(4);
			table8.setWidthPercentage(100f);
			table8.setWidths(new float[] { 1.0f, 2.0f, 0.8f, 1.2f });
			agregarEtiqueta(table8, "Contacto:", Element.ALIGN_LEFT, margenes,1);
			agregarTexto(table8, ticketDevolucion.getContacto(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table8, "Tipo:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table8, ticketDevolucion.getTipoDirVen(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table8);

			// -----------------------------------------------------------------------------
			// NOVENA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table9 = new PdfPTable(2);
			table9.setWidthPercentage(100f);
			table9.setWidths(new float[] { 1.5f, 5.0f });
			agregarEtiqueta(table9, "Motivo:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table9, ticketDevolucion.getMotivo(), Element.ALIGN_LEFT, margenes, 1);
			document.add(table9);
			agregarSeparacion(document);

			// -----------------------------------------------------------------------------
			// DECIMA FILA
			// -----------------------------------------------------------------------------
			List<DetalleTicketDevolucion> listaDetalleTicketDevolucion = ticketDevolucion.getListaDetalleTicketDevolucion();
			PdfPTable tblDetalleTicketDevolucion = new PdfPTable(7);
			tblDetalleTicketDevolucion.setWidthPercentage(100f);
			tblDetalleTicketDevolucion.setWidths(new float[] { 1.0f, 1.0f, 0.9f, 0.7f, 2.0f, 0.7f, 0.7f });
			agregarEtiqueta(tblDetalleTicketDevolucion, "POSICIÓN", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblDetalleTicketDevolucion, "MATERIAL", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblDetalleTicketDevolucion, "CANTIDAD", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblDetalleTicketDevolucion, "U.M.", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblDetalleTicketDevolucion, "DENOMINACIÓN", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblDetalleTicketDevolucion, "P. NETO", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblDetalleTicketDevolucion, "V. NETO", Element.ALIGN_CENTER, margenes, 1);

			for (DetalleTicketDevolucion detalleTicketDevolucion : listaDetalleTicketDevolucion) {
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getPosicion(), Element.ALIGN_CENTER, margenes, 1);
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getMaterial(), Element.ALIGN_CENTER, margenes, 1);
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getCantidad(), Element.ALIGN_RIGHT, margenes, 1);
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getUm(), Element.ALIGN_CENTER, margenes, 1);
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getDenominacion(), Element.ALIGN_CENTER, margenes, 1);
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getPrecioNeto(), Element.ALIGN_RIGHT, margenes, 1);
				agregarTexto(tblDetalleTicketDevolucion, detalleTicketDevolucion.getValorNeto(), Element.ALIGN_RIGHT, margenes, 1);
			}
			document.add(tblDetalleTicketDevolucion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generarCuerpoRegistroPagoOffline(Document document) {
		try {
			margenes = false;
			PdfPTable table0 = new PdfPTable(1);
			table0.setWidthPercentage(100f);
			agregarEtiqueta(table0, Util.obtenerNombrePromesa(), Element.ALIGN_CENTER, margenes, 1);
			PdfPTable table1 = new PdfPTable(6);
			table1.setWidthPercentage(100f);
			table1.setWidths(new float[] { 0.7f, 2.0f, 0.67f, 0.6f, 0.8f, 0.82f });
			agregarEtiqueta(table1, "DIRECCIÓN:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketPagoOffline.getDireccion(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "TELÉFONO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketPagoOffline.getTelefono(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "N° REGISTRO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketPagoOffline.getNumeroTicket(), Element.ALIGN_LEFT, margenes, 1);

			PdfPTable tablaCabecera = new PdfPTable(1);
			tablaCabecera.setWidthPercentage(100f);
			PdfPCell celda0 = new PdfPCell(table0);
			celda0.setBorder(Rectangle.NO_BORDER);
			PdfPCell celda1 = new PdfPCell(table1);
			celda1.setBorder(Rectangle.NO_BORDER);
			tablaCabecera.addCell(celda0);
			tablaCabecera.addCell(celda1);
			document.add(tablaCabecera);
			// -----------------------------------------------------------------------------
			// SEGUNDA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table2 = new PdfPTable(2);
			table2.setWidthPercentage(100f);
			table2.setWidths(new float[] { 0.4f, 2f });
			agregarEtiqueta(table2, "CLIENTE:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketPagoOffline.getCliente(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table2, "VENDEDOR:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketPagoOffline.getVendedor(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table2, "FEC. CREACIÓN:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketPagoOffline.getFechadoc(), Element.ALIGN_LEFT, margenes, 1);
			agregarSeparacion(document);
			document.add(table2);
			agregarSeparacion(document);
			// -----------------------------------------------------------------------------
			// TERCERA FILA
			// -----------------------------------------------------------------------------
			List<PagoRecibido> listaPagosRecibidos = ticketPagoOffline.getListaPagosRecibidos();
			for (PagoRecibido pagoRecibido : listaPagosRecibidos) {
				margenes = false;
				PdfPTable tblTipoPago = new PdfPTable(2);
				tblTipoPago.setWidthPercentage(100f);
				tblTipoPago.setWidths(new float[] { 0.15f, 0.85f });
				agregarEtiqueta(tblTipoPago, "FORMA PAGO:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(tblTipoPago, pagoRecibido.getFormaPago().getDescripcionFormaPago(), Element.ALIGN_LEFT, margenes, 1);
				PdfPTable table = new PdfPTable(5);
				table.setWidthPercentage(100f);
				table.setWidths(new float[] { 0.01f, 0.2f, 0.22f, 0.25f, 0.3f });
				agregarEtiqueta(table, "", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "FECHA DOC:", Element.ALIGN_LEFT, margenes, 1);
//				agregarTexto(table, "" + Util.convierteFechaPuntoDDMMYYYAFormatoYYYYMMdd(pagoRecibido.getFechaDocumento()), Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, "" + pagoRecibido.getFechaDocumento(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "FECHA VENC:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table,pagoRecibido.getFechaVencimiento(), Element.ALIGN_LEFT, margenes, 1);
				
				agregarEtiqueta(table, "", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "IMPORTE:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, "" + pagoRecibido.getImporte(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "NRO. FACT:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, "" + pagoRecibido.getNumeroFactura(), Element.ALIGN_LEFT, margenes, 1);
				
				agregarEtiqueta(table, "", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "BCO.CLIENTE:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, "" + pagoRecibido.getBancoCliente().getNroCtaBcoCliente() + " - " + pagoRecibido.getBancoCliente().getDescripcionBancoCliente(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "NRO.CTA.BCO.CLIENTE:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, ""+ pagoRecibido.getBancoCliente().getNroCtaBcoCliente(), Element.ALIGN_LEFT, margenes, 1);
				
				agregarEtiqueta(table, "", Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "BCO. PROMESA:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, "" + pagoRecibido.getBancoPromesa().getDescripcionBancoPromesa(), Element.ALIGN_LEFT, margenes, 1);
				agregarEtiqueta(table, "REFERENCIA:", Element.ALIGN_LEFT, margenes, 1);
				agregarTexto(table, "" + pagoRecibido.getReferencia(), Element.ALIGN_LEFT, margenes, 1);
				
				PdfPTable tblContenedorTipoPago = new PdfPTable(1);
				tblContenedorTipoPago.setWidthPercentage(100f);
				margenes = true;
				tblContenedorTipoPago.addCell(tblTipoPago);
				tblContenedorTipoPago.addCell(table);
				document.add(tblContenedorTipoPago);
				agregarSeparacion(document);
				margenes = false;
			}
			agregarSeparacion(document);
			List<PagoParcial> listaPagosParciales = ticketPagoOffline.getListaPagosParciales();
			PdfPTable tblTipoDetalles = new PdfPTable(4);
			tblTipoDetalles.setWidthPercentage(100f);
			agregarEtiqueta(tblTipoDetalles, "Nro. Factura", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblTipoDetalles, "Fec. Venc.", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblTipoDetalles, "Valor", Element.ALIGN_RIGHT, margenes, 1);
			agregarEtiqueta(tblTipoDetalles, "Abono", Element.ALIGN_RIGHT, margenes, 1);
			Double abonoTotal = 0d;
			for (PagoParcial pagoParcial : listaPagosParciales) {
				agregarTexto(tblTipoDetalles, pagoParcial.getReferencia(), Element.ALIGN_CENTER, margenes, 1);
				agregarTexto(tblTipoDetalles, Util.convierteFechaPuntoDDMMYYYAFormatoDDMMMYYYY(pagoParcial.getVencimiento()), Element.ALIGN_CENTER, margenes, 1);
				agregarTexto(tblTipoDetalles, Util.formatearNumero(pagoParcial.getImportePagoTotal()), Element.ALIGN_RIGHT, margenes, 1);
				agregarTexto(tblTipoDetalles, Util.formatearNumero(pagoParcial.getImportePago()), Element.ALIGN_RIGHT, margenes, 1);
				abonoTotal += pagoParcial.getImportePago();
			}
			agregarEtiqueta(tblTipoDetalles, "", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblTipoDetalles, "", Element.ALIGN_CENTER, margenes, 1);
			agregarEtiqueta(tblTipoDetalles, "Total:", Element.ALIGN_RIGHT, margenes, 1);
			agregarEtiqueta(tblTipoDetalles, Util.formatearNumero(abonoTotal), Element.ALIGN_RIGHT, margenes, 1);
			document.add(tblTipoDetalles);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generarCuerpoAnticipo(Document document) {
		try {
			PdfPTable table0 = new PdfPTable(1);
			table0.setWidthPercentage(100f);
			agregarEtiqueta(table0, Util.obtenerNombrePromesa(), Element.ALIGN_CENTER, margenes, 1);
			PdfPTable table1 = new PdfPTable(6);
			table1.setWidthPercentage(100f);
			table1.setWidths(new float[] { 0.7f, 2.0f, 0.67f, 0.6f, 0.8f, 0.82f });
			agregarEtiqueta(table1, "DIRECCIÓN:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketAnticipo.getDireccion(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "TELÉFONO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketAnticipo.getTelefono(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "N° REGISTRO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketAnticipo.getNumeroTicket(), Element.ALIGN_LEFT, margenes, 1);

			PdfPTable tablaCabecera = new PdfPTable(1);
			tablaCabecera.setWidthPercentage(100f);
			PdfPCell celda0 = new PdfPCell(table0);
			celda0.setBorder(Rectangle.NO_BORDER);
			PdfPCell celda1 = new PdfPCell(table1);
			celda1.setBorder(Rectangle.NO_BORDER);
			tablaCabecera.addCell(celda0);
			tablaCabecera.addCell(celda1);
			document.add(tablaCabecera);
			
			// SEGUNDA FILA
			// -----------------------------------------------------------------------------
			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100f);
			table2.setWidths(new float[] { 1.5f, 2f, 2f, 2f });
			agregarEtiqueta(table2, "CLIENTE:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getCliente(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "VENDEDOR:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getVendedor(), Element.ALIGN_LEFT, margenes, 3);

			// Marcelo Moyano 19/03/2013 - 10:40
			agregarEtiqueta(table2, "FEC. CREACIÓN:", Element.ALIGN_LEFT, margenes, 1);
//			agregarTexto(table2, Util.convierteFechaAFormatoDDMMMYYYY(new Date()), Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(new Date()), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table2, "FEC. DOCUMENTO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getFecha(), Element.ALIGN_LEFT, margenes, 1);
//			agregarTexto(table2, Util.convierteFechaPuntoDDMMYYYAFormatoDDMMMYYYY(ticketAnticipo.getFecha()), Element.ALIGN_LEFT, margenes, 1);
			// Marcelo Moyano 19/03/2013 - 10:40

			agregarEtiqueta(table2, "REFERENCIA:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getReferencia(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "CTA. BANCO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getNumeroCuentaBanco(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "FORMA PAGO:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getFormaPago(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "OBSERVACIÓN:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getObservacion(), Element.ALIGN_LEFT, margenes, 3);
			// -----------------------------------------------------------------------------
//			PdfPTable table3 = new PdfPTable(2);
//			PdfPTable tableIva4 = new PdfPTable(2);
			String totalCobranza = "Total Cobranza:";
//			if(ticketAnticipo.getIdFormaPago().equalsIgnoreCase("VT")){
//				// -----------------------------------------------------------------------------
//				// TERCERA FILA
//				table3.setWidthPercentage(100f);
//				table3.setWidths(new float[] { 4f, 1f });
//				agregarEtiqueta(table3, "Retención Renta:", Element.ALIGN_RIGHT, margenes, 1);
//				agregarTexto(table3, ticketAnticipo.getImporte(), Element.ALIGN_RIGHT, margenes, 1);
//				// -----------------------------------------------------------------------------
//				// -----------------------------------------------------------------------------
//				// cuarta FILA
//				tableIva4.setWidthPercentage(100f);
//				tableIva4.setWidths(new float[] { 4f, 1f });
//				agregarEtiqueta(tableIva4, "Retención IVA:", Element.ALIGN_RIGHT, margenes, 1);
//				agregarTexto(tableIva4, ticketAnticipo.getIva(), Element.ALIGN_RIGHT, margenes, 1);
//				// -----------------------------------------------------------------------------
//				// -----------------------------------------------------------------------------
//				
//				totalCobranza = "Total Retención:";
//			}
			
			// QUINTA FILA
			PdfPTable table5 = new PdfPTable(2);
			table5.setWidthPercentage(100f);
			table5.setWidths(new float[] { 4f, 1f });
			agregarEtiqueta(table5, totalCobranza, Element.ALIGN_RIGHT, margenes, 1);
			agregarTexto(table5, ticketAnticipo.getTotalCobranza(), Element.ALIGN_RIGHT, margenes, 1);
			// -----------------------------------------------------------------------------
			agregarSeparacion(document);
			document.add(table2);
//			if(ticketAnticipo.getIdFormaPago().equalsIgnoreCase("VT")){
//				document.add(table3);
//				document.add(tableIva4);
//			}
			document.add(table5);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	// Marcelo Moyano 01/04/2013 Reporte Anticipo Online
	private void generarCuerpoAnticipoOnline(Document document) {
		try {
			PdfPTable table0 = new PdfPTable(1);
			table0.setWidthPercentage(100f);
			agregarEtiqueta(table0, Util.obtenerNombrePromesa(), Element.ALIGN_CENTER, margenes, 1);
			PdfPTable table1 = new PdfPTable(6);
			table1.setWidthPercentage(100f);
			table1.setWidths(new float[] { 0.7f, 1.2f, 0.67f, 0.6f, 0.7f, 0.8f });
			agregarEtiqueta(table1, "Dirección:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketAnticipo.getDireccion(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "Teléfono:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table1, ticketAnticipo.getTelefono(), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table1, "No. Ticket:", Element.ALIGN_RIGHT, margenes, 1);

			String[] ticket = ticketAnticipo.getNumeroTicket().split("-");
			String ticketSecuencia = Util.completarCeros(10, ticket[0]);
			agregarTexto(table1, ticketSecuencia, Element.ALIGN_LEFT, margenes, 1);

			// Marcelo Moyano Número de Sap 01/04/2013
			PdfPTable table4 = new PdfPTable(6);
			table4.setWidthPercentage(100f);
			table4.setWidths(new float[] { 0.7f, 1.2f, 0.67f, 0.6f, 0.7f, 0.8f });
			agregarEtiqueta(table4, "No. SAP  :", Element.ALIGN_RIGHT, margenes, 5);
			agregarTexto(table4, ticket[1], Element.ALIGN_LEFT, margenes, 1);
			// Marcelo Moyano

			PdfPTable tablaCabecera = new PdfPTable(1);
			tablaCabecera.setWidthPercentage(100f);
			PdfPCell celda0 = new PdfPCell(table0);
			celda0.setBorder(Rectangle.NO_BORDER);
			PdfPCell celda1 = new PdfPCell(table1);
			celda1.setBorder(Rectangle.NO_BORDER);
			PdfPCell celda4 = new PdfPCell(table4);// Marcelo Moyano Número de	// Sap 01/04/2013
			celda4.setBorder(Rectangle.NO_BORDER);// Marcelo Moyano Número de Sap 01/04/2013
			tablaCabecera.addCell(celda0);
			tablaCabecera.addCell(celda1);
			tablaCabecera.addCell(celda4);// Marcelo Moyano Número de Sap 01/04/2013
			document.add(tablaCabecera);

			PdfPTable table2 = new PdfPTable(4);
			table2.setWidthPercentage(100f);
			table2.setWidths(new float[] { 1.5f, 2f, 2f, 2f });
			agregarEtiqueta(table2, "Cliente:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getCliente(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "Vendedor:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getVendedor(), Element.ALIGN_LEFT, margenes, 3);

			// Marcelo Moyano 19/03/2013 - 10:40
			agregarEtiqueta(table2, "Fec. Creación:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, Util.convierteFechaHoyAFormatoDDMMMYYYYHHMM(new Date()), Element.ALIGN_LEFT, margenes, 1);
			agregarEtiqueta(table2, "Fec. Documento:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getFecha(), Element.ALIGN_LEFT, margenes, 1);
//			agregarTexto(table2, Util.convierteFechaPuntoDDMMYYYAFormatoDDMMMYYYY(ticketAnticipo.getFecha()), Element.ALIGN_LEFT, margenes, 1);
			// Marcelo Moyano 19/03/2013 - 10:40

			agregarEtiqueta(table2, "Referencia:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getReferencia(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "Cta. Banco:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getNumeroCuentaBanco(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "Forma Pago:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getFormaPago(), Element.ALIGN_LEFT, margenes, 3);
			agregarEtiqueta(table2, "Observacion:", Element.ALIGN_LEFT, margenes, 1);
			agregarTexto(table2, ticketAnticipo.getObservacion(), Element.ALIGN_LEFT, margenes, 3);
			
			
//			Marcelo Moyano Retencion renta, Retencion iva
//			PdfPTable table3 = new PdfPTable(2);
//			PdfPTable tableIva4 = new PdfPTable(2);
			String totalCobranza = "Total Cobranza:";
//			if(ticketAnticipo.getIdFormaPago().equalsIgnoreCase("VT")){
//				// -----------------------------------------------------------------------------
//				// TERCERA FILA
////				PdfPTable table3 = new PdfPTable(2);
//				table3.setWidthPercentage(100f);
//				table3.setWidths(new float[] { 4f, 1f });
//				agregarEtiqueta(table3, "Retención Renta:", Element.ALIGN_RIGHT, margenes, 1);
//				agregarTexto(table3, ticketAnticipo.getImporte(), Element.ALIGN_RIGHT, margenes, 1);
//				// -----------------------------------------------------------------------------
//				// -----------------------------------------------------------------------------
//				// cuarta FILA
////				PdfPTable tableIva4 = new PdfPTable(2);
//				tableIva4.setWidthPercentage(100f);
//				tableIva4.setWidths(new float[] { 4f, 1f });
//				agregarEtiqueta(tableIva4, "Retención IVA:", Element.ALIGN_RIGHT, margenes, 1);
//				agregarTexto(tableIva4, ticketAnticipo.getIva(), Element.ALIGN_RIGHT, margenes, 1);
//				// -----------------------------------------------------------------------------
//				// -----------------------------------------------------------------------------
//				
//				totalCobranza = "Total Retención:";
//			}
			
//			Marcelo Moyano Retencion renta, Retencion iva
			// QUINTA FILA
			PdfPTable table5 = new PdfPTable(2);
			table5.setWidthPercentage(100f);
			table5.setWidths(new float[] { 4f, 1f });
			agregarEtiqueta(table5, totalCobranza, Element.ALIGN_RIGHT, margenes, 1);
			agregarTexto(table5, ticketAnticipo.getTotalCobranza(), Element.ALIGN_RIGHT, margenes, 1);
			// -----------------------------------------------------------------------------
			agregarSeparacion(document);
			document.add(table2);
//			if(ticketAnticipo.getIdFormaPago().equalsIgnoreCase("VT")){
//				document.add(table3);
//				document.add(tableIva4);
//			}
			document.add(table5);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	private void generarCuerpoFactura(Document document) {
		try {
			agregarTextoNormal(document, Util.obtenerNombrePromesa(), fuenteTitulo, Element.ALIGN_CENTER);
			agregarSeparacion(document);
			document.add(crearFilaUno());
			agregarSeparacion(document);
			document.add(crearFilaDos());
			document.add(crearFilaTres());
			agregarSeparacion(document);
			document.add(crearDetalles());
			document.add(new Paragraph("\n"));
			agregarTextoNormal(document, "                                        ", fuenteSubrayada, Element.ALIGN_CENTER);
			agregarTextoNormal(document, "FIRMA DEL CLIENTE", fuenteTextos, Element.ALIGN_CENTER);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	private void agregarEtiqueta(PdfPTable table, String string, int align, boolean isBorder, int colspan) {
		Chunk c = new Chunk(string, fuenteLabels);
		PdfPCell cell = new PdfPCell(new Phrase(c));
		if (!isBorder) {
			cell.setBorder(Rectangle.NO_BORDER);
		}
		cell.setColspan(colspan);
		cell.setVerticalAlignment(align);
		cell.setHorizontalAlignment(align);
		table.addCell(cell);
	}

	private void agregarTexto(PdfPTable table, String string, int align, boolean isBorder, int colspan) {
		Chunk c = new Chunk(string, fuenteTextos);
		PdfPCell cell = new PdfPCell(new Phrase(c));
		if (!isBorder) {
			cell.setBorder(Rectangle.NO_BORDER);
		}
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(align);
		table.addCell(cell);
	}

	private void agregarTextoFijoCursiva(PdfPTable table, String string, int align, boolean isBorder, int colspan) {
		Chunk c = new Chunk(string, fuenteCombos);
		PdfPCell cell = new PdfPCell(new Phrase(c));
		if (!isBorder) {
			cell.setBorder(Rectangle.NO_BORDER);
		}
		cell.setColspan(colspan);
		cell.setBackgroundColor(new BaseColor(230, 230, 230));
		cell.setHorizontalAlignment(align);
		cell.setFixedHeight(10f);
		table.addCell(cell);
	}

	private void agregarTextoFijo(PdfPTable table, String string, int align, boolean isBorder, int colspan) {
		Chunk c = new Chunk(string, fuenteTextos);
		PdfPCell cell = new PdfPCell(new Phrase(c));
		if (!isBorder) {
			cell.setBorder(Rectangle.NO_BORDER);
		}
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(align);
		cell.setFixedHeight(10f);
		table.addCell(cell);
	}

	private PdfPTable crearFilaUno() throws DocumentException {
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 0.78f, 2.08f, 0.75f, 0.72f, 0.73f, 0.64f });
		agregarEtiqueta(table, "DIRECCIÓN:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getDireccion(), Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "TELÉFONO:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getTelefono(), Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "N° PEDIDO:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getNumeroPedido(), Element.ALIGN_LEFT, margenes, 1);
		return table;
	}

	private PdfPTable crearFilaDos() throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 0.355f, 2f });
		agregarEtiqueta(table, "CLIENTE:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getCliente(), Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "DIRECCIÓN:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getDireccionCliente(), Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "VENDEDOR:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getVendedor(), Element.ALIGN_LEFT, margenes, 1);
		return table;
	}

	private PdfPTable crearFilaTres() throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 0.37f, 0.6f, 0.65f, 1f });
		agregarEtiqueta(table, "FECHA:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getFecha(), Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "CONDICIÓN DE PAGO:", Element.ALIGN_LEFT, margenes, 1);
		agregarTexto(table, ticket.getCondicionesPago(), Element.ALIGN_LEFT, margenes, 1);
		return table;
	}

	private PdfPTable crearDetalles() throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		table.setWidths(new float[] { 0.4f, 2.7f, 0.6f, 0.6f, 0.6f });
		table.setWidthPercentage(100f);
		agregarEtiqueta(table, "ITEM", Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "DESCRIPCIÓN", Element.ALIGN_LEFT, margenes, 1);
		agregarEtiqueta(table, "CANTIDAD", Element.ALIGN_RIGHT, margenes, 1);
		agregarEtiqueta(table, "PRECIO", Element.ALIGN_RIGHT, margenes, 1);
		agregarEtiqueta(table, "VALOR", Element.ALIGN_RIGHT, margenes, 1);

		List<DetalleTicketOrden> detalles = ticket.getDetalles();
		for (DetalleTicketOrden d : detalles) {
			if (d.getTipo() == 2) {
				agregarTextoFijoCursiva(table, d.getItem(), Element.ALIGN_LEFT, margenes, 1);
				agregarTextoFijoCursiva(table, d.getDescripcion(), Element.ALIGN_LEFT, margenes, 1);
				agregarTextoFijoCursiva(table, Util.formatearNumero("" + d.getCantidad()), Element.ALIGN_RIGHT, margenes, 1);
				agregarTextoFijoCursiva(table, Util.formatearNumero("" + d.getPrecio()), Element.ALIGN_RIGHT, margenes, 1);
				agregarTextoFijoCursiva(table, Util.formatearNumero("" + d.getValor()), Element.ALIGN_RIGHT, margenes, 1);
			} else {
				agregarTextoFijo(table, d.getItem(), Element.ALIGN_LEFT, margenes, 1);
				agregarTextoFijo(table, d.getDescripcion(), Element.ALIGN_LEFT, margenes, 1);
				agregarTextoFijo(table, Util.formatearNumero("" + d.getCantidad()), Element.ALIGN_RIGHT, margenes, 1);
				agregarTextoFijo(table, Util.formatearNumero("" + d.getPrecio()), Element.ALIGN_RIGHT, margenes, 1);
				agregarTextoFijo(table, Util.formatearNumero("" + d.getValor()), Element.ALIGN_RIGHT, margenes, 1);
			}
		}

		agregarEtiqueta(table, "SUBTOTAL:", Element.ALIGN_RIGHT, margenes, 4);
		agregarTexto(table, Util.formatearNumero("" + Math.round(ticket.getSubtotal() * 1000) / 1000.0d), Element.ALIGN_RIGHT, margenes, 1);
		agregarEtiqueta(table, "IVA:", Element.ALIGN_RIGHT, margenes, 4);
		agregarTexto(table, Util.formatearNumero("" + Math.round(ticket.getIva() * 1000) / 1000.0d), Element.ALIGN_RIGHT, margenes, 1);
		agregarEtiqueta(table, "TOTAL:", Element.ALIGN_RIGHT, margenes, 4);
		agregarTexto(table, Util.formatearNumero("" + Math.round(ticket.getTotal() * 1000) / 1000.0d), Element.ALIGN_RIGHT, margenes, 1);
		return table;
	}
}
