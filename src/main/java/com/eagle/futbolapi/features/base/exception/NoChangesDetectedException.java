package com.eagle.futbolapi.features.base.exception;

public class NoChangesDetectedException extends RuntimeException {
    public NoChangesDetectedException(String message) {
        super(message);
    }

    public NoChangesDetectedException(String resource, Object id) {
        super(String.format("No changes detected for %s with id: '%s'", resource, id));
    }
}
