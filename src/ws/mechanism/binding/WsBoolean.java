package ws.mechanism.binding;

public class WsBoolean extends WsDataCommon{
	private DataBinding bridge= null;
	private boolean data = false;
	
	public WsBoolean() {}
	public WsBoolean(boolean b) {
		this.data = b;
	}

	@Override
	void _bindingoperateFrom(DataBinding b) {
		this.bridge = b;
	}
	public void set(boolean b) {
		this.data = b;

		//如果是由外部设置，在set过程中触发数据同步操作
		if(this.bridge != null && this.bridge.getSource() == null)
			this.bridge.push_Sync_Operate(this);
	}
	public boolean get() {return this.data;}
	@Override
	void refresh() {
		this.set(data);
	}
	
}
