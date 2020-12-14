package com.donerpoint.shawarma

import com.google.gson.Gson
import org.springframework.stereotype.Service
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam

//Level 1 - Resources

@Service
@Path("/orders")
class OrderService {

    val gson = Gson()

    @POST
    fun orders(data: String): String? {
        val elements = data.split(":")

        when (elements[0]) {
            "create" -> return OrdersStore.save(elements[1]).toString()
            else -> return gson.toJson(OrdersStore.list())
        }
    }

    @POST
    @Path("{id}")
    fun processOrder(@PathParam("id") orderId: String, operationType: String): String? {
        when (operationType) {
            "get" -> return gson.toJson(OrdersStore.get(orderId.toInt()))
            "delete" -> {
                OrdersStore.delete(orderId.toInt())
                return "OK"
            }
        }

        return "[error] unknown operation type"
    }
}