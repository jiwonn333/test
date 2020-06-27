package com.roopre.simpleboard;

public class BoardVO {

    private String seq;
    private String crt_dt;

    public BoardVO(String seq, String crt_dt) {
        this.seq = seq;
        this.crt_dt = crt_dt;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCrt_dt() {
        return crt_dt;
    }

    public void setCrt_dt(String crt_dt) {
        this.crt_dt = crt_dt;
    }

    @Override
    public String toString() {
        return "BoardVO{" +
                "seq='" + seq + '\'' +
                ", crt_dt='" + crt_dt + '\'' +
                '}';
    }
}
