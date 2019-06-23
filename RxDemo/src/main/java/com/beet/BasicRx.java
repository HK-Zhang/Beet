package com.beet;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BasicRx
 */
public class BasicRx {

    public static void Execute() {
        // BasicCreate();
        Just();
    }

    public static void Just() {
        Observable<String> observable = Observable.just("Hello");
        // observable.subscribe(System.out::println);
        observable.subscribe((a) -> System.out.println(a));
    }

    public static void BasicCreate() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // 执行一些其他操作
                // .............
                // 执行完毕，触发回调，通知观察者
                e.onNext("我来发射数据");
            }
        });

        Observer<String> observer = new Observer<String>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            //观察者接收到通知,进行相关操作
            public void onNext(String aLong) {
                System.out.println("我接收到数据了");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }
}