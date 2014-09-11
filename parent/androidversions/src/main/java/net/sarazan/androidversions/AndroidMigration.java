package net.sarazan.androidversions;

/**
 * Created by Aaron Sarazan on 6/22/14
 * Copyright(c) 2014 Manotaur, LLC.
 */
public abstract class AndroidMigration {

    /**
     * The app version at which the migration was added.
     */
    public final int versionAdded;

    /**
     * Create a migration that will fire if upgrading from a version < versionAdded
     * @param versionAdded the version when the migration was added
     */
    public AndroidMigration(int versionAdded) {
        this.versionAdded = versionAdded;
    }

    /**
     * Provide any runnable logic here, e.g. post a notification, migrate schemas, etc.
     * @param previous version we're upgrading from
     */
    public abstract void run(int previous);
}
