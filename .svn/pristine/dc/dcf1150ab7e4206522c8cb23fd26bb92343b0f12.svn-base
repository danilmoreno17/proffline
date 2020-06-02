package com.promesa.internalFrame.cobranzas.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

//import com.promesa.cobranzas.bean.AnticipoCliente;
import com.promesa.cobranzas.bean.CabeceraRegistroPagoCliente;

public class ModeloTablaListaRegistroPagos implements TableModel {

	private List<CabeceraRegistroPagoCliente> listaRegistroPagos = new ArrayList<CabeceraRegistroPagoCliente>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		listeners.add(arg0);
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public String getColumnName(int arg0) {
		String[] nombresColumnas = { "", "SECUENCIAL", "VENDEDOR", "CLIENTE", "CANT.FORMAS PAGO", "IMPTE.ENTRADO", "IMPTE.ASIGNADO", "IMPTE.SIN ASIGNAR" };
		return nombresColumnas[arg0];
	}

	@Override
	public int getRowCount() {
		return listaRegistroPagos.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		CabeceraRegistroPagoCliente registroPago = listaRegistroPagos.get(arg0);
		switch (arg1) {
		case 0:
			return registroPago.getIdCabecera();
		case 1:
			return registroPago.getCabeceraSecuencia();
		case 2:
			return registroPago.getCodigoVendedor();
		case 3:
			return registroPago.getCodigoCliente();
		case 4:
			return registroPago.getCantidadPagoRecibido();
		case 5:
			return registroPago.getImporteEntrado();
		case 6:
			return registroPago.getImporteAsignado();
		case 7:
			return registroPago.getImporteSinAsignar();
		default:
			break;
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		listeners.remove(arg0);
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		CabeceraRegistroPagoCliente registroPago = listaRegistroPagos.get(arg1);
		if (arg0 == null) {
			arg0 = "";
		}
		switch (arg2) {
		case 0:
			Long id = 0l;
			try {
				id = Long.parseLong(arg0.toString());
			} catch (Exception e) {
				id = -1l;
			}
			registroPago.setIdCabecera(id);
			break;
		case 1:
			registroPago.setCabeceraSecuencia(arg0.toString());
			break;
		case 2:
			registroPago.setCodigoVendedor(arg0.toString());
			break;
		case 3:
			registroPago.setCodigoCliente(arg0.toString());
			break;
		case 4:
			Integer cantidad = 0;
			try {
				cantidad = Integer.parseInt(arg0.toString());
			} catch (Exception e) {
				cantidad = 0;
			}
			registroPago.setCantidadPagoRecibido(cantidad);
			break;
		case 5:
			Double importeEntrado = 0d;
			try {
				importeEntrado = Double.parseDouble(arg0.toString());
			} catch (Exception e) {
				importeEntrado = 0d;
			}
			registroPago.setImporteEntrado(importeEntrado);
			break;
		case 6:
			Double importeAsignado = 0d;
			try {
				importeAsignado = Double.parseDouble(arg0.toString());
			} catch (Exception e) {
				importeAsignado = 0d;
			}
			registroPago.setImporteAsignado(importeAsignado);
			break;
		case 7:
			Double importeSinAsignar = 0d;
			try {
				importeSinAsignar = Double.parseDouble(arg0.toString());
			} catch (Exception e) {
				importeSinAsignar = 0d;
			}
			registroPago.setImporteSinAsignar(importeSinAsignar);
			break;
		default:
			break;
		}
		TableModelEvent evento = new TableModelEvent(this, arg1, arg1, arg2);
		avisaSuscriptores(evento);
	}

	private void avisaSuscriptores(TableModelEvent evento) {
		int i;
		for (i = 0; i < listeners.size(); i++) {
			((TableModelListener) listeners.get(i)).tableChanged(evento);
		}
	}

	public void limpiar() {
		listaRegistroPagos.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	public void agregarCabeceraRegistroPago(CabeceraRegistroPagoCliente cabecera) {
		listaRegistroPagos.add(cabecera);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
	
	public CabeceraRegistroPagoCliente obtenerCabeceraRegistroPagoCliente(int fila){
		if(listaRegistroPagos != null && !listaRegistroPagos.isEmpty()){
			return listaRegistroPagos.get(fila);
		}else{
			return null;
		}
	}
}