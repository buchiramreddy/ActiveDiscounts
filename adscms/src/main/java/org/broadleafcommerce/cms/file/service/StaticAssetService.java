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

package org.broadleafcommerce.cms.file.service;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.cms.file.domain.StaticAsset;

import org.broadleafcommerce.common.sandbox.domain.SandBox;

import org.broadleafcommerce.openadmin.server.domain.SandBoxItemListener;

import org.hibernate.Criteria;

import org.springframework.web.multipart.MultipartFile;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface StaticAssetService extends SandBoxItemListener {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called from within the CMS admin only.
   *
   * <p>Adds the passed in page to the DB.</p>
   *
   * <p>Creates a sandbox/site if one doesn't already exist.</p>
   *
   * @param   staticAsset         DOCUMENT ME!
   * @param   destinationSandbox  DOCUMENT ME!
   *
   * @return  this method is intended to be called from within the CMS admin only.
   */
  StaticAsset addStaticAsset(StaticAsset staticAsset, SandBox destinationSandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method will take in an assetPath (think image url) and convert it if the value contains the asseturlprefix.
   *
   * @see     org.broadleafcommerce.cms.file.service.StaticAssetService#getStaticAssetUrlPrefix()
   * @see     org.broadleafcommerce.cms.file.service.StaticAssetService#getStaticAssetEnvironmentUrlPrefix()
   *
   * @param   assetPath      - The path to rewrite if it is a cms managed asset
   * @param   contextPath    - The context path of the web application (if applicable)
   * @param   secureRequest  - True if the request is being served over https
   *
   * @return  this method will take in an assetPath (think image url) and convert it if the value contains the
   *          asseturlprefix.
   */
  String convertAssetPath(String assetPath, String contextPath, boolean secureRequest);

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @param   sandbox   DOCUMENT ME!
   * @param   criteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long countAssets(SandBox sandbox, Criteria criteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used when uploading a file to Broadleaf. This method will create the corresponding asset.
   *
   * <p>Depending on the the implementation, the actual asset may be saved to the DB or to the file system. The default
   * implementation {@link org.broadleafcommerce.cms.file.service.StaticAssetServiceImpl} has a environment properties
   * that determine this behavior <code>asset.use.filesystem.storage</code>, and <code>
   * asset.server.file.system.path</code>.</p>
   *
   * <p>The properties allows for implementors to update other Asset properties at the same time they are uploading a
   * file. The default implementation uses this for an optional URL to be specified.</p>
   *
   * @see     org.broadleafcommerce.cms.file.service.StaticAssetServiceImpl
   *
   * @param   file        - the file being uploaded
   * @param   properties  - additional meta-data properties
   *
   * @return  used when uploading a file to Broadleaf.
   *
   * @throws  java.io.IOException
   */
  StaticAsset createStaticAssetFromFile(MultipartFile file, Map<String, String> properties);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If deleting and item where page.originalPageId != null then the item is deleted from the database.
   *
   * <p>If the originalPageId is null, then this method marks the items as deleted within the passed in sandbox.</p>
   *
   * @param  staticAsset         DOCUMENT ME!
   * @param  destinationSandbox  DOCUMENT ME!
   */
  void deleteStaticAsset(StaticAsset staticAsset, SandBox destinationSandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandbox   DOCUMENT ME!
   * @param   criteria  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<StaticAsset> findAssets(SandBox sandbox, Criteria criteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fullUrl        DOCUMENT ME!
   * @param   targetSandBox  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAsset findStaticAssetByFullUrl(String fullUrl, SandBox targetSandBox);

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  StaticAsset findStaticAssetById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If set to true, then this service will not use the SandBox concept and will instead automatically promote images to
   * production as they are entered into the system.
   *
   * <p>This is recommended for the best workflow within the BLC-CMS and has been set as the default behavior.</p>
   *
   * @return  if set to true, then this service will not use the SandBox concept and will instead automatically promote
   *          images to production as they are entered into the system.
   */
  boolean getAutomaticallyApproveAndPromoteStaticAssets();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the secure value of the environment url prefix (e.g. prefixed with https if needed).
   *
   * @return  the secure value of the environment url prefix (e.g.
   */
  String getStaticAssetEnvironmentSecureUrlPrefix();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the value configured for the current environment for the static asset url prefix. If this is different than
   * the common value, then the URLs will get rewritten by the FieldMapWrapper when called from the DisplayContentTag or
   * ProcessURLFilter.
   *
   * @return  the value configured for the current environment for the static asset url prefix.
   */
  String getStaticAssetEnvironmentUrlPrefix();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the value configured to mark an item as a static URL.
   *
   * <p>OOB BLC maintains this value in common.properties.</p>
   *
   * @return  the value configured to mark an item as a static URL.
   */
  String getStaticAssetUrlPrefix();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<StaticAsset> readAllStaticAssets();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If set to true, then this service will not use the SandBox concept and will instead automatically promote images to
   * production as they are entered into the system.
   *
   * <p>This is recommended for the best workflow within the BLC-CMS and has been set as the default behavior.</p>
   *
   * @param  setting  DOCUMENT ME!
   */
  void setAutomaticallyApproveAndPromoteStaticAssets(boolean setting);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the environment url prefix.
   *
   * @param  prefix  DOCUMENT ME!
   */
  void setStaticAssetEnvironmentUrlPrefix(String prefix);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  prefix  DOCUMENT ME!
   */
  void setStaticAssetUrlPrefix(String prefix);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method is intended to be called from within the CMS admin only.
   *
   * <p>Updates the page according to the following rules:</p>
   *
   * <p>1. If sandbox has changed from null to a value This means that the user is editing an item in production and the
   * edit is taking place in a sandbox.</p>
   *
   * <p>Clone the page and add it to the new sandbox and set the cloned page's originalPageId to the id of the page
   * being updated.</p>
   *
   * <p>2. If the sandbox has changed from one value to another This means that the user is moving the item from one
   * sandbox to another.</p>
   *
   * <p>Update the siteId for the page to the one associated with the new sandbox</p>
   *
   * <p>3. If the sandbox has changed from a value to null This means that the item is moving from the sandbox to
   * production.</p>
   *
   * <p>If the page has an originalPageId, then update that page by setting it's archived flag to true.</p>
   *
   * <p>Then, update the siteId of the page being updated to be the siteId of the original page.</p>
   *
   * <p>4. If the sandbox is the same then just update the page.</p>
   *
   * @param   staticAsset  DOCUMENT ME!
   * @param   sandbox      DOCUMENT ME!
   *
   * @return  this method is intended to be called from within the CMS admin only.
   */
  StaticAsset updateStaticAsset(StaticAsset staticAsset, SandBox sandbox);


} // end interface StaticAssetService
