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

package org.broadleafcommerce.openadmin.web.form.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.openadmin.web.form.component.ListGrid;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class EntityForm {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String  HIDDEN_GROUP        = "hiddenGroup";

  /** DOCUMENT ME! */
  public static final String  MAP_KEY_GROUP       = "keyGroup";

  /** DOCUMENT ME! */
  public static final String  DEFAULT_GROUP_NAME  = "Default";

  /** DOCUMENT ME! */
  public static final Integer DEFAULT_GROUP_ORDER = 99999;

  /** DOCUMENT ME! */
  public static final String  DEFAULT_TAB_NAME  = "General";

  /** DOCUMENT ME! */
  public static final Integer DEFAULT_TAB_ORDER = 100;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<EntityFormAction> actions                = new ArrayList<EntityFormAction>();

  /** DOCUMENT ME! */
  protected String                 ceilingEntityClassname;

  // These values are used when dynamic forms are in play. They are not rendered to the client,
  // but they can be used when performing actions on the submit event
  /** DOCUMENT ME! */
  protected Map<String, DynamicEntityFormInfo> dynamicFormInfos = new HashMap<String, DynamicEntityFormInfo>();

  // This is used in cases where there is a sub-form on this page that is dynamically
  // rendered based on other values on this entity form. It is keyed by the name of the
  // property that drives the dynamic form.
  /** DOCUMENT ME! */
  protected Map<String, EntityForm> dynamicForms = new HashMap<String, EntityForm>();

  /** DOCUMENT ME! */
  protected String                  entityType;

  // This is used to data-bind when this entity form is submitted
  /** DOCUMENT ME! */
  protected Map<String, Field> fields = null;

  /** DOCUMENT ME! */
  protected String   id;

  /** DOCUMENT ME! */
  protected String   idProperty     = "id";

  /** DOCUMENT ME! */
  protected String   mainEntityName;

  /** DOCUMENT ME! */
  protected String   sectionKey;

  /** DOCUMENT ME! */
  protected Set<Tab> tabs = new TreeSet<Tab>(new Comparator<Tab>() {
        @Override public int compare(Tab o1, Tab o2) {
          return new CompareToBuilder().append(o1.getOrder(), o2.getOrder()).append(o1.getTitle(), o2.getTitle())
            .toComparison();
        }
      });

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  action  DOCUMENT ME!
   */
  public void addAction(EntityFormAction action) {
    actions.add(action);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  field  DOCUMENT ME!
   */
  public void addField(Field field) {
    addField(field, DEFAULT_GROUP_NAME, DEFAULT_GROUP_ORDER, DEFAULT_TAB_NAME, DEFAULT_TAB_ORDER);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  field       DOCUMENT ME!
   * @param  groupName   DOCUMENT ME!
   * @param  groupOrder  DOCUMENT ME!
   * @param  tabName     DOCUMENT ME!
   * @param  tabOrder    DOCUMENT ME!
   */
  public void addField(Field field, String groupName, Integer groupOrder, String tabName, Integer tabOrder) {
    // System.out.println(String.format("Adding field [%s] to group [%s] to tab [%s]", field.getName(), groupName, tabName));
    groupName  = (groupName == null) ? DEFAULT_GROUP_NAME : groupName;
    groupOrder = (groupOrder == null) ? DEFAULT_GROUP_ORDER : groupOrder;
    tabName    = (tabName == null) ? DEFAULT_TAB_NAME : tabName;
    tabOrder   = (tabOrder == null) ? DEFAULT_TAB_ORDER : tabOrder;

    Tab tab = findTab(tabName);

    if (tab == null) {
      tab = new Tab();
      tab.setTitle(tabName);
      tab.setOrder(tabOrder);
      tabs.add(tab);
    }

    FieldGroup fieldGroup = tab.findGroup(groupName);

    if (fieldGroup == null) {
      fieldGroup = new FieldGroup();
      fieldGroup.setTitle(groupName);
      fieldGroup.setOrder(groupOrder);
      tab.getFieldGroups().add(fieldGroup);
    }

    fieldGroup.addField(field);
  } // end method addField

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  field  DOCUMENT ME!
   */
  public void addHiddenField(Field field) {
    if (StringUtils.isBlank(field.getFieldType())) {
      field.setFieldType(SupportedFieldType.HIDDEN.toString());
    }

    addField(field, HIDDEN_GROUP, DEFAULT_GROUP_ORDER, DEFAULT_TAB_NAME, DEFAULT_TAB_ORDER);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  listGrid  DOCUMENT ME!
   * @param  tabName   DOCUMENT ME!
   * @param  tabOrder  DOCUMENT ME!
   */
  public void addListGrid(ListGrid listGrid, String tabName, Integer tabOrder) {
    Tab tab = findTab(tabName);

    if (tab == null) {
      tab = new Tab();
      tab.setTitle(tabName);
      tab.setOrder(tabOrder);
      tabs.add(tab);
    }

    tab.getListGrids().add(listGrid);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  field  DOCUMENT ME!
   */
  public void addMapKeyField(Field field) {
    addField(field, MAP_KEY_GROUP, 0, DEFAULT_TAB_NAME, DEFAULT_TAB_ORDER);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Clears out the cached 'fields' variable which is used to render the form on the frontend. Use this method if you
   * want to force the entityForm to rebuild itself based on the tabs and groups that have been assigned and populated
   */
  public void clearFieldsMap() {
    fields = null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field findField(String fieldName) {
    fieldName = sanitizeFieldName(fieldName);

    for (Tab tab : tabs) {
      for (FieldGroup fieldGroup : tab.getFieldGroups()) {
        for (Field field : fieldGroup.getFields()) {
          if (field.getName().equals(fieldName)) {
            return field;
          }
        }
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Convenience method for grabbing a grid by its collection field name. This is very similar to
   * {@link #findField(String)} but differs in that this only searches through the sub collections for the current
   * entity
   *
   * @param   collectionFieldName  the field name of the collection on the top-level entity
   *
   * @return  convenience method for grabbing a grid by its collection field name.
   */
  public ListGrid findListGrid(String collectionFieldName) {
    for (ListGrid grid : getAllListGrids()) {
      if (grid.getSubCollectionFieldName().equals(collectionFieldName)) {
        return grid;
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   tabTitle  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Tab findTab(String tabTitle) {
    for (Tab tab : tabs) {
      if ((tab.getTitle() != null) && tab.getTitle().equals(tabTitle)) {
        return tab;
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Tab findTabForField(String fieldName) {
    fieldName = sanitizeFieldName(fieldName);

    for (Tab tab : tabs) {
      for (FieldGroup fieldGroup : tab.getFieldGroups()) {
        for (Field field : fieldGroup.getFields()) {
          if (field.getName().equals(fieldName)) {
            return tab;
          }
        }
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<EntityFormAction> getActions() {
    List<EntityFormAction> clonedActions = new ArrayList<EntityFormAction>(actions);
    Collections.reverse(clonedActions);

    return Collections.unmodifiableList(clonedActions);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<ListGrid> getAllListGrids() {
    List<ListGrid> list = new ArrayList<ListGrid>();

    for (Tab tab : tabs) {
      for (ListGrid lg : tab.getListGrids()) {
        list.add(lg);
      }
    }

    return list;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingEntityClassname() {
    return ceilingEntityClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EntityForm getDynamicForm(String name) {
    return getDynamicForms().get(name);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicEntityFormInfo getDynamicFormInfo(String name) {
    return getDynamicFormInfos().get(name);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, DynamicEntityFormInfo> getDynamicFormInfos() {
    return dynamicFormInfos;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, EntityForm> getDynamicForms() {
    return dynamicForms;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getEntityType() {
    return entityType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * A flattened, field name keyed representation of all of the fields in all of the groups for this form.
   *
   * @return  a flattened, field name keyed representation of all of the fields in all of the groups for this form
   */
  public Map<String, Field> getFields() {
    if (fields == null) {
      Map<String, Field> map = new LinkedHashMap<String, Field>();

      for (Tab tab : tabs) {
        for (FieldGroup group : tab.getFieldGroups()) {
          for (Field field : group.getFields()) {
            map.put(field.getName(), field);
          }
        }
      }

      fields = map;
    }

    for (Entry<String, EntityForm> entry : dynamicForms.entrySet()) {
      Map<String, Field> dynamicFormFields = entry.getValue().getFields();
      fields.putAll(dynamicFormFields);
    }

    return fields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* *********************** */
  /* GENERIC GETTERS/SETTERS */
  /* *********************** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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
  public String getMainEntityName() {
    return StringUtils.isBlank(mainEntityName) ? "" : mainEntityName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionKey() {
    return (sectionKey.charAt(0) == '/') ? sectionKey : ('/' + sectionKey);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Set<Tab> getTabs() {
    return tabs;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   * @param  ef    DOCUMENT ME!
   */
  public void putDynamicForm(String name, EntityForm ef) {
    getDynamicForms().put(name, ef);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   * @param  info  DOCUMENT ME!
   */
  public void putDynamicFormInfo(String name, DynamicEntityFormInfo info) {
    getDynamicFormInfos().put(name, info);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  action  DOCUMENT ME!
   */
  public void removeAction(EntityFormAction action) {
    actions.remove(action);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public void removeAllActions() {
    actions.clear();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field removeField(String fieldName) {
    Field      fieldToRemove   = null;
    FieldGroup containingGroup = null;

findField:  {
      for (Tab tab : tabs) {
        for (FieldGroup fieldGroup : tab.getFieldGroups()) {
          for (Field field : fieldGroup.getFields()) {
            if (field.getName().equals(fieldName)) {
              fieldToRemove   = field;
              containingGroup = fieldGroup;

              break findField;
            }
          }
        }
      }
    }

    if (fieldToRemove != null) {
      containingGroup.removeField(fieldToRemove);
    }

    if (fields != null) {
      fields.remove(fieldName);
    }

    return fieldToRemove;
  } // end method removeField

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   subCollectionFieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ListGrid removeListGrid(String subCollectionFieldName) {
    ListGrid lgToRemove    = null;
    Tab      containingTab = null;

findLg:  {
      for (Tab tab : tabs) {
        for (ListGrid lg : tab.getListGrids()) {
          if (subCollectionFieldName.equals(lg.getSubCollectionFieldName())) {
            lgToRemove    = lg;
            containingTab = tab;

            break findLg;
          }
        }
      }
    }

    if (lgToRemove != null) {
      containingTab.removeListGrid(lgToRemove);
    }

    if ((containingTab.getListGrids().size() == 0) && (containingTab.getFields().size() == 0)) {
      removeTab(containingTab);
    }

    return lgToRemove;
  } // end method removeListGrid

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tab  DOCUMENT ME!
   */
  public void removeTab(Tab tab) {
    tabs.remove(tab);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Since this field name could come from the frontend (where all fields are referenced like fields[name].value, we
   * need to strip that part out to look up the real field name in this entity.
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  since this field name could come from the frontend (where all fields are referenced like
   *          fields[name].value, we need to strip that part out to look up the real field name in this entity.
   */
  public String sanitizeFieldName(String fieldName) {
    if (fieldName.contains("[")) {
      fieldName = fieldName.substring(fieldName.indexOf('[') + 1, fieldName.indexOf(']'));
    }

    return fieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  actions  DOCUMENT ME!
   */
  public void setActions(List<EntityFormAction> actions) {
    this.actions = actions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingEntityClassname  DOCUMENT ME!
   */
  public void setCeilingEntityClassname(String ceilingEntityClassname) {
    this.ceilingEntityClassname = ceilingEntityClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dynamicFormInfos  DOCUMENT ME!
   */
  public void setDynamicFormInfos(Map<String, DynamicEntityFormInfo> dynamicFormInfos) {
    this.dynamicFormInfos = dynamicFormInfos;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dynamicForms  DOCUMENT ME!
   */
  public void setDynamicForms(Map<String, EntityForm> dynamicForms) {
    this.dynamicForms = dynamicForms;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entityType  DOCUMENT ME!
   */
  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(String id) {
    this.id = id;
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

  /**
   * DOCUMENT ME!
   *
   * @param  mainEntityName  DOCUMENT ME!
   */
  public void setMainEntityName(String mainEntityName) {
    this.mainEntityName = mainEntityName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public void setReadOnly() {
    if (getFields() != null) {
      for (Entry<String, Field> entry : getFields().entrySet()) {
        entry.getValue().setReadOnly(true);
      }
    }

    if (getAllListGrids() != null) {
      for (ListGrid lg : getAllListGrids()) {
        lg.setReadOnly(true);
      }
    }

    if (getDynamicForms() != null) {
      for (Entry<String, EntityForm> entry : getDynamicForms().entrySet()) {
        entry.getValue().setReadOnly();
      }
    }

    actions.clear();
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
   * @param  tabs  DOCUMENT ME!
   */
  public void setTabs(Set<Tab> tabs) {
    this.tabs = tabs;
  }

} // end class EntityForm
