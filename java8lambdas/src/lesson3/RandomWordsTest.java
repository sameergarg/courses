package lesson3;

import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class RandomWordsTest {

    @Test
    public void shouldReadWords() throws Exception {
        RandomWords randomWords = new RandomWords();
    }

    @Test
    public void shouldObtainRandonWordList() throws Exception {
        //given

        RandomWords randomWords = new RandomWords();

        //when
        assertThat(randomWords.createList(5).size(), is(5));
        assertThat(randomWords.createList(10).size(), is(10));

        //then
    }
}