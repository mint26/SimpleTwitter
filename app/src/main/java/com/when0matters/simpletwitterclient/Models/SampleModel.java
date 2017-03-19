package com.when0matters.simpletwitterclient.Models;

/**
 * Created by dongdong on 3/13/2017.
 */

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.when0matters.simpletwitterclient.Global.SimpleTwitterClientDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the DBFlow wiki for more details:
 * https://github.com/codepath/android_guides/wiki/DBFlow-Guide
 *
 * Note: All models **must extend from** `BaseModel` as shown below.
 *
 */
@Table(database = SimpleTwitterClientDatabase.class)
public class SampleModel extends BaseModel {

    @PrimaryKey
    @Column
    Long id;

    // Define table fields
    @Column
    private String name;

    public SampleModel() {
        super();
    }

    // Parse model from JSON
    public SampleModel(JSONObject object){
        super();

        try {
            this.name = object.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

	/* The where class in this code below will be marked red until you first compile the project, since the code
	 * for the SampleModel_Table class is generated at compile-time.
	 */

    // Record Finders
//    public static SampleModel byId(long id) {
//        return new Select().from(SampleModel.class).where("id = ?", id).querySingle();
//    }
//
//    public static List<SampleModel> recentItems() {
//        return new Select().from(SampleModel.class).orderBy("id DESC").limit(300).queryList();
//    }
}

