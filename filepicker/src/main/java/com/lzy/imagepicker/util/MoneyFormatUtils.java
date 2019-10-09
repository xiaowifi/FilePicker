package com.lzy.imagepicker.util;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 人民币格式化工具。
 */
public class MoneyFormatUtils {
    String TAG="MoneyFormatUtils";
    DecimalFormat format=new DecimalFormat("0.00");
    //阿拉伯数字对照表。
    ArrayMap<String,String> cNumber=new ArrayMap<>();
    //小数：分、厘、毛、糸、忽、微、纤、沙、尘、埃、渺、漠、模糊、逡巡、须臾、瞬息、弹指、刹那、六德、虚空、清净、阿赖耶、阿摩罗、涅槃寂静。
    private static final String[] DUNIT = {"元","角","分","厘"};
    //大数：个、十、百、千、万、亿、兆、京、垓、秭、穣、沟、涧、正、载、极、恒河沙、阿僧祇、那由他、不可思议、无量大数。
    private static final String[] IUNIT = {"元","拾","佰","仟","万","拾万","佰万","仟万","亿","拾亿","佰亿","仟亿","万亿","拾兆","佰兆","仟兆","万兆","亿兆"};
    private MoneyFormatUtils() {
        cNumber.clear();
        cNumber.put("0","零");
        cNumber.put("1","壹");
        cNumber.put("2","贰");
        cNumber.put("3","叁");
        cNumber.put("4","肆");
        cNumber.put("5","伍");
        cNumber.put("6","陆");
        cNumber.put("7","柒");
        cNumber.put("8","捌");
        cNumber.put("9","玖");
    }

    static MoneyFormatUtils formatUtils;

    public static MoneyFormatUtils getInstance(){
        if (formatUtils==null){
            formatUtils=new MoneyFormatUtils();
        }
        return formatUtils;
    }



    public String formatChinese(double money){
        String m= format.format(money);
        Log.e(TAG, "formatChinese: "+Double.MAX_VALUE );
        String [] sg= m.split("\\.");
        String heard= sg[0];
        String footer= sg[1];
        getHeard(heard);
        getFooter(footer);
        return "";
    }

    private void getFooter(String footer) {

    }

    private void getHeard(String heard) {
        char[] ch = heard.toCharArray();
        List<String> list=new ArrayList<>();
        for (char c: ch){
            list.add(String.valueOf(c));
        }
        Collections.reverse(list);

    }
}
