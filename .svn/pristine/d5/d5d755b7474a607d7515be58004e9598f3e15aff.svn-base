package com.promesa.internalFrame.cobranzas.custom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.util.Util;

public class ModeloPagosRecibidos implements TableModel {

	private List<PagoParcial> listaPagoParcial = new ArrayList<PagoParcial>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 11;
	}

	@Override
	public String getColumnName(int arg0) {
		String[] nombresColumnas = { "", "", "N° DOC.", "<html><b>REFERENCIA ▼</b></html>", "CLASE", "<html><b>POSICIÓN ▼</b></html>", "<html><b>VENCIMIENTO ▼</b></html>", "IMPTE.DE POS.", "IMPTE.PAG.EN PARTE", "IMPTE.PAGO", "COMENTARIO" };
		return nombresColumnas[arg0];
	}

	@Override
	public int getRowCount() {
		return listaPagoParcial.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		PagoParcial pp = listaPagoParcial.get(arg0);
		switch (arg1) {
		case 0:
			return pp.getSecuencia();
		case 2:
			return pp.getNumeroDocumento();
		case 3:
			return pp.getReferencia();
		case 4:
			return pp.getClaseDocumento();
		case 5:
			return pp.getPosicion();
		case 6:
			return pp.getVencimiento();
		case 7:
			return Util.formatearNumero(pp.getImportePagoTotal());
		case 8:
			return Util.formatearNumero(pp.getImportePagoParcial());
		case 9:
			return Util.formatearNumero(pp.getImportePago());
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		PagoParcial pp = listaPagoParcial.get(arg0);
		if (pp.isPago_hecho_offline()){
			return false;
		}
		if (arg1 == 9) {
			PagoParcial pagoParcial = listaPagoParcial.get(arg0);
			if (pagoParcial.isActivo()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		PagoParcial pp = listaPagoParcial.get(arg1);
		switch (arg2) {
		case 0:
			if (arg0 == null) {
				arg0 = 0;
			}
			pp.setSecuencia(Integer.parseInt(arg0.toString()));
			break;
		case 9:
			if (arg0 == null) {
				arg0 = "";
			}
			Double importe = 0d;
			try {
				importe = Double.parseDouble(arg0.toString());
			} catch (Exception e) {
				importe = 0d;
			}
			pp.setImportePago(importe);
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
		listaPagoParcial.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	public void agregarPagoParcial(PagoParcial pp) {
		listaPagoParcial.add(pp);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}

	public PagoParcial obtenerPagoParcial(int indice) {
		if (indice > -1) {
			return listaPagoParcial.get(indice);
		}
		return null;
	}

	public List<PagoParcial> obtenerListaPagoParcial() {
		return listaPagoParcial;
	}

	public void agregarHijos(List<PagoParcial> hijos, int posicion) {
		if (hijos != null && !hijos.isEmpty()) {
			for (PagoParcial pagoParcial : hijos) {
				listaPagoParcial.add(++posicion, pagoParcial);
			}
		}
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}

	public boolean esHijo(int fila) {
		if (fila > -1) {
			return listaPagoParcial.get(fila).isHijo();
		}
		return false;
	}

	public boolean estaDesplegado(int fila) {
		if (fila > -1) {
			return listaPagoParcial.get(fila).isDesplegado();
		}
		return false;
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

	public void eliminarHijos(long idPagoParcial) {
		List<PagoParcial> listaPagoParcialAuxiliar = new ArrayList<PagoParcial>();
		for (PagoParcial pagoParcial : listaPagoParcial) {
			if (pagoParcial.getIdPadre() != idPagoParcial) {
				listaPagoParcialAuxiliar.add(pagoParcial);
			}
		}
		listaPagoParcial = new ArrayList<PagoParcial>();
		listaPagoParcial.addAll(listaPagoParcialAuxiliar);
	}

	public void validarImportesIngresados() {
		for (PagoParcial pagoParcial : listaPagoParcial) {
			Double importePago = pagoParcial.getImportePago();
			Double importePagoParcial = pagoParcial.getImportePagoParcial();
			Double importePagoTotal = pagoParcial.getImportePagoTotal();
			if (importePago <= 0d || importePago > importePagoTotal - importePagoParcial) {
				importePago = importePagoTotal - importePagoParcial;
				pagoParcial.setImportePago(importePago);
			}
		}
	}

	public BigDecimal obtenerImporteTotal() {
		BigDecimal importeTotal = new BigDecimal(0);
		for (PagoParcial pagoParcial : listaPagoParcial) {
			Double importePagoTotal = pagoParcial.getImportePago();
			if (pagoParcial.isActivo()) {
				importeTotal = importeTotal.add(new BigDecimal(importePagoTotal));
			}
		}
		importeTotal = importeTotal.setScale(2, RoundingMode.HALF_UP);
		return importeTotal;
	}

	public int obtenerCantidadPartidasActivas() {
		int contador = 0;
		for (PagoParcial pagoParcial : listaPagoParcial) {
			if (pagoParcial.isActivo()) {
				contador++;
			}
		}
		return contador;
	}
	
	public void ActualizarModeloPagosRecibidos(PagoParcial pptmp, int i){
		if(i >= 0 && i < listaPagoParcial.size()){
			listaPagoParcial.set(i, pptmp);
		}
	}
}
