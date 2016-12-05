package eu.spock

class Transaction {
    TaxCalculator taxCalculator

    TransactionStatus status
    BigDecimal price = BigDecimal.ZERO
    String productName
    BigDecimal tax

    Transaction() {
        taxCalculator = new ZeroTaxCalculator()
    }

    Transaction(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator
    }

    Transaction(String productName, BigDecimal price) {
        this.price = price
        this.productName = productName
        status = TransactionStatus.UNRESOLVED
    }

    void perform() {
        status = TransactionStatus.SUCCESSFUL
    }


    void perform(String productName, BigDecimal price) {
        this.productName = productName
        this.price = price
        this.tax = taxCalculator.calculateTax(price)
    }

    String doISuck() {
        return ZeroTaxCalculator.iSuckAndIWriteStaticMethods()
    }
}

enum TransactionStatus {
    SUCCESSFUL,
    FAILURE,
    UNRESOLVED
}
