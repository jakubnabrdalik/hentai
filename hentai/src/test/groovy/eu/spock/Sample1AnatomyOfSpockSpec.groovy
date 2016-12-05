package eu.spock

import spock.lang.Ignore
import spock.lang.Specification

class Sample1AnatomyOfSpockSpec extends Specification {
    BigDecimal price = new BigDecimal(200)
    String productName = "Sanity injectors"

    def "should be readable"() {
        given:
            Transaction transaction = new Transaction(productName, price)

        when:
            transaction.perform()

        then:
            transaction.status == TransactionStatus.SUCCESSFUL
    }

    def "should be very short"() {
        expect:
            "WJUG is awesome!".contains("WJUG")
    }

    def "should have nice asserts built-in in groovy"() {
        given:
            List<String> todaysFlashtalks = ['Awaitility', 'Spock', 'Guice', 'JUnitParams', 'Fest Assert', 'Geb']

        expect:
            //todaysFlashtalks.each {}
            todaysFlashtalks.find { it == 'Spock' }
    }

    def "exception handling should be... handled"() {
        when:
            throw new IllegalArgumentException('dupa')
        then:
            thrown(IllegalArgumentException)
    }

    @Ignore
    def "will it pass if exception is thrown in given?"() {
        given:
            throw new IllegalArgumentException('dupa')
        when:
            true
        then:
            thrown(IllegalArgumentException)
    }

}
