package eu.spock

import spock.lang.Specification

class Sample9GlobalMocksSpec extends Specification {
    BigDecimal price = new BigDecimal(200)
    String productName = "Sanity injectors"

    def "mocking static methods? No problem!"() {
        given:
            GroovyMock(ZeroTaxCalculator, global: true)
            Transaction transaction = new Transaction()
            ZeroTaxCalculator.iSuckAndIWriteStaticMethods() >> "nope"

        when:
            String outcome = transaction.doISuck()

        then:
            outcome == "nope"
    }
}
