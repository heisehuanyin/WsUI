package ws.app.ui.component;

import java.awt.Color;
import java.awt.Rectangle;

import ws.app.ui.View;

public class FlowPanel extends View{
	public final static int LEFT_TO_RIGHT = 0;
	public final static int RIGHT_TO_LEFT = 1;
	public final static int TOP_TO_BOTTOM = 2;
	
	private int orientend=0;

	public FlowPanel(int orientend, Color background) {
		super(background);
		this.orientend = orientend;
	}

	@Override
	public void __resizeSubWidth() {
		int oPointOffset = 0;
		
		switch(this.orientend) {
		case FlowPanel.LEFT_TO_RIGHT:{
			//布局方向上的所有容器宽度==基本尺寸宽度
			for(int i=0;i<this.getViewCount();++i) {
				View child = this.getViewAtIndex(i);
				child.visibleWidth.set(child.basicWidth.get());
				oPointOffset += child.leftSpace.get();
				child.OpointX.set(this.OpointX.get() + oPointOffset);
				oPointOffset += child.visibleWidth.get() + child.rightSpace.get();
			}
		}break;
		case FlowPanel.RIGHT_TO_LEFT:{
			oPointOffset = this.visibleWidth.get();
			
			for(int i=0;i<this.getViewCount();++i) {
				View child = this.getViewAtIndex(i);
				child.visibleWidth.set(child.basicWidth.get());
				
				oPointOffset -= (child.rightSpace.get() + child.visibleWidth.get());
				child.OpointX.set(this.OpointX.get() + oPointOffset);
				oPointOffset -= child.leftSpace.get();
				System.out.println("------------------------------------------->right to left:child OX:" + child.OpointX.get());
			}
		}break;
		default:{
			//有选择的伸缩垂直方向
			for(int i=0;i<this.getViewCount();++i) {
				View child = this.getViewAtIndex(i);
				if(child.autoWidth.get()) {
					child.visibleWidth.set(this.visibleWidth.get() - child.leftSpace.get() - child.rightSpace.get());
				}//设置了autoWidth == false 的视图他的visibleWidth《==basicWidth，不需要调整；
				child.OpointX.set(this.OpointX.get() + child.leftSpace.get());
			}
		}break;
		}
		
	}

	@Override
	public void __resizeSubHeight() {
		int oPointOffset = 0;
		
		switch(this.orientend) {
		case FlowPanel.TOP_TO_BOTTOM:{
			for(int i=0;i<this.getViewCount();++i) {
				View child = this.getViewAtIndex(i);
				child.visibleHeight.set(child.basicHeight.get());
				
				oPointOffset += child.topSpace.get();
				child.OpointY.set(this.OpointY.get() + oPointOffset);
				oPointOffset += child.visibleHeight.get() + child.bottomSpace.get();
			}
		}break;
		default:{
			for(int i=0;i<this.getViewCount();++i) {
				View child = this.getViewAtIndex(i);
				if(child.autoHeight.get()) {
					child.visibleHeight.set(this.visibleHeight.get() - child.topSpace.get() - child.bottomSpace.get());
				}
				child.OpointY.set(this.OpointY.get() +child.topSpace.get());
			}
		}break;
		}
	}

	@Override
	public void __refreshViewModel() {
		if(this.getShape() == null) {
			this.setShape(new Rectangle(
					this.OpointX.get(),
					this.OpointY.get(), 
					this.visibleWidth.get(),
					this.visibleHeight.get()));
		}
		((Rectangle)this.getShape()).setLocation(this.OpointX.get(), this.OpointY.get());
		((Rectangle)this.getShape()).setSize(this.visibleWidth.get(), this.visibleHeight.get());
	}

}
