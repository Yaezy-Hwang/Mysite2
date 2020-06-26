package com.javaex.vo;

public class GuestVo {

	private int no;
	private String name;
	private String pw;
	private String content;
	private String date;
	
	public GuestVo() {}
	
	public GuestVo(String name, String pw, String content) {
		this.name = name;
		this.pw = pw;
		this.content = content;
	}

	public GuestVo(int no, String name, String pw, String content, String date) {
		this.no = no;
		this.name = name;
		this.pw = pw;
		this.content = content;
		this.date = date;
	}
	
	public int getNo() {
		return no;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return pw;
	}
	public String getContent() {
		return content;
	}
	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "GuestVo [no=" + no + ", name=" + name + ", pw=" + pw + ", content=" + content + ", date=" + date + "]";
	}
	
	
	
}
