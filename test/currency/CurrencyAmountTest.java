package currency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyAmountTest {

    private static final Currency CANADA_DOLLAR_CURRENCY = Currency.getInstance("CAD");
    private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");
    private static final Currency US_DOLLAR_CURRENCY = Currency.getInstance("USD");
    private static final Currency YEN_CURRENCY = Currency.getInstance("JPY");

    @Test
    public void testGetAmountInCents() {
        System.out.println("getAmountInCents");
        long expected = 43075L;
        CurrencyAmount testAmount = new CurrencyAmount(expected, US_DOLLAR_CURRENCY);
        long actual = testAmount.getAmountInCents();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUnitAmount() {
        System.out.println("getUnitAmount");
        long amount = 10341L;
        long expected = (long) Math.floor(amount/100);
        CurrencyAmount testAmount = new CurrencyAmount(amount, CANADA_DOLLAR_CURRENCY);
        long actual = testAmount.getUnitAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetChangeAmount() {
        System.out.println("getChangeAmount");
        long amount = 10341L;
        long expected = amount % 100;
        CurrencyAmount testAmount = new CurrencyAmount(amount, CANADA_DOLLAR_CURRENCY);
        long actual = testAmount.getChangeAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrency() {
        System.out.println("getCurrency");
        CurrencyAmount testAmount = new CurrencyAmount(1899L, EURO_CURRENCY);
        Currency actual = testAmount.getCurrency();
        assertEquals(EURO_CURRENCY, actual);
    }

    @Test
    public void testPlus() {
        System.out.println("plus");
        CurrencyAmount testAddendA = new CurrencyAmount(4890398L, US_DOLLAR_CURRENCY);
        CurrencyAmount testAddendB = new CurrencyAmount(4899L, US_DOLLAR_CURRENCY);
        CurrencyAmount expected = new CurrencyAmount(4895297L, US_DOLLAR_CURRENCY);
        CurrencyAmount actual = testAddendA.plus(testAddendB);
        assertEquals(expected, actual);
    }

    @Test
    public void testPlusOnDiffCurrencies() {
        CurrencyAmount testAddendA = new CurrencyAmount(100000, CANADA_DOLLAR_CURRENCY);
        CurrencyAmount testAddendB = new CurrencyAmount(25000000, YEN_CURRENCY);
        try {
            CurrencyAmount result = testAddendA.plus(testAddendB);
            String failMsg = "Trying to add " + testAddendA.toString() + " to " + testAddendB.toString() + " should have caused an exception, not given result " + result.toString();
            fail(failMsg);
        } catch (CurrencyConversionNeededException currConvNeedExc) {
            System.out.println("Trying to add " + testAddendA.toString() + " to " + testAddendB.toString() + " correctly triggered CurrencyConversionNeededException");
            System.out.println("\"" + currConvNeedExc.getMessage() + "\"");
        } catch (UnsupportedOperationException unsupOperExc) {
            System.out.println("UnsupportedOperationException is perhaps an adequate exception to throw for trying to add " + testAddendA.toString() + " to " + testAddendB.toString());
            System.out.println("\"" + unsupOperExc.getMessage() + "\"");
        } catch (Exception exc) {
            String failMsg = exc.getClass().getName() + "is the wrong exception to throw for trying to add " + testAddendA.toString() + " to " + testAddendB.toString();
            fail(failMsg);
        }
    }

    @Test
    public void testMinus() {
        System.out.println("minus");
        CurrencyAmount testMinuend = new CurrencyAmount(4890398L, US_DOLLAR_CURRENCY);
        CurrencyAmount testSubtrahend = new CurrencyAmount(4899L, US_DOLLAR_CURRENCY);
        CurrencyAmount expected = new CurrencyAmount(4885499L, US_DOLLAR_CURRENCY);
        CurrencyAmount actual = testMinuend.minus(testSubtrahend);
        assertEquals(expected, actual);
    }

    @Test
    public void testMinusOnDiffCurrencies() {
        CurrencyAmount testMinuend = new CurrencyAmount(100000, CANADA_DOLLAR_CURRENCY);
        CurrencyAmount testSubtrahend = new CurrencyAmount(25000000, YEN_CURRENCY);
        try {
            CurrencyAmount result = testMinuend.minus(testSubtrahend);
            String failMsg = "Trying to subtract " + testSubtrahend.toString() + " from " + testMinuend.toString() + " should have caused an exception, not given result " + result.toString();
            fail(failMsg);
        } catch (CurrencyConversionNeededException currConvNeedExc) {
            System.out.println("Trying to subtract " + testSubtrahend.toString() + " from " + testMinuend.toString() + " correctly triggered CurrencyConversionNeededException");
            System.out.println("\"" + currConvNeedExc.getMessage() + "\"");
        } catch (UnsupportedOperationException unsupOperExc) {
            System.out.println("UnsupportedOperationException is perhaps an adequate exception to throw for trying to subtract " + testSubtrahend.toString() + " from " + testMinuend.toString());
            System.out.println("\"" + unsupOperExc.getMessage() + "\"");
        } catch (Exception exc) {
            String failMsg = exc.getClass().getName() + "is the wrong exception to throw for trying to subtract " + testSubtrahend.toString() + " from " + testMinuend.toString();
            fail(failMsg);
        }
    }

    @Test
    public void testTimes() {
        System.out.println("times");
        CurrencyAmount testMultiplicand = new CurrencyAmount(5399L, US_DOLLAR_CURRENCY);
        short testMultiplier = 3;
        CurrencyAmount expected = new CurrencyAmount(16197L, US_DOLLAR_CURRENCY);
        CurrencyAmount actual = testMultiplicand.times(testMultiplier);
        assertEquals(expected, actual);
    }

    @Test
    public void testDivides() {
        System.out.println("divides");
        CurrencyAmount testDividend = new CurrencyAmount(32200L, CANADA_DOLLAR_CURRENCY);
        CurrencyAmount expected = new CurrencyAmount(10733, CANADA_DOLLAR_CURRENCY);
        CurrencyAmount actual = testDividend.divides((short) 3);
        assertEquals(expected, actual);
    }

    @Test
    public void testDivideByZero() {
        CurrencyAmount testDividend = new CurrencyAmount(58347L, US_DOLLAR_CURRENCY);
        short testDivisor = 0;
        try {
            CurrencyAmount result = testDividend.divides(testDivisor);
            String failMsg = "Trying to divide " + testDividend.toString() + " by 0 should have caused an exception, not given result " + result.toString();
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to divide " + testDividend.toString() + " by 0 correctly triggered IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (ArithmeticException ae) {
            System.out.println("ArithmeticException is adequate for trying to divide " + testDividend.toString() + " by 0");
            System.out.println("\"" + ae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() + " is the wrong exception to throw for trying to divide " + testDividend.toString() + " by 0";
            fail(failMsg);
        }
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        CurrencyAmount testAmount = new CurrencyAmount(4399L, US_DOLLAR_CURRENCY);
        String expected = "$43.99";
        String actual = testAmount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringCentAmountsSingleDigit() {
        CurrencyAmount testAmount;
        String expected, actual;
        for (int i = 0; i < 10; i++) {
            testAmount = new CurrencyAmount(i, US_DOLLAR_CURRENCY);
            expected = "$0.0" + i;
            actual = testAmount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testToStringCentAmountsTwoDigits() {
        CurrencyAmount testAmount;
        String expected, actual;
        for (int i = 10; i < 100; i++) {
            testAmount = new CurrencyAmount(i, US_DOLLAR_CURRENCY);
            expected = "$0." + i;
            actual = testAmount.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testToStringNegativeAmount() {
        CurrencyAmount testAmount = new CurrencyAmount(-380, US_DOLLAR_CURRENCY);
        String expected = "$-3.80";
        String actual = testAmount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        CurrencyAmount testAmountA = new CurrencyAmount(3272500L, US_DOLLAR_CURRENCY);
        CurrencyAmount testAmountB = new CurrencyAmount(3272500L, US_DOLLAR_CURRENCY);
        String assertionMessage = testAmountA.toString() + " should be found to be equal to " + testAmountB.toString();
        assertEquals(assertionMessage, testAmountA, testAmountB);
        assertEquals(assertionMessage, testAmountB, testAmountA);
        testAmountB = new CurrencyAmount(3272500L, EURO_CURRENCY);
        assertionMessage = testAmountA.toString() + " should not be found to be equal to " + testAmountB.toString();
        assertNotEquals(assertionMessage, testAmountA, testAmountB);
        testAmountB = new CurrencyAmount(3272499L, US_DOLLAR_CURRENCY);
        assertionMessage = testAmountA.toString() + " should not be found to be equal to " + testAmountB.toString();
        assertNotEquals(assertionMessage, testAmountA, testAmountB);
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        CurrencyAmount dollarAmount = new CurrencyAmount(8987L, US_DOLLAR_CURRENCY);
        CurrencyAmount oneDollarCent = new CurrencyAmount(1L, US_DOLLAR_CURRENCY);
        CurrencyAmount euroAmount = new CurrencyAmount(8987L, EURO_CURRENCY);
        CurrencyAmount oneEuroCent = new CurrencyAmount(1L, EURO_CURRENCY);
        CurrencyAmount yenAmount = new CurrencyAmount(8987L, YEN_CURRENCY);
        CurrencyAmount oneYen = new CurrencyAmount(1L, YEN_CURRENCY);
        HashSet<Integer> hashSet = new HashSet<>();
        int expectedSize = 0;
        for (int i = 0; i < 100; i++) {
            hashSet.add(dollarAmount.hashCode());
            dollarAmount = dollarAmount.plus(oneDollarCent);
            hashSet.add(euroAmount.hashCode());
            euroAmount = euroAmount.plus(oneEuroCent);
            hashSet.add(yenAmount.hashCode());
            yenAmount = yenAmount.plus(oneYen);
            expectedSize += 3;
        }
        String assertionMessage = "Set of hash codes should have " + expectedSize + " elements";
        assertEquals(assertionMessage, expectedSize, hashSet.size());
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        CurrencyAmount debtAmount = new CurrencyAmount(-400053L, US_DOLLAR_CURRENCY);
        CurrencyAmount zeroAmount = new CurrencyAmount(0L, US_DOLLAR_CURRENCY);
        CurrencyAmount goodAmount = new CurrencyAmount(598047325L, US_DOLLAR_CURRENCY);
        int expected = -1;
        int actual = debtAmount.compareTo(zeroAmount);
        assertEquals(expected, actual);
        actual = zeroAmount.compareTo(goodAmount);
        assertEquals(expected, actual);
        expected = 0;
        CurrencyAmount sameAmount = new CurrencyAmount(-400053L, US_DOLLAR_CURRENCY);
        actual = debtAmount.compareTo(sameAmount);
        assertEquals(expected, actual);
        sameAmount = new CurrencyAmount(0L, US_DOLLAR_CURRENCY);
        actual = zeroAmount.compareTo(sameAmount);
        assertEquals(expected, actual);
        sameAmount = new CurrencyAmount(598047325L, US_DOLLAR_CURRENCY);
        actual = goodAmount.compareTo(sameAmount);
        assertEquals(expected, actual);
        expected = 1;
        actual = zeroAmount.compareTo(debtAmount);
        assertEquals(expected, actual);
        actual = goodAmount.compareTo(zeroAmount);
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareToThroughCollectionSort() {
        CurrencyAmount firstAmount = new CurrencyAmount(389L, EURO_CURRENCY);
        CurrencyAmount negativeAmount = new CurrencyAmount(-4500, EURO_CURRENCY);
        CurrencyAmount secondAmount = new CurrencyAmount(1899L, EURO_CURRENCY);
        CurrencyAmount thirdAmount = new CurrencyAmount(500044873L, EURO_CURRENCY);
        CurrencyAmount zeroethAmount = new CurrencyAmount(0L, EURO_CURRENCY);
        List<CurrencyAmount> toBeSortedList = new ArrayList<>();
        toBeSortedList.add(firstAmount);
        toBeSortedList.add(negativeAmount);
        toBeSortedList.add(secondAmount);
        toBeSortedList.add(thirdAmount);
        toBeSortedList.add(zeroethAmount);
        List<CurrencyAmount> expectedList = new ArrayList<>();
        expectedList.add(negativeAmount);
        expectedList.add(zeroethAmount);
        expectedList.add(firstAmount);
        expectedList.add(secondAmount);
        expectedList.add(thirdAmount);
        Collections.sort(toBeSortedList);
        assertEquals(expectedList, toBeSortedList);
    }

    @Test
    public void testCompareToDiffCurrency() {
        CurrencyAmount dollarAmount = new CurrencyAmount(599L, US_DOLLAR_CURRENCY);
        CurrencyAmount yenAmount = new CurrencyAmount(599L, YEN_CURRENCY);
        try {
            int result = dollarAmount.compareTo(yenAmount);
            String failMessage = "Trying to compare " + dollarAmount.toString() + " to " + yenAmount.toString() + " should have caused an exception, not given result " + result;
            fail(failMessage);
        } catch (CurrencyConversionNeededException currConvNeedExc) {
            System.out.println("Trying to compare " + dollarAmount.toString() + " to " + yenAmount.toString() + " correctly triggered CurrencyConversionNeededException");
            System.out.println("\"" + currConvNeedExc.getMessage() + "\"");
        } catch (Exception e) {
            String failMessage = e.getClass().getName() + " is the wrong exception to throw for trying to compare " + dollarAmount.toString() + " to " + yenAmount.toString();
            fail(failMessage);
        }
    }

    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        long cents = 50389L;
        CurrencyAmount oneItemConstr = new CurrencyAmount(cents);
        CurrencyAmount twoItemConstr = new CurrencyAmount(cents, US_DOLLAR_CURRENCY);
        assertEquals(oneItemConstr, twoItemConstr);
    }

}