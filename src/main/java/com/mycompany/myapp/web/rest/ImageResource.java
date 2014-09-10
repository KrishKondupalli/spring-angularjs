package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Image;
import com.mycompany.myapp.repository.ImageRepository;
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
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/app")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    @Inject
    private ImageRepository imageRepository;

    /**
     * POST  /rest/images -> Create a new image.
     */
    @RequestMapping(value = "/rest/images",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Image image) {
        log.debug("REST request to save Image : {}", image);
        imageRepository.save(image);
    }

    /**
     * GET  /rest/images -> get all the images.
     */
    @RequestMapping(value = "/rest/images",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Image> getAll() {
        log.debug("REST request to get all Images");
        return imageRepository.findAll();
    }

    /**
     * GET  /rest/images/:id -> get the "id" image.
     */
    @RequestMapping(value = "/rest/images/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Image> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/images/:id -> delete the "id" image.
     */
    @RequestMapping(value = "/rest/images/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Image : {}", id);
        imageRepository.delete(id);
    }
}
