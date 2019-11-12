package com.hobbymeetingapp.server.models;

import java.util.ArrayList;
import java.util.List;

public class GenericResponse extends BaseResponse {
    private List<String> errors;
    private List<String> messages;

    public GenericResponse()
    {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public GenericResponse(List<String> errors, List<String> messages) {
        this.errors = errors;
        this.messages = messages;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getMessages() {
        return messages;
    }
}
