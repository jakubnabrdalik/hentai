package eu.spock

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class Sample3ReadableErrorsSpec extends Specification {
    def "should have readable error messages"() {
        expect:
            'superDuper' == 'superReadableDuper'
    }

    def "should help you debug all the way down"() {
        given:
            Map<String, Map<String, String>> flashtalks =
                    [Spock: [prelegent: 'Jakub Nabrdalik'], Geb: [prelegent: 'Tomek Kalkosiński']]

        expect:
            flashtalks.Spock.prelegent == 'Kalkoś'
    }

}
