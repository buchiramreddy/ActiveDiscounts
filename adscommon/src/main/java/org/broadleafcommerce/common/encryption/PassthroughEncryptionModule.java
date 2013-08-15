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

package org.broadleafcommerce.common.encryption;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.broadleafcommerce.common.config.RuntimeEnvironmentKeyResolver;
import org.broadleafcommerce.common.config.SystemPropertyRuntimeEnvironmentKeyResolver;


/**
 * The default encryption module simply passes through the decrypt and encrypt text. A real implementation should adhere
 * to PCI compliance, which requires robust key management, including regular key rotation. An excellent solution would
 * be a module for interacting with the StrongKey solution. Refer to this discussion:
 *
 * <p>http://www.strongauth.com/forum/index.php?topic=44.0</p>
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PassthroughEncryptionModule implements EncryptionModule {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static final Logger LOG = LogManager.getLogger(PassthroughEncryptionModule.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected RuntimeEnvironmentKeyResolver keyResolver = new SystemPropertyRuntimeEnvironmentKeyResolver();

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PassthroughEncryptionModule object.
   */
  public PassthroughEncryptionModule() {
    if ("production".equals(keyResolver.resolveRuntimeEnvironmentKey())) {
      LOG.warn("This passthrough encryption module provides NO ENCRYPTION and should NOT be used in production.");
    }
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.encryption.EncryptionModule#decrypt(java.lang.String)
   */
  @Override public String decrypt(String cipherText) {
    return cipherText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.encryption.EncryptionModule#encrypt(java.lang.String)
   */
  @Override public String encrypt(String plainText) {
    return plainText;
  }

} // end class PassthroughEncryptionModule
