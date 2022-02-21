package com.example.repo;

import com.example.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review,Long>{//CrudRepository позволяет изменять удолять создавать и тд данные из таблицы

}
