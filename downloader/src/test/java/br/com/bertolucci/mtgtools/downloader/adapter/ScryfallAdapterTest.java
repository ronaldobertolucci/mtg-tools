package br.com.bertolucci.mtgtools.downloader.adapter;

import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.downloader.util.HttpConnectionUtil;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import br.com.bertolucci.mtgtools.shared.util.JsonFileToStringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScryfallAdapterTest {

    @Mock
    private HttpConnectionUtil httpConnectionUtil;
    @Mock
    private HttpResponse httpResponse;
    private ScryfallAdapter scryfallAdapter;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        MockitoAnnotations.initMocks(this);
        scryfallAdapter = new ScryfallAdapter(httpConnectionUtil);
        Mockito.when(httpConnectionUtil.getResponse(Mockito.any())).thenReturn(httpResponse);
    }

    @Test
    void testGetCard() throws IOException, NoApiConnectionException {
        String json = JsonFileToStringService.getString("src/test/resources/card.json");
        Mockito.when(httpResponse.body()).thenReturn(json);
        CardDto cardDto = scryfallAdapter.getObject("http://test.com.br/", CardDto.class);

        assertEquals("Black Ward", cardDto.name());
        assertEquals("{W}", cardDto.manaCost());
    }

    @ParameterizedTest
    @ValueSource(classes = {CardDto.class, SetDto.class})
    void throwsExceptionWhenNoApiConnectionOnObjects(Class input) throws IOException, InterruptedException {
        Mockito.when(httpConnectionUtil.getResponse(Mockito.any())).thenThrow(IOException.class);
        assertThrows(NoApiConnectionException.class, () -> scryfallAdapter.getObject("http://test.com.br/", input));
    }

    @ParameterizedTest
    @ValueSource(classes = {CardDto.class, SetDto.class, SymbolDto.class, RulingDto.class})
    <T> void testGetList(Class<T> var) throws IOException, NoApiConnectionException {
        String json = JsonFileToStringService.getString("src/test/resources/list.json");
        Mockito.when(httpResponse.body()).thenReturn(json);
        List<T> list = scryfallAdapter.getList("http://test.com.br/", var);
        assertEquals(3, list.size());
    }

    @ParameterizedTest
    @ValueSource(classes = {CardDto.class, SetDto.class, SymbolDto.class, RulingDto.class})
    <T> void testHasMoreOnList(Class<T> var) throws IOException, NoApiConnectionException {
        String json = JsonFileToStringService.getString("src/test/resources/has_more.json");
        String jsonWithoutMore = JsonFileToStringService.getString("src/test/resources/list.json");
        Mockito.when(httpResponse.body()).thenReturn(json).thenReturn(jsonWithoutMore);
        List<T> sets = scryfallAdapter.getList("http://test.com.br/", var);
        assertEquals(5, sets.size());
    }

    @ParameterizedTest
    @ValueSource(classes = {CardDto.class, SetDto.class, SymbolDto.class, RulingDto.class})
    <T> void testRawListWithDataNull(Class<T> var) throws NoApiConnectionException, IOException {
        String json = JsonFileToStringService.getString("src/test/resources/null_data.json");
        Mockito.when(httpResponse.body()).thenReturn(json);
        List<T> list = scryfallAdapter.getList("http://test.com.br/", var);
        assertEquals(0, list.size());
    }

}