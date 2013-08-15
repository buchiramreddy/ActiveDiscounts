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

package org.broadleafcommerce.openadmin.web.compatibility;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import org.broadleafcommerce.openadmin.server.service.JSCompatibilityHelper;

import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.savedrequest.Enumerator;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class JSCompatibilityRequestWrapper extends FirewalledRequest {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new JSCompatibilityRequestWrapper object.
   *
   * @param  request  DOCUMENT ME!
   */
  public JSCompatibilityRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletRequestWrapper#getContextPath()
   */
  @Override public String getContextPath() {
    return JSCompatibilityHelper.unencode(super.getContextPath());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
   */
  @Override public String getParameter(String name) {
    return JSCompatibilityHelper.unencode(super.getParameter(name));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletRequestWrapper#getParameterMap()
   */
  @Override public Map getParameterMap() {
    Map params = super.getParameterMap();
    Map temp   = new LinkedHashMap();

    for (Object key : params.keySet()) {
      Object value = params.get(key);

      if (value instanceof String) {
        temp.put(JSCompatibilityHelper.unencode((String) key), JSCompatibilityHelper.unencode((String) value));
      } else {
        String[] vals     = (String[]) value;
        String[] tempVals = new String[vals.length];
        int      j        = 0;

        for (String val : vals) {
          tempVals[j] = JSCompatibilityHelper.unencode(val);
          j++;
        }

        temp.put(JSCompatibilityHelper.unencode((String) key), tempVals);
      }
    }

    return temp;
  } // end method getParameterMap

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletRequestWrapper#getParameterNames()
   */
  @Override public Enumeration getParameterNames() {
    List<String> names       = new ArrayList<String>();
    Enumeration  enumeration = super.getParameterNames();

    while (enumeration.hasMoreElements()) {
      names.add(JSCompatibilityHelper.unencode((String) enumeration.nextElement()));
    }

    return new Enumerator<String>(names);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
   */
  @Override public String[] getParameterValues(String name) {
    String[] paramValues = super.getParameterValues(getParameterNameConversionMap().get(name));

    if (!ArrayUtils.isEmpty(paramValues)) {
      String[] temp = new String[paramValues.length];
      int      j    = 0;

      for (String val : paramValues) {
        temp[j] = JSCompatibilityHelper.unencode(val);
        j++;
      }

      return temp;
    }

    return paramValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletRequestWrapper#getPathTranslated()
   */
  @Override public String getPathTranslated() {
    return JSCompatibilityHelper.unencode(super.getPathTranslated());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletRequestWrapper#getQueryString()
   */
  @Override public String getQueryString() {
    return JSCompatibilityHelper.unencode(super.getQueryString());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletRequestWrapper#getRequestURI()
   */
  @Override public String getRequestURI() {
    return JSCompatibilityHelper.unencode(super.getRequestURI());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletRequestWrapper#getRequestURL()
   */
  @Override public StringBuffer getRequestURL() {
    return new StringBuffer(JSCompatibilityHelper.unencode(super.getRequestURL().toString()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletRequestWrapper#getServletPath()
   */
  @Override public String getServletPath() {
    return JSCompatibilityHelper.unencode(super.getServletPath());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.web.firewall.FirewalledRequest#reset()
   */
  @Override public void reset() {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Map<String, String> getParameterNameConversionMap() {
    if (getAttribute("requestParameterConversionMap") == null) {
      Map<String, String> map         = new HashMap<String, String>();
      Enumeration         enumeration = super.getParameterNames();

      while (enumeration.hasMoreElements()) {
        String temp = (String) enumeration.nextElement();
        map.put(JSCompatibilityHelper.unencode(temp), temp);
      }

      setAttribute("requestParameterConversionMap", map);
    }

    return (Map<String, String>) getAttribute("requestParameterConversionMap");
  }


} // end class JSCompatibilityRequestWrapper
