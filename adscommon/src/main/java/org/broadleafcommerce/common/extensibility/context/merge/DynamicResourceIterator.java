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

package org.broadleafcommerce.common.extensibility.context.merge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.extensibility.context.ResourceInputStream;


/**
 * This class serves as a basic iterator for a list of source files to be merged. The interesting part, however, is that
 * additional resources may be inserted at any time at the current iterator position.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class DynamicResourceIterator extends ArrayList<ResourceInputStream> {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(DynamicResourceIterator.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private int embeddedInsertPosition = 0;

  private int position = 0;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.util.ArrayList#add(org.broadleafcommerce.common.extensibility.context.ResourceInputStream)
   */
  @Override public boolean add(ResourceInputStream resourceInputStream) {
    byte[] sourceArray;

    try {
      sourceArray = buildArrayFromStream(resourceInputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ResourceInputStream ris = new ResourceInputStream(new ByteArrayInputStream(sourceArray), null,
        resourceInputStream.getNames());

    return super.add(ris);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.util.ArrayList#add(int, org.broadleafcommerce.common.extensibility.context.ResourceInputStream)
   */
  @Override public void add(int index, ResourceInputStream resourceInputStream) {
    byte[] sourceArray;

    try {
      sourceArray = buildArrayFromStream(resourceInputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ResourceInputStream ris = new ResourceInputStream(new ByteArrayInputStream(sourceArray), null,
        resourceInputStream.getNames());
    super.add(index, ris);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.util.ArrayList#addAll(java.util.Collection)
   */
  @Override public boolean addAll(Collection<? extends ResourceInputStream> c) {
    for (ResourceInputStream ris : c) {
      if (!add(ris)) {
        return false;
      }
    }

    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ris  DOCUMENT ME!
   */
  public void addEmbeddedResource(ResourceInputStream ris) {
    if (embeddedInsertPosition == size()) {
      add(ris);
    } else {
      add(embeddedInsertPosition, ris);
    }

    embeddedInsertPosition++;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPosition() {
    return position;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean hasNext() {
    return position < size();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ResourceInputStream nextResource() {
    ResourceInputStream ris = get(position);
    position++;
    embeddedInsertPosition = position;

    return ris;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   source  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IOException  DOCUMENT ME!
   */
  protected byte[] buildArrayFromStream(InputStream source) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    boolean               eof  = false;

    try {
      while (!eof) {
        int temp = source.read();

        if (temp == -1) {
          eof = true;
        } else {
          baos.write(temp);
        }
      }
    } finally {
      try {
        source.close();
      } catch (Throwable e) {
        LOG.error("Unable to merge source and patch locations", e);
      }
    }

    return baos.toByteArray();
  }
} // end class DynamicResourceIterator
