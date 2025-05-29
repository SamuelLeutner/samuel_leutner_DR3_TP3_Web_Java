package org.example.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractJsonHelper {
    public static String value(String jsonString, String key) {
        Pattern stringPattern = Pattern.compile("\"" + Pattern.quote(key) + "\":\\s*\"([^\"]*)\"");
        Matcher stringMatcher = stringPattern.matcher(jsonString);
        if (stringMatcher.find()) {
            return stringMatcher.group(1);
        }


        Pattern nonStringPattern = Pattern.compile("\"" + Pattern.quote(key) + "\":\\s*([^,\\]}]+)");
        Matcher nonStringMatcher = nonStringPattern.matcher(jsonString);
        if (nonStringMatcher.find()) {
            return nonStringMatcher.group(1).trim();
        }

        return null;
    }
}
