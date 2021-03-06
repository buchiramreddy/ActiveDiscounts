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
package org.broadleafcommerce.openadmin.web.rulebuilder.service;

import java.util.List;


/**
 * Factory class that returns the appropriate RuleBuilderFieldService given the service name. The services are injected
 * into the factory defined in applicationContext-servlet-open-admin.xml
 *
 * @see      RuleBuilderFieldService
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface RuleBuilderFieldServiceFactory {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  RuleBuilderFieldService createInstance(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<RuleBuilderFieldService> getFieldServices();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldServices  DOCUMENT ME!
   */
  void setFieldServices(List<RuleBuilderFieldService> fieldServices);
} // end interface RuleBuilderFieldServiceFactory
