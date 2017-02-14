package com.timagreat.stonetetris;

import com.badlogic.androidgames.framework.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.timagreat.stonetetris.Square;

//Коротко и понятно - ето клас для инициализации фигур, аточнее для их заполнения!

public abstract class Shape {
    int color;
    abstract Square[] getArray(); //абстрактный метод для получения данных о фигуре фигуры (описаных в класе Square);
    void update(float speedX, float speedY) { //метод для обновления позиции (данных) фигуры;
        Square[] sq = getArray();
        for (int i = 0; i < sq.length; i++) {
            sq[i].position.add(speedX, speedY);
            sq[i].newFieldY += speedY;
            sq[i].newFieldX += speedX;

        }
    }

    abstract int getType(); //Возвращает тип фигуры в виде номера константы;
}

class Shape1 extends Shape {  // Клас фигуры "Q" имеющий метод который заполнен координатами и принимает на вход цвет.
    Square[] a = new Square[4];
    int color;
    public Shape1(int color) {
        a[0] = new Square(new Vector2(7.5f, 23.5f), color, 7+ com.timagreat.stonetetris.World.PLUS_WIDTH, 23- com.timagreat.stonetetris.World.MINUS_HEIGHT); //координаты расположения елемента на поле. Размеры всего поля 16 на 27;
        a[1] = new Square(new Vector2(8.5f, 23.5f), color, 8+ com.timagreat.stonetetris.World.PLUS_WIDTH, 23- com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(7.5f, 22.5f), color, 7+ com.timagreat.stonetetris.World.PLUS_WIDTH, 22- com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(8.5f, 22.5f), color, 8+ com.timagreat.stonetetris.World.PLUS_WIDTH, 22- com.timagreat.stonetetris.World.MINUS_HEIGHT);
        this.color = color;
    }

    public Square[] getArray() { // заполняет и возвращает масив с данными
        Square[] square = a;
        return square;
    }

    @Override
    int getType() {
        return 1;
    }// при оспользовании метода возвращает тип фигуры в лице константы;
}


class Shape2 extends Shape {//Класс фигуры "I".
    Square[] a = new Square[4];
    int color;
    public Shape2(int color) {
        a[0] = new Square(new Vector2(6.5f, 23.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[1] = new Square(new Vector2(7.5f, 23.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(8.5f, 23.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(9.5f, 23.5f), color, 9+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        this.color = color;
    }

    public Square[] getArray() {
        return a;
    }
    int getType() {
        return 2;
    }
}

class Shape3 extends Shape {//Класс фигуры "J";
    Square[] a = new Square[4];

    public Shape3(int color) {
        a[0] = new Square(new Vector2(8.5f, 22.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[1] = new Square(new Vector2(7.5f, 22.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(6.5f, 22.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(6.5f, 23.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
    }

    public Square[] getArray() {
        return a;
    }
    int getType() {
        return 3;
    }
}

class Shape4 extends Shape {//Класс фигуры "L"
    Square[] a = new Square[4];

    public Shape4(int color) {
        a[0] = new Square(new Vector2(6.5f, 22.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[1] = new Square(new Vector2(7.5f, 22.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(8.5f, 22.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(8.5f, 23.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
    }

    public Square[] getArray() {
        return a;
    }
    int getType() {
        return 4;
    }
}
class Shape5 extends Shape {//Клас фигуры "Z"
    Square[] a = new Square[4];
    int color;
    public Shape5(int color) {
        a[0] = new Square(new Vector2(6.5f, 22.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[1] = new Square(new Vector2(7.5f, 22.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(7.5f, 23.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(8.5f, 23.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        this.color = color;
    }

    public Square[] getArray() {
        return a;
    }

    @Override
    int getType() {
        return 5;
    }
}
class Shape6 extends Shape {//Класс фигуры "T"
    Square[] a = new Square[4];
    int color;
    public Shape6(int color) {
        a[0] = new Square(new Vector2(6.5f, 22.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[1] = new Square(new Vector2(7.5f, 22.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(7.5f, 23.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(8.5f, 22.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        this.color = color;
    }

    public Square[] getArray() {
        return a;
    }

    @Override
    int getType() {
        return 6;
    }
}
class Shape7 extends Shape {//Класс фигуры "S"
    Square[] a = new Square[4];
    int color;
    public Shape7(int color) {
        a[0] = new Square(new Vector2(6.5f, 23.5f), color, 6+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[1] = new Square(new Vector2(7.5f, 23.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 23-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[2] = new Square(new Vector2(7.5f, 22.5f), color, 7+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        a[3] = new Square(new Vector2(8.5f, 22.5f), color, 8+com.timagreat.stonetetris.World.PLUS_WIDTH, 22-com.timagreat.stonetetris.World.MINUS_HEIGHT);
        this.color = color;
    }

    public Square[] getArray() {
        return a;
    }

    @Override
    int getType() {
        return 7;
    }
}



