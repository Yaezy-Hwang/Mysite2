package com.javaex.vo;

public class ListVo {
	
	private int no;
	private String title;
	private String content;
	private int hit;
	private String date;
	private int userNo;
	private String name;
	
	public ListVo() {}

	//list 출력
	public ListVo(int no, String title, int hit, String date, int userNo, String name) {
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.date = date;
		this.userNo = userNo;
		this.name = name;
	}
	
	//read
	public ListVo(int no, String name, int hit, String date, String title, String content, int userNo) {
		this.no = no;
		this.name = name;
		this.hit = hit;
		this.date = date;
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}

	public int getNo() {
		return no;
	}


	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getHit() {
		return hit;
	}

	public String getDate() {
		return date;
	}

	public int getUserNo() {
		return userNo;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ListVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", date=" + date
				+ ", userNo=" + userNo + ", name=" + name + "]";
	}
	
}
