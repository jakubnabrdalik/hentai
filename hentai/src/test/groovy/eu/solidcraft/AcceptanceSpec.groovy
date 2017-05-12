package eu.solidcraft

import eu.solidcraft.base.IntegrationSpec

class AcceptanceSpec extends IntegrationSpec {

    def "positive renting scenario"() {
        given: 'inventory has an old film "American Clingon Bondage" and a new release of "50 shades of Trumpet"'

        when: 'I go to /films'
        then: 'I see both films'

        when: 'I go to /points'
        then: 'I see I have no points'

        when: 'I post to /calculate with both films for 3 days'
        then: 'I can see it will cost me 120 SEK for Trumpet and 90 SEK for Clingon'

        when: 'I post to /rent with both firms for 3 days'
        then: 'I have rented both movies'

        when: 'I go to /rent'
        then: 'I see both movies are rented'

        when: 'I go to /points'
        then: 'I see I have 3 points'

        when: 'I post to /return with Trumper'
        then: 'trumper is returned'

        when: 'I go to /rent'
        then: 'I see only Clingon is rented'

    }
}
