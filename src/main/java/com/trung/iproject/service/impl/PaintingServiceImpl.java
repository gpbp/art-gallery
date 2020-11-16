package com.trung.iproject.service.impl;

import com.trung.iproject.service.PaintingService;
import com.trung.iproject.domain.Painting;
import com.trung.iproject.repository.PaintingRepository;
import com.trung.iproject.service.dto.PaintingDTO;
import com.trung.iproject.service.mapper.PaintingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Painting}.
 */
@Service
@Transactional
public class PaintingServiceImpl implements PaintingService {

    private final Logger log = LoggerFactory.getLogger(PaintingServiceImpl.class);

    private final PaintingRepository paintingRepository;

    private final PaintingMapper paintingMapper;

    public PaintingServiceImpl(PaintingRepository paintingRepository, PaintingMapper paintingMapper) {
        this.paintingRepository = paintingRepository;
        this.paintingMapper = paintingMapper;
    }

    @Override
    public PaintingDTO save(PaintingDTO paintingDTO) {
        log.debug("Request to save Painting : {}", paintingDTO);
        Painting painting = paintingMapper.toEntity(paintingDTO);
        painting = paintingRepository.save(painting);
        return paintingMapper.toDto(painting);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaintingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paintings");
        return paintingRepository.findAll(pageable)
            .map(paintingMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PaintingDTO> findOne(Long id) {
        log.debug("Request to get Painting : {}", id);
        return paintingRepository.findById(id)
            .map(paintingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Painting : {}", id);
        paintingRepository.deleteById(id);
    }
}
