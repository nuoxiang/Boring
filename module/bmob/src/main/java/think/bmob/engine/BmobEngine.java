package think.bmob.engine;

import cn.bmob.v3.Bmob;
import think.common.constant.Config;
import think.common.engine.EngineManger;
import think.common.engine.IBmobEngine;

/**
 * @author think
 * @date 2018/1/15 下午3:07
 */

public class BmobEngine implements IBmobEngine {
    @Override
    public void register() {
        Bmob.initialize(EngineManger.getInstance().getContext(), Config.BMOB_APPLICATION_ID, "test");
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
