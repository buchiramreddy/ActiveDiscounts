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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.CompareToBuilder;

import org.broadleafcommerce.openadmin.web.form.component.ListGrid;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class Tab {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Integer order;

  /** DOCUMENT ME! */
  protected String title;

  /** DOCUMENT ME! */
  Set<FieldGroup> fieldGroups = new TreeSet<FieldGroup>(new Comparator<FieldGroup>() {
        @Override public int compare(FieldGroup o1, FieldGroup o2) {
          return new CompareToBuilder().append(o1.getOrder(), o2.getOrder()).append(o1.getTitle(), o2.getTitle())
            .toComparison();
        }
      });

  /** DOCUMENT ME! */
  Set<ListGrid> listGrids = new TreeSet<ListGrid>(new Comparator<ListGrid>() {
        @Override public int compare(ListGrid o1, ListGrid o2) {
          return new CompareToBuilder().append(o1.getOrder(), o2.getOrder()).append(o1.getSubCollectionFieldName(),
              o2.getSubCollectionFieldName()).toComparison();
        }
      });

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   groupTitle  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldGroup findGroup(String groupTitle) {
    for (FieldGroup fg : fieldGroups) {
      if ((fg.getTitle() != null) && fg.getTitle().equals(groupTitle)) {
        return fg;
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
  public Set<FieldGroup> getFieldGroups() {
    return fieldGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Field> getFields() {
    List<Field> fields = new ArrayList<Field>();

    for (FieldGroup fg : getFieldGroups()) {
      fields.addAll(fg.getFields());
    }

    return fields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsVisible() {
    if (listGrids.size() > 0) {
      return true;
    }

    for (FieldGroup fg : fieldGroups) {
      if (fg.getIsVisible()) {
        return true;
      }
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Set<ListGrid> getListGrids() {
    return listGrids;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTitle() {
    return title;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  listGrid  DOCUMENT ME!
   */
  public void removeListGrid(ListGrid listGrid) {
    listGrids.remove(listGrid);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldGroups  DOCUMENT ME!
   */
  public void setFieldGroups(Set<FieldGroup> fieldGroups) {
    this.fieldGroups = fieldGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  listGrids  DOCUMENT ME!
   */
  public void setListGrids(Set<ListGrid> listGrids) {
    this.listGrids = listGrids;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Integer order) {
    this.order = order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  title  DOCUMENT ME!
   */
  public void setTitle(String title) {
    this.title = title;
  }

} // end class Tab
