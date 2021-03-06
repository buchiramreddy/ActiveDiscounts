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

package org.broadleafcommerce.cms.admin.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.cms.admin.web.service.AssetFormBuilderService;
import org.broadleafcommerce.cms.file.domain.StaticAssetImpl;
import org.broadleafcommerce.cms.file.service.StaticAssetService;

import org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import org.broadleafcommerce.openadmin.web.form.component.ListGrid;
import org.broadleafcommerce.openadmin.web.form.entity.EntityFormAction;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Handles admin operations for the {@link Asset} entity. This is mostly to support displaying image assets inline in
 * listgrids.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Controller("blAdminAssetController")
@RequestMapping("/" + AdminAssetController.SECTION_KEY)
public class AdminAssetController extends AdminBasicEntityController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static final String SECTION_KEY = "assets";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAssetFormBuilderService")
  protected AssetFormBuilderService formService;

  /** DOCUMENT ME! */
  @Resource(name = "blStaticAssetService")
  protected StaticAssetService staticAssetService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController#viewEntityForm(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, java.util.Map, java.lang.String)
   */
  @Override
  @RequestMapping(
    value  = "/{id}",
    method = RequestMethod.GET
  )
  public String viewEntityForm(HttpServletRequest request, HttpServletResponse response, Model model,
    @PathVariable Map<String, String> pathVars,
    @PathVariable(value = "id") String id) throws Exception {
    model.addAttribute("cmsUrlPrefix", staticAssetService.getStaticAssetUrlPrefix());

    return super.viewEntityForm(request, response, model, pathVars, id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController#viewEntityList(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, java.util.Map,
   *       org.springframework.util.MultiValueMap)
   */
  @Override
  @RequestMapping(
    value  = "",
    method = RequestMethod.GET
  )
  @SuppressWarnings("unchecked")
  public String viewEntityList(HttpServletRequest request, HttpServletResponse response, Model model,
    @PathVariable Map<String, String> pathVars, @RequestParam MultiValueMap<String, String> requestParams)
    throws Exception {
    String returnPath = super.viewEntityList(request, response, model, pathVars, requestParams);

    // Remove the default add button and replace it with an upload asset button
    List<EntityFormAction>     mainActions = (List<EntityFormAction>) model.asMap().get("mainActions");
    Iterator<EntityFormAction> actions     = mainActions.iterator();

    while (actions.hasNext()) {
      EntityFormAction action = actions.next();

      if (EntityFormAction.ADD.equals(action.getId())) {
        actions.remove();

        break;
      }
    }

    mainActions.add(0, new EntityFormAction("UPLOAD_ASSET").withButtonClass("upload-asset").withIconClass(
        "icon-camera").withDisplayText("Upload_Asset"));

    // Change the listGrid view to one that has a hidden form for uploading the image.
    model.addAttribute("viewType", "entityListWithUploadForm");

    ListGrid listGrid = (ListGrid) model.asMap().get("listGrid");
    formService.addImageThumbnailField(listGrid, "fullUrl");

    return returnPath;
  } // end method viewEntityList

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.AdminAbstractController#getDefaultEntityType()
   */
  @Override protected String getDefaultEntityType() {
    return StaticAssetImpl.class.getName();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.AdminAbstractController#getSectionKey(java.util.Map)
   */
  @Override protected String getSectionKey(Map<String, String> pathVars) {
    // allow external links to work for ToOne items
    if (super.getSectionKey(pathVars) != null) {
      return super.getSectionKey(pathVars);
    }

    return SECTION_KEY;
  }

} // end class AdminAssetController
