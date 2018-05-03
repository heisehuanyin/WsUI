package ws.tools.testcase;

import ws.mechanism.binding.WsBoolean;
import ws.mechanism.binding.WsDouble;
import ws.mechanism.binding.WsInt;
import ws.mechanism.binding.WsString;
import ws.mechanism.binding.WsClassBase;
import ws.mechanism.binding.WsDataBinding;

public class BuildObjectExtendClass extends WsClassBase {
	private WsString title = new WsString("");
	private WsInt num = new WsInt(0);
	private WsDouble d = new WsDouble(0);
	private WsBoolean boo = new WsBoolean(true);
	
	public BuildObjectExtendClass() {
		this.registerAttribute("title", title);
		this.registerAttribute("num", num);
		this.registerAttribute("d", d);
		this.registerAttribute("boo", boo);
	}
	
	public void printInt() {
		System.out.println(this.num.get());
	}
	
	public static void main(String[]  args) {
		WsInt end = new WsInt(9);
		BuildObjectExtendClass frontCollect = new BuildObjectExtendClass();
		new WsDataBinding(WsDataBinding.BINDINGMODE_INTERACT)
			.BindData(frontCollect.getWsInt("num"),end);
		end.set(100);
		
		frontCollect.printInt();
		
		end.set(2000);
		frontCollect.printInt();
		
		BuildObjectExtendClass two = new BuildObjectExtendClass();
		frontCollect.add(two);
		System.out.println(frontCollect.size());
	}
}
