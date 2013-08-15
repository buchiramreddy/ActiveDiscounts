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

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.broadleafcommerce.cms.structure.domain.StructuredContent;
import org.broadleafcommerce.cms.structure.message.ArchivedStructuredContentPublisher;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * JMS implementation of ArchivedPagePublisher. Intended usage is to notify other VMs that a pageDTO needs to be evicted
 * from cache. This occurs when the page is marked as archived - typically because a replacemet page has been promoted
 * to production.
 *
 * <p>Utilizes Spring JMS template pattern where template and destination are configured via Spring.</p>
 *
 * <p>Created by bpolster.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class JMSArchivedStructuredContentPublisher implements ArchivedStructuredContentPublisher {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Destination archiveStructuredContentDestination;

  private JmsTemplate archiveStructuredContentTemplate;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Destination getArchiveStructuredContentDestination() {
    return archiveStructuredContentDestination;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public JmsTemplate getArchiveStructuredContentTemplate() {
    return archiveStructuredContentTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.message.ArchivedStructuredContentPublisher#processStructuredContentArchive(org.broadleafcommerce.cms.structure.domain.StructuredContent,
   *       java.lang.String, java.lang.String)
   */
  @Override public void processStructuredContentArchive(final StructuredContent sc, final String baseNameKey,
    final String baseTypeKey) {
    archiveStructuredContentTemplate.send(archiveStructuredContentDestination, new MessageCreator() {
        @Override public Message createMessage(Session session) throws JMSException {
          HashMap<String, String> objectMap = new HashMap<String, String>(2);
          objectMap.put("nameKey", baseNameKey);
          objectMap.put("typeKey", baseTypeKey);

          return session.createObjectMessage(objectMap);
        }
      });
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  archiveStructuredContentDestination  DOCUMENT ME!
   */
  public void setArchiveStructuredContentDestination(Destination archiveStructuredContentDestination) {
    this.archiveStructuredContentDestination = archiveStructuredContentDestination;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  archiveStructuredContentTemplate  DOCUMENT ME!
   */
  public void setArchiveStructuredContentTemplate(JmsTemplate archiveStructuredContentTemplate) {
    this.archiveStructuredContentTemplate = archiveStructuredContentTemplate;
  }
} // end class JMSArchivedStructuredContentPublisher
