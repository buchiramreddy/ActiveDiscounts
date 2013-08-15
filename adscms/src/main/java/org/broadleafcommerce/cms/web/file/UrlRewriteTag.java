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

package org.broadleafcommerce.cms.web.file;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.broadleafcommerce.cms.file.service.StaticAssetService;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Will convert from http/https based on the request.
 *
 * <p>If the URL is based on a BLC-CMS-Asset and the environment specifies a replacement prefix, then the prefix will be
 * set.</p>
 *
 * <p>Populates the page-attribute "assetpath" with the updated url.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class UrlRewriteTag extends TagSupport {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  // The following attribute is set in BroadleafProcessURLFilter
  /** DOCUMENT ME! */
  public static final String REQUEST_DTO = "blRequestDTO";

  private static StaticAssetService staticAssetService;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String value;
  private String var;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.TagSupport#doEndTag()
   */
  @Override public int doEndTag() throws JspException {
    var = null;

    return super.doEndTag();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.TagSupport#doStartTag()
   */
  @Override public int doStartTag() throws JspException {
    initServices();

    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    String returnValue = staticAssetService.convertAssetPath(value, request.getContextPath(), isRequestSecure());

    if (var != null) {
      pageContext.setAttribute(var, returnValue);
    } else {
      try {
        pageContext.getOut().print(returnValue);
      } catch (IOException ioe) {
        throw new JspTagException(ioe.toString(), ioe);
      }
    }

    return EVAL_PAGE;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValue() {
    return value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getVar() {
    return var;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.TagSupport#release()
   */
  @Override public void release() {
    var = null;
    super.release();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  value  DOCUMENT ME!
   */
  public void setValue(String value) {
    this.value = value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  var  DOCUMENT ME!
   */
  public void setVar(String var) {
    this.var = var;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  protected void initServices() {
    if (staticAssetService == null) {
      WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(
          pageContext.getServletContext());
      staticAssetService = (StaticAssetService) applicationContext.getBean("blStaticAssetService");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if the current request.scheme = HTTPS or if the request.isSecure value is true.
   *
   * @return  true if the current request.scheme = HTTPS or if the request.isSecure value is true.
   */
  protected boolean isRequestSecure() {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    return ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
  }
} // end class UrlRewriteTag
