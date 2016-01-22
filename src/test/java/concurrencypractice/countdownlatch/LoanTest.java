package concurrencypractice.countdownlatch;

import org.junit.Test;

public class LoanTest {
    @Test
    public void should_pass_loan_test() throws Exception {
    	Loan loan = Loan.getInstance();
        loan = Loan.getInstance();
        loan = Loan.getInstance();
        loan.apply();
    }
}
