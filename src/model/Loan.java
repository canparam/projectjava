package model;

public class Loan {
    private int id;
    private String cName;
    private String bName;
    private String aName;
    private String create_at;
    private String end_date;
    private String uuid;
    private int status;
    private String maHS;
    public Loan() {
    }

    public Loan(int id, String cName, String bName, String aName, String create_at, String end_date, int status) {
        this.id = id;
        this.cName = cName;
        this.bName = bName;
        this.aName = aName;
        this.create_at = create_at;
        this.end_date = end_date;
        this.status = status;
    }

    public String getMaHS() {
        return maHS;
    }

    public void setMaHS(String maHS) {
        this.maHS = maHS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", cName='" + cName + '\'' +
                ", bName='" + bName + '\'' +
                ", aName='" + aName + '\'' +
                ", create_at='" + create_at + '\'' +
                ", end_date='" + end_date + '\'' +
                ", status=" + status +
                '}';
    }
}
