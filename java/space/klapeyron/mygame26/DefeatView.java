package space.klapeyron.mygame26;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import space.klapeyron.mygame26.Enemies.Meteorit;
import space.klapeyron.mygame26.Flyship.Bullet;
import space.klapeyron.mygame26.Flyship.FlyShip;

public class DefeatView extends SurfaceView implements SurfaceHolder.Callback {


    Bitmap background;
    FlyShip flyship;
    //TODO
    ArrayList<Meteorit> meteorits = new ArrayList<>();

    DefeatView defeatViewLink = this;
    MainActivity mainActivityLink;
    PlayThread playThread;


    /*
     *frames per second
     */
    public final static int FPS = 80;
    /*
     *the interval of integration in milliseconds
     */
    public final static long dT = 1000 / FPS;

    public DefeatView(Context context,MainActivity mainActivity) {
        super(context);
        mainActivityLink = mainActivity;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    class PlayThread extends Thread {

        private boolean running = false;
        Canvas canvas;
        private int countdT = 0;
        public PlayThread(){}

        @Override
        public void run() {
            while(true) {
                if(running) {
                    canvas = defeatViewLink.getHolder().lockCanvas();
                    onDrawDefeat();
                    defeatViewLink.getHolder().unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(dT, 0);
                    } catch (InterruptedException e) {
                    }
                } else {
                    return;
                }
            }
        }

        private void onDrawDefeat() {
            canvas.drawBitmap(background, 0, 0, null);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(25);
            canvas.drawText("GAME OVER",200,200,paint);
            canvas.drawText("scores: "+mainActivityLink.gameView.getScores(),220,240,paint);
            for(int i=0;i< meteorits.size();i++) {
                meteorits.get(i).onDraw(canvas);
                if (meteorits.get(i).getY() > MainActivity.screenHeight) {
                    meteorits.remove(i);
                    i--;
                }
            }
        }

        public void setRunning(boolean b) {
            running = b;
        }

        public boolean isRunning() {
            return running;
        }
    }

    public Bitmap resizeImage(Bitmap image, int maxWidth, int maxHeight) {
        Bitmap resizedImage = null;
        try {
            resizedImage = Bitmap.createScaledBitmap(image, MainActivity.screenWidth, MainActivity.screenHeight, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizedImage;
    }
}
