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

package org.broadleafcommerce.common.web;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class SandBoxContext {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final ThreadLocal<SandBoxContext> SANDBOXCONTEXT = new ThreadLocal<SandBoxContext>();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Boolean previewMode = false;

  /** DOCUMENT ME! */
  protected Long sandBoxId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static SandBoxContext getSandBoxContext() {
    return SANDBOXCONTEXT.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBoxContext  DOCUMENT ME!
   */
  public static void setSandBoxContext(SandBoxContext sandBoxContext) {
    SANDBOXCONTEXT.set(sandBoxContext);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public SandBoxContext clone() {
    SandBoxContext myContext = new SandBoxContext();
    myContext.setSandBoxId(getSandBoxId());
    myContext.setPreviewMode(getPreviewMode());

    return myContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getPreviewMode() {
    return previewMode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The sandBoxName.
   *
   * @return  the sandBoxName
   */
  public Long getSandBoxId() {
    return sandBoxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  previewMode  DOCUMENT ME!
   */
  public void setPreviewMode(Boolean previewMode) {
    this.previewMode = previewMode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  sandBoxId  the sandBoxName to set
   */
  public void setSandBoxId(Long sandBoxId) {
    this.sandBoxId = sandBoxId;
  }
} // end class SandBoxContext
