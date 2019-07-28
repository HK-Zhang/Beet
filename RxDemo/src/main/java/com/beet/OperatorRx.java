package com.beet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
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
        // Filter();
        // Take();
        // DoOnNext();
        // Merge();
        // Concat();
        Zip();
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

    public static void Take() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).take(5).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println((String) o);
            }
        });

    }

    public static void DoOnNext() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("Hello" + i);
        }

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).take(5).doOnNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("Preparation");
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println((String) o);
            }
        });
    }

    public static void Merge() {
        Integer nums1[] = new Integer[] { 5, 6, 7, 8, 9 };
        Observable.just(1, 2, 3, 4, 5).mergeWith(Observable.fromArray(nums1)).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                System.out.println(t);
            }

        });
    }

    public static void Concat() {
        Integer nums1[] = new Integer[] { 5, 6, 7, 8, 9 };
        Observable.just(1, 2, 3, 4, 5).concatWith(Observable.fromArray(nums1)).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                System.out.println(t);
            }

        });
    }

    public static void Zip() {
        String names[] = new String[] { "red", "orange", "yellow", "green", "blue", "purple" };
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .zipWith(Observable.fromArray(names), new BiFunction<Integer, String, String>() {

                    @Override
                    public String apply(Integer t1, String t2) throws Exception {
                        return t1 + t2;
                    }
                }).subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String t) throws Exception {
                        System.out.println(t);
                    }

                });
    }
}