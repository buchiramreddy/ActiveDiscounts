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

package org.broadleafcommerce.openadmin.server.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.broadleafcommerce.common.presentation.client.PersistencePerspectiveItemType;

import org.broadleafcommerce.openadmin.dto.AdornedTargetCollectionMetadata;
import org.broadleafcommerce.openadmin.dto.AdornedTargetList;
import org.broadleafcommerce.openadmin.dto.BasicCollectionMetadata;
import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.CollectionMetadata;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.FilterAndSortCriteria;
import org.broadleafcommerce.openadmin.dto.ForeignKey;
import org.broadleafcommerce.openadmin.dto.MapMetadata;
import org.broadleafcommerce.openadmin.dto.MapStructure;
import org.broadleafcommerce.openadmin.dto.OperationTypes;
import org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor;


/**
 * A DTO class used to seed a persistence package.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class PersistencePackageRequest {
  //~ Enums ------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @author   $author$
   * @version  $Revision$, $Date$
   */
  public enum Type {
    //~ Enum constants -------------------------------------------------------------------------------------------------

    STANDARD, ADORNED, MAP
  }

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // These properties are accessed via getters and setters that operate on arrays.
  // We back them with a list so that we can have the convenience .add methods
  /** DOCUMENT ME! */
  protected List<ForeignKey>            additionalForeignKeys  = new ArrayList<ForeignKey>();

  /** DOCUMENT ME! */
  protected AdornedTargetList           adornedList;

  /** DOCUMENT ME! */
  protected String                      ceilingEntityClassname;

  /** DOCUMENT ME! */
  protected String                      configKey;

  /** DOCUMENT ME! */
  protected List<String>                customCriteria        = new ArrayList<String>();

  /** DOCUMENT ME! */
  protected Entity                      entity;

  /** DOCUMENT ME! */
  protected List<FilterAndSortCriteria> filterAndSortCriteria = new ArrayList<FilterAndSortCriteria>();

  /** DOCUMENT ME! */
  protected ForeignKey                  foreignKey;

  /** DOCUMENT ME! */
  protected MapStructure                mapStructure;

  /** DOCUMENT ME! */
  protected Integer                     maxIndex;

  /** DOCUMENT ME! */
  protected OperationTypes operationTypesOverride = null;

  /** DOCUMENT ME! */
  protected Integer        startIndex;

  /** DOCUMENT ME! */
  protected Type type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /* ************ */
  /* CONSTRUCTORS */
  /* ************ */

  /**
   * Creates a new PersistencePackageRequest object.
   */
  public PersistencePackageRequest() { }

  /**
   * Creates a new PersistencePackageRequest object.
   *
   * @param  type  DOCUMENT ME!
   */
  public PersistencePackageRequest(Type type) {
    this.type = type;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static PersistencePackageRequest adorned() {
    return new PersistencePackageRequest(Type.ADORNED);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a semi-populate PersistencePacakageRequest based on the specified FieldMetadata. This initializer will copy
   * over persistence perspective items from the metadata as well as set the appropriate OperationTypes as specified in
   * the annotation/xml configuration for the field.
   *
   * @param   md  DOCUMENT ME!
   *
   * @return  the newly created PersistencePackageRequest
   */
  public static PersistencePackageRequest fromMetadata(FieldMetadata md) {
    final PersistencePackageRequest request = new PersistencePackageRequest();

    md.accept(new MetadataVisitor() {
        @Override public void visit(BasicFieldMetadata fmd) {
          request.setType(Type.STANDARD);
          request.setCeilingEntityClassname(fmd.getForeignKeyClass());
        }

        @Override public void visit(BasicCollectionMetadata fmd) {
          ForeignKey foreignKey = (ForeignKey) fmd.getPersistencePerspective().getPersistencePerspectiveItems().get(
              PersistencePerspectiveItemType.FOREIGNKEY);

          request.setType(Type.STANDARD);
          request.setCeilingEntityClassname(fmd.getCollectionCeilingEntity());
          request.setOperationTypesOverride(fmd.getPersistencePerspective().getOperationTypes());
          request.setForeignKey(foreignKey);
        }

        @Override public void visit(AdornedTargetCollectionMetadata fmd) {
          AdornedTargetList adornedList = (AdornedTargetList) fmd.getPersistencePerspective()
            .getPersistencePerspectiveItems().get(PersistencePerspectiveItemType.ADORNEDTARGETLIST);

          request.setType(Type.ADORNED);
          request.setCeilingEntityClassname(fmd.getCollectionCeilingEntity());
          request.setOperationTypesOverride(fmd.getPersistencePerspective().getOperationTypes());
          request.setAdornedList(adornedList);
        }

        @Override public void visit(MapMetadata fmd) {
          MapStructure mapStructure = (MapStructure) fmd.getPersistencePerspective().getPersistencePerspectiveItems()
            .get(PersistencePerspectiveItemType.MAPSTRUCTURE);

          ForeignKey foreignKey = (ForeignKey) fmd.getPersistencePerspective().getPersistencePerspectiveItems().get(
              PersistencePerspectiveItemType.FOREIGNKEY);

          request.setType(Type.MAP);
          request.setCeilingEntityClassname(foreignKey.getForeignKeyClass());
          request.setOperationTypesOverride(fmd.getPersistencePerspective().getOperationTypes());
          request.setMapStructure(mapStructure);
          request.setForeignKey(foreignKey);
        }
      });

    if (md instanceof CollectionMetadata) {
      request.setCustomCriteria(((CollectionMetadata) md).getCustomCriteria());
    }

    return request;
  } // end method fromMetadata

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static PersistencePackageRequest map() {
    return new PersistencePackageRequest(Type.MAP);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ******************* */
  /* STATIC INITIALIZERS */
  /* ******************* */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static PersistencePackageRequest standard() {
    return new PersistencePackageRequest(Type.STANDARD);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* *********** */
  /* ADD METHODS */
  /* *********** */

  /**
   * DOCUMENT ME!
   *
   * @param   foreignKey  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest addAdditionalForeignKey(ForeignKey foreignKey) {
    additionalForeignKeys.add(foreignKey);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customCriteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest addCustomCriteria(String customCriteria) {
    if (StringUtils.isNotBlank(customCriteria)) {
      this.customCriteria.add(customCriteria);
    }

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterAndSortCriteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest addFilterAndSortCriteria(FilterAndSortCriteria filterAndSortCriteria) {
    this.filterAndSortCriteria.add(filterAndSortCriteria);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterAndSortCriteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest addFilterAndSortCriteria(FilterAndSortCriteria[] filterAndSortCriteria) {
    if (filterAndSortCriteria != null) {
      this.filterAndSortCriteria.addAll(Arrays.asList(filterAndSortCriteria));
    }

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterAndSortCriteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest addFilterAndSortCriteria(List<FilterAndSortCriteria> filterAndSortCriteria) {
    this.filterAndSortCriteria.addAll(filterAndSortCriteria);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ForeignKey[] getAdditionalForeignKeys() {
    ForeignKey[] arr = new ForeignKey[this.additionalForeignKeys.size()];
    arr = this.additionalForeignKeys.toArray(arr);

    return arr;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public AdornedTargetList getAdornedList() {
    return adornedList;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingEntityClassname() {
    return ceilingEntityClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getConfigKey() {
    return configKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************ */
  /* CUSTOM GETTERS / SETTERS */
  /* ************************ */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getCustomCriteria() {
    String[] arr = new String[this.customCriteria.size()];
    arr = this.customCriteria.toArray(arr);

    return arr;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Entity getEntity() {
    return entity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterAndSortCriteria[] getFilterAndSortCriteria() {
    FilterAndSortCriteria[] arr = new FilterAndSortCriteria[this.filterAndSortCriteria.size()];
    arr = this.filterAndSortCriteria.toArray(arr);

    return arr;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************** */
  /* STANDARD GETTERS / SETTERS */
  /* ************************** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ForeignKey getForeignKey() {
    return foreignKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public MapStructure getMapStructure() {
    return mapStructure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getMaxIndex() {
    return maxIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationTypes getOperationTypesOverride() {
    return operationTypesOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getStartIndex() {
    return startIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Type getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  additionalForeignKeys  DOCUMENT ME!
   */
  public void setAdditionalForeignKeys(ForeignKey[] additionalForeignKeys) {
    this.additionalForeignKeys = Arrays.asList(additionalForeignKeys);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  adornedList  DOCUMENT ME!
   */
  public void setAdornedList(AdornedTargetList adornedList) {
    this.adornedList = adornedList;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingEntityClassname  DOCUMENT ME!
   */
  public void setCeilingEntityClassname(String ceilingEntityClassname) {
    this.ceilingEntityClassname = ceilingEntityClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  configKey  DOCUMENT ME!
   */
  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customCriteria  DOCUMENT ME!
   */
  public void setCustomCriteria(String[] customCriteria) {
    this.customCriteria = Arrays.asList(customCriteria);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entity  DOCUMENT ME!
   */
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  filterAndSortCriteria  DOCUMENT ME!
   */
  public void setFilterAndSortCriteria(FilterAndSortCriteria[] filterAndSortCriteria) {
    this.filterAndSortCriteria.addAll(Arrays.asList(filterAndSortCriteria));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  foreignKey  DOCUMENT ME!
   */
  public void setForeignKey(ForeignKey foreignKey) {
    this.foreignKey = foreignKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mapStructure  DOCUMENT ME!
   */
  public void setMapStructure(MapStructure mapStructure) {
    this.mapStructure = mapStructure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  maxIndex  DOCUMENT ME!
   */
  public void setMaxIndex(Integer maxIndex) {
    this.maxIndex = maxIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  operationTypesOverride  DOCUMENT ME!
   */
  public void setOperationTypesOverride(OperationTypes operationTypesOverride) {
    this.operationTypesOverride = operationTypesOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  startIndex  DOCUMENT ME!
   */
  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  public void setType(Type type) {
    this.type = type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   adornedList  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withAdornedList(AdornedTargetList adornedList) {
    setAdornedList(adornedList);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   className  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withCeilingEntityClassname(String className) {
    setCeilingEntityClassname(className);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   configKey  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withConfigKey(String configKey) {
    setConfigKey(configKey);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customCriteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withCustomCriteria(String[] customCriteria) {
    if (ArrayUtils.isNotEmpty(customCriteria)) {
      setCustomCriteria(customCriteria);
    }

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withEntity(Entity entity) {
    setEntity(entity);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterAndSortCriteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withFilterAndSortCriteria(FilterAndSortCriteria[] filterAndSortCriteria) {
    if (ArrayUtils.isNotEmpty(filterAndSortCriteria)) {
      setFilterAndSortCriteria(filterAndSortCriteria);
    }

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   foreignKey  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withForeignKey(ForeignKey foreignKey) {
    setForeignKey(foreignKey);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   mapStructure  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withMapStructure(MapStructure mapStructure) {
    setMapStructure(mapStructure);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   maxIndex  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withMaxIndex(Integer maxIndex) {
    setMaxIndex(maxIndex);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   startIndex  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withStartIndex(Integer startIndex) {
    setStartIndex(startIndex);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************ */
  /* WITH METHODS */
  /* ************ */

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePackageRequest withType(Type type) {
    setType(type);

    return this;
  }

} // end class PersistencePackageRequest
