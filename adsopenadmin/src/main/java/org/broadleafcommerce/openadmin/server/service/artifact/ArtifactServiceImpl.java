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

import org.springframework.stereotype.Service;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/10/11 Time: 2:24 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blArtifactService")
public class ArtifactServiceImpl implements ArtifactService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected ArtifactProcessor[] artifactProcessors;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.artifact.ArtifactService#buildOperations(java.util.Map,java.io.InputStream,
   *       java.lang.String)
   */
  @Override public Operation[] buildOperations(Map<String, String> parameterMap, InputStream artifactStream,
    String mimeType) {
    for (ArtifactProcessor artifactProcessor : artifactProcessors) {
      if (artifactProcessor.isSupported(artifactStream, mimeType)) {
        return artifactProcessor.buildOperations(parameterMap, artifactStream, mimeType);
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.artifact.ArtifactService#convert(java.io.InputStream,org.broadleafcommerce.openadmin.server.service.artifact.image.Operation[],
   *       java.lang.String)
   */
  @Override public InputStream convert(InputStream artifactStream, Operation[] operations, String mimeType)
    throws Exception {
    for (ArtifactProcessor artifactProcessor : artifactProcessors) {
      if (artifactProcessor.isSupported(artifactStream, mimeType)) {
        return artifactProcessor.convert(artifactStream, operations, mimeType);
      }
    }

    return artifactStream;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.artifact.ArtifactService#getArtifactProcessors()
   */
  @Override public ArtifactProcessor[] getArtifactProcessors() {
    return artifactProcessors;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.artifact.ArtifactService#setArtifactProcessors(org.broadleafcommerce.openadmin.server.service.artifact.ArtifactProcessor[])
   */
  @Override public void setArtifactProcessors(ArtifactProcessor[] artifactProcessors) {
    this.artifactProcessors = artifactProcessors;
  }
} // end class ArtifactServiceImpl
