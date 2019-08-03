package com.beet;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.beet.model.AllCity;
import com.beet.model.City;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Scheduler
 */
public class Scheduler {

    public static void Execute() {
        // Basic();
        // ApiCall();
        // Demo2();
        // Demo3();
        // TrampolineDemo();
        SingleDemo();
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

    public static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);

        return new Retrofit.Builder().baseUrl("http://v.juhe.cn/weather/").client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    public static void ApiCall() {
        Retrofit retrofit = create();
        IApi api = retrofit.create(IApi.class);

        Observable<AllCity> observable = api.getAllCity("6341962a627d6a6f16e5ce57b4bec0ba");
        observable.subscribeOn(Schedulers.io()).flatMap(new Function<AllCity, ObservableSource<City>>() {
            @Override
            public ObservableSource<City> apply(AllCity city) throws Exception {
                ArrayList<City> result = city.getResult();
                return Observable.fromIterable(result);
            }
        }).filter(new Predicate<City>() {

            @Override
            public boolean test(City city) throws Exception {
                String id = city.getId();
                if (Integer.parseInt(id) < 5) {
                    return true;
                }
                return false;
            }

        }).observeOn(Schedulers.newThread()).subscribe(new Consumer<City>() {

            @Override
            public void accept(City t) throws Exception {
                System.out.println(t);
            }
        });

    }

    // Two times subscribeone and one time onserver on
    public static void Demo2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Emit thread:" + Thread.currentThread().getName() + "------>" + "emit:" + i);
                    Thread.sleep(1000);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }

        }).subscribeOn(Schedulers.io()).map(new Function<Integer, Integer>() {

            @Override
            public Integer apply(Integer t) throws Exception {
                System.out.println("process thread:" + Thread.currentThread().getName() + "------>" + "process:" + t);
                return t;
            }

        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                System.out.println("receive thread:" + Thread.currentThread().getName() + "------>" + "receive:" + t);
            }

        });
    }

    // one time subscribeOn and Two times observerOn
    public static void Demo3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Emit thread:" + Thread.currentThread().getName() + "------>" + "emit:" + i);
                    Thread.sleep(1000);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }

        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).map(new Function<Integer, Integer>() {

            @Override
            public Integer apply(Integer t) throws Exception {
                System.out.println("process thread:" + Thread.currentThread().getName() + "------>" + "process:" + t);
                return t;
            }

        }).observeOn(Schedulers.newThread()).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                System.out.println("receive thread:" + Thread.currentThread().getName() + "------>" + "receive:" + t);
            }

        });

    }

    public static void TrampolineDemo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Emit thread:" + Thread.currentThread().getName() + "------>" + "emit:" + i);
                    Thread.sleep(1000);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }

        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.trampoline()).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                Thread.sleep(2000);
                System.out.println("receive thread:" + Thread.currentThread().getName() + "------>" + "receive:" + t);

            }

        });
    }

    public static void SingleDemo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Emit thread:" + Thread.currentThread().getName() + "------>" + "emit:" + i);
                    Thread.sleep(1000);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }

        }).subscribeOn(Schedulers.single()).observeOn(Schedulers.single()).map(new Function<Integer, Integer>() {

            @Override
            public Integer apply(Integer t) throws Exception {
                System.out.println("process thread:" + Thread.currentThread().getName() + "------>" + "process:" + t);
                return t;
            }

        }).observeOn(Schedulers.single()).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                System.out.println("receive thread:" + Thread.currentThread().getName() + "------>" + "receive:" + t);
            }

        });

    }
}