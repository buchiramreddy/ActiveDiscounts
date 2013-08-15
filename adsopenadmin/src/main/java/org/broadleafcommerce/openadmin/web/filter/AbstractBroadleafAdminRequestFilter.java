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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.filter.OncePerRequestFilter;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public abstract class AbstractBroadleafAdminRequestFilter extends OncePerRequestFilter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Value("${asset.server.url.prefix.internal}")
  private String assetPrefix;

  private Set<String> ignoreSuffixes;

  private final Log LOG = LogFactory.getLog(AbstractBroadleafAdminRequestFilter.class);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a set of suffixes that can be ignored by content processing. The following are returned:
   *
   * <p><B>List of suffixes ignored:</B></p>
   *
   * <p>".aif", ".aiff", ".asf", ".avi", ".bin", ".bmp", ".doc", ".eps", ".gif", ".hqx", ".jpg", ".jpeg", ".mid",
   * ".midi", ".mov", ".mp3", ".mpg", ".mpeg", ".p65", ".pdf", ".pic", ".pict", ".png", ".ppt", ".psd", ".qxd", ".ram",
   * ".ra", ".rm", ".sea", ".sit", ".stk", ".swf", ".tif", ".tiff", ".txt", ".rtf", ".vob", ".wav", ".wmf", ".xls",
   * ".zip";</p>
   *
   * @return  set of suffixes to ignore.
   */
  @SuppressWarnings("rawtypes")
  protected Set getIgnoreSuffixes() {
    if ((ignoreSuffixes == null) || ignoreSuffixes.isEmpty()) {
      String[] ignoreSuffixList = {
        ".aif",
        ".aiff",
        ".asf",
        ".avi",
        ".bin",
        ".bmp",
        ".css",
        ".doc",
        ".eps",
        ".gif",
        ".hqx",
        ".js",
        ".jpg",
        ".jpeg",
        ".mid",
        ".midi",
        ".mov",
        ".mp3",
        ".mpg",
        ".mpeg",
        ".p65",
        ".pdf",
        ".pic",
        ".pict",
        ".png",
        ".ppt",
        ".psd",
        ".qxd",
        ".ram",
        ".ra",
        ".rm",
        ".sea",
        ".sit",
        ".stk",
        ".swf",
        ".tif",
        ".tiff",
        ".txt",
        ".rtf",
        ".vob",
        ".wav",
        ".wmf",
        ".xls",
        ".zip"
      };
      ignoreSuffixes = new HashSet<String>(Arrays.asList(ignoreSuffixList));
    } // end if

    return ignoreSuffixes;
  } // end method getIgnoreSuffixes

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Determines if the passed in URL should be processed by the content management system.
   *
   * <p/>By default, this method returns false for any BLC-Admin URLs and service calls and for all common image/digital
   * mime-types (as determined by an internal call to {@code getIgnoreSuffixes}.
   * </p>This check is called with the {@code doFilterInternal} method to short-circuit the content processing which can
   * be expensive for requests that do not require it.
   *
   * @param   request     DOCUMENT ME!
   * @param   requestURI  - the HttpServletRequest.getRequestURI
   *
   * @return  true if the {@code HttpServletRequest} should be processed
   */
  protected boolean shouldProcessURL(HttpServletRequest request, String requestURI) {
    int pos = requestURI.lastIndexOf(".");

    if ((pos > 0) && !requestURI.contains(assetPrefix)) {
      String suffix = requestURI.substring(pos);

      if (getIgnoreSuffixes().contains(suffix.toLowerCase())) {
        if (LOG.isTraceEnabled()) {
          LOG.trace("BroadleafProcessURLFilter ignoring request due to suffix " + requestURI);
        }

        return false;
      }
    }

    return true;
  }
} // end class AbstractBroadleafAdminRequestFilter
