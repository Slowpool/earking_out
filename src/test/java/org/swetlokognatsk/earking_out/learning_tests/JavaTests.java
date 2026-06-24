package org.swetlokognatsk.earking_out.learning_tests;

import static org.junit.Assert.*;
import java.util.concurrent.Executor;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;
import javafx.collections.ObservableSet;
import scala.Int;

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
        } catch (IllegalArgumentException e) {
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
        var ring = new Ring() {
        };
        try {
            var bring = (Bring) ring;
            fail();
        } catch (ClassCastException e) {
        }
    }

    @Test
    public void switchTest1() {
        var day = Day.Mon;
        boolean value = doSwitch(day);
        assertTrue(value);
    }

    @Test
    public void switchTest2() {
        var day = Day.Thu;
        boolean value = doSwitch(day);
        assertFalse(value);
    }

    @Test
    public void switchTest3() {
        var day = Day.Sun;
        try {
            doSwitch(day);
            fail();
        } catch (RuntimeException e) {
        }

    }

    private boolean doSwitch(Day day) {
        return switch (day) {
        case Mon, Tue -> true;
        case Sun -> throw new RuntimeException();
        default -> false;
        };
    }

    @Test
    public void switchTest4() {
        int number1;
        var day = Day.Mon;
        var result = switch (day) {
        case Mon, Tue -> number1 = 1;
        case Sun -> throw new RuntimeException();
        default -> number1 = 2;
        };
        assertEquals(result, number1);

        int number2;
        switch (day) {
        case Mon, Tue -> number2 = 1;
        case Sun -> throw new RuntimeException();
        default -> number2 = 2;
        }
        assertEquals(number1, number2);
        assertEquals(1, number1);
        assertEquals(1, number2);
    }

    @Test
    public void switchTest5() {
        int number1;
        Day day = null;
        var result = switch (day) {
        case Mon, Tue, Wed, Thu, Fri, Sat, Sun -> 1;
        case null -> 2;
        default -> 3;
        };
        assertEquals(result, 2);
    }

    @Test
    public void returningPolymorphObjectViaGeneric() {
        var result = polymorphing();
    }

    private BaseClass polymorphing() {
        return new DerivedClass();
    }

    @Test
    public void oneMoreGenericsQuestion1() {
        var genericString1 = gettingTheValue1(new MyGenericString());
        assertEquals(MyGenericString.class.getName(), genericString1);

        var genericString2 = gettingTheValue2(new MyGenericString());
        assertEquals(MyGenericString.class.getName(), genericString2);

        var generic1 = gettingTheValue1(new MyGeneric<String>());
        assertEquals(MyGeneric.class.getName(), generic1);

        var generic2 = gettingTheValue2(new MyGeneric<String>());
        assertEquals(MyGeneric.class.getName(), generic2);
    }

    private String gettingTheValue1(MyGeneric<?> someClass) {
        return someClass.getClass().getName();
    }

    private <T extends MyGeneric<?>> String gettingTheValue2(T someClass) {
        return someClass.getClass().getName();
    }

    @Test
    public void oneMoreGenericsQuestion2() {
        var genericString1 = gettingTheValue3(new MyGenericString());
        assertEquals(MyGenericString.class, genericString1.getClass());

        var genericString2 = gettingTheValue4(new MyGenericString());
        assertEquals(MyGenericString.class, genericString2.getClass());

        var generic1 = gettingTheValue3(new MyGeneric<String>());
        assertEquals(MyGeneric.class, generic1.getClass());

        var generic2 = gettingTheValue4(new MyGeneric<String>());
        assertEquals(MyGeneric.class, generic2.getClass());
    }

    private MyGeneric<?> gettingTheValue3(MyGeneric<?> someClass) {
        return someClass;
    }

    private <T extends MyGeneric<?>> T gettingTheValue4(T someClass) {
        return someClass;
    }

    @Test
    public void polymorphismTest1() {
        Child child = new Child();
        assertEquals("child", child.foo(child));
        assertEquals("parent", child.parentFooViaSuper(child));
        assertEquals("parent", child.parentFooViaCast(child));
    }

    @Test
    public void toStringTest1() {
        assertEquals(String.valueOf(true), "true");
    }

    @Test
    public void invalidationListenerTest1() {
        var lol = "bazingalol";
        var result = Child.doSomething((firstArg, secondArg) -> {
            return firstArg + secondArg;
        });

        assertEquals(lol, result);
    }

    @Test
    public void stringTest1() {
        String string1 = "bazinga";

        var castedString1 = string1.toString();
        assertEquals(string1, castedString1);

        var castedString2 = (String) string1;
        assertEquals(string1, castedString2);

        var castedString3 = String.valueOf(string1);
        assertEquals(string1, castedString3);
    }

    @Test
    public void genericMethodWithGenericReturnType1() {
        var varObj = getGenericObj();
        Object objObj = getGenericObj();
        Parent parentObj = getGenericObj();
        Child childObj = getGenericObj();

        assertEquals(varObj.getClass().getName(), Child.class.getName());
        assertEquals(objObj.getClass().getName(), Child.class.getName());
        assertEquals(parentObj.getClass().getName(), Child.class.getName());
        assertEquals(childObj.getClass().getName(), Child.class.getName());
    }

    @Test
    public void genericMethodWithGenericReturnType2() {
        var varObj = getNotGenericObj();
        Object objObj = getNotGenericObj();
        Parent parentObj = getNotGenericObj();
        Child childObj = (Child) getNotGenericObj();

        assertEquals(varObj.getClass().getName(), Child.class.getName());
        assertEquals(objObj.getClass().getName(), Child.class.getName());
        assertEquals(parentObj.getClass().getName(), Child.class.getName());
        assertEquals(childObj.getClass().getName(), Child.class.getName());
    }

    private <P extends Parent> P getGenericObj() {
        return (P) new Child();
    }

    private Parent getNotGenericObj() {
        return new Child();
    }

    @Test
    public void enumToStringTest1() {
        var castedValue = Days.MONDAY.toString();
        var interpolatedValue = "" + Days.MONDAY;
        assertEquals("MONDAY", castedValue);
        assertEquals("MONDAY", interpolatedValue);
    }

    @Test
    public void staticVariableInheritanceTest1() {
        var fakeSolution = "bazinga";
        FakeAudioPerfectPitchSolutionGenerator.fakeSolution = fakeSolution;
        assertEquals(fakeSolution, FakeVisualPerfectPitchSolutionGenerator.fakeSolution);
    }

    @Test
    public void weirdoCast() {
        Object object = new Object();
        try {
            var byteObject = (Byte) object;
            fail();
        } catch (ClassCastException e) {
        }
        try {
            var mouseObject = (Mouse) object;
            fail();
        } catch (ClassCastException e) {
        }
        try {
            var observableList = (ObservableSet<Byte>) object;
            fail();
        } catch (ClassCastException e) {
        }
    }

    @Test
    public void weirdoCast2() {
        var book = new Book<String>();
        book.cover = "bazinga";

        Object objBook = book;
        Book<Byte> bookWithByteCover = (Book<Byte>) objBook;
        try {
            var x = bookWithByteCover.cover;
            fail();
        } catch (ClassCastException e) {
        }
    }

    @Test
    public void uncheckedCastCatching() {
        var book = new Book<String>();
        book.cover = "bazinga";

        Object objBook = book;
        try {
            Book<Byte> bookWithByteCover = (Book<Byte>) objBook;
        } catch (ClassCastException e) {

        }
    }

    @Test
    public void theMostWildThingIVeSeenTest() {

    }

    // protected static int test = 5;
    // public static final byte source = (byte)((byte)84 / test);
    // public static final byte dervied = (byte) (source / 1);
    // public static final byte result = dervied * 1;

    static final int first = 126;
    static final byte second = first + 1;

    @Test
    public void genericsTest5() {

    }

    public static <I extends Id, EF extends EntitiesFactory<? extends Entity<I>>> EF createFactory(final I id) {
        var factory = switch (id) {
        case PersonId i -> new PersonsFactory();
        case AnimalId i -> new AnimalsFactory();
        case RockId i -> new RocksFactory();
        default -> throw new IllegalArgumentException("unknown id: " + id);
        };
        return (EF) factory;
    }

    @Test
    public void nullTest() {
        Person person = new Person();
        setPersonToNull(person);
        assertNotEquals(null, person);
    }

    protected void setPersonToNull(Person person) {
        person = null;
    }

    @Test
    public void genericTest6() {
        getSomething();
    }

    protected Generic<Id> getSomething() {
        // // error
        // Finite finite = new Finite();
        // Generic<Id> casted = finite;
        
        // // error
        // Finite finite = new Finite();
        // Generic<Id> casted = (Generic<Id>) finite;

        // fine, though warning
        Generic<?> finite = new Finite();
        Generic<Id> casted = (Generic<Id>) finite;
        
        return casted;
    }
}

