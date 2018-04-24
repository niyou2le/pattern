package com.pattern.tutor.syntax.aop.callback;

public class TimeCostExample {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		algo();
		long end = System.currentTimeMillis();
		System.out.println(((end - start) / 1000.0) + "S.");
	}
	
	static long algo() {
		long sum = 0;
		for (int i = 0; i < 1_000_000_000; i++) {
			sum += i;
		}
		return sum;
	}
}
