package com.STTFV.laundryAPI.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class FieldValueUtil {
    public static Object getFieldValue(Object object, String fieldName) {
        BeanWrapper wrapper = new BeanWrapperImpl(object);
        return wrapper.getPropertyValue(fieldName);
    }
}

