package com.lubq.lm.syn;

public class DeadLock {
	public static Object locka = new Object();
	public static Object lockb = new Object();
	
	public static Thread ta = new Thread(new Runnable() {
		public void run() {
			synchronized (locka) {
				try {
					System.out.println("ta   locka finish ,try to lock b ");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (lockb) {
	
					System.out.println("ta   finish ");
				}
			}
		}
	},"T a");
	public static Thread tb = new Thread(new Runnable() {
		public void run() {
			synchronized (lockb) {
				System.out.println("tb   lockb finish ,try to lock a ");
				synchronized (locka) {

					System.out.println("tb   finish ");
				}
			}
		}
	},"T b");
	
	public static void main(String[] args) {
		ta.start();
		tb.start();
		
		
	}
	
}
