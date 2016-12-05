package eu.spock
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class Sample8PoolingSpec extends Specification {
    String productName = "Initial value"
    PollingConditions conditions = new PollingConditions(timeout: 10)

    def "should mock all instances"() {
        when:
            Thread.start {
                sleep(1000)
                productName = "first value"
                sleep(5000)
                productName = "second value"
            }

        then:
            conditions.within(2) {
                assert productName == "first value"
            }

            conditions.eventually {
                assert productName == "second value"
            }
    }

    //moar!? AsyncConditions, BlockingVariable
}
