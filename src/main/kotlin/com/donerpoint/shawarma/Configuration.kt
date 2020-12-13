package com.donerpoint.shawarma

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component


@Component
class JerseyConfig : ResourceConfig() {
    init {
        register(OrderService::class.java)
    }
}