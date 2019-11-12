package com.hobbymeetingapp.server.models.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericResponse extends BaseResponse {
    private List<String> errors;
    private List<String> messages;

    public GenericResponse()
    {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public GenericResponse(List<String> messages, List<String> errors) {
        this.errors = errors;
        this.messages = messages;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getMessages() {
        return messages;
    }

    public static GenericResponse singletonMessage(String message) {
        return new GenericResponse(Collections.singletonList(message), new ArrayList<>());
    }

    public static GenericResponse singletonError(String error) {
        return new GenericResponse(new ArrayList<>(), Collections.singletonList(error));
    }
}
