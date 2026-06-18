package util;

import java.util.concurrent.TimeUnit;

public class Delay {

	static public void pause(long time, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(time);
		} catch (InterruptedException ie) {

		}
	}

	static public void pauseSeconds(long time) {
		pause(time, TimeUnit.SECONDS);
	}

	static public void pauseMillis(long time) {
		pause(time, TimeUnit.MILLISECONDS);
	}

	static public void pauseMicro(long time) {
		pause(time, TimeUnit.MICROSECONDS);
	}

}
