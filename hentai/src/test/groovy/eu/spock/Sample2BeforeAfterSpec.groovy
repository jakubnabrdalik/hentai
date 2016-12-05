package eu.spock

import spock.lang.Specification

class Sample2BeforeAfterSpec extends Specification {

    def setup() {
        println 'setup is the same as @Before'
    }

    def setupSpec() {
        println 'setupSpec is the same as @BeforeClass'
    }

    def cleanup() {
        println 'cleanup is the same as @After'
    }

    def cleanupSpec() {
        println 'cleanupSpec is the same as @AfterClass'
    }

    def "should have setups and cleanups"() {
        expect:
            'to read nice print lines'
    }

}
