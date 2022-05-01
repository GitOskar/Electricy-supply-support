package pl.electricity_supply_support.util

import spock.lang.Specification

class ZipcodeUtilTest extends Specification {

    def "Should correctly extract zipcode"() {

        expect:
        ZipcodeUtil.extractGroup(zipcode) == expectedGroup

        where:
        zipcode  || expectedGroup
        "20-240" || "20"
        "30-121" || "30"
        "21-240" || "21"
    }
}
