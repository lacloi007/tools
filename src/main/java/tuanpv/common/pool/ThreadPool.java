package tuanpv.common.pool;

import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ThreadPool {
	private final int nThreads;
	private final PoolWorker[] threads;
	private final LinkedBlockingQueue queue;

	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;

		queue = new LinkedBlockingQueue();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < this.nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public void execute(Runnable task) {
		synchronized (queue) {
			queue.add(task);
			queue.notify();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		while (true) {
			if (queue.isEmpty())
				break;
		}

		while (true) {
			boolean isFinish = true;
			for (PoolWorker thread : threads) {
				if (!isFinish)
					continue;

				isFinish = thread.getState() == Thread.State.WAITING;
			}

			if (isFinish)
				break;
		}

		for (PoolWorker thread : threads)
			thread.stop();
	}

	private class PoolWorker extends Thread {

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

					task = (Runnable) queue.poll();
				}

				try {
					task.run();
				} catch (RuntimeException e) {
					System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
				}
			}
		}
	}
}
