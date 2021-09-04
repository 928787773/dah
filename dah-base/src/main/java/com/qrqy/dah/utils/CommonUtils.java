package com.qrqy.dah.utils;

import org.apache.http.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CommonUtils {

    /**
     * 生成6位随机数验证码
     */
    public static String vcode() {
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            vcode.append((int) (Math.random() * 9));
        }
        return vcode.toString();
    }

    /**
     * 性别转换
     */
    public static String getGenderStr(String gender) {
        String toGenderStr;
        switch (gender) {
            case "0":
                toGenderStr = "保密";
                break;
            case "1":
                toGenderStr = "男";
                break;
            case "2":
                toGenderStr = "女";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + gender);
        }
        return toGenderStr;
    }

    /**
     * 根据年龄计算出出生年份
     *
     * @return
     */
    public static Integer getAge(LocalDate birthday) {
        Calendar mycalendar = Calendar.getInstance();
        String nowYeal = String.valueOf(mycalendar.get(Calendar.YEAR));//获取当前年份
        String birth = String.valueOf(birthday.getYear());
        Integer age =  Integer.parseInt(nowYeal) - Integer.parseInt(birth);
        return age;
    }

    /**
     * 根据年龄计算出出生年份
     *
     * @return
     */
    public static LocalDate getBirthday(int age) {
        Calendar mycalendar = Calendar.getInstance();
        String nowYeal = String.valueOf(mycalendar.get(Calendar.YEAR));
        int birth = Integer.parseInt(nowYeal) - age;
        return LocalDate.of(birth, 1, 1);
    }

    /**
     * 获取当前时间年月日
     * 1@return
     */
    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前时间年月日时分秒
     * 1@return
     */
    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(calendar.getTime());
    }
    /**
     * 格式化时间年月日时分秒
     * 1@return
     * @return
     */
    public static LocalDateTime forDateTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.of(0,0,0));
    }

    /**
     * 利用j8的新特性，得到日期下一天更加方便了
     * @return
     */
    public static LocalDate getDateAdd(LocalDate date,Integer num){
        //当前对象减去指定的天数(一天)
        LocalDate resultDate = date.minusDays(num);
        return resultDate;
    }
    public static LocalDateTime parseDateTime(String date) {
        try {
            return LocalDateTime.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String formateDateTime(LocalDateTime date) {
        try {

            return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
