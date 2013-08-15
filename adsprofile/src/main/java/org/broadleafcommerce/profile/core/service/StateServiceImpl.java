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

package org.broadleafcommerce.profile.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.dao.StateDao;
import org.broadleafcommerce.profile.core.domain.State;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blStateService")
public class StateServiceImpl implements StateService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blStateDao")
  protected StateDao stateDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.StateService#findStateByAbbreviation(java.lang.String)
   */
  @Override public State findStateByAbbreviation(String abbreviation) {
    return stateDao.findStateByAbbreviation(abbreviation);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.StateService#findStates()
   */
  @Override public List<State> findStates() {
    return stateDao.findStates();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.StateService#findStates(java.lang.String)
   */
  @Override public List<State> findStates(String countryAbbreviation) {
    return stateDao.findStates(countryAbbreviation);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.StateService#save(org.broadleafcommerce.profile.core.domain.State)
   */
  @Override
  @Transactional("blTransactionManager")
  public State save(State state) {
    return stateDao.save(state);
  }
} // end class StateServiceImpl
