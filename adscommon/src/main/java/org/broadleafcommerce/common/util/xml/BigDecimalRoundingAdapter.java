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

package org.broadleafcommerce.common.util.xml;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * This class allows us to round Big Decimals to 2 decimal places, generally for marshalling purposes. This is to be
 * used with <code>javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters</code>.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BigDecimalRoundingAdapter extends XmlAdapter<String, BigDecimal> {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.math.BigDecimal)
   */
  @Override public String marshal(BigDecimal bigDecimal) throws Exception {
    return bigDecimal.setScale(2, RoundingMode.UP).toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.String)
   */
  @Override public BigDecimal unmarshal(String s) throws Exception {
    return new BigDecimal(s);
  }
}
