package com.promesa.util;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

public class ItemArbol implements TreeNode {

	private String titulo;
	private String id;
	private Vector<TreeNode> hijos = new Vector<TreeNode>();
	private TreeNode padre;

	public ItemArbol(String titulo, String id) {
		this.titulo = titulo;
		this.id = id;
	}

	public Vector<TreeNode> getHijos() {
		return hijos;
	}

	public void setHijos(Vector<TreeNode> hijos) {
		this.hijos = hijos;
	}

	public TreeNode getPadre() {
		return padre;
	}

	public void setPadre(TreeNode padre) {
		this.padre = padre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void addChild(TreeNode hijo) {
		hijos.add(hijo);
	}

	@Override
	public Enumeration<TreeNode> children() {
		return hijos.elements();
	}

	@Override
	public TreeNode getChildAt(int i) {
		return hijos.elementAt(i);
	}

	@Override
	public int getChildCount() {
		return hijos.size();
	}

	@Override
	public TreeNode getParent() {
		return this.padre;
	}

	@Override
	public int getIndex(TreeNode tn) {
		return hijos.indexOf(tn);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return (hijos.isEmpty());
	}

	@Override
	public String toString() {
		if(id==null || id.isEmpty()) {
			return titulo;
		}
		return id + " - " + titulo;
	}
}