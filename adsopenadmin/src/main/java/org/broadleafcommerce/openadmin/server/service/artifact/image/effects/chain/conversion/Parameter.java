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

package org.broadleafcommerce.openadmin.server.service.artifact.image.effects.chain.conversion;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class Parameter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Class  parameterClass;
  private Object parameterInstance;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The parameterClass.
   *
   * @return  the parameterClass
   */
  public Class getParameterClass() {
    return parameterClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The parameterInstance.
   *
   * @return  the parameterInstance
   */
  public Object getParameterInstance() {
    return parameterInstance;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  parameterClass  the parameterClass to set
   */
  public void setParameterClass(Class parameterClass) {
    this.parameterClass = parameterClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  parameterInstance  the parameterInstance to set
   */
  public void setParameterInstance(Object parameterInstance) {
    this.parameterInstance = parameterInstance;
  }

} // end class Parameter
