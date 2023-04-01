package datastructures.vendingmachine;

import datastructures.vendingmachine.util.Pair;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VendingMachineTest {

    private VendingMachine vendingMachine;

    @BeforeAll
    void setUp() {
        vendingMachine = new VendingMachine.Builder()
                .addProduct("Coke", 10, 1.50)
                .addProduct("Pepsi", 5, 2.00)
                .addProduct("Sprite", 7, 2.25)
                .addProduct("Keyboard", 3, 80.00)
                .addProduct("Laptop", 0, 500.00)
                .addProduct("Pen", 1, 0.10)
                .addProduct("Coin", 2, 0.00)
                .build();

    }

    @TestFactory
    Stream<DynamicTest> testBuyExistentItemsWithPaymentAmountEnough() {
        List<Pair<String, Double>> arguments = Arrays.asList(
                Pair.of("Coke", 1.50),
                Pair.of("Pepsi", 2.00),
                Pair.of("Sprite", 2.50),
                Pair.of("Keyboard", 80.00),
                Pair.of("Coin", 100.00)
        );

        return arguments.stream()
                .map(args -> DynamicTest.dynamicTest("Buying " + args.first() + " with $" + args.second(),
                        () -> assertTrue(vendingMachine.buyItem(args.first(), args.second())))
                );
    }

    @TestFactory
    Stream<DynamicTest> testBuyExistentItemsWithNotEnoughPayment() {
        List<Pair<String, Double>> arguments = Arrays.asList(
                Pair.of("Coke", 1.25),
                Pair.of("Pepsi", 1.98),
                Pair.of("Sprite", 1.99),
                Pair.of("Keyboard", 15.00),
                Pair.of("Coin", -0.01)
        );

        return arguments.stream()
                .map(args -> DynamicTest.dynamicTest("Buying " + args.first() + " with $" + args.second(),
                        () -> assertFalse(vendingMachine.buyItem(args.first(), args.second())))
                );
    }

    @TestFactory
    Stream<DynamicTest> testBuyInexistentItemsWithRandomPaymentAmount() {
        Random random = new Random();
        List<Pair<String, Double>> arguments = Arrays.asList(
                Pair.of("Dolly", random.nextDouble()),
                Pair.of("Guarana", random.nextDouble()),
                Pair.of("iPhone", random.nextDouble()),
                Pair.of("Mouse", random.nextDouble())
        );

        return arguments.stream()
                .map(args -> DynamicTest.dynamicTest("Buying " + args.first() + " with $" + args.second(),
                        () -> assertFalse(vendingMachine.buyItem(args.first(), args.second())))
                );
    }

    @Test
    void testBuySameItemTwice() {
        assertTrue(vendingMachine.buyItem("Pen", 0.11));
        assertFalse(vendingMachine.buyItem("Pen", 0.11));
    }
}