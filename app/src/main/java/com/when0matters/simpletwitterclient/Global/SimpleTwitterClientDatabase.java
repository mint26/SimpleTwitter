package com.when0matters.simpletwitterclient.Global;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by dongdong on 3/13/2017.
 */

@Database(name = SimpleTwitterClientDatabase.NAME, version = SimpleTwitterClientDatabase.VERSION)
public class SimpleTwitterClientDatabase {

    public static final String NAME = "SimpleTwitterClientDatabase";
    public static final int VERSION = 1;
}
