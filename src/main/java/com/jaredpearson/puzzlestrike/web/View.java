package com.jaredpearson.puzzlestrike.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {
	public void show(HttpServletRequest request, HttpServletResponse response) throws Exception;
}