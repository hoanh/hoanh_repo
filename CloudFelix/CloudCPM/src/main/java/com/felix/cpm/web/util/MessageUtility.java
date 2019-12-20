/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.felix.cpm.web.util;

import java.text.MessageFormat;

/**
 *
 * @author hoanh.hv
 */
public class MessageUtility extends AbstractConfig{
    private static final MessageUtility instance = new MessageUtility();
	private static final String MESSAGE_CONFIG_FILE_NAME = "message";

	public static MessageUtility get() {
		return instance;
	}
        public static String getMessage(String key){
            return get().getString(key);
        }
        public static String getMessage(String key, Object... params){
            return MessageFormat.format(MessageUtility.get().getString(key), params);
        }

	protected MessageUtility() {
		super(MESSAGE_CONFIG_FILE_NAME);
	}
}
