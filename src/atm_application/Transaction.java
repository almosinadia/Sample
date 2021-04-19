
package atm_application;


public class Transaction {
    private String trans_id ; 
    private String type;
    private Account account;
    private double amount;
    
    
    public Transaction (String trans_id, String type, double amount, Account account){
        this.trans_id = trans_id;
        this.type = type;
        this.account = account;
        this.amount = amount;
    }
    
        public Transaction (String trans_id, String type, double amount){
        this.trans_id = trans_id;
        this.type = type;
        this.amount = amount;
    }
    
    public void setTrans_id(String trans_id){this.trans_id =trans_id;}
    public String getTrans_id(){return this.trans_id;}

    public void setDate(double date){this.amount =amount;}
    public double getDate(){return this.amount;}

    public void setType(String type){this.type =type;}
    public String getType(){return this.type;}  
 

    public void setAccount(Account account){this.account =account;}
    public Account getAccount(){return this.account;}  
    
    
    
    
    
    
}
