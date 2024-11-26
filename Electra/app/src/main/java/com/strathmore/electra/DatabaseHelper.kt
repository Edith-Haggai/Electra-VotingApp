package com.strathmore.electra

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.mindrot.jbcrypt.BCrypt

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "electra.db"
        private const val DATABASE_VERSION = 3

        // Polls table
        private const val TABLE_POLLS = "polls"
        private const val COLUMN_POLL_ID = "poll_id"
        private const val COLUMN_VOTING_CODE = "voting_code"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_END_TIME = "end_time"

        // Candidates table
        private const val TABLE_CANDIDATES = "candidates"
        private const val COLUMN_CANDIDATE_ID = "id"
        private const val COLUMN_CANDIDATE_NAME = "name"
        private const val COLUMN_POSITION = "position"
        private const val COLUMN_MANIFESTO = "manifesto"
        private const val COLUMN_POLL_ID_FK = "poll_id"

        // Users table (for registration and login)
        const val TABLE_USERS = "users"
        const val COL_ID = "id"
        const val COL_FIRST_NAME = "first_name"
        const val COL_LAST_NAME = "last_name"
        const val COL_EMAIL = "email"
        const val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create Polls table
        val createPollsTableQuery = """
            CREATE TABLE $TABLE_POLLS (
                $COLUMN_POLL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_VOTING_CODE TEXT NOT NULL,
                $COLUMN_DURATION TEXT NOT NULL,
                $COLUMN_END_TIME INTEGER NOT NULL
            )
        """.trimIndent()

        // Create Candidates table
        val createCandidatesTableQuery = """
            CREATE TABLE $TABLE_CANDIDATES (
                $COLUMN_CANDIDATE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CANDIDATE_NAME TEXT NOT NULL,
                $COLUMN_POSITION TEXT NOT NULL,
                $COLUMN_MANIFESTO TEXT NOT NULL,
                $COLUMN_POLL_ID_FK INTEGER NOT NULL,
                FOREIGN KEY($COLUMN_POLL_ID_FK) REFERENCES $TABLE_POLLS($COLUMN_POLL_ID)
            )
        """.trimIndent()

        // Create Users table
        val createUsersTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_FIRST_NAME TEXT,
                $COL_LAST_NAME TEXT,
                $COL_EMAIL TEXT UNIQUE,
                $COL_PASSWORD TEXT
            )
        """.trimIndent()

        db.execSQL(createPollsTableQuery)
        db.execSQL(createCandidatesTableQuery)
        db.execSQL(createUsersTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            // Example: You can alter tables or add new columns in future versions
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN new_column TEXT")
        }
    }

    // Polls Table Operations
    fun insertPoll(votingCode: String, duration: String): Long {
        val db = writableDatabase
        val values = ContentValues()

        // Parse the duration into milliseconds
        val durationMillis = when (duration) {
            "30 minutes" -> 30 * 60 * 1000
            "2 hours" -> 2 * 60 * 60 * 1000
            "6 hours" -> 6 * 60 * 60 * 1000
            "24 hours" -> 24 * 60 * 60 * 1000
            else -> 0
        }

        // Calculate the end time
        val endTime = System.currentTimeMillis() + durationMillis

        values.put(COLUMN_VOTING_CODE, votingCode)
        values.put(COLUMN_DURATION, duration)
        values.put(COLUMN_END_TIME, endTime)

        val pollId = db.insert(TABLE_POLLS, null, values)
        db.close()
        return pollId
    }

    fun getPollEndTime(pollId: Long): Long {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_END_TIME FROM $TABLE_POLLS WHERE $COLUMN_POLL_ID = ?",
            arrayOf(pollId.toString())
        )

        var endTime = 0L
        if (cursor.moveToFirst()) {
            endTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_END_TIME))
        }

        cursor.close()
        db.close()
        return endTime
    }

    fun isPollActive(pollId: Long): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_END_TIME FROM $TABLE_POLLS WHERE $COLUMN_POLL_ID = ?",
            arrayOf(pollId.toString())
        )

        var isActive = false
        if (cursor.moveToFirst()) {
            val endTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_END_TIME))
            isActive = System.currentTimeMillis() < endTime
        }

        cursor.close()
        db.close()
        return isActive
    }

    // Candidates Table Operations
    fun insertCandidate(name: String, position: String, manifesto: String, pollId: Long): Long {
        val db = writableDatabase
        val values = ContentValues()

        values.put(COLUMN_CANDIDATE_NAME, name)
        values.put(COLUMN_POSITION, position)
        values.put(COLUMN_MANIFESTO, manifesto)
        values.put(COLUMN_POLL_ID_FK, pollId)

        val candidateId = db.insert(TABLE_CANDIDATES, null, values)
        db.close()
        return candidateId
    }

    // Users Table Operations (Registration and Login)
    fun insertUser(firstName: String, lastName: String, email: String, password: String): Long {
        val db = writableDatabase
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        val values = ContentValues().apply {
            put(COL_FIRST_NAME, firstName)
            put(COL_LAST_NAME, lastName)
            put(COL_EMAIL, email)
            put(COL_PASSWORD, hashedPassword)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun getUserByEmailAndPassword(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT $COL_PASSWORD FROM $TABLE_USERS WHERE $COL_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        if (cursor.moveToFirst()) {
            val hashedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
            cursor.close()
            return BCrypt.checkpw(password, hashedPassword)
        }

        cursor.close()
        return false
    }
}
