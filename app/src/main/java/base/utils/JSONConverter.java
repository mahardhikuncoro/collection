package base.utils;

public class JSONConverter {

    public static String convertStandardJSONString(String json) {
        json = json.replaceAll("\\\\r\\\\n", "");
        json = json.replaceAll("\n", "");
        json = json.replaceAll("\t", "");
        json = json.replace("\"{", "{");
        json = json.replace("}\",", "},");
        json = json.replace("}\"", "}");
        return json;
    }
}
