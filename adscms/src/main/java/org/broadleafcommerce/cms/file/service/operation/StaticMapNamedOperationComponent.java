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

package org.broadleafcommerce.cms.file.service.operation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class StaticMapNamedOperationComponent implements NamedOperationComponent {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected LinkedHashMap<String, LinkedHashMap<String, String>> namedOperations =
    new LinkedHashMap<String, LinkedHashMap<String, String>>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public LinkedHashMap<String, LinkedHashMap<String, String>> getNamedOperations() {
    return namedOperations;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  namedOperations  DOCUMENT ME!
   */
  public void setNamedOperations(LinkedHashMap<String, LinkedHashMap<String, String>> namedOperations) {
    this.namedOperations = namedOperations;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.operation.NamedOperationComponent#setOperationValues(java.util.Map, java.util.Map)
   */
  @Override public List<String> setOperationValues(Map<String, String> originalParameters,
    Map<String, String> derivedParameters) {
    List<String> utilizedNames = new ArrayList<String>();
    expandFulfilledMap(originalParameters, derivedParameters, utilizedNames);

    return utilizedNames;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  originalParameters  DOCUMENT ME!
   * @param  derivedParameters   DOCUMENT ME!
   * @param  utilizedNames       DOCUMENT ME!
   */
  protected void expandFulfilledMap(Map<String, String> originalParameters, Map<String, String> derivedParameters,
    List<String> utilizedNames) {
    for (Map.Entry<String, String> entry : originalParameters.entrySet()) {
      if (namedOperations.containsKey(entry.getKey())) {
        expandFulfilledMap(namedOperations.get(entry.getKey()), derivedParameters, utilizedNames);

        if (!utilizedNames.contains(entry.getKey())) {
          utilizedNames.add(entry.getKey());
        }
      } else {
        derivedParameters.put(entry.getKey(), entry.getValue());
      }
    }
  }
} // end class StaticMapNamedOperationComponent
