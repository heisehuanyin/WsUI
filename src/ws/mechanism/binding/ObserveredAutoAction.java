package ws.mechanism.binding;

public abstract interface ObserveredAutoAction {
	abstract void _bindingFrom(ContainerBinding c);
	abstract void _sync_to(ObserveredAutoAction front);
	abstract void actionPerferm(String typeMsg);
}
