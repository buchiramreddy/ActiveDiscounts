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

package org.broadleafcommerce.common.email.service.jms;

import javax.jms.Destination;

import org.broadleafcommerce.common.email.service.message.EmailServiceProducer;

import org.springframework.jms.core.JmsTemplate;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface JMSEmailServiceProducer extends EmailServiceProducer {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The emailServiceDestination.
   *
   * @return  the emailServiceDestination
   */
  Destination getEmailServiceDestination();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailServiceTemplate.
   *
   * @return  the emailServiceTemplate
   */
  JmsTemplate getEmailServiceTemplate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailServiceDestination  the emailServiceDestination to set
   */
  void setEmailServiceDestination(Destination emailServiceDestination);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailServiceTemplate  the emailServiceTemplate to set
   */
  void setEmailServiceTemplate(JmsTemplate emailServiceTemplate);

} // end interface JMSEmailServiceProducer
