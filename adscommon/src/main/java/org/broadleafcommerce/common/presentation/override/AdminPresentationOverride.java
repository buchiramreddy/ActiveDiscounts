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

package org.broadleafcommerce.common.presentation.override;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author pverheyden
 * @deprecated use {@link org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides} instead
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Deprecated
public @interface AdminPresentationOverride {
    
    /**
     * The name of the property whose {@link org.broadleafcommerce.common.presentation.AdminPresentation} annotation should be overwritten
     * 
     * @return the name of the property that should be overwritten
     */
    String name();
    
    /**
     * The {@link org.broadleafcommerce.common.presentation.AdminPresentation} to overwrite the property with. This is a comprehensive override,
     * meaning whatever was declared on the target property previously will be completely replaced
     * with what is defined in this {@link org.broadleafcommerce.common.presentation.AdminPresentation}.
     * 
     * @return the {@link org.broadleafcommerce.common.presentation.AdminPresentation} being mapped to the attribute
     */
    AdminPresentation value() default @AdminPresentation();

}
