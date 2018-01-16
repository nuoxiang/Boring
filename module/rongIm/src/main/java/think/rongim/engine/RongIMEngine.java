package think.rongim.engine;

import io.rong.imkit.RongIM;
import think.common.constant.Config;
import think.common.engine.EngineManger;
import think.common.engine.IRongIMEngine;

/**
 * @author think
 * @date 2018/1/15 下午4:43
 */

public class RongIMEngine implements IRongIMEngine {
    @Override
    public void register() {
        RongIM.init(EngineManger.getInstance().getContext(), Config.RONGIM_KEY);
    }

    @Override
    public void unregister() {

    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
