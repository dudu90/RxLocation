package rxlocation.github.pitt;

import com.amap.api.location.AMapLocationClientOption;
import com.baidu.location.LocationClientOption;

/**
 * Created by pitt on 2017/3/17.
 */

/**
 * ClientOption的工厂类
 */
public class ClientOptionFactory {

    /**
     * 创建一个默认的包含百度ClientOption的ClientOption
     *
     * @return A ClientOption
     */
    public ClientOption newDefaultBaiduOption() {
        return createBaiduOption(getDefaultLocationClientOption());
    }

    /**
     * 创建一个包含默认的高德ClientOption的ClientOption
     *
     * @return A ClientOption
     */
    public ClientOption newDefaultAMapOption() {
        return createAMapOption(getAMapDefaultOption());
    }

    /**
     * 创建百度ClientOption的工厂方法
     *
     * @param option 百度ClientOption 详情请查看{ http://wiki.lbsyun.baidu.com/cms/androidloc/doc/v7.1/index.html }
     * @return ClientOption
     */
    public ClientOption createBaiduOption(final LocationClientOption option) {
        return new ClientOption(option);
    }

    /**
     * 创建高德ClientOption的工厂方法
     *
     * @param option 高德地图的ClientOption 详情请查看{ http://wiki.lbsyun.baidu.com/cms/androidloc/doc/v7.1/index.html }
     * @return ClientOption
     */
    public ClientOption createAMapOption(final AMapLocationClientOption option) {
        return new ClientOption(option);
    }


    /***
     * @return Default LocationClientOption
     * 详情请查看{ http://wiki.lbsyun.baidu.com/cms/androidloc/doc/v7.1/index.html }
     */
    private LocationClientOption getDefaultLocationClientOption() {
        final LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setNeedDeviceDirect(false);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.SetIgnoreCacheException(false);
        option.setIsNeedAltitude(false);
        return option;
    }

    /**
     * @return Default AMapLocationClientOption
     * 详情请查看{ http://amappc.cn-hangzhou.oss-pub.aliyun-inc.com/lbs/static/unzip/Android_Location_Doc/index.html }
     */
    private AMapLocationClientOption getAMapDefaultOption() {
        final AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mOption.setGpsFirst(false);
        mOption.setHttpTimeOut(30000);
        mOption.setInterval(2000);
        mOption.setNeedAddress(true);
        mOption.setOnceLocation(false);
        mOption.setOnceLocationLatest(false);
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        mOption.setSensorEnable(false);
        mOption.setWifiScan(true);
        mOption.setLocationCacheEnable(true);
        return mOption;
    }

}
