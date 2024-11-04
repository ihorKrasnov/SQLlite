package com.example.clientslite

import DatabaseHelper
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddClientActivity : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var purchaseHistoryEditText: EditText
    private lateinit var orderCountEditText: EditText
    private lateinit var discountEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        // Отримуємо посилання на елементи UI
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        purchaseHistoryEditText = findViewById(R.id.purchaseHistoryEditText)
        orderCountEditText = findViewById(R.id.orderCountEditText)
        discountEditText = findViewById(R.id.discountEditText)
        saveButton = findViewById(R.id.saveButton)

        dbHelper = DatabaseHelper(this)

        saveButton.setOnClickListener {
            // Збираємо дані з полів введення
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val purchaseHistory = purchaseHistoryEditText.text.toString()
            val orderCount = orderCountEditText.text.toString().toIntOrNull() ?: 0
            val discount = discountEditText.text.toString().toFloatOrNull() ?: 0

            // Створюємо новий клієнт
            val newClient = Client(firstName, lastName, email, purchaseHistory, orderCount,
                discount.toFloat())

            // Зберігаємо клієнта в базі даних
            val result = dbHelper.addClient(newClient)

            // Перевіряємо результат вставки
            if (!result.equals(0)) {
                Toast.makeText(this, "Клієнта успішно додано", Toast.LENGTH_SHORT).show()
                finish() // Закриваємо активність
            } else {
                Toast.makeText(this, "Не вдалося додати клієнта", Toast.LENGTH_SHORT).show()
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
