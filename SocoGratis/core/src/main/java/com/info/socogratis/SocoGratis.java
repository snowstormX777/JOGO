package com.info.socogratis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class SocoGratis extends ApplicationAdapter {
    SpriteBatch batch;
    Texture[] soldier;
    Texture[] orc;
    Texture background;
    Texture gameOver;
    BitmapFont fonte;
    BitmapFont mensagem;
    Rectangle soldierForma;
    ArrayList<Rectangle> orcsFormas;
    Random numeroRandomico;
    int vidajogador;
    int pontuacao;
    float alturaDispositivo, larguraDispositivo;
    float variacao = 0;
    float posicaoInicialVertical;
    float posicaoMovimentoOrcsHorizontal;
    float espacoEntreHorda;
    int estadoJogo = 0;  // 0 - Início, 1 - Jogo, 2 - Game Over
    OrthographicCamera camera;
    Viewport viewport;
    final float LARGURAVIRTUAL = 768;
    final float ALTURAVIRTUAL = 1024;

    @Override
    public void create() {
        numeroRandomico = new Random();
        alturaDispositivo = ALTURAVIRTUAL;
        larguraDispositivo = LARGURAVIRTUAL;
        posicaoInicialVertical = alturaDispositivo / 2;
        posicaoMovimentoOrcsHorizontal = larguraDispositivo - 100;
        espacoEntreHorda = 300;
        pontuacao = 0;

        batch = new SpriteBatch();
        background = new Texture("background.png");
        gameOver = new Texture("game_over.png");

        fonte = new BitmapFont();
        mensagem = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);
        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        camera = new OrthographicCamera();
        camera.position.set(LARGURAVIRTUAL / 2, ALTURAVIRTUAL / 2, 0);
        viewport = new StretchViewport(LARGURAVIRTUAL, ALTURAVIRTUAL, camera);

        soldier = new Texture[1];
        soldier[0] = new Texture("soldier.png");

        orc = new Texture[1];
        orc[0] = new Texture("orc.png");

        orcsFormas = new ArrayList<>();
    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        variacao += Gdx.graphics.getDeltaTime() * 10;

        if (variacao > 2) variacao = 0;

        if (estadoJogo == 0) {

            if (Gdx.input.justTouched()) {
                estadoJogo = 1;
                iniciarJogo();
            }
        } else if (estadoJogo == 1) {

            movimentarJogador();
            movimentarInimigos();
            verificarColisoes();


            pontuacao++;

        } else if (estadoJogo == 2) {

            batch.begin();
            batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2);
            mensagem.draw(batch, "Toque para reiniciar", larguraDispositivo / 2 - 200, alturaDispositivo / 2);
            batch.end();
        }


        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);


        for (Rectangle orcForma : orcsFormas) {
            batch.draw(orc[0], orcForma.x, orcForma.y);
        }


        batch.draw(soldier[0], 100, posicaoInicialVertical);


        fonte.draw(batch, "Pontuação: " + pontuacao, 50, alturaDispositivo - 50);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture t : soldier) {
            t.dispose();
        }
        for (Texture t : orc) {
            t.dispose();
        }
    }

    private void iniciarJogo() {
        vidajogador = 3;
        posicaoInicialVertical = alturaDispositivo / 2;
        orcsFormas.clear();
        estadoJogo = 1;
    }

    private void movimentarJogador() {
        if (Gdx.input.isTouched()) {
            posicaoInicialVertical = Gdx.input.getY();
            if (posicaoInicialVertical > alturaDispositivo) {
                posicaoInicialVertical = alturaDispositivo;
            }
            if (posicaoInicialVertical < 0) {
                posicaoInicialVertical = 0;
            }
        }
    }

    private void movimentarInimigos() {
        for (Rectangle orcForma : orcsFormas) {
            orcForma.x -= 5;
            if (orcForma.x < -orc[0].getWidth()) {
                orcsFormas.remove(orcForma);
                break;
            }
        }

        if (numeroRandomico.nextFloat() < 0.02f) {
            gerarInimigo();
        }
    }

    private void gerarInimigo() {
        float y = numeroRandomico.nextFloat() * alturaDispositivo;
        orcsFormas.add(new Rectangle(larguraDispositivo, y, orc[0].getWidth(), orc[0].getHeight()));
    }

    private void verificarColisoes() {
        for (Rectangle orcForma : orcsFormas) {
            if (orcForma.overlaps(new Rectangle(100, posicaoInicialVertical, soldier[0].getWidth(), soldier[0].getHeight()))) {
                vidajogador--;
                if (vidajogador <= 0) {
                    estadoJogo = 2;
                }
                orcsFormas.remove(orcForma);
                break;
            }
        }
    }
}
