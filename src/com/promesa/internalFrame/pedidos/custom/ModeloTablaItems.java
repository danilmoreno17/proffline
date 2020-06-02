package com.promesa.internalFrame.pedidos.custom;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.bean.BeanDato;
//import com.promesa.bean.BeanDato;
//import com.promesa.main.Promesa;
//import com.promesa.util.Constante;
import com.promesa.main.Promesa;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.impl.SqlMaterialImpl;
import com.promesa.sap.SPedidos;
import com.promesa.util.Constante;
import com.promesa.util.Mensaje;
import com.promesa.util.Util;

public class ModeloTablaItems implements TableModel {

	private List<Item> items = new ArrayList<Item>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();
	private Double valorNeto;

	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		return 12;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "E";
		case 1:
			return "MATERIAL";
		case 2:
			return "CANTIDAD";
		case 3:
			return "CANT.CONF";
		case 4:
			return "STOCK";
		case 5:
			return "UM";
		case 6:
			return "% DESC";
		case 7:
			return "PROX. ING.";
		case 8:
			return "DENOMINACIÓN";
		case 9:
			return "PREC. NETO";
		case 10:
			return "VALOR NETO";
		case 11:
			return "TPO";	
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
		Item item = items.get(rowIndex);
		if(rowIndex == -1){
			return null;
		}
		switch (columnIndex) {
		case 0:
			//return item.getPosicion();
			return "";
		case 1:
			return item.getCodigo();
		case 2:
			if (item.getTipo() == 2 || item.getTipo() == 3) {
				return "" + item.getCantidadAMostrar();
			} else {
				String cantidad = "";
				try {
					cantidad = "" + (int) Double.parseDouble(item.getCantidad());
				} catch (Exception e) {
					cantidad = "0";
				}
				return cantidad;
			}
		case 3:
			String cantidad = item.getCantidadConfirmada();
			try {
				cantidad = "" + (int) Double.parseDouble(cantidad);
			} catch (Exception e) {
				cantidad = item.getCantidadConfirmada();
			}
			return cantidad;
		case 4:
			String stock = item.getStock();
			try {
				stock = "" + (int) Double.parseDouble(stock);
			} catch (Exception e) {
				stock = item.getStock();
			}
			return stock;
		case 5:
			return item.getUnidad();
		case 6:
			return Util.formatearNumero(item.getPorcentajeDescuento());
		case 7:
			String fecha="";
			if(item.getStrFech_Ing()!=null) {
				fecha=item.getStrFech_Ing();
			}
			return fecha;
		case 8:
			return item.getDenominacion();
		case 9:
			return Util.formatearNumeroTresDigitos(item.getPrecioNeto());
		case 10:
			return Util.formatearNumeroTresDigitos(item.getValorNeto());
		case 11:
			return item.getTipoMaterial();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		Item item = items.get(rowIndex);
		if (item.isEditable()) {
			switch (columnIndex) {
			case 1:
			case 2:
			case 6:
				return true;
			default:
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Item item = items.get(rowIndex);
		if (rowIndex > -1) {
			switch (columnIndex) {
			case 0:
				item.setPosicion(aValue.toString());
				break;
			case 1:
				item.setCodigo(aValue.toString());
				break;
			case 2:
				String cantidad = aValue.toString();
				if(cantidad.equals("")){
					cantidad = "0";
				}
				item.setCantidad(cantidad);
				break;
			case 3:
				item.setCantidadConfirmada(aValue.toString());
				break;
			case 4:
				item.setStock(aValue.toString());
				break;
			case 5:
				item.setUnidad(aValue.toString());
				break;
			case 6:
				String valor = aValue.toString();
				if(valor.equals("")){
					valor = "0.00";
				}
				item.setPorcentajeDescuento(valor);
				break;
			case 7:
				if(!aValue.toString().equals("null"))
					item.setStrFech_Ing(aValue.toString());
				else
					item.setStrFech_Ing("");
				break;
			case 8:
				item.setDenominacion(aValue.toString());
				break;
			case 9:
				Double precioNeto = Double.parseDouble(aValue.toString());
				item.setPrecioNeto(precioNeto);
				break;
			case 10:
				Double valorNeto = Double.parseDouble(aValue.toString());
				item.setValorNeto(valorNeto);
				break;
			case 11:
				if(aValue.toString() != null){
					item.setTipoMaterial(aValue.toString());
				}
				break;
			}
			TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
			avisaSuscriptores(evento);
		}
	}

	private void avisaSuscriptores(TableModelEvent evento) {
		int i;
		for (i = 0; i < listeners.size(); i++) {
			((TableModelListener) listeners.get(i)).tableChanged(evento);
		}
	}

	public boolean yaEstaAgregado(String codigo, int posicion) {
		int posTemp = 0;
		for (Item it : items) {
			if (it.getCodigo().compareTo(codigo) == 0 && it.getTipo() != 2 && !codigo.isEmpty() && (posTemp != posicion || posicion < 0)) {
				Mensaje.mostrarWarning("El material ya ha sido agregado en la posición " + it.getPosicion());
				return true;
			}
			posTemp++;
		}
		return false;
	}
	public boolean yaEstaAgregado3(String codigo) {
		for (Item it : items) {
			if (it.getCodigo().compareTo(codigo) == 0 && !codigo.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	public boolean yaEstaAgregado4(String codigo, int posicion) {
		int posTemp = 0;
		for (Item it : items) {
			if (it.getCodigo().compareTo(codigo) == 0 && !codigo.isEmpty()&&!it.getDenominacion().equals("") && (posTemp != posicion || posicion < 0)) {
				Mensaje.mostrarWarning("El material ya ha sido agregado en la posición " + it.getPosicion());
				return true;
			}
			posTemp++;
		}
		return false;
	}
	public boolean yaEstaAgregado5(String codigo) {
		for (Item it : items) {
			if (it.getCodigo().compareTo(codigo) == 0 && !codigo.isEmpty()&&!it.getDenominacion().equals("")) {
				return true;
			}
		}
		return false;
	}
	public boolean yaEstaAgregado2(String codigo, int posicion) {
		int posTemp = 0;
		for (Item it : items) {
			if (it.getCodigo().compareTo(codigo) == 0 && it.getTipo() != 2 && !codigo.isEmpty() && (posTemp != posicion || posicion < 0)) {
				return true;
			}
			posTemp++;
		}
		return false;
	}
	
	public int yaEstaAgregadoDeConsultaDinamica(String codigo, int cantidad) {
		int posTemp = 0;
		for (Item it : items) {
			Integer k = (int) Double.parseDouble(it.getCantidad());
			if (it.getCodigo().compareTo(codigo) == 0 && it.getTipo() != 2 && !codigo.isEmpty() && k == cantidad) {
				Mensaje.mostrarWarning("El material ya ha sido agregado en la posición " + it.getPosicion());
				return -2; // Ya está agregado y no se modifica.
			} else if (it.getCodigo().compareTo(codigo) == 0 && it.getTipo() != 2 && !codigo.isEmpty() && k != cantidad) {
				return posTemp; // Ya está agregado pero con diferente cantidad, sí // se modifica.
			}
			posTemp++;
		}
		return -1; // No está agregado, se agregará al último.
	}
	public int posicionDisponible(){
		for (int i = 0; i < items.size(); i++) {
			Item temp = items.get(i);
			if (temp.getCodigo().trim().isEmpty() || temp.getDenominacion().trim().isEmpty()) {
				return i;
			}
		}
		return 0;
	}
	public int agregarItem(Item item, int posicion) {
		int longitudLista = items.size();
		if (items.size() < 300) {
			String location = "0";
			int tipo = -1;
			if (!items.isEmpty()) {
				Item lastItem = items.get(longitudLista - 1);
				if (lastItem != null) {
					tipo = lastItem.getTipo();
					location = lastItem.getPosicion();
				}
			}
			////////////////////////////////////////////////
			if (posicion == -1) { // Agrega al último
				String pos = item.getPosicion();
				if (pos.isEmpty()) {
					if(location.equals("")){
						if(longitudLista<10)
						location="0000"+longitudLista+"0";
						else
							location="000"+longitudLista+"0";
					}
						
					item.setPosicion(Util.generarPosicion(location, tipo));
				}				
				consultarStock(item,item);
				items.add(item);
			} else if (posicion == -2) { // Agrega en la primera posicion libre
				boolean estaAgredado = false;
				for (int i = 0; i < items.size(); i++) {
					Item temp = items.get(i);
					if (temp.getCodigo().trim().isEmpty() || temp.getDenominacion().trim().isEmpty()) {
						item.setPosicion(temp.getPosicion());
						consultarStock(item,item);
						items.set(i, item);
						estaAgredado = true;
						break;
					}
				}
				if (!estaAgredado) {
					items.add(item);
				}
			} else {
				if (item.getTipo() == 0) { // Item normal
					consultarStock(item,item);
					items.set(posicion, item);
				} else if (item.getTipo() == 1) { // Combo
					// Si existe un combo, este debe ser eliminado
					Item temporalASerEliminado = items.get(posicion);
					if (temporalASerEliminado.getTipo() == 1) {
						borrarItem(posicion);
					}

					List<Item> detalles = item.getCombos();
					Item aux = items.get(posicion);
					item.setPosicion(aux.getPosicion());
					items.set(posicion, item);
					Double pn = 0d;
					Double vn = 0d;
					for (Item detalle : detalles) {
						detalle.setPadre(item);
						detalle.setTipo(2);
						pn += detalle.getPrecioNeto();
						vn += detalle.getValorNeto();
						detalle.setSimulado(true);
						consultarStock(item,detalle);
						items.add(++posicion, detalle);
					}
					pn = Math.round(pn * 1000) / 1000.0d;
					vn = Math.round(vn * 1000) / 1000.0d;
					item.setPrecioNeto(pn);
					item.setStrFech_Ing("");
					item.setValorNeto(vn);
				}
				actualizarPosiciones();
			}
			TableModelEvent evento;
			evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
			avisaSuscriptores(evento);
		} else {
			Mensaje.mostrarWarning("Se ha alcanzado el máximo  número de filas.");
		}
		return items.indexOf(item);
	}
	
	private void consultarStock(Item itPadre, Item itHijo) {
		if(!itHijo.getCodigo().equals("")) {
			SqlMaterialImpl sqlMateriales = new SqlMaterialImpl();
			if(itPadre.getTipo() != 1) {
				BeanDato usuario = Promesa.datose.get(0);
				if (usuario.getStrModo().equals("1")) {// DESDE SAP
					SPedidos objSAP = new SPedidos();
					List<BeanMaterial> materiales =objSAP.listaMaterialesStock(itHijo.getCodigo());
					if(materiales.size()>0)
						itHijo.setStock(materiales.get(0).getStock());
					else
						itHijo.setStock(sqlMateriales.obtenerStockMaterial(itHijo.getCodigo()));
				}else {
					itHijo.setStock(sqlMateriales.obtenerStockMaterial(itHijo.getCodigo()));
				}
			}else {
				itHijo.setStock(sqlMateriales.obtenerStockMaterial(itHijo.getCodigo()));
			}
		}
	}
	
	public void deshabilitarTodo() {
		for (Item item : items) {
			item.setEditable(false);
		}
	}

	public void borrarItem(int fila) {
		Item item = items.get(fila);
		if (item.getTipo() == 0) {
			for (Item it : items) {
				if (it.getPadre() == item && it.getTipo() == 2) {
					it.setRemover(true);
				} else {
					it.setRemover(false);
				}
			}
			items.remove(fila);
			List<Item> temp = new ArrayList<Item>();
			for (Item it : items) {
				if (!it.isRemover()) {
					temp.add(it);
				}
			}
			items.clear();
			items.addAll(temp);
		} else if (item.getTipo() == 1 ) { // Es un combo
			for (Item it : items) {
				if (it.getPadre() == item) {
					it.setRemover(true);
				} else {
					it.setRemover(false);
				}
			}
			items.remove(item);
			List<Item> temp = new ArrayList<Item>();
			for (Item it : items) {
				if (!it.isRemover()) {
					temp.add(it);
				}
			}
			items.clear();
			items.addAll(temp);
		}
		actualizarPosiciones();
		TableModelEvent evento = new TableModelEvent(this, fila, fila, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	@SuppressWarnings("unused")
	public synchronized void asociarItems() {
		try {
			Item posible_padre = null;
			int cantidadPadre = 1;
			List<Item> aux = new ArrayList<Item>(items);
			for (int i = 0; i < items.size(); i++) {
				Item it = aux.get(i);
				if (it.getTipo() == 1 || it.getTipo() == 0) {
					posible_padre = it;
				} else if (it.getTipo() == 2 || it.getTipo() == 3 ) {
					it.setPadre(posible_padre);
					int cantidadHijo = 0;
					if (posible_padre != null) {
						try {
							cantidadPadre = (int) Double.parseDouble(posible_padre.getCantidad());
						} catch (Exception e) {
							Util.mostrarExcepcion(e);
							cantidadPadre = 0;
						}
					} else {
						cantidadPadre = 0;
					}
					try {
						if (cantidadPadre > 0) {
							cantidadHijo = it.getCantidadAMostrar() / cantidadPadre;
						} else {
							cantidadHijo = 0;
						}
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						cantidadHijo = 0;
					}
					it.setCantidad("" + it.getCantidadAMostrar());
				}
			}
			items = new ArrayList<Item>(aux);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
			// e.printStackTrace();
		}
	}

	public synchronized void asociarItemsYPrecios() {
		try {
			Item posible_padre = null;
			Double pn = 0d;
			Double vn = 0d;
			int cantidadPadre = 1;
			SqlMaterialImpl sql = new SqlMaterialImpl();
			List<Item> aux = new ArrayList<Item>(items);
			for (int i = 0; i < items.size(); i++) {
				Item it = aux.get(i);
				if(Double.parseDouble(it.getCantidadConfirmada())==0) {
					BeanMaterial mat = sql.getMaterial(it.getCodigo());
					if(mat!=null) {						
						if(mat.getStrFec_Ing()!=null)
							if(!mat.getStrFec_Ing().equals("null"))
								it.setStrFech_Ing(Util.convierteFecha(mat.getStrFec_Ing()));
					}
				}else {
					it.setStrFech_Ing("");
				}
				if (it.getTipo() == 1 || it.getTipo() == 0) {
					posible_padre = it;
					if (it.getCantidad() == null || it.getCantidad().isEmpty()) {
						cantidadPadre = 0;
					} else {
						try {
							cantidadPadre = (int) Double.parseDouble(it.getCantidad());
						} catch (Exception e) {
							cantidadPadre = 0;
						}
					}
					pn = 0d;
					vn = 0d;
				} else if (it.getTipo() == 2 || it.getTipo() == 3) {
					pn += it.getPrecioNeto();
					vn += it.getValorNeto();
					pn = Math.round(pn * 1000) / 1000.0d;
					vn = Math.round(vn * 1000) / 1000.0d;
					it.setPadre(posible_padre);
					int cantidadHijo = 0;
					try {
						cantidadHijo = (int) (Double.parseDouble(it.getCantidad()) * cantidadPadre);
					} catch (Exception e) {
						Util.mostrarExcepcion(e);
						cantidadHijo = 0;
					}
					it.setCantidadAMostrar(cantidadHijo);
					if (Promesa.datose.get(0).getStrModo().equals(Constante.MODO_ONLINE) && 
							(it.getTipoSAP().compareTo("ZPH1") == 0 || it.getTipoSAP().compareTo("ZP01") == 0)) {
						posible_padre.setPrecioNeto(pn);
						posible_padre.setValorNeto(vn);
						posible_padre.setTipo(1);
						posible_padre.setTipoMaterial(it.getTipoMaterial());
					}else if(it.getTipoSAP().compareTo("") == 0) {
						posible_padre.setPrecioNeto(pn);
						posible_padre.setValorNeto(vn);
						posible_padre.setTipo(1);
						posible_padre.setTipoMaterial(it.getTipoMaterial());
					}
				}
			}
			items = new ArrayList<Item>(aux);
		} catch (Exception e) {
			Util.mostrarExcepcion(e);
		}
	}

	public void actualizarPosiciones() {
		int pos = 10;
		int pos_temp = 0;
		boolean start = false;
		for (Item item : items) {
			if (item.getTipo() == 0 || item.getTipo() == 1) {// Material normal
				start = false;
				item.setPosicion(Util.completarCeros(6, "" + pos));
				pos += 10;
			} else {
				if (!start) {
					pos_temp = pos - 10;
					start = true;
				}
				pos_temp++;
				if (pos_temp % 10 == 0) {
					pos += 10;
				}
				item.setPosicion(Util.completarCeros(6, "" + pos_temp));
			}
		}
	}

	public Item obtenerItem(int fila) {
		if (fila > -1) {
			return items.get(fila);
		}
		return null;
	}

	public void borrarItems() {
		items.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	public List<Item> obtenerTodosItems() {
		return items;
	}

	public List<Item> obtenerTodosItemsConfirmados() {
		List<Item> temp = new ArrayList<Item>();
		for (Item it : items) {
			int tipo = it.getTipo();
			if ((tipo == 0 || tipo == 1) && !it.getCodigo().isEmpty()) {
				temp.add(it);
			}
		}
		return temp;
	}

	public List<Item> obtenerTodosItemsConfirmadosSQLite() {
		List<Item> temp = new ArrayList<Item>();
		for (Item it : items) {
			if (!it.getCodigo().isEmpty()) {
				temp.add(it);
			}
		}
		return temp;
	}

	@SuppressWarnings("unused")
	public List<Item> obtenerTodosItemsSimulados() {
		List<Item> temp = new ArrayList<Item>();
		int i = 0;
		for (Item it : items) {
			if (it.isSimulado()) {
				temp.add(it);
			}
			i++;
		}
		return temp;
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

	public boolean permiteCerrar() {
		for (Item it : items) {
			if (it.getPrecioNeto() != 0d) {
				return false;
			}
		}
		return true;
	}

	public Double getValorNeto() {
		valorNeto = 0d;
		for (Item it : items) {
			if (it.getTipo() == 0 || it.getTipo() == 1) {
				valorNeto += it.getValorNeto();
			}
		}
		valorNeto = Math.round(valorNeto * 1000) / 1000.0d;
		return valorNeto;
	}

	public boolean esHijo(int fila) {
		if (fila < items.size()) {
			Item it = items.get(fila);
			if(it.getTipo() == 2 || it.getTipo() == 0 || it.getTipo() == 3)
				return true;
		}
		return false;
	}

	public void setValorNeto(Double valorNeto) {
		this.valorNeto = valorNeto;
	}

	public boolean existeCombo(String codigo) {
		for (Item item : items) {
			if (item.getCodigo().compareTo(codigo) == 0) {
				return true;
			}
		}
		return false;
	}

	public void actualizarItem(Item it) {
		List<Item> aux = new ArrayList<Item>();
		for (Item item : items) {
			if (item.getPosicion().compareTo(it.getPosicion()) == 0) {
				String strPorcentDscto = item.getPorcentajeDescuento();
				if(strPorcentDscto.equals("")){
					strPorcentDscto = "0";
				}
				Double dblPorcentDscto = null;
				try {
					dblPorcentDscto = Double.parseDouble(strPorcentDscto);
				} catch (Exception e) {
					Util.mostrarExcepcion(e);
					dblPorcentDscto = new Double("0");
				}
				try {
				item.setCantidadConfirmada(it.getCantidadConfirmada());
				if(it.getStock()!="-1")
					item.setStock(it.getStock());
				item.setStrFech_Ing(it.getStrFech_Ing());
				item.setPrecioNeto(it.getPrecioNeto() * (100 - Math.abs(dblPorcentDscto)) / 100.0d);
				item.setValorNeto(it.getValorNeto() * (100 - Math.abs(dblPorcentDscto)) / 100.0d);
				item.setPorcentajeDescuento(item.getPorcentajeDescuento());
				item.setSimulado(it.isSimulado());
				}catch(Exception e) {
					Util.mostrarExcepcion(e);
				}
				aux.add(item);
			} else {
				aux.add(item);
			}
		}
		items = new ArrayList<Item>(aux);
	}

	public void actualizarEstadoSimulado(int fila, boolean flag) {
		if (fila < items.size()) {
			items.get(fila).setSimulado(flag);
		}
	}
	
	public void actualizarDescuentoCombo(){
		for(Item item : items){
			List<Item> combos =  item.getCombos();
			for(Item combo : combos){
				combo.setPorcentajeDescuento(item.getPorcentajeDescuento());
			}
		}
	}
	
	public void actualizarDescuentoPadre(){
		for(Item item : items){
			List<Item> combos =  item.getCombos();
			if(combos.size() > 0){
				item.setPorcentajeDescuento("0.00");
			}
		}
	}
}