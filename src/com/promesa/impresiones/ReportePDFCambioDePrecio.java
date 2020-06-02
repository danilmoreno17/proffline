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

public class ReportePDFCambioDePrecio {

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
	private List<String[]> reporteCambiosDePrecioVendedor = new ArrayList<String[]>();
	
	public ReportePDFCambioDePrecio(List<String[]> reporteCambiosDePrecioVendedor) {
		this.reporteCambiosDePrecioVendedor = reporteCambiosDePrecioVendedor;
		inicializarFuentes();
	}

	private void inicializarFuentes() {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(FONTS[4][0], FONTS[4][1], BaseFont.EMBEDDED);
			fuenteTitulo = new Font(bf, 7.0f, Font.BOLD);
			fuenteTextos = new Font(bf, 4.0f, Font.NORMAL);
			fuenteLabels = new Font(bf, 4.0f, Font.BOLD);
		} catch (Exception e) {
			fuenteTitulo = new Font(Font.FontFamily.COURIER, 7.0f, Font.BOLD);
			fuenteTextos = new Font(Font.FontFamily.COURIER, 5.0f, Font.NORMAL);
			fuenteLabels = new Font(Font.FontFamily.COURIER, 4.0f, Font.BOLD);
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
	
	public void generarReporteCambioDePrecio(){
		try {
			if(reporteCambiosDePrecioVendedor.size() > 0){
				String strNombreArchivo = "reporte_indicador_cambio_precio_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
				float w = PageSize.A4.getWidth();
	            float h = PageSize.A4.getHeight();
	            Rectangle pagesize = new Rectangle(w, h);
				Document document = new Document(pagesize.rotate(), 15, 15, 10, 10);
				PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
				document.open();
				String titulo = "-      CAMBIOS/INGRESOS DE MERCADERIA      -";	//MARCELO MOYANO-27/02/2013-10:12
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				agregarSeparacion(document);
				generarCuerpoCambioDePrecio(document);
				document.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}
	
	
	//	METODO DE MARCELO MOYANO
	// 	28/02/2013 - 11:07
	public void generarReporteCambioDePrecioporFecha(String dateInicio,String dateFin){
		try {
			if(reporteCambiosDePrecioVendedor.size() > 0){
				String strNombreArchivo = "reporte_indicador_cambio_precio_" + Util.convierteFechaHoyAFormatoDeImpresionDDMMYYYYHHMMSS(new Date()) + ".pdf";
				float w = PageSize.A4.getWidth();
	            float h = PageSize.A4.getHeight();
	            Rectangle pagesize = new Rectangle(w, h);
	            Document document = new Document(pagesize, 15, 15, 10, 10);
				PdfWriter.getInstance(document, new FileOutputStream(strNombreArchivo));
				document.open();
				String titulo = "-      CAMBIOS/INGRESOS DE MERCADERIA      -";	//MARCELO MOYANO-27/02/2013-10:12
				String fecha = "" + dateInicio + " - " + dateFin;
				agregarTextoNormal(document, titulo, fuenteTitulo, Element.ALIGN_CENTER);
				agregarTextoNormal(document, fecha, fuenteTitulo, Element.ALIGN_CENTER);//MARCELO MOYANO-28/02/2013-10:36
				agregarSeparacion(document);
				generarCuerpoCambioDePrecio(document);
				document.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + strNombreArchivo);
			}
		} catch (Exception e) {
			if (!(e instanceof NullPointerException)) {
				Mensaje.mostrarError(e.getMessage());
			}
		}
	}
	
	private void generarCuerpoCambioDePrecio(Document document) throws Exception{
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		String division = "";
		String categoria = "";
		String familia = "";
		String linea = "";
		String grupo = "";
		String divisionTemporal = "¬";
		String categoriaTemporal = "¬";
		String familiaTemporal = "¬";
		String lineaTemporal = "¬";
		String grupoTemporal = "¬";
        
        PdfPTable body = new PdfPTable(7);
        body.setWidths(new float[]{14.28f, 14.28f, 14.28f, 14.28f, 14.28f, 14.28f, 14.28f});
        body.setWidthPercentage(100f);
        
          dibujarEtiqueta(body, "Código", Element.ALIGN_CENTER, true, 1);
          dibujarEtiqueta(body, "Descripción", Element.ALIGN_CENTER, true, 1);
          dibujarEtiqueta(body, "Und", Element.ALIGN_CENTER, true, 1);
          dibujarEtiqueta(body, "Prec. Actual", Element.ALIGN_CENTER, true, 1);
          dibujarEtiqueta(body, "Observación", Element.ALIGN_CENTER, true, 1);
          dibujarEtiqueta(body, "Validez I", Element.ALIGN_CENTER, true, 1);
          dibujarEtiqueta(body, "Marca", Element.ALIGN_CENTER, true, 1);
		for (String[] arregloCambiosDePrecioVendedor : reporteCambiosDePrecioVendedor) {
			divisionTemporal = arregloCambiosDePrecioVendedor[8];
			categoriaTemporal = arregloCambiosDePrecioVendedor[9];
			familiaTemporal = arregloCambiosDePrecioVendedor[10];
			lineaTemporal = arregloCambiosDePrecioVendedor[11];
			grupoTemporal = arregloCambiosDePrecioVendedor[12];
			if (divisionTemporal.compareTo(division) != 0) {
				division = divisionTemporal;
				dibujarTexto(body, arregloCambiosDePrecioVendedor[8], Element.ALIGN_LEFT, true, 7);
			}
			
			if (categoriaTemporal.compareTo(categoria) != 0) {
				categoria = categoriaTemporal;
				dibujarTexto(body, arregloCambiosDePrecioVendedor[9], Element.ALIGN_LEFT, true, 7);
			}
			
			if (familiaTemporal.compareTo(familia) != 0) {
				familia = familiaTemporal;
				dibujarTexto(body, arregloCambiosDePrecioVendedor[18], Element.ALIGN_LEFT, true, 1);
				dibujarTexto(body, arregloCambiosDePrecioVendedor[10], Element.ALIGN_LEFT, true, 6);
			}
			
			if (lineaTemporal.compareTo(linea) != 0) {
				linea = lineaTemporal;
				dibujarTexto(body, arregloCambiosDePrecioVendedor[19], Element.ALIGN_LEFT, true, 1);
				dibujarTexto(body, arregloCambiosDePrecioVendedor[11], Element.ALIGN_LEFT, true, 6);
			}
			if (grupoTemporal.compareTo(grupo) != 0) {
				grupo = grupoTemporal;
				dibujarTexto(body, arregloCambiosDePrecioVendedor[20], Element.ALIGN_LEFT, true, 1);
				dibujarTexto(body, arregloCambiosDePrecioVendedor[12], Element.ALIGN_LEFT, true, 6);
			}
			dibujarTexto(body, arregloCambiosDePrecioVendedor[21], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloCambiosDePrecioVendedor[22], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloCambiosDePrecioVendedor[36], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloCambiosDePrecioVendedor[33], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloCambiosDePrecioVendedor[1], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloCambiosDePrecioVendedor[30], Element.ALIGN_LEFT, true, 1);
			dibujarTexto(body, arregloCambiosDePrecioVendedor[39], Element.ALIGN_LEFT, true, 1);
		}
		PdfPCell cellBody = new PdfPCell();
        cellBody.setBorder(Rectangle.NO_BORDER);
        cellBody.addElement(body);
        table.addCell(cellBody);
		document.add(table);
	}
}