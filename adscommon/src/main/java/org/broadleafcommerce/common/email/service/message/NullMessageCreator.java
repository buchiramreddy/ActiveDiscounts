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

package org.broadleafcommerce.common.email.service.message;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.email.service.info.EmailInfo;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class NullMessageCreator extends MessageCreator {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(NullMessageCreator.class);

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new NullMessageCreator object.
   *
   * @param  mailSender  DOCUMENT ME!
   */
  public NullMessageCreator(JavaMailSender mailSender) {
    super(mailSender);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.message.MessageCreator#buildMessageBody(org.broadleafcommerce.common.email.service.info.EmailInfo,
   *       java.util.HashMap)
   */
  @Override public String buildMessageBody(EmailInfo info, HashMap<String, Object> props) {
    return info.getEmailTemplate();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.message.MessageCreator#sendMessage(java.util.HashMap)
   */
  @Override public void sendMessage(final HashMap<String, Object> props) throws MailException {
    LOG.warn("NullMessageCreator is defined -- specify a real message creator to send emails");
  }

} // end class NullMessageCreator
