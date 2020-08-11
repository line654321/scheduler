package scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsExample {
    public static void main(String[] args) {
        // Khai báo một Thread Pool của Executors
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

//        ExecutorService executorService = Executors.newCachedThreadPool();

        // Khai báo 10 Runnable, và "quăng" chúng vào Thread Pool vừa khai báo
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyRunnable myRunnable = new MyRunnable("Runnable " + i);
            executorService.execute(myRunnable);
        }

        // Phương thức này sẽ đóng ExecutorService lại sau khi thực thi xong
        executorService.shutdown();
    }
}

class MyRunnable implements Runnable {

    // Tên của Runnable, giúp chúng ta phân biệt Runnable nào đang thực thi trong Thread Pool
    private String name;

    public MyRunnable(String name) {
        // Khởi tạo Runnable với biến name truyền vào
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " đang thực thi..." + " in thread: " + Thread.currentThread().getName());

        // Giả lập thời gian chạy của Runnable mất 2 giây
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " kết thúc.");
    }

}
