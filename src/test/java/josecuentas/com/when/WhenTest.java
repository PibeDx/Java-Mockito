package josecuentas.com.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.sql.Date;

import static josecuentas.com.when.WhenTest.ReturnFirstArgument.returnFirstArgument;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

/**
 * Created by jcuentast on 19/04/17.
 *
 * info: http://site.mockito.org/
 * info: https://dzone.com/refcardz/mockito
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WhenTest {

    private static final int TEST_NUMBER_OF_LEAFS = 4;
    private static final int WANTED_DATE = 3;
    private static final int VALUE_FOR_WANTED_ARGUMENT = 6;
    private static final int ANY_OTHER_DATE = 4;

    @Test
    public void shouldReturnGivenValue() {
        Flower flowerMock = mock(Flower.class);
        when(flowerMock.getNumberOfLeafs()).thenReturn(TEST_NUMBER_OF_LEAFS);
        int numberOfLeafs = flowerMock.getNumberOfLeafs();
        assertEquals(numberOfLeafs, TEST_NUMBER_OF_LEAFS);
    }

    @Test
    public void shouldReturnGivenValueUsingBDDSemantics() {
        //given
        Flower flowerMock = mock(Flower.class);
        given(flowerMock.getNumberOfLeafs()).willReturn(TEST_NUMBER_OF_LEAFS);
        //when
        int numberOfLeafs = flowerMock.getNumberOfLeafs();
        //then
        assertEquals(numberOfLeafs, TEST_NUMBER_OF_LEAFS);

    }

    @Test
    public void shouldMatchSimpleArgument() {
        WateringScheduler schedulerMock = mock(WateringScheduler.class);
        given(schedulerMock.getNumberOfPlantsScheduledOnDate(WANTED_DATE)).willReturn(VALUE_FOR_WANTED_ARGUMENT);
        int numberForWantedArgument = schedulerMock.getNumberOfPlantsScheduledOnDate(WANTED_DATE);
        int numberForAnyOtherArgument = schedulerMock.getNumberOfPlantsScheduledOnDate(ANY_OTHER_DATE);
        assertEquals(numberForWantedArgument, VALUE_FOR_WANTED_ARGUMENT);
        assertEquals(numberForAnyOtherArgument, 0);  //default value for int
    }

    @Test
    public void should(){
        PlantSearcherMock plantSearcherMock = mock(PlantSearcherMock.class);
        given(plantSearcherMock.smellyMethod(anyInt(), contains("asparag"), eq("red"))).willReturn(true);
        boolean resultado = plantSearcherMock.smellyMethod(0, "asparag", "red");

        assertEquals(resultado, true);
    }

    @Test
    public void matches() {
        WateringScheduler schedulerMock = mock(WateringScheduler.class);
        given(schedulerMock.getNumberOfPlantsScheduledOnDate(argThat(haveHourFieldEqualTo(1)))).willReturn(1);
        //with the util method to create a matcher
        int resultado = schedulerMock.getNumberOfPlantsScheduledOnDate(1);

        assertEquals(resultado, 1);

    }
    private ArgumentMatcher haveHourFieldEqualTo(final int hour) {
        return new ArgumentMatcher () {
            public boolean matches(Object argument) {
                return ((Integer) argument) == hour;
            }
        };
    }

    @Test
    public void shouldReturnLastDefinedValueConsistently() {
        WaterSource waterSource = mock(WaterSource.class);
        given(waterSource.getWaterPressure()).willReturn(3, 5);
        assertEquals(waterSource.getWaterPressure(), 3);
        assertEquals(waterSource.getWaterPressure(), 5);
        assertEquals(waterSource.getWaterPressure(), 5);
    }
    @Test(expected = WaterException.class)
    public void shouldStubVoidMethod() throws WaterException {
        WaterSource waterSourceMock = mock(WaterSource.class);
        doThrow(WaterException.class).when(waterSourceMock).doSelfCheck();
        //the same with BDD semantics
        //willThrow(WaterException.class).given(waterSourceMock).doSelfCheck();
        waterSourceMock.doSelfCheck();


        //exception expected
    }

    @Test
    public void testWillReturn() {
        WaterSource waterSourceMock = mock(WaterSource.class);
        willReturn(6).given(waterSourceMock).getWaterPressure();
        int numberOfLeafs = waterSourceMock.getWaterPressure();
        assertEquals(numberOfLeafs, 6);
    }


    @Test
    public void shouldReturnTheSameValue() {
        int TEST_NUMBER_OF_FLOWERS = 2;
        FlowerFilter filterMock = mock(FlowerFilter.class);
        given(filterMock.filterNoOfFlowers(anyInt())).will(returnFirstArgument());
        int filteredNoOfFlowers = filterMock.filterNoOfFlowers(TEST_NUMBER_OF_FLOWERS);
        assertEquals(filteredNoOfFlowers, TEST_NUMBER_OF_FLOWERS);
    }
    //reusable answer class
    public static class ReturnFirstArgument implements Answer<Object> {

        public Object answer(InvocationOnMock invocation) throws Throwable {
            Object[] arguments = invocation.getArguments();
            for (Object argument : arguments) {
                System.out.println("asd" + argument);
            }
            if (arguments.length == 0) {
                throw new MockitoException("...");
            }
            return arguments[0];
        }

        public static ReturnFirstArgument returnFirstArgument() {
            return new ReturnFirstArgument();
        }
    }

    @Test
    public void verifying_behavior() throws WaterException {
        WaterSource waterSourceMock =mock(WaterSource.class);
        waterSourceMock.doSelfCheck();
        verify(waterSourceMock).doSelfCheck();
    }

    @Test
    public void Verifying_Call_Order() throws WaterException {
        WaterSource waterSource1=mock(WaterSource.class);
        WaterSource waterSource2=mock(WaterSource.class);
        waterSource1.doSelfCheck();
        waterSource2.getWaterPressure();
        waterSource1.getWaterPressure();
        InOrder inOrder = inOrder (waterSource1,waterSource2);
        inOrder.verify(waterSource1).doSelfCheck();
        inOrder.verify(waterSource2).getWaterPressure();
        inOrder.verify(waterSource1).getWaterPressure();
    }

    @Test
    public void Verifying_With_Argument_Matching() {
        FlowerSearcherMock flowerSearcherMock = mock(FlowerSearcherMock.class);
        SearchCriteria searchCriteria = new SearchCriteria();
        //when
        flowerSearcherMock.findMatching(searchCriteria);
        //then
        ArgumentCaptor<SearchCriteria>captor= ArgumentCaptor.forClass(SearchCriteria.class);
        verify(flowerSearcherMock).findMatching(captor.capture());
        SearchCriteria usedSearchCriteria = captor.getValue();
        assertEquals(usedSearchCriteria.getColor(),"yellow");
        assertEquals(usedSearchCriteria.getNumberOfBuds(),3);
    }


    @Test
    public void publicvoidshouldFailForLateCall() throws WaterException {
        WaterSource waterSourceMock = mock(WaterSource.class);
        Thread t = waitAndCallSelfCheck(40,waterSourceMock);
        t.start();
        verify(waterSourceMock,never()).doSelfCheck();
        try{
            verify(waterSourceMock,timeout(20)).doSelfCheck();
            fail("verificationshouldfail");
        }catch(MockitoAssertionError e){
            //expected
        }
    }

    private Thread waitAndCallSelfCheck(final long i, final WaterSource waterSourceMock) {
        return new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    waterSourceMock.doSelfCheck();
                } catch (WaterException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}

