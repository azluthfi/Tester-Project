/*
 *  Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.app.base.base;

import android.content.Context;

import com.app.base.utility.Preferences;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A presenter for RxJava, that assumes that only one Observable is subscribed by this presenter.
 * The idea is, that you make your (chain of) Observable and pass it to {@link
 * #subscribe(Observable, boolean)}. The presenter internally subscribes himself as Subscriber to
 * the
 * observable
 * (which executes the observable).
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class BaseRxLcePresenter<V extends MvpLceView<M>, M>
        extends com.hannesdorfmann.mosby3.mvp.MvpBasePresenter<V>
        implements com.hannesdorfmann.mosby3.mvp.MvpPresenter<V> {

    protected Subscriber<M> subscriber;
    protected Context context;
    protected Preferences prefs;
    private Observer<M> observer;

    public BaseRxLcePresenter(Context context) {
        if (prefs == null) {
            prefs = new Preferences(context);
        }
        this.context = context;
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public void subscribe(Observable<M> observable, final boolean pullToRefresh) {

        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        observer = new Observer<M>() {
            private boolean ptr = pullToRefresh;

            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(M m) {
                BaseRxLcePresenter.this.onNext(m);
            }

            @Override public void onError(Throwable e) {
                BaseRxLcePresenter.this.onError(e, ptr);
            }

            @Override public void onComplete() {
                BaseRxLcePresenter.this.onCompleted();
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    protected void onCompleted() {
        if (isViewAttached()) {
            getView().showContent();
        }
    }

    protected void onError(Throwable e, boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showError(e, pullToRefresh);
        }
    }

    protected void onNext(M data) {
        if (isViewAttached()) {
            getView().setData(data);
        }
    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
        }
    }
}
