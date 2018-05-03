package ws.mechanism.binding;

public interface BindObjectCommon {
	void registerAttribute(String name, WsInt attribute);
	void registerAttribute(String name, WsDouble attribute);
	void registerAttribute(String name, WsString attribute);
	void registerAttribute(String name, WsBoolean attribute);
	
	WsInt getWsInt(String name);
	WsDouble getWsDouble(String name);
	WsString getWsString(String name);
	WsBoolean getWsBoolean(String name);
}
