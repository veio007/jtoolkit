package com.veio007.example.validation;


import java.util.List;

public class Message {
	private String content;
	private int offline;
	private Template tpl;
	private List<String> list;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOffline() {
		return offline;
	}

	public void setOffline(int offline) {
		this.offline = offline;
	}

	public Template getTpl() {
		return tpl;
	}

	public void setTpl(Template tpl) {
		this.tpl = tpl;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}
