package com.trung.iproject.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaintingMapperTest {

    private PaintingMapper paintingMapper;

    @BeforeEach
    public void setUp() {
        paintingMapper = new PaintingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paintingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paintingMapper.fromId(null)).isNull();
    }
}
