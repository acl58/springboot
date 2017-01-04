package hello;

import java.io.Serializable;

/**
 * Created by SWKIM on 2016-12-22.
 */
public class CloverRequest implements Serializable {

    private static final long serialVersionUID = -1431864443150653397L;

    private final String code = "clover"; // 통화의 이름

    private final int freeChargeTotal = 0; // 추가된무상 통화의 합계 - 취급하지는 않음
    private final int freeUseTotal = 0; // 소비된무상 통화의 합계 - 취급하지는 않음
    private final int freeBalanceTotal = 0; // 무상 잔액 - 취급하지는 않음

    private int paidChargeTotal = 0; // 추가된유상 통화의 합계
    private int paidUseTotal = 0; // 소비된유상 통화의 합계
    private int paidBalanceTotal = 0; // 유상 잔액

    private String dateOfMonth; // 데이터를 집계하는 날짜.YYYYMMDD형식)

    public int getPaidChargeTotal() {
        return paidChargeTotal;
    }

    public void setPaidChargeTotal(int paidChargeTotal) {
        this.paidChargeTotal = paidChargeTotal;
    }

    public int getPaidBalanceTotal() {
        return paidBalanceTotal;
    }

    public void setPaidBalanceTotal(int paidBalanceTotal) {
        this.paidBalanceTotal = paidBalanceTotal;
    }

    public int getPaidUseTotal() {
        return paidUseTotal;
    }

    public void setPaidUseTotal(int paidUseTotal) {
        this.paidUseTotal = paidUseTotal;
    }

    public String getCode() {
        return code;
    }

    public int getFreeChargeTotal() {
        return freeChargeTotal;
    }

    public int getFreeUseTotal() {
        return freeUseTotal;
    }

    public int getFreeBalanceTotal() {
        return freeBalanceTotal;
    }

    public String getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(String dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

}

