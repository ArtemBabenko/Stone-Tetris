package com.timagreat.stonetetris;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Random;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class World {
    com.timagreat.stonetetris.Square[][] field;//Двухмекрный масив поля;
    com.timagreat.stonetetris.Shape shape;
    com.timagreat.stonetetris.Square[] miniShape;
    com.timagreat.stonetetris.Square[] shapeAr; //Масив в котором будут хранятся координаты елементов фигур;
    com.timagreat.stonetetris.Square sq1;
    int score;
    int level;
    int levelScore;
    static final int STATE_GAME_OVER = 1;
    static final int STATE_GAME_RUNNING = 2;
    static final int FIELD_WIDTH = 17;
    static final int FIELD_HEIGHT = 26;
    static final int SQUARE_CAPACITY = FIELD_WIDTH * FIELD_HEIGHT;
    static final float TICK_INITIAL = 0.8f;
    static final int MINUS_HEIGHT = 1;
    static final int PLUS_WIDTH = 2;
    Vector2 speed = new Vector2();
    float tick = 0;
    float tickLevel = 0;
    float tickTimeLevel = 160;
    float tickTime = TICK_INITIAL;
    int capacity = SQUARE_CAPACITY;
    static int state = 0;
    Rectangle turnButton;
    Rectangle moveRightButton;
    Rectangle moveLeftButton;
    Rectangle fieldScreen;
    Rectangle downButton;
    Rectangle yesBtn;
    Rectangle noBtn;
    static Random rand = new Random();
    private  int shapeType = rand.nextInt(7);
    private int color = rand.nextInt(4);


    private Context mContext;
    private MediaPlayer mMediaPlayer;

    float tickTimeShape = 0;//Переменная куда сохраняется временная константа и сравнивается для задержки;
    float tickTimeShapeInitial =  0.17f;//Задержка перед созданием новой фигуры;
    boolean shapeFall = false; //Переменная идентифицирующая падение фигуры (тоесть она упала или нет/true или folse);

    /*
    you need add onDestroy(), onResume() and onPause() to manage mMediaPlayer state
     */

    public World(Context context) {
        mContext = context;
        mMediaPlayer = MediaPlayer.create(context, R.raw.tetris_gameboy_typea);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        field = new com.timagreat.stonetetris.Square[FIELD_HEIGHT][FIELD_WIDTH];
        shape = com.timagreat.stonetetris.FactoryShape.newShape(rand.nextInt(7),rand.nextInt(3));
        score = 0;
        level = 1;
        levelScore = 0;
        speed = new Vector2(0, -1);
        turnButton = new Rectangle(6.1f, 0, 4, 3);
        moveLeftButton = new Rectangle(-2, 0, 8, 3);
        moveRightButton = new Rectangle(10, 0, 7, 3);
        fieldScreen = new Rectangle(0, 3.05f, 13, 22);
        downButton = new Rectangle(13.5f,4,3,3);
        noBtn = new Rectangle(7.3f,13f,4,3);
        yesBtn = new Rectangle(2.3f,13f,4,3);
        shapeAr = shape.getArray();
        state = STATE_GAME_RUNNING;
        newMiniShape();
    }

    void update(float deltaTime) {
        updateCollision();//Проверяем на возможность запустить фигуру в поле, достигла она дна, или же упала на фигуры или же достигла потолка;
        moveShape(deltaTime, speed);//Обновление фигуры по времени;
        updateLevel(deltaTime);//Обновляет уровень;
        if(shapeFall){ //Если фигура не движится (на дне или на другой фигуре) тогда...
            tickTimeShape +=deltaTime;//Присваеваем переменной текущее дельта время;
            if(tickTimeShape > tickTimeShapeInitial){//Если больше константы тогда...
                shapeFall = false;//фигура уже не лежит;
                tickTimeShape = 0;//Обнуляем константу;
                insertShape();//Вставляем фигуру в поле;
                newShape();//Создаем фигуру;
                shapeType = rand.nextInt(7);//Рандомно заряжаем тип;
                color = rand.nextInt(4);//Рандомно заряжаем цвет;
                newMiniShape();//Строим мини фигурку для превюшки;
                deleteFullLines();
            }
        }

    }

    //Метод обновления движения фигуры по времени(скоросчть передвижения)*********
    void moveShape(float deltaTime, Vector2 speed) {
        tick += deltaTime;//Переменной тик присваивается текущее время "шага";
        if (tick > tickTime) {//Тогда если текущее время "шага" больше заданого "время шага" (она рано константе 0,8f);
            tick -= tickTime; //Обнуляем переменную;
            shape.update(speed.x, speed.y);//Таким образом ориентируясь на временное обновление обновляем координаты фигуры(место расположение) каждые 0,8f;
        }
    }

    //Метод который вставляет фигуру в поле*************
    void insertShape() {

        for (int i = 0; i < shapeAr.length; i++) {
            field[shapeAr[i].newFieldY][shapeAr[i].newFieldX] = shapeAr[i];//Вставляет координаты из масива координат фигур в дыухмерный масив поля;
        }

    }

    //Метод столкновения*******
    void updateCollision() {
        for (int i = 0; i < shapeAr.length; i++) { //Идем по размеру масива;
            if (shapeAr[i].newFieldY == 2
                    || field[shapeAr[i].newFieldY - 1][shapeAr[i].newFieldX] != null) {//Если по Y(по вертикали) координата раняется 2(на дне) или относительно поля следующее движение фигуры не возможно(есть фигура уже);
                stopShape();  //Тогда используем метод остановки фигуры;
                if (shapeAr[i].newFieldY == 21) { //Если елемент фигуры еще и будет совпадать с координатами в точке 21 по Y(по вертикале);
                    state = STATE_GAME_OVER; //Присваиваем состоянию статус game over;
                    break;
                }
                stopShape(); //если он не совпадает с координатами(значет место для заполнение еще есть)21, тогда применяем метод остановки фигуры;
                shapeFall = true;//Фигура упала;
                break;
            }else{
                shapeFall = false;//Переводим падение в исходное состояние(не упала);
                startShape();//Если ничего выше описаного не происходит, тогда стартуем фигуру;
            }
        }
    }

    //Метод остановки фигуры*******
    void stopShape() {
        speed.set(0, 0);//Присвоение скорости значения 0. А точнее Vektor2(0, 0);
    }

    //Метод запуска фигуры********
    void startShape(){
        speed.set(0,-1); //Присвоение скорости значения -1 по Y(вертикали)с каждым шагом. А точнее Vektor2(0, -1);
    }

    //Метод создания фигуры с помощью фабрики*********
    void newShape() {
        shape = com.timagreat.stonetetris.FactoryShape.newShape(shapeType,color);//Екземпляр класса Фигура который заполняется с помощью фабрики и ее метода;
        speed.set(0, -1);//Перемещение по вектору на еденицу;
        shapeAr = shape.getArray();//Добавляем фигуру в масив фигур;
        com.timagreat.stonetetris.FactoryShape.positionLine = 1;//Ставим первую позицию на сллучай если нам попадется фигура "I";
    }

    //Метод падения фигуры***********************
    void fallShape() {
        boolean fall = false;//Переменная падения;
        int y = shapeAr[1].newFieldY;//Берем координаты первого елемента по Y;(Общее выражение в виде константы y)
        synchronized (shape) {//Синхронизируем с фигурой;
            while (true) {
                com.timagreat.stonetetris.Square[] shapeAr = shape.getArray();
                for (int i = 0; i < shapeAr.length; i++) {
                    if (shapeAr[i].newFieldY != 2
                            && field[shapeAr[i].newFieldY - 1][shapeAr[i].newFieldX] == null) {

                    } else {
                        stopShape();
                        fall = true;
                        break;
                    }

                }

                if (fall) {
                    //score+=y/3+levelScore;
                    playSound(R.raw.line_drop);
                    break;
                }
                shape.update(0, -1);//Обновляется фигура каждие -1 шаг;
            }
        }
    }

    //Метод удаления заполненых линий***********
    void deleteFullLines() {
        boolean isFull = true;//Переменная заполнености цикла
        //Ищем по точке заполненость.Первый цикл ето линии по вертикали, второй по горезонтали. Если хотя бы одна точка не заполнена, выходим из внутренего цикла. Если заполнены все тода записываем номер строки в переменную;
        int firstFullLine = 0;//Запысываем сюда результат первого цикла;
        for (int i = 2; i <= 23; i++) {
            for (int j = 2; j <= 14; j++) {
                if (field[i][j] == null) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                firstFullLine = i;
                break; //Если находи хоть одну заполненую, выходит из основного цикла;
            }
            isFull = true;
        }

        //Ищем по точке заполненость но только сверху.Обнуляем строку. Записываем в переменную строку. Записываем очки;
        int lastFullLine = 0;//Переменная заполнености цикла
        for (int i = 23; i >= 2; i--) {
            for (int j = 14; j >= 2; j--) {
                if (field[i][j] == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                for (int b = 14; b >= 2; b--) {
                    field[i][b] = null;
                }
                lastFullLine = i;
                score+=10+levelScore;
            }
            isFull = true;

        }

        boolean fill = false;
        int fillLine = firstFullLine; //Заполненая линия которая = первой заполненой линией;
        //У нас выходит так, что последняя линия совпадает с первой, так как при нахождени первой попавшейся линии выходим с основного цикла. И если последняя заполненая линия больше 0 тогда...
        if (lastFullLine > 0) {
            for (int i = lastFullLine + 1; i < 23; i++) {  //К последней линии прибавляем 1 для того что бы перейти на линию выш...
                for (int j = 2; j <= 14; j++) {
                    if (field[i][j] != null) {
                        field[fillLine][j] = field[i][j];//Вот таким образом если точка не пустая мы копируем линию которая была выше удаленой на ее место и тд. Таким образом создается ефект падения блока после удаления;
                        field[i][j] = null;
                        fill = true;

                    }
                }
                if (fill == true) {
                    fillLine += 1;
                }
                fill = false;
            }
            playSound(R.raw.line_remove);
        }

    }

    private void playSound(int id) {
        final MediaPlayer sound = MediaPlayer.create(mContext, id);
        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sound.stop();
                sound.release();
                mMediaPlayer.start();
            }
        });
        if(mMediaPlayer.isPlaying()) mMediaPlayer.pause();
        sound.start();
    }

    //Метод перезагрузки поля*********
    void reload() {
        //Чистим поле ну и обнуляем все остальное;
        for (int i = 1; i < FIELD_HEIGHT-1; i++) {
            for (int j = 1; j < FIELD_WIDTH-1; j++) {
                field[i][j] = null;
            }
            newShape();
            state = STATE_GAME_RUNNING;
            score = 0;
            level = 1;
            levelScore =0;
            tickTime = TICK_INITIAL;

        }
    }

    //Метод мини фигурок*********
    void newMiniShape() {
        int type = shapeType+1; //Выбирается тип фигуры. +1 потому что отсчит идет с 0. У нас нет нулевого типа;
        int color = this.color; //Выбирает цвет;
        miniShape = new com.timagreat.stonetetris.Square[4];//Берет фигуру с данныфми, и в зависимости от типа задает ей координаты;
        switch (type) {
            case 0:
                return;
            case 1:
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(14.2f, 19.0f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.9f, 19.0f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.2f, 19.7f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(14.9f, 19.7f), color, 0, 0);
                break;

            case 2:
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(14.5f, 20.3f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.5f, 19.6f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.5f, 18.9f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(14.5f, 18.2f), color, 0, 0);
                break;
            case 3:
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(14.9f, 19.9f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.2f, 19.9f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.2f, 19.2f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(14.2f, 18.5f), color, 0, 0);
                break;
            case 4:
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(14.2f, 19.9f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.9f, 19.9f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.9f, 19.2f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(14.9f, 18.5f), color, 0, 0);
                break;
            case 5:
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(13.9f, 19.0f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.6f, 19.0f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.6f, 19.7f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(15.3f, 19.7f), color, 0, 0);
                break;
            case 6 :
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(13.9f, 19.0f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.6f, 19.0f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.6f, 19.7f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(15.3f, 19.0f), color, 0, 0);
                break;
            case 7 :
                miniShape[0] = new com.timagreat.stonetetris.Square(new Vector2(13.9f, 19.7f), color, 0, 0);
                miniShape[1] = new com.timagreat.stonetetris.Square(new Vector2(14.6f, 19.0f), color, 0, 0);
                miniShape[2] = new com.timagreat.stonetetris.Square(new Vector2(14.6f, 19.7f), color, 0, 0);
                miniShape[3] = new com.timagreat.stonetetris.Square(new Vector2(15.3f, 19.0f), color, 0, 0);
                break;


        }
    }

    //Метод нажатия кнопки быстрее********
    public void fasterDown(){
        if(shapeFall!=true){
            shape.update(0, -1);//Перемещает на 1 еденицу вперед по вертекали;
        }
    }

    //Метод обновления уровня*************
    public void updateLevel(float deltaTime){ //Делает почти то же самое что и метод обновление фигуры, вот только обновляет уровень
        tickLevel += deltaTime;
        if (tickLevel > tickTimeLevel) {
            tickLevel -= tickTimeLevel;
            level +=1;
            levelScore+=10;
        }
    }
}

