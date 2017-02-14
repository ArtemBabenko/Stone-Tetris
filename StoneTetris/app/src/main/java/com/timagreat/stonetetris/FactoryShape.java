package com.timagreat.stonetetris;

import com.badlogic.androidgames.framework.gl.TextureRegion;

//Класс имеющий методы для проверки поворота, самого поворота, инициализации цвета, перемещения фигур;

public class FactoryShape {

    static int positionLine = 1; //Отображает позицию(вертикальная или горизонтальная) фигуры "I";

    public static Shape newShape(int shapeType,int color) { //возвращает фигуру по типу

        switch (shapeType) {
            case 0:
                return new Shape1(color);
            case 1:
                return new Shape2(color);
            case 2:
                return new Shape3(color);
            case 3:
                return new Shape4(color);
            case 4:
                return new Shape5(color);
            case 5:
                return new Shape6(color);
            case 6:
                return new Shape7(color);
        }


        return new Shape1(color);
    }

    public static TextureRegion setColor(int color) { //выбирает цвет текстуры по типу заданого цвета фигуре;

        switch (color) {
            case 0:
                return Assets.blue;
            case 1:
                return Assets.green;
            case 2:
                return Assets.red;
            case 3:
                return Assets.blueLight;
        }
        return null;
    }

    public static void turnShapeLine(Shape shape) { //пооврот фигуры "I" в отдельном классе
        Square square[] = shape.getArray();

        if (positionLine == 1) {             //Позиция линиии 1 ето горизонтальная позиция.Поворачиваем вокруг 2 елемента.Если ближайшие елементы к двойке используем один метод, если нет то другой;
            turnSquare(square[1], square[2]);
            turnSquare(square[3], square[2]);
            turnAroundSquareLine(square[0], square[2]);
        } else {
            turnSquare(square[0], square[1]);//Позиция линиии 2 ето вертикальная позиция.Поворачиваем вокруг 1 елемента.Если ближайшие елементы к еденице используем один метод, если нет то другой;
            turnSquare(square[2], square[1]);
            turnAroundSquareLine(square[3], square[1]);
        }

    }

    public static void turnShape(Shape shape, Square field[][]) { //поворот фигуры;
        Square[] square = shape.getArray(); //розбиваем фигуру на масив данных координат;
        Square squareCent = square[1];//берем точку;
        int type = shape.getType();//берем тип фигуры;
        boolean disable = false;
        if (disable != true) {
            switch (type) {// по типу проверяем фигуру;
                case 1: //Здесь у нас "Q". Возвращаем пустое значение;
                    return;
                case 2://Здесь у нас "I".
                    if (positionLine == 1) {
                        if (isNearSquare(square[1], field) == false//Проверяем 3 елемента на возможность поворота, если не folse, тогда продолжаем
                                && isNearSquare(square[3], field) == false
                                && isNearAroundSquareLine(square[0], field) == false) {
                            moveIfNearWallLine(shape);//Если фигура находится в упор к стенкам боковым, тогда делаем отступ при повороте
                            turnShapeLine(shape);//Используем особый метод поворота для етой фигуры
                            positionLine = 2;
                            return;
                        }
                    }
                    if (positionLine == 2) {
                        if (isNearSquare(square[0], field) == false
                                && isNearSquare(square[2], field) == false
                                && isNearAroundSquareLine(square[3], field) == false) {

                            moveIfNearWallLine(shape);
                            turnShapeLine(shape);
                            positionLine = 1;
                            return;
                        }
                    }

                    return;
                case 3:
                    if (isNearSquare(square[0], field) == false
                            && isNearSquare(square[2], field) == false
                            && isNearAroundSquare(square[3], field) == false) {
                        moveIfNearWall(shape, 1);
                        turnSquare(square[0], squareCent);
                        turnSquare(square[2], squareCent);
                        turnAroundSquare(square[3], squareCent);
                    }
                    return;
                case 4:
                    if (isNearSquare(square[0], field) == false
                            && isNearSquare(square[2], field) == false
                            && isNearAroundSquare(square[3], field) == false) {
                        moveIfNearWall(shape, 1);
                        turnSquare(square[0], squareCent);
                        turnSquare(square[2], squareCent);
                        turnAroundSquare(square[3], squareCent);
                    }
                    return;
                case 5:
                    if (isNearSquare(square[0], field) == false
                            && isNearSquare(square[2], field) == false
                            && isNearAroundSquare(square[3], field) == false) {
                        moveIfNearWall(shape, 1);
                        turnSquare(square[0], squareCent);
                        turnSquare(square[2], squareCent);
                        turnAroundSquare(square[3], squareCent);
                    }
                    return;
                case 6:

                    if (isNearSquare(square[0], field) == false
                            && isNearSquare(square[2], field) == false
                            && isNearSquare(square[3], field) == false) { //Здесь метод сстоит isNearSquare так-как 3-й элемент находится на 1 или -1 от центра как и все блжайшие;
                        moveIfNearWall(shape, 1);
                        turnSquare(square[0], squareCent);
                        turnSquare(square[2], squareCent);
                        turnSquare(square[3], squareCent);
                    }
                    return;
                case 7:

                    if (isNearSquare(square[0], field) == false
                            && isNearSquare(square[2], field) == false
                            && isNearAroundSquare(square[3], field) == false) {
                        moveIfNearWall(shape, 1);
                        turnSquare(square[0], squareCent);
                        turnSquare(square[2], squareCent);
                        turnAroundSquare(square[3], squareCent);
                    }
                    return;
            }
        }

    }

