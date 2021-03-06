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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class FieldGroup {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Set<Field> alternateOrderedFields = new HashSet<Field>();

  /** DOCUMENT ME! */
  protected Set<Field> fields = new HashSet<Field>();

  /** DOCUMENT ME! */
  protected Integer    order;

  /** DOCUMENT ME! */
  protected String title;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   field  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean addField(Field field) {
    if (field.getAlternateOrdering()) {
      return alternateOrderedFields.add(field);
    } else {
      return fields.add(field);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Set<Field> getFields() {
    List<Field> myFields = new ArrayList<Field>();
    myFields.addAll(fields);
    Collections.sort(myFields, new Comparator<Field>() {
        @Override public int compare(Field o1, Field o2) {
          return new CompareToBuilder().append(o1.getOrder(), o2.getOrder()).append(o1.getFriendlyName(),
              o2.getFriendlyName()).append(o1.getName(), o2.getName()).toComparison();
        }
      });

    if (!alternateOrderedFields.isEmpty()) {
      List<Field> mapFieldsList = new ArrayList<Field>(alternateOrderedFields);
      Collections.sort(mapFieldsList, new Comparator<Field>() {
          @Override public int compare(Field o1, Field o2) {
            return new CompareToBuilder().append(o1.getOrder(), o2.getOrder()).append(o1.getFriendlyName(),
                o2.getFriendlyName()).append(o1.getName(), o2.getName()).toComparison();
          }
        });

      /*
      alternate ordered fields whose order is less or equal to zero appear first and are
      prepended to the response list in order
       */
      List<Field> smallOrderFields = new ArrayList<Field>();

      for (Field mapField : mapFieldsList) {
        if (mapField.getOrder() <= 0) {
          smallOrderFields.add(mapField);
        }
      }

      myFields.addAll(0, smallOrderFields);

      /*
      Alternate ordered fields (specifically custom fields) have a different ordering rule than regular fields. For example,
      if a user enters 3 for the field order value for a custom field, that custom field should be the third
      on the form. Regular BLC AdminPresentation fields tends to have orders like 1000, 2000, etc..., so this
      distinction is necessary.
       */
      for (Field mapField : mapFieldsList) {
        if (mapField.getOrder() <= 0) {
          continue;
        }

        if (mapField.getOrder() < myFields.size()) {
          myFields.add(mapField.getOrder() - 1, mapField);

          continue;
        }

        myFields.add(mapField);
      }
    } // end if

    // don't allow any modification of the fields
    return Collections.unmodifiableSet(new LinkedHashSet<Field>(myFields));
  } // end method getFields

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsVisible() {
    for (Field f : getFields()) {
      if (f.getIsVisible()) {
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
   * @param   field  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean removeField(Field field) {
    if (field.getAlternateOrdering()) {
      return alternateOrderedFields.remove(field);
    } else {
      return fields.remove(field);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fields  DOCUMENT ME!
   */
  public void setFields(Set<Field> fields) {
    this.fields = fields;
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

} // end class FieldGroup
