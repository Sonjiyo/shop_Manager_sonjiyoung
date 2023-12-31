package dao;

import java.util.ArrayList;

import Util.InputManager;
import vo.Cart;
import vo.Item;

public class CartDAO {
	private ArrayList<Cart> cartList;
	
	public CartDAO(){
		cartList = new ArrayList<Cart>();
	}
	
	private boolean cartListIsEmpty(String id) {
		if(cartList.size()==0) {
			System.out.println("카트에 아이템이 없습니다.");
			return true;
		}
		int idx = -1;
		for(int i =0; i<cartList.size(); i++) {
			if(cartList.get(i).getUserId().equals(id)) {
				idx = i;
				break;
			}
		}
		if(idx==-1) {
			System.out.println("카트에 아이템이 없습니다.");
			return true;
		}
		return false;
	}
	
	public void cartDataLoad(String data){
		if(data.isEmpty()) return;
		cartList.clear();
		String[] arr = data.split("\n");
		for(int i =0; i<arr.length; i++) {
			String[] temp = arr[i].split("/");
			
			cartList.add(new Cart(temp[0],temp[1]));
		}
	}
	
	public String cartDataSave(){
		String data = "";
		if(cartList.size()==0) return data;
		for(Cart c : cartList) {
			data += "%s/%s\n".formatted(c.getUserId(),c.getItemName());
		}
		data = data.substring(0,data.length()-1);
		return data;
	}
	
	public void addcartList(String id, ItemDAO itemDAO) {
		String name = itemDAO.itemSelect();
		cartList.add(new Cart(id, name));
	}
	
	public void allCartListPrint() {
		System.out.println("id\t제품명");
		System.out.println("================");
		for(Cart c : cartList) {
			System.out.println(c);
		}
	}
	
	private boolean checkCartItemList(ArrayList<String> list, String name) {
		for(String l : list) {
			if(l.equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public void myCartListPrint(String id) {
		if(cartListIsEmpty(id)) return;
		System.out.println("===장바구니 목록===");
		ArrayList<String> list = new ArrayList<String>();
	
		for(Cart c : cartList) {
			if(c.getUserId().equals(id) && checkCartItemList(list, c.getItemName())) {
				list.add(c.getItemName());					
			}
		}
		
		for(String l : list) {
			int cnt = 0;
			for(Cart c : cartList) {
				if(c.getUserId().equals(id) && l.equals(c.getItemName())) {
					cnt++;
				}
			}
			if(cnt>0) {
				System.out.println(l+"\t"+cnt+"개");
			}
		}
		return;
	}
	
	public void removeCartList(String id) {
		String name = InputManager.getValue("삭제할 아이템 입력");
		int idx = -1;
		for(int i=0; i<cartList.size(); i++) {
			if(cartList.get(i).getItemName().equals(name) && 
					cartList.get(i).getUserId().equals(id)) {
				idx = i;
				break;
			}
		}
		if(idx==-1) {
			System.out.println("장바구니에 해당 아이템이 없습니다.");
			return;
		}
		cartList.remove(idx);
		System.out.println("[삭제 완료]");
	}
}
