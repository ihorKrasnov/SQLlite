import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.clientslite.Client

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "clients.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_CLIENTS = "clients"
        const val COLUMN_ID = "_id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PURCHASE_HISTORY = "purchase_history"
        const val COLUMN_ORDER_COUNT = "order_count"
        const val COLUMN_DISCOUNT = "discount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_CLIENTS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_FIRST_NAME TEXT, "
                + "$COLUMN_LAST_NAME TEXT, "
                + "$COLUMN_EMAIL TEXT, "
                + "$COLUMN_PURCHASE_HISTORY TEXT, "
                + "$COLUMN_ORDER_COUNT INTEGER, "
                + "$COLUMN_DISCOUNT REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
        onCreate(db)
    }

    // Додати клієнта
    fun addClient(client: Client) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, client.firstName)
            put(COLUMN_LAST_NAME, client.lastName)
            put(COLUMN_EMAIL, client.email)
            put(COLUMN_PURCHASE_HISTORY, client.purchaseHistory)
            put(COLUMN_ORDER_COUNT, client.orderCount)
            put(COLUMN_DISCOUNT, client.discount)
        }
        db.insert(TABLE_CLIENTS, null, values)
        db.close()
    }

    // Отримати всіх клієнтів
    fun getAllClients(): List<Client> {
        val clients = mutableListOf<Client>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CLIENTS", null)

        if (cursor.moveToFirst()) {
            do {
                val client = Client(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_HISTORY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_COUNT)),
                    cursor.getFloat(cursor.getColumnIndex(COLUMN_DISCOUNT))
                )
                clients.add(client)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clients
    }

    // Оновити клієнта
    fun updateClient(client: Client) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, client.firstName)
            put(COLUMN_LAST_NAME, client.lastName)
            put(COLUMN_EMAIL, client.email)
            put(COLUMN_PURCHASE_HISTORY, client.purchaseHistory)
            put(COLUMN_ORDER_COUNT, client.orderCount)
            put(COLUMN_DISCOUNT, client.discount)
        }
        db.update(TABLE_CLIENTS, values, "$COLUMN_ID = ?", arrayOf(client.id.toString()))
        db.close()
    }

    // Видалити клієнта
    fun deleteClient(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CLIENTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

}
