package edu.byu.cs.superasteroids.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

public class GameView extends BaseSurfaceView {

    private Canvas currentCanvas;
    private boolean done = false;
    private SurfaceHolder holder;
    private boolean canDraw = false;
    private IGameDelegate gameDelegate;
    private GameLoopThread gameLoopThread;

	public GameView(Context context) {
		super(context);
        holder = getHolder();
        DrawingHelper.setCanvas(new Canvas());

        gameLoopThread = new GameLoopThread(this);
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                canDraw = true;
                GameView.this.startGameLoop();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                GameView.this.joinGameLoop();
            }
        });
	}

	public void gameDraw(Canvas canvas) {
		//super.onDraw(canvas);

        if(!done && canDraw && canvas != null) {
            currentCanvas = canvas;
            DrawingHelper.setCanvas(canvas);
            DrawingHelper.setViewHeight(getMeasuredHeight());
            DrawingHelper.setViewWidth(getMeasuredWidth());
            canvas.drawColor(Color.BLACK);
            gameDelegate.draw();
            gameDelegate.update((1000 / GameLoopThread.FPS) / 1000.0);
        }
	}

    public Canvas getCurrentCanvas() {
        return currentCanvas;
    }

    public void setGameDelegate(IGameDelegate gameDelegate) {
		this.gameDelegate = gameDelegate;
	}

    public void pause() {
        gameLoopThread.setRunning(false);
    }

    public void resume() {
        gameLoopThread.setRunning(true);
    }

//    private void startGameLoop() {
//        gameLoopThread.setRunning(true);
//        gameLoopThread.start();
//    }
    private void startGameLoop() {
        if(gameLoopThread.getState() == Thread.State.TERMINATED)
            gameLoopThread = new GameLoopThread(this);
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }
    private void joinGameLoop() {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return super.onTouchEvent(e);
	}

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
