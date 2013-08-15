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

package org.broadleafcommerce.openadmin.server.service.artifact.image;

import org.broadleafcommerce.openadmin.server.service.artifact.image.effects.chain.UnmarshalledParameter;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/10/11 Time: 1:57 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class Operation {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Double factor;

  /** DOCUMENT ME! */
  protected String                  name;

  /** DOCUMENT ME! */
  protected UnmarshalledParameter[] parameters;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Double getFactor() {
    return factor;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public UnmarshalledParameter[] getParameters() {
    return parameters;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  factor  DOCUMENT ME!
   */
  public void setFactor(Double factor) {
    this.factor = factor;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  parameters  DOCUMENT ME!
   */
  public void setParameters(UnmarshalledParameter[] parameters) {
    this.parameters = parameters;
  }
} // end class Operation
