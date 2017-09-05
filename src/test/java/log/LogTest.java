package log;

import static org.junit.Assert.*;

import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.rookit.utils.log.Logs;

@SuppressWarnings("javadoc")
public class LogTest {

	@Test
	public final void test() {
		for(Logs log : Logs.values()) {
			final Logger logger = log.getLogger();
			assertNotNull(logger);
			logger.info("Working!");
		}
	}

}
