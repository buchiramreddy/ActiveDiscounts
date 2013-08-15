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

package org.broadleafcommerce.profile.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

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
@AdminPresentationClass(friendlyName = "ChallengeQuestionImpl_baseChallengeQuestion")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CHALLENGE_QUESTION")
public class ChallengeQuestionImpl implements ChallengeQuestion {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "QUESTION_ID")
  @GeneratedValue(generator = "ChallengeQuestionId")
  @GenericGenerator(
    name       = "ChallengeQuestionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ChallengeQuestionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.profile.core.domain.ChallengeQuestionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ChallengeQuestionImpl_Challenge_Question",
    group        = "ChallengeQuestionImpl_Customer"
  )
  @Column(
    name     = "QUESTION",
    nullable = false
  )
  protected String question;

  /**
   * @see  org.broadleafcommerce.profile.core.domain.ChallengeQuestion#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.ChallengeQuestion#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.ChallengeQuestion#getQuestion()
   */
  @Override public String getQuestion() {
    return question;
  }

  /**
   * @see  org.broadleafcommerce.profile.core.domain.ChallengeQuestion#setQuestion(java.lang.String)
   */
  @Override public void setQuestion(String question) {
    this.question = question;
  }

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return question;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((question == null) ? 0 : question.hashCode());

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

    ChallengeQuestionImpl other = (ChallengeQuestionImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (question == null) {
      if (other.question != null) {
        return false;
      }
    } else if (!question.equals(other.question)) {
      return false;
    }

    return true;
  } // end method equals
} // end class ChallengeQuestionImpl
