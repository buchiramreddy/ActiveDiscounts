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

package org.broadleafcommerce.common.extensibility.context;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ResourceInputStream extends InputStream {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final InputStream is;
  private List<String>      names = new ArrayList<String>(20);

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ResourceInputStream object.
   *
   * @param  is    DOCUMENT ME!
   * @param  name  DOCUMENT ME!
   */
  public ResourceInputStream(InputStream is, String name) {
    this.is = is;
    names.add(name);
  }

  /**
   * Creates a new ResourceInputStream object.
   *
   * @param  is             DOCUMENT ME!
   * @param  name           DOCUMENT ME!
   * @param  previousNames  DOCUMENT ME!
   */
  public ResourceInputStream(InputStream is, String name, List<String> previousNames) {
    this.is = is;
    names.addAll(previousNames);

    if (!StringUtils.isEmpty(name)) {
      names.add(name);
    }
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#available()
   */
  @Override public int available() throws IOException {
    return (is == null) ? -1 : is.available();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#close()
   */
  @Override public void close() throws IOException {
    is.close();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getName() {
    assert names.size() == 1;

    return names.get(0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getNames() {
    return names;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#mark(int)
   */
  @Override public void mark(int i) {
    is.mark(i);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#markSupported()
   */
  @Override public boolean markSupported() {
    return is.markSupported();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#read()
   */
  @Override public int read() throws IOException {
    return is.read();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#read(byte[])
   */
  @Override public int read(byte[] bytes) throws IOException {
    return is.read(bytes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#read(byte[], int, int)
   */
  @Override public int read(byte[] bytes, int i, int i1) throws IOException {
    return is.read(bytes, i, i1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#reset()
   */
  @Override public void reset() throws IOException {
    is.reset();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.InputStream#skip(long)
   */
  @Override public long skip(long l) throws IOException {
    return is.skip(l);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    StringBuilder sb   = new StringBuilder(100);
    int           size = names.size();

    for (int j = 0; j < size; j++) {
      sb.append(names.get(j));

      if (j < (size - 1)) {
        sb.append(" : ");
      }
    }

    return sb.toString();
  }
} // end class ResourceInputStream
