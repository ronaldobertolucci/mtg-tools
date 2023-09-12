package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class FindSetServiceTest {

    @Mock
    private CollectionService collectionService;
    private FindSetService findSetService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        findSetService = new FindSetService(collectionService);
    }

    @ParameterizedTest
    @NullSource
    void testNullSet(Set set) {
        assertNull(findSetService.find(set));
    }

    @Test
    void testNoResultSet() {
        Mockito.when(collectionService.findSetByCode("ttt")).thenReturn(null);
        Set set = new Set("test", "ttt", "core", 1, false, "test.com", null);
        assertSame(set, findSetService.find(set));
    }

    @Test
    void testResultSet() {
        Mockito.when(collectionService.findSetByCode("ttt"))
                .thenReturn(new Set("test", "ttt", "core", 1, false, "test.com", null));
        Set set = new Set("test", "ttt", "core", 1, false, "test.com", null);
        assertNotSame(set, findSetService.find(set));
    }

    @ParameterizedTest
    @NullSource
    void testNullSetWithSetCode(String set) {
        assertNull(findSetService.find(set));
    }

    @Test
    void testNoResultSetWithSetCode() {
        Mockito.when(collectionService.findSetByCode("ttt")).thenReturn(null);
        Set set = new Set("test", "ttt", "core", 1, false, "test.com", null);
        assertNull(findSetService.find("ttt"));
    }

    @Test
    void testResultSetWithSetCode() {
        Mockito.when(collectionService.findSetByCode("ttt"))
                .thenReturn(new Set("test", "ttt", "core", 1, false, "test.com", null));
        Set set = new Set("test", "ttt", "core", 1, false, "test.com", null);
        assertNotSame(set, findSetService.find("ttt"));
    }

}