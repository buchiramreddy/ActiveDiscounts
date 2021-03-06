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

package org.broadleafcommerce.core.order.domain;

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PersonalMessage extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getMessageTo();

  /**
   * DOCUMENT ME!
   *
   * @param  messageTo  DOCUMENT ME!
   */
  void setMessageTo(String messageTo);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getMessageFrom();

  /**
   * DOCUMENT ME!
   *
   * @param  messageFrom  DOCUMENT ME!
   */
  void setMessageFrom(String messageFrom);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getMessage();

  /**
   * DOCUMENT ME!
   *
   * @param  message  DOCUMENT ME!
   */
  void setMessage(String message);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getOccasion();

  /**
   * DOCUMENT ME!
   *
   * @param  occasion  DOCUMENT ME!
   */
  void setOccasion(String occasion);
} // end interface PersonalMessage
