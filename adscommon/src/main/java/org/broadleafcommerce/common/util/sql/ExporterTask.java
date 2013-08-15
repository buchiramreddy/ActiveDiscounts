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

import java.io.File;

import java.util.Properties;

import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.PropertySet;

import org.hibernate.cfg.Configuration;

import org.hibernate.tool.hbm2x.Exporter;


/**
 * This is a re-worked version from Hibernate tools.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public abstract class ExporterTask {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  // refactor out so not dependent on Ant ?
  /** DOCUMENT ME! */
  protected HibernateToolTask parent;

  /** DOCUMENT ME! */
  Configuration               configuration;

  /** DOCUMENT ME! */
  File                        destdir;

  /** DOCUMENT ME! */
  String                      outputFileName = null;

  /** DOCUMENT ME! */
  Properties                  properties;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ExporterTask object.
   *
   * @param  parent  DOCUMENT ME!
   */
  public ExporterTask(HibernateToolTask parent) {
    this.parent     = parent;
    this.properties = new Properties();
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  property  DOCUMENT ME!
   */
  public void addConfiguredProperty(Environment.Variable property) {
    properties.put(property.getKey(), property.getValue());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ps  DOCUMENT ME!
   */
  public void addConfiguredPropertySet(PropertySet ps) {
    properties.putAll(ps.getProperties());
  }

  //~ ------------------------------------------------------------------------------------------------------------------
  /*final*//**
   * DOCUMENT ME!
   */
  public void execute() {
    Exporter exporter = configureExporter(createExporter());
    exporter.start();

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Configuration getConfiguration() {
    return configuration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public File getDestdir() {
    return destdir;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  configuration  DOCUMENT ME!
   */
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  destdir  DOCUMENT ME!
   */
  public void setDestdir(File destdir) {
    this.destdir = destdir;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * File out put name (default: empty)
   *
   * @param  fileName  DOCUMENT ME!
   */
  public void setOutputFileName(String fileName) {
    outputFileName = fileName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected abstract Exporter createExporter();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   exporter  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  abstract Exporter configureExporter(Exporter exporter);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  abstract String getName();
} // end class ExporterTask
