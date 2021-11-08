package androidx.appcompat.app;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.core.view.LayoutInflaterCompat;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
// 这个类要放在 androidx.appcompat.app 下与 AppCompatDelegateImpl 同样的报下，不然无法继承。
public class SkinAppCompatDelegateImpl extends AppCompatDelegateImpl {
    private static Map<Activity, WeakReference<AppCompatDelegate>> sDelegateMap = new WeakHashMap<>();

    public static AppCompatDelegate get(Activity activity, AppCompatCallback callback) {
        // 一个 Activity 对应一个 AppCompatDelegate 对象
        // 享元模式的应用
        WeakReference<AppCompatDelegate> delegateRef = sDelegateMap.get(activity);
        AppCompatDelegate delegate = (delegateRef == null ? null : delegateRef.get());
        if (delegate == null) {
            delegate = new SkinAppCompatDelegateImpl(activity, activity.getWindow(), callback);
            sDelegateMap.put(activity, new WeakReference<>(delegate));
        }
        return delegate;
    }

    private SkinAppCompatDelegateImpl(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
    }

    @Override
    public void installViewFactory() {
        // 这里要空实现，因为在初始化的时候已经设置了工厂了以及onActivityCreated回调中会设置工厂的。
        // https://github.com/ximsfei/Android-skin-support/issues/460
//        try {
//            LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
//            if (layoutInflater.getFactory() == null) {
//                LayoutInflaterCompat.setFactory2(layoutInflater, this);
//            } else if (!(layoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
//                Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
