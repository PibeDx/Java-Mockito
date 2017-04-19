package josecuentas.com.setup;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by jcuentast on 19/04/17.
 */

public class SetupThree {

    List mockedList = mock(List.class);

    @Test
    public void testList() throws Exception {

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
