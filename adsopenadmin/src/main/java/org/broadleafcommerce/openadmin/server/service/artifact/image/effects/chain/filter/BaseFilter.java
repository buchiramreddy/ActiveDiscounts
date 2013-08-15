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

package org.broadleafcommerce.openadmin.server.service.artifact.image.effects.chain.filter;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;

import java.util.Map;

import org.broadleafcommerce.openadmin.server.service.artifact.OperationBuilder;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class BaseFilter implements BufferedImageOp, OperationBuilder {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected RenderingHints hints;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see java.awt.image.BufferedImageOp#createCompatibleDestImage(java.awt.image.BufferedImage, java.awt.image.ColorModel)
   */
  @Override public BufferedImage createCompatibleDestImage(BufferedImage src,
    ColorModel destCM) {
    BufferedImage image;

    if (destCM == null) {
      destCM = src.getColorModel();

      // Not much support for ICM
      if (destCM instanceof IndexColorModel) {
        destCM = ColorModel.getRGBdefault();
      }
    }

    int w = src.getWidth();
    int h = src.getHeight();
    image = new BufferedImage(destCM,
        destCM.createCompatibleWritableRaster(w, h),
        destCM.isAlphaPremultiplied(), null);

    return image;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   src     DOCUMENT ME!
   * @param   destCM  DOCUMENT ME!
   * @param   width   DOCUMENT ME!
   * @param   height  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM, int width, int height) {
    BufferedImage image;

    if (destCM == null) {
      destCM = src.getColorModel();

      // Not much support for ICM
      if (destCM instanceof IndexColorModel) {
        destCM = ColorModel.getRGBdefault();
      }
    }

    image = new BufferedImage(destCM,
        destCM.createCompatibleWritableRaster(width, height),
        destCM.isAlphaPremultiplied(), null);

    return image;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see java.awt.image.BufferedImageOp#getBounds2D(java.awt.image.BufferedImage)
   */
  @Override public Rectangle2D getBounds2D(BufferedImage src) {
    return src.getRaster().getBounds();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see java.awt.image.BufferedImageOp#getPoint2D(java.awt.geom.Point2D, java.awt.geom.Point2D)
   */
  @Override public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
    if (dstPt == null) {
      dstPt = new Point2D.Float();
    }

    dstPt.setLocation(srcPt.getX(), srcPt.getY());

    return dstPt;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see java.awt.image.BufferedImageOp#getRenderingHints()
   */
  @Override public RenderingHints getRenderingHints() {
    return hints;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   key           DOCUMENT ME!
   * @param   parameterMap  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean containsMyFilterParams(String key, Map<String, String> parameterMap) {
    for (String paramKey : parameterMap.keySet()) {
      if (paramKey.startsWith(key)) {
        return true;
      }
    }

    return false;
  }
} // end class BaseFilter
