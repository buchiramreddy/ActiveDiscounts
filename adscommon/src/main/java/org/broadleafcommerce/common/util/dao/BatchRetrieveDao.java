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

package org.broadleafcommerce.common.util.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class BatchRetrieveDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  // Default batch read size
  private int inClauseBatchSize = 300;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   <T>            DOCUMENT ME!
   * @param   query          DOCUMENT ME!
   * @param   params         DOCUMENT ME!
   * @param   parameterName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> batchExecuteReadQuery(Query query, List<?> params, String parameterName) {
    List<T> response = new ArrayList<T>();
    int     start    = 0;

    while (start < params.size()) {
      List<?> batchParams = params.subList(start,
          (params.size() < inClauseBatchSize) ? params.size() : inClauseBatchSize);
      query.setParameter(parameterName, batchParams);
      response.addAll(query.getResultList());
      start += inClauseBatchSize;
    }

    return response;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getInClauseBatchSize() {
    return inClauseBatchSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  inClauseBatchSize  DOCUMENT ME!
   */
  public void setInClauseBatchSize(int inClauseBatchSize) {
    this.inClauseBatchSize = inClauseBatchSize;
  }

} // end class BatchRetrieveDao
