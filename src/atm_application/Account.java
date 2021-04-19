
package atm_application;


public class Account {
    private int id  ;
    
    private double balance;
    private double overDraftLimit;
    private String PIN;
    private String password;
    private Client client; 

    
    public Account (int id  ,String PIN , String password){
         this.id = id ; 
         this.PIN = PIN;
         this.balance = 0;
         this.password = password;
 
    }
    
    public void setId( int id ){this.id = this.id;}
    public int getId(){return  this.id;}
       
    public void setBalance (double balance){this.balance = balance;}
    public double getBalance(){return this.balance  ;}
    
    public void setPIN (String pin){this.PIN = pin;}
    public String getPIN(){return this.PIN  ;}
    
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;} 
    
    public void setClient(Client client){this.client = client;}
    public Client getClient(){return this.client;} 
    
    
    public double deposit(double amount) {
        this.balance = this.getBalance() + amount;
        return this.balance;
    } // deposit 
    
        public double withdraw(double amount) {
            if (this.getBalance()>=amount) {
        this.balance = this.getBalance() -  amount;
      
            }
            else{
                System.out.println("Your Balance is not enough..");
                this.balance = -1;
            }
                return this.balance;
    } // Withdraw 
        
    public String toString(){
        String output = "\nAccount ID: "+ this.id + "\tPIN: "+this.PIN +"\tPassword: "+this.password;
        return output;
    }    
}




