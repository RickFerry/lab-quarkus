package domain;

import java.util.Optional;

public record Candidate(
        Long id,
        Optional<String> photo,
        String givenName,
        String familyName,
        String email,
        Optional<String> phone,
        Optional<String> jobTitle
) {
}
