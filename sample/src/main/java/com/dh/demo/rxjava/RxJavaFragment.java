package com.dh.demo.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dh.baseactivity.BaseFragment;
import com.dh.demo.R;
import com.dh.demo.alarm.BksService;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Larry on 2017/5/25.
 */

public class RxJavaFragment extends BaseFragment {

    public static final String TAG = "RxJavaFragment";

    private Button mTask;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_normal;
    }


    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTask = (Button) view.findViewById(R.id.button1);
        mTask.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setPackage(getActivity().getPackageName());
            intent.setAction(BksService.ACTION1);
            getActivity().startService(intent);

        });

        // Observable.create(new ObservableOnSubscribe<Integer>() {
        // @Override
        // public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        //
        // }
        // }).ca

        // 1
        Flowable.just("Hello world!!!").subscribe(new FlowableSubscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext");
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
        // 2
        Flowable.just("Hello world").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Log.d(TAG, s);
            }
        });

        // 3
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); // imitate expensive computation
            return "Done";
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe((String s) -> Log.d(TAG, s),
                Throwable::printStackTrace);
        // try {
        // Thread.sleep(2000); // <--- wait for the flow to finish
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        // 4
        Flowable<String> source = Flowable.fromCallable(() -> {
            Thread.sleep(1000); // imitate expensive computation
            return "Done";
        });

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());
        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());
        // showForeground.subscribe(new Consumer<String>() {
        // @Override
        // public void accept(String s) throws Exception {
        // Log.d(TAG, s);
        // }
        // }, Throwable::printStackTrace);
        showForeground.subscribe((String s) -> Log.d(TAG, s), Throwable::printStackTrace);


        // 5
        Flowable.range(1, 10).observeOn(Schedulers.computation()).map(v -> v * v)
                .blockingSubscribe(System.out::println);

        // 6
        Observable.just(1, 2, 3, 4, 5).subscribe((Integer index) -> Log.d(TAG, String.format("index = %d", index)));
    }
}
