package jianqiang.com.core.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

import jianqiang.com.core.engine.ProductConfigManager;

class ActivityThreadHandlerCallback implements Handler.Callback {

    Handler mBase;

    public ActivityThreadHandlerCallback(Handler base) {
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {
            // ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
            case 100:
                handleLaunchActivity(msg);
                break;
        }

        mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        Object obj = msg.obj;

        try {
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);

            String packageName = raw.getComponent().getPackageName();
            String className = raw.getComponent().getClassName();
            String shortClassName = className.substring(className.lastIndexOf('.') + 1);

            //找不到就走正常逻辑
            String newClassName = ProductConfigManager.findMap(shortClassName);
            if(newClassName == null)
                return;

            ComponentName componentName = new ComponentName(packageName, newClassName);
            raw.setComponent(componentName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
