package net.sarazan.androidversions;

/**
 * Created by Aaron Sarazan on 6/22/14
 * Copyright(c) 2014 Manotaur, LLC.
 */
public abstract class AndroidMigration {

    // Run migration on any version before and including this one.
    public final int version;

    public AndroidMigration(int version) {
        this.version = version;
    }

    public abstract void run(int previous);
}
