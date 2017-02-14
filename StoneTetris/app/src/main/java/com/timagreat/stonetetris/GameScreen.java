package com.timagreat.stonetetris;

import android.content.Context;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Input.KeyEvent;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.helper.DebugDraw;
import com.badlogic.androidgames.framework.helper.FPSCounter;
import com.badlogic.androidgames.framework.helper.Logger;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

import com.timagreat.stonetetris.MainMenuScreen;
import com.timagreat.stonetetris.Settings;
import com.timagreat.stonetetris.Square;

//Класс состояния игрового екрана;

public class GameScreen extends GLScreen {

    static final int GAME_RUNNING = 1; //Константы состояний;
    static final int GAME_READY = 2;
    static final int GAME_PAUSE = 3;
    static final int GAME_OVER = 4;



    private SpriteBatcher batcher; //Предназначен для отрисовки текстур, регионов текстур и спрайтов;
    Camera2D guiCam;//Концентрируется на игровое поле. Своего рода "виртуальное окно" в наш мир
    com.timagreat.stonetetris.World world;//Екземпляр класса World;
    FPSCounter fps = new FPSCounter(); //Счетчик fps, для мониторинга разраба**************;
    Logger log = new Logger();//Логи для мониторинга;
    TextureRegion text;//Регионы текстур;
    DebugDraw dd;
    Vector2 touchPoint = new Vector2();//Точка соприкасания по вектору;
    int state; //Переменная состояния;

    public GameScreen(Game game) {
        super(game); //Переоприделяет класс game,в libGDX является абстрактным и предоставляет для использования реализацию ApplicationListener, наряду со вспомогательными методами для обработки визуализации экрана.
        batcher = new SpriteBatcher(glGraphics, 360);//Сколько максимально спрайтов буферезировать;
        guiCam = new Camera2D(glGraphics, 16, 24);//Фиксирует игровой мир 16(по X) на 24(по Y);
        world = new com.timagreat.stonetetris.World();//Создает новый екземпляр World;
        state = GAME_RUNNING;//Присваивает состоянию константу запущено;
        dd = new DebugDraw(glGraphics);

    }

