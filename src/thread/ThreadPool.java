package thread;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Task - Создать собственную реализацию Thread Pool:
 *
 * Создать собственный класс, который на входе будет получать класс,
 * реализующий Runnable и запускать его метод run в отдельном потоке при этом
 * количество потоков ограничено (задается при запуске пула потоков). Если число
 * работающих потоков больше ограничения, новые объекты ожидают завершения работы
 * выполняющихся потоков.
 */
public class ThreadPool implements Runnable {
    private int threadCount;
    private List<Thread> runningThreads = new CopyOnWriteArrayList<>();
    private Queue<Runnable> queueThreads = new ConcurrentLinkedQueue<>();
    Thread thread;

    public ThreadPool(int threadCount) {
        this.threadCount = threadCount;
        System.out.println(threadCount);
    }

    public Thread run(Runnable runnable) {

        if (runningThreads.size() < threadCount) {
            thread = new Thread(runnable);
            runningThreads.add(thread);
            thread.start();
        } else {
            queueThreads.add(runnable);
            while (true) {
                if (runningThreads.removeIf(t -> !t.isAlive())) {
                    thread = new Thread(queueThreads.poll());
                    runningThreads.add(thread);
                    thread.start();
                    break;
                }
            }
        }
        return thread;
    }

    @Override
    public void run() {

    }
}