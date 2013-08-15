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

package org.broadleafcommerce.cms.file.dao;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Blob;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.cms.file.domain.StaticAssetStorage;
import org.broadleafcommerce.cms.file.domain.StaticAssetStorageImpl;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.hibernate.ejb.HibernateEntityManager;

import org.springframework.stereotype.Repository;

import org.springframework.web.multipart.MultipartFile;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/8/11 Time: 7:31 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blStaticAssetStorageDao")
public class StaticAssetStorageDaoImpl implements StaticAssetStorageDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blCMSStorage")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.dao.StaticAssetStorageDao#create()
   */
  @Override public StaticAssetStorage create() {
    return (StaticAssetStorage) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.cms.file.domain.StaticAssetStorage");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.dao.StaticAssetStorageDao#createBlob(org.springframework.web.multipart.MultipartFile)
   */
  @Override public Blob createBlob(MultipartFile uploadedFile) throws IOException {
    Long        size        = uploadedFile.getSize();
    InputStream inputStream = uploadedFile.getInputStream();

    // We'll work with Blob instances and streams so that the uploaded files are never read into memory
    return ((HibernateEntityManager) em).getSession().getLobHelper().createBlob(inputStream, size);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.dao.StaticAssetStorageDao#delete(org.broadleafcommerce.cms.file.domain.StaticAssetStorage)
   */
  @Override public void delete(StaticAssetStorage assetStorage) {
    if (!em.contains(assetStorage)) {
      assetStorage = (StaticAssetStorage) readStaticAssetStorageById(assetStorage.getId());
    }

    em.remove(assetStorage);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.dao.StaticAssetStorageDao#readStaticAssetStorageById(java.lang.Long)
   */
  @Override public StaticAssetStorage readStaticAssetStorageById(Long id) {
    return (StaticAssetStorage) em.find(StaticAssetStorageImpl.class, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.dao.StaticAssetStorageDao#readStaticAssetStorageByStaticAssetId(java.lang.Long)
   */
  @Override public StaticAssetStorage readStaticAssetStorageByStaticAssetId(Long id) {
    Query query = em.createNamedQuery("BC_READ_STATIC_ASSET_STORAGE_BY_STATIC_ASSET_ID");
    query.setParameter("id", id);

    return (StaticAssetStorage) query.getSingleResult();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.dao.StaticAssetStorageDao#save(org.broadleafcommerce.cms.file.domain.StaticAssetStorage)
   */
  @Override public StaticAssetStorage save(StaticAssetStorage assetStorage) {
    if (em.contains(assetStorage)) {
      return em.merge(assetStorage);
    }

    em.persist(assetStorage);
    em.flush();

    return assetStorage;
  }
} // end class StaticAssetStorageDaoImpl
