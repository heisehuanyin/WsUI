package ws.mechanism.binding;

public interface ObserveredAutoAction {
	void _bindingFrom(ContainerBinding c);
	void _sync_to(ObserveredAutoAction front);
	void actionPerferm(String typeMsg);
}
