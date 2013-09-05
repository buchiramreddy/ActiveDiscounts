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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductOption;
import org.broadleafcommerce.core.catalog.domain.ProductOptionValue;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.service.CatalogService;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.LRUMap;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * This processor will add the following information to the model, available for consumption by a template: -pricing for
 * a sku based on the product option values selected -the complete set of product options and values for a given product
 *
 * @author   jfridye
 * @version  $Revision$, $Date$
 */
@Component("blProductOptionsProcessor")
public class ProductOptionsProcessor extends AbstractModelVariableModifierProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(ProductOptionsProcessor.class);

  /** DOCUMENT ME! */
  protected static final Map<Object, String> JSON_CACHE = Collections.synchronizedMap(new LRUMap<Object, String>(100,
        500));

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCatalogService")
  protected CatalogService catalogService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ProductOptionsProcessor object.
   */
  public ProductOptionsProcessor() {
    super("product_options");
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
   * @see  org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor#modifyModelAttributes(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected void modifyModelAttributes(Arguments arguments, Element element) {
    Long    productId = (Long) StandardExpressionProcessor.processExpression(arguments,
        element.getAttributeValue("productId"));
    Product product   = catalogService.findProductById(productId);

    if (product != null) {
      addAllProductOptionsToModel(arguments, product);
      addProductOptionPricingToModel(arguments, product);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void addAllProductOptionsToModel(Arguments arguments, Product product) {
    List<ProductOption>    productOptions = product.getProductOptions();
    List<ProductOptionDTO> dtos           = new ArrayList<ProductOptionDTO>();

    for (ProductOption option : productOptions) {
      ProductOptionDTO dto = new ProductOptionDTO();
      dto.setId(option.getId());
      dto.setType(option.getType().getType());

      Map<Long, String> values = new HashMap<Long, String>();

      for (ProductOptionValue value : option.getAllowedValues()) {
        values.put(value.getId(), value.getAttributeValue());
      }

      dto.setValues(values);
      dtos.add(dto);
    }

    writeJSONToModel(arguments, "allProductOptions", dtos);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void addProductOptionPricingToModel(Arguments arguments, Product product) {
    List<Sku>                     skus       = product.getSkus();
    List<ProductOptionPricingDTO> skuPricing = new ArrayList<ProductOptionPricingDTO>();

    for (Sku sku : skus) {
      List<Long> productOptionValueIds = new ArrayList<Long>();

      List<ProductOptionValue> productOptionValues = sku.getProductOptionValues();

      for (ProductOptionValue productOptionValue : productOptionValues) {
        productOptionValueIds.add(productOptionValue.getId());
      }

      Long[] values = new Long[productOptionValueIds.size()];
      productOptionValueIds.toArray(values);

      ProductOptionPricingDTO dto          = new ProductOptionPricingDTO();
      Money                   currentPrice;

      if (sku.isOnSale()) {
        currentPrice = sku.getSalePrice();
      } else {
        currentPrice = sku.getRetailPrice();
      }

      dto.setPrice(formatPrice(currentPrice));
      dto.setSelectedOptions(values);
      skuPricing.add(dto);
    } // end for

    writeJSONToModel(arguments, "skuPricing", skuPricing);
  } // end method addProductOptionPricingToModel

  //~ ------------------------------------------------------------------------------------------------------------------

  private String formatPrice(Money price) {
    if (price == null) {
      return null;
    }

    BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();

    /*if (brc.getJavaLocale() != null) {
      NumberFormat format = NumberFormat.getCurrencyInstance(brc.getJavaLocale());
      format.setCurrency(price.getCurrency());

      return format.format(price.getAmount());
    } else {*/
    // Setup your BLC_CURRENCY and BLC_LOCALE to display a diff default.
    return "₹ " + price.getAmount().toString();
      /*}*/
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void writeJSONToModel(Arguments arguments, String modelKey, Object o) {
    try {
      if (!JSON_CACHE.containsKey(o)) {
        ObjectMapper mapper    = new ObjectMapper();
        Writer       strWriter = new StringWriter();
        mapper.writeValue(strWriter, o);
        JSON_CACHE.put(o, strWriter.toString());
      }

      addToModel(arguments, modelKey, JSON_CACHE.get(o));
    } catch (Exception ex) {
      LOG.error("There was a problem writing the product option map to JSON", ex);
    }
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  private class ProductOptionDTO {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    private Long              id;
    private String            selectedValue;
    private String            type;
    private Map<Long, String> values;

    //~ Methods --------------------------------------------------------------------------------------------------------

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (!(o instanceof ProductOptionDTO)) {
        return false;
      }

      ProductOptionDTO that = (ProductOptionDTO) o;

      if ((id != null) ? (!id.equals(that.id)) : (that.id != null)) {
        return false;
      }

      if ((selectedValue != null) ? (!selectedValue.equals(that.selectedValue)) : (that.selectedValue != null)) {
        return false;
      }

      if ((type != null) ? (!type.equals(that.type)) : (that.type != null)) {
        return false;
      }

      if ((values != null) ? (!values.equals(that.values)) : (that.values != null)) {
        return false;
      }

      return true;
    } // end method equals

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public Long getId() {
      return id;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public String getSelectedValue() {
      return selectedValue;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public String getType() {
      return type;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public Map<Long, String> getValues() {
      return values;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public int hashCode() {
      int result = (id != null) ? id.hashCode() : 0;
      result = (31 * result) + ((type != null) ? type.hashCode() : 0);
      result = (31 * result) + ((values != null) ? values.hashCode() : 0);
      result = (31 * result) + ((selectedValue != null) ? selectedValue.hashCode() : 0);

      return result;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setId(Long id) {
      this.id = id;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public void setSelectedValue(String selectedValue) {
      this.selectedValue = selectedValue;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setType(String type) {
      this.type = type;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setValues(Map<Long, String> values) {
      this.values = values;
    }
  } // end class ProductOptionDTO

  private class ProductOptionPricingDTO {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    private String price;
    private Long[] skuOptions;

    //~ Methods --------------------------------------------------------------------------------------------------------

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (!(o instanceof ProductOptionPricingDTO)) {
        return false;
      }

      ProductOptionPricingDTO that = (ProductOptionPricingDTO) o;

      if ((price != null) ? (!price.equals(that.price)) : (that.price != null)) {
        return false;
      }

      if (!Arrays.equals(skuOptions, that.skuOptions)) {
        return false;
      }

      return true;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public String getPrice() {
      return price;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public Long[] getSelectedOptions() {
      return skuOptions;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public int hashCode() {
      int result = (skuOptions != null) ? Arrays.hashCode(skuOptions) : 0;
      result = (31 * result) + ((price != null) ? price.hashCode() : 0);

      return result;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setPrice(String price) {
      this.price = price;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setSelectedOptions(Long[] skuOptions) {
      this.skuOptions = skuOptions;
    }
  } // end class ProductOptionPricingDTO

} // end class ProductOptionsProcessor
