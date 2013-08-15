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

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 *
 *           <p>An example of the Serialized JSON:</p>
 *
 *           <p>{'fields': [ {'label':'Order Item - name', 'name':'name', 'operators':blcOperators_Text, 'options':[]},
 *           {'label':'Order Item - Retail Price', 'name':'retailPrice', 'operators':blcOperators_Numeric,
 *           'options':[]}, {'label':'Product - is Featured Product', 'name':'sku.product.isFeaturedProduct',
 *           'operators':blcOperators_Boolean, 'options':[]}, {'label':'Sku - Active End Date',
 *           'name':'sku.activeEndDate', 'operators':blcOperators_Date, 'options':[]}, {'label':'Category - Fulfillment
 *           Type', 'name':'category.fulfillmentType', 'operators':blcOperators_Enumeration,
 *           'options':blcOptions_FulfillmentType} ]}</p>
 * @version  $Revision$, $Date$
 */
public class FieldWrapper implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected ArrayList<FieldDTO> fields = new ArrayList<FieldDTO>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ArrayList<FieldDTO> getFields() {
    return fields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  JsonGenerationException  DOCUMENT ME!
   * @throws  JsonMappingException     DOCUMENT ME!
   * @throws  IOException              DOCUMENT ME!
   */
  public String serialize() throws JsonGenerationException, JsonMappingException, IOException {
    return new ObjectMapper().writeValueAsString(this);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fields  DOCUMENT ME!
   */
  public void setFields(ArrayList<FieldDTO> fields) {
    this.fields = fields;
  }
} // end class FieldWrapper
