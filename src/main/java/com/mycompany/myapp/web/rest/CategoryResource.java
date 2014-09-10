package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/app")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Inject
    private CategoryRepository categoryRepository;

    /**
     * POST  /rest/categorys -> Create a new category.
     */
    @RequestMapping(value = "/rest/categorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Category category) {
        log.debug("REST request to save Category : {}", category);
        categoryRepository.save(category);
    }

    /**
     * GET  /rest/categorys -> get all the categorys.
     */
    @RequestMapping(value = "/rest/categorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Category> getAll() {
        log.debug("REST request to get all Categorys");
        return categoryRepository.findAll();
    }

    /**
     * GET  /rest/categorys/:id -> get the "id" category.
     */
    @RequestMapping(value = "/rest/categorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Category> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/categorys/:id -> delete the "id" category.
     */
    @RequestMapping(value = "/rest/categorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryRepository.delete(id);
    }
}
