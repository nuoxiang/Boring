package think.common.engine;

import android.content.Context;

import java.util.HashMap;

/**
 * @author think
 * @date 2018/1/15 下午3:01
 */

public class EngineManger implements IEngine {
    private static EngineManger INSTANCE = new EngineManger();

    private EngineManger() {

    }

    public static EngineManger getInstance() {
        return INSTANCE;
    }

    private HashMap<Class<? extends IEngine>, IEngine> iEngineHashMap = new HashMap<>();

    @Override
    public void register() {
        for (Class<? extends IEngine> iEngine : iEngineHashMap.keySet()) {
            getEngine(iEngine).register();
        }
    }

    @Override
    public void unregister() {
        for (Class<? extends IEngine> iEngine : iEngineHashMap.keySet()) {
            getEngine(iEngine).unregister();
        }
    }

    @Override
    public void onAttach() {
        for (Class<? extends IEngine> iEngine : iEngineHashMap.keySet()) {
            getEngine(iEngine).onAttach();
        }
    }

    @Override
    public void onDetach() {
        for (Class<? extends IEngine> iEngine : iEngineHashMap.keySet()) {
            getEngine(iEngine).onDetach();
        }
    }

    /**
     * 添加组件
     *
     * @param key   组件接口
     * @param value 实例对象
     */
    public void addEngine(Class<? extends IEngine> key, IEngine value) {
        iEngineHashMap.put(key, value);
    }

    /**
     * 得到组件对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends IEngine> T getEngine(Class<T> clazz) {
        return (T) iEngineHashMap.get(clazz);
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public Context getContext() {
        return context;
    }

    public static boolean DEBUG = false;
}
