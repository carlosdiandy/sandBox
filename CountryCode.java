package com.atps.docformat.service.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class CountryCode {

    static class Country {
        String code;
        String name;

        public Country(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
    public static String getCountryCode(String country) {
        String code = country;
        for (Country c: Arrays.stream(Locale.getISOCountries())
            .filter(
                ic -> ic.startsWith(
                    country.substring(0, 1)
                )
            )
            .collect(Collectors.toList())
            .stream()
            .map(c -> {
                Locale obj = new Locale("", c);
                Country moc = new Country(obj.getCountry(), obj.getDisplayCountry(Locale.FRENCH));
                return moc;
            })
            .collect(Collectors.toList())

        ) {
            String nc = Normalizer.normalize(c.name, Normalizer.Form.NFD);
            String tag = nc.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

            if(tag.equalsIgnoreCase(country)) {

                Locale locale = new Locale("fr", c.code);
                code = locale.getISO3Country();

                break;
            }
        }
        return code;
    }
}
