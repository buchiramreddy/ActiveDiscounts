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

import org.broadleafcommerce.openadmin.web.form.entity.Field;
import org.broadleafcommerce.openadmin.web.rulebuilder.dto.DataWrapper;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 * @version  $Revision$, $Date$
 */
public class RuleBuilderField extends Field {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected DataWrapper dataWrapper;

  /** DOCUMENT ME! */
  protected String fieldBuilder;

  /** DOCUMENT ME! */
  protected String json;

  /** DOCUMENT ME! */
  protected String jsonFieldName;

  /** DOCUMENT ME! */
  protected String styleClass;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DataWrapper getDataWrapper() {
    return dataWrapper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFieldBuilder() {
    return fieldBuilder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getJson() {
    return json;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getJsonFieldName() {
    return jsonFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getStyleClass() {
    return styleClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dataWrapper  DOCUMENT ME!
   */
  public void setDataWrapper(DataWrapper dataWrapper) {
    this.dataWrapper = dataWrapper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldBuilder  DOCUMENT ME!
   */
  public void setFieldBuilder(String fieldBuilder) {
    this.fieldBuilder = fieldBuilder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  json  DOCUMENT ME!
   */
  public void setJson(String json) {
    this.json = json;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  jsonFieldName  DOCUMENT ME!
   */
  public void setJsonFieldName(String jsonFieldName) {
    this.jsonFieldName = jsonFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  styleClass  DOCUMENT ME!
   */
  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }
} // end class RuleBuilderField
