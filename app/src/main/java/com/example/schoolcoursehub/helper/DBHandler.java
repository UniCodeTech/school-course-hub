package com.example.schoolcoursehub.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    private static final String DB_NAME = "schoolcoursehub-db";
    private static final int DB_VERSION = 2; // database version


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
    private static final String COLUMN_CURRENT_ENROLLMENT = "current_enrollment";
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
                + COLUMN_CURRENT_ENROLLMENT + " INTEGER DEFAULT 0, "
                + COLUMN_COURSE_BRANCH_ID + " INTEGER, "
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
        boolean x;
        x = insertBranch(db, "Colombo", 6.9063005, 79.8682518);
        x = insertBranch(db, "Matara", 5.9492564, 80.5437563);
        x = insertBranch(db, "Kandy", 7.2871104, 80.5913717);
        x = insertBranch(db, "Negombo", 7.2040113, 79.850386);
        x = insertBranch(db, "Jafna", 9.6668384, 80.0070977);
        x = insertBranch(db, "Kegalle", 7.2495909, 80.3405299);
        x = insertBranch(db, "Piliyandala", 6.7997972, 79.9213416);
        x = insertBranch(db, "Kottawa", 6.8413356, 79.9621716);


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

        insertPromotion(db,"M563432", 25.0);
        insertPromotion(db,"S663435", 40.0);
        insertPromotion(db,"L763434", 60.0);

        Log.d("DBHandler", "onCreate() called");

    }

    public void updateCourseTable(SQLiteDatabase db) {
        System.out.println("** ** ** updateCourseTable **");
        String CREATE_COURSE_TABLE_NEW = "CREATE TABLE " + TABLE_COURSE + "("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COURSE_NAME + " TEXT, "
                + COLUMN_COURSE_COST + " REAL, "
                + COLUMN_COURSE_DURATION + " TEXT, "
                + COLUMN_COURSE_MAX_PARTICIPANTS + " INTEGER, "
                + COLUMN_COURSE_STARTING_DATE + " TEXT, "
                + COLUMN_COURSE_REGISTRATION_CLOSING_DATE + " TEXT, "
                + COLUMN_COURSE_PUBLISH_DATE + " TEXT, "
                + COLUMN_COURSE_BRANCH_ID + " TEXT, "
                + COLUMN_CURRENT_ENROLLMENT + " INTEGER DEFAULT 0, " // New column
                + "FOREIGN KEY (" + COLUMN_COURSE_BRANCH_ID + ") REFERENCES " + TABLE_BRANCH + "(" + COLUMN_BRANCH_ID + ")"
                + ")";

        //db.execSQL("ALTER TABLE " + TABLE_COURSE + " RENAME TO temp_" + TABLE_COURSE);
        db.execSQL(CREATE_COURSE_TABLE_NEW);

        //db.execSQL("INSERT INTO " + TABLE_COURSE + " SELECT * FROM temp_" + TABLE_COURSE);
        //db.execSQL("DROP TABLE temp_" + TABLE_COURSE);
    }


    // get all branch data
    public List<String> getAllBranchNames() {
        List<String> branchNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = { COLUMN_BRANCH_NAME };
            cursor = db.query(
                    TABLE_BRANCH,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String branchName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRANCH_NAME));
                    branchNames.add(branchName);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return branchNames;
    }

    private void insertPromotion(SQLiteDatabase db, String promotionCode, double promotionDiscount) {
        ContentValues values = new ContentValues();
        values.put(PROMOTION_CODE, promotionCode);
        values.put(PROMOTION_DISCOUNT, promotionDiscount);
        db.insert(TABLE_PROMOTION, null, values);
    }


    public boolean insertBranch(SQLiteDatabase db, String branchName, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRANCH_NAME, branchName);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        long result = db.insert(TABLE_BRANCH, null, values);

        return result != -1;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    // login
    public boolean login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL_ADDRESS + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isLoggedIn = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return isLoggedIn;
    }

    public UserInfo getUserInfo(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserInfo userInfo = new UserInfo(); // Class to hold user ID and role
        Cursor cursor = null;

        try {
            String[] projection = { COLUMN_USERID, COLUMN_USER_ROLE };
            String selection = COLUMN_USER_EMAIL_ADDRESS + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
            String[] selectionArgs = { email, password };

            cursor = db.query(
                    TABLE_USERS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                userInfo.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USERID)));
                userInfo.setRole(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ROLE)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return userInfo;
    }


    public int getBranchId(String branchName){
        SQLiteDatabase db = this.getReadableDatabase();
        int branchId = 1;

        String[] projection = {COLUMN_BRANCH_ID};
        String selection = COLUMN_BRANCH_NAME + " = ?";
        String[] selectionArgs = {branchName};

        Cursor cursor = db.query(
                TABLE_BRANCH,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            branchId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BRANCH_ID));
            cursor.close();
        }

        db.close();
        return branchId;
    }

    // Getting all branches
    public List<Branch> getAllBranches() {
        List<Branch> branchList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BRANCH;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Branch branch = new Branch();
                branch.setBranchId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BRANCH_ID)));
                branch.setBranchName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRANCH_NAME)));
                branch.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)));
                branch.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)));

                branchList.add(branch);
            } while (cursor.moveToNext());
        }
        db.close();
        return branchList;
    }


    // Add a new course
    public long addCourse(String courseName, float courseCost, String courseDuration, int maxParticipants,
                          String courseStartDate, String courseRegistrationCloseDate, String publishDate, int courseBranchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_COST, courseCost);
        values.put(COLUMN_COURSE_DURATION, courseDuration);
        values.put(COLUMN_COURSE_MAX_PARTICIPANTS, maxParticipants);
        values.put(COLUMN_COURSE_STARTING_DATE, courseStartDate);
        values.put(COLUMN_COURSE_REGISTRATION_CLOSING_DATE, courseRegistrationCloseDate);
        values.put(COLUMN_COURSE_PUBLISH_DATE, publishDate);
        values.put(COLUMN_CURRENT_ENROLLMENT, 0);
        values.put(COLUMN_COURSE_BRANCH_ID, courseBranchId);
        long newRowId = db.insert(TABLE_COURSE, null, values);
        db.close();
        return newRowId;
    }


    // TODO: Get all courses
    public List<String> getAllUsers() {
        List<String> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {
                    COLUMN_USER_NAME,
                    COLUMN_USER_ADDRESS,
                    COLUMN_USER_LIVING_CITY,
                    COLUMN_USER_DATE_OF_BIRTH,
                    COLUMN_USER_NIC,
                    COLUMN_USER_EMAIL_ADDRESS,
                    COLUMN_USER_GENDER,
                    COLUMN_USER_MOBILE_NUMBER
            };

            // Filter results WHERE user_role != 'Admin'
            String selection = COLUMN_USER_ROLE + " != ?";
            String[] selectionArgs = { "Admin" };

            cursor = db.query(
                    TABLE_USERS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            // Loop through all rows and add user details to the list
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String userDetails = "Name: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)) + "\n" +
                            "Address: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ADDRESS)) + "\n" +
                            "Living City: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_LIVING_CITY)) + "\n" +
                            "Date of Birth: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_DATE_OF_BIRTH)) + "\n" +
                            "NIC: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NIC)) + "\n" +
                            "Email: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL_ADDRESS)) + "\n" +
                            "Gender: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_GENDER)) + "\n" +
                            "Mobile Number: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_MOBILE_NUMBER));

                    userList.add(userDetails);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userList;
    }


    // Method to get all courses from the database
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_COURSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURSE_ID)));
                course.setCourseName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME)));
                course.setCourseCost(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COURSE_COST)));
                course.setCourseDuration(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_DURATION)));
                course.setMaxParticipants(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURSE_MAX_PARTICIPANTS)));
                course.setStartingDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_STARTING_DATE)));
                course.setRegistrationClosingDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_REGISTRATION_CLOSING_DATE)));
                course.setPublishDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_PUBLISH_DATE)));
                course.setCurrentEnrollment(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CURRENT_ENROLLMENT)));
                course.setBranchId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURSE_BRANCH_ID)));

                // Adding course to list
                courseList.add(course);
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        // Return course list
        return courseList;
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

    // Fetch course details based on courseId
    public Course fetchCourseDetails(int courseId) {
        Course course = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_COURSE,
                    new String[]{COLUMN_COURSE_ID, COLUMN_COURSE_NAME, COLUMN_COURSE_COST,
                            COLUMN_COURSE_DURATION, COLUMN_COURSE_MAX_PARTICIPANTS, COLUMN_COURSE_STARTING_DATE,
                            COLUMN_COURSE_REGISTRATION_CLOSING_DATE, COLUMN_COURSE_PUBLISH_DATE,
                            COLUMN_CURRENT_ENROLLMENT, COLUMN_COURSE_BRANCH_ID},
                    COLUMN_COURSE_ID + "=?",
                    new String[]{String.valueOf(courseId)}, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                course = new Course(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COURSE_COST)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_DURATION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURSE_MAX_PARTICIPANTS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_STARTING_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_REGISTRATION_CLOSING_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_PUBLISH_DATE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CURRENT_ENROLLMENT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COURSE_BRANCH_ID))
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return course;
    }

}

