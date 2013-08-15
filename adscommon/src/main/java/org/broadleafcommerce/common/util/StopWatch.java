/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.util;

import org.apache.commons.lang.time.DurationFormatUtils;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class StopWatch {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final int STATE_UNSTARTED = 0;
  private static final int STATE_RUNNING   = 1;
  private static final int STATE_STOPPED   = 2;
  private static final int STATE_SUSPENDED = 3;

  private static final int STATE_UNSPLIT = 10;
  private static final int STATE_SPLIT   = 11;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private int runningState = STATE_UNSTARTED;
  private int splitState   = STATE_UNSPLIT;

  private long startTime = -1;
  private long stopTime  = -1;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new StopWatch object.
   */
  public StopWatch() {
    start();
    split();
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public long getLapTime() {
    if (this.splitState != STATE_SPLIT) {
      throw new IllegalStateException("Stopwatch must be split to get the lap time. ");
    }

    return System.currentTimeMillis() - this.stopTime;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public long getSplitTime() {
    if (this.splitState != STATE_SPLIT) {
      throw new IllegalStateException("Stopwatch must be split to get the split time. ");
    }

    return this.stopTime - this.startTime;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public long getStartTime() {
    if (this.runningState == STATE_UNSTARTED) {
      throw new IllegalStateException("Stopwatch has not been started");
    }

    return this.startTime;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public long getTime() {
    if ((this.runningState == STATE_STOPPED) || (this.runningState == STATE_SUSPENDED)) {
      return this.stopTime - this.startTime;
    } else if (this.runningState == STATE_UNSTARTED) {
      return 0;
    } else if (this.runningState == STATE_RUNNING) {
      return System.currentTimeMillis() - this.startTime;
    }

    throw new RuntimeException("Illegal running state has occured. ");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   msg  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public StopWatch printLapString(String msg) {
    System.out.println(msg + ": " + toLapString());

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   msg  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public StopWatch printSplitString(String msg) {
    System.out.println(msg + ": " + toSplitString());

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   msg  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public StopWatch printString(String msg) {
    System.out.println(msg + ": " + toString());

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public StopWatch reset() {
    this.runningState = STATE_UNSTARTED;
    this.splitState   = STATE_UNSPLIT;
    this.startTime    = -1;
    this.stopTime     = -1;

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public StopWatch resume() {
    if (this.runningState != STATE_SUSPENDED) {
      throw new IllegalStateException("Stopwatch must be suspended to resume. ");
    }

    this.startTime    += (System.currentTimeMillis() - this.stopTime);
    this.stopTime     = -1;
    this.runningState = STATE_RUNNING;

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public StopWatch split() {
    if (this.runningState != STATE_RUNNING) {
      throw new IllegalStateException("Stopwatch is not running. ");
    }

    this.stopTime   = System.currentTimeMillis();
    this.splitState = STATE_SPLIT;

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public StopWatch start() {
    if (this.runningState == STATE_STOPPED) {
      throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
    }

    if (this.runningState != STATE_UNSTARTED) {
      throw new IllegalStateException("Stopwatch already started. ");
    }

    this.stopTime     = -1;
    this.startTime    = System.currentTimeMillis();
    this.runningState = STATE_RUNNING;

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public StopWatch stop() {
    if ((this.runningState != STATE_RUNNING) && (this.runningState != STATE_SUSPENDED)) {
      throw new IllegalStateException("Stopwatch is not running. ");
    }

    if (this.runningState == STATE_RUNNING) {
      this.stopTime = System.currentTimeMillis();
    }

    this.runningState = STATE_STOPPED;

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public StopWatch suspend() {
    if (this.runningState != STATE_RUNNING) {
      throw new IllegalStateException("Stopwatch must be running to suspend. ");
    }

    this.stopTime     = System.currentTimeMillis();
    this.runningState = STATE_SUSPENDED;

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String toLapString() {
    return DurationFormatUtils.formatDurationHMS(getLapTime());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String toSplitString() {
    return DurationFormatUtils.formatDurationHMS(getSplitTime());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return DurationFormatUtils.formatDurationHMS(getTime());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalStateException  DOCUMENT ME!
   */
  public StopWatch unsplit() {
    if (this.splitState != STATE_SPLIT) {
      throw new IllegalStateException("Stopwatch has not been split. ");
    }

    this.stopTime   = -1;
    this.splitState = STATE_UNSPLIT;

    return this;
  }

} // end class StopWatch
