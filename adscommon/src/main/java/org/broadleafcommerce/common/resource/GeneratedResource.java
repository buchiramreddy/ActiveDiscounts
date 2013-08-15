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

package org.broadleafcommerce.common.resource;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import org.springframework.security.util.InMemoryResource;


/**
 * An in memory generated resource. This class also overrides some parent Spring AbstractResource methods to ensure
 * compatibility with the {@link org.springframework.web.servlet.resource.ResourceHttpRequestHandler}.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class GeneratedResource extends InMemoryResource {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String hashRepresentation;

  /** DOCUMENT ME! */
  protected long timeGenerated;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * <b>Note: This constructor should not be explicitly used</b>
   *
   * <p>To properly allow for serialization, we must provide this no-arg constructor that will create a "dummy"
   * GeneratedResource. The appropriate fields will be set during deserialization.</p>
   */
  public GeneratedResource() {
    super("");
  }

  /**
   * Creates a new GeneratedResource object.
   *
   * @param  source       DOCUMENT ME!
   * @param  description  DOCUMENT ME!
   */
  public GeneratedResource(byte[] source, String description) {
    super(source, description);
    timeGenerated = System.currentTimeMillis();
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.io.AbstractResource#getFilename()
   */
  @Override public String getFilename() {
    return getDescription();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getHashRepresentation() {
    return StringUtils.isBlank(hashRepresentation) ? String.valueOf(timeGenerated) : hashRepresentation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.io.AbstractResource#lastModified()
   */
  @Override public long lastModified() throws IOException {
    return timeGenerated;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  hashRepresentation  DOCUMENT ME!
   */
  public void setHashRepresentation(String hashRepresentation) {
    this.hashRepresentation = hashRepresentation;
  }

} // end class GeneratedResource
