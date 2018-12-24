/**
 *
 */
package com.example.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author feng.yingjie
 *
 */
public abstract class BaseService extends HttpServlet {

	public static final String HTML_PATH = "HTML_PATH";
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected abstract HashMap<String,String> setReplaceMap(HttpServletRequest req);

	protected abstract void doBusiness(HttpServletRequest req,HttpServletResponse resp);


	private void writeFile(FileReader reader,PrintWriter writer,HashMap<String,String> replaceMap) throws IOException{
		char[] buff = new char[1024];
		int len = 0;

		while( -1 != (len=reader.read(buff)) ){
			String fileText = String.valueOf(buff, 0, len);

			Iterator keys= replaceMap.keySet().iterator();
			while(keys.hasNext()){
				String key = (String) keys.next();
				fileText = fileText.replaceFirst(key, replaceMap.get(key));
			}

			writer.print(fileText);
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		PrintWriter writer = null;
		FileReader reader = null;

		try {
			writer = resp.getWriter();
			String filePath = req.getRealPath("") + req.getServletPath().replace("/","\\");

			reader = new FileReader(filePath);
			HashMap<String,String> replaceMap = setReplaceMap(req);

			doBusiness(req,resp);
			writeFile(reader,writer,replaceMap);

		} catch(FileNotFoundException e){
			writer.print("html file not found");
		} catch (IOException e) {
			try {
				resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Server Error");
			} catch (IOException e1) {
			}
		}finally{
			writer.flush();
			writer.close();
			if(reader !=null){
				try {
					reader.close();
				} catch (IOException e) {
				}
				reader = null;
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp){

		PrintWriter writer = null;
		FileReader reader = null;
		try {
			writer = resp.getWriter();
			String htmlName = req.getParameter(HTML_PATH);
			String filePath = req.getContextPath() + "/html/" + htmlName + ".html";
			reader = new FileReader(filePath);
			HashMap<String,String> replaceMap = setReplaceMap(req);
			doBusiness(req,resp);
			writeFile(reader,writer,replaceMap);

			if(htmlName == null || htmlName.trim().length() == 0){
				resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "HTML_PATH Empty");
			}
		} catch(FileNotFoundException e){
			writer.print("html file not found");
		} catch (IOException e) {
			try {
				resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Server Error");
			} catch (IOException e1) {
			}
		}finally{
			writer.flush();
			writer.close();
			if(reader !=null){
				try {
					reader.close();
				} catch (IOException e) {
				}
				reader = null;
			}
		}
	}
}