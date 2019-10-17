package top.crossoverjie.cicada.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author admin
 */
@AllArgsConstructor
public enum StatusEnum {
    SUCCESS("9000", "success"),
    REQUEST_ERROR("7000", "Request Error"),
    DUPLICATE_IOC("8000", "Duplicate ioc impl error"),
    NULL_PACKAGE("8000", "Your main class is empty of package"),
    NOT_FOUND("404", "Need to declare a method by using @CicadaRoute!"),
    ILLEGAL_PARAMETER("404", "IllegalArgumentException: You can only have two parameters at most by using @CicadaRoute!");

    @Getter
    private final String code;

    @Getter
    private final String message;

    public static StatusEnum findStatus(String code) {
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ResultInfo StatusEnum not legal:" + code);
    }

    public static List<StatusEnum> getAllStatus() {
        List<StatusEnum> list = new ArrayList<>();
        Collections.addAll(list, values());
        return list;
    }

    public static List<String> getAllStatusCode() {
        List<String> list = new ArrayList<>();
        for (StatusEnum status : values()) {
            list.add(status.getCode());
        }
        return list;
    }
}
