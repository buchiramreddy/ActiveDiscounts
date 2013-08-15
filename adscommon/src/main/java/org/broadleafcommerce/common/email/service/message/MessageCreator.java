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

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.service.info.EmailInfo;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class MessageCreator {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private JavaMailSender mailSender;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new MessageCreator object.
   *
   * @param  mailSender  DOCUMENT ME!
   */
  public MessageCreator(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   info   DOCUMENT ME!
   * @param   props  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public abstract String buildMessageBody(EmailInfo info, HashMap<String, Object> props);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   props  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public MimeMessagePreparator buildMimeMessagePreparator(final HashMap<String, Object> props) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      @Override public void prepare(MimeMessage mimeMessage) throws Exception {
        EmailTarget       emailUser = (EmailTarget) props.get(EmailPropertyType.USER.getType());
        EmailInfo         info      = (EmailInfo) props.get(EmailPropertyType.INFO.getType());
        MimeMessageHelper message   = new MimeMessageHelper(mimeMessage,
            ((info.getAttachments() != null) && (info.getAttachments().size() > 0)));
        message.setTo(emailUser.getEmailAddress());
        message.setFrom(info.getFromAddress());
        message.setSubject(info.getSubject());

        if ((emailUser.getBCCAddresses() != null) && (emailUser.getBCCAddresses().length > 0)) {
          message.setBcc(emailUser.getBCCAddresses());
        }

        if ((emailUser.getCCAddresses() != null) && (emailUser.getCCAddresses().length > 0)) {
          message.setCc(emailUser.getCCAddresses());
        }

        String messageBody = info.getMessageBody();

        if (messageBody == null) {
          messageBody = buildMessageBody(info, props);
        }

        message.setText(messageBody, true);

        for (Attachment attachment : info.getAttachments()) {
          ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getData(), attachment.getMimeType());
          message.addAttachment(attachment.getFilename(), dataSource);
        }
      } // end method prepare
    };

    return preparator;

  } // end method buildMimeMessagePreparator

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public JavaMailSender getMailSender() {
    return mailSender;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   props  DOCUMENT ME!
   *
   * @throws  MailException  DOCUMENT ME!
   */
  public void sendMessage(final HashMap<String, Object> props) throws MailException {
    MimeMessagePreparator preparator = buildMimeMessagePreparator(props);
    this.mailSender.send(preparator);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mailSender  DOCUMENT ME!
   */
  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }
} // end class MessageCreator
