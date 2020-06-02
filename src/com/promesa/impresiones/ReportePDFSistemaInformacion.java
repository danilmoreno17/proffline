package com.promesa.impresiones;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class ReportePDFSistemaInformacion {

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
	private Font fuenteLabels;
	private List<String[]> reporteIndicadoresVendedor = new ArrayList<String[]>();
	private List<String[]> reporteIndicadoresDetalleVendedor = new ArrayList<String[]>();
	private List<String[]> reporteIndicadoresSupervisor = new ArrayList<String[]>();
	private List<String[]> reporteIndicadoresDetalleSupervisor = new ArrayList<String[]>();

	public ReportePDFSistemaInformacion(List<String[]> reporte, int caso) {
		switch(caso){
			case 1 :
				this.reporteIndicadoresVendedor = reporte;
				inicializarFuentes(1);
				break;
			case 2 :
				this.reporteIndicadoresDetalleVendedor = reporte;
				inicializarFuentes(1);
				break;
			case 3 :
				this.reporteIndicadoresSupervisor = reporte;
				inicializarFuentes(1);
				break;
			case 4 :
				this.reporteIndicadoresDetalleSupervisor = reporte;
				inicializarFuentes(2);
		}
	}

	private void inicializarFuentes(int modo) {
		BaseFont bf = null;
		switch(modo){
			case 1 :
				try {
					bf = BaseFont.createFont(FONTS[4][0], FONTS[4][1], BaseFont.EMBEDDED);
					fuenteTitulo = new Font(bf, 7.3f, Font.BOLD);
					fuenteTextos = new Font(bf, 6f, Font.NORMAL);
					fuenteLabels = new Font(bf, 6.0f, Font.BOLD);
				} catch (Exception e) {
					fuenteTitulo = new Font(Font.FontFamily.COURIER, 7, Font.BOLD);
					fuenteTextos = new Font(Font.FontFamily.COURIER, 7f, Font.NORMAL);
					fuenteLabels = new Font(Font.FontFamily.COURIER, 6f, Font.BOLD);
				}
				break;
			case 2 :
				try {
					bf = BaseFont.createFont(FONTS[4][0], FONTS[4][1], BaseFont.EMBEDDED);
					fuenteTitulo = new Font(bf, 7.3f, Font.BOLD);
					fuenteTextos = new Font(bf, 3f, Font.NORMAL);
					fuenteLabels = new Font(bf, 3.0f, Font.BOLD);
				} catch (Exception e) {
					fuenteTitulo = new Font(Font.FontFamily.COURIER, 7, Font.BOLD);
					fuenteTextos = new Font(Font.FontFamily.COURIER, 4f, Font.NORMAL);
					fuenteLabels = new Font(Font.FontFamily.COURIER, 3f, Font.BOLD);
				}
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
	
	private void dibujarEtiqueta(PdfPTable table, String string, int align, boolean isBorder, int colspan) {
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
	
	private void dibujarTexto(PdfPTable table, String string, int align, boolean isBorder, int colspan) {
		Chunk c = new Chunk(string, fuenteTextos);
		PdfPCell cell = new PdfPCell(new Phrase(c));
		if (!isBorder) {
			cell.setBorder(Rectangle.NO_BORDER);
		}
		cell.setColspan(colspan);
		cell.setVerticalAlignment(align);
		cell.setHorizontalAlignment(align);
		table.addCell(cell);
	}

	public static void main(String[] args) {
		
	}
	
	public void generarReporteIndicadoresVendedor(){
		try {
			if(reporteIndicadoresVendedor.size() > 0){
				String strNombreArchivo = "reporte_indicador_vendedor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
				float w = PageSize.A4.getWidth();
	            float h = PageSize.A4.getHeight();
	            Rectangle pagesize = new Rectangle(w, h);
				Document document = new Document(pagesize, 15, 15, 10, 10);
				PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
				document.open();
				String titulo = "-      REPORTE INDICADORES VENDEDOR      -";
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				agregarSeparacion(document);
				generarCuerpoIndicadoresVendedor(document);
				document.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}
	
	private void generarCuerpoIndicadoresVendedor(Document document) throws Exception{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
        
        PdfPTable body = new PdfPTable(5);
        body.setWidths(new float[]{0.8f, 1.4f, 0.6f, 0.6f, 0.8f});
        body.setWidthPercentage(100f);
        
        dibujarEtiqueta(body, "CÓDIGO KPI", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "DESCRIPCIÓN", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "REAL", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "OBJETIVO", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "CUMPLIMIENTO", Element.ALIGN_CENTER, true, 1);
        
		for (String[] arregloIndicadoresVendedor : reporteIndicadoresVendedor) {
			dibujarTexto(body, arregloIndicadoresVendedor[1], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloIndicadoresVendedor[2], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresVendedor[3]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresVendedor[4]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresVendedor[5]) + "%", Element.ALIGN_RIGHT, true, 1);
		}
		PdfPCell cellBody = new PdfPCell();
        cellBody.setBorder(Rectangle.NO_BORDER);
        cellBody.addElement(body);
        table.addCell(cellBody);
		document.add(table);
	}
	
	public void generarReporteIndicadoresDetalleVendedor(){
		try {
			if(reporteIndicadoresDetalleVendedor.size() > 0){
				String strNombreArchivo = "reporte_indicador_detalle_vendedor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
				float w = PageSize.A4.getWidth();
	            float h = PageSize.A4.getHeight();
	            Rectangle pagesize = new Rectangle(w, h);
				Document document = new Document(pagesize, 15, 15, 10, 10);
				PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
				document.open();
				String titulo = "-      REPORTE INDICADORES DETALLE VENDEDOR      -";
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				agregarSeparacion(document);
				generarCuerpoIndicadoresDetalleVendedor(document);
				document.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}
	
	private void generarCuerpoIndicadoresDetalleVendedor(Document document) throws Exception{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
        
        PdfPTable body = new PdfPTable(6);
        body.setWidths(new float[]{0.8f, 2f, 0.6f, 0.6f, 0.6f, 0.8f});
        body.setWidthPercentage(100f);
        
        dibujarEtiqueta(body, "CÓDIGO CLIENTE", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "CLIENTE", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "PICKING", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "VENTA NETA", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "IMPORTE REAL", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "IMPORTE OBJETIVO", Element.ALIGN_CENTER, true, 1);
        
		for (String[] arregloIndicadoresDetalleVendedor : reporteIndicadoresDetalleVendedor) {
			dibujarTexto(body, arregloIndicadoresDetalleVendedor[1], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloIndicadoresDetalleVendedor[2], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleVendedor[3]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleVendedor[4]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleVendedor[5]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleVendedor[6]), Element.ALIGN_RIGHT, true, 1);
		}
		PdfPCell cellBody = new PdfPCell();
        cellBody.setBorder(Rectangle.NO_BORDER);
        cellBody.addElement(body);
        table.addCell(cellBody);
		document.add(table);
	}
	
	public void generarReporteIndicadoresSupervisor(){
		try {
			if(reporteIndicadoresSupervisor.size() > 0){
				String strNombreArchivo = "reporte_indicador_supervisor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
				float w = PageSize.A4.getWidth();
	            float h = PageSize.A4.getHeight();
	            Rectangle pagesize = new Rectangle(w, h);
				Document document = new Document(pagesize, 15, 15, 10, 10);
				PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
				document.open();
				String titulo = "-      REPORTE INDICADORES SUPERVISOR      -";
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				agregarSeparacion(document);
				generarCuerpoIndicadoresSupervisor(document);
				document.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}
	
	private void generarCuerpoIndicadoresSupervisor(Document document) throws Exception{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
        
        PdfPTable body = new PdfPTable(5);
        body.setWidths(new float[]{0.8f, 1.6f, 0.6f, 0.6f, 0.6f});
        body.setWidthPercentage(100f);
        
        dibujarEtiqueta(body, "CÓDIGO KPI", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "DESCRIPCIÓN", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "REAL", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "OBJETIVO", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "VALOR", Element.ALIGN_CENTER, true, 1);
        
		for (String[] arregloIndicadoresSupervisor : reporteIndicadoresSupervisor) {
			dibujarTexto(body, arregloIndicadoresSupervisor[1], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloIndicadoresSupervisor[2], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresSupervisor[3]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresSupervisor[4]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresSupervisor[5]) + "%", Element.ALIGN_RIGHT, true, 1);
		}
		PdfPCell cellBody = new PdfPCell();
        cellBody.setBorder(Rectangle.NO_BORDER);
        cellBody.addElement(body);
        table.addCell(cellBody);
		document.add(table);
	}
	
	public void generarReporteIndicadoresDetalleSupervisor(){
		try {
			if(reporteIndicadoresDetalleSupervisor.size() > 0){
				String strNombreArchivo = "reporte_indicador_detalle_supervisor_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
				float w = PageSize.A4.getWidth();
	            float h = PageSize.A4.getHeight();
	            Rectangle pagesize = new Rectangle(w, h);
				Document document = new Document(pagesize.rotate(), 15, 15, 10, 10);
				PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
				document.open();
				String titulo = "-      REPORTE INDICADORES DETALLE SUPERVISOR      -";
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				agregarSeparacion(document);
				generarCuerpoIndicadoresDetalleSupervisor(document);
				document.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}
	
	private void generarCuerpoIndicadoresDetalleSupervisor(Document document) throws Exception{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
        
        PdfPTable body = new PdfPTable(26);
        body.setWidths(new float[]{1.2f, 2.4f, 0.9f, 0.9f, 0.6f, 1.0f, 1.0f, 0.7f, 1.1f, 1.1f, 0.9f, 1.0f, 1.0f, 0.8f, 1.2f, 1.2f, 0.9f, 1.2f, 1.2f, 1.0f, 1.3f, 1.3f, 1.0f, 1.1f, 1.1f, 0.8f});
        body.setWidthPercentage(100f);
        
        dibujarEtiqueta(body, "COD. VENDEDOR", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "VENDEDOR", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "V. NETA (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "V. NETA (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "V. NETA", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "F. BÁSICA (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "F. BÁSICA (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "F. BÁSICA", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "F. OBJETIVO (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "F. OBJETIVO (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "F. OBJETIVO", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "C. CLIENTE (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "C. CLIENTE (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "C. CLIENTE", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "COBRANZAS (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "COBRANZAS (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "COBRANZAS", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "D. PENALIZADA (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "D. PENALIZADA (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "D. PENALIZADA", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "V. COD. ROJOS (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "V. COD. ROJOS (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "V. COD. ROJO", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "R. VENTAS (I.R.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "R. VENTAS (I.O.)", Element.ALIGN_CENTER, true, 1);
        dibujarEtiqueta(body, "R. VENTAS", Element.ALIGN_CENTER, true, 1);
        
		for (String[] arregloIndicadoresDetalleSupervisor : reporteIndicadoresDetalleSupervisor) {
			dibujarTexto(body, arregloIndicadoresDetalleSupervisor[1], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloIndicadoresDetalleSupervisor[2], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[3]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[4]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[5]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[6]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[7]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[8]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[9]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[10]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[11]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[12]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[13]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[14]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[15]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[16]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[17]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[18]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[19]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[20]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[21]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[22]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[23]) + "%", Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[24]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[25]), Element.ALIGN_RIGHT, true, 1);
			dibujarTexto(body, Util.formatearNumero(arregloIndicadoresDetalleSupervisor[26]) + "%", Element.ALIGN_RIGHT, true, 1);
		}
		PdfPCell cellBody = new PdfPCell();
        cellBody.setBorder(Rectangle.NO_BORDER);
        cellBody.addElement(body);
        table.addCell(cellBody);
		document.add(table);
	}	
}