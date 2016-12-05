package eu.solidcraft.hentai;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.CharMatcher.JAVA_LOWER_CASE;
import static com.google.common.base.CharMatcher.JAVA_UPPER_CASE;

class PasswordStrengthChecker {
    public ValidationOutcome check(String password) {
        final Set<ValidationResult> validationOutcome = new HashSet<>();
        if(password == null) {
            validationOutcome.add(ValidationResult.IS_NULL);
        } else {
            if(!hasProperLength(password)) {
                validationOutcome.add(ValidationResult.TOO_SHORT);
            } if (!hasLowerCaseLetter(password)){
                validationOutcome.add(ValidationResult.HAS_NO_LOWERCASE);
            } if (!hasNoUppercaseLetter(password)){
                validationOutcome.add(ValidationResult.HAS_NO_UPPERCASE);
            }
        }
        return new ValidationOutcome(ImmutableSet.copyOf(validationOutcome));
    }

    private boolean hasProperLength(String password) {
        return password.length() >= 8;
    }

    private boolean hasNoUppercaseLetter(String password) {
        return JAVA_UPPER_CASE.matchesAnyOf(password);
    }

    private boolean hasLowerCaseLetter(String password) {
        return JAVA_LOWER_CASE.matchesAnyOf(password);
    }

    public enum ValidationResult {
        TOO_SHORT, IS_NULL, HAS_NO_LOWERCASE, HAS_NO_UPPERCASE
    }

    @RequiredArgsConstructor
    @Getter
    public static class ValidationOutcome {
        private final Set<ValidationResult> validationOutcome;

        public boolean isValid() {
            return validationOutcome.isEmpty();
        }
    }
}
