package org.hpdroid.base.net.RxUtil;

import org.hpdroid.base.net.exception.RespException;

import rx.Subscriber;

/**
 * Created by tuantuan on 2016/12/26.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
		if (e instanceof RespException) {
			_onError(e.getMessage());
		}else{
			_onError("请求失败，请稍后重试...");
		}
	}

	@Override
	public void onNext(T t) {
		_onNext(t);
	}

	public abstract void _onNext(T t);

	public abstract void _onError(String msg);
}