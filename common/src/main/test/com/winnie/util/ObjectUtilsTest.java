package com.winnie.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjectUtilsTest {

    @Test
    public void deepClone() throws IOException, ClassNotFoundException {
        Map<Object, Object>  map = new HashMap<>();

        Map map2 = ObjectUtils.deepClone(map, Map.class);
        System.out.println(map==map2);
        Assert.assertSame(map2,map);
        Assert.assertEquals(map2, map);
    }

}