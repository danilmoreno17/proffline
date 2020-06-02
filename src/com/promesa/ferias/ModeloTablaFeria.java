package com.promesa.ferias;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.main.Promesa;

public class ModeloTablaFeria implements TableModel {

	private List<Asistencia> asistencia = new ArrayList<Asistencia>();
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
		return 24;
	}
	
	public List<Asistencia> getAsistencia(){
		return asistencia;
	}

	@Override
	public String getColumnName(int columnIndex) {//
		switch (columnIndex) {
		case 0:
			return "SOURCE";
		case 1://	Marcelo Moyano 12/05/2013
			return "SECUENCIA";//	Marcelo Moyano 12/05/2013
		case 2:
			return "LUGAR FERIAS";
		case 3:
			return "RELACION";
		case 4:
			return "COD. CLIENTE";
		case 5:
			return "RAZON SOCIAL.";
		case 6:
			return "CUIDAD CLIENTE";
		case 7:
			return "INVITADO";
		case 8:
			return "CORREO";
		case 9:
			return "FACEBOOK";
		case 10:
			return "TWITTER";
		case 11:
			return "TELEFONO";
		case 12:
			return "CELULAR";
		case 13:
			return "OTRO TELEFONO";
		case 14:
			return "INTERES ESPECIAL";
		case 15:
			return "OBSERVACION";
		case 16:
			return "FECHA ASISTENCIA";
		case 17:
			return "HORA ASISTENCIA";
		case 18:
			return "USUARIO";
		case 19:
			return "ID";
		case 20:
			return "EJERCICIO";
		case 21:
			return "SINCR0NIZACION";
		case 22:
			return "GREMI O INST. EDU.";
		case 23:
			return "PROFESION";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return asistencia.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Asistencia f = asistencia.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return f.getSource();
		case 1:
			return f.getSecuencia();
		case 2:
			return f.getCiudadFeria();
		case 3:
			return f.getRelacion();
		case 4:
			return f.getCodigoCliente();
		case 5:
			return f.getRazonSocial();
		case 6:
			return f.getCiudadCliente();
		case 7:
			return f.getInvitado();
		case 8:
			return f.getCorreo();
		case 9:
			return f.getFacebook();
		case 10:
			return f.getTwitter();
		case 11:
			return f.getTelefono();
		case 12:
			return f.getCelular();
		case 13:
			return f.getOtroTelefono();
		case 14:
			return f.getInteresEspeciales();
		case 15:
			return f.getObservacion();
		case 16:
			return f.getFechaAsistencia();
		case 17:
			return f.getHoraAsistencia();
		case 18:
			return Promesa.datose.get(0).getStrUsuario();//f.getIdUsuario();
		case 19:
			return f.getId();
		case 20:
			return f.getEjercicio();
		case 21:
			return f.getSincronizacion();
		case 22:
			return f.getGremio();
		case 23:
			return f.getProfesion();
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
		Asistencia f = asistencia.get(rowIndex);
		String valor = ("" + aValue).toString();
		switch (columnIndex) {
		case 0:
			f.setSource(valor);
		case 1:
			f.setSecuencia(valor);
		case 2:
			f.setCiudadFeria(valor);
		case 3:
			f.setRelacion(valor);
		case 4:
			f.setCodigoCliente(valor);
		case 5:
			f.setRazonSocial(valor);
		case 6:
			f.setCiudadCliente(valor);
		case 7:
			f.setInvitado(valor);
		case 8:
			f.setCorreo(valor);
		case 9:
			f.setFacebook(valor);
		case 10:
			f.setTwitter(valor);
		case 11:
			f.setTelefono(valor);
		case 12:
			f.setCelular(valor);
		case 13:
			f.setOtroTelefono(valor);
		case 14:
			f.setInteresEspeciales(valor);
		case 15:
			f.setObservacion(valor);
		case 16:
			f.setFechaAsistencia(valor);
		case 17:
			f.setHoraAsistencia(valor);
		case 18:
			f.setIdUsuario(valor);
		case 19:
			f.setId(valor);
		case 20:
			f.setEjercicio(valor);
		case 21:
			f.setSincronizacion(valor);
		case 22:
			f.setGremio(valor);
		case 23:
			f.setProfesion(valor);
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
		asistencia.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}
	
	public int obtenerCantidadAsistencia() {
		return asistencia.size();
	}
	
	public Asistencia obtenerFila(int fila){
		Asistencia filaAsistencia = asistencia.get(fila);
		return filaAsistencia;
	}

	public void agregarAsistencia(Asistencia factura) {
		asistencia.add(factura);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
	
	public void elimnarFila(int fila){
		asistencia.remove(fila);
	}
}