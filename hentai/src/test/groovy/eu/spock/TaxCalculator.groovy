package eu.spock

interface TaxCalculator {
    BigDecimal calculateTax(BigDecimal price)
}

class ZeroTaxCalculator implements TaxCalculator {
    BigDecimal calculateTax(BigDecimal price) {
        return BigDecimal.ZERO
    }

    static String iSuckAndIWriteStaticMethods() {
        return "I sucks"
    }
}

class XTaxCalculator implements TaxCalculator {
    BigDecimal multiplier

    XTaxCalculator(BigDecimal multiplier) {
        this.multiplier = multiplier
    }

    BigDecimal calculateTax(BigDecimal price) {
        return price.multiply(multiplier)
    }
}
