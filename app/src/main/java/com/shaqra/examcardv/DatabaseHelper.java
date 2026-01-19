package com.shaqra.examcardv;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_REMEMBER_ME = "remember_me";

    private static final String ATTENDANCE_TABLE_NAME = "Students";
    private static final String COLUMN_STUD_NAME = "studname";
    private static final String COLUMN_CLASS_NAME = "classname";
    private static final String COLUMN_ATTEND_DAYS = "attenddays";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_REMEMBER_ME + " INTEGER)";
        db.execSQL(createTableQuery);

        String createAttendanceTableQuery = "CREATE TABLE " + ATTENDANCE_TABLE_NAME + " (" +
                COLUMN_STUD_NAME + " TEXT, " +
                COLUMN_CLASS_NAME + " TEXT, " +
                COLUMN_ATTEND_DAYS + " INTEGER)";
        db.execSQL(createAttendanceTableQuery);

        // إضافة البيانات الأولية للمستخدم
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, "admin");//
        contentValues.put(COLUMN_PASSWORD, "1234");
        contentValues.put(COLUMN_REMEMBER_ME, 0);


        db.insert(TABLE_NAME, null, contentValues);


        // إضافة بيانات حضور الطالبات
        insertStudentAttendance(db, "أميرة", "الصف الثاني", 20);
        insertStudentAttendance(db, "نورة", "الصف الأول", 18);
        insertStudentAttendance(db, "لمى", "الصف الثالث", 22);
        insertStudentAttendance(db, "ريم", "الصف الرابع", 17);

        // إضافة بيانات الطلاب
        insertStudent(db, "أميرة علي", 123456, "المستوى 1", "علوم الحاسوب", "هندسة البرمجيات");
        insertStudent(db, "نورة سلمان", 789012, "المستوى 2", "إدارة الأعمال", "التسويق");
        insertStudent(db, "ريم حسن", 345678, "المستوى 3", "الهندسة", "الهندسة الميكانيكية");
        insertStudent(db, "سارة جمال", 901234, "المستوى 2", "علم النفس", "الإرشاد");
        insertStudent(db, "لينا أحمد", 567890, "المستوى 3", "الطب", "الطب العام");
        insertStudent(db, "نور محمد", 456789, "المستوى 1", "الفنون الجميلة", "الرسم");
    }// 123456 أميرة علي
    //789012 نورة سلمان
    //345678 ريم حسن
    //901234 سارة جمال
    //567890 لينا أحمد
    // 456789 نور محمد

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        String dropAttendanceTableQuery = "DROP TABLE IF EXISTS " + ATTENDANCE_TABLE_NAME;
        db.execSQL(dropAttendanceTableQuery);
        onCreate(db);
    }

    public void insertStudentAttendance(SQLiteDatabase db, String studName, String className, int attendDays) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUD_NAME, studName);
        contentValues.put(COLUMN_CLASS_NAME, className);
        contentValues.put(COLUMN_ATTEND_DAYS, attendDays);
        db.insert(ATTENDANCE_TABLE_NAME, null, contentValues);
    }

    private void insertStudent(SQLiteDatabase db, String studname, int universityID, String level, String faculty, String specialization) {
        ContentValues values = new ContentValues();
        values.put("studname", studname);
        values.put("UniversityID", universityID);
        values.put("level", level);
        values.put("Faculty", faculty);
        values.put("Specialization", specialization);
        db.insert("students", null, values);
    }
}
