package org.rookit.utils.builder;

import java.util.Objects;

import org.rookit.utils.log.Validator;

@SuppressWarnings("javadoc")
public abstract class IdempotentBuilder<P> implements Builder<P> {

	private final Validator validator;

	private P builtObject; 

	protected IdempotentBuilder(final Validator validator) {
		this.validator = validator;
	}

	protected final <T> T checkNotBuilt() {
		return validator.checkState()
				.is(Objects.isNull(this.builtObject), 
				"This builder is already built and as such, cannot be used.");
	}

	@Override
	public final synchronized P build() {
		if (Objects.isNull(this.builtObject)) {
			this.builtObject = doBuild();
		}
		return this.builtObject;
	}

	protected abstract P doBuild();

}
