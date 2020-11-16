package com.trung.iproject.web.rest;

import com.trung.iproject.service.PaintingService;
import com.trung.iproject.web.rest.errors.BadRequestAlertException;
import com.trung.iproject.service.dto.PaintingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.trung.iproject.domain.Painting}.
 */
@RestController
@RequestMapping("/api")
public class PaintingResource {

    private final Logger log = LoggerFactory.getLogger(PaintingResource.class);

    private static final String ENTITY_NAME = "painting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaintingService paintingService;

    public PaintingResource(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    /**
     * {@code POST  /paintings} : Create a new painting.
     *
     * @param paintingDTO the paintingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paintingDTO, or with status {@code 400 (Bad Request)} if the painting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paintings")
    public ResponseEntity<PaintingDTO> createPainting(@RequestBody PaintingDTO paintingDTO) throws URISyntaxException {
        log.debug("REST request to save Painting : {}", paintingDTO);
        if (paintingDTO.getId() != null) {
            throw new BadRequestAlertException("A new painting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaintingDTO result = paintingService.save(paintingDTO);
        return ResponseEntity.created(new URI("/api/paintings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paintings} : Updates an existing painting.
     *
     * @param paintingDTO the paintingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paintingDTO,
     * or with status {@code 400 (Bad Request)} if the paintingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paintingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paintings")
    public ResponseEntity<PaintingDTO> updatePainting(@RequestBody PaintingDTO paintingDTO) throws URISyntaxException {
        log.debug("REST request to update Painting : {}", paintingDTO);
        if (paintingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaintingDTO result = paintingService.save(paintingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paintingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paintings} : get all the paintings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paintings in body.
     */
    @GetMapping("/paintings")
    public ResponseEntity<List<PaintingDTO>> getAllPaintings(Pageable pageable) {
        log.debug("REST request to get a page of Paintings");
        Page<PaintingDTO> page = paintingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paintings/:id} : get the "id" painting.
     *
     * @param id the id of the paintingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paintingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paintings/{id}")
    public ResponseEntity<PaintingDTO> getPainting(@PathVariable Long id) {
        log.debug("REST request to get Painting : {}", id);
        Optional<PaintingDTO> paintingDTO = paintingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paintingDTO);
    }

    /**
     * {@code DELETE  /paintings/:id} : delete the "id" painting.
     *
     * @param id the id of the paintingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paintings/{id}")
    public ResponseEntity<Void> deletePainting(@PathVariable Long id) {
        log.debug("REST request to delete Painting : {}", id);
        paintingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
