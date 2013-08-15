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

package org.broadleafcommerce.openadmin.dto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import org.broadleafcommerce.common.util.BLCMapUtils;
import org.broadleafcommerce.common.util.TypedClosure;


/**
 * Generic DTO for a domain object. Each property of the domain object is represented by the 'properties' instance
 * variable which allows for further display metadata to be stored.
 *
 * @author   jfischer
 * @see      {@link org.broadleafcommerce.openadmin.dto.Property}
 * @version  $Revision$, $Date$
 */
public class Entity implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Boolean isActive                   = false;

  /** DOCUMENT ME! */
  protected Boolean isDeleted                  = false;

  /** DOCUMENT ME! */
  protected boolean isDirty                    = false;

  /** DOCUMENT ME! */
  protected Boolean isInactive                 = false;

  /** DOCUMENT ME! */
  protected Boolean isLocked                   = false;

  /** DOCUMENT ME! */
  protected boolean isValidationFailure        = false;

  /** DOCUMENT ME! */
  protected String  lockedBy;

  /** DOCUMENT ME! */
  protected String  lockedDate;

  /** DOCUMENT ME! */
  protected boolean multiPartAvailableOnThread = false;

  /** DOCUMENT ME! */
  protected Map<String, Property> pMap       = null;

  /** DOCUMENT ME! */
  protected Property[]            properties;

  /** DOCUMENT ME! */
  protected String[]                  type;

  /** DOCUMENT ME! */
  protected Map<String, List<String>> validationErrors = new HashMap<String, List<String>>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  property  DOCUMENT ME!
   */
  public void addProperty(Property property) {
    Property[] allProps = getProperties();
    Property[] newProps = new Property[allProps.length + 1];

    for (int j = 0; j < allProps.length; j++) {
      newProps[j] = allProps[j];
    }

    newProps[newProps.length - 1] = property;
    setProperties(newProps);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Adds a single validation error to this entity. This will also set the entire entity in an error state by invoking
   * {@link #setValidationFailure(boolean)}.
   *
   * @param  fieldName        - the field that is in error. This works on top-level properties (like a 'manufacturer'
   *                          field on a Product entity) but can also work on properties gleaned from a related entity
   *                          (like 'defaultSku.weight.weightUnitOfMeasure' on a Product entity)
   * @param  errorOrErrorKey  - the error message to present to a user. Could be the actual error message or a key to a
   *                          property in messages.properties to support different locales
   */
  public void addValidationError(String fieldName, String errorOrErrorKey) {
    Map<String, List<String>> fieldErrors   = getValidationErrors();
    List<String>              errorMessages = fieldErrors.get(fieldName);

    if (errorMessages == null) {
      errorMessages = new ArrayList<String>();
      fieldErrors.put(fieldName, errorMessages);
    }

    errorMessages.add(errorOrErrorKey);
    setValidationFailure(true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Entity)) {
      return false;
    }

    Entity entity = (Entity) o;

    if (isDirty != entity.isDirty) {
      return false;
    }

    if (isValidationFailure != entity.isValidationFailure) {
      return false;
    }

    if (multiPartAvailableOnThread != entity.multiPartAvailableOnThread) {
      return false;
    }

    if ((isActive != null) ? (!isActive.equals(entity.isActive)) : (entity.isActive != null)) {
      return false;
    }

    if ((isDeleted != null) ? (!isDeleted.equals(entity.isDeleted)) : (entity.isDeleted != null)) {
      return false;
    }

    if ((isInactive != null) ? (!isInactive.equals(entity.isInactive)) : (entity.isInactive != null)) {
      return false;
    }

    if ((isLocked != null) ? (!isLocked.equals(entity.isLocked)) : (entity.isLocked != null)) {
      return false;
    }

    if ((lockedBy != null) ? (!lockedBy.equals(entity.lockedBy)) : (entity.lockedBy != null)) {
      return false;
    }

    if ((lockedDate != null) ? (!lockedDate.equals(entity.lockedDate)) : (entity.lockedDate != null)) {
      return false;
    }

    if (!Arrays.equals(properties, entity.properties)) {
      return false;
    }

    if (!Arrays.equals(type, entity.type)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Property findProperty(String name) {
    Arrays.sort(properties, new Comparator<Property>() {
        @Override public int compare(Property o1, Property o2) {
          if ((o1 == null) && (o2 == null)) {
            return 0;
          } else if (o1 == null) {
            return 1;
          } else if (o2 == null) {
            return -1;
          }

          return o1.getName().compareTo(o2.getName());
        }
      });

    Property searchProperty = new Property();
    searchProperty.setName(name);

    int index = Arrays.binarySearch(properties, searchProperty, new Comparator<Property>() {
          @Override public int compare(Property o1, Property o2) {
            if ((o1 == null) && (o2 == null)) {
              return 0;
            } else if (o1 == null) {
              return 1;
            } else if (o2 == null) {
              return -1;
            }

            return o1.getName().compareTo(o2.getName());
          }
        });

    if (index >= 0) {
      return properties[index];
    }

    return null;
  } // end method findProperty

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getActive() {
    return isActive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getDeleted() {
    return isDeleted;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getInactive() {
    return isInactive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getLocked() {
    return isLocked;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getLockedBy() {
    return lockedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getLockedDate() {
    return lockedDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Property> getPMap() {
    if (pMap == null) {
      pMap = BLCMapUtils.keyedMap(properties, new TypedClosure<String, Property>() {
            @Override public String getKey(Property value) {
              return value.getName();
            }
          });
    }

    return pMap;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Property[] getProperties() {
    return properties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Validation error map where the key corresponds to the property that failed validation (which could be
   * dot-separated) and the value corresponds to a list of the error messages, in the case of multiple errors on the
   * same field.
   *
   * <p>For instance, you might have a configuration where the field is both a Required validator and a regex validator.
   * The validation map in this case might contain something like:</p>
   *
   * <p>defaultSku.name => ['This field is required', 'Cannot have numbers in name']</p>
   *
   * @return  a map keyed by property name to the list of error messages for that property
   */
  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (type != null) ? Arrays.hashCode(type) : 0;
    result = (31 * result) + ((properties != null) ? Arrays.hashCode(properties) : 0);
    result = (31 * result) + (isDirty ? 1 : 0);
    result = (31 * result) + ((isDeleted != null) ? isDeleted.hashCode() : 0);
    result = (31 * result) + ((isInactive != null) ? isInactive.hashCode() : 0);
    result = (31 * result) + ((isActive != null) ? isActive.hashCode() : 0);
    result = (31 * result) + ((isLocked != null) ? isLocked.hashCode() : 0);
    result = (31 * result) + ((lockedBy != null) ? lockedBy.hashCode() : 0);
    result = (31 * result) + ((lockedDate != null) ? lockedDate.hashCode() : 0);
    result = (31 * result) + (multiPartAvailableOnThread ? 1 : 0);
    result = (31 * result) + (isValidationFailure ? 1 : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isDirty() {
    return isDirty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isMultiPartAvailableOnThread() {
    return multiPartAvailableOnThread;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isValidationFailure() {
    return isValidationFailure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  prefix  DOCUMENT ME!
   * @param  entity  DOCUMENT ME!
   */
  public void mergeProperties(String prefix, Entity entity) {
    int        j      = 0;
    Property[] merged = new Property[properties.length + entity.getProperties().length];

    for (Property property : properties) {
      merged[j] = property;
      j++;
    }

    for (Property property : entity.getProperties()) {
      property.setName((prefix != null) ? (prefix + "." + property.getName()) : ("" + property.getName()));
      merged[j] = property;
      j++;
    }

    properties = merged;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Replaces all property values in this entity with the values from the given entity. This also resets the
   * {@link #pMap}
   *
   * @param  entity  DOCUMENT ME!
   */
  public void overridePropertyValues(Entity entity) {
    for (Property property : entity.getProperties()) {
      Property myProperty = findProperty(property.getName());

      if (myProperty != null) {
        myProperty.setValue(property.getValue());
        myProperty.setRawValue(property.getRawValue());
      }
    }

    pMap = null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  active  DOCUMENT ME!
   */
  public void setActive(Boolean active) {
    isActive = active;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deleted  DOCUMENT ME!
   */
  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dirty  DOCUMENT ME!
   */
  public void setDirty(boolean dirty) {
    isDirty = dirty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  inactive  DOCUMENT ME!
   */
  public void setInactive(Boolean inactive) {
    isInactive = inactive;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  locked  DOCUMENT ME!
   */
  public void setLocked(Boolean locked) {
    isLocked = locked;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  lockedBy  DOCUMENT ME!
   */
  public void setLockedBy(String lockedBy) {
    this.lockedBy = lockedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  lockedDate  DOCUMENT ME!
   */
  public void setLockedDate(String lockedDate) {
    this.lockedDate = lockedDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  multiPartAvailableOnThread  DOCUMENT ME!
   */
  public void setMultiPartAvailableOnThread(boolean multiPartAvailableOnThread) {
    this.multiPartAvailableOnThread = multiPartAvailableOnThread;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  properties  DOCUMENT ME!
   */
  public void setProperties(Property[] properties) {
    this.properties = properties;
    pMap            = null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  public void setType(String[] type) {
    if ((type != null) && (type.length > 0)) {
      Arrays.sort(type);
    }

    this.type = type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Completely reset the validation errors for this Entity. In most cases it is more appropriate to use the convenience
   * method for adding a single error via {@link #addValidationError(String, String)}. This will also set the entire
   * entity in an error state by invoking {@link #setValidationFailure(boolean)}.
   *
   * @param  validationErrors  DOCUMENT ME!
   *
   * @see    #addValidationError(String, String)
   */
  public void setValidationErrors(Map<String, List<String>> validationErrors) {
    if (MapUtils.isNotEmpty(validationErrors)) {
      setValidationFailure(true);
    }

    this.validationErrors = validationErrors;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  validationFailure  DOCUMENT ME!
   */
  public void setValidationFailure(boolean validationFailure) {
    isValidationFailure = validationFailure;
  }
} // end class Entity
