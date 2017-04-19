package josecuentas.com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by jcuentast on 19/04/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class HelloMockito {
    @Mock
    List mockedList;

    @Before
    public void setup() {
    }

    @Test
    public void testList() throws Exception {

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();


    }

    @Test
    public void testLinkedList() {

        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");

        System.out.println(mockedList.get(0));

        System.out.println(mockedList.get(999));
    }

}
