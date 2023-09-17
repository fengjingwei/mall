package com.hengxunda.springcloud.common.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    private static final Pattern REPLACE_NULL_PATTERN = Pattern.compile("//r|//n|//u3000");
    private static final Pattern NULL_PATTERN = Pattern.compile("^(//s)*$");

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            return str.getBytes(StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @param
     * @return
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (html == null) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString 对象串
     *                     例如：row.user.id
     *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (String val1 : vals) {
            val.append(".").append(val1);
            result.append("!").append(val.substring(1)).append("?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    public static String toHex(byte[] b) {
        return toHex(b, b.length);
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b    byte[] byte数组
     * @param iLen int 取前N位处理 N=iLen
     * @return String 每个Byte值之间空格分隔
     */
    public static String toHex(byte[] b, int iLen) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < iLen; n++) {
            sb.append(HEX_CHARS[(b[n] & 0xFF) >> 4]);
            sb.append(HEX_CHARS[b[n] & 0x0F]);
        }
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    /**
     * 空值检查<br>
     * <br>
     *
     * @param pInput 要检查的字符串<br>
     * @return boolean 返回检查结果,但传入的字符串为空的场合,返回真<br>
     */
    public static boolean isNull(Object pInput) {
        // 判断参数是否为空或者''
        if (pInput == null || "".equals(pInput)) {
            return true;
        } else if ("java.lang.String".equals(pInput.getClass().getName())) {
            // 判断传入的参数的String类型的
            // 替换各种空格
            String tmpInput = REPLACE_NULL_PATTERN.matcher((String) pInput).replaceAll("");
            // 匹配空
            return NULL_PATTERN.matcher(tmpInput).matches();
        } else {
            // 方法类
            Method method;
            String newInput;
            try {
                // 访问传入参数的size方法
                method = pInput.getClass().getMethod("size");
                // 判断size大小
                // 转换为String类型
                newInput = String.valueOf(method.invoke(pInput));
                // size为0的场合
                return Integer.parseInt(newInput) == 0;
            } catch (Exception e) {
                // 访问失败
                try {
                    // 访问传入参数的getItemCount方法
                    method = pInput.getClass().getMethod("getItemCount");
                    // 判断size大小
                    // 转换为String类型
                    newInput = String.valueOf(method.invoke(pInput));
                    // getItemCount为0的场合
                    return Integer.parseInt(newInput) == 0;
                } catch (Exception ex) {
                    // 访问失败
                    try {
                        // 判断传入参数的长度
                        // 长度为0的场合
                        return Array.getLength(pInput) == 0;
                    } catch (Exception exx) {
                        // 访问失败
                        try {
                            // 访问传入参数的hasNext方法
                            method = Iterator.class.getMethod("hasNext");
                            // 转换String类型
                            newInput = String.valueOf(method.invoke(pInput));
                            // 转换hasNext的值
                            return !Boolean.parseBoolean(newInput);
                        } catch (Exception exxx) {
                            // 以上场合不满足
                            return false;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("args = " + toCamelCase("hello_world"));
    }
}
