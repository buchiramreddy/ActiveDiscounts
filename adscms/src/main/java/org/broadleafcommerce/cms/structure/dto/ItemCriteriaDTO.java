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


/**
 * StructuredContent data is converted into a DTO since it requires pre-processing. The data is fairly static so the
 * desire is to cache the value after it has been processed.
 *
 * <p>This DTO represents a compact version of StructuredContentItemCriteria</p>
 *
 * <p>Created by bpolster.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ItemCriteriaDTO implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String matchRule;

  /** DOCUMENT ME! */
  protected Integer qty;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMatchRule() {
    return matchRule;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getQty() {
    return qty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  matchRule  DOCUMENT ME!
   */
  public void setMatchRule(String matchRule) {
    this.matchRule = matchRule;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  qty  DOCUMENT ME!
   */
  public void setQty(Integer qty) {
    this.qty = qty;
  }
} // end class ItemCriteriaDTO
