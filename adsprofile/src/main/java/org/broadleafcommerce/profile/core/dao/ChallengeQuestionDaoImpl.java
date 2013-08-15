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

package org.broadleafcommerce.profile.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.profile.core.domain.ChallengeQuestion;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blChallengeQuestionDao")
public class ChallengeQuestionDaoImpl implements ChallengeQuestionDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.ChallengeQuestionDao#readChallengeQuestionById(long)
   */
  @Override public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId) {
    Query query = em.createNamedQuery("BC_READ_CHALLENGE_QUESTION_BY_ID");
    query.setParameter("question_id", challengeQuestionId);

    List<ChallengeQuestion> challengeQuestions = query.getResultList();

    return ((challengeQuestions == null) || challengeQuestions.isEmpty()) ? null : challengeQuestions.get(0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.ChallengeQuestionDao#readChallengeQuestions()
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<ChallengeQuestion> readChallengeQuestions() {
    Query query = em.createNamedQuery("BC_READ_CHALLENGE_QUESTIONS");
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    return query.getResultList();
  }

} // end class ChallengeQuestionDaoImpl
