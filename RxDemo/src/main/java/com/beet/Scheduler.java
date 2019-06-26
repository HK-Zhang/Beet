package com.beet;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Scheduler
 */
public class Scheduler {

    public static void Execute() {
        Basic();
    }

    public static void Basic() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) {
                System.out.println("current thread" + Thread.currentThread().getName());
                System.out.println("sending data" + 1);
                e.onNext(1);
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                System.out.println("current thread" + Thread.currentThread().getName());
                System.out.println("receiving data" + t);
            }

        });
    }
}