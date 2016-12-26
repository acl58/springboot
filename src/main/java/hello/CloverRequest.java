package hello;

import java.io.Serializable;

/**
 * Created by SWKIM on 2016-12-22.
 */
public class CloverRequest implements Serializable {

    private static final long serialVersionUID = -1431864443150653397L;

    private int paidChargeTotal; // 추가된유상 통화의 합계
    private int paidUseTotal; // 소비된유상 통화의 합계
    private int paidBalanceTotal; // 유상 잔액

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
}
