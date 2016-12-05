package eu.solidcraft.hentai

import eu.solidcraft.hentai.PasswordStrengthChecker.ValidationOutcome
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static eu.solidcraft.hentai.PasswordStrengthChecker.ValidationResult.*

class PasswordStrengthCheckerSpec extends Specification {

    @Subject
    PasswordStrengthChecker checker = new PasswordStrengthChecker()

    @Unroll
    def "password #password is #expectedOutcome"() {
        when:
            ValidationOutcome output = checker.check(password)
        then:
            output.isValid() == isValid
            output.getValidationOutcome().containsAll(expectedOutcome)
        where:
            password           || isValid | expectedOutcome
            null               || false   | [IS_NULL]
            '123HDhd'          || false   | [TOO_SHORT]
            '1234567890##HDhd' || true    | []
            '1234567890##HDHD' || false   | [HAS_NO_LOWERCASE]
            '1234567890##hdhd' || false   | [HAS_NO_UPPERCASE]
            '1234asd'          || false   | [TOO_SHORT, HAS_NO_UPPERCASE]
            '1234ASD'          || false   | [TOO_SHORT, HAS_NO_LOWERCASE]
            '123451234124'     || false   | [HAS_NO_UPPERCASE, HAS_NO_LOWERCASE]
            '12345'            || false   | [TOO_SHORT, HAS_NO_UPPERCASE, HAS_NO_LOWERCASE]
    }

}
