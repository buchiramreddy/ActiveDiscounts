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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.tools.view.ImportSupport;

import org.broadleafcommerce.cms.common.AbstractContentService;
import org.broadleafcommerce.cms.field.type.StorageType;
import org.broadleafcommerce.cms.file.dao.StaticAssetDao;
import org.broadleafcommerce.cms.file.domain.ImageStaticAsset;
import org.broadleafcommerce.cms.file.domain.ImageStaticAssetImpl;
import org.broadleafcommerce.cms.file.domain.StaticAsset;
import org.broadleafcommerce.cms.file.domain.StaticAssetImpl;

import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxType;

import org.broadleafcommerce.openadmin.server.dao.SandBoxItemDao;
import org.broadleafcommerce.openadmin.server.domain.SandBoxItem;
import org.broadleafcommerce.openadmin.server.domain.SandBoxItemType;
import org.broadleafcommerce.openadmin.server.domain.SandBoxOperationType;
import org.broadleafcommerce.openadmin.server.service.artifact.image.ImageArtifactProcessor;
import org.broadleafcommerce.openadmin.server.service.artifact.image.ImageMetadata;

import org.hibernate.Criteria;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blStaticAssetService")
public class StaticAssetServiceImpl extends AbstractContentService implements StaticAssetService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(StaticAssetServiceImpl.class);

  static {
    MimeUtil.registerMimeDetector(ExtensionMimeDetector.class.getName());
    MimeUtil.registerMimeDetector(MagicMimeMimeDetector.class.getName());
  }

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Value("${automatically.approve.static.assets}")
  protected boolean automaticallyApproveAndPromoteStaticAssets = true;

  /** DOCUMENT ME! */
  @Resource(name = "blImageArtifactProcessor")
  protected ImageArtifactProcessor imageArtifactProcessor;

  /** DOCUMENT ME! */
  @Resource(name = "blSandBoxItemDao")
  protected SandBoxItemDao sandBoxItemDao;

  /** DOCUMENT ME! */
  @Resource(name = "blStaticAssetDao")
  protected StaticAssetDao staticAssetDao;

  /** DOCUMENT ME! */
  @Value("${asset.server.url.prefix.secure}")
  protected String staticAssetEnvironmentSecureUrlPrefix;

  /** DOCUMENT ME! */
  @Value("${asset.server.url.prefix}")
  protected String staticAssetEnvironmentUrlPrefix;

  /** DOCUMENT ME! */
  @Resource(name = "blStaticAssetStorageService")
  protected StaticAssetStorageService staticAssetStorageService;

  /** DOCUMENT ME! */
  @Value("${asset.server.url.prefix.internal}")
  protected String staticAssetUrlPrefix;

  /** DOCUMENT ME! */
  @Value("${asset.use.filesystem.storage}")
  protected boolean    storeAssetsOnFileSystem = false;
  private final String FILE_NAME_CHARS         = "0123456789abcdef";

  private final Random random = new Random();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#addStaticAsset(org.broadleafcommerce.cms.file.domain.StaticAsset,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public StaticAsset addStaticAsset(StaticAsset staticAsset, SandBox destinationSandbox) {
    if (automaticallyApproveAndPromoteStaticAssets) {
      if ((destinationSandbox != null) && (destinationSandbox.getSite() != null)) {
        destinationSandbox = destinationSandbox.getSite().getProductionSandbox();
      } else {
        // Null means production for single-site installations.
        destinationSandbox = null;
      }
    }

    staticAsset.setSandbox(destinationSandbox);
    staticAsset.setDeletedFlag(false);
    staticAsset.setArchivedFlag(false);

    StaticAsset newAsset = staticAssetDao.addOrUpdateStaticAsset(staticAsset, true);

    if (!isProductionSandBox(destinationSandbox)) {
      sandBoxItemDao.addSandBoxItem(destinationSandbox.getId(), SandBoxOperationType.ADD, SandBoxItemType.STATIC_ASSET,
        newAsset.getFullUrl(), newAsset.getId(), null);
    }

    return newAsset;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method will take in an assetPath (think image url) and prepend the staticAssetUrlPrefix if one exists.
   *
   * <p>Will append any contextPath onto the request. If the incoming assetPath contains the internalStaticAssetPrefix
   * and the image is being prepended, the prepend will be removed.</p>
   *
   * @param   assetPath      - The path to rewrite if it is a cms managed asset
   * @param   contextPath    - The context path of the web application (if applicable)
   * @param   secureRequest  - True if the request is being served over https
   *
   * @return  this method will take in an assetPath (think image url) and prepend the staticAssetUrlPrefix if one
   *          exists.
   *
   * @see     StaticAssetService#getStaticAssetUrlPrefix()
   * @see     StaticAssetService#getStaticAssetEnvironmentUrlPrefix()
   */
  @Override public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest) {
    String returnValue = assetPath;

    if ((assetPath != null) && (getStaticAssetEnvironmentUrlPrefix() != null)
          && !"".equals(getStaticAssetEnvironmentUrlPrefix())) {
      final String envPrefix;

      if (secureRequest) {
        envPrefix = getStaticAssetEnvironmentSecureUrlPrefix();
      } else {
        envPrefix = getStaticAssetEnvironmentUrlPrefix();
      }

      if (envPrefix != null) {
        // remove the starting "/" if it exists.
        if (returnValue.startsWith("/")) {
          returnValue = returnValue.substring(1);
        }

        // Also, remove the "cmsstatic" from the URL before prepending the staticAssetUrlPrefix.
        if (returnValue.startsWith(getStaticAssetUrlPrefix())) {
          returnValue = returnValue.substring(getStaticAssetUrlPrefix().trim().length());

          // remove the starting "/" if it exists.
          if (returnValue.startsWith("/")) {
            returnValue = returnValue.substring(1);
          }
        }

        returnValue = envPrefix + returnValue;
      }
    } else {
      if ((returnValue != null) && !ImportSupport.isAbsoluteUrl(returnValue)) {
        if (!returnValue.startsWith("/")) {
          returnValue = "/" + returnValue;
        }

        // Add context path
        if ((contextPath != null) && !contextPath.equals("")) {
          if (!contextPath.equals("/")) {
            // Shouldn't be the case, but let's handle it anyway
            if (contextPath.endsWith("/")) {
              returnValue = returnValue.substring(1);
            }

            if (contextPath.startsWith("/")) {
              returnValue = contextPath + returnValue; // normal case
            } else {
              returnValue = "/" + contextPath + returnValue;
            }
          }
        }
      }
    } // end if-else

    return returnValue;
  } // end method convertAssetPath

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#countAssets(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       org.hibernate.Criteria)
   */
  @Override public Long countAssets(SandBox sandbox, Criteria c) {
    return countItems(sandbox, c, StaticAssetImpl.class, "originalAssetId");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#createStaticAssetFromFile(org.springframework.web.multipart.MultipartFile,
   *       java.util.Map)
   */
  @Override
  @Transactional("blTransactionManager")
  public StaticAsset createStaticAssetFromFile(MultipartFile file, Map<String, String> properties) {
    if (properties == null) {
      properties = new HashMap<String, String>();
    }

    String      fullUrl  = buildAssetURL(properties, file.getOriginalFilename());
    StaticAsset newAsset = staticAssetDao.readStaticAssetByFullUrl(fullUrl, null);
    int         count    = 0;

    while (newAsset != null) {
      count++;
      newAsset = staticAssetDao.readStaticAssetByFullUrl(fullUrl + "-" + count, null);
    }

    if (count > 0) {
      fullUrl = fullUrl + "-" + count;
    }

    try {
      ImageMetadata metadata = imageArtifactProcessor.getImageMetadata(file.getInputStream());
      newAsset = new ImageStaticAssetImpl();
      ((ImageStaticAsset) newAsset).setWidth(metadata.getWidth());
      ((ImageStaticAsset) newAsset).setHeight(metadata.getHeight());
    } catch (Exception e) {
      // must not be an image stream
      newAsset = new StaticAssetImpl();
    }

    if (storeAssetsOnFileSystem) {
      newAsset.setStorageType(StorageType.FILESYSTEM);
    } else {
      newAsset.setStorageType(StorageType.DATABASE);
    }

    newAsset.setName(file.getOriginalFilename());
    getMimeType(file, newAsset);
    newAsset.setFileExtension(getFileExtension(file.getOriginalFilename()));
    newAsset.setFileSize(file.getSize());
    newAsset.setFullUrl(fullUrl);

    return staticAssetDao.addOrUpdateStaticAsset(newAsset, false);
  } // end method createStaticAssetFromFile

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#deleteStaticAsset(org.broadleafcommerce.cms.file.domain.StaticAsset,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void deleteStaticAsset(StaticAsset staticAsset, SandBox destinationSandbox) {
    staticAsset.setDeletedFlag(true);
    updateStaticAsset(staticAsset, destinationSandbox);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#findAssets(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       org.hibernate.Criteria)
   */
  @Override public List<StaticAsset> findAssets(SandBox sandbox, Criteria c) {
    return findItems(sandbox, c, StaticAsset.class, StaticAssetImpl.class, "originalAssetId");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#findStaticAssetByFullUrl(java.lang.String, org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public StaticAsset findStaticAssetByFullUrl(String fullUrl, SandBox targetSandBox) {
    try {
      fullUrl = URLDecoder.decode(fullUrl, "UTF-8");

      // strip out the jsessionid if it's there
      fullUrl = fullUrl.replaceAll(";jsessionid=.*?(?=\\?|$)", "");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Unsupported encoding to decode fullUrl", e);
    }

    return staticAssetDao.readStaticAssetByFullUrl(fullUrl, targetSandBox);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#findStaticAssetById(java.lang.Long)
   */
  @Override public StaticAsset findStaticAssetById(Long id) {
    return staticAssetDao.readStaticAssetById(id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#getAutomaticallyApproveAndPromoteStaticAssets()
   */
  @Override public boolean getAutomaticallyApproveAndPromoteStaticAssets() {
    return automaticallyApproveAndPromoteStaticAssets;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#getStaticAssetEnvironmentSecureUrlPrefix()
   */
  @Override public String getStaticAssetEnvironmentSecureUrlPrefix() {
    if (StringUtils.isEmpty(staticAssetEnvironmentSecureUrlPrefix)) {
      if (!StringUtils.isEmpty(staticAssetEnvironmentUrlPrefix)
            && (staticAssetEnvironmentUrlPrefix.indexOf("http:") >= 0)) {
        staticAssetEnvironmentSecureUrlPrefix = staticAssetEnvironmentUrlPrefix.replace("http:", "https:");
      }
    }

    return fixEnvironmentUrlPrefix(staticAssetEnvironmentSecureUrlPrefix);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#getStaticAssetEnvironmentUrlPrefix()
   */
  @Override public String getStaticAssetEnvironmentUrlPrefix() {
    return fixEnvironmentUrlPrefix(staticAssetEnvironmentUrlPrefix);
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#getStaticAssetUrlPrefix()
   */
  @Override public String getStaticAssetUrlPrefix() {
    return staticAssetUrlPrefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItemListener#itemPromoted(org.broadleafcommerce.openadmin.server.domain.SandBoxItem,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void itemPromoted(SandBoxItem sandBoxItem, SandBox destinationSandBox) {
    if (!SandBoxItemType.STATIC_ASSET.equals(sandBoxItem.getSandBoxItemType())) {
      return;
    }

    StaticAsset asset = staticAssetDao.readStaticAssetById(sandBoxItem.getTemporaryItemId());

    if (asset == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Asset not found " + sandBoxItem.getTemporaryItemId());
      }
    } else {
      boolean productionSandBox = isProductionSandBox(destinationSandBox);

      if (productionSandBox) {
        asset.setLockedFlag(false);
      } else {
        asset.setLockedFlag(true);
      }

      if (productionSandBox && (asset.getOriginalAssetId() != null)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Asset promoted to production.  " + asset.getId() + ".  Archiving original asset "
            + asset.getOriginalAssetId());
        }

        StaticAsset originalAsset = staticAssetDao.readStaticAssetById(sandBoxItem.getTemporaryItemId());
        originalAsset.setArchivedFlag(Boolean.TRUE);
        staticAssetDao.addOrUpdateStaticAsset(originalAsset, false);
        asset.setOriginalAssetId(null);

        if (asset.getDeletedFlag()) {
          asset.setArchivedFlag(Boolean.TRUE);
        }
      }
    } // end if-else

    if (asset.getOriginalSandBox() == null) {
      asset.setOriginalSandBox(asset.getSandbox());
    }

    asset.setSandbox(destinationSandBox);
    staticAssetDao.addOrUpdateStaticAsset(asset, false);
  } // end method itemPromoted

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItemListener#itemRejected(org.broadleafcommerce.openadmin.server.domain.SandBoxItem,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void itemRejected(SandBoxItem sandBoxItem, SandBox destinationSandBox) {
    if (!SandBoxItemType.STATIC_ASSET.equals(sandBoxItem.getSandBoxItemType())) {
      return;
    }

    StaticAsset asset = staticAssetDao.readStaticAssetById(sandBoxItem.getTemporaryItemId());

    if (asset != null) {
      asset.setSandbox(destinationSandBox);
      asset.setOriginalSandBox(null);
      asset.setLockedFlag(false);
      staticAssetDao.addOrUpdateStaticAsset(asset, false);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItemListener#itemReverted(org.broadleafcommerce.openadmin.server.domain.SandBoxItem)
   */
  @Override public void itemReverted(SandBoxItem sandBoxItem) {
    if (!SandBoxItemType.STATIC_ASSET.equals(sandBoxItem.getSandBoxItemType())) {
      return;
    }

    StaticAsset asset = staticAssetDao.readStaticAssetById(sandBoxItem.getTemporaryItemId());

    if (asset != null) {
      asset.setArchivedFlag(Boolean.TRUE);
      asset.setLockedFlag(false);
      staticAssetDao.addOrUpdateStaticAsset(asset, false);

      StaticAsset originalAsset = staticAssetDao.readStaticAssetById(sandBoxItem.getOriginalItemId());
      originalAsset.setLockedFlag(false);
      staticAssetDao.addOrUpdateStaticAsset(originalAsset, false);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#readAllStaticAssets()
   */
  @Override public List<StaticAsset> readAllStaticAssets() {
    return staticAssetDao.readAllStaticAssets();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#setAutomaticallyApproveAndPromoteStaticAssets(boolean)
   */
  @Override public void setAutomaticallyApproveAndPromoteStaticAssets(
    boolean automaticallyApproveAndPromoteStaticAssets) {
    this.automaticallyApproveAndPromoteStaticAssets = automaticallyApproveAndPromoteStaticAssets;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  staticAssetEnvironmentSecureUrlPrefix  DOCUMENT ME!
   */
  public void setStaticAssetEnvironmentSecureUrlPrefix(String staticAssetEnvironmentSecureUrlPrefix) {
    this.staticAssetEnvironmentSecureUrlPrefix = staticAssetEnvironmentSecureUrlPrefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#setStaticAssetEnvironmentUrlPrefix(java.lang.String)
   */
  @Override public void setStaticAssetEnvironmentUrlPrefix(String staticAssetEnvironmentUrlPrefix) {
    this.staticAssetEnvironmentUrlPrefix = staticAssetEnvironmentUrlPrefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#setStaticAssetUrlPrefix(java.lang.String)
   */
  @Override public void setStaticAssetUrlPrefix(String staticAssetUrlPrefix) {
    this.staticAssetUrlPrefix = staticAssetUrlPrefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.service.StaticAssetService#updateStaticAsset(org.broadleafcommerce.cms.file.domain.StaticAsset,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public StaticAsset updateStaticAsset(StaticAsset staticAsset, SandBox destSandbox) {
    if (staticAsset.getLockedFlag()) {
      throw new IllegalArgumentException("Unable to update a locked record");
    }

    if (automaticallyApproveAndPromoteStaticAssets) {
      if ((destSandbox != null) && (destSandbox.getSite() != null)) {
        destSandbox = destSandbox.getSite().getProductionSandbox();
      } else {
        // Null means production for single-site installations.
        destSandbox = null;
      }
    }

    if (checkForSandboxMatch(staticAsset.getSandbox(), destSandbox)) {
      if (staticAsset.getDeletedFlag()) {
        SandBoxItem item = sandBoxItemDao.retrieveBySandboxAndTemporaryItemId((staticAsset.getSandbox() == null)
              ? null : staticAsset.getSandbox().getId(), SandBoxItemType.STATIC_ASSET, staticAsset.getId());

        if ((staticAsset.getOriginalAssetId() == null) && (item != null)) {
          // This item was added in this sandbox and now needs to be deleted.
          staticAsset.setArchivedFlag(true);
          item.setArchivedFlag(true);
        } else if (item != null) {
          // This item was being updated but now is being deleted - so change the
          // sandbox operation type to deleted
          item.setSandBoxOperationType(SandBoxOperationType.DELETE);
          sandBoxItemDao.updateSandBoxItem(item);
        } else if (automaticallyApproveAndPromoteStaticAssets) {
          staticAsset.setArchivedFlag(true);
        }
      }

      return staticAssetDao.addOrUpdateStaticAsset(staticAsset, true);
    } else if (isProductionSandBox(staticAsset.getSandbox())) {
      // Move from production to destSandbox
      StaticAsset clonedAsset = staticAsset.cloneEntity();
      clonedAsset.setOriginalAssetId(staticAsset.getId());
      clonedAsset.setSandbox(destSandbox);

      StaticAsset returnAsset = staticAssetDao.addOrUpdateStaticAsset(clonedAsset, true);

      StaticAsset prod = findStaticAssetById(staticAsset.getId());
      prod.setLockedFlag(true);
      staticAssetDao.addOrUpdateStaticAsset(prod, false);

      SandBoxOperationType type = SandBoxOperationType.UPDATE;

      if (clonedAsset.getDeletedFlag()) {
        type = SandBoxOperationType.DELETE;
      }

      sandBoxItemDao.addSandBoxItem(destSandbox.getId(), type, SandBoxItemType.STATIC_ASSET, returnAsset.getFullUrl(),
        returnAsset.getId(), returnAsset.getOriginalAssetId());

      return returnAsset;
    } else {
      // This should happen via a promote, revert, or reject in the sandbox service
      throw new IllegalArgumentException("Update called when promote or reject was expected.");
    } // end if-else
  } // end method updateStaticAsset

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Will assemble the url from the passed in properties as /{entityType}/{fileName} /product/7001-ab12.
   *
   * <p>If the properties above are not set, it will generate the fileName randomly.</p>
   *
   * @param   assetProperties   DOCUMENT ME!
   * @param   originalFilename  url
   *
   * @return  will assemble the url from the passed in properties as /{entityType}/{fileName} /product/7001-ab12.
   */
  protected String buildAssetURL(Map<String, String> assetProperties, String originalFilename) {
    StringBuilder path = new StringBuilder("/");

    String entityType = assetProperties.get("entityType");
    String entityId   = assetProperties.get("entityId");
    String fileName   = assetProperties.get("fileName");

    if (entityType != null) {
      path = path.append(entityType).append("/");
    }

    if (entityId != null) {
      path = path.append(entityId).append("/");
    }

    if (fileName != null) {
      int pos = fileName.indexOf(":");

      if (pos > 0) {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Removing protocol from URL name" + fileName);
        }

        fileName = fileName.substring(pos + 1);
      }
    } else {
      fileName = originalFilename;
    }

    return path.append(fileName).toString();
  } // end method buildAssetURL

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Generates a filename as a set of Hex digits.
   *
   * @param   size  DOCUMENT ME!
   *
   * @return  generates a filename as a set of Hex digits.
   */
  protected String generateFileName(int size) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < size; i++) {
      int pos = random.nextInt(FILE_NAME_CHARS.length());
      sb = sb.append(FILE_NAME_CHARS.charAt(pos));
    }

    return sb.toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fileName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String getFileExtension(String fileName) {
    int pos = fileName.lastIndexOf(".");

    if (pos > 0) {
      return fileName.substring(pos + 1, fileName.length()).toLowerCase();
    } else {
      LOG.warn("No extension provided for asset : " + fileName);

      return null;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   file      DOCUMENT ME!
   * @param   newAsset  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  protected void getMimeType(MultipartFile file, StaticAsset newAsset) {
    Collection mimeTypes = MimeUtil.getMimeTypes(file.getOriginalFilename());

    if (!mimeTypes.isEmpty()) {
      MimeType mimeType = (MimeType) mimeTypes.iterator().next();
      newAsset.setMimeType(mimeType.toString());
    } else {
      try {
        mimeTypes = MimeUtil.getMimeTypes(file.getInputStream());
      } catch (IOException ioe) {
        throw new RuntimeException(ioe);
      }

      if (!mimeTypes.isEmpty()) {
        MimeType mimeType = (MimeType) mimeTypes.iterator().next();
        newAsset.setMimeType(mimeType.toString());
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  // Returns true if the src and dest sandbox are the same.
  private boolean checkForSandboxMatch(SandBox src, SandBox dest) {
    if (src != null) {
      if (dest != null) {
        return src.getId().equals(dest.getId());
      }
    }

    return ((src == null) && (dest == null));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Trims whitespace. If the value is the same as the internal url prefix, then return null.
   *
   * @param   urlPrefix  DOCUMENT ME!
   *
   * @return  trims whitespace.
   */
  private String fixEnvironmentUrlPrefix(String urlPrefix) {
    if (urlPrefix != null) {
      urlPrefix = urlPrefix.trim();

      if ("".equals(urlPrefix)) {
        // The value was not set.
        urlPrefix = null;
      } else if (urlPrefix.equals(staticAssetUrlPrefix)) {
        // The value is the same as the default, so no processing needed.
        urlPrefix = null;
      }
    }

    if ((urlPrefix != null) && !urlPrefix.endsWith("/")) {
      urlPrefix = urlPrefix + "/";
    }

    return urlPrefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

// // Returns true if the dest sandbox is production.
// private boolean checkForProductionSandbox(SandBox dest) {
// boolean productionSandbox = false;
//
// if (dest == null) {
// productionSandbox = true;
// } else {
// if (dest.getSite() != null && dest.getSite().getProductionSandbox() != null && dest.getSite().getProductionSandbox().getId() != null) {
// productionSandbox = dest.getSite().getProductionSandbox().getId().equals(dest.getId());
// }
// }
//
// return productionSandbox;
// }

  // Returns true if the dest sandbox is production.
  private boolean isProductionSandBox(SandBox dest) {
    return (dest == null) || SandBoxType.PRODUCTION.equals(dest.getSandBoxType());
  }
} // end class StaticAssetServiceImpl
