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

package org.broadleafcommerce.openadmin.server.dao;

import java.lang.reflect.Type;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FieldInfo {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Type   genericType;

  /** DOCUMENT ME! */
  protected String manyToManyMappedBy;

  /** DOCUMENT ME! */
  protected String manyToManyTargetEntity;

  /** DOCUMENT ME! */
  protected String name;

  /** DOCUMENT ME! */
  protected String oneToManyMappedBy;

  /** DOCUMENT ME! */
  protected String oneToManyTargetEntity;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Type getGenericType() {
    return genericType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getManyToManyMappedBy() {
    return manyToManyMappedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getManyToManyTargetEntity() {
    return manyToManyTargetEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOneToManyMappedBy() {
    return oneToManyMappedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOneToManyTargetEntity() {
    return oneToManyTargetEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  genericType  DOCUMENT ME!
   */
  public void setGenericType(Type genericType) {
    this.genericType = genericType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  manyToManyMappedBy  DOCUMENT ME!
   */
  public void setManyToManyMappedBy(String manyToManyMappedBy) {
    this.manyToManyMappedBy = manyToManyMappedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  manyToManyTargetEntity  DOCUMENT ME!
   */
  public void setManyToManyTargetEntity(String manyToManyTargetEntity) {
    this.manyToManyTargetEntity = manyToManyTargetEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  oneToManyMappedBy  DOCUMENT ME!
   */
  public void setOneToManyMappedBy(String oneToManyMappedBy) {
    this.oneToManyMappedBy = oneToManyMappedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  oneToManyTargetEntity  DOCUMENT ME!
   */
  public void setOneToManyTargetEntity(String oneToManyTargetEntity) {
    this.oneToManyTargetEntity = oneToManyTargetEntity;
  }
} // end class FieldInfo
