package model;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class Account {
    private String name;
    private String ID;
    private Double balance;

    public Account(String _name, String _id, Double _balance) {
        this.name = _name;
        this.ID = _id;
        this.balance = _balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("%s %s $%s", this.name, this.ID, this.balance);
    }
}
