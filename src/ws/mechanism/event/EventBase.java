package ws.mechanism.event;

public abstract class EventBase {
	public final static String VIEW_RESIZED = "ViewResizedEvent";
	public final static String VIEW_REFRESH = "ViewRefresh";
	
	private Object source=null;
	private String eventInstanceType = "";
	private String msg = "";
	
	public EventBase(Object source, String eventType, String msg) {
		this.source=source;
		this.eventInstanceType=eventType;
		this.msg = msg;
	}
	
	public String getType() {
		return eventInstanceType;
	}
	
	public Object getSource() {
		return this.source;
	}
	
	public String getMsg() {
		return this.msg;
	}


}
