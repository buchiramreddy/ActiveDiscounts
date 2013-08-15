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

package org.broadleafcommerce.profile.web.core.service;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.State;

import org.broadleafcommerce.test.CommonSetupBaseTest;

import org.testng.annotations.Test;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class AddressTest extends CommonSetupBaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  List<Long> addressIds = new ArrayList<Long>();

  /** DOCUMENT ME! */
  Long       userId;

  /** DOCUMENT ME! */
  String     userName = new String();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.test.CommonSetupBaseTest#createCountry()
   */
  @Override
  @Test(groups = "createCountry")
  public void createCountry() {
    super.createCountry();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.test.CommonSetupBaseTest#createState()
   */
  @Override
  @Test(groups = "createState")
  public void createState() {
    super.createState();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = "findCountries",
    dependsOnGroups = "createCountry"
  )
  public void findCountries() {
    List<Country> countries = countryService.findCountries();
    assert countries.size() > 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = "findCountryByShortName",
    dependsOnGroups = "createCountry"
  )
  public void findCountryByShortName() {
    Country country = countryService.findCountryByAbbreviation("US");
    assert country != null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = "findStateByAbbreviation",
    dependsOnGroups = "findStates"
  )
  public void findStateByAbbreviation() {
    State state = stateService.findStateByAbbreviation("KY");
    assert state != null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = "findStates",
    dependsOnGroups = "createState"
  )
  public void findStates() {
    List<State> states = stateService.findStates();
    assert states.size() > 0;
  }

} // end class AddressTest
