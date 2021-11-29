package com.example.demo.controller

import com.example.demo.entity.Book
import com.example.demo.repository.BookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Configuration
class BookReactiveController(val repository: BookRepository) {

    @Bean
    fun router(): RouterFunction<ServerResponse> {
        return route()
            .GET("/books", this::findAllHandler)
            .POST("/books", this::createHandler)
            .DELETE("/books/{id:[0-9]+}", this::deleteHandler)
            .build()
    }

    fun findAllHandler(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(repository.findAll(), Book::class.java)
    }

    fun createHandler(request: ServerRequest): Mono<ServerResponse> {
        val book = request.bodyToMono(Book::class.java)
        return book.flatMap { it ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.save(it), Book::class.java)
        }
    }

    fun deleteHandler(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id").toLong()
        return repository
            .findById(id)
            .flatMap { it ->
                ServerResponse.noContent().build(repository.delete(it))
            }
            .switchIfEmpty(ServerResponse.notFound().build())
    }
}