class Generic<T> {
}

class Finite extends Generic<PersonId> {
}

abstract class Id {
}

class PersonId extends AliveId {
}

abstract class RockId extends Id {
}

abstract class AliveId extends Id {
}



class AnimalId extends AliveId {
}


abstract class EntitiesFactory<E extends Entity<?>> {
    public abstract E create();
}

class PersonsFactory extends EntitiesFactory<Person> {
    public Person create() {
        return new Person();
    }
}

class AnimalsFactory extends EntitiesFactory<Animal> {
    public Animal create() {
        return new Animal();
    }
}

class RocksFactory extends EntitiesFactory<Rock> {
    public Rock create() {
        return new Rock();
    }
}

abstract class Entity<I extends Id> {

}

abstract class AliveEntity<I extends AliveId> {
}

class Person extends Entity<PersonId> {
}

class Animal extends Entity<AnimalId> {
}

class Rock extends Entity<RockId> {
}

class Parent {
    public String foo(Parent parent) {
        return "parent";
    }
}

class Child extends Parent {
    public String foo(Child child) {
        return "child";
    }

    public String parentFooViaSuper(Child child) {
        return super.foo(child);
    }

    public String parentFooViaCast(Child child) {
        return ((Parent) this).foo(child);
    }

    public static String doSomething(CustomInvalidationListener listener) {
        return listener.something("bazinga", "lol");
    }
}

@FunctionalInterface
interface CustomInvalidationListener {
    public String something(String firstArg, String secondArg);
}

enum Days {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

interface Feedable {
    default void doSomething() {
        var b = 3;
    }
}

class Mouse implements Feedable {
}

class Book<Cover> {
    public Cover cover;
}

abstract class Person2 {
    public abstract void doSomething();
}

interface Doinger {
    default void doSomething() {
    }
}

class John extends Person implements Doinger {
    // you'll get fired by compiler if this method is absent (yes, from your job)
    public void doSomething() {

    }
}