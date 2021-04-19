
package atm_application;

public class Admin {
    private int admin_id;
    private String name;
    private String username;
    private String password;
    private String phone;
    
        public Admin (int admin_id, String name, String username, String password,String phone)
    {
    this.admin_id = admin_id;
    this.name = name;
    this.username = username;
    this.phone = phone;
    this.password = password;
    
    }
    
    
    public void setAdmin_Id(int admin_id){this.admin_id = admin_id;}
    public int getAdmin_ID(){return this.admin_id;}

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}    
    public void setUsername(String username){this.username = username;}
    public String getUsername(){return this.username;} 
    public void setPhone(String phone){this.phone = phone;}
    public String getPhone(){return this.phone;} 
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;} 
}
