package com.promesa.internalFrame.pedidos.custom;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.promesa.util.Util;

public class ModeloConsultaDinamica2 implements TableModel{
	
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
		case 10:
			return Long.class;
		case 6:
		case 7:
		case 8:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 18:
		case 20:
		case 21:
		case 22:
			return Double.class;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 9:
		case 17:
		case 19:
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		return 23;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "CODIGO";
		case 1:
			return "DESCRIPCIÓN/LARGA";
		case 2:
			return "UN";
		case 3:
			return "MARCA";
		case 4:
			return "TPO";
		case 5:
		case 9:
			return "";
		case 6:
			return "LISTA";
		case 7:
			return "NETO";
		case 8:
			return "+IVA";
		case 10:
			return "PEDIDO";
		case 11:
			return "DESC.";
		case 12:
			return "CONFIRM.";
		case 13:
			return "STOCK";
		case 14:
			return "TOTAL";
		case 15:
			return "VTA/USD";
//			return "TOT. ACM.";
		case 16:
			return "VTA/UN.VTA";
//			return "PMD/UNM";
		case 17:
			return "FECHA";
		case 18:
			return "VTA/USD";
		case 19:
			return "%CUMPL.";
		case 20:
			return "SUG. MES";
		case 21:
			return "FAC. MES";
		case 22:
			return "RESTANTE";
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
			return items.get(rowIndex).getTipoMaterial();
		case 5:
		case 9:
			return items.get(rowIndex).getStrVacio();
		case 6:
			return items.get(rowIndex).getPrecioLista();
		case 7:
			return items.get(rowIndex).getPrecioNeto();
		case 8:
			return items.get(rowIndex).getPrecioIVA();
		case 10:
			try {
				String cantidad = items.get(rowIndex).getCantidad();
				if(cantidad.equals("")){
					cantidad = "0";
				}
				lvalue = Long.parseLong(cantidad);
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				lvalue = 0l;
			}
			return lvalue;
		case 11:
			String strDescuentoManuales = items.get(rowIndex).getDescuentosManuales().trim();
			double descuentosManuales = 0d;
			try {
				descuentosManuales = Double.parseDouble(strDescuentoManuales);
			} catch (Exception e) {
				descuentosManuales = 0d;
			}
			return descuentosManuales;
		case 12:
			return items.get(rowIndex).getCantidadConfirmada();
		case 13:		
			return items.get(rowIndex).getStock();
		case 14:
			try {
				return items.get(rowIndex).getValorNeto();
			} catch (Exception e) {
				return 0d;
			}
		case 15:
			String strValorAcumulado = items.get(rowIndex).getStrValorAcumulado().trim();
			Double valorAcumulado = 0d;
			if (strValorAcumulado != null) {
				try {
					valorAcumulado = Double.parseDouble(strValorAcumulado);
				} catch (Exception e) {
					valorAcumulado = 0d;
				}
			}
			return Util.formatearNumero(valorAcumulado);
		case 16:
			String strValorPromedio = items.get(rowIndex).getStrValorPromedio().trim();
//			return strValorPromedio;
			Double valorPromedio = 0d;
			if (strValorPromedio != null) {
				try {
					valorPromedio = Double.parseDouble(strValorPromedio);
				} catch (Exception e) {
					valorPromedio = 0d;
				}
			}
			return valorPromedio.intValue();
//			return Util.formatearNumero(valorPromedio);
		case 17:
			return items.get(rowIndex).getFecha();
		case 18:
			return items.get(rowIndex).getDblValorReal();
		case 19:
			return  String.format("%.2f",items.get(rowIndex).getDblCumplimiento())+"%";
		case 20:
			String strValorSuge = items.get(rowIndex).getStrCantSugerido().trim();
//			return strValorPromedio;
			Double valorSuge = 0d;
			if (strValorSuge != null) {
				try {
					valorSuge = Double.parseDouble(strValorSuge);
				} catch (Exception e) {
					valorSuge = 0d;
				}
			}
			return valorSuge.intValue();
		case 21:
			String strValorFact = items.get(rowIndex).getStrCantFacturado().trim();
//			return strValorPromedio;
			Double valorFact = 0d;
			if (strValorFact != null) {
				try {
					valorFact = Double.parseDouble(strValorFact);
				} catch (Exception e) {
					valorFact = 0d;
				}
			}
			return valorFact.intValue();
		case 22:
			String strValorReal = items.get(rowIndex).getStrCantRestante().trim();
//			return strValorPromedio;
			Double valorRest = 0d;
			if (strValorReal != null) {
				try {
					valorRest = Double.parseDouble(strValorReal);
				} catch (Exception e) {
					valorRest = 0d;
				}
			}
			return valorRest.intValue();
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 10 || columnIndex == 11 ){
			return true;
		} else {
			return false;
		}
