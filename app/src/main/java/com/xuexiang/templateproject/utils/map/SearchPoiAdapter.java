/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.templateproject.utils.map;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuexiang.templateproject.R;

/**
 * 类说明：
 * 
 * @author Liucd
 * @date 2014-10-30
 * @version 1.0
 */
public class SearchPoiAdapter extends BaseAdapter {
	private Context mContext;
	private List<LocationBean> cityPoiList;

	public SearchPoiAdapter(Context context, List<LocationBean> list) {
		this.mContext = context;
		this.cityPoiList = list;
	}

	@Override
	public int getCount() {
		if (cityPoiList != null) {
			return cityPoiList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (cityPoiList != null) {
			return cityPoiList.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class CityPoiHolder {
		public TextView tvMLIPoiName, tvMLIPoiAddress;
	}

	private CityPoiHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new CityPoiHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(
					R.layout.mapview_location_poi_lv_item, null);
			holder.tvMLIPoiName = (TextView) convertView
					.findViewById(R.id.tvMLIPoiName);
			holder.tvMLIPoiAddress = (TextView) convertView
					.findViewById(R.id.tvMLIPoiAddress);
			convertView.setTag(holder);
		} else {
			holder = (CityPoiHolder) convertView.getTag();
		}
		LocationBean cityPoi = cityPoiList.get(position);
		holder.tvMLIPoiName.setText(cityPoi.getLocName());
		holder.tvMLIPoiAddress.setText(cityPoi.getAddStr());
		return convertView;
	}
}
