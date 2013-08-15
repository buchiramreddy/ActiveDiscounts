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

package org.broadleafcommerce.openadmin.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO object to represent the components of a Section (a Module has many of these).
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class Section {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<String> sectionPermissions;

  /** DOCUMENT ME! */
  protected String       sectionPresenterClass;

  /** DOCUMENT ME! */
  protected String       sectionPresenterKey;

  /** DOCUMENT ME! */
  protected String sectionTitle;

  /** DOCUMENT ME! */
  protected String sectionViewClass;

  /** DOCUMENT ME! */
  protected String sectionViewKey;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new Section object.
   *
   * @param  sectionTitle           DOCUMENT ME!
   * @param  sectionViewKey         DOCUMENT ME!
   * @param  sectionViewClass       DOCUMENT ME!
   * @param  sectionPresenterKey    DOCUMENT ME!
   * @param  sectionPresenterClass  DOCUMENT ME!
   * @param  sectionPermissions     DOCUMENT ME!
   */
  public Section(String sectionTitle, String sectionViewKey, String sectionViewClass, String sectionPresenterKey,
    String sectionPresenterClass, List<String> sectionPermissions) {
    this.sectionTitle          = sectionTitle;
    this.sectionViewKey        = sectionViewKey;
    this.sectionViewClass      = sectionViewClass;
    this.sectionPresenterKey   = sectionPresenterKey;
    this.sectionPresenterClass = sectionPresenterClass;
    this.sectionPermissions    = new ArrayList<String>(sectionPermissions);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getSectionPermissions() {
    return sectionPermissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionPresenterClass() {
    return sectionPresenterClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionPresenterKey() {
    return sectionPresenterKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionTitle() {
    return sectionTitle;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionViewClass() {
    return sectionViewClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSectionViewKey() {
    return sectionViewKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionPermissions  DOCUMENT ME!
   */
  public void setSectionPermissions(List<String> sectionPermissions) {
    this.sectionPermissions = sectionPermissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionPresenterClass  DOCUMENT ME!
   */
  public void setSectionPresenterClass(String sectionPresenterClass) {
    this.sectionPresenterClass = sectionPresenterClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionPresenterKey  DOCUMENT ME!
   */
  public void setSectionPresenterKey(String sectionPresenterKey) {
    this.sectionPresenterKey = sectionPresenterKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionTitle  DOCUMENT ME!
   */
  public void setSectionTitle(String sectionTitle) {
    this.sectionTitle = sectionTitle;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionViewClass  DOCUMENT ME!
   */
  public void setSectionViewClass(String sectionViewClass) {
    this.sectionViewClass = sectionViewClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sectionViewKey  DOCUMENT ME!
   */
  public void setSectionViewKey(String sectionViewKey) {
    this.sectionViewKey = sectionViewKey;
  }

} // end class Section
