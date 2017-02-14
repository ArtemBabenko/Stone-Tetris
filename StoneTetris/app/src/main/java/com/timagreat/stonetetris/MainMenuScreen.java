package com.timagreat.stonetetris;


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.helper.DebugDraw;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.timagreat.stonetetris.ResultScreen;

public class MainMenuScreen extends GLScreen{
    private final String TAG = "ST | MainMenuScreen";

    Camera2D  guiCam;//Объявляем камеру
    SpriteBatcher batcher;//Предназначен для отрисовки текстур, регионов текстур и спрайтов;
    Rectangle start;//Прямоугольник "старт";
    Rectangle result;//Прямоугольник "результат";
    DebugDraw dd;
    Vector2 touchPoint = new Vector2();//Вектор для опредиления точки прикосновения;

    Sound sound;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics,320,480);//Задаем камере размер;
        batcher = new SpriteBatcher(glGraphics,100);//Отрисовка спрайта
        start = new Rectangle(110,170,96,45);//110(по X) и 170(по Y) координаты левой нижней точки, 96 - ширина, 45 - висота;
        result = new Rectangle(110,90,96,45);
        dd = new DebugDraw(glGraphics);
    }

    @Override
    public void update(float deltaTime) {
        Log.v(TAG, "Update mainMenuScreen");
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touch = touchEvents.get(i);

            if (touch.type == TouchEvent.TOUCH_DOWN) {
                touchPoint.set(touch.x, touch.y);
                guiCam.touchToWorld(touchPoint);
                if (OverlapTester.pointInRectangle(start, touchPoint)) {
                    game.setScreen(new com.timagreat.stonetetris.GameScreen(game));
                }
                if(OverlapTester.pointInRectangle(result, touchPoint)){
                    game.setScreen(new ResultScreen(game));
                }

            }
        }

    }
    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        batcher.beginBatch(com.timagreat.stonetetris.Assets.startScreen);
        batcher.drawSprite(160, 240, 320, 480, com.timagreat.stonetetris.Assets.startScreenRegion, 1);
        batcher.endBatch();
        //batcher.beginBatch(Assets.sd_0);
        //Assets.font.drawText(batcher,Settings.highscore+"", 120, 385, 3);
        //batcher.endBatch();
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
    }
    public void fuck() {
    }
}
