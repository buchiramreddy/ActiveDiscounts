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

package org.broadleafcommerce.core.catalog.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.util.DimensionUnitOfMeasureType;
import org.broadleafcommerce.common.vendor.service.type.ContainerShapeType;
import org.broadleafcommerce.common.vendor.service.type.ContainerSizeType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Embeddable public class Dimension implements Serializable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductDimension_Product_Width",
    order        = 1000,
    tab          = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder     = ProductImpl.Presentation.Tab.Order.Shipping,
    group        = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder   = ProductImpl.Presentation.Group.Order.Shipping
  )
  @Column(name = "WIDTH")
  protected BigDecimal width;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductDimension_Product_Height",
    order        = 2000,
    tab          = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder     = ProductImpl.Presentation.Tab.Order.Shipping,
    group        = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder   = ProductImpl.Presentation.Group.Order.Shipping
  )
  @Column(name = "HEIGHT")
  protected BigDecimal height;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductDimension_Product_Depth",
    order        = 3000,
    tab          = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder     = ProductImpl.Presentation.Tab.Order.Shipping,
    group        = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder   = ProductImpl.Presentation.Group.Order.Shipping
  )
  @Column(name = "DEPTH")
  protected BigDecimal depth;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductDimension_Product_Girth",
    order        = 4000,
    tab          = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder     = ProductImpl.Presentation.Tab.Order.Shipping,
    group        = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder   = ProductImpl.Presentation.Group.Order.Shipping
  )
  @Column(name = "GIRTH")
  protected BigDecimal girth;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "ProductDimension_Product_Container_Size",
    order                = 5000,
    tab                  = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder             = ProductImpl.Presentation.Tab.Order.Shipping,
    group                = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder           = ProductImpl.Presentation.Group.Order.Shipping,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.vendor.service.type.ContainerSizeType"
  )
  @Column(name = "CONTAINER_SIZE")
  protected String size;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "ProductDimension_Product_Container_Shape",
    order                = 6000,
    tab                  = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder             = ProductImpl.Presentation.Tab.Order.Shipping,
    group                = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder           = ProductImpl.Presentation.Group.Order.Shipping,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.vendor.service.type.ContainerShapeType"
  )
  @Column(name = "CONTAINER_SHAPE")
  protected String container;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "ProductDimension_Product_Dimension_Units",
    order                = 7000,
    tab                  = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder             = ProductImpl.Presentation.Tab.Order.Shipping,
    group                = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder           = ProductImpl.Presentation.Group.Order.Shipping,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.util.DimensionUnitOfMeasureType"
  )
  @Column(name = "DIMENSION_UNIT_OF_MEASURE")
  protected String dimensionUnitOfMeasure;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getWidth() {
    return width;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  width  DOCUMENT ME!
   */
  public void setWidth(final BigDecimal width) {
    this.width = width;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getHeight() {
    return height;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  height  DOCUMENT ME!
   */
  public void setHeight(final BigDecimal height) {
    this.height = height;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getDepth() {
    return depth;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  depth  DOCUMENT ME!
   */
  public void setDepth(final BigDecimal depth) {
    this.depth = depth;
  }

  /**
   * Returns the product dimensions as a String (assumes measurements are in inches).
   *
   * @return  a String value of the product dimensions
   */
  public String getDimensionString() {
    return height + "Hx" + width + "Wx" + depth + "D\"";
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getGirth() {
    return girth;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  girth  DOCUMENT ME!
   */
  public void setGirth(final BigDecimal girth) {
    this.girth = girth;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ContainerSizeType getSize() {
    return ContainerSizeType.getInstance(size);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  size  DOCUMENT ME!
   */
  public void setSize(final ContainerSizeType size) {
    if (size != null) {
      this.size = size.getType();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ContainerShapeType getContainer() {
    return ContainerShapeType.getInstance(container);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  container  DOCUMENT ME!
   */
  public void setContainer(final ContainerShapeType container) {
    if (container != null) {
      this.container = container.getType();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DimensionUnitOfMeasureType getDimensionUnitOfMeasure() {
    return DimensionUnitOfMeasureType.getInstance(dimensionUnitOfMeasure);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  dimensionUnitOfMeasure  DOCUMENT ME!
   */
  public void setDimensionUnitOfMeasure(final DimensionUnitOfMeasureType dimensionUnitOfMeasure) {
    if (dimensionUnitOfMeasure != null) {
      this.dimensionUnitOfMeasure = dimensionUnitOfMeasure.getType();
    }
  }
} // end class Dimension
