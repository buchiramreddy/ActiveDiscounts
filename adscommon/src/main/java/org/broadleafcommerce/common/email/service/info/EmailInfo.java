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

package org.broadleafcommerce.common.email.service.info;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.common.email.service.message.Attachment;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class EmailInfo implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private List<Attachment> attachments   = new ArrayList<Attachment>();
  private String           emailTemplate;

  private String emailType;
  private String fromAddress;
  private String messageBody;
  private String sendAsyncPriority;

  private String sendEmailReliableAsync;
  private String subject;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public synchronized EmailInfo clone() {
    EmailInfo info = new EmailInfo();
    info.setAttachments(attachments);
    info.setEmailTemplate(emailTemplate);
    info.setEmailType(emailType);
    info.setFromAddress(fromAddress);
    info.setMessageBody(messageBody);
    info.setSendAsyncPriority(sendAsyncPriority);
    info.setSendEmailReliableAsync(sendEmailReliableAsync);
    info.setSubject(subject);

    return info;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Attachment> getAttachments() {
    return attachments;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailTemplate.
   *
   * @return  the emailTemplate
   */
  public String getEmailTemplate() {
    return emailTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailType.
   *
   * @return  the emailType
   */
  public String getEmailType() {
    return emailType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The fromAddress.
   *
   * @return  the fromAddress
   */
  public String getFromAddress() {
    return fromAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMessageBody() {
    return messageBody;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The sendAsyncPriority.
   *
   * @return  the sendAsyncPriority
   */
  public String getSendAsyncPriority() {
    return sendAsyncPriority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The sendEmailReliableAsync.
   *
   * @return  the sendEmailReliableAsync
   */
  public String getSendEmailReliableAsync() {
    return sendEmailReliableAsync;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The subject.
   *
   * @return  the subject
   */
  public String getSubject() {
    return subject;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  attachments  DOCUMENT ME!
   */
  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailTemplate  the emailTemplate to set
   */
  public void setEmailTemplate(String emailTemplate) {
    this.emailTemplate = emailTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailType  the emailType to set
   */
  public void setEmailType(String emailType) {
    this.emailType = emailType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  fromAddress  the fromAddress to set
   */
  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  messageBody  DOCUMENT ME!
   */
  public void setMessageBody(String messageBody) {
    this.messageBody = messageBody;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  sendAsyncPriority  the sendAsyncPriority to set
   */
  public void setSendAsyncPriority(String sendAsyncPriority) {
    this.sendAsyncPriority = sendAsyncPriority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  sendEmailReliableAsync  the sendEmailReliableAsync to set
   */
  public void setSendEmailReliableAsync(String sendEmailReliableAsync) {
    this.sendEmailReliableAsync = sendEmailReliableAsync;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  subject  the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }
} // end class EmailInfo
