import org.junit.jupiter.api.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class WordDistanceAnalyzerTest {
    @InjectMocks
    WordDistanceAnalyzer analyzer;

    @Spy
    ResultsPrinter spyResultsPrinter;

    @Mock
    InputReader mockReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void exceptionHandling() throws FileNotFoundException {
        when(mockReader.readInputFromPath(anyString())).thenThrow(FileNotFoundException.class);

        analyzer.getPairsWithMaxDistance();

        verify(spyResultsPrinter).printError("file not found");
    }

    @Test
    void testCorrectResultsPrinter() throws FileNotFoundException {
        when(mockReader.readInputFromPath(anyString())).thenReturn("a bb abb");

        analyzer.getPairsWithMaxDistance();

        verify(spyResultsPrinter).print("a - bb");
        verify(spyResultsPrinter).print("a - abb");
        verify(spyResultsPrinter).print("bb - abb");
    }

    @Test
    void testCaseSensitive() throws FileNotFoundException {
       when(mockReader.readInputFromPath(anyString())).thenReturn("WORD words WORDS");

       analyzer.getPairsWithMaxDistance();

       verify(spyResultsPrinter).printResults(Arrays.asList(new Pair("WORD", "words")));
    }

    @Test
    void testInputReadCorrectPath() throws FileNotFoundException {
        when(mockReader.readInputFromPath(anyString())).thenReturn("a b c");

        analyzer.getPairsWithMaxDistance();

        verify(mockReader).readInputFromPath("C:\\Users\\forus\\Desktop\\test.txt");
    }

    @Test
    void correctNumberOfResultsPrinted() throws FileNotFoundException {
        when(mockReader.readInputFromPath(anyString())).thenReturn("a b c");

        analyzer.getPairsWithMaxDistance();

        verify(spyResultsPrinter, times(3)).printPair(anyString(), anyString());
    }
}