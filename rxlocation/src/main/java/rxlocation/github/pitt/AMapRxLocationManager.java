package rxlocation.github.pitt;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.Subscription;

/**
 * 高德RxLocationManager的实现类.
 * Created by pitt on 2017/3/16.
 */

final class AMapRxLocationManager implements RxLocationManager<AMapLocation, AMapLocationClient> {
    private static volatile AMapRxLocationManager sINSTANCE;
    private volatile AMapLocationClient mAMapLocationClient;

    private AMapRxLocationManager(final Context context) {
        mAMapLocationClient = new AMapLocationClient(context.getApplicationContext());
    }

    @SuppressWarnings("PMD.NonThreadSafeSingleton")
    static AMapRxLocationManager getInstance(final Context context) {
        if (sINSTANCE == null) {
            synchronized (AMapRxLocationManager.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new AMapRxLocationManager(context);
                }
            }
        }
        return sINSTANCE;
    }

    @Override
    public Observable<AMapLocation> getLastLocation() {
        return Single.just(mAMapLocationClient.getLastKnownLocation()).toObservable();
    }

    @Override
    public Observable<AMapLocation> requestLocation() {
        final RxLocationListener rxLocationListener = new RxLocationListener(mAMapLocationClient);
        return Observable.unsafeCreate(rxLocationListener);
    }


    @Override
    public void setOption(final ClientOption option) {
        if (option == null) {
            throw new IllegalArgumentException("Option can't be null.");
        }
        mAMapLocationClient.setLocationOption(option.getAmapOption());
    }

    @SuppressWarnings("PMD.NullAssignment")
    @Override
    public void shutDown() {
        synchronized (AMapRxLocationManager.class) {
            if (mAMapLocationClient != null) {
                mAMapLocationClient.onDestroy();
            }
            mAMapLocationClient = null;
            sINSTANCE = null;
        }
    }

    static class RxLocationListener implements Observable.OnSubscribe<AMapLocation> {
        private final AMapLocationClient mAMapLocationClient;
        private AMapLocationListener mListener;

        RxLocationListener(final AMapLocationClient client) {
            mAMapLocationClient = client;
        }

        @Override
        public void call(final Subscriber<? super AMapLocation> subscriber) {
            mListener = new AMapLocationListener() {
                @Override
                public void onLocationChanged(final AMapLocation aMapLocation) {
                    subscriber.onNext(aMapLocation);
                }
            };
            mAMapLocationClient.setLocationListener(mListener);
            synchronized (RxLocationListener.class) {
                mAMapLocationClient.startLocation();
            }
            subscriber.add(new Subscription() {
                @Override
                public void unsubscribe() {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.unsubscribe();
                    }
                    removeListener();
                }

                @Override
                public boolean isUnsubscribed() {
                    return subscriber.isUnsubscribed();
                }
            });
        }

        private void removeListener() {
            if (mListener != null) {
                mAMapLocationClient.unRegisterLocationListener(mListener);
                synchronized (RxLocationListener.class) {
                    mAMapLocationClient.stopLocation();
                }
            }
        }
    }
}
