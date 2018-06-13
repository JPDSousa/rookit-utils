
package org.rookit.utils.log.validator;

import org.rookit.utils.VoidUtils;
import org.rookit.utils.log.AbstractLogCategory;
import org.rookit.utils.log.LogCategory;
import org.rookit.utils.log.LogManager;

@SuppressWarnings("javadoc")
public final class UtilsValidator extends AbstractValidator {

    private static final Validator SINGLETON = new UtilsValidator(new UtilsLogCategory());

    public static Validator getDefault() {
        return SINGLETON;
    }

    private UtilsValidator(final LogCategory abstractLogCategory) {
        super(LogManager.create(abstractLogCategory));
    }

    private static class UtilsLogCategory extends AbstractLogCategory {

        @Override
        public String getName() {
            return "RookitUtils";
        }

        @Override
        public Package getPackage() {
            return VoidUtils.class.getPackage();
        }
    }
}
