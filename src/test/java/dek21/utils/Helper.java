package dek21.utils;

public class Helper {

    public static String replaceSlash(String string) {
        return System.getProperty("os.name").contains("Windows") ? string : string.replace("\\", "/");
    }
}
