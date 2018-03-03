package org.rookit.utils.log;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class LogTest {

	private static final Lock SYSTEM_ERR_LOCK = new ReentrantLock();
	private static final Lock SYSTEM_OUT_LOCK = new ReentrantLock();

	private static PrintStream initialError;
	private static PrintStream initialStd;

	@BeforeClass
	public static final void saveInitialOutputs() {
		initialError = System.err;
		initialStd = System.out;
	}

	@AfterClass
	public static final void resetToInitialOutputs() {
		System.setErr(initialError);
		System.setOut(initialStd);
	}

	private LogCategory category;

	@Before
	public final void createDummyCategory() {
		category = new DummyCategory();
	}
	
	@After
	public final void clearLogManagerCache() {
		LogManager.reset();
	}

	@Ignore("There is a problem in the System print stream in the test design")
	@Test
	public final void testErrorMessageGoesToSystemErr() {
		SYSTEM_ERR_LOCK.lock();
		try {
			final ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
//			System.setErr(new PrintStream(errorOutput));

			final LogManager manager = LogManager.create(this.category);
			final Logger logger = manager.getLogger(getClass());
			final String expected4Error = "Error in: " + category.getName();
			logger.error(expected4Error);

			assertThat(new String(errorOutput.toByteArray()))
			.as("The output of errors and warns")
			.contains(expected4Error);
		} finally {
			SYSTEM_ERR_LOCK.unlock();
		}
	}

	@Ignore("There is a problem in the System print stream in the test design")
	@Test
	public final void testWarnMessageGoesToSystemErr() {
		SYSTEM_ERR_LOCK.lock();
		try {
			final ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();

			final LogManager manager = LogManager.create(this.category);
			final Logger logger = manager.getLogger(getClass());
			final String expected4Warn = "Warn in: " + category.getName();

			logger.warn(expected4Warn);

			final String actual = new String(errorOutput.toByteArray());
			assertThat(actual)
			.as("The output of warns")
			.contains(expected4Warn);
			
		} finally {
			SYSTEM_ERR_LOCK.unlock();
		}
	}

	@Ignore("There is a problem in the System print stream in the test design")
	@Test
	public final void testInfoMessageGoesToSystemOut() {
		SYSTEM_OUT_LOCK.lock();
		try {
			final ByteArrayOutputStream stdOutput = new ByteArrayOutputStream();

			final LogManager manager = LogManager.create(this.category);
			final Logger logger = manager.getLogger(getClass());
			final String expected4Info = "Info in: " + category.getName();
			logger.info(expected4Info);

			assertThat(new String(stdOutput.toByteArray()))
			.as("The output for info")
			.contains(expected4Info);
		} finally {
			SYSTEM_OUT_LOCK.unlock();
		}
	}

	private class DummyCategory extends AbstractLogCategory {

		@Override
		public String getName() {
			return "Dummy";
		}

		@Override
		public Package getPackage() {
			return LogTest.class.getPackage();
		}

	}

}
