package org.swetlokognatsk.earking_out.learning_tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

import org.junit.*;

public class JavaTests {
    @Test
    public void genericTest1() {
        Foo foo = new Foo();
        var contains = foo.list.get(0).contains("test");
        assertTrue(contains);
    }

    @Test
    public void diamondMethodParameterInferringTest1() {
        acceptsBarString(new Bar<>());
    }

    public void acceptsBarString(Bar<String> bar) {
        bar.variable = "test";
        assertEquals(bar.variable.getClass(), String.class);
    }

    @Test
    public void diamondMethodParameterInferringTest2() {
        var bar = acceptsBarStringAndReturnsIt(new Bar<>());
        assertEquals(bar.variable.getClass(), String.class);
    }

    public Bar<String> acceptsBarStringAndReturnsIt(Bar<String> bar) {
        bar.variable = "test";
        return bar;
    }

    @Test
    public void diamondMethodParameterInferringTest3() {
        var bar = acceptsBarGeneric(new Bar<>());
        assertEquals(Object.class, bar.variable.getClass());
    }

    public <T extends Object> Bar<T> acceptsBarGeneric(Bar<T> bar) {
        // compile error
        // bar.variable = new T();
        bar.variable = (T) new Object();
        return bar;
    }

    @Test
    public void scalaBigDecimalSum() {
        scala.math.BigDecimal scalaBigDecimal1 = scala.math.BigDecimal.valueOf(1);
        scala.math.BigDecimal scalaBigDecimal2 = scala.math.BigDecimal.valueOf(2);
        var sum = scalaBigDecimal1.$plus(scalaBigDecimal2);
        assertEquals(scala.math.BigDecimal.valueOf(3), sum);
    }

    @Test
    public void javaBigDecimalSum() {
        java.math.BigDecimal javaBigDecimal1 = java.math.BigDecimal.valueOf(1);
        java.math.BigDecimal javaBigDecimal2 = java.math.BigDecimal.valueOf(2);
        var sum = javaBigDecimal1.add(javaBigDecimal2);
        assertNotEquals(scala.math.BigDecimal.valueOf(3), sum);
        assertEquals(scala.math.BigDecimal.valueOf(3).intValue(), sum.intValue());
    }

    @Test
    public void recordsEqualityTest() {
        double length = 10;
        double width = 20;
        var rectangle1 = new Rectangle(width, length);
        var rectangle2 = new Rectangle(width, length);
        assertTrue(rectangle1.equals(rectangle2));
        assertTrue(rectangle2.equals(rectangle1));
        assertEquals(rectangle2, rectangle1);
        assertEquals(rectangle1, rectangle2);
    }

    @Test
    public void recordToStringTest() {
        double someNumber = 1.0D;
        var rectangle = new Rectangle(someNumber, someNumber);
        assertEquals("Rectangle[width=1.0, length=1.0]", rectangle.toString());
    }

    @Test
    public void recordCompactConstructorTest() {
        try {
            var rectangle = new Rectangle(-1, 1);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("width cannot be negative", e.getMessage());
        }
    }

    @Test
    public void recordsInheritingTest() {
        assertTrue(java.lang.Record.class.isAssignableFrom(Rectangle.class));
        assertFalse(java.lang.Record.class.isAssignableFrom(Foo.class));
    }

    @Test
    public void instanceInitializer() {
        var obj = new InstanceInitializer();
        assertEquals(obj.value, 1);
    }

    @Test
    public void rawTypeTest() {
        var rawType = new SomeGenericClass();
        var variable = rawType.variable;
    }

    void toCommandHandler(Executor executor) {
        var _this = this;
        // Function test = () -> executor.supplyAsync(() -> _this.doSomething());
    }

    @Test
    public void javaTypeHole() {
        var ring = new Ring() {};
        try {
            var bring = (Bring)ring;
            fail();
        }
        catch (ClassCastException e) {
        }
    }
}

