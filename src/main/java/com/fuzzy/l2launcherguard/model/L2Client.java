package com.fuzzy.l2launcherguard.model;

/**
 * Created by a.kiperku
 * Date: 28.08.2023
 */

public class L2Client {

    private final long pid;
    private final Process process;

    public L2Client(Process process) {
        this.process = process;
        this.pid = process.pid();
    }

    public boolean isAlive(){
        return process.isAlive();
    }


    public int getListingPort(){
        process.getOutputStream();
        return 1;
    }




}
