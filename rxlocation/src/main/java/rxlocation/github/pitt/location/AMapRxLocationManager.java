package rxlocation.github.pitt.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * 高德RxLocationManager的实现类.
 * Created by pitt on 2017/3/16.
 */

public final class AMapRxLocationManager implements RxLocationManager<AMapLocation, AMapLocationClient> {
    private static volatile AMapRxLocationManager sINSTANCE;
    private volatile AMapLocationClient mAMapLocationClient;

    private AMapRxLocationManager(final Context context) {
        mAMapLocationClient = new AMapLocationClient(context.getApplicationContext());
    }

    public static AMapRxLocationManager getInstance() {
        if (sINSTANCE == null) {
            throw new IllegalArgumentException("AMapRxLocationManager was not initialized!");
        }
        return sINSTANCE;
    }

    public static void initialize(final Context context) {
        synchronized (AMapRxLocationManager.class) {
            if (sINSTANCE == null) {
                sINSTANCE = new AMapRxLocationManager(context);
            }
        }
    }

    @Override
    public Observable<AMapLocation> getLastLocation() {
        return Observable.just(mAMapLocationClient.getLastKnownLocation());
    }

    @Override
    public Observable<AMapLocation> requestLocation() {
        final RxLocationListener rxLocationListener = new RxLocationListener(mAMapLocationClient);
        return Observable.unsafeCreate(rxLocationListener);
    }

    @Override
    public AMapLocationClient currentClient() {
        return mAMapLocationClient;
    }

    @Override
    public void setOption(final ClientOption option) {
        if (option == null) {
            throw new IllegalArgumentException("Option can't be null.");
        }
        mAMapLocationClient.setLocationOption(option.getAmapOption());
    }

    @Override
    public Observable<AMapLocation> requestLocationByOption(final ClientOption option) {
        setOption(option);
        return requestLocation();
    }

    @Override
    public String getVersion() {
        return mAMapLocationClient.getVersion();
    }

    @Override
    public AMapLocationClient getClient() {
        return mAMapLocationClient;
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
