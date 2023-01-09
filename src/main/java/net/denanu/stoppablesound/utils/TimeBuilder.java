package net.denanu.stoppablesound.utils;

public class TimeBuilder {
	int h = 0, m = 0, s = 0, t = 0;

	public static TimeBuilder of() {
		return new TimeBuilder();
	}

	public TimeBuilder tick(final int tick) {
		this.t = tick;
		return this;
	}

	public TimeBuilder sec(final int sec) {
		this.s = sec;
		return this;
	}

	public TimeBuilder min(final int min) {
		this.m = min;
		return this;
	}

	public TimeBuilder hour(final int hour) {
		this.h = hour;
		return this;
	}

	public int build() {
		return ((60*this.h + this.m) * 60 + this.s) * 20 + this.t;
	}
}
