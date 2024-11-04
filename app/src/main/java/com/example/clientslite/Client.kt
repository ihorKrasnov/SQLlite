package com.example.clientslite

data class Client(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val purchaseHistory: String,
    val orderCount: Int,
    val discount: Float
) {
    constructor(
        firstName: String,
        lastName: String,
        email: String,
        purchaseHistory: String,
        orderCount: Int,
        discount: Float
    ) : this(0, firstName, lastName, email, purchaseHistory, orderCount, discount)
    // Перетворення в строку
    fun toStringRepresentation(): String {
        return "$id,$firstName,$lastName,$email,$purchaseHistory,$orderCount,$discount"
    }

    // Статичний метод для створення об'єкта з строки
    companion object {
        fun fromStringRepresentation(data: String): Client {
            val parts = data.split(",")
            return Client(
                id = parts[0].toInt(),
                firstName = parts[1],
                lastName = parts[2],
                email = parts[3],
                purchaseHistory = parts[4],
                orderCount = parts[5].toInt(),
                discount = parts[6].toFloat()
            )
        }
    }
}
