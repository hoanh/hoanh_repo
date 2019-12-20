/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.felix.cpm.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 *
 * @author iesvn17
 */
public class Util {
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.
                getCurrentInstance().
                getExternalContext().getResponse();
    }
    public static  void setFlashAttribute(String key,Object value){
        FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .put(key, value);
    }
    public static Object getFashAttribute(String key){
        return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
    }
    public static String getRequestParam(String key){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }
    public static Long getRequestLongParam(String key){
    	Long result = null;
    	String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    	if(param != null){
            try {
            	result = Long.parseLong(param);
            } catch (Exception e) {
            }
        }
        return result;
    }
    public static void eraseCookie() {
        HttpServletRequest req = Util.getRequest();
        HttpServletResponse resp = Util.getResponse();
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
        for (Cookie cookie : cookies) {
            cookie.setValue(null);
            cookie.setPath(null);
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
    }
    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }
//    public static String getSupplierCode() {
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//        return (String)session.getAttribute("suppliercode");
//    }
    public static Long getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (Long) session.getAttribute("userid");
        } else {
            return null;
        }
    }

//    public static boolean ifGranted(Integer role) {
//        HttpSession session = getSession();
//        return role.equals(session.getAttribute("role"));
//    }
//
//    public static boolean ifAllGranted(Integer... values) {
//        boolean isAuthorized = false;
//
//        for (Integer role : values) {
//            if (ifGranted(role)) {
//                isAuthorized = true;
//            } else {
//                isAuthorized = false;
//                break;
//            }
//        }
//
//        return isAuthorized;
//    }
//
//    public static boolean ifAnyGranted(Integer... values) {
//        boolean isAuthorized = false;
//
//        for (Integer role : values) {
//            if (ifGranted(role)) {
//                isAuthorized = true;
//                break;
//            }
//        }
//
//        return isAuthorized;
//    }
//
//    public static boolean ifNoneGranted(Integer... values) {
//        boolean isAuthorized = false;
//
//        for (Integer role : values) {
//            if (ifGranted(role)) {
//                isAuthorized = false;
//                break;
//            } else {
//                isAuthorized = true;
//            }
//        }
//
//        return isAuthorized;
//    }
    public static void show(String mess, Severity type) {
    	FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(type, mess, null));
    }
    public static void showMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
    public static String getMessage(String key, Object... params){
        return MessageFormat.format(MessageUtility.get().getString(key), params);
    }
    public static void showMessage(Severity severity, String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, MessageUtility.get()
                .getString(key), null));
    }

    private static void showMessage(Severity severity, String key, Object... params) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, MessageFormat.format(MessageUtility.get().getString(key), params) , null));
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     */
    public static void showWarn(String key) {
        showMessage(FacesMessage.SEVERITY_WARN, key);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     * @param param - String
     */
    public static void showWarn(String key, Object... param) {
        showMessage(FacesMessage.SEVERITY_WARN, key, param);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     */
    public static void showInfo(String key) {
        showMessage(FacesMessage.SEVERITY_INFO, key);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     * @param param - String
     */
    public static void showInfo(String key, Object... param) {
        showMessage(FacesMessage.SEVERITY_INFO, key, param);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     */
    public static void showError(String key) {
        showMessage(FacesMessage.SEVERITY_ERROR, key);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     * @param param - String
     */
    public static void showError(String key, Object... param) {
        showMessage(FacesMessage.SEVERITY_ERROR, key, param);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     */
    public static void showFatal(String key) {
        showMessage(FacesMessage.SEVERITY_FATAL, key);
    }

    /**
     * Method get message and show in faces
     *
     * @param key - String
     * @param param - String
     */
    public static void showFatal(String key, Object... param) {
        showMessage(FacesMessage.SEVERITY_FATAL, key, param);
    }
    
    public static String getFileExt(String fileName) {
		return fileName.split("\\.(?=[^\\.]+$)")[1];
	}
    
 

 	
 	public static boolean hasContent(String s) {
		return s != null && !s.trim().isEmpty();
	}
 	
 	public static String toShortDate(Date d) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(d);
	}
 	public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
 	public static void redirectPage(String page){
 		try {
// 			getResponse().sendRedirect(page);
// 			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, page);
			System.out.println("Redirect:"+getRequest().getContextPath() + "/"+page);
 			FacesContext.getCurrentInstance().getExternalContext().redirect(getRequest().getContextPath() + "/"+page);
// 			FacesContext.getCurrentInstance().getExternalContext().dispatch(page);
 		} catch (IOException e) {
			e.printStackTrace();
		}
 	}
 	public static String getURLWithContextPath(HttpServletRequest request) {
 	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
 	}
	public static <T> List<T> toList(Iterable<T> iterable){
    	if(iterable != null){
    		List<T> list = new ArrayList<T>();
    		for (T item : iterable) {
				list.add(item);
			}
    		return list;
    	}else{
    		return null;
    	}
    }
}
