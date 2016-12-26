package org.hpdroid.base.net.RxUtil;


import org.hpdroid.base.net.ErrorCode;
import org.hpdroid.base.net.RespResult;
import org.hpdroid.base.net.exception.RespException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by tuantuan on 2016/12/26.
 */

public class RxResultHelper {

	public static <T> Observable.Transformer<RespResult<T>, T> handleResult() {
		return new Observable.Transformer<RespResult<T>, T>() {
			@Override
			public Observable<T> call(Observable<RespResult<T>> tObservable) {
				return tObservable.flatMap(
						new Func1<RespResult<T>, Observable<T>>() {
							@Override
							public Observable<T> call(RespResult<T> result) {

								if (result.getStatus() == ErrorCode.kSuccess.getCode()) {
									return createData(result.getData());
								} else if (result.getStatus() == ErrorCode.kUnLogin.getCode()) {
									// 举个栗子:处理Token失效情况(当然这种情况在convert里面已经处理过了)
								} else {
									return Observable.error(new RespException(result.getStatus(),result.getMsg()));
								}
								return Observable.empty();

							}
						}

				);
			}
		};
	}

	private static <T> Observable<T> createData(final T t) {
		return Observable.create(new Observable.OnSubscribe<T>() {
			@Override
			public void call(Subscriber<? super T> subscriber) {
				try {
					subscriber.onNext(t);
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}
}
