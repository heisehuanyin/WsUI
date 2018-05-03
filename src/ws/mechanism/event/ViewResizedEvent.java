package ws.mechanism.event;

import ws.app.ui.View;

public class ViewResizedEvent extends EventBase{
	public ViewResizedEvent(View source, String msg) {
		super(source, EventBase.VIEW_RESIZED, msg);
	}
	
	@Override
	public View getSource() {
		return (View)super.getSource();
	}


}
