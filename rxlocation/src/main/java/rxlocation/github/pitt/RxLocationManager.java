package rxlocation.github.pitt;

/**
 * Created by pitt on 2017/3/16.
 */

import rx.Observable;

/**
 * @param <L> Location信息
 * @param <C> 定位使用的Client
 */
interface RxLocationManager<L, C> {

    /**
     * 获取最后一次定位的信息
     *
     * @return observable that emit last location
     */
    Observable<L> getLastLocation();

    /**
     * 申请定位，获取定位信息
     *
     * @return observable that emit location
     */
    Observable<L> requestLocation();

    /**
     * 设置Client的配置信息
     *
     * @param option
     */
    void setOption(final ClientOption option);

    /**
     * 销毁定位，释放资源，建议在App关闭时调用此方法，调用后，如果再次使用需要重新创建LocationManager
     */
    void shutDown();
}
