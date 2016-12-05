package eu.solidcraft.hentai

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

class XlsxDslSpec extends Specification {



    def "Should create empty spreadsheet"() {
        given:
            SpreadsheetFactory spreadhseetFactory = new SpreadsheetFactory()
            String title = "Spreadsheet title"

        when:
            Spreadsheet spreadsheet = spreadhseetFactory.spreadsheet(title)
                .create()

        then:
            spreadsheet != null
            spreadsheet.title == title

    }

    def "Should create empty worksheet"() {
        given:
            WorksheetFactory worksheetFactory = new WorksheetFactory()
            String title = "Worksheet title"
        when:
            Worksheet worksheet = worksheetFactory.worksheet(title).create()
        then:
            worksheet != null
            worksheet.title == title

    }

    @Ignore
    def "Should fill a row with properties from an object"() {
        given:
            WorksheetFactory worksheetFactory = new WorksheetFactory()
            Properties props2 = new Properties()
            expect:
                worksheetFactory.spreadsheet("Spreadshet name")
                    .worksheet("Worksheet name 1")
                        .add(props)
                        .add(props2)
                    .worksheet("Worksheet name 2")
    }

}
