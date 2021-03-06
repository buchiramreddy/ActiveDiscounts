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

package org.broadleafcommerce.openadmin.web.filter;

import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.exception.SiteNotFoundException;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.site.domain.Site;
import org.broadleafcommerce.common.web.AbstractBroadleafWebRequestProcessor;
import org.broadleafcommerce.common.web.BroadleafLocaleResolver;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.broadleafcommerce.common.web.BroadleafSiteResolver;
import org.broadleafcommerce.common.web.BroadleafTimeZoneResolver;

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
@Component("blAdminRequestProcessor")
public class BroadleafAdminRequestProcessor extends AbstractBroadleafWebRequestProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminTimeZoneResolver")
  protected BroadleafTimeZoneResolver broadleafTimeZoneResolver;

  /** DOCUMENT ME! */
  @Resource(name = "blLocaleResolver")
  protected BroadleafLocaleResolver localeResolver;

  /** DOCUMENT ME! */
  protected final Log LOG = LogFactory.getLog(getClass());

  /** DOCUMENT ME! */
  @Resource(name = "messageSource")
  protected MessageSource messageSource;

  /** DOCUMENT ME! */
  @Resource(name = "blSiteResolver")
  protected BroadleafSiteResolver siteResolver;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.BroadleafWebRequestProcessor#process(org.springframework.web.context.request.WebRequest)
   */
  @Override public void process(WebRequest request) throws SiteNotFoundException {
    Site site = siteResolver.resolveSite(request);

    BroadleafRequestContext brc = new BroadleafRequestContext();
    BroadleafRequestContext.setBroadleafRequestContext(brc);

    brc.setSite(site);
    brc.setWebRequest(request);
    brc.setIgnoreSite(site == null);

    Locale locale = localeResolver.resolveLocale(request);
    brc.setLocale(locale);

    brc.setMessageSource(messageSource);

    TimeZone timeZone = broadleafTimeZoneResolver.resolveTimeZone(request);
    brc.setTimeZone(timeZone);
  }

} // end class BroadleafAdminRequestProcessor
