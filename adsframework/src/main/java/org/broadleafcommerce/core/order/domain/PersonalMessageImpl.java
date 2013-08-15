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

package org.broadleafcommerce.core.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PERSONAL_MESSAGE")
public class PersonalMessageImpl implements PersonalMessage {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PERSONAL_MESSAGE_ID")
  @GeneratedValue(generator = "PersonalMessageId")
  @GenericGenerator(
    name       = "PersonalMessageId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PersonalMessageImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.PersonalMessageImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PersonalMessageImpl_Message_To",
    order        = 1,
    group        = "PersonalMessageImpl_Personal_Message"
  )
  @Column(name = "MESSAGE_TO")
  protected String messageTo;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PersonalMessageImpl_Message_From",
    order        = 2,
    group        = "PersonalMessageImpl_Personal_Message"
  )
  @Column(name = "MESSAGE_FROM")
  protected String messageFrom;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PersonalMessageImpl_Message",
    order        = 3,
    group        = "PersonalMessageImpl_Personal_Message"
  )
  @Column(name = "MESSAGE")
  protected String message;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PersonalMessageImpl_Occasion",
    order        = 4,
    group        = "PersonalMessageImpl_Personal_Message"
  )
  @Column(name = "OCCASION")
  protected String occasion;

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#getMessageTo()
   */
  @Override public String getMessageTo() {
    return messageTo;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#setMessageTo(java.lang.String)
   */
  @Override public void setMessageTo(String messageTo) {
    this.messageTo = messageTo;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#getMessageFrom()
   */
  @Override public String getMessageFrom() {
    return messageFrom;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#setMessageFrom(java.lang.String)
   */
  @Override public void setMessageFrom(String messageFrom) {
    this.messageFrom = messageFrom;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#getMessage()
   */
  @Override public String getMessage() {
    return message;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#setMessage(java.lang.String)
   */
  @Override public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#getOccasion()
   */
  @Override public String getOccasion() {
    return occasion;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.PersonalMessage#setOccasion(java.lang.String)
   */
  @Override public void setOccasion(String occasion) {
    this.occasion = occasion;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((message == null) ? 0 : message.hashCode());
    result = (prime * result) + ((messageFrom == null) ? 0 : messageFrom.hashCode());
    result = (prime * result) + ((messageTo == null) ? 0 : messageTo.hashCode());

    return result;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    PersonalMessageImpl other = (PersonalMessageImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!message.equals(other.message)) {
      return false;
    }

    if (messageFrom == null) {
      if (other.messageFrom != null) {
        return false;
      }
    } else if (!messageFrom.equals(other.messageFrom)) {
      return false;
    }

    if (messageTo == null) {
      if (other.messageTo != null) {
        return false;
      }
    } else if (!messageTo.equals(other.messageTo)) {
      return false;
    }

    return true;
  } // end method equals
} // end class PersonalMessageImpl
