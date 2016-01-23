package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.graphics.RectF;
import android.test.AndroidTestCase;

import static edu.byu.cs.superasteroids.core.GraphicsUtils.*;

public class GraphicsUtilsTests extends AndroidTestCase {

    public void testRadiansToDegrees() {
        assertEquals(0.0, radiansToDegrees(ZERO));
        assertEquals(90.0, radiansToDegrees(HALF_PI));
        assertEquals(180.0, radiansToDegrees(PI));
        assertEquals(270.0, radiansToDegrees(THREE_HALF_PI));
        assertEquals(360.0, radiansToDegrees(TWO_PI));
    }

    public void testAdd() {
        assertEquals(new PointF(30.0f, 30.0f),
                        add(new PointF(10.0f, 10.0f), new PointF(20.0f, 20.0f)));
        assertEquals(new PointF(-30.0f, -30.0f),
                add(new PointF(-10.0f, -10.0f), new PointF(-20.0f, -20.0f)));
        assertEquals(new PointF(10.0f, 10.0f),
                add(new PointF(-10.0f, -10.0f), new PointF(20.0f, 20.0f)));
    }

    public void testSubtract() {
        assertEquals(new PointF(-10.0f, -10.0f),
                subtract(new PointF(10.0f, 10.0f), new PointF(20.0f, 20.0f)));
        assertEquals(new PointF(10.0f, 10.0f),
                subtract(new PointF(-10.0f, -10.0f), new PointF(-20.0f, -20.0f)));
        assertEquals(new PointF(-30.0f, -30.0f),
                subtract(new PointF(-10.0f, -10.0f), new PointF(20.0f, 20.0f)));
    }

    public void testDistance() {
        assertEquals(10.0, distance(new PointF(-5.0f, 0.0f), new PointF(5.0f, 0.0f)));
        assertEquals(10.0, distance(new PointF(0.0f, -5.0f), new PointF(0.0f, 5.0f)));
    }

    public void testScale() {
        assertEquals(new PointF(-20.0f, 50.0f), scale(new PointF(-2.0f, 5.0f), 10.0f));
    }

    public void testTranslate() {
        assertEquals(new PointF(0.0f, 0.0f), translate(new PointF(5.0f, -5.0f), -5.0f, 5.0f));
    }

    public void testRotate_1() {
        PointF expected;
        PointF actual;

        expected = new PointF(0.0f, 1.0f);
        actual = rotate(new PointF(1.0f, 0.0f), HALF_PI);
        assertFloatsEqual(expected.x, actual.x);
        assertFloatsEqual(expected.y, actual.y);
    }

    public void testRotate_2() {
        PointF expected;
        PointF actual;

        expected = new PointF(0.0f, 1.0f);
        actual = rotate(new PointF(1.0f, 0.0f), 0.0, 1.0);
        assertFloatsEqual(expected.x, actual.x);
        assertFloatsEqual(expected.y, actual.y);
    }

    public void testMoveObject_1() {
        MoveObjectResult expected;
        MoveObjectResult actual;

        expected = new MoveObjectResult();
        expected.getNewObjPosition().x = 5.0f;
        expected.getNewObjPosition().y = -5.0f;
        expected.getNewObjBounds().left = 4.0f;
        expected.getNewObjBounds().top = -6.0f;
        expected.getNewObjBounds().right = 6.0f;
        expected.getNewObjBounds().bottom = -4.0f;
        actual = moveObject(new PointF(5.0f, 5.0f), new RectF(4.0f, 4.0f, 6.0f, 6.0f), 10.0f,
                            THREE_HALF_PI, 1.0);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
    }

    public void testMoveObject_2() {
        MoveObjectResult expected;
        MoveObjectResult actual;

        expected = new MoveObjectResult();
        expected.getNewObjPosition().x = 5.0f;
        expected.getNewObjPosition().y = -5.0f;
        expected.getNewObjBounds().left = 4.0f;
        expected.getNewObjBounds().top = -6.0f;
        expected.getNewObjBounds().right = 6.0f;
        expected.getNewObjBounds().bottom = -4.0f;
        actual = moveObject(new PointF(5.0f, 5.0f), new RectF(4.0f, 4.0f, 6.0f, 6.0f), 10.0f,
                0.0, -1.0, 1.0);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
    }

