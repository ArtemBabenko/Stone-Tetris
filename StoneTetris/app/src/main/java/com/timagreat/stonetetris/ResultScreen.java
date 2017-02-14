package com.timagreat.stonetetris;
import android.content.Context;

import com.badlogic.androidgames.framework.Input.KeyEvent;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.timagreat.stonetetris.Settings;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

//Класс результатов игры

public class ResultScreen extends GLScreen {

    Camera2D  guiCam;//Объявляем камеру
    SpriteBatcher batcher;//Предназначен для отрисовки текстур, регионов текстур и спрайтов;
    Vector2 touchPoint = new Vector2();
    Rectangle exit;


    public ResultScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics,320,480);//Задаем камере размер;
        batcher = new SpriteBatcher(glGraphics,100);//Отрисовка спрайта
        exit = new Rectangle(110,50,96,45);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {


            TouchEvent touch = touchEvents.get(i);

            if (touch.type == TouchEvent.TOUCH_DOWN) {
                touchPoint.set(touch.x, touch.y);
                guiCam.touchToWorld(touchPoint);
                if (OverlapTester.pointInRectangle(exit, touchPoint)) {
                    game.setScreen(new com.timagreat.stonetetris.MainMenuScreen(game));
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
        batcher.beginBatch(com.timagreat.stonetetris.Assets.result);
        batcher.drawSprite(160, 240, 320, 480, com.timagreat.stonetetris.Assets.startScreenRegion, 1);
        batcher.endBatch();
        batcher.beginBatch(com.timagreat.stonetetris.Assets.sd_0);
        com.timagreat.stonetetris.Assets.font.drawText(batcher, Settings.highscore+"", 120, 380, 3);
        com.timagreat.stonetetris.Assets.font.drawText(batcher,Settings.highscore2+"", 120, 340, 3);
        com.timagreat.stonetetris.Assets.font.drawText(batcher,Settings.highscore3+"", 120, 300, 3);
        com.timagreat.stonetetris.Assets.font.drawText(batcher,Settings.highscore4+"", 120, 260, 3);
        com.timagreat.stonetetris.Assets.font.drawText(batcher,Settings.highscore5+"", 120, 220, 3);
        batcher.endBatch();
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
