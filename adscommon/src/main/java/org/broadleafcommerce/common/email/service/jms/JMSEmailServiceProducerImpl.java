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

import java.util.HashMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.EmailPropertyType;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class JMSEmailServiceProducerImpl implements JMSEmailServiceProducer {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Destination emailServiceDestination;

  private JmsTemplate emailServiceTemplate;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The emailServiceDestination.
   *
   * @return  the emailServiceDestination
   */
  @Override public Destination getEmailServiceDestination() {
    return emailServiceDestination;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailServiceTemplate.
   *
   * @return  the emailServiceTemplate
   */
  @Override public JmsTemplate getEmailServiceTemplate() {
    return emailServiceTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.message.EmailServiceProducer#send(java.util.HashMap)
   */
  @Override public void send(@SuppressWarnings("rawtypes") final HashMap props) {
    emailServiceTemplate.send(emailServiceDestination, new MessageCreator() {
        @Override public Message createMessage(Session session) throws JMSException {
          ObjectMessage message = session.createObjectMessage(props);
          EmailInfo     info    = (EmailInfo) props.get(EmailPropertyType.INFO.getType());
          message.setJMSPriority(Integer.parseInt(info.getSendAsyncPriority()));

          return message;
        }
      });
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailServiceDestination  the emailServiceDestination to set
   */
  @Override public void setEmailServiceDestination(Destination emailServiceDestination) {
    this.emailServiceDestination = emailServiceDestination;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailServiceTemplate  the emailServiceTemplate to set
   */
  @Override public void setEmailServiceTemplate(JmsTemplate emailServiceTemplate) {
    this.emailServiceTemplate = emailServiceTemplate;
  }

} // end class JMSEmailServiceProducerImpl
