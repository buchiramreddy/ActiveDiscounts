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

package org.broadleafcommerce.core.search.service.solr;

import org.apache.solr.client.solrj.SolrServer;


/**
 * Provides a class that will statically hold the Solr server.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class SolrContext {
  /** DOCUMENT ME! */
  public static final String PRIMARY = "primary";

  /** DOCUMENT ME! */
  public static final String REINDEX = "reindex";

  /** DOCUMENT ME! */
  protected static SolrServer primaryServer = null;

  /** DOCUMENT ME! */
  protected static SolrServer reindexServer = null;

  /**
   * DOCUMENT ME!
   *
   * @param  server  DOCUMENT ME!
   */
  public static void setPrimaryServer(SolrServer server) {
    primaryServer = server;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  server  DOCUMENT ME!
   */
  public static void setReindexServer(SolrServer server) {
    reindexServer = server;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static SolrServer getServer() {
    return primaryServer;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static SolrServer getReindexServer() {
    return isSingleCoreMode() ? primaryServer : reindexServer;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static boolean isSingleCoreMode() {
    return reindexServer == null;
  }

} // end class SolrContext
