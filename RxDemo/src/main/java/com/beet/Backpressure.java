package com.beet;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Backpressure
 */
public class Backpressure {

    public static void Execute() {
        // FlowableDemo();
        FlowableError();
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
}