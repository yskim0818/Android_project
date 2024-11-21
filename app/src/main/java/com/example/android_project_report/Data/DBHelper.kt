package com.example.android_project_report.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

data class UserData(
    var id: Long = 0,
    var name: String,
    var phone: String,
    var email: String,
    var workPlace: String,
    var jobTitle: String
)

class DBHelper(context: Context): SQLiteOpenHelper(context, "userDB", null, 1) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_WORK_PLACE = "workPlace"
        const val COLUMN_JOB_TITLE = "jobTitle"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_WORK_PLACE TEXT,
                $COLUMN_JOB_TITLE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllUsers(): List<UserData> {
        val userList = mutableListOf<UserData>()
        var cursor: android.database.Cursor? = null
        val db = this.readableDatabase
        try {
            cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor.moveToFirst() == true) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                    val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                    val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                    val workPlace =
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORK_PLACE))
                    val jobTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB_TITLE))

                    userList.add(UserData(id, name, phone, email, workPlace, jobTitle))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DBHelper", "Error retrieving users", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return userList

    }
}