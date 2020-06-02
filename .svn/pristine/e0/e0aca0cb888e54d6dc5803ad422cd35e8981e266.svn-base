package com.promesa.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class TablaAExcel {

	public void generarReporteAsistencia(String nombreArchivo, TableModel modeloTabla) throws Exception {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		HSSFRow head = hoja.createRow(0);
		CellStyle estiloTitulo;
		estiloTitulo = getEstiloTitulo(libro);
		int numeroColumnas = modeloTabla.getColumnCount();
		HSSFCell celda;
		HSSFRichTextString texto;
		for (int i = 0; i < numeroColumnas; i++) {
			String nombreColumna = modeloTabla.getColumnName(i);
			celda = head.createCell(i);
			celda.setCellStyle(estiloTitulo);
			texto = new HSSFRichTextString(nombreColumna);
			celda.setCellValue(texto);
		}
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			HSSFRow body = hoja.createRow(i + 1);
			for (int j = 0; j < numeroColumnas ; j++) {
				celda = body.createCell(j);
				String strContenido = ("" + modeloTabla.getValueAt(i, j)).toString();
				System.out.println(strContenido);
				if(!strContenido.equals("null")){
					texto = new HSSFRichTextString(strContenido);
				} else {
					texto = new HSSFRichTextString("");
				}
				celda.setCellValue(texto);
			}
		}
		for (int i = 1; i < numeroColumnas - 1; i++) {
			hoja.autoSizeColumn(i);
		}
		FileOutputStream elFichero = new FileOutputStream(nombreArchivo);
		libro.write(elFichero);
		elFichero.close();
		Runtime.getRuntime().exec("cmd.exe /c start " + nombreArchivo);
	}
	
	public void generarReporteOrdenes(String nombreArchivo, TableModel modeloTabla) throws Exception {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		InputStream fis = this.getClass().getClass().getResourceAsStream("/imagenes/logo_promesa.jpg");
		ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
		int b;
		while ((b = fis.read()) != -1) {
			img_bytes.write(b);
		}
		fis.close();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 0, (short) 2, 2);
		int index = libro.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
		HSSFPatriarch patriarch = hoja.createDrawingPatriarch();
		patriarch.createPicture(anchor, index);
		anchor.setAnchorType(2);
		HSSFRow head = hoja.createRow(2);
		CellStyle estiloTitulo;
		estiloTitulo = getEstiloTitulo(libro);
		int numeroColumnas = modeloTabla.getColumnCount();
		HSSFCell celda;
		HSSFRichTextString texto;
		for (int i = 1; i < numeroColumnas - 1; i++) {
			String nombreColumna = modeloTabla.getColumnName(i);
			celda = head.createCell(i - 1);
			celda.setCellStyle(estiloTitulo);
			texto = new HSSFRichTextString(nombreColumna);
			celda.setCellValue(texto);
		}
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			HSSFRow body = hoja.createRow(i + 3);
			for (int j = 1; j < numeroColumnas - 1; j++) {
				celda = body.createCell(j - 1);
				texto = new HSSFRichTextString(modeloTabla.getValueAt(i, j).toString());
				celda.setCellValue(texto);
			}
		}
		for (int i = 1; i < numeroColumnas - 1; i++) {
			hoja.autoSizeColumn(i);
		}
		FileOutputStream elFichero = new FileOutputStream(nombreArchivo);
		libro.write(elFichero);
		elFichero.close();
		Runtime.getRuntime().exec("cmd.exe /c start " + nombreArchivo);
	}

	public void generarReporte(String nombreArchivo, TableModel modeloTabla) throws Exception {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		InputStream fis = this.getClass().getClass().getResourceAsStream("/imagenes/logo_promesa.jpg");
		ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
		int b;
		while ((b = fis.read()) != -1) {
			img_bytes.write(b);
		}
		fis.close();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 0, (short) 2, 2);
		int index = libro.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG);
		HSSFPatriarch patriarch = hoja.createDrawingPatriarch();
		patriarch.createPicture(anchor, index);
		anchor.setAnchorType(2);
		HSSFRow head = hoja.createRow(2);
		CellStyle estiloTitulo;
		estiloTitulo = getEstiloTitulo(libro);
		int numeroColumnas = modeloTabla.getColumnCount();
		HSSFCell celda;
		HSSFRichTextString texto;
		for (int i = 0; i < numeroColumnas; i++) {
			String nombreColumna = modeloTabla.getColumnName(i);
			celda = head.createCell(i);
			celda.setCellStyle(estiloTitulo);
			texto = new HSSFRichTextString(nombreColumna);
			celda.setCellValue(texto);
		}
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			HSSFRow body = hoja.createRow(i + 3);
			for (int j = 0; j < numeroColumnas; j++) {
				celda = body.createCell(j);
				texto = new HSSFRichTextString(modeloTabla.getValueAt(i, j).toString());
				celda.setCellValue(texto);
			}
		}
		for (int i = 0; i < numeroColumnas; i++) {
			hoja.autoSizeColumn(i);
		}
		FileOutputStream elFichero = new FileOutputStream(nombreArchivo);
		libro.write(elFichero);
		elFichero.close();
		Runtime.getRuntime().exec("cmd.exe /c start " + nombreArchivo);
	}
	
	public void generarReporteExcelSistemaInformacion(String nombreArchivo, TableModel modeloTabla) throws Exception {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		HSSFRow head = hoja.createRow(2);
		CellStyle estiloTitulo;
		estiloTitulo = getEstiloTitulo(libro);
		int numeroColumnas = modeloTabla.getColumnCount();
		HSSFCell celda;
		HSSFRichTextString texto;
		for (int i = 0; i < numeroColumnas; i++) {
			String nombreColumna = modeloTabla.getColumnName(i);
			celda = head.createCell(i);
			celda.setCellStyle(estiloTitulo);
			texto = new HSSFRichTextString(nombreColumna);
			celda.setCellValue(texto);
		}
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			HSSFRow body = hoja.createRow(i + 3);
			for (int j = 0; j < numeroColumnas; j++) {
				celda = body.createCell(j);
				CellStyle cellStyle = libro.createCellStyle();
				String valorDeCelda = modeloTabla.getValueAt(i, j).toString();
				if(!valorDeCelda.equals("")){
					if(valorDeCelda.endsWith("%")){
						cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
					}else{
						if(j == 0){
							cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						}else{
							try {
								@SuppressWarnings("unused")
								double valorDecimal = Double.parseDouble(valorDeCelda);
								cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
							} catch (NumberFormatException e) {
								cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
							}
						}
					}
				}else{
					cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				}
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(valorDeCelda);
				celda.setCellValue(texto);
			}
		}
		for (int i = 0; i < numeroColumnas; i++) {
			hoja.autoSizeColumn(i);
		}
		FileOutputStream elFichero = new FileOutputStream(nombreArchivo);
		libro.write(elFichero);
		elFichero.close();
		Runtime.getRuntime().exec("cmd.exe /c start " + nombreArchivo);
	}
	
	//	Marcelo Moyano
	public void generarReporteExcelCambioDePrecioVendedor(String nombreArchivo, String DateInicio, String DateFin, List<String[]> reporteCambioPrecio) throws Exception {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		HSSFRow head = hoja.createRow(2);
		CellStyle estiloTitulo = getEstiloTitulo(libro);
		String[] nombresColumnas = { "CÓDIGO", "DESCRIPCIÓN", "UND", "PREC. ACTUAL", "OBSERVACIÓN", "VALIDEZ I", "MARCA" };
		int numeroColumnas = nombresColumnas.length;
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
		final Font font = libro.createFont();
		
		HSSFCell celda;
		HSSFRichTextString texto;
		
		HSSFRow titulos = hoja.createRow(0);
		CellStyle celdaStyle;
		
		//	Generar Titulo de la Tabla
		celda = titulos.createCell(0);
		celdaStyle = libro.createCellStyle();
		celdaStyle.setAlignment(CellStyle.ALIGN_CENTER);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		celdaStyle.setFont(font);
		celda.setCellStyle(celdaStyle);
		texto = new HSSFRichTextString("-	CAMBIOS/INGRESOS DE MERCADERIA	-");
		celda.setCellValue(texto);
		//Marcelo Moyano
		
		//	Generar Fecha de consulta de tabla
		HSSFRow fechas = hoja.createRow(1);
		celda = fechas.createCell(0);
		celdaStyle = libro.createCellStyle();
		celdaStyle.setAlignment(CellStyle.ALIGN_CENTER);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		celdaStyle.setFont(font);
		celda.setCellStyle(celdaStyle);
		texto = new HSSFRichTextString(DateInicio + " - " + DateFin);
		celda.setCellValue(texto);
		// Marcelo Moyano
		for (int i = 0; i < numeroColumnas; i++) {
			celda = head.createCell(i);
			celda.setCellStyle(estiloTitulo);
			texto = new HSSFRichTextString(nombresColumnas[i]);
			celda.setCellValue(texto);
		}
		int j = 0;
		for (String[] strings : reporteCambioPrecio)
		 {
			divisionTemporal = strings[8];
			categoriaTemporal = strings[9];
			familiaTemporal = strings[10];
			lineaTemporal = strings[11];
			grupoTemporal = strings[12];
			//	Marcelo Moyano
			if (divisionTemporal.compareTo(division) != 0) {
				division = divisionTemporal;
				HSSFRow body = hoja.createRow(j + 3);
				celda = body.createCell(0);
				CellStyle cellStyle = libro.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[8]);
				celda.setCellValue(texto);
			}else { j--;}
			if (categoriaTemporal.compareTo(categoria) != 0) {
				categoria = categoriaTemporal;
				HSSFRow body = hoja.createRow(j + 4);
				celda = body.createCell(0);
				CellStyle cellStyle = libro.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[9]);
				celda.setCellValue(texto);
			}else { j--;}
			if (familiaTemporal.compareTo(familia) != 0) {
				familia = familiaTemporal;
				HSSFRow body = hoja.createRow(j + 5);
				celda = body.createCell(0);
				CellStyle cellStyle = libro.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[18]);
				celda.setCellValue(texto);
				
				celda = body.createCell(1);
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[10]);
				celda.setCellValue(texto);
			}else { j--;}
			if (lineaTemporal.compareTo(linea) != 0) {
				linea = lineaTemporal;
				HSSFRow body = hoja.createRow(j + 6);
				celda = body.createCell(0);
				CellStyle cellStyle = libro.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[19]);
				celda.setCellValue(texto);
				
				celda = body.createCell(1);
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[11]);
				celda.setCellValue(texto);
			}else { j--;}
			if (grupoTemporal.compareTo(grupo) != 0) {
				grupo = grupoTemporal;
				HSSFRow body = hoja.createRow(j + 7);
				celda = body.createCell(0);
				CellStyle cellStyle = libro.createCellStyle();
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[20]);
				celda.setCellValue(texto);
				
				celda = body.createCell(1);
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				celda.setCellStyle(cellStyle);
				texto = new HSSFRichTextString(strings[12]);
				celda.setCellValue(texto);
			}else { j--;}
			HSSFRow body = hoja.createRow(j + 8);
			celda = body.createCell(0);
			CellStyle cellStyle = libro.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[21]);
			celda.setCellValue(texto);
			
			celda = body.createCell(1);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[22]);
			celda.setCellValue(texto);
			
			celda = body.createCell(2);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[36]);
			celda.setCellValue(texto);
			
			celda = body.createCell(3);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[33]);
			celda.setCellValue(texto);
			
			celda = body.createCell(4);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[1]);
			celda.setCellValue(texto);
			
			celda = body.createCell(5);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[30]);
			celda.setCellValue(texto);
			
			celda = body.createCell(6);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			celda.setCellStyle(cellStyle);
			texto = new HSSFRichTextString(strings[39]);
			celda.setCellValue(texto);
			// Marcelo Moyano
			j = j + 6;
		 }
		for (int i = 0; i < numeroColumnas; i++) {
			hoja.autoSizeColumn(i);
		}
		FileOutputStream elFichero = new FileOutputStream(nombreArchivo);
		libro.write(elFichero);
		elFichero.close();
		Runtime.getRuntime().exec("cmd.exe /c start " + nombreArchivo);
	}
	// Marcelo Moyano
	private CellStyle getEstiloTitulo(HSSFWorkbook libro) {
		final CellStyle cellStyle = libro.createCellStyle();
		final Font cellFont = libro.createFont();
		cellFont.setColor(IndexedColors.WHITE.getIndex());
		cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(cellFont);
		cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}
}