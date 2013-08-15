/*
 * Copyright 2013 the original author or authors.
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

package org.broadleafcommerce.openadmin.server.service.persistence.validation;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>Implementation of the
 * {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.EntityValidatorService} that runs
 * entities through JSR-303 validations. The default behavior of this implementation is to use the out-of-the-box
 * Broadleaf validations (through {@link org.broadleafcommerce.common.presentation.ValidationConfiguration} <i>in
 * addition to</i> any JSR-303 annotations that you have configured on your entity.</p>
 *
 * <p>In order to use this validator rather than the default, you will need to include an implementation of
 * {@link javax.validation.Validator} in your Spring root application context (not the servlet). For example, you would
 * modify your applicationContext-admin.xml to inject Spring's default implementation of
 * {@link javax.validation.Validator} (the one used by Spring MVC):</p>
 *
 * <pre>
 *<code>
   &lt;bean class=&quotorg.springframework.validation.beanvalidation.LocalValidatorFactoryBean&quot/&gt;
   </code></pre>
 *
 * <p>Then override the the blEntityValidatorService bean to use this class instead:</p>
 *
 * <pre>
 *<code>
   &lt;bean id=&quotblEntityValidatorService&quot
          class=&quotorg.broadleafcommerce.openadmin.server.service.persistence.validation.BeanValidationEntityValidatorServiceImpl&quot/&gt;
   </code></pre>
 *
 * <p>For more information on the default Spring JSR-303 validator, check out the docs at</p>
 *
 * @author   Phillip Verheyden
 * @see
 *           
 *           {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.EntityValidatorServiceImpl#validate(org.broadleafcommerce.openadmin.dto.Entity, java.io.Serializable, java.util.Map)}
 * @see      {@link javax.validation.Validator}
 * @see      <a
 *           href="http://static.springsource.org/spring/docs/3.1.3.RELEASE/spring-framework-reference/html/validation.html#validation-beanvalidation">
 *           Spring Validation Docs</a>
 * @version  $Revision$, $Date$
 */
public class BeanValidationEntityValidatorServiceImpl extends EntityValidatorServiceImpl {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /**
   * If true (default behavior) this will invoke the default implementation to perform validations hooked up via
   * {@link org.broadleafcommerce.common.presentation.ValidationConfiguration} from
   * {@link org.broadleafcommerce.common.presentation.AdminPresentation}.
   */
  protected boolean useDefaultEntityValidations = true;

  /** DOCUMENT ME! */
  @Autowired protected Validator validator;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Validator getValidator() {
    return validator;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isUseDefaultEntityValidations() {
    return useDefaultEntityValidations;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  useDefaultEntityValidations  DOCUMENT ME!
   */
  public void setUseDefaultEntityValidations(boolean useDefaultEntityValidations) {
    this.useDefaultEntityValidations = useDefaultEntityValidations;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  validator  DOCUMENT ME!
   */
  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.validation.EntityValidatorServiceImpl#validate(org.broadleafcommerce.openadmin.dto.Entity,
   *       java.io.Serializable, java.util.Map)
   */
  @Override public void validate(Entity entity, Serializable instance, Map<String, FieldMetadata> mergedProperties) {
    if (isUseDefaultEntityValidations()) {
      super.validate(entity, instance, mergedProperties);
    }

    Set<ConstraintViolation<Serializable>> violations = getValidator().validate(instance);

    for (ConstraintViolation<Serializable> violation : violations) {
      entity.addValidationError(violation.getPropertyPath().toString(), violation.getMessage());
    }
  }

} // end class BeanValidationEntityValidatorServiceImpl
