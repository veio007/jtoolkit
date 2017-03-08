package com.veio.example;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// tag json处理
public class JsonTest {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("aa",10);
		System.out.println(JSONObject.toJSONString(map));

		Bean bean = new Bean();
		bean.setI(10);
		bean.setS("oooo");
		List<Bean> beans = new ArrayList<>();
		beans.add(bean);
		System.out.println(JSONObject.toJSONString(beans));
	}

	public static class Bean{
		@JSONField(name="intvalue")
		private int i;
		@JSONField(name="stringvalue")
		private String s;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}
	}
}
