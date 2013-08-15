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
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;

import org.broadleafcommerce.common.email.service.info.EmailInfo;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.ui.velocity.VelocityEngineUtils;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class VelocityMessageCreator extends MessageCreator {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Map<String, Object> additionalConfigItems;

  private VelocityEngine velocityEngine;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new VelocityMessageCreator object.
   *
   * @param  velocityEngine         DOCUMENT ME!
   * @param  mailSender             DOCUMENT ME!
   * @param  additionalConfigItems  DOCUMENT ME!
   */
  public VelocityMessageCreator(VelocityEngine velocityEngine, JavaMailSender mailSender,
    HashMap<String, Object> additionalConfigItems) {
    super(mailSender);
    this.additionalConfigItems = additionalConfigItems;
    this.velocityEngine        = velocityEngine;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.message.MessageCreator#buildMessageBody(org.broadleafcommerce.common.email.service.info.EmailInfo,
   *       java.util.HashMap)
   */
  @Override public String buildMessageBody(EmailInfo info, HashMap<String, Object> props) {
    @SuppressWarnings("unchecked")
    HashMap<String, Object> propsCopy = (HashMap<String, Object>) props.clone();

    if (additionalConfigItems != null) {
      propsCopy.putAll(additionalConfigItems);
    }

    return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, info.getEmailTemplate(), propsCopy);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Object> getAdditionalConfigItems() {
    return additionalConfigItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public VelocityEngine getVelocityEngine() {
    return velocityEngine;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  additionalConfigItems  DOCUMENT ME!
   */
  public void setAdditionalConfigItems(Map<String, Object> additionalConfigItems) {
    this.additionalConfigItems = additionalConfigItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  velocityEngine  DOCUMENT ME!
   */
  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }
} // end class VelocityMessageCreator
