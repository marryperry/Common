package org.hpdroid.base.net.RxUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tuantuan on 2016/12/26.
 */

public class RxSchedulersHelper {

	public static <T> Observable.Transformer<T, T> io_main() {
		return new Observable.Transformer<T, T>() {
			@Override
			public Observable<T> call(Observable<T> tObservable) {
				return tObservable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread());
			}
		};
	}
}
