package org.rookit.utils.string;

import com.google.inject.Inject;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

public final class StringUtilsImpl implements StringUtils {

    public static StringUtils create(final OptionalFactory optionalFactory) {
        return new StringUtilsImpl(optionalFactory);
    }

    private final OptionalFactory optionalFactory;

    @Inject
    private StringUtilsImpl(final OptionalFactory optionalFactory) {
        this.optionalFactory = optionalFactory;
    }

    @Override
    public int countMatchesIgnoreCase(final String str, final String sub) {
        return org.apache.commons.lang3.StringUtils.countMatches(str.toLowerCase(), sub.toLowerCase());
    }

    @Override
    public Optional<String> getWithin(final String str, final String init, final String end){
        final int initIndex = str.indexOf(init);
        if(initIndex >= 0){
            return str.contains(end)
                    ? this.optionalFactory.of(str.substring(initIndex + init.length(), str.indexOf(end, initIndex)))
                    : this.optionalFactory.of(str.substring(initIndex));
        }
        return this.optionalFactory.empty();
    }

    @Override
    public String preview(final String content, final int characters) {
        return content.substring(0, characters) + "...";
    }

    @Override
    public String toString() {
        return "StringUtilsImpl{" +
                "optionalFactory=" + this.optionalFactory +
                "}";
    }
}
