package io.github.severnarch.archlib.exceptions.config;

import java.io.FileNotFoundException;

public class PropertyNotFoundException extends Throwable {
    public PropertyNotFoundException(String propertyName, String configurationName) {
        super("Property of name '"+propertyName+"' could not be found in configuration '"+configurationName+"'.");
    }
}
