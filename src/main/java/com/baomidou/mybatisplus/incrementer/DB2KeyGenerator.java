package com.baomidou.mybatisplus.incrementer;

/**
 * <p>
 * DB2 Sequence
 * </p>
 *
 * @author Caratacus
 * @Date 2017-06-12
 */
public class DB2KeyGenerator implements IKeyGenerator {

    @Override
    public String executeSql(String incrementerName) {
        return "values nextval for " + incrementerName;
    }


}
