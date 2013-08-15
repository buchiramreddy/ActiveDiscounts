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


import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.RequestDTO;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxType;
import org.broadleafcommerce.common.site.domain.Catalog;
import org.broadleafcommerce.common.site.domain.Site;
import org.broadleafcommerce.common.site.domain.Theme;

import org.springframework.context.MessageSource;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


/**
 * Convenient holder class for various objects to be automatically available on thread local without invoking the
 * various services yourself.
 *
 * @see      {@link org.broadleafcommerce.common.web.BroadleafRequestProcessor}
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafRequestContext {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final ThreadLocal<BroadleafRequestContext> BROADLEAF_REQUEST_CONTEXT =
    new ThreadLocal<BroadleafRequestContext>();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** DOCUMENT ME! */
  protected BroadleafCurrency   broadleafCurrency;

  /** DOCUMENT ME! */
  protected Catalog             currentCatalog;

  /** DOCUMENT ME! */
  protected Boolean             ignoreSite    = false;

  /** DOCUMENT ME! */
  protected Currency            javaCurrency;

  /** DOCUMENT ME! */
  protected java.util.Locale    javaLocale;

  /** DOCUMENT ME! */
  protected Locale              locale;

  /** DOCUMENT ME! */
  protected MessageSource       messageSource;

  /** DOCUMENT ME! */
  protected HttpServletRequest  request;

  /** DOCUMENT ME! */
  protected RequestDTO          requestDTO;

  /** DOCUMENT ME! */
  protected HttpServletResponse response;

  /** DOCUMENT ME! */
  protected SandBox             sandbox;

  /** DOCUMENT ME! */
  protected Site                site;

  /** DOCUMENT ME! */
  protected Theme               theme;

  /** DOCUMENT ME! */
  protected TimeZone            timeZone;

  /** DOCUMENT ME! */
  protected WebRequest          webRequest;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BroadleafRequestContext getBroadleafRequestContext() {
    return BROADLEAF_REQUEST_CONTEXT.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String[]> getRequestParameterMap() {
    return getBroadleafRequestContext().getRequest().getParameterMap();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static boolean hasCurrency() {
    if (getBroadleafRequestContext() != null) {
      if (getBroadleafRequestContext().getBroadleafCurrency() != null) {
        return true;
      }
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static boolean hasLocale() {
    if (getBroadleafRequestContext() != null) {
      if (getBroadleafRequestContext().getLocale() != null) {
        return true;
      }
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  broadleafRequestContext  DOCUMENT ME!
   */
  public static void setBroadleafRequestContext(BroadleafRequestContext broadleafRequestContext) {
    BROADLEAF_REQUEST_CONTEXT.set(broadleafRequestContext);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BroadleafCurrency getBroadleafCurrency() {
    return broadleafCurrency;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Catalog getCurrentCatalog() {
    return currentCatalog;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIgnoreSite() {
    return ignoreSite;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the java.util.Currency constructed from the org.broadleafcommerce.common.currency.domain.BroadleafCurrency.
   *
   * @return  the java.util.Currency constructed from the
   *          org.broadleafcommerce.common.currency.domain.BroadleafCurrency.
   */
  public Currency getJavaCurrency() {
    if (this.javaCurrency == null) {
      if ((getBroadleafCurrency() != null) && (getBroadleafCurrency().getCurrencyCode() != null)) {
        this.javaCurrency = Currency.getInstance(getBroadleafCurrency().getCurrencyCode());
      }
    }

    return this.javaCurrency;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the java.util.Locale constructed from the org.broadleafcommerce.common.locale.domain.Locale.
   *
   * @return  the java.util.Locale constructed from the org.broadleafcommerce.common.locale.domain.Locale.
   */
  public java.util.Locale getJavaLocale() {
    if (this.javaLocale == null) {
      this.javaLocale = convertLocaleToJavaLocale();
    }

    return this.javaLocale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Locale getLocale() {
    return locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public MessageSource getMessageSource() {
    return messageSource;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the current request on the context.
   *
   * @return  gets the current request on the context.
   */
  public HttpServletRequest getRequest() {
    return request;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RequestDTO getRequestDTO() {
    return requestDTO;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getRequestURIWithoutContext() {
    String requestURIWithoutContext = null;

    if (request.getRequestURI() != null) {
      if (request.getContextPath() != null) {
        requestURIWithoutContext = request.getRequestURI().substring(request.getContextPath().length());
      } else {
        requestURIWithoutContext = request.getRequestURI();
      }

      // Remove JSESSION-ID or other modifiers
      int pos = requestURIWithoutContext.indexOf(";");

      if (pos >= 0) {
        requestURIWithoutContext = requestURIWithoutContext.substring(0, pos);
      }
    }

    return requestURIWithoutContext;

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the response for the context.
   *
   * @return  the response for the context.
   */
  public HttpServletResponse getResponse() {
    return response;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SandBox getSandbox() {
    return sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Site getSite() {
    return site;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Theme getTheme() {
    return theme;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public TimeZone getTimeZone() {
    return timeZone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the generic request for use outside of servlets (like in Portlets). This will be automatically set by
   * invoking {@link #setRequest(javax.servlet.http.HttpServletRequest)}
   *
   * @return  the generic request
   *
   * @see     {@link #setWebRequest(org.springframework.web.context.request.WebRequest)}
   */
  public WebRequest getWebRequest() {
    return webRequest;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isProductionSandbox() {
    return ((sandbox == null) || SandBoxType.PRODUCTION.equals(sandbox.getSandBoxType()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isSecure() {
    boolean secure = false;

    if (request != null) {
      secure = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
    }

    return secure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  additionalProperties  DOCUMENT ME!
   */
  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  broadleafCurrency  DOCUMENT ME!
   */
  public void setBroadleafCurrency(BroadleafCurrency broadleafCurrency) {
    this.broadleafCurrency = broadleafCurrency;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currentCatalog  DOCUMENT ME!
   */
  public void setCurrentCatalog(Catalog currentCatalog) {
    this.currentCatalog = currentCatalog;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ignoreSite  DOCUMENT ME!
   */
  public void setIgnoreSite(Boolean ignoreSite) {
    this.ignoreSite = ignoreSite;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  locale  DOCUMENT ME!
   */
  public void setLocale(Locale locale) {
    this.locale     = locale;
    this.javaLocale = convertLocaleToJavaLocale();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  messageSource  DOCUMENT ME!
   */
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the current request on the context. Note that this also invokes
   * {@link #setWebRequest(org.springframework.web.context.request.WebRequest)} by wrapping <b>request</b> in a
   * {@link org.springframework.web.context.request.ServletWebRequest}.
   *
   * @param  request  DOCUMENT ME!
   */
  public void setRequest(HttpServletRequest request) {
    this.request = request;

    if (webRequest == null) {
      setWebRequest(new ServletWebRequest(request));
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  requestDTO  DOCUMENT ME!
   */
  public void setRequestDTO(RequestDTO requestDTO) {
    this.requestDTO = requestDTO;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the response on the context.
   *
   * @param  response  DOCUMENT ME!
   */
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandbox  DOCUMENT ME!
   */
  public void setSandbox(SandBox sandbox) {
    this.sandbox = sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  site  DOCUMENT ME!
   */
  public void setSite(Site site) {
    this.site = site;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  theme  DOCUMENT ME!
   */
  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  timeZone  DOCUMENT ME!
   */
  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the generic request on the context. This is available to be used in non-Servlet environments (like Portlets).
   * Note that if <b>webRequest</b> is an instance of {@link org.springframework.web.context.request.ServletWebRequest}
   * then {@link #setRequest(javax.servlet.http.HttpServletRequest)} will be invoked as well with the native underlying
   * {@link javax.servlet.http.HttpServletRequest} passed as a parameter.<br />
   * <br />
   * Also, if <b>webRequest</b> is an instance of {@link org.springframework.web.context.request.ServletWebRequest} then
   * an attempt is made to set the response (note that this could be null if the ServletWebRequest was not instantiated
   * with both the {@link javax.servlet.http.HttpServletRequest} and {@link javax.servlet.http.HttpServletResponse}
   *
   * @param  webRequest  DOCUMENT ME!
   */
  public void setWebRequest(WebRequest webRequest) {
    this.webRequest = webRequest;

    if (this.request == null) {
      if (webRequest instanceof ServletWebRequest) {
        setRequest(((ServletWebRequest) webRequest).getRequest());
        setResponse(((ServletWebRequest) webRequest).getResponse());
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private java.util.Locale convertLocaleToJavaLocale() {
    if ((locale == null) || (locale.getLocaleCode() == null)) {
      return null;
    } else {
      String   localeString = locale.getLocaleCode();
      String[] components   = localeString.split("_");

      if (components.length == 1) {
        return new java.util.Locale(components[0]);
      } else if (components.length == 2) {
        return new java.util.Locale(components[0], components[1]);
      } else if (components.length == 3) {
        return new java.util.Locale(components[0], components[1], components[2]);
      }

      return null;
    }
  }
} // end class BroadleafRequestContext
