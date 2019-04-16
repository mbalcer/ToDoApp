package utility;

import dao.TaskDAO;
import entity.Task;
import entity.User;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Notification {

    private AtomicBoolean monitor;
    private User user;

    public Notification() {
        monitor = new AtomicBoolean();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void start() {
        monitor.set(true);
        Runnable task = () -> {
            while(monitor.get()) {
                checkTask();
                try {
                    Thread.sleep(1000*60);
                } catch(InterruptedException e) {
                    System.out.println("Oczekiwanie przerwania");
                }
            }
        };
        Thread worker = new Thread(task);
        worker.start();
    }

    public void stop() {
        monitor.set(false);
    }

    public void checkTask() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String now = dateFormat.format(new Date());
        TaskDAO taskDAO = new TaskDAO();
        List<Task> taskList = taskDAO.readAll(user.getId(), false);
        taskList.stream()
                .filter(task -> {
                    String dateTask = dateFormat.format(task.getDate());
                    return dateTask.equals(now);
                })
                .forEach(System.out::println);
    }

    private void sendNotification() {
    }
}
