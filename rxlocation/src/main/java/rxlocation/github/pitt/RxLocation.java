package rxlocation.github.pitt;

import android.content.Context;

/**
 * Created by pitt on 2017/3/17.
 */

/**
 * RxLocation 用于定位lib的初始化和销毁.
 */
public final class RxLocation {

    @Platform.Type
    private static volatile int sClientType = Platform.TYPE_UNKNOWN;
    private static volatile Context sContext;

    private RxLocation() {
    }

    /**
     * 使用默认配置初始化RxLocation
     *
     * @param context Context
     */
    public static void initialize(final Context context) {
        initialize(context, Platform.TYPE_BAIDU);
    }

    /**
     * 使用默认的Option和自定义的Type初始化RxLocation
     *
     * @param context Context
     * @param type    定位Client类型
     */
    public static void initialize(final Context context, final @Platform.Type int type) {
        initialize(context, type, null);
    }

    /**
     * 初始化RxLocation
     *
     * @param context Context
     * @param type    定位Client类型
     * @param option  定位Client定位配置信息
     */
    public static void initialize(final Context context, final @Platform.Type int type,
                                  final ClientOption option) {
        if (null == context) {
            throw new IllegalArgumentException("Context can't be null.");
        }

        if (type != Platform.TYPE_AMAP && type != Platform.TYPE_BAIDU) {
            throw new IllegalArgumentException("Platform Type must be AMAP or BAIDU.");
        }
        sClientType = type;
        sContext = context.getApplicationContext();

        final ClientOption clientOption;
        if (null == option) {
            final ClientOptionFactory clientOptionFactory = new ClientOptionFactory();
            clientOption = sClientType == Platform.TYPE_AMAP
                    ? clientOptionFactory.newDefaultAMapOption()
                    : clientOptionFactory.newDefaultBaiduOption();
        } else {
            clientOption = option;
        }
        setOption(clientOption);
    }

    /**
     * 获取当前LocationManager
     *
     * @return 当前LocationManager
     */
    static RxLocationManager currentManager() {
        if (null == sContext || sClientType == Platform.TYPE_UNKNOWN) {
            throw new IllegalArgumentException("RxLocation was not initialized");
        }

        return (sClientType == Platform.TYPE_AMAP)
                ? AMapRxLocationManager.getInstance(sContext)
                : BaiduRxLocationManager.getInstance(sContext);
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
    @SuppressWarnings("PMD.NullAssignment")
    public static void shutDown() {
        final RxLocationManager rxLocationManager = currentManager();
        if (rxLocationManager != null) {
            rxLocationManager.shutDown();
        }
        sContext = null;
        sClientType = Platform.TYPE_UNKNOWN;
    }
}
