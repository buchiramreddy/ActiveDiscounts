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

package org.broadleafcommerce.profile.core.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import javax.persistence.OptimisticLockException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.profile.core.dao.IdGenerationDao;
import org.broadleafcommerce.profile.core.domain.IdGeneration;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blIdGenerationService")
public class IdGenerationServiceImpl implements IdGenerationService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(IdGenerationServiceImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blIdGenerationDao")
  protected IdGenerationDao idGenerationDao;

  /** DOCUMENT ME! */
  protected Map<String, Id> idTypeIdMap = new HashMap<String, Id>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.IdGenerationService#findNextId(java.lang.String)
   */
  @Override public Long findNextId(String idType) {
    Id id;

    synchronized (idTypeIdMap) {
      id = idTypeIdMap.get(idType);

      if (id == null) {
        // recheck, another thread may have added this.
        id = idTypeIdMap.get(idType);

        if (id == null) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Getting the initial id from the database.");
          }

          IdGeneration idGeneration = getCurrentIdRange(idType);
          id = new Id(idGeneration.getBatchStart(), idGeneration.getBatchSize());
        }

        idTypeIdMap.put(idType, id);
      }
    }

    synchronized (id) {
      if (id.batchSize == 0L) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Updating batch size for idType " + idType);
        }

        IdGeneration idGeneration = getCurrentIdRange(idType);
        id.nextId    = idGeneration.getBatchStart();
        id.batchSize = idGeneration.getBatchSize();
      }

      Long retId = id.nextId++;
      id.batchSize--;

      return retId;
    }
  } // end method findNextId

  //~ ------------------------------------------------------------------------------------------------------------------

  private IdGeneration getCurrentIdRange(String idType) {
    IdGeneration idGeneration = null;
    int          retryCount   = 0;
    boolean      stale        = true;

    while (stale) {
      try {
        idGeneration = idGenerationDao.findNextId(idType);
        stale        = false;
      } catch (OptimisticLockException e) {
        // do nothing -- we will try again
        if (LOG.isWarnEnabled()) {
          LOG.warn("Error saving batch start for " + idType + ".  Requerying table.");
        }
      } catch (Exception e) {
        throw new RuntimeException("Unable to retrieve id range for " + idType, e);
      }

      if (retryCount >= 10) {
        throw new RuntimeException("Unable to retrieve id range for " + idType + ". Tried " + retryCount
          + " times, but the version for this entity continues to be concurrently modified.");
      }

      retryCount++;
    }

    return idGeneration;
  } // end method getCurrentIdRange

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  private class Id {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    public Long batchSize;
    public Long nextId;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    public Id(Long nextId, Long batchSize) {
      this.nextId    = nextId;
      this.batchSize = batchSize;
    }
  }
} // end class IdGenerationServiceImpl
