package ws.tools.testcase;

import ws.mechanism.binding.WsInt;
import ws.mechanism.binding.DataBinding;

public class ClassExtendsBindable {

	public static void main(String[] args) {
		SubclassExtendsBindable_Int inta = new SubclassExtendsBindable_Int();

		System.out.println("==>>绑定前既存的前端数据结果");
		inta.set(100);
		WsInt end = new WsInt();
		
		DataBinding bridge = new DataBinding(DataBinding.BINDINGMODE_INTERACT);
		System.out.println("==>>绑定过程中的第一次同步操作，结果");
		bridge.BindData(inta, end);
		
		System.out.println("==>>绑定成功的第一次同步操作，结果");
		end.set(10);
	}

}

class SubclassExtendsBindable_Int extends WsInt{
	
	public void set(int i) {
		super.set(i);
		int content = this.get();
		System.out.println("获取的最终结果:"+ content);
	}
}