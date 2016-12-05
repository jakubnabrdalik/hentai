package eu.spock

import spock.lang.Ignore
import spock.lang.Specification

class Sample6MockingSpec extends Specification {
    BigDecimal price = new BigDecimal(200)
    String productName = "Sanity injectors"

    TaxCalculator calculator = Mock(TaxCalculator)

    def "stubbing should work"() {
        given:
            TaxCalculator calculator = Stub(TaxCalculator)
            Transaction transaction = new Transaction(calculator)
            calculator.calculateTax(price) >> BigDecimal.ONE

        when:
            transaction.perform(productName, price)

        then:
            transaction.tax == BigDecimal.ONE
    }

    def "stubbing should work with subsequent calls"() {
        given:
            TaxCalculator calculator = Stub(TaxCalculator)
            Transaction transaction = new Transaction(calculator)
            calculator.calculateTax(price) >>> [BigDecimal.ONE,
                                                BigDecimal.TEN,
                                                {throw new RuntimeException()}]

        when:
            transaction.perform(productName, price)

        then:
            transaction.tax == BigDecimal.ONE

        when:
            transaction.perform(productName, price)

        then:
            transaction.tax == BigDecimal.TEN

        when:
            transaction.perform(productName, price)

        then:
            thrown(RuntimeException)
    }

    def "stubs and mocks can be defined at creation"() {
        given:
            TaxCalculator calculator = Stub(TaxCalculator) {
                calculateTax(price) >> BigDecimal.ONE
            }
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            transaction.tax == BigDecimal.ONE
    }


    def "mocking should work"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax(price)
    }

    def "stubbing and mocking should work together"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax(price) >> BigDecimal.ONE
            transaction.tax == BigDecimal.ONE
    }

    @Ignore
    def "errors for mocking should be nice"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            2 * calculator.calculateTax(price)
    }

    def "expectations can be in range"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            (0..2) * calculator.calculateTax(price)
    }

    def "you can check method parameters"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax({it == 200})
    }

    def "if you don't care about the parameter"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax(_ as BigDecimal)
    }

    @Ignore
    def "if you don't care about the parameter even more"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax(_)      //whatever
            1 * calculator.calculateTax(!price) //not this price
            1 * calculator.calculateTax(*_)     // any argument list
            1 * calculator.calculateTax(!null)  // any non-null argument
            1 * calculator.calculateTax({it.toString() == "200"})
    }

    @Ignore
    def "when you are a bad, bad person"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            0 * _._
    }

    def "when the order of calls matter"() {
        given:
            Transaction transaction = new Transaction(calculator)

        when:
            transaction.perform(productName, price)

        then:
            1 * calculator.calculateTax(price)

        then:
            0 * _._
    }

}
