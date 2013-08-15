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

package org.broadleafcommerce.cms.web.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.broadleafcommerce.cms.file.service.StaticAssetService;
import org.broadleafcommerce.cms.structure.domain.StructuredContentType;
import org.broadleafcommerce.cms.structure.dto.StructuredContentDTO;
import org.broadleafcommerce.cms.structure.service.StructuredContentService;
import org.broadleafcommerce.cms.web.BroadleafProcessURLFilter;

import org.broadleafcommerce.common.RequestDTO;
import org.broadleafcommerce.common.TimeDTO;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.time.SystemTime;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Tag used to display structured content that is maintained with the Broadleaf CMS.
 *
 * <p>Usage based on the following attributes:<br>
 * </p>
 *
 * <ul>
 *   <li>contentType (required) - specifies the content you are retrieving</li>
 *   <li>contentName - if included will retrieve only content that matches the name. When no name is specified, all
 *     matching content items of the passed in type are retrieved.</li>
 *   <li>count - if specified limits the results to a specified number of items. The content will be returned according
 *     to priority. If content items share the same priority, then they will be returned randomly. Consider the example
 *     with 5 matching items with priorities (1,2,3,3,3) respectively. If the count is set to 3. Items 1 and 2 will
 *     ALWAYS be returned. The third item returned will randomy rotate through the 3rd, 4th, and 5th item.</li>
 *   <li>contentListVar - allows you to specify an alternate name for the list of content results. By default, the
 *     results are returned in the page attributed "contentList"</li>
 *   <li>contentItemVar - since a typical usage is to only return one item, the first item is returned in the variable
 *     "contentItem". This variable can be used to change the attribute name.</li>
 *   <li>numResultsVar - variable holding the returns the number of results being returned to through the tag-lib.
 *     defaults to "numResults".</li>
 *   <li>locale - the locale being targeted for the content. Defaults to locale that exists in the requestAttribute
 *     "blLocale". This is typically setup through Broadleaf's ProcessURLFilter.</li>
 * </ul>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class DisplayContentTag extends BodyTagSupport {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  public static final String BLC_RULE_MAP_PARAM = "blRuleMap";

  // The following attribute is set in BroadleafProcessURLFilter
  /** DOCUMENT ME! */
  public static final String REQUEST_DTO = "blRequestDTO";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String contentItemVar;
  private String contentListVar;
  private String contentName;

  private String             contentType;
  private Integer            count;
  private Locale             locale;
  private String             numResultsVar;
  private Object             product;
  private StaticAssetService staticAssetService;

  private StructuredContentService structuredContentService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new DisplayContentTag object.
   */
  public DisplayContentTag() {
    initVariables();
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
   */
  @Override public int doEndTag() throws JspException {
    int returnVal = super.doEndTag();
    initVariables();

    return returnVal;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
   */
  @Override public int doStartTag() throws JspException {
    HttpServletRequest  request        = (HttpServletRequest) pageContext.getRequest();
    Map<String, Object> mvelParameters = buildMvelParameters(request);
    SandBox             currentSandbox = (SandBox) request.getAttribute(BroadleafProcessURLFilter.SANDBOX_VAR);
    initServices();

    List<StructuredContentDTO> contentItems;
    StructuredContentType      structuredContentType = structuredContentService.findStructuredContentTypeByName(
        contentType);
    assert ((contentName != null) && !"".equals(contentName) && (structuredContentType != null));

    if (locale == null) {
      locale = (Locale) request.getAttribute(BroadleafProcessURLFilter.LOCALE_VAR);
    }

    int cnt = (count == null) ? Integer.MAX_VALUE : count;

    if (structuredContentType == null) {
      contentItems = structuredContentService.lookupStructuredContentItemsByName(currentSandbox, contentName, locale,
          cnt, mvelParameters, isSecure(request));
    } else {
      if ((contentName == null) || "".equals(contentName)) {
        contentItems = structuredContentService.lookupStructuredContentItemsByType(currentSandbox,
            structuredContentType, locale, cnt, mvelParameters, isSecure(request));
      } else {
        contentItems = structuredContentService.lookupStructuredContentItemsByName(currentSandbox,
            structuredContentType, contentName, locale, cnt, mvelParameters, isSecure(request));
      }
    }

    pageContext.setAttribute(getNumResultsVar(), contentItems.size());

    if (contentItems.size() > 0) {
      List<Map<String, String>> contentItemFields = new ArrayList<Map<String, String>>();

      for (StructuredContentDTO item : contentItems) {
        contentItemFields.add(item.getValues());
      }

      pageContext.setAttribute(contentItemVar, contentItemFields.get(0));
      pageContext.setAttribute(contentListVar, contentItemFields);
      pageContext.setAttribute("structuredContentList", contentItems);
      pageContext.setAttribute(numResultsVar, contentItems.size());
    } else {
      pageContext.setAttribute(contentItemVar, null);
      pageContext.setAttribute(contentListVar, null);
      pageContext.setAttribute("structuredContentList", null);
      pageContext.setAttribute(numResultsVar, 0);
    }

    return EVAL_BODY_INCLUDE;
  } // end method doStartTag

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContentItemVar() {
    return contentItemVar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContentListVar() {
    return contentListVar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContentName() {
    return contentName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getContentType() {
    return contentType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getCount() {
    return count;
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
  public String getNumResultsVar() {
    return numResultsVar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Object getProduct() {
    return product;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isSecure(HttpServletRequest request) {
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
   * @param  contentItemVar  DOCUMENT ME!
   */
  public void setContentItemVar(String contentItemVar) {
    this.contentItemVar = contentItemVar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  contentVar  DOCUMENT ME!
   */
  public void setContentListVar(String contentVar) {
    this.contentListVar = contentVar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  contentName  DOCUMENT ME!
   */
  public void setContentName(String contentName) {
    this.contentName = contentName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  contentType  DOCUMENT ME!
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  count  DOCUMENT ME!
   */
  public void setCount(Integer count) {
    this.count = count;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  locale  DOCUMENT ME!
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  numResultsVar  DOCUMENT ME!
   */
  public void setNumResultsVar(String numResultsVar) {
    this.numResultsVar = numResultsVar;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  product  DOCUMENT ME!
   */
  public void setProduct(Object product) {
    this.product = product;
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   */
  protected void initServices() {
    if ((structuredContentService == null) || (staticAssetService == null)) {
      WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(
          pageContext.getServletContext());
      structuredContentService = (StructuredContentService) applicationContext.getBean("blStructuredContentService");
      staticAssetService       = (StaticAssetService) applicationContext.getBean("blStaticAssetService");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * MVEL is used to process the content targeting rules.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  mVEL is used to process the content targeting rules.
   */
  private Map<String, Object> buildMvelParameters(HttpServletRequest request) {
    TimeDTO    timeDto    = new TimeDTO(SystemTime.asCalendar());
    RequestDTO requestDto = (RequestDTO) request.getAttribute(REQUEST_DTO);

    Map<String, Object> mvelParameters = new HashMap<String, Object>();
    mvelParameters.put("time", timeDto);
    mvelParameters.put("request", requestDto);

    Map<String, Object> blcRuleMap = (Map<String, Object>) request.getAttribute(BLC_RULE_MAP_PARAM);

    if (blcRuleMap != null) {
      for (String mapKey : blcRuleMap.keySet()) {
        mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
      }
    }

    if (product != null) {
      mvelParameters.put("product", product);
    }

    return mvelParameters;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void initVariables() {
    contentType    = null;
    contentName    = null;
    product        = null;
    count          = null;
    locale         = null;
    contentListVar = "contentList";
    contentItemVar = "contentItem";
    numResultsVar  = "numResults";
  }
} // end class DisplayContentTag
