package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Category;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
