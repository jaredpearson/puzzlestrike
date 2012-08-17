package com.jaredpearson.puzzlestrike.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	public View handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}