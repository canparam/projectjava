package services;

import dao.LoanDao;
import model.Loan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LoanService {
    private LoanDao loanDao(){
        return new LoanDao();
    }


}
