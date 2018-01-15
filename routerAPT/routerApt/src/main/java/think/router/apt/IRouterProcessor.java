package think.router.apt;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import think.router.apt.util.ListUtil;
import think.router.apt.util.Tools;
import think.router.lib.IPath;

/**
 * @author think
 */
@AutoService(Processor.class)//自动生成 javax.annotation.processing.IProcessor 文件
@SupportedSourceVersion(SourceVersion.RELEASE_7)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "think.router.lib.IPath",
        "think.router.lib.IPaths"
})
public class IRouterProcessor extends AbstractProcessor {
    /**
     * 文件相关的辅助类
     */
    private Filer mFiler;
    /**
     * 元素相关的辅助类
     */
    private Elements mElements;
    /**
     * 日志相关的辅助类
     */
    private Messager mMessager;

    public static final String PACKAGE_NAME = "think.common.router";

    private ClassName intentClassName, routerUtilClassName, listClassName, arrayListClassName, parcelClassName;

    private String fragmentClassNameStr = "android.support.v4.app.Fragment";

    private boolean isFirst = true;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (isFirst) {
            isFirst = false;
            try {
                mFiler = processingEnv.getFiler();
                mElements = processingEnv.getElementUtils();
                mMessager = processingEnv.getMessager();

                log("=========生成路由表中=========");

                ClassName routerInterface = ClassName.get(PACKAGE_NAME, "IRouter");

                TypeSpec.Builder tb = TypeSpec.classBuilder("IRouterImp")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addSuperinterface(routerInterface)
                        .addJavadoc("@ 生成路由表");

                intentClassName = ClassName.get("android.content", "Intent");
                routerUtilClassName = ClassName.get(PACKAGE_NAME, "RouterUtil");
                listClassName = ClassName.get("java.util", "List");
                arrayListClassName = ClassName.get("java.util", "ArrayList");
                parcelClassName = ClassName.get("android.os", "Parcelable");

                for (Element element : ElementFilter.methodsIn(roundEnv.getElementsAnnotatedWith(IPath.class))) {
                    IPath iPath = element.getAnnotation(IPath.class);
                    String path = iPath.value();

                    tb.addMethod(buildMethod(element, path));
                }

                // 生成源代码
                JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, tb.build()).build();

                javaFile.writeTo(mFiler);

                log("=========生成路由表完成=========");
            } catch (IOException e) {
                log("=========生成路由表出错=========");
                e.printStackTrace();
            }
        }

        return false;
    }

    private void log(String text) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, text);
    }

    private MethodSpec buildMethod(Element element, String path) {
        MethodSpec.Builder m = MethodSpec.methodBuilder(element.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(TypeName.VOID);

        ExecutableElement executableElement = (ExecutableElement) element;

        List<? extends VariableElement> parameters = executableElement.getParameters();

        if (parameters.size() > 0) {
            //不需要传值
            if (parameters.size() == 1) {
                m.addParameter(TypeName.get(parameters.get(0).asType()), parameters.get(0).getSimpleName().toString());
                m.addStatement("$T.router(context,\"$L\")", routerUtilClassName, path);
            } else {
                VariableElement variableElement = parameters.get(0);
                if (Tools.isMatchClass(variableElement, fragmentClassNameStr)) {
                    String param = variableElement.getSimpleName().toString();
                    m.addStatement("$T intent = $T.getIntent($L.getActivity(),\"$L\")", intentClassName, routerUtilClassName, param, path);
                } else {
                    m.addStatement("$T intent = $T.getIntent(context,\"$L\")", intentClassName, routerUtilClassName, path);
                }
                boolean isRequestCode = false;
                String mRequestCode = "0";

                for (VariableElement ep : parameters) {
                    //变量名
                    String param = ep.getSimpleName().toString();
                    //变量类型
                    TypeName typeName = TypeName.get(ep.asType());

                    m.addParameter(typeName, param);

                    //需要调用 startActivityForResult
                    if ("requestCode".equals(param)) {
                        isRequestCode = true;
                        mRequestCode = param;
                    } else if (!"context".equals(param)) {
                        //判断传的值是否为数组
                        if (ListUtil.isList(typeName.toString())) {
                            String className = ListUtil.getClassName(typeName.toString());
                            //String
                            if ("java.lang.String".equals(className)) {
                                ClassName stringClassName = ClassName.get(String.class);
                                TypeName arrayStringList = ParameterizedTypeName.get(arrayListClassName, stringClassName);
                                m.addStatement("intent.putStringArrayListExtra(\"$L\", ($L) $L)", param, arrayStringList, param);
                                //int
                            } else if ("java.lang.Integer".equals(className)) {
                                ClassName cls = ClassName.get(Integer.class);
                                TypeName arrayList = ParameterizedTypeName.get(arrayListClassName, cls);
                                m.addStatement("intent.putIntegerArrayListExtra(\"$L\", ($L) $L)", param, arrayList, param);
                            } else {
                                m.addStatement("intent.putParcelableArrayListExtra(\"$L\", ($L<? extends $L>) $L)", param, arrayListClassName, parcelClassName, param);
                            }
                        } else {
                            m.addStatement("intent.putExtra(\"$L\", $L)", param, param);
                        }
                    }
                }

                if (!isRequestCode) {
                    m.addStatement("context.startActivity(intent)");
                } else {
                    m.addStatement("context.startActivityForResult(intent,$L)", mRequestCode);
                }
            }
        }

        return m.build();
    }
}
