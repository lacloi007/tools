package tuanpv.common.pool;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
	private final int nThreads;
	private final ThreadWorker[] threads;
	private final LinkedBlockingQueue<Runnable> queue;

	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;

		queue = new LinkedBlockingQueue<>();
		threads = new ThreadWorker[nThreads];

		for (int i = 0; i < this.nThreads; i++) {
			threads[i] = new ThreadWorker(queue);
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
			for (ThreadWorker thread : threads) {
				if (!isFinish)
					continue;

				isFinish = thread.getState() == Thread.State.WAITING;
			}

			if (isFinish)
				break;
		}

		for (ThreadWorker thread : threads)
			thread.stop();
	}
}
