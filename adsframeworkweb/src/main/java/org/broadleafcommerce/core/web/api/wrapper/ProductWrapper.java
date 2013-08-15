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

package org.broadleafcommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.cms.file.service.StaticAssetService;

import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductAttribute;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.ProductOption;
import org.broadleafcommerce.core.catalog.domain.RelatedProduct;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;


/**
 * This is a JAXB wrapper around Product.
 *
 * <p>User: Kelly Tisdell Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "product")
public class ProductWrapper extends BaseWrapper implements APIWrapper<Product> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Boolean active;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
  protected Date             activeEndDate;

  // End for bundles

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
  protected Date             activeStartDate;

  /** DOCUMENT ME! */
  @XmlElement protected Money bundleItemsRetailPrice;

  /** DOCUMENT ME! */
  @XmlElement protected Money bundleItemsSalePrice;

  /** DOCUMENT ME! */
  @XmlElement(name = "crossSaleProduct")
  @XmlElementWrapper(name = "crossSaleProducts")
  protected List<RelatedProductWrapper> crossSaleProducts;

  /** DOCUMENT ME! */
  @XmlElement protected Long defaultCategoryId;

  /** DOCUMENT ME! */
  @XmlElement protected String description;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String longDescription;

  /** DOCUMENT ME! */
  @XmlElement protected String manufacturer;

  /** DOCUMENT ME! */
  @XmlElement(name = "media")
  @XmlElementWrapper(name = "mediaItems")
  protected List<MediaWrapper> media;

  /** DOCUMENT ME! */
  @XmlElement protected String model;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement protected MediaWrapper primaryMedia;

  // For bundles
  /** DOCUMENT ME! */
  @XmlElement protected Integer priority;

  /** DOCUMENT ME! */
  @XmlElement(name = "productAttribute")
  @XmlElementWrapper(name = "productAttributes")
  protected List<ProductAttributeWrapper> productAttributes;

  /** DOCUMENT ME! */
  @XmlElement(name = "productOption")
  @XmlElementWrapper(name = "productOptions")
  protected List<ProductOptionWrapper> productOptions;

  /** DOCUMENT ME! */
  @XmlElement protected String promoMessage;

  /** DOCUMENT ME! */
  @XmlElement protected Money retailPrice;

  /** DOCUMENT ME! */
  @XmlElement protected Money salePrice;

  /** DOCUMENT ME! */
  @XmlElement(name = "skuBundleItem")
  @XmlElementWrapper(name = "skuBundleItems")
  protected List<SkuBundleItemWrapper> skuBundleItems;

  /** DOCUMENT ME! */
  @XmlElement(name = "upsaleProduct")
  @XmlElementWrapper(name = "upsaleProducts")
  protected List<RelatedProductWrapper> upsaleProducts;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.Product,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Product model, HttpServletRequest request) {
    this.id              = model.getId();
    this.name            = model.getName();
    this.description     = model.getDescription();
    this.longDescription = model.getLongDescription();
    this.activeStartDate = model.getActiveStartDate();
    this.activeEndDate   = model.getActiveEndDate();
    this.manufacturer    = model.getManufacturer();
    this.model           = model.getModel();
    this.promoMessage    = model.getPromoMessage();
    this.active          = model.isActive();

    if (model instanceof ProductBundle) {
      ProductBundle bundle = (ProductBundle) model;
      this.priority               = bundle.getPriority();
      this.bundleItemsRetailPrice = bundle.getBundleItemsRetailPrice();
      this.bundleItemsSalePrice   = bundle.getBundleItemsSalePrice();

      if (bundle.getSkuBundleItems() != null) {
        this.skuBundleItems = new ArrayList<SkuBundleItemWrapper>();

        List<SkuBundleItem> bundleItems = bundle.getSkuBundleItems();

        for (SkuBundleItem item : bundleItems) {
          SkuBundleItemWrapper skuBundleItemsWrapper = (SkuBundleItemWrapper) context.getBean(SkuBundleItemWrapper.class
              .getName());
          skuBundleItemsWrapper.wrapSummary(item, request);
          this.skuBundleItems.add(skuBundleItemsWrapper);
        }
      }
    } else {
      this.retailPrice = model.getDefaultSku().getRetailPrice();
      this.salePrice   = model.getDefaultSku().getSalePrice();
    }

    if ((model.getProductOptions() != null) && !model.getProductOptions().isEmpty()) {
      this.productOptions = new ArrayList<ProductOptionWrapper>();

      List<ProductOption> options = model.getProductOptions();

      for (ProductOption option : options) {
        ProductOptionWrapper optionWrapper = (ProductOptionWrapper) context.getBean(ProductOptionWrapper.class
            .getName());
        optionWrapper.wrapSummary(option, request);
        this.productOptions.add(optionWrapper);
      }
    }

    if ((model.getMedia() != null) && !model.getMedia().isEmpty()) {
      Media media = model.getMedia().get("primary");

      if (media != null) {
        StaticAssetService staticAssetService = (StaticAssetService) this.context.getBean("blStaticAssetService");
        primaryMedia = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
        primaryMedia.wrapDetails(media, request);

        if (primaryMedia.isAllowOverrideUrl()) {
          primaryMedia.setUrl(staticAssetService.convertAssetPath(media.getUrl(), request.getContextPath(),
              request.isSecure()));
        }
      }
    }

    if (model.getDefaultCategory() != null) {
      this.defaultCategoryId = model.getDefaultCategory().getId();
    }

    if ((model.getUpSaleProducts() != null) && !model.getUpSaleProducts().isEmpty()) {
      upsaleProducts = new ArrayList<RelatedProductWrapper>();

      for (RelatedProduct p : model.getUpSaleProducts()) {
        RelatedProductWrapper upsaleProductWrapper = (RelatedProductWrapper) context.getBean(RelatedProductWrapper.class
            .getName());
        upsaleProductWrapper.wrapSummary(p, request);
        upsaleProducts.add(upsaleProductWrapper);
      }
    }

    if ((model.getCrossSaleProducts() != null) && !model.getCrossSaleProducts().isEmpty()) {
      crossSaleProducts = new ArrayList<RelatedProductWrapper>();

      for (RelatedProduct p : model.getCrossSaleProducts()) {
        RelatedProductWrapper crossSaleProductWrapper = (RelatedProductWrapper) context.getBean(
            RelatedProductWrapper.class.getName());
        crossSaleProductWrapper.wrapSummary(p, request);
        crossSaleProducts.add(crossSaleProductWrapper);
      }
    }

    if ((model.getProductAttributes() != null) && !model.getProductAttributes().isEmpty()) {
      productAttributes = new ArrayList<ProductAttributeWrapper>();

      if (model.getProductAttributes() != null) {
        for (Map.Entry<String, ProductAttribute> entry : model.getProductAttributes().entrySet()) {
          ProductAttributeWrapper wrapper = (ProductAttributeWrapper) context.getBean(ProductAttributeWrapper.class
              .getName());
          wrapper.wrapSummary(entry.getValue(), request);
          productAttributes.add(wrapper);
        }
      }
    }

    if ((model.getMedia() != null) && !model.getMedia().isEmpty()) {
      Map<String, Media> mediaMap = model.getMedia();
      media = new ArrayList<MediaWrapper>();

      StaticAssetService staticAssetService = (StaticAssetService) this.context.getBean("blStaticAssetService");

      for (Media med : mediaMap.values()) {
        MediaWrapper wrapper = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
        wrapper.wrapSummary(med, request);

        if (wrapper.isAllowOverrideUrl()) {
          wrapper.setUrl(staticAssetService.convertAssetPath(med.getUrl(), request.getContextPath(),
              request.isSecure()));
        }

        media.add(wrapper);
      }
    }
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.Product,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Product model, HttpServletRequest request) {
    this.id              = model.getId();
    this.name            = model.getName();
    this.description     = model.getDescription();
    this.longDescription = model.getLongDescription();
    this.active          = model.isActive();

    if (model instanceof ProductBundle) {
      ProductBundle bundle = (ProductBundle) model;
      this.priority               = bundle.getPriority();
      this.bundleItemsRetailPrice = bundle.getBundleItemsRetailPrice();
      this.bundleItemsSalePrice   = bundle.getBundleItemsSalePrice();
    } else {
      this.retailPrice = model.getDefaultSku().getRetailPrice();
      this.salePrice   = model.getDefaultSku().getSalePrice();
    }

    if ((model.getProductOptions() != null) && !model.getProductOptions().isEmpty()) {
      this.productOptions = new ArrayList<ProductOptionWrapper>();

      List<ProductOption> options = model.getProductOptions();

      for (ProductOption option : options) {
        ProductOptionWrapper optionWrapper = (ProductOptionWrapper) context.getBean(ProductOptionWrapper.class
            .getName());
        optionWrapper.wrapSummary(option, request);
        this.productOptions.add(optionWrapper);
      }
    }

    if ((model.getMedia() != null) && !model.getMedia().isEmpty()) {
      Media media = model.getMedia().get("primary");

      if (media != null) {
        StaticAssetService staticAssetService = (StaticAssetService) this.context.getBean("blStaticAssetService");
        primaryMedia = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
        primaryMedia.wrapDetails(media, request);

        if (primaryMedia.isAllowOverrideUrl()) {
          primaryMedia.setUrl(staticAssetService.convertAssetPath(media.getUrl(), request.getContextPath(),
              request.isSecure()));
        }
      }
    }
  } // end method wrapSummary

} // end class ProductWrapper
