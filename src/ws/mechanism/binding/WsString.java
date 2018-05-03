package ws.mechanism.binding;

public class WsString extends SimpleAutosync{
	private String data;
	private WsDataBinding bridge;
	
	public WsString() {};
	public WsString(String str) {this.data = str;}
	
	void _bindingoperateFrom(WsDataBinding bindingBase) {
		this.bridge = bindingBase;
	}

	public String get() {
		return this.data;
	}

	public void set(String string) {
		this.data = string;//同步数据

		//如果是由外部设置，在set过程中触发数据同步操作
		if(this.bridge != null &&this.bridge.getSource() == null)
			this.bridge.push_Sync_Operate(this);
	}
	@Override
	void refresh() {
		this.set(data);
	}

}
