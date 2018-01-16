package think.common.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import think.common.engine.EngineManger;

/**
 * @author think
 * @date 2018/1/15 下午10:52
 */

public class RxUtil {

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 等待几毫秒执行任务
     *
     * @param seconds
     * @param action1
     */
    public static void timer(int seconds, Action1<Long> action1) {
        Observable.timer(seconds, TimeUnit.MILLISECONDS).compose(RxUtil.rxSchedulerHelper()).subscribe(action1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (EngineManger.DEBUG) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
