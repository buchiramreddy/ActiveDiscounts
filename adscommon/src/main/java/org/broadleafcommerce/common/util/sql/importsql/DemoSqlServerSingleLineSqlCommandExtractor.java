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

package org.broadleafcommerce.common.util.sql.importsql;

import java.io.Reader;

import org.broadleafcommerce.common.logging.SupportLogManager;
import org.broadleafcommerce.common.logging.SupportLogger;

import org.hibernate.tool.hbm2ddl.SingleLineSqlCommandExtractor;


/**
 * This is a utility class that is only meant to be used for testing the BLC demo on SQL Server. In our current import
 * sql files, there are a number of value declarations that are incompatible with Sql Server. This custom extractor
 * takes care of transforming those values into something SQL Server understands.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class DemoSqlServerSingleLineSqlCommandExtractor extends SingleLineSqlCommandExtractor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final SupportLogger LOGGER = SupportLogManager.getLogger("UserOverride",
      DemoSqlServerSingleLineSqlCommandExtractor.class);

  private static final String BOOLEANTRUEMATCH  = "(?i)(true)";
  private static final String BOOLEANFALSEMATCH = "(?i)(false)";
  private static final String TIMESTAMPMATCH    = "(?i)(current_date)";

  /** DOCUMENT ME! */
  public static final String  TRUE              = "'TRUE'";

  /** DOCUMENT ME! */
  public static final String  FALSE             = "'FALSE'";

  /** DOCUMENT ME! */
  public static final String  CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

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
      statements[j] = statements[j].replaceAll(TIMESTAMPMATCH + "\\s*[,]", CURRENT_TIMESTAMP + ",");

      // try middle matches
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANTRUEMATCH + "\\s*[,]", "," + TRUE + ",");
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANFALSEMATCH + "\\s*[,]", "," + FALSE + ",");
      statements[j] = statements[j].replaceAll("[,]\\s*" + TIMESTAMPMATCH + "\\s*[,]", "," + CURRENT_TIMESTAMP + ",");

      // try end matches
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANTRUEMATCH, "," + TRUE);
      statements[j] = statements[j].replaceAll("[,]\\s*" + BOOLEANFALSEMATCH, "," + FALSE);
      statements[j] = statements[j].replaceAll("[,]\\s*" + TIMESTAMPMATCH, "," + CURRENT_TIMESTAMP);
    }

    return statements;
  } // end method extractCommands
} // end class DemoSqlServerSingleLineSqlCommandExtractor
