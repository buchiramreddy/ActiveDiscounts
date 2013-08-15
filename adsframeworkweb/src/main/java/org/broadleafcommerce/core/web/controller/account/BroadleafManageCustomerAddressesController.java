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

package org.broadleafcommerce.core.web.controller.account;

import java.beans.PropertyEditorSupport;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.core.web.controller.account.validator.CustomerAddressValidator;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.broadleafcommerce.profile.core.service.StateService;
import org.broadleafcommerce.profile.web.core.CustomerState;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafManageCustomerAddressesController extends BroadleafAbstractController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String customerAddressesView     = "account/manageCustomerAddresses";

  /** DOCUMENT ME! */
  protected static String customerAddressesRedirect = "redirect:/account/addresses";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String addressAddedMessage        = "Address successfully added";

  /** DOCUMENT ME! */
  protected String addressRemovedErrorMessage = "Address could not be removed as it is in use";

  /** DOCUMENT ME! */
  protected String addressRemovedMessage = "Address successfully removed";

  /** DOCUMENT ME! */
  protected String       addressUpdatedMessage = "Address successfully updated";
  @Resource(name = "blAddressService")
  private AddressService addressService;
  @Resource(name = "blCountryService")
  private CountryService countryService;

  @Resource(name = "blCustomerAddressService")
  private CustomerAddressService   customerAddressService;
  @Resource(name = "blCustomerAddressValidator")
  private CustomerAddressValidator customerAddressValidator;
  @Resource(name = "blStateService")
  private StateService             stateService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   form                DOCUMENT ME!
   * @param   result              DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  public String addCustomerAddress(HttpServletRequest request, Model model, CustomerAddressForm form,
    BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
    customerAddressValidator.validate(form, result);

    if (result.hasErrors()) {
      return getCustomerAddressesView();
    }

    Address         address         = addressService.saveAddress(form.getAddress());
    CustomerAddress customerAddress = customerAddressService.create();
    customerAddress.setAddress(address);
    customerAddress.setAddressName(form.getAddressName());
    customerAddress.setCustomer(CustomerState.getCustomer());
    customerAddress = customerAddressService.saveCustomerAddress(customerAddress);

    if (form.getAddress().isDefault()) {
      customerAddressService.makeCustomerAddressDefault(customerAddress.getId(), customerAddress.getCustomer().getId());
    }

    if (!isAjaxRequest(request)) {
      List<CustomerAddress> addresses = customerAddressService.readActiveCustomerAddressesByCustomerId(CustomerState
          .getCustomer().getId());
      model.addAttribute("addresses", addresses);
    }

    redirectAttributes.addFlashAttribute("successMessage", getAddressAddedMessage());

    return getCustomerAddressesRedirect();
  } // end method addCustomerAddress

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getAddressAddedMessage() {
    return addressAddedMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getAddressRemovedErrorMessage() {
    return addressRemovedErrorMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getAddressRemovedMessage() {
    return addressRemovedMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getAddressUpdatedMessage() {
    return addressUpdatedMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCustomerAddressesRedirect() {
    return customerAddressesRedirect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCustomerAddressesView() {
    return customerAddressesView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   customerAddressId   DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String removeCustomerAddress(HttpServletRequest request, Model model, Long customerAddressId,
    RedirectAttributes redirectAttributes) {
    try {
      customerAddressService.deleteCustomerAddressById(customerAddressId);
      redirectAttributes.addFlashAttribute("successMessage", getAddressRemovedMessage());
    } catch (DataIntegrityViolationException e) {
      // This likely occurred because there is an order or cart in the system that is currently utilizing this
      // address. Therefore, we're not able to remove it as it breaks a foreign key constraint
      redirectAttributes.addFlashAttribute("errorMessage", getAddressRemovedErrorMessage());
    }

    return getCustomerAddressesRedirect();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request             DOCUMENT ME!
   * @param   model               DOCUMENT ME!
   * @param   customerAddressId   DOCUMENT ME!
   * @param   form                DOCUMENT ME!
   * @param   result              DOCUMENT ME!
   * @param   redirectAttributes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException          DOCUMENT ME!
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public String updateCustomerAddress(HttpServletRequest request, Model model, Long customerAddressId,
    CustomerAddressForm form, BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
    customerAddressValidator.validate(form, result);

    if (result.hasErrors()) {
      return getCustomerAddressesView();
    }

    CustomerAddress customerAddress = customerAddressService.readCustomerAddressById(customerAddressId);

    if (customerAddress == null) {
      throw new IllegalArgumentException("Customer Address not found with the specified customerAddressId");
    }

    customerAddress.setAddress(form.getAddress());
    customerAddress.setAddressName(form.getAddressName());
    customerAddress = customerAddressService.saveCustomerAddress(customerAddress);

    if (form.getAddress().isDefault()) {
      customerAddressService.makeCustomerAddressDefault(customerAddress.getId(), customerAddress.getCustomer().getId());
    }

    redirectAttributes.addFlashAttribute("successMessage", getAddressUpdatedMessage());

    return getCustomerAddressesRedirect();
  } // end method updateCustomerAddress

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request            DOCUMENT ME!
   * @param   model              DOCUMENT ME!
   * @param   customerAddressId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public String viewCustomerAddress(HttpServletRequest request, Model model, Long customerAddressId) {
    CustomerAddress customerAddress = customerAddressService.readCustomerAddressById(customerAddressId);

    if (customerAddress == null) {
      throw new IllegalArgumentException("Customer Address not found with the specified customerAddressId");
    }

    CustomerAddressForm form = new CustomerAddressForm();
    form.setAddress(customerAddress.getAddress());
    form.setAddressName(customerAddress.getAddressName());
    form.setCustomerAddressId(customerAddress.getId());
    model.addAttribute("customerAddressForm", form);

    return getCustomerAddressesView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String viewCustomerAddresses(HttpServletRequest request, Model model) {
    model.addAttribute("customerAddressForm", new CustomerAddressForm());

    return getCustomerAddressesView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Initializes some custom binding operations for the managing an address. More specifically, this method will attempt
   * to bind state and country abbreviations to actual State and Country objects when the String representation of the
   * abbreviation is submitted.
   *
   * @param   request  DOCUMENT ME!
   * @param   binder   DOCUMENT ME!
   *
   * @throws  Exception
   */
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    binder.registerCustomEditor(State.class, "address.state", new PropertyEditorSupport() {
        @Override public void setAsText(String text) {
          State state = stateService.findStateByAbbreviation(text);
          setValue(state);
        }
      });

    binder.registerCustomEditor(Country.class, "address.country", new PropertyEditorSupport() {
        @Override public void setAsText(String text) {
          Country country = countryService.findCountryByAbbreviation(text);
          setValue(country);
        }
      });

    binder.registerCustomEditor(Phone.class, "address.phonePrimary", new PropertyEditorSupport() {
        @Override public void setAsText(String text) {
          if (!StringUtils.isBlank(text)) {
            Phone phone = new PhoneImpl();
            phone.setPhoneNumber(text);
            setValue(phone);
          } else {
            setValue(null);
          }
        }
      });
  } // end method initBinder

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<Country> populateCountries() {
    return countryService.findCountries();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<CustomerAddress> populateCustomerAddresses() {
    return customerAddressService.readActiveCustomerAddressesByCustomerId(CustomerState.getCustomer().getId());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<State> populateStates() {
    return stateService.findStates();
  }

} // end class BroadleafManageCustomerAddressesController
