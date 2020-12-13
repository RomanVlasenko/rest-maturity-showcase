package com.donerpoint.shawarma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class ShawarmaApplication

fun main(args: Array<String>) {
    runApplication<ShawarmaApplication>(*args)
}