package net.sarazan.androidversions;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;

import net.sarazan.prefs.SharedPref;

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

    /**
     * Will check last known app version, and run any migrations added after that number.
     * @param c context
     * @param migrations list of AndroidMigration objects
     * @return the app version we upgraded FROM, or null if there was no tracked previous version.
     */
    @Nullable
    public static Integer migrate(@NotNull Context c, @NotNull List<AndroidMigration> migrations) {
        int versionCode = getVersionCode(c);
        Integer lastVersionCode = getLastVersionCode(c);
        if (lastVersionCode != null && lastVersionCode != versionCode) {
            for (AndroidMigration m : migrations) {
                if (lastVersionCode < m.versionAdded) {
                    m.run(lastVersionCode);
                }
            }
        }
        putVersionCode(c, versionCode);
        return lastVersionCode;
    }

    @Nullable
    public static Integer getLastVersionCode(@NotNull Context c) {
        return new SharedPref<>(KEY, Integer.class).get(c);
    }

    private static void putVersionCode(@NotNull Context c, int version) {
        new SharedPref<>(KEY, Integer.class).put(c, version, true);
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

    public static void whenApi(int atLeast, Runnable run) {
        if (VERSION.SDK_INT >= atLeast) {
            run.run();
        }
    }
}
