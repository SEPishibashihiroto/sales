package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderUpdateRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Status;
import com.example.demo.entity.Update;

@Service
@Transactional(rollbackOn = Exception.class)
public class VariousService {

	/**
	 * OrderService以外のメソッドを扱う
	 * 主にOrderControllerで使用
	 * */

	@Autowired
	OrderService orderService;

	//顧客のプルダウン作成に必要なものを取得
	public List<Customer> createListC() {
		return orderService.getCustomer();
	}

	//ステータスのプルダウン作成に必要なものを取得
	public List<Status> createListS() {
		return orderService.getStatus();
	}

	//編集や削除で取得した情報を実行で使用する型に情報をセット
	public OrderUpdateRequest setValue(int id) {
		Update order = orderService.findUpdateById(id);
		OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
		orderUpdateRequest.setId(order.getId());
		orderUpdateRequest.setCustomerid(order.getCustomerid());
		orderUpdateRequest.setOrderdate(writesura(order.getOrderdate()));
		orderUpdateRequest.setSnumber(order.getSnumber());
		orderUpdateRequest.setTitle(order.getTitle());
		orderUpdateRequest.setCount(order.getCount());
		orderUpdateRequest.setSpecifieddate(writesura(order.getSpecifieddate()));
		orderUpdateRequest.setDeliverydate(writesura(order.getDeliverydate()));
		orderUpdateRequest.setBillingdate(writesura(order.getBillingdate()));
		orderUpdateRequest.setQuoteprice(order.getQuoteprice());
		orderUpdateRequest.setOrderprice(order.getOrderprice());
		orderUpdateRequest.setStatusid(order.getStatusid());
		orderUpdateRequest.setDelete_flg(order.getDelete_flg());

		return orderUpdateRequest;
	}

	//編集確認画面から戻るボタンを使用して編集画面に遷移した際に入力情報をセット
	public OrderUpdateRequest setValue(OrderUpdateRequest editOrderRequest) {
		OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
		orderUpdateRequest.setId(editOrderRequest.getId());
		orderUpdateRequest.setCustomerid(editOrderRequest.getCustomerid());
		orderUpdateRequest.setOrderdate(editOrderRequest.getOrderdate());
		orderUpdateRequest.setSnumber(editOrderRequest.getSnumber());
		orderUpdateRequest.setTitle(editOrderRequest.getTitle());
		orderUpdateRequest.setCount(editOrderRequest.getCount());
		orderUpdateRequest.setSpecifieddate(editOrderRequest.getSpecifieddate());
		orderUpdateRequest.setDeliverydate(editOrderRequest.getDeliverydate());
		orderUpdateRequest.setBillingdate(editOrderRequest.getBillingdate());
		orderUpdateRequest.setQuoteprice(editOrderRequest.getQuoteprice());
		orderUpdateRequest.setOrderprice(editOrderRequest.getOrderprice());
		orderUpdateRequest.setStatusid(editOrderRequest.getStatusid());
		orderUpdateRequest.setDelete_flg(editOrderRequest.getDelete_flg());
		return orderUpdateRequest;
	}

