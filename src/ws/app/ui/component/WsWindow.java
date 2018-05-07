package ws.app.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import ws.app.ui.ViewManager;
import ws.app.ui.View;
import ws.mechanism.binding.WsInt;
import ws.mechanism.binding.WsString;
import ws.mechanism.event.EventBase;
import ws.mechanism.event.ViewRefreshEvent;

class CustomFrame extends Frame{
	private Image offScreenImg;
	private WsWindow w = null;
	
	public CustomFrame(WsWindow w) {
		this.w = w;
	}
	public void paint(Graphics a) {
		this.paintOneByOne(w, (Graphics2D)a);
	}
	private void paintOneByOne(View v, Graphics2D g) {
		Image x = v.getPredrawArea();
		g.drawImage(x, v.originX.get(), v.originY.get(), this);
		
		
		for(int i=0;i<v.getViewCount();++i) {
			View x2 = v.getViewAtIndex(i);
			paintOneByOne(x2, g);
		}
	}
	
	@Override
	public void update(Graphics a) {
		
		if(this.offScreenImg == null) {
			this.offScreenImg = this.createImage(this.getWidth(), this.getHeight());
		}
		
		Graphics gImage = this.offScreenImg.getGraphics();
		
		gImage.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		this.paint(gImage);
		a.drawImage(offScreenImg, 0, 0, this);
	}
}

public class WsWindow extends SplitPanel {
	public final static int WSWINDOW_DECORATED = 0;
	public final static int WSWINDOW_UNDECORATED = 1;
	
	private CustomFrame trueWindow = new CustomFrame(this);
	private ViewManager app = null;
	
	//暴漏一个title属性，用于设置标题
	public final WsString title = new WsString("") {
		public void set(String title) {
			super.set(title);
			WsWindow.this.trueWindow.setTitle(title);
		}
	};
	
	//推送事件
	public void pushWsEvent(EventBase e) {
		this.app.pushWsEvent(e);
	}

	//位置属性
	public final WsInt locationX = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
				WsWindow.this.trueWindow.setLocation(i, WsWindow.this.locationY.get());
			}
		}
	};
	public final WsInt locationY = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
				WsWindow.this.trueWindow.setLocation(WsWindow.this.locationX.get(), i);
			}
		}
	};

	//窗口轮廓属性
	public final WsInt width = new WsInt(80) {
		@Override
		public int get() {
			return WsWindow.this.trueWindow.getWidth();
		}
		@Override
		public void set(int i) {
			super.set(i);
			WsWindow.this.visibleWidth.set(i -WsWindow.this.leftSpace.get() -WsWindow.this.rightSpace.get());
			WsWindow.this.trueWindow.setSize(i ,WsWindow.this.visibleHeight.get()
					+ WsWindow.this.topSpace.get() + WsWindow.this.bottomSpace.get());
		}
	};
	public final WsInt height= new WsInt(60) {
		@Override
		public int get() {
			return WsWindow.this.trueWindow.getHeight();
		}
		public void set(int i) {
			super.set(i);
			WsWindow.this.visibleHeight.set(i -WsWindow.this.topSpace.get() -WsWindow.this.bottomSpace.get());
			WsWindow.this.trueWindow.setSize(WsWindow.this.visibleWidth.get()
					+ WsWindow.this.leftSpace.get() + WsWindow.this.rightSpace.get(), i);
		}
	};
	
	public WsWindow(ViewManager app) {
		//调用超类构造器，构建一个初始面板
		super(SplitPanel.TOP_TO_BOTTOM, Color.darkGray);
		//打包，为获取信息做准备
		this.trueWindow.pack();
		//可以获取信息了
		this.app = app;
		this.topSpace.set(this.trueWindow.getInsets().top);
		this.bottomSpace.set(this.trueWindow.getInsets().bottom);
		this.leftSpace.set(this.trueWindow.getInsets().left);
		this.rightSpace.set(this.trueWindow.getInsets().right);
		this.initWindow(this);
		this.originX.set(this.leftSpace.get());
		this.originY.set(this.topSpace.get());
		
		this.trueWindow.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				if(WsWindow.this.app!=null)
					WsWindow.this.app.__windowIncrease();
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				if(WsWindow.this.app!=null)
					WsWindow.this.app.__windowDecrease();
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				if(WsWindow.this.app!=null)
					WsWindow.this.trueWindow.dispose();
			}
			//一下方法暂时不涉及
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
		});
		
		this.trueWindow.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				WsWindow.this.visibleWidth.set(WsWindow.this.trueWindow.getWidth() - 
						WsWindow.this.leftSpace.get() - WsWindow.this.rightSpace.get());
				WsWindow.this.visibleHeight.set(WsWindow.this.trueWindow.getHeight()
						-WsWindow.this.topSpace.get() -WsWindow.this.bottomSpace.get());
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				WsWindow.this.FRESH.set(true);
				WsWindow.this.pushWsEvent(new ViewRefreshEvent(WsWindow.this,"Window Moved!"));
			}
			//以下方法暂时不处理
			@Override
			public void componentHidden(ComponentEvent arg0) {}
			@Override
			public void componentShown(ComponentEvent arg0) {}
		});
		
	}
	public void __show() {
		this.trueWindow.setVisible(true);
	}
	public void __dispose() {
		this.trueWindow.dispose();
	}
	public Image __getOffScreenImg(int width, int height) {
		return this.trueWindow.createImage(width, height);
	}
	public void __refreshWindow() {
		this.trueWindow.repaint();
	}

	public static void main(String[] args) {
		WsWindow win = new WsWindow(new ViewManager());
		win.title.set("重新设置标题");
		win.width.set(800);
		win.height.set(600);
		win.__show();
		win.refreshViewModel();
	}

}
