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

package org.broadleafcommerce.openadmin.web.rulebuilder.service;

import java.util.List;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldDTO;
import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldData;
import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldWrapper;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 * @version  $Revision$, $Date$
 */
public interface RuleBuilderFieldService extends Cloneable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FieldWrapper buildFields();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override RuleBuilderFieldService clone() throws CloneNotSupportedException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FieldDTO getField(String fieldName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FieldData> getFields();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SupportedFieldType getSecondaryFieldType(String fieldName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SupportedFieldType getSupportedFieldType(String fieldName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fields  DOCUMENT ME!
   */
  void setFields(List<FieldData> fields);
} // end interface RuleBuilderFieldService
