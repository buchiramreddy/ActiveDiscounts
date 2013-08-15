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

package org.broadleafcommerce.cms.structure.message.jms;

import java.util.HashMap;

import javax.annotation.Resource;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.broadleafcommerce.cms.structure.service.StructuredContentService;


/**
 * Receives JMS message with a String that indicates the cache key to invalidate.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class JMSArchivedStructuredContentSubscriber implements MessageListener {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blStructuredContentService")
  private StructuredContentService structuredContentService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
   */
  @Override
  @SuppressWarnings("unchecked")
  public void onMessage(Message message) {
    String basePageCacheKey = null;

    try {
      HashMap<String, String> props = (HashMap<String, String>) ((ObjectMessage) message).getObject();

      if (props != null) {
        structuredContentService.removeItemFromCache(props.get("nameKey"), props.get("typeKey"));
      }
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }

} // end class JMSArchivedStructuredContentSubscriber
