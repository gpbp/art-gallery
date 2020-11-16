package com.trung.iproject.service;

import com.trung.iproject.service.dto.PaintingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.trung.iproject.domain.Painting}.
 */
public interface PaintingService {

    /**
     * Save a painting.
     *
     * @param paintingDTO the entity to save.
     * @return the persisted entity.
     */
    PaintingDTO save(PaintingDTO paintingDTO);

    /**
     * Get all the paintings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaintingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" painting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaintingDTO> findOne(Long id);

    /**
     * Delete the "id" painting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