    public void testRicochetObject_1_Edges() {
        final float WORLD_WIDTH = 1000.0f;
        final float WORLD_HEIGHT = 1000.0f;

        RicochetObjectResult expected;
        RicochetObjectResult actual;

        // Top edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 500.0f;
        expected.getNewObjPosition().y = 1.0f;
        expected.getNewObjBounds().left = 499.0f;
        expected.getNewObjBounds().top = 0.0f;
        expected.getNewObjBounds().right = 501.0f;
        expected.getNewObjBounds().bottom = 2.0f;
        expected.setNewAngleRadians(HALF_PI);
        expected.setNewAngleCosine(0.0);
        expected.setNewAngleSine(1.0);
        actual = ricochetObject(new PointF(500.0f, -1.0f), new RectF(499.0f, -2.0f, 501.0f, 0.0f),
                THREE_HALF_PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Bottom edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 500.0f;
        expected.getNewObjPosition().y = WORLD_HEIGHT - 1.0f;
        expected.getNewObjBounds().left = 499.0f;
        expected.getNewObjBounds().top = WORLD_HEIGHT - 2.0f;
        expected.getNewObjBounds().right = 501.0f;
        expected.getNewObjBounds().bottom = WORLD_HEIGHT;
        expected.setNewAngleRadians(THREE_HALF_PI);
        expected.setNewAngleCosine(0.0);
        expected.setNewAngleSine(-1.0);
        actual = ricochetObject(new PointF(500.0f, WORLD_HEIGHT + 1.0f),
                new RectF(499.0f, WORLD_HEIGHT, 501.0f, WORLD_HEIGHT + 2.0f),
                HALF_PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Left edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 1.0f;
        expected.getNewObjPosition().y = 500.0f;
        expected.getNewObjBounds().left = 0.0f;
        expected.getNewObjBounds().top = 499.0f;
        expected.getNewObjBounds().right = 2.0f;
        expected.getNewObjBounds().bottom = 501.0f;
        expected.setNewAngleRadians(ZERO);
        expected.setNewAngleCosine(1.0);
        expected.setNewAngleSine(0.0);
        actual = ricochetObject(new PointF(-1.0f, 500.0f), new RectF(-2.0f, 499.0f, 0.0f, 501.0f),
                PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Right edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = WORLD_WIDTH - 1.0f;
        expected.getNewObjPosition().y = 500.0f;
        expected.getNewObjBounds().left = WORLD_WIDTH - 2.0f;
        expected.getNewObjBounds().top = 499.0f;
        expected.getNewObjBounds().right = WORLD_WIDTH;
        expected.getNewObjBounds().bottom = 501.0f;
        expected.setNewAngleRadians(PI);
        expected.setNewAngleCosine(-1.0);
        expected.setNewAngleSine(0.0);
        actual = ricochetObject(new PointF(WORLD_WIDTH + 1.0f, 500.0f),
                new RectF(WORLD_WIDTH, 499.0f, WORLD_WIDTH + 2.0f, 501.0f),
                ZERO, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());
    }

    public void testRicochetObject_1_Corners() {
        final float WORLD_WIDTH = 1000.0f;
        final float WORLD_HEIGHT = 1000.0f;

        RicochetObjectResult expected;
        RicochetObjectResult actual;

        // Top-Left corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 1.0f;
        expected.getNewObjPosition().y = 1.0f;
        expected.getNewObjBounds().left = 0.0f;
        expected.getNewObjBounds().top = 0.0f;
        expected.getNewObjBounds().right = 2.0f;
        expected.getNewObjBounds().bottom = 2.0f;
        expected.setNewAngleRadians(FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(FOURTH_PI));
        expected.setNewAngleSine(Math.sin(FOURTH_PI));
        actual = ricochetObject(new PointF(-1.0f, -1.0f), new RectF(-2.0f, -2.0f, 0.0f, 0.0f),
                FIVE_FOURTH_PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Top-Right corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = WORLD_WIDTH - 1.0f;
        expected.getNewObjPosition().y = 1.0f;
        expected.getNewObjBounds().left = WORLD_WIDTH - 2.0f;
        expected.getNewObjBounds().top = 0.0f;
        expected.getNewObjBounds().right = WORLD_WIDTH;
        expected.getNewObjBounds().bottom = 2.0f;
        expected.setNewAngleRadians(THREE_FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(THREE_FOURTH_PI));
        expected.setNewAngleSine(Math.sin(THREE_FOURTH_PI));
        actual = ricochetObject(new PointF(WORLD_WIDTH + 1.0f, -1.0f),
                new RectF(WORLD_WIDTH, -2.0f, WORLD_WIDTH + 2.0f, 0.0f),
                SEVEN_FOURTH_PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Bottom-Left corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 1.0f;
        expected.getNewObjPosition().y = WORLD_HEIGHT - 1.0f;
        expected.getNewObjBounds().left = 0.0f;
        expected.getNewObjBounds().top = WORLD_HEIGHT - 2.0f;
        expected.getNewObjBounds().right = 2.0f;
        expected.getNewObjBounds().bottom = WORLD_HEIGHT;
        expected.setNewAngleRadians(SEVEN_FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(SEVEN_FOURTH_PI));
        expected.setNewAngleSine(Math.sin(SEVEN_FOURTH_PI));
        actual = ricochetObject(new PointF(-1.0f, WORLD_HEIGHT + 1.0f),
                new RectF(-2.0f, WORLD_HEIGHT, 0.0f, WORLD_HEIGHT + 2.0f),
                THREE_FOURTH_PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Bottom-Right corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = WORLD_WIDTH - 1.0f;
        expected.getNewObjPosition().y = WORLD_HEIGHT - 1.0f;
        expected.getNewObjBounds().left = WORLD_WIDTH - 2.0f;
        expected.getNewObjBounds().top = WORLD_HEIGHT - 2.0f;
        expected.getNewObjBounds().right = WORLD_WIDTH;
        expected.getNewObjBounds().bottom = WORLD_HEIGHT;
        expected.setNewAngleRadians(FIVE_FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(FIVE_FOURTH_PI));
        expected.setNewAngleSine(Math.sin(FIVE_FOURTH_PI));
        actual = ricochetObject(new PointF(WORLD_WIDTH + 1.0f, WORLD_HEIGHT + 1.0f),
                new RectF(WORLD_WIDTH, WORLD_HEIGHT, WORLD_WIDTH + 2.0f, WORLD_HEIGHT + 2.0f),
                FOURTH_PI, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());
    }

    public void testRicochetObject_2_Edges() {
        final float WORLD_WIDTH = 1000.0f;
        final float WORLD_HEIGHT = 1000.0f;

        RicochetObjectResult expected;
        RicochetObjectResult actual;

        // Top edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 500.0f;
        expected.getNewObjPosition().y = 1.0f;
        expected.getNewObjBounds().left = 499.0f;
        expected.getNewObjBounds().top = 0.0f;
        expected.getNewObjBounds().right = 501.0f;
        expected.getNewObjBounds().bottom = 2.0f;
        expected.setNewAngleRadians(HALF_PI);
        expected.setNewAngleCosine(0.0);
        expected.setNewAngleSine(1.0);
        actual = ricochetObject(new PointF(500.0f, -1.0f), new RectF(499.0f, -2.0f, 501.0f, 0.0f),
                THREE_HALF_PI, 0.0, -1.0, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Bottom edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 500.0f;
        expected.getNewObjPosition().y = WORLD_HEIGHT - 1.0f;
        expected.getNewObjBounds().left = 499.0f;
        expected.getNewObjBounds().top = WORLD_HEIGHT - 2.0f;
        expected.getNewObjBounds().right = 501.0f;
        expected.getNewObjBounds().bottom = WORLD_HEIGHT;
        expected.setNewAngleRadians(THREE_HALF_PI);
        expected.setNewAngleCosine(0.0);
        expected.setNewAngleSine(-1.0);
        actual = ricochetObject(new PointF(500.0f, WORLD_HEIGHT + 1.0f),
                new RectF(499.0f, WORLD_HEIGHT, 501.0f, WORLD_HEIGHT + 2.0f),
                HALF_PI, 0.0, 1.0, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Left edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 1.0f;
        expected.getNewObjPosition().y = 500.0f;
        expected.getNewObjBounds().left = 0.0f;
        expected.getNewObjBounds().top = 499.0f;
        expected.getNewObjBounds().right = 2.0f;
        expected.getNewObjBounds().bottom = 501.0f;
        expected.setNewAngleRadians(ZERO);
        expected.setNewAngleCosine(1.0);
        expected.setNewAngleSine(0.0);
        actual = ricochetObject(new PointF(-1.0f, 500.0f), new RectF(-2.0f, 499.0f, 0.0f, 501.0f),
                PI, -1.0, 0.0, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Right edge
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = WORLD_WIDTH - 1.0f;
        expected.getNewObjPosition().y = 500.0f;
        expected.getNewObjBounds().left = WORLD_WIDTH - 2.0f;
        expected.getNewObjBounds().top = 499.0f;
        expected.getNewObjBounds().right = WORLD_WIDTH;
        expected.getNewObjBounds().bottom = 501.0f;
        expected.setNewAngleRadians(PI);
        expected.setNewAngleCosine(-1.0);
        expected.setNewAngleSine(0.0);
        actual = ricochetObject(new PointF(WORLD_WIDTH + 1.0f, 500.0f),
                new RectF(WORLD_WIDTH, 499.0f, WORLD_WIDTH + 2.0f, 501.0f),
                ZERO, 1.0, 0.0, WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());
    }

    public void testRicochetObject_2_Corners() {
        final float WORLD_WIDTH = 1000.0f;
        final float WORLD_HEIGHT = 1000.0f;

        RicochetObjectResult expected;
        RicochetObjectResult actual;

        // Top-Left corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 1.0f;
        expected.getNewObjPosition().y = 1.0f;
        expected.getNewObjBounds().left = 0.0f;
        expected.getNewObjBounds().top = 0.0f;
        expected.getNewObjBounds().right = 2.0f;
        expected.getNewObjBounds().bottom = 2.0f;
        expected.setNewAngleRadians(FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(FOURTH_PI));
        expected.setNewAngleSine(Math.sin(FOURTH_PI));
        actual = ricochetObject(new PointF(-1.0f, -1.0f), new RectF(-2.0f, -2.0f, 0.0f, 0.0f),
                FIVE_FOURTH_PI, Math.cos(FIVE_FOURTH_PI), Math.sin(FIVE_FOURTH_PI),
                WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Top-Right corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = WORLD_WIDTH - 1.0f;
        expected.getNewObjPosition().y = 1.0f;
        expected.getNewObjBounds().left = WORLD_WIDTH - 2.0f;
        expected.getNewObjBounds().top = 0.0f;
        expected.getNewObjBounds().right = WORLD_WIDTH;
        expected.getNewObjBounds().bottom = 2.0f;
        expected.setNewAngleRadians(THREE_FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(THREE_FOURTH_PI));
        expected.setNewAngleSine(Math.sin(THREE_FOURTH_PI));
        actual = ricochetObject(new PointF(WORLD_WIDTH + 1.0f, -1.0f),
                new RectF(WORLD_WIDTH, -2.0f, WORLD_WIDTH + 2.0f, 0.0f),
                SEVEN_FOURTH_PI, Math.cos(SEVEN_FOURTH_PI), Math.sin(SEVEN_FOURTH_PI),
                WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Bottom-Left corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = 1.0f;
        expected.getNewObjPosition().y = WORLD_HEIGHT - 1.0f;
        expected.getNewObjBounds().left = 0.0f;
        expected.getNewObjBounds().top = WORLD_HEIGHT - 2.0f;
        expected.getNewObjBounds().right = 2.0f;
        expected.getNewObjBounds().bottom = WORLD_HEIGHT;
        expected.setNewAngleRadians(SEVEN_FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(SEVEN_FOURTH_PI));
        expected.setNewAngleSine(Math.sin(SEVEN_FOURTH_PI));
        actual = ricochetObject(new PointF(-1.0f, WORLD_HEIGHT + 1.0f),
                new RectF(-2.0f, WORLD_HEIGHT, 0.0f, WORLD_HEIGHT + 2.0f),
                THREE_FOURTH_PI, Math.cos(THREE_FOURTH_PI), Math.sin(THREE_FOURTH_PI),
                WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());

        // Bottom-Right corner
        expected = new RicochetObjectResult();
        expected.getNewObjPosition().x = WORLD_WIDTH - 1.0f;
        expected.getNewObjPosition().y = WORLD_HEIGHT - 1.0f;
        expected.getNewObjBounds().left = WORLD_WIDTH - 2.0f;
        expected.getNewObjBounds().top = WORLD_HEIGHT - 2.0f;
        expected.getNewObjBounds().right = WORLD_WIDTH;
        expected.getNewObjBounds().bottom = WORLD_HEIGHT;
        expected.setNewAngleRadians(FIVE_FOURTH_PI);
        expected.setNewAngleCosine(Math.cos(FIVE_FOURTH_PI));
        expected.setNewAngleSine(Math.sin(FIVE_FOURTH_PI));
        actual = ricochetObject(new PointF(WORLD_WIDTH + 1.0f, WORLD_HEIGHT + 1.0f),
                new RectF(WORLD_WIDTH, WORLD_HEIGHT, WORLD_WIDTH + 2.0f, WORLD_HEIGHT + 2.0f),
                FOURTH_PI, Math.cos(FOURTH_PI), Math.sin(FOURTH_PI),
                WORLD_WIDTH, WORLD_HEIGHT);
        assertEquals(expected.getNewObjPosition(), actual.getNewObjPosition());
        assertEquals(expected.getNewObjBounds(), actual.getNewObjBounds());
        assertDoublesEqual(expected.getNewAngleRadians(), actual.getNewAngleRadians());
        assertDoublesEqual(expected.getNewAngleCosine(), actual.getNewAngleCosine());
        assertDoublesEqual(expected.getNewAngleSine(), actual.getNewAngleSine());
    }

    private void assertFloatsEqual(float expected, float actual) {
        final float DELTA = 0.000001f;
        assertEquals(expected, actual, DELTA);
    }

    private void assertDoublesEqual(double expected, double actual) {
        final double DELTA = 0.000001;
        assertEquals(expected, actual, DELTA);
    }
}
