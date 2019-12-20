/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.felix.cpm.web.util;

import java.util.ResourceBundle;

/**
 *
 * @author hoanh.hv
 */
class AbstractConfig {

    private final ResourceBundle rb;

    protected AbstractConfig(String config) {
        rb = ResourceBundle.getBundle(config);
    }

    public String getString(String key) {
        String value = rb.getString(key);
        if (value != null) {
            value = value.trim();
        }
        return value;
    }

    public Integer getInt(String key) {
        try {
            return Integer.valueOf(getString(key));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long getLong(String key) {
        try {
            return Long.valueOf(getString(key));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(getString(key));
    }
}
