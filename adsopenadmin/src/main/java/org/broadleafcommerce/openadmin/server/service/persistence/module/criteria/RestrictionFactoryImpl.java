/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Service("blRestrictionFactory")
public class RestrictionFactoryImpl implements RestrictionFactory {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blRestrictionFactoryMap")
  protected Map<String, Restriction> restrictions = new LinkedHashMap<String, Restriction>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.RestrictionFactory#getRestriction(java.lang.String,
   *       java.lang.String)
   */
  @Override public Restriction getRestriction(String type, String propertyId) {
    Restriction restriction = restrictions.get(type).clone();
    restriction.setFieldPathBuilder(new FieldPathBuilder());

    return restriction;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Restriction> getRestrictions() {
    return restrictions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  restrictions  DOCUMENT ME!
   */
  public void setRestrictions(Map<String, Restriction> restrictions) {
    this.restrictions = restrictions;
  }
} // end class RestrictionFactoryImpl
