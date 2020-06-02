package com.promesa.internalFrame.cobranzas.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.cobranzas.bean.AnticipoCliente;
//import com.promesa.cobranzas.bean.PartidaIndividual;

public class ModeloTablaListaAnticipos implements TableModel {

	private List<AnticipoCliente> listaAnticipos = new ArrayList<AnticipoCliente>();
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
		return 12;
	}

	@Override
	public String getColumnName(int arg0) {
		String[] nombresColumnas = { "","SECUENCIA", "CLIENTE", "VENDEDOR", "FORMA PAGO", "REFERENCIA", "IMPORTE", 
				"NRO. CTA. BANCO CLIENTE", "BANCO CLIENTE", "BANCO PROMESA", "OBSERVACIONES", "FECHA DOCUMENTO" };
		return nombresColumnas[arg0];
	}

	@Override
	public int getRowCount() {
		return listaAnticipos.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		AnticipoCliente anticipo = listaAnticipos.get(arg0);
		switch (arg1) {
		case 0:
			return anticipo.getId();
		case 1:
			return anticipo.getAnticipoSecuencia();
		case 2:
			return anticipo.getCodigoCliente();
		case 3:
			return anticipo.getCodigoVendedor();
		case 4:
			return anticipo.getDescripcionFormaPago();
		case 5:
			return anticipo.getReferencia();
		case 6:
			return anticipo.getImporte();
		case 7:
			return anticipo.getNroCtaBcoCliente();
		case 8:
			return anticipo.getDescripcionBancoCliente();
		case 9:
			return anticipo.getDescripcionBancoPromesa();
		case 10:
			return anticipo.getObservaciones();
		case 11:
			return anticipo.getFechaCreacion();
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
		AnticipoCliente anticipo = listaAnticipos.get(arg1);
		if (arg0 == null) {
			arg0 = "";
		}
		switch (arg2) {
		case 0:
			int id = 0;
			try {
				id = Integer.parseInt(arg0.toString());
			} catch (Exception e) {
				id = -1;
			}
			anticipo.setId(id);
			break;
		case 1:
			anticipo.setAnticipoSecuencia(arg0.toString());
			break;
		case 2:
			anticipo.setCodigoCliente(arg0.toString());
			break;
		case 3:
			anticipo.setCodigoVendedor(arg0.toString());
			break;
		case 4:
			anticipo.setDescripcionFormaPago(arg0.toString());
			break;
		case 5:
			anticipo.setReferencia(arg0.toString());
			break;
		case 6:
			anticipo.setImporte(arg0.toString());
			break;
		case 7:
			anticipo.setNroCtaBcoCliente(arg0.toString());
			break;
		case 8:
			anticipo.setDescripcionBancoCliente(arg0.toString());
			break;
		case 9:
			anticipo.setDescripcionBancoPromesa(arg0.toString());
			break;
		case 10:
			anticipo.setObservaciones(arg0.toString());
			break;
		case 11:
			anticipo.setFechaCreacion(arg0.toString());
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
		listaAnticipos.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}

	public void agregarAnticipoCliente(AnticipoCliente anticipo) {
		listaAnticipos.add(anticipo);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
	
	public AnticipoCliente obtenerAnticipoCliente(int fila) {
		if(listaAnticipos != null && !listaAnticipos.isEmpty()){
			return listaAnticipos.get(fila);
		}else{
			return null;
		}
	}
}