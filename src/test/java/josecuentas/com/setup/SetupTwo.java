package josecuentas.com.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by jcuentast on 19/04/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetupTwo {

    @Mock
    List mockedList;

    @Test
    public void testList() throws Exception {

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
