package scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        int corePoolSize = 5;
        int maximumPoolSize = 10;
        long keepAliveTime = 500;
        TimeUnit unit = TimeUnit.SECONDS;

        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);

        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, unit, workQueue, handler);

        /*
        * (corePoolSize) là số lượng Thread tối thiểu trong ThreadPool. Khi khởi tạo, số lượng Thread có thể là 0. Khi nhiệm vụ
        * được thêm vào thì Thread mới được tạo ra và kể từ đây, nếu số lượng Thread ít hơn corePoolSize thì những Thread mới
        * sẽ được tạo ra đến khi số Thread bằng giá trị của corePoolSize.
        *
        * (maximumPoolSize) là số lượng tối đa các Thread trong ThreadPool.
        *
        * (keepAliveTime): khi số Thread lớn hơn corePoolSize thì keepAliveTime là thời gian tối đa mà 1 Thread "nhàn rỗi" chờ
        * nhiệm vụ. Khi hết thời gian chờ mà Thread đó chưa có nhiệm vụ thì nó sẽ bị hủy.
        *
        * (unit) là đơn vị thời gian của keepAliveTime. Trong ví dụ này thì unit của tôi là TimeUnit.SECONDS.
        *
        * (workQueue) là hàng đợi dùng để chứa các nhiệm vụ mà các Thread sẽ lấy chúng ra và thực thi lần lượt, ở đây tôi dùng ArrayBlockingQueue.
        *
        * (handler): Hành động khi một request (task) bị từ chối (rejected)
        *      + CallerRunsPolicy: Đây là một error handler được gọi đến khi một task vì lý do gì đó bị từ chối, nó sẽ được chạy lại bởi một thread mới khác
        *        (khi ThreadPool có một thread đang rảnh rỗi)
        *      + ThreadPoolExecutor.AbortPolicy: Khi một task bị từ chối chương trình sẽ throw ra một runtime RejectedExecutionException.
        *      + ThreadPoolExecutor.DiscardPolicy: Khi một task bị từ chối nó đơn gian là sẽ bị “bỏ qua” (discard), lỗi lầm gì đó cũng sẽ không bị throw ra.
               + ThreadPoolExecutor.DiscardOldestPolicy: Khi một task bị từ chối, chương trình sẽ hủy task “cũ nhất” (oldest) trong queue mà chưa được sử lý,
               * sau đó gửi task vừa bị từ chối vô queue và cố gắng xử lý lại task đó.
        */

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Run(i));
        }
    }
}

class Run implements Runnable{
    int id;

    public Run(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Tiến trình đang được thực thi " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tiến trình đã được thực thi" + id);
    }
}
