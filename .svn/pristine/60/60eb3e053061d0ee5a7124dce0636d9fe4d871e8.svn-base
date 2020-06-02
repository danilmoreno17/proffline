package com.promesa.internarlFrame.devoluciones.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.internalFrame.pedidos.custom.Item;
/*
 * Autor: Marcelo Moyano 
 */
public class ModeloConsultaDevoluciones implements TableModel {

	private List<Item> items = new ArrayList<Item>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();
	
	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		listeners.add(arg0);
	}

	@Override
	public Class<?> getColumnClass(int key) {
		// TODO Auto-generated method stub
		/*switch (key) {
		case 0:
		case 1:
		case 3:
		case 4:
			return String.class;
		case 2:
			return Integer.class;
		case 5:
		case 6:
		case 7:
			return Double.class;
		default:
			return Object.class;
		}*/
		switch (key) {
		case 0:
		case 1:
		case 3:
		case 4:
		case 2:
		case 5:
		case 6:
		case 7:
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	//"", "", "", "", "", "", "", "" 
	public String getColumnName(int key) {
		// TODO Auto-generated method stub
		String strName = "";
		switch (key) {
		case 0:
			strName = "POS.";
			break;
		case 1:
			strName = "MATERIAL";
			break;
		case 2:
			strName = "CANTIDAD";
			break;
		case 3:
			strName = "UM";
			break;
		case 4:
			strName = "DENOMINACIÓN";
			break;
		case 5:
			strName = "PRC. NETO";
			break;
		case 6:
			strName = "VALOR NETO";
			break;
		case 7:
			strName = "PESO UNIT.";
			break;
		}
		return strName;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			return items.get(rowIndex).getPosicion();
		case 1:
			return items.get(rowIndex).getCodigo();
		case 2:
			return items.get(rowIndex).getCantidad();
		case 3:
			return items.get(rowIndex).getUnidad();
		case 4:
			return items.get(rowIndex).getDenominacion();
		case 5:
			return items.get(rowIndex).getPrecioNeto();
		case 6:
			return items.get(rowIndex).getValorNeto();
		case 7:
			return items.get(rowIndex).getPesoUnitario();
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex == 2){
			return true;
		}
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			items.get(rowIndex).setPosicion(aValue.toString());
			break;
		case 1:
			items.get(rowIndex).setCodigo(aValue.toString());
			break;
		case 2:
			items.get(rowIndex).setCantidad(aValue.toString());
			break;
		case 3:
			items.get(rowIndex).setUnidad(aValue.toString());
			break;
		case 4:
			items.get(rowIndex).setDenominacion(aValue.toString());
			break;
		case 5:
			Double precioNeto = Double.parseDouble(aValue.toString());
			items.get(rowIndex).setPrecioNeto(precioNeto);
			break;
		case 6:
			Double valorNeto = Double.parseDouble(aValue.toString());
			items.get(rowIndex).setValorNeto(valorNeto);
			break;
		case 7:
			Double pesoUnitario = Double.parseDouble(aValue.toString());
			items.get(rowIndex).setPesoUnitario(pesoUnitario);
			break;
		}
	}
	
	public void agregarItem(Item item) {
		items.add(item);
	}

	public void limpiarItems() {
		items.clear();
	}
	
	public void eliminarFila(int fila){
		items.remove(fila);
	}
	public Item getItems(int fila){
		return items.get(fila);
	}

}
