package org.b3log.tank.html;

import org.b3log.tank.core.GdxTank;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GdxTankHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new GdxTank();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
