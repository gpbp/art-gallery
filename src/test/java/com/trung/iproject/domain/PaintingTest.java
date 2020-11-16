package com.trung.iproject.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.trung.iproject.web.rest.TestUtil;

public class PaintingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Painting.class);
        Painting painting1 = new Painting();
        painting1.setId(1L);
        Painting painting2 = new Painting();
        painting2.setId(painting1.getId());
        assertThat(painting1).isEqualTo(painting2);
        painting2.setId(2L);
        assertThat(painting1).isNotEqualTo(painting2);
        painting1.setId(null);
        assertThat(painting1).isNotEqualTo(painting2);
    }
}
