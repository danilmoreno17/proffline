package com.promesa.pedidos.sql;

import java.util.List;

import com.promesa.internalFrame.pedidos.custom.Item;
import com.promesa.pedidos.bean.BeanClaseMaterial;
import com.promesa.pedidos.bean.BeanCondicionComercial1;
import com.promesa.pedidos.bean.BeanCondicionComercial2;
import com.promesa.pedidos.bean.BeanMaterial;

public interface SqlMaterial {
	public void setListarMaterial();
	public void setInsertarMaterial(List<BeanMaterial> listm);
	public void setInsertarActualizarMaterial(List<BeanMaterial> listm);
	public void setInsertarActualizarMaterialNuevo(List<BeanMaterial> listm);
	public void insertarMaterialesStock(List<BeanMaterial> listm);
	public void migrarMaterialConsultaDinamica1(List<BeanMaterial> listm);
	public void migrarMaterialConsultaDinamica2(List<BeanMaterial> listm);
	public void migrarMaterialConsultaDinamica3(List<BeanMaterial> listm);
	public void migrarMaterialesTopCliente(List<List<BeanMaterial>> listm);
	
	public void migrarMaterialesTopCliente2(List<List<BeanMaterial>> listm, List<BeanMaterial> listaMaterial);
	public void migrarMaterialesTopTipologia2(List<List<BeanMaterial>> listm, List<BeanMaterial> listaMaterial);
	public List<BeanMaterial> obtenerTodosMateriales(List<BeanMaterial> listaMateriales, String mensaje);
	public List<BeanMaterial> obtenerTodosMateriales2();
	
	public void migrarMaterialesTopTipologia(List<List<BeanMaterial>> listm);
	public void setInsertarCombo(List<Item> listCombos);
	public void setInsertarCondicion1(List<BeanCondicionComercial1> listCondiciones);
	public void setInsertarCondicion2(List<BeanCondicionComercial2> listCondiciones);
	public void setInsertarClaseMaterial(List<BeanClaseMaterial> listClaseMaterial);
	public List<BeanCondicionComercial1> listarCondicion1(BeanCondicionComercial1 beanCondicion1);
	public List<BeanCondicionComercial2> listarCondicion2(BeanCondicionComercial2 beanCondicion2);
	public void setInsertarComboHijos(List<Item> listCombos);
	public void eliminarMaterialConsultaDinamica1();
	public void eliminarMaterialConsultaDinamica2();
	public void eliminarMaterialConsultaDinamica3();
	public void setEliminarCombo();
	public void setEliminarCondicion1();
	public void setEliminarCondicion2();
	public void setEliminarClaseMaterial();
	public void setEliminarComboHijos();
	public boolean isEliminarMaterial();
	public boolean getInsertarUsuario();
	public List<BeanMaterial> getListaMaterial();
	public BeanMaterial getMaterial(String codigo);
	public List<BeanMaterial> getMaterialNuevo(String codigo, String IdMaterial);
	public int getCountRow();
	public int getCountRowNuevo(String strCodMaterial);
	public int getCountRowStock();
	public int getCountRowPromoOferta();
	public int getCountRowDsctoPolitica();
	public int getCountRowDsctoManual();
	public int getCountRowCombos();
	public int getCountRowCondicion1();
	public int getCountRowCondicion2();
	public int getCountRowClaseMaterial();
	public int getCountRowTopCliente();
	public int getCountRowTopTipologia();
	public void actualizarStock(String strCodMaterial, int intStock);
	public List<BeanClaseMaterial> obtenerClaseMaterial(String strCodigoMaterial);
	public void setEliminarMaterialStock();
	
	public String getMaterialMarcaEstrategica(String matnr);
}
