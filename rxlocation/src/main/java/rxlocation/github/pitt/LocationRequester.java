package rxlocation.github.pitt;


import rx.Observable;

/**
 * Created by pitt on 2017/3/16.
 */

/**
 * @param <T> Location,定位返回的数据信息
 */
public class LocationRequester<T> {
    /**
     * 请求定位
     *
     * @return result Observable
     */
    public Observable<T> requestLocation() {
        return RxLocation.currentManager().requestLocation();
    }

    /**
     * 获取最后位置
     *
     * @return result Observable
     */
    public Observable<T> lastLocation() {
        return RxLocation.currentManager().getLastLocation();
    }
}
