import constant.Data;
import constant.LongValue;
import core.Board;
import UI.LifePane;
import UI.ScoreBoard;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.FileInputStream;


/**
 * Klasa glowna aplikacji
 * Posiada korzenie: mainMenuRootPane - korzen glownego menu
 *                   mazeRootPane - korzen wlasciwej gry
 * Posiada Odpowiedzialne za layout aplikacji boxy:
 *                   gameButtonMenu - layout pionowy na przycisk exit, punkty, oraz zycia
 *                   boardPane - layout na plansze
 *                   scoreBoard - layout na punkty
 *                   lifePane - layout na zycia
 * scene - scena naszej aplikacji
 * animator - petla glowna naszej aplikajci
 *
 * trzy buttony exitToMainmenubutton, startButton i exitGameButton
 *
 * trzy EventHandlery odpowiedzialne za obsluge zdarzen buttony
 *
 */
public class Game extends Application {

    private VBox mainMenuRootPane;
    private HBox mazeRootPane;
    private VBox gameButtonMenu;
    private Board boardPane;
    private ScoreBoard scoreBoard;
    private LifePane lifePane;
    private Scene scene;
    private AnimationTimer animator;

    private Rectangle exitToMainMenuButton;
    private Rectangle startButton;
    private Rectangle exitGameButton;

    private EventHandler<MouseEvent> exitToMainMenuButtonMouseEventEventHandler;
    private EventHandler<MouseEvent> exitGameButtonMouseEventEventHandler;
    private EventHandler<MouseEvent> startGameButtonMouseEventEventHandler;


    /**
     *metoda inicializujaca/ladujaca wszystkie potrzebne obiekty
     * metoda inicializuje korzenie, obiekty odpowiedzialne za layout, petle glowna, eventhandlery, przyciski
     * a nastepnie dodaje wszystkie elementy do odpowiednich rodzicow
     */
    @Override
    public void init() {
        initRoots();
        initAnimator();
        initEventHandlers();
        initButtons();
        addAllChildrensToAll();
    }


