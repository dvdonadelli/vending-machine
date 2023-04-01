package datastructures.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private final Map<String, Integer> productsStock;

    private final Map<String, Double> productsPrices;

    public VendingMachine() {
        this.productsStock = new HashMap<>();
        this.productsPrices = new HashMap<>();
    }

    public Integer getStock(String product) {
        if (!productsStock.containsKey(product)) return -1;

        return productsStock.get(product);
    }

    public static class Builder {
        private final VendingMachine machine;

        public Builder() {
            this.machine = new VendingMachine();
        }

        public Builder addProduct(String name, int stock, double price) {
            machine.productsStock.put(name, stock);
            machine.productsPrices.put(name, price);
            return this;
        }

        public VendingMachine build() {
            return machine;
        }
    }

    public boolean buyItem(String selectedProduct, Double paymentAmount) {
        if (!productsStock.containsKey(selectedProduct))
            return false;

        if (!productsPrices.containsKey(selectedProduct))
            return false;

        int stock = productsStock.get(selectedProduct);
        double price = productsPrices.get(selectedProduct);

        if (stock <= 0 || paymentAmount < price)
            return false;

        productsStock.put(selectedProduct, stock - 1);
        return true;
    }


}