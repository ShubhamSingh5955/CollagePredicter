package com.example.dell.collagepredicter;

/**
 * Created by dell on 12/29/2018.
 */

public class College {
private String College;
private String rank;
private String branch;

    public College() {
    }

    public College(String college, String rank, String branch) {
        College = college;
        this.rank = rank;
        this.branch = branch;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
