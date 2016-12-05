package eu.spock

import spock.lang.Specification
import spock.lang.Unroll

class Sample5WhereSpockRulesSpec extends Specification {

    def "maximum of two numbers"() {
        expect:
            Math.max(a, b) == max

        where:
            a | b | max
            1 | 3 | 3
            7 | 4 | 7
            0 | 0 | 0
    }

    @Unroll
    def "maximum of two numbers with default unrolling"() {
        expect:
            Math.max(a, b) == max

        where:
            a | b || max
            1 | 3 || 3
            7 | 4 || 7
            0 | 0 || 0
    }

    @Unroll
    def "max from #a and #b should be #max"() {
        expect:
            Math.max(a, b) == max

        where:
            a | b || max
            1 | 3 || 3
            7 | 4 || 7
            0 | 0 || 0
    }

    static List<String> staticList = ['This', 'is', 'a', 'small', 'disadvantage']

    @Unroll
    def "list contains word: #word"() {
        expect:
            ['This', 'is', 'a', 'small', 'disadvantage'].contains(word)
        where: "unfortunately, because of ASTT, where has only access to static and @Shared variables"
            word << staticList
            //could be populated from db or excel: sql.rows("select * from maxdata")
    }
}
