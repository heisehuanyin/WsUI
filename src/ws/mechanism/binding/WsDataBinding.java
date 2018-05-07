package ws.mechanism.binding;

public class WsDataBinding {
	public static final int BINDINGMODE_READONLY=0;
	public static final int BINDINGMODE_INTERACT=1;
	private int bindingMode = 0;
	private SimpleAutosync front;
	private SimpleAutosync end;
	private SimpleAutosync source = null;
	
	public WsDataBinding() {}
	
	public WsDataBinding(int bindingMode) {this.bindingMode = bindingMode;}
	
	public void BindData(SimpleAutosync front, SimpleAutosync end) {
		this.front = front;
		front._bindingoperateFrom(this);
		this.end = end;
		end._bindingoperateFrom(this);
		end._refresh();
	}
	
	final SimpleAutosync getSource() {
		return this.source;
	}

	void push_Sync_Operate(WsInt source) {
		this.source = source;
		
		if(source == front) {
			//如果是只读绑定，那么前端数据无法改变后端数据,甚至前端自己的数据都会变回去
			if(bindingMode == WsDataBinding.BINDINGMODE_READONLY) {
				((WsInt)front).set(((WsInt)end).get());
			}
			else {
				((WsInt)end).set(source.get());
			}
		}else {
			((WsInt)front).set(source.get());
		}
		
		this.source = null;
	}
	void push_Sync_Operate(WsDouble source) {
		this.source = source;
		
		if(source == front) {
			//如果是只读绑定，那么前端数据无法改变后端数据,甚至前端自己的数据都会变回去
			if(bindingMode == WsDataBinding.BINDINGMODE_READONLY) {
				((WsDouble)front).set(((WsDouble)end).get());
			}
			else{
				((WsDouble)end).set(source.get());
			}
		}
		else {
			((WsDouble)front).set(source.get());
		}
		
		this.source = null;
	}
	void push_Sync_Operate(WsString source) {
		this.source = source;
		
		if(source == front) {
			//如果是只读绑定，那么前端数据无法改变后端数据,甚至前端自己的数据都会变回去
			if(bindingMode == WsDataBinding.BINDINGMODE_READONLY) {
				((WsString)front).set(((WsString)end).get());
			}
			else {
				((WsString)end).set(source.get());
			}
		}
		else {
			((WsString)front).set(source.get());
		}
		
		this.source = null;
	}

	void push_Sync_Operate(WsBoolean source) {
		this.source = source;
		
		if(source == front) {
			//如果是只读绑定，那么前端数据无法改变后端数据,甚至前端自己的数据都会变回去
			if(bindingMode == WsDataBinding.BINDINGMODE_READONLY) {
				((WsBoolean)front).set(((WsBoolean)end).get());
			}
			else {
				((WsBoolean)end).set(source.get());
			}
		}
		else {
			((WsBoolean)front).set(source.get());
		}
		
		this.source = null;
	}
}
