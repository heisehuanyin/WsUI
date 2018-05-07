package ws.app.ui;

import java.awt.Graphics2D;
import java.awt.Image;

import ws.mechanism.event.ViewRefreshEvent;

public class ViewRefreshTask implements Runnable {
	private View v=null;
	
	public ViewRefreshTask(View v) {
		this.v = v;
	}

	@Override
	public void run() {
		Image x = v.getImage();
		Graphics2D g = (Graphics2D) x.getGraphics();
		g.clearRect(0, 0, v.visibleWidth.get(), v.visibleHeight.get());
		v.__paintItSelf(g);
		v.drawArea = x;
		v.FRESH.set(true);
		v.pushEvent(new ViewRefreshEvent(v,"View Refreshed"));
	}

}
