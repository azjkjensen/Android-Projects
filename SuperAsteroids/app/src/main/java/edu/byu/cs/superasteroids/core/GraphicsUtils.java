package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.graphics.RectF;

public class GraphicsUtils {

    public static final double ZERO = 0.0;
    public static final double PI = Math.PI;
    public static final double HALF_PI = 0.5 * PI;
    public static final double THREE_HALF_PI = 1.5 * PI;
    public static final double TWO_PI = 2.0 * PI;
    public static final double FOURTH_PI = 0.25 * PI;
    public static final double THREE_FOURTH_PI = 3.0 * FOURTH_PI;
    public static final double FIVE_FOURTH_PI = 5.0 * FOURTH_PI;
    public static final double SEVEN_FOURTH_PI = 7.0 * FOURTH_PI;

    /**
     * Converts an angle measured in radians to the equivalent angle measured in degrees
     * @param angleRadians The angle measured in radians
     * @return The equivalent angle measured in degrees
     */
    public static double radiansToDegrees(double angleRadians) {
        return ((angleRadians * 180.0) / Math.PI);
    }

    /**
     * Adds two points together
     * @param p1 First point
     * @param p2 Second point
     * @return The sum of p1 and p2
     */
    public static PointF add(PointF p1, PointF p2) {
        return new PointF(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * Subtracts one point from another
     * @param p1 First point
     * @param p2 Second point
     * @return The difference between p1 and p2 (i.e., p1 - p2)
     */
    public static PointF subtract(PointF p1, PointF p2) {
        return new PointF(p1.x - p2.x, p1.y - p2.y);
    }

    /**
     * Calculates the distance between two points
     * @param p1 First point
     * @param p2 Second point
     * @return The distance between p1 and p2
     */
    public static double distance(PointF p1, PointF p2) {
        PointF vector = subtract(p1, p2);
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    /**
     * Scales a point by the specified factor
     * @param p The point to be scaled
     * @param scale The scaling factor
     * @return The scaled point (i.e., s * p)
     */
    public static PointF scale(PointF p, float scale) {
        return new PointF(p.x * scale, p.y * scale);
    }

    /**
     * Translates a point by the specified delta-x and delta-y
     * @param p The point to be translated
     * @param dx Delta-X
     * @param dy Delta-Y
     * @return The translated point, i.e. (p.x + dx, p.y + dy)
     */
    public static PointF translate(PointF p, float dx, float dy) {
        return new PointF(p.x + dx, p.y + dy);
    }

    /**
     * Rotates a point around the origin (0, 0) by a specified angle
     * @param p The point to be rotated
     * @param angleRadians The rotation angle (measured in radians)
     * @return The rotated point
     */
    public static PointF rotate(PointF p, double angleRadians) {
        double angleCosine = Math.cos(angleRadians);
        double angleSine = Math.sin(angleRadians);
        return rotate(p, angleCosine, angleSine);
    }

    /**
     * Rotates a point around the origin (0, 0) by a specified angle
     * @param p The point to be rotated
     * @param angleCosine The cosine of the rotation angle
     * @param angleSine The sine of the rotation angle
     * @return The rotated point
     */
    public static PointF rotate(PointF p, double angleCosine, double angleSine) {
        double newX = p.x * angleCosine - p.y * angleSine;
        double newY = p.y * angleCosine + p.x * angleSine;
        return new PointF((float)newX, (float)newY);
    }

    /**
     * Calculates the new position of a moving object
     * @param objPosition The object's original position
     * @param objBounds The object's original bounding rectangle
     * @param speed The object's speed
     * @param angleRadians The object's direction angle
     * @param elapsedTime The time period over which the movement is calculated
     * @return A MoveObjectResult containing the object's new position and bounding rectangle
     */
    public static MoveObjectResult moveObject(PointF objPosition, RectF objBounds, double speed,
                                              double angleRadians, double elapsedTime) {
        double angleCosine = Math.cos(angleRadians);
        double angleSine = Math.sin(angleRadians);
        return moveObject(objPosition, objBounds, speed, angleCosine, angleSine, elapsedTime);
    }

    /**
     * Calculates the new position of a moving object
     * @param objPosition The object's original position
     * @param objBounds The object's original bounding rectangle
     * @param speed The object's speed
     * @param angleCosine The cosine of the object's direction angle
     * @param angleSine The sine of the object's direction angle
     * @param elapsedTime The time period over which the movement is calculated
     * @return A MoveObjectResult containing the object's new position and bounding rectangle
     */
    public static MoveObjectResult moveObject(PointF objPosition, RectF objBounds, double speed,
                                                double angleCosine, double angleSine,
                                                double elapsedTime) {
        MoveObjectResult result = new MoveObjectResult();

        double distance = speed * elapsedTime;
        double deltaX = distance * angleCosine;
        double deltaY = distance * angleSine;

        result.getNewObjPosition().x = (float)(objPosition.x + deltaX);
        result.getNewObjPosition().y = (float)(objPosition.y + deltaY);

        result.getNewObjBounds().left = (float)(objBounds.left + deltaX);
        result.getNewObjBounds().top = (float)(objBounds.top + deltaY);
        result.getNewObjBounds().right = (float)(objBounds.right + deltaX);
        result.getNewObjBounds().bottom = (float)(objBounds.bottom + deltaY);

        return result;
    }

    /**
     * Ricochets an object off the edges of the world in order to keep the object from leaving
     * the world.
     * If the object is not at one of the world's edges, it does nothing.
     * If the object is at one of the world's edges, it ricochets the object off the edge
     * in order to prevent the object from leaving the world.
     * @param objPosition The object's original position
     * @param objBounds The object's original bounding rectangle
     * @param angleRadians The object's direction angle
     * @param worldWidth The world's width
     * @param worldHeight The world's height
     * @return A RicochetObjectResult containing the object's new position, bounding rectangle,
     * and direction angle
     */
    public static RicochetObjectResult ricochetObject(PointF objPosition, RectF objBounds,
                                                        double angleRadians,
                                                        float worldWidth, float worldHeight) {
        double angleCosine = Math.cos(angleRadians);
        double angleSine = Math.sin(angleRadians);
        return ricochetObject(objPosition, objBounds, angleRadians, angleCosine, angleSine,
                                worldWidth, worldHeight);
    }

    /**
     * Ricochets an object off the edges of the world in order to keep the object from leaving
     * the world.
     * If the object is not at one of the world's edges, it does nothing.
     * If the object is at one of the world's edges, it ricochets the object off the edge
     * in order to prevent the object from leaving the world.
     * @param objPosition The object's original position
     * @param objBounds The object's original bounding rectangle
     * @param angleRadians The object's direction angle
     * @param angleCosine The cosine of the object's direction angle
     * @param angleSine The sine of the object's direction angle
     * @param worldWidth The world's width
     * @param worldHeight The world's height
     * @return A RicochetObjectResult containing the object's new position, bounding rectangle,
     * and direction angle
     */
    public static RicochetObjectResult ricochetObject(PointF objPosition, RectF objBounds,
                                                        double angleRadians,
                                                        double angleCosine, double angleSine,
                                                        float worldWidth, float worldHeight) {
        RicochetObjectResult result = new RicochetObjectResult();

        PointF position = new PointF();
        position.set(objPosition);

        RectF bounds = new RectF();
        bounds.set(objBounds);

        double radians = angleRadians;
        double cosine = angleCosine;
        double sine = angleSine;

        if (bounds.left < 0 || bounds.right > worldWidth) {
            // Negate x component of trajectory vector
            double x = -cosine;
            double y = sine;
            radians = Math.atan2(y, x);
            if (radians < 0) {
                radians += TWO_PI;
            }
            cosine = x;
            sine = y;

            float deltaX = 0.0f;
            if (bounds.left < 0) {
                deltaX = -bounds.left;
            }
            else if (bounds.right > worldWidth) {
                deltaX = worldWidth - bounds.right;
            }
            position.x += deltaX;
            bounds.left += deltaX;
            bounds.right += deltaX;
        }

        if (bounds.top < 0 || bounds.bottom > worldHeight) {
            // Negate y component of trajectory vector
            double x = cosine;
            double y = -sine;
            radians = Math.atan2(y, x);
            if (radians < 0) {
                radians += TWO_PI;
            }
            cosine = x;
            sine = y;

            float deltaY = 0.0f;
            if (bounds.top < 0) {
                deltaY = -bounds.top;
            }
            else if (bounds.bottom > worldHeight) {
                deltaY = worldHeight - bounds.bottom;
            }
            position.y += deltaY;
            bounds.top += deltaY;
            bounds.bottom += deltaY;
        }

        result.getNewObjPosition().set(position);
        result.getNewObjBounds().set(bounds);
        result.setNewAngleRadians(radians);
        result.setNewAngleCosine(cosine);
        result.setNewAngleSine(sine);

        return result;
    }

    /**
     * This class represents the result of the "moveObject" method.
     */
    public static class MoveObjectResult {
        /**
         * The object's new position
         */
        private PointF newObjPosition;
        /**
         * The object's new bounding rectangle
         */
        private RectF newObjBounds;

        public MoveObjectResult() {
            newObjPosition = new PointF();
            newObjBounds = new RectF();
        }

        public PointF getNewObjPosition() {
            return newObjPosition;
        }

        public RectF getNewObjBounds() {
            return newObjBounds;
        }
    }

    /**
     * This class represents the result of the "ricochetObject" method.
     */
    public static class RicochetObjectResult extends MoveObjectResult {
        /**
         * The object's new direction angle
         */
        private double newAngleRadians;
        /**
         * The cosine of the object's new direction angle
         */
        private double newAngleCosine;
        /**
         * The sine of the object's new direction angle
         */
        private double newAngleSine;

        public RicochetObjectResult() {
            super();
            newAngleRadians = 0.0;
            newAngleCosine = 0.0;
            newAngleSine = 0.0;
        }

        public double getNewAngleRadians() {
            return newAngleRadians;
        }

        public void setNewAngleRadians(double newAngleRadians) {
            this.newAngleRadians = newAngleRadians;
        }

        public double getNewAngleCosine() {
            return newAngleCosine;
        }

        public void setNewAngleCosine(double newAngleCosine) {
            this.newAngleCosine = newAngleCosine;
        }

        public double getNewAngleSine() {
            return newAngleSine;
        }

        public void setNewAngleSine(double newAngleSine) {
            this.newAngleSine = newAngleSine;
        }
    }
}
