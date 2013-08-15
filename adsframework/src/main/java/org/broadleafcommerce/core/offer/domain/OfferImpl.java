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

package org.broadleafcommerce.core.offer.domain;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.persistence.ArchiveStatus;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationMapField;
import org.broadleafcommerce.common.presentation.AdminPresentationMapFields;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.util.DateUtil;

import org.broadleafcommerce.core.offer.service.type.OfferDeliveryType;
import org.broadleafcommerce.core.offer.service.type.OfferDiscountType;
import org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType;
import org.broadleafcommerce.core.offer.service.type.OfferType;

import org.broadleafcommerce.openadmin.server.service.type.RuleIdentifier;

import org.codehaus.jackson.annotate.JsonIgnore;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "OfferImpl_baseOffer"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE BLC_OFFER SET ARCHIVED = 'Y' WHERE OFFER_ID = ?")
@Table(name = "BLC_OFFER")
public class OfferImpl implements Offer, Status {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(
    name   = "APPLIES_WHEN_RULES",
    length = Integer.MAX_VALUE - 1
  )
  @Deprecated @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String                  appliesToCustomerRules;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(
    name   = "APPLIES_TO_RULES",
    length = Integer.MAX_VALUE - 1
  )
  @Deprecated @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String                  appliesToOrderRules;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "APPLY_OFFER_TO_MARKED_ITEMS")
  @Deprecated protected boolean applyDiscountToMarkedItems;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Description",
    order        = 2000,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description,
    prominent    = true,
    gridOrder    = 2,
    largeEntry   = true
  )
  @Column(name = "OFFER_DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "OfferImpl_Offer_Discount_Type",
    order                = 1000,
    group                = Presentation.Group.Name.Amount,
    groupOrder           = Presentation.Group.Order.Amount,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.offer.service.type.OfferDiscountType"
  )
  @Column(name = "OFFER_DISCOUNT_TYPE")
  @Index(
    name        = "OFFER_DISCOUNT_INDEX",
    columnNames = { "OFFER_DISCOUNT_TYPE" }
  )
  protected String discountType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_End_Date",
    order        = 2,
    group        = Presentation.Group.Name.ActivityRange,
    groupOrder   = Presentation.Group.Order.ActivityRange
  )
  @Column(name = "END_DATE")
  protected Date endDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Id",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "OFFER_ID")
  @GeneratedValue(generator = "OfferId")
  @GenericGenerator(
    name       = "OfferId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OfferImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.OfferImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_marketingMessage",
    order        = 6000,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description,
    translatable = true
  )
  @Column(name = "MARKETING_MESSASGE")
  @Index(
    name        = "OFFER_MARKETING_MESSAGE_INDEX",
    columnNames = { "MARKETING_MESSASGE" }
  )
  protected String marketingMessage;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Name",
    order        = 1000,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description,
    prominent    = true,
    gridOrder    = 1
  )
  @Column(
    name     = "OFFER_NAME",
    nullable = false
  )
  @Index(
    name        = "OFFER_NAME_INDEX",
    columnNames = { "OFFER_NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Apply_To_Sale_Price",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "APPLY_TO_SALE_PRICE")
  protected Boolean applyToSalePrice = false;

  /** DOCUMENT ME! */
  @Embedded protected ArchiveStatus archiveStatus = new ArchiveStatus();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Automatically_Added",
    order        = 5000,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description,
    fieldType    = SupportedFieldType.BOOLEAN
  )
  @Column(name = "AUTOMATICALLY_ADDED")
  protected Boolean automaticallyAdded = false;

  /** No offers can be applied on top of this offer; If false, stackable has to be false also. */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Combinable",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "COMBINABLE_WITH_OTHER_OFFERS")
  protected Boolean combinableWithOtherOffers = true;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "OFFER_DELIVERY_TYPE")
  protected String deliveryType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Max_Uses_Per_Customer",
    order        = 8,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "MAX_USES_PER_CUSTOMER")
  protected Long maxUsesPerCustomer;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Max_Uses_Per_Order",
    order        = 7,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "MAX_USES")
  protected Integer maxUsesPerOrder;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType      = AddMethodType.PERSIST,
    friendlyName = "offerCodeTitle",
    order        = 1,
    tab          = Presentation.Tab.Name.Codes,
    tabOrder     = Presentation.Tab.Order.Codes
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @OneToMany(
    mappedBy      = "offer",
    targetEntity  = OfferCodeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<OfferCode> offerCodes = new ArrayList<OfferCode>(100);

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "OfferImpl_Item_Qualifier_Rule",
    tab                  = Presentation.Tab.Name.Advanced,
    tabOrder             = Presentation.Tab.Order.Advanced,
    group                = Presentation.Group.Name.Advanced,
    groupOrder           = Presentation.Group.Order.Advanced,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType"
  )
  @Column(name = "OFFER_ITEM_QUALIFIER_RULE")
  protected String offerItemQualifierRuleType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "OfferImpl_Item_Target_Rule",
    tab                  = Presentation.Tab.Name.Advanced,
    tabOrder             = Presentation.Tab.Order.Advanced,
    group                = Presentation.Group.Name.Advanced,
    groupOrder           = Presentation.Group.Order.Advanced,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType"
  )
  @Column(name = "OFFER_ITEM_TARGET_RULE")
  protected String offerItemTargetRuleType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Priority",
    order        = 7,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "OFFER_PRIORITY")
  protected Integer priority;

  /** DOCUMENT ME! */
  @Column(name = "STACKABLE")
  protected Boolean stackable = true;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Start_Date",
    order        = 1,
    group        = Presentation.Group.Name.ActivityRange,
    groupOrder   = Presentation.Group.Order.ActivityRange
  )
  @Column(name = "START_DATE")
  protected Date startDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Target_System",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "TARGET_SYSTEM")
  protected String targetSystem;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "OfferImpl_Offer_Type",
    order                = 3000,
    group                = Presentation.Group.Name.Description,
    groupOrder           = Presentation.Group.Order.Description,
    prominent            = true,
    gridOrder            = 3,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.offer.service.type.OfferType"
  )
  @Column(
    name     = "OFFER_TYPE",
    nullable = false
  )
  @Index(
    name        = "OFFER_TYPE_INDEX",
    columnNames = { "OFFER_TYPE" }
  )
  protected String type;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Current_Uses",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "USES")
  @Deprecated protected int uses;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Offer_Value",
    order        = 2000,
    group        = Presentation.Group.Name.Amount,
    groupOrder   = Presentation.Group.Order.Amount,
    prominent    = true,
    gridOrder    = 4
  )
  @Column(
    name      = "OFFER_VALUE",
    nullable  = false,
    precision = 19,
    scale     = 5
  )
  protected BigDecimal value;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName   = "OfferImpl_Qualifying_Item_Rule",
    group          = Presentation.Group.Name.Qualifiers,
    groupOrder     = Presentation.Group.Order.Qualifiers,
    fieldType      = SupportedFieldType.RULE_WITH_QUANTITY,
    ruleIdentifier = RuleIdentifier.ORDERITEM
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_QUAL_CRIT_OFFER_XREF",
    joinColumns        = @JoinColumn(name = "OFFER_ID"),
    inverseJoinColumns = @JoinColumn(name = "OFFER_ITEM_CRITERIA_ID")
  )
  @OneToMany(
    fetch        = FetchType.LAZY,
    targetEntity = OfferItemCriteriaImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected Set<OfferItemCriteria> qualifyingItemCriteria = new HashSet<OfferItemCriteria>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Qualifying_Item_Subtotal",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(
    name      = "QUALIFYING_ITEM_MIN_TOTAL",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal qualifyingItemSubTotal;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "OfferImpl_Target_Item_Rule",
    group            = Presentation.Group.Name.ItemTarget,
    groupOrder       = Presentation.Group.Order.ItemTarget,
    fieldType        = SupportedFieldType.RULE_WITH_QUANTITY,
    ruleIdentifier   = RuleIdentifier.ORDERITEM,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_TAR_CRIT_OFFER_XREF",
    joinColumns        = @JoinColumn(name = "OFFER_ID"),
    inverseJoinColumns = @JoinColumn(name = "OFFER_ITEM_CRITERIA_ID")
  )
  @OneToMany(
    fetch        = FetchType.LAZY,
    targetEntity = OfferItemCriteriaImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected Set<OfferItemCriteria> targetItemCriteria = new HashSet<OfferItemCriteria>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Totalitarian_Offer",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "TOTALITARIAN_OFFER")
  protected Boolean totalitarianOffer = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OfferImpl_Treat_As_New_Format",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "USE_NEW_FORMAT")
  protected Boolean treatAsNewFormat = false;

  /** DOCUMENT ME! */
  @AdminPresentationMapFields(
    mapDisplayFields = {
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.CUSTOMER_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            group          = Presentation.Group.Name.Qualifiers,
            groupOrder     = Presentation.Group.Order.Qualifiers,
            ruleIdentifier = RuleIdentifier.CUSTOMER,
            friendlyName   = "OfferImpl_Customer_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.TIME_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            group          = Presentation.Group.Name.ActivityRange,
            groupOrder     = Presentation.Group.Order.ActivityRange,
            ruleIdentifier = RuleIdentifier.TIME,
            friendlyName   = "OfferImpl_Time_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.ORDER_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            group          = Presentation.Group.Name.Qualifiers,
            groupOrder     = Presentation.Group.Order.Qualifiers,
            ruleIdentifier = RuleIdentifier.ORDER,
            friendlyName   = "OfferImpl_Order_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.FULFILLMENT_GROUP_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            group          = Presentation.Group.Name.Qualifiers,
            groupOrder     = Presentation.Group.Order.Qualifiers,
            ruleIdentifier = RuleIdentifier.FULFILLMENTGROUP,
            friendlyName   = "OfferImpl_FG_Rule"
          )
      )
    }
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_OFFER_RULE_MAP",
    inverseJoinColumns =
      @JoinColumn(
        name           = "OFFER_RULE_ID",
        referencedColumnName = "OFFER_RULE_ID"
      )
  )
  @ManyToMany(
    targetEntity = OfferRuleImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @MapKeyColumn(
    name     = "MAP_KEY",
    nullable = false
  )
  Map<String, OfferRule> offerMatchRules = new HashMap<String, OfferRule>();

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

    OfferImpl other = (OfferImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (startDate == null) {
      if (other.startDate != null) {
        return false;
      }
    } else if (!startDate.equals(other.startDate)) {
      return false;
    }

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getAppliesToCustomerRules()
   */
  @Deprecated @Override public String getAppliesToCustomerRules() {
    return appliesToCustomerRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getAppliesToOrderRules()
   */
  @Deprecated @Override public String getAppliesToOrderRules() {
    return appliesToOrderRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated @JsonIgnore public boolean getApplyDiscountToMarkedItems() {
    return applyDiscountToMarkedItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getApplyDiscountToSalePrice()
   */
  @Override public boolean getApplyDiscountToSalePrice() {
    return (applyToSalePrice == null) ? false : applyToSalePrice;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#getArchived()
   */
  @Override public Character getArchived() {
    if (archiveStatus == null) {
      archiveStatus = new ArchiveStatus();
    }

    return archiveStatus.getArchived();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated @JsonIgnore public boolean getCombinableWithOtherOffers() {
    return combinableWithOtherOffers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getDeliveryType()
   */
  @Deprecated @JsonIgnore @Override public OfferDeliveryType getDeliveryType() {
    if (deliveryType == null) {
      if (isAutomaticallyAdded()) {
        return OfferDeliveryType.AUTOMATIC;
      } else {
        return OfferDeliveryType.MANUAL;
      }
    }

    return OfferDeliveryType.getInstance(deliveryType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getDiscountType()
   */
  @Override public OfferDiscountType getDiscountType() {
    return OfferDiscountType.getInstance(discountType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getEndDate()
   */
  @Override public Date getEndDate() {
    return endDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getMarketingMessage()
   */
  @Override public String getMarketingMessage() {
    return DynamicTranslationProvider.getValue(this, "marketingMessage", marketingMessage);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getMaxUses()
   */
  @Override public int getMaxUses() {
    return getMaxUsesPerOrder();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getMaxUsesPerCustomer()
   */
  @Override public Long getMaxUsesPerCustomer() {
    return maxUsesPerCustomer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getMaxUsesPerOrder() {
    return (maxUsesPerOrder == null) ? 0 : maxUsesPerOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<OfferCode> getOfferCodes() {
    return offerCodes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getOfferItemQualifierRuleType()
   */
  @Override public OfferItemRestrictionRuleType getOfferItemQualifierRuleType() {
    return OfferItemRestrictionRuleType.getInstance(offerItemQualifierRuleType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getOfferItemTargetRuleType()
   */
  @Override public OfferItemRestrictionRuleType getOfferItemTargetRuleType() {
    return OfferItemRestrictionRuleType.getInstance(offerItemTargetRuleType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getOfferMatchRules()
   */
  @Override public Map<String, OfferRule> getOfferMatchRules() {
    if (offerMatchRules == null) {
      offerMatchRules = new HashMap<String, OfferRule>();
    }

    return offerMatchRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getPriority()
   */
  @Override public int getPriority() {
    return (priority == null) ? 0 : priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getQualifyingItemCriteria()
   */
  @Override public Set<OfferItemCriteria> getQualifyingItemCriteria() {
    return qualifyingItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getQualifyingItemSubTotal()
   */
  @Override public Money getQualifyingItemSubTotal() {
    return (qualifyingItemSubTotal == null) ? null : BroadleafCurrencyUtils.getMoney(qualifyingItemSubTotal, null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated @JsonIgnore public boolean getStackable() {
    return stackable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getStartDate()
   */
  @Override public Date getStartDate() {
    if ('Y' == getArchived()) {
      return null;
    }

    return startDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getTargetItemCriteria()
   */
  @Override public Set<OfferItemCriteria> getTargetItemCriteria() {
    return targetItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getTargetSystem()
   */
  @Override public String getTargetSystem() {
    return targetSystem;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getTreatAsNewFormat()
   */
  @Override public Boolean getTreatAsNewFormat() {
    return treatAsNewFormat;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getType()
   */
  @Override public OfferType getType() {
    return OfferType.getInstance(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getUses()
   */
  @Deprecated @Override public int getUses() {
    return uses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#getValue()
   */
  @Override public BigDecimal getValue() {
    return value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());
    result = (prime * result) + ((startDate == null) ? 0 : startDate.hashCode());
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());
    result = (prime * result) + ((value == null) ? 0 : value.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#isActive()
   */
  @Override public boolean isActive() {
    return DateUtil.isActive(startDate, endDate, true) && ('Y' != getArchived());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#isApplyDiscountToMarkedItems()
   */
  @Deprecated @Override public boolean isApplyDiscountToMarkedItems() {
    return applyDiscountToMarkedItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#isAutomaticallyAdded()
   */
  @Override public boolean isAutomaticallyAdded() {
    if (automaticallyAdded == null) {
      if (deliveryType != null) {
        OfferDeliveryType offerDeliveryType = OfferDeliveryType.getInstance(deliveryType);

        return OfferDeliveryType.AUTOMATIC.equals(offerDeliveryType);
      }

      return false;
    }

    return automaticallyAdded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if this offer can be combined with other offers in the order.
   *
   * @return  true if combinableWithOtherOffers, otherwise false
   */
  @Override public boolean isCombinableWithOtherOffers() {
    return (combinableWithOtherOffers == null) ? false : combinableWithOtherOffers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if this offer can be stacked on top of another offer. Stackable is evaluated against offers with the
   * same offer type.
   *
   * @return  true if stackable, otherwise false
   */
  @Override public boolean isStackable() {
    return (stackable == null) ? false : stackable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#isTotalitarianOffer()
   */
  @Override public Boolean isTotalitarianOffer() {
    if (totalitarianOffer == null) {
      return false;
    } else {
      return totalitarianOffer.booleanValue();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setAppliesToCustomerRules(java.lang.String)
   */
  @Deprecated @Override public void setAppliesToCustomerRules(String appliesToCustomerRules) {
    this.appliesToCustomerRules = appliesToCustomerRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setAppliesToOrderRules(java.lang.String)
   */
  @Deprecated @Override public void setAppliesToOrderRules(String appliesToOrderRules) {
    this.appliesToOrderRules = appliesToOrderRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setApplyDiscountToMarkedItems(boolean)
   */
  @Deprecated @Override public void setApplyDiscountToMarkedItems(boolean applyDiscountToMarkedItems) {
    this.applyDiscountToMarkedItems = applyDiscountToMarkedItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setApplyDiscountToSalePrice(boolean)
   */
  @Override public void setApplyDiscountToSalePrice(boolean applyToSalePrice) {
    this.applyToSalePrice = applyToSalePrice;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#setArchived(java.lang.Character)
   */
  @Override public void setArchived(Character archived) {
    if (archiveStatus == null) {
      archiveStatus = new ArchiveStatus();
    }

    archiveStatus.setArchived(archived);
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setAutomaticallyAdded(boolean)
   */
  @Override public void setAutomaticallyAdded(boolean automaticallyAdded) {
    this.automaticallyAdded = automaticallyAdded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the combinableWithOtherOffers value for this offer.
   *
   * @param  combinableWithOtherOffers  DOCUMENT ME!
   */
  @Override public void setCombinableWithOtherOffers(boolean combinableWithOtherOffers) {
    this.combinableWithOtherOffers = combinableWithOtherOffers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setDeliveryType(org.broadleafcommerce.core.offer.service.type.OfferDeliveryType)
   */
  @Override public void setDeliveryType(OfferDeliveryType deliveryType) {
    this.deliveryType = deliveryType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setDiscountType(org.broadleafcommerce.core.offer.service.type.OfferDiscountType)
   */
  @Override public void setDiscountType(OfferDiscountType discountType) {
    this.discountType = discountType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setEndDate(java.util.Date)
   */
  @Override public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setMarketingMessage(java.lang.String)
   */
  @Override public void setMarketingMessage(String marketingMessage) {
    this.marketingMessage = marketingMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setMaxUses(int)
   */
  @Override public void setMaxUses(int maxUses) {
    setMaxUsesPerOrder(maxUses);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setMaxUsesPerCustomer(java.lang.Long)
   */
  @Override public void setMaxUsesPerCustomer(Long maxUsesPerCustomer) {
    this.maxUsesPerCustomer = maxUsesPerCustomer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  maxUsesPerOrder  DOCUMENT ME!
   */
  public void setMaxUsesPerOrder(int maxUsesPerOrder) {
    this.maxUsesPerOrder = maxUsesPerOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerCodes  DOCUMENT ME!
   */
  public void setOfferCodes(List<OfferCode> offerCodes) {
    this.offerCodes = offerCodes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setOfferItemQualifierRuleType(org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType)
   */
  @Override public void setOfferItemQualifierRuleType(OfferItemRestrictionRuleType restrictionRuleType) {
    this.offerItemQualifierRuleType = restrictionRuleType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setOfferItemTargetRuleType(org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType)
   */
  @Override public void setOfferItemTargetRuleType(OfferItemRestrictionRuleType restrictionRuleType) {
    this.offerItemTargetRuleType = restrictionRuleType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setOfferMatchRules(java.util.Map)
   */
  @Override public void setOfferMatchRules(Map<String, OfferRule> offerMatchRules) {
    this.offerMatchRules = offerMatchRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setPriority(int)
   */
  @Override public void setPriority(int priority) {
    this.priority = priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setQualifyingItemCriteria(java.util.Set)
   */
  @Override public void setQualifyingItemCriteria(Set<OfferItemCriteria> qualifyingItemCriteria) {
    this.qualifyingItemCriteria = qualifyingItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setQualifyingItemSubTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setQualifyingItemSubTotal(Money qualifyingItemSubTotal) {
    this.qualifyingItemSubTotal = Money.toAmount(qualifyingItemSubTotal);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the stackable value for this offer.
   *
   * @param  stackable  DOCUMENT ME!
   */
  @Override public void setStackable(boolean stackable) {
    this.stackable = stackable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setStartDate(java.util.Date)
   */
  @Override public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setTargetItemCriteria(java.util.Set)
   */
  @Override public void setTargetItemCriteria(Set<OfferItemCriteria> targetItemCriteria) {
    this.targetItemCriteria = targetItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setTargetSystem(java.lang.String)
   */
  @Override public void setTargetSystem(String targetSystem) {
    this.targetSystem = targetSystem;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setTotalitarianOffer(java.lang.Boolean)
   */
  @Override public void setTotalitarianOffer(Boolean totalitarianOffer) {
    if (totalitarianOffer == null) {
      this.totalitarianOffer = false;
    } else {
      this.totalitarianOffer = totalitarianOffer;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setTreatAsNewFormat(java.lang.Boolean)
   */
  @Override public void setTreatAsNewFormat(Boolean treatAsNewFormat) {
    this.treatAsNewFormat = treatAsNewFormat;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setType(org.broadleafcommerce.core.offer.service.type.OfferType)
   */
  @Override public void setType(OfferType type) {
    this.type = type.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setUses(int)
   */
  @Deprecated @Override public void setUses(int uses) {
    this.uses = uses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.Offer#setValue(java.math.BigDecimal)
   */
  @Override public void setValue(BigDecimal value) {
    this.value = value;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class Presentation {
    //~ Inner Classes --------------------------------------------------------------------------------------------------

    public static class Tab {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String Codes    = "OfferImpl_Codes_Tab";
        public static final String Advanced = "OfferImpl_Advanced_Tab";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int Codes    = 1000;
        public static final int Advanced = 2000;
      }
    }

    public static class Group {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String Description   = "OfferImpl_Description";
        public static final String Amount        = "OfferImpl_Amount";
        public static final String ActivityRange = "OfferImpl_Activity_Range";
        public static final String Qualifiers    = "OfferImpl_Qualifiers";
        public static final String ItemTarget    = "OfferImpl_Item_Target";
        public static final String Advanced      = "OfferImpl_Advanced";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int Description   = 1000;
        public static final int Amount        = 2000;
        public static final int ActivityRange = 3000;
        public static final int Qualifiers    = 4000;
        public static final int ItemTarget    = 5000;
        public static final int Advanced      = 1000;
      }
    } // end class Group
  } // end class Presentation
} // end class OfferImpl
