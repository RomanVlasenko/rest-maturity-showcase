package com.donerpoint.shawarma

object OrdersStore {
    val orders: MutableMap<Int, Order> = mutableMapOf()
    var idCounter = 1


    fun save(orderDesc: String): Int {
        val id = idCounter++
        orders[id] = Order(orderDesc, id)
        return id
    }

    fun delete(orderId: Int):String {
        orders.remove(orderId)
        return "OK"
    }

    fun get(orderId: Int): Order? {
        return orders[orderId]
    }
    fun list(): List<Order> {
        return orders.values.toList()
    }
}