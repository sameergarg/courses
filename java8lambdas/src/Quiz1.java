import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 */
public class Quiz1 {
    private BiFunction<Buffer, Integer, Buffer> f = (Buffer b, Integer n) -> b.position(n);

    public static void main(String[] args) {
        Quiz1 q1 = new Quiz1();

        List<Integer> l = q1.getIntegersSignum();

        System.out.println(l);
    }

    private List<Integer> getIntegersSignum() {
        List<Integer> l = Arrays.asList(1, -1, 2, -2);
        //l.replaceAll(n->Integer.signum(n));
        l.replaceAll(Integer::signum);
        return l;
    }
}
