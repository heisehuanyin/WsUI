package ws.mechanism.binding;

public class WsDouble extends SimpleAutosync{
	private double data=0;
	private WsDataBinding bridge;
	
	public WsDouble() {}
	public WsDouble(double d) {this.data = d;}
	
	public void set(double d) {
		this.data = d;//同步数据

		//如果是由外部设置，在set过程中触发数据同步操作
		if(this.bridge != null && this.bridge.getSource() == null)
			this.bridge.push_Sync_Operate(this);
	}
	
	public double get() {return this.data;}
	
	void _bindingoperateFrom(WsDataBinding b) {
		this.bridge = b;
	}
	@Override
	void refresh() {
		this.set(data);
	}
}
