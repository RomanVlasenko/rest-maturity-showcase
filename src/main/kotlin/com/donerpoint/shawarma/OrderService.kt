package com.donerpoint.shawarma

import com.google.gson.Gson
import org.springframework.stereotype.Service
import javax.ws.rs.*
import javax.ws.rs.core.Response

//Level 2 - HTTP verbs

@Service
@Path("/orders")
class OrderService {

    val gson = Gson()

    @POST
    fun create(data: String): String {
        return OrdersStore.save(data).toString()
    }

    @GET
    fun list(): String {
        return gson.toJson(OrdersStore.list())
    }

    @GET
    @Path("{id}")
    fun get(@PathParam("id") orderId: String): Response {
        val order = OrdersStore.get(orderId.toInt())

        return if (order == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {
            Response.ok(gson.toJson(order)).build()
        }
    }

    @DELETE
    @Path("{id}")
    fun delete(@PathParam("id") orderId: String): Response {
        val order = OrdersStore.get(orderId.toInt())

        return if (order == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {
            OrdersStore.delete(orderId.toInt())
            Response.ok().build()
        }
    }
}