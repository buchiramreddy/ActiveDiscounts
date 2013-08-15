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

package org.broadleafcommerce.common.util.sql;

import org.hibernate.tool.hbm2x.Exporter;
import org.hibernate.tool.hbm2x.Hbm2DDLExporter;


/**
 * This is a re-worked version from Hibernate tools.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class Hbm2DDLExporterTask extends ExporterTask {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  boolean create    = true;

  /** DOCUMENT ME! */
  String  delimiter = ";";

  /** DOCUMENT ME! */
  boolean drop = false;

  /** DOCUMENT ME! */
  boolean exportToDatabase = true;

  /** DOCUMENT ME! */
  boolean format          = false;

  /** DOCUMENT ME! */
  boolean schemaUpdate    = false;

  /** DOCUMENT ME! */
  boolean scriptToConsole = true;

  private boolean haltOnError = false;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new Hbm2DDLExporterTask object.
   *
   * @param  parent  DOCUMENT ME!
   */
  public Hbm2DDLExporterTask(HibernateToolTask parent) {
    super(parent);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDelimiter() {
    return delimiter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.sql.ExporterTask#getName()
   */
  @Override public String getName() {
    return "hbm2ddl (Generates database schema)";
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Output sql to console ? (default true)
   *
   * @param  console  DOCUMENT ME!
   */
  public void setConsole(boolean console) {
    this.scriptToConsole = console;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  create  DOCUMENT ME!
   */
  public void setCreate(boolean create) {
    this.create = create;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  delimiter  DOCUMENT ME!
   */
  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  drop  DOCUMENT ME!
   */
  public void setDrop(boolean drop) {
    this.drop = drop;
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @param  export  DOCUMENT ME!
   */
  public void setExport(boolean export) {
    exportToDatabase = export;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Format the generated sql.
   *
   * @param  format  DOCUMENT ME!
   */
  public void setFormat(boolean format) {
    this.format = format;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  haltOnError  DOCUMENT ME!
   */
  public void setHaltonerror(boolean haltOnError) {
    this.haltOnError = haltOnError;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Run SchemaUpdate instead of SchemaExport.
   *
   * @param  update  DOCUMENT ME!
   */
  public void setUpdate(boolean update) {
    this.schemaUpdate = update;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.sql.ExporterTask#configureExporter(org.hibernate.tool.hbm2x.Exporter)
   */
  @Override protected Exporter configureExporter(Exporter exp) {
    Hbm2DDLExporter exporter = (Hbm2DDLExporter) exp;
    exporter.setExport(exportToDatabase);
    exporter.setConsole(scriptToConsole);
    exporter.setUpdate(schemaUpdate);
    exporter.setDelimiter(delimiter);
    exporter.setDrop(drop);
    exporter.setCreate(create);
    exporter.setFormat(format);
    exporter.setOutputFileName(outputFileName);
    exporter.setHaltonerror(haltOnError);

    return exporter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.sql.ExporterTask#createExporter()
   */
  @Override protected Exporter createExporter() {
    Hbm2DDLExporter exporter = new Hbm2DDLExporter(getConfiguration(), getDestdir());

    return exporter;
  }

} // end class Hbm2DDLExporterTask
