package com.harsh.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Snake {
    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    private Vector2 position;
    private Vector2 velocity;
    private static int SPEED = 1;
    private Texture texture;
    public Snake(float x, float y) {
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        texture = new Texture("rectTexture.png");
    }

    public void update(float dt){
        velocity.set(SPEED,0);
        velocity.scl(dt);
            position.scl(velocity.x, position.y);
        velocity.scl(1/dt);
    }
}
