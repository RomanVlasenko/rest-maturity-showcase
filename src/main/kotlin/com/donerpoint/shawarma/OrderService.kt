package com.donerpoint.shawarma

import com.google.gson.Gson
import org.springframework.stereotype.Service
import javax.ws.rs.POST
import javax.ws.rs.Path

//Level 0 - The Swamp of POX

@Service
@Path("/")
class OrderService {

    val gson = Gson()

    @POST
    fun processRequest(data: String): String? {
        val elements = data.split(":")

        when (elements[0]) {
            "create" -> return OrdersStore.save(elements[1]).toString()
            "get" -> return gson.toJson(OrdersStore.get(elements[1].toInt()))
            "delete" -> return OrdersStore.delete(elements[1].toInt())
            "list" -> return gson.toJson(OrdersStore.list())
        }

        return "[error] unknown operation type"
    }
}