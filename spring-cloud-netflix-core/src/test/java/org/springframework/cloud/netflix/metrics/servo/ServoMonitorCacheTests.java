package org.springframework.cloud.netflix.metrics.servo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.OutputCapture;

import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.monitor.MonitorConfig;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Spencer Gibb
 */
public class ServoMonitorCacheTests {

	@Rule
	public OutputCapture capture = new OutputCapture();

	@Mock
	MonitorRegistry monitorRegistry;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCacheWarningLog() {
		ServoMetricsConfigBean config = new ServoMetricsConfigBean();
		config.setCacheWarningThreshold(1);
		ServoMonitorCache cache = new ServoMonitorCache(monitorRegistry, config);

		cache.getTimer(MonitorConfig.builder("monitorA").build());

		assertThat(this.capture.toString(), not(containsString("timerCache is above the warning threshold")));

		cache.getTimer(MonitorConfig.builder("monitorB").build());

		assertThat(this.capture.toString(), containsString("timerCache is above the warning threshold"));
	}
}
