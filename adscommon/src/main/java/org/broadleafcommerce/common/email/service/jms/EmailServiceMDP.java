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

import javax.annotation.Resource;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.broadleafcommerce.common.email.service.exception.EmailException;
import org.broadleafcommerce.common.email.service.message.MessageCreator;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class EmailServiceMDP implements MessageListener {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blMessageCreator")
  private MessageCreator messageCreator;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  @Override
  @SuppressWarnings("unchecked")
  public void onMessage(Message message) {
    try {
      HashMap props = (HashMap) ((ObjectMessage) message).getObject();
      messageCreator.sendMessage(props);
    } catch (MailAuthenticationException e) {
      throw new EmailException(e);
    } catch (MailPreparationException e) {
      throw new EmailException(e);
    } catch (MailParseException e) {
      throw new EmailException(e);
    } catch (MailSendException e) {
      /*
       * TODO find the specific exception that results from the smtp
       * server being down, and throw this as an EmailException.
       * Otherwise, log and then swallow this exception, as it may have
       * been possible that this email was actually sent.
       */
      throw new EmailException(e);
    } catch (JMSException e) {
      throw new EmailException(e);
    }
  }

} // end class EmailServiceMDP