    static void turnSquare(Square square, Square squareCent) {//Поворачивает елемент фигуры на 1 или -1 значение по двум параметрам;
        if (square.newFieldX - 1 == squareCent.newFieldX      //у нас есть елемент фигуры, ориентируясь на который мы вращаем фигуру вокруг него.
                && square.newFieldY == squareCent.newFieldY) {//а теперь в етом ифе проверяем если соседний елемент в переди, или с зади прилегает к данному елементу-центру,
            square.newFieldX -= 1;                            // тогда вертим соседний по часовой на 1 еденицу
            square.newFieldY -= 1;
            square.position.add(-1, -1);
        } else if (square.newFieldX == squareCent.newFieldX
                && square.newFieldY + 1 == squareCent.newFieldY) {
            square.newFieldX -= 1;
            square.newFieldY += 1;
            square.position.add(-1, +1);
        } else if (square.newFieldX + 1 == squareCent.newFieldX
                && square.newFieldY == squareCent.newFieldY) {
            square.newFieldX += 1;
            square.newFieldY += 1;
            square.position.add(+1, +1);
        } else if (square.newFieldX == squareCent.newFieldX
                && square.newFieldY - 1 == squareCent.newFieldY) {
            square.newFieldX += 1;
            square.newFieldY -= 1;
            square.position.add(+1, -1);
        }

    }

    static void turnAroundSquare(Square square, Square squareCent) {//Поворачивает елемент фигуры на 2 или -2 значение по х или y  параметрам;
        //Метод для 4 елемента фигуры(самого крайнего и отдаленого от центра);
        if (square.newFieldX + 1 == squareCent.newFieldX
                && square.newFieldY - 1 == squareCent.newFieldY) {
            square.newFieldX += 2;
            square.newFieldY -= 0;
            square.position.add(+2, 0);
        } else if (square.newFieldX - 1 == squareCent.newFieldX
                && square.newFieldY - 1 == squareCent.newFieldY) {
            square.newFieldX -= 0;
            square.newFieldY -= 2;
            square.position.add(0, -2);
        } else if (square.newFieldX - 1 == squareCent.newFieldX
                && square.newFieldY + 1 == squareCent.newFieldY) {
            square.newFieldX -= 2;
            square.newFieldY += 0;
            square.position.add(-2, 0);
        } else if (square.newFieldX + 1 == squareCent.newFieldX
                && square.newFieldY + 1 == squareCent.newFieldY) {
            square.newFieldX += 0;
            square.newFieldY += 2;
            square.position.add(0, +2);
        }

    }

    static void turnAroundSquareLine(Square square, Square squareCent) {//Поварачивает елемент фигуры на 2 или -2 значение по двум параметрам;
        if (square.newFieldX - 2 == squareCent.newFieldX                //Предназаначен для поворота фигуры "I".
                && square.newFieldY == squareCent.newFieldY) {
            square.newFieldX -= 2;
            square.newFieldY -= 2;
            square.position.add(-2, -2);
        } else if (square.newFieldX == squareCent.newFieldX
                && square.newFieldY + 2 == squareCent.newFieldY) {
            square.newFieldX -= 2;
            square.newFieldY += 2;
            square.position.add(-2, +2);
        } else if (square.newFieldX + 2 == squareCent.newFieldX
                && square.newFieldY == squareCent.newFieldY) {
            square.newFieldX += 2;
            square.newFieldY += 2;
            square.position.add(+2, +2);
        } else if (square.newFieldX == squareCent.newFieldX
                && square.newFieldY - 2 == squareCent.newFieldY) {
            square.newFieldX += 2;
            square.newFieldY -= 2;
            square.position.add(+2, -2);
        }

    }

