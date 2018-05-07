package net.mobileinsight.milab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moonsky219 on 5/6/18.
 */

public class TaskObject implements Parcelable {

    private String TaskName;
    private String TaskDescription;
    private String pathOutputFolder;
    private String pluginNameMI;

    public void setTaskName(String taskName) {
        this.TaskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.TaskDescription = taskDescription;
    }

    public void setPathOutputFolder(String pathOutputFolder) {
        this.pathOutputFolder = pathOutputFolder;
    }

    public void setPluginNameMI(String pluginNameMI) {
        this.pluginNameMI = pluginNameMI;
    }

    public static final Parcelable.Creator<TaskObject> CREATOR = new Creator<TaskObject>() {
        @Override
        public TaskObject createFromParcel(Parcel parcel) {
            return new TaskObject(parcel);
        }

        @Override
        public TaskObject[] newArray(int i) {
            return new TaskObject[i];
        }
    };

    public String getTaskName() {
        return this.TaskName;
    }

    public String getTaskDescription() {
        return this.TaskDescription;
    }

    public String getPathOutputFolder() {
        return this.pathOutputFolder;
    }

    public String getPluginNameMI() {
        return this.pluginNameMI;
    }

    public TaskObject() {
    }

    private TaskObject(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        TaskName = in.readString();
        TaskDescription = in.readString();
        pathOutputFolder = in.readString();
        pluginNameMI = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(TaskName);
        parcel.writeString(TaskDescription);
        parcel.writeString(pathOutputFolder);
        parcel.writeString(pluginNameMI);
    }
}
