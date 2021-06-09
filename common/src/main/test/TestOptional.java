import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestOptional {
    @Test
    public void test1(){
        Optional<String> empty = Optional.empty();
        Assertions.assertNotNull(empty,"empty非空null");
        System.out.println(empty.get());
    }

    @Test
    public void arraysTest(){
        List<String> list = Arrays.asList("winnie");
        list.add("Jhon");
    }
}