    @Override
    public void update(float deltaTime) { //Класс обновления статуса ориентируясь на константу времени;
        switch (state) {
            case GAME_RUNNING:
                updateRunning(deltaTime);//Если запущено, вызываем метод который то же ориентируется на константу времени;
                break;
            case GAME_READY:
                updateReady();
            case GAME_PAUSE:
                updatePause();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    //Метод обновления касания************
    void updateRunning(float deltaTime) {

        if (com.timagreat.stonetetris.World.state == com.timagreat.stonetetris.World.STATE_GAME_OVER) { //Если классе World состояние равно константе game over, тогда game over присваивается константе состояния етого класса;
            state = GAME_OVER;
            return;
        }
        world.update(deltaTime); //К екземпляру класса world присваивается его метод;
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();//Список строк с координатами нажатия на дисплей.Touch Event представляет событие которое происходит когда изменяется состояние касания на поверхности.
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touch = touchEvents.get(i);//По одному берем елемент со списка;

            if (touch.type == TouchEvent.TOUCH_DOWN) {//Если тип елемента совпадает с константой Touch Down(Нажатие)
                touchPoint.set(touch.x, touch.y);     //touch point - Точка контакта с поверхностью.Тогда в вектор передаем координаты
                guiCam.touchToWorld(touchPoint);      //ну апотом в камеру передаем вектор с координатами;
                if (OverlapTester
                        .pointInRectangle(world.turnButton, touchPoint)) {//Проверка, если точка прикосновения(Вектор)оказивается на кнопке(прямоугольнике), повернуть фигуру,
                    com.timagreat.stonetetris.FactoryShape.turnShape(world.shape, world.field);     //Тогда запускаем метод поворота фигуры описаного в класе FactoryShape, и вносим туда фигуру и поле с класса World;


                }
                if (OverlapTester.pointInRectangle(world.moveRightButton,//То же самое и с кнопкой в право;
                        touchPoint)) {
                    com.timagreat.stonetetris.FactoryShape.moveRightShape(world.shape, world.field);

                }
                if (OverlapTester.pointInRectangle(world.moveLeftButton,//То же самое и с кнопкой в лево;
                        touchPoint)) {

                    com.timagreat.stonetetris.FactoryShape.moveLeftShape(world.shape, world.field);

                }
                if (OverlapTester.pointInRectangle(world.fieldScreen,//То же самое и при нажатиии на поле игровое;
                        touchPoint)) {
                    world.fallShape();//Запускается метод падения в world;
                }
                if (OverlapTester
                        .pointInRectangle(world.downButton, touchPoint)) {//При нажатии на кнопку быстрее;
                    world.fasterDown();//Запускается метод ускорения падения;

                }

            }
        }

    }

    //Метод паузы*******************************
    void updatePause() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();//Заганяем касания в список;
        game.getInput().getKeyEvents();//Читаем нажатие на кнопку(именно кнопку а не дисплей)
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touch = touchEvents.get(i);//Берем по одному со списка;
            if (touch.type == TouchEvent.TOUCH_UP) {//Отпускание;
                touchPoint.set(touch.x, touch.y);//Вносим координаты;
                guiCam.touchToWorld(touchPoint);//Передаем камеере;
                if (OverlapTester.pointInRectangle(world.noBtn, touchPoint)) { //Если совпадают с координатами квадрата кнопки No
                    state = GAME_RUNNING;                             //Запускаем игру заново
                }
                if(OverlapTester.pointInRectangle(world.yesBtn, touchPoint)){//Усли совпадает с координатами квадрата точки Yes
                    state = GAME_OVER; //Останавливаем игру
                    game.setScreen(new com.timagreat.stonetetris.MainMenuScreen(game));//Выход на основной екран игры;
                }


            }
        }
    }

    //Обновление конца игры*************
    void updateGameOver() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();//Заганяем касания в список;
        game.getInput().getKeyEvents();//Читаем нажатие на кнопку(именно кнопку а не дисплей)
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touch = touchEvents.get(i);//Читаем касания по одному;
            touchPoint.set(touch.x, touch.y);//Вносим координаты;
            guiCam.touchToWorld(touchPoint);//Передаем камере;
            if (touch.type == TouchEvent.TOUCH_DOWN) {//Если нажимаем...
                if (OverlapTester.pointInRectangle(world.fieldScreen,//Если нажимаем поле игровое...
                        touchPoint))
                    ;
                Settings.addScore(world.score);//Сравнование результатов игры (Больше-меньше);
                Settings.saveInBase();//сохраняем
                state = GAME_READY;//Статус принимае состояние готов! и продолжаем
                break;

            }
        }
    }

    //Метод обновления готовности*******
    void updateReady() {
        world.reload();//Обнуляем все методом reload;
        state = GAME_RUNNING;//Переводим игру в статус "запущенно"
    }

    //Метод призентации и прорисовки атласа******
    @Override
    public void present(float deltaTime) {//обновляет чере закоето время;
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);//очистит все цвета на екране;
        gl.glLoadIdentity();//сбрасываем все параметры,получается единичная матрица;
        guiCam.setViewportAndMatrices();//обновляет матрицу добавляя в нее изминения;
        gl.glEnable(GL10.GL_BLEND);//Включает смешение обчисленых значений цвета фрагмента с значением в цветовом буфере;
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//Указывает пиксельную арифметику.Прозрачность фона про отрисовки елемента текстуры;
        gl.glEnable(GL10.GL_TEXTURE_2D);//Включает использование 2D текестур.
        switch (state) {
            case GAME_RUNNING: //Если статус "запущенная";
                batcher.beginBatch(com.timagreat.stonetetris.Assets.atlas);//Берем атлас;
                batcher.drawSprite(8f, 12, 16, 24, com.timagreat.stonetetris.Assets.background, 1);//Загружаем игровое поле с атласа;
                drawWorld();//Отрисовываем мир игры;
                batcher.endBatch();//Закрываем бачер;
                batcher.beginBatch(com.timagreat.stonetetris.Assets.sd_0);//Берем другой фон;(Прозрачность)
                com.timagreat.stonetetris.Assets.font.drawText(batcher, "" + world.score, 13.7f, 9.1f, 0.1f);//Отрисовываем тескст счета;
                com.timagreat.stonetetris.Assets.font.drawText(batcher, "" + world.level, 14.4f, 22, 0.15f);//Отрисовываем текст уровня;
                batcher.endBatch();
                break;
            case GAME_OVER://Если проигрыш;
                batcher.beginBatch(com.timagreat.stonetetris.Assets.backgroundGameOver);//Берем атлас проигрыша;
                batcher.drawSprite(8f, 12, 16, 24, com.timagreat.stonetetris.Assets.backgroundGameOverRegion, 1);//Отрисовываем его по ращмерам;
                batcher.endBatch();//Закрываем батч
                batcher.beginBatch(com.timagreat.stonetetris.Assets.sd_0);//Создаем атлас пустой;
                com.timagreat.stonetetris.Assets.font.drawText(batcher, world.score + "", 7, 14.1f, 0.1f);//Пишем текс счета на екран;
                batcher.endBatch();//закрываем батч;
                break;
            case GAME_READY://Статусч готово:
                batcher.beginBatch(com.timagreat.stonetetris.Assets.atlas);//Грузим атлас;
                batcher.drawSprite(8f, 12, 16, 24, com.timagreat.stonetetris.Assets.background, 1);//Прорисовываем спрайт игрового поля;
                batcher.endBatch();//закрываем батч
                break;
            case GAME_PAUSE://Статус паузы
                batcher.beginBatch(com.timagreat.stonetetris.Assets.atlas);//Грузим атлас;
                batcher.drawSprite(8f, 12, 16, 24, com.timagreat.stonetetris.Assets.background, 1);//Прорисовываем спрайт игрового поля;
                drawWorld();//отрисовываем мир:
                batcher.drawSprite(7.0f, 15, 8, 8, com.timagreat.stonetetris.Assets.exit, 1);//Рисуем спрайт выхода;
                batcher.endBatch();//закрываем батч;
                batcher.beginBatch(com.timagreat.stonetetris.Assets.sd_0);//окрываем батч;
                com.timagreat.stonetetris.Assets.font.drawText(batcher, "" + world.score, 13.7f, 9.1f, 0.1f);//рисуем текст счета;
                com.timagreat.stonetetris.Assets.font.drawText(batcher, "" + world.level, 14.4f, 22, 0.15f);//рисуем текст уровня;
                batcher.endBatch();//закрываем батч;
                break;

        }

        gl.glDisable(GL10.GL_BLEND);//Вырубаем смешивание;
        gl.glDisable(GL10.GL_TEXTURE_2D);//Вырубаем текстуры;
        fps.logFrame();//пишем fps в лог;

    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {
        // Assets.reload();
        state = GAME_RUNNING;

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    //Клас отрисовки мира***************
    public void drawWorld() {
        for (int i = 0; i < com.timagreat.stonetetris.World.FIELD_HEIGHT; i++) {   //Пробиваем каждую точку поля
            for (int j = 0; j < com.timagreat.stonetetris.World.FIELD_WIDTH; j++) {
                if (world.field[i][j] != null) {         //Если ета точка не null
                    text = com.timagreat.stonetetris.FactoryShape.setColor(world.field[i][j].color);//Задаем ей цвет
                    batcher.drawSprite(j - 2 + 0.5f, i + 0.5f + 1, 1, 1, text,//Потом прорисовываем фигуру
                            1f);
                }
            }
        }

        Square[] shape = world.shape.getArray(); //Берем фигуру и розбываем ее данные на масив;
        text = com.timagreat.stonetetris.FactoryShape.setColor(shape[0].color);//В текстурах выбераем цвет по цвету первого елеиента фигуры;
        for (int i = 0; i < shape.length; i++) { //В цикле прорисовываем все елементы, ориентируясь на елементы и на цвет;
            batcher.drawSprite(shape[i].position.x, shape[i].position.y, 1, 1,
                    text, 1f);
        }

        Square[] miniShape = world.miniShape;//Тут все то же самое только с мими фигурками;
        text = com.timagreat.stonetetris.FactoryShape.setColor(miniShape[0].color);
        for (int i = 0; i < 4; i++) {
            batcher.drawSprite(miniShape[i].position.x,
                    miniShape[i].position.y, 0.7f, 0.7f, text, 1);
        }

    }


    public void fuck() {
        if (state == GAME_RUNNING) {
            state = GAME_PAUSE;
        }

    }

}
