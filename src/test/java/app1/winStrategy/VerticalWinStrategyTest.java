package app1.winStrategy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class VerticalWinStrategyTest {

    @InjectMocks
    private VerticalWinStrategy verticalWinStrategy;

    @Test
    public void shouldReturnSymbol(){
        char[][] boardToTest= new char[2][2];
        boardToTest[0][0] = 'C';
        boardToTest[0][1] = 'C';
        assertThat(verticalWinStrategy.isWin(boardToTest), is('C'));
    }
    @Test
    public void shouldReturnNull(){
        char[][] boardToTest= new char[2][2];
        boardToTest[1][1] = 'C';
        boardToTest[0][0] = 'C';
        assertNull(verticalWinStrategy.isWin(boardToTest));
    }
}