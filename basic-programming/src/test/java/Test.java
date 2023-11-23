import org.apache.commons.lang.StringUtils;

import java.util.*;

public class Test {

    @org.junit.jupiter.api.Test
    void a(){
        Map<String, String> map = new HashMap<>();
        map.put("小明","男");
        map.put("小l","男");
        map.put("小p","男");


        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> m : entries){
            System.out.println(m);
        }
        String a[] = new String[]{"a","b","c"};
        String join = StringUtils.join(a, ",");
        System.out.println(join);
        String[] split = join.split(",");
        String s = split.toString();
        String str ="afsdfsdfdsfssss";
        System.out.println(str.equalsIgnoreCase("afsdfsdfdsfSSSS"));
    }
}
