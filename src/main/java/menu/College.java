package menu;

import java.util.Arrays;

public enum College {
    AGH,
    UJ,
    PK;

    public static College resolveFaculty(String faculty) {
        return Arrays.stream(values())
                .filter(x -> x.name().equalsIgnoreCase(faculty))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Cannot find requested enum for value: " + faculty));
    }
}
