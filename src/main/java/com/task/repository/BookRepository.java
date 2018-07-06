package com.task.repository;

import com.task.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dimon on 27.06.2018.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
