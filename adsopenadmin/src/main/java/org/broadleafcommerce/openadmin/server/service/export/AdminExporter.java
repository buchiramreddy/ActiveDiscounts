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

package org.broadleafcommerce.openadmin.server.service.export;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.broadleafcommerce.openadmin.dto.Property;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public interface AdminExporter {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * <p>The list of field names that are necessary for this exporter to function properly. For instance, an Order
   * exporter might request a start date and end date for criteria. While this returns a list of Property, only a subset
   * of the available fields on Property are used to build the form. Methods that should not return null:</p>
   *
   * <ul>
   *   <li>property.getName()</li>
   *   <li>property.getMetadata().getPresentationAttributes().getFriendlyName()</li>
   *   <li>property.getMetaData().getFieldType()</li>
   * </ul>
   *
   * <p>These methods can be null, but can contain values to further control display:</p>
   *
   * <ul>
   *   <li>property.getMetadata().getLength()</li>
   *   <li>property.getMetadata().getPresentationAttributes().isLargeEntry()</li>
   *   <li>property.getMetadata().getPresentationAttributes().getTooltip()</li>
   *   <li>property.getMetadata().getPresentationAttributes().getRequiredOverride()</li>
   * </ul>
   *
   * <p>For instance, this would be an example of creating start date and end date criteria fields:</p>
   *
   * <pre>
     {@code
   *  List<Property> criteria = new ArrayList<Property>();
   *  Property startDate = new Property();
   *  startDate.setName("startDate");
   *  startDate.getMetadata().getPresentationAttributes().setFriendlyName("Start Date");
   *  startDate.getMetadata().setFieldType(SupportedFieldType.DATE);
   *  criteria.add(startDate);
   *   
   *  Property endDate = new Property();
   *  endDate.setName("endDate");
   *  endDate.getMetadata().getPresentationAttributes().setFriendlyName("End Date");
   *  endDate.getMetadata().setFieldType(SupportedFieldType.DATE);
   *  criteria.add(endDate);
   *  return criteria;
   * }
   * </pre>
   *
   * @return  <b>null</b> if no additional criteria is needed
   */
  List<Property> getCriteriaFields();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The file name used in the Content-Disposition header for "attachment"
   *
   * @return  the file name used in the Content-Disposition header for "attachment"
   */
  String getFileName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The friendly name of this exporter to display to the user to select from.
   *
   * @return  the friendly name of this exporter to display to the user to select from.
   */
  String getFriendlyName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The name of this exporter. Should be unique
   *
   * @return  the name of this exporter.
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The type of this exporter.
   *
   * @return  the type of this exporter.
   */
  String getType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   out     DOCUMENT ME!
   * @param   params  DOCUMENT ME!
   *
   * @throws  IOException  DOCUMENT ME!
   */
  void writeExport(ServletOutputStream out, Map<String, String> params) throws IOException;

} // end interface AdminExporter
