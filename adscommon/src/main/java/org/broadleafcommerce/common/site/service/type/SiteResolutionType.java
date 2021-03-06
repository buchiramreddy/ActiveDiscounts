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

package org.broadleafcommerce.common.site.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of order status types.
 *
 * <ul>
 *   <li><b>DOMAIN</b> - Resolve a site based on the whole domain (e.g. request.getServerName())</li>
 *   <li><b>DOMAIN_PREFIX</b> - Resolve a site based on the first word in the domain.</li>
 * </ul>
 *
 * <p>For example, if the URL you wanted to resolve was http://mysite.mycompany.com.</p>
 *
 * <p>The {@link #getSiteIdentifierValue()} should be set as follows:</p>
 *
 * <p>to use DOMAIN resolution, set to "mysite.mycompany.com" to use DOMAIN_PREFIX resolution, set to "mysite"</p>
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class SiteResolutionType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, SiteResolutionType> TYPES = new LinkedHashMap<String, SiteResolutionType>();


  /** DOCUMENT ME! */
  public static final SiteResolutionType DOMAIN        = new SiteResolutionType("DOMAIN", "Domain");

  /** DOCUMENT ME! */
  public static final SiteResolutionType DOMAIN_PREFIX = new SiteResolutionType("DOMAIN_PREFIX", "Domain Prefix");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new SiteResolutionType object.
   */
  public SiteResolutionType() {
    // do nothing
  }

  /**
   * Creates a new SiteResolutionType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public SiteResolutionType(final String type, final String friendlyType) {
    this.friendlyType = friendlyType;
    setType(type);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static SiteResolutionType getInstance(final String type) {
    return TYPES.get(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    SiteResolutionType other = (SiteResolutionType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getFriendlyType()
   */
  @Override public String getFriendlyType() {
    return friendlyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getType()
   */
  @Override public String getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }

} // end class SiteResolutionType
