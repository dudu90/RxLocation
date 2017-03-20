package rxlocation.github.pitt;


import rx.Observable;
import rxlocation.github.pitt.location.ClientOption;

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

    /**
     * 获取Client Version
     *
     * @return 当前Client的信息.
     */
    public String version() {
        return RxLocation.currentManager().getVersion();
    }

    /**
     * set新的ClientOption申请定位
     *
     * @param option Client Option
     * @return result Observable
     */
    public Observable<T> requestLocationByOption(final ClientOption option) {
        return RxLocation.currentManager().requestLocationByOption(option);
    }
}
