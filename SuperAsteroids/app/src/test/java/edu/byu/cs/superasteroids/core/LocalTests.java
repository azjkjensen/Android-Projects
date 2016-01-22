package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.graphics.RectF;

import static edu.byu.cs.superasteroids.core.GraphicsUtils.*;

import org.junit.*;
import static org.junit.Assert.*;

public class LocalTests {

    @Test
    public void testRadiansToDegrees() {
        assertEquals(0.0, radiansToDegrees(ZERO), 0.0);
        assertEquals(90.0, radiansToDegrees(HALF_PI), 0.0);
        assertEquals(180.0, radiansToDegrees(PI), 0.0);
        assertEquals(270.0, radiansToDegrees(THREE_HALF_PI), 0.0);
        assertEquals(360.0, radiansToDegrees(TWO_PI), 0.0);
    }

}
