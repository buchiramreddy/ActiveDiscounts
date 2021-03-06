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

package org.broadleafcommerce.core.offer;

import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.service.processor.OfferTimeZoneProcessor;
import org.broadleafcommerce.core.offer.service.processor.OfferTimeZoneProcessorImpl;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class TestOfferTimeZoneProcessorImpl implements OfferTimeZoneProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(OfferTimeZoneProcessorImpl.class);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.service.processor.OfferTimeZoneProcessor#getTimeZone(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public TimeZone getTimeZone(Offer offer) {
    return TimeZone.getTimeZone("CST");
  }

}
