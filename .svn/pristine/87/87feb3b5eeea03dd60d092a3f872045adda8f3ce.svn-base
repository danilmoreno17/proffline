package com.promesa.internalFrame.pedidos.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.pedidos.bean.BeanFactura;

public class ModeloTablaFacturas implements TableModel {
	private List<BeanFactura> facturas = new ArrayList<BeanFactura>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 14;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "N° FACT.";
		case 1://	Marcelo Moyano 12/05/2013
			return "FACT. SRI";//	Marcelo Moyano 12/05/2013
		case 2:
			return "MATERIAL";
		case 3:
			return "DESCRIPCIÓN";
		case 4:
			return "C.CONF";
		case 5:
			return "V.NET.";
		case 6:
			return "F.FAC.";
		case 7:
			return "P.UNI.";
		case 8:
			return "D.CAN.";
		case 9:
			return "D.3X";
		case 10:
			return "D.4X";
		case 11:
			return "D.VOL.";
		case 12:
			return "D.COD.";
		case 13:
			return "D.MAN.";
		case 14:
			return "P.NET.";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return facturas.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BeanFactura f = facturas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return f.getStrNFactura();
		case 1:
			return f.getStrFacturaSRI();
		case 2:
			return f.getMaterial().getIdMaterial();
		case 3:
			return f.getMaterial().getDescripcion();
		case 4:
			return f.getStrCFactura();
		case 5:
			return f.getStrVNeto();
		case 6:
			return f.getStrFFactura();
		case 7:
			return f.getStrPUnitario();
		case 8:
			return f.getStrDCanal();
		case 9:
			return f.getStrD3x();
		case 10:
			return f.getStrD4x();
		case 11:
			return f.getStrDVolumen();
		case 12:
			return f.getStrD5x();
		case 13:
			return f.getStrDManual();
		case 14:
			return f.getStrCFactura();
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		BeanFactura f = facturas.get(rowIndex);
		String valor = ("" + aValue).toString();
		switch (columnIndex) {
		case 0:
			f.setStrNFactura(valor);
		case 1:
			f.setStrFacturaSRI(valor);
		case 2:
			f.getMaterial().setIdMaterial(valor);
		case 3:
			f.getMaterial().setDescripcion(valor);
		case 4:
			f.setStrCFactura(valor);
		case 5:
			f.setStrVNeto(valor);
		case 6:
			f.setStrFFactura(valor);
		case 7:
			f.setStrPUnitario(valor);
		case 8:
			f.setStrDCanal(valor);
		case 9:
			f.setStrD3x(valor);
		case 10:
			f.setStrD4x(valor);
		case 11:
			f.setStrDVolumen(valor);
		case 12:
			f.setStrD5x(valor);
		case 13:
			f.setStrDManual(valor);
		case 14:
			f.setStrVNeto(valor);
		}
		TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
		avisaSuscriptores(evento);
	}

	private void avisaSuscriptores(TableModelEvent evento) {
		int i;
		for (i = 0; i < listeners.size(); i++) {
			((TableModelListener) listeners.get(i)).tableChanged(evento);
		}
	}

	public void limpiar() {
		facturas.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	public BeanFactura obtenerFactura(int index) {
		return facturas.get(index);
	}
	
	public int obtenerCantidadFacturas() {
		return facturas.size();
	}

	public void agregarFactura(BeanFactura factura) {
		facturas.add(factura);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
}