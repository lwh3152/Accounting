package com.lwh.accounting.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lwh.accounting.R;
import com.lwh.accounting.entity.Accounting;

public class AccountAdapter extends BaseAdapter{

	List<Accounting> accountList;
	Context mContext;
	
	public AccountAdapter(Context context,List<Accounting> accountList){
		this.mContext = context;
		this.accountList = accountList;
	}
	
	@Override
	public int getCount() {
		return accountList.size();
	}

	@Override
	public Object getItem(int position) {
		return accountList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder viewHolder = null;
		if(convertView == null){
			view = LayoutInflater.from(mContext).inflate(R.layout.listview_detail, null);
			TextView textIndex = (TextView) view.findViewById(R.id.textIndex);
			TextView textDate = (TextView) view.findViewById(R.id.textDate);
			TextView textType = (TextView) view.findViewById(R.id.textType);
			TextView textPrice = (TextView) view.findViewById(R.id.textPrice);
			TextView textNote = (TextView) view.findViewById(R.id.textNote);
			viewHolder = new ViewHolder(textIndex,textDate, textType, textPrice, textNote);
			view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		// 序号
		viewHolder.index.setText(position+1 + ".");
		// 日期
		int year = accountList.get(position).getYear();
		int month = accountList.get(position).getMonth();
		int day = accountList.get(position).getDay();
		viewHolder.date.setText(year+"-"+month+"-"+day);
		// 类型:收入/支出
		int type = accountList.get(position).getType();
		String strType = type == 1 ? "收入" : "支出";
		viewHolder.type.setText(strType);
		// 金额
		viewHolder.price.setText(accountList.get(position).getPrice()+"");
		// 备注
		viewHolder.note.setText(accountList.get(position).getNote()+"");
		return view;
	}

	class ViewHolder{
		TextView index;
		TextView date;
		TextView type;
		TextView price;
		TextView note;
		public ViewHolder (TextView textIndex,TextView textDate,TextView textType,TextView textPrice,TextView textNote){
			this.index = textIndex;
			this.date = textDate;
			this.type = textType;
			this.price = textPrice;
			this.note = textNote;
		}
	}
}
