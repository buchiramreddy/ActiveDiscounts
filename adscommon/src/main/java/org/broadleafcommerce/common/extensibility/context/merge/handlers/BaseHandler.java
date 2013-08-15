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

package org.broadleafcommerce.common.extensibility.context.merge.handlers;

/**
 * Convenience base class which all handler implementations extend. This class provides the common properties required
 * by all MergeHandler implemenations.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public abstract class BaseHandler implements MergeHandler, Comparable<Object> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected MergeHandler[] children = {};

  /** DOCUMENT ME! */
  protected String         name;

  /** DOCUMENT ME! */
  protected int    priority;

  /** DOCUMENT ME! */
  protected String xpath;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override public int compareTo(Object arg0) {
    return new Integer(getPriority()).compareTo(new Integer(((MergeHandler) arg0).getPriority()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#getChildren()
   */
  @Override public MergeHandler[] getChildren() {
    return children;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#getPriority()
   */
  @Override public int getPriority() {
    return priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#getXPath()
   */
  @Override public String getXPath() {
    return xpath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#setChildren(org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler[])
   */
  @Override public void setChildren(MergeHandler[] children) {
    this.children = children;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#setPriority(int)
   */
  @Override public void setPriority(int priority) {
    this.priority = priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.handlers.MergeHandler#setXPath(java.lang.String)
   */
  @Override public void setXPath(String xpath) {
    this.xpath = xpath;
  }

} // end class BaseHandler
