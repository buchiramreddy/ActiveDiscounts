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

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Generic interface that should be used for processing requests from Servlet Filters, Spring interceptors or Portlet
 * filters. Note that the actual type of the request passed in should be something that extends {@link org.springframework.web.context.request.NativeWebRequest}.
 * 
 * Example usage by a Servlet Filter:
 * 
 * <pre>
 *   public class SomeServletFilter extends GenericFilterBean {
 *      &#064;Resource(name="blCustomerStateRequestProcessor")
 *      protected BroadleafWebRequestProcessor processor;
 *      
 *      public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
 *          processor.process(new ServletWebRequest(request, response));
 *      }
 *   }
 * </pre>
 * 
 * <p>Also note that you should always instantiate the {@link org.springframework.web.context.request.WebRequest} with as much information available. In the above
 * example, this means using both the {@link javax.servlet.http.HttpServletRequest} and {@link javax.servlet.http.HttpServletResponse} when instantiating the
 * {@link org.springframework.web.context.request.ServletWebRequest}</p>
 * 
 * @author Phillip Verheyden
 * @see {@link org.springframework.web.context.request.NativeWebRequest}
 * @see {@link org.springframework.web.context.request.ServletWebRequest}
 * @see {@link org.springframework.web.portlet.context.PortletWebRequest}
 * @see {@link org.broadleafcommerce.common.web.BroadleafRequestFilter}
 */
public interface BroadleafWebRequestProcessor {

    /**
     * Process the current request. Examples would be setting the currently logged in customer on the request or handling
     * anonymous customers in session
     * 
     * @param request
     */
    public void process(WebRequest request);
    
    /**
     * Should be called if work needs to be done after the request has been processed.
     * 
     * @param request
     */
    public void postProcess(WebRequest request);

}
