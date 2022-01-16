package web4.back;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import web4.back.dots.Dot;

class AreaCheckerTest {

    @Test
    void checkArea() {
        AreaChecker areaChecker = new AreaChecker();
        Assert.assertFalse(areaChecker.checkArea(new Dot(10.0d, 10.0d, 1.0d)));
        Assert.assertTrue(areaChecker.checkArea(new Dot(0.0d, 0.0d, 1.0d)));
        Assert.assertTrue(areaChecker.checkArea(new Dot(-1.0d, 0.0d, 1.0d)));
        Assert.assertFalse(areaChecker.checkArea(new Dot(-1.01d, 0.0d, 1.0d)));
    }
}