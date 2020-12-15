/*
  StringUtils.java

  @license Use of this software requires acceptance of the Evaluation License Agreement. See LICENSE file.
 * @copyright Copyright © 2016-present Heidelberger Payment GmbH. All rights reserved.
 *
 * @author vukhang

 */
package vn.vdc.nab.alice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  public static final String SPACE = " ";
  public static final String DASH = "-";

  private StringUtils() {
  }

  /**
   * Verifies string is null or empty after remove all spaces
   * @param string
   * @return
   */
  public static boolean isBlank(String string) {
    return string == null || string.trim().isEmpty();
  }
  
  /**
   * Method to remove all string in one string
   * 
   * @param source
   * @param stringToReplace
   * @param stringToRemove
   * @return string after being replaced
   */
  public static String replaceAll(String source, String stringToReplace, String... stringToRemove) {
    if (source == null || stringToReplace == null || stringToRemove == null)
      return source;
    if (source.length() == 0)
      return source;
    if (stringToRemove.length == 0)
      return source;

    String result = source;
    for (String string : stringToRemove) {
      result = result.replaceAll(string, stringToReplace);
    }

    return result;
  }
  
  /**
   * Find the 1st matched group of regex from template
   * @param regex refers to regular expression to be used
   * @param template refers to using extract string
   * @return matched value or else the template itself
   */
  public static String findFirstMatching(String regex, String template) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(template);
    if (matcher.find()) {
        return matcher.group(1);
    }
    return template;
  }


}
