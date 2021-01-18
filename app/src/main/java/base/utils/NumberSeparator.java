package base.utils;

public class NumberSeparator {

    private static String dot = ".";
    private static String comma = ".";

    public static String split(String input) {
        String output = "", pre = "", post = "";
        int _comma = input.indexOf(dot);
        if (_comma == -1) {
            pre = input;
        } else {
            pre = input.substring(0, _comma);
            post = input.substring(_comma + 1, input.length());
        }
        for (int i=0; i<pre.length(); i++) {
            if ((pre.length()-i)%3 == 0 && i!=0)
                output += comma;
            output += pre.charAt(i);
        }
        if (post.trim().length() > 0)
            output = output + dot + post;

        return output;
    }

    public static String join(String delimitedString) {
        String strippedString = "";
        for (int i=0; i<delimitedString.length(); i++) {
            if (delimitedString.charAt(i) == ',')
                continue;
            strippedString += delimitedString.charAt(i);
        }
        return strippedString;
    }
}