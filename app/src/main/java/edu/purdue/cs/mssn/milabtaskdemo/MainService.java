package edu.purdue.cs.mssn.milabtaskdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import net.mobileinsight.milab.IMILab;
import net.mobileinsight.milab.ITask;
import net.mobileinsight.milab.TaskObject;

import java.io.File;

/**
 * Created by moonsky219 on 5/6/18.
 */

public class MainService extends Service {
    public MainService() {
    }

    protected IMILab interfaceMILab;

    BroadcastReceiver brPauseMI = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getString(R.string.tag), "Service received broadcast: " + intent.getAction());
            try {
                MainService.this.interfaceMILab.sendMsg("Task request to pause MI");
                MainService.this.interfaceMILab.pauseMI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver brResumeMI = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getString(R.string.tag), "Service received broadcast: " + intent.getAction());
            try {
                MainService.this.interfaceMILab.sendMsg("Task request to resume MI");
                MainService.this.interfaceMILab.resumeMI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver brInsertCustomMsg = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getString(R.string.tag), "Service received broadcast: " + intent.getAction());
            try {
                String strMsg = "Demo task message";
                MainService.this.interfaceMILab.sendMsg("Task request to insert custom message: " + strMsg);
                MainService.this.interfaceMILab.insertCustomMsg(strMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(getString(R.string.tag), "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(getString(R.string.tag), "onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log.i(getString(R.string.tag), "onDestroy()");
        getApplicationContext().unregisterReceiver(brPauseMI);
        getApplicationContext().unregisterReceiver(brResumeMI);

        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent intent) {
        Log.i(getString(R.string.tag), "MainService.onTaskRemoved");
        super.onTaskRemoved(intent);
    }

    private final ITask.Stub mBinder = new ITask.Stub() {
        @Override
        public int getPid() throws RemoteException {
            Log.i(getString(R.string.tag), "getPid: " + Integer.toString(Process.myPid()));
            return Process.myPid();
        }

        @Override
        public TaskObject getOutput() throws RemoteException {
            Log.i(getString(R.string.tag), "getOutput() is called.");
            TaskObject toSend = new TaskObject();
            toSend.setTaskName(getString(R.string.app_name));
            toSend.setTaskDescription("A demo task using NetLoggerMMDiag");
            String strOutput = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name)).getAbsolutePath();
            toSend.setPathOutputFolder(strOutput);
            toSend.setPluginNameMI("NetLoggerMMDiag");
            return toSend;
        }

        @Override
        public void exit() throws RemoteException {
            Log.i(getString(R.string.tag), "exit");
        }

        @Override
        public void register(IMILab interfaceMILab) throws RemoteException {
            Log.i(getString(R.string.tag), "register() is called.");
            MainService.this.interfaceMILab = interfaceMILab;
        }

        @Override
        public void run() throws RemoteException {
            Log.i(getString(R.string.tag), "run");
            MainService.this.interfaceMILab.sendMsg(getString(R.string.tag) + " started.");
            getApplicationContext().registerReceiver(brPauseMI, new IntentFilter(getString(R.string.tag) + ".MainService.PauseMI"));
            getApplicationContext().registerReceiver(brResumeMI, new IntentFilter(getString(R.string.tag) + ".MainService.ResumeMI"));
            getApplicationContext().registerReceiver(brInsertCustomMsg, new IntentFilter(getString(R.string.tag) + ".MainService.InsertCustomMsg"));
            Intent dialogIntent = new Intent(getApplicationContext(), MainActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble, String aString) throws RemoteException {

        }
    };
}
