package ws.mechanism.binding;

public class WsContainerBinding {
	public static final int BINDINGMODE_READONLY=0;
	public static final int BINDINGMODE_INTERACT=1;
	private int bindingMode = 0;
	private ObserveredAutoSync front;
	private ObserveredAutoSync end;
	private ObserveredAutoSync source = null;
	

	public WsContainerBinding() {}
	public WsContainerBinding(int bindingMode) {this.bindingMode = bindingMode;}
	
	public void BindContainer(WsList<?> front, WsList<?> end) {
		this.front = front;
		this.front._bindingFrom(this);
		this.end = end;
		this.end._bindingFrom(this);
		this.end._sync_to(front);
	}
	public void BindContainer(WsMap<?, ?> front, WsMap<?, ?> end) {
		this.front = front;
		this.front._bindingFrom(this);
		this.end = end;
		this.end._bindingFrom(this);
		this.end._sync_to(front);
	}
	Object getSource() {
		return this.source;
	}
	Object getFront() {
		return this.front;
	}
	int getMode() {
		return this.bindingMode;
	}

	private int listProcessJudgement(ObserveredAutoSync source) {
		this.source = source;
		if(this.source == this.front) {
			if(this.bindingMode != WsContainerBinding.BINDINGMODE_READONLY) {
				return 1;//从front 流向 end
			}
			return 0;//不做
		}else
			return -1;//从end 流向front
	}
	 
	void actionRecall(ObserveredAutoSync source, String typeMsg) {
		if(this.listProcessJudgement(source) > 0)
			end.actionPerferm(typeMsg);
		if(this.listProcessJudgement(source) < 0)
			front.actionPerferm(typeMsg);
		this.source = null;
	}
}
