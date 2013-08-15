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

package org.broadleafcommerce.openadmin.web.controller;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.openadmin.server.service.export.AdminExporter;

import org.springframework.web.servlet.ModelAndView;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public class AdminExportController extends AdminAbstractController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminExporters")
  protected List<AdminExporter> exporters;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   params    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IOException       DOCUMENT ME!
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public ModelAndView export(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
    throws IOException {
    String        exporterName = params.get("exporter");
    AdminExporter exporter     = null;

    for (AdminExporter test : exporters) {
      if (test.getName().equals(exporterName)) {
        exporter = test;
      }
    }

    if (exporter == null) {
      throw new RuntimeException("Could not find exporter with name: " + exporterName);
    }

    response.setContentType("application/download");

    String fileName = exporter.getFileName();
    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

    ServletOutputStream stream = response.getOutputStream();
    exporter.writeExport(stream, params);
    stream.flush();

    return null;
  } // end method export

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<AdminExporter> getExporters() {
    return exporters;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  exporters  DOCUMENT ME!
   */
  public void setExporters(List<AdminExporter> exporters) {
    this.exporters = exporters;
  }

} // end class AdminExportController
