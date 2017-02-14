package com.timagreat.stonetetris;

import com.badlogic.androidgames.framework.gl.BTMPFont;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
    static	Texture atlas;
    static Texture sd_0;
    static Texture startScreen;
    static Texture result;

    static BTMPFont font;
    static Texture backgroundGameOver;
    static TextureRegion startScreenRegion;
    static TextureRegion resultScreenRegion;
    static TextureRegion backgroundGameOverRegion;
    static	TextureRegion background;
    static TextureRegion red;
    static TextureRegion green;
    static TextureRegion blue;
    static TextureRegion blueLight;
    static TextureRegion gameOver;
    static TextureRegion exit;

    public static void load(GLGame game){
        atlas = new Texture(game,"atlas.png");
        backgroundGameOver = new Texture(game,"gameover.png");
        sd_0 = new Texture(game,"sd_0.png");
        startScreen = new Texture(game,"startScreen2.png");
        result = new Texture(game,"result.png");

        /*
            All sizes of resources such as bitmap(texture) yoy should get from resource itself or
            hardcode it in dimens.xml
         */
        startScreenRegion = new TextureRegion(startScreen,0,0,320,480); // why not startScreen.width, startScreen.height ?
        resultScreenRegion = new TextureRegion(result,0,0,320,480);
        backgroundGameOverRegion = new TextureRegion(backgroundGameOver,0,0,320,480);
        background = new TextureRegion(atlas,0,0,320,480);
        red = new TextureRegion(atlas,340,0,20,20);//(Red)
        green = new TextureRegion(atlas,380,0,20,20);//(Orange)
        blue = new TextureRegion(atlas,420,0,20,20);//(Blue)
        blueLight = new TextureRegion(atlas,460,0,20,20);//(Fiolet)
        gameOver = new TextureRegion(atlas,330,41,172,60);
        exit = new TextureRegion(atlas,324,41,180,140);//(In Game)
        font = new BTMPFont(game,"sd.fnt",sd_0);//растовый шрифт

    }
    public static void reload(){
        atlas.reload();
        backgroundGameOver.reload();
        sd_0.reload();
        startScreen.reload();
        result.reload();
    }


}

