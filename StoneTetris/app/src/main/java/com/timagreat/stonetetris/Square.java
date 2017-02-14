package com.timagreat.stonetetris;
import com.badlogic.androidgames.framework.math.Vector2;

//Класс инициализации игрового поля;

public class Square {
    Vector2 position; //векторная позиция для двигающих елементов
    int color;
    int newFieldX,newFieldY;
    public Square(Vector2 position,int color,int fieldX,int fieldY){
        this.position = position;
        this.color = color;
        this.newFieldX = fieldX; //координаты поля
        this.newFieldY = fieldY;
    }
}
