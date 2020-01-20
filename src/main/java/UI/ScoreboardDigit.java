package UI;

import constant.Data;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

/**
 * klasa odpowiedzialna za wyswietlanie cyfry w tabeli punktow
 */
public class ScoreboardDigit {
    protected Rectangle texture;

    public Rectangle getTexture() {
        return texture;
    }


    public ScoreboardDigit(int digit) {
        this.texture = new Rectangle(Data.BLOCK_SIZE_PX, Data.BLOCK_SIZE_PX);
        switch(digit){
            case 0:
                loadTexture(Data.NUMBER_ZERO);
                break;
            case 1:
                loadTexture(Data.NUMBER_ONE);
                break;
            case 2:
                loadTexture(Data.NUMBER_TWO);
                break;
            case 3:
                loadTexture(Data.NUMBER_THREE);
                break;
            case 4:
                loadTexture(Data.NUMBER_FOUR);
                break;
            case 5:
                loadTexture(Data.NUMBER_FIVE);
                break;
            case 6:
                loadTexture(Data.NUMBER_SIX);
                break;
            case 7:
                loadTexture(Data.NUMBER_SEVEN);
                break;
            case 8:
                loadTexture(Data.NUMBER_EIGHT);
                break;
            case 9:
                loadTexture(Data.NUMBER_NINE);
                break;


        }
    }
    private void loadTexture(final String number){
        try {
            FileInputStream fileInputStream = new FileInputStream(number);
            Image image = new Image(fileInputStream);
            ImagePattern imagePattern = new ImagePattern(image);
            texture.setFill(imagePattern);
        }
        catch(Exception e ) {
            System.out.println("Error loading Number Texture");
        }
    }
}
