package edu.byu.cs.superasteroids.base;

import android.graphics.Canvas;

import edu.byu.cs.superasteroids.content.ContentManager;

public class GameLoopThread extends Thread {

    public static final long FPS = 30;
	private long time;
	private IGameDelegate gameDelegate;
    private boolean running = false;
    private GameView gameView;
	
	public GameLoopThread(GameView gameView) {
		this.gameView = gameView;
	}

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
	public void run() {

        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()) {
                    gameView.gameDraw(c);
                }
            } finally {
                if (c != null) {
                    gameView.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
            } catch (Exception e) {}
        }
	}


    public void pause() {

    }
	
	public void loadContent(ContentManager content) {
		gameDelegate.loadContent(content);
	}

	public IGameDelegate getGameDelegate() {
		return gameDelegate;
	}

	public void setGameDelegate(IGameDelegate gameDelegate) {
		this.gameDelegate = gameDelegate;
	}
}
