package think.common.engine;

/**
 * @author think
 * @date 2018/1/15 下午3:00
 */

public interface IEngine {
    /**
     * 注册组件
     */
    void register();

    /**
     * 解除注册
     */
    void unregister();

    /**
     * 组件加载
     */
    void onAttach();

    /**
     * 组件释放
     */
    void onDetach();
}
