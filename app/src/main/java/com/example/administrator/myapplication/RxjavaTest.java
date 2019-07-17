package com.example.administrator.myapplication;


import android.util.Log;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxjavaTest {
    private static final String TAG = "RxjavaTest";
    public static void main(String[] ss){

        final Observable observable= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
                emitter.onNext("Hi");
                emitter.onNext("Aloha");
                emitter.onComplete();
            }
        });

        Observer<String> subscriber = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.print(s);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

//        observable.subscribe(subscriber);

        Consumer<String> consumer=new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.print(s);
            }
        };
        observable.subscribe(consumer);

        List<String> names = new ArrayList<>();
        names.add("Hello");
        names.add("Hi");
        names.add("Aloha");
        Observable.fromIterable(names)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.print(s);
                    }
                });
        System.out.print("\n****************变换测试*******************\n");

        Consumer<Integer> consumer2=new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                System.out.print(s);
            }
        };
        Observable.just(1, 3, 5, 7, 9)
                .map(new Function<Integer,Integer>(){

                    @Override
                    public Integer apply(Integer o) throws Exception {
                        return o+1;
                    }

                })
//                .filter(new Predicate<Integer>(){
//
//                    @Override
//                    public boolean test(Integer integer) throws Exception {
//                        if (integer<5) return true;
//                        return false;
//                    }
//                })
                .subscribe(consumer2);

        System.out.print("\n****************变换测试2*******************\n");

        List<TestBean> list=new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            TestBean testBean=new TestBean();
            List<String> data=new ArrayList<>();
            for (int i1 = 0; i1 < i; i1++) {
                data.add(i1+"");
            }
            testBean.setData(data);
            testBean.setTime(System.currentTimeMillis()+"");
            list.add(testBean);
        }

        Consumer<String> consumer3=new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.print("\n"+s);
            }
        };
        Observable.fromIterable(list)
                .flatMap(new Function<TestBean, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final TestBean testBean) throws Exception {
                return new ObservableSource<String>() {
                    @Override
                    public void subscribe(Observer<? super String> observer) {
                        for (String datum : testBean.getData()) {
                            observer.onNext(datum);
                        }

                    }
                };
            }
        }).subscribe(consumer3);

    }

    static class TestBean{
        List<String> data;
        String time;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
