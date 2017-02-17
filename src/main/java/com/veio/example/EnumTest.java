package com.veio.example;

enum TYPE {
	OK(0),
	OTHER(-1);

	private int code;

	TYPE(int code) {
		this.code = code;
	}

	public static TYPE valueOf(int code) {
		for (TYPE type : TYPE.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.toString();
	}
}

public class EnumTest {
	public static void main(String[] args) {
		TYPE t = TYPE.valueOf(0);
		System.out.println(t.getCode());
		System.out.println(t.getName());
	}
}
