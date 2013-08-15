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

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.service.AddressService;

import org.springframework.context.ApplicationContext;


/**
 * This is a JAXB wrapper around Address.
 *
 * <p>User: Elbert Bautista Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "address")
public class AddressWrapper extends BaseWrapper implements APIWrapper<Address>, APIUnwrapper<Address> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String addressLine1;

  /** DOCUMENT ME! */
  @XmlElement protected String addressLine2;

  /** DOCUMENT ME! */
  @XmlElement protected String addressLine3;

  /** DOCUMENT ME! */
  @XmlElement protected String city;

  /** DOCUMENT ME! */
  @XmlElement protected String companyName;

  /** DOCUMENT ME! */
  @XmlElement protected CountryWrapper country;

  /** DOCUMENT ME! */
  @XmlElement protected String firstName;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected Boolean isBusiness;

  /** DOCUMENT ME! */
  @XmlElement protected Boolean isDefault;

  /** DOCUMENT ME! */
  @XmlElement protected String lastName;

  /** DOCUMENT ME! */
  @XmlElement protected PhoneWrapper phoneFax;

  /** DOCUMENT ME! */
  @XmlElement protected PhoneWrapper phonePrimary;

  /** DOCUMENT ME! */
  @XmlElement protected PhoneWrapper phoneSecondary;

  /** DOCUMENT ME! */
  @XmlElement protected String postalCode;

  /** DOCUMENT ME! */
  @XmlElement protected StateWrapper state;

  //~ Methods ----------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper#unwrap(javax.servlet.http.HttpServletRequest, org.springframework.context.ApplicationContext)
   */
  @Override public Address unwrap(HttpServletRequest request, ApplicationContext appContext) {
    AddressService addressService = (AddressService) appContext.getBean("blAddressService");
    Address        address        = addressService.create();

    address.setId(this.id);
    address.setFirstName(this.firstName);
    address.setLastName(this.lastName);
    address.setAddressLine1(this.addressLine1);
    address.setAddressLine2(this.addressLine2);
    address.setAddressLine3(this.addressLine3);
    address.setCity(this.city);
    address.setPostalCode(this.postalCode);
    address.setCompanyName(this.companyName);

    if (this.isBusiness != null) {
      address.setBusiness(this.isBusiness);
    }

    if (this.isDefault != null) {
      address.setDefault(this.isDefault);
    }

    if (this.state != null) {
      address.setState(this.state.unwrap(request, appContext));
    }

    if (this.country != null) {
      address.setCountry(this.country.unwrap(request, appContext));
    }

    if (this.phonePrimary != null) {
      address.setPhonePrimary(this.phonePrimary.unwrap(request, appContext));
    }

    if (this.phoneSecondary != null) {
      address.setPhoneSecondary(this.phoneSecondary.unwrap(request, appContext));
    }

    if (this.phoneFax != null) {
      address.setPhoneFax(this.phoneFax.unwrap(request, appContext));
    }

    return address;
  } // end method unwrap

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.profile.core.domain.Address,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Address model, HttpServletRequest request) {
    this.id           = model.getId();
    this.firstName    = model.getFirstName();
    this.lastName     = model.getLastName();
    this.addressLine1 = model.getAddressLine1();
    this.addressLine2 = model.getAddressLine2();
    this.addressLine3 = model.getAddressLine3();
    this.city         = model.getCity();
    this.postalCode   = model.getPostalCode();
    this.companyName  = model.getCompanyName();
    this.isBusiness   = model.isBusiness();
    this.isDefault    = model.isDefault();

    if (model.getState() != null) {
      StateWrapper stateWrapper = (StateWrapper) context.getBean(StateWrapper.class.getName());
      stateWrapper.wrapDetails(model.getState(), request);
      this.state = stateWrapper;
    }

    if (model.getCountry() != null) {
      CountryWrapper countryWrapper = (CountryWrapper) context.getBean(CountryWrapper.class.getName());
      countryWrapper.wrapDetails(model.getCountry(), request);
      this.country = countryWrapper;
    }

    if (model.getPhonePrimary() != null) {
      PhoneWrapper primaryWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
      primaryWrapper.wrapDetails(model.getPhonePrimary(), request);
      this.phonePrimary = primaryWrapper;
    }

    if (model.getPhoneSecondary() != null) {
      PhoneWrapper secondaryWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
      secondaryWrapper.wrapDetails(model.getPhoneSecondary(), request);
      this.phoneSecondary = secondaryWrapper;
    }

    if (model.getPhoneFax() != null) {
      PhoneWrapper faxWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
      faxWrapper.wrapDetails(model.getPhoneFax(), request);
      this.phoneFax = faxWrapper;
    }
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.profile.core.domain.Address,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Address model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class AddressWrapper
