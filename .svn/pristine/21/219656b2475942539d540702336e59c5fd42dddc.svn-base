package com.promesa.ferias.sql;

import java.util.List;
import com.promesa.ferias.Asistencia;
import com.promesa.ferias.BeanGremioEInstitutoEducativo;
import com.promesa.ferias.BeanProfesion;
import com.promesa.ferias.Cliente;
import com.promesa.ferias.LugarFeria;
import com.promesa.ferias.Relacion;
import com.promesa.internalFrame.ferias.IFerias;

public interface SqlFerias {

	public void registrarCliente(Cliente cliente) throws Exception;
	public void registrarAsistencia(Asistencia a) throws Exception;
	public Cliente buscarCliente(String codigoCliente);
	public String buscarGremioInstitucionEducativa(String strCodigoGremioInst);
	public String obtenerCodigoCiudad(String nombreCiudad);
	public String obtenerCodigoRelacion(String nombreRelacion);
	public String obtenerTipoRelacion(String codigoRelacion);
	public String obtenerUltimoRegistroAsistencia();
	public String obtenerCodigoGremioInstitucionEducativa(String strNombreGremio);
	public String obtenerCodigoProfesion(String strNombreProfesion);
	public String obtenerNombreProfesion(String strCodigoProfesion);
	public void actualizarAsistencia(Asistencia ca) throws Exception;
	public List<Cliente> obtenerClientes();
	public List<LugarFeria> obtenerLugares();
	public List<Relacion> obtenerRelacion();
	public List<Asistencia> obtenerAsistencias(String strFechaDesde, String strFechaHasta);
	public List<BeanProfesion> obtenerProfesion();
	public List<BeanGremioEInstitutoEducativo> obtenerGremioEInstitucionEducativa();
	public void guardarClientes(IFerias intance, List<Cliente> listaClientes) throws Exception;
	public void guardarClientes2(IFerias intance, List<Cliente> listaClientes) throws Exception;
	public void guardarLugarFeria(IFerias intance, List<LugarFeria> lf) throws Exception;
	public void guardarLugarFeria2(IFerias intance, List<LugarFeria> lf) throws Exception;
	public void guardarRelacion(IFerias intance, List<Relacion> r) throws Exception;
	public void guardarProfesion(IFerias intance, List<BeanProfesion> listaProfesiones) throws Exception;
	public void guardarGremioEInstitucionEducativa(IFerias intance, List<BeanGremioEInstitutoEducativo> listaGremioInst) throws Exception;
	public void eliminarTablaClienteFerias() throws Exception;
	public void eliminarTablaRelacionFerias() throws Exception;
	public void eliminarTablaLugarFerias() throws Exception;
	public void eliminarTablaProfesion() throws Exception;
	public void eliminarTablaGremioEInstitucionEducativa() throws Exception;
}