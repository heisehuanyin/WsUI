package ws.tools.testcase;

import ws.mechanism.binding.WsDouble;
import ws.mechanism.binding.WsInt;
import ws.mechanism.binding.WsString;
import ws.mechanism.binding.DataBinding;

public class BindingTest {

	public static void main(String[] args) {
		WsInt front = new WsInt(0);
		WsInt end = new WsInt(10);
		
		DataBinding bridge = new DataBinding(DataBinding.BINDINGMODE_INTERACT);
		
		bridge.BindData(front, end);
		System.out.println("end初始为10，绑定伊始的同步操作测试：front："+ front.get() + "\n================================");
		
		end.set(0);
		System.out.println("end设置为"+end.get()+",front的值："+ front.get() + ",end的值："+end.get());
		end.set(59);
		System.out.println("end设置为"+end.get()+",front的值："+ front.get() + ",end的值："+end.get());
		
		front.set(0);
		System.out.println("front设置为0,end的值："+ end.get()+",front的值为："+front.get());
		front.set(22);
		System.out.println("front设置为22,end的值："+ end.get()+",front的值为："+front.get());
		
		System.out.println("========================================");
		
		WsDouble front2 = new WsDouble();
		WsDouble end2 = new WsDouble();

		DataBinding bridge2 = new DataBinding();
		
		bridge2.BindData(front2, end2);
		
		end2.set(0);
		System.out.println("end设置为"+end2.get()+",front的值："+ front2.get() + ",end的值："+end2.get());
		end2.set(59);
		System.out.println("end设置为"+end2.get()+",front的值："+ front2.get() + ",end的值："+end2.get());
		
		front2.set(0);
		System.out.println("front设置为0,end的值："+ end2.get()+",front的值为："+front2.get());
		front2.set(22);
		System.out.println("front设置为22,end的值："+ end2.get()+",front的值为："+front2.get());
		System.out.println("========================================");
		
		WsString front3 = new WsString();
		WsString end3 = new WsString();
		DataBinding bridge3 = new DataBinding(DataBinding.BINDINGMODE_INTERACT);
		
		bridge3.BindData(front3, end3);
		
		front3.set("设置了前端文字");
		System.out.println(end3.get());
		
	}

}
