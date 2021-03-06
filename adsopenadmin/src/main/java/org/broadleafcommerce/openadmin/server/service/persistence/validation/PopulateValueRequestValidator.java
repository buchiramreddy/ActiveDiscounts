/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.validation;

import java.io.Serializable;

import org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;


/**
 * <p>This is injected into the
 * {@link org.broadleafcommerce.openadmin.server.service.persistence.module.BasicPersistenceModule} and invoked prior to
 * any attempts to actually populate values from the {@link org.broadleafcommerce.openadmin.dto.Entity} DTO
 * representation into the Hibernate entity using the
 * {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider} paradigm.
 * </p>
 *
 * <p>An example validator would ensure that Booleans are actually booleans, integers are actually integers, etc. since
 * all values come in as Strings by default</p>
 *
 * @author   Phillip Verheyden (phillipuniverse)
 * @see      {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider}
 * @see
 *           
 *           {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest}
 * @see
 *           
 *           {@link org.broadleafcommerce.openadmin.server.service.persistence.module.BasicPersistenceModule#createPopulatedInstance(java.io.Serializable, org.broadleafcommerce.openadmin.dto.Entity, java.util.Map, Boolean)}
 * @version  $Revision$, $Date$
 */
public interface PopulateValueRequestValidator {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Validates a population request prior to invoking any
   * {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider}s. If no
   * validation could be performed for the given
   * {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest}
   * then return <b>true</b> to let it pass on to a different
   * {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.PopulateValueRequestValidator} or on
   * to a {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider}.
   *
   * @param   populateValueRequest  the
   *                                {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest}
   *                                that should be validated
   * @param   instance              the Hibernate entity that will attempt to be populated
   *
   * @return  false if the
   *          {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest}
   *          failed validation. In this case, the request should not be passed to any
   *          {@link org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider}s.
   */
  PropertyValidationResult validate(PopulateValueRequest populateValueRequest, Serializable instance);

} // end interface PopulateValueRequestValidator
