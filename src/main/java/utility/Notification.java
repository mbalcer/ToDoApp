package utility;

import dao.TaskDAO;
import entity.Task;
import entity.User;
import javafx.application.Platform;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Notification {

    private User user;
    private ResourceBundle properties;

    public Notification(User user) {
        this.user = user;
        this.properties = ResourceBundle.getBundle("bundles/messages");
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
            String content = String.format("%s: %s\n%s: %s\n%s: %s",
                    properties.getString("notification.content.task"),  task.getName(),
                    properties.getString("notification.content.date"), task.getDate(),
                    properties.getString("notification.content.description"), task.getDescription());
            InfoDialog.showAlert(properties.getString("notification.title"), content);
        });
    }
}
