package net.sarazan.androidversions;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import net.sarazan.prefs.Pref;
import net.sarazan.prefs.Prefs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Aaron Sarazan on 6/22/14
 * Copyright(c) 2014 Manotaur, LLC.
 */
public final class AndroidVersions {

    private static final String TAG = "Versions";

    private static final String KEY = "sarazan_versions_last";

    private AndroidVersions() {}

    @Nullable
    public static Integer migrate(@NotNull Context c, @NotNull List<AndroidMigration> migrations) {
        int versionCode = getVersionCode(c);
        Pref<Integer> pref = Prefs.sharedPreference(c, KEY, Integer.class);
        Integer lastVersionCode = pref.get();
        if (lastVersionCode != null && lastVersionCode != versionCode) {
            for (AndroidMigration m : migrations) {
                if (m.version <= lastVersionCode) {
                    m.run(lastVersionCode);
                }
            }
        }
        pref.put(versionCode, true);
        return lastVersionCode;
    }

    public static int getVersionCode(@NotNull Context c) {
        PackageManager manager = c.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Nullable
    public static String getVersionName(@NotNull Context c) {
        PackageManager manager = c.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