    /**
     * @param stage
     * metoda odpowiedzialna za uruchomienie aplikacji
     * metoda wlacza soundtrack, ustawia scene, ustawia fullscreen, i pokazuje nasza scene
     */
    @Override
    public void start(Stage stage) {
        try {
            boardPane.soundPlayer.playMainMenuSoundtrack();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * metoda iniciujaca petle glowna programu
     * renderuje plansze, punkty, oraz zycia
     */
    private void initAnimator() {
        animator = new AnimationTimer() {
            LongValue lastNanoTime = new LongValue(System.nanoTime());

            @Override
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / Data.TIME_CONSTANT;
                lastNanoTime.setValue(currentNanoTime);
                boardPane.renderBoard(elapsedTime);
                ScoreBoard.render();
                LifePane.render();
                boardPane.setPaused(false);
            }
        };
    }


    /**
     * @param rootPane layout ktoremu chcemy nadac background
     * @param backgroundTexture sciezka do pliku z backgroundem
     */
    private void loadBackGround(Pane rootPane, String backgroundTexture) {
        try {
            FileInputStream fileInputStream1 = new FileInputStream(backgroundTexture);
            Image image1 = new Image(fileInputStream1);
            BackgroundSize backgroundSize = new BackgroundSize(Data.DEFAULT_SCREEN_RES_WIDTH, Data.DEFAULT_SCREEN_RES_HEIGHT, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            rootPane.setBackground(background);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    /**
     * @param rootPane layout ktoremu chcemy nadac background
     * @param backgroundTexture sciezka do pliku z backgroundem
     * @param width dlugosc backgroundu
     * @param height szerokosc backgroundu
     */
    private void loadBackGround(Pane rootPane, String backgroundTexture, int width, int height) {
        try {
            FileInputStream fileInputStream1 = new FileInputStream(backgroundTexture);
            Image image1 = new Image(fileInputStream1);
            BackgroundSize backgroundSize = new BackgroundSize(width, height, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            rootPane.setBackground(background);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    /**
     * @param button przycisk ktoremu chcemy nadac teksture
     * @param ButtonTextureFilePath sciezka do tekstury
     */
    private void loadButtonTexture(Rectangle button, String ButtonTextureFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(ButtonTextureFilePath);
            Image image = new Image(fileInputStream);
            ImagePattern imagePattern = new ImagePattern(image);
            button.setFill(imagePattern);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    /**
     * metoda ktora dodaje przyciski, oraz podlayouty do layoutow
     */
    public void addAllChildrensToAll() {
        mainMenuRootPane.getChildren().addAll(startButton, exitGameButton);
        mazeRootPane.getChildren().addAll(boardPane.getRoot(), gameButtonMenu);
        gameButtonMenu.getChildren().addAll(scoreBoard.getScoreBoardTable(), lifePane.getLifeTable(), exitToMainMenuButton);

    }

    /**
     * metoda inicializujaca przyciski
     * metoda najpierw tworzy nowe obiekty typu rectangle
     * pozniej zaladowuje ich tekstury
     * nastepnie dodaje obsluge zdarzen
     */
    public void initButtons() {

        startButton = new Rectangle(Data.DEFAULT_BUTTON_WIDTH, Data.DEFAULT_BUTTON_HEIGHT);
        exitGameButton = new Rectangle(Data.DEFAULT_BUTTON_WIDTH, Data.DEFAULT_BUTTON_HEIGHT);
        exitToMainMenuButton = new Rectangle(Data.DEFAULT_BUTTON_WIDTH, Data.DEFAULT_BUTTON_HEIGHT);

        loadButtonTexture(startButton, Data.PLAY_BUTTON_IMAGE);
        loadButtonTexture(exitToMainMenuButton, Data.EXIT_TO_MAIN_MENU_BUTTON);
        loadButtonTexture(exitGameButton, Data.EXIT_BUTTON_IMAGE);

        exitToMainMenuButton.setTranslateY(800);

        exitToMainMenuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, exitToMainMenuButtonMouseEventEventHandler);
        exitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, exitGameButtonMouseEventEventHandler);
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, startGameButtonMouseEventEventHandler);
    }


    /**
     * @param args
     * metoda glowna aplikacji
     */
    public static void main(String[] args) {

        Application.launch(args);
    }

    /**
     * metoda ustawiajaca obsluge zdarzen na przyciskach
     */
    private void initEventHandlers() {
        exitToMainMenuButtonMouseEventEventHandler = e -> {
            Board.soundPlayer.playButtonSound();
            Board.soundPlayer.playMainMenuSoundtrack();
            animator.stop();
            scene.setRoot(mainMenuRootPane);
            boardPane.setPaused(true);


        };
        exitGameButtonMouseEventEventHandler = e -> {
            Board.soundPlayer.playButtonSound();
            Board.interruptAllThreads();
            Board.cleanUp();
            Platform.exit();
        };
        startGameButtonMouseEventEventHandler = e -> {
            Board.soundPlayer.playButtonSound();
            Board.soundPlayer.playMazeSoundtrack();
            scene.setRoot(mazeRootPane);
            boardPane.getRoot().setFocusTraversable(true);
            boardPane.reset();
            animator.start();
        };
    }


    /**
     * metoda inicializujaca wszystkie layouty, lacznie z korzeniami
     */
    private void initRoots() {
        mainMenuRootPane = new VBox();
        mazeRootPane = new HBox();
        gameButtonMenu = new VBox();
        boardPane = new Board();
        scoreBoard = new ScoreBoard();
        lifePane = new LifePane(boardPane);
        scene = new Scene(mainMenuRootPane, 800, 800);

        gameButtonMenu.setMaxSize(800, 1040);


        loadBackGround(mainMenuRootPane, Data.START_BACKGROUND_IMAGE);
        loadBackGround(mazeRootPane, Data.GAME_BACKGROUND_IMAGE);
        loadBackGround(gameButtonMenu, Data.GAME_BUTTON_BACKGROUND, 400, 950);


        mainMenuRootPane.setAlignment(Pos.CENTER);

        mainMenuRootPane.setSpacing(10);
        gameButtonMenu.setSpacing(10);


        boardPane.getRoot().setFocusTraversable(true);
    }

}


