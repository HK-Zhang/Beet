package com.beet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * OperatorRx
 */
public class OperatorRx {

    public static void Execute() {
        // Map();
        // FlatMap();
        Filter();
    }

    public static void Map() {
        Observable<Integer> observable = Observable.just("hello").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        });

        observable.subscribe((a) -> System.out.println(a));

    }

    public static void FlatMap() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }

        Observable<Object> observable = Observable.just(list)
                .flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.fromIterable(strings);
                    }
                });

        observable.subscribe((a) -> System.out.println(a));
    }

    public static void Filter() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object s) throws Exception {
                String newStr = (String) s;
                if (newStr.charAt(5) - '0' > 5) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println((String) o);
            }
        });
    }
}