package com.beet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * BasicRx
 */
public class BasicRx {

    public static void Execute() throws InterruptedException {
        // BasicCreate();
        // Just();
        // FromIterable();
        // Defer();
        // Interval();
        // Range();
        // Timer();
        // Repeat();
        // ConsumerDemo();
        // Trigger();
        DisposableDemo();
    }

    public static void Just() {
        Observable<String> observable = Observable.just("Hello");
        // observable.subscribe(System.out::println);
        observable.subscribe((a) -> System.out.println(a));
    }

    public static void FromIterable() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }

        Observable<String> observable = Observable.fromIterable((Iterable<String>) list);

        observable.subscribe((a) -> System.out.println(a));
    }

    public static void Defer() {
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("hello");
            }
        });

        observable.subscribe((a) -> System.out.println(a));
    }

    public static void Interval() {
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
        observable.subscribe((a) -> System.out.println(a));
    }

    public static void Range() {
        Observable<Integer> observable = Observable.range(1, 20);
        observable.subscribe((a) -> System.out.println(a));
    }

    public static void Timer() {
        Observable<Long> observable = Observable.timer(2, TimeUnit.SECONDS);
        observable.subscribe((a) -> System.out.println(a));
    }

    public static void Repeat() {
        Observable<Integer> observable = Observable.just(123).repeat();
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

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            // 观察者接收到通知,进行相关操作
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

    public static void ConsumerDemo() {
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        }, new Consumer<Throwable>() {

            @Override
            public void accept(@NonNull Throwable t) throws Exception {
                t.printStackTrace();
            }
        }, new Action() {

            @Override
            public void run() throws Exception {
                System.out.println("done");
            }
        });
    }

    public static void Trigger() throws InterruptedException {
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                System.out.println(aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Thread.sleep(10000);// 睡眠10秒后，才进行订阅 仍然从0开始，表示Observable内部逻辑刚开始执行
        observable.subscribe(observer);
    }

    public static void DisposableDemo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10; i++) {
                    System.out.println("emit" + i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("done");
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("receive" + t);
                if (t > 4)
                    disposable.dispose();
            }

        });
    }
}