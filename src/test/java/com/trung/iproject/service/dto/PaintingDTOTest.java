package com.trung.iproject.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.trung.iproject.web.rest.TestUtil;

public class PaintingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaintingDTO.class);
        PaintingDTO paintingDTO1 = new PaintingDTO();
        paintingDTO1.setId(1L);
        PaintingDTO paintingDTO2 = new PaintingDTO();
        assertThat(paintingDTO1).isNotEqualTo(paintingDTO2);
        paintingDTO2.setId(paintingDTO1.getId());
        assertThat(paintingDTO1).isEqualTo(paintingDTO2);
        paintingDTO2.setId(2L);
        assertThat(paintingDTO1).isNotEqualTo(paintingDTO2);
        paintingDTO1.setId(null);
        assertThat(paintingDTO1).isNotEqualTo(paintingDTO2);
    }
}
