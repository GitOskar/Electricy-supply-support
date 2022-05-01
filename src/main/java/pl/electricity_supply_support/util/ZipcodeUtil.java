package pl.electricity_supply_support.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ZipcodeUtil {

    public static String extractGroup(String zipcode) {
        return zipcode.substring(0, 2);
    }
}
