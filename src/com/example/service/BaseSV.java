package com.example.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
				nameList.append("<tr><td>" + id + "</td><td>" + time + "</td></tr>");
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
			Date dateTime = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");

			dateTime = new Date(Long.valueOf(loginTime).longValue());
			loginTime = formatter.format(dateTime);

			loginMap.put(id, loginTime);
//			if(!loginMap.containsKey(id)){
//				loginMap.put(id, loginTime);
//			}else{
//				loginMap.put(id, loginTime);
//			}
			getServletContext().setAttribute("LOGIN_NAME",loginMap);
		}
	}
}
