package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.exception.ActionNegativeQuantityException;
import core.basesyntax.exception.ActionProductNotFoundException;
import core.basesyntax.model.ProductTransaction;

public class PurchaseHandler implements ActionHandler {
    @Override
    public void process(ProductDao productDao, ProductTransaction transaction) {
        String productName = transaction.getProduct();
        int quantity = productDao.getQuantity(productName).orElseThrow(() ->
                new ActionProductNotFoundException(String.format("Product %s not found in storage",
                        productName)));
        quantity -= transaction.getQuantity();
        if (quantity < 0) {
            throw new ActionNegativeQuantityException(
                    String.format("There's negative value in result of purchasing %s",
                            productName));
        }
        productDao.setQuantity(productName, quantity);
    }
}
