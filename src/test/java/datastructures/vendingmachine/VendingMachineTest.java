package datastructures.vendingmachine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInstance;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

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
                .build();

    }

    @TestFactory
    Stream<DynamicTest> testBuyItem() {
        return Stream.of(
                dynamicTest("Buy Coke with exact amount", () -> {
                    assertTrue(vendingMachine.buyItem("Coke", 1.50));
                    assertEquals(9, vendingMachine.getStock("Coke"));
                }),
                dynamicTest("Buy Pepsi with not enough amount", () -> {
                    assertFalse(vendingMachine.buyItem("Pepsi", 1.00));
                    assertEquals(vendingMachine.getStock("Pepsi"), vendingMachine.getStock("Pepsi"));
                })
        );
    }

}