package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BaseSV extends BaseService {

	@Override
	protected HashMap<String, String> setReplaceMap(HttpServletRequest req) {
		HashMap<String, String> resMap = new HashMap<String, String>();

		HashMap<String,String> loginMap = (HashMap<String,String>) getServletContext().getAttribute("LOGIN_NAME");
		StringBuilder nameList = new StringBuilder();
		if(loginMap != null){
			Iterator ite = loginMap.keySet().iterator();
			while(ite.hasNext()){
				String id = (String) ite.next();
				String time = loginMap.get(id);
				nameList.append(id + ":" + time);nameList.append("\r\n");
			}
		}

		resMap.put("<JSCONTEST>", nameList.toString());
		return resMap;
	}

	@Override
	protected void doBusiness(HttpServletRequest req, HttpServletResponse resp) {

		String action = req.getParameter(ACTION);
		if(ACTION_FACEIN.equals(action)){
			String id = req.getParameter("ID");
			String loginTime = req.getParameter("LOGINTIME");

			HashMap<String,String> loginMap = (HashMap<String,String>) getServletContext().getAttribute("LOGIN_NAME");
			if(loginMap == null){
				loginMap = new HashMap<String,String>();
			}
			if(!loginMap.containsKey(id)){
				loginMap.put(id, loginTime);
			}
			getServletContext().setAttribute("LOGIN_NAME",loginMap);
		}
	}
}
