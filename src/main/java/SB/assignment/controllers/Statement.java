package SB.assignment.controllers;



public class Statement {

    private Integer id;
    private Integer account_id;
    private String datefield;
    private Integer amount;


    public Statement(Integer id, Integer account_id, String datefield, Integer amount) {
        this.id = id;
        this.account_id = account_id;
        this.datefield = datefield;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getDatefield() {
        return datefield;
    }

    public void setDatefield(String datefield) {
        this.datefield = datefield;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
