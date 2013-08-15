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

import java.sql.Blob;

import org.broadleafcommerce.cms.file.domain.StaticAssetStorage;

import org.springframework.web.multipart.MultipartFile;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/9/11 Time: 10:47 AM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface StaticAssetStorageDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   uploadedFile  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IOException  DOCUMENT ME!
   */
  Blob createBlob(MultipartFile uploadedFile) throws IOException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAssetStorage readStaticAssetStorageByStaticAssetId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAssetStorage create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  assetStorage  DOCUMENT ME!
   */
  void delete(StaticAssetStorage assetStorage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAssetStorage readStaticAssetStorageById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   assetStorage  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAssetStorage save(StaticAssetStorage assetStorage);
} // end interface StaticAssetStorageDao
