package com.example.demo.repository

import com.example.demo.entity.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long>
