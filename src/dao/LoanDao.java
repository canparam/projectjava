package dao;

import daoFactory.DaoFactory;
import model.Book;
import model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDao implements IGenericDao<Loan> {
    private static String ALL = "SELECT * FROM vLoan";
    private static String by_Status = "SELECT * FROM vLoan WHERE status = 2";
    private static String by_id = "SELECT * from vLoan WHERE int = ?";
    private static String by_UUID = "SELECT * from vLoan WHERE uuid = ?";
    private static String UPDATE = "UPDATE loan SET status = ? WHERE int = ?";
    private static String by_Code = "SELECT * from vLoan WHERE maHS = ?";
    private static String by_Issua = "SELECT * from vLoan WHERE int = 1";

    @Override
    public List<Loan> getAll() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(ALL);
        List<Loan> loans = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            loans.add(extractData(rs));
        }
        preparedStatement.close();

        return loans;
    }
    public List<Loan> getNotReturn() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        String sql = "SELECT * FROM vLoan WHERE status = 1";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        List<Loan> loans = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            loans.add(extractData(rs));
        }
        preparedStatement.close();

        return loans;
    }

    public List<Loan> Status() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(by_Status);
        List<Loan> loans = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            loans.add(extractData(rs));
        }
        preparedStatement.close();

        return loans;
    }
    public List<Loan> getIssua() throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(by_Issua);
        List<Loan> loans = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            loans.add(extractData(rs));
        }
        preparedStatement.close();

        return loans;
    }
    @Override
    public Loan insert(Loan loan) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Loan loan, int id) throws SQLException {
        return false;
    }
    public boolean update(int status,int id) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(UPDATE);
        preparedStatement.setInt(1, status);
        preparedStatement.setInt(2, id);
        int i = preparedStatement.executeUpdate();
        System.out.println(i);
        if (i == 1){
            return true;
        }
        return false;
    }
    @Override
    public Loan getOne(int id) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(by_id);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Loan loan = new Loan();
        while (rs.next()) {
            loan = extractData(rs);
        }
        preparedStatement.close();

        return loan;
    }
    public Loan getOneByUUID(String uuid) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(by_UUID);
        preparedStatement.setString(1, uuid);
        ResultSet rs = preparedStatement.executeQuery();
        Loan loan = new Loan();
        while (rs.next()) {
            loan = extractData(rs);
        }
        preparedStatement.close();

        return loan;
    }
    public ArrayList<Loan> getOne(String code) throws SQLException {
        Connection c = DaoFactory.sqlServer().openConnect();
        PreparedStatement preparedStatement = c.prepareStatement(by_Code);
        preparedStatement.setString(1, code);
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Loan> arrayList = new ArrayList<>();
        while (rs.next()) {
           arrayList.add(extractData(rs));
        }
        preparedStatement.close();
        return arrayList;
    }

    @Override
    public Loan extractData(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();
        loan.setId(resultSet.getInt("int"));
        loan.setcName(resultSet.getString("name"));
        loan.setbName(resultSet.getString("title"));
        loan.setaName(resultSet.getString("uname"));
        loan.setMaHS(resultSet.getString("maHS"));
        loan.setCreate_at(resultSet.getString("created_at"));
        loan.setEnd_date(resultSet.getString("end_date"));
        loan.setStatus(resultSet.getInt("status"));
        loan.setUuid(resultSet.getString("uuid"));
        return loan;
    }
}
