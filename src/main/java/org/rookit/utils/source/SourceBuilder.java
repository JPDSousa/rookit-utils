
package org.rookit.utils.source;

import java.io.IOException;

import javax.annotation.processing.Filer;

@SuppressWarnings("javadoc")
public interface SourceBuilder {

    String getElementName();

    void writeTo(Filer filer) throws IOException;

}
