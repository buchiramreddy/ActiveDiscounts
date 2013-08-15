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

package org.broadleafcommerce.common.security.util;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PasswordChange extends PasswordReset {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String challengeAnswer;

  private String challengeQuestion;

  private String currentPassword;

  private String newPassword;

  private String newPasswordConfirm;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PasswordChange object.
   *
   * @param  username  DOCUMENT ME!
   */
  public PasswordChange(String username) {
    super(username);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getChallengeAnswer() {
    return challengeAnswer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getChallengeQuestion() {
    return challengeQuestion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCurrentPassword() {
    return currentPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getNewPassword() {
    return newPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getNewPasswordConfirm() {
    return newPasswordConfirm;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  challengeAnswer  DOCUMENT ME!
   */
  public void setChallengeAnswer(String challengeAnswer) {
    this.challengeAnswer = challengeAnswer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  challengeQuestion  DOCUMENT ME!
   */
  public void setChallengeQuestion(String challengeQuestion) {
    this.challengeQuestion = challengeQuestion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currentPassword  DOCUMENT ME!
   */
  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  newPassword  DOCUMENT ME!
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  newPasswordConfirm  DOCUMENT ME!
   */
  public void setNewPasswordConfirm(String newPasswordConfirm) {
    this.newPasswordConfirm = newPasswordConfirm;
  }

} // end class PasswordChange
