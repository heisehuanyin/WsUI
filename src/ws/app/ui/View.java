package ws.app.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.util.ArrayList;

import ws.app.ui.component.WsWindow;
import ws.mechanism.binding.WsBoolean;
import ws.mechanism.binding.WsInt;
import ws.mechanism.binding.WsDataBinding;
import ws.mechanism.event.ViewResizedEvent;

public abstract class View {
	private Shape sp = null;
	private WsWindow window = null;
	private Color background = null;
	private Image drawArea = null;
	
	public View(Color background) {
		this.background = background;
	}
	public Image getResultOfPredraw() {
		return this.drawArea;
	}
	
	public Shape getShape() {
		return sp;
	}
	public void setShape(Shape sp) {
		this.sp = sp;
	}
	public void refreshView() {
		this.window.__refreshWindow();
	}
	
	public void initWindow(WsWindow w) {
		this.window = w;
		this.drawArea = this.window.__getOffScreenImg(
				this.visibleWidth.get(),this.visibleHeight.get());
	}
	
	public Color getBackGround() {
		return this.background;
	}
	public void resetBackGround(Color c) {
		this.background = c;
	}
	//更新标志
	public final WsBoolean FRESH = new WsBoolean(true);
	
	//基础尺寸宽度属性、其默认值和合法性检查操作
	public final WsInt basicWidth = new WsInt(1) {
		public void set(int width) {
			if(width > 0) {
				super.set(width);
				View.this.FRESH.set(true);
				if(View.this.window != null) {
					View.this.window.pushWsEvent(new ViewResizedEvent(View.this, View.this.toString() + "设置basicWidth:" + this.get()));
				}
				View.this.__resizeSubWidth();
			}
		}
	};
	//基础尺寸高度属性、其默认值和合法性检查操作
	public final WsInt basicHeight = new WsInt(1) {
		public void set(int height) {
			if(height > 0) {
				super.set(height);
				View.this.FRESH.set(true);
				if(View.this.window != null) {
					View.this.window.pushWsEvent(new ViewResizedEvent(View.this, View.this.toString() + "设置basicHeight:" + this.get()));
				}
				View.this.__resizeSubHeight();
			}
		}
	};
	
	//可视化宽度属性、其默认值和合法性检查操作
	public final WsInt visibleWidth = new WsInt(basicWidth.get()) {
		public void set(int vWidth) {
			if(vWidth >= basicWidth.get()) {
				super.set(vWidth);
				View.this.FRESH.set(true);
				if(View.this.window != null) {
					View.this.window.pushWsEvent(new ViewResizedEvent(View.this, View.this.toString() + "设置visibleWidth:" + this.get()));
				}
				View.this.__resizeSubWidth();
			}
		}
	};
	//可视化高度属性、其默认值和合法性检查操作
	public final WsInt visibleHeight = new WsInt(basicHeight.get()) {
		public void set(int height) {
			if(height >= basicHeight.get()) {
				super.set(height);
				View.this.FRESH.set(true);
				if(View.this.window != null) {
					View.this.window.pushWsEvent(new ViewResizedEvent(View.this, View.this.toString() + "设置visibleHeight:"+ this.get()));
				}
				View.this.__resizeSubHeight();
			}
		}
	};
	public final WsInt OpointX = new WsInt(0) {
		public void set(int i) {
			super.set(i);
			View.this.FRESH.set(true);
			if(View.this.window != null) {
				View.this.window.pushWsEvent(new ViewResizedEvent(View.this, View.this.toString() + "设置OpointX:"+ this.get()));
			}
			View.this.__resizeSubWidth();
		}
	};
	public final WsInt OpointY = new WsInt(0) {
		public void set(int i) {
			super.set(i);
			View.this.FRESH.set(true);
			if(View.this.window != null) {
				View.this.window.pushWsEvent(new ViewResizedEvent(View.this, View.this.toString() + "设置OpointY:"+ this.get()));
			}
			View.this.__resizeSubHeight();
		}
	};
	
	//是否自动宽度适应和默认值
	public final WsBoolean autoWidth = new WsBoolean(true) {
		public void set(boolean b) {
			if(!b) {
				super.set(b);
				WsDataBinding x = new WsDataBinding(WsDataBinding.BINDINGMODE_READONLY);
				x.BindData(View.this.visibleWidth, View.this.basicWidth);
			}
		}
	};
	//是否自动高度适应和默认值
	public final WsBoolean autoHeight = new WsBoolean(true) {
		public void set(boolean b) {
			if(!b) {
				super.set(b);
				WsDataBinding x = new WsDataBinding(WsDataBinding.BINDINGMODE_READONLY);
				x.BindData(View.this.visibleHeight, View.this.basicHeight);
			}
		}
	};
	
	//视图内部子视图容器
	private ArrayList<View> viewContainer = new ArrayList<View>();
	
	//控件外围四周空白空间值属性、默认值和合法校验
	public final WsInt leftSpace = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
			}
		}
	};
	public final WsInt rightSpace = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
			}
		}
	};
	public final WsInt topSpace = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
			}
		}
	};
	public final WsInt bottomSpace = new WsInt(0) {
		public void set(int i) {
			if(i>=0) {
				super.set(i);
			}
		}
	};
	
	
	
	/*添加区域内子视图
	 * View view：指定子视图*/
	public void appendView(View view) {
		this.viewContainer.add(view);
		view.initWindow(this.window);
	}
	/*获取子视图数量*/
	public int getViewCount() { return this.viewContainer.size();}
	/*获取子视图*/
	public View getViewAtIndex(int index){ 
		if(index < this.getViewCount()) {
			return this.viewContainer.get(index);
		}
		else 
			return null;
	}
	
	/*排版操作*/
	//当修改视图尺寸，计算子控件宽度和原点X坐标
	public abstract void __resizeSubWidth();
	//当修改视图高度，计算子控件高度和原点Y坐标
	public abstract void __resizeSubHeight();
	//绘制控件
	public abstract void __refreshViewModel();
}
