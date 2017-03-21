package rxlocation.github.pitt;

import com.amap.api.location.AMapLocationClientOption;
import com.baidu.location.LocationClientOption;

/**
 * Created by pitt on 2017/3/16.
 */

/**
 * 对百度和高德ClientOption的封装,使用时其中一个Option为null
 */
public class ClientOption {
    //百度Client的Option
    private LocationClientOption mBaiduOption;
    //高德Client的Option
    private AMapLocationClientOption mAmapOption;

    public ClientOption(final LocationClientOption option) {
        mBaiduOption = option;
    }

    public ClientOption(final AMapLocationClientOption option) {
        mAmapOption = option;
    }

    public LocationClientOption getBaiduOption() {
        return mBaiduOption;
    }


    public AMapLocationClientOption getAmapOption() {
        return mAmapOption;
    }


}
