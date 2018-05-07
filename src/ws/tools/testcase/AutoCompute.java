package ws.tools.testcase;

import java.awt.Color;

import ws.app.ui.View;
import ws.app.ui.component.SplitPanel;

public class AutoCompute {

	public static void main(String[] args) {
		SplitPanel one = new SplitPanel(SplitPanel.LEFT_TO_RIGHT,Color.black);
		one.autoHeight.set(true);
		one.autoWidth.set(true);
		for(int i = 0;i<4;++i) {
			SplitPanel two = new SplitPanel(SplitPanel.TOP_TO_BOTTOM, Color.yellow);
			two.autoHeight.set(true);
			two.autoWidth.set(true);
			one.appendView(two);
			for(int j=0;j<4;++j) {
				SplitPanel three = new SplitPanel(SplitPanel.LEFT_TO_RIGHT, Color.GRAY);
				three.autoHeight.set(true);
				three.autoWidth.set(true);
				two.appendView(three);
			}
		}
		printChildrenSize("",one);
		//在设置子视图的可视尺寸的时候，自动计算其内部的子视图可视尺寸分配
		one.visibleWidth.set(800);
		one.visibleHeight.set(600);
		
		printChildrenSize("", one);
	}
	
	public static void printChildrenSize(String preStr,View parent) {
		System.out.print(preStr + "组件宽度：" + parent.visibleWidth.get()+"px____");
		System.out.print("组件高度：" + parent.visibleHeight.get()+"px____");
		System.out.println("组件坐标：（" + parent.originX.get() + "px," + parent.originY.get() + "px）");
		
		for(int i=0;i<parent.getViewCount();++i) {
			View one = parent.getViewAtIndex(i);
			if(one == null) return;
			printChildrenSize(preStr + "    " ,one);
		}
	}

}
