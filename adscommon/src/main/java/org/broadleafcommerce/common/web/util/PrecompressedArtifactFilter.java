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
package org.broadleafcommerce.common.web.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.MalformedURLException;

import java.util.Enumeration;

import javax.annotation.Resource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesConfigurer;

import org.springframework.web.filter.GenericFilterBean;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class PrecompressedArtifactFilter extends GenericFilterBean {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blConfiguration")
  RuntimeEnvironmentPropertiesConfigurer configurer;

  private boolean useWhileInDefaultEnvironment = true;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   *       javax.servlet.FilterChain)
   */
  @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest  request  = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

checkOutput:  {
      if (!configurer.determineEnvironment().equals(configurer.getDefaultEnvironment())
            || useWhileInDefaultEnvironment) {
        String path     = getResourcePath(request);
        String gzipPath = path + ".gz";

        if (useGzipCompression(request, response, path, gzipPath)) {
          File output = new File(getServletContext().getRealPath(gzipPath));

          if (output.exists()) {
            response.addHeader("Content-Encoding", "gzip");

            ServletOutputStream sos = servletResponse.getOutputStream();
            BufferedInputStream bis = null;

            try {
              bis = new BufferedInputStream(new FileInputStream(output));

              boolean eof = false;

              while (!eof) {
                int temp = bis.read();

                if (temp < 0) {
                  eof = true;
                } else {
                  sos.write(temp);
                }
              }
            } finally {
              sos.flush();

              try {
                if (bis != null) {
                  bis.close();
                }
              } catch (Exception e) {
                // do nothing
              }
            } // end try-finally

            break checkOutput;
          } // end if
        } // end if
      } // end if

      chain.doFilter(request, response);
    }
  } // end method doFilter

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RuntimeEnvironmentPropertiesConfigurer getConfigurer() {
    return configurer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Copied from Tomcat.
   *
   * <p>Return the page resource path from the request. For example:</p>
   *
   * <pre class="codeHtml">
     <span class="blue">http://www.mycorp.com/banking/secure/login.htm</span>  ->  <span class="red">/secure/login.htm</span> </pre>
   *
   * @param   request  the page servlet request
   *
   * @return  the page resource path from the request
   */
  public String getResourcePath(HttpServletRequest request) {
    // Adapted from VelocityViewServlet.handleRequest() method:

    // If we get here from RequestDispatcher.include(), getServletPath()
    // will return the original (wrong) URI requested.  The following
    // special attribute holds the correct path.  See section 8.3 of the
    // Servlet 2.3 specification.

    String path = (String) request.getAttribute("javax.servlet.include.servlet_path");

    // Also take into account the PathInfo stated on
    // SRV.4.4 Request Path Elements.
    String info = (String) request.getAttribute("javax.servlet.include.path_info");

    if (path == null) {
      path = request.getServletPath();
      info = request.getPathInfo();
    }

    if (info != null) {
      path += info;
    }

    return path;
  } // end method getResourcePath

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isUseWhileInDefaultEnvironment() {
    return useWhileInDefaultEnvironment;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  configurer  DOCUMENT ME!
   */
  public void setConfigurer(RuntimeEnvironmentPropertiesConfigurer configurer) {
    this.configurer = configurer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  useWhileInDefaultEnvironment  DOCUMENT ME!
   */
  public void setUseWhileInDefaultEnvironment(boolean useWhileInDefaultEnvironment) {
    this.useWhileInDefaultEnvironment = useWhileInDefaultEnvironment;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Copied from Tomcat.
   *
   * <p>Return true if the response should be GZIP compressed.</p>
   *
   * @param   request   the request to test
   * @param   response  the response to test
   * @param   path      the request path to test
   * @param   gzipPath  DOCUMENT ME!
   *
   * @return  true if the response should be GZIP compressed
   *
   * @throws  MalformedURLException  DOCUMENT ME!
   */
  protected boolean useGzipCompression(HttpServletRequest request, HttpServletResponse response, String path,
    String gzipPath) throws MalformedURLException {
    if (gzipPath == null) {
      return false;
    }

    String temp = path.toLowerCase();

    if (temp.endsWith(".gif") || temp.endsWith(".png") || temp.endsWith(".jpg")) {
      return false;
    }

    if (getServletContext().getResource(gzipPath) == null) {
      return false;
    }

    // If Content-Encoding header is already set on response, skip compression
    if (response.containsHeader("Content-Encoding")) {
      return false;
    }

    // Are we allowed to compress ?
    String s = request.getParameter("gzip");

    if ("false".equals(s)) {
      return false;
    }

    Enumeration<?> e = request.getHeaders("Accept-Encoding");

    while (e.hasMoreElements()) {
      String name = (String) e.nextElement();

      if (name.contains("gzip")) {
        return true;
      }
    }

    return true;
  } // end method useGzipCompression
} // end class PrecompressedArtifactFilter
