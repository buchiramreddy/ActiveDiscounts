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

package org.broadleafcommerce.core.web.catalog;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService;
import org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import java.util.HashMap;

/**
 * Responsible for setting up the {@link org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext}. Rather than simply creating a filter that
 * implements this interface, consider instead subclassing the {@link org.broadleafcommerce.core.web.catalog.DefaultDynamicSkuPricingFilter} and overriding the
 * appropriate methods.
 * 
 * @author jfischer
 * @see {@link org.broadleafcommerce.core.web.catalog.DefaultDynamicSkuPricingFilter}
 * @see {@link org.broadleafcommerce.core.web.catalog.AbstractDynamicSkuPricingFilter}
 * @see {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService}
 * @see {@link org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext}
 */
public interface DynamicSkuPricingFilter extends Filter {

    /**
     * The result of this invocation should be set on
     * {@link org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext#setSkuPricingConsiderationContext(java.util.HashMap)} and ultimately passed to
     * {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService} to determine prices.
     * 
     * @param request
     * @return a map of considerations to be used by the service in {@link #getDynamicSkuPricingService(javax.servlet.ServletRequest)}.
     * @see {@link org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext#getSkuPricingConsiderationContext()}
     * @see {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService}
     */
    @SuppressWarnings("rawtypes")
    public HashMap getPricingConsiderations(ServletRequest request);

    /**
     * The result of this invocation should be set on
     * {@link org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext#setSkuPricingService(org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService)}. This is the service that will
     * be used in calculating dynamic prices for a Sku or product option value
     * 
     * @param request
     * @return
     * @see {@link org.broadleafcommerce.core.catalog.domain.Sku#getRetailPrice()}
     * @see {@link org.broadleafcommerce.core.catalog.domain.Sku#getSalePrice()}
     */
    public DynamicSkuPricingService getDynamicSkuPricingService(ServletRequest request);
    
}
