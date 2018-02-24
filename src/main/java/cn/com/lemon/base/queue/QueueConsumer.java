package cn.com.lemon.base.queue;

import java.util.concurrent.BlockingQueue;

public class QueueConsumer<T> implements Runnable {
	private BlockingQueue<T> queue;
	private int SLEEP_TIME = 60000;

	public QueueConsumer(BlockingQueue<T> queue, int sleepTime) {
		this.queue = queue;
		this.SLEEP_TIME = sleepTime;
		Thread.currentThread().setName("QueueConsumer");
	}

	public void run() {
		try {
			while (true) {
				T data = queue.take();
				if (data != null) {
					Thread.sleep(SLEEP_TIME);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
