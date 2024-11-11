package store.service;

public class MembershipService {
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;

    public int calculateMembershipDiscount(int remainAmount) {
        System.out.println(remainAmount);
        int membershipDiscount = (int) ((double) remainAmount * MEMBERSHIP_DISCOUNT_RATE);
        System.out.println(membershipDiscount);
        if (membershipDiscount > MAX_MEMBERSHIP_DISCOUNT) {
            membershipDiscount = MAX_MEMBERSHIP_DISCOUNT;
        }

        return membershipDiscount;
    }
}
