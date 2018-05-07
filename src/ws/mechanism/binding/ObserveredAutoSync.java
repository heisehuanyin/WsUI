package ws.mechanism.binding;

public interface ObserveredAutoSync {
	void _bindingFrom(WsContainerBinding c);
	void _sync_to(ObserveredAutoSync front);
	void actionPerferm(String typeMsg);
}
