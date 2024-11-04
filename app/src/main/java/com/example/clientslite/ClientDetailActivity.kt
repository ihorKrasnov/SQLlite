package com.example.clientslite

import DatabaseHelper
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClientDetailActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var purchaseHistoryTextView: TextView
    private lateinit var orderCountTextView: TextView
    private lateinit var discountTextView: TextView
    private lateinit var removeClientButton: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_detail)

        dbHelper = DatabaseHelper(this)

        // Прив'язка елементів макету до змінних
        nameTextView = findViewById(R.id.clientNameTextView)
        emailTextView = findViewById(R.id.clientEmailTextView)
        purchaseHistoryTextView = findViewById(R.id.clientPurchaseHistoryTextView)
        orderCountTextView = findViewById(R.id.clientOrderCountTextView)
        discountTextView = findViewById(R.id.clientDiscountTextView)
        removeClientButton = findViewById(R.id.deleteClientButton)

        // Отримання даних з Intent
        val clientJson = intent.getStringExtra("client")
        val client: Client
        if (clientJson != null) {
            client = Client.fromStringRepresentation(clientJson)
            client?.let {
                nameTextView.text = "${it.firstName} ${it.lastName}"
                emailTextView.text = it.email
                purchaseHistoryTextView.text = it.purchaseHistory
                orderCountTextView.text = "Замовлень: ${it.orderCount}"
                discountTextView.text = "Знижка: ${it.discount}%"
            }

            removeClientButton.setOnClickListener {
                dbHelper.deleteClient(client.id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
