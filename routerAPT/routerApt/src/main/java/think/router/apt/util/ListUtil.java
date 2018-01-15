package think.router.apt.util;

/**
 * Created by think on 17/5/11.
 */

public class ListUtil {
    private static final String LIST_CLASS_NAME = "java.util.List";

    /**
     * 是否是个List
     *
     * @param str
     * @return
     */
    public static boolean isList(String str) {
        return str.startsWith(LIST_CLASS_NAME);
    }

    /**
     * List中的泛型
     *
     * @param str
     * @return
     */
    public static String getClassName(String str) {
        if (!isList(str)) {
            throw new ClassCastException(" it not a list");
        }
        if (str.length() == LIST_CLASS_NAME.length()) {
            throw new ClassCastException(" list should have <E>");
        }
        return str.substring(LIST_CLASS_NAME.length() + 1, str.length() - 1);
    }
}
