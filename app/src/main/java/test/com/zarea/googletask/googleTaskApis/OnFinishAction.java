package test.com.zarea.googletask.googleTaskApis;

import com.google.api.services.tasks.model.Task;

/**
 * Created by zarea on 2/23/16.
 */
public interface OnFinishAction {
    public void onSuccess(Task task);
}
