package ws.mechanism.event;

import ws.app.ui.View;

public class ViewRefreshEvent extends EventBase{
	public ViewRefreshEvent(View source, String msg) {
		super(source, EventBase.VIEW_REFRESH, msg);
	}
	
	@Override
	public View getSource() {
		return (View)super.getSource();
	}

}
