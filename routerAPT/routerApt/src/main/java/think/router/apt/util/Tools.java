package think.router.apt.util;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

/**
 * @author think
 * @date 17/10/26 下午4:17
 */

public class Tools {
    /**
     * 类型是否匹配
     *
     * @param ep
     * @param className 类名
     * @return 是
     */
    public static boolean isMatchClass(VariableElement ep, String className) {
        //变量类型
        TypeName typeName = TypeName.get(ep.asType());
        return typeName.toString().startsWith(className);
    }
}