//		return columnIndex == 10;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Double valor = 0d;
//		int intValor;
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
			items.get(rowIndex).setTipoMaterial(aValue.toString());
			break;
		case 5:
			items.get(rowIndex).setStrVacio("");
			break;
		case 9:
			items.get(rowIndex).setStrVacio("");
			break;
		case 6:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioLista(valor);
			break;
		case 7:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioNeto(valor);
			break;
		case 8:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setPrecioIVA(valor);
			break;
		case 10:
			String cantidad = aValue.toString();
			if(cantidad.equals("")){
				cantidad = "0";
			}
			items.get(rowIndex).setCantidad(aValue.toString());
			break;
		case 11:
			String descuento = aValue.toString();
			if(descuento.equals("")){
				descuento = "0.00";
			}
			items.get(rowIndex).setDescuentosManuales(descuento);
			break;
		case 12:
			items.get(rowIndex).setCantidadConfirmada(aValue.toString());
			break;
		case 13:
			items.get(rowIndex).setStock(aValue.toString());
			break;
		case 14:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setValorNeto(valor);
			break;
		case 15:
			items.get(rowIndex).setStrValorAcumulado(aValue.toString());
			break;
		case 16:
			items.get(rowIndex).setStrValorPromedio(valor.toString());
			break;
		case 17:
			items.get(rowIndex).setFecha(aValue.toString());
			break;	
		case 18:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setDblValorReal(valor);
			break;
		case 19:
			try {
				valor = Double.parseDouble(aValue.toString());
			} catch (Exception e) {
				Util.mostrarExcepcion(e);
				valor = 0d;
			}
			items.get(rowIndex).setDblCumplimiento(valor);
			break;
		case 20:
			items.get(rowIndex).setStrCantSugerido(valor.toString());
			break;
		case 21:
			items.get(rowIndex).setStrCantFacturado(valor.toString());
			break;
		case 22:
			items.get(rowIndex).setStrCantRestante(valor.toString());
			break;
		}
	}
	
	public void agregarItem(Item item) {
		items.add(item);
	}

	public void limpiarItems() {
		items.clear();
	}
	
	public Object[] identificadorClumnas(){
		Object idColumnas[] = new Object[]{"CODIGO", "DESCRIPCIÓN/LARGA", "UN", "MARCE", "TPO", " ", "LISTA","NETO",
				"+IVA", " ", "PEDIDO", "% DSCT","CONFIRM.","STOCK","TOTAL", "VTA/USD"/*"TOT. ACM."*/, "VTA/UN.VTA"/*"PMD/UND"*/,"FECHA","VTA/USD","%CUMPL.","CANT. SUG. MES","CANT. FAC. MES","CANT. RESTANTE"};
		;
		return idColumnas;
	}
	
	public List<Item> obtenerTodosItemsNoSimulados() {
		List<Item> temp = new ArrayList<Item>();
		for (Item it : items) {
			if (!it.isSimulado() && !it.getDenominacion().isEmpty()) {
				temp.add(it);
			}
		}
		return temp;
	}
	
	
	
	public Item obtenerItem(int fila) {
		if (fila > -1) {
			return items.get(fila);
		}
		return null;
	}
	
}