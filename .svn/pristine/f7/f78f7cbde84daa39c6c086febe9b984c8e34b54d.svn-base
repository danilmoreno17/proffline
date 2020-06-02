package com.promesa.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class LeerHojaExcel {
	private List<HashMap<String, String>> listaDataSet = null;
	public LeerHojaExcel(){}

	@SuppressWarnings("rawtypes")
	public void LeerHojaExcel1(String direccion, int inicioColumna, int finColumna, HashMap<Integer, String> lblColum){
		HashMap<Integer, String> lblColumCell = new HashMap<Integer, String>();
		HashMap<String, String> dataSet = null;
		Cell indRLeeAux = null;
		Cell indRLeeAux2 = null;
		String labelColumna = "";
		String dataColumna = "";
		int f = 0;
		String bandera = " ";
		listaDataSet = new ArrayList<HashMap<String, String>>();
		try {
			Workbook libro = Workbook.getWorkbook(new File(direccion));
			Sheet hoja1 = libro.getSheet(0);
			for (int c0 = inicioColumna; c0 < finColumna; c0++) {
				indRLeeAux = hoja1.getCell(c0, 0);
				labelColumna = indRLeeAux.getContents();
				for (int c = 0; c < lblColum.size(); c++) {
					if (labelColumna.trim().equals(lblColum.get(c).trim())) {
						lblColumCell.put(c0, labelColumna.trim());
					}
				}
			}
			while (bandera != null) {
				dataSet = new HashMap<String, String>();
				f++;
				Iterator itLblC = lblColumCell.entrySet().iterator();
				while (itLblC.hasNext()) {
					Map.Entry e = (Map.Entry) itLblC.next();
					try {
						indRLeeAux = hoja1.getCell(Integer.parseInt(e.getKey().toString()), f);
						dataColumna = indRLeeAux.getContents();
					} catch (Exception exec) {
						Util.mostrarExcepcion(exec);
						dataColumna = " ";
					}
					try {
						indRLeeAux2 = hoja1.getCell(Integer.parseInt(e.getKey().toString()), f + 1);
						bandera = indRLeeAux2.getContents();
					} catch (Exception exec) {
						bandera = null;
//						Util.mostrarExcepcion(exec);
					}
					dataSet.put(e.getValue().toString(), dataColumna);
				}
				listaDataSet.add(dataSet);
				if (bandera == null || bandera.trim().length() == 0) {
					break;
				}
			}
		} catch (Exception eP) {
			Util.mostrarExcepcion(eP);
		}
	}

	public List<HashMap<String, String>> getListaDataSet() {
		return listaDataSet;
	}
}