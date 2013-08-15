/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.web.form.component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import org.broadleafcommerce.common.presentation.client.AddMethodType;

import org.broadleafcommerce.openadmin.web.form.entity.Field;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ListGrid {
  //~ Enums ------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @author   $author$
   * @version  $Revision$, $Date$
   */
  public enum Type {
    //~ Enum constants -------------------------------------------------------------------------------------------------

    MAIN, INLINE, TO_ONE, BASIC, ADORNED, ADORNED_WITH_FORM, MAP, TRANSLATION, ASSET
  }

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected AddMethodType addMethodType;

  /** DOCUMENT ME! */
  protected Boolean       canFilterAndSort;

  /** DOCUMENT ME! */
  protected String className;

  /** DOCUMENT ME! */
  protected String containingEntityId;

  // If this list grid is a sublistgrid, meaning it is rendered as part of a different entity, these properties
  // help identify the parent entity.
  /** DOCUMENT ME! */
  protected String externalEntitySectionKey;

  /** DOCUMENT ME! */
  protected String friendlyName = null;

  /** DOCUMENT ME! */
  protected String idProperty;

  /** DOCUMENT ME! */
  protected int    order;

  /** DOCUMENT ME! */
  protected Set<Field>           headerFields = new TreeSet<Field>(new Comparator<Field>() {
        @Override public int compare(Field o1, Field o2) {
          return new CompareToBuilder().append(o1.getOrder(), o2.getOrder()).append(o1.getFriendlyName(),
              o2.getFriendlyName()).append(o1.getName(), o2.getName()).toComparison();
        }
      });

  /** DOCUMENT ME! */
  protected Boolean              hideIdColumn;

  /** DOCUMENT ME! */
  protected Boolean              isReadOnly;

  /** DOCUMENT ME! */
  protected String               listGridType;

  /** DOCUMENT ME! */
  protected int                  pageSize;

  /** DOCUMENT ME! */
  protected String               pathOverride;

  /** DOCUMENT ME! */
  protected List<ListGridRecord> records = new ArrayList<ListGridRecord>();

  // These actions will start greyed out and unable to be clicked until a specific row has been selected
  /** DOCUMENT ME! */
  protected List<ListGridAction> rowActions = new ArrayList<ListGridAction>();

  // The section url that maps to this particular list grid
  /** DOCUMENT ME! */
  protected String               sectionKey;

  /** DOCUMENT ME! */
  protected int                  startIndex;

  /** DOCUMENT ME! */
  protected String               subCollectionFieldName;

  /** DOCUMENT ME! */
  protected List<ListGridAction> toolbarActions = new ArrayList<ListGridAction>();

  /** DOCUMENT ME! */
  protected int                  totalRecords;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  action  DOCUMENT ME!
   */
  public void addRowAction(ListGridAction action) {
    getRowActions().add(action);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  action  DOCUMENT ME!
   */
  public void addToolbarAction(ListGridAction action) {
    getToolbarActions().add(action);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public AddMethodType getAddMethodType() {
    return addMethodType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getCanFilterAndSort() {
    return ((canFilterAndSort == null) ? true : canFilterAndSort);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getClassName() {
    return className;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getClickable() {
    return !"main".equals(listGridType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContainingEntityId() {
    return containingEntityId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getExternalEntitySectionKey() {
    return externalEntitySectionKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFriendlyName() {
    return friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Set<Field> getHeaderFields() {
    return headerFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getHideIdColumn() {
    return (hideIdColumn == null) ? false : hideIdColumn;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************** */
  /* STANDARD GETTERS / SETTERS */
  /* ************************** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getIdProperty() {
    return idProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getListGridType() {
    return listGridType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPageSize() {
    return pageSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************** */
  /* CUSTOM METHODS */
  /* ************** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPath() {
    if (StringUtils.isNotBlank(pathOverride)) {
      return pathOverride;
    }

    StringBuilder sb = new StringBuilder();

    if (!getSectionKey().startsWith("/")) {
      sb.append("/");
    }

    sb.append(getSectionKey());

    if (getContainingEntityId() != null) {
      sb.append("/").append(getContainingEntityId());
    }

    if (StringUtils.isNotBlank(getSubCollectionFieldName())) {
      sb.append("/").append(getSubCollectionFieldName());
    }

    // to-one grids need a slightly different grid URL; these need to be appended with 'select'
    // TODO: surely there's a better way to do this besides just hardcoding the 'select'?
    if (Type.TO_ONE.toString().toLowerCase().equals(listGridType)) {
      sb.append("/select");
    }

    return sb.toString();
  } // end method getPath

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPathOverride() {
    return pathOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getReadOnly() {
    return (isReadOnly == null) ? false : isReadOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<ListGridRecord> getRecords() {
    return records;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<ListGridAction> getRowActions() {
    return rowActions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionKey() {
    return sectionKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getStartIndex() {
    return startIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSubCollectionFieldName() {
    return subCollectionFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<ListGridAction> getToolbarActions() {
    return toolbarActions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getTotalRecords() {
    return totalRecords;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This grid is sortable if there is a reorder action defined in the toolbar. If records can be reordered, then the
   * sort functionality doesn't make any sense.
   *
   * <p>Also, map structures are currently unsortable.</p>
   *
   * @return  this grid is sortable if there is a reorder action defined in the toolbar.
   */
  public boolean isSortable() {
    return getToolbarActions().contains(DefaultListGridActions.REORDER)
      || Type.MAP.toString().toLowerCase().equals(getListGridType());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addMethodType  DOCUMENT ME!
   */
  public void setAddMethodType(AddMethodType addMethodType) {
    this.addMethodType = addMethodType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  canFilterAndSort  DOCUMENT ME!
   */
  public void setCanFilterAndSort(Boolean canFilterAndSort) {
    this.canFilterAndSort = canFilterAndSort;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  className  DOCUMENT ME!
   */
  public void setClassName(String className) {
    this.className = className;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  containingEntityId  DOCUMENT ME!
   */
  public void setContainingEntityId(String containingEntityId) {
    this.containingEntityId = containingEntityId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  externalEntitySectionKey  DOCUMENT ME!
   */
  public void setExternalEntitySectionKey(String externalEntitySectionKey) {
    this.externalEntitySectionKey = externalEntitySectionKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  friendlyName  DOCUMENT ME!
   */
  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  headerFields  DOCUMENT ME!
   */
  public void setHeaderFields(Set<Field> headerFields) {
    this.headerFields = headerFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  hideIdColumn  DOCUMENT ME!
   */
  public void setHideIdColumn(Boolean hideIdColumn) {
    this.hideIdColumn = hideIdColumn;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  idProperty  DOCUMENT ME!
   */
  public void setIdProperty(String idProperty) {
    this.idProperty = idProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************ */
  /* CUSTOM GETTERS / SETTERS */
  /* ************************ */

  /**
   * DOCUMENT ME!
   *
   * @param  listGridType  DOCUMENT ME!
   */
  public void setListGridType(Type listGridType) {
    this.listGridType = listGridType.toString().toLowerCase();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(int order) {
    this.order = order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  pageSize  DOCUMENT ME!
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  pathOverride  DOCUMENT ME!
   */
  public void setPathOverride(String pathOverride) {
    this.pathOverride = pathOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  readOnly  DOCUMENT ME!
   */
  public void setReadOnly(Boolean readOnly) {
    this.isReadOnly = readOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  records  DOCUMENT ME!
   */
  public void setRecords(List<ListGridRecord> records) {
    this.records = records;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  rowActions  DOCUMENT ME!
   */
  public void setRowActions(List<ListGridAction> rowActions) {
    this.rowActions = rowActions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionKey  DOCUMENT ME!
   */
  public void setSectionKey(String sectionKey) {
    this.sectionKey = sectionKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  startIndex  DOCUMENT ME!
   */
  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  subCollectionFieldName  DOCUMENT ME!
   */
  public void setSubCollectionFieldName(String subCollectionFieldName) {
    this.subCollectionFieldName = subCollectionFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  toolbarActions  DOCUMENT ME!
   */
  public void setToolbarActions(List<ListGridAction> toolbarActions) {
    this.toolbarActions = toolbarActions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  totalRecords  DOCUMENT ME!
   */
  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

} // end class ListGrid
