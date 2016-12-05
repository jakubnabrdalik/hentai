package eu.solidcraft.hentai.infrastructure;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimeService {
    public static LocalDate now() {
        return LocalDate.now(ZoneId.from(ZoneOffset.UTC));
    }
}
