package com.example.typhoonvision001.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 乱不得静 on 2017/4/8.
 */
public class TyStatistics {
    public static int Count(List<String> var1){
        int var2=Integer.parseInt(var1.get(0));
        int var3=Integer.parseInt(var1.get(var1.toArray().length-1))+1;
        return var3-var2;
    }
}
