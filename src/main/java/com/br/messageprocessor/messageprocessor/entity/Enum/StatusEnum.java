package com.br.messageprocessor.messageprocessor.entity.Enum;

import java.util.Objects;

public enum StatusEnum {
    QUEUED(1, "queued"),
    IN_PROGRESS(2, "in_progress"),
    REQUIRES_ACTION(3, "requires_action"),
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

    public Integer getCode(){
        return code;
    }

    public String getDescription(){

        return description;
    }

    public static StatusEnum fromCode(Integer code){
        for(StatusEnum statusEnum : StatusEnum.values()){
            if(Objects.equals(statusEnum.code, code)){
                return statusEnum;
            }
        }
        return null;
    }
}
