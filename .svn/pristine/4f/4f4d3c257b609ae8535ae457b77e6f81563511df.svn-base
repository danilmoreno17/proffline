package com.promesa.internalFrame.pedidos.custom;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//import javax.swing.JButton;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.sincronizacion.bean.BeanSincronizacion;
//import com.promesa.util.Util;

public class ModeloTablaSincrinizacion implements TableModel {

	private List<BeanSincronizacion> elementos = new ArrayList<BeanSincronizacion>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

	@Override
	public int getRowCount() {
		return elementos.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		String[] columnas = { "", "INFORMACIÓN", "REGISTROS", "ÚLTIMA SINCRONIZACIÓN", "TIEMPO (S)"};
		return columnas[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Boolean.class;
		case 1:
		case 3:
			return String.class;
		case 2:
		case 4:
			return Integer.class;
		default:
			return String.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0 || columnIndex == 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return elementos.get(rowIndex).isSeleccionado();
		case 1:
			return elementos.get(rowIndex).getStrInfSinc();
		case 2:
			return elementos.get(rowIndex).getStrNumCantReg();
		case 3:
			return elementos.get(rowIndex).getStrFecHor();
		case 4:
			return elementos.get(rowIndex).getStrNumTie();
		default:
			return Object.class;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			elementos.get(rowIndex).setSeleccionado((Boolean) aValue);
			break;
		case 1:
			elementos.get(rowIndex).setStrInfSinc(aValue.toString());
			break;
		case 2:
			elementos.get(rowIndex).setStrNumCantReg(aValue.toString());
			break;
		case 3:
			elementos.get(rowIndex).setStrFecHor(aValue.toString());
			break;
		case 4:
			elementos.get(rowIndex).setStrNumTie(aValue.toString());
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	public void agregarElemento(BeanSincronizacion elemento) {
		elementos.add(elemento);
	}

	public BeanSincronizacion obtenerElemento(int fila) {
		return elementos.get(fila);
	}

	public void limpiarItems() {
		elementos.clear();
	}

	public List<BeanSincronizacion> getElementos() {
		return elementos;
	}

	public void setElementos(List<BeanSincronizacion> elementos) {
		this.elementos = elementos;
	}
}