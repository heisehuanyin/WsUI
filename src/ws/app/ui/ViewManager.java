package ws.app.ui;

import java.awt.Color;
import java.util.concurrent.LinkedBlockingQueue;

import ws.app.ui.component.FlowPanel;
import ws.app.ui.component.SplitPanel;
import ws.app.ui.component.WsWindow;
import ws.mechanism.event.EventBase;

public class ViewManager implements Runnable{
	private int windowSum=0;
	private LinkedBlockingQueue<EventBase> eventList = new LinkedBlockingQueue<EventBase>();
	
	
	public void __windowIncrease() {
		++windowSum;
	}
	public void __windowDecrease() {
		--windowSum;
		if(this.windowSum <= 0) {
			System.exit(0);
		}
	}
	
	public void openWindow(WsWindow w) {
		w.__show();
	}
	public void closeWindow(WsWindow w) {
		w.__dispose();
	}

	@Override
	public void run() {
		for(;;) {
			EventBase e=null;
			try {
				e = this.eventList.take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//Resized事件处理，重绘图形
			//Refresh事件处理，重绘图形
			if(e.getType().equals(EventBase.VIEW_RESIZED) 
					|| e.getType().equals(EventBase.VIEW_REFRESH)) {
				View target = (View) e.getSource();
				
				if(target.FRESH.get()) {
					target.FRESH.set(false);
					System.out.println("〓★Process Once==="
							+ "========================================\nnew:" + e.getMsg());
					target.refreshViewModel();
					target.refreshView();
				}
				else {
					System.out.println("old:" + e.getMsg());
				}
				
			}
		}
	}
	public void pushWsEvent(EventBase event) {
		try {
			this.eventList.put(event);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ViewManager app = new ViewManager();
		
		WsWindow appWindow = new WsWindow(app);
		appWindow.title.set("测试Application功能");
		appWindow.resetSetting(Color.black);
		
		//菜单区域
		SplitPanel menubar = new SplitPanel(SplitPanel.LEFT_TO_RIGHT,new Color(0xe4,0xdd,0xdc));
		menubar.autoHeight.set(false);
		menubar.autoWidth.set(true);
		menubar.basicHeight.set(20);
		menubar.leftSpace.set(0);
		menubar.rightSpace.set(0);
		menubar.topSpace.set(0);
		menubar.bottomSpace.set(0);
		appWindow.appendView(menubar);

		//主工作区域
		SplitPanel workSpace = new SplitPanel(SplitPanel.LEFT_TO_RIGHT,Color.GRAY);
		workSpace.autoHeight.set(true);
		workSpace.autoWidth.set(true);
		workSpace.leftSpace.set(0);
		workSpace.rightSpace.set(0);
		workSpace.topSpace.set(1);
		workSpace.bottomSpace.set(1);
		appWindow.appendView(workSpace);
		
		//状态栏
		FlowPanel stateBar = new FlowPanel(FlowPanel.RIGHT_TO_LEFT,Color.lightGray);
		stateBar.autoHeight.set(false);
		stateBar.basicHeight.set(25);
		stateBar.autoWidth.set(true);
		stateBar.rightSpace.set(2);
		appWindow.appendView(stateBar);
		
		for(int i=0;i<5;++i) {
			SplitPanel stateIcon = new SplitPanel(SplitPanel.LEFT_TO_RIGHT, Color.BLACK);
			stateIcon.autoHeight.set(true);
			stateIcon.autoWidth.set(false);
			stateIcon.basicWidth.set(23);
			stateIcon.topSpace.set(1);
			stateIcon.bottomSpace.set(1);
			stateIcon.leftSpace.set(1);
			stateBar.appendView(stateIcon);
		}

		//左窗口
		SplitPanel leftWindow = new SplitPanel(SplitPanel.LEFT_TO_RIGHT,Color.white);
		leftWindow.autoHeight.set(true);
		leftWindow.autoWidth.set(false);
		leftWindow.basicWidth.set(180);
		leftWindow.leftSpace.set(2);
		leftWindow.rightSpace.set(2);
		leftWindow.topSpace.set(2);
		leftWindow.bottomSpace.set(2);
		workSpace.appendView(leftWindow);
		
		//右工作区域
		SplitPanel rightWindow = new SplitPanel(SplitPanel.LEFT_TO_RIGHT,Color.white);
		rightWindow.autoHeight.set(true);
		rightWindow.autoWidth.set(true);
		rightWindow.leftSpace.set(2);
		rightWindow.rightSpace.set(2);
		rightWindow.topSpace.set(2);
		rightWindow.bottomSpace.set(2);
		workSpace.appendView(rightWindow);
		
		app.openWindow(appWindow);
		appWindow.width.set(800);
		appWindow.height.set(600);

		new Thread(app).start();
	}
}