	//登録確認画面から戻るボタンを使用して登録画面に遷移した際に入力情報をセット
	public OrderRequest setValue(OrderRequest addOrderRequest) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setCustomerid(addOrderRequest.getCustomerid());
		orderRequest.setOrderdate(addOrderRequest.getOrderdate());
		orderRequest.setSnumber(addOrderRequest.getSnumber());
		orderRequest.setTitle(addOrderRequest.getTitle());
		orderRequest.setCount(addOrderRequest.getCount());
		orderRequest.setSpecifieddate(addOrderRequest.getSpecifieddate());
		orderRequest.setDeliverydate(addOrderRequest.getDeliverydate());
		orderRequest.setBillingdate(addOrderRequest.getBillingdate());
		orderRequest.setQuoteprice(addOrderRequest.getQuoteprice());
		orderRequest.setOrderprice(addOrderRequest.getOrderprice());
		orderRequest.setStatusid(addOrderRequest.getStatusid());
		return orderRequest;
	}

	//バリデーション
	public List<String> createErrorList(OrderRequest request) {
		List<String> errorList = new ArrayList<String>();
		/**
		 * 顧客に関するエラー
		 * */
		if (request.getCustomer().equals("") || request.getCustomer().equals("bran")) {
			errorList.add(getErrorMessage(0));
		}
		/**
		 * 受注日に関するエラー
		 * */
		if (!(request.getOrderdate().equals(""))) {
			if (!(request.getOrderdate().matches("^\\d{4}/\\d{2}/\\d{2}$")
					&& chackDayData(request.getOrderdate()))) {
				errorList.add(getErrorMessage(1));
			}
		}

		/**
		 * S番号に関するエラー
		 **/
		if (!(request.getSnumber().equals(""))) {
			if (!(isInt(request.getSnumber()))) {
				errorList.add(getErrorMessage(2));
			} else if (request.getSnumber().length() >= 5) {
				errorList.add(getErrorMessage(3));
			}
		}

		/**
		 * 件名に関するエラー
		 * */
		if (stringDigits(request.getTitle()) > 80) {
			errorList.add(getErrorMessage(4));
		} else if (stringDigits(request.getTitle()) == 0) {
			errorList.add(getErrorMessage(5));
		}
		/**
		 * 数量に関するエラー
		 *
		 */
		if (!(request.getCount().equals(""))) {
			if (!(isInt(request.getCount()))) {
				errorList.add(getErrorMessage(6));
			}
		}

		/**
		 * 納入指定日に関するエラー
		 * */
		if (!(request.getSpecifieddate().equals(""))) {
			if (!(request.getSpecifieddate().matches("^\\d{4}/\\d{2}/\\d{2}$")
					&& chackDayData(request.getSpecifieddate()))) {
				errorList.add(getErrorMessage(7));
			}
		}
		/**
		 * 納入日に関するエラー
		 * */
		if (!(request.getDeliverydate().equals(""))) {
			if (!(request.getDeliverydate().matches("^\\d{4}/\\d{2}/\\d{2}$")
					&& chackDayData(request.getDeliverydate()))) {
				errorList.add(getErrorMessage(8));
			}
		}
		/**
		 * 請求日に関するエラー
		 * */
		if (!(request.getBillingdate().equals(""))) {
			if (!(request.getBillingdate().matches("^\\d{4}/\\d{2}/\\d{2}$")
					&& chackDayData(request.getBillingdate()))) {
				errorList.add(getErrorMessage(9));
			}
		}
		/**
		 * 見積金額に関するエラー
		 * */
		if (getdigitcount(request.getQuoteprice()) > 12) {
			errorList.add(getErrorMessage(11));
		}
		/**
		 * 受注金額に関するエラー
		 * */
		if (request.getStatus().equals("") || request.getStatus().equals("bran")) {
			errorList.add(getErrorMessage(14));
		}

		return errorList;
	}

	//エラー文
	private String getErrorMessage(int n) {
		List<String> messageList = new ArrayList<String>();
		messageList.add("顧客は必須項目です");//0
		messageList.add("受注日は「yyyy/mm/dd」の形式で入力してください");//1
		messageList.add("S番号は数値形式で入力してください");//2
		messageList.add("S番号は4桁以内で入力してください");//3
		messageList.add("件名は全角40字以内で入力してください");//4
		messageList.add("件名は必須項目です");//5
		messageList.add("数量は数値形式で入力してください");//6
		messageList.add("納入指定日は「yyyy/mm/dd」の形式で入力してください");//7
		messageList.add("納入日は「yyyy/mm/dd」の形式で入力してください");//8
		messageList.add("請求日は「yyyy/mm/dd」の形式で入力してください");//9
		messageList.add("見積金額は数値形式で入力してください");//10
		messageList.add("見積金額は12桁以内で入力してください");//11
		messageList.add("受注金額は数値形式で入力してください");//12
		messageList.add("受注金額は12桁以内で入力してください");//13
		messageList.add("ステータスは必須項目です");//14
		return messageList.get(n);
	}

	//文字数確認
	private static int stringDigits(String s) {
		char[] chars = s.toCharArray();
		int digits = 0;
		for (int i = 0; i < chars.length; i++) {
			digits += (String.valueOf(chars[i]).getBytes().length < 2) ? 1 : 2;
		}
		return digits;
	}

	//数字桁数確認
	private int getdigitcount(int n) {
		int cnt = 0;
		while (n % 10 != 0) {
			n /= 10;
			cnt++;
		}
		return cnt;
	}

	//文字列のもので数字で書かれているか
	private boolean isInt(String s) {
		boolean isDigit = true;
		for (int i = 0; i < s.length(); i++) {
			isDigit = Character.isDigit(s.charAt(i));
			if (!isDigit) {
				break;
			}
		}
		return isDigit;
	}

	//重複した処理  顧客とステータスのリストのモデル追加
	public void addListModel(Model model) {
		model.addAttribute("clist", createListC());
		model.addAttribute("slist", createListS());
	}

	//重複した処理  確認画面にて使用するモデル追加
	public void addIndividualModel(OrderRequest request, Model model) {
		model.addAttribute("sNumber", writesunmber(request.getSnumber()));
		model.addAttribute("orderPrice", writekanma(request.getOrderprice()));
		model.addAttribute("quotePrice", writekanma(request.getQuoteprice()));
	}

	//重複した処理  エラー検知により入力画面へ戻る際のidセット
	public void setIdes(OrderRequest request) {
		request.setCustomerid(request.getCustomer());
		request.setStatusid(request.getStatus());
	}

	//入力された日付が正しい日付かどうか
	private boolean chackDayData(String s) {
		return ismonth(s) && isday(createdays(s), s);
	}

	//対応した月の日付表を渡す
	private int[] createdays(String s) {
		char[] chars = createCharList(s);
		int nen = 0;
		for (int i = 0; i < 4; i++) {
			nen *= 10;
			nen += Integer.parseUnsignedInt("" + chars[i]);
		}
		System.out.println("nen ; " + nen);
		int month = 0;
		for (int i = 4; i < 6; i++) {
			month *= 10;
			month += Integer.parseUnsignedInt("" + chars[i]);
		}
		System.out.println("month : " + month);
		int[] days;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = new int[31];
			for (int i = 0; i < days.length; i++) {
				days[i] = i + 1;
			}
			return days;
		case 2:
			if (nen % 4 == 0) {
				days = new int[29];
			} else {
				days = new int[28];
			}
			for (int i = 0; i < days.length; i++) {
				days[i] = i + 1;
			}
			return days;

		case 4:
		case 6:
		case 9:
		case 11:
			days = new int[30];
			for (int i = 0; i < days.length; i++) {
				days[i] = i + 1;
			}
			return days;
		}
		return null;
	}

	//入力された日付の「日」が正しいかどうか
	private boolean isday(int[] list, String s) {
		char[] chars = createCharList(s);
		int day = 0;
		for (int i = 6; i < chars.length; i++) {
			day *= 10;
			day += Integer.parseUnsignedInt("" + chars[i]);
		}
		System.out.println("day : " + day);
		for (int listdays : list) {
			if (listdays == day) {
				return true;
			}
		}
		return false;
	}

	//入力された日付の「月」が正しいかどうか
	private boolean ismonth(String s) {
		char[] chars = createCharList(s);
		int month = 0;
		for (int i = 4; i < 6; i++) {
			month *= 10;
			month += Integer.parseUnsignedInt("" + chars[i]);
		}
		for (int i = 1; i <= 12; i++) {
			if (i == month) {
				return true;
			}
		}
		return false;
	}

	private char[] createCharList(String s) {
		return s.replaceAll("/", "").toCharArray();
	}

	/**
	 * データ表示において必要なものを挿入したり削除したりする
	 * */
	//編集画面の日付に’/’をつける
	private String writesura(String s) {
		return s.equals("") ? s : s.replaceAll("-", "/");
	}

	//確認画面のS番号に’S-’をつける
	public String writesunmber(String s) {
		return s.equals("") ? s : "S-" + s;
	}

	//確認画面の金額にカンマと'\'をつける
	public String writekanma(int n) {
		return n == 0 ? "" + n : "\\" + String.format("%,d", n);
	}

}
