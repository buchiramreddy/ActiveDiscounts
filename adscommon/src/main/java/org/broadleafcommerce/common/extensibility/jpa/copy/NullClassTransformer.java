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

package org.broadleafcommerce.common.extensibility.jpa.copy;

import java.lang.instrument.IllegalClassFormatException;

import java.security.ProtectionDomain;

import java.util.Properties;

import org.broadleafcommerce.common.extensibility.jpa.convert.BroadleafClassTransformer;


/**
 * This class transformer will do nothing. The main use case for this transformer is when you would prefer to not have a
 * module's template classes copied over, and would rather do it yourself.
 *
 * <p>This transformer should not typically be used.</p>
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class NullClassTransformer implements BroadleafClassTransformer {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.jpa.convert.BroadleafClassTransformer#compileJPAProperties(java.util.Properties,
   *       java.lang.Object)
   */
  @Override public void compileJPAProperties(Properties props, Object key) throws Exception { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.persistence.spi.ClassTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class,
   *       java.security.ProtectionDomain, byte[])
   */
  @Override public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
    ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    return null;
  }

}
