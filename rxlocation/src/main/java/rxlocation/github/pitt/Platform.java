package rxlocation.github.pitt;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pitt on 2017/3/16.
 */

/**
 * 使用的Client平台信息.
 */
public final class Platform {
    //Platform 百度
    public static final int TYPE_BAIDU = 0;
    //Platform 高德
    public static final int TYPE_AMAP = 1;

    private Platform() {
    }

    @IntDef({TYPE_BAIDU, TYPE_AMAP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
}
