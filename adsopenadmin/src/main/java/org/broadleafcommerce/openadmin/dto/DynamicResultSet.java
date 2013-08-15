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

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class DynamicResultSet implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Integer batchId;

  private ClassMetadata classMetaData;
  private Integer       pageSize;
  private Entity[]      records;
  private Integer       startIndex;
  private Integer       totalRecords;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new DynamicResultSet object.
   */
  public DynamicResultSet() {
    // do nothing
  }

  /**
   * Creates a new DynamicResultSet object.
   *
   * @param  classMetaData  DOCUMENT ME!
   */
  public DynamicResultSet(ClassMetadata classMetaData) {
    this.classMetaData = classMetaData;
  }

  /**
   * Creates a new DynamicResultSet object.
   *
   * @param  records       DOCUMENT ME!
   * @param  totalRecords  DOCUMENT ME!
   */
  public DynamicResultSet(Entity[] records, Integer totalRecords) {
    this.records      = records;
    this.totalRecords = totalRecords;
  }

  /**
   * Creates a new DynamicResultSet object.
   *
   * @param  classMetaData  DOCUMENT ME!
   * @param  records        DOCUMENT ME!
   * @param  totalRecords   DOCUMENT ME!
   */
  public DynamicResultSet(ClassMetadata classMetaData, Entity[] records, Integer totalRecords) {
    this.records       = records;
    this.classMetaData = classMetaData;
    this.totalRecords  = totalRecords;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getBatchId() {
    return batchId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ClassMetadata getClassMetaData() {
    return classMetaData;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPageSize() {
    return pageSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Entity[] getRecords() {
    return records;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getStartIndex() {
    return startIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getTotalRecords() {
    return totalRecords;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  batchId  DOCUMENT ME!
   */
  public void setBatchId(Integer batchId) {
    this.batchId = batchId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  classMetaData  DOCUMENT ME!
   */
  public void setClassMetaData(ClassMetadata classMetaData) {
    this.classMetaData = classMetaData;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  pageSize  DOCUMENT ME!
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  records  DOCUMENT ME!
   */
  public void setRecords(Entity[] records) {
    this.records = records;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  startIndex  DOCUMENT ME!
   */
  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  totalRecords  DOCUMENT ME!
   */
  public void setTotalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
  }

} // end class DynamicResultSet
