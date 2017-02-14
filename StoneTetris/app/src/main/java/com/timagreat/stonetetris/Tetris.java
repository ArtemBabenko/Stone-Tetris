package com.timagreat.stonetetris;

import android.media.MediaPlayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;



//Изображение надо на чем-то показывать. Для этого мы будем использовать компонент GLSurfaceView (surface).
//Класс запускалка. Наследует GLGame в котором розмещены методы onCreate и тд. Промежуточный класс, наследует тот который принимает от нас инструкции, что и как рисовать(Render).
public class Tetris extends GLGame {
    boolean firstTimeCreate = false;

    @Override
    public Screen getStartScreen() {//Запускает класс главного меню. Наследывается от класса Game который в свою очередь имплементит GLGame;
        return new com.timagreat.stonetetris.MainMenuScreen(this);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {//Вызывается при создании/пересоздании surface. Т.е. метод будет вызываться при запуске приложения или, например,
        super.onSurfaceCreated(gl, config);//в уже запущенном приложении при выходе девайса из сна. Здесь будет выполняться установка OpenGL параметров и инициализация графических объектов.
        new com.timagreat.stonetetris.Settings(getApplicationContext());
        if (firstTimeCreate == false) {
            com.timagreat.stonetetris.Assets.load(this);
            com.timagreat.stonetetris.Settings.load();
            firstTimeCreate = true;
        } else {
            com.timagreat.stonetetris.Assets.reload();
        }
    }
}