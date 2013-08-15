/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.social.domain;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface UserConnection {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  UserConnectionImpl.UserConnectionPK getUserConnectionPK();

  /**
   * DOCUMENT ME!
   *
   * @param  userConnectionPK  DOCUMENT ME!
   */
  void setUserConnectionPK(UserConnectionImpl.UserConnectionPK userConnectionPK);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer getRank();

  /**
   * DOCUMENT ME!
   *
   * @param  rank  DOCUMENT ME!
   */
  void setRank(Integer rank);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getDisplayName();

  /**
   * DOCUMENT ME!
   *
   * @param  displayName  DOCUMENT ME!
   */
  void setDisplayName(String displayName);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getProfileUrl();

  /**
   * DOCUMENT ME!
   *
   * @param  profileUrl  DOCUMENT ME!
   */
  void setProfileUrl(String profileUrl);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getImageUrl();

  /**
   * DOCUMENT ME!
   *
   * @param  imageUrl  DOCUMENT ME!
   */
  void setImageUrl(String imageUrl);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAccessToken();

  /**
   * DOCUMENT ME!
   *
   * @param  accessToken  DOCUMENT ME!
   */
  void setAccessToken(String accessToken);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getSecret();

  /**
   * DOCUMENT ME!
   *
   * @param  secret  DOCUMENT ME!
   */
  void setSecret(String secret);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getRefreshToken();

  /**
   * DOCUMENT ME!
   *
   * @param  refreshToken  DOCUMENT ME!
   */
  void setRefreshToken(String refreshToken);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getExpireTime();

  /**
   * DOCUMENT ME!
   *
   * @param  expireTime  DOCUMENT ME!
   */
  void setExpireTime(Long expireTime);

} // end interface UserConnection
