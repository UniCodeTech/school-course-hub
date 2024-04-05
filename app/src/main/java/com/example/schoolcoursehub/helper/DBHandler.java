package com.example.schoolcoursehub.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";


    // Creating Users table Variables
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERID = "user_id";
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
    private static final String COLUMN_USER_ROLE = "user_role";


    // Create Course Registration Table Variables
    private static final String TABLE_COURSE_USERS = "course_users";
    private static final String COLUMN_COURSE_USER_ID = "course_user_id";
    private static final String COLUMN_COURSE_NO = "course_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_REGISTRATION_DATE = "user_register_date";
    private static final String COLUMN_PROMOTION_CODE = "promotion_code";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_TOTAL_FEE = "discounted_price";

    // Promotion Code
    private static final String TABLE_PROMOTION = "promotion";
    private static final String PROMOTION_ID = "promotion_id";
    private static final String PROMOTION_CODE = "promotion_code";
    private static final String PROMOTION_DISCOUNT = "promotion_discount";



    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    // creating a database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the courses table
        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE + "("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COURSE_NAME + " TEXT, "
                + COLUMN_COURSE_COST + " REAL, "
                + COLUMN_COURSE_DURATION + " TEXT, "
                + COLUMN_COURSE_MAX_PARTICIPANTS + " INTEGER, "
                + COLUMN_COURSE_STARTING_DATE + " TEXT, "
                + COLUMN_COURSE_REGISTRATION_CLOSING_DATE + " TEXT, "
                + COLUMN_COURSE_PUBLISH_DATE + " TEXT, "
                + COLUMN_COURSE_BRANCH_ID + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_COURSE_BRANCH_ID + ") REFERENCES " + TABLE_BRANCH + "(" + COLUMN_BRANCH_ID + ")"
                + ")";
        db.execSQL(CREATE_COURSE_TABLE);

        // Create the Branch table
        String CREATE_BRANCH_TABLE = "CREATE TABLE " + TABLE_BRANCH + "("
                + COLUMN_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRANCH_NAME + " TEXT,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL"
                + ")";
        db.execSQL(CREATE_BRANCH_TABLE);
        // Insert 8 branches details
        insertBranch(db, "Colombo", 6.9063005, 79.8682518);
        insertBranch(db, "Matara", 5.9492564, 80.5437563);
        insertBranch(db, "Kandy", 7.2871104, 80.5913717);
        insertBranch(db, "Negombo", 7.2040113, 79.850386);
        insertBranch(db, "Jafna", 9.6668384, 80.0070977);
        insertBranch(db, "Kegalle", 7.2495909, 80.3405299);
        insertBranch(db, "Piliyandala", 6.7997972, 79.9213416);
        insertBranch(db, "Kottawa", 6.8413356, 79.9621716);

        // Create the Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_ADDRESS + " TEXT,"
                + COLUMN_USER_LIVING_CITY + " TEXT,"
                + COLUMN_USER_DATE_OF_BIRTH + " TEXT,"
                + COLUMN_USER_NIC + " TEXT UNIQUE,"
                + COLUMN_USER_EMAIL_ADDRESS + " TEXT,"
                + COLUMN_USER_GENDER + " TEXT,"
                + COLUMN_USER_MOBILE_NUMBER + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_PROFILE_PICTURE + " TEXT,"
                + COLUMN_USER_ROLE + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        // Add admin user
        ContentValues adminValues = new ContentValues();
        adminValues.put(COLUMN_USER_NAME, "Admin");
        adminValues.putNull(COLUMN_USER_ADDRESS);
        adminValues.putNull(COLUMN_USER_LIVING_CITY);
        adminValues.putNull(COLUMN_USER_DATE_OF_BIRTH);
        adminValues.putNull(COLUMN_USER_NIC);
        adminValues.put(COLUMN_USER_EMAIL_ADDRESS, "admin");
        adminValues.putNull(COLUMN_USER_GENDER);
        adminValues.putNull(COLUMN_USER_MOBILE_NUMBER);
        adminValues.put(COLUMN_USER_PASSWORD, "admin");
        adminValues.putNull(COLUMN_USER_PROFILE_PICTURE);
        adminValues.put(COLUMN_USER_ROLE, "Admin");
        db.insert(TABLE_USERS, null, adminValues);


        // Create the Course Registration table
        String CREATE_COURSE_USERS_TABLE = "CREATE TABLE " + TABLE_COURSE_USERS + "("
                + COLUMN_COURSE_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COURSE_NO + " INTEGER, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_USER_REGISTRATION_DATE + " TEXT, "
                + COLUMN_PROMOTION_CODE + " TEXT, "
                + COLUMN_DISCOUNT + " REAL, "
                + COLUMN_TOTAL_FEE + " REAL, "
                + "FOREIGN KEY (" + COLUMN_COURSE_NO + ") REFERENCES " + TABLE_COURSE + "(" + COLUMN_COURSE_ID + "), "
                + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERID + ")"
                + ")";
        db.execSQL(CREATE_COURSE_USERS_TABLE);

        String CREATE_PROMOTION_TABLE = "CREATE TABLE " + TABLE_PROMOTION + "("
                + PROMOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PROMOTION_CODE + " TEXT UNIQUE, "
                + PROMOTION_DISCOUNT + " REAL"
                + ")";
        db.execSQL(CREATE_PROMOTION_TABLE);


    }

    private void insertBranch(SQLiteDatabase db, String branchName, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRANCH_NAME, branchName);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.insert(TABLE_BRANCH, null, values);
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
    }

    // insert user
    public boolean insertUser(String name, String address, String livingCity, String dateOfBirth, String nic, String emailAddress,
                               String gender, String mobileNumber, String password, String profilePicture, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_ADDRESS, address);
        values.put(COLUMN_USER_LIVING_CITY, livingCity);
        values.put(COLUMN_USER_DATE_OF_BIRTH, dateOfBirth);
        values.put(COLUMN_USER_NIC, nic);
        values.put(COLUMN_USER_EMAIL_ADDRESS, emailAddress);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_MOBILE_NUMBER, mobileNumber);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_PROFILE_PICTURE, profilePicture);
        values.put(COLUMN_USER_ROLE, role);

        long newRowId = db.insert(TABLE_USERS, null, values);

        db.close();

        return newRowId != -1;
    }

    public boolean isUniqueNIC(String nic) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isUnique = true;

        try {
            String[] projection = {COLUMN_USER_NIC};

            String selection = COLUMN_USER_NIC + " = ?";
            String[] selectionArgs = {nic};

            cursor = db.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null);

            // Check if the cursor has any rows
            if (cursor != null && cursor.getCount() > 0) {
                // NIC already exists
                isUnique = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return isUnique;
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

    // TODO: Get all courses

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