/*
 * Broadleaf Commerce Confidential
 * _______________________________
 *
 * [2009] - [2013] Broadleaf Commerce, LLC
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Broadleaf Commerce, LLC
 * The intellectual and technical concepts contained
 * herein are proprietary to Broadleaf Commerce, LLC
 * and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Broadleaf Commerce, LLC.
 */

package org.broadleafcommerce.common.util.sql.importsql;

import java.io.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.broadleafcommerce.common.logging.SupportLogManager;
import org.broadleafcommerce.common.logging.SupportLogger;

import org.hibernate.tool.hbm2ddl.SingleLineSqlCommandExtractor;


/**
 * This is a utility class that is only meant to be used for testing the BLC demo on Oracle. In our current import sql
 * files, there are a number of value declarations that are incompatible with Oracle. This custom extractor takes care
 * of transforming those values into something Oracle understands.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class DemoOracleSingleLineSqlCommandExtractor extends SingleLineSqlCommandExtractor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final SupportLogger LOGGER = SupportLogManager.getLogger("UserOverride",
      DemoOracleSingleLineSqlCommandExtractor.class);

  private static final String BOOLEANTRUEMATCH  = "(?i)(true)";
  private static final String BOOLEANFALSEMATCH = "(?i)(false)";
  private static final String TIMESTAMPMATCH    = "(?<!\\{ts\\s)('\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}')";

  /** DOCUMENT ME! */
  public static final String  TRUE  = "1";

  /** DOCUMENT ME! */
  public static final String  FALSE = "0";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected boolean alreadyRun = false;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.hibernate.tool.hbm2ddl.SingleLineSqlCommandExtractor#extractCommands(java.io.Reader)
   */
  @Override public String[] extractCommands(Reader reader) {
    if (!alreadyRun) {
      alreadyRun = true;
      LOGGER.support("Converting hibernate.hbm2ddl.import_files sql statements for compatibility with Oracle");
    }

    String[] statements = super.extractCommands(reader);

    for (int j = 0; j < statements.length; j++) {
      // try start matches
      statements[j] = statements[j].replaceAll(BOOLEANTRUEMATCH + "\\s*[,]", TRUE + ",");
      statements[j] = statements[j].replaceAll(BOOLEANFALSEMATCH + "\\s*[,]", FALSE + ",");

      // try middle matches
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANTRUEMATCH + "\\s*[,]", "," + TRUE + ",");
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANFALSEMATCH + "\\s*[,]", "," + FALSE + ",");

      // try end matches
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANTRUEMATCH, "," + TRUE);
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANFALSEMATCH, "," + FALSE);
    }

    // remove Oracle incompatible - multi-row inserts
    List<String> stringList = new ArrayList<String>(Arrays.asList(statements)); // Arrays.asList is immutable
    int          j          = 0;

    for (String statement : statements) {
      if (statement.matches(".*[)]\\s*[,].*")) {
        int    pos    = statement.toUpperCase().indexOf("VALUES ") + "VALUES ".length();
        String prefix = statement.substring(0, pos);
        stringList.remove(j);

        String   values        = statement.substring(pos, statement.length());
        String[] tokens        = values.split("[)]\\s*[,]\\s*[(]");
        String[] newStatements = new String[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
          String suffix = tokens[i];

          if (!suffix.startsWith("(")) {
            suffix = "(" + suffix;
          }

          if (!suffix.endsWith(")")) {
            suffix += ")";
          }

          newStatements[i] = prefix + suffix;
        }

        stringList.addAll(j, Arrays.asList(newStatements));
        j += tokens.length;
      } else {
        j++;
      } // end if-else
    } // end for

    // Address raw string dates, if any, for Oracle
    Pattern pattern = Pattern.compile(TIMESTAMPMATCH);
    statements = stringList.toArray(new String[stringList.size()]);

    for (int x = 0; x < statements.length; x++) {
      Matcher matcher = pattern.matcher(statements[x]);

      while (matcher.find()) {
        String date   = matcher.group(1);
        String temp   = statements[x].substring(0, statements[x].indexOf(date)) + "{ts " + date + "}"
          + statements[x].substring(statements[x].indexOf(date) + date.length(), statements[x].length());
        statements[x] = temp;
      }
    }

    return statements;
  } // end method extractCommands
} // end class DemoOracleSingleLineSqlCommandExtractor
