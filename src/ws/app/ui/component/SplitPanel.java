package ws.app.ui.component;

import java.awt.Color;
import java.awt.Rectangle;
import ws.app.ui.View;

public class SplitPanel extends View {
	public final static int LEFT_TO_RIGHT = 0;
	public final static int TOP_TO_BOTTOM = 1;
	private int oriented;
	
	public SplitPanel(int oriented, Color c) {
		super(c);
		this.oriented = oriented;
	}

	@Override
	public void __resizeSubWidth() {
		int viewWidth = this.visibleWidth.get();
		
		switch(this.oriented){
		case SplitPanel.LEFT_TO_RIGHT:{//自左至右排布
			int basicWidthCount = 0;
			int adjustableWidthCount = 0;
			for(int i=0;i<this.getViewCount();++i) {
				View one = this.getViewAtIndex(i);
				basicWidthCount += one.basicWidth.get() + one.leftSpace.get() + one.rightSpace.get();
				if(one.autoWidth.get()) {
					adjustableWidthCount += one.basicWidth.get();
				}
			}
			//视图是否溢出
			if(basicWidthCount > viewWidth) {
				//TODO 处理视图溢出事件
				System.out.println("SplitPanel：待处理溢出事件机制-Width");
			}
			else {
				//分配空闲空间
				int emptyWidth = viewWidth - basicWidthCount;
				int opointOffset = 0;//相对视图偏移量
				
				for(int i=0;i<this.getViewCount();++i) {
					View rone = this.getViewAtIndex(i);
					
					int width = (!rone.autoWidth.get())? rone.visibleWidth.get():
							rone.basicWidth.get() + emptyWidth/adjustableWidthCount*(rone.basicWidth.get());

					opointOffset += rone.leftSpace.get();
					rone.originX.set(this.originX.get() + opointOffset);
					opointOffset += width + rone.rightSpace.get();
					
					if(rone.autoWidth.get()) {
						rone.visibleWidth.set(width);
					}
				}
			}
		}break;
		default:{//自上至下排布
			for(int i=0;i<this.getViewCount();++i) {
				View one = this.getViewAtIndex(i);
				if(one.autoWidth.get()) {
					one.visibleWidth.set(viewWidth - one.leftSpace.get() - one.rightSpace.get());
				}
				one.originX.set(this.originX.get() + one.leftSpace.get());
			}
		}break;
		}
	}

	@Override
	public void __resizeSubHeight() {
		int viewHeight = this.visibleHeight.get();
		
		switch(this.oriented){
		case SplitPanel.LEFT_TO_RIGHT:{//自左至右排布
			for(int i=0;i<this.getViewCount();++i) {
				View one = this.getViewAtIndex(i);
				if(one.autoHeight.get()) {
					one.visibleHeight.set(viewHeight - one.topSpace.get() - one.bottomSpace.get());
				}
				one.originY.set(this.originY.get() + one.topSpace.get());
			}
		}break;
		default:{//自上至下排布
			int basicHeightSum = 0;
			int adjustableHeightSum = 0;
			for(int i=0;i<this.getViewCount();++i) {
				View one = this.getViewAtIndex(i);
				basicHeightSum += one.basicHeight.get() + one.topSpace.get() + one.bottomSpace.get();
				if(one.autoHeight.get()) {
					adjustableHeightSum += one.basicHeight.get();
				}
			}
			if(basicHeightSum > viewHeight) {
				//TODO 处理视图溢出事件
				System.out.println("SplitPanel：待处理溢出事件机制-Height");
			}
			else {
				int emptyHeight = viewHeight - basicHeightSum;
				int opointOffset = 0;//相对偏移量
				
				for(int i=0;i<this.getViewCount();++i) {
					View rone = this.getViewAtIndex(i);
					
					int height = (!rone.autoHeight.get())? rone.visibleHeight.get():
						rone.basicHeight.get() + emptyHeight/adjustableHeightSum*(rone.basicHeight.get());

					opointOffset += rone.topSpace.get();
					rone.originY.set(this.originY.get() + opointOffset);
					opointOffset += height + rone.bottomSpace.get();
					
					if(rone.autoHeight.get()) {
						rone.visibleHeight.set(height);
					}
				}
			}
		}break;
		}
	}

	@Override
	public void refreshViewModel() {
		if(this.getShape() == null) {
			this.setShape(new Rectangle(
					this.originX.get(),
					this.originY.get(), 
					this.visibleWidth.get(),
					this.visibleHeight.get()));
		}
		((Rectangle)this.getShape()).setLocation(this.originX.get(), this.originY.get());
		((Rectangle)this.getShape()).setSize(this.visibleWidth.get(), this.visibleHeight.get());
	}

}
