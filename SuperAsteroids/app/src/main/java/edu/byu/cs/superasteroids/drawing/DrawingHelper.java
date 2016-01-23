package edu.byu.cs.superasteroids.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import edu.byu.cs.superasteroids.content.ContentManager;

/**
 * A class that enables drawing to the game view without having to know anything about Android canvases
 * or matrices.
 */
public class DrawingHelper {

    //The current GameView's current canvas
	private static Canvas canvas;

    //Helper paints
    private static Paint paint = new Paint();
    private static Paint fillPaint = new Paint();
    private static int viewWidth;
    private static int viewHeight;

	public static void setCanvas(Canvas canvas) {
		DrawingHelper.canvas = canvas;
	}

    /**
     * Draws an image to the current game view centered at the specified view coordinates, with the specified
     * rotation in degrees, scale, and alpha. Will not draw if the current canvas is null.
     * @param imageId The ID of an image that has already been loaded through the content manager
     * @param x The x coordinate to draw the center of the image at
     * @param y The y coordinate to draw the center of the image at
     * @param rotationDegrees The rotation on the image in degrees
     * @param scaleX The image X scale
     * @param scaleY The image Y scale
     * @param alpha Image alpha (0 - 255, 0 = completely transparent, 255 = completely opaque)
     */
	public static void drawImage(int imageId, float x, float y, float rotationDegrees, float scaleX, float scaleY, int alpha){

        if(canvas == null)
            return;

		Bitmap image = ContentManager.getInstance().getImage(imageId);
        paint.reset();
		Matrix matrix = new Matrix();
		matrix.setTranslate(-image.getWidth()/2f, -image.getHeight()/2f);
		matrix.postRotate(rotationDegrees);
		matrix.postTranslate(x / scaleX, y / scaleY);
		matrix.postScale(scaleX, scaleY);
        paint.setAlpha(alpha);
		canvas.drawBitmap(image, matrix, paint);
	}

    /**
     * Draws a portion of an image to a destination rectangle on the current game view.
     * Will not draw if the current canvas is null.
     * @param imageId The ID of an image that has already been loaded through the content manager
     * @param src A rectangle in image coordinates specifying the portion of the image to draw
     * @param dest A rectangle in view coordinates specifying where to draw the portion of the image
     */
    public static void drawImage(int imageId, Rect src, Rect dest) {

        if(canvas == null)
            return;

        if(dest == null) {
            dest = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        paint.reset();
        paint.setAlpha(255);
        Bitmap image = ContentManager.getInstance().getImage(imageId);
        canvas.drawBitmap(image, src, dest, paint);
    }

    /**
     * Draws a point to the game view using the provided parameters.  Will not draw if the current canvas is null.
     * @param location The location of the point in view coordinates
     * @param width The width of the point
     * @param color The color of the point
     * @param alpha The point alpha value (0 - 255, 0 = completely transparent, 255 = completely opaque)
     */
    public static void drawPoint(PointF location, float width, int color, int alpha) {

        if(canvas == null)
            return;

        paint.reset();
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvas.drawPoint(location.x, location.y, paint);
    }

    /**
     * Draws a point to the game view using the provided paint object.  Will not draw if the current canvas is null.
     * @param location The location of the point in view coordinates
     * @param p The paint used to draw the point
     */
    public static void drawPointWithPaint(PointF location, Paint p) {

        if(canvas == null)
            return;

        canvas.drawPoint(location.x, location.y, p);
    }

    /**
     * Draws a filled circle to the game view.  Will not draw if the current canvas is null.
     * @param location The location of the circle in view coordinates
     * @param color The color of the circle
     * @param alpha The circle's alpha (0 - 255, 0 = completely transparent, 255 = completely opaque)
     */
    public static void drawFilledCircle(PointF location, float radius, int color, int alpha) {

        if(canvas == null)
            return;

        fillPaint.reset();
        fillPaint.setStrokeWidth(1.0f);
        fillPaint.setColor(color);
        fillPaint.setAlpha(alpha);
        fillPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(location.x, location.y, radius, fillPaint);
    }

    /**
     * Draws a filled rectangle to the game view.  Will not draw if the current canvas is null.
     * @param r A rectangle in view coordinates
     * @param color The color of the rectangle
     * @param alpha The rectangle alpha (0 - 255, 0 = completely transparent, 255 = completely opaque)
     */
    public static void drawFilledRectangle(Rect r, int color, int alpha) {

        if(canvas == null)
            return;

        fillPaint.reset();
        fillPaint.setStrokeWidth(1.0f);
        fillPaint.setColor(color);
        fillPaint.setAlpha(alpha);
        fillPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(r, fillPaint);
    }

    /**
     * Draws an unfilled rectangle to the game view.  Will not draw if the current canvas is null.
     * @param r A rectangle in view coordinates
     * @param color The color of the rectangle outline
     * @param alpha The rectangle outline alpha (0 - 255, 0 = completely transparent, 255 = completely opaque)
     */
    public static void drawRectangle(Rect r, int color, int alpha) {

        if(canvas == null)
            return;

        paint.reset();
        paint.setStrokeWidth(1.0f);
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(r, paint);
    }

    /**
     * Draws text in the center of the game view.  Will not draw if the current canvas is null.
     * @param text The text to be drawn
     * @param size The font size of the text
     * @param color The color of the text
     */
    public static void drawCenteredText(String text, int size, int color) {

        if(canvas == null)
            return;

        Rect bounds = new Rect();
        paint.reset();
        paint.setTextSize(size);
        paint.setColor(color);
        paint.setAlpha(255);
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        int y = (canvas.getHeight() / 2) - (bounds.height() / 2);
        canvas.drawText(text, x, y, paint);
    }

    /**
     * Draws text to the game view.  Will not draw if the current canvas is null.
     * @param position The position of the text in view coordinates. This point represents the top left
     *                 corner of the text.
     * @param text The text to be drawn
     * @param size The font size of the text
     * @param color The color of the text
     */
    public static void drawText(Point position, String text, int color, float size) {

        if(canvas == null)
            return;

        paint.reset();
        paint.setTextSize(size);
        paint.setColor(color);
        paint.setAlpha(255);
        canvas.drawText(text, position.x, position.y, paint);
    }

    /**
     * Get the width of the game view
     * @return The width of the game view, 0 if the current canvas is null
     */
	public static int getGameViewWidth() {

        if(canvas == null)
            return 0;

        return viewWidth;
	}

    /**
     * Get the height of the game view
     * @return The height of the game view, 0 if the current canvas is null
     */
	public static int getGameViewHeight() {

        if(canvas == null)
            return 0;

        return viewHeight;
	}

    public static void setViewWidth(int w) {
        viewWidth = w;
    }

    public static void setViewHeight(int h) {
        viewHeight = h;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

}
