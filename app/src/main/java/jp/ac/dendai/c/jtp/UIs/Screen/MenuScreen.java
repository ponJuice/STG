package jp.ac.dendai.c.jtp.UIs.Screen;

import android.util.Log;
import android.view.MotionEvent;

import java.util.NavigableMap;
import java.util.Objects;

import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.UIs.Transition.LoadingTransition;
import jp.ac.dendai.c.jtp.UIs.Transition.ScrollTransition;
import jp.ac.dendai.c.jtp.UIs.UI.Button;
import jp.ac.dendai.c.jtp.UIs.UI.Listener.ButtonListener;
import jp.ac.dendai.c.jtp.UIs.UI.Text.Text;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

public class MenuScreen implements Screenable {
	public enum TOSCREEN{
		NON,
		GAMESCREEN
	}
	private boolean isFreese = false;
	private TOSCREEN nextScreen = TOSCREEN.NON;
	private Object lock;
	private static MenuScreen instance;
	private final static String[] content = {"START","OPTION","CREDIT"};
	private static Text[] b_content;
	private static Button button,optionButton;
	private long time = 0;

	private MenuScreen(){
		b_content = new Text[content.length];
		for(int n=0;n<content.length;n++){
			b_content[n] = new Text(content[n],255,255,255);
			b_content[n].setHorizontalTextAlign(Text.TextAlign.CENTOR);
			b_content[n].setVerticalTextAlign(Text.TextAlign.CENTOR);
		}
		Log.d("MenuScreen",String.valueOf(GLES20Util.getAspect()));
		button = new Button(GLES20Util.getAspect(), 0.5f, 0.7f, 0.2f, 1f, "START",255,255,0,0);
		button.setX(GLES20Util.getWidth_gl() / 2f);
		button.setListener(new StartButtonListener());
		optionButton = new Button(GLES20Util.getAspect(), 0.5f, 0.7f, 0.2f, 1f, "OPTION",255,255,0,0);
		optionButton.setX(GLES20Util.getWidth_gl()/2f);
		lock = new Object();
	}

	public static MenuScreen getInstance(){
		if(instance == null)
			instance = new MenuScreen();
		return instance;
	}

	@Override
	public void Proc() {
		if(isFreese)
			return;
		if(nextScreen == TOSCREEN.GAMESCREEN)
			toGameScreen();
	}

	@Override
	public void Draw(float offsetX, float offsetY) {
		button.setY((float)(0.01*Math.sin(Math.toRadians(time))) + 1.5f);
		optionButton.setY((float) (0.01 * Math.sin(Math.toRadians(time))) + 1.0f - button.getLengthY());

		for(int n=0;n<content.length;n++){
			if(b_content[n] != null){
				b_content[n].draw(offsetX+GLES20Util.getAspect()+(float)(0.01*Math.sin(Math.toRadians(time))*(content.length-n)),
						offsetY-0.3f*n+1.5f+(float)(0.01*Math.sin(Math.toRadians(time)*n)),
						1f,
						GLES20COMPOSITIONMODE.ALPHA);
			}
		}
		button.draw(offsetX,offsetY);
		optionButton.draw(offsetX,offsetY);
		time++;
	}
	@Override
	public void Touch(MotionEvent event) {
		float tempX = GLES20Util.screenToInnerPosition(event.getX(0), GLES20Util.GLES20UTIL_MODE.POSX);
	    float tempY = GLES20Util.screenToInnerPosition(event.getY(0), GLES20Util.GLES20UTIL_MODE.POSY);
		button.touch(event);
	}

	@Override
	public void death() {

	}

	@Override
	public void freeze() {
		isFreese = true;
	}

	@Override
	public void unFreeze() {
		isFreese = false;
	}

	private void toGameScreen(){
		GameManager.isTransition = true;
		//ScrollTransition.getInstance().setScrollTime(10);
		//ScrollTransition.getInstance().setDirect(GLES20Util.getAspect() * 2f, 0);
		//GameManager.nextScreen = new StageSelectScreen();
		//GameManager.nextScreen = new GameScreen();
		//GameManager.transition = ScrollTransition.getInstance();
		LoadingTransition.getInstance().setNextScreen(new GameScreen());
		LoadingTransition.getInstance().setTransitionTime(10);
		GameManager.transition = LoadingTransition.getInstance();
		nextScreen = TOSCREEN.NON;
	}

	public class StartButtonListener implements ButtonListener {
		@Override
		public void execute(Button button) {
			synchronized (lock) {
				nextScreen = TOSCREEN.GAMESCREEN;
			}
		}

	}

}
