package josecuentas.com.setup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by jcuentast on 19/04/17.
 */
public class SetupOne {

    @Mock
    List mockedList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testList() throws Exception {

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
