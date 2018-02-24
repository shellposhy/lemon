package cn.com.lemon.base.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueProducer<T> implements Runnable {
	private final Logger LOG = LoggerFactory.getLogger(QueueProducer.class.getName());
	private volatile boolean isRunning = true;
	private BlockingQueue<T> queue;// Blocking queue
	private T data;
	private int SLEEP_TIME = 60000;// Message sending interval

	public QueueProducer(BlockingQueue<T> queue, T data, int sleepTime) {
		this.queue = queue;
		this.data = data;
		this.SLEEP_TIME=sleepTime;
		Thread.currentThread().setName("QueueProducer");
	}

	public void run() {
		try {
			while (isRunning) {
				Thread.sleep(SLEEP_TIME);
				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
					LOG.error("Failed to join the queue.");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public void stop() {
		isRunning = false;
	}
}
