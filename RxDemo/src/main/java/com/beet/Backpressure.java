package com.beet;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
// import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Backpressure
 */
public class Backpressure {

    public static void Execute() {
        // FlowableDemo();
        // FlowableError();
        // FlowableBuffer();
        // FlowableDrop();
        // FlowableLatest();
        // OnBackpressure();
        EmitOnDemand();
    }

    public static void FlowableDemo() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                System.out.println("emit 1");
                emitter.onNext(1);
                System.out.println("emit 2");
                emitter.onNext(2);
                System.out.println("emit 3");
                emitter.onNext(3);
                System.out.println("emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError: " + t);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext: " + integer);
            }

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                s.request(Long.MAX_VALUE);
            }
        };

        flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(subscriber);
    }

    public static void FlowableError() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 129; i++) {
                    System.out.println("emit " + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError: " + t);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        // System.out.println("onSubscribe");
                        // s.request(Long.MAX_VALUE);
                    }

                });
    }

    public static void FlowableBuffer() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                System.out.println("emit---->1");
                e.onNext(1);
                System.out.println("emit---->2");
                e.onNext(2);
                System.out.println("emit---->3");
                e.onNext(3);
                System.out.println("emit---->done");
                e.onComplete();
                ;
            }

        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);

                        // s.request(2);//设置Subscriber的消费能力为2

                        // s.request(3);//调用两次request,设置Subscriber的消费能力为7
                        // s.request(4);
                    }

                    @Override
                    public void onNext(Integer t) {
                        System.out.println("receive----> " + t);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("receive----> done");
                    }

                });
    }

    public static void FlowableDrop() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + "begin to emit data" + System.currentTimeMillis());
                for (int i = 1; i < 500; i++) {
                    System.out.println(threadName + "emit---->" + i);
                    e.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                }
                System.out.println(threadName + "emit done" + System.currentTimeMillis());
                e.onComplete();
            }

        }, BackpressureStrategy.DROP).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer t) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ignore) {
                            // TODO: handle exception
                        }
                        System.out.println(Thread.currentThread().getName() + "receive------->" + t);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread().getName() + "receive ----> done");
                    }
                });
    }

    public static void FlowableLatest() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + "begin to emit data" + System.currentTimeMillis());
                for (int i = 1; i < 500; i++) {
                    System.out.println(threadName + "emit---->" + i);
                    e.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                }
                System.out.println(threadName + "emit done" + System.currentTimeMillis());
                e.onComplete();
            }

        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer t) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ignore) {
                            // TODO: handle exception
                        }
                        System.out.println(Thread.currentThread().getName() + "receive------->" + t);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread().getName() + "receive ----> done");
                    }
                });
    }

    public static void OnBackpressure() {
        Flowable.range(0, 500).onBackpressureDrop().subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread()).subscribe(new Consumer<Integer>() {

                    @Override
                    public void accept(Integer t) throws Exception {
                        System.out.println(t);
                    }

                });
    }

    public static void EmitOnDemand() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                int i = 0;
                while (true) {
                    if (e.requested() == 0)
                        continue;
                    System.out.println("emit---->" + i);
                    i++;
                    e.onNext(i);
                }
            }

        }, BackpressureStrategy.MISSING).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    private Subscription mSubscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer t) {
                        try {
                            Thread.sleep(50);
                            System.out.println("receive------->" + t);
                            mSubscription.request(1);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }
}