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

package org.broadleafcommerce.openadmin.web.rulebuilder.grouping;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.openadmin.web.rulebuilder.BLCOperator;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @author   Elbert Bautista (elbertbautista)
 * @version  $Revision$, $Date$
 */
public class Group {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Boolean     isTopGroup   = false;
  private BLCOperator operatorType;

  private List<String> phrases   = new ArrayList<String>();
  private List<Group>  subGroups = new ArrayList<Group>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsTopGroup() {
    return isTopGroup;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BLCOperator getOperatorType() {
    return operatorType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getPhrases() {
    return phrases;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Group> getSubGroups() {
    return subGroups;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isTopGroup  DOCUMENT ME!
   */
  public void setIsTopGroup(Boolean isTopGroup) {
    this.isTopGroup = isTopGroup;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  operatorType  DOCUMENT ME!
   */
  public void setOperatorType(BLCOperator operatorType) {
    this.operatorType = operatorType;
  }
} // end class Group
