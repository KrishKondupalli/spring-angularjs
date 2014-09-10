package com.mycompany.myapp.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.CategoryRepository;


/**
 * Test class for the CategoryResource REST controller.
 *
 * @see CategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class CategoryResourceTest {
    
    private static final Long DEFAULT_ID = new Long(1L);

    private static final LocalDate DEFAULT_SAMPLE_DATE_ATTR = new LocalDate(0L);

    private static final LocalDate UPD_SAMPLE_DATE_ATTR = new LocalDate();

    private static final String DEFAULT_SAMPLE_TEXT_ATTR = "sampleTextAttribute";

    private static final String UPD_SAMPLE_TEXT_ATTR = "sampleTextAttributeUpt";

    @Inject
    private CategoryRepository categoryRepository;

    private MockMvc restCategoryMockMvc;
    
    private Category category;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryResource categoryResource = new CategoryResource();
        ReflectionTestUtils.setField(categoryResource, "categoryRepository", categoryRepository);

        this.restCategoryMockMvc = MockMvcBuilders.standaloneSetup(categoryResource).build();

        category = new Category();
        category.setId(DEFAULT_ID);
    	category.setSampleDateAttribute(DEFAULT_SAMPLE_DATE_ATTR);
    	category.setSampleTextAttribute(DEFAULT_SAMPLE_TEXT_ATTR);
    }

    @Test
    public void testCRUDCategory() throws Exception {

    	// Create Category
    	restCategoryMockMvc.perform(post("/app/rest/categorys")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(category)))
                .andExpect(status().isOk());

    	// Read Category
    	restCategoryMockMvc.perform(get("/app/rest/categorys/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
    			.andExpect(jsonPath("$.sampleDateAttribute").value(DEFAULT_SAMPLE_DATE_ATTR.toString()))
    			.andExpect(jsonPath("$.sampleTextAttribute").value(DEFAULT_SAMPLE_TEXT_ATTR));

    	// Update Category
    	category.setSampleDateAttribute(UPD_SAMPLE_DATE_ATTR);
    	category.setSampleTextAttribute(UPD_SAMPLE_TEXT_ATTR);
  
    	restCategoryMockMvc.perform(post("/app/rest/categorys")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(category)))
                .andExpect(status().isOk());

    	// Read updated Category
    	restCategoryMockMvc.perform(get("/app/rest/categorys/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
    			.andExpect(jsonPath("$.sampleDateAttribute").value(UPD_SAMPLE_DATE_ATTR.toString()))
    			.andExpect(jsonPath("$.sampleTextAttribute").value(UPD_SAMPLE_TEXT_ATTR));

    	// Delete Category
    	restCategoryMockMvc.perform(delete("/app/rest/categorys/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    	// Read nonexisting Category
    	restCategoryMockMvc.perform(get("/app/rest/categorys/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
