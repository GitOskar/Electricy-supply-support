package pl.electricity_supply_support.configuration;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Arrays.asList;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    private static final Locale DEFAULT_LOCALE = new Locale("en");
    private static final List<Locale> SUPPORTED_LOCALES = asList(new Locale("en"), new Locale("pl"));


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
            return DEFAULT_LOCALE;
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        Locale locale = Locale.lookup(list, SUPPORTED_LOCALES);

        return Optional.ofNullable(locale)
                .orElse(DEFAULT_LOCALE);
    }
}