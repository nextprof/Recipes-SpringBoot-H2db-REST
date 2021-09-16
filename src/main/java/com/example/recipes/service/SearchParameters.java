package com.example.recipes.service;

import org.apache.commons.lang3.StringUtils;

public enum SearchParameters {
    NAME,CATEGORY;

    public static Boolean isParamValid(String name) {
        if(StringUtils.isBlank(name))
            return false;
        for(SearchParameters param : SearchParameters.values()) {
            if(name.equalsIgnoreCase(param.toString()))
                return true;
        }
        return false;
    }
}
