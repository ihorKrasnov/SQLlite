package com.example.clientslite

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var clientAdapter: ClientAdapter
    private lateinit var addClientButton: Button
    private lateinit var sortByOrderCountButton: Button
    private lateinit var sortByFirstNameButton: Button
    private var clientsList: MutableList<Client> = mutableListOf() // Змінено на MutableList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        addClientButton = findViewById(R.id.addClientButton)
        sortByOrderCountButton = findViewById(R.id.sortByOrderCountButton)
        sortByFirstNameButton = findViewById(R.id.sortByFirstNameButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadClients()

        addClientButton.setOnClickListener {
            val intent = Intent(this, AddClientActivity::class.java)
            startActivity(intent)
        }

        sortByOrderCountButton.setOnClickListener {
            sortClientsByOrderCount()
        }

        sortByFirstNameButton.setOnClickListener {
            sortClientsByFirstName()
        }
    }

    private fun sortClientsByFirstName() {
        clientsList.sortBy { it.firstName } // Сортування за іменем
        clientAdapter.notifyDataSetChanged() // Оновлення адаптера
    }

    private fun sortClientsByOrderCount() {
        clientsList.sortBy { it.orderCount } // Сортування за кількістю замовлень
        clientAdapter.notifyDataSetChanged() // Оновлення адаптера
    }

    private fun loadClients() {
        clientsList = databaseHelper.getAllClients().toMutableList() // Отримуємо список як MutableList
        clientAdapter = ClientAdapter(clientsList) { client ->
            val intent = Intent(this, ClientDetailActivity::class.java)
            intent.putExtra("client", client.toStringRepresentation())
            startActivity(intent)
        }
        recyclerView.adapter = clientAdapter
    }

    override fun onResume() {
        super.onResume()
        loadClients() // Оновлюємо список клієнтів при поверненні до активності
    }
}
