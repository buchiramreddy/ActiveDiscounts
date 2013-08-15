/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FieldPath {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<String> associationPath      = new ArrayList<String>();

  /** DOCUMENT ME! */
  protected String       targetProperty;

  /** DOCUMENT ME! */
  protected List<String> targetPropertyPieces = new ArrayList<String>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getAssociationPath() {
    return associationPath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTargetProperty() {
    return targetProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getTargetPropertyPieces() {
    return targetPropertyPieces;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  associationPath  DOCUMENT ME!
   */
  public void setAssociationPath(List<String> associationPath) {
    this.associationPath = associationPath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetProperty  DOCUMENT ME!
   */
  public void setTargetProperty(String targetProperty) {
    this.targetProperty = targetProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetPropertyPieces  DOCUMENT ME!
   */
  public void setTargetPropertyPieces(List<String> targetPropertyPieces) {
    this.targetPropertyPieces = targetPropertyPieces;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   associationPath  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldPath withAssociationPath(List<String> associationPath) {
    setAssociationPath(associationPath);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   targetProperty  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldPath withTargetProperty(String targetProperty) {
    setTargetProperty(targetProperty);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   targetPropertyPieces  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldPath withTargetPropertyPieces(List<String> targetPropertyPieces) {
    setTargetPropertyPieces(targetPropertyPieces);

    return this;
  }
} // end class FieldPath
