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

package org.broadleafcommerce.profile.core.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;
import org.broadleafcommerce.common.time.domain.TemporalTimestampListener;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "AddressImpl_baseAddress"
)
@AdminPresentationMergeOverrides(
  {
    @AdminPresentationMergeOverride(
      name = "phonePrimary",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
    ),
    @AdminPresentationMergeOverride(
      name = "phoneSecondary",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
    ),
    @AdminPresentationMergeOverride(
      name = "phoneFax",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = true
        )
    ),
    @AdminPresentationMergeOverride(
      name = "phonePrimary.phoneNumber",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = false
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.ORDER,
          intOverrideValue = 1300
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.REQUIREDOVERRIDE,
          overrideValue = "NOT_REQUIRED"
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.FRIENDLYNAME,
          overrideValue = "PhoneImpl_Primary_Phone"
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "phoneSecondary.phoneNumber",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = false
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.ORDER,
          intOverrideValue = 1400
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.REQUIREDOVERRIDE,
          overrideValue = "NOT_REQUIRED"
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.FRIENDLYNAME,
          overrideValue = "PhoneImpl_Secondary_Phone"
        )
      }
    ),
    @AdminPresentationMergeOverride(
      name = "phoneFax.phoneNumber",
      mergeEntries = {
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.EXCLUDED,
          booleanOverrideValue = false
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.ORDER,
          intOverrideValue = 1500
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.REQUIREDOVERRIDE,
          overrideValue = "NOT_REQUIRED"
        ),
        @AdminPresentationMergeEntry(
          propertyType = PropertyType.AdminPresentation.FRIENDLYNAME,
          overrideValue = "PhoneImpl_Fax_Phone"
        )
      }
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADDRESS")
public class AddressImpl implements Address {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Address_1",
    order        = 50,
    group        = "AddressImpl_Address"
  )
  @Column(
    name     = "ADDRESS_LINE1",
    nullable = false
  )
  protected String addressLine1;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Address_2",
    order        = 60,
    group        = "AddressImpl_Address"
  )
  @Column(name = "ADDRESS_LINE2")
  protected String addressLine2;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Address_3",
    order        = 60,
    group        = "AddressImpl_Address"
  )
  @Column(name = "ADDRESS_LINE3")
  protected String addressLine3;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_City",
    order        = 70,
    group        = "AddressImpl_Address"
  )
  @Column(
    name     = "CITY",
    nullable = false
  )
  protected String city;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Company_Name",
    order        = 40,
    group        = "AddressImpl_Address"
  )
  @Column(name = "COMPANY_NAME")
  protected String companyName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Country",
    order        = 100,
    group        = "AddressImpl_Address"
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "ADDRESS_COUNTRY_INDEX",
    columnNames = { "COUNTRY" }
  )
  @JoinColumn(name = "COUNTRY")
  @ManyToOne(
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE },
    targetEntity = CountryImpl.class,
    optional     = false
  )
  protected Country                               country;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_County",
    order        = 90,
    group        = "AddressImpl_Address"
  )
  @Column(name = "COUNTY")
  protected String county;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Email_Address",
    order        = 30,
    group        = "AddressImpl_Address"
  )
  @Column(name = "EMAIL_ADDRESS")
  protected String emailAddress;

  /** DOCUMENT ME! */
  @Column(name = "FAX")
  @Deprecated protected String fax;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_First_Name",
    order        = 10,
    group        = "AddressImpl_Address"
  )
  @Column(name = "FIRST_NAME")
  protected String firstName;

  /** DOCUMENT ME! */
  @Column(name = "ADDRESS_ID")
  @GeneratedValue(generator = "AddressId")
  @GenericGenerator(
    name       = "AddressId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AddressImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.AddressImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Active_Address",
    order        = 170,
    group        = "AddressImpl_Address"
  )
  @Column(name = "IS_ACTIVE")
  protected boolean isActive = true;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Business_Address",
    order        = 180,
    group        = "AddressImpl_Address"
  )
  @Column(name = "IS_BUSINESS")
  protected boolean isBusiness = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Default_Address",
    order        = 160,
    group        = "AddressImpl_Address"
  )
  @Column(name = "IS_DEFAULT")
  protected boolean isDefault = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Last_Name",
    order        = 20,
    group        = "AddressImpl_Address"
  )
  @Column(name = "LAST_NAME")
  protected String lastName;

  /** DOCUMENT ME! */
  @Index(
    name        = "ADDRESS_PHONE_FAX_IDX",
    columnNames = { "PHONE_FAX_ID" }
  )
  @JoinColumn(name = "PHONE_FAX_ID")
  @ManyToOne(
    targetEntity = PhoneImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected Phone phoneFax;

  /** DOCUMENT ME! */
  @Index(
    name        = "ADDRESS_PHONE_PRI_IDX",
    columnNames = { "PHONE_PRIMARY_ID" }
  )
  @JoinColumn(name = "PHONE_PRIMARY_ID")
  @ManyToOne(
    targetEntity = PhoneImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected Phone phonePrimary;

  /** DOCUMENT ME! */
  @Index(
    name        = "ADDRESS_PHONE_SEC_IDX",
    columnNames = { "PHONE_SECONDARY_ID" }
  )
  @JoinColumn(name = "PHONE_SECONDARY_ID")
  @ManyToOne(
    targetEntity = PhoneImpl.class,
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE }
  )
  protected Phone phoneSecondary;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Postal_Code",
    order        = 110,
    group        = "AddressImpl_Address"
  )
  @Column(
    name     = "POSTAL_CODE",
    nullable = false
  )
  protected String postalCode;

  /** DOCUMENT ME! */
  @Column(name = "PRIMARY_PHONE")
  @Deprecated protected String primaryPhone;

  /** DOCUMENT ME! */
  @Column(name = "SECONDARY_PHONE")
  @Deprecated protected String secondaryPhone;

  /** This is intented to be used for address verification integrations and should not be modifiable in the admin. */
  @AdminPresentation(
    friendlyName = "AddressImpl_Standardized",
    order        = 200,
    group        = "AddressImpl_Address",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "STANDARDIZED")
  protected Boolean standardized = Boolean.FALSE;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_State",
    order        = 80,
    group        = "AddressImpl_Address"
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "ADDRESS_STATE_INDEX",
    columnNames = { "STATE_PROV_REGION" }
  )
  @JoinColumn(name = "STATE_PROV_REGION")
  @ManyToOne(
    cascade      = { CascadeType.PERSIST, CascadeType.MERGE },
    targetEntity = StateImpl.class
  )
  protected State                               state;

  /** This is intented to be used for address verification integrations and should not be modifiable in the admin. */
  @AdminPresentation(
    friendlyName = "AddressImpl_Tokenized_Address",
    order        = 190,
    group        = "AddressImpl_Address",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "TOKENIZED_ADDRESS")
  protected String tokenizedAddress;

  /** This is intented to be used for address verification integrations and should not be modifiable in the admin. */
  @AdminPresentation(
    friendlyName = "AddressImpl_Verification_Level",
    order        = 210,
    group        = "AddressImpl_Address",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "VERIFICATION_LEVEL")
  protected String verificationLevel;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AddressImpl_Four_Digit_Zip",
    order        = 120,
    group        = "AddressImpl_Address"
  )
  @Column(name = "ZIP_FOUR")
  protected String zipFour;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    AddressImpl other = (AddressImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (addressLine1 == null) {
      if (other.addressLine1 != null) {
        return false;
      }
    } else if (!addressLine1.equals(other.addressLine1)) {
      return false;
    }

    if (addressLine2 == null) {
      if (other.addressLine2 != null) {
        return false;
      }
    } else if (!addressLine2.equals(other.addressLine2)) {
      return false;
    }

    if (city == null) {
      if (other.city != null) {
        return false;
      }
    } else if (!city.equals(other.city)) {
      return false;
    }

    if (companyName == null) {
      if (other.companyName != null) {
        return false;
      }
    } else if (!companyName.equals(other.companyName)) {
      return false;
    }

    if (country == null) {
      if (other.country != null) {
        return false;
      }
    } else if (!country.equals(other.country)) {
      return false;
    }

    if (county == null) {
      if (other.county != null) {
        return false;
      }
    } else if (!county.equals(other.county)) {
      return false;
    }

    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }

    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }

    if (postalCode == null) {
      if (other.postalCode != null) {
        return false;
      }
    } else if (!postalCode.equals(other.postalCode)) {
      return false;
    }

    if (state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!state.equals(other.state)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getAddressLine1()
   */
  @Override public String getAddressLine1() {
    return addressLine1;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getAddressLine2()
   */
  @Override public String getAddressLine2() {
    return addressLine2;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getAddressLine3()
   */
  @Override public String getAddressLine3() {
    return addressLine3;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getCity()
   */
  @Override public String getCity() {
    return city;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getCompanyName()
   */
  @Override public String getCompanyName() {
    return companyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getCountry()
   */
  @Override public Country getCountry() {
    return country;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getCounty()
   */
  @Override public String getCounty() {
    return county;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getEmailAddress()
   */
  @Override public String getEmailAddress() {
    return this.emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getFax()
   */
  @Deprecated @Override public String getFax() {
    return this.fax;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getFirstName()
   */
  @Override public String getFirstName() {
    return firstName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getLastName()
   */
  @Override public String getLastName() {
    return lastName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getPhoneFax()
   */
  @Override public Phone getPhoneFax() {
    Phone legacyPhone = new PhoneImpl();
    legacyPhone.setPhoneNumber(this.fax);

    return ((phoneFax == null) && (this.fax != null)) ? legacyPhone : phoneFax;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getPhonePrimary()
   */
  @Override public Phone getPhonePrimary() {
    Phone legacyPhone = new PhoneImpl();
    legacyPhone.setPhoneNumber(this.primaryPhone);

    return ((phonePrimary == null) && (this.primaryPhone != null)) ? legacyPhone : phonePrimary;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getPhoneSecondary()
   */
  @Override public Phone getPhoneSecondary() {
    Phone legacyPhone = new PhoneImpl();
    legacyPhone.setPhoneNumber(this.secondaryPhone);

    return ((phoneSecondary == null) && (this.secondaryPhone != null)) ? legacyPhone : phoneSecondary;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getPostalCode()
   */
  @Override public String getPostalCode() {
    return postalCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getPrimaryPhone()
   */
  @Deprecated @Override public String getPrimaryPhone() {
    return primaryPhone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getSecondaryPhone()
   */
  @Deprecated @Override public String getSecondaryPhone() {
    return secondaryPhone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getStandardized()
   */
  @Override public Boolean getStandardized() {
    return standardized;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getState()
   */
  @Override public State getState() {
    return state;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getTokenizedAddress()
   */
  @Override public String getTokenizedAddress() {
    return tokenizedAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getVerificationLevel()
   */
  @Override public String getVerificationLevel() {
    return verificationLevel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#getZipFour()
   */
  @Override public String getZipFour() {
    return zipFour;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((addressLine1 == null) ? 0 : addressLine1.hashCode());
    result = (prime * result) + ((addressLine2 == null) ? 0 : addressLine2.hashCode());
    result = (prime * result) + ((city == null) ? 0 : city.hashCode());
    result = (prime * result) + ((companyName == null) ? 0 : companyName.hashCode());
    result = (prime * result) + ((country == null) ? 0 : country.hashCode());
    result = (prime * result) + ((county == null) ? 0 : county.hashCode());
    result = (prime * result) + ((firstName == null) ? 0 : firstName.hashCode());
    result = (prime * result) + ((lastName == null) ? 0 : lastName.hashCode());
    result = (prime * result) + ((postalCode == null) ? 0 : postalCode.hashCode());
    result = (prime * result) + ((state == null) ? 0 : state.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#isActive()
   */
  @Override public boolean isActive() {
    return isActive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#isBusiness()
   */
  @Override public boolean isBusiness() {
    return isBusiness;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#isDefault()
   */
  @Override public boolean isDefault() {
    return isDefault;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setActive(boolean)
   */
  @Override public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setAddressLine1(java.lang.String)
   */
  @Override public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setAddressLine2(java.lang.String)
   */
  @Override public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setAddressLine3(java.lang.String)
   */
  @Override public void setAddressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setBusiness(boolean)
   */
  @Override public void setBusiness(boolean isBusiness) {
    this.isBusiness = isBusiness;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setCity(java.lang.String)
   */
  @Override public void setCity(String city) {
    this.city = city;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setCompanyName(java.lang.String)
   */
  @Override public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setCountry(org.broadleafcommerce.profile.core.domain.Country)
   */
  @Override public void setCountry(Country country) {
    this.country = country;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setCounty(java.lang.String)
   */
  @Override public void setCounty(String county) {
    this.county = county;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setDefault(boolean)
   */
  @Override public void setDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setEmailAddress(java.lang.String)
   */
  @Override public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setFax(java.lang.String)
   */
  @Deprecated @Override public void setFax(String fax) {
    this.fax = fax;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setFirstName(java.lang.String)
   */
  @Override public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setLastName(java.lang.String)
   */
  @Override public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setPhoneFax(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public void setPhoneFax(Phone phoneFax) {
    this.phoneFax = phoneFax;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setPhonePrimary(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public void setPhonePrimary(Phone phonePrimary) {
    this.phonePrimary = phonePrimary;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setPhoneSecondary(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public void setPhoneSecondary(Phone phoneSecondary) {
    this.phoneSecondary = phoneSecondary;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setPostalCode(java.lang.String)
   */
  @Override public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setPrimaryPhone(java.lang.String)
   */
  @Deprecated @Override public void setPrimaryPhone(String primaryPhone) {
    this.primaryPhone = primaryPhone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setSecondaryPhone(java.lang.String)
   */
  @Deprecated @Override public void setSecondaryPhone(String secondaryPhone) {
    this.secondaryPhone = secondaryPhone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setStandardized(java.lang.Boolean)
   */
  @Override public void setStandardized(Boolean standardized) {
    this.standardized = standardized;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setState(org.broadleafcommerce.profile.core.domain.State)
   */
  @Override public void setState(State state) {
    this.state = state;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setTokenizedAddress(java.lang.String)
   */
  @Override public void setTokenizedAddress(String tokenizedAddress) {
    this.tokenizedAddress = tokenizedAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setVerificationLevel(java.lang.String)
   */
  @Override public void setVerificationLevel(String verificationLevel) {
    this.verificationLevel = verificationLevel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Address#setZipFour(java.lang.String)
   */
  @Override public void setZipFour(String zipFour) {
    this.zipFour = zipFour;
  }
} // end class AddressImpl
