package com.mygdx.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import static com.badlogic.gdx.Input.Keys.R;


public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;  // draw anything on the screen  or putting something on the screen
	Texture background;  // texture is use to add visuals / images in the app
	Texture[] girl; // character images
	int girlState = 0; // for looping
	//int pause = 0;  // for speed
	float gravity = 0.2f;  // how quickly our character fall on the ground
	float velocity = 0;
	int girlY = 0;  // character y position
	Rectangle loliRectangle;
	BitmapFont font;
	Texture dead;
	Texture over;
	Texture start;
	Texture name;
	int score = 0;
	int gameState = 0;

	Random random;

	ArrayList<Integer> coinXs = new ArrayList<Integer>();
	ArrayList<Integer> coinYs = new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangles =  new ArrayList<Rectangle>();
	Texture coin;
	int coinCount;

	ArrayList<Integer> bombXs = new ArrayList<Integer>();
	ArrayList<Integer> bombYs = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangles =  new ArrayList<Rectangle>();
	Texture bomb;
	int bombCount;

	public static AssetManager manager;


	@Override
	public void create () {   // opening game
		batch = new SpriteBatch();
		background = new Texture("bg.png"); // add background
		girl = new Texture[20];
		girl[0] = new Texture("run10.png");
		girl[1] = new Texture("run11.png");
		girl[2] = new Texture("run12.png");
		girl[3] = new Texture("run13.png");
		girl[4] = new Texture("run14.png");
		girl[5] = new Texture("run15.png");
		girl[6] = new Texture("run16.png");
		girl[7] = new Texture("run17.png");
		girl[8] = new Texture("run18.png");
		girl[9] = new Texture("run19.png");
		girl[10] = new Texture("run20.png");
		girl[11] = new Texture("run21.png");
		girl[12] = new Texture("run22.png");
		girl[13] = new Texture("run23.png");
		girl[14] = new Texture("run24.png");
		girl[15] = new Texture("run25.png");
		girl[16] = new Texture("run26.png");
		girl[17] = new Texture("run27.png");
		girl[18] = new Texture("run28.png");
		girl[19] = new Texture("run29.png");
		girlY = Gdx.graphics.getHeight() / 2;  // center to the screen

		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		random = new Random();
		dead = new Texture("dead1.png");
		over = new Texture("gameover.png");
		start = new Texture("start.png");
		name = new Texture("game_name.png");


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);


	}

	public void makeCoin() {
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	public void makeBomb() {
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}



	@Override
	public void render () {  //  run until you finish your game
		batch.begin(); // starting rendering from here
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); // starting position and height,weight


		if (gameState == 1) {
			// GAME IS LIVE
			// BOMB
			if (bombCount < 250) {
				bombCount++;
			} else {
				bombCount = 0;
				makeBomb();
			}

			bombRectangles.clear();
			for (int i=0;i < bombXs.size();i++) {
				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
				bombXs.set(i, bombXs.get(i) - 8);
				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
			}

			// COINS
			if (coinCount < 100) {
				coinCount++;
			} else {
				coinCount = 0;
				makeCoin();
			}

			coinRectangles.clear();
			for (int i=0;i < coinXs.size();i++) {
				batch.draw(coin, coinXs.get(i), coinYs.get(i));
				coinXs.set(i, coinXs.get(i) - 4);
				coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));
			}

			if (Gdx.input.justTouched()) {  // for jumping
				velocity = -10;  // how tall character jump
			}

		//	if (pause < 8) {          // for speed
		//		pause++;
		//	} else {
		//		pause = 0;
				if (girlState < 19) {           // for looping
					girlState++;
				} else {
					girlState = 0;
				}
			//}

			velocity += gravity; // adding velocity e.g first it is 0.2 than its 0.4 and so on
			girlY -= velocity;  // character falling according  to that

			if (girlY <= 0) {   // putting condition so character did not fall
				girlY = 0;      // stay on the ground
			}
		}
		else if (gameState == 0) {
			batch.draw(start,Gdx.graphics.getWidth() / 16,Gdx.graphics.getWidth() / 5);
			batch.draw(name,Gdx.graphics.getWidth() / 16,Gdx.graphics.getWidth() );

			// Waiting to start
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}
		else if (gameState == 2) {
			// GAME OVER
			if (Gdx.input.justTouched()) {
				gameState = 1;
				girlY = Gdx.graphics.getHeight() / 2;
				score = 0;
				velocity = 0;
				coinXs.clear();
				coinYs.clear();
				coinRectangles.clear();
				coinCount = 0;
				bombXs.clear();
				bombYs.clear();
				bombRectangles.clear();
				bombCount = 0;
			}
		}

		if (gameState == 2) {
			batch.draw(dead, Gdx.graphics.getWidth() / 2 - girl[girlState].getWidth() / 2, girlY);
			batch.draw(over,Gdx.graphics.getWidth() / 16,Gdx.graphics.getWidth() / 4);
		} else {
			batch.draw(girl[girlState], Gdx.graphics.getWidth() / 2 - girl[girlState].getWidth() / 2, girlY);
		}
		loliRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - girl[girlState].getWidth() / 2, girlY, girl[girlState].getWidth(), girl[girlState].getHeight());




		for (int i=0; i < coinRectangles.size();i++) {
			if (Intersector.overlaps(loliRectangle, coinRectangles.get(i))) {
				score++;

				coinRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;
			}
		}

		for (int i=0; i < bombRectangles.size();i++) {
			if (Intersector.overlaps(loliRectangle, bombRectangles.get(i))) {
				Gdx.app.log("Bomb!", "Collision!");
				gameState = 2;
			}
		}

		font.draw(batch, String.valueOf(score),100,200);

		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
