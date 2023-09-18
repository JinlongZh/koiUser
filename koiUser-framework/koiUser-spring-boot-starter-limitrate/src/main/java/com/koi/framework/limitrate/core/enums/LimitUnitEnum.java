package com.koi.framework.limitrate.core.enums;

import lombok.Getter;

import java.util.Calendar;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 11:32
 */
@Getter
public enum LimitUnitEnum {

    /**
     * 每相当于非自然时间段,即从当前时间起的时间间隔
     **/
    PER_SECOND(0, "PER_SECOND", "每秒"),
    PER_MINUTES(0, "PER_MINUTES", "每分"),
    PER_HOUR(0, "PER_HOUR", "每时"),
    PER_DAY(0, "PER_DAY", "每日"),
    PER_WEEK(0, "PER_WEEK", "每周"),
    PER_MONTH(0, "PER_MONTH", "每月"),
    PER_YEAR(0, "PER_YEAR", "每年"),
    /**
     * 自然时间,即该时间的开始时间,例如自然月,即今日是1月31 则2月1日重置
     ***/
    MINUTES(1, "MINUTES", "自然分"),
    HOUR(1, "HOUR", "自然时"),
    DAY(1, "DAY", "自然日"),
    WEEK(1, "WEEK", "自然周"),
    MONTH(1, "MONTH", "自然月"),
    YEAR(1, "YEAR", "自然年"),

    ;
    private final Integer type;
    private final String code;
    private final String name;

    LimitUnitEnum(Integer type, String code, String name) {
        this.type = type;
        this.code = code;
        this.name = name;
    }

    public Long getExpireTs() {
        return getExpireTs(System.currentTimeMillis());
    }

    public Long getExpireTs(Long compareTime) {
        switch (this) {
            case PER_SECOND:
                return compareTime + 1000L;
            case PER_MINUTES:
                return compareTime + 60000L;
            case PER_HOUR:
                return compareTime + 60 * 60000L;
            case PER_DAY:
                return compareTime + 24 * 60 * 60000L;
            case PER_WEEK:
                return compareTime + 7 * 24 * 60 * 60000L;
            case PER_MONTH:
                return compareTime + 30 * 24 * 60 * 60000L;
            case PER_YEAR:
                return compareTime + 365 * 24 * 60 * 60000L;
            default:
                return getNaturalTime(compareTime);
        }

    }

    /**
     * 获取自然时间
     *
     * @param compareTime
     * @return
     */
    private Long getNaturalTime(Long compareTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(compareTime);
        //不需要break
        switch (this) {
            case YEAR:
                c.set(Calendar.MONTH, 11);
            case MONTH:
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            case WEEK:
                if (WEEK.equals(this)) {
                    int week = c.get(Calendar.DAY_OF_WEEK);
                    if (week > 1) {
                        c.add(Calendar.DATE, 8 - week);
                    }
                }
            case DAY:
                c.set(Calendar.HOUR, 23);
            case HOUR:
                c.set(Calendar.MINUTE, 59);
            case MINUTES:
                c.set(Calendar.SECOND, 59);
        }
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

}
