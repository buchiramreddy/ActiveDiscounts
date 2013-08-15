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

package org.broadleafcommerce.profile.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.config.service.ModuleConfigurationService;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;

import org.broadleafcommerce.profile.core.dao.AddressDao;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.service.exception.AddressVerificationException;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blAddressService")
public class AddressServiceImpl implements AddressService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAddressDao")
  protected AddressDao addressDao;

  /** DOCUMENT ME! */
  @Resource(name = "blModuleConfigurationService")
  protected ModuleConfigurationService moduleConfigService;

  /** DOCUMENT ME! */
  @Resource(name = "blAddressVerificationProviders")
  protected List<AddressVerificationProvider> providers;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.AddressService#create()
   */
  @Override public Address create() {
    return addressDao.create();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.AddressService#delete(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void delete(Address address) {
    addressDao.delete(address);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.AddressService#readAddressById(java.lang.Long)
   */
  @Override public Address readAddressById(Long addressId) {
    return addressDao.readAddressById(addressId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.AddressService#saveAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public Address saveAddress(Address address) {
    return addressDao.save(address);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.AddressService#verifyAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public List<Address> verifyAddress(Address address) throws AddressVerificationException {
    if ((providers != null) && !providers.isEmpty()) {
      List<ModuleConfiguration> moduleConfigs = moduleConfigService.findActiveConfigurationsByType(
          ModuleConfigurationType.ADDRESS_VERIFICATION);

      if ((moduleConfigs != null) && !moduleConfigs.isEmpty()) {
        // Try to find a default configuration
        ModuleConfiguration config = null;

        for (ModuleConfiguration configuration : moduleConfigs) {
          if (configuration.getIsDefault()) {
            config = configuration;

            break;
          }
        }

        if (config == null) {
          // if there wasn't a default one, use the first active one...
          config = moduleConfigs.get(0);
        }

        for (AddressVerificationProvider provider : providers) {
          if (provider.canRespond(config)) {
            return provider.validateAddress(address, config);
          }
        }
      }
    } // end if

    throw new AddressVerificationException("No providers were configured to handle address validation");
  } // end method verifyAddress

} // end class AddressServiceImpl
