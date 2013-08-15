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

package org.broadleafcommerce.openadmin.web.rulebuilder.dto;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 *
 *           <p>A temporary container object used to load the data into a RuleBuilderFieldService</p>
 * @see      org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService
 * @see      org.broadleafcommerce.openadmin.web.rulebuilder.service.OrderItemFieldServiceImpl
 * @version  $Revision$, $Date$
 */
public class FieldData {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String             fieldLabel;

  /** DOCUMENT ME! */
  protected String             fieldName;

  /** DOCUMENT ME! */
  protected SupportedFieldType fieldType;

  /** DOCUMENT ME! */
  protected String             operators;

  /** DOCUMENT ME! */
  protected String             options;

  /** DOCUMENT ME! */
  protected SupportedFieldType secondaryFieldType;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  private FieldData(Builder builder) {
    this.fieldLabel         = builder.fieldLabel;
    this.fieldName          = builder.fieldName;
    this.operators          = builder.operators;
    this.options            = builder.options;
    this.fieldType          = builder.fieldType;
    this.secondaryFieldType = builder.secondaryFieldType;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFieldLabel() {
    return fieldLabel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFieldName() {
    return fieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SupportedFieldType getFieldType() {
    return fieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOperators() {
    return operators;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOptions() {
    return options;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SupportedFieldType getSecondaryFieldType() {
    return secondaryFieldType;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class Builder {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    protected String             fieldLabel         = null;
    protected String             fieldName          = null;
    protected SupportedFieldType fieldType          = null;
    protected String             operators          = null;
    protected String             options            = null;
    protected SupportedFieldType secondaryFieldType = null;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    public Builder() { }

    //~ Methods --------------------------------------------------------------------------------------------------------

    public FieldData build() {
      return new FieldData(this);
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Builder label(String fieldLabel) {
      this.fieldLabel = fieldLabel;

      return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Builder name(String fieldName) {
      this.fieldName = fieldName;

      return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Builder operators(String operators) {
      this.operators = operators;

      return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Builder options(String options) {
      this.options = options;

      return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Builder secondaryType(SupportedFieldType fieldType) {
      this.secondaryFieldType = fieldType;

      return this;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Builder type(SupportedFieldType fieldType) {
      this.fieldType = fieldType;

      return this;
    }
  } // end class Builder
} // end class FieldData
