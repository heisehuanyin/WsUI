package ws.mechanism.binding;

public class WsInt extends SimpleAutosync{
	private int data = 0;
	private WsDataBinding bridge;
	
	public WsInt() {}
	public WsInt(int num) {this.data = num;}
	
	void _bindingoperateFrom(WsDataBinding bindingBase) {
		this.bridge = bindingBase;
	}

	public int get() {
		return this.data;
	}

	public void set(int i) {
		this.data = i;//同步数据

		//如果是由外部设置，在set过程中触发数据同步操作
		if(this.bridge!=null && this.bridge.getSource() == null)
			this.bridge.push_Sync_Operate(this);
	}
	@Override
	void _refresh() {
		this.set(data);
	}

}
