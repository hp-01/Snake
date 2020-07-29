package com.harsh.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.harsh.game.SnakeGame;

public class PlayScreen implements Screen {
    Array<Sprite> snakeTextures;
    
    SnakeGame game;
    Array<Array<Vector2>> positions;
    boolean left,right,up,down;
    public float WIDTH,HEIGHT;
    public OrthographicCamera camera;
    public Viewport viewport;
    public Texture texture;
    public float deltaTimer;
    public Sprite food;
    public PlayScreen(SnakeGame game) {
        this.game = game;

        WIDTH = 600;
        HEIGHT = 600;

        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH,HEIGHT,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        // background image
        texture = new Texture("rectTexture.png");

        // snake sprite
        snakeTextures = new Array<Sprite>();
        for(int i = 0; i <= 4; i++) {
            snakeTextures.add(new Sprite(new Texture("rectTexture.png")));
        }

        for(int i = 0; i <= 4; i++) {
            snakeTextures.get(i).setPosition( 50 - i*10,100);
            snakeTextures.get(i).setSize(10,10);
        }
        snakeTextures.get(0).setBounds(snakeTextures.get(0).getX(), snakeTextures.get(0).getY(), 10, 10);
        // snake block position
        positions = new Array<Array<Vector2>>();

        for(int i = 0; i <= 4; i++) {
            positions.add(new Array<Vector2>(),new Array<Vector2>());
        }

        for(int i = 0; i <= 4; i++) {
            positions.get(i).add(new Vector2(snakeTextures.get(i).getX()-10,snakeTextures.get(i).getY()));
            positions.get(i).add(new Vector2(snakeTextures.get(i).getX(),snakeTextures.get(i).getY()));
        }

        right = true;
        left = false;
        up = true;
        down = true;

        deltaTimer = 3.0f;

        // snake simple food
        food = new Sprite(new Texture("rectTexture.png"));
        food.setSize(10,10);
        food.setPosition(MathUtils.random(viewport.getScreenX(),viewport.getWorldWidth()),
                MathUtils.random(viewport.getScreenY(),viewport.getWorldHeight()));
        System.out.println(MathUtils.random(viewport.getScreenX(),viewport.getWorldWidth())+"    "+MathUtils.random(viewport.getScreenY(),viewport.getWorldHeight()));
        food.setBounds(food.getX(),food.getY(),food.getWidth(),food.getHeight());

    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && right && positions.get(0).get(0).x <= 600 - 30){
            positions.get(0).get(0).x = positions.get(0).get(1).x;
            positions.get(0).get(0).y = positions.get(0).get(1).y;
            positions.get(0).get(1).x += 10;
            positions.get(0).get(1).y += 0;

            snakePositionUpdate();

            right = true;
            left = false;
            up = true;
            down = true;

        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && up && positions.get(0).get(0).y <= 600 - 30){
            positions.get(0).get(0).x = positions.get(0).get(1).x;
            positions.get(0).get(0).y = positions.get(0).get(1).y;
            positions.get(0).get(1).x += 0;
            positions.get(0).get(1).y += 10;

            snakePositionUpdate();

            right = true;
            left = true;
            up = true;
            down = false;


        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && left && positions.get(0).get(0).x >= viewport.getScreenX()+20){
            positions.get(0).get(0).x = positions.get(0).get(1).x;
            positions.get(0).get(0).y = positions.get(0).get(1).y;
            positions.get(0).get(1).x -= 10;
            positions.get(0).get(1).y += 0;

            snakePositionUpdate();

            right = false;
            left = true;
            up = true;
            down = true;


        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && down && positions.get(0).get(0).y >= 20){
            positions.get(0).get(0).x = positions.get(0).get(1).x;
            positions.get(0).get(0).y = positions.get(0).get(1).y;
            positions.get(0).get(1).x += 0;
            positions.get(0).get(1).y -= 10;

            snakePositionUpdate();

            right = true;
            left = true;
            up = false;
            down = true;

        }


    }
    public void update(float dt) {
       handleInput(dt);
       deltaTimer += dt;
        if(deltaTimer >= 3.0f) {
            //game.batch.draw(texture, MathUtils.random(30, viewport.getWorldWidth() - 30), MathUtils.random(20, viewport.getWorldHeight() - 30), 10, 10);
            deltaTimer = 0.0f;
        }

        if(getBounds()){
            System.out.println("True");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        //Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.setColor(0,1,0,1);
        game.batch.draw(texture,0,0,viewport.getWorldHeight(),viewport.getWorldHeight());
        update(delta);
        game.batch.setColor(0,0,0,1);
        // Updating snake position
        for(int i = 0; i <= 4; i++) {
            game.batch.draw(snakeTextures.get(i), positions.get(i).get(1).x, positions.get(i).get(1).y, 10, 10);
            snakeTextures.get(i).setBounds(snakeTextures.get(i).getX(), snakeTextures.get(i).getY(), 10, 10);
        }

        // update simple food
        game.batch.draw(food.getTexture(),food.getX(),food.getY(),food.getWidth(),food.getHeight());
        game.batch.end();
        
    }

    @Override
    public void resize(int width, int height) {
            viewport.update(width,height);
            camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void snakePositionUpdate(){
        for(int i=1;i<=4;i++)
        {
            positions.get(i).get(0).x = positions.get(i).get(1).x;
            positions.get(i).get(0).y = positions.get(i).get(1).y;

            positions.get(i).get(1).x = positions.get(i-1).get(0).x;
            positions.get(i).get(1).y = positions.get(i-1).get(0).y;
        }

    }

    public boolean getBounds(){
        System.out.println(snakeTextures.get(0).getBoundingRectangle().x+"   "+snakeTextures.get(0).getBoundingRectangle().y);
        return true;
    }
}
