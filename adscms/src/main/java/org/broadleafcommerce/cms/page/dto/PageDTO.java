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

package org.broadleafcommerce.cms.page.dto;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.cms.structure.dto.ItemCriteriaDTO;


/**
 * Page fields must be pre-processed (for example to fix image paths). This DTO allows us to process the PageFields once
 * and then cache the results.
 *
 * <p>Created by bpolster.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PageDTO implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String description;

  /** DOCUMENT ME! */
  protected Long                  id;

  /** DOCUMENT ME! */
  protected List<ItemCriteriaDTO> itemCriteriaDTOList;

  /** DOCUMENT ME! */
  protected String                localeCode;

  /** DOCUMENT ME! */
  protected Map<String, String>   pageFields     = new HashMap<String, String>();

  /** DOCUMENT ME! */
  protected Integer               priority;

  /** DOCUMENT ME! */
  protected String                ruleExpression;

  /** DOCUMENT ME! */
  protected Long                  sandboxId;

  /** DOCUMENT ME! */
  protected String                templatePath;

  /** DOCUMENT ME! */
  protected String                url;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDescription() {
    return description;
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
  public Map<String, String> getPageFields() {
    return pageFields;
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
  public String getTemplatePath() {
    return templatePath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getUrl() {
    return url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  description  DOCUMENT ME!
   */
  public void setDescription(String description) {
    this.description = description;
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
    this.localeCode = localeCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  pageFields  DOCUMENT ME!
   */
  public void setPageFields(Map<String, String> pageFields) {
    this.pageFields = pageFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  priority  DOCUMENT ME!
   */
  public void setPriority(Integer priority) {
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
   * @param  templatePath  DOCUMENT ME!
   */
  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  url  DOCUMENT ME!
   */
  public void setUrl(String url) {
    this.url = url;
  }

} // end class PageDTO
