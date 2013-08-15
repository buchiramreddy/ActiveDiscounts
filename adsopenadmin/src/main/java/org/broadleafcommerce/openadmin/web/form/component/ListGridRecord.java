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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.broadleafcommerce.openadmin.web.form.entity.Field;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ListGridRecord {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** Convenience map keyed by the field name. Used to guarantee field ordering with header fields within a ListGrid */
  protected Map<String, Field> fieldMap;

  /** DOCUMENT ME! */
  protected List<Field>        fields       = new ArrayList<Field>();

  /** DOCUMENT ME! */
  protected List<Field>        hiddenFields = new ArrayList<Field>();

  /** DOCUMENT ME! */
  protected String             id;

  /** DOCUMENT ME! */
  protected ListGrid listGrid;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public void clearFieldMap() {
    fieldMap = null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean getCanLinkToExternalEntity() {
    return StringUtils.isNotBlank(listGrid.getExternalEntitySectionKey());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getExternalEntityPath() {
    return listGrid.getExternalEntitySectionKey() + "/" + id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a {@link org.broadleafcommerce.openadmin.web.form.entity.Field} in this record for a particular field name.
   * Used when displaying a {@link org.broadleafcommerce.openadmin.web.form.component.ListGrid} in order to guarantee
   * proper field ordering
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  a {@link org.broadleafcommerce.openadmin.web.form.entity.Field} in this record for a particular field
   *          name.
   */
  public Field getField(String fieldName) {
    if (fieldMap == null) {
      fieldMap = new LinkedHashMap<String, Field>();

      for (Field field : fields) {
        fieldMap.put(field.getName(), field);
      }

      for (Field hiddenField : hiddenFields) {
        fieldMap.put(hiddenField.getName(), hiddenField);
      }
    }

    Field field = fieldMap.get(fieldName);

    // We'll return a null field is this particular record doesn't have this polymorphic property.
    // This prevents NPEs in list grids
    if (field == null) {
      field = new Field();
    }

    return field;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Normally you should not be looping through these fields. In order to preserve proper field ordering, instead you
   * should loop through {@link org.broadleafcommerce.openadmin.web.form.component.ListGrid#getHeaderFields()} and then
   * invoke the {@link #getField(String)} method with that header field name.
   *
   * @return  normally you should not be looping through these fields.
   */
  public List<Field> getFields() {
    return fields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Field> getHiddenFields() {
    return hiddenFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getHiddenFieldsJson() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("\"hiddenFields\":[");

    for (int j = 0; j < hiddenFields.size(); j++) {
      sb.append("{\"name\":\"");
      sb.append(hiddenFields.get(j).getName());
      sb.append("\",\"val\":\"");
      sb.append(hiddenFields.get(j).getValue());
      sb.append("\"}");

      if (j < (hiddenFields.size() - 1)) {
        sb.append(",");
      }
    }

    sb.append("]}");

    return sb.toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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
  public int getIndex() {
    return listGrid.getStartIndex() + listGrid.getRecords().indexOf(this);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ListGrid getListGrid() {
    return listGrid;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPath() {
    return listGrid.getPath() + "/" + id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fields  DOCUMENT ME!
   */
  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  hiddenFields  DOCUMENT ME!
   */
  public void setHiddenFields(List<Field> hiddenFields) {
    this.hiddenFields = hiddenFields;
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
   * @param  listGrid  DOCUMENT ME!
   */
  public void setListGrid(ListGrid listGrid) {
    this.listGrid = listGrid;
  }
} // end class ListGridRecord
