package com.task.repository;

import com.task.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dimon on 27.06.2018.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
