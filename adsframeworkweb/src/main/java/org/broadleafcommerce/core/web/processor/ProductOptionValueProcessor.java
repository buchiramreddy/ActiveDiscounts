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

package org.broadleafcommerce.core.web.processor;

import java.io.StringWriter;
import java.io.Writer;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.core.catalog.domain.ProductOptionValue;

import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Component("blProductOptionValueProcessor")
public class ProductOptionValueProcessor extends AbstractAttrProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(ProductOptionValueProcessor.class);

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ProductOptionValueProcessor object.
   */
  public ProductOptionValueProcessor() {
    super("product_option_value");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 10000;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.attr.AbstractAttrProcessor#processAttribute(org.thymeleaf.Arguments,org.thymeleaf.dom.Element,
   *       java.lang.String)
   */
  @Override protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
    ProductOptionValue    productOptionValue = (ProductOptionValue) StandardExpressionProcessor.processExpression(
        arguments, element.getAttributeValue(attributeName));
    ProductOptionValueDTO dto                = new ProductOptionValueDTO();
    dto.setOptionId(productOptionValue.getProductOption().getId());
    dto.setValueId(productOptionValue.getId());
    dto.setValueName(productOptionValue.getAttributeValue());

    if (productOptionValue.getPriceAdjustment() != null) {
      dto.setPriceAdjustment(productOptionValue.getPriceAdjustment().getAmount());
    }

    try {
      ObjectMapper mapper    = new ObjectMapper();
      Writer       strWriter = new StringWriter();
      mapper.writeValue(strWriter, dto);
      element.setAttribute("data-product-option-value", strWriter.toString());

      return ProcessorResult.OK;
    } catch (Exception ex) {
      LOG.error("There was a problem writing the product option value to JSON", ex);
    }

    return null;

  } // end method processAttribute

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  private class ProductOptionValueDTO {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    private Long       optionId;
    private BigDecimal priceAdjustment;
    private Long       valueId;
    private String     valueName;

    //~ Methods --------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public Long getOptionId() {
      return optionId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public BigDecimal getPriceAdjustment() {
      return priceAdjustment;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public Long getValueId() {
      return valueId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public String getValueName() {
      return valueName;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setOptionId(Long optionId) {
      this.optionId = optionId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setPriceAdjustment(BigDecimal priceAdjustment) {
      this.priceAdjustment = priceAdjustment;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setValueId(Long valueId) {
      this.valueId = valueId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setValueName(String valueName) {
      this.valueName = valueName;
    }
  } // end class ProductOptionValueDTO

} // end class ProductOptionValueProcessor
