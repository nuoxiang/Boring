package think.common.router;

/**
 * @author think
 * @date 2018/1/15 下午5:37
 */

public interface RouterList {

    interface Module {
        String MAIN = "MAIN";
        String RONGIM = "IM";
    }

    interface Main {
        /**
         * 首页
         */
        String MAIN = "main";
    }
}
