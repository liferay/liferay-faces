/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.MenuModel;
import org.primefaces.model.TreeNode;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.demos.list.DocumentDataModel;
import com.liferay.faces.demos.tree.FolderTreeModel;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.helper.LongHelper;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;

import com.liferay.faces.demos.tree.PrimefacesDocument;
import com.liferay.faces.demos.tree.PrimefacesFolder;


import javax.faces.context.FacesContext;  
import javax.faces.application.FacesMessage;  

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.event.NodeCollapseEvent;  
import org.primefaces.event.NodeExpandEvent;  
import org.primefaces.event.NodeSelectEvent;  
import org.primefaces.event.NodeUnselectEvent;  
import java.util.LinkedList;
import java.util.ListIterator;



/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
@ManagedBean(name = "docLibModelBean")
@SessionScoped
public class DocLibModelBean implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient, because it's not possible to de-serialize
	// instances of Liferay's DLFolderImpl class due to classloader prolems.
	private static final long serialVersionUID = 6145332622204857486L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocLibModelBean.class);

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private transient DocumentDataModel documentDataModel;
	private transient FolderTreeModel folderTreeModel;

	// Ajout de jch
	private int INITIAL_OPENED_TREENODE_LEVEL=2;
	private int expandLevel = INITIAL_OPENED_TREENODE_LEVEL;
	private TreeNode root ;
	private TreeNode selectedNode = null;
	private long selectedNodeId = 0;
	private long rootNodeId;
	private PrimefacesDocument selectedPrimefacesDocument = null;
	private List<PrimefacesDocument> folderPrimefacesDocumentList=new ArrayList<PrimefacesDocument>();
	private List<Long> expandedTreeNodeList=new ArrayList<Long>();
	private HashMap <Long,TreeNode> treeNodeListHm = new HashMap <Long,TreeNode>();
	private boolean initialTreeNodeRendering = true;
	private String renderedPanel ="documentList";
	List<Long> list = new LinkedList<Long>();
	private int documentNumber;
	// Fin de Ajout de jch

	@PostConstruct
	public void postConstruct() {

		// NOTE: Since we don't have those nice ICEfaces 2.0 ACE components, we have to do file upload the Web 1.0 way
		// with the bridge:inputFile component. That forces us to keep the ModelBean in session scope, so that we can
		// retain knowledge of the selected tree folder. Having the following calls to docLibModelBean.force*() will
		// essentially cause the tree and list to be RequestScoped, even though the selected folder remains
		// SessionScoped. This prevents having two model beans, one RequestScoped, and the other SessionScoped, which
		// will enable easier refactoring of the model bean to ViewScoped when the ACE file upload component is
		// available. Note that the whole point of having the list data ViewScoped is to force the list to reload more
		// frequently, so that it contains updates by other users.
		forceTreeRequery();
	}

	public void forceDocumentRequery() {
		buildFolderPrimefacesDocumentList(selectedNodeId);
		}

	public DLFolder getSelectedNodeDlFolder(){
		DLFolder dlFolder =  ( (PrimefacesFolder) treeNodeListHm.get(selectedNodeId).getData()).getDlFolder();
		return dlFolder;
	}

	public long getSelectedNodeId(){
		return selectedNodeId;
	}

	public void setSelectedNodeId(Long nodeId){
		selectedNodeId = nodeId;
	}

	public void selectParentNodeOf(Long nodeId){
		selectedNodeId =  ( (PrimefacesFolder) treeNodeListHm.get(nodeId).getParent().getData() ).getId();
	}
	
	public void buildBreadCrumb(long nodeId){
		list.add(nodeId);
		long parentId =  ( (PrimefacesFolder) treeNodeListHm.get(nodeId).getParent().getData() ).getId();
    	if (parentId != -1 ){
    		buildBreadCrumb( parentId);
    	}
	}

	public MenuModel getBreadCrumb(){
    	MenuModel simpleMenuModel = new DefaultMenuModel();
    	MenuItem menuItem = new MenuItem();
    	simpleMenuModel.addMenuItem(menuItem);
    	list.clear();
     	buildBreadCrumb(selectedNodeId);
        ListIterator<Long> iter = list.listIterator(list.size());
        while (iter.hasPrevious()) {
    		menuItem = new MenuItem();
    		menuItem.setValue(( (PrimefacesFolder) treeNodeListHm.get(iter.previous()).getData()).getDlFolder().getName());
    		menuItem.setUrl("#");
    		simpleMenuModel.addMenuItem(menuItem);
        }
     	return simpleMenuModel;
    }
	
	public void addExpandeNode(long nodeId){
		expandedTreeNodeList.add(nodeId);
	}

	private void logNodesState(){
		// Debbugging mode only, to check nodes state
		for ( long i : treeNodeListHm.keySet()  ) {
			logger.info("\n----------- \033[44m\033[36mMESSAGE Nodes [{0}] : [{1}]", i,treeNodeListHm.get(i) +"\033[0m\n");
		}		
		for (long i : expandedTreeNodeList ) {
			logger.info("\n----------- \033[44m\033[36mMESSAGE logExpandeNodes [{0}][{1}]", i,treeNodeListHm.get(i) +"\033[0m\n");
		}		
		logger.info("\n----------- \033[44m\033[36mMESSAGE logselectednode [{0}][{1}]",selectedNodeId +"\033[0m\n");
	}

	public HashMap <Long,TreeNode> getTreeNodeList(TreeNode node){
		treeNodeListHm.clear();
		treeNodeEnum((DefaultTreeNode)root,0);
		if ( initialTreeNodeRendering ){
			selectedNode = treeNodeListHm.get( selectedNodeId );
			initialTreeNodeRendering=false;
		}
		return treeNodeListHm;
	}

	public void treeNodeEnum(DefaultTreeNode node, int level){
		long nodeId =  ((PrimefacesFolder) node.getData() ).getId() ;
// TODO decide node's type ( see docLib.xhtml for available types ) 		
//	    String type = ( condition ) ? "admin" : "guest"; 
		String type =  "admin";
		((PrimefacesFolder) node.getData() ).setType(type);
		node.setType(type);
		node.setExpanded(false);
		node.setSelected(false);
		treeNodeListHm.put(nodeId, node);
		if ( level <= expandLevel && initialTreeNodeRendering ) { 
			expandedTreeNodeList.add(nodeId);
		}
		for (TreeNode childNode : node.getChildren()) {
			treeNodeEnum((DefaultTreeNode) childNode, level+1);
		}
	}
	
	public void forceTreeRequery() {

		// Clear the document list model, but clear-and-requery the folder tree model.
		//		setDocumentDataModel(null);
		setFolderTreeModel(null);
		getFolderTreeModel();
		getTreeNodeList(root);
		logger.info("\n----------- \033[44m\033[36mJCH_MESSAGE_PRIMEFACES forceTreeRequery  selectedNodeId "+ getSelectedNodeId()+"\033[0m\n");
		if (folderTreeModel != null) {
			// Attempt to re-expand the previously expanded folders.
			for (long i : expandedTreeNodeList ) {
				treeNodeListHm.get(i).setExpanded(true);
			}
			// Attempt to re-select the previously selected folder.
			treeNodeListHm.get(selectedNodeId).setSelected(true);
			forceDocumentRequery();
			}
	}

	public List<PrimefacesDocument> getFolderPrimefacesDocumentList() {
		return this.folderPrimefacesDocumentList;
	}

	public int getDocumentNumber(){
		return documentNumber;
	}
	public void buildFolderPrimefacesDocumentList(long nodeId) {
		documentNumber=0;
		setDocumentDataModel(null);
		folderPrimefacesDocumentList.clear();
		DocumentDataModel documentDataModel=getDocumentDataModel(nodeId);
		for (DLFileEntry fileEntry : documentDataModel) {
			documentNumber++;
			long id = fileEntry.getFileEntryId();
			String documentName = fileEntry.getName();
			String type = fileEntry.getExtension();
			String description = fileEntry.getDescription();
			try {
				PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
				boolean permittedToDelete = DLFileEntryPermission.contains(permissionChecker, fileEntry,"DELETE");
				permittedToDelete = ( fileEntry.getTitle().contains("a") ) ? true : false;
				permittedToDelete = true;
				PrimefacesDocument primefacesDocument = new PrimefacesDocument(id, documentName, type, description, fileEntry, permittedToDelete );
				folderPrimefacesDocumentList.add(primefacesDocument);			
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public DocumentDataModel getDocumentDataModel(long nodeId) {
		DLFolder dlFolder =  ( (PrimefacesFolder) treeNodeListHm.get(nodeId).getData()).getDlFolder();
		int rowsPerPage = liferayFacesContext.getPortletPreferenceAsInt("rowsPerPage", SearchContainer.DEFAULT_DELTA);
		ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();
		String portalURL = themeDisplay.getPortalURL();
		String pathContext = themeDisplay.getPathContext();
		PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
		documentDataModel = new DocumentDataModel(dlFolder, rowsPerPage, portalURL, pathContext, permissionChecker);
		return documentDataModel;
	}

	public void setDocumentDataModel(DocumentDataModel documentDataModel) {
		this.documentDataModel = documentDataModel;
	}

	public TreeNode getRoot() {
		return root;
	}

	public long getNodeId(TreeNode node) {
		return (node != null) ? ( (PrimefacesFolder) node.getData() ).getId() : 0L;  
	}  

	public void setSelectedPrimefacesDocument(PrimefacesDocument selectedPrimefacesDocument){
		if (selectedPrimefacesDocument != null){
		}
		this.selectedPrimefacesDocument = selectedPrimefacesDocument;    	    
	}

	public PrimefacesDocument getSelectedPrimefacesDocument(){
		return this.selectedPrimefacesDocument;    	    
	}

	public boolean isPrimefacesDocumentPermittedTo(String action){
		boolean permitted=false;
		try {
			PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
			DLFileEntry selectedDLFileEntry = getSelectedPrimefacesDocument().getFileEntry();
			if (selectedDLFileEntry	!= null){
				permitted = DLFileEntryPermission.contains(permissionChecker, selectedDLFileEntry, action);
			} else {
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return permitted;		
	}

	public void deletePrimefacesDocument(){
		try {
			selectedPrimefacesDocument = getSelectedPrimefacesDocument();
			long selectedDLFileEntryId = selectedPrimefacesDocument.getFileEntry().getFileEntryId() ;
			if ( isPrimefacesDocumentPermittedTo("DELETE") ) {
				try {
					folderPrimefacesDocumentList.remove(selectedPrimefacesDocument);
					selectedPrimefacesDocument=null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					DLFileEntryServiceUtil.deleteFileEntry(selectedDLFileEntryId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		buildFolderPrimefacesDocumentList(selectedNodeId);
	}

	/////////////////////////////////////////////////////////////////////
	public void onNodeExpand(NodeExpandEvent event) {  
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", ""+((PrimefacesFolder) event.getTreeNode().getData() ).getId());  
		FacesContext.getCurrentInstance().addMessage(null, message); 
		long nodeId=((PrimefacesFolder) event.getTreeNode().getData() ).getId();
		expandedTreeNodeList.add(nodeId);
//		logNodesState();
	}  

	public void onNodeCollapse(NodeCollapseEvent event) {  
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", ""+((PrimefacesFolder) event.getTreeNode().getData() ).getId());  
		FacesContext.getCurrentInstance().addMessage(null, message);  
		long nodeId=((PrimefacesFolder) event.getTreeNode().getData() ).getId();
		expandedTreeNodeList.remove(nodeId);
//		logNodesState();
	}  

	public TreeNode getSelectedNode() {    	
		return selectedNode;  
	}  

	public void setSelectedNode(TreeNode selectedNode) {  
		selectedNodeId = ((PrimefacesFolder) selectedNode.getData() ).getId();
		buildFolderPrimefacesDocumentList(selectedNodeId);
		this.selectedNode = selectedNode;
//      don't do that as setSelectedNode is triggered when page is rendered
//		setRenderedPanel("documentList");
//		end of don't do that
		logger.info("\n----------- \033[44m\033[36mMESSAGE setSelectedNode selectedNodeId = "+selectedNodeId+"\033[0m\n");
	}

	public void onNodeSelect(NodeSelectEvent event) {  
		setRenderedPanel("documentList");
		logger.info("\n----------- \033[44m\033[36mMESSAGE onNodeSelect selectedNodeId = "+selectedNodeId+"\033[0m\n");
	}  

	public void onNodeUnselect(NodeUnselectEvent event) {  
	}  

	public FolderTreeModel getFolderTreeModel() {

		if (folderTreeModel == null) {
			try {
				Group scopeGroup = liferayFacesContext.getThemeDisplay().getScopeGroup();
				logger.debug("Querying folders for scopeGroupId=[" + scopeGroup.getGroupId() + "] scopeGroupName=[" + scopeGroup.getName() + "]");
				String scopeGroupIdPreference = (String) liferayFacesContext.getPortletPreference("scopeGroupId", null);
				long scopeGroupId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
				if (scopeGroupIdPreference != null) {
					scopeGroupId = LongHelper.toLong(scopeGroupIdPreference, 0L);
					if (scopeGroupId != 0L) {
						try {
							scopeGroup = GroupServiceUtil.getGroup(scopeGroupId);
							logger.debug("Overriding with preference scopeGroupId=[" + scopeGroup.getGroupId() +
									"] scopeGroupName=[" + scopeGroup.getName() + "]");
						}
						catch (NoSuchGroupException e) {
							logger.error(e.getMessage() + " using current group scopeId=[{0}]",
									scopeGroup.getGroupId());
						}
					}
				}
				root = new DefaultTreeNode( new PrimefacesFolder(-1, "level00" , null), null);
				folderTreeModel = new FolderTreeModel(scopeGroup,root);				
				rootNodeId = ((PrimefacesFolder) root.getChildren().get(0).getData()).getId();
//				logNodesState();
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return folderTreeModel;
	}

	public void setFolderTreeModel(FolderTreeModel folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
	}

	public String documentAddCancel(){
		setRenderedPanel("documentList");
		return "/views/portletViewMode.xhtml";		
	}

	public String getRenderedPanel(){
		return renderedPanel;
	}
	
	public void setRenderedPanel(String renderedPanel){
		this.renderedPanel = renderedPanel;
	}

	public void expandLevel(String level){
	    expandLevel= (int) Integer.parseInt(level);
	    initialTreeNodeRendering=true;
	    expandedTreeNodeList.clear();
	    forceTreeRequery();
	}
	
}

