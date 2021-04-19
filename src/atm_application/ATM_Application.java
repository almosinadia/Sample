
package atm_application;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.UUID;


public class ATM_Application {
    static String Account_id = "" ;


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int trans_id = 0;
        
        String url="jdbc:sqlserver://localHost:1433;"+ "databaseName=ATM; integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement pst = null;
                    
        Statement st = conn.createStatement();
        ResultSet result = null;
                    
        Scanner input = new Scanner(System.in);        
        
        int choice ;
        System.out.println("\nATM System User Type");
        System.out.println("-------------------------------");
        System.out.println("  1- Admin\n  2- Client\n  3- Exit");
        System.out.print("Enter your choice: ");
        choice = input.nextInt();

        while(choice !=3 ){

        //user is admin
        if (choice==1){
            // check admin username and password
            
            System.out.println("\nSign In to ATM System");
            System.out.println("---------------------------------");
            System.out.print("Username : ");
            String adminUsername = input.next();
            System.out.print("Password : ");
            String adminPassword = input.next(); 
            
            String q1 = "select * from Admin";
            pst =conn.prepareStatement(q1);
            result = pst.executeQuery();
            boolean found = false;
            while(result.next()) {
                String user1 = result.getString(3);
                String pass1 = result.getString(4);
                if (adminUsername.equals(user1) && adminPassword.equals(pass1))
                    found = true;
            }
            
            if (!found){
                System.out.println("Not Correct username or password");
                System.out.println("Press (1) to try again  OR  Press (2) to Exit");
                int x = input.nextInt();
                if(x==1){

                    continue;
                }
                else
                    System.exit(0);
            }
    
            System.out.println("\n Add new Account to ATM System");
            System.out.println("-------------------------------------");

            System.out.println("Enter Account Data:");
            System.out.print("Client ID :");
            int client_id = input.nextInt();
            System.out.print("Account ID :");
            int Account_id = input.nextInt();
            System.out.print("PIN :");
            String pin = input.next();
            System.out.print("Password :");
            String pass = input.next();
           
            
            String query1 = "select * from client where client_id ="+ client_id ;
            pst=conn.prepareStatement(query1);
            
            String c_name="" , c_phone = "",c_email="";
            result = pst.executeQuery();
            while(result.next()) {
                c_name = result.getString(2);
                c_phone = result.getString(3);
                c_email = result.getString(4);
            }            
            
            Client c = new Client(client_id,c_name , c_phone,c_email);
            Account ac = new Account( Account_id, pin,pass);
            ac.setClient(c);
           
            
            String query2 = "INSERT INTO Account VALUES ('"+Account_id+"','"+pin+"','"+pass+"',"+0+","+0+","+client_id+")";
            st.executeUpdate(query2);
            
            System.out.println("New Account Added Successfully..");


   
            System.out.println("\nATM System User Type");
            System.out.println("-------------------------------");
            System.out.println("  1- Admin\n  2- Client\n  3- Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            continue;
            
        // insert Client
        }  // end admin
        
        
        if (choice == 2){
                        
            System.out.println("\nSign In to ATM System");
            System.out.println("---------------------------------");

            System.out.println("Enter your PIN: ");
            String pin = input.next();
            System.out.println("Enter your Password: ");
            String password = input.next();            
        
            
            String q2 = "select * from Account";
            pst =conn.prepareStatement(q2);
            result = pst.executeQuery();
            
            boolean found = false;
            Account current_Acc = null;
            
            while(result.next()) {
                int id = Integer.parseInt(result.getString(1));
                String correct_pin = result.getString(2);
                String correct_pass = result.getString(3);
                if (pin.equals(correct_pin) && password.equals(correct_pass)){
                    found = true;
                    current_Acc = new Account(id,correct_pin,correct_pass);
                }
            }
            
            
                if (!found){
                    System.out.println("PIN or Password are Not valid !!!!!!!");
                    System.out.println("Press (1) to try again  OR  Press (2) to Exit");
                    int x = input.nextInt();
                    if(x==1)
                         continue;
                    else
                        System.exit(0);
                }
        
      
            while(true){
            System.out.println("\n1. Transactions History ");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            
            System.out.print("\nEnter your Choice: ");
            
            int choice2 = input.nextInt();
            switch(choice2){
                case 1:{
                    String query1 = "select * from transactions where Account_id  ="+ current_Acc.getId() ;
                    pst=conn.prepareStatement(query1);
                    result = pst.executeQuery();

                    String output="";  
                    Transaction tr = null;
                    
                    while(result.next()) {
                        
                        String id = result.getString(1);
                        output += "\nTransaction ID: "+id;
                        String type = result.getString(2);
                        output += "\tTransaction Type: "+type;
                        double amount = Double.parseDouble(result.getString(3));
                        output += "\tAmount: "+amount;
                        tr = new Transaction(id , type,amount );
                    }    
                    if (output.equals(""))
                        System.out.println("There are No transactions..");
                    else
                        System.out.println(output);
                        
                        }
                        break;
                
                case 2:{
                            // Withdraw
                            System.out.print("Enter deposit Amount: ");
                            double amount = input.nextDouble();
                            
                             String query1 = "select * from Account where Account_id  ="+ current_Acc.getId() ;
                             pst=conn.prepareStatement(query1);
                             result = pst.executeQuery();

                             double last_balance = 0;          
                             while(result.next()) {
                                last_balance = Double.parseDouble(result.getString(4));
                             }            
                            current_Acc.setBalance(last_balance);
                            double balance = current_Acc.withdraw(amount);
                            String query2 = "UPDATE Account SET balance ="+balance+" WHERE account_id = "+current_Acc.getId()+";";
                            st.executeUpdate(query2);
                            
            
                            System.out.println("Withdraw process completed Successfully..");
                            System.out.println("Your new Balance = "+balance);
                            
                            // insert into transaction
                            
                             String query3 = "select max(trans_id) from transactions" ;
                             pst=conn.prepareStatement(query3);
                             result = pst.executeQuery();
         
                             while(result.next()) {
                               trans_id = Integer.parseInt(result.getString(1));
                             }   
                             ++trans_id;
                             
                             java.util.Date utilDate = new java.util.Date();
                             java.sql.Date sqlDate = new java.sql.Date(utilDate.getDate());
                            String query4= "insert into transactions values ("+trans_id+",'Withdraw',"+amount+",'"+current_Acc.getId()+"')";
                            st.executeUpdate(query4);
                            
                        }
                        break;
                        
                case 3:{
                            // Deposit
                            System.out.print("Enter deposit Amount: ");
                            double amount = input.nextDouble();
                            
                             String query1 = "select * from Account where Account_id  ="+ current_Acc.getId() ;
                             pst=conn.prepareStatement(query1);
                             result = pst.executeQuery();

                             double last_balance = 0;          
                             while(result.next()) {
                                last_balance = Double.parseDouble(result.getString(4));
                             }            
                            current_Acc.setBalance(last_balance);
                            double balance = current_Acc.deposit(amount);
                            String query2 = "UPDATE Account SET balance ="+balance+" WHERE account_id = "+current_Acc.getId()+";";
                            st.executeUpdate(query2);
            
                            System.out.println("Deposit process completed Successfully..");
                            System.out.println("Your new Balance = "+balance);
                            
                            
                            // insert into transaction
                            
                             String query3 = "select max(trans_id) from transactions" ;
                             pst=conn.prepareStatement(query3);
                             result = pst.executeQuery();
         
                             while(result.next()) {
                               trans_id = Integer.parseInt(result.getString(1));
                             }   
                             ++trans_id;
                             
                             java.util.Date utilDate = new java.util.Date();
                             java.sql.Date sqlDate = new java.sql.Date(utilDate.getDate());
                            String query4= "insert into transactions values ("+trans_id+",'Deposit',"+amount+",'"+current_Acc.getId()+"')";
                            st.executeUpdate(query4);
                            
                
                        }
                        break;
                        
                case 4:{
                    
                    System.out.println("Enter the Account ID you want to sent to: ");
                    String id = input.next();
                    System.out.print("Enter Amount: ");
                    double amount = input.nextDouble();
                    
                    
                    // current user balance
                    String query1 = "select * from Account where Account_id  ="+ current_Acc.getId() ;
                    pst=conn.prepareStatement(query1);
                    result = pst.executeQuery();

                    double last_balance = 0;          
                    while(result.next()) {
                           last_balance = Double.parseDouble(result.getString(4));
                    }
                    if (last_balance < amount){
                        System.out.println("Amount you want to transfer it is greater than your balance.. Try again later..");
                        continue;}
                    
                    // widthdraw from current user
                    current_Acc.setBalance(last_balance);
                    double balance = current_Acc.withdraw(amount);
                    String query2 = "UPDATE Account SET balance ="+balance+" WHERE account_id = "+current_Acc.getId()+";";
                    st.executeUpdate(query2);
                    
                    // deposit to receiver account
                    
                    String query3 = "select * from Account where Account_id  ="+ id;
                    pst=conn.prepareStatement(query3);
                    result = pst.executeQuery();

                    double last_balance2 = 0;          
                    while(result.next()) {
                        last_balance2 = Double.parseDouble(result.getString(4));
                        }
                    double new_balance = last_balance2 + amount;
                        String query4 = "UPDATE Account SET balance ="+new_balance+" WHERE account_id = "+id+";";
                            st.executeUpdate(query4);
            
                    
                             // insert into transaction
                            
                             String query5 = "select max(trans_id) from transactions" ;
                             pst=conn.prepareStatement(query5);
                             result = pst.executeQuery();
         
                             while(result.next()) {
                               trans_id = Integer.parseInt(result.getString(1));
                             }   
                             ++trans_id;
                             

                            String query6= "insert into transactions values ("+trans_id+",'Transfer',"+amount+",'"+current_Acc.getId()+"')";
                            st.executeUpdate(query6);
                                             
                    
                        }
                        break;

                case 5:{
                            System.exit(0);
                        }
                        break;                        
            }//end switch
            }//end second while
        }// end choice 2
        if(choice == 3)
            System.exit(0);
    } // end while loop   
       
        
    }// end main
    

    }





