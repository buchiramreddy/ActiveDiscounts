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

package org.broadleafcommerce.core.offer.dao;

import org.broadleafcommerce.core.offer.domain.OfferCode;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OfferCodeDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferCode create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerCodeId  DOCUMENT ME!
   */
  void delete(OfferCode offerCodeId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   code  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferCode readOfferCodeByCode(String code);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerCode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferCode readOfferCodeById(Long offerCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerCode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferCode save(OfferCode offerCode);

} // end interface OfferCodeDao
