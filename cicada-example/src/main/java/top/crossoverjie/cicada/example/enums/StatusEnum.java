package top.crossoverjie.cicada.example.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author admin
 */

@AllArgsConstructor
public enum StatusEnum {

    SUCCESS("9000", "成功"),
    FALLBACK("8000", "FALL_BACK"),
    VALIDATION_FAIL("3000", "参数校验失败"),
    FAIL("4000", "失败"),
    REPEAT_REQUEST("5000", "重复请求"),
    REQUEST_LIMIT("6000", "请求限流");

    @Setter
    @Getter
    private final String code;
    @Setter
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
