package com.example.schoolcoursehub.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    private static final String DB_NAME = "schoolcoursehub-db";
    private static final int DB_VERSION = 1; // database version

    // Creating Courses table Variables
    private static final String TABLE_COURSE = "course";
    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_COST = "course_cost";
    private static final String COLUMN_COURSE_DURATION = "course_duration";
    private static final String COLUMN_COURSE_MAX_PARTICIPANTS = "max_participants";
    private static final String COLUMN_COURSE_STARTING_DATE = "starting_date";
    private static final String COLUMN_COURSE_REGISTRATION_CLOSING_DATE = "registration_closing_date";
    private static final String COLUMN_COURSE_PUBLISH_DATE = "publish_date";
    private static final String COLUMN_COURSE_BRANCH_ID = "course_branch_id";

    // Creating Branch table Variables
    private static final String TABLE_BRANCH = "branch";
    private static final String COLUMN_BRANCH_ID = "branch_id";
    private static final String COLUMN_BRANCH_NAME = "branch_name";

    // Creating Users table Variables
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_NIC = "user_nic";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_LIVING_CITY = "user_living_city";
    private static final String COLUMN_USER_DATE_OF_BIRTH = "user_date_of_birth";
    private static final String COLUMN_USER_EMAIL_ADDRESS = "user_email_address";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_USER_MOBILE_NUMBER = "user_mobile_number";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_PROFILE_PICTURE = "user_profile_picture";

    // Create Course Registration Table Variables
    private static final String TABLE_COURSE_USERS = "course_users";
    private static final String COLUMN_COURSE_USER_ID = "course_user_id";
    private static final String COLUMN_COURSE_NO = "course_no";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_REGISTRATION_DATE = "user_register_date";
    private static final String COLUMN_PROMOTION_CODE = "promtion_code";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_TOTAL_FEE = "discounted_price";

    // Create User Location Table Variables
    private static final String TABLE_BRANCH_LOCATIONS = "branch_locations";
    private static final String COLUMN_LOCATION_ID = "location_id";
    private static final String COLUMN_BRANCH_NO = "branch_no";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    // creating a database tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the courses table
        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE + "("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_COURSE_NAME + " TEXT,"
                + COLUMN_COURSE_COST + " REAL,"
                + COLUMN_COURSE_DURATION + " TEXT,"
                + COLUMN_COURSE_MAX_PARTICIPANTS + " INTEGER"
                + COLUMN_COURSE_STARTING_DATE + " TEXT"
                + COLUMN_COURSE_REGISTRATION_CLOSING_DATE + " TEXT"
                + COLUMN_COURSE_PUBLISH_DATE + " TEXT"
                + COLUMN_COURSE_BRANCH_ID + "TEXT"
                + "FOREIGN KEY (" + COLUMN_COURSE_BRANCH_ID + ") REFERENCES " + TABLE_BRANCH + "(" + COLUMN_BRANCH_ID
                + "),"
                + ")";
        db.execSQL(CREATE_COURSE_TABLE);

        // Create the Branch table
        String CREATE_BRANCH_TABLE = "CREATE TABLE " + TABLE_BRANCH + "("
                + COLUMN_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRANCH_NAME + " TEXT,"
                + ")";
        db.execSQL(CREATE_BRANCH_TABLE);

        // Create the Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_NIC + " TEXT PRIMARY KEY,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_ADDRESS + " TEXT,"
                + COLUMN_USER_LIVING_CITY + " TEXT,"
                + COLUMN_USER_DATE_OF_BIRTH + " TEXT,"
                + COLUMN_USER_EMAIL_ADDRESS + " TEXT,"
                + COLUMN_USER_GENDER + " TEXT,"
                + COLUMN_USER_MOBILE_NUMBER + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_PROFILE_PICTURE + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // create the Course Registration table
        String CREATE_COURSE_USERS_TABLE = "CREATE TABLE " + TABLE_COURSE_USERS + "("
                + COLUMN_COURSE_USER_ID + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + COLUMN_COURSE_NO + " INTEGER,"
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_USER_REGISTRATION_DATE + " TEXT,"
                + COLUMN_PROMOTION_CODE + "TEXT"
                + COLUMN_DISCOUNT + "REAL"
                + COLUMN_TOTAL_FEE + "REAL"
                + "FOREIGN KEY (" + COLUMN_COURSE_NO + ") REFERENCES " + TABLE_COURSE + "(" + COLUMN_COURSE_ID + "),"
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_NIC + ")"
                + ")";

        // Create Location Table
        String CREATE_BRANCH_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_BRANCH_LOCATIONS + "("
                + COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRANCH_NO + " INTEGER,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL"
                + "FOREIGN KEY (" + COLUMN_BRANCH_NO + ") REFERENCES " + TABLE_BRANCH + "(" + COLUMN_BRANCH_ID
                + "),"
                + ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed and create fresh
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_USERS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH_LOCATIONS);
        onCreate(db);
    }

    // Add a new course
    public long addCourse(String courseName, float courseCost, String courseDuration, int maxParticipants,
            String courseStartDate, String courseRegistrationCloseDate, String publishDate, String courseBranchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_COST, courseCost);
        values.put(COLUMN_COURSE_DURATION, courseDuration);
        values.put(COLUMN_COURSE_MAX_PARTICIPANTS, maxParticipants);
        values.put(COLUMN_COURSE_STARTING_DATE, courseStartDate);
        values.put(COLUMN_COURSE_REGISTRATION_CLOSING_DATE, courseRegistrationCloseDate);
        values.put(COLUMN_COURSE_PUBLISH_DATE, publishDate);
        values.put(COLUMN_COURSE_BRANCH_ID, courseBranchId);
        long newRowId = db.insert(TABLE_COURSE, null, values);
        db.close();
        return newRowId;
    }

    // Get all courses
    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COURSE, null, null, null, null, null, null);
        return cursor;
    }

    // Update course
    public int updateCourse(long courseId, String courseName, float courseCost, String courseDuration,
            int maxParticipants, String courseStartDate, String courseRegistrationCloseDate, String publishDate,
            String courseBranchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_COST, courseCost);
        values.put(COLUMN_COURSE_DURATION, courseDuration);
        values.put(COLUMN_COURSE_MAX_PARTICIPANTS, maxParticipants);
        values.put(COLUMN_COURSE_STARTING_DATE, courseStartDate);
        values.put(COLUMN_COURSE_REGISTRATION_CLOSING_DATE, courseRegistrationCloseDate);
        values.put(COLUMN_COURSE_PUBLISH_DATE, publishDate);
        values.put(COLUMN_COURSE_BRANCH_ID, courseBranchId);
        int rowsAffected = db.update(TABLE_COURSE, values, COLUMN_COURSE_ID + " = ?",
                new String[] { String.valueOf(courseId) });
        db.close();
        return rowsAffected;
    }

    // Delete course
    public int deleteCourse(long courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_COURSE, COLUMN_COURSE_ID + " = ?", new String[] { String.valueOf(courseId) });
        db.close();
        return rowsDeleted;
    }
}