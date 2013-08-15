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

package org.broadleafcommerce.common.web.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;

import org.thymeleaf.processor.IProcessor;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BLCAdminDialect extends AbstractDialect {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Set<IProcessor> processors = new HashSet<IProcessor>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.IDialect#getPrefix()
   */
  @Override public String getPrefix() {
    return "blc_admin";
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.AbstractDialect#getProcessors()
   */
  @Override public Set<IProcessor> getProcessors() {
    return processors;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.IDialect#isLenient()
   */
  @Override public boolean isLenient() {
    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  processors  DOCUMENT ME!
   */
  public void setProcessors(Set<IProcessor> processors) {
    this.processors = processors;
  }

} // end class BLCAdminDialect
