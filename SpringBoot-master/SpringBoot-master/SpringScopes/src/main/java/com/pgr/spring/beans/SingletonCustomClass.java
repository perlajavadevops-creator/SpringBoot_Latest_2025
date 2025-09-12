package com.pgr.spring.beans;

class Singleton {
	private final static singleton = new Singleton();
	
	private Singleton() {}

	public static Singleton instance() {
		return singleton;
	}

}

public class SingletonCustomClass {

	public static void main(String[] args) {
		Singleton sig1 = Singleton.instance();
		Singleton sig2 = Singleton.instance();
		System.out.println(sig1.hashCode());
		System.out.println(sig2.hashCode());
	}
}
