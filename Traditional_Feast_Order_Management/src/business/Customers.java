
package business;

import java.util.ArrayList;
import model.Customer;
import tools.Acceptable;
import tools.Inputter;

public class Customers {
    // anh quản lí các khách hàng 
    public ArrayList<Customer> cusList = new ArrayList<>();
    
    // hàm tìm khách hàng trong mảng 
    public Customer searchCustomerById(String cusId){
        Customer cus;
        for (Customer customer : cusList) {
            if(customer.getCus_id().equals(cusId)){
                return customer;
            }
        }
        return null;
    }
    
    // hàm thêm một khách hàng 
    public void addNewCustomer (){
        // nhập các thông tin của khách hàng 
        // nhập id cho khách hàng 
      
        // cấm trùng id 
        boolean isCheck;
        String cusId = "";
        
        do {   
            isCheck = false;
            cusId = Inputter.getString("Input your id: ",
           "Data is invalid! Re-enter...");
            Customer cus = searchCustomerById(cusId);
            if( Acceptable.isValid(cusId, Acceptable.CUS_ID_VALID) && cus == null){
                isCheck = true;
            }else{
                System.out.println("Data is invalid! Re-enter... ");
            }
            
        } while (!isCheck);
        
        
         // nhập name
         String name = Inputter.getString("Input Your Name",
        "Data is invalid! Re-enter...", Acceptable.NAME_VALID);
        
        // nhập số điện thoại 
        String phoneNumber = Inputter.getString("Input Your phoneNumber: ",
        "Data is invalid! Re-enter...", Acceptable.PHONE_VALID);
        // nhập email 
        String email = Inputter.getString("Input Your Email",
        "Data is invalid! Re-enter...", Acceptable.EMAIL_VALID);
        
        Customer newCus = new Customer(cusId, name, phoneNumber, email);
        if(newCus != null){
            cusList.add(newCus);
            System.out.println("Adding successfully!!!");
        }  
    }
    
    // hàm cập nhập khách hàng 
    public void updateCustomer (){
       // nhập id khách hàng cần tìm 
        String keyId = "";

        keyId = Inputter.getString("Input your is customer you wanna to update",
                "Data is invalid! Re-enter... ");

        Customer updateCus = searchCustomerById(keyId);
        if (Acceptable.isValid(keyId, Acceptable.CUS_ID_VALID) && updateCus == null) {
            System.out.println("This customer does not exist!!!");
        } else {
            // nhập số điện thoại 
            String newPhoneNumber = Inputter.getString("Input Your phoneNumber",
                    "Data is invalid! Re-enter...", Acceptable.PHONE_VALID);
            // nhập name
            String newName = Inputter.getString("Input Your Name",
                    "Data is invalid! Re-enter...", Acceptable.NAME_VALID);
            // nhập email 
            String newEmail = Inputter.getString("Input Your Email",
                    "Data is invalid! Re-enter...", Acceptable.EMAIL_VALID);

            updateCus.setPhoneNumber(newPhoneNumber);
            updateCus.setName(newName);
            updateCus.setEmail(newEmail);

            cusList.set(cusList.indexOf(updateCus), updateCus);
            System.out.println("Updating successfully!!!");
        }
    }
    // hàm tìm thông tin theo name
    
    public  void seachByName(){
        
        ArrayList<Customer> searchedList = new ArrayList<>();
        // nhập name khách hàng cần tìm 
        String keyName = Inputter.getString("Input name a part of name: ",
                "Data is invalid! Re-enter...");
        // duyệt mảng và tìm 
        for (Customer cus : cusList) {
            if (cus.getName().contains(keyName)) {
                searchedList.add(cus);
            } else {
                System.out.println("No one matches the search criteria");
            }
        }
        // in ra các sinh viên đã tìm thấy 
          String str = String.format(
          "  |------------------------------------------------------------------|\n" +
          "  | Code  |Customer Name        |  Phone      | Email   \n" +
          "  |------------------------------------------------------------------|\n");
        String str1 = String.format(    
          "  |------------------------------------------------------------------|");
         
        System.out.println(str);
        for (Customer customer : searchedList) {
            System.out.println(customer.toString());
        }
        System.out.println(str1);
    }
    
}
