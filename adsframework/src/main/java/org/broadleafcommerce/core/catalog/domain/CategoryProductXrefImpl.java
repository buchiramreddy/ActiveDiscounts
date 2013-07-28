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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * The Class CategoryProductXrefImpl is the default implmentation of {@link org.broadleafcommerce.core.catalog.domain.Category}.
 * This entity is only used for executing a named query.
 *
 * If you want to add fields specific to your implementation of BroadLeafCommerce you should extend
 * this class and add your fields.  If you need to make significant changes to the class then you
 * should implement your own version of {@link org.broadleafcommerce.core.catalog.domain.Category}.
 * <br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations.
 * The Entity references the following tables:
 * BLC_CATEGORY_PRODUCT_XREF,
 *
 * @see {@link org.broadleafcommerce.core.catalog.domain.Category}, {@link org.broadleafcommerce.core.catalog.domain.ProductImpl}
 * @author btaylor
 */
@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CATEGORY_PRODUCT_XREF")
@AdminPresentationClass(excludeFromPolymorphism = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
public class CategoryProductXrefImpl implements CategoryProductXref {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    CategoryProductXrefPK categoryProductXref = new CategoryProductXrefPK();

    public CategoryProductXrefPK getCategoryProductXref() {
        return categoryProductXref;
    }

    public void setCategoryProductXref(CategoryProductXrefPK categoryProductXref) {
        this.categoryProductXref = categoryProductXref;
    }

    /** The display order. */
    @Column(name = "DISPLAY_ORDER")
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long displayOrder;

    /* (non-Javadoc)
     * @see org.broadleafcommerce.core.catalog.domain.CategoryProductXref#getDisplayOrder()
     */
    public Long getDisplayOrder() {
        return displayOrder;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.core.catalog.domain.CategoryProductXref#setDisplayOrder(java.lang.Integer)
     */
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * @return
     * @see CategoryProductXrefImpl.CategoryProductXrefPK#getCategory()
     */
    public Category getCategory() {
        return categoryProductXref.getCategory();
    }

    /**
     * @param category
     * @see CategoryProductXrefImpl.CategoryProductXrefPK#setCategory(Category)
     */
    public void setCategory(Category category) {
        categoryProductXref.setCategory(category);
    }

    /**
     * @return
     * @see CategoryProductXrefImpl.CategoryProductXrefPK#getProduct()
     */
    public Product getProduct() {
        return categoryProductXref.getProduct();
    }

    /**
     * @param product
     * @see CategoryProductXrefImpl.CategoryProductXrefPK#setProduct(Product)
     */
    public void setProduct(Product product) {
        categoryProductXref.setProduct(product);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CategoryProductXrefImpl) {
            CategoryProductXrefImpl that = (CategoryProductXrefImpl) o;
            return new EqualsBuilder()
                .append(categoryProductXref, that.categoryProductXref)
                .build();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = categoryProductXref != null ? categoryProductXref.hashCode() : 0;
        return result;
    }

}
