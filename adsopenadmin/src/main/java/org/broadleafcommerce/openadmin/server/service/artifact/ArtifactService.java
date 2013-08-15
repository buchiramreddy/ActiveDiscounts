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

package org.broadleafcommerce.openadmin.server.service.artifact;

import java.io.InputStream;

import java.util.Map;

import org.broadleafcommerce.openadmin.server.service.artifact.image.Operation;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/10/11 Time: 3:33 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface ArtifactService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   parameterMap    DOCUMENT ME!
   * @param   artifactStream  DOCUMENT ME!
   * @param   mimeType        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Operation[] buildOperations(Map<String, String> parameterMap, InputStream artifactStream, String mimeType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   artifactStream  DOCUMENT ME!
   * @param   operations      DOCUMENT ME!
   * @param   mimeType        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  Exception  DOCUMENT ME!
   */
  InputStream convert(InputStream artifactStream, Operation[] operations, String mimeType) throws Exception;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ArtifactProcessor[] getArtifactProcessors();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  artifactProcessors  DOCUMENT ME!
   */
  void setArtifactProcessors(ArtifactProcessor[] artifactProcessors);
} // end interface ArtifactService
