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

package org.broadleafcommerce.common.resource.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;


/**
 * DOCUMENT ME!
 *
 * @see      org.broadleafcommerce.common.resource.service.ResourceMinificationService
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Service("blResourceMinificationService")
public class ResourceMinificationServiceImpl implements ResourceMinificationService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static final Log LOG = LogFactory.getLog(ResourceMinificationServiceImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Value("${minify.disableOptimizations}")
  protected boolean disableOptimizations;

  /** DOCUMENT ME! */
  @Value("${minify.enabled}")
  protected boolean enabled;

  /** DOCUMENT ME! */
  @Value("${minify.linebreak}")
  protected int linebreak;

  /** DOCUMENT ME! */
  @Value("${minify.munge}")
  protected boolean munge;

  /** DOCUMENT ME! */
  @Value("${minify.preserveAllSemiColons}")
  protected boolean preserveAllSemiColons;

  /** DOCUMENT ME! */
  @Value("${minify.verbose}")
  protected boolean verbose;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.resource.service.ResourceMinificationService#minify(java.lang.String, byte[])
   */
  @Override public byte[] minify(String filename, byte[] bytes) {
    if (!enabled) {
      return bytes;
    }

    String type = null;

    if (filename.endsWith(".js")) {
      type = "js";
    } else if (filename.endsWith(".css")) {
      type = "css";
    }

    if (!"js".equals(type) && !"css".equals(type)) {
      throw new IllegalArgumentException("Can only minify js or css resources");
    }

    byte[] minifiedBytes;

    // Input streams to read the bytes
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    InputStreamReader    isr  = new InputStreamReader(bais);
    BufferedReader       in   = new BufferedReader(isr);

    // Output streams to save the modified bytes
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStreamWriter    osw  = new OutputStreamWriter(baos);
    BufferedWriter        out  = new BufferedWriter(osw);

    try {
      if ("js".equals(type)) {
        JavaScriptCompressor jsc = new JavaScriptCompressor(in, getLogBasedErrorReporter());
        jsc.compress(out, linebreak, true, verbose, preserveAllSemiColons, disableOptimizations);
      } else if ("css".equals(type)) {
        CssCompressor cssc = new CssCompressor(in);
        cssc.compress(out, 100);
      }

      out.flush();
      minifiedBytes = baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        in.close();
        out.close();
      } catch (IOException e2) {
        throw new RuntimeException(e2);
      }
    }

    return minifiedBytes;
  } // end method minify

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected ErrorReporter getLogBasedErrorReporter() {
    return new ErrorReporter() {
      @Override public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
        if (line < 0) {
          LOG.warn(message);
        } else {
          LOG.warn(line + ':' + lineOffset + ':' + message);
        }
      }

      @Override public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
        if (line < 0) {
          LOG.error(message);
        } else {
          LOG.error(line + ':' + lineOffset + ':' + message);
        }
      }

      @Override public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
        int lineOffset) {
        error(message, sourceName, line, lineSource, lineOffset);

        return new EvaluatorException(message);
      }

    };
  } // end method getLogBasedErrorReporter

} // end class ResourceMinificationServiceImpl
