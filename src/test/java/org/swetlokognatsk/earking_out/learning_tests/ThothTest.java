package org.swetlokognatsk.earking_out.learning_tests;

import org.junit.Test;

import fr.maif.eventsourcing.AbstractState;
import fr.maif.eventsourcing.Unit;
import fr.maif.reactor.eventsourcing.InMemoryEventStore;
import io.vavr.Tuple0;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Either.Left;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThothTest {
    @Test
    public void testTest() throws Exception {
        // var commandHandler = new BankCommandHandler();
        // var context = new TxCtx();
        // var state = Option.of(new Account());
        // var command = new OpenAccount();
        // commandHandler.handleCommand(context, state, command);
    }

    @Test
    public void UnitTest() {
        var unit1 = Unit.unit();
        Unit.unit = null;
        var unit2 = Unit.unit();
        assertNotEquals(unit1, unit2);
    }

    @Test
    public void collectionTest() {
        Collection<Object> collection = new ArrayDeque<Object>();
        putArrayToCollection(new Integer[]{1, 2, 3}, collection);
        assertEquals(3, collection.size());
    }

    <T> void putArrayToCollection(T[] array, Collection<T> collection) {
        for (T object : array) {
            collection.add(object);
        }
    }

    @Test
    public void genericMethodTest() {
        var one = genericMethod(1);
        var string = genericMethod("string");
        assertEquals(one.getClass(), Integer.class);
        assertEquals(string.getClass(), String.class);
    }

    <T> T genericMethod(T value) {
        return value;
    }

    @Test
    public void genericMethodTest2() {
        Integer number = genericMethod2(1, 1);
        assertEquals(number.getClass(), Integer.class);

        var varBazinga = genericMethod2(1, "bazinga");
        assertEquals(varBazinga.getClass(), String.class);

        Serializable serializableBazinga = genericMethod2(1, "bazinga");
        assertEquals(serializableBazinga.getClass(), String.class);

        Object objectBazinga = genericMethod2(1, "bazinga");
        assertEquals(objectBazinga.getClass(), String.class);
    }

    <T> T genericMethod2(T value1, T value2) {
        return value2;
    }

    @Test
    public void genericMethodTest3() {
        var list1 = List.of(1, 5);
        boolean result1 = containsAll1(list1);

        var list2 = List.of("bazinga", 5);
        boolean result2 = containsAll1(list2);

        var list3 = List.of("bazinga", "bazinga");
        boolean result3 = containsAll1(list3);

        var list4 = List.of(1, 5);
        boolean result4 = containsAll2(list4);

        Set<?> list5 = Set.of("bazinga", 5);
        boolean result5 = containsAll2(list5);

        var list6 = List.of("bazinga", "bazinga");
        boolean result6 = containsAll2(list6);
    }

    boolean containsAll1(Collection<?> c) {
        return true;
    }

    <T> boolean containsAll2(Collection<T> c) {
        return true;
    }

    @Test
    public void eitherLeftTest() {
        Either<String, Integer> either = Either.left("bazinga");
        String left = either.getLeft();
        assertEquals("bazinga", left);
        assertTrue(either.isLeft());
    }

    @Test
    public void eitherRightTest() {
        Either<String, Integer> either = Either.right(1);
        Integer right = either.get();
        assertEquals(1, (int)right);
        assertTrue(either.isRight());
    }

    @Test
    public void Tuple0Test() {
        Tuple0 tuple0 = Tuple0.instance();
        tuple0.append(1937);
        var seq = tuple0.toSeq();
        assertEquals(0, seq.size());
        try {
            seq.get(0);
            fail("seq is expected to not have elements");
        }
        catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void finalLocalVariableTest() {
        var three = sumFirstNumberAsFinalAndSecondNumber(1, 2);
        assertEquals(1 + 2, three);
        var greatNumber = sumFirstNumberAsFinalAndSecondNumber(1937, 4);
        assertEquals(1937 + 4, greatNumber);
    }

    int sumFirstNumberAsFinalAndSecondNumber(int number1, int number2) {
        final int finalNumber = number1;
        // finalNumber += 5; // compile error
        return finalNumber + number2;
    }

    @Test
    public void finalStaticLocalVariableTest() {
        var one = getStaticFinalLocalVariable(1);
        assertEquals(1, one);
        var two = getStaticFinalLocalVariable(2);
        assertEquals(2, two);
    }

    static int getStaticFinalLocalVariable(int number) {
        final int finalNumber = number;
        return finalNumber;
    }

    @Test
    public void optionMappingImmutabilityTest() {
        var state = Option.of(new Account());
        var account = state.get();
        var sourceAccountId = "1937";
        account.id = sourceAccountId;
        assertEquals(sourceAccountId, account.id);

        var state2 = state.map(st -> {
            return st;
        });
        assertTrue(state.equals(state2));
    }

    // @Test
    // TODO
    public void omitGenericTypeSpecifyingTest() throws Exception {
        var state = new TxCtx();
        var genericVariable = state.genericVariable;
        throw new Exception();
    }

    @Test
    public void weirdGenericTest() {
        var weirdObject = new WeirdGeneric<WeirdAccount>();
        weirdObject.something = new WeirdAccount();
    }

    @Test
    public void weirdMethodCallNotSpecifyingGenerics() {
        var eventStore = InMemoryEventStore.create();
        
    }
}
