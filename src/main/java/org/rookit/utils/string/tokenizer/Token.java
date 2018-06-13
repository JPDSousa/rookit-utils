package org.rookit.utils.string.tokenizer;

import org.immutables.value.Value;

@Value.Immutable
public interface Token {

    String token();

    String payload();

}
