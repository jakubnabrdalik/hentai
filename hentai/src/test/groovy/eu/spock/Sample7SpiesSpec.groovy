package eu.spock

import spock.lang.Specification

class Sample7SpiesSpec extends Specification {
    BigDecimal price = new BigDecimal(200)
    String productName = "Sanity injectors"

    def "spies should spy, but not intercept"() {
        given:
            TaxCalculator calculator = Spy(XTaxCalculator, constructorArgs: [0.1])
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax(price)
            transaction.tax == 20
    }

    def "unless James Bond, of course"() {
        given:
            TaxCalculator calculator = Spy(XTaxCalculator, constructorArgs: [0.1])
            Transaction transaction = new Transaction(calculator)
            calculator.calculateTax(price) >> BigDecimal.ZERO

        when:
            transaction.perform(productName, price)

        then:
            transaction.tax == 0
    }

}
