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

package org.broadleafcommerce.common.payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class AccountNumberMask {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private char maskCharacter;

  private List<UnmaskRange> ranges;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AccountNumberMask object.
   *
   * @param  ranges         DOCUMENT ME!
   * @param  maskCharacter  DOCUMENT ME!
   */
  public AccountNumberMask(List<UnmaskRange> ranges, char maskCharacter) {
    this.ranges        = ranges;
    this.maskCharacter = maskCharacter;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  args  DOCUMENT ME!
   */
  public static void main(String[] args) {
    ArrayList<UnmaskRange> ranges = new ArrayList<UnmaskRange>();
    ranges.add(new UnmaskRange(UnmaskRange.BEGINNINGTYPE, 4));
    ranges.add(new UnmaskRange(UnmaskRange.ENDTYPE, 4));

    AccountNumberMask mask = new AccountNumberMask(ranges, 'X');
    System.out.println("Card: " + mask.mask("1111111111111111"));
    System.out.println("Card: " + mask.mask("111111111111111"));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   accountNumber  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public String mask(String accountNumber) {
    if (accountNumber == null) {
      throw new RuntimeException("account number is null");
    }

    char[] characters    = accountNumber.toCharArray();
    char[] newCharacters = new char[characters.length];

    // do mask phase
    Arrays.fill(newCharacters, 0, newCharacters.length, maskCharacter);

    for (UnmaskRange range : ranges) {
      if (range.getPositionType() == UnmaskRange.BEGINNINGTYPE) {
        System.arraycopy(characters, 0, newCharacters, 0, range.getLength());
      } else {
        System.arraycopy(characters, characters.length - range.getLength(), newCharacters,
          newCharacters.length - range.getLength(), range.getLength());
      }
    }

    return new String(newCharacters);
  }
} // end class AccountNumberMask