    //Методы проверок****************************

    static boolean isNearAroundSquare(Square square, Square field[][]) { //Проверяет на возможность поворота на +2 или -2 значание по одному параметру так как другой уже есть проверка
        synchronized (square) {
            if (field[square.newFieldY + 2][square.newFieldX - 0] != null) {
                return true;
            } else if (field[square.newFieldY - 0][square.newFieldX - 2] != null) {
                return true;
            } else if (field[square.newFieldY - 2][square.newFieldX] != null) {
                return true;
            } else if (field[square.newFieldY][square.newFieldX - 2] != null) {
                return true;
            }
            return false;
        }
    }

    static boolean isNearAroundSquareLine(Square square, Square field[][]) {//Проверяет на возможность поворота на +2 или -2 значание (Нужен для фигуры "I")
        synchronized (square) {
            if (field[square.newFieldY - 2][square.newFieldX - 2] != null) {
                return true;
            } else if (field[square.newFieldY - 2][square.newFieldX + 2] != null) {
                return true;
            } else if (field[square.newFieldY + 2][square.newFieldX + 2] != null) {
                return true;
            } else if (field[square.newFieldY + 2][square.newFieldX - 2] != null) {
                return true;
            }

            return false;
        }
    }
    static boolean isNearSquare(Square square, Square field[][]) {//Проверяет на возможность поворота на +1 или -1 значание по двум параметрам
        synchronized (square) //Синхронизируем квадратный елемент фигуры с полем
        {
            if (field[square.newFieldY - 1][square.newFieldX - 1] != null) {
                return true;
            }
            if (field[square.newFieldY - 1][square.newFieldX + 1] != null) {
                return true;
            }
            if (field[square.newFieldY + 1][square.newFieldX + 1] != null) {
                return true;
            }
            if (field[square.newFieldY + 1][square.newFieldX - 1] != null) {
                return true;
            }
            return false; //Если у него есть возможность повернутся, на +1 или -1 значание, поворачиваемся. Если нет, тогда folse;
        }
    }

    //Методы смещений*************************

    static void moveLeftShape(Shape shape, Square field[][]) { //Перемещение фигуры на еденицу влево;
        synchronized (shape) {
            Square square[] = shape.getArray();
            for (Square element : square) {
                if (element.newFieldX == 2) {//Если фигура доходитотметки 2 по горизонтали, передвинуть в лево не получится
                    return;
                } else if (field[element.newFieldY][element.newFieldX - 1] != null) {//Тогда если у нас есть возможность подвингуть на еденицу в лево, двигаем.
                    return;
                }
            }
            shape.update(-1, 0); //обновляем данные;
        }
    }

    static void moveRightShape(Shape shape, Square field[][]) { //Перемещение вправо, то же чтои с влево;
        synchronized (shape) {
            Square square[] = shape.getArray();
            for (Square element : square) {
                if (element.newFieldX == 14) {
                    return;
                } else if (field[element.newFieldY][element.newFieldX + 1] != null) {
                    return;
                }
            }
            shape.update(1, 0);
        }
    }


    //Методы отступов******************

    static void moveIfNearWall(Shape shape, int x) {// Метод делающий отступ от стенок при повороте, если фигура находится в упор к стенке;
        Square square[] = shape.getArray();
        if (square[1].newFieldX == 2) {//Отступ до 2 если в упор к левой;
            shape.update(x, 0);
        }
        if (square[1].newFieldX == 14) {//Отступ до 14 если в упор к правой;
            shape.update(-x, 0);
        }
    }


    static void moveIfNearWallLine(Shape shape) {// Метод делающий отступ от стенок при повороте, если фигура находится в упор к стенке( для фигуры "I");

        Square square[] = shape.getArray();

        if (square[2].newFieldX == 2) {//Отступ до 2 и 3 если в упор к левой;
            shape.update(2, 0);
        }
        if (square[2].newFieldX == 3) {
            shape.update(1, 0);
        }
        if (square[2].newFieldX == 14) {//Отступ до 14 и 13 если в упор к правой;
            shape.update(-2, 0);
        }
        if (square[2].newFieldX == 13) {
            shape.update(-1, 0);
        }
    }


}


