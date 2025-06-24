package com.info.socogratis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Rectangle;
import java.util.Random;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SocoGratis extends ApplicationAdapter {
    SpriteBatch batch;
    Texture[] soldier;
    Texture[] orc;
    Texture background;
    Texture gameOver;
    BitmapFont fonte;
    BitmapFont mensagem;
    Rectangle soldierForma;
    Rectangle orcForma;



    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
