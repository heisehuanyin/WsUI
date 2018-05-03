package ws.mechanism.binding;

import java.util.HashMap;
import java.util.Map;

public class DefaultBuildObject extends WsList<DefaultBuildObject> implements BindObjectCommon{
	private Map<String,WsInt> attribute = new HashMap<>();
	private Map<String,WsDouble> attribute2 = new HashMap<>();
	private Map<String,WsString> attribute3 = new HashMap<>();
	private Map<String,WsBoolean> attribute4 = new HashMap<>();
	
	public void registerAttribute(String name, WsInt attribute) {
		this.attribute.put(name, attribute);
	}
	public void registerAttribute(String name, WsDouble attribute) {
		this.attribute2.put(name,attribute);
	}
	public void registerAttribute(String name, WsString attribute) {
		this.attribute3.put(name, attribute);
	}
	
	public WsInt getWsInt(String name) {
		return this.attribute.get(name);
	}
	public WsDouble getWsDouble(String name) {
		return this.attribute2.get(name);
	}
	public WsString getWsString(String name) {
		return this.attribute3.get(name);
	}
	@Override
	public void actionPerferm(String typeMsg) {
		//do nothing
	}
	@Override
	public void registerAttribute(String name, WsBoolean attribute) {
		// TODO Auto-generated method stub
		this.attribute4.put(name, attribute);
	}
	@Override
	public WsBoolean getWsBoolean(String name) {
		// TODO Auto-generated method stub
		return this.attribute4.get(name);
	}
}
