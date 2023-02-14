package com.github.morningwn.tools.utils;

/**
 * @author morningwn
 * @create in 2022/11/19 14:25
 */
public class Pair<L, R> {
    private L l;
    private R r;

    public Pair() {
    }

    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public L getL() {
        return l;
    }

    public void setL(L l) {
        this.l = l;
    }

    public R getR() {
        return r;
    }

    public void setR(R r) {
        this.r = r;
    }

    public static <L, R> Pair<L, R> of(L l, R r) {
        return new Pair<>(l, r);
    }
}
