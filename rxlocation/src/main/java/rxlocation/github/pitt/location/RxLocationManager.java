package rxlocation.github.pitt.location;

/**
 * Created by pitt on 2017/3/16.
 */

import rx.Observable;

/**
 * @param <L> Location信息
 * @param <C> 定位使用的Client
 * @param <O> Client中对用的Option信息
 */
public interface RxLocationManager<L, C> {

    /**
     * 获取最后一次定位的信息，会在onNext()中返回信息，如果出现错误会在onError中回调
     *
     * @return observable that emit last location
     */
    Observable<L> getLastLocation();

    /**
     * 申请定位，获取定位信息，会在onNext()中返回信息，如果出现错误会在onError中回调
     *
     * @return observable that emit location
     */
    Observable<L> requestLocation();

    /**
     * 获取定位的Client
     *
     * @return
     */
    C currentClient();

    /**
     * 设置Client的配置信息
     *
     * @param option
     */
    void setOption(final ClientOption option);

    /**
     * 申请定位，根据新设置的option获取定位信息，会在onNext()中返回信息，如果出现错误会在onError中回调
     *
     * @param option Client的配置信息
     * @return observable that emit location
     */
    Observable<L> requestLocationByOption(final ClientOption option);

    /**
     * 获取Client的版本信息
     *
     * @return Client的版本信息
     */
    String getVersion();

    /**
     * 获取当前定位的Client
     *
     * @return 当前的定位Client
     */
    C getClient();

    /**
     * 销毁定位，释放资源，建议在App关闭时调用此方法，调用后，如果再次使用需要重新创建LocationManager
     */
    void shutDown();
}
