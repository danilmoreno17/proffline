package com.promesa.internalFrame.pedidos.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.pedidos.bean.BeanFactura;

public class ModeloTablaFacturaDetalle implements TableModel {

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
		return 15;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "MATERIAL";
		case 1:
			return "DESCRIPCION";
		case 2:
			return "U. VTA.";
		case 3:
			return "MARCA";
		case 4:
			return "C.PED.";
		case 5:
			return "C.FACT.";
		case 6:
			return "P.UNIT.";
		case 7:
			return "D.CAN.";
		case 8:
			return "D.3X";
		case 9:
			return "D.4X";
		case 10:
			return "D.VOL.";
		case 11:
			return "D.COD.";
		case 12:
			return "D.MAN.";
		case 13:
			return "V.NET.";
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
			return f.getMaterial().getIdMaterial();
		case 1:
			return f.getMaterial().getDescripcion();
		case 2:
			return f.getStrUVentas();
		case 3:
			return f.getStrMarca();
		case 4:
			return f.getStrCPedida();
		case 5:
			return f.getStrCFactura();
		case 6:
			return f.getStrPUnitario();
		case 7:
			return f.getStrDCanal();
		case 8:
			return f.getStrD3x();
		case 9:
			return f.getStrD4x();
		case 10:
			return f.getStrDVolumen();
		case 11:
			return f.getStrD5x();
		case 12:
			return f.getStrDManual();
		case 13:
			return f.getStrVNeto();
		case 14:
			return f.getStrPNeto();
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
			f.getMaterial().setIdMaterial(valor);
		case 1:
			f.getMaterial().setDescripcion(valor);
		case 2:
			f.setStrUVentas(valor);
		case 3:
			f.setStrMarca(valor);
		case 4:
			f.setStrCPedida(valor);
		case 5:
			f.setStrCFactura(valor);
		case 6:
			f.setStrPUnitario(valor);
		case 7:
			f.setStrDCanal(valor);
		case 8:
			f.setStrD3x(valor);
		case 9:
			f.setStrD4x(valor);
		case 10:
			f.setStrDVolumen(valor);
		case 11:
			f.setStrD5x(valor);
		case 12:
			f.setStrDManual(valor);
		case 13:
			f.setStrVNeto(valor);
		case 14:
			f.setStrPNeto(valor);
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

	public void agregarFactura(BeanFactura factura) {
		facturas.add(factura);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
}