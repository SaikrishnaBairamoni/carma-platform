/*
 * TODO: Copyright (C) 2017 LEIDOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package gov.dot.fhwa.saxton.carma.rosutils;

import org.apache.commons.logging.Log;
import org.ros.RosCore;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Extending the ROS Logger functionality for Carma purposes.
 */
public class SaxtonLogger {

  private Log saxtonLog;
  private String source = "NO SOURCE SET";
  private File file = null;
  private String fileName;
  private String emptyTag = "NO TAG";

  /***
   * Get source name which is usually the className.
   * @param sourceName
   */
  public void setSource(String sourceName) {
    source = sourceName;
  }

  /***
   * Get the source name.
   * @return
   */
  public String getSource() {
    return source;
  }

  /***
   * Initialize the logger.
   * @param className
   * @param connectedNodeLog
   */
  public SaxtonLogger(String className, Log connectedNodeLog) {
    this.saxtonLog = connectedNodeLog;
    this.source = className;

    try {

      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      fileName = LocalDateTime.now().format(dateFormatter) + ".txt";
      file = new File("/tmp/carmalogs/" + fileName); //TODO: Will see later if needed to be stored in param.
      file.getParentFile().mkdirs();

    } catch (Exception e) {

      //Ignore but do log it.
      saxtonLog.info("SaxtonLogger main function caught an exception: ", e);
    }
  }

  /***
   * Need to apply this to not break existing calls to regular logs *
   * */
  public void info(String message) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.info(messageToStore);
    writeToFile(messageToStore);

  }

  public void info(String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.info(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void error(String message) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.error(messageToStore);
    writeToFile(messageToStore);
  }

  public void error(String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.error(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void warn(String message) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.warn(messageToStore);
    writeToFile(messageToStore);
  }

  public void warn(String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.warn(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void fatal(String message) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.fatal(messageToStore);
    writeToFile(messageToStore);
  }

  public void fatal(String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.fatal(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void trace(String message) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.trace(messageToStore);
    writeToFile(messageToStore);
  }

  public void trace(String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + emptyTag + " | " + message;
    saxtonLog.trace(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  /**
   * The log* methods below were created to leverage the ROS node log and then adds the source and tag from the calling procedure
   * onto the message.
   *
   * @param tag     A string representing the category of the data
   * @param message A string containing the message to be logged
   */
  public void logInfo(String tag, String message) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.info(messageToStore);
    writeToFile(messageToStore);

  }

  public void logInfo(String tag, String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.info(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void logError(String tag, String message) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.error(messageToStore);
    writeToFile(messageToStore);
  }

  public void logError(String tag, String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.error(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void logWarn(String tag, String message) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.warn(messageToStore);
    writeToFile(messageToStore);
  }

  public void logWarn(String tag, String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.warn(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void logFatal(String tag, String message) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.fatal(messageToStore);
    writeToFile(messageToStore);
  }

  public void logFatal(String tag, String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.fatal(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  public void logTrace(String tag, String message) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.trace(messageToStore);
    writeToFile(messageToStore);
  }

  public void logTrace(String tag, String message, Throwable t) {
    String messageToStore = " | " + getSource() + " | " + tag + " | " + message;
    saxtonLog.trace(messageToStore, t);
    writeToFile(messageToStore, t);
  }

  /***
   * Write the log to a file with no exceptions.
   * @param message
   */
  private void writeToFile(String message) {
      writeToFile(message, null);
  }

  /***
   * Write the logs to a file with exception.
   * @param message
   * @param exception : Optional, set to null.
   */
  private void writeToFile(String message, Throwable exception) {

    //try-with-resources
    try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter pw = new PrintWriter(bw);) {

      //Using local date time for now, since this is just for logging purpose. Will see if need to pass in the entire connectedNode to get the time.
      DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
      String formatDateTime = LocalDateTime.now().format(dateTimeformatter);

      bw.append(formatDateTime + message);
      bw.newLine();

      if (exception !=null )
        exception.printStackTrace(pw);

    } catch (IOException e) {

      //Ignore but do log it.
      saxtonLog.info("SaxtonLogger printToLogFile failed (catch): ", e);

    }
  }// end of writeToFile()
}