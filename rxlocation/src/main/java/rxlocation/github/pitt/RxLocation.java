package rxlocation.github.pitt;

import android.content.Context;

import rxlocation.github.pitt.location.AMapRxLocationManager;
import rxlocation.github.pitt.location.BaiduRxLocationManager;
import rxlocation.github.pitt.location.ClientOption;
import rxlocation.github.pitt.location.ClientOptionFactory;
import rxlocation.github.pitt.location.RxLocationManager;

/**
 * Created by pitt on 2017/3/17.
 */

/**
 * RxLocation 用于定位lib的初始化和销毁.
 */
public final class RxLocation {

    @Platform.Type
    private static volatile int sClientType = Platform.TYPE_BAIDU;


    private RxLocation() {
    }

    /**
     * 使用默认配置初始化RxLocation
     *
     * @param context Application Context
     */
    public static void initialize(final Context context) {
        initialize(Platform.TYPE_BAIDU, context);
    }

    /**
     * 使用默认的Option和自定义的Type初始化RxLocation
     */
    /**
     * 初始化RxLocation
     *
     * @param type    定位Client类型
     * @param context Application Context
     */
    public static void initialize(final @Platform.Type int type, final Context context) {
        final ClientOptionFactory clientOptionFactory = new ClientOptionFactory();
        initialize(type, context, type == Platform.TYPE_BAIDU
                ? clientOptionFactory.newDefaultBaiduOption()
                : type == Platform.TYPE_AMAP
                ? clientOptionFactory.newDefaultAMapOption() : null);
    }

    /**
     * 初始化RxLocation
     *
     * @param type    定位Client类型
     * @param context Application Context
     * @param option  定位Client定位配置信息
     */
    public static void initialize(final @Platform.Type int type, final Context context,
                                  final ClientOption option) {
        if (null == option) {
            throw new IllegalArgumentException("Option can't be null");
        }

        if (type != Platform.TYPE_AMAP && type != Platform.TYPE_BAIDU) {
            throw new IllegalArgumentException("Platform Type must be AMAP or BAIDU.");
        }

        sClientType = type;

        if (sClientType == Platform.TYPE_AMAP) {
            AMapRxLocationManager.initialize(context);
            AMapRxLocationManager.getInstance().setOption(option);
        } else if (sClientType == Platform.TYPE_BAIDU) {
            BaiduRxLocationManager.initialize(context);
            BaiduRxLocationManager.getInstance().setOption(option);
        }
    }

    /**
     * 获取当前LocationManager
     *
     * @return 当前LocationManager
     */
    static RxLocationManager currentManager() {
        final RxLocationManager rxLocationManager;
        if (sClientType == Platform.TYPE_AMAP) {
            rxLocationManager = AMapRxLocationManager.getInstance();
        } else if (sClientType == Platform.TYPE_BAIDU) {
            rxLocationManager = BaiduRxLocationManager.getInstance();
        } else {
            rxLocationManager = null;
        }
        return rxLocationManager;
    }

    /**
     * 设置新的ClientOption
     *
     * @param option 定位Client的配置信息.
     */
    public static void setOption(final ClientOption option) {
        currentManager().setOption(option);
    }

    /**
     * 关闭RxLocation,释放资源.
     */
    public static void shutDown() {
        final RxLocationManager rxLocationManager = currentManager();
        if (rxLocationManager != null) {
            rxLocationManager.shutDown();
        }
    }
}
