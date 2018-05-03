package ws.mechanism.binding;

public abstract interface WsContainerCommon {
	abstract void _bindingFrom(ContainerBinding c);
	abstract void _sync_to(WsContainerCommon front);
	abstract void actionPerferm(String typeMsg);
}
