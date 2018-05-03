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
		Shape sp = v.getShape();
		if(sp == null) return;
		g.setColor(v.getBackGround());
		System.out.println("))))))))))绘制图形高度尺寸："+ sp.toString());
		g.fill(sp);
		
		for(int i=0;i<v.getViewCount();++i) {
			View x = v.getViewAtIndex(i);
			paintOneByOne(x, g);
		}
	}
	
	@Override
	public void update(Graphics a) {
		
		Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		
		if(this.offScreenImg == null) {
			this.offScreenImg = this.createImage((int)screensize.getWidth(), (int)screensize.getHeight());
		}
		
		Graphics gImage = this.offScreenImg.getGraphics();
		
		gImage.clearRect(0, 0, (int)screensize.getWidth(), (int)screensize.getHeight());
		
		this.paint(gImage);
		a.drawImage(offScreenImg, 0, 0, null);
	}
}

public class WsWindow extends SplitPanel {
	public final static int WSWINDOW_DECORATED = 0;
	public final static int WSWINDOW_UNDECORATED = 1;
	
	private CustomFrame window = new CustomFrame(this);
	private ViewManager app = null;
	
	//暴漏一个title属性，用于设置标题
	public final WsString title = new WsString("") {
		public void set(String title) {
			super.set(title);
			WsWindow.this.window.setTitle(title);
		}
	};
	
	public ViewManager getApp() {
		return this.app;
	}

	//位置属性
	public final WsInt locationX = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
				WsWindow.this.window.setLocation(i, WsWindow.this.locationY.get());
			}
		}
	};
	public final WsInt locationY = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
				WsWindow.this.window.setLocation(WsWindow.this.locationX.get(), i);
			}
		}
	};

	//窗口轮廓属性
	public final WsInt width = new WsInt(80) {
		@Override
		public int get() {
			return WsWindow.this.window.getWidth();
		}
		@Override
		public void set(int i) {
			super.set(i);
			WsWindow.this.visibleWidth.set(i -WsWindow.this.leftSpace.get() -WsWindow.this.rightSpace.get());
			WsWindow.this.window.setSize(i ,WsWindow.this.visibleHeight.get()
					+ WsWindow.this.topSpace.get() + WsWindow.this.bottomSpace.get());
		}
	};
	public final WsInt height= new WsInt(60) {
		@Override
		public int get() {
			return WsWindow.this.window.getHeight();
		}
		public void set(int i) {
			super.set(i);
			WsWindow.this.visibleHeight.set(i -WsWindow.this.topSpace.get() -WsWindow.this.bottomSpace.get());
			WsWindow.this.window.setSize(WsWindow.this.visibleWidth.get()
					+ WsWindow.this.leftSpace.get() + WsWindow.this.rightSpace.get(), i);
		}
	};
	
	public WsWindow(ViewManager app) {
		//调用超类构造器，构建一个初始面板
		super(SplitPanel.TOP_TO_BOTTOM, Color.darkGray);
		//打包，为获取信息做准备
		this.window.pack();
		//可以获取信息了
		this.app = app;
		this.topSpace.set(this.window.getInsets().top);
		this.bottomSpace.set(this.window.getInsets().bottom);
		this.leftSpace.set(this.window.getInsets().left);
		this.rightSpace.set(this.window.getInsets().right);
		this.initWindow(this);
		this.OpointX.set(this.leftSpace.get());
		this.OpointY.set(this.topSpace.get());
		
		this.window.addWindowListener(new WindowListener() {
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
					WsWindow.this.window.dispose();
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
		
		this.window.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				WsWindow.this.visibleWidth.set(WsWindow.this.window.getWidth() - 
						WsWindow.this.leftSpace.get() - WsWindow.this.rightSpace.get());
				WsWindow.this.visibleHeight.set(WsWindow.this.window.getHeight()
						-WsWindow.this.topSpace.get() -WsWindow.this.bottomSpace.get());
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				WsWindow.this.__refreshViewModel();
			}
			//以下方法暂时不处理
			@Override
			public void componentHidden(ComponentEvent arg0) {}
			@Override
			public void componentShown(ComponentEvent arg0) {}
		});
		
	}
	public void __show() {
		this.window.setVisible(true);
	}
	public void __dispose() {
		this.window.dispose();
	}
	
	public Frame __getAwtFrame() {
		return this.window;
	}

	public static void main(String[] args) {
		WsWindow win = new WsWindow(new ViewManager());
		win.title.set("重新设置标题");
		win.width.set(800);
		win.height.set(600);
		win.__show();
		win.__refreshViewModel();
	}


}
