package lesson3;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class Lesson3Test {

    @Test
    public void shouldComputeDistanceParallely() throws Exception {
        //given
        Lesson3 lesson = new Lesson3();

        //when
        int[][] distances = lesson.computeLevenshtein(Arrays.asList("kitten", "sitting"), true);
        assertThat(distances[0][0], is(0));
        assertThat(distances[0][1], is(3));
        assertThat(distances[1][0], is(3));
        assertThat(distances[1][1], is(0));

        //then
        //assertThat(pairs, Matchers.hasEntry("Hello", "Sameer"));
    }

    @Test
    public void shouldComputeDistanceSequentially() throws Exception {
        //given
        Lesson3 lesson = new Lesson3();

        //when
        int[][] distances = lesson.computeLevenshtein(Arrays.asList("kitten", "sitting"), false);
        assertThat(distances[0][0], is(0));
        assertThat(distances[0][1], is(3));
        assertThat(distances[1][0], is(3));
        assertThat(distances[1][1], is(0));

        //then
        //assertThat(pairs, Matchers.hasEntry("Hello", "Sameer"));
    }
}