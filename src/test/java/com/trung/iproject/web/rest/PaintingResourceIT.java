package com.trung.iproject.web.rest;

import com.trung.iproject.ArtGalleryApp;
import com.trung.iproject.config.TestSecurityConfiguration;
import com.trung.iproject.domain.Painting;
import com.trung.iproject.repository.PaintingRepository;
import com.trung.iproject.service.PaintingService;
import com.trung.iproject.service.dto.PaintingDTO;
import com.trung.iproject.service.mapper.PaintingMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.trung.iproject.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaintingResource} REST controller.
 */
@SpringBootTest(classes = { ArtGalleryApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PaintingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private PaintingMapper paintingMapper;

    @Autowired
    private PaintingService paintingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaintingMockMvc;

    private Painting painting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Painting createEntity(EntityManager em) {
        Painting painting = new Painting()
            .name(DEFAULT_NAME)
            .author(DEFAULT_AUTHOR)
            .creationDate(DEFAULT_CREATION_DATE)
            .price(DEFAULT_PRICE);
        return painting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Painting createUpdatedEntity(EntityManager em) {
        Painting painting = new Painting()
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .creationDate(UPDATED_CREATION_DATE)
            .price(UPDATED_PRICE);
        return painting;
    }

    @BeforeEach
    public void initTest() {
        painting = createEntity(em);
    }

    @Test
    @Transactional
    public void createPainting() throws Exception {
        int databaseSizeBeforeCreate = paintingRepository.findAll().size();
        // Create the Painting
        PaintingDTO paintingDTO = paintingMapper.toDto(painting);
        restPaintingMockMvc.perform(post("/api/paintings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paintingDTO)))
            .andExpect(status().isCreated());

        // Validate the Painting in the database
        List<Painting> paintingList = paintingRepository.findAll();
        assertThat(paintingList).hasSize(databaseSizeBeforeCreate + 1);
        Painting testPainting = paintingList.get(paintingList.size() - 1);
        assertThat(testPainting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPainting.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPainting.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPainting.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPaintingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paintingRepository.findAll().size();

        // Create the Painting with an existing ID
        painting.setId(1L);
        PaintingDTO paintingDTO = paintingMapper.toDto(painting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaintingMockMvc.perform(post("/api/paintings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paintingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Painting in the database
        List<Painting> paintingList = paintingRepository.findAll();
        assertThat(paintingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaintings() throws Exception {
        // Initialize the database
        paintingRepository.saveAndFlush(painting);

        // Get all the paintingList
        restPaintingMockMvc.perform(get("/api/paintings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(painting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPainting() throws Exception {
        // Initialize the database
        paintingRepository.saveAndFlush(painting);

        // Get the painting
        restPaintingMockMvc.perform(get("/api/paintings/{id}", painting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(painting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPainting() throws Exception {
        // Get the painting
        restPaintingMockMvc.perform(get("/api/paintings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePainting() throws Exception {
        // Initialize the database
        paintingRepository.saveAndFlush(painting);

        int databaseSizeBeforeUpdate = paintingRepository.findAll().size();

        // Update the painting
        Painting updatedPainting = paintingRepository.findById(painting.getId()).get();
        // Disconnect from session so that the updates on updatedPainting are not directly saved in db
        em.detach(updatedPainting);
        updatedPainting
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .creationDate(UPDATED_CREATION_DATE)
            .price(UPDATED_PRICE);
        PaintingDTO paintingDTO = paintingMapper.toDto(updatedPainting);

        restPaintingMockMvc.perform(put("/api/paintings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paintingDTO)))
            .andExpect(status().isOk());

        // Validate the Painting in the database
        List<Painting> paintingList = paintingRepository.findAll();
        assertThat(paintingList).hasSize(databaseSizeBeforeUpdate);
        Painting testPainting = paintingList.get(paintingList.size() - 1);
        assertThat(testPainting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPainting.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPainting.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPainting.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPainting() throws Exception {
        int databaseSizeBeforeUpdate = paintingRepository.findAll().size();

        // Create the Painting
        PaintingDTO paintingDTO = paintingMapper.toDto(painting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaintingMockMvc.perform(put("/api/paintings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paintingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Painting in the database
        List<Painting> paintingList = paintingRepository.findAll();
        assertThat(paintingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePainting() throws Exception {
        // Initialize the database
        paintingRepository.saveAndFlush(painting);

        int databaseSizeBeforeDelete = paintingRepository.findAll().size();

        // Delete the painting
        restPaintingMockMvc.perform(delete("/api/paintings/{id}", painting.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Painting> paintingList = paintingRepository.findAll();
        assertThat(paintingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
