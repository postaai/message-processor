package com.br.messageprocessor.messageprocessor.entity.Enum;

public enum StatusEnum {
    QUEUED(1, "queued"),
    IN_PROGRESS(2, "in_progress"),
    requires_action(3, "requires_action"),
    CANCELING(4, "cancelling"),
    CANCELED(5, "cancelled"),
    FAILED(7, "failed"),
    COMPLETED(8, "completed"),
    EXPIRED(9, "expired");

    private Integer code;
    private String description;

    StatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Integer getCode(){
        return this.code;
    }

    public static String getDescription(){
        return this.description;
    }
}
