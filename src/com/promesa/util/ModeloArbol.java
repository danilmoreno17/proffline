package com.promesa.util;

import java.util.Vector;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ModeloArbol implements TreeModel {
	private TreeNode rootNode;
    private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();

    public ModeloArbol(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public Object getRoot() {
        return rootNode;
    }

    @Override
    public Object getChild(Object o, int i) {
        TreeNode parentNode = (TreeNode) o;
        return parentNode.getChildAt(i);
    }

    @Override
    public int getChildCount(Object o) {
        TreeNode parentNode = (TreeNode) o;
        return parentNode.getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        TreeNode treeNode = (TreeNode) node;
        return treeNode.isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        TreeNode parentNode = (TreeNode) parent;
        TreeNode childNode = (TreeNode) child;
        return parentNode.getIndex(childNode);
    }

    @Override
    public void addTreeModelListener(TreeModelListener tl) {
        listeners.add(tl);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl) {
        listeners.remove(tl);
    }
}