package carsbyjamal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class DataBase extends SQLiteOpenHelper {
    @NotNull
    private Context context;
    @NotNull
    private static final String DATABASENAME = "MessagesDB";
    private  static final int VERSION_NUM = 5;
    @NotNull
    private static final String TABLENAME = "Cars";
    @NotNull
    private static final String COL_ID = "_ID";
    @NotNull
    private static final String COL_MODELID = "MODEL_ID";
    @NotNull
    private static final String COL_MODEL = "MODEL";
    @NotNull
    private static final String COL_MAKEID = "MAKE_ID";
    @NotNull
    private static final String COL_MAKE = "MAKE";

    public DataBase(Context context) {
        super(context, DATABASENAME, null, VERSION_NUM);
    }

    public void onCreate(@Nullable SQLiteDatabase db) {

        db.execSQL("Create TABLE " + TABLENAME + ' ' + '(' + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_MAKEID + " TEXT, " + COL_MAKE + " TEXT, " + COL_MODELID + " TEXT, " + COL_MODEL + " TEXT);");


    }

    public void onUpgrade(@Nullable SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);


        this.onCreate(db);
    }

    public void onDowngrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        this.onCreate(db);
    }

    @Nullable
    public final Long insertData(@Nullable String model, @Nullable String modelId, @Nullable String make, @Nullable String makeId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MAKEID, makeId);
        contentValues.put(COL_MAKE, make);
        contentValues.put(COL_MODELID, modelId);
        contentValues.put(COL_MODEL, model);
        return database.insert(TABLENAME, (String) null, contentValues);
    }

    @Nullable
    public final Integer deleteData(@Nullable String modelId) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] whereArgs = new String[]{modelId};
        return database.delete(TABLENAME, COL_MODELID + "=?", whereArgs);
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    public final void setContext(@NotNull Context var1) {
        this.context = var1;
    }

    public static final class getCars {
        @NotNull
        public final String getDATABASENAME() {
            return DataBase.DATABASENAME;
        }

        public final int getVERSION_NUM() {
            return DataBase.VERSION_NUM;
        }

        @NotNull
        public static final String getTABLENAME() {
            return DataBase.TABLENAME;
        }

        @NotNull
        public final String getCOL_ID() {
            return DataBase.COL_ID;
        }

        @NotNull
        public static final String getCOL_MODELID() {
            return DataBase.COL_MODELID;
        }

        @NotNull
        public static final String getCOL_MODEL() {
            return DataBase.COL_MODEL;
        }

        @NotNull
        public static final String getCOL_MAKEID() {
            return DataBase.COL_MAKEID;
        }

        @NotNull
        public static final String getCOL_MAKE() {
            return DataBase.COL_MAKE;
        }

        private getCars() {
        }
    }
}
