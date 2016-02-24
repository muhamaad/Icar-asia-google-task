package test.com.zarea.googletask.googleTaskApis;

import com.google.api.services.tasks.model.Tasks;

/**
 * Created by zarea on 2/23/16.
 */
public interface OnFinishGetTasks {
    void onSuccess(Tasks tasks);
}
