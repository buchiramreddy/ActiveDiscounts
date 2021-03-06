/*
 * Copyright 2008-2013 the original author or authors.
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
/**
 *
 */

package org.broadleafcommerce.admin.server.service.handler;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.presentation.client.OperationType;
import org.broadleafcommerce.common.presentation.client.PersistencePerspectiveItemType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductOption;
import org.broadleafcommerce.core.catalog.domain.ProductOptionValue;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;
import org.broadleafcommerce.core.catalog.service.CatalogService;

import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.FilterAndSortCriteria;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceManager;
import org.broadleafcommerce.openadmin.server.service.persistence.module.AdornedTargetListPersistenceModule;
import org.broadleafcommerce.openadmin.server.service.persistence.module.InspectHelper;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FieldPath;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.Restriction;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.RestrictionFactory;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.predicate.PredicateProvider;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public class SkuCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(SkuCustomPersistenceHandler.class);

  /** DOCUMENT ME! */
  public static String PRODUCT_OPTION_FIELD_PREFIX = "productOption";

  /**
   * This represents the field that all of the product option values will be stored in. This would be used in the case
   * where there are a bunch of product options and displaying each option as a grid header would have everything
   * squashed together. Filtering on this field is currently unsupported.
   */
  public static String CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME = "consolidatedProductOptions";

  /** DOCUMENT ME! */
  public static String CONSOLIDATED_PRODUCT_OPTIONS_DELIMETER = "; ";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdornedTargetListPersistenceModule")
  protected AdornedTargetListPersistenceModule adornedPersistenceModule;

  /** DOCUMENT ME! */
  @Resource(name = "blCatalogService")
  protected CatalogService catalogService;

  /** DOCUMENT ME! */
  @Resource(name = "blSkuRestrictionFactory")
  protected RestrictionFactory skuRestrictionFactory;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  filterMappings      DOCUMENT ME!
   * @param  cto                 DOCUMENT ME!
   * @param  persistencePackage  DOCUMENT ME!
   * @param  skuPropertyPrefix   DOCUMENT ME!
   */
  public static void applyProductOptionValueCriteria(List<FilterMapping> filterMappings, CriteriaTransferObject cto,
    PersistencePackage persistencePackage, String skuPropertyPrefix) {
    // if the front
    final List<Long> productOptionValueFilterIDs = new ArrayList<Long>();

    for (String filterProperty : cto.getCriteriaMap().keySet()) {
      if (filterProperty.startsWith(PRODUCT_OPTION_FIELD_PREFIX)) {
        FilterAndSortCriteria criteria = cto.get(filterProperty);
        productOptionValueFilterIDs.add(Long.parseLong(criteria.getFilterValues().get(0)));
      }
    }

    // also determine if there is a consolidated POV query
    final List<String>    productOptionValueFilterValues = new ArrayList<String>();
    FilterAndSortCriteria consolidatedCriteria           = cto.get(CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME);

    if (!consolidatedCriteria.getFilterValues().isEmpty()) {
      // the criteria in this case would be a semi-colon delimeter value list
      productOptionValueFilterValues.addAll(Arrays.asList(
          StringUtils.split(consolidatedCriteria.getFilterValues().get(0), CONSOLIDATED_PRODUCT_OPTIONS_DELIMETER)));
    }

    if (productOptionValueFilterIDs.size() > 0) {
      FilterMapping filterMapping = new FilterMapping().withFieldPath(new FieldPath().withTargetProperty(
            StringUtils.isEmpty(skuPropertyPrefix) ? "" : (skuPropertyPrefix + "productOptionValues.id")))
        .withDirectFilterValues(productOptionValueFilterIDs).withRestriction(new Restriction().withPredicateProvider(
            new PredicateProvider() {
              @Override public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder,
                From root, String ceilingEntity,
                String fullPropertyName, Path explicitPath, List directValues) {
                return explicitPath.as(Long.class).in(directValues);
              }
            }));
      filterMappings.add(filterMapping);
    }

    if (productOptionValueFilterValues.size() > 0) {
      FilterMapping filterMapping = new FilterMapping().withFieldPath(new FieldPath().withTargetProperty(
            StringUtils.isEmpty(skuPropertyPrefix) ? "" : (skuPropertyPrefix + "productOptionValues.attributeValue")))
        .withDirectFilterValues(productOptionValueFilterValues).withRestriction(new Restriction().withPredicateProvider(
            new PredicateProvider() {
              @Override public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder,
                From root, String ceilingEntity,
                String fullPropertyName, Path explicitPath, List directValues) {
                return explicitPath.as(String.class).in(directValues);
              }
            }));
      filterMappings.add(filterMapping);
    }
  } // end method applyProductOptionValueCriteria

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates the metadata necessary for displaying all of the product option values in a single field. The display of
   * this field is a single string with every product option value appended to it separated by a semicolon. This method
   * should be invoked on an inspect for whatever is utilizing this so that the property will be ready to be populated
   * on fetch.
   *
   * <p>The metadata that is returned will also be set to prominent by default so that it will be ready to display on
   * whatever grid is being inspected. If you do not want this behavior you will need to override this functionality in
   * the metadata that is returned.</p>
   *
   * @param   inheritedFromType  which type this should appear on. This would normally be SkuImpl.class, but if you want
   *                             to display this field with a different entity then this should be that entity
   *
   * @return  creates the metadata necessary for displaying all of the product option values in a single field.
   */
  public static FieldMetadata createConsolidatedOptionField(Class<?> inheritedFromType) {
    BasicFieldMetadata metadata = new BasicFieldMetadata();
    metadata.setFieldType(SupportedFieldType.STRING);
    metadata.setMutable(false);
    metadata.setInheritedFromType(SkuImpl.class.getName());
    metadata.setAvailableToTypes(new String[] { SkuImpl.class.getName() });
    metadata.setForeignKeyCollection(false);
    metadata.setMergedPropertyType(MergedPropertyType.PRIMARY);

    metadata.setName(CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME);
    metadata.setFriendlyName(CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME);
    metadata.setGroup("");
    metadata.setExplicitFieldType(SupportedFieldType.UNKNOWN);
    metadata.setProminent(true);
    metadata.setVisibility(VisibilityEnum.FORM_HIDDEN);
    metadata.setBroadleafEnumeration("");
    metadata.setReadOnly(true);
    metadata.setRequiredOverride(false);
    metadata.setGridOrder(Integer.MAX_VALUE);

    return metadata;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * <p>Creates an individual property for the specified product option. This should set up an enum field whose values
   * will be the option values for this option. This is useful when you would like to display each product option in as
   * its own field in a grid so that you can further filter by product option values.</p>
   *
   * <p>In order for these fields to be utilized property on the fetch, in the GWT frontend you must use the for your
   * datasource.</p>
   *
   * @param   option  DOCUMENT ME!
   * @param   order   DOCUMENT ME!
   *
   * @return  creates an individual property for the specified product option.
   */
  public static FieldMetadata createIndividualOptionField(ProductOption option, int order) {
    BasicFieldMetadata       metadata      = new BasicFieldMetadata();
    List<ProductOptionValue> allowedValues = option.getAllowedValues();

    if (CollectionUtils.isNotEmpty(allowedValues)) {
      metadata.setFieldType(SupportedFieldType.EXPLICIT_ENUMERATION);
      metadata.setMutable(true);
      metadata.setInheritedFromType(SkuImpl.class.getName());
      metadata.setAvailableToTypes(new String[] { SkuImpl.class.getName() });
      metadata.setForeignKeyCollection(false);
      metadata.setMergedPropertyType(MergedPropertyType.PRIMARY);

      // Set up the enumeration based on the product option values
      String[][] optionValues = new String[allowedValues.size()][2];

      for (int i = 0; i < allowedValues.size(); i++) {
        ProductOptionValue value = option.getAllowedValues().get(i);
        optionValues[i][0] = value.getId().toString();
        optionValues[i][1] = value.getAttributeValue();
      }

      metadata.setEnumerationValues(optionValues);

      metadata.setName(PRODUCT_OPTION_FIELD_PREFIX + option.getId());
      metadata.setFriendlyName(option.getLabel());
      metadata.setGroup("productOption_group");
      metadata.setGroupOrder(-1);
      metadata.setOrder(order);
      metadata.setExplicitFieldType(SupportedFieldType.UNKNOWN);
      metadata.setProminent(false);
      metadata.setVisibility(VisibilityEnum.FORM_EXPLICITLY_SHOWN);
      metadata.setBroadleafEnumeration("");
      metadata.setReadOnly(false);
      metadata.setRequiredOverride(BooleanUtils.isFalse(option.getRequired()));

      return metadata;
    } // end if

    return null;
  } // end method createIndividualOptionField

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * A blank {@link org.broadleafcommerce.openadmin.dto.Property} corresponding to the
   * CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME.
   *
   * @return  a blank {@link org.broadleafcommerce.openadmin.dto.Property} corresponding to the
   *          CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME
   */
  public static Property getBlankConsolidatedOptionProperty() {
    Property optionValueProperty = new Property();
    optionValueProperty.setName(CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME);
    optionValueProperty.setValue("");

    return optionValueProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a {@link org.broadleafcommerce.openadmin.dto.Property} filled out with a delimited list of the <b>
   * values</b> that are passed in. This should be invoked on a fetch and the returned property should be added to the
   * fetched {@link org.broadleafcommerce.openadmin.dto.Entity} dto.
   *
   * @param   values  DOCUMENT ME!
   *
   * @return  a {@link org.broadleafcommerce.openadmin.dto.Property} filled out with a delimited list of the <b>
   *          values</b> that are passed in.
   *
   * @see     {@link #createConsolidatedOptionField(Class)};
   */
  public static Property getConsolidatedOptionProperty(List<ProductOptionValue> values) {
    Property optionValueProperty = new Property();
    optionValueProperty.setName(CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME);

    // order the values by the display order of their correspond product option
    // Collections.sort(values, new Comparator<ProductOptionValue>() {
    //
    // @Override
    // public int compare(ProductOptionValue value1, ProductOptionValue value2) {
    // return new CompareToBuilder().append(value1.getProductOption().getDisplayOrder(),
    // value2.getProductOption().getDisplayOrder()).toComparison();
    // }
    // });

    ArrayList<String> stringValues = new ArrayList<String>();
    CollectionUtils.collect(values, new Transformer() {
        @Override public Object transform(Object input) {
          return ((ProductOptionValue) input).getAttributeValue();
        }
      }, stringValues);

    optionValueProperty.setValue(StringUtils.join(stringValues, CONSOLIDATED_PRODUCT_OPTIONS_DELIMETER));

    return optionValueProperty;
  } // end method getConsolidatedOptionProperty

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#add(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    Entity entity = persistencePackage.getEntity();

    try {
      // Fill out the Sku instance from the form
      PersistencePerspective     persistencePerspective = persistencePackage.getPersistencePerspective();
      Sku                        adminInstance          = (Sku) Class.forName(entity.getType()[0]).newInstance();
      Map<String, FieldMetadata> adminProperties        = helper.getSimpleMergedProperties(Sku.class.getName(),
          persistencePerspective);
      adminInstance = (Sku) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

      // Verify that there isn't already a Sku for this particular product option value combo
      Entity errorEntity = validateUniqueProductOptionValueCombination(adminInstance.getProduct(),
          getProductOptionProperties(entity),
          null);

      if (errorEntity != null) {
        entity.setValidationErrors(errorEntity.getValidationErrors());

        return entity;
      }

      // persist the newly-created Sku
      adminInstance = (Sku) dynamicEntityDao.persist(adminInstance);

      // associate the product option values
      associateProductOptionValuesToSku(entity, adminInstance, dynamicEntityDao);

      // After associating the product option values, save off the Sku
      adminInstance = (Sku) dynamicEntityDao.merge(adminInstance);

      // Fill out the DTO and add in the product option value properties to it
      Entity result = helper.getRecord(adminProperties, adminInstance, null, null);

      for (Property property : getProductOptionProperties(entity)) {
        result.addProperty(property);
      }

      return result;
    } catch (Exception e) {
      throw new ServiceException("Unable to perform fetch for entity: " + Sku.class.getName(), e);
    } // end try-catch
  } // end method add

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * <p>Available override point for subclasses if they would like to add additional criteria via the queryCritiera. At
   * the point that this method has been called, criteria from the frontend has already been applied, thus allowing you
   * to override from there as well.</p>
   *
   * <p>Subclasses that choose to override this should also call this super method so that correct filter criteria can
   * be applied for product option values</p>
   *
   * @param  filterMappings      DOCUMENT ME!
   * @param  cto                 DOCUMENT ME!
   * @param  persistencePackage  DOCUMENT ME!
   */
  public void applyAdditionalFetchCriteria(List<FilterMapping> filterMappings, CriteriaTransferObject cto,
    PersistencePackage persistencePackage) {
    // unimplemented
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleAdd(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleAdd(PersistencePackage persistencePackage) {
    OperationType addType = persistencePackage.getPersistencePerspective().getOperationTypes().getAddType();

    return canHandle(persistencePackage, addType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleFetch(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleFetch(PersistencePackage persistencePackage) {
    OperationType fetchType = persistencePackage.getPersistencePerspective().getOperationTypes().getFetchType();

    return canHandle(persistencePackage, fetchType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleInspect(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleInspect(PersistencePackage persistencePackage) {
    return canHandle(persistencePackage,
        persistencePackage.getPersistencePerspective().getOperationTypes().getInspectType());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleUpdate(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
    OperationType updateType = persistencePackage.getPersistencePerspective().getOperationTypes().getUpdateType();

    return canHandle(persistencePackage, updateType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#fetch(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.dto.CriteriaTransferObject,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override
  @SuppressWarnings("unchecked")
  public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto,
    DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
    String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();

    try {
      PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();

      // get the default properties from Sku and its subclasses
      Map<String, FieldMetadata> originalProps = helper.getSimpleMergedProperties(Sku.class.getName(),
          persistencePerspective);

      // Pull back the Skus based on the criteria from the client
      List<FilterMapping> filterMappings = helper.getFilterMappings(persistencePerspective, cto,
          ceilingEntityFullyQualifiedClassname, originalProps, skuRestrictionFactory);

      // allow subclasses to provide additional criteria before executing the query
      applyProductOptionValueCriteria(filterMappings, cto, persistencePackage, null);
      applyAdditionalFetchCriteria(filterMappings, cto, persistencePackage);

      List<Serializable> records = helper.getPersistentRecords(
          persistencePackage.getCeilingEntityFullyQualifiedClassname(), filterMappings, cto.getFirstResult(),
          cto.getMaxResults());

      // Convert Skus into the client-side Entity representation
      Entity[] payload = helper.getRecords(originalProps, records);

      int totalRecords = helper.getTotalRecords(persistencePackage.getCeilingEntityFullyQualifiedClassname(),
          filterMappings);

      // Communicate to the front-end to allow form editing for all of the product options available for the current
      // Product to allow inserting Skus one at a time
      ClassMetadata metadata = new ClassMetadata();

      if (cto.get("product").getFilterValues().size() > 0) {
        Long           productId  = Long.parseLong(cto.get("product").getFilterValues().get(0));
        Product        product    = catalogService.findProductById(productId);
        List<Property> properties = new ArrayList<Property>();

        for (ProductOption option : product.getProductOptions()) {
          Property optionProperty = new Property();
          optionProperty.setName(PRODUCT_OPTION_FIELD_PREFIX + option.getId());
          properties.add(optionProperty);
        }

        metadata.setProperties(properties.toArray(new Property[0]));
      }

      // Now fill out the relevant properties for the product options for the Skus that were returned
      for (int i = 0; i < records.size(); i++) {
        Sku    sku    = (Sku) records.get(i);
        Entity entity = payload[i];

        List<ProductOptionValue> optionValues = sku.getProductOptionValues();

        for (ProductOptionValue value : optionValues) {
          Property optionProperty = new Property();
          optionProperty.setName(PRODUCT_OPTION_FIELD_PREFIX + value.getProductOption().getId());
          optionProperty.setValue(value.getId().toString());
          entity.addProperty(optionProperty);
        }

        if (CollectionUtils.isNotEmpty(optionValues)) {
          entity.addProperty(getConsolidatedOptionProperty(optionValues));
        } else {
          entity.addProperty(getBlankConsolidatedOptionProperty());
        }
      }

      return new DynamicResultSet(metadata, payload, totalRecords);
    } catch (Exception e) {
      throw new ServiceException("Unable to perform fetch for entity: " + ceilingEntityFullyQualifiedClassname, e);
    } // end try-catch
  } // end method fetch

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Build out the extra fields for the product options.
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   dynamicEntityDao    DOCUMENT ME!
   * @param   helper              DOCUMENT ME!
   *
   * @return  build out the extra fields for the product options.
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  @Override public DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    InspectHelper helper) throws ServiceException {
    try {
      PersistencePerspective                              persistencePerspective =
        persistencePackage.getPersistencePerspective();
      Map<MergedPropertyType, Map<String, FieldMetadata>> allMergedProperties    =
        new HashMap<MergedPropertyType, Map<String, FieldMetadata>>();

      // Grab the default properties for the Sku
      Map<String, FieldMetadata> properties = helper.getSimpleMergedProperties(Sku.class.getName(),
          persistencePerspective);

      if ((persistencePackage.getCustomCriteria() == null) || (persistencePackage.getCustomCriteria().length == 0)) {
        // look up all the ProductOptions and then create new fields for each of them. Although
        // all of the options might not be relevant for the current Product (and thus the Skus as well) we
        // can hide the irrelevant fields in the fetch via a custom ClientEntityModule
        List<ProductOption> options = catalogService.readAllProductOptions();
        int                 order   = 0;

        for (ProductOption option : options) {
          // add this to the built Sku properties
          FieldMetadata md = createIndividualOptionField(option, order);

          if (md != null) {
            properties.put("productOption" + option.getId(), md);
          }
        }
      } else {
        Long    productId = Long.parseLong(persistencePackage.getCustomCriteria()[0]);
        Product product   = catalogService.findProductById(productId);

        for (ProductOption option : product.getProductOptions()) {
          FieldMetadata md = createIndividualOptionField(option, 0);

          if (md != null) {
            properties.put("productOption" + option.getId(), md);
          }
        }
      } // end if-else

      // also build the consolidated field; if using the SkuBasicClientEntityModule then this field will be
      // permanently hidden
      properties.put(CONSOLIDATED_PRODUCT_OPTIONS_FIELD_NAME, createConsolidatedOptionField(SkuImpl.class));

      allMergedProperties.put(MergedPropertyType.PRIMARY, properties);

      // allow the adorned list to contribute properties as well in the case of Sku bundle items
      adornedPersistenceModule.setPersistenceManager((PersistenceManager) helper);
      adornedPersistenceModule.updateMergedProperties(persistencePackage, allMergedProperties);

      Class<?>[]       entityClasses  = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(Sku.class);
      ClassMetadata    mergedMetadata = helper.getMergedClassMetadata(entityClasses, allMergedProperties);
      DynamicResultSet results        = new DynamicResultSet(mergedMetadata, null, null);

      return results;
    } catch (Exception e) {
      ServiceException ex = new ServiceException("Unable to retrieve inspection results for "
          + persistencePackage.getCeilingEntityFullyQualifiedClassname(), e);
      throw ex;
    } // end try-catch
  } // end method inspect

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#update(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    Entity entity = persistencePackage.getEntity();

    try {
      // Fill out the Sku instance from the form
      PersistencePerspective     persistencePerspective = persistencePackage.getPersistencePerspective();
      Map<String, FieldMetadata> adminProperties        = helper.getSimpleMergedProperties(Sku.class.getName(),
          persistencePerspective);
      Object                     primaryKey             = helper.getPrimaryKey(entity, adminProperties);
      Sku                        adminInstance          = (Sku) dynamicEntityDao.retrieve(Class.forName(
            entity.getType()[0]), primaryKey);
      adminInstance = (Sku) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

      // Verify that there isn't already a Sku for this particular product option value combo
      Entity errorEntity = validateUniqueProductOptionValueCombination(adminInstance.getProduct(),
          getProductOptionProperties(entity),
          adminInstance);

      if (errorEntity != null) {
        entity.setValidationErrors(errorEntity.getValidationErrors());

        return entity;
      }

      associateProductOptionValuesToSku(entity, adminInstance, dynamicEntityDao);

      adminInstance = (Sku) dynamicEntityDao.merge(adminInstance);

      // Fill out the DTO and add in the product option value properties to it
      Entity result = helper.getRecord(adminProperties, adminInstance, null, null);

      for (Property property : getProductOptionProperties(entity)) {
        result.addProperty(property);
      }

      return result;
    } catch (Exception e) {
      throw new ServiceException("Unable to perform fetch for entity: " + Sku.class.getName(), e);
    } // end try-catch
  } // end method update

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This initially removes all of the product option values that are currently related to the Sku and then
   * re-associates the {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s.
   *
   * @param  entity            DOCUMENT ME!
   * @param  adminInstance     DOCUMENT ME!
   * @param  dynamicEntityDao  DOCUMENT ME!
   */
  protected void associateProductOptionValuesToSku(Entity entity, Sku adminInstance,
    DynamicEntityDao dynamicEntityDao) {
    // Get the list of product option value ids that were selected from the form
    List<Long> productOptionValueIds = new ArrayList<Long>();

    for (Property property : getProductOptionProperties(entity)) {
      productOptionValueIds.add(Long.parseLong(property.getValue()));
    }

    // remove the current list of product option values from the Sku
    if (adminInstance.getProductOptionValues().size() > 0) {
      adminInstance.getProductOptionValues().clear();
      dynamicEntityDao.merge(adminInstance);
    }

    // Associate the product option values from the form with the Sku
    List<ProductOption> productOptions = adminInstance.getProduct().getProductOptions();

    for (ProductOption option : productOptions) {
      for (ProductOptionValue value : option.getAllowedValues()) {
        if (productOptionValueIds.contains(value.getId())) {
          adminInstance.getProductOptionValues().add(value);
        }
      }
    }
  } // end method associateProductOptionValuesToSku

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Since this is the default for all Skus, it's possible that we are providing custom criteria for this Sku lookup. In
   * that case, we probably want to delegate to a child class, so only use this particular persistence handler if there
   * is no custom criteria being used and the ceiling entity is an instance of Sku. The exception to this rule is when
   * we are pulling back Media, since the admin actually uses Sku for the ceiling entity class name. That should be
   * handled by the map structure module though, so only handle things in the Sku custom persistence handler for
   * OperationType.BASIC
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   operationType       DOCUMENT ME!
   *
   * @return  since this is the default for all Skus, it's possible that we are providing custom criteria for this Sku
   *          lookup.
   */
  protected Boolean canHandle(PersistencePackage persistencePackage, OperationType operationType) {
    String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();

    try {
      Class testClass = Class.forName(ceilingEntityFullyQualifiedClassname);

      return Sku.class.isAssignableFrom(testClass)
        &&
        // ArrayUtils.isEmpty(persistencePackage.getCustomCriteria()) &&
        OperationType.BASIC.equals(operationType)
        && (persistencePackage.getPersistencePerspective().getPersistencePerspectiveItems().get(
            PersistencePerspectiveItemType.ADORNEDTARGETLIST) == null);
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<Property> getProductOptionProperties(Entity entity) {
    List<Property> productOptionProperties = new ArrayList<Property>();

    for (Property property : entity.getProperties()) {
      if (property.getName().startsWith(PRODUCT_OPTION_FIELD_PREFIX)) {
        productOptionProperties.add(property);
      }
    }

    return productOptionProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Ensures that the given list of {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue} IDs is unique
   * for the given {@link org.broadleafcommerce.core.catalog.domain.Product}.
   *
   * @param   product                  DOCUMENT ME!
   * @param   productOptionProperties  DOCUMENT ME!
   * @param   currentSku               - for update operations, this is the current Sku that is being updated; should be
   *                                   excluded from attempting validation
   *
   * @return  <b>null</b> if successfully validation, the error entity otherwise
   */
  protected Entity validateUniqueProductOptionValueCombination(Product product, List<Property> productOptionProperties,
    Sku currentSku) {
    // do not attempt POV validation if no PO properties were passed in
    if (CollectionUtils.isNotEmpty(productOptionProperties)) {
      List<Long> productOptionValueIds = new ArrayList<Long>();

      for (Property property : productOptionProperties) {
        productOptionValueIds.add(Long.parseLong(property.getValue()));
      }

      boolean validated = true;

      for (Sku sku : product.getAdditionalSkus()) {
        if ((currentSku == null) || !sku.getId().equals(currentSku.getId())) {
          List<Long> testList = new ArrayList<Long>();

          for (ProductOptionValue optionValue : sku.getProductOptionValues()) {
            testList.add(optionValue.getId());
          }

          if (CollectionUtils.isNotEmpty(testList)
                && productOptionValueIds.containsAll(testList)
                && (productOptionValueIds.size() == testList.size())) {
            validated = false;

            break;
          }
        }
      }

      if (!validated) {
        Entity errorEntity = new Entity();

        for (Property productOptionProperty : productOptionProperties) {
          errorEntity.addValidationError(productOptionProperty.getName(), "uniqueSkuError");
        }

        return errorEntity;
      }
    } // end if

    return null;
  } // end method validateUniqueProductOptionValueCombination

} // end class SkuCustomPersistenceHandler
