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

package org.broadleafcommerce.cms.structure.domain;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;


/**
 * Holds the values for custom fields that are part of a <code>StructuredContent</code> item.<br>
 * Each item maintains a list of its custom fields. The fields associated with an item are determined by the
 * {@link org.broadleafcommerce.cms.field.domain.FieldDefinition}s associated with the
 * {@link org.broadleafcommerce.cms.structure.domain.StructuredContentType}.
 *
 * @see      org.broadleafcommerce.cms.structure.domain.StructuredContentType
 * @see      org.broadleafcommerce.cms.field.domain.FieldDefinition
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface StructuredContentField extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Builds a copy of this item. Used by the content management system when an item is edited.
   *
   * @return  a copy of this item
   */
  @Nonnull StructuredContentField cloneEntity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns audit information for this content item.
   *
   * @return  audit information for this content item.
   */
  @Nullable AdminAuditable getAuditable();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the fieldKey associated with this field. The key used for a <code>StructuredContentField</code> is
   * determined by the associated {@link org.broadleafcommerce.cms.field.domain.FieldDefinition} that was used by the
   * Content Management System to create this instance.
   *
   * <p>As an example, a <code>StructuredContentType</code> might be configured to contain a field definition with a key
   * of "targetUrl".</p>
   *
   * @return  the key associated with this item
   *
   * @see     org.broadleafcommerce.cms.field.domain.FieldDefinition
   */
  @Nonnull String getFieldKey();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the primary key.
   *
   * @return  the primary key
   */
  @Nullable Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the parent <code>StructuredContent</code> item to which this field belongs.
   *
   * @return  the parent <code>StructuredContent</code> item to which this field belongs.
   */
  @Nonnull StructuredContent getStructuredContent();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the value of this custom field.
   *
   * @return  sets the value of this custom field.
   */
  @Nonnull String getValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets audit information for this content item. Default implementations automatically populate this data during
   * persistence.
   *
   * @param  auditable  DOCUMENT ME!
   */
  void setAuditable(@Nullable AdminAuditable auditable);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the fieldKey.
   *
   * @param  fieldKey  DOCUMENT ME!
   *
   * @see    org.broadleafcommerce.cms.field.domain.FieldDefinition
   */
  void setFieldKey(@Nonnull String fieldKey);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the primary key.
   *
   * @param  id  the new primary key
   */
  void setId(@Nullable Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the parent <code>StructuredContent</code> item.
   *
   * @param  structuredContent  DOCUMENT ME!
   */
  void setStructuredContent(@Nonnull StructuredContent structuredContent);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the value for this custom field.
   *
   * @param  value  DOCUMENT ME!
   */
  void setValue(@Nonnull String value);

} // end interface StructuredContentField
