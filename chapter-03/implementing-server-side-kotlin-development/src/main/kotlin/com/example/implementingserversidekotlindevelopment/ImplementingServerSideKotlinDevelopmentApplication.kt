package com.example.implementingserversidekotlindevelopment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Implementing server side kotlin development application
 */
@SpringBootApplication
class ImplementingServerSideKotlinDevelopmentApplication

/**
 * Main
 * サンプルアプリケーションのメイン関数
 * @param args
 */
fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<ImplementingServerSideKotlinDevelopmentApplication>(*args)
}
