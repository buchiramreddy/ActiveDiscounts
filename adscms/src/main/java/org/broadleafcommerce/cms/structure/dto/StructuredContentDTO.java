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

package org.broadleafcommerce.cms.structure.dto;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class StructuredContentDTO implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String contentName;

  /** DOCUMENT ME! */
  protected String contentType;

  /** DOCUMENT ME! */
  protected Long                  id;

  /** DOCUMENT ME! */
  protected List<ItemCriteriaDTO> itemCriteriaDTOList;

  /** DOCUMENT ME! */
  protected String                localeCode;

  /** DOCUMENT ME! */
  protected Integer               priority;

  /** DOCUMENT ME! */
  protected String                ruleExpression;

  /** DOCUMENT ME! */
  protected Long                  sandboxId;

  /** DOCUMENT ME! */
  protected Map                   values = new HashMap<String, String>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContentName() {
    return contentName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContentType() {
    return contentType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<ItemCriteriaDTO> getItemCriteriaDTOList() {
    return itemCriteriaDTOList;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getLocaleCode() {
    return localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPriority() {
    return priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getRuleExpression() {
    return ruleExpression;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getSandboxId() {
    return sandboxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map getValues() {
    return values;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  contentName  DOCUMENT ME!
   */
  public void setContentName(String contentName) {
    values.put("contentName", contentName);
    this.contentName = contentName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  contentType  DOCUMENT ME!
   */
  public void setContentType(String contentType) {
    values.put("contentType", contentType);
    this.contentType = contentType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  itemCriteriaDTOList  DOCUMENT ME!
   */
  public void setItemCriteriaDTOList(List<ItemCriteriaDTO> itemCriteriaDTOList) {
    this.itemCriteriaDTOList = itemCriteriaDTOList;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  localeCode  DOCUMENT ME!
   */
  public void setLocaleCode(String localeCode) {
    values.put("localeCode", localeCode);
    this.localeCode = localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  priority  DOCUMENT ME!
   */
  public void setPriority(Integer priority) {
    values.put("priority", priority);
    this.priority = priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ruleExpression  DOCUMENT ME!
   */
  public void setRuleExpression(String ruleExpression) {
    this.ruleExpression = ruleExpression;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandboxId  DOCUMENT ME!
   */
  public void setSandboxId(Long sandboxId) {
    this.sandboxId = sandboxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  values  DOCUMENT ME!
   */
  public void setValues(Map values) {
    this.values = values;
  }
} // end class StructuredContentDTO
