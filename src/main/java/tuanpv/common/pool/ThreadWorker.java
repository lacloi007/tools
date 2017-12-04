package tuanpv.common.pool;

import java.util.Queue;

public class ThreadWorker extends Thread {
	private Queue<Runnable> queue;

	public ThreadWorker(Queue<Runnable> queue) {
		this.queue = queue;
	}

	public void run() {
		Runnable task;

		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						System.out.println("An error occurred while queue is waiting: " + e.getMessage());
					}
				}

				task = queue.poll();
			}

			try {
				task.run();
			} catch (RuntimeException e) {
				System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
			}
		}
	}
}