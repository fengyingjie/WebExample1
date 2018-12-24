package com.example.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BaseSV extends BaseService {

	@Override
	protected HashMap<String, String> setReplaceMap(HttpServletRequest req) {
		HashMap<String, String> resMap = new HashMap<String, String>();

		resMap.put("<JSCONTEST>", "<h>11111</h>");
		return resMap;
	}

	@Override
	protected void doBusiness(HttpServletRequest req, HttpServletResponse resp) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
