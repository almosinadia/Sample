
package atm_application;


public class Client {
        
    private int id;

    private String name;
    private String phone;
    private String email;
    
    public Client(int id,String name, String phone, String email){
        this.id = id;
         this.name = name;
         this.phone = phone;
         this.email = email;
}
    
    public void setId( int id ){this.id = this.id;}
    public int getId(){return  this.id;}
    
    public void setName(String name){ this.name = name;}
    public String getName(){return  this.name ;} 
    
    public void setPhone(String phone){ this.phone = phone;}
    public String getPhone(){return  this.phone ;} 
    
    public String toString(){
        String output = "\nClient name: "+ this.name + "\tClient phone: "+this.phone +"\tClient Email: "+this.email;
        return output;
    }
    
}
