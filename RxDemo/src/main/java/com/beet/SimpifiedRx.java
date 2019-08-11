package com.beet;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * SimpifiedRx
 */
public class SimpifiedRx {
    public static void Execute() {
        // singleDemo1();
        // singleDemo2();
        // completableDemo1();
        // completableDemo2();
        // maybeDemo1();
        maybeDemo2();
    }

    public static void singleDemo1() {
        Single.create(new SingleOnSubscribe<Integer>() {

            @Override
            public void subscribe(SingleEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(0);
            }

        }).subscribe(new SingleObserver<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer t) {
                System.out.println(t);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public static void singleDemo2() {
        Single.create(new SingleOnSubscribe<Integer>() {

            @Override
            public void subscribe(SingleEmitter<Integer> emitter) throws Exception {
                emitter.onError(new Exception("test Exception"));
            }

        }).subscribe(new SingleObserver<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer t) {
                System.out.println(t);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });

    }

    public static void completableDemo1() {
        Completable.create(new CompletableOnSubscribe(){

			@Override
			public void subscribe(CompletableEmitter emitter) throws Exception {
				emitter.onComplete();
			}
            
        }).subscribe(new CompletableObserver(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("execution done");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            
        });
    }

    public static void completableDemo2() {
        Completable.create(new CompletableOnSubscribe(){

			@Override
			public void subscribe(CompletableEmitter emitter) throws Exception {
				emitter.onError(new Exception("test Exception"));
			}
            
        }).subscribe(new CompletableObserver(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("execution done");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            
        });
    }

    public static void maybeDemo1() {
        Maybe.create(new MaybeOnSubscribe<Integer>(){

			@Override
			public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(1);
                emitter.onComplete();
			}
            
        }).subscribe(new MaybeObserver<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer t) {
                System.out.println(t);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("done");
            }
            
        });
    }

    public static void maybeDemo2() {
        Maybe.create(new MaybeOnSubscribe<Integer>(){

			@Override
			public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(1);
                emitter.onError(new Exception("error"));
			}
            
        }).subscribe(new MaybeObserver<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer t) {
                System.out.println(t);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("done");
            }
            
        });
    }
}