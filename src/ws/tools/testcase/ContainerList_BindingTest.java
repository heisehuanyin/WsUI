package ws.tools.testcase;

import ws.mechanism.binding.WsContainerBinding;
import ws.mechanism.binding.WsList;

public class ContainerList_BindingTest {
	public static void main(String[] args) {
		WsList<String> one = new WsList<String>() {
			@Override
			public void actionPerferm(String typeMsg) {
				// TODO Auto-generated method stub
				System.out.println("前端捕获事件："+typeMsg);
			}
		};
		WsList<String> end = new WsList<String>() {
			@Override
			public void actionPerferm(String typeMsg) {
				// TODO Auto-generated method stub
				System.out.println("后端捕获事件："+typeMsg);
			}
		};
		WsContainerBinding bridge = new WsContainerBinding();
		bridge.BindContainer(one, end);
		end.add("object");
		end.add("object");
		end.add("object");
		end.add("object");
		end.add("object");
		end.add("object");
		end.add("object");
		end.add("object");
		end.add("object");
		if(!one.isEmpty()) {
			for(int i=0;i<one.size();++i) {
				System.out.println(one.get(i));
			}
		}
		
	}

}
