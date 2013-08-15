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

package org.broadleafcommerce.common.web;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.RequestDTO;
import org.broadleafcommerce.common.RequestDTOImpl;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.site.domain.Site;
import org.broadleafcommerce.common.site.domain.Theme;

import org.springframework.context.MessageSource;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.WebRequest;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.common.web.BroadleafRequestFilter}
 * @version  $Revision$, $Date$
 */
@Component("blRequestProcessor")
public class BroadleafRequestProcessor extends AbstractBroadleafWebRequestProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static String REQUEST_DTO_PARAM_NAME = BroadleafRequestFilter.REQUEST_DTO_PARAM_NAME;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blTimeZoneResolver")
  protected BroadleafTimeZoneResolver broadleafTimeZoneResolver;

  /** DOCUMENT ME! */
  @Resource(name = "blCurrencyResolver")
  protected BroadleafCurrencyResolver currencyResolver;

  /** DOCUMENT ME! */
  @Resource(name = "blLocaleResolver")
  protected BroadleafLocaleResolver localeResolver;

  /** DOCUMENT ME! */
  protected final Log LOG = LogFactory.getLog(getClass());

  /** DOCUMENT ME! */
  @Resource(name = "messageSource")
  protected MessageSource messageSource;

  /** DOCUMENT ME! */
  @Resource(name = "blSandBoxResolver")
  protected BroadleafSandBoxResolver sandboxResolver;

  /** DOCUMENT ME! */
  @Resource(name = "blSiteResolver")
  protected BroadleafSiteResolver siteResolver;

  /** DOCUMENT ME! */
  @Resource(name = "blThemeResolver")
  protected BroadleafThemeResolver themeResolver;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.BroadleafWebRequestProcessor#process(org.springframework.web.context.request.WebRequest)
   */
  @Override public void process(WebRequest request) {
    Site site = siteResolver.resolveSite(request);

    BroadleafRequestContext brc = new BroadleafRequestContext();

    brc.setSite(site);
    brc.setWebRequest(request);

    if (site == null) {
      brc.setIgnoreSite(true);
    }

    BroadleafRequestContext.setBroadleafRequestContext(brc);

    Locale            locale   = localeResolver.resolveLocale(request);
    TimeZone          timeZone = broadleafTimeZoneResolver.resolveTimeZone(request);
    BroadleafCurrency currency = currencyResolver.resolveCurrency(request);

    // Assumes BroadleafProcess
    RequestDTO requestDTO = (RequestDTO) request.getAttribute(REQUEST_DTO_PARAM_NAME, WebRequest.SCOPE_REQUEST);

    if (requestDTO == null) {
      requestDTO = new RequestDTOImpl(request);
    }

    SandBox currentSandbox = sandboxResolver.resolveSandBox(request, site);

    if (currentSandbox != null) {
      SandBoxContext previewSandBoxContext = new SandBoxContext();
      previewSandBoxContext.setSandBoxId(currentSandbox.getId());
      previewSandBoxContext.setPreviewMode(true);
      SandBoxContext.setSandBoxContext(previewSandBoxContext);
    }

    // Note that this must happen after the request context is set up as resolving a theme is dependent on site
    Theme theme = themeResolver.resolveTheme(request);
    brc.setLocale(locale);
    brc.setBroadleafCurrency(currency);
    brc.setSandbox(currentSandbox);
    brc.setTheme(theme);
    brc.setMessageSource(messageSource);
    brc.setTimeZone(timeZone);
    brc.setRequestDTO(requestDTO);

    Map<String, Object> ruleMap = (Map<String, Object>) request.getAttribute("blRuleMap", WebRequest.SCOPE_REQUEST);

    if (ruleMap == null) {
      LOG.trace("Creating ruleMap and adding in Locale.");
      ruleMap = new HashMap<String, Object>();
      request.setAttribute("blRuleMap", ruleMap, WebRequest.SCOPE_REQUEST);
    } else {
      LOG.trace("Using pre-existing ruleMap - added by non standard BLC process.");
    }

    ruleMap.put("locale", locale);

  } // end method process

} // end class BroadleafRequestProcessor
