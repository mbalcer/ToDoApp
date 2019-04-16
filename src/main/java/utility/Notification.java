package utility;

import dao.TaskDAO;
import entity.Task;
import entity.User;
import javafx.application.Platform;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Notification {

    private User user;

    public Notification(User user) {
        this.user = user;
        checkTask();
    }

    public void checkTask() {
        TaskDAO taskDAO = new TaskDAO();
        List<Task> taskList = taskDAO.readAll(user.getId(), false);
        Timer timer = new Timer();
        for (Task task : taskList) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendNotification(task);
                }
            }, task.getDate());
        }
    }

    private void sendNotification(Task task) {
        Platform.runLater(() -> {
            String content = String.format("Zadanie: %s\nOpis: %s", task.getName(), task.getDescription());
            InfoDialog.showAlert("Powiadomienie!", content);
        });
    }
}
