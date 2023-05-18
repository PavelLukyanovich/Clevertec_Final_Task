package ru.clevertec.userservice.util;

public class StringConstant {
    /*
     * ^[a-zA-Z0-9] - start with an alphanumeric character
     * start of group 1
     * ([._-](?![._-]) - follow by a dot, hyphen, or underscore, negative lookahead to
     *                   ensures dot, hyphen, and underscore does not appear consecutively
     * |[a-zA-Z0-9]) - or an alphanumeric character
     * end of group 1
     * {3,18} -  ensures the length of (group 1) between 3 and 18
     * [a-zA-Z0-9]$ - end with an alphanumeric character
     *
     * {3,18} plus the first and last alphanumeric characters, total length became {5,20}
     * */
    public static final String LOGIN_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    /*
     * ^ - start of line
     * (?=.*[0-9]) - positive lookahead, digit [0-9]
     * (?=.*[a-z]) - positive lookahead, one lowercase character [a-z]
     * (?=.*[A-Z]) - positive lookahead, one uppercase character [A-Z]
     * (?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) - positive lookahead, one of the special character in this [..]
     * . - matches anything
     * {8,20} - length at least 6 characters and maximum of 12 characters
     * $ - end of line
     * */
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,12}$";


}
