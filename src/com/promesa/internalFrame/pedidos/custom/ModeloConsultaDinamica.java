package com.promesa.internalFrame.pedidos.custom;

//import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.util.Util;

public class ModeloConsultaDinamica implements TableModel {

	private List<Item> items = new ArrayList<Item>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 4:
		case 5:
			return Long.class;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 12:
		case 13:
		case 14:
			return Double.class;
		case 1:
		case 2:
		case 3:
		case 11:
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		return 14;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "CÓDIGO";
		case 1:
			return "DESCRIPCIÓN LARGA";
		case 2:
			return "UN";
		case 3:
			return "MARCA";
		case 4:
			return "CANT.";
		case 5:
			return "STOCK";
		case 6:
			return "PRC.NETO";
		case 7:
			return "PRC.+IVA";
		case 8:
			return "VAL. NETO";
		case 9:
			return "PRC.LISTA";
		case 10:
			return "TIP.MAT.";
		case 11:
			return "DESCRIPCIÓN CORTA";
		case 12:
			return "CANT.CONF.";
		case 13:
			return "ACUM. USD";
		case 14:
			return "PROM. UND";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Long lvalue = 0l;
		switch (columnIndex) {
		case 0:
			return items.get(rowIndex).getCodigo();
		case 1:
			return items.get(rowIndex).getDenominacion();
		case 2:
			return items.get(rowIndex).getUnidad();
		case 3:
			return items.get(rowIndex).getMarca();
		case 4:
			try {
				lvalue = Long.parseLong(items.get(rowIndex).getCantidad());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				lvalue = 0l;
			}
			return lvalue;
		case 5:
			try {
				lvalue = Long.parseLong(items.get(rowIndex).getStock());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				lvalue = 0l;
			}
			return lvalue;
		case 6:
			return items.get(rowIndex).getPrecioNeto();
		case 7:
			return items.get(rowIndex).getPrecioIVA();
		case 8:
			return items.get(rowIndex).getPrecioCompra();
		case 9:
			return items.get(rowIndex).getPrecioLista();
		case 10:
			return items.get(rowIndex).getTipoMaterial();
		case 11:
			return items.get(rowIndex).getDenominacionCorta();
		case 12:
			return items.get(rowIndex).getCantidadConfirmada();
		case 13:
			String strValorAcumulado = items.get(rowIndex).getStrValorAcumulado().trim();
			Double valorAcumulado = 0d;
			if (strValorAcumulado != null) {
				try {
					valorAcumulado = Double.parseDouble(strValorAcumulado);
				} catch (Exception e) {
					valorAcumulado = 0d;
				}
			}
			return valorAcumulado;
		case 14:
			String strValorPromedio = items.get(rowIndex).getStrValorPromedio().trim();
			Double valorPromedio = 0d;
			if (strValorPromedio != null) {
				try {
					valorPromedio = Double.parseDouble(strValorPromedio);
				} catch (Exception e) {
					valorPromedio = 0d;
				}
			}
			return valorPromedio;
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 4;
//		return columnIndex == 10;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Double valor = 0d;
		switch (columnIndex) {
		case 0:
			items.get(rowIndex).setCodigo(aValue.toString());
			break;
		case 1:
			items.get(rowIndex).setDenominacion(aValue.toString());
			break;
		case 2:
			items.get(rowIndex).setUnidad(aValue.toString());
			break;
		case 3:
			items.get(rowIndex).setMarca(aValue.toString());
			break;
		case 4:
			items.get(rowIndex).setCantidad(aValue.toString());
			break;
		case 5:
			items.get(rowIndex).setStock(aValue.toString());
			break;
		case 6:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioNeto(valor);
			break;
		case 7:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioIVA(valor);
			break;
		case 8:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioCompra(valor);
			break;
		case 9:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioLista(valor);
			break;
		case 10:
			items.get(rowIndex).setTipoMaterial(aValue.toString());
			break;
		case 11:
			items.get(rowIndex).setDenominacionCorta(aValue.toString());
			break;
		case 12:
			items.get(rowIndex).setCantidadConfirmada(aValue.toString());
			break;
		case 13:
			items.get(rowIndex).setStrValorAcumulado(aValue.toString());
			break;
		case 14:
			items.get(rowIndex).setStrValorPromedio(aValue.toString());
			break;
		}
	}

	public void agregarItem(Item item) {
		items.add(item);
	}

	public void limpiarItems() {
		items.clear();
	}
}